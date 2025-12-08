package com.ecommerce.projeto.controller;

import com.ecommerce.projeto.model.Message;
import com.ecommerce.projeto.model.User;
import com.ecommerce.projeto.service.MessageService;
import com.ecommerce.projeto.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam; // <-- ¡Importante!
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class ContactController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;


    @GetMapping("/contacto")
    public String showContactForm(Model model) {

        return "contacto-form";
    }

    @PostMapping("/contacto")
    public String submitContactForm(
            @RequestParam("message") String messageText, // <-- 1. Recibimos solo el TEXTO
            Principal principal,
            RedirectAttributes redirectAttributes) {

        try {

            Message message = new Message();
            message.setMessage(messageText);


            String email = principal.getName();
            User user = userService.buscarPorEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));


            message.setUser(user);
            messageService.save(message);

            redirectAttributes.addFlashAttribute("success", "Mensagem enviada com sucesso!");
            return "redirect:/home";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao enviar mensagem: " + e.getMessage());
            return "redirect:/contacto";
        }
    }

    @GetMapping("/admin/mensajes")
    public String showAdminMessages(Model model) {
        List<Message> listaMensajes = messageService.findAll();
        model.addAttribute("mensajes", listaMensajes);
        return "admin/admin-mensajes";
    }
}