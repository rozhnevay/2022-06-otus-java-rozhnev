package ru.otus.crm.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "address")
public class Address {

    @Id
    private Long id;

    private Long clientId;

    private String street;

    public Address(String street) {
        this.id = null;
        this.clientId = null;
        this.street = street;
    }

    public Address(Long id, Long clientId, String street) {
        this.id = id;
        this.street = street;
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "Address{" +
            "id=" + id +
            ", street='" + street + '\'' +
            '}';
    }
}
