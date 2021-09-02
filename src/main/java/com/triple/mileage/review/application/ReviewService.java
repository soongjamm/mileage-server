package com.triple.mileage.review.application;

import com.triple.mileage.common.ReviewOutbox;
import com.triple.mileage.review.domain.Review;
import com.triple.mileage.review.domain.ReviewRepository;
import com.triple.mileage.review.infra.ReviewCreated;
import com.triple.mileage.review.infra.ReviewDeleted;
import com.triple.mileage.review.infra.ReviewEventPublisher;
import com.triple.mileage.review.infra.ReviewModified;
import com.triple.mileage.review.interfaces.ReviewRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ReviewService {

	private final ReviewRepository reviewRepository;
	private final ReviewValidator reviewValidator;
	private final ReviewEventPublisher reviewEventPublisher;

	@Transactional
	public Review addReview(ReviewRequest reviewRequest) {
		reviewValidator.validate(reviewRequest);
		Review review = reviewRequest.toEntity();
		reviewRepository.save(review);

		reviewEventPublisher.publish(ReviewOutbox.builder()
				.reviewId(review.getReviewId())
				.action(ReviewAction.ADD)
				.payload(new ReviewCreated(review.getReviewId()).payload())
				.build());
		return review;
	}

	@Transactional
	public Review modifyReview(ReviewRequest reviewRequest) {
		reviewValidator.validate(reviewRequest);
		Review original = reviewRepository.findById(reviewRequest.getReviewId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 리뷰입니다."));
		Review updated = original.update(reviewRequest.getContent(), reviewRequest.getAttachedPhotoIds());
		reviewRepository.save(updated);
		reviewEventPublisher.publish(ReviewOutbox.builder()
				.reviewId(updated.getReviewId())
				.action(ReviewAction.MOD)
				.payload(new ReviewModified(original.getReviewId(), updated.getReviewId()).payload())
				.build());
		return updated;
	}

	@Transactional
	public void deleteReview(ReviewRequest reviewRequest) {
		UUID reviewId = reviewRequest.getReviewId();
		Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 리뷰입니다."));
		review.delete();

		reviewEventPublisher.publish(ReviewOutbox.builder()
				.reviewId(review.getReviewId())
				.action(ReviewAction.DELETE)
				.payload(new ReviewDeleted(reviewRequest.getReviewId()).payload())
				.build());
	}
}
