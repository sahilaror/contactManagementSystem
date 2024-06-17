package com.smartProject.allSpec.config;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.smartProject.allSpec.businessOrServiceLogic.smartService;
import com.smartProject.allSpec.model.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationSucess implements AuthenticationSuccessHandler{
	
	@Autowired
	smartService smartService;
	
	user user1=new user();
	
	

	Logger logger=LoggerFactory.getLogger(AuthenticationSucess.class);
	
   @Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication ) throws IOException, ServletException {
		// TODO Auto-generated method stub
	   
	 
		DefaultOAuth2User user=(DefaultOAuth2User)authentication.getPrincipal();
		logger.info("AuthenticationSucess");
//		logger.info(user.getName());
//		user.getAttributes().forEach((key,value) ->{
//			logger.info("{} => {}",key,value);
//		});
//		logger.info(user.getAttributes().toString());
		
		String email=user.getAttribute("email").toString();
		String name=user.getAttribute("name").toString();
		String picture=user.getAttribute("picture").toString();
		System.out.println(email+name+picture);
		
		
		user1.setName(name);
		user1.setEmail(email);
		user1.setRole("Role_USER");
		user1.setEnabled(true);
		user1.setImageUrl(picture);
		smartService.save(user1);
		System.out.println("hcbwbjwwwwwwwwwwww_______________"+name);
		new DefaultRedirectStrategy().sendRedirect(request, response,"/user/index");
   }
   
  
}
