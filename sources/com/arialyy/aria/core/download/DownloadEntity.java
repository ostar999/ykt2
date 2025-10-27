package com.arialyy.aria.core.download;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import cn.hutool.core.text.CharPool;
import com.arialyy.aria.core.common.AbsNormalEntity;
import com.arialyy.aria.orm.DbEntity;
import com.arialyy.aria.orm.annotation.Ignore;
import com.arialyy.aria.orm.annotation.Unique;
import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.CommonUtil;

/* loaded from: classes2.dex */
public class DownloadEntity extends AbsNormalEntity implements Parcelable {
    public static final Parcelable.Creator<DownloadEntity> CREATOR = new Parcelable.Creator<DownloadEntity>() { // from class: com.arialyy.aria.core.download.DownloadEntity.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DownloadEntity createFromParcel(Parcel parcel) {
            return new DownloadEntity(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DownloadEntity[] newArray(int i2) {
            return new DownloadEntity[i2];
        }
    };
    private String disposition;

    @Unique
    private String downloadPath;
    private String groupHash;

    @Ignore
    private M3U8Entity m3U8Entity;
    private String md5Code;
    private String serverFileName;

    public DownloadEntity() {
    }

    @Override // com.arialyy.aria.core.common.AbsNormalEntity, com.arialyy.aria.core.common.AbsEntity, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getDisposition() {
        return TextUtils.isEmpty(this.disposition) ? "" : CommonUtil.decryptBASE64(this.disposition);
    }

    @Override // com.arialyy.aria.core.common.AbsNormalEntity
    public String getFilePath() {
        return this.downloadPath;
    }

    public String getGroupHash() {
        return this.groupHash;
    }

    @Override // com.arialyy.aria.core.common.AbsEntity
    public String getKey() {
        return getUrl();
    }

    public M3U8Entity getM3U8Entity() {
        if (TextUtils.isEmpty(this.downloadPath)) {
            ALog.e("DownloadEntity", "文件保存路径为空，获取m3u8实体之前需要设置文件保存路径");
            return null;
        }
        if (this.m3U8Entity == null) {
            this.m3U8Entity = (M3U8Entity) DbEntity.findFirst(M3U8Entity.class, "filePath=?", this.downloadPath);
        }
        return this.m3U8Entity;
    }

    public String getMd5Code() {
        return this.md5Code;
    }

    public String getRealUrl() {
        return isRedirect() ? getRedirectUrl() : getUrl();
    }

    public String getServerFileName() {
        return this.serverFileName;
    }

    @Override // com.arialyy.aria.core.common.AbsEntity
    public int getTaskType() {
        if (getUrl() == null) {
            return 0;
        }
        if (getUrl().startsWith("http")) {
            M3U8Entity m3U8Entity = getM3U8Entity();
            return m3U8Entity == null ? 1 : m3U8Entity.isLive() ? 8 : 7;
        }
        if (getUrl().startsWith("ftp")) {
            return 3;
        }
        return getUrl().startsWith("sftp") ? 12 : 0;
    }

    public void setDisposition(String str) {
        this.disposition = str;
    }

    public DownloadEntity setFilePath(String str) {
        this.downloadPath = str;
        return this;
    }

    public void setGroupHash(String str) {
        this.groupHash = str;
    }

    public void setMd5Code(String str) {
        this.md5Code = str;
    }

    public void setServerFileName(String str) {
        this.serverFileName = str;
    }

    public String toString() {
        return "DownloadEntity{downloadPath='" + this.downloadPath + CharPool.SINGLE_QUOTE + ", groupHash='" + this.groupHash + CharPool.SINGLE_QUOTE + ", fileName='" + getFileName() + CharPool.SINGLE_QUOTE + ", md5Code='" + this.md5Code + CharPool.SINGLE_QUOTE + ", disposition='" + this.disposition + CharPool.SINGLE_QUOTE + ", serverFileName='" + this.serverFileName + CharPool.SINGLE_QUOTE + '}';
    }

    @Override // com.arialyy.aria.core.common.AbsNormalEntity, com.arialyy.aria.core.common.AbsEntity, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.writeToParcel(parcel, i2);
        parcel.writeString(this.downloadPath);
        parcel.writeString(this.groupHash);
        parcel.writeString(this.md5Code);
        parcel.writeString(this.disposition);
        parcel.writeString(this.serverFileName);
        parcel.writeParcelable(this.m3U8Entity, i2);
    }

    public DownloadEntity(Parcel parcel) {
        super(parcel);
        this.downloadPath = parcel.readString();
        this.groupHash = parcel.readString();
        this.md5Code = parcel.readString();
        this.disposition = parcel.readString();
        this.serverFileName = parcel.readString();
        this.m3U8Entity = (M3U8Entity) parcel.readParcelable(M3U8Entity.class.getClassLoader());
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public DownloadEntity m68clone() throws CloneNotSupportedException {
        return (DownloadEntity) super.clone();
    }
}
