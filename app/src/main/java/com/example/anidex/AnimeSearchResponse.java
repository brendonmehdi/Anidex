package com.example.anidex;

import java.util.List;

public class AnimeSearchResponse {
    private List<AnimeSearchItem> data;

    public List<AnimeSearchItem> getData() {
        return data;
    }

    public void setData(List<AnimeSearchItem> data) {
        this.data = data;
    }
}
