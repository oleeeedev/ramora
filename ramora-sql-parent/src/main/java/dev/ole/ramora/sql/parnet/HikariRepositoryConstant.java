package dev.ole.ramora.sql.parnet;

import dev.ole.ramora.RepositoryConstant;
import dev.ole.ramora.sql.parnet.types.Type;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class HikariRepositoryConstant {

    public static final RepositoryConstant<Type> SQL_TYPE = new RepositoryConstant<>("SQL_TYPE");

}