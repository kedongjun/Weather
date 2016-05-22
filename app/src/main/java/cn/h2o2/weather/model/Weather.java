package cn.h2o2.weather.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by kent on 16/5/22.
 */
public class Weather implements Serializable {

    private String id;
    private ArrayList<Hour> hours;
    private ArrayList<Day> days;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Hour> getHours() {
        return hours;
    }

    public void setHours(ArrayList<Hour> hours) {
        this.hours = hours;
    }

    public ArrayList<Day> getDays() {
        return days;
    }

    public void setDays(ArrayList<Day> days) {
        this.days = days;
    }

    public static class Hour implements Serializable {
        private String tem;
        private String state;
        private String hour;

        public String getTem() {
            return tem;
        }

        public void setTem(String tem) {
            this.tem = tem;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getHour() {
            return hour;
        }

        public void setHour(String hour) {
            this.hour = hour;
        }
    }

    public static class Day implements Serializable {
        private String temMin;
        private String temMax;
        private String stateMin;
        private String stateMax;
        private String day;
        private String date;

        public String getTemMin() {
            return temMin;
        }

        public void setTemMin(String temMin) {
            this.temMin = temMin;
        }

        public String getTemMax() {
            return temMax;
        }

        public void setTemMax(String temMax) {
            this.temMax = temMax;
        }

        public String getStateMin() {
            return stateMin;
        }

        public void setStateMin(String stateMin) {
            this.stateMin = stateMin;
        }

        public String getStateMax() {
            return stateMax;
        }

        public void setStateMax(String stateMax) {
            this.stateMax = stateMax;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}
