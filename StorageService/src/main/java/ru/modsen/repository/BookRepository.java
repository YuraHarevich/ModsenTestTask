package ru.modsen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.modsen.domain.Book;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long> {
    Optional<Book> findByISBN(String ISBN);
}
