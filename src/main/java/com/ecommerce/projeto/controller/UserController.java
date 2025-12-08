package com.ecommerce.projeto.controller;

import com.ecommerce.projeto.model.Product; // <-- 1. ¡ASEGÚRATE DE IMPORTAR Product!
import com.ecommerce.projeto.model.User;
import com.ecommerce.projeto.service.ProductService; // <-- 2. ¡ASEGÚRATE DE IMPORTAR ProductService!
import com.ecommerce.projeto.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List; // <-- 3. ¡ASEGÚRATE DE IMPORTAR List!

@Controller
public class UserController { // o UsuarioController

    @Autowired
    private UserService userService;

    // --- 4. ¡AÑADE ESTO! ---
    // Inyecta el servicio de productos para poder buscarlos
    @Autowired
    private ProductService productService;

    // --- Métodos que ya tenías ---

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        try {
            userService.registrarUsuario(user);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao registrar: " + e.getMessage());
            return "redirect:/register";
        }
        redirectAttributes.addFlashAttribute("success", "Registro bem-sucedido! Faça o login.");
        return "redirect:/login";
    }

    @GetMapping("/home")
    public String showHomePage() {
        return "home";
    }


    @GetMapping("/produtos")
    public String showProdutosPage(Model model) {
        // Buscamos todos los productos de la base de datos
        List<Product> listaProductos = productService.findAll();
        // Los mandamos al HTML (al th:each="prod : ${produtos}")
        model.addAttribute("produtos", listaProductos);
        return "produtos"; // Devuelve produtos.html
    }


    @GetMapping("/sobre-nos")
    public String showSobreNosPage() {
        return "sobre-nos"; // Devuelve sobre-nos.html
    }
}