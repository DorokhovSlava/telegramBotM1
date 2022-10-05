package com.dorokhov.telegrambotm1.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    //@Column(name = "chatId")
    private Long chatId;
    //@Column(name = "firstName")
    private String firstName;
    //@Column(name = "lastName")
    private String lastName;
    //@Column(name = "userName")
    private String userName;
    //@Column(name = "userRegistered")
    private Timestamp registeredAt;

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", chatId=" + chatId +
                ", registeredAt=" + registeredAt +
                '}';
    }

}
