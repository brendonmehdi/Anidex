package com.example.anidex.Music;

import java.util.List;
//captures the structure of the response for the
// /anime/{id}/themes endpoint of the API for parsing and utilizing the themes data in app.
public class ThemesData {
    private List<String> openings;
    private List<String> endings;

    // Getters and setters
    public List<String> getOpenings() {
        return openings;
    }

    public void setOpenings(List<String> openings) {
        this.openings = openings;
    }

    public List<String> getEndings() {
        return endings;
    }

    public void setEndings(List<String> endings) {
        this.endings = endings;
    }
}
