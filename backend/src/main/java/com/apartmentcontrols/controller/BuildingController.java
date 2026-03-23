package com.apartmentcontrols.controller;

import com.apartmentcontrols.dto.*;
import com.apartmentcontrols.model.Building;
import com.apartmentcontrols.model.Room;
import com.apartmentcontrols.service.BuildingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller exposing building management endpoints.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class BuildingController {

    private final BuildingService buildingService;

    public BuildingController(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    // ---- Building ----

    @GetMapping("/building")
    public ResponseEntity<Building> getBuilding() {
        return ResponseEntity.ok(buildingService.getBuilding());
    }

    @PutMapping("/building/temperature")
    public ResponseEntity<Building> setRequestedTemperature(@RequestBody SetTemperatureRequest request) {
        buildingService.setRequestedTemperature(request.getRequestedTemperature());
        return ResponseEntity.ok(buildingService.getBuilding());
    }

    // ---- Room management ----

    @PostMapping("/rooms/apartment")
    public ResponseEntity<?> addApartment(@RequestBody AddApartmentRequest request) {
        try {
            Room room = buildingService.addApartment(request.getRoomName(), request.getOwnerName());
            return ResponseEntity.ok(room);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/rooms/common-room")
    public ResponseEntity<?> addCommonRoom(@RequestBody AddCommonRoomRequest request) {
        try {
            Room room = buildingService.addCommonRoom(request.getRoomName(), request.getCommonRoomType());
            return ResponseEntity.ok(room);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/rooms/{roomId}")
    public ResponseEntity<Room> updateRoom(@PathVariable String roomId, @RequestBody UpdateRoomRequest request) {
        Room room = buildingService.updateRoom(roomId, request.getRoomName(), request.getOwnerName(), request.getCommonRoomType());
        if (room == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(room);
    }

    @DeleteMapping("/rooms/{roomId}")
    public ResponseEntity<Void> removeRoom(@PathVariable String roomId) {
        boolean removed = buildingService.removeRoom(roomId);
        if (!removed) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
