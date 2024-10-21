package ru.modsen.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Schema(description = "Автор")
public class Author {
    @Id
    @SequenceGenerator(
            name = "author_id_sequence",
            sequenceName = "author_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "author_id_sequence"
    )
    private Long id;

    @NotBlank(message = "Имя не может отсутствовать")
    @Schema(description = "имя", example = "Джордж")
    private String firstName;

    @Schema(description = "фамилия", example = "Оруэл")
    @NotBlank(message = "Фамилия не может отсутствовать")
    private String lastName;
}
