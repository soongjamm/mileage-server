package com.triple.mileage.review.application;

import com.triple.mileage.place.PlaceRepository;
import com.triple.mileage.review.domain.ReviewRepository;
import com.triple.mileage.review.interfaces.ReviewRequest;
import com.triple.mileage.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;

import static com.triple.mileage.review.TestData.review;
import static com.triple.mileage.review.TestData.reviewRequest;
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
		ReviewRequest reviewRequest = reviewRequest()
				.action(ReviewAction.ADD)
				.content("")
				.attachedPhotoIds(Collections.emptyList())
				.build();

		//when//then
		assertThrows(IllegalArgumentException.class, () -> reviewValidator.validate(reviewRequest));
	}

	@Test
	void if_already_have_reviewed_the_place_throw_exception() {
		//given
		ReviewRequest reviewRequest = reviewRequest().action(ReviewAction.ADD).build();
		given(reviewRepository.existsReview(reviewRequest.getUserId(), reviewRequest.getPlaceId(),
				PageRequest.of(0, 1, Sort.by("createdDate").descending())))
				.willReturn(List.of(review().build()));

		//when//then
		assertThrows(IllegalStateException.class, () -> reviewValidator.validate(reviewRequest));
	}

	@Test
	void if_user_not_exists_throw_exception() {
		//given
		ReviewRequest reviewRequest = reviewRequest().action(ReviewAction.ADD).build();
		given(userRepository.existsById(reviewRequest.getUserId())).willReturn(false);

		//when//then
		assertThrows(IllegalArgumentException.class, () -> reviewValidator.validate(reviewRequest));
	}

	@Test
	void if_place_not_exists_throw_exception() {
		//given
		ReviewRequest reviewRequest = reviewRequest().action(ReviewAction.ADD).build();
		given(userRepository.existsById(reviewRequest.getUserId())).willReturn(true);
		given(placeRepository.existsById(reviewRequest.getPlaceId())).willReturn(false);

		//when//then
		assertThrows(IllegalArgumentException.class, () -> reviewValidator.validate(reviewRequest));
	}
}