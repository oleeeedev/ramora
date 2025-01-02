package dev.ole.ramora.process.kind;

import dev.ole.ramora.RepositoryExternalEntry;
import dev.ole.ramora.filtering.Filter;
import dev.ole.ramora.layer.connection.Connection;
import dev.ole.ramora.process.Process;
import dev.ole.ramora.process.ProcessReference;

public abstract class QueryProcess<T, R extends ProcessReference<? extends Connection<?, ?>>, F extends Filter<?, ?>> extends Process<F> {

    public abstract T run(RepositoryExternalEntry entry, R reference);

    @SuppressWarnings("unchecked")
    public T runMapping(RepositoryExternalEntry entry, ProcessReference<?> reference) {
        return this.run(entry, (R) reference);
    }
}