package com.triple.mileage.review.application;

import com.triple.mileage.common.ReviewOutbox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReviewEventPublisherTest {

	@Mock
	private ApplicationEventPublisher springPublisher;

	@Mock
	private ReviewOutboxRepository outboxRepository;
	private ReviewEventPublisher eventPublisher;

	@BeforeEach
	void setup() {
		eventPublisher = new ReviewEventPublisher(springPublisher, outboxRepository);
	}

	@Test
	void if_publish_spring_publishEvent_is_invoked() {
		//given
		ReviewOutbox outbox = new ReviewOutbox();
		//when
		eventPublisher.publish(outbox);
		//then
		verify(springPublisher).publishEvent(outbox);
	}
}