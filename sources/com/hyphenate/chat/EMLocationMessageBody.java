package com.hyphenate.chat;

import android.os.Parcel;
import android.os.Parcelable;
import com.hyphenate.chat.adapter.message.EMALocationMessageBody;

/* loaded from: classes4.dex */
public class EMLocationMessageBody extends EMMessageBody implements Parcelable {
    public static final Parcelable.Creator<EMLocationMessageBody> CREATOR = new Parcelable.Creator<EMLocationMessageBody>() { // from class: com.hyphenate.chat.EMLocationMessageBody.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EMLocationMessageBody createFromParcel(Parcel parcel) {
            return new EMLocationMessageBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EMLocationMessageBody[] newArray(int i2) {
            return new EMLocationMessageBody[i2];
        }
    };

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v0, types: [T, com.hyphenate.chat.adapter.message.EMALocationMessageBody] */
    private EMLocationMessageBody(Parcel parcel) {
        this.emaObject = new EMALocationMessageBody(0.0d, 0.0d, "", "");
        String string = parcel.readString();
        double d3 = parcel.readDouble();
        double d4 = parcel.readDouble();
        String string2 = parcel.readString();
        ((EMALocationMessageBody) this.emaObject).setAddress(string);
        ((EMALocationMessageBody) this.emaObject).setLatitude(d3);
        ((EMALocationMessageBody) this.emaObject).setLongitude(d4);
        ((EMALocationMessageBody) this.emaObject).setBuildingName(string2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public EMLocationMessageBody(EMALocationMessageBody eMALocationMessageBody) {
        this.emaObject = eMALocationMessageBody;
    }

    /* JADX WARN: Type inference failed for: r7v0, types: [T, com.hyphenate.chat.adapter.message.EMALocationMessageBody] */
    public EMLocationMessageBody(String str, double d3, double d4) {
        this.emaObject = new EMALocationMessageBody(d3, d4, str, "");
    }

    /* JADX WARN: Type inference failed for: r7v0, types: [T, com.hyphenate.chat.adapter.message.EMALocationMessageBody] */
    public EMLocationMessageBody(String str, double d3, double d4, String str2) {
        this.emaObject = new EMALocationMessageBody(d3, d4, str, str2);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getAddress() {
        return ((EMALocationMessageBody) this.emaObject).address();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getBuildingName() {
        return ((EMALocationMessageBody) this.emaObject).buildingName();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public double getLatitude() {
        return ((EMALocationMessageBody) this.emaObject).latitude();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public double getLongitude() {
        return ((EMALocationMessageBody) this.emaObject).longitude();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String toString() {
        return "location:" + ((EMALocationMessageBody) this.emaObject).address() + ",lat:" + ((EMALocationMessageBody) this.emaObject).latitude() + ",lng:" + ((EMALocationMessageBody) this.emaObject).longitude() + ",build:" + ((EMALocationMessageBody) this.emaObject).buildingName();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(((EMALocationMessageBody) this.emaObject).address());
        parcel.writeDouble(((EMALocationMessageBody) this.emaObject).latitude());
        parcel.writeDouble(((EMALocationMessageBody) this.emaObject).longitude());
        parcel.writeString(((EMALocationMessageBody) this.emaObject).buildingName());
    }
}
