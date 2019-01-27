package com.authenticationmanager.authmanager.repository;

import com.authenticationmanager.authmanager.Model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface ProfessorRepository extends JpaRepository<Professor, Integer> {
    boolean existsByProfessorNumber(long professor);
    boolean existsById(int ID);
    @Transactional
    void deleteByProfessorNumber(long professornumber);
    void deleteById(int ID);
}
