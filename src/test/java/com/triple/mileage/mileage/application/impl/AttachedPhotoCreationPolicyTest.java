package com.triple.mileage.mileage.application.impl;

import com.triple.mileage.common.ReviewOutbox;
import com.triple.mileage.review.application.ReviewAction;
import com.triple.mileage.review.application.ReviewModified;
import com.triple.mileage.review.domain.Review;
import com.triple.mileage.review.domain.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static com.triple.mileage.review.TestData.review;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AttachedPhotoCreationPolicyTest {

	@Mock
	private ReviewRepository reviewRepository;
	private AttachedPhotoCreationPolicy policy;

	@BeforeEach
	void setup() {
		policy = new AttachedPhotoCreationPolicy(reviewRepository);
	}

	@Test
	void if_action_is_DELETE_then_false() {
		//given
		Review review = review().build();
		ReviewOutbox outbox = ReviewOutbox.builder().reviewId(review.getReviewId()).action(ReviewAction.DELETE).payload("").build();
		//when
		boolean satisfied = policy.isSatisfied(outbox, review);
		//then
		assertThat(satisfied).isFalse();
	}

	@Test
	void if_action_is_ADD_and_has_photo_then_true() {
		//given
		Review review = review().build();
		ReviewOutbox outbox = ReviewOutbox.builder().reviewId(review.getReviewId()).action(ReviewAction.ADD).payload("").build();
		//when
		boolean satisfied = policy.isSatisfied(outbox, review);
		//then
		assertThat(satisfied).isTrue();
	}

	@Test
	void if_action_is_ADD_and_has_no_photo_then_false() {
		//given
		Review review = review().attachedPhotoIds(Collections.emptyList()).build();
		ReviewOutbox outbox = ReviewOutbox.builder().reviewId(review.getReviewId()).action(ReviewAction.ADD).payload("").build();
		//when
		boolean satisfied = policy.isSatisfied(outbox, review);
		//then
		assertThat(satisfied).isFalse();
	}

	@Test
	void if_action_is_MOD_and_has_photo_and_was_not_exists_then_true() {
		//given
		UUID originalId = UUID.randomUUID();
		Review original = review().reviewId(originalId).attachedPhotoIds(Collections.emptyList()).build();
		Review modified = review().build();
		ReviewModified reviewModified = new ReviewModified(originalId, modified.getReviewId());
		ReviewOutbox outbox = ReviewOutbox.builder().reviewId(modified.getReviewId()).action(ReviewAction.MOD).payload(reviewModified.payload()).build();
		given(reviewRepository.findById(originalId)).willReturn(Optional.of(original));

		//when
		boolean satisfied = policy.isSatisfied(outbox, modified);
		//then
		assertThat(satisfied).isTrue();
	}

	@Test
	void if_action_is_MOD_and_has_no_photo_then_false() {
		//given
		Review review = review().attachedPhotoIds(Collections.emptyList()).build();
		ReviewOutbox outbox = ReviewOutbox.builder().reviewId(review.getReviewId()).action(ReviewAction.MOD).payload("").build();
		//when
		boolean satisfied = policy.isSatisfied(outbox, review);
		//then
		assertThat(satisfied).isFalse();
	}

	@Test
	void if_action_is_MOD_and_has_photo_but_was_already_exists_then_false() {
		//given
		UUID originalId = UUID.randomUUID();
		Review original = review().reviewId(originalId).build();
		Review modified = review().build();
		ReviewModified reviewModified = new ReviewModified(originalId, modified.getReviewId());
		ReviewOutbox outbox = ReviewOutbox.builder().reviewId(modified.getReviewId()).action(ReviewAction.MOD).payload(reviewModified.payload()).build();
		given(reviewRepository.findById(originalId)).willReturn(Optional.of(original));

		//when
		boolean satisfied = policy.isSatisfied(outbox, modified);
		//then
		assertThat(satisfied).isFalse();
	}
}