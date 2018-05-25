package com.aaturenko.pethotel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
//                .globalOperationParameters(
//                        Collections.singletonList(new ParameterBuilder()
//                                .name("user")
//                                .description("Access token.")
//                                .modelRef(new ModelRef("string"))
//                                .parameterType("header")
//                                .required(true)
//                                .build()
//                        )
//                )
                .apiInfo(metadata())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo metadata() {
        return new ApiInfoBuilder().title("Pets Hotel API")
                .description("Pets Hotel API reference for developers")
                .build();
    }
}
