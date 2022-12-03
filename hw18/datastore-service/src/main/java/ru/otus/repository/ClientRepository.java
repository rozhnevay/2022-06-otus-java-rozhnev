package ru.otus.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.model.Client;

@Repository
public interface ClientRepository extends ReactiveCrudRepository<Client, Long> {
}
