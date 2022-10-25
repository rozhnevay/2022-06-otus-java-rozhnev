package ru.otus.crm.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "phone")
public class Phone {

    @Id
    private Long id;

    private Long clientId;

    private String number;

    public Phone(String number, Long clientId) {
        this.id = null;
        this.number = number;
        this.clientId = clientId;
    }

    public Phone(Long id, String number) {
        this.id = id;
        this.number = number;
    }

    public Phone(Long id, String number, Long clientId) {
        this.id = id;
        this.number = number;
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "Phone{" +
            "id=" + id +
            ", number='" + number + '\'' +
            ", clientId='" + clientId + '\'' +
            '}';
    }
}
