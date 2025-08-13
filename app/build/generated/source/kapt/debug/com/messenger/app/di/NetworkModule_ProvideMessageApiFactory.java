package com.messenger.app.di;

import com.messenger.app.data.remote.api.MessageApi;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;

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
public final class NetworkModule_ProvideMessageApiFactory implements Factory<MessageApi> {
  private final Provider<Retrofit> retrofitProvider;

  public NetworkModule_ProvideMessageApiFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public MessageApi get() {
    return provideMessageApi(retrofitProvider.get());
  }

  public static NetworkModule_ProvideMessageApiFactory create(Provider<Retrofit> retrofitProvider) {
    return new NetworkModule_ProvideMessageApiFactory(retrofitProvider);
  }

  public static MessageApi provideMessageApi(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideMessageApi(retrofit));
  }
}
