package dev.ole.ramora.sql.parnet.connection;

import java.sql.SQLException;

@FunctionalInterface
@Deprecated
public interface HikariConnectionFunction<I> {

    void apply(I i) throws SQLException;

}