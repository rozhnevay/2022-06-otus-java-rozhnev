package ru.otus.listener.homework;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import ru.otus.listener.Listener;
import ru.otus.model.Message;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {

    private final Map<Long, Message> messageHistoryMap = new HashMap<>();

    @Override
    public void onUpdated(Message msg) {
        messageHistoryMap.put(msg.getId(), msg.copy());
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.of(messageHistoryMap.get(id));
    }
}
