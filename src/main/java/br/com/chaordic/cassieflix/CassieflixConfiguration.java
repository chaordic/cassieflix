package br.com.chaordic.cassieflix;

import io.dropwizard.Configuration;
import br.com.chaordic.cassieflix.core.cassandra.CassandraInitializer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.internal.NotNull;

public class CassieflixConfiguration extends Configuration {
    @JsonProperty("cassandra")
    @NotNull   
    private CassandraInitializer cassandraInitializer;

    public CassandraInitializer getCassandraInitializer() {
        return cassandraInitializer;
    }

    public void setCassandraInitializer(CassandraInitializer cassandraInitializer) {
        this.cassandraInitializer = cassandraInitializer;
    }
}
