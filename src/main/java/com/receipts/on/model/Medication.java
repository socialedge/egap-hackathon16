package com.receipts.on.model;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Accessors(fluent = true)
public class Medication {
    private final String name;
    private final int count;
    private final String description;

    public static Medication of(String name, int count, String description) {
        return new Medication(name, count, description);
    }
}
