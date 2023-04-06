package com.dorokhov.telegrambotm1.repository;

import com.dorokhov.telegrambotm1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByChatId(Long chatId);

    @Modifying
    @Query(value = "delete User u WHERE u.chatId =: chatId")
    void deleteUserByChatId(@Param("chatId") Long chatId);

}
