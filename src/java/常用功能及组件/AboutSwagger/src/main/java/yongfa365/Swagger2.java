package yongfa365;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
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

        var apiInfo = new ApiInfoBuilder()
                .title(" 项目接口文档")
                .description("  项目的线下接口文档")
                .version("1.0")
                .build();

        return new Docket(DocumentationType.SWAGGER_2)
                .host("localhost:12345") //点击 try it out 请求接口时 主机和端口
                .apiInfo(apiInfo)
                .select()
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public UiConfiguration uiConfiguration() {
        return UiConfigurationBuilder.builder()
                .defaultModelExpandDepth(100)
                .build();
    }
}

