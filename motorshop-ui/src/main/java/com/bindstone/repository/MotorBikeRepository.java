package com.bindstone.repository;

import com.bindstone.domain.Motorbike;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

public interface MotorBikeRepository extends ReactiveSortingRepository<Motorbike, Long> {
}
