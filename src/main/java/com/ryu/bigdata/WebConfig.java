package com.ryu.bigdata;

import org.springframework.context.annotation.*;
import springfox.documentation.swagger2.annotations.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import springfox.documentation.spring.web.plugins.*;
import springfox.documentation.builders.*;
import springfox.documentation.spi.*;

import org.springframework.web.servlet.config.annotation.*;



@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy
public class WebConfig extends WebMvcConfigurerAdapter {
  //---중략---/
    
  //static 리소스 처리
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/WEB-INF/resources/");

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
       
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");

    }
}