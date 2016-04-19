package team.project.weather.model;

import android.databinding.ObservableField;

import team.project.weather.model.Day;

public class Model {
    private Day currentDay;

    public Model(Day day){
        currentDay = day;
    }

    public Day getCurrentDay() {
        return currentDay;
    }

    public void setCurrentDay(Day currentDay) {
        this.currentDay = currentDay;
    }
}
