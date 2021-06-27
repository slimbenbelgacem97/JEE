package com.syqu.shop.service;

import com.syqu.shop.domain.Cart;
import com.syqu.shop.domain.Product;
import com.syqu.shop.domain.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public interface ShoppingCartService {
//    void addProduct(Product product);
    void removeProduct(Product product);
    void clearProducts();
    void cartCheckout();
    List<Cart> findAllByUser(User user);
    Cart findAllByUserAndProduct(User user, Product product);
    void save(Cart cart);
    public int countProduct(User userid, Product productId);
    public void incrementQte(User user,Product product,int qte);
    double totalPrice(List<Cart> carts);
    void  removeCartByUserAndAndProduct(User user,Product product);
    void removeAllByUser(User user);

}
