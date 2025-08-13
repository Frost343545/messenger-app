package com.messenger.app.data.remote.repository;

import com.messenger.app.data.remote.api.ChatApi;
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
public final class RemoteChatRepository_Factory implements Factory<RemoteChatRepository> {
  private final Provider<ChatApi> chatApiProvider;

  public RemoteChatRepository_Factory(Provider<ChatApi> chatApiProvider) {
    this.chatApiProvider = chatApiProvider;
  }

  @Override
  public RemoteChatRepository get() {
    return newInstance(chatApiProvider.get());
  }

  public static RemoteChatRepository_Factory create(Provider<ChatApi> chatApiProvider) {
    return new RemoteChatRepository_Factory(chatApiProvider);
  }

  public static RemoteChatRepository newInstance(ChatApi chatApi) {
    return new RemoteChatRepository(chatApi);
  }
}
