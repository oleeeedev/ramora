package dev.ole.ramora.demo.models;

import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Getter
@Accessors(fluent = true)
public final class NullModel {

    private final String name;
    private final int age;
    private final UUID editId;

    public NullModel(String name, int age) {
        this.name = name;
        this.age = age;
        this.editId = null;
    }
}