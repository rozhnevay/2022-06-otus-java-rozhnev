package ru.otus.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class MessageWithTime extends Message {
    private final LocalDateTime createdAt;

    public MessageWithTime(long id, String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, ObjectForMessage field11, ObjectForMessage field12, ObjectForMessage field13, LocalDateTime createdAt) {
        super(id, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13);
        this.createdAt = createdAt;
    }
}
