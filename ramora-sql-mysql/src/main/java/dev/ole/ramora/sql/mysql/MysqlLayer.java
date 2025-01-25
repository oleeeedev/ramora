package dev.ole.ramora.sql.mysql;

import dev.ole.ramora.RepositoryConstant;
import dev.ole.ramora.sql.parnet.HikariDefaultAuthentication;
import dev.ole.ramora.sql.parnet.HikariParentConnectionLayer;
import dev.ole.ramora.sql.parnet.types.Type;
import lombok.SneakyThrows;

import java.util.UUID;

public final class MysqlLayer extends HikariParentConnectionLayer<HikariDefaultAuthentication> {

    @SneakyThrows
    public MysqlLayer() {
        super(new HikariDefaultAuthentication("MYSQL", false));

        Class.forName("com.mysql.cj.jdbc.Driver");

        detector().overwrite(Type.of("CHAR(36)", it -> {

            if (it.clazz().equals(UUID.class)) {
                it.constants().put(RepositoryConstant.VALUE_RENDERING, o -> UUID.fromString((String) o));
                return true;
            }
            return false;
        }));

        detector().overwrite(Type.of("BOOL", it -> {
            if (!(it.clazz().equals(boolean.class) || it.clazz().equals(Boolean.class))) {
                return false;
            }
            it.constants().put(RepositoryConstant.VALUE_REFACTOR, o -> ((boolean) o) ? 1 : 0);
            return true;
        }));
    }
}