package com.example.employeevolot.Models;

import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    private String id;

    private String volotid;

    @ManyToOne(optional = true, cascade = CascadeType.REFRESH)
    private User user;

    @ManyToOne(optional = true, cascade = CascadeType.REFRESH)
    private Product product;

    private String dateTime;

    @ManyToOne(optional = true, cascade = CascadeType.REFRESH)
    private Status status;

    public Order() {
    }

    public String getId() {
        return id;
    }

    public String getVolotid() {
        return volotid;
    }

    public void setVolotid(String volotid) {
        this.volotid = volotid;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
