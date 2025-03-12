package com.example.userServiceTaskManagement.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "task")
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String taskId;

    @Column(nullable = false, length = 20)
    private String taskName;

    @Column(name = "taskDescription")
    private String taskDescription;

    @JoinColumn(name = "studentId", nullable = false)
    @ManyToOne
    private Student student;

    @JoinColumn(name = "statusId", nullable = false)
    @ManyToOne
    private TaskStatus taskStatusId;

    @JoinColumn(name = "subjectId", nullable = false)
    @ManyToOne
    private Subject subject;
}
