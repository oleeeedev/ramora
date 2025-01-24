package dev.ole.ramora.sql.parnet.process;

import dev.ole.ramora.RepositoryConstant;
import dev.ole.ramora.RepositoryExternalEntry;
import dev.ole.ramora.process.kind.UpdateProcess;
import dev.ole.ramora.sql.parnet.HikariFilter;
import dev.ole.ramora.sql.parnet.HikariParentConnectionLayer;
import dev.ole.ramora.sql.parnet.HikariRepositoryConstant;
import dev.ole.ramora.sql.parnet.reference.HikariProcessReference;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@AllArgsConstructor
public final class HikariPreppedProcess extends UpdateProcess<HikariProcessReference, HikariFilter<Object>> {

    private static final String TABLE_CREATE_QUERY = "CREATE TABLE IF NOT EXISTS %s (%s);";
    private static final String TABLE_VALUE_FORMAT = "%s %s";
    private static final String FOREIGN_FORMAT = "FOREIGN KEY (%s) REFERENCES %s(%s) ON DELETE CASCADE";
    private static final String PRIMARY_FORMAT = "PRIMARY KEY (%s)";

    // we need the layer here because we must detect different types for different sql platforms
    private final HikariParentConnectionLayer<?> layer;

    @Override
    public void run(@NotNull RepositoryExternalEntry entry, HikariProcessReference reference) {
        var elements = new ArrayList<String>();
        var primaries = new ArrayList<String>();

        for (var child : entry.children()) {

            if (child instanceof RepositoryExternalEntry externalEntry) {
                this.run(externalEntry, reference);
                continue;
            }

            if (child.constants().has(RepositoryConstant.PRIMARY_KEY)) {
                primaries.add(child.id());
            }

            // on first initialization, we put the sql type into the constants
            var type = child.constants().put(HikariRepositoryConstant.SQL_TYPE, layer.detector().detect(child));
            elements.add(TABLE_VALUE_FORMAT.formatted(child.id(), type));
        }

        if (!primaries.isEmpty()) {
            elements.add(PRIMARY_FORMAT.formatted(String.join(", ", primaries)));
        }

        if (entry.constants().has(RepositoryConstant.FOREIGN_REFERENCE)) {
            for (var foreignKey : entry.constants().constant(RepositoryConstant.FOREIGN_REFERENCE)) {
                // for a better format we put all primaries oder foreign keys in front of the sql entries
                elements.add(0, TABLE_VALUE_FORMAT.formatted(foreignKey.id(), foreignKey.constants().constant(HikariRepositoryConstant.SQL_TYPE)));
                // last we add the foreign constraint
                elements.add(FOREIGN_FORMAT.formatted(foreignKey.id(), foreignKey.parent().id(), foreignKey.id()));
            }
        }
        reference.append(TABLE_CREATE_QUERY.formatted(entry.id(), String.join(", ", elements)));
    }
}