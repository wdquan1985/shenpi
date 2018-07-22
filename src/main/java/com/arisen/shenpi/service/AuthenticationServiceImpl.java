package com.arisen.shenpi.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.arisen.shenpi.model.KeycloakUser;
import com.arisen.shenpi.model.system.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

//@Service("shenPiService")
@Component("authenticationService")
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService{
	  @Value("${keycloak.realm}")
	  private String realm;
	  
	  @Value("${keycloak.auth-server-url}")
	  private String serverUrl;
	  
	  @Value("${keycloak.ssl-required}")
	  private String sslRequired;
	  
	  @Value("${keycloak.resource}")
	  private String resource;

	  @Value("${keycloak.credentials.secret}")
	  private String secret;

		@Override
		public String addUser(KeycloakUser keycloakuser) {
			// TODO Auto-generated method stub
			// User "idm-admin" needs at least "manage-users, view-clients, view-realm, view-users" roles for "realm-management"
//			Keycloak kc = Keycloak.getInstance(
//			  serverUrl,
//			  "master", // the realm to log in to
//			  "admin", "123456", // the user
//			  secret);
			Keycloak kc = KeycloakBuilder.builder()
				.serverUrl(serverUrl)
				.realm("master")
				.username("admin")
				.password("123456")
				.clientId("admin-cli")
				.resteasyClient(
					new ResteasyClientBuilder()
					.connectionPoolSize(10).build())
				.build();
			CredentialRepresentation credential = new CredentialRepresentation();
			credential.setType(CredentialRepresentation.PASSWORD);
			credential.setValue("123456");
			
			UserRepresentation user = new UserRepresentation();
			user.setUsername("testuser");
			user.setFirstName("yangming");
			user.setLastName("wang");
			user.setCredentials(Arrays.asList(credential));
			Response createUserResponse = kc.realm(realm).users().create(user);
	        createUserResponse.close();
	        
			if (createUserResponse.getStatus() != 201) {
				System.err.println("Couldn't create user.");
			}
			return null;
		}
		
		@Override
		public String addUserOne() {
			// TODO Auto-generated method stub
			// 重要：User "bwang018" needs at least "manage-users, view-clients, view-realm, view-users" roles for "realm-management"
			Keycloak kc = KeycloakBuilder.builder()
					.serverUrl(serverUrl)
					.realm("master")
					.username("admin")
					.password("123456")
					.clientId("admin-cli")
					.resteasyClient(
						new ResteasyClientBuilder()
						.connectionPoolSize(10).build())
					.build();
			
			// Define user
			UserRepresentation user = new UserRepresentation();
			user.setEnabled(true);
			user.setUsername("bruce2");
			user.setFirstName("bruce2First");
			user.setLastName("bruce2Last");
			user.setEmail("bruce2+tester1@tdlabs.local");
			user.setAttributes(Collections.singletonMap("origin", Arrays.asList("demo")));
//			user.setRealmRoles(realmRoles);
			
			// Get realm
			RealmResource realmResource = kc.realm(realm);
			UsersResource userRessource = realmResource.users();

			// Create user (requires manage-users role)
			Response response = userRessource.create(user);
			System.out.println("Repsonse: " + response.getStatusInfo());
			System.out.println(response.getLocation());
			return null;
		}
		
		@Override
		public List<UserRepresentation> searchUser() {
			// TODO Auto-generated method stub
			// 重要：User "bwang018" needs at least "manage-users, view-clients, view-realm, view-users" roles for "realm-management"
			Keycloak keycloak = KeycloakBuilder.builder() //
					.serverUrl(serverUrl) //
					.realm(realm) //
					.grantType(OAuth2Constants.PASSWORD) //
					.clientId(resource) //
					.clientSecret(secret) //
					.username("bwang018") //
					.password("1234567") //
					.build();
			
			// Get realm
			RealmResource realmResource = keycloak.realm(realm);
			UsersResource userRessource = realmResource.users();
			
			// Create user (requires manage-users role)
			List<UserRepresentation> users = userRessource.search(resource, 0, 10);
			users.forEach(user -> System.out.println(user.getUsername()));
			return users;
		}

		@Override
		public String updateUser(HttpServletRequest req,
				HttpServletResponse response) {
			// TODO Auto-generated method stub
			return null;
		}
		
//		@Override
//		public String updateUser(HttpServletRequest req, HttpServletResponse response) {
//			// TODO Auto-generated method stub
//			// 重要：User "bwang018" needs at least "manage-users, view-clients, view-realm, view-users" roles for "realm-management" 
//	        String userId = "e265429a-1b60-490d-bc0f-c45d6504b8f6";
//			KeycloakSecurityContext securityContext = (KeycloakSecurityContext) req.getSession().getAttribute(KeycloakSecurityContext.class.getName());
//			String token = securityContext.getTokenString();
//			//get token http://localhost:8086/auth/realms/shenpi/protocol/openid-connect/token
//			
//	    	List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
//	    	paramsList.add(new BasicNameValuePair("email", "bwang018@sina.com"));
//
//	    	String url = "http://localhost:8086/auth/admin/realms/shenpi/users/e265429a-1b60-490d-bc0f-c45d6504b8f6";
//
//	    	String[] jenkinsjobBuildInfo = new String[2];
//			try {
//				jenkinsjobBuildInfo = HttpClientTool.putRequest(url, "","",token, "","application/json","",paramsList);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			Map<String, Object> resultMap = new HashMap<String, Object>();
//			resultMap.put("token", securityContext.getTokenString());
//			return null;
//		}
	  

}
