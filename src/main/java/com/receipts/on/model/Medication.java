package com.receipts.on.model;

public class Medication {
    private String name;
    private int count;
    private String description;

    public Medication(String name, int count, String description) {
        this.name = name;
        this.count = count;
        this.description = description;
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
