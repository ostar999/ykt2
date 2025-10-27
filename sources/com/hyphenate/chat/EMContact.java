package com.hyphenate.chat;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
class EMContact implements Parcelable {
    public static final Parcelable.Creator<EMContact> CREATOR = new Parcelable.Creator<EMContact>() { // from class: com.hyphenate.chat.EMContact.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EMContact createFromParcel(Parcel parcel) {
            return new EMContact(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EMContact[] newArray(int i2) {
            return new EMContact[i2];
        }
    };
    protected String nick;
    protected String username;

    public EMContact() {
    }

    private EMContact(Parcel parcel) {
        this.username = parcel.readString();
    }

    public EMContact(String str) {
        this.username = str;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getNickname() {
        String str = this.nick;
        return str == null ? getUsername() : str;
    }

    public String getUsername() {
        return this.username;
    }

    public void setNickname(String str) {
        this.nick = str;
    }

    public String toString() {
        return "<contact , username:" + this.username + ">";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(this.username);
    }
}
