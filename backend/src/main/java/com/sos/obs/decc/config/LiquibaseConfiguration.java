/*
package com.sos.obs.decc.config;

import liquibase.integration.spring.SpringLiquibase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.task.TaskExecutor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.sql.DataSource;

//AROBASE Configuration
//AROBASE EnableAutoConfiguration
public class LiquibaseConfiguration {

    private final Logger log = LoggerFactory.getLogger(LiquibaseConfiguration.class);

    private final Environment env;

    public static final String SPRING_PROFILE_NO_LIQUIBASE = "no-liquibase";


    public LiquibaseConfiguration(Environment env) {
        this.env = env;
    }


    @Bean
    public SpringLiquibase liquibase(DataSource dataSource, LiquibaseProperties liquibaseProperties) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:config/liquibase/master.xml");
        liquibase.setDataSource(dataSource);

        //liquibase.setContexts(liquibaseProperties.getContexts());
        //liquibase.setDefaultSchema(liquibaseProperties.getDefaultSchema());
        //liquibase.setDropFirst(liquibaseProperties.isDropFirst());
        //liquibase.setChangeLogParameters(liquibaseProperties.getParameters());

        if (env.acceptsProfiles(SPRING_PROFILE_NO_LIQUIBASE)) {
            liquibase.setShouldRun(false);
        } else {
            liquibase.setShouldRun(liquibaseProperties.isEnabled());
            log.debug("Configuring Liquibase");
        }
        return liquibase;
    }

}
*/
