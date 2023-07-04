package com.immutable.visitormanagement.service;

import java.util.List;



import com.immutable.visitormanagement.dto.EmployeeDto;

import jakarta.validation.Valid;

public interface EmployeeService {

	List<EmployeeDto> getEmployees();

	

	void save(@Valid EmployeeDto employeeDto);



	EmployeeDto getEmployeeById(Long employeeId);



	void update(@Valid EmployeeDto employeeDto);



	void deleteEmployeeById(Long employeeId);
}
