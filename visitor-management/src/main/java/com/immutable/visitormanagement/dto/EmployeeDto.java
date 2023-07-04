package com.immutable.visitormanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;


import org.hibernate.validator.constraints.Length;



public class EmployeeDto {
	
	private Long id;
    //@NotEmpty(message = "please enter your employee Id")
    //@Length(min = 8, max = 8,message = "only 8 characters allowed")
    private long employeeId;

    //@NotBlank(message = "please provide valid employee name")
    //@Length(min = 4, max = 25, message = "your name must be in between 4 and 25 characters")
    private String employeeName;

    
    
	public EmployeeDto() {
		super();
	}



	public EmployeeDto(Long id,
			@NotEmpty(message = "please enter your employee Id") @Length(min = 8, max = 8, message = "only 8 characters allowed") long employeeId,
			@NotBlank(message = "please provide valid employee name") @Length(min = 4, max = 25, message = "your name must be in between 4 and 25 characters") String employeeName) {
		super();
		this.id = id;
		this.employeeId = employeeId;
		this.employeeName = employeeName;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public long getEmployeeId() {
		return employeeId;
	}



	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}



	public String getEmployeeName() {
		return employeeName;
	}



	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
    
    
}
