package dev.ole.ramora.document.parent.reference;

import dev.ole.ramora.document.parent.connection.DocumentConnection;
import dev.ole.ramora.process.ProcessReference;

public final class DocumentProcessReference extends ProcessReference<DocumentConnection> {

    public DocumentProcessReference(DocumentConnection connection) {
        super(connection);
    }
}