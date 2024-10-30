package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class EmployeeController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/")
    public String showForm() {
        return "employeeForm"; // Returns the employeeForm.html view
    }

    @PostMapping("/submitEmployee")
    public String submitEmployee(@RequestParam("firstName") String firstName,
                                 @RequestParam("lastName") String lastName,
                                 @RequestParam("email") String email,
                                 @RequestParam("department") String department,
                                 @RequestParam("salary") Double salary) {
        
        String sql = "INSERT INTO employees (first_name, last_name, email, department, salary) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, firstName, lastName, email, department, salary);
        
        return "redirect:/employees"; // Redirect to the employees list after submission
    }

    @GetMapping("/employees")
    public String getEmployees(Model model) {
        String sql = "SELECT * FROM employees";
        List<Map<String, Object>> employees = jdbcTemplate.queryForList(sql);
        model.addAttribute("employees", employees);
        return "employeeList"; // Returns the employeeList.html view
    }
}
