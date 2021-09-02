package com.triple.mileage.mileage.application;

import com.triple.mileage.common.ReviewOutbox;
import com.triple.mileage.mileage.domain.MileageLog;
import com.triple.mileage.mileage.domain.MileageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MileageManagementService {

	private final MileageRepository mileageRepository;
	private final MileageCalculator mileageCalculator;

	@Transactional
	public void handle(ReviewOutbox outbox) {
		List<MileageLog> mileages = mileageCalculator.calculate(outbox);
		try {
			mileageRepository.saveAll(mileages);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}
}
