package dev.ole.ramora;

import dev.ole.ramora.constant.Constant;
import dev.ole.ramora.transformers.Transformer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Function;

public final class RepositoryConstant<T> extends Constant<T> {

    public RepositoryConstant(String id) {
        super(id);
    }

    public static final RepositoryConstant<Field> PARAM_FIELD = constant("PARAM_FIELD");

    public static final RepositoryConstant<List<RepositoryEntry>> FOREIGN_REFERENCE = constant("FOREIGN_REFERENCE");

    public static final RepositoryConstant<Void> PRIMARY_KEY = constant("PRIMARY_KEY");

    public static final RepositoryConstant<Transformer> TRANSFORMER = constant("TRANSFORMER");

    public static final RepositoryConstant<Function<Object, Object>> VALUE_RENDERING = constant("VALUE_RENDERING");

    public static final RepositoryConstant<Function<Object, Object>> VALUE_REFACTOR = constant("VALUE_WRITING");
    @Contract("_ -> new")
    private static <T> @NotNull RepositoryConstant<T> constant(String id) {
        return new RepositoryConstant<>(id);
    }
}
