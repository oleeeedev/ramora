package dev.ole.ramora.sql.parnet.driver;

import dev.ole.ramora.layer.connection.ConnectionAuthentication;

public interface ProtocolDriverLoader<CRE extends ConnectionAuthentication> extends ProtocolDriver<CRE> {

    void initialize();

}