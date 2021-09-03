package com.triple.mileage.review.infra;

import com.triple.mileage.common.ReviewOutbox;
import com.triple.mileage.review.application.ReviewOutboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Component
public class ReviewMessageRelay {

	private final MileageClient mileageRestClient;
	private final ReviewOutboxRepository reviewOutboxRepository;

	@Transactional
	@Scheduled(fixedDelay = 1000 * 3)
	public void readOutbox() {
		List<ReviewOutbox> unreadMessages = reviewOutboxRepository.findAll();
		mileageRestClient.requestMileage(unreadMessages);
		reviewOutboxRepository.deleteAll(unreadMessages);
	}
}
