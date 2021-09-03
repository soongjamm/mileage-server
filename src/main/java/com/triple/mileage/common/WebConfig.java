package com.triple.mileage.common;

import com.triple.mileage.review.application.ReviewOutbox;
import com.triple.mileage.review.infra.KafkaMileageClient;
import com.triple.mileage.review.infra.MileageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class WebConfig {

	@Autowired
	private KafkaTemplate<String, ReviewOutbox> kafkaTemplate;


}
