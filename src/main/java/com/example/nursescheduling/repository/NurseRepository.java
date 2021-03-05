package com.example.nursescheduling.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.nursescheduling.model.Nurse;

@Repository
public interface NurseRepository extends JpaRepository<Nurse,Long> {
	
	@Transactional
	@Modifying
	@Query(value="DELETE FROM nurses WHERE id<(SELECT * FROM (SELECT MAX(id) FROM nurses) as t)-9",nativeQuery=true) void deletePreviousRecords();
	
	

}

