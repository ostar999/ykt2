package com.luck.picture.lib.style;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public class AlbumWindowStyle implements Parcelable {
    public static final Parcelable.Creator<AlbumWindowStyle> CREATOR = new Parcelable.Creator<AlbumWindowStyle>() { // from class: com.luck.picture.lib.style.AlbumWindowStyle.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AlbumWindowStyle createFromParcel(Parcel parcel) {
            return new AlbumWindowStyle(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AlbumWindowStyle[] newArray(int i2) {
            return new AlbumWindowStyle[i2];
        }
    };
    private int albumAdapterItemBackground;
    private int albumAdapterItemSelectStyle;
    private int albumAdapterItemTitleColor;
    private int albumAdapterItemTitleSize;

    public AlbumWindowStyle() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getAlbumAdapterItemBackground() {
        return this.albumAdapterItemBackground;
    }

    public int getAlbumAdapterItemSelectStyle() {
        return this.albumAdapterItemSelectStyle;
    }

    public int getAlbumAdapterItemTitleColor() {
        return this.albumAdapterItemTitleColor;
    }

    public int getAlbumAdapterItemTitleSize() {
        return this.albumAdapterItemTitleSize;
    }

    public void setAlbumAdapterItemBackground(int i2) {
        this.albumAdapterItemBackground = i2;
    }

    public void setAlbumAdapterItemSelectStyle(int i2) {
        this.albumAdapterItemSelectStyle = i2;
    }

    public void setAlbumAdapterItemTitleColor(int i2) {
        this.albumAdapterItemTitleColor = i2;
    }

    public void setAlbumAdapterItemTitleSize(int i2) {
        this.albumAdapterItemTitleSize = i2;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.albumAdapterItemBackground);
        parcel.writeInt(this.albumAdapterItemSelectStyle);
        parcel.writeInt(this.albumAdapterItemTitleSize);
        parcel.writeInt(this.albumAdapterItemTitleColor);
    }

    public AlbumWindowStyle(Parcel parcel) {
        this.albumAdapterItemBackground = parcel.readInt();
        this.albumAdapterItemSelectStyle = parcel.readInt();
        this.albumAdapterItemTitleSize = parcel.readInt();
        this.albumAdapterItemTitleColor = parcel.readInt();
    }
}
