package ru.sryzhkov.test.transactionalal_outbox.scheduled_outbox;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface DataRepository extends JpaRepository<Data, UUID> {
}
