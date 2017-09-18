package com.ss.nitro.analytics.dyorg.dao;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.ss.nitro.analytics.dyorg.domain.Role;

/**
 * @author sivan.sasidharan
 *
 */
public interface RoleRepository extends GraphRepository<Role> {

}
