package com.immutable.visitormanagement.service;

import java.util.List;

import com.immutable.visitormanagement.dto.EmployeeDto;


public interface EmployeeService {
	
	public void save(EmployeeDto employeeDto);

    public List<EmployeeDto> getAllEmployees();

}
