package com.apartmentcontrols.dto;

/**
 * Request DTO for adding an Apartment.
 */
public class AddApartmentRequest {
    private String roomName;
    private String ownerName;

    public String getRoomName() { return roomName; }
    public void setRoomName(String roomName) { this.roomName = roomName; }

    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }
}
