package dev.ole.ramora;

import dev.ole.ramora.sql.parnet.HikariDefaultAuthentication;
import dev.ole.ramora.sql.parnet.HikariParentConnectionLayer;
import dev.ole.ramora.sql.parnet.types.Type;
import lombok.SneakyThrows;

public final class MariaDbLayer extends HikariParentConnectionLayer<HikariDefaultAuthentication> {

    @SneakyThrows
    public MariaDbLayer() {
        super(new HikariDefaultAuthentication("MARIADB", false));

        Class.forName("org.mariadb.jdbc.Driver");

        detector().overwrite(Type.of("VARCHAR(128)", it -> it.clazz().equals(String.class) && it.constants().has(RepositoryConstant.PRIMARY_KEY)));

        // todo: remove we can set this on #HikariConnection
        detector().overwrite(Type.of("BOOL", it -> {
            if (!(it.clazz().equals(boolean.class) || it.clazz().equals(Boolean.class))) {
                return false;
            }
            it.constants().put(RepositoryConstant.VALUE_REFACTOR, o -> ((boolean) o) ? 1 : 0);
            return true;
        }));
    }
}