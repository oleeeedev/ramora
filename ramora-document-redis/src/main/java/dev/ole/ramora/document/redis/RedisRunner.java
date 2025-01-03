package dev.ole.ramora.document.redis;

import dev.ole.ramora.document.redis.connection.RedisConnection;
import dev.ole.ramora.process.ProcessRunner;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
public final class RedisRunner extends ProcessRunner<RedisProcessReference> {

    private RedisConnection connection;

    @Contract(" -> new")
    @Override
    public @NotNull RedisProcessReference generateBase() {
        return new RedisProcessReference(connection);
    }

    @Override
    protected void query(RedisProcessReference query) {

    }

    @Override
    protected void update(RedisProcessReference query) {

    }
}