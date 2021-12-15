package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.DTO.CustomerDTO;
import com.udacity.jdnd.course3.critter.DTO.EmployeeDTO;
import com.udacity.jdnd.course3.critter.DTO.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.EmployeeSkill;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    CustomerService customerService;

    @Autowired
    PetService petService;

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = convertDTOToCustomer(customerDTO);
        customer = customerService.saveCustomer(customer);
        return convertCustomerToDTO(customer);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customerList = customerService.getAllCustomers();
        List<CustomerDTO> customerDTOList = new ArrayList<>();

        if(customerList.size() != 0){
            for(Customer customer : customerList){
                CustomerDTO customerDTO = convertCustomerToDTO(customer);
                customerDTOList.add(customerDTO);
            }
        }

        return customerDTOList;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Customer customer = customerService.getCustomerByPetId(petId);
        return convertCustomerToDTO(customer);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = convertDTOToEmployee(employeeDTO);
        employeeService.saveEmployee(employee);
        return convertEmployeeToDTO(employee);
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.getEmployee(employeeId);
        return convertEmployeeToDTO(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        Set <EmployeeSkill> skillSet = employeeDTO.getSkills();
        DayOfWeek dayOfWeek = employeeDTO.getDate().getDayOfWeek();
        List<Employee> employeeList = employeeService.getEmployeesForService(skillSet, dayOfWeek);

        List<EmployeeDTO> employeeDTOList = new ArrayList<>();

        if(employeeList.size() != 0){
            for(Employee employee : employeeList){
                EmployeeDTO dto = convertEmployeeToDTO(employee);
                employeeDTOList.add(dto);
            }
        }

        return employeeDTOList;
    }

    public CustomerDTO convertCustomerToDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        List<Pet> petList = customer.getPets();
        List<Long> petIds = new ArrayList<>();

        if(petList.size() != 0){
            for(Pet p : petList){
                petIds.add(p.getId());
            }
        }

        customerDTO.setPetIds(petIds);
        return customerDTO;
    }

    public Customer convertDTOToCustomer(CustomerDTO customerDTO){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        List<Long> petIds = customerDTO.getPetIds();
        List<Pet> petList = new ArrayList<>();

        if(petIds.size()!= 0 && petIds!= null){
            for(Long id : petIds){
                petList.add(petService.getPet(id));
            }
        }

        customer.setPets(petList);
        return customer;
    }

    public EmployeeDTO convertEmployeeToDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }

    public Employee convertDTOToEmployee(EmployeeDTO employeeDTO){
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        return employee;
    }
}
