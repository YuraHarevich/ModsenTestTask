package ru.modsen.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.modsen.domain.TookBook;
import ru.modsen.service.LibraryService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/accounting")
@AllArgsConstructor
@Tag(name = "Сервис учета взятых книг")
public class LibraryController {
    private final LibraryService service;

    @Operation(summary = "Добавление взятой книги")
    @PostMapping("add/{id}")
    public ResponseEntity<Void> createNewBook(@PathVariable("id") long id){
        TookBook book = TookBook.builder()
                .book_id(id)
                .took_time(LocalDateTime.now())
                .back_time(LocalDateTime.now().plusHours(10))
                .build();
        service.save(book);
        return ResponseEntity.ok().build();
    }
    @GetMapping("get")
    public List<TookBook> getAll(){
        return service.getAll();
    }
}
