package dev.ole.ramora.sql.parnet.process;

import dev.ole.ramora.RepositoryExternalEntry;
import dev.ole.ramora.process.kind.QueryProcess;
import dev.ole.ramora.sql.parnet.HikariFilter;
import dev.ole.ramora.sql.parnet.HikariFilterUtil;
import dev.ole.ramora.sql.parnet.reference.HikariProcessReference;
import org.jetbrains.annotations.NotNull;

public final class HikariCountProcess extends QueryProcess<Long, HikariProcessReference, HikariFilter<Object>> {

    private static final String COUNT_QUERY = "SELECT COUNT(*) AS ELEMENTS FROM %s";

    @Override
    public @NotNull Long run(@NotNull RepositoryExternalEntry entry, @NotNull HikariProcessReference reference) {
        return reference.directly(HikariFilterUtil.appendFiltering(COUNT_QUERY.formatted(entry.id()), filters()) + ";", filterArguments(), (success, data) -> {
            if (!success || !data.getResultSet().next()) {
                return -1L;
            }

            return data.getResultSet().getLong("ELEMENTS");
        });
    }
}