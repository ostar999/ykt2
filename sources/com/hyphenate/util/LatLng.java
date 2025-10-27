package com.hyphenate.util;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public class LatLng implements Parcelable {
    public static final Parcelable.Creator<LatLng> CREATOR = new Parcelable.Creator<LatLng>() { // from class: com.hyphenate.util.LatLng.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LatLng createFromParcel(Parcel parcel) {
            return new LatLng(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LatLng[] newArray(int i2) {
            return new LatLng[i2];
        }
    };
    public double latitude;
    public double longitude;

    public LatLng(double d3, double d4) {
        this.latitude = d3;
        this.longitude = d4;
    }

    public LatLng(Parcel parcel) {
        this.latitude = parcel.readDouble();
        this.longitude = parcel.readDouble();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeDouble(this.latitude);
        parcel.writeDouble(this.longitude);
    }
}
