package com.ss.nitro.analytics.dyorg.controllers;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.ss.nitro.analytics.dyorg.domain.Employee;
import com.ss.nitro.analytics.dyorg.domain.Role;
import com.ss.nitro.analytics.dyorg.dto.EmployeeCsv;
import com.ss.nitro.analytics.dyorg.dto.EmployeeDTO;
import com.ss.nitro.analytics.dyorg.dto.EmployeeDetailedDTO;
import com.ss.nitro.analytics.dyorg.dto.EmployeeTreeDTO;
import com.ss.nitro.analytics.dyorg.dto.EmployeeDTO.Children1;
import com.ss.nitro.analytics.dyorg.dto.EmployeeDTO.Children1.Children2;
import com.ss.nitro.analytics.dyorg.dto.EmployeeDTO.Children1.Children2.Children;
import com.ss.nitro.analytics.dyorg.dto.EmployeeTreeDTO.Children3;
import com.ss.nitro.analytics.dyorg.service.EmployeeProfileService;
import com.ss.nitro.analytics.dyorg.service.EmployeeRoleService;
import com.ss.nitro.analytics.dyorg.utility.AttributeCopier;

@RestController
@RequestMapping("/dynamicorg")
@ComponentScan("com.ss.nitro.analytics.dyorg.service")
public class UserController {

	@Autowired
	private EmployeeProfileService employeeService;

	@Autowired
	private EmployeeRoleService employeeRoleService;

	@Autowired
	private Neo4jTemplate template;

	AttributeCopier copier = new AttributeCopier();

