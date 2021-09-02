package com.triple.mileage.mileage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MileageRepository extends JpaRepository<MileageLog, UUID> {
}
