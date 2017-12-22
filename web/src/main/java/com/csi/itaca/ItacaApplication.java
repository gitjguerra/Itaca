package com.csi.itaca;

import com.csi.itaca.config.model.Configurator;
import com.csi.itaca.config.tools.ConfiguratorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@SpringBootApplication
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
