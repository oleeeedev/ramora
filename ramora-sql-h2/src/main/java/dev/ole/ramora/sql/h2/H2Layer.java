package dev.ole.ramora.sql.h2;

import dev.ole.ramora.sql.parnet.HikariParentConnectionLayer;
import dev.ole.ramora.sql.parnet.driver.ProtocolDriver;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class H2Layer extends HikariParentConnectionLayer<H2ConnectionAuthentication> {

    public H2Layer() {
        super(new H2ConnectionAuthentication());
    }

    @Contract(value = " -> new", pure = true)
    @Override
    public @NotNull ProtocolDriver<H2ConnectionAuthentication> protocol() {
        return new H2ProtocolDriver();
    }
}