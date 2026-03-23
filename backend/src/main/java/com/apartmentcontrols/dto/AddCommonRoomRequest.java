package com.apartmentcontrols.dto;

import com.apartmentcontrols.model.CommonRoomType;

/**
 * Request DTO for adding a Common Room.
 */
public class AddCommonRoomRequest {
    private String roomName;
    private CommonRoomType commonRoomType;

    public String getRoomName() { return roomName; }
    public void setRoomName(String roomName) { this.roomName = roomName; }

    public CommonRoomType getCommonRoomType() { return commonRoomType; }
    public void setCommonRoomType(CommonRoomType commonRoomType) { this.commonRoomType = commonRoomType; }
}
