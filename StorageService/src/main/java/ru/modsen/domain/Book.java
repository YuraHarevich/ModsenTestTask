package ru.modsen.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @Pattern(regexp = "97[89]-\\d{1,5}-\\d{1,7}-\\d{1,6}-\\d{1}",
    message = "String should be of standard ISBN-13")
    @Column(unique = true)
    private String ISBN;

    @NotBlank(message = "Название не может отсутствовать")
    private String name;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @NotBlank(message = "Описание не может отсутствовать")
    private String description;

    @NotNull(message = "Автор не может отсутствовать")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id")
    private Author author;

}
