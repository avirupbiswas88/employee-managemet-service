package com.employee.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI employeeApi() {

        return new OpenAPI()
                .info(new Info()
                        .title("Employee Salary Management API")
                        .description("REST APIs for Employee CRUD operations, salary calculations and salary analytics")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Employee API Team")
                                .email("support@test.com")));
    }
}
