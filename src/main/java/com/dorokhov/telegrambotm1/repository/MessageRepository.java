package com.dorokhov.telegrambotm1.repository;

import com.dorokhov.telegrambotm1.model.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Messages, Long> {

    //List<Messages> getMessagesByUserName(String userName);

}
