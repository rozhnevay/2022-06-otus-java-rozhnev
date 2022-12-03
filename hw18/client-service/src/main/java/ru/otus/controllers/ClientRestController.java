package ru.otus.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.otus.model.Client;

@RestController
@RequiredArgsConstructor
public class ClientRestController {

    private final WebClient webClient;


    @PostMapping("/api/client")
    public Mono<Client> saveClient(@RequestBody Client client) {
        return webClient.post().uri("/api/client")
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(client)
            .exchangeToMono(response -> response.bodyToMono(Client.class));
    }


}
