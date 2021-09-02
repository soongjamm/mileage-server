package com.triple.mileage.mileage.application;

import com.triple.mileage.common.ReviewOutbox;
import com.triple.mileage.mileage.domain.MileageLog;
import com.triple.mileage.mileage.domain.MileageRepository;
import com.triple.mileage.mileage.interfaces.CheckMileageTarget;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MileageManagementService {

	private final MileageRepository mileageRepository;
	private final MileageCalculator mileageCalculator;

	@Transactional
	public void handle(ReviewOutbox outbox) {
		List<MileageLog> mileages = mileageCalculator.calculate(outbox);
		mileageRepository.saveAll(mileages);
	}

	@Transactional(readOnly = true)
	public CheckMileageResponse checkMileage(CheckMileageTarget checkMileageTarget) {
		int sum = mileageRepository.getSumByUser(checkMileageTarget.getUserId());
		Page<MileageLog> logs = mileageRepository.findAll(checkMileageTarget.getPageable());
		return new CheckMileageResponse(sum, logs);
	}
}
