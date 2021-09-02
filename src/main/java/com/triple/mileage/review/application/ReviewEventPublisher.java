package com.triple.mileage.review.application;

import com.triple.mileage.common.ReviewOutbox;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReviewEventPublisher {

	private final ApplicationEventPublisher publisher;
	private final ReviewOutboxRepository reviewOutboxRepository;

	public void publish(ReviewOutbox outbox) {
		publisher.publishEvent(outbox);
	}

	@EventListener
	public void handle(ReviewOutbox outbox) {
		reviewOutboxRepository.save(outbox);
	}
}
