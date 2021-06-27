package com.syqu.shop.controller;

import com.syqu.shop.domain.Product;
import com.syqu.shop.service.CategoryService;
import com.syqu.shop.service.ProductService;
import com.syqu.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {
    private final ProductService productService;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private  UserService userService;
    @Autowired
    public HomeController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = {"/","/index","/home"})
    public String home(Principal principal, Model model){
        if(principal!=null) {
            model.addAttribute("products", getAllProducts());
            model.addAttribute("productsCount", productsCount());
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("user", userService.findByUsername(principal.getName()));//find by username
        }
        else
        {
            return  "login";
        }
        return "home";
    }





    private List<Product> getAllProducts(){
        return productService.findAllByOrderByIdAsc();
    }

    private long productsCount(){
        return productService.count();
    }
}
