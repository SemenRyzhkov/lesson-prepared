package ru.sryzhkov.test.transactionalal_outbox.scheduled_outbox;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

public interface OutboxRepository extends JpaRepository<OutboxMessage, UUID> {

    @Transactional(readOnly = true)
    @Query("SELECT m FROM OutboxMessage m WHERE m.status = 'PENDING' ORDER BY m.createdAt")
    List<OutboxMessage> findPendingMessages();

    @Modifying
    @Transactional
    @Query("DELETE FROM OutboxMessage m WHERE m.id = ?1")
    void deleteById(UUID id);

    @Modifying
    @Transactional
    @Query("UPDATE OutboxMessage m SET m.status = 'FAILED', m.attempts = m.attempts + 1 WHERE m.id = ?1")
    void markAsFailed(UUID id);


}
