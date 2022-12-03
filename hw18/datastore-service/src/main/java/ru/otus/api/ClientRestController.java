package ru.otus.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import ru.otus.model.Client;
import ru.otus.service.ClientService;

@RestController
@RequiredArgsConstructor
public class ClientRestController {

    private final ClientService clientService;
    private final Scheduler workerPool;

    @GetMapping(value = "/api/client/{id}", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Client> getClientById(@PathVariable(name = "id") long id) {
        return Mono.just(id)
            .flatMapMany(clientService::getClient)
            .subscribeOn(workerPool);
    }

    @GetMapping(value = "/api/client", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Client> getClients() {
        return clientService
            .findAll()
            .subscribeOn(workerPool);
    }

    @PostMapping("/api/client")
    public Mono<Client> saveClient(@RequestBody Client client) {
        return Mono.just(client)
            .flatMap(clientService::saveClient)
            .publishOn(workerPool)
            .subscribeOn(workerPool);
    }


}
