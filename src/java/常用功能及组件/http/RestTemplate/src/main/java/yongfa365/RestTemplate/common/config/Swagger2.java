package yongfa365.RestTemplate.common.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Set;
import java.util.TreeSet;

@Configuration
@EnableSwagger2
public class Swagger2 {
    @Bean
    public Docket createRestApi() {

        Set<String> set = new TreeSet<>();
        return new Docket(DocumentationType.SWAGGER_2)
                //.host()//点击 try it out 请求接口时 主机和端口
                //.protocols(set)//点击 try it out 请求接口时 使用的协议
                .apiInfo(new ApiInfoBuilder()
                        .title("可选项 项目接口文档")
                        .description("可选项  项目的线下接口文档")
                        .version("1.0")
                        .build())
                .select()
                .apis(RequestHandlerSelectors.basePackage("yongfa365.RestTemplate")) //以扫描包的方式
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public UiConfiguration uiConfiguration() {
        return UiConfigurationBuilder.builder().defaultModelExpandDepth(100)
                .build();
    }
}


