package com.ecommerce.projeto.repository;

import com.ecommerce.projeto.model.Cart;
import com.ecommerce.projeto.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    Optional<Cart> findByUser(User user);

}