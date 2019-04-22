package com.hyk.code.modules.api.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.Serializable;

/**
 * @Auther: 霍中曦
 * @Date: 2018/11/26 09:53
 * @Description:
 */
@Configuration
@EnableWebMvc//springmvc必须加上
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport implements Serializable {

    @Bean
    public Docket customDocket() {
        //
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hyk.code.modules.api")) .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("老王", "", "842021834@qq.com");
        return new ApiInfo("油大大",//大标题 title
                "油大大接口文档",//小标题
                "0.0.1",//版本
                "http://localhost:8181/manager",//termsOfServiceUrl
                contact,//作者
                "",//链接显示文字
                ""//网站链接
        );
    }
}
