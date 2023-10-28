package com.manglooassignment.controller;

import com.manglooassignment.model.Customer;
import com.manglooassignment.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.Objects.requireNonNull;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService){
        this.customerService = requireNonNull(customerService, "CustomerService is missing");
    }
    @PostMapping
    public void createCustomer(@RequestBody Customer customer){
        customerService.createCustomer(customer);
    }
    @GetMapping
    public List<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    @GetMapping("/{customerId}")
    public Customer getCustomerById(@PathVariable int customerId){
        return customerService.getCustomerById(customerId);
    }

    @PutMapping
    public Customer updateCustomer(@RequestBody Customer customer){ return customerService.updateCustomer(customer); }

    @DeleteMapping("/{customerId}")
    public void deleteCustomer(@PathVariable int customerId){
        customerService.deleteCustomerById(customerId);
    }
}
