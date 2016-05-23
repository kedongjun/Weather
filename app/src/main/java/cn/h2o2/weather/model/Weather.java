package cn.h2o2.weather.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kent on 16/5/22.
 */
public class Weather implements Serializable {

    private String id;
    private Top top;
    private ArrayList<Hour> hours;
    private ArrayList<Day> days;
    private Detail detail;
    private ArrayList<Guide> guides;

    public String getId() {
        return id;
    }

    public Top getTop() {
        return top;
    }

    public void setTop(Top top) {
        this.top = top;
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

    public Detail getDetail() {
        return detail;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }

    public ArrayList<Guide> getGuides() {
        return guides;
    }

    public void setGuides(ArrayList<Guide> guides) {
        this.guides = guides;
    }

    public static class Top implements Serializable{
        private String state;
        private String tem;
        private String update;
        private int kongqi;
        private HashMap<String,String> infos;

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public HashMap<String, String> getInfos() {
            return infos;
        }

        public void setInfos(HashMap<String, String> infos) {
            this.infos = infos;
        }

        public int getKongqi() {
            return kongqi;
        }

        public void setKongqi(int kongqi) {
            this.kongqi = kongqi;
        }

        public String getTem() {
            return tem;
        }

        public void setTem(String tem) {
            this.tem = tem;
        }

        public String getUpdate() {
            return update;
        }

        public void setUpdate(String update) {
            this.update = update;
        }
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

    public static class Detail implements Serializable {
        private String tem;
        private HashMap<String,String> infos;

        public HashMap<String, String> getInfos() {
            return infos;
        }

        public void setInfos(HashMap<String, String> infos) {
            this.infos = infos;
        }

        public String getTem() {
            return tem;
        }

        public void setTem(String tem) {
            this.tem = tem;
        }
    }

    public static class Guide implements Serializable {
        private String icon;
        private String title;
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
