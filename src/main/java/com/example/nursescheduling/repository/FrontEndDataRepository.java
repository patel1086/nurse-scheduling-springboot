package com.example.nursescheduling.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.nursescheduling.model.FrontEndData;



@Repository
public interface FrontEndDataRepository extends JpaRepository<FrontEndData,Long>{
	@Transactional
	@Modifying
	@Query(value="DELETE FROM nurse_front_end_data f where f.id<(select * from (select MAX(f.id) from nurse_front_end_data f) as t)",nativeQuery=true) void deletePreviousRecords();
}
