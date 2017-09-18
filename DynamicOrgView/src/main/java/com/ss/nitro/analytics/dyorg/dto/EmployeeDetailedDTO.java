package com.ss.nitro.analytics.dyorg.dto;

import java.util.Set;

import org.codehaus.jackson.annotate.JsonProperty;

import com.ss.nitro.analytics.dyorg.domain.Employee;
import com.ss.nitro.analytics.dyorg.domain.Role;

public class EmployeeDetailedDTO {

	@JsonProperty(value = "graph_id")
	private Long id;

	@JsonProperty(value = "employeename")
	private String employeename;

	@JsonProperty(value = "userlpn")
	private String userlpn;

	@JsonProperty(value = "employeeemail")
	private String employeeemail;

	@JsonProperty(value = "employeelocation")
	private String employeelocation;

	@JsonProperty(value = "employeegrade")
	private String employeegrade;

	@JsonProperty(value = "immediatesupervisor")
	private Employee immediatesupervisor;

	@JsonProperty(value = "primarycounsellor")
	private Employee primarycounsellor;

	@JsonProperty(value = "directReports")
	private Set<Employee> directReports;

//	@JsonProperty(value = "roles")
//	private Role roles;

	@JsonProperty(value = "immediatesupervisorlpn")
	private String immediatesupervisorlpn;

	@JsonProperty(value = "primarycounsellorlpn")
	private String primarycounsellorlpn;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmployeename() {
		return employeename;
	}

	public void setEmployeename(String employeename) {
		this.employeename = employeename;
	}

	public String getUserlpn() {
		return userlpn;
	}

	public void setUserlpn(String userlpn) {
		this.userlpn = userlpn;
	}

	public String getEmployeeemail() {
		return employeeemail;
	}

	public void setEmployeeemail(String employeeemail) {
		this.employeeemail = employeeemail;
	}

	public String getEmployeelocation() {
		return employeelocation;
	}

	public void setEmployeelocation(String employeelocation) {
		this.employeelocation = employeelocation;
	}

	public String getEmployeegrade() {
		return employeegrade;
	}

	public void setEmployeegrade(String employeegrade) {
		this.employeegrade = employeegrade;
	}

	public Employee getImmediatesupervisor() {
		return immediatesupervisor;
	}

	public void setImmediatesupervisor(Employee immediatesupervisor) {
		this.immediatesupervisor = immediatesupervisor;
	}

	public Employee getPrimarycounsellor() {
		return primarycounsellor;
	}

	public void setPrimarycounsellor(Employee primarycounsellor) {
		this.primarycounsellor = primarycounsellor;
	}

	public Set<Employee> getDirectReports() {
		return directReports;
	}

	public void setDirectReports(Set<Employee> directReports) {
		this.directReports = directReports;
	}

//	public Role getRoles() {
//		return roles;
//	}

//	public void setRoles(Role roles) {
//		this.roles = roles;
//	}

	public String getImmediatesupervisorlpn() {
		return immediatesupervisorlpn;
	}

	public void setImmediatesupervisorlpn(String immediatesupervisorlpn) {
		this.immediatesupervisorlpn = immediatesupervisorlpn;
	}

	public String getPrimarycounsellorlpn() {
		return primarycounsellorlpn;
	}

	public void setPrimarycounsellorlpn(String primarycounsellorlpn) {
		this.primarycounsellorlpn = primarycounsellorlpn;
	}

}
