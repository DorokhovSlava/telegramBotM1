package com.dorokhov.telegrambotm1.repository;

import com.dorokhov.telegrambotm1.model.AIRespose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AIResposeRepository extends JpaRepository<AIRespose, Long> {

}
