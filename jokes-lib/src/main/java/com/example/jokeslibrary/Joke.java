package com.example.jokeslibrary;

public class Joke {
    private String description;
    private String category;

    public Joke(String category, String description) {
        this.category = category;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
