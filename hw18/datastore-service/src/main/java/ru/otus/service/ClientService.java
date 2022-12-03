package ru.otus.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.model.Client;

public interface ClientService {

    Mono<Client> saveClient(Client client);

    Mono<Client> getClient(long id);

    Flux<Client> findAll();
}
