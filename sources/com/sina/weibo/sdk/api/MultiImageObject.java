package com.sina.weibo.sdk.api;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public class MultiImageObject extends MediaObject {
    public static final Parcelable.Creator<MultiImageObject> CREATOR = new Parcelable.Creator<MultiImageObject>() { // from class: com.sina.weibo.sdk.api.MultiImageObject.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final MultiImageObject createFromParcel(Parcel parcel) {
            return new MultiImageObject(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final MultiImageObject[] newArray(int i2) {
            return new MultiImageObject[i2];
        }
    };
    private static final long serialVersionUID = 4858450022450986236L;
    public ArrayList<Uri> imageList;

    public MultiImageObject() {
    }

    @Override // com.sina.weibo.sdk.api.MediaObject, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ArrayList<Uri> getImageList() {
        return this.imageList;
    }

    @Override // com.sina.weibo.sdk.api.MediaObject, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.writeToParcel(parcel, i2);
        parcel.writeTypedList(this.imageList);
    }

    public MultiImageObject(Parcel parcel) {
        super(parcel);
        this.imageList = parcel.createTypedArrayList(Uri.CREATOR);
    }
}
