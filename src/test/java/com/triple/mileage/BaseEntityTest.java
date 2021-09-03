package com.triple.mileage;

import com.triple.mileage.mileage.domain.MileageLog;
import com.triple.mileage.mileage.domain.MileageRepository;
import com.triple.mileage.review.domain.Review;
import com.triple.mileage.review.domain.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static com.triple.mileage.review.TestData.review;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BaseEntityTest {

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private MileageRepository mileageRepository;

	@Test
	void created_date_test() {
	    //given
		Review review = review().build();
		assertThat(review.getCreatedDate() == null);

		//when
		reviewRepository.save(review);

		//then
		assertThat(review.getCreatedDate()).isNotNull();
	}

	@Test
	void created_date_test2() {
		//given
		MileageLog log = new MileageLog(1, UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), "");
		assertThat(log.getCreatedDate() == null);

		//when
		mileageRepository.save(log);

		//then
		assertThat(log.getCreatedDate()).isNotNull();
	}
}
