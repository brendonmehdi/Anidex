package com.example.anidex;

import java.util.List;

public class AnimeSearchResponse {
    private List<AnimeSearchItem> data; // Assuming the key for the list of results is "data"

    public List<AnimeSearchItem> getData() {
        return data;
    }

    public void setData(List<AnimeSearchItem> data) {
        this.data = data;
    }
}
