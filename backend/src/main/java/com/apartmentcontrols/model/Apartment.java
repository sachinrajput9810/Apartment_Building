package com.apartmentcontrols.model;

/**
 * An Apartment is a Room with an owner name.
 */
public class Apartment extends Room {

    private String ownerName;

    public Apartment() {
        super();
    }

    public Apartment(String roomName, double currentTemperature, String ownerName) {
        super(roomName, currentTemperature);
        this.ownerName = ownerName;
    }

    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }

    @Override
    public String getType() {
        return "apartment";
    }
}
