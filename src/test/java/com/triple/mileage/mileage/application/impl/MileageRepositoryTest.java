package com.triple.mileage.mileage.application.impl;

import com.triple.mileage.mileage.domain.MileageLog;
import com.triple.mileage.mileage.domain.MileageRepository;
import com.triple.mileage.review.domain.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.UUID;

import static com.triple.mileage.review.TestData.review;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MileageRepositoryTest {

	@Autowired
	private MileageRepository mileageRepository;
	private DeleteReviewPolicy policy;
	private Review review = review().build();

	@BeforeEach
	void setup() {
		 policy = new DeleteReviewPolicy(mileageRepository);
	}

	@Test
	@DisplayName("총 3점을 적립하고 1점이 회수되었을 때, 해당 리뷰로 얻은 마일리지는 2점이므로 2점을 차감한다. 따라서 -2점을 기록한다.")
	void sum_by_review() {
		//given
		MileageLog log1 = new MileageLog(1, review.getUserId(), review.getReviewId(), review.getOriginReviewId(), "first review");
		MileageLog log2 = new MileageLog(1, review.getUserId(), review.getReviewId(), review.getOriginReviewId(), "content included");
		MileageLog log3 = new MileageLog(1, review.getUserId(), review.getReviewId(), review.getOriginReviewId(), "photo included");
		MileageLog log4 = new MileageLog(-1, review.getUserId(), review.getReviewId(), review.getOriginReviewId(), "content deleted");
		mileageRepository.saveAll(List.of(log1, log2, log3, log4));
		//when
		MileageLog apply = policy.apply(review);
		//then
		assertThat(apply.getAmount()).isEqualTo(-2);
	}

	@Test
	@DisplayName("총 3점을 적립하고 1점이 회수되었을 때, 해당 리뷰로 얻은 마일리지는 2점이므로 2점을 차감한다. 따라서 -2점을 기록한다.")
	void sum_by_user() {
		//given
		MileageLog log1 = new MileageLog(1, review.getUserId(), UUID.randomUUID(), UUID.randomUUID(), "first review");
		MileageLog log2 = new MileageLog(1, review.getUserId(), UUID.randomUUID(), UUID.randomUUID(), "content included");
		MileageLog log3 = new MileageLog(1, review.getUserId(), UUID.randomUUID(), UUID.randomUUID(), "photo included");
		MileageLog log4 = new MileageLog(-1, review.getUserId(), UUID.randomUUID(), UUID.randomUUID(), "content deleted");
		mileageRepository.saveAll(List.of(log1, log2, log3, log4));

		//when
		int sum = mileageRepository.getSumByUser(review.getUserId());
		//then
		assertThat(sum).isEqualTo(2);
	}
}
