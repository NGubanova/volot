package com.example.employeevolot.Models;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Поле не должно быть пустым!")
    @Size(min = 2, max = 30, message = "Поле должно содержать от 2 до 30 символов")
    private String name;

    @NotBlank(message = "Поле не должно быть пустым!")
    @Size(min = 5, max = 30, message = "Поле должно содержать от 5 до 30 символов")
    @Pattern(regexp = "^(?=.{1,64}@)" +
            "[A-Za-z0-9_-]" +
            "+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]" +
            "+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",
            message = "Не корректно введена почта")
    private String username;

    @NotBlank(message = "Поле не должно быть пустым!")
    private String phone;
    private String address;
    private boolean active;

    @Pattern(regexp = "^(?=.*[0-9])" +
            "(?=.*[a-z])" +
            "(?=.*[A-Z])" +
            "(?=.*[!@#$%^&+=])" +
            "(?=\\S+$)" +
            ".{6,}", message = "Пароль должен быть не меньше 6 символов,"+ "\n" +
            "иметь числа и латинкие строчные и заглавные буквы," + "\n" +
            "а также специальные символы ")
    private String password;
    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Order> order;
    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "product", orphanRemoval = true)
    private List<Feedback> feedback;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @ManyToMany
    @JoinTable(name="favorite",
            joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="product_id"))
    private List<Product> products;

    public User() {
    }

    public void removeProduct(Product product){
        this.products.remove(product);
        product.getUsers().remove(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Order> getOrder() {
        return order;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }

    public List<Feedback> getFeedback() {
        return feedback;
    }

    public void setFeedback(List<Feedback> feedback) {
        this.feedback = feedback;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
