package com.hyphenate.chat;

import android.os.Parcel;
import android.os.Parcelable;
import com.hyphenate.chat.adapter.message.EMACustomMessageBody;
import java.util.Map;

/* loaded from: classes4.dex */
public class EMCustomMessageBody extends EMMessageBody implements Parcelable {
    public static final Parcelable.Creator<EMCustomMessageBody> CREATOR = new Parcelable.Creator<EMCustomMessageBody>() { // from class: com.hyphenate.chat.EMCustomMessageBody.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EMCustomMessageBody createFromParcel(Parcel parcel) {
            return new EMCustomMessageBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EMCustomMessageBody[] newArray(int i2) {
            return new EMCustomMessageBody[i2];
        }
    };

    /* JADX WARN: Type inference failed for: r0v0, types: [T, com.hyphenate.chat.adapter.message.EMACustomMessageBody] */
    private EMCustomMessageBody(Parcel parcel) {
        this.emaObject = new EMACustomMessageBody(parcel.readString());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public EMCustomMessageBody(EMACustomMessageBody eMACustomMessageBody) {
        this.emaObject = eMACustomMessageBody;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [T, com.hyphenate.chat.adapter.message.EMACustomMessageBody] */
    public EMCustomMessageBody(String str) {
        this.emaObject = new EMACustomMessageBody(str);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String event() {
        return ((EMACustomMessageBody) this.emaObject).event();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Map<String, String> getParams() {
        return ((EMACustomMessageBody) this.emaObject).params();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setEvent(String str) {
        ((EMACustomMessageBody) this.emaObject).setEvent(str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setParams(Map<String, String> map) {
        ((EMACustomMessageBody) this.emaObject).setParams(map);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(((EMACustomMessageBody) this.emaObject).event());
    }
}
