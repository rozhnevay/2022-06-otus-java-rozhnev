package ru.otus.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
public class Message implements Copyable<Message> {
    private final long id;
    private final String field1;
    private final String field2;
    private final String field3;
    private final String field4;
    private final String field5;
    private final String field6;
    private final String field7;
    private final String field8;
    private final String field9;
    private final String field10;
    private final ObjectForMessage field11;
    private final ObjectForMessage field12;
    private final ObjectForMessage field13;

    @Override
    public Message copy() {
        return Message.builder()
            .id(id)
            .field1(field1)
            .field2(field1)
            .field3(field1)
            .field4(field4)
            .field5(field5)
            .field6(field6)
            .field7(field7)
            .field8(field8)
            .field9(field9)
            .field9(field10)
            .field10(field10)
            .field11(field11 == null ? field11 : field11.copy())
            .field12(field12 == null ? field12 : field12.copy())
            .field13(field13 == null ? field13 : field13.copy())
            .build();
    }
}
