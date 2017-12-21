package org.posmall.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Created by USER on 2017-12-21.
 */
@Configuration
@MapperScan(value = "org.posmall.mapper.webcache", sqlSessionFactoryRef = "webcacheSqlSessionFactory")
@EnableTransactionManagement(proxyTargetClass = true)
public class WebcacheDatabaseConfig {

    @Bean(name = "webcacheDataSource")
    @ConfigurationProperties(prefix = "datasource.webcache")
    public DataSource webcacheDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "webcacheSqlSessionFactory")
    public SqlSessionFactory webcacheSqlSessionFactory(@Qualifier("webcacheDataSource") DataSource webcacheDataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(webcacheDataSource);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:org/posmall/mapper/webcache/*.xml"));
        sqlSessionFactoryBean.getObject().getConfiguration().setJdbcTypeForNull(JdbcType.NULL);
        sqlSessionFactoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "webcacheSqlSessionTemplate")
    public SqlSessionTemplate webcacheSqlSessionTemplate(SqlSessionFactory webcacheSqlSessionFactory) throws Exception {

        return new SqlSessionTemplate(webcacheSqlSessionFactory);
    }

    @Bean(name = "webcacheTransactionManager")
    public PlatformTransactionManager webcacheTransactionManager(@Qualifier("webcacheDataSource") DataSource posmallDataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(posmallDataSource);
        transactionManager.setGlobalRollbackOnParticipationFailure(false);
        return transactionManager;
    }
}
