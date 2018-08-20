package com.cmcc.hy.learnDemo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Project:  learnDemo
 * File Created by James on 2018/2/13
 *
 * Copyright 2018 CMCC Corporation Limited.
 * All rights reserved.
 */
@Configuration
@EnableSwagger2
public class Swagger2 {

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.cmcc.hy.learnDemo"))
            .paths(PathSelectors.any())
            .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
            .title("onemall管理平台接口V1.0.0")
            .description("接口问题反馈：祝军/18867103801")
            .termsOfServiceUrl("www.zhujun.me")
            .contact("")
            .version("1.0.0")
            .build();
    }
}
