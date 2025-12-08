package com.ecommerce.projeto.service;

import com.ecommerce.projeto.model.Role;
import com.ecommerce.projeto.model.User;
import com.ecommerce.projeto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registrarUsuario(User user) {

        String senhaEncriptada = passwordEncoder.encode(user.getPassword());
        user.setPassword(senhaEncriptada);

        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }

        userRepository.save(user);
    }

    public Optional<User> buscarPorEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // (Aquí irían otros métodos, como actualizarPerfil, etc.)
}