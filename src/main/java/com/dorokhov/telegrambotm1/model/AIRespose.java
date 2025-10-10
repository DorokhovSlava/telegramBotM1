package com.dorokhov.telegrambotm1.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "ai_response")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Builder
public class AIRespose {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "text_resp")
    private String textResponse;

    @Column(name = "text_request")
    private String textRequest;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "resp_date")
    private Timestamp responseDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Override
    public String toString() {
        return " \n Messages{" +
                "id=" + id +
                ", textResponse='" + textResponse + '\'' +
                ", textRequest='" + textRequest + '\'' +
                ", userName='" + userName + '\'' +
                ", messageDate=" + responseDate + '\'' +
                '}';
    }

}
