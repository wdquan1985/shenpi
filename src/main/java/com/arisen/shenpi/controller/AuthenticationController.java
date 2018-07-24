package com.arisen.shenpi.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.keycloak.AuthorizationContext;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.IDToken;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.arisen.shenpi.service.AuthenticationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


/**
 * Created by bruce on 18-07-12.
 */
@Api(value="/api/authentication", tags="认证授权模块")
@Controller
@RequestMapping(value = "/api/authentication")
public class AuthenticationController {
	@Autowired
	private  AuthenticationService authenticationService;
	
	@ApiOperation(value="从keycloak得到登陆用户信息", notes = "从keycloak得到登陆用户信息")
	@RequestMapping(value = "/userinfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> userinfo(HttpServletRequest req, HttpServletResponse response) {

		KeycloakSecurityContext securityContext = (KeycloakSecurityContext) req.getSession().getAttribute(KeycloakSecurityContext.class.getName());
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("token", securityContext.getTokenString());
		IDToken idToken = securityContext.getIdToken();
		
		resultMap.put("email", idToken.getEmail());
		resultMap.put("name", idToken.getName());
		resultMap.put("acr", idToken.getAcr());
		resultMap.put("audience", idToken.getAudience());
		resultMap.put("id", idToken.getId());
		resultMap.put("audience", idToken.getAudience());
		resultMap.put("username", idToken.getPreferredUsername());
		resultMap.put("otherClaims", idToken.getOtherClaims());

		AccessToken accessToken = securityContext.getToken();
		resultMap.put("authorization", accessToken.getRealmAccess().getRoles().toString());
		
		AuthorizationContext authorizationContext = securityContext.getAuthorizationContext();
		
//		resultMap.put("user", accessToken.getPreferredUsername());
//		resultMap.put("userName", accessToken.getName());
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
	
	@ApiOperation(value="从spring security session中得到登陆用户信息", notes = "从keycloakspring security session中得到登陆用户信息")
	@RequestMapping(value = "/userinfosession", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> userInfoSession(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();		
//		HttpSession session = 
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority>  authorities = authentication.getAuthorities();
		Object object = authentication.getPrincipal();
		System.out.println(authentication.getPrincipal());
				
//		resultMap.put("user", accessToken.getPreferredUsername());
//		resultMap.put("userName", accessToken.getName());
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
	
	@ApiOperation(value="在keycloak中添加用户", notes = "在keycloak中添加用户")
	@RequestMapping(value = "/adduser", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> addUser(HttpServletRequest req, HttpServletResponse response) {

		KeycloakSecurityContext securityContext = (KeycloakSecurityContext) req.getSession().getAttribute(KeycloakSecurityContext.class.getName());
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("token", securityContext.getTokenString());
		authenticationService.addUserOne();
		
//		resultMap.put("user", accessToken.getPreferredUsername());
//		resultMap.put("userName", accessToken.getName());
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
	
	@ApiOperation(value="根据分页信息获取用户列表", notes = "根据分页信息获取用户列表")
	@RequestMapping(value = "/searchUserByPage", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> searchUserByPage(HttpServletRequest req, HttpServletResponse response) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<UserRepresentation> userlist = authenticationService.searchUser();
		
		resultMap.put("userlist", userlist);
//		resultMap.put("userName", accessToken.getName());
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
	
	@ApiOperation(value="更新用户信息", notes = "更新用户信息")
	@RequestMapping(value = "/updateUser", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> updateUser(HttpServletRequest req, HttpServletResponse response) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		authenticationService.updateUser(req, response);
		
//		resultMap.put("userlist", userlist);
//		resultMap.put("userName", accessToken.getName());
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
}
