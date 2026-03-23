package com.apartmentcontrols.model;

import java.util.UUID;

/**
 * Base Room class. Every room has a unique ID, current temperature,
 * and heating/cooling statuses.
 */
public abstract class Room {

    private String id;
    private double currentTemperature;
    private boolean heatingEnabled;
    private boolean coolingEnabled;
    private String roomName;

    /** Deadband threshold – heating/cooling won't toggle when within this range of the setpoint. */
    private double deadband = 0.5;

    protected Room() {
        this.id = UUID.randomUUID().toString().substring(0, 8);
    }

    protected Room(String roomName, double currentTemperature) {
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.roomName = roomName;
        this.currentTemperature = currentTemperature;
        this.heatingEnabled = false;
        this.coolingEnabled = false;
    }

    // ---- Getters & Setters ----

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getRoomName() { return roomName; }
    public void setRoomName(String roomName) { this.roomName = roomName; }

    public double getCurrentTemperature() { return currentTemperature; }
    public void setCurrentTemperature(double currentTemperature) {
        this.currentTemperature = Math.round(currentTemperature * 10.0) / 10.0;
    }

    public boolean isHeatingEnabled() { return heatingEnabled; }
    public void setHeatingEnabled(boolean heatingEnabled) { this.heatingEnabled = heatingEnabled; }

    public boolean isCoolingEnabled() { return coolingEnabled; }
    public void setCoolingEnabled(boolean coolingEnabled) { this.coolingEnabled = coolingEnabled; }

    public double getDeadband() { return deadband; }
    public void setDeadband(double deadband) { this.deadband = deadband; }

    /**
     * Returns the type of room: "apartment" or "common_room".
     */
    public abstract String getType();
}
