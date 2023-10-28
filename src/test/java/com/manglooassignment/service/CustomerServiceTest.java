package com.manglooassignment.service;

import com.manglooassignment.model.Customer;
import com.manglooassignment.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

@SpringBootTest
@Transactional
@Rollback
public class CustomerServiceTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerService customerService;

    @Test
    public void create_customer(){
        var customer = new Customer("username", "email", "1234567");
        customerService.createCustomer(customer);

        assertThat(customerRepository.findAll().stream().filter(cust -> cust.getUsername().equals(customer.getUsername())).findFirst())
                .isPresent().hasValueSatisfying(storedCustomer -> {
                    assertThat(storedCustomer.getUsername()).isEqualTo(customer.getUsername());
                    assertThat(storedCustomer.getEmail()).isEqualTo(customer.getEmail());
                    assertThat(storedCustomer.getPhoneNumber()).isEqualTo(customer.getPhoneNumber());
                });
    }

    @Test
    public void get_all_customers(){
        var customer1 = new Customer("username_1", "email_1", "1234567_1");
        var customer2 = new Customer("username_2", "email_2", "1234567_2");

        customerRepository.save(customer1);
        customerRepository.save(customer2);

        var result = customerService.getAllCustomers();

        assertThat(result).isNotEmpty().hasSize(2);

        assertThat(result)
                .extracting(Customer::getUsername, Customer::getEmail, Customer::getPhoneNumber)
                .contains(tuple(customer1.getUsername(), customer1.getEmail(), customer1.getPhoneNumber()),
                        tuple(customer2.getUsername(), customer2.getEmail(), customer2.getPhoneNumber()));
    }

    @Test
    public void update_customer(){
        var customer = new Customer("username", "email", "1234567");
        customerRepository.save(customer);

        Optional<Customer> storedCustomer = customerRepository.findAll().stream().findFirst();
        assertThat(storedCustomer).isPresent();

        var toUpdate = storedCustomer.get();
        toUpdate.setEmail("updated_email");

        customerService.updateCustomer(toUpdate);

        assertThat(customerRepository.findAll().stream().filter(customer1 -> customer1.getUsername().equals(customer.getUsername())).findFirst())
                .isPresent().hasValueSatisfying(storedCust -> {
                    assertThat(storedCust).isEqualTo(toUpdate);
                });
    }

    @Test
    public void delete_customer(){
        var customer = new Customer("username", "email", "1234567");

        customerRepository.save(customer);

        Optional<Customer> storedCustomer = customerRepository.findAll().stream().findFirst();
        assertThat(storedCustomer).isPresent();

        customerService.deleteCustomerById(storedCustomer.get().getId());

        assertThat(customerRepository.findAll()).hasSize(0);
    }
}
