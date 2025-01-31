package dev.ole.ramora.sql.parnet;

import dev.ole.ramora.filtering.Filter;
import dev.ole.ramora.filtering.FilterHandler;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class HikariFilterHandler implements FilterHandler<String, Object> {

    @Contract("_, _ -> new")
    @Override
    public @NotNull Filter<String, Object> match(String id, Object value) {
        return new HikariFilter.SequenceMatchFilter(id, value, "=");
    }

    @Contract("_, _ -> new")
    @Override
    public @NotNull Filter<String, Object> noneMatch(String id, Object value) {
        return new HikariFilter.SequenceMatchFilter(id, value, "!=");
    }

    @Contract("_, _ -> new")
    @Override
    public @NotNull Filter<String, Object> like(String id, String value) {
        return new HikariFilter.Like(id, value);
    }

    @Override
    public Filter<String, Object> matchIgnoreCase(String id, String value) {
        return this.match(id, value);
    }

    @Override
    public Filter<String, Object> between(String id, Number min, Number max) {
        return null;
    }

    @Override
    public Filter<String, Object> min(String id, Number min) {
        return new HikariFilter.SequenceMatchFilter(id, min, ">=");
    }

    @Override
    public Filter<String, Object> max(String id, Number max) {
        return new HikariFilter.SequenceMatchFilter(id, max, "<=");
    }
}