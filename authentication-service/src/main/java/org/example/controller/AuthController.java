package org.example.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.entity.AuthenticationResponse;
import org.example.entity.SignInRequest;
import org.example.entity.SignUpRequest;
import org.example.entity.UserDTO;
import org.example.exceptions.UnauthorizedException;
import org.example.service.AuthService;
import org.example.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/authentication")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping(value = "/sign-up")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid SignUpRequest request) {
        return ResponseEntity.ok(authService.signUp(request));
    }
    @PostMapping(value = "/sign-in")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid SignInRequest request) {
        return ResponseEntity.ok(authService.signIn(request));
    }
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handleUnauthorizedException(UnauthorizedException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный формат данных");
    }
}
