package ru.sryzhkov.test.transactionalal_outbox.scheduled_outbox;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "orders")
public class Data {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String name;
}