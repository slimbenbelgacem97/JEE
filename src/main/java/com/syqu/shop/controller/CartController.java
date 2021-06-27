package com.syqu.shop.controller;

import com.syqu.shop.domain.Cart;
import com.syqu.shop.domain.User;
import com.syqu.shop.service.ShoppingCartService;
import com.syqu.shop.domain.Product;
import com.syqu.shop.service.ProductService;
import com.syqu.shop.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;

@Controller
public class CartController {
    private static final Logger logger = LoggerFactory.getLogger(CartController.class);
    private final ShoppingCartService shoppingCartService;
    private final ProductService productService;
    private final UserService userService;


    @Autowired
    public CartController(ShoppingCartService shoppingCartService, ProductService productService,UserService userService) {
        this.shoppingCartService = shoppingCartService;
        this.productService = productService;
        this.userService=userService;
    }

    @GetMapping("/cart")
    public String cart(Model model, Principal principal){
        if(principal==null)
            return "login";
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("carts", shoppingCartService.findAllByUser(user));
        List<Cart> carts=shoppingCartService.findAllByUser(user);


        model.addAttribute("totalPrice", shoppingCartService.totalPrice(carts));

        return "cart";
    }

    @GetMapping("/cart/add/{productId}/{userId}")
    public String addProductToCart(@PathVariable("productId") long productId,@PathVariable("userId") long userId){
        User user =userService.findById(userId);
        Product product = productService.findById(productId);

        if (!(product.equals(null))&& !(user.equals(null))){
            if (shoppingCartService.countProduct(user,product)==0) {
                Cart cart = new Cart(user, product);
                shoppingCartService.save(cart);
                logger.debug(String.format("Product with id: %s added to shopping cart.", productId));
            }
            else {
            Cart cart= shoppingCartService.findAllByUserAndProduct(user,product);
            shoppingCartService.incrementQte(user,product,cart.getQte()+1);
                logger.debug(String.format("exist!"));
            }
        }
        return "redirect:/home";
    }

    @GetMapping("/cart/remove/{id}")
    public String removeProductFromCart(@PathVariable("id") long id,Principal principal){
        Product product = productService.findById(id);
        User user=userService.findByUsername(principal.getName());
        if (product != null){
            shoppingCartService.removeCartByUserAndAndProduct(user,product);
            logger.debug(String.format("Product with id: %s removed from shopping cart.", id));
        }
        return "redirect:/cart";
    }

    @GetMapping("/cart/clear")
    public String clearProductsInCart(Principal principal){
        User user=userService.findByUsername(principal.getName());
        shoppingCartService.removeAllByUser(user);

        return "redirect:/cart";
    }

    @GetMapping("/cart/checkout")
    public String cartCheckout(Principal principal){
        User user=userService.findByUsername(principal.getName());
        //TODO SAVE ORDER ON CHECKOUT INSTEAD OF CLEAR
        shoppingCartService.removeAllByUser(user);

        return "redirect:/cart";
    }
}
