package com.triple.mileage.review.application;

import com.triple.mileage.place.PlaceRepository;
import com.triple.mileage.review.domain.ReviewRepository;
import com.triple.mileage.review.interfaces.ReviewedEvent;
import com.triple.mileage.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static com.triple.mileage.review.TestData.reviewedEvent;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ReviewValidatorTest {

	private ReviewValidator reviewValidator;

	@Mock
	private ReviewRepository reviewRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private PlaceRepository placeRepository;

	@BeforeEach
	void setup() {
		reviewValidator = new ReviewValidator(reviewRepository, userRepository, placeRepository);
	}

	@Test
	void if_review_content_and_photos_are_empty_throw_exception() {
		//given
		ReviewedEvent reviewedEvent = reviewedEvent()
				.action(ReviewedEvent.Action.ADD)
				.content("")
				.attachedPhotoIds(Collections.emptyList())
				.build();

		//when//then
		assertThrows(IllegalArgumentException.class, () -> reviewValidator.validate(reviewedEvent));
	}

	@Test
	void if_already_have_reviewed_the_place_throw_exception() {
		//given
		ReviewedEvent reviewedEvent = reviewedEvent().action(ReviewedEvent.Action.ADD).build();
		given(reviewRepository.existsReviewByUserIdAndPlaceId(reviewedEvent.getUserId(), reviewedEvent.getPlaceId())).willReturn(true);

		//when//then
		assertThrows(IllegalStateException.class, () -> reviewValidator.validate(reviewedEvent));
	}

	@Test
	void if_user_not_exists_throw_exception() {
		//given
		ReviewedEvent reviewedEvent = reviewedEvent().action(ReviewedEvent.Action.ADD).build();
		given(userRepository.existsById(reviewedEvent.getUserId())).willReturn(false);

		//when//then
		assertThrows(IllegalArgumentException.class, () -> reviewValidator.validate(reviewedEvent));
	}

	@Test
	void if_place_not_exists_throw_exception() {
		//given
		ReviewedEvent reviewedEvent = reviewedEvent().action(ReviewedEvent.Action.ADD).build();
		given(userRepository.existsById(reviewedEvent.getUserId())).willReturn(true);
		given(placeRepository.existsById(reviewedEvent.getPlaceId())).willReturn(false);

		//when//then
		assertThrows(IllegalArgumentException.class, () -> reviewValidator.validate(reviewedEvent));
	}
}