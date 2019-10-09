package com.walderman.darcrume;

public class Note {
    private String date;
    private String film;
    private String note;


    public Note(String date, String film, String note) {
        this.date = date;
        this.film = film;
        this.note = note;
    }

    public String getNoteDate() {
        return date;
    }

    public String getNoteFilm() {
        return film;
    }

    public String getNoteText() {
        return note;
    }
}
