package dev.ole.ramora.demo.models.collection;

import dev.ole.ramora.PrimaryKey;
import dev.ole.ramora.demo.models.RecordModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
@ToString
public final class CollectionRecordModel {

    @PrimaryKey
    private final UUID uuid;
    private final int age;
    private final List<RecordModel> groups;

}