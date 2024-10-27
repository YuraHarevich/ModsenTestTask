package ru.modsen.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.modsen.exception.BookNotFoundException;
import ru.modsen.model.Book;
import ru.modsen.service.BookService;

import java.util.List;

@RestController
@RequestMapping("storage")
@AllArgsConstructor
@Tag(name = "Библиотека")
public class MainController {
    private final BookService service;
    @GetMapping("all")
    @Operation(summary = "Получение всех книг")
    public List<Book> getAllBooks(){
        return service.getAllBooks();
    }
    @GetMapping("id/{id}")
    @Operation(summary = "Получение книг по id")
    public Book getBookByID(@PathVariable("id")long id){

        return service.getById(id);
    }
    @GetMapping("isbn/{ISBN}")
    @Operation(summary = "Получение книг по ISBN")
    public Book getBookByISBN(@PathVariable("ISBN")String ISBN){
        return service.getByISBN(ISBN);
    }
    @PostMapping("create")
    @Operation(summary = "Создание новой книги")
    public void createNewBook(@RequestBody @Valid Book book){
        service.save(book);
    }
    @DeleteMapping("delete/{id}")
    @Operation(summary = "Удаление книги")
    public void deleteBook(@PathVariable("id") long id){
        service.delete(id);
    }
    @PatchMapping("change/{id}")
    @Operation(summary = "Изменение книги")
    public void changeBook(@RequestBody @Valid Book book,
                           @PathVariable("id")long id){
        service.changeBook(book,id);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Не верный формат данных");
    }
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<String> handleBOokNotFound(BookNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("указанная книга не найдена");
    }
    @ExceptionHandler({DataIntegrityViolationException.class, ConstraintViolationException.class})
    public ResponseEntity<String> handleDuplicateKeyException() {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Книга с таким ISBN уже существует.");
    }
}
