package com.unloadbrain.assignement.payconiq.stockapi;

import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CassandraTestConfig extends AbstractCassandraConfiguration {

    @Bean
    public CassandraClusterFactoryBean cluster() {

        try {

            EmbeddedCassandraServerHelper.startEmbeddedCassandra(
                    EmbeddedCassandraServerHelper.DEFAULT_CASSANDRA_YML_FILE, 1000000L);

            EmbeddedCassandraServerHelper.getCluster()
                    .getConfiguration()
                    .getSocketOptions()
                    .setReadTimeoutMillis(1000000);

            EmbeddedCassandraServerHelper.getSession();

        } catch (Exception e) {
            throw new RuntimeException("Can't start Embedded Cassandra", e);
        }
        return super.cluster();
    }

    @Override
    protected int getPort() {
        return 9142;
    }

    @Override
    protected boolean getMetricsEnabled() {
        return false;
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        return Arrays.asList(
                CreateKeyspaceSpecification.createKeyspace(getKeyspaceName()).ifNotExists()
        );
    }

    @Override
    protected String getKeyspaceName() {
        return "stockapi";
    }
}