package com.triple.mileage.mileage.application.impl;

import com.triple.mileage.common.ReviewOutbox;
import com.triple.mileage.review.application.ReviewAction;
import com.triple.mileage.review.infra.ReviewModified;
import com.triple.mileage.review.domain.Review;
import com.triple.mileage.review.domain.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.triple.mileage.review.TestData.review;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AttachedPhotoDeletionPolicyTest {

	@Mock
	private ReviewRepository reviewRepository;
	private AttachedPhotoDeletionPolicy policy;

	@BeforeEach
	void setup() {
		policy = new AttachedPhotoDeletionPolicy(reviewRepository);
	}

	@Test
	void if_action_DELETE_then_false() {
		//given
		Review review = review().build();
		ReviewOutbox outbox = ReviewOutbox.builder().reviewId(review.getReviewId()).action(ReviewAction.DELETE).payload("").build();
		//when
		boolean satisfied = policy.isSatisfied(outbox, review);
		//then
		assertThat(satisfied).isFalse();
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
	void if_action_MOD_and_has_photo_then_false() {
		//given
		Review review = review().attachedPhotoIds(List.of(UUID.randomUUID())).build();
		ReviewOutbox outbox = ReviewOutbox.builder().reviewId(review.getReviewId()).action(ReviewAction.MOD).payload("").build();
		//when
		boolean satisfied = policy.isSatisfied(outbox, review);
		//then
		assertThat(satisfied).isFalse();
	}

	@Test
	void if_action_MOD_and_has_no_photo_and_had_no_photo_before_then_false() {
		//given
		UUID originalId = UUID.randomUUID();
		Review original = review().reviewId(originalId).attachedPhotoIds(Collections.emptyList()).build();
		Review modified = review().attachedPhotoIds(Collections.emptyList()).build();
		ReviewModified reviewModified = new ReviewModified(originalId, modified.getReviewId());
		ReviewOutbox outbox = ReviewOutbox.builder().reviewId(modified.getReviewId()).action(ReviewAction.MOD).payload(reviewModified.payload()).build();
		given(reviewRepository.findById(originalId)).willReturn(Optional.of(original));

		//when
		boolean satisfied = policy.isSatisfied(outbox, modified);
		//then
		assertThat(satisfied).isFalse();
	}

	@Test
	void if_action_MOD_and_has_no_photo_and_had_photo_before_then_true() {
		//given
		UUID originalId = UUID.randomUUID();
		Review original = review().reviewId(originalId).attachedPhotoIds(List.of(UUID.randomUUID())).build();
		Review modified = review().attachedPhotoIds(Collections.emptyList()).build();
		ReviewModified reviewModified = new ReviewModified(originalId, modified.getReviewId());
		ReviewOutbox outbox = ReviewOutbox.builder().reviewId(modified.getReviewId()).action(ReviewAction.MOD).payload(reviewModified.payload()).build();
		given(reviewRepository.findById(originalId)).willReturn(Optional.of(original));

		//when
		boolean satisfied = policy.isSatisfied(outbox, modified);
		//then
		assertThat(satisfied).isTrue();
	}
}