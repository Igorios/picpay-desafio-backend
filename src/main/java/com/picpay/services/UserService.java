package com.picpay.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.picpay.domain.user.User;
import com.picpay.domain.user.UserType;
import com.picpay.dtos.UserDTO;
import com.picpay.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(UserDTO data) {
        User newUser = new User(data);
        this.saveUser(newUser);
        return newUser;
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public void validateTransaction (User sender, BigDecimal amount) throws Exception {

        if (sender.getUserType() == UserType.MERCHANT) {
            throw new Exception("Usuário lojista não está autorizado realizar transação");
        }

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new Exception("Saldo insuficiente!");
        }

    }

    public User findUserById(Long id) throws Exception{
        return this.userRepository.findUserById(id).orElseThrow(() -> new Exception("Usuário não encontrado"));
    }

    public void saveUser(User user) {
        this.userRepository.save(user);
    }
    
}
