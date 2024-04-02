package com.example.anidex.Music;

import com.example.anidex.Music.AnimeSearchItem;

import java.util.List;

//used to search for data
public class AnimeSearchResponse {
    private List<AnimeSearchItem> data;

    public List<AnimeSearchItem> getData() {
        return data;
    }

    public void setData(List<AnimeSearchItem> data) {
        this.data = data;
    }
}
