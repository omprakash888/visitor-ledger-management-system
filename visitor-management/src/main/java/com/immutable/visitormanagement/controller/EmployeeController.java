package com.immutable.visitormanagement.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.immutable.visitormanagement.dto.EmployeeDto;
import com.immutable.visitormanagement.service.EmployeeService;

import jakarta.validation.Valid;

import static com.immutable.visitormanagement.constants.Constants.*;

@RestController
public class EmployeeController {

	private final EmployeeService employeeService;

	@Autowired
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}


	@PostMapping(CREATE_URL_EMPLOYEE)
    public ResponseEntity<String> createEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
		employeeDto.setEmployeeName(employeeDto.getEmployeeName().trim());
        employeeService.save(employeeDto);
        return new ResponseEntity<>("Employee created successfully",HttpStatus.CREATED);
    }
	
	@GetMapping(GET_ALL_URL_EMPLOYEE)
    public ResponseEntity<List<EmployeeDto>> getEmployees() {
        List<EmployeeDto> employeeDto = this.employeeService.getEmployees();
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

	@GetMapping(GET_BY_ID_FOR_VISITOR_EMPLOYEE)
	public ResponseEntity<List<String>> getEmployeesForVisitor(@PathVariable String secretKey) {
		if(!secretKey.equals(SECRET_KEY_VISITOR)) {
			return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
		}
		List<EmployeeDto> employeeDto = this.employeeService.getEmployees();
		List<String> employeeNames = employeeDto.stream()
				.map(EmployeeDto::getEmployeeName)
				.collect(Collectors.toList());
		System.out.println("he;;o");

		return new ResponseEntity<>(employeeNames, HttpStatus.OK);
	}
	
	@GetMapping(GET_BY_ID_URL_EMPLOYEE)
    public ResponseEntity<EmployeeDto> getEmployeeById(@RequestParam("id") Long employeeId) {
        EmployeeDto employeeDto = this.employeeService.getEmployeeById(employeeId);
        return new ResponseEntity<>(employeeDto,HttpStatus.OK);
    }
	
	@PutMapping(UPDATE_URL_EMPLOYEE)
	public ResponseEntity<String> updateEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
		employeeDto.setEmployeeName(employeeDto.getEmployeeName().trim());
		employeeService.update(employeeDto);
		return new ResponseEntity<>("Employee updated",HttpStatus.OK);
	}
	
	
	@DeleteMapping(DELETE_URL_EMPLOYEE)
	public ResponseEntity<String> deleteEmployeeById(@RequestParam("id") Long employeeId){
		this.employeeService.deleteEmployeeById(employeeId);
		return new ResponseEntity<>("employee deleted sucessfully",HttpStatus.OK);
	}

}
