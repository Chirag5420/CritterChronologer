package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("SELECT s FROM Schedule s JOIN Pet p WHERE p.id = :id")
    List<Schedule> findAllByPetsId(@Param("id") long id);

    @Query("SELECT s FROM Schedule s JOIN s.employees e WHERE e.id = :id")
    List<Schedule> findAllByEmployeesId(@Param("id") long id);

    @Query("SELECT s FROM Schedule s JOIN Pet p JOIN Customer c WHERE c.id=:id")
    List<Schedule> findAllByCustomerId(@Param("id") long id);
}
