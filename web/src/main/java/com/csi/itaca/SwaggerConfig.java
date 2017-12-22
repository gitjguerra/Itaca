package com.csi.itaca;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

import static springfox.documentation.builders.PathSelectors.any;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("com.csi.itaca"))
                    .paths(any())
                    .build()
                    .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Itaca® Core RESTful Services",
                "Itaca® RESTful layer enables access to CSI's core insurance technology solutions.",
                getClass().getPackage().getImplementationVersion(),
                "© 2018 CSI Group - Make your business evolve.",
                new Contact("CSI Group", "http://www.csi-ti.com", "sistemas@csi-ti.com"),
                null,
                null,
                Collections.EMPTY_LIST);
    }
}
