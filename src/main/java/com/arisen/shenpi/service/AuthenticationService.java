package com.arisen.shenpi.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.keycloak.representations.idm.UserRepresentation;

import com.arisen.shenpi.model.KeycloakUser;


public interface AuthenticationService {

	String addUser(KeycloakUser keycloakuser);
	public String addUserOne();
	public List<UserRepresentation> searchUser();
	public String updateUser(HttpServletRequest req, HttpServletResponse response);
}
