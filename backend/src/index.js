import 'dotenv/config';
import express from 'express';
import http from 'http';
import cors from 'cors';
import { Server as SocketIOServer } from 'socket.io';
import { sequelize } from './config/database.js';
import { initModels, User, Conversation, ConversationMember, Message } from './models/index.js';
import authRouter from './routes/auth.js';
import conversationsRouter from './routes/conversations.js';
import messagesRouter from './routes/messages.js';
import usersRouter from './routes/users.js';
import { authenticateSocket } from './middleware/socketAuth.js';

const app = express();

app.use(cors({
  origin: process.env.CORS_ORIGIN?.split(',') || '*',
  credentials: true,
}));
app.use(express.json({ limit: '1mb' }));

app.get('/api/health', (req, res) => {
  res.json({ ok: true, service: 'vibe-backend' });
});

app.use('/api/auth', authRouter);
app.use('/api/conversations', conversationsRouter);
app.use('/api/users', usersRouter);
app.use('/api/messages', messagesRouter);

const server = http.createServer(app);

const io = new SocketIOServer(server, {
  cors: {
    origin: process.env.CORS_ORIGIN?.split(',') || '*',
    methods: ['GET', 'POST'],
  },
  allowEIO3: true,
});

io.use(authenticateSocket);

io.on('connection', async (socket) => {
  const userId = socket.user?.id;
  if (!userId) return socket.disconnect(true);

  socket.join(`user:${userId}`);
  io.emit('presence:update', { userId, online: true });

  socket.on('conversation:join', (conversationId) => {
    socket.join(`conv:${conversationId}`);
  });

  socket.on('message:send', async ({ conversationId, content }) => {
    if (!conversationId || !content) return;
    const isMember = await ConversationMember.findOne({ where: { conversationId, userId } });
    if (!isMember) return;
    const message = await Message.create({ conversationId, senderId: userId, content });
    await Conversation.update({ lastMessageAt: new Date() }, { where: { id: conversationId } });
    io.to(`conv:${conversationId}`).emit('message:new', {
      id: message.id,
      conversationId,
      senderId: userId,
      content,
      createdAt: message.createdAt,
    });
  });

  socket.on('typing:start', ({ conversationId }) => {
    if (!conversationId) return;
    io.to(`conv:${conversationId}`).emit('typing', { conversationId, userId, typing: true });
  });

  socket.on('typing:stop', ({ conversationId }) => {
    if (!conversationId) return;
    io.to(`conv:${conversationId}`).emit('typing', { conversationId, userId, typing: false });
  });

  socket.on('message:read', ({ messageId, conversationId }) => {
    if (!messageId || !conversationId) return;
    io.to(`conv:${conversationId}`).emit('message:read', { messageId, userId, conversationId, readAt: new Date().toISOString() });
  });

  socket.on('disconnect', () => {
    io.emit('presence:update', { userId, online: false });
  });
});

const PORT = process.env.PORT || 4000;

async function start() {
  try {
    await sequelize.authenticate();
    initModels();
    await sequelize.sync();
    server.listen(PORT, () => {
      // eslint-disable-next-line no-console
      console.log(`VIBE backend listening on port ${PORT}`);
    });
  } catch (err) {
    // eslint-disable-next-line no-console
    console.error('Failed to start server', err);
    process.exit(1);
  }
}

start();


