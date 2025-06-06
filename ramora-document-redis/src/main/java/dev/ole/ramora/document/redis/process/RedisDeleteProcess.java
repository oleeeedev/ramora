package dev.ole.ramora.document.redis.process;

import dev.ole.ramora.RepositoryExternalEntry;
import dev.ole.ramora.document.redis.RedisFilter;
import dev.ole.ramora.document.redis.RedisProcessReference;
import dev.ole.ramora.process.kind.UpdateProcess;
import io.lettuce.core.ScanArgs;
import io.lettuce.core.ScanCursor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

@Slf4j
public final class RedisDeleteProcess extends UpdateProcess<RedisProcessReference, RedisFilter<?>> {

    @Override
    public void run(@NotNull RepositoryExternalEntry entry, @NotNull RedisProcessReference reference) {
        var pattern = entry.id() + "*";
        var cursor = ScanCursor.INITIAL;
        var scanArgs = ScanArgs.Builder.matches(pattern);


        do {
            var scan = reference.connection().connection().sync().scan(cursor, scanArgs);
            var keys = scan.getKeys();
            cursor = scan;


            if (!keys.isEmpty()) {
                reference.connection().connection().sync().del(keys.toArray(new String[0]));
                log.debug("Ramora delete redis key{}", cursor.getCursor());
            }

        } while (!cursor.isFinished());
    }
}