package com.luck.picture.lib.style;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public class BottomNavBarStyle implements Parcelable {
    public static final Parcelable.Creator<BottomNavBarStyle> CREATOR = new Parcelable.Creator<BottomNavBarStyle>() { // from class: com.luck.picture.lib.style.BottomNavBarStyle.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BottomNavBarStyle createFromParcel(Parcel parcel) {
            return new BottomNavBarStyle(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BottomNavBarStyle[] newArray(int i2) {
            return new BottomNavBarStyle[i2];
        }
    };
    private String bottomEditorText;
    private int bottomEditorTextColor;
    private int bottomEditorTextSize;
    private int bottomNarBarBackgroundColor;
    private int bottomNarBarHeight;
    private int bottomOriginalDrawableLeft;
    private String bottomOriginalText;
    private int bottomOriginalTextColor;
    private int bottomOriginalTextSize;
    private int bottomPreviewNarBarBackgroundColor;
    private String bottomPreviewNormalText;
    private int bottomPreviewNormalTextColor;
    private int bottomPreviewNormalTextSize;
    private String bottomPreviewSelectText;
    private int bottomPreviewSelectTextColor;
    private int bottomSelectNumResources;
    private int bottomSelectNumTextColor;
    private int bottomSelectNumTextSize;
    private boolean isCompleteCountTips;

    public BottomNavBarStyle() {
        this.isCompleteCountTips = true;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getBottomEditorText() {
        return this.bottomEditorText;
    }

    public int getBottomEditorTextColor() {
        return this.bottomEditorTextColor;
    }

    public int getBottomEditorTextSize() {
        return this.bottomEditorTextSize;
    }

    public int getBottomNarBarBackgroundColor() {
        return this.bottomNarBarBackgroundColor;
    }

    public int getBottomNarBarHeight() {
        return this.bottomNarBarHeight;
    }

    public int getBottomOriginalDrawableLeft() {
        return this.bottomOriginalDrawableLeft;
    }

    public String getBottomOriginalText() {
        return this.bottomOriginalText;
    }

    public int getBottomOriginalTextColor() {
        return this.bottomOriginalTextColor;
    }

    public int getBottomOriginalTextSize() {
        return this.bottomOriginalTextSize;
    }

    public int getBottomPreviewNarBarBackgroundColor() {
        return this.bottomPreviewNarBarBackgroundColor;
    }

    public String getBottomPreviewNormalText() {
        return this.bottomPreviewNormalText;
    }

    public int getBottomPreviewNormalTextColor() {
        return this.bottomPreviewNormalTextColor;
    }

    public int getBottomPreviewNormalTextSize() {
        return this.bottomPreviewNormalTextSize;
    }

    public String getBottomPreviewSelectText() {
        return this.bottomPreviewSelectText;
    }

    public int getBottomPreviewSelectTextColor() {
        return this.bottomPreviewSelectTextColor;
    }

    public int getBottomSelectNumResources() {
        return this.bottomSelectNumResources;
    }

    public int getBottomSelectNumTextColor() {
        return this.bottomSelectNumTextColor;
    }

    public int getBottomSelectNumTextSize() {
        return this.bottomSelectNumTextSize;
    }

    public boolean isCompleteCountTips() {
        return this.isCompleteCountTips;
    }

    public void setBottomEditorText(String str) {
        this.bottomEditorText = str;
    }

    public void setBottomEditorTextColor(int i2) {
        this.bottomEditorTextColor = i2;
    }

    public void setBottomEditorTextSize(int i2) {
        this.bottomEditorTextSize = i2;
    }

    public void setBottomNarBarBackgroundColor(int i2) {
        this.bottomNarBarBackgroundColor = i2;
    }

    public void setBottomNarBarHeight(int i2) {
        this.bottomNarBarHeight = i2;
    }

    public void setBottomOriginalDrawableLeft(int i2) {
        this.bottomOriginalDrawableLeft = i2;
    }

    public void setBottomOriginalText(String str) {
        this.bottomOriginalText = str;
    }

    public void setBottomOriginalTextColor(int i2) {
        this.bottomOriginalTextColor = i2;
    }

    public void setBottomOriginalTextSize(int i2) {
        this.bottomOriginalTextSize = i2;
    }

    public void setBottomPreviewNarBarBackgroundColor(int i2) {
        this.bottomPreviewNarBarBackgroundColor = i2;
    }

    public void setBottomPreviewNormalText(String str) {
        this.bottomPreviewNormalText = str;
    }

    public void setBottomPreviewNormalTextColor(int i2) {
        this.bottomPreviewNormalTextColor = i2;
    }

    public void setBottomPreviewNormalTextSize(int i2) {
        this.bottomPreviewNormalTextSize = i2;
    }

    public void setBottomPreviewSelectText(String str) {
        this.bottomPreviewSelectText = str;
    }

    public void setBottomPreviewSelectTextColor(int i2) {
        this.bottomPreviewSelectTextColor = i2;
    }

    public void setBottomSelectNumResources(int i2) {
        this.bottomSelectNumResources = i2;
    }

    public void setBottomSelectNumTextColor(int i2) {
        this.bottomSelectNumTextColor = i2;
    }

    public void setBottomSelectNumTextSize(int i2) {
        this.bottomSelectNumTextSize = i2;
    }

    public void setCompleteCountTips(boolean z2) {
        this.isCompleteCountTips = z2;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.bottomNarBarBackgroundColor);
        parcel.writeInt(this.bottomPreviewNarBarBackgroundColor);
        parcel.writeInt(this.bottomNarBarHeight);
        parcel.writeString(this.bottomPreviewNormalText);
        parcel.writeInt(this.bottomPreviewNormalTextSize);
        parcel.writeInt(this.bottomPreviewNormalTextColor);
        parcel.writeString(this.bottomPreviewSelectText);
        parcel.writeInt(this.bottomPreviewSelectTextColor);
        parcel.writeString(this.bottomEditorText);
        parcel.writeInt(this.bottomEditorTextSize);
        parcel.writeInt(this.bottomEditorTextColor);
        parcel.writeInt(this.bottomOriginalDrawableLeft);
        parcel.writeString(this.bottomOriginalText);
        parcel.writeInt(this.bottomOriginalTextSize);
        parcel.writeInt(this.bottomOriginalTextColor);
        parcel.writeInt(this.bottomSelectNumResources);
        parcel.writeInt(this.bottomSelectNumTextSize);
        parcel.writeInt(this.bottomSelectNumTextColor);
        parcel.writeByte(this.isCompleteCountTips ? (byte) 1 : (byte) 0);
    }

    public BottomNavBarStyle(Parcel parcel) {
        this.isCompleteCountTips = true;
        this.bottomNarBarBackgroundColor = parcel.readInt();
        this.bottomPreviewNarBarBackgroundColor = parcel.readInt();
        this.bottomNarBarHeight = parcel.readInt();
        this.bottomPreviewNormalText = parcel.readString();
        this.bottomPreviewNormalTextSize = parcel.readInt();
        this.bottomPreviewNormalTextColor = parcel.readInt();
        this.bottomPreviewSelectText = parcel.readString();
        this.bottomPreviewSelectTextColor = parcel.readInt();
        this.bottomEditorText = parcel.readString();
        this.bottomEditorTextSize = parcel.readInt();
        this.bottomEditorTextColor = parcel.readInt();
        this.bottomOriginalDrawableLeft = parcel.readInt();
        this.bottomOriginalText = parcel.readString();
        this.bottomOriginalTextSize = parcel.readInt();
        this.bottomOriginalTextColor = parcel.readInt();
        this.bottomSelectNumResources = parcel.readInt();
        this.bottomSelectNumTextSize = parcel.readInt();
        this.bottomSelectNumTextColor = parcel.readInt();
        this.isCompleteCountTips = parcel.readByte() != 0;
    }
}
