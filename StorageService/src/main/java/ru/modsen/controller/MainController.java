package ru.modsen.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.modsen.domain.Book;
import ru.modsen.service.BookService;

import java.util.List;

@RestController
@RequestMapping("api/v1/storage")
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
        //todo: solve optional
        return service.getById(id).get();
    }
    @GetMapping("isbn/{ISBN}")
    @Operation(summary = "Получение книг по ISBN")
    public Book getBookByISBN(@PathVariable("ISBN")String ISBN){
        //todo: solve optional
        return service.getByISBN(ISBN).get();
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
    public void changeBook(@RequestBody Book book,
                           @PathVariable("id")long id){
        service.changeBook(book,id);
    }
}
