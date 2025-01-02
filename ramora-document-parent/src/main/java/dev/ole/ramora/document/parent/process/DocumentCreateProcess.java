package dev.ole.ramora.document.parent.process;

import dev.ole.ramora.RepositoryExternalEntry;
import dev.ole.ramora.document.parent.DocumentFilter;
import dev.ole.ramora.document.parent.reference.DocumentProcessReference;
import dev.ole.ramora.process.kind.UpdateProcess;

public final class DocumentCreateProcess extends UpdateProcess<DocumentProcessReference, DocumentFilter<Object>> {

    @Override
    public void run(RepositoryExternalEntry entry, DocumentProcessReference reference) {
        //todo generate jsonObject
    }
}