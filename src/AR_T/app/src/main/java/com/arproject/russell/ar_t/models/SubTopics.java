package com.arproject.russell.ar_t.models;

import java.util.ArrayList;
import java.util.List;

public class SubTopics {

    private static final List<Topic> PLANES = new ArrayList<>();
    static {
    }

    public static ArrayList<Topic> getPlanes() {
        return (ArrayList<Topic>) PLANES;
    }
}
