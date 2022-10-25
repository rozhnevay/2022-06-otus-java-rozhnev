package ru.otus.core.controllers;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.ClientService;

@RestController
@RequiredArgsConstructor
public class ClientRestController {

    private final ClientService clientService;

    @GetMapping("/api/client/{id}")
    public Client getClientById(@PathVariable(name = "id") long id) {
        return clientService.getClient(id).orElseThrow(() -> new RuntimeException("Client not found"));
    }

    @GetMapping("/api/client")
    public List<Client> getClientByName() {
        return clientService.findAll();
    }

    @PostMapping("/api/client")
    public Client saveClient(@RequestBody Client client) {
        return clientService.saveClient(client);
    }


}
