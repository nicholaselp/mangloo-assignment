package com.manglooassignment.service;

import com.manglooassignment.model.Order;
import com.manglooassignment.repository.CustomerRepository;
import com.manglooassignment.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.requireNonNull;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository){
        this.orderRepository = requireNonNull(orderRepository, "orderRepository is missiing");
        this.customerRepository = requireNonNull(customerRepository, "customerRepository is missing");
    }

    public void createOrder(Order order) {
        logger.info("Creating order {}", order);

        var customer = customerRepository.findById(order.getCustomer().getId())
                .orElseThrow(() -> new RuntimeException("No customer found with ID: " + order.getCustomer().getId()));

        orderRepository.save(new Order(order.getAmount(), order.getDescription(), customer));
    }

    public List<Order> getAllOrders() {
        logger.info("Retrieving all orders..");
        return orderRepository.findAll();
    }

    public Order getOrderById(Integer orderId) {
        logger.info("Retrieving order with ID: {}", orderId);
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("No order found by id: " + orderId));
    }

    public List<Order> getOrdersByCustomerId(int customerId) {
        logger.info("Get Order by customerId {}", customerId);
        return orderRepository.findByCustomerId(customerId);
    }

    public Order updateOrder(Order order) {
        logger.info("Updating order {}", order);
        return orderRepository.save(order);
    }

    public void deleteOrderById(int orderId) {
        logger.info("Deleting order with ID: {}", orderId);
        orderRepository.deleteById(orderId);
    }
}
