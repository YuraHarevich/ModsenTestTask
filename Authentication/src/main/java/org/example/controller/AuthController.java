package org.example.controller;

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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authentication")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping(value = "/sign-up")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authService.signUp(request));
    }
    @PostMapping(value = "/sign-in")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody SignInRequest request) {
        return ResponseEntity.ok(authService.signIn(request));
    }
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handleUnauthorizedException(UnauthorizedException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
}
