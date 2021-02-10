package com.example.nursescheduling.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.nursescheduling.model.Nurse;

@Repository
public interface NurseRepository extends JpaRepository<Nurse,Long> {

}

