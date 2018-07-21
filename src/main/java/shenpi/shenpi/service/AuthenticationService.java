package shenpi.shenpi.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public interface AuthenticationService {

	public String addUserOne();
	public String updateUser(HttpServletRequest req, HttpServletResponse response);
}
