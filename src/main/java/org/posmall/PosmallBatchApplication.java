package org.posmall;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableScheduling
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class PosmallBatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(PosmallBatchApplication.class, args);
	}

    @Override
    public void run(String... strings) throws Exception {

    }
}
