package com.HolgersDream.Deckforge.domain;

import java.sql.Date;
import java.sql.Time;

public class Event {
    private int eventId;
    private int maxSlots;
    private int availableSlots;
    private Format format;
    private String location;
    private Time startTime;
    private Date date;

    public Event() {
    }

    public Event(int eventId, int maxSlots, int availableSlots, Format format, String location,
                 Time startTime, Date date) {
        this.eventId = eventId;
        this.maxSlots = maxSlots;
        this.availableSlots = availableSlots;
        this.format = format;
        this.location = location;
        this.startTime = startTime;
        this.date = date;
    }

    /// Getters
    public int getEventId() {
        return eventId;
    }

    public int getMaxSlots() {
        return maxSlots;
    }

    public int getAvailableSlots() {
        return availableSlots;
    }

    public Format getFormat() {
        return format;
    }

    public String getLocation() {
        return location;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Date getDate() {
        return date;
    }

    /// Setters
    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public void setMaxSlots(int maxSlots) {
        this.maxSlots = maxSlots;
    }

    public void setAvailableSlots(int availableSlots) {
        this.availableSlots = availableSlots;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
