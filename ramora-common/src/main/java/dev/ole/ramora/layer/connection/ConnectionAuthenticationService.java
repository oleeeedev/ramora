package dev.ole.ramora.layer.connection;

import com.google.gson.*;
import dev.ole.ramora.common.JsonUtils;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;
import java.util.Set;

public final class ConnectionAuthenticationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionAuthenticationService.class);

    static {
        createConfigurationFile();
    }

    @SneakyThrows
    private static void createConfigurationFile() {
        // Create if the not exists
        Files.newByteChannel(ConnectionAuthenticationPath.CONFIGURATION_PATH, Set.of(StandardOpenOption.CREATE, StandardOpenOption.WRITE)).close();
    }

    public static void appendCredentials(ConnectableLayer<?, ?> connectableLayer, ConnectionAuthentication authentication) {
        var elements = readCredentialsContext();
        for (var credentials : elements) {
            if (!credentials.isJsonObject()) {
                continue;
            }
            var credentialsAsJsonObject = credentials.getAsJsonObject();

            if (!credentialsAsJsonObject.get("id").getAsString().equalsIgnoreCase(connectableLayer.id())) {
                continue;
            }

            if (!credentialsAsJsonObject.get("active").getAsBoolean()) {
                LOGGER.warn("Repository use {}, but session is inactive.", connectableLayer.id());
                return;
            }

            if(!connectableLayer.connection().isConnected()) {
                connectableLayer.connection().connect(JsonUtils.GSON.fromJson(credentials, authentication.getClass()));
            }
            return;
        }
        elements.add(JsonUtils.GSON.toJsonTree(authentication));
        updateCredentialsContext(elements);
    }

    @SneakyThrows
    private static @NotNull JsonArray readCredentialsContext() {
        var authenticationArray = JsonUtils.GSON.fromJson(Files.newBufferedReader(ConnectionAuthenticationPath.CONFIGURATION_PATH), JsonArray.class);
        return Objects.requireNonNullElseGet(authenticationArray, JsonArray::new);
    }

    @SneakyThrows
    private static void updateCredentialsContext(JsonArray elements) {
        Files.writeString(ConnectionAuthenticationPath.CONFIGURATION_PATH, JsonUtils.GSON.toJson(elements));
    }
}