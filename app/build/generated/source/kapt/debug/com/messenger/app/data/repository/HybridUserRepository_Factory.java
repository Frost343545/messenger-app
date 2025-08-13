package com.messenger.app.data.repository;

import com.messenger.app.data.local.dao.UserDao;
import com.messenger.app.data.remote.repository.RemoteUserRepository;
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
public final class HybridUserRepository_Factory implements Factory<HybridUserRepository> {
  private final Provider<UserDao> localUserDaoProvider;

  private final Provider<RemoteUserRepository> remoteUserRepositoryProvider;

  public HybridUserRepository_Factory(Provider<UserDao> localUserDaoProvider,
      Provider<RemoteUserRepository> remoteUserRepositoryProvider) {
    this.localUserDaoProvider = localUserDaoProvider;
    this.remoteUserRepositoryProvider = remoteUserRepositoryProvider;
  }

  @Override
  public HybridUserRepository get() {
    return newInstance(localUserDaoProvider.get(), remoteUserRepositoryProvider.get());
  }

  public static HybridUserRepository_Factory create(Provider<UserDao> localUserDaoProvider,
      Provider<RemoteUserRepository> remoteUserRepositoryProvider) {
    return new HybridUserRepository_Factory(localUserDaoProvider, remoteUserRepositoryProvider);
  }

  public static HybridUserRepository newInstance(UserDao localUserDao,
      RemoteUserRepository remoteUserRepository) {
    return new HybridUserRepository(localUserDao, remoteUserRepository);
  }
}
