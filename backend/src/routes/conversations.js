import { Router } from 'express';
import { Op } from 'sequelize';
import { sequelize } from '../config/database.js';
import { authRequired } from '../middleware/auth.js';
import { Conversation, ConversationMember, Message, User } from '../models/index.js';

const router = Router();

// List conversations for current user
router.get('/', authRequired, async (req, res) => {
  const userId = req.user.id;
  const memberships = await ConversationMember.findAll({ where: { userId }, attributes: ['conversationId'] });
  const conversationIds = memberships.map((m) => m.conversationId);
  if (conversationIds.length === 0) return res.json({ conversations: [] });

  const conversations = await Conversation.findAll({
    where: { id: conversationIds },
    order: [['lastMessageAt', 'DESC']],
    include: [
      { model: User, as: 'members', attributes: ['id', 'username', 'displayName', 'avatarUrl'] },
    ],
  });

  // Load last message per conversation
  const lastMessages = await Message.findAll({
    where: { conversationId: { [Op.in]: conversationIds } },
    order: [['createdAt', 'DESC']],
  });
  const convIdToLast = new Map();
  for (const msg of lastMessages) {
    if (!convIdToLast.has(msg.conversationId)) convIdToLast.set(msg.conversationId, msg);
  }

  const result = conversations.map((c) => ({
    id: c.id,
    type: c.type,
    title: c.title,
    lastMessageAt: c.lastMessageAt,
    members: c.members,
    lastMessage: convIdToLast.get(c.id) || null,
  }));
  return res.json({ conversations: result });
});

// Create or get private conversation with another user
router.post('/private', authRequired, async (req, res) => {
  const userId = req.user.id;
  const { userId: otherUserId } = req.body;
  if (!otherUserId || otherUserId === userId) return res.status(400).json({ error: 'Invalid userId' });

  // Find existing
  const candidates = await Conversation.findAll({ where: { type: 'private' } });
  for (const c of candidates) {
    const members = await ConversationMember.findAll({ where: { conversationId: c.id } });
    const ids = members.map((m) => m.userId);
    if (ids.length === 2 && ids.includes(userId) && ids.includes(otherUserId)) {
      return res.json({ conversation: { id: c.id, type: c.type, title: c.title } });
    }
  }

  const t = await sequelize.transaction();
  try {
    const conv = await Conversation.create({ type: 'private' }, { transaction: t });
    await ConversationMember.bulkCreate([
      { conversationId: conv.id, userId, role: 'member' },
      { conversationId: conv.id, userId: otherUserId, role: 'member' },
    ], { transaction: t });
    await t.commit();
    return res.json({ conversation: { id: conv.id, type: conv.type, title: conv.title } });
  } catch (e) {
    await t.rollback();
    return res.status(500).json({ error: 'Failed to create conversation' });
  }
});

// Create group conversation
router.post('/group', authRequired, async (req, res) => {
  const userId = req.user.id;
  const { title, memberIds } = req.body;
  if (!title) return res.status(400).json({ error: 'title required' });
  const uniqueMemberIds = Array.from(new Set([userId, ...(Array.isArray(memberIds) ? memberIds : [])]));
  const t = await sequelize.transaction();
  try {
    const conv = await Conversation.create({ type: 'group', title }, { transaction: t });
    await ConversationMember.bulkCreate(uniqueMemberIds.map((id) => ({ conversationId: conv.id, userId: id, role: id === userId ? 'admin' : 'member' })), { transaction: t });
    await t.commit();
    return res.json({ conversation: { id: conv.id, type: conv.type, title: conv.title } });
  } catch (e) {
    await t.rollback();
    return res.status(500).json({ error: 'Failed to create group' });
  }
});

// Get messages with pagination
router.get('/:id/messages', authRequired, async (req, res) => {
  const userId = req.user.id;
  const conversationId = req.params.id;
  const membership = await ConversationMember.findOne({ where: { userId, conversationId } });
  if (!membership) return res.status(403).json({ error: 'Forbidden' });
  const limit = Math.min(parseInt(req.query.limit || '50', 10), 100);
  const before = req.query.before ? new Date(req.query.before) : null;
  const where = { conversationId };
  if (!Number.isNaN(before?.getTime())) where.createdAt = { [Op.lt]: before };
  const messages = await Message.findAll({ where, order: [['createdAt', 'DESC']], limit });
  return res.json({ messages });
});

// Send message via REST (optional; realtime via Socket.IO preferred)
router.post('/:id/messages', authRequired, async (req, res) => {
  const userId = req.user.id;
  const conversationId = req.params.id;
  const { content } = req.body;
  if (!content) return res.status(400).json({ error: 'content required' });
  const membership = await ConversationMember.findOne({ where: { userId, conversationId } });
  if (!membership) return res.status(403).json({ error: 'Forbidden' });
  const message = await Message.create({ conversationId, senderId: userId, content });
  await Conversation.update({ lastMessageAt: new Date() }, { where: { id: conversationId } });
  return res.json({ message });
});

export default router;


