package org.example.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "myUser")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", allocationSize = 1)
    private Long id;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String password;
    private String email;
    private String role;
}
