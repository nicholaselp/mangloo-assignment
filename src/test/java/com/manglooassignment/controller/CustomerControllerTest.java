package com.manglooassignment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manglooassignment.model.Customer;
import com.manglooassignment.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@Rollback
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void create_customer() throws Exception {
        var customer = new Customer("username", "email", "1234567");

        mockMvc.perform(MockMvcRequestBuilders.post("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        var customerFromDB = customerRepository.findAll().stream()
                .filter(cust -> cust.getUsername().equals(customer.getUsername()))
                .findFirst();

        assertThat(customerFromDB).isPresent().hasValueSatisfying(custFromDb -> {
            assertThat(custFromDb.getUsername()).isEqualTo(customer.getUsername());
            assertThat(custFromDb.getEmail()).isEqualTo(customer.getEmail());
            assertThat(custFromDb.getPhoneNumber()).isEqualTo(customer.getPhoneNumber());
        });
    }

    @Test
    public void get_customer_by_id() throws Exception {
        var customer = new Customer("username", "email", "1234567");
        customerRepository.save(customer);

        Optional<Customer> savedCustomer = customerRepository.findAll().stream().findFirst();

        assertThat(savedCustomer).isPresent();

        mockMvc.perform(MockMvcRequestBuilders.get("/customer/" + savedCustomer.get().getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(savedCustomer)));
    }

    @Test
    public void update_customer() throws Exception {
        var customer = new Customer("username", "email", "1234567");
        customerRepository.save(customer);

        Optional<Customer> savedCustomer = customerRepository.findAll().stream().findFirst();

        assertThat(savedCustomer).isPresent();
        var customerToUpdate = savedCustomer.get();

        customerToUpdate.setEmail("updated@example.com");

        mockMvc.perform(MockMvcRequestBuilders.post("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerToUpdate)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        assertThat(customerRepository.findById(customerToUpdate.getId())).isPresent().hasValueSatisfying(updated -> {
            assertThat(updated).isEqualTo(customerToUpdate);
        });
    }

    @Test
    public void delete_customer() throws Exception {
        var customer = new Customer("username", "email", "1234567");
        customerRepository.save(customer);

        Optional<Customer> savedCustomer = customerRepository.findAll().stream().findFirst();

        assertThat(savedCustomer).isPresent();

        mockMvc.perform(MockMvcRequestBuilders.delete("/customer/" + savedCustomer.get().getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        assertThat(customerRepository.findAll()).isEmpty();
    }
}
