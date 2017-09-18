package com.ss.nitro.analytics.dyorg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ss.nitro.analytics.dyorg.dao.EmployeeProfileRepository;
import com.ss.nitro.analytics.dyorg.domain.Employee;

@Service("employeeProfileService")
@Transactional
public class EmployeeProfileServiceImpl implements EmployeeProfileService {

	@Autowired	
	private EmployeeProfileRepository employeeProfileRepo;

	public Employee create(Employee profile) {
		return employeeProfileRepo.save(profile);
	}

	public void delete(Employee profile) {
		employeeProfileRepo.delete(profile);
	}

	public Employee findById(long id) {
		return employeeProfileRepo.findOne(id);
	}

	public Iterable<Employee> findAll() {
		return employeeProfileRepo.findAll();
	}

	public Employee findByEmployeename(String username) {
		return employeeProfileRepo.findByEmployeename(username);
	}

}
