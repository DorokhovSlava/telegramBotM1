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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "id")
    private Long id;

    @Column(name = "text_message")
    private String textMessage;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "message_date")
    private Timestamp messageDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_chat_id", referencedColumnName = "chat_id")
    private User user;

    @Override
    public String toString() {
        return "Messages{" +
                "id=" + id +
                ", textMessage='" + textMessage + '\'' +
                ", userName='" + userName + '\'' +
                ", messageDate=" + messageDate +
                ", user=" + user +
                '}';
    }
}
