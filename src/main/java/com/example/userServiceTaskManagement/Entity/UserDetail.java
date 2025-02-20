package com.example.userServiceTaskManagement.Entity;


import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "UserDetail") //@Table annotation is not mandatory it wil automatically use class name as table name
@Data
public class UserDetail implements Serializable {

    @Id
    @Column(name = "userId") //@Column annotation is not mandatory it wil automatically use fieldName as column name
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userId;

    @Column(name = "userName")
    private String userName;

    @Column(name = "userMailId")
    private String userMailId;

    @Column(name = "password")
    private String password;
}
