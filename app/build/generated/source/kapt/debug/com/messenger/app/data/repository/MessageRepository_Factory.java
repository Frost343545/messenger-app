package com.messenger.app.data.repository;

import com.messenger.app.data.local.dao.MessageDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class MessageRepository_Factory implements Factory<MessageRepository> {
  private final Provider<MessageDao> messageDaoProvider;

  public MessageRepository_Factory(Provider<MessageDao> messageDaoProvider) {
    this.messageDaoProvider = messageDaoProvider;
  }

  @Override
  public MessageRepository get() {
    return newInstance(messageDaoProvider.get());
  }

  public static MessageRepository_Factory create(Provider<MessageDao> messageDaoProvider) {
    return new MessageRepository_Factory(messageDaoProvider);
  }

  public static MessageRepository newInstance(MessageDao messageDao) {
    return new MessageRepository(messageDao);
  }
}
