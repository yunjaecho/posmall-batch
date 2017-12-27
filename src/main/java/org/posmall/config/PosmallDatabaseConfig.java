package org.posmall.config;

import net.sf.log4jdbc.Log4jdbcProxyDataSource;
import net.sf.log4jdbc.SpyLogDelegator;
import net.sf.log4jdbc.tools.Log4JdbcCustomFormatter;
import net.sf.log4jdbc.tools.LoggingType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Created by USER on 2017-12-21.
 */
@Configuration
@MapperScan(value = "org.posmall.mapper.posmall", sqlSessionFactoryRef = "posmallSqlSessionFactory")
public class PosmallDatabaseConfig {
    @Bean(name = "posmallDataSourceSpied")
    @ConfigurationProperties(prefix = "datasource.posmall")
    public DataSource posmallDataSourceSpied() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "posmallDataSource")
    @Primary
    public DataSource posmallDataSource(@Qualifier("posmallDataSourceSpied") DataSource posmallDataSourceSpied) {
        Log4jdbcProxyDataSource dataSource = new Log4jdbcProxyDataSource(posmallDataSourceSpied);
        Log4JdbcCustomFormatter formatter= new Log4JdbcCustomFormatter();
        formatter.setLoggingType(LoggingType.MULTI_LINE);
        formatter.setSqlPrefix("SQL       \n");
        dataSource.setLogFormatter(formatter);
        return dataSource;
    }


    @Bean(name = "posmallSqlSessionFactory")
    @Primary
    public SqlSessionFactory posmallSqlSessionFactory(@Qualifier("posmallDataSource") DataSource posmallDataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(posmallDataSource);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:org/posmall/mapper/posmall/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "posmallSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate posmallSqlSessionTemplate(SqlSessionFactory posmallSqlSessionFactory) throws Exception {

        return new SqlSessionTemplate(posmallSqlSessionFactory);
    }

    @Bean(name = "posmallTransactionManager")
    @Primary
    public PlatformTransactionManager  posmallTransactionManager(@Qualifier("posmallDataSource") DataSource posmallDataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(posmallDataSource);
        transactionManager.setGlobalRollbackOnParticipationFailure(false);
        return transactionManager;
    }

}
