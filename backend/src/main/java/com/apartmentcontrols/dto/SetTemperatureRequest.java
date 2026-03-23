package com.apartmentcontrols.dto;

/**
 * Request DTO for setting the building's requested temperature.
 */
public class SetTemperatureRequest {
    private double requestedTemperature;

    public double getRequestedTemperature() { return requestedTemperature; }
    public void setRequestedTemperature(double requestedTemperature) {
        this.requestedTemperature = requestedTemperature;
    }
}
