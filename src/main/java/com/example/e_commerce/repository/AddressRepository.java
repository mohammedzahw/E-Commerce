package com.example.e_commerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.e_commerce.model.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {

    @Query("select a from Address a where a.user.id = :userId")
    List<Address> findAllByUserId(@Param("userId") Integer userId);

    @Query("select case when count(a) > 0 then true else false end from Address a where a.id = :addressId and a.user.id = :userId")
    boolean isUserIsOwner(Integer addressId, Integer userId);

}
