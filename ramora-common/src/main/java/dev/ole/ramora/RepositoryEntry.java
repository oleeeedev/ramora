package dev.ole.ramora;

import dev.ole.ramora.constant.ConstantPool;
import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
@ToString
public class RepositoryEntry {

    private final String id;
    private final Class<?> clazz;
    private final ConstantPool constants = new ConstantPool();

}