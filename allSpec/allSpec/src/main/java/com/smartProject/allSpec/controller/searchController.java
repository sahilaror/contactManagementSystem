package com.smartProject.allSpec.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.smartProject.allSpec.model.contact;
import com.smartProject.allSpec.model.user;
import com.smartProject.allSpec.repositoryOrDao.contactRepo;
import com.smartProject.allSpec.repositoryOrDao.smartRepo;

@RestController
public class searchController {

	@Autowired
	contactRepo contactRep;
	
	@Autowired
	smartRepo smartRep;
	
	@GetMapping("/search/{query}")
	public ResponseEntity<?> search(@PathVariable("query") String query,Principal principal)
	{
		System.out.println("sahillllllllllllllllllllllllllllll");
		user user=smartRep.getUserByUserName(principal.getName());
		List<contact> contact=contactRep.findByNameContainingAndUser(query,user);
		return ResponseEntity.ok(contact);
	}
}
