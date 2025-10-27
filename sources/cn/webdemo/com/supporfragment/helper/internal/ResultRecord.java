package cn.webdemo.com.supporfragment.helper.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public final class ResultRecord implements Parcelable {
    public static final Parcelable.Creator<ResultRecord> CREATOR = new Parcelable.Creator<ResultRecord>() { // from class: cn.webdemo.com.supporfragment.helper.internal.ResultRecord.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ResultRecord createFromParcel(Parcel parcel) {
            return new ResultRecord(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ResultRecord[] newArray(int i2) {
            return new ResultRecord[i2];
        }
    };
    public int requestCode;
    public Bundle resultBundle;
    public int resultCode;

    public ResultRecord() {
        this.resultCode = 0;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.requestCode);
        parcel.writeInt(this.resultCode);
        parcel.writeBundle(this.resultBundle);
    }

    public ResultRecord(Parcel parcel) {
        this.resultCode = 0;
        this.requestCode = parcel.readInt();
        this.resultCode = parcel.readInt();
        this.resultBundle = parcel.readBundle(ResultRecord.class.getClassLoader());
    }
}
