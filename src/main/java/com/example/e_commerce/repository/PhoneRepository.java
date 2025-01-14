package com.example.e_commerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.e_commerce.model.Phone;

public interface PhoneRepository extends JpaRepository<Phone, Integer> {
    @Query("select p from Phone p where p.user.id = :userId")
    List<Phone> findAllByUserId(@Param("userId") Integer userId);

    /***************************************************************************************************** */
    @Query("select case when count(p) > 0 then true else false end from Phone p where p.id = :phoneId and p.user.id = :userId")
    boolean isUserIsOwner(Integer phoneId, Integer userId);

}
