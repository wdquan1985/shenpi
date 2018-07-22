package com.arisen.shenpi;

import java.util.List;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(exclude=org.activiti.spring.boot.SecurityAutoConfiguration.class)
@EnableAspectJAutoProxy //Enable Aop
public class ShenpiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShenpiApplication.class, args);
		//testKeyCloak();
	}
	
	private static void testKeyCloak() {
		Keycloak kc = KeycloakBuilder.builder()
	            .serverUrl("http://localhost:8180/auth")
	            .realm("master")
	            .username("admin")
	            .password("newsys")
	            .clientId("admin-cli")
	            .resteasyClient(
	                new ResteasyClientBuilder()
	                    .connectionPoolSize(10).build()
	            ).build(); 
		UsersResource userResource = kc.realm("shenpi").users();
		UserRepresentation user = new UserRepresentation();
		user.setUsername("bruce");
		userResource.create(user);
		List<UserRepresentation> users = userResource.list();
		System.out.println(users);
	}

}
