package dev.ole.ramora.layer;

import dev.ole.ramora.Repository;
import dev.ole.ramora.filtering.FilterHandler;
import dev.ole.ramora.layer.connection.Connection;
import dev.ole.ramora.process.ProcessReference;

public abstract class PreppedLayer<Q extends ProcessReference<? extends Connection<?, ?>>> extends Layer<Q> {

    public PreppedLayer(String id, FilterHandler<?, ?> filterHandler) {
        super(id, filterHandler);
    }

    public abstract void prepped(Repository<?> repository);

}