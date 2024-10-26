package ru.modsen.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.modsen.enumeration.Genre;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Schema(description = "Книга")
public class Book {
    @Id
    @SequenceGenerator(
            name = "book_id_sequence",
            sequenceName = "book_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "book_id_sequence"
    )
    private Long id;

    @Schema(description = "ISBN", example = "978-5-17-080115-2")
    @Pattern(regexp = "97[89]-\\d{1,5}-\\d{1,7}-\\d{1,6}-\\d{1}",
    message = "String should be of standard ISBN-13")
    @Column(unique = true)
    private String isbn;

    @Schema(description = "название", example = "1984")
    @NotBlank(message = "Название не может отсутствовать")
    private String name;

    @Schema(description = "жанр", example = "THRILLER")
    @Enumerated(EnumType.STRING)
    private Genre genre;

    @NotBlank(message = "Описание не может отсутствовать")
    @Schema(description = "описание", example = "Величайшая антиутопия XX века об обществе абсолюта идеи")
    private String description;

    @NotNull(message = "Автор не может отсутствовать")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id")
    @Schema(description = "автор", example = "THRILLER")
    private Author author;

}
