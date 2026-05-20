package com.HolgersDream.Deckforge.controller;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventRequest {
    private int eventId;
    private int ownerId;
    private String eventName;
    private int maxSlots;
    private int availableSlots;
    private String location;
    private LocalTime startTime;
    private LocalDate date;


    public EventRequest(){}


    /// Getters
    public int getEventId() {
        return eventId;
    }

    public int getOwnerId(){
        return ownerId;
    }

    public String getEventName(){
        return eventName;
    }

    public int getMaxSlots() {
        return maxSlots;
    }

    public int getAvailableSlots() {
        return availableSlots;
    }

    public String getLocation() {
        return location;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalDate getDate() {
        return date;
    }


    /// Setters
    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public void setOwnerId(int ownerId){
        this.ownerId = ownerId;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setMaxSlots(int maxSlots) {
        this.maxSlots = maxSlots;
    }

    public void setAvailableSlots(int availableSlots) {
        this.availableSlots = availableSlots;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
