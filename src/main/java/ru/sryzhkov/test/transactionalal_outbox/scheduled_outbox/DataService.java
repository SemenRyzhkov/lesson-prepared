package ru.sryzhkov.test.transactionalal_outbox.scheduled_outbox;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DataService {

    private final OutboxRepository outboxRepository;
    private final DataRepository dataRepository;


    @Transactional
    public void saveDataWithEvent(Data data, String topic) {
        dataRepository.save(data);

        OutboxMessage message = OutboxMessage.builder()
                .status("SUCCESS")
                .payload(data.toString())
                .topic(topic)
                .build();
        outboxRepository.save(message);
    }
}