package com.triple.mileage.mileage.application.impl;

import com.triple.mileage.common.ReviewOutbox;
import com.triple.mileage.place.Place;
import com.triple.mileage.place.PlaceRepository;
import com.triple.mileage.review.application.ReviewAction;
import com.triple.mileage.review.domain.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static com.triple.mileage.review.TestData.review;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class FirstReviewOfPlacePolicyTest {

	@Mock
	private PlaceRepository placeRepository;
	private FirstReviewOfPlacePolicy policy;

	@BeforeEach
	void setup() {
		policy = new FirstReviewOfPlacePolicy(placeRepository);
	}

	@Test
	void if_action_is_ADD_and_the_place_has_no_review_then_true() {
		//given
		Review review = review().attachedPhotoIds(Collections.emptyList()).build();
		Place place = new Place(review.getPlaceId());
		ReviewOutbox outbox = ReviewOutbox.builder().reviewId(review.getReviewId()).action(ReviewAction.ADD).payload("").build();
		given(placeRepository.findById(review.getPlaceId())).willReturn(Optional.of(place));
		//when
		boolean satisfied = policy.isSatisfied(outbox, review);
		//then
		assertThat(satisfied).isTrue();
	}

	@Test
	void if_action_is_ADD_and_the_place_has_review_then_false() {
		//given
		Review review = review().attachedPhotoIds(Collections.emptyList()).build();
		Place place = new Place(review.getPlaceId());
		place.reviewed();
		ReviewOutbox outbox = ReviewOutbox.builder().reviewId(review.getReviewId()).action(ReviewAction.ADD).payload("").build();
		given(placeRepository.findById(review.getPlaceId())).willReturn(Optional.of(place));
		//when
		boolean satisfied = policy.isSatisfied(outbox, review);
		//then
		assertThat(satisfied).isFalse();
	}

	@Test 
	void if_action_is_MOD_then_false() {
		//given
		Review review = review().attachedPhotoIds(Collections.emptyList()).build();
		ReviewOutbox outbox = ReviewOutbox.builder().reviewId(review.getReviewId()).action(ReviewAction.MOD).payload("").build();
		//when
		boolean satisfied = policy.isSatisfied(outbox, review);
		//then
		assertThat(satisfied).isFalse();
	}

	@Test
	void if_action_is_DELETE_then_false() {
		//given
		Review review = review().attachedPhotoIds(Collections.emptyList()).build();
		ReviewOutbox outbox = ReviewOutbox.builder().reviewId(review.getReviewId()).action(ReviewAction.DELETE).payload("").build();
		//when
		boolean satisfied = policy.isSatisfied(outbox, review);
		//then
		assertThat(satisfied).isFalse();
	}

}