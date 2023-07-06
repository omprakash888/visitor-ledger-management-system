package com.immutable.visitormanagement.controller;

import java.util.ArrayList;
import java.util.List;

import com.immutable.visitormanagement.request.GetAllEmployeesForVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.immutable.visitormanagement.dto.EmployeeDto;
import com.immutable.visitormanagement.service.EmployeeService;

import jakarta.validation.Valid;

import static com.immutable.visitormanagement.constants.Constants.SECRET_KEY_VISITOR;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

	private final EmployeeService employeeService;

	@Autowired
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}


	@PostMapping("/create")
    public ResponseEntity<String> createEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
		employeeDto.setEmployeeName(employeeDto.getEmployeeName().trim());
        employeeService.save(employeeDto);
        return new ResponseEntity<>("Employee created successfully",HttpStatus.CREATED);
    }
	
	@GetMapping("/getAllEmployees")
    public ResponseEntity<List<EmployeeDto>> getEmployees() {
        List<EmployeeDto> employeeDto = this.employeeService.getEmployees();
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

	@GetMapping("/getAllEmployeesForVisitor")
	public ResponseEntity<List<EmployeeDto>> getEmployeesForVisitor(@RequestBody GetAllEmployeesForVisitor getAllEmployeesForVisitor) {
		if(!getAllEmployeesForVisitor.getSecretKey().equals(SECRET_KEY_VISITOR)) {
			return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
		}
		List<EmployeeDto> employeeDto = this.employeeService.getEmployees();
		return new ResponseEntity<>(employeeDto, HttpStatus.OK);
	}
	
	@GetMapping("/getById")
    public ResponseEntity<EmployeeDto> getEmployeeById(@RequestParam("id") Long employeeId) {
        EmployeeDto employeeDto = this.employeeService.getEmployeeById(employeeId);
        return new ResponseEntity<>(employeeDto,HttpStatus.OK);
    }
	
	@PutMapping("/update")
	public ResponseEntity<String> updateEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
		employeeDto.setEmployeeName(employeeDto.getEmployeeName().trim());
		employeeService.update(employeeDto);
		return new ResponseEntity<>("Employee updated",HttpStatus.OK);
	}
	
	
	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteEmployeeById(@RequestParam("id") Long employeeId){
		this.employeeService.deleteEmployeeById(employeeId);
		return new ResponseEntity<>("employee deleted sucessfully",HttpStatus.OK);
	}

}
