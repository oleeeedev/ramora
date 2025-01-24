package dev.ole.ramora.sql.parnet.process;

import dev.ole.ramora.RepositoryExternalEntry;
import dev.ole.ramora.process.kind.QueryProcess;
import dev.ole.ramora.sql.parnet.HikariFilter;
import dev.ole.ramora.sql.parnet.HikariFilterUtil;
import dev.ole.ramora.sql.parnet.reference.HikariProcessReference;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
public final class HikariMathProcess<T> extends QueryProcess<Object, HikariProcessReference, HikariFilter<Object>> {

    private static final String MATH_QUERY = "SELECT %s AS data FROM %s";
    private String type;
    private T defaultValue;

    @Override
    public @NotNull Object run(@NotNull RepositoryExternalEntry entry, @NotNull HikariProcessReference reference) {
        return reference.directly(HikariFilterUtil.appendFiltering(MATH_QUERY.formatted(type, entry.id()), filters()) + ";", filterArguments(), (success, data) -> {
            if (!success || !data.getResultSet().next()) {
                return null;
            }

            var result = data.getResultSet();

            return result.getObject("data") != null ? result.getObject("data") : defaultValue;
        });
    }
}