package com.sos.obs.decc.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.boot.jdbc.DataSourceBuilder;

import com.zaxxer.hikari.HikariDataSource;



/*
@ComponentScan is used for scanning all your components those are marked as @Controller, @Service, @Repository, @Component etc…
*/

@Configuration
@EnableJpaRepositories(basePackages ="com.sos.obs.decc.repository", entityManagerFactoryRef = "mainEntityManagerFactory",
        transactionManagerRef = "mainTransactionManager")
//@ComponentScan(basePackages = { "com.sos.obs.decc.domain" })
//@EntityScan("com.sos.obs.decc.domain")
@EnableTransactionManagement
//EnableAutoConfiguration
public class DatabaseConfiguration {

    private final Logger log = LoggerFactory.getLogger(DatabaseConfiguration.class);

    private static final String CLOUD_CONFIGURATION_MAIN_PREFIX = "spring.datasource";

    //Hikari pool
    @Bean(name = "customDataSource")
    @ConfigurationProperties("spring.datasource")
    public DataSource customDataSource() {
       return DataSourceBuilder.create().build();
     }

    @Bean(name = "liquibase")
    @ConfigurationProperties("spring.datasource")
    public DataSource liquibaseDataSource() {
       return DataSourceBuilder.create().build();
     }



    @Bean
    @Primary
    @ConfigurationProperties(CLOUD_CONFIGURATION_MAIN_PREFIX)
    public DataSourceProperties mainDataSourceProperties() {
        return new DataSourceProperties();
    }


    @Bean
    @ConfigurationProperties("app.datasource")
    public HikariDataSource dataSource(DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().type(HikariDataSource.class)
                .build();
    }

    @Bean("mainDataSource")
    @Primary
    @ConfigurationProperties(CLOUD_CONFIGURATION_MAIN_PREFIX)
    public DataSource mainDataSource() {
      // return mainDataSourceProperties().initializeDataSourceBuilder().driverClassName("com.mysql.cj.jdbc.Driver").build();
		return DataSourceBuilder.create().build();
	}


    @Bean(name = "entityManagerFactory")
    public LocalSessionFactoryBean sessionFactory()
    {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setHibernateProperties(hibernateMYSQLProperties());
        return sessionFactory;
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


   Properties hibernatePostgreSQLProperties() {
      return new Properties() {
         /**
         *
         */
        private static final long serialVersionUID = 1L;

        {
            setProperty("hibernate.hbm2ddl.auto", "create");
            setProperty("hibernate.show_sql", "true");
            setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
         }
      };
   }

   Properties hibernateMYSQLProperties() {
      return new Properties() {
         /**
         *
         */
        private static final long serialVersionUID = 1L;

        {
            setProperty("hibernate.hbm2ddl.auto", "create");
            setProperty("hibernate.show_sql", "true");
            setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
         }
      };
   }



}
