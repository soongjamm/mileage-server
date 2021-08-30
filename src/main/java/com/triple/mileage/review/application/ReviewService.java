package com.triple.mileage.review.application;

import com.triple.mileage.review.domain.Review;
import com.triple.mileage.review.domain.ReviewRepository;
import com.triple.mileage.review.interfaces.ReviewedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class ReviewService {

	private final ReviewRepository reviewRepository;
	private final ReviewValidator reviewValidator;

	public Review postReview(ReviewedEvent event) {
		reviewValidator.validate(event);
		Review review = new Review(event.getReviewId(), event.getContent(), event.getAttachedPhotoIds(), event.getUserId(), event.getPlaceId());
		return reviewRepository.save(review);
	}

	@Transactional
	public void modifyReview(ReviewedEvent event) {
		Review review = reviewRepository.findById(event.getReviewId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 리뷰입니다."));
		review.update(event.getContent(), event.getAttachedPhotoIds());
	}

	public void deleteReview(ReviewedEvent event) {
		reviewRepository.deleteById(event.getReviewId());
	}
}
