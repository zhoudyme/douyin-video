package me.zhoudongyu;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Steve
 * @date 2019/04/10
 */

@Configuration
@EnableSwagger2
public class Swagger2 {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("me.zhoudongyu.controller"))
                .paths(PathSelectors.any()).build();
    }

    /**
     * 构建api文档信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("使用swagger2构建短视频后端api接口文档")
                .contact(new Contact("zhoudongyu", "https://github.com/SteveZhou8", ""))
                .description("欢迎访问短视频接口文档")
                .version("1.0").build();
    }
}
