package com.immutable.visitormanagement.service.impl;

import com.immutable.visitormanagement.dto.EmployeeDto;
import com.immutable.visitormanagement.entity.Employee;
import com.immutable.visitormanagement.exception.ResourceNotFoundException;
import com.immutable.visitormanagement.repository.EmployeeRepository;
import com.immutable.visitormanagement.service.EmployeeService;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImplementations implements EmployeeService {

	private final EmployeeRepository employeeRepository;
	private  final ModelMapper modelMapper;

	@Autowired
	public EmployeeServiceImplementations(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
		this.employeeRepository = employeeRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public void save(EmployeeDto employeeDto) {
		Employee employee=mapToEmployee(employeeDto);
		this.employeeRepository.save(employee);
	}
	
	@Override
	public List<EmployeeDto> getEmployees(){
		List<Employee> employees= this.employeeRepository.findAll();
		List<EmployeeDto> employeeDto =employees.stream().map(this::mapToEmployeeDto).toList();
		return employeeDto;
	}
	
	@Override
	public EmployeeDto getEmployeeById(Long employeeId) {
		Employee employee= this.employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("Employee", "Id", employeeId));
		return mapToEmployeeDto(employee);
	}
	
	@Override
	public void update(EmployeeDto employeeDto) {
		 this.employeeRepository.findById(employeeDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Employee", "Id", employeeDto.getId()));
		 Employee employee = mapToEmployee(employeeDto);
		 this.employeeRepository.save(employee);
	}
	
	@Override
	public void deleteEmployeeById(Long employeeId) {
		this.employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("Employee", "Id", employeeId));

		this.employeeRepository.deleteById(employeeId);
	}
	
	
	private Employee mapToEmployee(EmployeeDto employeeDto) {
		return this.modelMapper.map(employeeDto,Employee.class);
	} 
	
	private EmployeeDto mapToEmployeeDto(Employee employee) {
		return this.modelMapper.map(employee,EmployeeDto.class);
	}

	
	
	
	
}
