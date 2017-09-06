package itmo.visits_control.configuration;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

@Configuration
@PropertySource("classpath:db/db.properties")
public class DBconfig {
	@Autowired
	private Environment env;

	/* FIREBIRD CONNECTION PROPERTIES */
	@Bean
	public HibernateTemplate FirebirdHibernateTemplate() {
		return new HibernateTemplate(firebirdSessionFactory());
	}
	@Bean("firebirdTX")
	public HibernateTransactionManager hibFirebirdTransMan() {

		return new HibernateTransactionManager(firebirdSessionFactory());
	}
	@Bean(name = "firebirdSessionFactory")
	public SessionFactory firebirdSessionFactory() {
		LocalSessionFactoryBean lsfb = new LocalSessionFactoryBean();
		lsfb.setDataSource(getFirebirdDataSource());
		lsfb.setPackagesToScan("itmo.visits_control.entities.firebird");
		lsfb.setHibernateProperties(hibernateFirebirdProperties());
		try {
			lsfb.afterPropertiesSet();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lsfb.getObject();
	}

	@Bean
	public DataSource getFirebirdDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(env.getProperty("jdbc.firebird.driver"));
		dataSource.setUrl(env.getProperty("jdbc.firebird.url"));
		dataSource.setUsername(env.getProperty("jdbc.firebird.username"));
		dataSource.setPassword(env.getProperty("jdbc.firebird.password"));
		return dataSource;
	}

	

	private Properties hibernateFirebirdProperties() {
		/*
		 * hibernate.show_sql=true
		 * hibernate.dialect=org.hibernate.dialect.MySQLDialect
		 * hibernate.charset=UTF-8 hibernate.charenc=UTF-8
		 * hibernate.useunicode=true
		 */
		Properties properties = new Properties();
		properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		properties.put("hibernate.dialect", env.getProperty("hibernate.firebird.dialect"));
		properties.put("hibernate.charset", env.getProperty("hibernate.charset"));
		properties.put("hibernate.useunicode", env.getProperty("hibernate.useunicode"));
		properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
		properties.put("hibernate.format_sql", true);
		properties.put("hibernate.generate_statistics", true);
		properties.put("hibernate.dbcp.initialSize",8);
		properties.put("hibernate.dbcp.maxActive",20);
		properties.put("hibernate.dbcp.maxIdle",20);
		properties.put("hibernate.dbcp.minIdle",0);
		properties.put("hibernate.dbcp.validationQuery", "SELECT 1");

		return properties;
	}

	/* SQLSERVER CONNECTION PROPERTIES */
	@Bean
	public HibernateTemplate sqlServerHibernateTemplate() {
		return new HibernateTemplate(sqlServerSessionFactory());
	}

	@Bean(name = "mssqlSessionFactory")
	public SessionFactory sqlServerSessionFactory() {
		LocalSessionFactoryBean lsfb = new LocalSessionFactoryBean();
		lsfb.setDataSource(getsqlServerDataSource());
		lsfb.setPackagesToScan("itmo.visits_control.entities.mssql");
		lsfb.setHibernateProperties(hibernatesqlServerProperties());
		try {
			lsfb.afterPropertiesSet();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lsfb.getObject();
	}
	@Bean("mssqlTX")
	public HibernateTransactionManager hibMSSQLTransMan() {

		return new HibernateTransactionManager(sqlServerSessionFactory());
	}
	@Bean
	public DataSource getsqlServerDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(env.getProperty("jdbc.sqlserver.driver"));
		dataSource.setUrl(env.getProperty("jdbc.sqlserver.url"));
		dataSource.setUsername(env.getProperty("jdbc.sqlserver.username"));
		dataSource.setPassword(env.getProperty("jdbc.sqlserver.password"));

		return dataSource;
	}

	

	private Properties hibernatesqlServerProperties() {
		/*
		 * hibernate.show_sql=true
		 * hibernate.dialect=org.hibernate.dialect.MySQLDialect
		 * hibernate.charset=UTF-8 hibernate.charenc=UTF-8
		 * hibernate.useunicode=true
		 */
		Properties properties = new Properties();
		properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		properties.put("hibernate.dialect", env.getProperty("hibernate.sqlserver.dialect"));
		properties.put("hibernate.charset", env.getProperty("hibernate.charset"));
		properties.put("hibernate.useunicode", env.getProperty("hibernate.useunicode"));
		properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
		properties.put("hibernate.format_sql", true);
		properties.put("hibernate.generate_statistics", true);
		properties.put("hibernate.dbcp.initialSize",8);
		properties.put("hibernate.dbcp.maxActive",20);
		properties.put("hibernate.dbcp.maxIdle",20);
		properties.put("hibernate.dbcp.minIdle",0);
		properties.put("hibernate.dbcp.validationQuery", "SELECT 1");
		return properties;
	}
}
