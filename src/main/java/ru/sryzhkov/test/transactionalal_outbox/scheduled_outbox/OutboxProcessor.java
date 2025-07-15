package ru.sryzhkov.test.transactionalal_outbox.scheduled_outbox;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Component
public class OutboxProcessor {

    private final OutboxRepository outboxRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public OutboxProcessor(OutboxRepository outboxRepository,
                           KafkaTemplate<String, String> kafkaTemplate) {
        this.outboxRepository = outboxRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void processOutbox() {
        List<OutboxMessage> messages = outboxRepository.findPendingMessages();

        messages.forEach(message -> {
            try {
                kafkaTemplate.send(message.getTopic(), message.getPayload());
                outboxRepository.deleteById(message.getId());
            } catch (Exception e) {
                outboxRepository.markAsFailed(message.getId());
            }
        });
    }
}