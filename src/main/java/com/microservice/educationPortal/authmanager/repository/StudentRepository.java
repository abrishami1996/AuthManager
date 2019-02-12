package com.microservice.educationPortal.authmanager.repository;

import com.microservice.educationPortal.authmanager.Model.*;
import com.microservice.educationPortal.authmanager.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    boolean existsByStudentNumber(long studentNumber);
    boolean existsById(int ID);
    @Transactional
    void deleteByStudentNumber(long StudentNumber);
    void deleteById(int ID);
    Student findById(int fk);

}
