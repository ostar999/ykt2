package com.hyphenate.notification;

import android.os.Parcel;
import android.os.Parcelable;
import cn.hutool.core.text.CharPool;

/* loaded from: classes4.dex */
public class EMNotificationMessage implements Parcelable {
    public static final Parcelable.Creator<EMNotificationMessage> CREATOR = new Parcelable.Creator<EMNotificationMessage>() { // from class: com.hyphenate.notification.EMNotificationMessage.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EMNotificationMessage createFromParcel(Parcel parcel) {
            return new EMNotificationMessage(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EMNotificationMessage[] newArray(int i2) {
            return new EMNotificationMessage[i2];
        }
    };
    int badgeAdd;
    String badgeClass;
    int badgeSet;
    String extras;
    boolean needNotification;
    boolean notificationAutoClear;
    String notificationBigPicPath;
    String notificationBigText;
    long notificationCancelTime;
    String notificationChannelId;
    int notificationChannelLevel;
    String notificationChannelName;
    String notificationContent;
    long notificationExpiresTime;
    String notificationIconUrl;
    int notificationNotifyId;
    boolean notificationSound;
    int notificationStyle;
    String notificationTitle;
    boolean notificationVibrate;
    String openAction;
    String openActivity;
    int openType;
    String openUrl;

    public EMNotificationMessage() {
    }

    public EMNotificationMessage(Parcel parcel) {
        this.notificationTitle = parcel.readString();
        this.notificationContent = parcel.readString();
        this.notificationStyle = parcel.readInt();
        this.notificationIconUrl = parcel.readString();
        this.notificationBigPicPath = parcel.readString();
        this.notificationBigText = parcel.readString();
        this.notificationChannelId = parcel.readString();
        this.notificationChannelName = parcel.readString();
        this.notificationChannelLevel = parcel.readInt();
        this.notificationNotifyId = parcel.readInt();
        this.notificationAutoClear = parcel.readByte() != 0;
        this.notificationSound = parcel.readByte() != 0;
        this.notificationVibrate = parcel.readByte() != 0;
        this.notificationExpiresTime = parcel.readLong();
        this.notificationCancelTime = parcel.readLong();
        this.openType = parcel.readInt();
        this.openUrl = parcel.readString();
        this.openAction = parcel.readString();
        this.openActivity = parcel.readString();
        this.extras = parcel.readString();
        this.badgeAdd = parcel.readInt();
        this.badgeSet = parcel.readInt();
        this.badgeClass = parcel.readString();
        this.needNotification = parcel.readByte() != 0;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getBadgeAdd() {
        return this.badgeAdd;
    }

    public String getBadgeClass() {
        return this.badgeClass;
    }

    public int getBadgeSet() {
        return this.badgeSet;
    }

    public String getExtras() {
        return this.extras;
    }

    public String getNotificationBigPicPath() {
        return this.notificationBigPicPath;
    }

    public String getNotificationBigText() {
        return this.notificationBigText;
    }

    public long getNotificationCancelTime() {
        return this.notificationCancelTime;
    }

    public String getNotificationChannelId() {
        return this.notificationChannelId;
    }

    public int getNotificationChannelLevel() {
        return this.notificationChannelLevel;
    }

    public String getNotificationChannelName() {
        return this.notificationChannelName;
    }

    public String getNotificationContent() {
        return this.notificationContent;
    }

    public long getNotificationExpiresTime() {
        return this.notificationExpiresTime;
    }

    public String getNotificationIconUrl() {
        return this.notificationIconUrl;
    }

    public int getNotificationNotifyId() {
        return this.notificationNotifyId;
    }

    public int getNotificationStyle() {
        return this.notificationStyle;
    }

    public String getNotificationTitle() {
        return this.notificationTitle;
    }

    public String getOpenAction() {
        return this.openAction;
    }

    public String getOpenActivity() {
        return this.openActivity;
    }

    public int getOpenType() {
        return this.openType;
    }

    public String getOpenUrl() {
        return this.openUrl;
    }

    public boolean isNeedNotification() {
        return this.needNotification;
    }

    public boolean isNotificationAutoClear() {
        return this.notificationAutoClear;
    }

    public boolean isNotificationSound() {
        return this.notificationSound;
    }

    public boolean isNotificationVibrate() {
        return this.notificationVibrate;
    }

    public void readFromParcel(Parcel parcel) {
        this.notificationTitle = parcel.readString();
        this.notificationContent = parcel.readString();
        this.notificationStyle = parcel.readInt();
        this.notificationIconUrl = parcel.readString();
        this.notificationBigPicPath = parcel.readString();
        this.notificationBigText = parcel.readString();
        this.notificationChannelId = parcel.readString();
        this.notificationChannelName = parcel.readString();
        this.notificationChannelLevel = parcel.readInt();
        this.notificationNotifyId = parcel.readInt();
        this.notificationAutoClear = parcel.readByte() != 0;
        this.notificationSound = parcel.readByte() != 0;
        this.notificationVibrate = parcel.readByte() != 0;
        this.notificationExpiresTime = parcel.readLong();
        this.notificationCancelTime = parcel.readLong();
        this.openType = parcel.readInt();
        this.openUrl = parcel.readString();
        this.openAction = parcel.readString();
        this.openActivity = parcel.readString();
        this.extras = parcel.readString();
        this.badgeAdd = parcel.readInt();
        this.badgeSet = parcel.readInt();
        this.badgeClass = parcel.readString();
        this.needNotification = parcel.readByte() != 0;
    }

    public void setBadgeAdd(int i2) {
        this.badgeAdd = i2;
    }

    public void setBadgeClass(String str) {
        this.badgeClass = str;
    }

    public void setBadgeSet(int i2) {
        this.badgeSet = i2;
    }

    public void setExtras(String str) {
        this.extras = str;
    }

    public void setNeedNotification(boolean z2) {
        this.needNotification = z2;
    }

    public void setNotificationAutoClear(boolean z2) {
        this.notificationAutoClear = z2;
    }

    public void setNotificationBigPicPath(String str) {
        this.notificationBigPicPath = str;
    }

    public void setNotificationBigText(String str) {
        this.notificationBigText = str;
    }

    public void setNotificationCancelTime(long j2) {
        this.notificationCancelTime = j2;
    }

    public void setNotificationChannelId(String str) {
        this.notificationChannelId = str;
    }

    public void setNotificationChannelLevel(int i2) {
        this.notificationChannelLevel = i2;
    }

    public void setNotificationChannelName(String str) {
        this.notificationChannelName = str;
    }

    public void setNotificationContent(String str) {
        this.notificationContent = str;
    }

    public void setNotificationExpiresTime(long j2) {
        this.notificationExpiresTime = j2;
    }

    public void setNotificationIconUrl(String str) {
        this.notificationIconUrl = str;
    }

    public void setNotificationNotifyId(int i2) {
        this.notificationNotifyId = i2;
    }

    public void setNotificationSound(boolean z2) {
        this.notificationSound = z2;
    }

    public void setNotificationStyle(int i2) {
        this.notificationStyle = i2;
    }

    public void setNotificationTitle(String str) {
        this.notificationTitle = str;
    }

    public void setNotificationVibrate(boolean z2) {
        this.notificationVibrate = z2;
    }

    public void setOpenAction(String str) {
        this.openAction = str;
    }

    public void setOpenActivity(String str) {
        this.openActivity = str;
    }

    public void setOpenType(int i2) {
        this.openType = i2;
    }

    public void setOpenUrl(String str) {
        this.openUrl = str;
    }

    public String toString() {
        return "EMNotificationMessage{notificationTitle='" + this.notificationTitle + CharPool.SINGLE_QUOTE + ", notificationContent='" + this.notificationContent + CharPool.SINGLE_QUOTE + ", notificationStyle=" + this.notificationStyle + ", notificationIconUrl='" + this.notificationIconUrl + CharPool.SINGLE_QUOTE + ", notificationBigPicPath='" + this.notificationBigPicPath + CharPool.SINGLE_QUOTE + ", notificationBigText='" + this.notificationBigText + CharPool.SINGLE_QUOTE + ", notificationChannelId='" + this.notificationChannelId + CharPool.SINGLE_QUOTE + ", notificationChannelName='" + this.notificationChannelName + CharPool.SINGLE_QUOTE + ", notificationChannelLevel=" + this.notificationChannelLevel + ", notificationNotifyId=" + this.notificationNotifyId + ", notificationAutoClear=" + this.notificationAutoClear + ", notificationSound=" + this.notificationSound + ", notificationVibrate=" + this.notificationVibrate + ", notificationExpiresTime=" + this.notificationExpiresTime + ", notificationCancelTime=" + this.notificationCancelTime + ", openType=" + this.openType + ", openUrl='" + this.openUrl + CharPool.SINGLE_QUOTE + ", openAction='" + this.openAction + CharPool.SINGLE_QUOTE + ", openActivity='" + this.openActivity + CharPool.SINGLE_QUOTE + ", extras='" + this.extras + CharPool.SINGLE_QUOTE + ", badgeAdd=" + this.badgeAdd + ", badgeSet=" + this.badgeSet + ", badgeClass='" + this.badgeClass + CharPool.SINGLE_QUOTE + ", needNotification=" + this.needNotification + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(this.notificationTitle);
        parcel.writeString(this.notificationContent);
        parcel.writeInt(this.notificationStyle);
        parcel.writeString(this.notificationIconUrl);
        parcel.writeString(this.notificationBigPicPath);
        parcel.writeString(this.notificationBigText);
        parcel.writeString(this.notificationChannelId);
        parcel.writeString(this.notificationChannelName);
        parcel.writeInt(this.notificationChannelLevel);
        parcel.writeInt(this.notificationNotifyId);
        parcel.writeByte(this.notificationAutoClear ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.notificationSound ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.notificationVibrate ? (byte) 1 : (byte) 0);
        parcel.writeLong(this.notificationExpiresTime);
        parcel.writeLong(this.notificationCancelTime);
        parcel.writeInt(this.openType);
        parcel.writeString(this.openUrl);
        parcel.writeString(this.openAction);
        parcel.writeString(this.openActivity);
        parcel.writeString(this.extras);
        parcel.writeInt(this.badgeAdd);
        parcel.writeInt(this.badgeSet);
        parcel.writeString(this.badgeClass);
        parcel.writeByte(this.needNotification ? (byte) 1 : (byte) 0);
    }
}
