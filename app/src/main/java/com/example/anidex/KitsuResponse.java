package com.example.anidex;

import java.util.List;

public class KitsuResponse<T> {
    private List<T> data;

    public List<T> getData() {
        return data;
    }
}
