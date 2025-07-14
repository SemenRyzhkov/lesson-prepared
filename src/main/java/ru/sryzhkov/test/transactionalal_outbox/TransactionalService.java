package ru.sryzhkov.test.transactionalal_outbox;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.jdbc.core.JdbcTemplate;

@Service
public class TransactionalService {

    private final JdbcTemplate jdbcTemplate;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public TransactionalService(JdbcTemplate jdbcTemplate, KafkaTemplate<String, String> kafkaTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    public void saveToDbAndKafka(String data, String topic) {
        // Сохраняем данные в базу
        jdbcTemplate.update("INSERT INTO your_table (data) VALUES (?)", data);

        // Отправляем сообщение в Kafka
        kafkaTemplate.send(topic, data)
                .addCallback(
                        result -> {},
                        ex -> { throw new RuntimeException("Failed to send message to Kafka", ex); }
                );

        // Если произойдет ошибка после этого места, оба действия откатятся
    }
}