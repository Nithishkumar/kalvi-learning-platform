package com.example.kalvi.repository;

import com.example.kalvi.entity.StudentProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentProgressRepository extends JpaRepository<StudentProgress,Long> {
    List<StudentProgress> findByStudentId(Long studentId);
}
