package com.messenger.app.data.remote.repository;

import com.messenger.app.data.remote.api.MessageApi;
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
public final class RemoteMessageRepository_Factory implements Factory<RemoteMessageRepository> {
  private final Provider<MessageApi> messageApiProvider;

  public RemoteMessageRepository_Factory(Provider<MessageApi> messageApiProvider) {
    this.messageApiProvider = messageApiProvider;
  }

  @Override
  public RemoteMessageRepository get() {
    return newInstance(messageApiProvider.get());
  }

  public static RemoteMessageRepository_Factory create(Provider<MessageApi> messageApiProvider) {
    return new RemoteMessageRepository_Factory(messageApiProvider);
  }

  public static RemoteMessageRepository newInstance(MessageApi messageApi) {
    return new RemoteMessageRepository(messageApi);
  }
}
