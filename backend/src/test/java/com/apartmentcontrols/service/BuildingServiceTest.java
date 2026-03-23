package com.apartmentcontrols.service;

import com.apartmentcontrols.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuildingServiceTest {

    private BuildingService service;

    @BeforeEach
    void setUp() {
        service = new BuildingService();
        service.init();
    }

    @Test
    void initialBuildingHasFourRooms() {
        Building building = service.getBuilding();
        assertEquals(4, building.getRooms().size());
        assertEquals("Daikin Apartments", building.getName());
        assertEquals(25.0, building.getRequestedTemperature());
    }

    @Test
    void initialRoomTemperaturesAreInRange() {
        for (Room room : service.getBuilding().getRooms()) {
            assertTrue(room.getCurrentTemperature() >= 10.0 && room.getCurrentTemperature() <= 40.0,
                    "Temperature should be between 10 and 40: " + room.getCurrentTemperature());
        }
    }

    @Test
    void heatingEnabledWhenBelowSetpoint() {
        // Force a room's temperature well below the setpoint
        Room room = service.getBuilding().getRooms().get(0);
        room.setCurrentTemperature(15.0);
        service.recalculateAllRooms();

        assertTrue(room.isHeatingEnabled(), "Heating should be enabled at 15°C with setpoint 25°C");
        assertFalse(room.isCoolingEnabled());
    }

    @Test
    void coolingEnabledWhenAboveSetpoint() {
        Room room = service.getBuilding().getRooms().get(0);
        room.setCurrentTemperature(35.0);
        service.recalculateAllRooms();

        assertFalse(room.isHeatingEnabled());
        assertTrue(room.isCoolingEnabled(), "Cooling should be enabled at 35°C with setpoint 25°C");
    }

    @Test
    void neitherEnabledWithinDeadband() {
        Room room = service.getBuilding().getRooms().get(0);
        room.setCurrentTemperature(25.3); // within 0.5 deadband
        service.recalculateAllRooms();

        assertFalse(room.isHeatingEnabled(), "Heating should be off within deadband");
        assertFalse(room.isCoolingEnabled(), "Cooling should be off within deadband");
    }

    @Test
    void setRequestedTemperatureRecalculates() {
        Room room = service.getBuilding().getRooms().get(0);
        room.setCurrentTemperature(22.0);
        service.setRequestedTemperature(30.0);

        assertTrue(room.isHeatingEnabled(), "Heating should be on when temp 22 < setpoint 30");
    }

    @Test
    void addApartmentWorks() {
        Room room = service.addApartment("Apartment 201", "John Doe");
        assertEquals(5, service.getBuilding().getRooms().size());
        assertTrue(room instanceof Apartment);
        assertEquals("John Doe", ((Apartment) room).getOwnerName());
    }

    @Test
    void addCommonRoomWorks() {
        Room room = service.addCommonRoom("Laundry Room", CommonRoomType.LAUNDRY);
        assertEquals(5, service.getBuilding().getRooms().size());
        assertTrue(room instanceof CommonRoom);
        assertEquals(CommonRoomType.LAUNDRY, ((CommonRoom) room).getCommonRoomType());
    }

    @Test
    void removeRoomWorks() {
        String roomId = service.getBuilding().getRooms().get(0).getId();
        assertTrue(service.removeRoom(roomId));
        assertEquals(3, service.getBuilding().getRooms().size());
    }

    @Test
    void removeNonexistentRoomReturnsFalse() {
        assertFalse(service.removeRoom("nonexistent"));
    }

    @Test
    void updateRoomWorks() {
        Room room = service.getBuilding().getRooms().get(0);
        String id = room.getId();

        Room updated = service.updateRoom(id, "Updated Name", "New Owner", null);
        assertNotNull(updated);
        assertEquals("Updated Name", updated.getRoomName());

        if (updated instanceof Apartment apt) {
            assertEquals("New Owner", apt.getOwnerName());
        }
    }
}
