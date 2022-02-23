package com.example.shortlinks.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "user_statuses")
@Setter
@Getter
public class UserStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "status_name")
    private String status;

    @OneToMany(mappedBy = "status", fetch = FetchType.EAGER)
    private List<User> users;

    public UserStatus() {
    }

    public UserStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserStatus{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", users= " + Arrays.toString(users.stream().map(User::getUsername).toArray()) +
                '}';
    }
}
