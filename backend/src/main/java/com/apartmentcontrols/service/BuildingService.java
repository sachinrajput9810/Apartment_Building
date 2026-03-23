package com.apartmentcontrols.service;

import com.apartmentcontrols.model.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.Random;

/**
 * Core service managing building state, heating/cooling logic, and temperature simulation.
 */
@Service
public class BuildingService {

    private Building building;
    private final Random random = new Random();

    /**
     * Initialise the building with the default data as per requirements:
     * 2 apartments (101 & 102), a Gym, a Library, requested temp 25.0.
     */
    @PostConstruct
    public void init() {
        building = new Building("Daikin Apartments", 25.0);

        Apartment apt101 = new Apartment("Apartment 101", randomTemp(), "Owner 101");
        Apartment apt102 = new Apartment("Apartment 102", randomTemp(), "Owner 102");
        CommonRoom gym = new CommonRoom("Gym", randomTemp(), CommonRoomType.GYM);
        CommonRoom library = new CommonRoom("Library", randomTemp(), CommonRoomType.LIBRARY);

        building.addRoom(apt101);
        building.addRoom(apt102);
        building.addRoom(gym);
        building.addRoom(library);

        recalculateAllRooms();
    }

    /** Random temperature between 10.0 and 40.0. */
    private double randomTemp() {
        return Math.round((10.0 + random.nextDouble() * 30.0) * 10.0) / 10.0;
    }

    // ---- Building operations ----

    public Building getBuilding() {
        return building;
    }

    public void setRequestedTemperature(double temperature) {
        building.setRequestedTemperature(temperature);
        recalculateAllRooms();
    }

    public Room addApartment(String roomName, String ownerName) {
        if (roomName == null || roomName.trim().isEmpty()) {
            throw new IllegalArgumentException("Room name cannot be empty");
        }
        Apartment apt = new Apartment(roomName, randomTemp(), ownerName);
        recalculateRoom(apt);
        building.addRoom(apt);
        return apt;
    }

    public Room addCommonRoom(String roomName, CommonRoomType type) {
        if (roomName == null || roomName.trim().isEmpty()) {
            throw new IllegalArgumentException("Room name cannot be empty");
        }
        if (type == null) {
            throw new IllegalArgumentException("Common room type cannot be null");
        }
        CommonRoom room = new CommonRoom(roomName, randomTemp(), type);
        recalculateRoom(room);
        building.addRoom(room);
        return room;
    }

    public boolean removeRoom(String roomId) {
        return building.removeRoom(roomId);
    }

    public Room updateRoom(String roomId, String roomName, String ownerName, CommonRoomType commonRoomType) {
        Room room = building.findRoomById(roomId);
        if (room == null) return null;

        if (roomName != null) room.setRoomName(roomName);

        if (room instanceof Apartment apt && ownerName != null) {
            apt.setOwnerName(ownerName);
        }
        if (room instanceof CommonRoom cr && commonRoomType != null) {
            cr.setCommonRoomType(commonRoomType);
        }

        recalculateRoom(room);
        return room;
    }

    // ---- Heating / Cooling Logic ----

    /**
     * Recalculate heating/cooling for all rooms.
     */
    public void recalculateAllRooms() {
        for (Room room : building.getRooms()) {
            recalculateRoom(room);
        }
    }

    /**
     * Determines heating/cooling status for a single room.
     * Uses a deadband (default 0.5°C) so that if the room temperature
     * is "close enough" to the setpoint, neither heating nor cooling is enabled.
     */
    private void recalculateRoom(Room room) {
        double setpoint = building.getRequestedTemperature();
        double current = room.getCurrentTemperature();
        double deadband = room.getDeadband();

        if (current < setpoint - deadband) {
            room.setHeatingEnabled(true);
            room.setCoolingEnabled(false);
        } else if (current > setpoint + deadband) {
            room.setHeatingEnabled(false);
            room.setCoolingEnabled(true);
        } else {
            // Within deadband – close enough
            room.setHeatingEnabled(false);
            room.setCoolingEnabled(false);
        }
    }

    // ---- Temperature Simulation (Extra) ----

    /**
     * Every 5 seconds, simulate temperature drift:
     *  - if heating is on, temperature increases by 0.1–0.3°C
     *  - if cooling is on, temperature decreases by 0.1–0.3°C
     *  - if neither, temperature drifts randomly by ±0.05°C
     * Then recalculate statuses.
     */
    @Scheduled(fixedRate = 5000)
    public void simulateTemperatureChanges() {
        for (Room room : building.getRooms()) {
            double temp = room.getCurrentTemperature();

            if (room.isHeatingEnabled()) {
                temp += 0.1 + random.nextDouble() * 0.2;
            } else if (room.isCoolingEnabled()) {
                temp -= 0.1 + random.nextDouble() * 0.2;
            } else {
                temp += (random.nextDouble() - 0.5) * 0.1;
            }

            room.setCurrentTemperature(temp);
        }

        recalculateAllRooms();
    }
}
