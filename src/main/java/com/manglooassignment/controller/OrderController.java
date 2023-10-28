package com.manglooassignment.controller;

import com.manglooassignment.model.Order;
import com.manglooassignment.service.OrderService;
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
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = requireNonNull(orderService, "OrderService is missing");
    }

    @PostMapping
    public void createOrder(@RequestBody Order order){ orderService.createOrder(order); }

    @GetMapping
    public List<Order> getAllOrders(){ return orderService.getAllOrders(); }

    @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable int orderId){
        return orderService.getOrderById(orderId);
    }

    @PutMapping
    public Order updateOrder(@RequestBody Order order){
        return orderService.updateOrder(order);
    }

    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable int orderId){
        orderService.deleteOrderById(orderId);
    }

    @GetMapping("/{customerId}/customer")
    public List<Order> getOrdersByCustomerId(@PathVariable int customerId){ return orderService.getOrdersByCustomerId(customerId); }

}
