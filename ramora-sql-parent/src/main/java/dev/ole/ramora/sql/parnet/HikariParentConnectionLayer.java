package dev.ole.ramora.sql.parnet;

import dev.ole.ramora.Repository;
import dev.ole.ramora.layer.connection.ConnectableLayer;
import dev.ole.ramora.layer.connection.ConnectionAuthentication;
import dev.ole.ramora.process.ProcessRunner;
import dev.ole.ramora.query.Query;
import dev.ole.ramora.query.QueryMethod;
import dev.ole.ramora.sql.parnet.connection.HikariConnection;
import dev.ole.ramora.sql.parnet.driver.ProtocolDriver;
import dev.ole.ramora.sql.parnet.process.HikariPreppedProcess;
import dev.ole.ramora.sql.parnet.query.HikariLayerQuery;
import dev.ole.ramora.sql.parnet.reference.HikariProcessReference;
import dev.ole.ramora.sql.parnet.types.TypeDefaultDetector;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

@Getter
@Accessors(fluent = true)
public abstract class HikariParentConnectionLayer<A extends ConnectionAuthentication> extends ConnectableLayer<HikariConnection, HikariProcessReference> {

    private HikariConnection connection;
    private final TypeDefaultDetector detector = new TypeDefaultDetector();

    public HikariParentConnectionLayer(A templateCredentials) {
        super(templateCredentials, new HikariFilterHandler());
    }

    public ProtocolDriver<A> protocol() {
        return credentials -> {
            if (credentials instanceof HikariDefaultAuthentication auth) {
                return "jdbc:" + auth.id().toLowerCase() + "://" + auth.hostname() + ":" + auth.port() + "/" + auth.database();
            }
            return credentials.id();
        };
    }

    /**
     * Calculate and create the base of the specific repository
     *
     * @param repository which is preparing for access
     */
    @Override
    public void prepped(@NotNull Repository<?> repository) {
        runner().apply(this,  new Query<>(repository, Set.of()), new HikariPreppedProcess(this));
    }

    @Override
    public ProcessRunner<HikariProcessReference> generateRunner() {
        return new HikariConnectionRunner(this.connection = new HikariConnection(protocol()));
    }

    @Override
    public <T> QueryMethod<T> queryMethod(Repository<?> repository) {
        return new HikariLayerQuery<>(this, repository, runner());
    }
}