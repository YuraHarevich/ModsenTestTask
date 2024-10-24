package org.example.entity;

import lombok.*;

import java.util.Collection;
import java.util.List;


@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class UserDTO {
    private long id;
    private String username;
    private String password;
    private String email;
    private String role;

}
