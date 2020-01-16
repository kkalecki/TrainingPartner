package com.example.engineerproject;

import java.util.ArrayList;

public class Workout {
    public Workout() {
    }

    private String name;
    private Integer series;
    private ArrayList<One_set> sets;

    public Workout(String name, Integer series, ArrayList<One_set> sets) {
        this.name = name;
        this.series = series;
        this.sets = sets;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSeries() {
        return series;
    }

    public void setSeries(Integer series) {
        this.series = series;
    }


    public ArrayList<One_set> getSets() {
        return sets;
    }

    public void setSets(ArrayList<One_set> sets) {
        this.sets = sets;
    }

}
