package com.example.ordernotificationsystem.entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderEventAuditRepository
extends JpaRepository<OrderEventAudit, UUID> {
}
