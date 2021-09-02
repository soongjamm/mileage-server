package com.triple.mileage.mileage.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MileageRepository extends JpaRepository<MileageLog, UUID> {

	@Query("select sum(m.amount) from MileageLog m where m.originReviewId = :originReviewId")
	int getSumByOriginReview(UUID originReviewId);

	@Query("select sum(m.amount) from MileageLog m where m.userId = :userId")
	int getSumByUser(UUID userId);
}
