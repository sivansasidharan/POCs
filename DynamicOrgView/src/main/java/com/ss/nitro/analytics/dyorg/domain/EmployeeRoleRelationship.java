package com.ss.nitro.analytics.dyorg.domain;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

@RelationshipEntity(type = "WORKS_AS")
public class EmployeeRoleRelationship {

	@GraphId
	private String id;
	
	//private String location;
	@StartNode
	private Employee employee;
	@EndNode
	private Role role;

		public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	

}
