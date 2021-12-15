package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("SELECT s FROM SCHEDULE s JOIN PET p WHERE p.id=:petId")
    List<Schedule> findAllByPetsId(@Param("petId") long petId);

    @Query("SELECT s FROM SCHEDULE s JOIN EMPLOYEE e WHERE e.id=:employeeId")
    List<Schedule> findAllByEmployeeId(@Param("employeeId") long employeeId);

    @Query("SELECT s FROM SCHEDULE s JOIN s.pet p JOIN p.owner o WHERE o.id=:customerId")
    List<Schedule> findAllByCustomerId(@Param("customerId") long customerId);
}
