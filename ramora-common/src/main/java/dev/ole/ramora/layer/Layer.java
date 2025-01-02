package dev.ole.ramora.layer;

import dev.ole.ramora.Repository;
import dev.ole.ramora.filtering.FilterHandler;
import dev.ole.ramora.layer.connection.Connection;
import dev.ole.ramora.process.ProcessReference;
import dev.ole.ramora.process.ProcessRunner;
import dev.ole.ramora.query.QueryMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@AllArgsConstructor
@Accessors(fluent = true)
public abstract class Layer<Q extends ProcessReference<? extends Connection<?, ?>>> {

    // general auth binding id
    private final String id;

    // executor for every process
    private final ProcessRunner<Q> runner = generateRunner();

    private final FilterHandler<?, ?> filterHandler;

    protected abstract ProcessRunner<Q> generateRunner();

    public abstract <T> QueryMethod<T> queryMethod(Repository<?> repository);

}