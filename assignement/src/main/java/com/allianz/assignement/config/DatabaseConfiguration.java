package com.allianz.assignement.config;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory", basePackages = { "com.allianz.assignement.repo" })
@EnableTransactionManagement
public class DatabaseConfiguration {
	Logger log = LoggerFactory.getLogger(DatabaseConfiguration.class);
	private final Environment env;

	public DatabaseConfiguration(Environment env) {
		this.env = env;
	}

	@Bean(name = "dataSource")
	public DataSource dataSource() {
		log.info("Creating Datasource Start");

		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl(env.getProperty("spring.datasource.url"));
		dataSource.setUsername(env.getProperty("spring.datasource.username"));
		dataSource.setPassword(env.getProperty("spring.datasource.password"));
		log.info("Creating Datasource Finished");

		return dataSource;
	}

	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("dataSource") DataSource dataSource) {
		log.info("LocalContainerEntityManagerFactoryBean Created");
		return builder.dataSource(dataSource).packages("com.allianz.assignement.entity").persistenceUnit("entity").build();
	}

	@Primary
	@Bean(name = "transactionManager")
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		log.info("PlatformTransactionManager transactionManager");
		return new JpaTransactionManager(entityManagerFactory);
	}
}
