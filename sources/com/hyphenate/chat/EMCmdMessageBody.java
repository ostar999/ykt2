package com.hyphenate.chat;

import android.os.Parcel;
import android.os.Parcelable;
import com.hyphenate.chat.adapter.message.EMACmdMessageBody;

/* loaded from: classes4.dex */
public class EMCmdMessageBody extends EMMessageBody implements Parcelable {
    public static final Parcelable.Creator<EMCmdMessageBody> CREATOR = new Parcelable.Creator<EMCmdMessageBody>() { // from class: com.hyphenate.chat.EMCmdMessageBody.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EMCmdMessageBody createFromParcel(Parcel parcel) {
            return new EMCmdMessageBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EMCmdMessageBody[] newArray(int i2) {
            return new EMCmdMessageBody[i2];
        }
    };

    /* JADX WARN: Type inference failed for: r0v0, types: [T, com.hyphenate.chat.adapter.message.EMACmdMessageBody] */
    private EMCmdMessageBody(Parcel parcel) {
        this.emaObject = new EMACmdMessageBody(parcel.readString());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public EMCmdMessageBody(EMACmdMessageBody eMACmdMessageBody) {
        this.emaObject = eMACmdMessageBody;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [T, com.hyphenate.chat.adapter.message.EMACmdMessageBody] */
    public EMCmdMessageBody(String str) {
        this.emaObject = new EMACmdMessageBody(str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String action() {
        return ((EMACmdMessageBody) this.emaObject).action();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void deliverOnlineOnly(boolean z2) {
        ((EMACmdMessageBody) this.emaObject).deliverOnlineOnly(z2);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean isDeliverOnlineOnly() {
        return ((EMACmdMessageBody) this.emaObject).isDeliverOnlineOnly();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String toString() {
        return "cmd:\"" + ((EMACmdMessageBody) this.emaObject).action() + "\"";
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(((EMACmdMessageBody) this.emaObject).action());
    }
}
