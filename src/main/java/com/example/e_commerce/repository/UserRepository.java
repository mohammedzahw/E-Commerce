package com.example.e_commerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.e_commerce.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    /*************************************************************************************************** */
    @Modifying
    @Query(nativeQuery = true, value = "insert into cart(user_id, product_id) values(:id, :productId)")
    void addToCart(Integer id, Integer productId);

    /*************************************************************************************************** */

    @Modifying
    @Query(nativeQuery = true, value = "delete from cart where user_id = :id and product_id = :productId")
    void removeFromCart(Integer id, Integer productId);

    /***************************************************************************************************** */
    @Modifying
    @Query(nativeQuery = true, value = "insert into wishlist(user_id, product_id) values(:id, :productId)")
    void addToWishList(Integer id, Integer productId);

    /***************************************************************************************************** */
    @Modifying
    @Query(nativeQuery = true, value = "delete from wishlist where user_id = :id and product_id = :productId")
    void removeFromWishList(Integer id, Integer productId);

    /***************************************************************************************************** */
    @Modifying
    @Query(nativeQuery = true, value = "delete from cart where user_id = :userId")
    void clearCart(@Param("userId") Integer userId);
}
