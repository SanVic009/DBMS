package com.example.dairy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;  // Ensure to use java.sql.Date for SQL compatibility
import java.util.List;
import java.util.Map;

@Controller
public class FarmerController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/")
    public String showForm() {
        return "farmerForm"; // Returns the farmerForm.html view
    }

    @PostMapping("/submitFarmer")
    public String submitFarmer(@RequestParam("farmerName") String farmerName,
                                @RequestParam("contactNo") Long contactNo,
                                @RequestParam("address") String address,
                                @RequestParam("noOfCows") int noOfCows,
                                @RequestParam("milkProductionCapacity") int milkProductionCapacity,
                                @RequestParam("accountNo") Long accountNo,
                                @RequestParam("dateOfJoining") Date dateOfJoining) {  // Changed to Date for SQL compatibility
        
        String sql = "INSERT INTO farmers (Farmer_name, Contact_no, Address, no_of_cows, Milk_production_capacity, Account_no, Date_of_joining) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, farmerName, contactNo, address, noOfCows, milkProductionCapacity, accountNo, dateOfJoining);
        
        return "redirect:/farmers"; // Redirect to the farmers list after submission
    }

    @GetMapping("/farmers")
    public String getFarmers(Model model) {
        String sql = "SELECT * FROM farmers";
        List<Map<String, Object>> farmers = jdbcTemplate.queryForList(sql);
        model.addAttribute("farmers", farmers);
        return "farmerList"; // Returns the farmerList.html view
    }
    
    @GetMapping("/failedDNFTests")
    public String getFailedDNFTests(Model model) {
        String sql = "SELECT Lab_report_id, District_id, Pathogen_test, Temperature_test, fat_contents, Protein_test, Lactose_test, DNF_test, Milk_type FROM lab_report WHERE DNF_test = 0"; // 0 represents a failed test
        List<Map<String, Object>> failedDNFTests = jdbcTemplate.queryForList(sql);
        model.addAttribute("failedDNFTests", failedDNFTests);
        return "failedDNFTests"; // Returns the failedDNFTests.html view
    }
    
    @GetMapping("/farmersByCows")
    public String getFarmersByCows(@RequestParam("numberOfCows") int numberOfCows, Model model) {
        String sql = "SELECT * FROM farmers WHERE no_of_cows = ?";
        List<Map<String, Object>> farmers = jdbcTemplate.queryForList(sql, numberOfCows);
        model.addAttribute("farmers", farmers);
        return "farmerList"; // Returns the farmerList.html view
    }

}
