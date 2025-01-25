package dev.ole.ramora.demo.models;

import dev.ole.ramora.PrimaryKey;

import java.util.UUID;

public record RecordModel(@PrimaryKey UUID id, long coins, int age) {
}