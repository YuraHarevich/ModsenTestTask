package ru.modsen.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.modsen.model.TookBook;
import ru.modsen.service.LibraryService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("accountant")
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
