import jwt from 'jsonwebtoken';

export function authenticateSocket(socket, next) {
  try {
    const token = socket.handshake.auth?.token || socket.handshake.headers.authorization?.replace('Bearer ', '');
    if (!token) return next(new Error('Unauthorized'));
    const payload = jwt.verify(token, process.env.JWT_SECRET || 'dev_secret');
    socket.user = { id: payload.sub };
    return next();
  } catch (e) {
    return next(new Error('Unauthorized'));
  }
}


