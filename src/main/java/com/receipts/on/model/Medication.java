package com.receipts.on.model;

import lombok.*;
import lombok.experimental.Accessors;

//@AllArgsConstructor
//@Accessors(fluent = true)
public class Medication {
    private final String name;
    private final int count;
    private final String description;

    public Medication(String name, int count, String description) {
        this.name = name;
        this.count = count;
        this.description = description;
    }

    public static Medication of(String name, int count, String description) {
        return new Medication(name, count, description);
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public String getDescription() {
        return description;
    }
}
