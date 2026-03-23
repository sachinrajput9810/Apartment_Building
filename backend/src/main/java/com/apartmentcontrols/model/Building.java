package com.apartmentcontrols.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Building holds apartments and common rooms with a requested temperature setpoint.
 */
public class Building {

    private String name;
    private double requestedTemperature;
    private List<Room> rooms;

    public Building() {
        this.rooms = new ArrayList<>();
        this.requestedTemperature = 20.0; // default
    }

    public Building(String name, double requestedTemperature) {
        this.name = name;
        this.requestedTemperature = requestedTemperature;
        this.rooms = new ArrayList<>();
    }

    // ---- Getters & Setters ----

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getRequestedTemperature() { return requestedTemperature; }
    public void setRequestedTemperature(double requestedTemperature) {
        this.requestedTemperature = requestedTemperature;
    }

    public List<Room> getRooms() { return rooms; }
    public void setRooms(List<Room> rooms) { this.rooms = rooms; }

    /**
     * Add a room to the building.
     */
    public void addRoom(Room room) {
        this.rooms.add(room);
    }

    /**
     * Remove a room by its ID.
     * @return true if room was found and removed.
     */
    public boolean removeRoom(String roomId) {
        return this.rooms.removeIf(r -> r.getId().equals(roomId));
    }

    /**
     * Find a room by ID.
     */
    public Room findRoomById(String roomId) {
        return this.rooms.stream()
                .filter(r -> r.getId().equals(roomId))
                .findFirst()
                .orElse(null);
    }
}
