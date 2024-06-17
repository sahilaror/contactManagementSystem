package com.smartProject.allSpec.repositoryOrDao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartProject.allSpec.model.contact;
import com.smartProject.allSpec.model.user;
@Repository
public interface contactRepo extends JpaRepository<contact, Integer>{
	@Query("from contact as c where c.user.id =:userId")
	public Page<contact> findContactsByUser(@Param("userId") int userId, Pageable pageable);
	
	public List<contact> findByNameContainingAndUser(String name, user user);
}





