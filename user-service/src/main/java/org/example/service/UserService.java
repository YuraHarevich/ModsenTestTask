package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public User save(User user){
        return userRepository.save(user);
    }
    public User create(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            //todo custom exception
            throw new RuntimeException("Пользователь с таким именем уже существует");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            //todo custom exception
            throw new RuntimeException("Пользователь с таким email уже существует");
        }
        return save(user);
    }

    public Optional<User> getByUsername(String username) {
        //todo custom exception
        return userRepository.findByUsername(username);
    }

}
