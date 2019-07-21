package com.unloadbrain.assignement.payconiq.stockapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableReactiveCassandraRepositories
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Value("${app.cassandra.contactpoints}")
    private String contactPoints;

    @Value("${app.cassandra.port}")
    private int port;

    @Value("${app.cassandra.keyspace}")
    private String keyspace;

    @Value("${app.cassandra.basepackages}")
    private String basePackages;

    @Override
    protected String getKeyspaceName() {
        return keyspace;
    }

    @Override
    protected String getContactPoints() {
        return contactPoints;
    }

    @Override
    protected int getPort() {
        return port;
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[]{basePackages};
    }

    @Override
    protected boolean getMetricsEnabled() {
        return false;
    }

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        return Arrays.asList(
                CreateKeyspaceSpecification.createKeyspace(getKeyspaceName()).ifNotExists()
        );
    }
}
