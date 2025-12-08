package com.ecommerce.projeto.service;

import com.ecommerce.projeto.model.*;
import com.ecommerce.projeto.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private SaleItemRepository saleItemRepository;

    @Transactional
    public void addProductToCart(Integer productId, String userEmail) {

        Product product = productService.findById(productId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        User user = userService.buscarPorEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Cart cart = getOrCreateCart(user);

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + 1);
            cartItemRepository.save(item);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(1);
            cartItemRepository.save(newItem);

            cart.getItems().add(newItem);
        }

        cartRepository.save(cart);
    }

    private Cart getOrCreateCart(User user) {

        Optional<Cart> optionalCart = cartRepository.findByUser(user);

        if (optionalCart.isPresent()) {
            return optionalCart.get();
        } else {
            Cart newCart = new Cart();
            newCart.setUser(user);
            return cartRepository.save(newCart);
        }

    }

    public Optional<Cart> getCartByUserEmail(String userEmail) {
        User user = userService.buscarPorEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));


        return cartRepository.findByUser(user);
    }

    public void removeItemFromCart(Integer cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    @Transactional
    public Sale checkout(String userEmail) {


        User user = userService.buscarPorEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Cart cart = getCartByUserEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));

        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new RuntimeException("Seu carrinho está vazio!");
        }

        Sale newSale = new Sale();
        newSale.setUser(user);
        newSale.setTotal(BigDecimal.valueOf(cart.getTotal()));
        newSale.setRegion(user.getCity() + ", " + user.getCountry());


        Sale savedSale = saleRepository.save(newSale);


        for (CartItem cartItem : cart.getItems()) {
            SaleItem saleItem = new SaleItem();
            saleItem.setSale(savedSale);
            saleItem.setProduct(cartItem.getProduct());
            saleItem.setQuantity(cartItem.getQuantity());
            saleItem.setPrice(cartItem.getProduct().getPrice());


            saleItemRepository.save(saleItem);
        }

        cartRepository.delete(cart);

        return savedSale;
    }

}