package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.model.User;
import org.example.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/save")
    public ResponseEntity<User> create(@RequestBody User user) {
        return ResponseEntity.ok(userService.save(user));
    }
    @PostMapping("/get")
    public ResponseEntity<User> get(@RequestBody User user) {
        Optional<User> optional = userService.getByUsername(user.getUsername());
        return optional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
