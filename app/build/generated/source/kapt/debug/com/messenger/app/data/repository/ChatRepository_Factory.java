package com.messenger.app.data.repository;

import com.messenger.app.data.local.dao.ChatDao;
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
public final class ChatRepository_Factory implements Factory<ChatRepository> {
  private final Provider<ChatDao> chatDaoProvider;

  public ChatRepository_Factory(Provider<ChatDao> chatDaoProvider) {
    this.chatDaoProvider = chatDaoProvider;
  }

  @Override
  public ChatRepository get() {
    return newInstance(chatDaoProvider.get());
  }

  public static ChatRepository_Factory create(Provider<ChatDao> chatDaoProvider) {
    return new ChatRepository_Factory(chatDaoProvider);
  }

  public static ChatRepository newInstance(ChatDao chatDao) {
    return new ChatRepository(chatDao);
  }
}