	@RequestMapping(value = "/testService", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String testService() {
		return "Yes Sir !!";
	}

	@RequestMapping(value = "/findemployeebyid", produces = "application/json")
	@ResponseBody
	@Transactional
	public String findByGraphId(@RequestParam("graph_id") String graph_id)
			throws Exception {

		EmployeeDetailedDTO employeeDetails = new EmployeeDetailedDTO();
		ObjectMapper mapper = new ObjectMapper();
		Employee employeeData = employeeService.findById(Long
				.parseLong(graph_id));
		copier.copyAttribute(employeeData, employeeDetails);
		String jsonString = mapper.writeValueAsString(employeeDetails);
		System.out.println("employee detail ---->" + jsonString);
		return jsonString;
	}

	@RequestMapping(value = "/allusers", produces = "application/json")
	@ResponseBody
	@Transactional
	public String findAllEmployees() throws Exception {

		System.out.println("inside service allusers");
		return getEmployeeListFromFile();
		/*
		 * Iterable<Employee> employeeData = employeeService.findAll();
		 * List<Employee> emplyeeList = new ArrayList<Employee>(); ObjectMapper
		 * mapper = new ObjectMapper(); int i = 0; // @TODO for (Employee
		 * employee : employeeData) { // if (i > 10) // break;
		 * emplyeeList.add(employee); i++; } String jsonString =
		 * mapper.writeValueAsString(emplyeeList);
		 * System.out.println("all employee details ---->" + " count = " + i +
		 * " value --> " + jsonString); return jsonString;
		 */
	}

	@RequestMapping(value = "/roleemployeemapping", produces = "application/json")
	@ResponseBody
	@Transactional
	public String findAllRolesAndEmployee() throws JsonGenerationException,
			JsonMappingException, IOException {
		Map<String, Set<Employee>> allEmploForRole = new HashMap<String, Set<Employee>>();
		Iterable<Role> sample = employeeRoleService.findAll();
		int i = 0;
		for (Role roletype : sample) {
			if (i == 1)
				break;
			Set<Employee> values = getTemplate().fetch(roletype.getEmployees());
			if (values.size() > 0) {
				i++;
				allEmploForRole.put(roletype.getName(), values);
			}
		}
		return createJson(allEmploForRole);
	}

	@RequestMapping(value = "/findemployeetreebyid", produces = "application/json")
	@ResponseBody
	@Transactional
	public String findEmployeeTreeById(@RequestParam("graph_id") String graph_id)
			throws Exception {
		// String graph_id = "7482";
		Employee employeeData = employeeService.findById(Long
				.parseLong(graph_id));
		return createEmployeeTreeJson(employeeData);

	}

	@RequestMapping(value = "/allusersascsv", produces = { "text/html",
			"application/vnd.ms-excel", "text/csv" })
	@ResponseBody
	@Transactional
	public String getAllEmployee() throws Exception {
		Iterable<Employee> employeeData = employeeService.findAll();
		List<Employee> emplyeeList = new ArrayList<Employee>();
		ObjectMapper mapper = new ObjectMapper();
		for (Employee employee : employeeData) {
			emplyeeList.add(employee);
		}
		return convertoCSV(emplyeeList);

	}

	private String convertoCSV(List<Employee> emplyeeList)
			throws JsonProcessingException, IOException {
		EmployeeCsv user;
		List<EmployeeCsv> userList = new ArrayList<EmployeeCsv>();
		List<EmployeeCsv> completeUserList = new ArrayList<EmployeeCsv>();
		for (Employee employee : emplyeeList) {
			user = new EmployeeCsv();
			user.setEmployee(employee.getEmployeename());
			user.setLocation(employee.getEmployeelocation());
			user.setGrade(employee.getEmployeegrade());
			user.setGraphId(employee.getId().toString());
			userList.add(user);
		}
		completeUserList.addAll(userList);
		com.fasterxml.jackson.databind.ObjectMapper objmapper = new com.fasterxml.jackson.databind.ObjectMapper();
		CsvMapper mapper = new CsvMapper();
		CsvSchema schema1 = mapper.schemaFor(EmployeeCsv.class);
		schema1 = schema1.withColumnSeparator(',');
		// String result =
		// mapper.reader(EmployeeCsv.class).with(schema1).readValue(objmapper.writeValueAsString(completeUserList));
		String result = mapper.writer(schema1).writeValueAsString(
				completeUserList);
		return result;
	}

	private String createEmployeeTreeJson(Employee employeeData)
			throws JsonGenerationException, JsonMappingException, IOException {
		Employee employeeCouncellor = employeeData.getPrimarycounsellor();
		Employee employeeSupervisor = employeeData.getImmediatesupervisor();
		Set<Employee> reportess = employeeData.getDirectReports();
		List<EmployeeTreeDTO.Children3> employeeChildren = new ArrayList<EmployeeTreeDTO.Children3>();
		if (employeeCouncellor != null || employeeSupervisor != null) {
			Children3 child1 = new Children3(
					employeeSupervisor.getEmployeename(), employeeSupervisor
							.getId().toString(), "Immediate Supervisor",
					employeeSupervisor.getEmployeegrade(),
					employeeSupervisor.getEmployeeemail());
			Children3 child2 = new Children3(
					employeeCouncellor.getEmployeename(), employeeCouncellor
							.getId().toString(), "Primary Councellor",
					employeeCouncellor.getEmployeegrade(),
					employeeCouncellor.getEmployeeemail());
			employeeChildren.add(child1);
			employeeChildren.add(child2);
		}
		Children3 child3 = null;
		if (reportess.size() > 0) {
			for (Employee reporte : reportess) {
				child3 = new Children3(reporte.getEmployeename(), reporte
						.getId().toString(), "Direct Reportee",
						reporte.getEmployeegrade(), reporte.getEmployeeemail());
				employeeChildren.add(child3);
			}
		}
		EmployeeTreeDTO employeeTree = new EmployeeTreeDTO(
				employeeData.getEmployeename(),
				employeeChildren.toArray(new Children3[employeeChildren.size()]),
				employeeData.getEmployeegrade(), employeeData
						.getEmployeeemail(),employeeData.getEmployeegrade());
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(employeeTree);
		System.out.println("Tree Json Generated-- ->" + jsonString);
		return jsonString.replace("children3", "children");

	}

	private String createJson(Map<String, Set<Employee>> allEmploForRole)
			throws JsonGenerationException, JsonMappingException, IOException {

		List<EmployeeDTO.Children1.Children2.Children> children = new ArrayList<EmployeeDTO.Children1.Children2.Children>();
		List<EmployeeDTO.Children1.Children2> children2 = new ArrayList<EmployeeDTO.Children1.Children2>();
		Iterator iterator = allEmploForRole.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry mapEntry = (Map.Entry) iterator.next();
			String roleName = mapEntry.getKey().toString();
			Set<Employee> employess = (Set<Employee>) mapEntry.getValue();
			for (Employee employee : employess)
				children.add(new Children(employee.getEmployeename(), 590));
			Children[] samples = (Children[]) children
					.toArray(new Children[children.size()]);
			children2.add(new Children2(roleName, samples));
		}
		EmployeeDTO.Children1 sample1 = new Children1(
				"Roles",
				(Children2[]) children2.toArray(new Children2[children2.size()]));
		List<EmployeeDTO.Children1> children1 = new ArrayList<EmployeeDTO.Children1>();
		children1.add(sample1);
		EmployeeDTO sample = new EmployeeDTO(
				"CompanyHierarchy",
				(Children1[]) children1.toArray(new Children1[children1.size()]));
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(sample);
		System.out.println("All Role - Employee - Json Generated--->"
				+ jsonString.replace("children1", "children").replace(
						"children2", "children"));
		return jsonString;
	}

