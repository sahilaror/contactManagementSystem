package com.smartProject.allSpec.repositoryOrDao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartProject.allSpec.model.user;
@Repository
 public interface smartRepo extends JpaRepository<user,Integer>{
	@Query("select u from user u where u.email = :email")
public user getUserByUserName(@Param("email") String email);
}
