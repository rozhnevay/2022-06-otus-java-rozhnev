package ru.otus.model;


import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

@Table("client")
@Getter
public class Client {

    @Id
    private final Long id;
    private final String name;


    public Client() {
        this.id = null;
        this.name = null;
    }

    @PersistenceCreator
    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Client{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
    }
}
