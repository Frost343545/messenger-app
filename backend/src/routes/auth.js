import { Router } from 'express';
import { User } from '../models/index.js';
import { hashPassword, verifyPassword, signAccessToken } from '../utils/crypto.js';

const router = Router();

router.post('/register', async (req, res) => {
  try {
    const { username, password, displayName } = req.body;
    if (!username || !password || !displayName) {
      return res.status(400).json({ error: 'username, password, displayName required' });
    }
    const exists = await User.findOne({ where: { username } });
    if (exists) return res.status(409).json({ error: 'Username already taken' });
    const passwordHash = await hashPassword(password);
    const user = await User.create({ username, passwordHash, displayName });
    const token = signAccessToken(user.id);
    return res.json({ token, user: { id: user.id, username, displayName } });
  } catch (e) {
    return res.status(500).json({ error: 'Server error' });
  }
});

router.post('/login', async (req, res) => {
  try {
    const { username, password } = req.body;
    if (!username || !password) return res.status(400).json({ error: 'username, password required' });
    const user = await User.findOne({ where: { username } });
    if (!user) return res.status(401).json({ error: 'Invalid credentials' });
    const ok = await verifyPassword(password, user.passwordHash);
    if (!ok) return res.status(401).json({ error: 'Invalid credentials' });
    const token = signAccessToken(user.id);
    return res.json({ token, user: { id: user.id, username: user.username, displayName: user.displayName } });
  } catch (e) {
    return res.status(500).json({ error: 'Server error' });
  }
});

export default router;


