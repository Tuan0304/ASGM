package com.example.translate_application;

public class TuVungCT {
    public int Id;
    public String Words;
    public boolean Active;

    public TuVungCT(int id, String words) {
        Id = id;
        Words = words;
        Active = true;
    }


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getWords() {
        return Words;
    }

    public void setWords(String words) {
        Words = words;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean active) {
        Active = active;
    }
}
