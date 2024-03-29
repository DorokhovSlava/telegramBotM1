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
@Builder
public class Messages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "msg_text")
    private String textMessage;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "msg_date")
    private Timestamp messageDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Override
    public String toString() {
        return " \n Messages{" +
                "id=" + id +
                ", textMessage='" + textMessage + '\'' +
                ", userName='" + userName + '\'' +
                ", messageDate=" + messageDate + '\'' +
                '}';
    }
}
