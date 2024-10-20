package ru.modsen.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.modsen.domain.Book;
import ru.modsen.service.BookService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/storage")
@AllArgsConstructor
public class MainController {
    private final BookService service;
    @GetMapping("all")
    public List<Book> getAllBooks(){
        return service.getAllBooks();
    }
    @GetMapping("{id}")
    public Book getAllBooks(@PathVariable("id")long id){

        //todo: solve optional

        return service.getById(id).get();
    }
    @GetMapping("{ISBN}")
    public Book getAllBooks(@PathVariable("ISBN")String ISBN){

        //todo: solve optional

        return service.getByISBN(ISBN).get();
    }
    @PostMapping("create")
    public void createNewBook(@RequestBody @Valid Book book){
        service.save(book);
    }
    @DeleteMapping("delete/{id}")
    public void deleteBook(@PathVariable("id") long id){
        service.delete(id);
    }
    @PatchMapping("change/{id}")
    public void changeBook(@RequestBody Book book,
                           @PathVariable("id")long id){
        service.changeBook(book,id);
    }
}
