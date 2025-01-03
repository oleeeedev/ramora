package dev.ole.ramora.document.redis.process;

import dev.ole.ramora.RepositoryExternalEntry;
import dev.ole.ramora.document.redis.RedisFilter;
import dev.ole.ramora.document.redis.RedisProcessReference;
import dev.ole.ramora.process.kind.QueryProcess;
import io.lettuce.core.ScanArgs;
import io.lettuce.core.ScanCursor;
import org.jetbrains.annotations.NotNull;

public final class RedisCountProcess extends QueryProcess<Long, RedisProcessReference, RedisFilter<Object>> {

    @Override
    public Long run(@NotNull RepositoryExternalEntry entry, @NotNull RedisProcessReference reference) {
        var pattern = entry.id() + "*";
        var scanArgs = ScanArgs.Builder.matches(pattern);
        var scanCursor = ScanCursor.INITIAL;
        var keyCount = 0L;

        do {
            var cursor = reference.connection().connection().sync().scan(scanCursor, scanArgs);
            keyCount += cursor.getKeys().size();
            scanCursor = cursor;
        } while (!scanCursor.isFinished());

        return keyCount;
    }
}