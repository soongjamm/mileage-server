package com.triple.mileage.mileage.application.impl;

import com.triple.mileage.place.Place;
import com.triple.mileage.review.application.ReviewOutbox;
import com.triple.mileage.mileage.domain.MileageRepository;
import com.triple.mileage.place.PlaceRepository;
import com.triple.mileage.review.application.ReviewAction;
import com.triple.mileage.review.domain.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.triple.mileage.review.TestData.review;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class DeleteReviewPolicyTest {

	@Mock
	private MileageRepository mileageRepository;
	@Mock
	private PlaceRepository placeRepository;
	private DeleteReviewPolicy policy;

	@BeforeEach
	void setup() {
		policy = new DeleteReviewPolicy(mileageRepository, placeRepository);
	}

	@Test
	void if_action_DELETE_then_true() {
		//given
		Review review = review().build();
		ReviewOutbox outbox = ReviewOutbox.builder().reviewId(review.getReviewId()).action(ReviewAction.DELETE).payload("").build();
		given(placeRepository.findById(review.getPlaceId())).willReturn(Optional.of(new Place(review.getPlaceId())));
		//when
		boolean satisfied = policy.isSatisfied(outbox, review);
		//then
		assertThat(satisfied).isTrue();
	}

	@Test
	void if_action_ADD_then_false() {
		//given
		Review review = review().build();
		ReviewOutbox outbox = ReviewOutbox.builder().reviewId(review.getReviewId()).action(ReviewAction.ADD).payload("").build();
		//when
		boolean satisfied = policy.isSatisfied(outbox, review);
		//then
		assertThat(satisfied).isFalse();
	}

	@Test
	void if_action_MOD_then_false() {
		//given
		Review review = review().build();
		ReviewOutbox outbox = ReviewOutbox.builder().reviewId(review.getReviewId()).action(ReviewAction.MOD).payload("").build();
		//when
		boolean satisfied = policy.isSatisfied(outbox, review);
		//then
		assertThat(satisfied).isFalse();
	}
}
