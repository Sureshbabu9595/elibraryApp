package com.library.elibrary.config;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class BaseJDBCConfiguratioon {

	public BaseJDBCConfiguratioon() {
		super();
	}

	@Bean
	@ConfigurationProperties("spring.datasource")
	public DataSource getDataSource() {
		@SuppressWarnings("rawtypes")
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create(); 
	    dataSourceBuilder.username("SA"); 
	    dataSourceBuilder.password(""); 
	        return dataSourceBuilder.build();
	}

	@Profile("testWithH2DB")
	@Autowired
	@Bean
	public NamedParameterJdbcTemplate getNamedParameterJDBCTemplate(DataSource dataSource) {
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	return namedParameterJdbcTemplate;	
	}

}