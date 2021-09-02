package com.triple.mileage.review.application;

import com.triple.mileage.review.domain.Review;
import com.triple.mileage.review.infra.ReviewHandledEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.triple.mileage.review.TestData.reviewRequest;

@DataJpaTest
class ReviewHandledEventMapperTest {

	@Autowired
	private ReviewHandledEventMapper mapper;

	@Test
	void even_if_simultaneously_post_first_review_only_one_is_accepted() {
		//given
		ExecutorService es = Executors.newFixedThreadPool(2);
		Review review = reviewRequest().build().toEntity();

		//when
		Future<ReviewHandledEvent> work1 = es.submit(() -> mapper.add(review));
		Future<ReviewHandledEvent> work2 = es.submit(() -> mapper.add(review));

		//then

	}
}