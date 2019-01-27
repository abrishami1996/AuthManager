package com.authenticationmanager.authmanager.repository;

import com.authenticationmanager.authmanager.Model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    boolean existsByStudentNumber(long studentNumber);
    boolean existsById(int ID);
    @Transactional
    void deleteByStudentNumber(long StudentNumber);
    void deleteById(int ID);

}
