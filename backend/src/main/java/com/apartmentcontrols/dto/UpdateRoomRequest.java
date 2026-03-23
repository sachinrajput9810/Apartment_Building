package com.apartmentcontrols.dto;

import com.apartmentcontrols.model.CommonRoomType;

/**
 * Request DTO for updating a room.
 */
public class UpdateRoomRequest {
    private String roomName;
    private String ownerName;          // only for apartments
    private CommonRoomType commonRoomType;  // only for common rooms

    public String getRoomName() { return roomName; }
    public void setRoomName(String roomName) { this.roomName = roomName; }

    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }

    public CommonRoomType getCommonRoomType() { return commonRoomType; }
    public void setCommonRoomType(CommonRoomType commonRoomType) { this.commonRoomType = commonRoomType; }
}
