package com.example.dairy.controller;

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
public class FarmerController {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @GetMapping("/")
    public String login() {
    	return "login";
    }
    
    @GetMapping("/farmer")
    public String farmer() {
    	return "farmer";
    }

    @GetMapping("/newFarmer")
    public String newFarmer() {
        return "newf";
    }

    @PostMapping("/submitFarmer")
    public String submitRegistration(
            @RequestParam("name") String name,
            @RequestParam("contact") String contact,
            @RequestParam("email") String email,
            @RequestParam("date_of_joining") String dateOfJoining,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("confirm_password") String confirmPassword,
            @RequestParam("account_number") String accountNumber,
            @RequestParam("bank_name") String bankName,
            @RequestParam("branch_name") String branchName,
            @RequestParam("ifsc_code") String ifscCode,
            @RequestParam("num_cows") int numCows,
            @RequestParam("milk_capacity") int milkCapacity,
            @RequestParam("other_cattle") String otherCattle,
            Model model) {

        // Insert farmer data into the database
        String sql = "INSERT INTO farmers (Farmer_name, Contact_no, Address, no_of_cows, Milk_production_capacity, Account_no, Date_of_joining) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, name, contact, email, numCows, milkCapacity, accountNumber, dateOfJoining);

        // Add a success message to the model to display on the success page
        model.addAttribute("message", "Registration successful!");

        return "registrationSuccess"; // Return the success page (e.g., registrationSuccess.html)
    }

    @PostMapping("/employeeLogin")
    public String employeeLogin(@RequestParam("username") String username,
                                @RequestParam("email") String email,
                                @RequestParam("password") String password,
                                Model model) {
        
        return "employeeDashboard";
    }

    @GetMapping("/farmers")
    public String getFarmers(Model model) {
        String sql = "SELECT * FROM farmers";
        List<Map<String, Object>> farmers = jdbcTemplate.queryForList(sql);
        model.addAttribute("farmers", farmers);
        return "farmerList"; 
    }
    
    @GetMapping("/failedDNFTests")
    public String getFailedDNFTests(Model model) {
        String sql = "SELECT Lab_report_id, District_id, Pathogen_test, Temperature_test, fat_contents, Protein_test, Lactose_test, DNF_test, Milk_type FROM lab_report WHERE DNF_test = 0"; // 0 represents a failed test
        List<Map<String, Object>> failedDNFTests = jdbcTemplate.queryForList(sql);
        model.addAttribute("failedDNFTests", failedDNFTests);
        return "failedDNFTests";
    }
    
    @GetMapping("/empDash")
    public String getEmployeeDashboard(Model model) {
        // Sample data; replace with actual data from your database
        List<String> noMilkFarmers = List.of("Farmer John", "Farmer Doe", "Farmer Jane");
        List<String> failedDNFFarmers = List.of("Farmer Smith - DNF Test Failed", "Farmer Emily - DNF Test Failed");

        // Example chart data
        List<Integer> monthlyProductionData = List.of(500, 550, 600, 580, 620, 640, 670, 660, 700, 710, 730, 750);
        List<Integer> topFarmersData = List.of(1500, 1250, 1600, 1300, 1450);
        List<Integer> topVillagesData = List.of(3000, 2800, 3500, 3200, 3100);

        model.addAttribute("noMilkFarmers", noMilkFarmers);
        model.addAttribute("failedDNFFarmers", failedDNFFarmers);
        model.addAttribute("monthlyProductionData", monthlyProductionData);
        model.addAttribute("topFarmersData", topFarmersData);
        model.addAttribute("topVillagesData", topVillagesData);

        return "employeeDashboard";
    }
    
    @GetMapping("/farmersByCows")
    public String getFarmersByCows(@RequestParam("numberOfCows") int numberOfCows, Model model) {
        String sql = "SELECT * FROM farmers WHERE no_of_cows = ?";
        List<Map<String, Object>> farmers = jdbcTemplate.queryForList(sql, numberOfCows);
        model.addAttribute("farmers", farmers);
        return "farmerList";
    }
}