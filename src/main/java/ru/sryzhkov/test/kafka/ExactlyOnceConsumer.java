package ru.sryzhkov.test.kafka;

import org.apache.kafka.clients.consumer.*;
import java.sql.*;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

//Отключить автокоммит оффсетов.
//
//Проверять, было ли сообщение обработано ранее (через БД или кеш).
//
//Фиксировать оффсет только после успешной обработки.
//Откатываем транзакцию в БД при ошибке
public class ExactlyOnceConsumer {
    private final KafkaConsumer<String, String> consumer;
    private final Connection dbConnection;
    private final String topic;

    public ExactlyOnceConsumer(String bootstrapServers, String topic, String dbUrl, String dbUser, String dbPassword) throws SQLException {
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("group.id", "exactly-once-group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("enable.auto.commit", "false"); // Отключаем автокоммит!
        props.put("auto.offset.reset", "earliest"); // Начинает чтение с самого старого сообщения в партиции (с offset 0).
        this.consumer = new KafkaConsumer<>(props);
        this.topic = topic;
        this.dbConnection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        createDeduplicationTable(); // Создаем таблицу при старте
    }

    private void createDeduplicationTable() throws SQLException {
        try (Statement stmt = dbConnection.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS processed_messages (" +
                    "message_id VARCHAR(255) PRIMARY KEY," +
                    "processed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");
        }
    }

    //Используем JDBC-транзакции для согласованности БД и ручной коммит оффсета в Kafka.
    public void consume() {
        consumer.subscribe(Collections.singletonList(topic));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                try {
                    dbConnection.setAutoCommit(false); // Отключаем автокоммит
                    // Извлекаем message_id из заголовка (или ключа)
                    String messageId = new String(record.headers().lastHeader("message_id").value());

                    // Проверяем, не обрабатывалось ли сообщение ранее
                    if (!isMessageProcessed(messageId)) {
                        // Обработка сообщения (например, сохранение в БД)
                        processMessage(record.value());

                        // Фиксируем факт обработки
                        markMessageAsProcessed(messageId);

                        // Шаг 4: Коммитим транзакцию в БД
                        dbConnection.commit();

                        // Коммитим оффсет в Kafka (вручную)
                        consumer.commitSync();
                    }
                } catch (Exception e) {
                    try {
                        // Откатываем транзакцию в БД при ошибке
                        dbConnection.rollback();
                        System.err.println("Откат транзакции: " + e.getMessage());
                    } catch (SQLException ex) {
                        System.err.println("Ошибка при откате: " + ex.getMessage());
                    }
                }
            }
        }
    }

    private boolean isMessageProcessed(String messageId) throws SQLException {
        try (PreparedStatement stmt = dbConnection.prepareStatement(
                "SELECT 1 FROM processed_messages WHERE message_id = ?")) {
            stmt.setString(1, messageId);
            return stmt.executeQuery().next(); // true если сообщение уже обработано
        }
    }

    private void markMessageAsProcessed(String messageId) throws SQLException {
        try (PreparedStatement stmt = dbConnection.prepareStatement(
                "INSERT INTO processed_messages (message_id) VALUES (?)")) {
            stmt.setString(1, messageId);
            stmt.executeUpdate();
        }
    }

    private void processMessage(String message) {
        System.out.println("Обработка: " + message);
        // Здесь может быть бизнес-логика (например, запись в другую БД)
    }

    public void close() throws SQLException {
        consumer.close();
        dbConnection.close();
    }
}
