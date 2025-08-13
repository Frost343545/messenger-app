package com.messenger.app.data.remote.api;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0007\bf\u0018\u00002\u00020\u0001J\"\u0010\u0002\u001a\u00020\u00032\b\b\u0001\u0010\u0004\u001a\u00020\u00052\b\b\u0001\u0010\u0006\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u0018\u0010\b\u001a\u00020\t2\b\b\u0001\u0010\n\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\u000bJ\u0018\u0010\f\u001a\u00020\u00032\b\b\u0001\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\rJ\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\u000fH\u00a7@\u00a2\u0006\u0002\u0010\u0010J\u0018\u0010\u0011\u001a\u00020\t2\b\b\u0001\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\rJ\u001e\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\t0\u000f2\b\b\u0001\u0010\u0006\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\rJ\"\u0010\u0013\u001a\u00020\u00032\b\b\u0001\u0010\u0004\u001a\u00020\u00052\b\b\u0001\u0010\u0006\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\"\u0010\u0014\u001a\u00020\t2\b\b\u0001\u0010\u0004\u001a\u00020\u00052\b\b\u0001\u0010\n\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\u0015\u00a8\u0006\u0016"}, d2 = {"Lcom/messenger/app/data/remote/api/ChatApi;", "", "addParticipant", "", "chatId", "", "userId", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createChat", "Lcom/messenger/app/data/model/Chat;", "chat", "(Lcom/messenger/app/data/model/Chat;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteChat", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllChats", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getChatById", "getUserChats", "removeParticipant", "updateChat", "(Ljava/lang/String;Lcom/messenger/app/data/model/Chat;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface ChatApi {
    
    @retrofit2.http.GET(value = "chats")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllChats(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.messenger.app.data.model.Chat>> $completion);
    
    @retrofit2.http.GET(value = "chats/{id}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getChatById(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String chatId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.messenger.app.data.model.Chat> $completion);
    
    @retrofit2.http.POST(value = "chats")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object createChat(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.messenger.app.data.model.Chat chat, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.messenger.app.data.model.Chat> $completion);
    
    @retrofit2.http.PUT(value = "chats/{id}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateChat(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String chatId, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.messenger.app.data.model.Chat chat, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.messenger.app.data.model.Chat> $completion);
    
    @retrofit2.http.DELETE(value = "chats/{id}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteChat(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String chatId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion);
    
    @retrofit2.http.GET(value = "users/{userId}/chats")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getUserChats(@retrofit2.http.Path(value = "userId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.messenger.app.data.model.Chat>> $completion);
    
    @retrofit2.http.POST(value = "chats/{chatId}/participants")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object addParticipant(@retrofit2.http.Path(value = "chatId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String chatId, @retrofit2.http.Query(value = "userId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion);
    
    @retrofit2.http.DELETE(value = "chats/{chatId}/participants/{userId}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object removeParticipant(@retrofit2.http.Path(value = "chatId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String chatId, @retrofit2.http.Path(value = "userId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion);
}