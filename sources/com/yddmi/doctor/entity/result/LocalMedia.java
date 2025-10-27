package com.yddmi.doctor.entity.result;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import cn.hutool.core.text.CharPool;

/* loaded from: classes6.dex */
public class LocalMedia implements Parcelable {
    public static final Parcelable.Creator<LocalMedia> CREATOR = new Parcelable.Creator<LocalMedia>() { // from class: com.yddmi.doctor.entity.result.LocalMedia.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LocalMedia createFromParcel(Parcel parcel) {
            return new LocalMedia(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LocalMedia[] newArray(int i2) {
            return new LocalMedia[i2];
        }
    };
    private String androidQToPath;
    private long bucketId;
    private int chooseModel;
    private String compressPath;
    private boolean compressed;
    private int cropImageHeight;
    private int cropImageWidth;
    private int cropOffsetX;
    private int cropOffsetY;
    private float cropResultAspectRatio;
    private String cutPath;
    private long dateAddedTime;
    private long duration;
    private String fileName;
    private int height;
    private long id;
    private boolean isChecked;
    private boolean isCut;
    public boolean isLongImage;
    private boolean isMaxSelectEnabledMask;
    private boolean isOriginal;
    public int loadLongImageStatus;
    private String mimeType;
    private int num;

    @Deprecated
    private int orientation;
    private String originalPath;
    private String parentFolderName;
    private String path;
    public int position;
    private String realPath;
    private long size;
    private int width;

    public LocalMedia() {
        this.orientation = -1;
        this.loadLongImageStatus = 1;
        this.bucketId = -1L;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getAndroidQToPath() {
        return this.androidQToPath;
    }

    public long getBucketId() {
        return this.bucketId;
    }

    public int getChooseModel() {
        return this.chooseModel;
    }

    public String getCompressPath() {
        return this.compressPath;
    }

    public int getCropImageHeight() {
        return this.cropImageHeight;
    }

    public int getCropImageWidth() {
        return this.cropImageWidth;
    }

    public int getCropOffsetX() {
        return this.cropOffsetX;
    }

    public int getCropOffsetY() {
        return this.cropOffsetY;
    }

    public float getCropResultAspectRatio() {
        return this.cropResultAspectRatio;
    }

    public String getCutPath() {
        return this.cutPath;
    }

    public long getDateAddedTime() {
        return this.dateAddedTime;
    }

    public long getDuration() {
        return this.duration;
    }

    public String getFileName() {
        return this.fileName;
    }

    public int getHeight() {
        return this.height;
    }

    public long getId() {
        return this.id;
    }

    public String getMimeType() {
        return TextUtils.isEmpty(this.mimeType) ? "image/jpeg" : this.mimeType;
    }

    public int getNum() {
        return this.num;
    }

    @Deprecated
    public int getOrientation() {
        return this.orientation;
    }

    public String getOriginalPath() {
        return this.originalPath;
    }

    public String getParentFolderName() {
        return this.parentFolderName;
    }

    public String getPath() {
        return this.path;
    }

    public int getPosition() {
        return this.position;
    }

    public String getRealPath() {
        return this.realPath;
    }

    public long getSize() {
        return this.size;
    }

    public int getWidth() {
        return this.width;
    }

    public boolean isChecked() {
        return this.isChecked;
    }

    public boolean isCompressed() {
        return this.compressed;
    }

    public boolean isCut() {
        return this.isCut;
    }

    public boolean isMaxSelectEnabledMask() {
        return this.isMaxSelectEnabledMask;
    }

    public boolean isOriginal() {
        return this.isOriginal;
    }

    public void setAndroidQToPath(String str) {
        this.androidQToPath = str;
    }

    public void setBucketId(long j2) {
        this.bucketId = j2;
    }

    public void setChecked(boolean z2) {
        this.isChecked = z2;
    }

    public void setChooseModel(int i2) {
        this.chooseModel = i2;
    }

    public void setCompressPath(String str) {
        this.compressPath = str;
    }

    public void setCompressed(boolean z2) {
        this.compressed = z2;
    }

    public void setCropImageHeight(int i2) {
        this.cropImageHeight = i2;
    }

    public void setCropImageWidth(int i2) {
        this.cropImageWidth = i2;
    }

    public void setCropOffsetX(int i2) {
        this.cropOffsetX = i2;
    }

    public void setCropOffsetY(int i2) {
        this.cropOffsetY = i2;
    }

    public void setCropResultAspectRatio(float f2) {
        this.cropResultAspectRatio = f2;
    }

    public void setCut(boolean z2) {
        this.isCut = z2;
    }

    public void setCutPath(String str) {
        this.cutPath = str;
    }

    public void setDateAddedTime(long j2) {
        this.dateAddedTime = j2;
    }

    public void setDuration(long j2) {
        this.duration = j2;
    }

    public void setFileName(String str) {
        this.fileName = str;
    }

    public void setHeight(int i2) {
        this.height = i2;
    }

    public void setId(long j2) {
        this.id = j2;
    }

    public void setMaxSelectEnabledMask(boolean z2) {
        this.isMaxSelectEnabledMask = z2;
    }

    public void setMimeType(String str) {
        this.mimeType = str;
    }

    public void setNum(int i2) {
        this.num = i2;
    }

    @Deprecated
    public void setOrientation(int i2) {
        this.orientation = i2;
    }

    public void setOriginal(boolean z2) {
        this.isOriginal = z2;
    }

    public void setOriginalPath(String str) {
        this.originalPath = str;
    }

    public void setParentFolderName(String str) {
        this.parentFolderName = str;
    }

    public void setPath(String str) {
        this.path = str;
    }

    public void setPosition(int i2) {
        this.position = i2;
    }

    public void setRealPath(String str) {
        this.realPath = str;
    }

    public void setSize(long j2) {
        this.size = j2;
    }

    public void setWidth(int i2) {
        this.width = i2;
    }

    public String toString() {
        return "LocalMedia{id=" + this.id + ", path='" + this.path + CharPool.SINGLE_QUOTE + ", realPath='" + this.realPath + CharPool.SINGLE_QUOTE + ", originalPath='" + this.originalPath + CharPool.SINGLE_QUOTE + ", compressPath='" + this.compressPath + CharPool.SINGLE_QUOTE + ", cutPath='" + this.cutPath + CharPool.SINGLE_QUOTE + ", androidQToPath='" + this.androidQToPath + CharPool.SINGLE_QUOTE + ", duration=" + this.duration + ", isChecked=" + this.isChecked + ", isCut=" + this.isCut + ", position=" + this.position + ", num=" + this.num + ", mimeType='" + this.mimeType + CharPool.SINGLE_QUOTE + ", chooseModel=" + this.chooseModel + ", compressed=" + this.compressed + ", width=" + this.width + ", height=" + this.height + ", cropImageWidth=" + this.cropImageWidth + ", cropImageHeight=" + this.cropImageHeight + ", cropOffsetX=" + this.cropOffsetX + ", cropOffsetY=" + this.cropOffsetY + ", cropResultAspectRatio=" + this.cropResultAspectRatio + ", size=" + this.size + ", isOriginal=" + this.isOriginal + ", fileName='" + this.fileName + CharPool.SINGLE_QUOTE + ", parentFolderName='" + this.parentFolderName + CharPool.SINGLE_QUOTE + ", orientation=" + this.orientation + ", loadLongImageStatus=" + this.loadLongImageStatus + ", isLongImage=" + this.isLongImage + ", bucketId=" + this.bucketId + ", isMaxSelectEnabledMask=" + this.isMaxSelectEnabledMask + ", dateAddedTime=" + this.dateAddedTime + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeLong(this.id);
        parcel.writeString(this.path);
        parcel.writeString(this.realPath);
        parcel.writeString(this.originalPath);
        parcel.writeString(this.compressPath);
        parcel.writeString(this.cutPath);
        parcel.writeString(this.androidQToPath);
        parcel.writeLong(this.duration);
        parcel.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isCut ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.position);
        parcel.writeInt(this.num);
        parcel.writeString(this.mimeType);
        parcel.writeInt(this.chooseModel);
        parcel.writeByte(this.compressed ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.width);
        parcel.writeInt(this.height);
        parcel.writeInt(this.cropImageWidth);
        parcel.writeInt(this.cropImageHeight);
        parcel.writeInt(this.cropOffsetX);
        parcel.writeInt(this.cropOffsetY);
        parcel.writeFloat(this.cropResultAspectRatio);
        parcel.writeLong(this.size);
        parcel.writeByte(this.isOriginal ? (byte) 1 : (byte) 0);
        parcel.writeString(this.fileName);
        parcel.writeString(this.parentFolderName);
        parcel.writeInt(this.orientation);
        parcel.writeInt(this.loadLongImageStatus);
        parcel.writeByte(this.isLongImage ? (byte) 1 : (byte) 0);
        parcel.writeLong(this.bucketId);
        parcel.writeByte(this.isMaxSelectEnabledMask ? (byte) 1 : (byte) 0);
        parcel.writeLong(this.dateAddedTime);
    }

    public LocalMedia(long j2, String str, String str2, String str3, String str4, long j3, int i2, String str5, int i3, int i4, long j4, long j5, long j6) {
        this.orientation = -1;
        this.loadLongImageStatus = 1;
        this.id = j2;
        this.path = str;
        this.realPath = str2;
        this.fileName = str3;
        this.parentFolderName = str4;
        this.duration = j3;
        this.chooseModel = i2;
        this.mimeType = str5;
        this.width = i3;
        this.height = i4;
        this.size = j4;
        this.bucketId = j5;
        this.dateAddedTime = j6;
    }

    public LocalMedia(String str, long j2, boolean z2, int i2, int i3, int i4) {
        this.orientation = -1;
        this.loadLongImageStatus = 1;
        this.bucketId = -1L;
        this.path = str;
        this.duration = j2;
        this.isChecked = z2;
        this.position = i2;
        this.num = i3;
        this.chooseModel = i4;
    }

    public LocalMedia(Parcel parcel) {
        this.orientation = -1;
        this.loadLongImageStatus = 1;
        this.bucketId = -1L;
        this.id = parcel.readLong();
        this.path = parcel.readString();
        this.realPath = parcel.readString();
        this.originalPath = parcel.readString();
        this.compressPath = parcel.readString();
        this.cutPath = parcel.readString();
        this.androidQToPath = parcel.readString();
        this.duration = parcel.readLong();
        this.isChecked = parcel.readByte() != 0;
        this.isCut = parcel.readByte() != 0;
        this.position = parcel.readInt();
        this.num = parcel.readInt();
        this.mimeType = parcel.readString();
        this.chooseModel = parcel.readInt();
        this.compressed = parcel.readByte() != 0;
        this.width = parcel.readInt();
        this.height = parcel.readInt();
        this.cropImageWidth = parcel.readInt();
        this.cropImageHeight = parcel.readInt();
        this.cropOffsetX = parcel.readInt();
        this.cropOffsetY = parcel.readInt();
        this.cropResultAspectRatio = parcel.readFloat();
        this.size = parcel.readLong();
        this.isOriginal = parcel.readByte() != 0;
        this.fileName = parcel.readString();
        this.parentFolderName = parcel.readString();
        this.orientation = parcel.readInt();
        this.loadLongImageStatus = parcel.readInt();
        this.isLongImage = parcel.readByte() != 0;
        this.bucketId = parcel.readLong();
        this.isMaxSelectEnabledMask = parcel.readByte() != 0;
        this.dateAddedTime = parcel.readLong();
    }
}
