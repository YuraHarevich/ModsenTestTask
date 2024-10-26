package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.AuthenticationResponse;
import org.example.entity.SignInRequest;
import org.example.entity.SignUpRequest;
import org.example.entity.UserDTO;
import org.example.exceptions.UnauthorizedException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final JwtService jwtService;
    private final RestTemplate restTemplate;
    public AuthenticationResponse signIn(SignInRequest request) {
        UserDTO requestUser = UserDTO.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .build();
        UserDTO response;
        try {
            response=restTemplate.postForObject(
                    "http://USER-SERVICE/users/get",
                    requestUser,
                    UserDTO.class
            );
        }catch (HttpClientErrorException e){
            throw new UnauthorizedException("User with such username not found or wrong data type");
        }

            if (!BCrypt.checkpw(request.getPassword(), response.getPassword()))
                throw new UnauthorizedException("Bad credentials or wrong data type");
            return new AuthenticationResponse(jwtService.generateToken(response));

    }
    public AuthenticationResponse signUp(SignUpRequest request) {
        UserDTO user = UserDTO.builder()
                        .role("ROLE_USER")
                        .email(request.getEmail())
                        .password(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()))
                        .username(request.getUsername()).build();
        restTemplate.postForObject(
                "http://USER-SERVICE/users/save",
                user,
                UserDTO.class);
        return new AuthenticationResponse(jwtService.generateToken(user));
    }

    //сделано для демонстрации

}
