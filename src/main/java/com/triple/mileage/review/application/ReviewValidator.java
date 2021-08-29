package com.triple.mileage.review.application;

import com.triple.mileage.place.PlaceRepository;
import com.triple.mileage.review.domain.ReviewRepository;
import com.triple.mileage.review.interfaces.ReviewedEvent;
import com.triple.mileage.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ReviewValidator {

	private final ReviewRepository reviewRepository;
	private final UserRepository userRepository;
	private final PlaceRepository placeRepository;

	public void validate(ReviewedEvent event) {
		boolean duplicate = reviewRepository.existsReviewByUserIdAndPlaceId(event.getUserId(), event.getPlaceId());
		if (isReviewEmpty(event)) {
			throw new IllegalArgumentException("리븊 내용이 존재하지 않습니다.");
		}
		if (duplicate) {
			throw new IllegalStateException("이미 해당 지역에 리뷰가 존재합니다.");
		}
		if (!userRepository.existsById(event.getUserId())) {
			throw new IllegalArgumentException("유저가 존재하지 않습니다.");
		}
		if (!placeRepository.existsById(event.getPlaceId())) {
			throw new IllegalArgumentException("장소가 존재하지 않습니다.");
		}
	}

	private boolean isReviewEmpty(ReviewedEvent event) {
		return event.getAttachedPhotoIds().size() == 0 && event.getContent().length() == 0;
	}
}
