package com.triple.mileage.mileage;

import com.triple.mileage.common.ReviewOutbox;
import com.triple.mileage.place.Place;
import com.triple.mileage.place.PlaceRepository;
import com.triple.mileage.review.application.ReviewAction;
import com.triple.mileage.review.domain.Review;
import com.triple.mileage.review.domain.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class FirstReviewOfPlacePolicy implements MileagePolicy {

	private final PlaceRepository placeRepository;
	private final ReviewRepository reviewRepository;

	@Override
	public MileageLog apply(ReviewOutbox reviewOutbox) {
		return new MileageLog(1, reviewOutbox.getReviewId(), getReason());
	}

	@Override
	public boolean isSatisfied(ReviewOutbox reviewOutbox) {
		if (!(reviewOutbox.getAction() == ReviewAction.ADD)) {
			return false;
		}
		Review review = reviewRepository.findById(reviewOutbox.getReviewId()).orElseThrow();
		Place place = placeRepository.findById(review.getPlaceId()).orElseThrow();
		if (place.isReviewed()) {
			return false;
		}
		place.reviewed();
		return true;
	}

	@Override
	public String getReason() {
		return "First review of the place";
	}
}
