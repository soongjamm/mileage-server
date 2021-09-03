package com.triple.mileage.review.application;

import com.triple.mileage.place.PlaceRepository;
import com.triple.mileage.review.domain.Review;
import com.triple.mileage.review.domain.ReviewRepository;
import com.triple.mileage.review.interfaces.ReviewRequest;
import com.triple.mileage.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ReviewValidator {

	private final ReviewRepository reviewRepository;
	private final UserRepository userRepository;
	private final PlaceRepository placeRepository;

	public void validate(ReviewRequest event) {
		if (event.getAction() == ReviewAction.ADD) {
			checkDuplication(event);
		}
		if (isReviewEmpty(event)) {
			throw new IllegalArgumentException("리뷰 내용이 존재하지 않습니다.");
		}
		if (!userRepository.existsById(event.getUserId())) {
			throw new IllegalArgumentException("유저가 존재하지 않습니다.");
		}
		if (!placeRepository.existsById(event.getPlaceId())) {
			throw new IllegalArgumentException("장소가 존재하지 않습니다.");
		}
	}

	private void checkDuplication(ReviewRequest event) {
		List<Review> reviews = reviewRepository.existsReview(
				event.getUserId(), event.getPlaceId(),
				PageRequest.of(0, 1, Sort.by("createdDate").descending()));
		if (reviews.size() > 0) {
			if (reviews.get(0).getReviewStatus() != Review.ReviewStatus.DELETED) {
				throw new IllegalStateException("이미 해당 지역에 리뷰가 존재합니다.");
			}
		}
	}

	private boolean isReviewEmpty(ReviewRequest event) {
		return event.getAttachedPhotoIds().size() == 0 && event.getContent().length() == 0;
	}
}
