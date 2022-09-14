package com.example.project_java_springboot.controller;

import com.example.project_java_springboot.entity.Comment;
import com.example.project_java_springboot.entity.Customer;
import com.example.project_java_springboot.entity.dto.CommentDTO;
import com.example.project_java_springboot.entity.dto.CustomerDTO;
import com.example.project_java_springboot.entity.enums.EnumStatus;
import com.example.project_java_springboot.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;
    @Autowired
    private final ModelMapper modelMapper;

    public CustomerController(CustomerService customerService, ModelMapper modelMapper) {
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Customer>> findAll() {
        return ResponseEntity.ok(customerService.findAll());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Customer> save(@RequestBody CustomerDTO customerDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.save(customerDTO));
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    public ResponseEntity<CustomerDTO> update(@PathVariable Integer id, @RequestBody CustomerDTO customerDTO) {
        Optional<Customer> optionalObj = customerService.findById(id);
        if (!optionalObj.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Customer objRequest = modelMapper.map(customerDTO, Customer.class);
        Customer obj = customerService.update(id, objRequest);
        CustomerDTO objResponse = modelMapper.map(obj, CustomerDTO.class);
        return ResponseEntity.ok().body(objResponse);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<Customer> findById(@PathVariable Integer id) {
        Optional<Customer> optionalObj = customerService.findById(id);

        if (!optionalObj.isPresent()) {
            ResponseEntity.notFound();
        }

        return ResponseEntity.ok(optionalObj.get());
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public boolean delete(@PathVariable Integer id) {
        customerService.deleteById(id);
        return true;
    }

}
