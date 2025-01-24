package dev.ole.ramora.sql.parnet.process;

import dev.ole.ramora.RepositoryExternalEntry;
import dev.ole.ramora.process.kind.UpdateProcess;
import dev.ole.ramora.sql.parnet.HikariFilter;
import dev.ole.ramora.sql.parnet.HikariFilterUtil;
import dev.ole.ramora.sql.parnet.reference.HikariProcessReference;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;

public final class HikariDeleteProcess extends UpdateProcess<HikariProcessReference, HikariFilter<Object>> {

    private static final String DELETE_SQL = "DELETE FROM %s";

    @Override
    public void run(@NotNull RepositoryExternalEntry entry, @NotNull HikariProcessReference reference) {
        boolean success = reference.directly(HikariFilterUtil.appendFiltering(DELETE_SQL.formatted(entry.id()), filters()) + ";", filterArguments(), (querySuccess, data) -> querySuccess && data.getUpdateCount() > 0);

        // todo return maybe back
    }
}