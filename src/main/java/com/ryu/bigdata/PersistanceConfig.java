package com.ryu.bigdata;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

@Configuration
@MapperScan(value="com.ryu.bigdata.mapper", sqlSessionFactoryRef="sqlSessionFactory")
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class PersistanceConfig {

	@Value("${mybatis.type-aliases-package}")
	private String typeAliasesPackage;
	
	@Value("${mybatis.mapper-locations}")
	private String mapperLocation;
	
	@Value("${mybatis.configuration.map-underscore-to-camel-case}")
	private String camelConfig;
	
	@Bean(name="dataSource")
	@Primary
	@ConfigurationProperties(prefix="spring.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name="sqlSessionFactory") 
	@Primary
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource, ApplicationContext context) throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(dataSource());
		factoryBean.setMapperLocations(context.getResources(mapperLocation));
		factoryBean.setTypeAliasesPackage(typeAliasesPackage);
		return factoryBean.getObject();
	}
	
	@Bean(name="sqlSessionTemplate")
	@Primary
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory factory) throws Exception {
		return new SqlSessionTemplate(factory);
	}
	
	@Bean
    public PlatformTransactionManager transactionManager() throws URISyntaxException, GeneralSecurityException, ParseException, IOException {
        return new DataSourceTransactionManager(dataSource());
    }
	
	
}
