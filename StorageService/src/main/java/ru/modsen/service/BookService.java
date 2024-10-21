package ru.modsen.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.modsen.domain.Author;
import ru.modsen.domain.Book;
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
        try {
        restTemplate.postForObject(
                "http://localhost:8081/api/v1/accounting/add/{customerId}",
                null,
                Void.class,
                book.getId());
        } catch (HttpClientErrorException e) {
            log.error("!!!book wasnt saved at accounting service!!!");
        }
        //temporary solution
        repository.save(book);
    }
    public List<Book> getAllBooks(){
        return repository.findAll();
    }
    public Optional<Book> getById(long id){
        return repository.findById(id);
    }
    public Optional<Book> getByISBN(String ISBN){
        return repository.findByISBN(ISBN);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }
    public void changeBook(Book book,long id){
        // todo NO SUCH BOOK
        Book modifyingBook = repository.findById(id).get();
        modifyingBook.setAuthor(book.getAuthor());
        modifyingBook.setName(book.getName());
        modifyingBook.setISBN(book.getISBN());
        modifyingBook.setDescription(book.getDescription());
        modifyingBook.setGenre(book.getGenre());
        repository.save(modifyingBook);
    }

}
