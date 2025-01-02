package dev.ole.ramora.process;

import dev.ole.ramora.layer.connection.Connection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public abstract class ProcessReference<C extends Connection<?, ?>> {

    private C connection;

}