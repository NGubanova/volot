package com.example.employeevolot.Repository;

import com.example.employeevolot.Models.Order;
import com.example.employeevolot.Models.Status;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {

    Order getFirstByVolotid(String Volotid);
    Iterable<Order> findByVolotid(String Volotid);

    @Query("select distinct volotid from Order")
    ArrayList<String> getByVolotid();

    @Query("SELECT DISTINCT volotid FROM Order WHERE status = ?1")
    ArrayList<String> findByStatusId(Status id);

    @Query("SELECT DISTINCT o.volotid FROM Order o JOIN User u ON u.id = o.user.id WHERE u.address LIKE %?1%")
    ArrayList<String> findByAddressLike(String address);

    @Query("SELECT DISTINCT o.volotid FROM Order o JOIN User u ON u.id = o.user.id WHERE u.name LIKE %?1% " +
            "OR u.username LIKE %?1% OR u.address LIKE %?1% OR o.volotid LIKE %?1%")
    ArrayList<String> findByOrder(String name);
}
