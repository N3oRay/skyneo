package com.sos.obs.decc;

import com.sos.obs.decc.config.ApplicationProperties;
import com.sos.obs.decc.config.Constants;
import com.sos.obs.decc.config.DefaultProfileUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

@SpringBootApplication
@EnableConfigurationProperties({ ApplicationProperties.class})
@EntityScan("com.sos.obs.decc")
public class DashboardAdminApp {

    private static final Logger log = LoggerFactory.getLogger(DashboardAdminApp.class);

    private final Environment env;

    public DashboardAdminApp(Environment env) {
        this.env = env;
    }

    /**
     * Initializes Animation Admin.
     * <p>
     * Spring profiles can be configured with a program argument --spring.profiles.active=your-active-profile
     * <p>
     * You can find more information on how profiles work with JHipster on <a href="https://www.jhipster.tech/profiles/">https://www.jhipster.tech/profiles/</a>.
     */
    @PostConstruct
    public void initApplication() {
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (activeProfiles.contains(Constants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(Constants.SPRING_PROFILE_PRODUCTION)) {
            log.error("You have misconfigured your application! It should not run " +
                "with both the 'dev' and 'prod' profiles at the same time.");
        }
        if (activeProfiles.contains(Constants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(Constants.SPRING_PROFILE_CLOUD)) {
            log.error("You have misconfigured your application! It should not " +
                "run with both the 'dev' and 'cloud' profiles at the same time.");
        }
    }

    /**
     * Main method, used to run the application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        log.info("1 - Demarrage - main.");
        SpringApplication app = new SpringApplication(DashboardAdminApp.class);
        log.info("2 - Demarrage - main. SpringApplication");
        DefaultProfileUtil.addDefaultProfile(app);
        log.info("3 - Demarrage - main. add Profile.");
        Environment env = app.run(args).getEnvironment();
        log.info("4 - Demarrage - main. logApplicationStartup.");
        //logger.info("Foo from System.getenv(): {}", System.getenv("bar"));
        logApplicationStartup(env);
        log.info("5 - Demarrage - main. fin.");
    }

    private static void logApplicationStartup(Environment env) {
        log.info("4 - 1 Demarrage - logApplicationStartup.'");
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            log.info("Demarrage de l'application en https : key-store valid.'");
            protocol = "https";
        }else{
            log.info("Demarrage de l'application en http uniquement: key-store invalid.'");
        }
        log.info("4 - 2 Demarrage - serverPort.'");
        String serverPort = "8080";
        if (env.getProperty("server.port") != null) {
            serverPort = env.getProperty("server.port");
        }
        log.info("4 - 3 Demarrage - serverPort.'");
        String contextPath = env.getProperty("server.servlet.context-path");
        if (StringUtils.isBlank(contextPath)) {
            contextPath = "/";
        }
        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }
        log.info("\n----------------------------------------------------------\n\t" +
                "Application '{}' is running! Access URLs:\n\t" +
                "Local: \t\t{}://localhost:{}{}\n\t" +
                "External: \t{}://{}:{}{}\n\t" +
                "Profile(s): \t{}\n----------------------------------------------------------",
            env.getProperty("spring.application.name"),
            protocol,
            serverPort,
            contextPath,
            protocol,
            hostAddress,
            serverPort,
            contextPath,
            env.getActiveProfiles());
    }
}
