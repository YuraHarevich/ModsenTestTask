package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.model.User;
import org.example.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.Optional;

@RestController
@RequestMapping(value = "users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/save")
    public void create(@RequestBody User user) {
        userService.save(user);
    }
    @PostMapping("/get")
    public ResponseEntity<User> get(@RequestBody User user) {
        Optional<User> optional = userService.getByUsername(user.getUsername());
        return optional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PatchMapping("/set_admin/{id}")
    public ResponseEntity<String> set_admin(@PathVariable("id") long id) {
        Optional<User> optional = userService.getById(id);
        optional.ifPresent(userService::setAdminRole);
        if(optional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        else{
            userService.setAdminRole(optional.get());
            return ResponseEntity.ok("Admin successfully set");
        }
    }
}
