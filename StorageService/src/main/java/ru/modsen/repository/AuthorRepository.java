package ru.modsen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.modsen.domain.Author;

public interface AuthorRepository extends JpaRepository<Author,Long> {
}
