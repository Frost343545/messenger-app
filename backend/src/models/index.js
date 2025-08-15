import { DataTypes, Model } from 'sequelize';
import { sequelize } from '../config/database.js';

export class User extends Model {}
export class Conversation extends Model {}
export class ConversationMember extends Model {}
export class Message extends Model {}
export class MessageRead extends Model {}

export function initModels() {
  User.init(
    {
      id: { type: DataTypes.UUID, defaultValue: DataTypes.UUIDV4, primaryKey: true },
      username: { type: DataTypes.STRING(50), allowNull: false, unique: true },
      displayName: { type: DataTypes.STRING(100), allowNull: false },
      passwordHash: { type: DataTypes.STRING(255), allowNull: false },
      avatarUrl: { type: DataTypes.STRING(255), allowNull: true },
      status: { type: DataTypes.STRING(50), allowNull: true },
    },
    { sequelize, tableName: 'users' }
  );

  Conversation.init(
    {
      id: { type: DataTypes.UUID, defaultValue: DataTypes.UUIDV4, primaryKey: true },
      type: { type: DataTypes.ENUM('private', 'group'), allowNull: false, defaultValue: 'private' },
      title: { type: DataTypes.STRING(255), allowNull: true },
      lastMessageAt: { type: DataTypes.DATE, allowNull: true },
    },
    { sequelize, tableName: 'conversations' }
  );

  ConversationMember.init(
    {
      id: { type: DataTypes.UUID, defaultValue: DataTypes.UUIDV4, primaryKey: true },
      conversationId: { type: DataTypes.UUID, allowNull: false },
      userId: { type: DataTypes.UUID, allowNull: false },
      role: { type: DataTypes.ENUM('member', 'admin'), allowNull: false, defaultValue: 'member' },
    },
    {
      sequelize,
      tableName: 'conversation_members',
      indexes: [
        { name: 'cm_conv_user_unique', unique: true, fields: ['conversation_id', 'user_id'] },
        { name: 'cm_user_idx', fields: ['user_id'] },
      ],
    }
  );

  Message.init(
    {
      id: { type: DataTypes.UUID, defaultValue: DataTypes.UUIDV4, primaryKey: true },
      content: { type: DataTypes.TEXT, allowNull: false },
      conversationId: { type: DataTypes.UUID, allowNull: false },
      senderId: { type: DataTypes.UUID, allowNull: false },
    },
    {
      sequelize,
      tableName: 'messages',
      indexes: [
        { name: 'msg_conv_created_idx', fields: ['conversation_id', 'created_at'] },
        { name: 'msg_sender_idx', fields: ['sender_id'] },
      ],
    }
  );

  MessageRead.init(
    {
      id: { type: DataTypes.UUID, defaultValue: DataTypes.UUIDV4, primaryKey: true },
      messageId: { type: DataTypes.UUID, allowNull: false },
      userId: { type: DataTypes.UUID, allowNull: false },
      readAt: { type: DataTypes.DATE, allowNull: false, defaultValue: DataTypes.NOW },
    },
    {
      sequelize,
      tableName: 'message_reads',
      indexes: [
        { name: 'mr_message_user_unique', unique: true, fields: ['message_id', 'user_id'] },
        { name: 'mr_user_idx', fields: ['user_id'] },
      ],
    }
  );

  // Associations
  User.hasMany(Message, { foreignKey: { name: 'senderId', allowNull: false }, as: 'messages' });
  Message.belongsTo(User, { foreignKey: { name: 'senderId', allowNull: false }, as: 'sender' });

  Conversation.hasMany(Message, { foreignKey: { name: 'conversationId', allowNull: false }, as: 'messages' });
  Message.belongsTo(Conversation, { foreignKey: { name: 'conversationId', allowNull: false }, as: 'conversation' });

  User.belongsToMany(Conversation, { through: ConversationMember, foreignKey: 'userId', otherKey: 'conversationId', as: 'conversations' });
  Conversation.belongsToMany(User, { through: ConversationMember, foreignKey: 'conversationId', otherKey: 'userId', as: 'members' });

  Message.hasMany(MessageRead, { foreignKey: { name: 'messageId', allowNull: false }, as: 'reads' });
  MessageRead.belongsTo(Message, { foreignKey: { name: 'messageId', allowNull: false }, as: 'message' });
  User.hasMany(MessageRead, { foreignKey: { name: 'userId', allowNull: false }, as: 'messageReads' });
  MessageRead.belongsTo(User, { foreignKey: { name: 'userId', allowNull: false }, as: 'user' });
}


