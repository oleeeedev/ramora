package dev.ole.ramora.sql.parnet.reference;

import dev.ole.ramora.sql.parnet.connection.HikariConnectionFunction;

public record HikariReferenceData<R>(String query, Object[] values, HikariConnectionFunction<R> consumer) {

}