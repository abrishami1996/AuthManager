package com.authenticationmanager.authmanager.repository;

import com.authenticationmanager.authmanager.Model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface FacultyEmployeeRepository extends JpaRepository<FacultyEmployee, Integer> {
    boolean existsByEmployeeNumber(long employeenumber);
    boolean existsById(int ID);
    @Transactional
    void deleteByEmployeeNumber(long employeenumber);
    void deleteById(int ID);
}
