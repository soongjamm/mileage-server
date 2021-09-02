package com.triple.mileage.review.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {
	boolean existsReviewByUserIdAndPlaceId(UUID userId, UUID placeId);

	@Modifying(clearAutomatically = true)
	@Query("delete from Review r where r.originReviewId = :reviewId")
	void deleteAllByOriginId(UUID reviewId);
}
