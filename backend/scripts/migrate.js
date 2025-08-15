import 'dotenv/config';
import { sequelize } from '../src/config/database.js';
import { initModels } from '../src/models/index.js';

async function migrate() {
  try {
    initModels();
    const force = process.argv.includes('--force');
    const alter = process.argv.includes('--alter') || !force;
    console.log('Connecting to DB:', {
      dialect: process.env.DB_DIALECT || process.env.MYSQL_DIALECT || 'mysql',
      host: process.env.MYSQL_HOST || process.env.DB_HOST,
      port: process.env.MYSQL_PORT || process.env.DB_PORT,
      database: process.env.MYSQL_DATABASE || process.env.DB_NAME,
      user: process.env.MYSQL_USERNAME || process.env.DB_USER,
      ssl: process.env.MYSQL_SSL || process.env.DB_SSL,
    });
    await sequelize.authenticate();
    console.log('DB connection OK');
    await sequelize.sync({ force, alter });
    console.log('Migration complete');
    await sequelize.close();
  } catch (err) {
    console.error('Migration failed:', err);
    process.exit(1);
  }
}

migrate();


