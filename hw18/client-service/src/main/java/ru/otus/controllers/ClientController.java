package ru.otus.controllers;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.otus.model.Client;

@Controller
@RequiredArgsConstructor
public class ClientController {

    private final WebClient webClient;

    @GetMapping({"/", "/client/list"})
    public String clientsListView(Model model) {
        List<Client> clients = webClient.get().uri("/api/client").exchangeToFlux(response -> {
            if (response.statusCode().equals(HttpStatus.OK)) {
                return response.bodyToFlux(Client.class);
            } else {
                return response.createException().flatMapMany(Mono::error);
            }
        }).toStream().toList();
        model.addAttribute("clients", clients);
        return "clients";
    }

    @GetMapping("/client/create")
    public String clientCreateView(Model model) {
        model.addAttribute("client", new Client());
        return "clientCreate";
    }

}
