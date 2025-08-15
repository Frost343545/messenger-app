import { Sequelize } from 'sequelize';

const {
  DB_DIALECT = process.env.MYSQL_DIALECT || 'mysql',
  DB_HOST = process.env.MYSQL_HOST || 'localhost',
  DB_PORT = process.env.MYSQL_PORT || '3306',
  DB_NAME = process.env.MYSQL_DATABASE || 'vibe',
  DB_USER = process.env.MYSQL_USERNAME || 'root',
  DB_PASS = process.env.MYSQL_PASSWORD || '',
  DB_SSL = process.env.MYSQL_SSL || process.env.DB_SSL || 'false',
} = process.env;

let sequelize;
if (DB_DIALECT === 'sqlite') {
  sequelize = new Sequelize({
    dialect: 'sqlite',
    storage: process.env.DB_STORAGE || 'vibe.sqlite',
    logging: false,
    define: { underscored: true },
  });
} else {
  const dialectOptions = {};
  if (String(DB_SSL).toLowerCase() === 'true') {
    dialectOptions.ssl = { rejectUnauthorized: false };
  }
  sequelize = new Sequelize(DB_NAME, DB_USER, DB_PASS, {
    host: DB_HOST,
    port: Number(DB_PORT),
    dialect: 'mysql',
    logging: false,
    dialectOptions,
    define: {
      underscored: true,
    },
  });
}

export { sequelize };


