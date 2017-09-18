package com.ss.nitro.analytics.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.ss.nitro.analytics.dyorg.dao.RoleRepository;
import com.ss.nitro.analytics.dyorg.domain.Employee;
import com.ss.nitro.analytics.dyorg.domain.Role;
import com.ss.nitro.analytics.dyorg.dto.EmployeeDTO;
import com.ss.nitro.analytics.dyorg.dto.EmployeeDTO.Children1;
import com.ss.nitro.analytics.dyorg.dto.EmployeeDTO.Children1.Children2;
import com.ss.nitro.analytics.dyorg.dto.EmployeeDTO.Children1.Children2.Children;
import com.ss.nitro.analytics.dyorg.service.EmployeeRoleService;
import com.ss.nitro.analytics.dyorg.utility.AttributeCopier;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
public class EmployeeRoleTransactionalTest {

	@Autowired
	EmployeeRoleService service;

	@Autowired
	RoleRepository repository;

	@Autowired
	private Neo4jTemplate template;

	public Neo4jTemplate getTemplate() {
		return template;
	}

	public void setTemplate(Neo4jTemplate template) {
		this.template = template;
	}

	private AttributeCopier attributeCopier = new AttributeCopier();

	@Test
	@Transactional
	public void findAllEmployeRoles() throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> jsonToBeUpdated = new HashMap<String, Object>();
		Map<String, Set<Employee>> allEmploForRole = new HashMap<String, Set<Employee>>();
		Iterable<Role> sample = service.findAll();
		for (Role roletype : sample) {
			Set<Employee> values = getTemplate().fetch(roletype.getEmployees());
			if (values.size() > 0) {
				jsonToBeUpdated.put("children", values);
				allEmploForRole.put(roletype.getName(), values);
				String jsonString = mapper.writeValueAsString(jsonToBeUpdated);
				// System.out.println("Json Generated--->" + jsonString);
			}
		}
		createJson(allEmploForRole);
	}

	public void createJson(Map<String, Set<Employee>> allEmploForRole)
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
		System.out.println("Json Generated-999-->" + jsonString);
	}
}
