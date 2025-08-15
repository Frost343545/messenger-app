import { Router } from 'express';
import { Op, fn, col, where } from 'sequelize';
import { User } from '../models/index.js';
import { authRequired } from '../middleware/auth.js';

const router = Router();

router.get('/me', authRequired, async (req, res) => {
  const user = await User.findByPk(req.user.id, { attributes: ['id', 'username', 'displayName', 'avatarUrl', 'status'] });
  return res.json({ user });
});

router.get('/search', authRequired, async (req, res) => {
  const q = (req.query.q || '').toString().trim();
  if (!q) return res.json({ users: [] });
  const users = await User.findAll({
    where: {
      username: where(fn('LOWER', col('username')), { [Op.like]: `%${q.toLowerCase()}%` }),
    },
    limit: 20,
    attributes: ['id', 'username', 'displayName', 'avatarUrl', 'status'],
  });
  return res.json({ users });
});

export default router;


