package com.example.dairaapp;

public class Events {
    String event,subevent,info; //info can be venue,score or news

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getSubevent() {
        return subevent;
    }

    public void setSubevent(String subevent) {
        this.subevent = subevent;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Events() {
    }

    public Events(String event, String subevent, String info) {
        this.event = event;
        this.subevent = subevent;
        this.info = info;
    }
}
