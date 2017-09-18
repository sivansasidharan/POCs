package com.ss.nitro.analytics.dyorg.service;

import com.ss.nitro.analytics.dyorg.domain.Role;

public interface EmployeeRoleService {

	Role create(Role profile);

	void delete(Role profile);

	Role findById(long id);

	Iterable<Role> findAll();
}
