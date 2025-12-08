package com.ecommerce.projeto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pedido")
public class OrderController {

    @GetMapping("/sucesso")
    public String showOrderSuccessPage() {
        return "pedido-sucesso"; // El nombre del nuevo HTML
    }
}