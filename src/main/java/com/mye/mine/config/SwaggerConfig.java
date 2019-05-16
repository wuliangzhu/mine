package com.mye.mine.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig /*implements WebMvcConfigurer*/ {

    @Bean
    public Docket scratchApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("demoapi")
                .apiInfo(statApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.mye.mine.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo statApiInfo() {
        return new ApiInfoBuilder().title("demoserver")
                .description("demo description")
                .termsOfServiceUrl("no terms of service")
                .version("1.0")
                .build();
    }
}

