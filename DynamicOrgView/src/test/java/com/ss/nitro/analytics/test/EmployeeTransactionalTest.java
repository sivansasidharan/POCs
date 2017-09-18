package com.ss.nitro.analytics.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.ss.nitro.analytics.dyorg.dao.EmployeeProfileRepository;
import com.ss.nitro.analytics.dyorg.domain.Employee;
import com.ss.nitro.analytics.dyorg.service.EmployeeProfileService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
public class EmployeeTransactionalTest {

	@Autowired
	EmployeeProfileService service;
	
	@Autowired
	EmployeeProfileRepository repository;
	
	@Test
    @Transactional
    public void findAllEmployees() throws Exception{
		
		Iterable<Employee> sample = service.findAll();
		for (Employee employee : sample) {
			//System.out.println("Immediatesupervisor --->" + employee.getImmediatesupervisor());
			//System.out.println("DirectReports --->" + employee.getDirectReports());
			System.out.println("ROLE ---->"+employee.getRoles());
		}
	}
	
/*	@Test
    @Transactional
    public void findByUserName() throws Exception{
		
		Employee sample = service.findByEmployeename("Sivan Sasidharan");
		System.out.println("Name ---->"+sample.getEmployeename());
			//System.out.println("Immediatesupervisor --->" + sample.getImmediatesupervisor());
			//System.out.println("DirectReports --->" + sample.getDirectReports());
			System.out.println("ROLE ---->"+sample.getRoles());
	}*/
}
