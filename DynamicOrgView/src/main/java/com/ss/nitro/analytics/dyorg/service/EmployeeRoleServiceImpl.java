package com.ss.nitro.analytics.dyorg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ss.nitro.analytics.dyorg.dao.RoleRepository;
import com.ss.nitro.analytics.dyorg.domain.Role;

@Service("employeeRoleService")
@Transactional
public class EmployeeRoleServiceImpl implements EmployeeRoleService {

	@Autowired
	private RoleRepository roleRepository;

	public Role create(Role profile) {
		return roleRepository.save(profile);
	}

	public void delete(Role profile) {
		roleRepository.delete(profile);
	}

	public Role findById(long id) {
		return roleRepository.findOne(id);
	}

	public Iterable<Role> findAll() {
		return roleRepository.findAll();
	}

}
