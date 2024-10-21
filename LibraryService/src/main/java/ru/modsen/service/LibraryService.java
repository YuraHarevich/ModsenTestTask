package ru.modsen.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.modsen.domain.TookBook;
import ru.modsen.repository.LibraryRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LibraryService {
    private final LibraryRepository repository;
    public void save(TookBook book){
        repository.save(book);
    }
    public Optional<TookBook> getById(long id){
        return repository.findById(id);
    }
}
