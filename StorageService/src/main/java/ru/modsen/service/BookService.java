package ru.modsen.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.modsen.domain.Author;
import ru.modsen.domain.Book;
import ru.modsen.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookService {
    private final BookRepository repository;
    public void save(Book book){
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
