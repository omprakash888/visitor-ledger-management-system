package com.immutable.visitormanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import com.immutable.visitormanagement.dto.EmployeeDto;
import com.immutable.visitormanagement.dto.VisitorDto;
import com.immutable.visitormanagement.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
public class EmployeeController {
	@Autowired
	private EmployeeService employeeservices;
	
	@PostMapping("/createemployee")
	public ResponseEntity<String> createEmployee(@Valid @RequestBody EmployeeDto employeeDto){
		employeeservices.save(employeeDto);
		return new ResponseEntity<>("Employee Created Successfully",HttpStatus.CREATED);
	}
	
	@GetMapping("/getemployees")
	public ResponseEntity<List<VisitorDto>> getAllEmployees() {
        List<EmployeeDto> employeeDtos = this.employeeservices.getAllEmployees();
        return new ResponseEntity<>(employeeDtos,HttpStatus.OK);
    }
	
	


}
