package com.arisen.shenpi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.arisen.shenpi.service.AuthenticationService;


/**
 * Created by bruce on 18-07-12.
 */
@Api(value="/api/authentication", tags="认证授权模块")
@Controller
@RequestMapping(value = "/api/authentication")
public class AuthenticationController {
	@Autowired
	private  AuthenticationService authenticationService;
	
	@ApiOperation(value="在keycloak中添加用户", notes = "在keycloak中添加用户")
	@RequestMapping(value = "/adduser", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> addUser(HttpServletRequest req, HttpServletResponse response) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		authenticationService.addUserOne();
		
//		resultMap.put("user", accessToken.getPreferredUsername());
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
