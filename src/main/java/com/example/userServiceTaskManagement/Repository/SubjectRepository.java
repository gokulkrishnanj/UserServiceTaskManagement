package com.example.userServiceTaskManagement.Repository;

import com.example.userServiceTaskManagement.Entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, String> {
    Subject findBySubjectName(String subjectName);
}
