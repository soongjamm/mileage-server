package com.triple.mileage.review.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {

	@Query("select r from Review r where r.userId = :userId and r.placeId = :placeId")
	List<Review> existsReview(UUID userId, UUID placeId, Pageable pageable);

	@Modifying(clearAutomatically = true)
	@Query("delete from Review r where r.originReviewId = :reviewId")
	void deleteAllByOriginId(UUID reviewId);
}
