package me.victorcruz.ninjaserver.constants;

public class SecurityConstants {
    public static final String SECRET = "OMINzNaTDft5mS06xwRVTk2y6GzxAloA"; // Use ENV in PROD
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String LOGIN_URL = "/api/v1/login";
}
