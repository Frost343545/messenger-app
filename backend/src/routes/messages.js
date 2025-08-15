import { Router } from 'express';
import { authRequired } from '../middleware/auth.js';
import { ConversationMember, Message, MessageRead } from '../models/index.js';

const router = Router();

// Mark message as read
router.post('/:id/read', authRequired, async (req, res) => {
  const userId = req.user.id;
  const messageId = req.params.id;
  const message = await Message.findByPk(messageId);
  if (!message) return res.status(404).json({ error: 'Message not found' });
  const membership = await ConversationMember.findOne({ where: { userId, conversationId: message.conversationId } });
  if (!membership) return res.status(403).json({ error: 'Forbidden' });
  await MessageRead.findOrCreate({ where: { messageId, userId }, defaults: { readAt: new Date() } });
  return res.json({ ok: true });
});

export default router;


