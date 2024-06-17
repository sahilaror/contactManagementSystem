package com.smartProject.allSpec.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.smartProject.allSpec.model.user;
import com.smartProject.allSpec.repositoryOrDao.smartRepo;

public class userDetailImpl implements UserDetailsService{
	@Autowired
	private smartRepo smartRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("username"+username);
		user user=smartRepo.getUserByUserName(username);
		if (user==null) {
			throw new UsernameNotFoundException("could not found user");
		}
		customUserDetails cud=new customUserDetails(user);
		return cud;
	}

}


