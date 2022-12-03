package ru.otus.model;

import lombok.Data;

@Data
public class Client {

    private final Long id;
    private final String name;

    public Client() {
        this.id = null;
        this.name = null;
    }

    @Override
    public String toString() {
        return "Client{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
    }
}
