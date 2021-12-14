package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.EmployeeSkill;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.*;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    public Employee getEmployee(Long id){
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);

        if(optionalEmployee.isPresent()){
            return optionalEmployee.get();
        }
        else{
            throw new NoSuchElementException("Employee with id : " + id + " cannot be found");
        }
    }

    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    public void setAvailability(Set <DayOfWeek> daysAvailable, long employeeId){
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);

        if(optionalEmployee.isPresent()){
            Employee employee = optionalEmployee.get();
            employee.setDaysAvailable(daysAvailable);
            employeeRepository.save(employee);
        }
        else{
            throw new NoSuchElementException("Employee with id : " + employeeId + " cannot be found");
        }
    }

    public List<Employee> getEmployeesForService(Set<EmployeeSkill> skillSet, DayOfWeek dayOfWeek){
        List <Employee> employeeList = new ArrayList<>();
        List <Employee> availableEmployees = employeeRepository.findAllByDaysAvailable(dayOfWeek);

        for(Employee e : availableEmployees){
            if(e.getSkills().containsAll(skillSet)){
                employeeList.add(e);
            }
        }

        return employeeList;
    }
}
