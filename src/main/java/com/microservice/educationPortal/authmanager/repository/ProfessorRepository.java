package com.microservice.educationPortal.authmanager.repository;

import com.microservice.educationPortal.authmanager.Model.*;
import com.microservice.educationPortal.authmanager.Model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface ProfessorRepository extends JpaRepository<Professor, Integer> {
    boolean existsByProfessorNumber(long professor);
    boolean existsById(int ID);
    @Transactional
    void deleteByProfessorNumber(long professornumber);
    void deleteById(int ID);
    Professor findById(int fk);
}
