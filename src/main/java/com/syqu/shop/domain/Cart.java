package com.syqu.shop.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity(name="cart")
@Data
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "userid",referencedColumnName = "id")
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn (name="productid",referencedColumnName = "id")
    public Product product;
    @Column(name = "Qte")
    public int qte;
    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }



    public User getUser() {
        return user;
    }

    public void setUser(User userid) {
        this.user = userid;
    }



    public Cart(User user,Product product) {
        this.user = user;
        this.product = product;
        this.qte=1;
    }

    public Cart() {
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product productid) {
        this.product = productid;
    }

}