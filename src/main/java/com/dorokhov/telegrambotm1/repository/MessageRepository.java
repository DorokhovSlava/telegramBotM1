package com.dorokhov.telegrambotm1.repository;

import com.dorokhov.telegrambotm1.model.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Messages, Long> {

    @Query("SELECT msg FROM Messages msg WHERE msg.userName=:userName")
    List<Messages> findAllByName(String userName);

    @Query("SELECT msg FROM Messages msg WHERE msg.textMessage=:msgText")
    List<Messages> findAllByText(String msgText);

    @Modifying
    @Transactional
    @Query("DELETE FROM Messages msg WHERE msg.userName=:userName")
    void deleteAllByName(String userName);



}
