package com.smartProject.allSpec.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class myConfig  {
	
	@Autowired
	AuthenticationSucess handler;
	
	@Bean
	public UserDetailsService getUserDetailsService() {
		return new userDetailImpl();	
	}
	@Bean
	public BCryptPasswordEncoder getBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	public DaoAuthenticationProvider getAuthenticationProvider() {
		DaoAuthenticationProvider dao=new DaoAuthenticationProvider();
		dao.setUserDetailsService(this.getUserDetailsService());
		dao.setPasswordEncoder(getBCryptPasswordEncoder());
		return dao;
	}
	 @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		 
		 http.authorizeHttpRequests(authorized ->{
			authorized.requestMatchers("/css/**","/js/**","/image/**","/home","/do_register","/signup","/signin","/forgot","/changed-pass","/send-otp","/new-pass").permitAll();
			authorized.requestMatchers("/admin").hasRole("Role_ADMIN");
			authorized.anyRequest().authenticated();
		 });
		 http.formLogin(form->
		 form.loginPage("/signin")
		 .loginProcessingUrl("/dologin")
		 .successHandler(new AuthenticationSuccessHandler())
				 );
		 
		 // oauth2 login with Customizer and loginpage
		 
		 //http.oauth2Login(Customizer.withDefaults());
		 http.oauth2Login(oauth -> {
			 oauth.loginPage("/signin")
			 .successHandler(handler);
		 });
		 
		 
		 
		http.csrf(c -> c.disable());
		 return http.build();
		 
	//      return http.csrf(c -> c.disable()).
	   //   authorizeHttpRequests(request -> request
//	    		  .requestMatchers("/user/**").hasRole("Role_USER")
//	    		.anyRequest().permitAll())
//	      .formLogin(f -> f.loginPage("/signin")
//	    		  .loginProcessingUrl("/dologin")
//	    		  .defaultSuccessUrl("/user/index")
//	    		  )
//	      .build();
	      
	      
	    } 
}

