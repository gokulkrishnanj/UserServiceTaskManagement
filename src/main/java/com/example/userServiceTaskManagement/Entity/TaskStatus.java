package com.example.userServiceTaskManagement.Entity;

import com.example.userServiceTaskManagement.Constants.StatusName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String statusId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusName statusName;
}
