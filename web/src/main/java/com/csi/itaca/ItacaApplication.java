package com.csi.itaca;

import com.csi.itaca.config.tools.Configurator;
import com.csi.itaca.config.tools.ConfiguratorImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ItacaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItacaApplication.class, args);
	}

	@Bean
	public Configurator configurator() {
		return new ConfiguratorImpl();
	}
}
