package com.example.userServiceTaskManagement.Repository;

import com.example.userServiceTaskManagement.Entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SubjectRepository extends JpaRepository<Subject, String> {
    Subject findBySubjectName(String subjectName);
}
