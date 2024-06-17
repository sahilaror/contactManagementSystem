package com.smartProject.allSpec.businessOrServiceLogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartProject.allSpec.model.user;
import com.smartProject.allSpec.repositoryOrDao.smartRepo;

@Service
public class smartService {

	@Autowired
	smartRepo repo;
	public void save(user user) {
		repo.save(user);
	}
	public void delete(Integer id) {
		repo.deleteById(id);
	}
}
