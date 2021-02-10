package com.example.nursescheduling.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.nursescheduling.model.FrontEndData;



@Repository
public interface FrontEndDataRepository extends JpaRepository<FrontEndData,Long>{

}
