package com.example.e_commerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.e_commerce.model.Order;
import com.example.e_commerce.model.User;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("select o.user from Order o where o.id = :id")
    Optional<User> findOrderUser(@Param("id") Integer id);

    /***************************************************************************************************** */
    @Query("select case when count(o) > 0 then true else false end from Order o where o.user.id = :userId and o.id = :orderId")
    boolean isUserIsOwner(Integer userId, Integer orderId);

    /***************************************************************************************************** */
}
