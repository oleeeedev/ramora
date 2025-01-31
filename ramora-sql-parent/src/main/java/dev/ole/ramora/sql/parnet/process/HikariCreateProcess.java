package dev.ole.ramora.sql.parnet.process;

import dev.ole.ramora.RepositoryConstant;
import dev.ole.ramora.RepositoryExternalEntry;
import dev.ole.ramora.common.Pair;
import dev.ole.ramora.common.Reflections;
import dev.ole.ramora.external.RepositoryCollectionEntry;
import dev.ole.ramora.external.RepositoryMapEntry;
import dev.ole.ramora.process.kind.UpdateProcess;
import dev.ole.ramora.query.QueryConstant;
import dev.ole.ramora.sql.parnet.HikariFilter;
import dev.ole.ramora.sql.parnet.reference.HikariProcessReference;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@AllArgsConstructor
public final class HikariCreateProcess extends UpdateProcess<HikariProcessReference, HikariFilter<Object>> {

    private static final String CREATE_VALUE_SQL = "INSERT INTO %s (%s) VALUES (%s);";
    private final Object value;

    @Override
    @SneakyThrows
    public void run(@NotNull RepositoryExternalEntry entry, HikariProcessReference reference) {
        var elements = new ArrayList<String>();
        var arguments = new ArrayList<>();

        // Since lists can also have individual attributes as types, we have to check whether these are present,
        // otherwise serialization can be carried out as normal.
        if (entry instanceof RepositoryCollectionEntry collectionEntry && !(collectionEntry.typeEntry() instanceof RepositoryExternalEntry)) {
            elements.add(collectionEntry.typeEntry().id());
            arguments.add(value);
            // maps are specific parameters there have not 1 value, rather more.
        } else if (entry instanceof RepositoryMapEntry mapEntry && !(mapEntry.keyEntry() instanceof RepositoryExternalEntry && mapEntry.valueEntry() instanceof RepositoryExternalEntry)) {
            var keyEntry = mapEntry.keyEntry();
            var valueEntry = mapEntry.valueEntry();

            elements.add(keyEntry.id());
            elements.add(valueEntry.id());

            var data = (Pair<?, ?>) value;

            arguments.add(keyEntry.constants().has(RepositoryConstant.VALUE_REFACTOR) ? keyEntry.constants().constant(RepositoryConstant.VALUE_REFACTOR).apply(data.first()) : data.first());
            arguments.add(valueEntry.constants().has(RepositoryConstant.VALUE_REFACTOR) ? valueEntry.constants().constant(RepositoryConstant.VALUE_REFACTOR).apply(data.second()) : data.second());
        } else {
            for (var child : entry.children()) {
                var childValue = Reflections.value(child.constants().constant(RepositoryConstant.PARAM_FIELD), value);
                if (child instanceof RepositoryExternalEntry externalEntry) {
                    for (var object : externalEntry.readValues(childValue)) {
                        var subprocess = new HikariCreateProcess(object);

                        // we put all parent primaries in the next process
                        subprocess.constants().put(QueryConstant.PRIMARY_SHORTCUT, QueryConstant.PrimaryShortCut.append(entry.primaries(), value));

                        // append the sub process
                        subprocess.run(externalEntry, reference);
                    }
                } else {
                    elements.add(child.id());

                    if (child.constants().has(RepositoryConstant.VALUE_REFACTOR)) {
                        arguments.add(child.constants().constant(RepositoryConstant.VALUE_REFACTOR).apply(childValue));
                    } else {
                        arguments.add(childValue);
                    }
                }
            }
        }

        if (entry.constants().has(RepositoryConstant.FOREIGN_REFERENCE)) {
            for (var foreignKey : entry.constants().constant(RepositoryConstant.FOREIGN_REFERENCE)) {
                arguments.add(0, constants().constant(QueryConstant.PRIMARY_SHORTCUT).value(foreignKey));
                elements.add(0, foreignKey.id());
            }
        }
        reference.append(CREATE_VALUE_SQL.formatted(entry.id(), String.join(", ", elements), String.join(", ", "?".repeat(elements.size()).split(""))), arguments.toArray());
    }
}