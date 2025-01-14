package com.example.e_commerce.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.e_commerce.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("""
            select p from Product p
            join p.categories c
            where c.id = :categoryId
            """)
    public List<Product> findProductsByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);

    /*********************************************************************************** */

    @Query("""
                select p from Product p
                join p.vendor v
                where v.id = :categoryId
            """)
    public List<Product> findProductsByVendorId(@Param("categoryId") Long categoryId);

}
