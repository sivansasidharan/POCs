package com.ss.nitro.analytics.dyorg.dto;

import org.codehaus.jackson.annotate.JsonProperty;

public final class EmployeeTreeDTO {

	private String name;

	private String employeeRole;

	private String employeeEmail;

	private String employeeGrade;

	private Children3[] children;

	public EmployeeTreeDTO(String name, Children3[] children,
			String employeeRole, String employeeEmail, String employeeGrade) {
		this.name = name;
		this.children = children;
		this.employeeRole = employeeRole;
		this.employeeEmail = employeeEmail;
		this.employeeGrade = employeeGrade;
	}

	public String getEmployeeGrade() {
		return employeeGrade;
	}

	public void setEmployeeGrade(String employeeGrade) {
		this.employeeGrade = employeeGrade;
	}

	public String getEmployeeRole() {
		return employeeRole;
	}

	public void setEmployeeRole(String employeeRole) {
		this.employeeRole = employeeRole;
	}

	public String getEmployeeEmail() {
		return employeeEmail;
	}

	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Children3[] getChildren() {
		return children;
	}

	public void setChildren(Children3[] children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "ClassPojo [name = " + name + ", children = " + children + "]";
	}

	public static final class Children3 {
		private String name;

		private String graphId;

		private String employeeGrade;

		private String employeeRole;

		private String employeeEmail;

		public String getEmployeeGrade() {
			return employeeGrade;
		}

		public void setEmployeeGrade(String employeeGrade) {
			this.employeeGrade = employeeGrade;
		}

		public String getGraphId() {
			return graphId;
		}

		public void setGraphId(String graphId) {
			this.graphId = graphId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getEmployeeRole() {
			return employeeRole;
		}

		public void setEmployeeRole(String employeeRole) {
			this.employeeRole = employeeRole;
		}

		public String getEmployeeEmail() {
			return employeeEmail;
		}

		public void setEmployeeEmail(String employeeEmail) {
			this.employeeEmail = employeeEmail;
		}

		public Children3(@JsonProperty("name") String name,
				@JsonProperty("graphid") String graphid,
				@JsonProperty("employeeGrade") String relation,
				@JsonProperty("employeeRole") String employeeRole,
				@JsonProperty("employeeEmail") String employeeEmail) {
			this.name = name;
			this.graphId = graphid;
			this.employeeGrade = relation;
			this.employeeRole = employeeRole;
			this.employeeEmail = employeeEmail;
		}

		@Override
		public String toString() {
			return "ClassPojo [name = " + name + "]";
		}
	}

}
