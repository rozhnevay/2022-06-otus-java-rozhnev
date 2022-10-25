package ru.otus.crm.model;


import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@Table("client")
@Getter
public class Client {

    @Id
    private final Long id;
    private final String name;

    @MappedCollection(idColumn = "client_id")
    private final Set<Phone> phones;

    @MappedCollection(idColumn = "client_id")
    private final Address address;

    public Client() {
        this.id = null;
        this.name = null;
        this.phones = new HashSet<>();
        this.address = null;
    }

    @PersistenceCreator
    public Client(Long id, String name, Set<Phone> phones, Address address) {
        this.id = id;
        this.name = name;
        this.phones = phones;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Client{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
    }
}
