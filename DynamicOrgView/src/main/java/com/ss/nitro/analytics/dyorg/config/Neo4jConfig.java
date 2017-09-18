package com.ss.nitro.analytics.dyorg.config;

import javax.inject.Inject;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableNeo4jRepositories(basePackages = "com.ss.nitro.analytics.dyorg.dao")
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
public class Neo4jConfig extends Neo4jConfiguration implements BeanFactoryAware {

	private BeanFactory beanFactory;

	@Bean(destroyMethod = "shutdown")
	public GraphDatabaseService graphDatabaseService() {
		//GraphDatabaseService graphDb = new GraphDatabaseFactory()
			//	.newEmbeddedDatabase("var/graphdb");
		return null;
	}

	@Inject
	GraphDatabase graphDatabase;

	public void setBeanFactory(BeanFactory arg0) throws BeansException {
		this.beanFactory = arg0;

	}

}
