package com.example.employeevolot.Controllers;

import com.example.employeevolot.Models.Employee;
import com.example.employeevolot.Models.Role;
import com.example.employeevolot.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;

@Controller
public class EmptyController {
    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping("/")
    public String mainPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Employee employee = employeeRepository.findByUsername(auth.getName());
        if (employee.getRoles().equals(Collections.singleton(Role.ADMIN))) {
            return "redirect:/employee";
        } else if (employee.getRoles().equals(Collections.singleton(Role.HR))) {
            return "redirect:/hr";
        } else
            return "empty";
    }
}
