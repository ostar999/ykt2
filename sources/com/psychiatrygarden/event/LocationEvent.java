package com.psychiatrygarden.event;

/* loaded from: classes5.dex */
public class LocationEvent {
    private double latitude;
    private double longitude;

    public LocationEvent(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
