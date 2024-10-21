package ru.modsen.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Schema(description = "Взятая книга")
public class TookBook {
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

    @Schema(description = "id взятой книги", example = "1")
    @NotBlank(message = "Отсутсвует id взятой книги")
    private long book_id;

    @Schema(description = "время взятия книги", example = "1")
    @NotBlank(message = "Отсутсвует время взятия книги")
    private LocalDateTime took_time;

    @Schema(description = "время возврата книги", example = "1")
    @NotBlank(message = "Отсутсвует время возврата книги")
    private LocalDateTime back_time;
}
