package com.messenger.app.data.remote.config;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0011\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0018\u001a\u00020\u00062\u0006\u0010\u0019\u001a\u00020\u0006J\u0006\u0010\u001a\u001a\u00020\u0006J\u0006\u0010\u001b\u001a\u00020\u0006R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u000bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2 = {"Lcom/messenger/app/data/remote/config/DatabaseConfig;", "", "()V", "ALLOW_PUBLIC_KEY_RETRIEVAL", "", "API_BASE_URL", "", "API_TIMEOUT", "", "CACHE_EXPIRY", "CACHE_SIZE", "", "CONNECTION_TIMEOUT", "MAX_POOL_SIZE", "MIN_POOL_SIZE", "MYSQL_DATABASE", "MYSQL_HOST", "MYSQL_PASSWORD", "MYSQL_PORT", "MYSQL_ROOT_PASSWORD", "MYSQL_ROOT_USERNAME", "MYSQL_USERNAME", "SOCKET_TIMEOUT", "USE_SSL", "getApiEndpoint", "endpoint", "getApiUrl", "getConnectionString", "app_debug"})
public final class DatabaseConfig {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String MYSQL_HOST = "localhost";
    public static final int MYSQL_PORT = 3306;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String MYSQL_DATABASE = "messenger_db";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String MYSQL_USERNAME = "messenger_user";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String MYSQL_PASSWORD = "messenger_password123";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String MYSQL_ROOT_USERNAME = "root";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String MYSQL_ROOT_PASSWORD = "Frost113ru";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String API_BASE_URL = "http://localhost:8080/api/";
    public static final long API_TIMEOUT = 30L;
    public static final long CONNECTION_TIMEOUT = 10000L;
    public static final long SOCKET_TIMEOUT = 30000L;
    public static final int MAX_POOL_SIZE = 10;
    public static final int MIN_POOL_SIZE = 2;
    public static final int CACHE_SIZE = 100;
    public static final long CACHE_EXPIRY = 300000L;
    public static final boolean USE_SSL = false;
    public static final boolean ALLOW_PUBLIC_KEY_RETRIEVAL = true;
    @org.jetbrains.annotations.NotNull()
    public static final com.messenger.app.data.remote.config.DatabaseConfig INSTANCE = null;
    
    private DatabaseConfig() {
        super();
    }
    
    /**
     * Получить строку подключения к MySQL
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getConnectionString() {
        return null;
    }
    
    /**
     * Получить URL для API
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getApiUrl() {
        return null;
    }
    
    /**
     * Получить полный URL для конкретного эндпоинта
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getApiEndpoint(@org.jetbrains.annotations.NotNull()
    java.lang.String endpoint) {
        return null;
    }
}