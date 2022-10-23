package ru.otus.core.repository;

import java.util.Optional;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.otus.crm.model.Client;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {
}
