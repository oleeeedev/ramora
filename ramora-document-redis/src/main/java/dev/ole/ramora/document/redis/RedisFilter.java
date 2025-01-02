package dev.ole.ramora.document.redis;

import dev.ole.ramora.filtering.Filter;

public abstract class RedisFilter<R> extends Filter<String, R> {

    public RedisFilter(String id, R value) {
        super(id, value);
    }
}
