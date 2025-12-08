package com.ecommerce.projeto.controller;

import com.ecommerce.projeto.model.Product;
import com.ecommerce.projeto.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/productos") // ¡Importante! Prefijo para todas las rutas
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String listProductos(Model model) {
        List<Product> listaProductos = productService.findAll();
        model.addAttribute("productos", listaProductos);
        return "admin/admin-productos"; // HTML de la lista
    }

    @GetMapping("/nuevo")
    public String showFormularioNuevo(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("pageTitle", "Novo Produto");
        return "admin/admin-producto-form"; // HTML del formulario
    }

    @PostMapping("/guardar")
    public String saveProducto(@ModelAttribute("product") Product product, RedirectAttributes redirectAttributes) {
        productService.save(product);
        redirectAttributes.addFlashAttribute("success", "Produto salvo com sucesso!");
        return "redirect:/admin/productos";
    }

    @GetMapping("/editar/{id}")
    public String showFormularioEditar(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Product product = productService.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("ID do produto inválido:" + id));

            model.addAttribute("product", product);
            model.addAttribute("pageTitle", "Editar Produto (ID: " + id + ")");
            return "admin/admin-producto-form"; // Reutilizamos el mismo HTML del formulario

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/productos";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String deleteProducto(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            productService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Produto eliminado com sucesso.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Não foi possível eliminar o produto.");
        }
        return "redirect:/admin/productos";
    }
}