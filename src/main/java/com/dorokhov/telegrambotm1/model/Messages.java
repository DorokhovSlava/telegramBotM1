package com.dorokhov.telegrambotm1.model;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "messages")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Messages {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text_message")
    private String textMessage;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "message_date")
    private Timestamp messageDate;

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", text message='" + textMessage + '\'' +
                ", user name='" + userName + '\'' +
                ", message date='" + messageDate + '\'' +
                '}';
    }
}
