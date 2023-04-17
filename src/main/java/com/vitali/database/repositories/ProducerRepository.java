package com.vitali.database.repositories;

import com.vitali.database.entities.Producer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProducerRepository extends JpaRepository<Producer, Integer> {
}