	public EmployeeProfileService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(EmployeeProfileService employeeService) {
		this.employeeService = employeeService;
	}

	public EmployeeRoleService getEmployeeRoleService() {
		return employeeRoleService;
	}

	public void setEmployeeRoleService(EmployeeRoleService employeeRoleService) {
		this.employeeRoleService = employeeRoleService;
	}

	public Neo4jTemplate getTemplate() {
		return template;
	}

	public void setTemplate(Neo4jTemplate template) {
		this.template = template;
	}

	public enum UserGrade {
		Manager("Manager", "26"), Sr_Manager("Sr Manager", "32"), Partner(
				"Partner", "60"), Executive_Director("Executive Director", "40"), Director_GSS(
				"Director GSS", "40"), Associate_Director("Associate Director",
				"30"), Assistant_Director("Assistant Director", "25"), Supervising_Associate(
				"Supervising Associate", "15"), Supervising_Associate_EA(
				"Supervising Associate EA", "15"), Senior_Associate(
				"Senior Associate", "12"), Senior_Associate_DS(
				"Senior Associate DS", "12"), Senior_Associate_EA(
				"Senior Associate EA", "12"), Staff("Staff", "5"), Associate(
				"Associate", "5"), Associate_PA("Associate PA", "5"), Associate_DS(
				"Associate DS", "5"), Associate_EA("Associate EA", "5"), Associate_Grade1(
				"Associate Grade1", "5"), Associate_Grade2("Associate Grade2",
				"5"), Trainee("Trainee", "3"), Trainee_KPS("Trainee KPS", "3"), Intern(
				"Intern", "2"), Senior("Senior", "16");

		private String grade;
		private String compValue;

		private UserGrade(String grade, String compValue) {
			this.grade = grade;
			this.compValue = compValue;

		}

		public String getGrade() {
			return grade;
		}

		public void setGrade(String grade) {
			this.grade = grade;
		}

		public String getCompValue() {
			return compValue;
		}

		public void setCompValue(String compValue) {
			this.compValue = compValue;
		}

	}

	public static String getStringValueFromEnum(String checkValue) {
		for (UserGrade status : UserGrade.values()) {
			if (status.getGrade().equalsIgnoreCase(checkValue)) {
				return status.getCompValue();
			}
		}
		// throw an IllegalArgumentException or return null
		throw new IllegalArgumentException(
				"the given value doesn't match any Status.");
	}

	private String getEmployeeListFromFile() {
		ClassLoader classLoader = getClass().getClassLoader();
		JSONParser parser = new JSONParser();
		String employeeList = null;
		try {
			Object obj = parser.parse(new FileReader(classLoader.getResource(
					"employeeList.json").getFile()));
			JSONArray jsonObject = (JSONArray) obj;
			System.out.println(" - >" + jsonObject.toJSONString());
			employeeList = jsonObject.toJSONString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return employeeList;

	}
}
