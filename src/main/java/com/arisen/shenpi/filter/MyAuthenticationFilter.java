package com.arisen.shenpi.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.IDToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class MyAuthenticationFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
//        if (null  != SecurityContextHolder.getContext().getAuthentication()) {
//        	Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
//        	if (!authorities.isEmpty()) {
//                filterChain.doFilter(request, response);
//                return;
//        	}
//		}
        
//        if (request != null) {
//            String authHeader = ((HttpServletRequest) request).getHeader("Authorization");
//            if (authHeader == null || authHeader.startsWith("apiToken ")) {
//                filterChain.doFilter(request, response);
//                return;
//            }
//        }
        
        Authentication authentication = getAuthentication((HttpServletRequest)request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request,response);
//      SecurityContextHolder.getContext().setAuthentication(null);
    }
    
	public Authentication getAuthentication(HttpServletRequest request) {
		KeycloakSecurityContext securityContext = (KeycloakSecurityContext) request.getSession().getAttribute(KeycloakSecurityContext.class.getName());
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("token", securityContext.getTokenString());
		
		IDToken idToken = securityContext.getIdToken();
		
		resultMap.put("email", idToken.getEmail());
		resultMap.put("name", idToken.getName());
		resultMap.put("acr", idToken.getAcr());
		resultMap.put("audience", idToken.getAudience());
		resultMap.put("id", idToken.getId());
		resultMap.put("username", idToken.getPreferredUsername());
		resultMap.put("otherClaims", idToken.getOtherClaims());

		AccessToken accessToken = securityContext.getToken();
		resultMap.put("authorization", accessToken.getRealmAccess().getRoles().toString());

		String username = idToken.getPreferredUsername();
		
		Collection<? extends GrantedAuthority> authorities = getGrantedAuthorities(accessToken.getRealmAccess().getRoles());
		PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(username, null, authorities);
		authentication.setDetails(idToken.getOtherClaims());
		
		return authentication;
	}
	
	private List<GrantedAuthority> getGrantedAuthorities(Collection<String> permissions) {
	    List<GrantedAuthority> authorities = new ArrayList<>();
	    for (String permission : permissions) {
	        authorities.add(new SimpleGrantedAuthority(permission));
	    }
	    return authorities;
	}
}