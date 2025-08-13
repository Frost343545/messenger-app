package com.messenger.app.ui.viewmodel;

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
public final class ContactsViewModel_Factory implements Factory<ContactsViewModel> {
  private final Provider<UserRepository> userRepositoryProvider;

  public ContactsViewModel_Factory(Provider<UserRepository> userRepositoryProvider) {
    this.userRepositoryProvider = userRepositoryProvider;
  }

  @Override
  public ContactsViewModel get() {
    return newInstance(userRepositoryProvider.get());
  }

  public static ContactsViewModel_Factory create(Provider<UserRepository> userRepositoryProvider) {
    return new ContactsViewModel_Factory(userRepositoryProvider);
  }

  public static ContactsViewModel newInstance(UserRepository userRepository) {
    return new ContactsViewModel(userRepository);
  }
}
