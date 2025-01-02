package dev.ole.ramora.process;

import dev.ole.ramora.constant.ConstantPool;
import dev.ole.ramora.filtering.Filter;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.*;

@Getter
@Accessors(fluent = true)
public abstract class Process<F extends Filter<?, ?>> {

    private final List<F> filters = new ArrayList<>();
    private final ConstantPool constants = new ConstantPool();

    @SuppressWarnings("unchecked")
    public void appendFilters(List<Filter<?, ?>> filters) {
        this.filters.addAll((Collection<? extends F>) filters);
    }

    public Object[] filterArguments() {
        return filters.stream().map(Filter::value).toArray();
    }
}