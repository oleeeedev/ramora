package dev.ole.ramora.document.redis.process;

import dev.ole.ramora.RepositoryConstant;
import dev.ole.ramora.RepositoryExternalEntry;
import dev.ole.ramora.common.Reflections;
import dev.ole.ramora.document.redis.RedisFilter;
import dev.ole.ramora.document.redis.RedisProcessReference;
import dev.ole.ramora.process.kind.UpdateProcess;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
public final class RedisCreateProcess extends UpdateProcess<RedisProcessReference, RedisFilter<?>> {

    private final Object value;

    @Override
    public void run(@NotNull RepositoryExternalEntry entry, @NotNull RedisProcessReference reference) {
        var connection = reference.connection().connection();
        var executor = connection.sync();

        var primaryPattern = ":[" + String.join(",", entry.primaries().stream().map(it -> it.id() + "=" + Reflections.value(it.constants().constant(RepositoryConstant.PARAM_FIELD), value).toString()).toList()) + "]";

        for (var child : entry.children()) {
            var childValue = Reflections.value(child.constants().constant(RepositoryConstant.PARAM_FIELD), value);
            if (child instanceof RepositoryExternalEntry externalEntry) {
                //todo
                return;
            }

            executor.hset(entry.id() + primaryPattern, child.id(), childValue == null ? null : childValue.toString());
        }
    }
}
