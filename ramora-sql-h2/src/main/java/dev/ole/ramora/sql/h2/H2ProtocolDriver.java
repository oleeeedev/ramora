package dev.ole.ramora.sql.h2;

import dev.ole.ramora.sql.parnet.driver.ProtocolDriverLoader;
import org.h2.Driver;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public final class H2ProtocolDriver implements ProtocolDriverLoader<H2ConnectionAuthentication> {

    @Override
    public void initialize() {
        Driver.load();
    }

    @Override
    public @NotNull String jdbcUrl(@NotNull H2ConnectionAuthentication credentials) {
        return "jdbc:h2:" + Path.of(credentials.path()).toAbsolutePath() + ";AUTO_SERVER=TRUE;AUTO_RECONNECT=TRUE";
    }
}