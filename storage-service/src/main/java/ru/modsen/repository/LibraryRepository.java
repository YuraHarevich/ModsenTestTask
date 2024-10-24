package ru.modsen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.modsen.model.TookBook;

public interface LibraryRepository extends JpaRepository<TookBook,Long> {
}
