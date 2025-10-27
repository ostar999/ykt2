package com.luck.picture.lib.style;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public class TitleBarStyle implements Parcelable {
    public static final Parcelable.Creator<TitleBarStyle> CREATOR = new Parcelable.Creator<TitleBarStyle>() { // from class: com.luck.picture.lib.style.TitleBarStyle.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TitleBarStyle createFromParcel(Parcel parcel) {
            return new TitleBarStyle(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TitleBarStyle[] newArray(int i2) {
            return new TitleBarStyle[i2];
        }
    };
    private boolean isAlbumTitleRelativeLeft;
    private boolean isHideCancelButton;
    private boolean isHideTitleBar;
    private int previewDeleteBackgroundResource;
    private int previewTitleBackgroundColor;
    private int previewTitleLeftBackResource;
    private int titleAlbumBackgroundResource;
    private int titleBackgroundColor;
    private int titleBarHeight;
    private int titleCancelBackgroundResource;
    private String titleCancelText;
    private int titleCancelTextColor;
    private int titleCancelTextSize;
    private String titleDefaultText;
    private int titleDrawableRightResource;
    private int titleLeftBackResource;
    private int titleTextColor;
    private int titleTextSize;

    public TitleBarStyle() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getPreviewDeleteBackgroundResource() {
        return this.previewDeleteBackgroundResource;
    }

    public int getPreviewTitleBackgroundColor() {
        return this.previewTitleBackgroundColor;
    }

    public int getPreviewTitleLeftBackResource() {
        return this.previewTitleLeftBackResource;
    }

    public int getTitleAlbumBackgroundResource() {
        return this.titleAlbumBackgroundResource;
    }

    public int getTitleBackgroundColor() {
        return this.titleBackgroundColor;
    }

    public int getTitleBarHeight() {
        return this.titleBarHeight;
    }

    public int getTitleCancelBackgroundResource() {
        return this.titleCancelBackgroundResource;
    }

    public String getTitleCancelText() {
        return this.titleCancelText;
    }

    public int getTitleCancelTextColor() {
        return this.titleCancelTextColor;
    }

    public int getTitleCancelTextSize() {
        return this.titleCancelTextSize;
    }

    public String getTitleDefaultText() {
        return this.titleDefaultText;
    }

    public int getTitleDrawableRightResource() {
        return this.titleDrawableRightResource;
    }

    public int getTitleLeftBackResource() {
        return this.titleLeftBackResource;
    }

    public int getTitleTextColor() {
        return this.titleTextColor;
    }

    public int getTitleTextSize() {
        return this.titleTextSize;
    }

    public boolean isAlbumTitleRelativeLeft() {
        return this.isAlbumTitleRelativeLeft;
    }

    public boolean isHideCancelButton() {
        return this.isHideCancelButton;
    }

    public boolean isHideTitleBar() {
        return this.isHideTitleBar;
    }

    public void setAlbumTitleRelativeLeft(boolean z2) {
        this.isAlbumTitleRelativeLeft = z2;
    }

    public void setHideCancelButton(boolean z2) {
        this.isHideCancelButton = z2;
    }

    public void setHideTitleBar(boolean z2) {
        this.isHideTitleBar = z2;
    }

    public void setPreviewDeleteBackgroundResource(int i2) {
        this.previewDeleteBackgroundResource = i2;
    }

    public void setPreviewTitleBackgroundColor(int i2) {
        this.previewTitleBackgroundColor = i2;
    }

    public void setPreviewTitleLeftBackResource(int i2) {
        this.previewTitleLeftBackResource = i2;
    }

    public void setTitleAlbumBackgroundResource(int i2) {
        this.titleAlbumBackgroundResource = i2;
    }

    public void setTitleBackgroundColor(int i2) {
        this.titleBackgroundColor = i2;
    }

    public void setTitleBarHeight(int i2) {
        this.titleBarHeight = i2;
    }

    public void setTitleCancelBackgroundResource(int i2) {
        this.titleCancelBackgroundResource = i2;
    }

    public void setTitleCancelText(String str) {
        this.titleCancelText = str;
    }

    public void setTitleCancelTextColor(int i2) {
        this.titleCancelTextColor = i2;
    }

    public void setTitleCancelTextSize(int i2) {
        this.titleCancelTextSize = i2;
    }

    public void setTitleDefaultText(String str) {
        this.titleDefaultText = str;
    }

    public void setTitleDrawableRightResource(int i2) {
        this.titleDrawableRightResource = i2;
    }

    public void setTitleLeftBackResource(int i2) {
        this.titleLeftBackResource = i2;
    }

    public void setTitleTextColor(int i2) {
        this.titleTextColor = i2;
    }

    public void setTitleTextSize(int i2) {
        this.titleTextSize = i2;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeByte(this.isHideTitleBar ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.titleLeftBackResource);
        parcel.writeInt(this.previewTitleLeftBackResource);
        parcel.writeString(this.titleDefaultText);
        parcel.writeInt(this.titleTextSize);
        parcel.writeInt(this.titleTextColor);
        parcel.writeInt(this.titleBackgroundColor);
        parcel.writeInt(this.previewTitleBackgroundColor);
        parcel.writeInt(this.titleBarHeight);
        parcel.writeInt(this.titleAlbumBackgroundResource);
        parcel.writeByte(this.isAlbumTitleRelativeLeft ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.titleDrawableRightResource);
        parcel.writeInt(this.titleCancelBackgroundResource);
        parcel.writeByte(this.isHideCancelButton ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.previewDeleteBackgroundResource);
        parcel.writeString(this.titleCancelText);
        parcel.writeInt(this.titleCancelTextSize);
        parcel.writeInt(this.titleCancelTextColor);
    }

    public TitleBarStyle(Parcel parcel) {
        this.isHideTitleBar = parcel.readByte() != 0;
        this.titleLeftBackResource = parcel.readInt();
        this.previewTitleLeftBackResource = parcel.readInt();
        this.titleDefaultText = parcel.readString();
        this.titleTextSize = parcel.readInt();
        this.titleTextColor = parcel.readInt();
        this.titleBackgroundColor = parcel.readInt();
        this.previewTitleBackgroundColor = parcel.readInt();
        this.titleBarHeight = parcel.readInt();
        this.titleAlbumBackgroundResource = parcel.readInt();
        this.isAlbumTitleRelativeLeft = parcel.readByte() != 0;
        this.titleDrawableRightResource = parcel.readInt();
        this.titleCancelBackgroundResource = parcel.readInt();
        this.isHideCancelButton = parcel.readByte() != 0;
        this.previewDeleteBackgroundResource = parcel.readInt();
        this.titleCancelText = parcel.readString();
        this.titleCancelTextSize = parcel.readInt();
        this.titleCancelTextColor = parcel.readInt();
    }
}
