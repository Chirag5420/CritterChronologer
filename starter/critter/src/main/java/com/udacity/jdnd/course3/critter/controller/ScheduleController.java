package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.DTO.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    ScheduleService scheduleService;

    @Autowired
    PetService petService;

    @Autowired
    EmployeeService employeeService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = convertDTOToSchedule(scheduleDTO);
        schedule = scheduleService.createSchedule(schedule);
        return convertScheduleToDTO(schedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> scheduleList = scheduleService.getAllSchedules();
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();

        if(scheduleList.size() != 0){
            for(Schedule schedule : scheduleList){
                ScheduleDTO scheduleDTO = convertScheduleToDTO(schedule);
                scheduleDTOList.add(scheduleDTO);
            }
        }

        return scheduleDTOList;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        List<Schedule> scheduleList = scheduleService.getScheduleByPetId(petId);

        if(scheduleList.size() != 0){
            for(Schedule schedule : scheduleList){
                ScheduleDTO scheduleDTO = convertScheduleToDTO(schedule);
                scheduleDTOList.add(scheduleDTO);
            }
        }

        return scheduleDTOList;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        List<Schedule> scheduleList = scheduleService.getScheduleByEmployeeId(employeeId);

        if(scheduleList.size() != 0){
            for(Schedule schedule : scheduleList){
                ScheduleDTO scheduleDTO = convertScheduleToDTO(schedule);
                scheduleDTOList.add(scheduleDTO);
            }
        }

        return scheduleDTOList;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        List<Schedule> scheduleList = scheduleService.getScheduleByCustomerId(customerId);

        if(scheduleList.size() != 0){
            for(Schedule schedule : scheduleList){
                ScheduleDTO scheduleDTO = convertScheduleToDTO(schedule);
                scheduleDTOList.add(scheduleDTO);
            }
        }

        return scheduleDTOList;
    }

    public ScheduleDTO convertScheduleToDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        List<Pet> petList = petService.getAllPets();
        List<Employee> employeeList = employeeService.getAllEmployees();

        List<Long> petIds = new ArrayList<>();
        if(petList.size() != 0){
            for(Pet p : petList){
                petIds.add(p.getId());
            }
        }

        List<Long> employeeIds = new ArrayList<>();
        if(employeeList.size() != 0){
            for(Employee e : employeeList){
                employeeIds.add(e.getId());
            }
        }

        scheduleDTO.setPetIds(petIds);
        scheduleDTO.setEmployeeIds(employeeIds);

        return scheduleDTO;
    }

    public Schedule convertDTOToSchedule(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);

        List<Long> petIds = scheduleDTO.getPetIds();
        List<Long> employeeIds = scheduleDTO.getEmployeeIds();

        List<Pet> petList = new ArrayList<>();
        if(petIds.size() != 0){
            for(Long petId : petIds){
                Pet pet = petService.getPet(petId);
                petList.add(pet);
            }
        }

        List<Employee> employeeList = new ArrayList<>();
        if(employeeIds.size() != 0){
            for(Long employeeId : employeeIds){
                Employee employee = employeeService.getEmployee(employeeId);
                employeeList.add(employee);
            }
        }

        schedule.setPet(petList);
        schedule.setEmployees(employeeList);

        return schedule;
    }
}
