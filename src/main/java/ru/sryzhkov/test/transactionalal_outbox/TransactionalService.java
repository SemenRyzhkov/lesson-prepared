package ru.sryzhkov.test.transactionalal_outbox;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.jdbc.core.JdbcTemplate;


//Как это работает
//Что не так?
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
        jdbcTemplate.update("INSERT INTO your_table (data) VALUES (?)", data);
        kafkaTemplate.send(topic, data);

    }
}




