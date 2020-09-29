package com.example.simplecrudjdbc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import oracle.jdbc.driver.OracleDriver;

@Configuration
public class DatabaseConfiguration {

	@Value("${db.datasource.url}")
	private String databaseUrl;
	
	@Value("${db.datasource.username}")
	private String databaseUser;
	
	@Value("${db.datasource.password}")
	private String databasePassword;
	
	@Bean(name = "jdbcTemplate")
	@Lazy
	public JdbcTemplate getJdbcTemplate() {
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		dataSource.setDriver(new OracleDriver());
		dataSource.setUrl(databaseUrl);
		dataSource.setUsername(databaseUser);
		dataSource.setPassword(databasePassword);
		
		return new JdbcTemplate(dataSource);
	}	
	
}
