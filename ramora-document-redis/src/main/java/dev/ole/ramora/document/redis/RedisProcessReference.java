package dev.ole.ramora.document.redis;

import dev.ole.ramora.document.redis.connection.RedisConnection;
import dev.ole.ramora.process.ProcessReference;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public final class RedisProcessReference extends ProcessReference<RedisConnection> {

    public RedisProcessReference(RedisConnection connection) {
        super(connection);
    }
}
