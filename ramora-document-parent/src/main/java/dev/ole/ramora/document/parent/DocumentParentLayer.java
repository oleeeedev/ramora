package dev.ole.ramora.document.parent;

import dev.ole.ramora.Repository;
import dev.ole.ramora.document.parent.reference.DocumentProcessReference;
import dev.ole.ramora.filtering.FilterHandler;
import dev.ole.ramora.layer.Layer;
import dev.ole.ramora.process.ProcessRunner;
import dev.ole.ramora.query.QueryMethod;

public abstract class DocumentParentLayer extends Layer<DocumentProcessReference> {

    public DocumentParentLayer(String id, FilterHandler<?, ?> filterHandler) {
        //todo
        super(id, filterHandler);
    }

    @Override
    protected ProcessRunner<DocumentProcessReference> generateRunner() {
        //todo
        return null;
    }

    @Override
    public <T> QueryMethod<T> queryMethod(Repository<?> repository) {
        //todo
        return null;
    }
}