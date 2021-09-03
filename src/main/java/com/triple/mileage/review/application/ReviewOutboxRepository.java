package com.triple.mileage.review.application;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReviewOutboxRepository extends JpaRepository<ReviewOutbox, UUID> {
}
