package com.sina.weibo.sdk.api;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class TextObject extends MediaObject {
    public static final Parcelable.Creator<TextObject> CREATOR = new Parcelable.Creator<TextObject>() { // from class: com.sina.weibo.sdk.api.TextObject.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final TextObject createFromParcel(Parcel parcel) {
            return new TextObject(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final TextObject[] newArray(int i2) {
            return new TextObject[i2];
        }
    };
    private static final long serialVersionUID = -5610314414793811821L;
    public String text;

    public TextObject() {
    }

    @Override // com.sina.weibo.sdk.api.MediaObject, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.sina.weibo.sdk.api.MediaObject, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(this.text);
    }

    public TextObject(Parcel parcel) {
        this.text = parcel.readString();
    }
}
