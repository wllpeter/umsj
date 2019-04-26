package com.tuniu.bi.umsj.base.config;

import com.google.common.base.Predicate;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangwei21
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Value("${swagger.enable}")
    private boolean enable;

    @Bean
    public Docket api() {
        // header中添加token
        ParameterBuilder token = new ParameterBuilder();
        List<Parameter> params = new ArrayList<Parameter>();
        token.name("token").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        params.add(token.build());

        // 自定义， 只有增加了ApiOperation.class的才能展示
        Predicate<RequestHandler> predicate = new Predicate<RequestHandler>() {
            @Override
            public boolean apply(RequestHandler input) {
                if (input.isAnnotatedWith(ApiOperation.class)) {
                    return true;
                }
                return false;
            }
        };
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(enable)
                .select()
                .apis(predicate)
                .paths(PathSelectors.any())
                .build().globalOperationParameters(params);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Umsj接口文档")
                .description("swagger接入教程")
                .version("1.0")
                .build();
    }
}
