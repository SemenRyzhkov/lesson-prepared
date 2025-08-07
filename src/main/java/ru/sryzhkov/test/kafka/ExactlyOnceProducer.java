package ru.sryzhkov.test.kafka;
import org.apache.kafka.clients.producer.*;

import java.util.Properties;
import java.util.UUID;

public class ExactlyOnceProducer {
    private final KafkaProducer<String, String> producer;
    private final String topic;

    public ExactlyOnceProducer(String bootstrapServers, String topic) {
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("acks", "all"); // Ждем подтверждения от всех реплик
        props.put("retries", 3);  // Повторные попытки при ошибках
        this.producer = new KafkaProducer<>(props);
        this.topic = topic;
    }

    public void send(String key, String value) {
        // Генерируем уникальный ID сообщения (можно использовать внешний сервис)
        String messageId = UUID.randomUUID().toString();

        // Добавляем messageId в заголовок (или в ключ/значение)
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);
        record.headers().add("message_id", messageId.getBytes());

        producer.send(record, (metadata, exception) -> {
            if (exception != null) {
                System.err.println("Ошибка отправки: " + exception.getMessage());
            } else {
                System.out.println("Сообщение отправлено: " + metadata.offset());
            }
        });
    }

    public void close() {
        producer.close();
    }
}