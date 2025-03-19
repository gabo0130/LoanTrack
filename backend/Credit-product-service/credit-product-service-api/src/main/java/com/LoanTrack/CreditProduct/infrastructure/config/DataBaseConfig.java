package com.LoanTrack.Backend.infrastructure.config;

import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySources({
	@PropertySource(value = "database.properties", ignoreResourceNotFound = true),
	@PropertySource(value = "file:${spring.config.location}/database.properties", ignoreResourceNotFound = true)
	
})
@EnableTransactionManagement
public class DataBaseConfig
{
    @Autowired
    private Environment environment;
    
    
    @Bean(destroyMethod = "", name = { "dataSource", "xmlQueryDatasource" })
    @Primary
    DataSource getDataSource() throws IllegalArgumentException {
        final HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(this.environment.getProperty("spring.datasource.url"));
        dataSource.setUsername(this.environment.getProperty("spring.datasource.username"));
        dataSource.setPassword(this.environment.getProperty("spring.datasource.password"));
        dataSource.setSchema(this.environment.getProperty("spring.datasource.default_schema"));
        setPoolParameters(dataSource);        
        return dataSource;
    }
    
    @Bean(name = { "sessionFactory", "sessionFactorySecurity", "entityManagerFactory" })
    @Primary
    LocalSessionFactoryBean sessionFactory(DataSource dataSource) throws IllegalArgumentException {
        final LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setPackagesToScan(new String[] { this.environment.getProperty("spring.jpa.packageScan") });
        final Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.dialect", this.environment.getProperty("spring.jpa.properties.hibernate.dialect"));
        hibernateProperties.put("hibernate.show_sql", this.environment.getProperty("spring.jpa.show-sql"));
        sessionFactoryBean.setHibernateProperties(hibernateProperties);
        return sessionFactoryBean;
    }
    
    
    @Bean(name = { "transactionManager" })
    @Primary
	HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		return new HibernateTransactionManager(sessionFactory);
	}
    
     
    private void setPoolParameters(HikariDataSource dataSource) {
    	dataSource.setMaximumPoolSize(Integer.parseInt(this.environment.getProperty("datasource.maxPoolSize")));
        dataSource.setIdleTimeout(Long.parseLong(this.environment.getProperty("datasource.idleTimeoutMs")));
        dataSource.setConnectionTimeout(Long.parseLong(this.environment.getProperty("datasource.connectionTimeoutMs")));
        dataSource.setMaxLifetime(Long.parseLong(this.environment.getProperty("datasource.maxLifetimeMs")));
        dataSource.setMinimumIdle(Integer.parseInt(this.environment.getProperty("datasource.minIdle")));
    }

}