package com.psychiatrygarden.event;

/* loaded from: classes5.dex */
public class NetworkEvent {
    private boolean available;

    public NetworkEvent(boolean available) {
        this.available = available;
    }

    public boolean isAvailable() {
        return this.available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
