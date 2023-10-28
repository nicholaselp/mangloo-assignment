package com.manglooassignment.service;

import com.manglooassignment.model.Customer;
import com.manglooassignment.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.requireNonNull;

@Service
public class CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;
    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = requireNonNull(customerRepository, "CustomerRepository is missing");
    }

    public void createCustomer(Customer customer) {
        logger.info("Creating customer {}", customer);
        customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        logger.info("Retrieving all customers..");
        return customerRepository.findAll();
    }

    public Customer getCustomerById(int customerId) {
        logger.info("Retrieving customer with ID: {}", customerId);
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("No customer found with ID: " + customerId));
    }

    public Customer updateCustomer(Customer customer) {
        logger.info("Updating customer {}", customer);
        return customerRepository.save(customer);
    }

    public void deleteCustomerById(int customerId) {
        logger.info("Deleting customer with ID: {}", customerId);
        customerRepository.deleteById(customerId);
    }


}
