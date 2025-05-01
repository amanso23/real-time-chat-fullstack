package com.amanso.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SpringBootApplication
@EnableJpaAuditing
@SecurityScheme(name = "keycloak", type = SecuritySchemeType.OAUTH2, bearerFormat = "JWT", scheme = "bearer", in = SecuritySchemeIn.HEADER, flows = @OAuthFlows(password = @OAuthFlow(authorizationUrl = "http://localhost:8080/realms/master/protocol/openid-connect/auth", tokenUrl = "http://localhost:8080/realms/master/protocol/openid-connect/token")))
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}
