package com.ryu.bigdata;
import org.springframework.context.annotation.*;
import springfox.documentation.swagger2.annotations.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import springfox.documentation.spring.web.plugins.*;
import springfox.documentation.builders.*;
import springfox.documentation.spi.*;

@Configuration
@EnableSwagger2
public class SwaggerConfig {                                    
    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.basePackage("com.ryu.bigdata.controller"))      
          .paths(PathSelectors.any())                          
          .build();                                           
    }
}