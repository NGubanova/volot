package com.example.employeevolot.Controllers;

import com.example.employeevolot.Models.*;
import com.example.employeevolot.Repository.EmployeeRepository;
import com.example.employeevolot.Repository.OrderRepository;
import com.example.employeevolot.Repository.StatusRepository;
import com.example.employeevolot.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
@PreAuthorize("hasAuthority('HR')")
public class MainController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    StatusRepository statusRepository;

    @GetMapping("/hr")
    public String mainPage(Order order, Model model) {
        ArrayList<String> orders = orderRepository.getByVolotid();
        model.addAttribute("orders", convertArrayToList(orders));

        model.addAttribute("status", statusMap());
        return "/hr/main";
    }

    @GetMapping("/order/{Volotid}")
    public String OrderPage(Order order, Model model,
                            @PathVariable String Volotid) {

        Order orders = orderRepository.getFirstByVolotid(Volotid);
        model.addAttribute("orders", orders);

        Iterable<Order> productList = orderRepository.findByVolotid(Volotid);
        model.addAttribute("product", productList);

        model.addAttribute("status", statusMap());
        return "/hr/detailOrder";
    }

    @PostMapping("/order/{Volotid}")
    public String OrderPageEdit(String status, Model model,
                            @PathVariable String Volotid) {
        Iterable<Order> orders = orderRepository.findByVolotid(Volotid);
        model.addAttribute("orders", orders);

        model.addAttribute("status", statusMap());

        for(Order order1:orders){
        order1.setStatus(statusRepository.findByName(status));
        orderRepository.save(order1);
        }
        return "redirect:/";
    }

    @GetMapping("/search")
    public String Search(@RequestParam String search,
                         Model model) {
        model.addAttribute("status", statusMap());
        List<Order> orderSearch = convertArrayToList(orderRepository.findByOrder(search));
        model.addAttribute("orders", orderSearch);
        return "/hr/main";
    }


    @GetMapping("/filterStatus")
    public String FilterStatus(@RequestParam Long status,
                               Model model) {
        model.addAttribute("status", statusMap());

        Status status1 = statusRepository.findById(status).orElseThrow();
        List<String> orders = orderRepository.findByStatusId(status1);
        model.addAttribute("orders", convertArrayToList(orders));
        return "/hr/main";
    }

    public List<Order> convertArrayToList(List<String> orders) {
        List<Order> orderList = new ArrayList<>();
        for (String string : orders) {
            orderList.add(orderRepository.getFirstByVolotid(string));
        }
        return orderList;
    }

    public Iterable<Status> statusMap() {
        return statusRepository.findAll();
    }
}
