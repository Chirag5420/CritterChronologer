package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    Customer saveCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    Customer getCustomerById(Long id) throws Exception{
        Optional<Customer> optionalCustomer = customerRepository.findById(id);

        if(optionalCustomer.isPresent()){
            return optionalCustomer.get();
        }
        else{
            throw new NoSuchElementException("Customer with id : " + id + " cannot be found");
        }
    }

    List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    Customer getCustomerByPetId(Long id){
        return customerRepository.getCustomerByPetId(id);
    }
}
