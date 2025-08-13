package com.messenger.app.ui.viewmodel;

import com.messenger.app.data.repository.ChatRepository;
import com.messenger.app.data.repository.UserRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class ChatsViewModel_Factory implements Factory<ChatsViewModel> {
  private final Provider<ChatRepository> chatRepositoryProvider;

  private final Provider<UserRepository> userRepositoryProvider;

  public ChatsViewModel_Factory(Provider<ChatRepository> chatRepositoryProvider,
      Provider<UserRepository> userRepositoryProvider) {
    this.chatRepositoryProvider = chatRepositoryProvider;
    this.userRepositoryProvider = userRepositoryProvider;
  }

  @Override
  public ChatsViewModel get() {
    return newInstance(chatRepositoryProvider.get(), userRepositoryProvider.get());
  }

  public static ChatsViewModel_Factory create(Provider<ChatRepository> chatRepositoryProvider,
      Provider<UserRepository> userRepositoryProvider) {
    return new ChatsViewModel_Factory(chatRepositoryProvider, userRepositoryProvider);
  }

  public static ChatsViewModel newInstance(ChatRepository chatRepository,
      UserRepository userRepository) {
    return new ChatsViewModel(chatRepository, userRepository);
  }
}
