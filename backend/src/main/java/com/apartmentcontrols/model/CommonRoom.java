package com.apartmentcontrols.model;

/**
 * A CommonRoom is a Room with a type (GYM, LIBRARY, LAUNDRY).
 */
public class CommonRoom extends Room {

    private CommonRoomType commonRoomType;

    public CommonRoom() {
        super();
    }

    public CommonRoom(String roomName, double currentTemperature, CommonRoomType commonRoomType) {
        super(roomName, currentTemperature);
        this.commonRoomType = commonRoomType;
    }

    public CommonRoomType getCommonRoomType() { return commonRoomType; }
    public void setCommonRoomType(CommonRoomType commonRoomType) { this.commonRoomType = commonRoomType; }

    @Override
    public String getType() {
        return "common_room";
    }
}
