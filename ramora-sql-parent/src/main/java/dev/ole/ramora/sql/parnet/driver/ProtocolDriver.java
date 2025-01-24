package dev.ole.ramora.sql.parnet.driver;

import dev.ole.ramora.layer.connection.ConnectionAuthentication;

public interface ProtocolDriver<A extends ConnectionAuthentication> {

    String jdbcUrl(A credentials);

    @SuppressWarnings("unchecked")
    default String jdbcUrlBinding(ConnectionAuthentication credentials) {
        return this.jdbcUrl((A) credentials);
    }

}