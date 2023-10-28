package com.manglooassignment.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "description")
    private String description;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Order(){}

    public Order(Double amount, String description, Customer customer){
        this.amount = amount;
        this.orderDate = LocalDateTime.now();
        this.description = description;
        this.customer = customer;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public double getAmount(){ return amount; }

    public void setAmount(double amount){ this.amount = amount; }

    public LocalDateTime getOrderDate() { return orderDate; }

    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Customer getCustomer() { return customer; }

    public void setCustomer(Customer customer) { this.customer = customer; }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", description='" + description + '\'' +
                ", customer=" + customer +
                '}';
    }
}
