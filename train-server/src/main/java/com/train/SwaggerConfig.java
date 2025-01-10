package com.train;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger配置
 *
 *
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi systemApi() {
        String[] paths = {"/**"};
        String[] packagedToMatch = {"com.train.system"};
        return GroupedOpenApi.builder()
                .group("1")
                .displayName("System API")
                .pathsToMatch(paths)
                .packagesToScan(packagedToMatch).build();
    }

    @Bean
    public GroupedOpenApi quartzApi() {
        String[] paths = {"/**"};
        String[] packagedToMatch = {"com.train.quartz"};
        return GroupedOpenApi.builder()
                .group("2")
                .displayName("Quartz API")
                .pathsToMatch(paths)
                .packagesToScan(packagedToMatch).build();
    }

    @Bean
    public GroupedOpenApi monitorApi() {
        String[] paths = {"/**"};
        String[] packagedToMatch = {"com.train.monitor"};
        return GroupedOpenApi.builder()
                .group("3")
                .displayName("Monitor API")
                .pathsToMatch(paths)
                .packagesToScan(packagedToMatch).build();
    }
    
    @Bean
    public GroupedOpenApi trainApi() {
        String[] paths = {"/**"};
        String[] packagedToMatch = {"com.train.train"};
        return GroupedOpenApi.builder()
                .group("4")
                .displayName("Train API")
                .pathsToMatch(paths)
                .packagesToScan(packagedToMatch).build();
    }
    @Bean
    public GroupedOpenApi studentApi() {
        String[] paths = {"/**"};
        String[] packagedToMatch = {"com.train.student"};
        return GroupedOpenApi.builder()
                .group("5")
                .displayName("Student API")
                .pathsToMatch(paths)
                .packagesToScan(packagedToMatch).build();
    }
    @Bean
    public GroupedOpenApi newApi() {
        String[] paths = {"/**"};
        String[] packagedToMatch = {"com.train.test"};
        return GroupedOpenApi.builder()
                .group("7")
                .displayName("Test API")
                .pathsToMatch(paths)
                .packagesToScan(packagedToMatch).build();
    }
    @Bean
    public GroupedOpenApi otherApi() {
        String[] paths = {"/**"};
        String[] packagedToMatch = {"com.train"};
        return GroupedOpenApi.builder()
                .group("99")
                .displayName("Other API")
                .pathsToMatch(paths)
                .packagesToExclude(
                        "com.train.system", "com.train.quartz", "com.train.monitor", "com.train.member", "com.train.iot", "com.train.train"
                )
                .packagesToScan(packagedToMatch)
                .build();
    }

    @Bean
    public OpenAPI openApi() {
        Contact contact = new Contact();
        contact.setName("jasonzhu");

        return new OpenAPI().info(new Info()
                .title("Train API")
                .description("培训机构管理系统")
                .contact(contact)
                .version("1.x")
        );
    }

}