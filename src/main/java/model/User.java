package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
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

    public Long getChatId() {
        return chatId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public Timestamp getRegisteredAt() {
        return registeredAt;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setRegisteredAt(Timestamp registeredAt) {
        this.registeredAt = registeredAt;
    }
}
