package com.syqu.shop.repository;

import com.syqu.shop.domain.Cart;
import com.syqu.shop.domain.Product;
import com.syqu.shop.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository  extends JpaRepository<Cart, Long> {
    List<Cart> findAllByUser(User user);
    List<Cart> findAllByUserAndAndProduct(User user, Product product);

    @Query ("select count (id) from cart where user =:user and product=:product")
    int countProduct(@Param("user") User user,@Param("product") Product product);
    @Modifying
    @Query("UPDATE  cart SET qte=:qte WHERE user =:user and product=:product")
    void incrementQte(@Param("user") User user, @Param("product") Product product, @Param("qte") int qte);
    @Query(" SELECT SUM(p.price*c.qte) FROM cart c,Product p WHERE c.user=:user AND p.id=c.product.id")
    int totalPrice(@Param("user")User user);


    void removeCartByUserAndAndProduct(User user,Product product);
    void removeAllByUser(User user);




}
