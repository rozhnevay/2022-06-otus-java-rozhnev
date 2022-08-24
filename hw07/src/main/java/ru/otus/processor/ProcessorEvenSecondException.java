package ru.otus.processor;

import java.time.LocalDateTime;
import ru.otus.exceptions.EvenSecondException;
import ru.otus.model.Message;
import ru.otus.model.MessageWithTime;

public class ProcessorEvenSecondException implements Processor {

    @Override
    public Message process(Message message) {
        if (message instanceof MessageWithTime &&
            ((MessageWithTime) message).getCreatedAt().getSecond() % 2 == 0) {
            throw new EvenSecondException();
        }
        return message;
    }
}
