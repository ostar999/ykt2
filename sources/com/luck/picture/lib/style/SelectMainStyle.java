package com.luck.picture.lib.style;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public class SelectMainStyle implements Parcelable {
    public static final Parcelable.Creator<SelectMainStyle> CREATOR = new Parcelable.Creator<SelectMainStyle>() { // from class: com.luck.picture.lib.style.SelectMainStyle.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SelectMainStyle createFromParcel(Parcel parcel) {
            return new SelectMainStyle(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SelectMainStyle[] newArray(int i2) {
            return new SelectMainStyle[i2];
        }
    };
    private int adapterCameraBackgroundColor;
    private int adapterCameraDrawableTop;
    private String adapterCameraText;
    private int adapterCameraTextColor;
    private int adapterCameraTextSize;
    private int adapterDurationBackgroundResources;
    private int adapterDurationDrawableLeft;
    private int[] adapterDurationGravity;
    private int adapterDurationTextColor;
    private int adapterDurationTextSize;
    private int[] adapterImageEditorGravity;
    private int adapterImageEditorResources;
    private int adapterItemSpacingSize;
    private int adapterPreviewGalleryBackgroundResource;
    private int adapterPreviewGalleryFrameResource;
    private int adapterPreviewGalleryItemSize;
    private int adapterSelectClickArea;
    private int[] adapterSelectStyleGravity;
    private int adapterSelectTextColor;
    private int adapterSelectTextSize;
    private int adapterTagBackgroundResources;
    private int[] adapterTagGravity;
    private int adapterTagTextColor;
    private int adapterTagTextSize;
    private boolean isAdapterItemIncludeEdge;
    private boolean isCompleteSelectRelativeTop;
    private boolean isDarkStatusBarBlack;
    private boolean isPreviewDisplaySelectGallery;
    private boolean isPreviewSelectNumberStyle;
    private boolean isPreviewSelectRelativeBottom;
    private boolean isSelectNumberStyle;
    private int mainListBackgroundColor;
    private int navigationBarColor;
    private int previewSelectBackground;
    private int previewSelectMarginRight;
    private String previewSelectText;
    private int previewSelectTextColor;
    private int previewSelectTextSize;
    private int selectBackground;
    private int selectBackgroundResources;
    private int selectNormalBackgroundResources;
    private String selectNormalText;
    private int selectNormalTextColor;
    private int selectNormalTextSize;
    private String selectText;
    private int selectTextColor;
    private int selectTextSize;
    private int statusBarColor;

    public SelectMainStyle() {
        this.isDarkStatusBarBlack = false;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getAdapterCameraBackgroundColor() {
        return this.adapterCameraBackgroundColor;
    }

    public int getAdapterCameraDrawableTop() {
        return this.adapterCameraDrawableTop;
    }

    public String getAdapterCameraText() {
        return this.adapterCameraText;
    }

    public int getAdapterCameraTextColor() {
        return this.adapterCameraTextColor;
    }

    public int getAdapterCameraTextSize() {
        return this.adapterCameraTextSize;
    }

    public int getAdapterDurationBackgroundResources() {
        return this.adapterDurationBackgroundResources;
    }

    public int getAdapterDurationDrawableLeft() {
        return this.adapterDurationDrawableLeft;
    }

    public int[] getAdapterDurationGravity() {
        return this.adapterDurationGravity;
    }

    public int getAdapterDurationTextColor() {
        return this.adapterDurationTextColor;
    }

    public int getAdapterDurationTextSize() {
        return this.adapterDurationTextSize;
    }

    public int[] getAdapterImageEditorGravity() {
        return this.adapterImageEditorGravity;
    }

    public int getAdapterImageEditorResources() {
        return this.adapterImageEditorResources;
    }

    public int getAdapterItemSpacingSize() {
        return this.adapterItemSpacingSize;
    }

    public int getAdapterPreviewGalleryBackgroundResource() {
        return this.adapterPreviewGalleryBackgroundResource;
    }

    public int getAdapterPreviewGalleryFrameResource() {
        return this.adapterPreviewGalleryFrameResource;
    }

    public int getAdapterPreviewGalleryItemSize() {
        return this.adapterPreviewGalleryItemSize;
    }

    public int getAdapterSelectClickArea() {
        return this.adapterSelectClickArea;
    }

    public int[] getAdapterSelectStyleGravity() {
        return this.adapterSelectStyleGravity;
    }

    public int getAdapterSelectTextColor() {
        return this.adapterSelectTextColor;
    }

    public int getAdapterSelectTextSize() {
        return this.adapterSelectTextSize;
    }

    public int getAdapterTagBackgroundResources() {
        return this.adapterTagBackgroundResources;
    }

    public int[] getAdapterTagGravity() {
        return this.adapterTagGravity;
    }

    public int getAdapterTagTextColor() {
        return this.adapterTagTextColor;
    }

    public int getAdapterTagTextSize() {
        return this.adapterTagTextSize;
    }

    public int getMainListBackgroundColor() {
        return this.mainListBackgroundColor;
    }

    public int getNavigationBarColor() {
        return this.navigationBarColor;
    }

    public int getPreviewSelectBackground() {
        return this.previewSelectBackground;
    }

    public int getPreviewSelectMarginRight() {
        return this.previewSelectMarginRight;
    }

    public String getPreviewSelectText() {
        return this.previewSelectText;
    }

    public int getPreviewSelectTextColor() {
        return this.previewSelectTextColor;
    }

    public int getPreviewSelectTextSize() {
        return this.previewSelectTextSize;
    }

    public int getSelectBackground() {
        return this.selectBackground;
    }

    public int getSelectBackgroundResources() {
        return this.selectBackgroundResources;
    }

    public int getSelectNormalBackgroundResources() {
        return this.selectNormalBackgroundResources;
    }

    public String getSelectNormalText() {
        return this.selectNormalText;
    }

    public int getSelectNormalTextColor() {
        return this.selectNormalTextColor;
    }

    public int getSelectNormalTextSize() {
        return this.selectNormalTextSize;
    }

    public String getSelectText() {
        return this.selectText;
    }

    public int getSelectTextColor() {
        return this.selectTextColor;
    }

    public int getSelectTextSize() {
        return this.selectTextSize;
    }

    public int getStatusBarColor() {
        return this.statusBarColor;
    }

    public boolean isAdapterItemIncludeEdge() {
        return this.isAdapterItemIncludeEdge;
    }

    public boolean isCompleteSelectRelativeTop() {
        return this.isCompleteSelectRelativeTop;
    }

    public boolean isDarkStatusBarBlack() {
        return this.isDarkStatusBarBlack;
    }

    public boolean isPreviewDisplaySelectGallery() {
        return this.isPreviewDisplaySelectGallery;
    }

    public boolean isPreviewSelectNumberStyle() {
        return this.isPreviewSelectNumberStyle;
    }

    public boolean isPreviewSelectRelativeBottom() {
        return this.isPreviewSelectRelativeBottom;
    }

    public boolean isSelectNumberStyle() {
        return this.isSelectNumberStyle;
    }

    public void setAdapterCameraBackgroundColor(int i2) {
        this.adapterCameraBackgroundColor = i2;
    }

    public void setAdapterCameraDrawableTop(int i2) {
        this.adapterCameraDrawableTop = i2;
    }

    public void setAdapterCameraText(String str) {
        this.adapterCameraText = str;
    }

    public void setAdapterCameraTextColor(int i2) {
        this.adapterCameraTextColor = i2;
    }

    public void setAdapterCameraTextSize(int i2) {
        this.adapterCameraTextSize = i2;
    }

    public void setAdapterDurationBackgroundResources(int i2) {
        this.adapterDurationBackgroundResources = i2;
    }

    public void setAdapterDurationDrawableLeft(int i2) {
        this.adapterDurationDrawableLeft = i2;
    }

    public void setAdapterDurationGravity(int[] iArr) {
        this.adapterDurationGravity = iArr;
    }

    public void setAdapterDurationTextColor(int i2) {
        this.adapterDurationTextColor = i2;
    }

    public void setAdapterDurationTextSize(int i2) {
        this.adapterDurationTextSize = i2;
    }

    public void setAdapterImageEditorGravity(int[] iArr) {
        this.adapterImageEditorGravity = iArr;
    }

    public void setAdapterImageEditorResources(int i2) {
        this.adapterImageEditorResources = i2;
    }

    public void setAdapterItemIncludeEdge(boolean z2) {
        this.isAdapterItemIncludeEdge = z2;
    }

    public void setAdapterItemSpacingSize(int i2) {
        this.adapterItemSpacingSize = i2;
    }

    public void setAdapterPreviewGalleryBackgroundResource(int i2) {
        this.adapterPreviewGalleryBackgroundResource = i2;
    }

    public void setAdapterPreviewGalleryFrameResource(int i2) {
        this.adapterPreviewGalleryFrameResource = i2;
    }

    public void setAdapterPreviewGalleryItemSize(int i2) {
        this.adapterPreviewGalleryItemSize = i2;
    }

    public void setAdapterSelectClickArea(int i2) {
        this.adapterSelectClickArea = i2;
    }

    public void setAdapterSelectStyleGravity(int[] iArr) {
        this.adapterSelectStyleGravity = iArr;
    }

    public void setAdapterSelectTextColor(int i2) {
        this.adapterSelectTextColor = i2;
    }

    public void setAdapterSelectTextSize(int i2) {
        this.adapterSelectTextSize = i2;
    }

    public void setAdapterTagBackgroundResources(int i2) {
        this.adapterTagBackgroundResources = i2;
    }

    public void setAdapterTagGravity(int[] iArr) {
        this.adapterTagGravity = iArr;
    }

    public void setAdapterTagTextColor(int i2) {
        this.adapterTagTextColor = i2;
    }

    public void setAdapterTagTextSize(int i2) {
        this.adapterTagTextSize = i2;
    }

    public void setCompleteSelectRelativeTop(boolean z2) {
        this.isCompleteSelectRelativeTop = z2;
    }

    public void setDarkStatusBarBlack(boolean z2) {
        this.isDarkStatusBarBlack = z2;
    }

    public void setMainListBackgroundColor(int i2) {
        this.mainListBackgroundColor = i2;
    }

    public void setNavigationBarColor(int i2) {
        this.navigationBarColor = i2;
    }

    public void setPreviewDisplaySelectGallery(boolean z2) {
        this.isPreviewDisplaySelectGallery = z2;
    }

    public void setPreviewSelectBackground(int i2) {
        this.previewSelectBackground = i2;
    }

    public void setPreviewSelectMarginRight(int i2) {
        this.previewSelectMarginRight = i2;
    }

    public void setPreviewSelectNumberStyle(boolean z2) {
        this.isPreviewSelectNumberStyle = z2;
    }

    public void setPreviewSelectRelativeBottom(boolean z2) {
        this.isPreviewSelectRelativeBottom = z2;
    }

    public void setPreviewSelectText(String str) {
        this.previewSelectText = str;
    }

    public void setPreviewSelectTextColor(int i2) {
        this.previewSelectTextColor = i2;
    }

    public void setPreviewSelectTextSize(int i2) {
        this.previewSelectTextSize = i2;
    }

    public void setSelectBackground(int i2) {
        this.selectBackground = i2;
    }

    public void setSelectBackgroundResources(int i2) {
        this.selectBackgroundResources = i2;
    }

    public void setSelectNormalBackgroundResources(int i2) {
        this.selectNormalBackgroundResources = i2;
    }

    public void setSelectNormalText(String str) {
        this.selectNormalText = str;
    }

    public void setSelectNormalTextColor(int i2) {
        this.selectNormalTextColor = i2;
    }

    public void setSelectNormalTextSize(int i2) {
        this.selectNormalTextSize = i2;
    }

    public void setSelectNumberStyle(boolean z2) {
        this.isSelectNumberStyle = z2;
    }

    public void setSelectText(String str) {
        this.selectText = str;
    }

    public void setSelectTextColor(int i2) {
        this.selectTextColor = i2;
    }

    public void setSelectTextSize(int i2) {
        this.selectTextSize = i2;
    }

    public void setStatusBarColor(int i2) {
        this.statusBarColor = i2;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.statusBarColor);
        parcel.writeInt(this.navigationBarColor);
        parcel.writeByte(this.isDarkStatusBarBlack ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isCompleteSelectRelativeTop ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isPreviewSelectRelativeBottom ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isPreviewDisplaySelectGallery ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.previewSelectMarginRight);
        parcel.writeString(this.previewSelectText);
        parcel.writeInt(this.previewSelectTextSize);
        parcel.writeInt(this.previewSelectTextColor);
        parcel.writeInt(this.selectBackground);
        parcel.writeInt(this.previewSelectBackground);
        parcel.writeByte(this.isSelectNumberStyle ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isPreviewSelectNumberStyle ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.mainListBackgroundColor);
        parcel.writeString(this.selectNormalText);
        parcel.writeInt(this.selectNormalTextSize);
        parcel.writeInt(this.selectNormalTextColor);
        parcel.writeInt(this.selectNormalBackgroundResources);
        parcel.writeString(this.selectText);
        parcel.writeInt(this.selectTextSize);
        parcel.writeInt(this.selectTextColor);
        parcel.writeInt(this.selectBackgroundResources);
        parcel.writeInt(this.adapterItemSpacingSize);
        parcel.writeByte(this.isAdapterItemIncludeEdge ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.adapterSelectTextSize);
        parcel.writeInt(this.adapterSelectClickArea);
        parcel.writeInt(this.adapterSelectTextColor);
        parcel.writeIntArray(this.adapterSelectStyleGravity);
        parcel.writeInt(this.adapterDurationDrawableLeft);
        parcel.writeInt(this.adapterDurationTextSize);
        parcel.writeInt(this.adapterDurationTextColor);
        parcel.writeIntArray(this.adapterDurationGravity);
        parcel.writeInt(this.adapterDurationBackgroundResources);
        parcel.writeInt(this.adapterCameraBackgroundColor);
        parcel.writeInt(this.adapterCameraDrawableTop);
        parcel.writeString(this.adapterCameraText);
        parcel.writeInt(this.adapterCameraTextColor);
        parcel.writeInt(this.adapterCameraTextSize);
        parcel.writeInt(this.adapterTagBackgroundResources);
        parcel.writeInt(this.adapterTagTextSize);
        parcel.writeInt(this.adapterTagTextColor);
        parcel.writeIntArray(this.adapterTagGravity);
        parcel.writeInt(this.adapterImageEditorResources);
        parcel.writeIntArray(this.adapterImageEditorGravity);
        parcel.writeInt(this.adapterPreviewGalleryFrameResource);
        parcel.writeInt(this.adapterPreviewGalleryBackgroundResource);
        parcel.writeInt(this.adapterPreviewGalleryItemSize);
    }

    public SelectMainStyle(Parcel parcel) {
        this.isDarkStatusBarBlack = false;
        this.statusBarColor = parcel.readInt();
        this.navigationBarColor = parcel.readInt();
        this.isDarkStatusBarBlack = parcel.readByte() != 0;
        this.isCompleteSelectRelativeTop = parcel.readByte() != 0;
        this.isPreviewSelectRelativeBottom = parcel.readByte() != 0;
        this.isPreviewDisplaySelectGallery = parcel.readByte() != 0;
        this.previewSelectMarginRight = parcel.readInt();
        this.previewSelectText = parcel.readString();
        this.previewSelectTextSize = parcel.readInt();
        this.previewSelectTextColor = parcel.readInt();
        this.selectBackground = parcel.readInt();
        this.previewSelectBackground = parcel.readInt();
        this.isSelectNumberStyle = parcel.readByte() != 0;
        this.isPreviewSelectNumberStyle = parcel.readByte() != 0;
        this.mainListBackgroundColor = parcel.readInt();
        this.selectNormalText = parcel.readString();
        this.selectNormalTextSize = parcel.readInt();
        this.selectNormalTextColor = parcel.readInt();
        this.selectNormalBackgroundResources = parcel.readInt();
        this.selectText = parcel.readString();
        this.selectTextSize = parcel.readInt();
        this.selectTextColor = parcel.readInt();
        this.selectBackgroundResources = parcel.readInt();
        this.adapterItemSpacingSize = parcel.readInt();
        this.isAdapterItemIncludeEdge = parcel.readByte() != 0;
        this.adapterSelectTextSize = parcel.readInt();
        this.adapterSelectClickArea = parcel.readInt();
        this.adapterSelectTextColor = parcel.readInt();
        this.adapterSelectStyleGravity = parcel.createIntArray();
        this.adapterDurationDrawableLeft = parcel.readInt();
        this.adapterDurationTextSize = parcel.readInt();
        this.adapterDurationTextColor = parcel.readInt();
        this.adapterDurationGravity = parcel.createIntArray();
        this.adapterDurationBackgroundResources = parcel.readInt();
        this.adapterCameraBackgroundColor = parcel.readInt();
        this.adapterCameraDrawableTop = parcel.readInt();
        this.adapterCameraText = parcel.readString();
        this.adapterCameraTextColor = parcel.readInt();
        this.adapterCameraTextSize = parcel.readInt();
        this.adapterTagBackgroundResources = parcel.readInt();
        this.adapterTagTextSize = parcel.readInt();
        this.adapterTagTextColor = parcel.readInt();
        this.adapterTagGravity = parcel.createIntArray();
        this.adapterImageEditorResources = parcel.readInt();
        this.adapterImageEditorGravity = parcel.createIntArray();
        this.adapterPreviewGalleryFrameResource = parcel.readInt();
        this.adapterPreviewGalleryBackgroundResource = parcel.readInt();
        this.adapterPreviewGalleryItemSize = parcel.readInt();
    }
}
