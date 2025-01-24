package dev.ole.ramora.sql.parnet.process;

import dev.ole.ramora.RepositoryExternalEntry;
import dev.ole.ramora.process.kind.QueryProcess;
import dev.ole.ramora.sql.parnet.HikariFilter;
import dev.ole.ramora.sql.parnet.HikariFilterUtil;
import dev.ole.ramora.sql.parnet.reference.HikariProcessReference;
import org.jetbrains.annotations.NotNull;

public final class HikariCheckProcess extends QueryProcess<Boolean, HikariProcessReference, HikariFilter<Object>> {

    private static final String CHECK_QUERY = "SELECT * FROM %s";

    @Override
    public @NotNull Boolean run(@NotNull RepositoryExternalEntry entry, @NotNull HikariProcessReference reference) {
        return reference.directly(HikariFilterUtil.appendFiltering(CHECK_QUERY.formatted(entry.id()), filters()) + " LIMIT 1;", filterArguments(), (success, data) -> success && data.getResultSet().next());
    }
}