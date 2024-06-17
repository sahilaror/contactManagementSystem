package com.smartProject.allSpec.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import com.smartProject.allSpec.model.user;
import java.io.IOException;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
		@Autowired
		user user;
	
	 @Override
	    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
	        boolean isAdmin = authentication.getAuthorities().stream()
	                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("Role_ADMIN"));
	        if (isAdmin) {
	            setDefaultTargetUrl("/about");
	        } else {
	            setDefaultTargetUrl("/user/index");
	        }
	        super.onAuthenticationSuccess(request, response, authentication);
	    }
}
