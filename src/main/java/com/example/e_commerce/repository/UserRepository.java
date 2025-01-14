package com.example.e_commerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.e_commerce.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    /*************************************************************************************************** */
    @Modifying
    @Query(nativeQuery = true, value = "insert into cart(user_id, product_id) values(:id, :productId)")
    void addToCart(Long id, Long productId);

    /*************************************************************************************************** */

    @Modifying
    @Query(nativeQuery = true, value = "delete from cart where user_id = :id and product_id = :productId")
    void removeFromCart(Long id, Long productId);

    /***************************************************************************************************** */
    @Modifying
    @Query(nativeQuery = true, value = "insert into wishlist(user_id, product_id) values(:id, :productId)")
    void addToWishList(Long id, Long productId);

    /***************************************************************************************************** */
    @Modifying
    @Query(nativeQuery = true, value = "delete from wishlist where user_id = :id and product_id = :productId")
    void removeFromWishList(Long id, Long productId);

    /***************************************************************************************************** */
    @Modifying
    @Query(nativeQuery = true, value = "delete from cart where user_id = :userId")
    void clearCart(@Param("userId") Integer userId);
}
