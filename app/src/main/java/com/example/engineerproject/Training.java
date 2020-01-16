package com.example.engineerproject;

import java.util.ArrayList;
import java.util.Date;

public class Training {
    private String name;
    private String date;
    private ArrayList<Workout> listOfWorkouts = new ArrayList<>();
    private Integer likes;




    public Training(String name, String date, ArrayList<Workout> listOfWorkouts, Integer likes) {
        this.name = name;
        this.date = date;
        this.listOfWorkouts = listOfWorkouts;
        this.likes = likes;
    }

    public Training() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Workout> getListOfWorkouts() {
        return listOfWorkouts;
    }

    public void setListOfWorkouts(ArrayList<Workout> listOfWorkouts) {
        this.listOfWorkouts = listOfWorkouts;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

}
