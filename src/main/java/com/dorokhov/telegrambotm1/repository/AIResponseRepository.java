package com.dorokhov.telegrambotm1.repository;

import com.dorokhov.telegrambotm1.model.AIResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AIResponseRepository extends JpaRepository<AIResponse, Long> {

}
