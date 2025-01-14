package com.example.e_commerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.e_commerce.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

    @Query("select a from Admin a where a.name = :username")
    Optional<Admin> findByName(String username);

    /****************************************************************************************************/
    @Query("select case when count(a) > 0 then true else false end from Admin a where a.id = :id")
    boolean exisexistsById(Integer id);
    /****************************************************************************************************/

}