package ru.zotov.carracing.store.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Created by ZotovES on 21.01.2022
 * Конфигурация Swagger
 */
@Configuration
@EnableSwagger2
public class SpringfoxConfig {
    public static final String STORE = "Store";

    /**
     * RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class) указывает на то что документация
     * будет рендерится только для методов помеченных аннотацией ApiOperation.class
     * Для автоматического рендеринга всех методов использовать RequestHandlerSelectors.any()
     */
    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .tags(new Tag(STORE, "Магазин"));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("CarRacing")
                .description("Микросервис магазина игры ")
                .version("1.0")
                .build();
    }
}
