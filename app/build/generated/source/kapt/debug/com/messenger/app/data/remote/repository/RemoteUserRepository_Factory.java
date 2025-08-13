package com.messenger.app.data.remote.repository;

import com.messenger.app.data.remote.api.UserApi;
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
public final class RemoteUserRepository_Factory implements Factory<RemoteUserRepository> {
  private final Provider<UserApi> userApiProvider;

  public RemoteUserRepository_Factory(Provider<UserApi> userApiProvider) {
    this.userApiProvider = userApiProvider;
  }

  @Override
  public RemoteUserRepository get() {
    return newInstance(userApiProvider.get());
  }

  public static RemoteUserRepository_Factory create(Provider<UserApi> userApiProvider) {
    return new RemoteUserRepository_Factory(userApiProvider);
  }

  public static RemoteUserRepository newInstance(UserApi userApi) {
    return new RemoteUserRepository(userApi);
  }
}
