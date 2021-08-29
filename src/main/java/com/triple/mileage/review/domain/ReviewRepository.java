package com.triple.mileage.review.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {
	boolean existsReviewsByUserIdAndPlaceId(UUID userId, UUID placeId);
}
