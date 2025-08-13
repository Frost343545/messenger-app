package com.messenger.app.data.remote.api;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u000b\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\b\b\u0001\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0018\u0010\u0007\u001a\u00020\b2\b\b\u0001\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J2\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\n2\b\b\u0001\u0010\u000b\u001a\u00020\u00052\b\b\u0003\u0010\f\u001a\u00020\r2\b\b\u0003\u0010\u000e\u001a\u00020\rH\u00a7@\u00a2\u0006\u0002\u0010\u000fJ\u001e\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\b0\n2\b\b\u0001\u0010\u000b\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0018\u0010\u0011\u001a\u00020\u00032\b\b\u0001\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0018\u0010\u0012\u001a\u00020\u00032\b\b\u0001\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0018\u0010\u0013\u001a\u00020\b2\b\b\u0001\u0010\u0014\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\u0015J\"\u0010\u0016\u001a\u00020\b2\b\b\u0001\u0010\u0004\u001a\u00020\u00052\b\b\u0001\u0010\u0014\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\u0017\u00a8\u0006\u0018"}, d2 = {"Lcom/messenger/app/data/remote/api/MessageApi;", "", "deleteMessage", "", "messageId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMessageById", "Lcom/messenger/app/data/model/Message;", "getMessagesByChatId", "", "chatId", "limit", "", "offset", "(Ljava/lang/String;IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getUnreadMessages", "markMessageAsDelivered", "markMessageAsRead", "sendMessage", "message", "(Lcom/messenger/app/data/model/Message;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateMessage", "(Ljava/lang/String;Lcom/messenger/app/data/model/Message;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface MessageApi {
    
    @retrofit2.http.GET(value = "chats/{chatId}/messages")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getMessagesByChatId(@retrofit2.http.Path(value = "chatId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String chatId, @retrofit2.http.Query(value = "limit")
    int limit, @retrofit2.http.Query(value = "offset")
    int offset, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.messenger.app.data.model.Message>> $completion);
    
    @retrofit2.http.GET(value = "messages/{id}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getMessageById(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String messageId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.messenger.app.data.model.Message> $completion);
    
    @retrofit2.http.POST(value = "messages")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object sendMessage(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.messenger.app.data.model.Message message, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.messenger.app.data.model.Message> $completion);
    
    @retrofit2.http.PUT(value = "messages/{id}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateMessage(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String messageId, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.messenger.app.data.model.Message message, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.messenger.app.data.model.Message> $completion);
    
    @retrofit2.http.DELETE(value = "messages/{id}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteMessage(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String messageId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion);
    
    @retrofit2.http.POST(value = "messages/{id}/read")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object markMessageAsRead(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String messageId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion);
    
    @retrofit2.http.POST(value = "messages/{id}/delivered")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object markMessageAsDelivered(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String messageId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion);
    
    @retrofit2.http.GET(value = "chats/{chatId}/messages/unread")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getUnreadMessages(@retrofit2.http.Path(value = "chatId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String chatId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.messenger.app.data.model.Message>> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}