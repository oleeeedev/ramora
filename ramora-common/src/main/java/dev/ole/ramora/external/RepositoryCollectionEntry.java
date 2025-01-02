package dev.ole.ramora.external;

import dev.ole.ramora.RepositoryEntry;
import dev.ole.ramora.RepositoryEntryFinder;
import dev.ole.ramora.RepositoryExternalEntry;
import dev.ole.ramora.common.GenericReader;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Collection;

@Getter
@Accessors(fluent = true)
public final class RepositoryCollectionEntry extends RepositoryExternalEntry {

    private final RepositoryEntry typeEntry;

    public RepositoryCollectionEntry(String id, @NotNull Field field, RepositoryExternalEntry parent) {
        this(id, field, parent, GenericReader.type(field, 0));
    }

    public RepositoryCollectionEntry(String id, @NotNull Field field, RepositoryExternalEntry parent, Class<?> typeEntryClass) {
        super(id, field.getType(), parent);

        this.typeEntry = RepositoryEntryFinder.find(typeEntryClass, null, field.getName(), this);

        if (!(this.typeEntry instanceof RepositoryExternalEntry)) {
            this.children(typeEntry);
        } else {
            this.children().addAll(((RepositoryExternalEntry) typeEntry).children());
        }
    }


    @Override
    @SuppressWarnings("unchecked")
    public Collection<Object> readValues(Object parent) {
        return ((Collection<Object>) parent);
    }
}