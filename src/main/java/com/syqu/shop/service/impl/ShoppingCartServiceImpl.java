package com.syqu.shop.service.impl;

import com.syqu.shop.domain.Cart;
import com.syqu.shop.domain.Product;
import com.syqu.shop.domain.User;
import com.syqu.shop.repository.CartRepository;
import com.syqu.shop.service.ProductService;
import com.syqu.shop.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;



@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private CartRepository cartRepository;
    @Autowired
    private ProductService productService;
    private Map<Product, Integer> cart = new LinkedHashMap<>();

    public ShoppingCartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

//    @Override
//    public void addProduct(Product product) {
//        //Add prod to cart
//        if (cart.containsKey(product)){
//            cart.replace(product, cart.get(product) + 1);
//        }else{
//            cart.put(product, 1);
//        }
//    }

    @Override
    public void removeProduct(Product product) {
        if (cart.containsKey(product)) {
            if (cart.get(product) > 1)
                cart.replace(product, cart.get(product) - 1);
            else if (cart.get(product) == 1) {
                cart.remove(product);
            }
        }
    }

    @Override
    public void clearProducts() {
        cart.clear();
    }






    @Override
    public void cartCheckout() {
        cart.clear();
        //TODO Normally we would save the order etc.
    }

    @Override
    public void save(Cart cart) {
        cartRepository.save(cart);
    }
    public int countProduct(User userid, Product productId){
        return cartRepository.countProduct(userid,productId);
    }

    @Override
    public List<Cart> findAllByUser(User user) {
        return cartRepository.findAllByUser(user) ;
    }

    @Override
    public Cart findAllByUserAndProduct(User user, Product product) {
        return cartRepository.findAllByUserAndAndProduct(user, product).get(0);
    }
    public void incrementQte(User user,Product product,int qte)
    {
        cartRepository.incrementQte(user,product,qte);
    }
    public double totalPrice(List<Cart> carts) {
        double total = 0;
        for (Cart cart : carts
        ) {
            total += cart.getQte() * cart.getProduct().getPrice().doubleValue();

        }
        return total;
    }
    public void removeCartByUserAndAndProduct(User user,Product product)
    {
        cartRepository.removeCartByUserAndAndProduct(user,product);
    }

    public void removeAllByUser(User user){
        cartRepository.removeAllByUser(user);
    }
}
