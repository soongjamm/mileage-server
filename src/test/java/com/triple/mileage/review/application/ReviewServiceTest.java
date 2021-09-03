package com.triple.mileage.review.application;

import com.triple.mileage.review.domain.Review;
import com.triple.mileage.review.domain.ReviewRepository;
import com.triple.mileage.review.infra.ReviewEventPublisher;
import com.triple.mileage.review.interfaces.ReviewRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import static com.triple.mileage.review.TestData.reviewRequest;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

	private ReviewService reviewService;

	@Mock private ReviewRepository reviewRepository;
	@Mock private ReviewValidator reviewValidator;
	private ReviewEventPublisher reviewEventPublisher;

	@Mock private ApplicationEventPublisher appPublisher;
	@Mock(lenient = true) private ReviewOutboxRepository outboxRepository;

	@BeforeEach
	void setup() {
		reviewEventPublisher = new ReviewEventPublisher(appPublisher, outboxRepository);
		reviewService = new ReviewService(reviewRepository, reviewValidator, reviewEventPublisher);
	}

	@Test
	void if_review_is_added_review_is_saved() {
		//given
		ReviewRequest request = reviewRequest().build();
		Review created = request.toEntity();
		//when
		reviewService.addReview(request);
		//then
		verify(reviewRepository).save(created);
	}

	@Test
	void if_review_is_added_review_outbox_is_saved() {
		//given
		ReviewRequest request = reviewRequest().build();
		//when
		reviewService.addReview(request);
		//then
		verify(appPublisher).publishEvent(argThat((ArgumentMatcher<ReviewOutbox>) argument -> argument.getClass().isAssignableFrom(ReviewOutbox.class)));
	}
}