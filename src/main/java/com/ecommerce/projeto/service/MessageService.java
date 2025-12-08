package com.ecommerce.projeto.service;

import com.ecommerce.projeto.model.Message;
import com.ecommerce.projeto.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public void save(Message message) {
        messageRepository.save(message);
    }

    public List<Message> findAll() {
        return messageRepository.findAll();
    }
}