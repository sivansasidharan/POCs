package com.ss.nitro.analytics.test;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.transaction.annotation.Transactional;

import com.ss.nitro.analytics.dyorg.domain.Employee;
import com.ss.nitro.analytics.dyorg.service.EmployeeProfileService;

public class EmployeeProfileTest {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"employeeprofile.xml");
		EmployeeProfileService service = (EmployeeProfileService) context
				.getBean("employeeProfileService");

		/*
		 * Person profile = createPofile(); createProfile(service, profile);
		 * System.out.println("Person created successfully.");
		 */

		// FIND ONE
		/*
		 * Person profile = getOneProfileById(service,67515L);
		 * System.out.println(profile);
		 */

		// FIND ALL

		getAllProfiles(service);

		// DELETE
		/*
		 * Person profile = createPofile(); deleteProfile(service,profile);
		 * System.out.println("Person deleted successfully.");
		 */

		// find By name
		// Employee profile = findProfile(service,"Sivan Sasidharan");
		// System.out.println(profile);

	}

	private static Employee findProfile(EmployeeProfileService service,
			String userName) {
		return service.findByEmployeename(userName);
	}

	private static Employee createProfile(EmployeeProfileService service,
			Employee profile) {
		return service.create(profile);
	}

	private static void deleteProfile(EmployeeProfileService service,
			Employee profile) {
		service.delete(profile);
	}

	private static Employee getOneProfileById(EmployeeProfileService service,
			Long id) {
		return service.findById(id);
	}

	@Transactional
	private static void getAllProfiles(EmployeeProfileService service) {
		System.out.println("here");
		Iterable<Employee> sample = service.findAll();
		// Result<Employee> result = service.findAll();
		// Iterator<Employee> iterator = result.iterator();
		for (Employee employee : sample) {
			System.out.println("Employee --->" + employee.toString());
		}
	}

	private static Employee createPofile() {
		Employee profile = new Employee();
		profile.setEmployeename("Sudev");
		return profile;
	}
}