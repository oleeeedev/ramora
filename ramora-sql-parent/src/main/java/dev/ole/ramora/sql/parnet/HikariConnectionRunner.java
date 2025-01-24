package dev.ole.ramora.sql.parnet;

import dev.ole.ramora.process.ProcessRunner;
import dev.ole.ramora.sql.parnet.connection.HikariConnection;
import dev.ole.ramora.sql.parnet.reference.HikariProcessReference;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
public final class HikariConnectionRunner extends ProcessRunner<HikariProcessReference> {

    private final HikariConnection connection;

    @Override
    public void update(HikariProcessReference reference) {
        this.connection.update(reference);
    }

    @Contract(" -> new")
    @Override
    public @NotNull HikariProcessReference generateBase() {
        return new HikariProcessReference(this.connection);
    }

    @Override
    public void query(HikariProcessReference query) {
        this.connection.query(query);
    }
}