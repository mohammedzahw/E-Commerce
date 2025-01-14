package com.example.e_commerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.e_commerce.model.Product;

@SuppressWarnings("null")
public interface ProductRepository extends JpaRepository<Product, Integer> {

        Optional<Product> findByTitle(String title);

        /***************************************************************************** */
        Optional<Product> findById(Integer id);

        /***************************************************************************** */
        @Query("select case when count(p) > 0 then true else false end from Product p where p.id = :productId")
        boolean existsById(@Param("productId") Integer productId);

        /****************************************************************************** */

        @Query("select case when count(p) > 0 then true else false end from Product p where p.vendor.id = :vendorId and p.id = :productId")
        boolean isVendorProductOwner(@Param("vendorId") Integer vendorId, @Param("productId") Integer productId);

        /***************************************************************************** */
        @Query("""
                        select p from Product p
                        join p.categories c
                        where c.id = :categoryId
                        """)
        public List<Product> findProductsByCategoryId(@Param("categoryId") Integer categoryId, Pageable pageable);

        /*********************************************************************************** */

        @Query("""
                            select p from Product p
                            join p.vendor v
                            where v.id = :categoryId
                        """)
        public List<Product> findProductsByVendorId(@Param("categoryId") Integer categoryId);

        /*********************************************************************************** */

        @Query("""
                        select p from Product p where p.title like %:keyword% or p.description like %:keyword%
                        """)
        List<Product> searchProducts(String keyword);

}
