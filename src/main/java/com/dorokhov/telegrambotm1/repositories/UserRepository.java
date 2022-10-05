package com.dorokhov.telegrambotm1.repositories;

import com.dorokhov.telegrambotm1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
