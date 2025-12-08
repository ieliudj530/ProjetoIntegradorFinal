package com.ecommerce.projeto.controller;

import com.ecommerce.projeto.model.Cart;
import com.ecommerce.projeto.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping; // ¡Importante!
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Optional; // ¡Importante!

@Controller
@RequestMapping("/carrinho")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public String showCartPage(Model model, Principal principal) {

        if (principal == null) {
            return "redirect:/login";
        }

        String userEmail = principal.getName();
        Optional<Cart> cartOptional = cartService.getCartByUserEmail(userEmail);

        if (cartOptional.isPresent()) {

            model.addAttribute("cart", cartOptional.get());
        } else {

            model.addAttribute("cart", null);
        }

        return "carrinho";
    }

    @PostMapping("/adicionar/{id}")
    public String addProductToCart(@PathVariable("id") Integer productId,
                                   Principal principal,
                                   RedirectAttributes redirectAttributes) {

        if (principal == null) {
            return "redirect:/login";
        }

        try {
            String userEmail = principal.getName();
            cartService.addProductToCart(productId, userEmail);
            redirectAttributes.addFlashAttribute("success", "Produto adicionado ao carrinho!");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao adicionar produto: " + e.getMessage());
        }

        return "redirect:/produtos";
    }
    @PostMapping("/remover/{id}")
    public String removeItemFromCart(@PathVariable("id") Integer itemId,
                                     Principal principal,
                                     RedirectAttributes redirectAttributes) {

        if (principal == null) {
            return "redirect:/login";
        }

        try {
            cartService.removeItemFromCart(itemId);
            redirectAttributes.addFlashAttribute("success", "Produto removido do carrinho.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao remover produto: " + e.getMessage());
        }

        return "redirect:/carrinho";
    }
    @PostMapping("/checkout")
    public String processCheckout(Principal principal, RedirectAttributes redirectAttributes) {

        if (principal == null) {
            return "redirect:/login";
        }

        try {
            cartService.checkout(principal.getName());

            redirectAttributes.addFlashAttribute("success", "Compra finalizada com sucesso!");

            return "redirect:/pedido/sucesso";

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute("error", "Erro ao finalizar compra: " + e.getMessage());
            return "redirect:/carrinho";
        }
    }
}