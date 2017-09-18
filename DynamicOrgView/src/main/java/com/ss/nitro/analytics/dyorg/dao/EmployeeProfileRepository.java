package com.ss.nitro.analytics.dyorg.dao;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.ss.nitro.analytics.dyorg.domain.Employee;

public interface EmployeeProfileRepository extends GraphRepository<Employee>{

	Employee findByEmployeename(String username);
}
