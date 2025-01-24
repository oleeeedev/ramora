package dev.ole.ramora.sql.parnet.reference;

import dev.ole.ramora.process.ProcessReference;
import dev.ole.ramora.sql.parnet.connection.HikariConnection;
import dev.ole.ramora.sql.parnet.connection.HikariConnectionFunction;
import dev.ole.ramora.sql.parnet.connection.HikariStatementBuilder;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.sql.ResultSet;
import java.util.*;

@Getter
@Accessors(fluent = true)
public final class HikariProcessReference extends ProcessReference<HikariConnection> {

    private final List<HikariReferenceData<ResultSet>> sqlQueries = new ArrayList<>();

    public HikariProcessReference(HikariConnection connection) {
        super(connection);
    }

    public <R> R directly(String query, Object[] arguments, HikariStatementBuilder<R> consumer) {
        return this.connection().query(query, arguments, consumer);
    }

    @Deprecated
    public HikariProcessReference append(String query, Object[] parameters, HikariConnectionFunction<ResultSet> consumer) {
        this.sqlQueries.add(new HikariReferenceData<>(query, parameters, consumer));
        return this;
    }

    @Deprecated
    public HikariProcessReference append(String query, Object[] parameters) {
        return this.append(query, parameters, resultSet -> {
        });
    }

    @Deprecated
    public HikariProcessReference append(String query) {
        return this.append(query, new Object[0]);
    }

}