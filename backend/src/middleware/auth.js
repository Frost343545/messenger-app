import jwt from 'jsonwebtoken';
import { User } from '../models/index.js';

export function authRequired(req, res, next) {
  const header = req.headers.authorization || '';
  const token = header.startsWith('Bearer ') ? header.slice(7) : null;
  if (!token) return res.status(401).json({ error: 'Unauthorized' });
  try {
    const payload = jwt.verify(token, process.env.JWT_SECRET || 'dev_secret');
    req.user = { id: payload.sub };
    return next();
  } catch (e) {
    return res.status(401).json({ error: 'Invalid token' });
  }
}


