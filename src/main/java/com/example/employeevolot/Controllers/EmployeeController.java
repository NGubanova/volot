package com.example.employeevolot.Controllers;

import com.example.employeevolot.Models.Employee;
import com.example.employeevolot.Models.Role;
import com.example.employeevolot.Models.Status;
import com.example.employeevolot.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/employee")
@PreAuthorize("hasAuthority('ADMIN')")
public class EmployeeController {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("")
    public String employeeMain(Model model){
        Iterable<Employee> EmpList = employeeRepository.findAll();
        model.addAttribute("EmpList", EmpList);
        return "employee/index";
    }

    @GetMapping("/add")
    public String employeeAddView(Employee employee, Model model){
        model.addAttribute("roles", roleMap());
        return "employee/action";
    }

    @PostMapping("/add")
    public String employeeAdd(@Valid Employee employee,
                              BindingResult result, Model model,
                              @RequestParam String[] roles){
        model.addAttribute("roles", roleMap());
        if(result.hasErrors())
            return ("employee/action");

        employee.getRoles().clear();
        for(String role: roles){
            employee.getRoles().add(Role.valueOf(role));
        }
        employee.setActive(true);
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        employeeRepository.save(employee);
        return "redirect:/employee";
    }

    @GetMapping("/edit/{id}")
    public String employeeEdit(Model model,
                               @PathVariable long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow();
        model.addAttribute("employee", employee);
        model.addAttribute("roles", roleMap());

        return("/employee/edit");
    }

    @PostMapping("/edit/{id}")
    public String employeeEdit(@Valid Employee employee,
                               BindingResult result, Model model,
                               @RequestParam Long id,
                               @RequestParam String[] roles) {

        employee.setPassword(employeeRepository.findById(id).get().getPassword());
        model.addAttribute("roles", roleMap());

        if (result.hasErrors())
            return ("/employee/edit");

        employee.getRoles().clear();
        for(String role: roles){
            employee.getRoles().add(Role.valueOf(role));
        }
        employee.setActive(true);
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        employeeRepository.save(employee);
        return("redirect:/employee");
    }

    @GetMapping("/delete/{id}")
    public String employeeDelete(@PathVariable long id) {
        Employee employee= employeeRepository.findById(id).orElseThrow();
        employee.setActive(false);
        employeeRepository.save(employee);
        return("redirect:/employee");
    }

    @GetMapping("/filter")
    public String employeeFilter(@RequestParam String searchName,
                                 Model model){
        List<Employee> EmpList =employeeRepository.findByNameContaining(searchName);
        model.addAttribute("EmpList", EmpList);
        return "employee/index";
    }

    public Iterable<Role> roleMap(){
        Iterable<Role> role =  List.of(Role.values());
        return role;
    };


}
