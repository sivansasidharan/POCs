package com.ss.nitro.analytics.dyorg.service;

import com.ss.nitro.analytics.dyorg.domain.Employee;

public interface EmployeeProfileService {

	Employee create(Employee profile);

	void delete(Employee profile);

	Employee findById(long id);

	Iterable<Employee> findAll();

	Employee findByEmployeename(String username);
}
