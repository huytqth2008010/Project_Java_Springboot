package com.example.project_java_springboot.service;

import com.example.project_java_springboot.entity.Category;
import com.example.project_java_springboot.entity.Comment;
import com.example.project_java_springboot.entity.Customer;
import com.example.project_java_springboot.entity.dto.CustomerDTO;
import com.example.project_java_springboot.entity.enums.EnumStatus;
import com.example.project_java_springboot.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Optional<Customer> findById(Integer id) {
        return customerRepository.findById(id);
    }

    public Customer save(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setId_account(customerDTO.getId_account());
        customer.setGender(customerDTO.getGender());
        customer.setEmail(customerDTO.getEmail());
        customer.setAddress(customerDTO.getAddress());
        customer.setPhone_number(customerDTO.getPhone_number());
        return customerRepository.save(customer);
    }
    public Customer update(int id, Customer objRequestUpdate) {
        Optional<Customer> customer = customerRepository.findById(id);
        Customer objGet = customer.get();
        objGet.setName(objRequestUpdate.getName());
        objGet.setId_account(objRequestUpdate.getId_account());
        objGet.setGender(objRequestUpdate.getGender());
        objGet.setEmail(objRequestUpdate.getEmail());
        objGet.setAddress(objRequestUpdate.getAddress());
        objGet.setPhone_number(objRequestUpdate.getPhone_number());
        return customerRepository.save(objGet);
    }
    public void deleteById(Integer id) {
        customerRepository.deleteById(id);
    }
}
