package com.cognizant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.model.UserHospital;

@Repository
public interface UserRepository extends JpaRepository<UserHospital, String> {

}
