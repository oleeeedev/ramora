package dev.ole.ramora.document.redis.process;

import dev.ole.ramora.RepositoryExternalEntry;
import dev.ole.ramora.document.redis.RedisFilter;
import dev.ole.ramora.document.redis.RedisProcessReference;
import dev.ole.ramora.process.kind.QueryProcess;

import java.util.List;

public final class RedisMathProcess extends QueryProcess<Object, RedisProcessReference, RedisFilter<Object>> {
    @Override
    public Object run(RepositoryExternalEntry entry, RedisProcessReference reference) {




        return List.of();
    }
}