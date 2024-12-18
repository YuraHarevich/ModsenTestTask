package ru.modsen.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.modsen.exception.BookNotFoundException;
import ru.modsen.model.Book;
import ru.modsen.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class BookService {
    private final BookRepository repository;
    private final RestTemplate restTemplate;
    public void save(Book book){
        //temporary solution
        repository.saveAndFlush(book);
        log.info("book was successfully saved into library!!!");
        try {
        restTemplate.postForObject(
                "http://ACCOUNTANT-SERVICE/accountant/add/{bookId}",
                null,
                Void.class,
                book.getId());
        } catch (HttpClientErrorException e) {
            log.error("!!!book wasn't saved at accounting service!!!");
        }
        log.info("book was successfully took!!!");
        //temporary solution

    }
    public List<Book> getAllBooks(){
        return repository.findAll();
    }
    public Book getById(long id){
        if(repository.findById(id).isEmpty())
            throw new BookNotFoundException("No book with such id");
        else
            return repository.findById(id).get();
    }
    public Book getByISBN(String ISBN){
        if(repository.findByIsbn(ISBN).isEmpty())
            throw new BookNotFoundException("No book with such ISBN");
        return repository.findByIsbn(ISBN).get();
    }

    public void delete(long id) {
        repository.deleteById(id);
    }
    public void changeBook(Book book,long id){
        Optional<Book> optional = repository.findById(id);
        if(optional.isPresent())
        {
            Book modifyingBook = optional.get();
            modifyingBook.setAuthor(book.getAuthor());
            modifyingBook.setName(book.getName());
            modifyingBook.setIsbn(book.getIsbn());
            modifyingBook.setDescription(book.getDescription());
            modifyingBook.setGenre(book.getGenre());
            repository.save(modifyingBook);
        }
        else
            throw new BookNotFoundException("No such book found");
    }

}
