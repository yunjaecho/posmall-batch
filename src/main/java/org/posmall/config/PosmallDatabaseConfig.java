package org.posmall.config;

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
@EnableTransactionManagement(proxyTargetClass = true)
public class PosmallDatabaseConfig {
    @Bean(name = "posmallDataSource")
    @Primary
    @ConfigurationProperties(prefix = "datasource.posmall")
    public DataSource posmallDataSource() {
        return DataSourceBuilder.create().build();
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
    public PlatformTransactionManager posmallTransactionManager(@Qualifier("posmallDataSource") DataSource posmallDataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(posmallDataSource);
        transactionManager.setGlobalRollbackOnParticipationFailure(false);
        return transactionManager;
    }

}
