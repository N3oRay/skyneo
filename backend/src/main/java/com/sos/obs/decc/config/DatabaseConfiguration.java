package com.sos.obs.decc.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableJpaRepositories(basePackages ="com.sos.obs.decc.repository", entityManagerFactoryRef = "mainEntityManagerFactory",
        transactionManagerRef = "mainTransactionManager")
//@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
@EnableTransactionManagement
public class DatabaseConfiguration {

    private final Logger log = LoggerFactory.getLogger(DatabaseConfiguration.class);

    private static final String CLOUD_CONFIGURATION_MAIN_PREFIX = "spring.datasource";


    @Bean
    @Primary
    @ConfigurationProperties(CLOUD_CONFIGURATION_MAIN_PREFIX)
    public DataSourceProperties mainDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean("mainDataSource")
    @Primary
    public DataSource mainDataSource() {
       return mainDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Primary
    @Bean("mainEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean mainEntityManagerFactory(final EntityManagerFactoryBuilder builder,
                                                                           final @Qualifier("mainDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.sos.obs.decc.domain")
                .persistenceUnit("mainDbPersistence")
                .build();
    }

    @Primary
    @Bean("mainTransactionManager")
    public PlatformTransactionManager mainTransactionManager(@Qualifier("mainEntityManagerFactory")
                                                                      EntityManagerFactory firstEntityManagerFactory) {

        JpaTransactionManager transactionManager =new JpaTransactionManager(firstEntityManagerFactory);
        return transactionManager;
    }

}
