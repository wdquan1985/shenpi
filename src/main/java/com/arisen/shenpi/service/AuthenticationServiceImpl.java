package com.arisen.shenpi.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@Service("shenPiService")
@Component("authenticationService")
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService{
//	  @Value("${keycloak.realm}")
//	  private String realm;
//	  
//	  @Value("${keycloak.auth-server-url}")
//	  private String serverUrl;
//	  
//	  @Value("${keycloak.ssl-required}")
//	  private String sslRequired;
//	  
//	  @Value("${keycloak.resource}")
//	  private String resource;
//
//	  @Value("${keycloak.credentials.secret}")
//	  private String secret;

	@Override
	public String addUserOne() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateUser(HttpServletRequest req,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}
	  

}
