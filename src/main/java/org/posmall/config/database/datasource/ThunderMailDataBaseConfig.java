package org.posmall.config.database.datasource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Created by USER on 2018-02-06.
 */
@Configuration
public class ThunderMailDataBaseConfig {
    @Bean(name = "thunderMailDataSource")
    @ConfigurationProperties(prefix = "datasource.thunderMail")
    public DataSource thunderMailDataSource() {
        return DataSourceBuilder.create().build();
    }
}
