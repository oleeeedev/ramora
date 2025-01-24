package dev.ole.ramora.sql.parnet.process;

import dev.ole.ramora.RepositoryConstant;
import dev.ole.ramora.RepositoryExternalEntry;
import dev.ole.ramora.common.Reflections;
import dev.ole.ramora.external.RepositoryCollectionEntry;
import dev.ole.ramora.external.RepositoryMapEntry;
import dev.ole.ramora.filtering.Filter;
import dev.ole.ramora.process.kind.UpdateProcess;
import dev.ole.ramora.query.QueryConstant;
import dev.ole.ramora.sql.parnet.HikariFilter;
import dev.ole.ramora.sql.parnet.HikariFilterUtil;
import dev.ole.ramora.sql.parnet.reference.HikariProcessReference;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@AllArgsConstructor
public final class HikariUpdateProcess extends UpdateProcess<HikariProcessReference, HikariFilter<Object>> {

    private static final String UPDATE_VALUE = "UPDATE %s SET %s";
    private Object value;

    @Override
    public void run(@NotNull RepositoryExternalEntry entry, @NotNull HikariProcessReference reference) {
        var elements = new ArrayList<String>();
        var arguments = new ArrayList<>();

        if (entry instanceof RepositoryCollectionEntry || entry instanceof RepositoryMapEntry) {
            // delete
            var deleteProcess = new HikariDeleteProcess();

            for (var primary : entry.parent().primaries()) {
                deleteProcess.filters().add(new HikariFilter.SequenceMatchFilter(primary.id(), constants().constant(QueryConstant.PRIMARY_SHORTCUT).value(primary), "="));
            }

            for (var object : entry.readValues(value)) {
                var createProcess = new HikariCreateProcess(object);
                createProcess.constants().cloneConstants(constants(), QueryConstant.PRIMARY_SHORTCUT);
                createProcess.run(entry, reference);
            }
            deleteProcess.run(entry, reference);
            return;
        }


        for (var child : entry.children()) {
            var value = Reflections.value(child.constants().constant(RepositoryConstant.PARAM_FIELD), this.value);

            if (child instanceof RepositoryExternalEntry externalEntry) {
                var subprocess = new HikariUpdateProcess(value);
                subprocess.constants().put(QueryConstant.PRIMARY_SHORTCUT, QueryConstant.PrimaryShortCut.append(entry.primaries(), this.value));
                subprocess.run(externalEntry, reference);
                continue;
            }

            if (child.constants().has(RepositoryConstant.PRIMARY_KEY)) {
                // primaries cannot be updated
                continue;
            }

            if (child.constants().has(RepositoryConstant.VALUE_REFACTOR)) {
                value = child.constants().constant(RepositoryConstant.VALUE_REFACTOR).apply(value);
            }

            elements.add(child.id() + " = ?");
            arguments.add(value);
        }

        if (elements.isEmpty()) {
            return;
        }

        arguments.addAll(filters().stream().map(Filter::value).toList());
        reference.append(HikariFilterUtil.appendFiltering(UPDATE_VALUE.formatted(entry.id(), String.join(", ", elements)), filters()) + ";", arguments.toArray());
    }
}