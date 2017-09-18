package com.ss.nitro.analytics.dyorg.domain;

import java.util.Set;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonProperty;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.NONE)
public class Employee {

	@GraphId
	@JsonProperty(value = "graph_id")
	private Long id;

	// @Indexed(indexType = IndexType.FULLTEXT, indexName = "searchByUsername")
	@JsonProperty(value = "employeename")
	private String employeename;

	// @Indexed(indexType = IndexType.FULLTEXT, indexName = "searchByUserlpn")
	@JsonProperty(value = "userlpn")
	private String userlpn;

	@JsonProperty(value = "employeeemail")
	private String employeeemail;
	
	@JsonProperty(value = "employeelocation")
	private String employeelocation;
	
	@JsonProperty(value = "employeegrade")
	private String employeegrade;

	@Fetch
	@RelatedTo(type = "REPORTS_TO", direction = Direction.OUTGOING)
	private Employee immediatesupervisor;

	@Fetch
	@RelatedTo(type = "COUNSELLED_BY", direction = Direction.OUTGOING)
	private Employee primarycounsellor;

	@Fetch
	@RelatedTo(type = "REPORTS_TO", direction = Direction.INCOMING)
	private Set<Employee> directReports;

	@Fetch
//	@JsonBackReference
	@RelatedTo(elementClass = Role.class, type = "WORKS_AS", direction = Direction.OUTGOING)
	private Set<Role> roles;

	private String immediatesupervisorlpn;

	private String primarycounsellorlpn;

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

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", employeename=" + employeename
				+ ", userlpn=" + userlpn + ", employeeemail=" + employeeemail
				+ ", employeelocation=" + employeelocation + ", employeegrade="
				+ employeegrade + ", immediatesupervisorlpn="
				+ immediatesupervisorlpn + ", primarycounsellorlpn="
				+ primarycounsellorlpn + "]";
	}

}
