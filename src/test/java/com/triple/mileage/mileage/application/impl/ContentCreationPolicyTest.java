package com.triple.mileage.mileage.application.impl;

import com.triple.mileage.review.application.ReviewOutbox;
import com.triple.mileage.review.application.ReviewAction;
import com.triple.mileage.review.infra.ReviewModified;
import com.triple.mileage.review.domain.Review;
import com.triple.mileage.review.domain.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static com.triple.mileage.review.TestData.review;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ContentCreationPolicyTest {

	@Mock
	private ReviewRepository reviewRepository;
	private ContentCreationPolicy policy;

	@BeforeEach
	void setup() {
		policy = new ContentCreationPolicy(reviewRepository);
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
	void if_action_ADD_and_has_content_then_true() {
		//given
		Review review = review().build();
		ReviewOutbox outbox = ReviewOutbox.builder().reviewId(review.getReviewId()).action(ReviewAction.ADD).payload("").build();
		//when
		boolean satisfied = policy.isSatisfied(outbox, review);
		//then
		assertThat(satisfied).isTrue();
	}

	@Test
	void if_action_ADD_and_has_no_content_then_false() {
		//given
		Review review = review().content("").build();
		ReviewOutbox outbox = ReviewOutbox.builder().reviewId(review.getReviewId()).action(ReviewAction.ADD).payload("").build();
		//when
		boolean satisfied = policy.isSatisfied(outbox, review);
		//then
		assertThat(satisfied).isFalse();
	}

	@Test
	void if_action_MOD_and_has_content_and_not_exists_before_then_true() {
		//given
		UUID originalId = UUID.randomUUID();
		Review original = review().reviewId(originalId).content("").build();
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
	void if_action_MOD_and_has_no_content_then_false() {
		//given
		Review review = review().content("").build();
		ReviewOutbox outbox = ReviewOutbox.builder().reviewId(review.getReviewId()).action(ReviewAction.MOD).payload("").build();
		//when
		boolean satisfied = policy.isSatisfied(outbox, review);
		//then
		assertThat(satisfied).isFalse();
	}

	@Test
	void if_action_MOD_and_has_content_but_was_already_exists_then_false() {
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