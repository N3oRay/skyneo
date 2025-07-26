package com.sos.obs.decc.config;

import org.springframework.boot.SpringApplication;
import org.springframework.core.env.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class to load a Spring profile to be used as default
 * when there is no <code>spring.profiles.active</code> set in the environment or as command line argument.
 * If the value is not available in <code>application.yml</code> then <code>dev</code> profile will be used as default.
 */
public final class DefaultProfileUtil {

    private static final Logger log = LoggerFactory.getLogger(DefaultProfileUtil.class);
    private static final String SPRING_PROFILE_DEFAULT = "spring.profiles.default";

    private DefaultProfileUtil() {
    }

    /**
     * Set a default to use when no profile is configured.
     *
     * @param app the Spring application
     */
    public static void addDefaultProfile(SpringApplication app) {

        log.info("Demarrage - addDefaultProfile'");
        Map<String, Object> defProperties = new HashMap<>();
        /*
        * The default profile to use when no other profiles are defined
        * This cannot be set in the <code>application.yml</code> file.
        * See https://github.com/spring-projects/spring-boot/issues/1219
        */
        defProperties.put(SPRING_PROFILE_DEFAULT, Constants.SPRING_PROFILE_DEVELOPMENT);
        app.setDefaultProperties(defProperties);
    }

    /**
     * Get the profiles that are applied else get default profiles.
     *
     * @param env spring environment
     * @return profiles
     */
    public static String[] getActiveProfiles(Environment env) {
        log.info("Demarrage - getActiveProfiles'");
        String[] profiles = env.getActiveProfiles();
        if (profiles.length == 0) {
             log.info("-> getDefaultProfiles'");
            return env.getDefaultProfiles();
        }
        return profiles;
    }
}
