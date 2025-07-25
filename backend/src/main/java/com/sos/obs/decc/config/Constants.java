package com.sos.obs.decc.config;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_.@A-Za-z0-9-]*$";

    public static final String SYSTEM_ACCOUNT = "system";
    //public static final String ANONYMOUS_USER = "anonymoususer";
    public static final String DEFAULT_LANGUAGE = "fr";
    
    public static final String DEFAULT_MAIL = "serge.lopes.ex@sos.com"; // mail par défault d'activation de compte

    public static final String SPRING_PROFILE_DEVELOPMENT = "dev";
    public static final String DEFAULT_PROFILE_TEST = "test";
    public static final String SPRING_PROFILE_PRODUCTION = "prod";
    public static final String SPRING_PROFILE_CLOUD = "cloud";

    private Constants() {
    }
}
