package dev.ole.ramora.process.kind;

import dev.ole.ramora.RepositoryExternalEntry;
import dev.ole.ramora.filtering.Filter;
import dev.ole.ramora.layer.connection.Connection;
import dev.ole.ramora.process.Process;
import dev.ole.ramora.process.ProcessReference;

public abstract class UpdateProcess<R extends ProcessReference<? extends Connection<?, ?>>, F extends Filter<?, ?>> extends Process<F> {

    public abstract void run(RepositoryExternalEntry entry, R reference);

    @SuppressWarnings("unchecked")
    public void runMapping(RepositoryExternalEntry entry, ProcessReference<?> reference) {
        run(entry, (R) reference);
    }
}