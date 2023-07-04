package com.immutable.visitormanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.immutable.visitormanagement.dto.EmployeeDto;
import com.immutable.visitormanagement.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
public class EmployeeController {
	@Autowired
	private EmployeeService employeeservices;
	
	@PostMapping("/createEmployee")
    public ResponseEntity<String> createEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        employeeservices.save(employeeDto);
        return new ResponseEntity<>("Employee created successfully",HttpStatus.CREATED);
    }
	
	@GetMapping("/getEmployees")
    public ResponseEntity<List<EmployeeDto>> getEmployees() {
        List<EmployeeDto> employeeDto = this.employeeservices.getEmployees();
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }
	
	@GetMapping("/getEmployeeById")
    public ResponseEntity<EmployeeDto> getEmployeeById(@RequestParam("id") Long employeeId) {
        EmployeeDto employeeDto = this.employeeservices.getEmployeeById(employeeId);
        return new ResponseEntity<>(employeeDto,HttpStatus.OK);
    }
	
	@PutMapping("/updateEmployees")
	public ResponseEntity<String> updateEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
		employeeservices.update(employeeDto);
		return new ResponseEntity<>("Employee updated",HttpStatus.OK);
	}
	
	
	@DeleteMapping("/deleteEmployees")
	public ResponseEntity<String> deleteEmployeeById(@RequestParam("id") Long employeeId){
		this.employeeservices.deleteEmployeeById(employeeId);
		return new ResponseEntity<>("employee deleted sucessfully",HttpStatus.OK);
	}
}
