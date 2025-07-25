package com.sos.obs.decc.config;

// desactivation connection bdd evita : CiscoIndicatorsRepository


/*
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;


@Configuration
@EnableJpaRepositories(basePackages = "com.sos.obs.decc.evita.repository", entityManagerFactoryRef = "evitaEntityManagerFactory",
        transactionManagerRef = "mainTransactionManager")
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")

*/
public class EvitaDatabaseConfiguration {
	/*
    private final Logger log = LoggerFactory.getLogger(EvitaDatabaseConfiguration.class);

    private static final String CLOUD_CONFIGURATION_MAIN_PREFIX = "evita.datasource";


    @Bean
    @ConfigurationProperties(CLOUD_CONFIGURATION_MAIN_PREFIX)
    public DataSourceProperties evitaDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean("evitaDataSource")
	public DataSource evitaDataSource() {
        return evitaDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean(name="evitaEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean evitaEntityManagerFactory(final EntityManagerFactoryBuilder builder,
                                                                            final @Qualifier("evitaDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.sos.obs.decc.evita.domain")
                .persistenceUnit("evitaDbPersistence")
                .build();
    }

    @Bean("evitaTransactionManager")
    public PlatformTransactionManager evitaTransactionManager(@Qualifier("evitaEntityManagerFactory")
                                                                     EntityManagerFactory firstEntityManagerFactory) {

        JpaTransactionManager transactionManager =new JpaTransactionManager(firstEntityManagerFactory);
        return transactionManager;
    }*/

}
