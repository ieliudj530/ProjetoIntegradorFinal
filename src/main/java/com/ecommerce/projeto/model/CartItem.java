package com.ecommerce.projeto.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne
    @JoinColumn (name = "product_id", nullable = false)
    private Product product;

    @PrePersist
    public void prePersist(){
        if (quantity == null){
            quantity = 1;
        }
    }
}
