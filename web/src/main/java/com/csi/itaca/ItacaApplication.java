package com.csi.itaca;

import com.csi.itaca.config.model.Configurator;
import com.csi.itaca.config.tools.ConfiguratorImpl;
import config.modules.CORSFilter;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@ServletComponentScan
@EnableBatchProcessing
public class ItacaApplication {

    @Autowired
    private Configurator configurator;

    public static void main(String[] args) {
		SpringApplication.run(ItacaApplication.class, args);
	}

	@Bean
	public Configurator configurator() {
		return new ConfiguratorImpl();
	}

    @PostConstruct
    private void registerModules() {
        configurator.registerModule("config.modules");
    }

}
