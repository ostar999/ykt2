package com.hyphenate.chat;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.hyphenate.chat.EMFileMessageBody;
import com.hyphenate.chat.adapter.message.EMAFileMessageBody;
import com.hyphenate.chat.adapter.message.EMAImageMessageBody;
import com.hyphenate.util.EMFileHelper;
import java.io.File;

/* loaded from: classes4.dex */
public class EMImageMessageBody extends EMFileMessageBody implements Parcelable {
    public static final Parcelable.Creator<EMImageMessageBody> CREATOR = new Parcelable.Creator<EMImageMessageBody>() { // from class: com.hyphenate.chat.EMImageMessageBody.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EMImageMessageBody createFromParcel(Parcel parcel) {
            return new EMImageMessageBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EMImageMessageBody[] newArray(int i2) {
            return new EMImageMessageBody[i2];
        }
    };
    private static final String TAG = "EMImageMessageBody";
    private boolean sendOriginalImage;

    /* renamed from: com.hyphenate.chat.EMImageMessageBody$2, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$hyphenate$chat$adapter$message$EMAFileMessageBody$EMADownloadStatus;

        static {
            int[] iArr = new int[EMAFileMessageBody.EMADownloadStatus.values().length];
            $SwitchMap$com$hyphenate$chat$adapter$message$EMAFileMessageBody$EMADownloadStatus = iArr;
            try {
                iArr[EMAFileMessageBody.EMADownloadStatus.DOWNLOADING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$hyphenate$chat$adapter$message$EMAFileMessageBody$EMADownloadStatus[EMAFileMessageBody.EMADownloadStatus.SUCCESSED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$hyphenate$chat$adapter$message$EMAFileMessageBody$EMADownloadStatus[EMAFileMessageBody.EMADownloadStatus.FAILED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$hyphenate$chat$adapter$message$EMAFileMessageBody$EMADownloadStatus[EMAFileMessageBody.EMADownloadStatus.PENDING.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public EMImageMessageBody(Uri uri) {
        super(uri, 1);
        this.sendOriginalImage = false;
        setFileName(EMFileHelper.getInstance().getFilename(uri));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public EMImageMessageBody(Uri uri, Uri uri2) {
        super(uri, 1);
        this.sendOriginalImage = false;
        ((EMAImageMessageBody) this.emaObject).setThumbnailLocalPath(uri2 != null ? uri2.toString() : "");
        setFileName(EMFileHelper.getInstance().getFilename(uri));
    }

    /* JADX WARN: Multi-variable type inference failed */
    private EMImageMessageBody(Parcel parcel) {
        super((Uri) null, 1);
        this.sendOriginalImage = false;
        ((EMAImageMessageBody) this.emaObject).setDisplayName(parcel.readString());
        ((EMAImageMessageBody) this.emaObject).setLocalPath(parcel.readString());
        ((EMAImageMessageBody) this.emaObject).setRemotePath(parcel.readString());
        ((EMAImageMessageBody) this.emaObject).setThumbnailRemotePath(parcel.readString());
        ((EMAImageMessageBody) this.emaObject).setSize(parcel.readInt(), parcel.readInt());
    }

    public EMImageMessageBody(EMAImageMessageBody eMAImageMessageBody) {
        super(eMAImageMessageBody);
        this.sendOriginalImage = false;
    }

    public EMImageMessageBody(File file) {
        super(Uri.fromFile(file), 1);
        this.sendOriginalImage = false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public EMImageMessageBody(File file, File file2) {
        super(Uri.fromFile(file), 1);
        this.sendOriginalImage = false;
        ((EMAImageMessageBody) this.emaObject).setThumbnailLocalPath(file2 == null ? "" : Uri.fromFile(file2).toString());
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2, types: [T, com.hyphenate.chat.adapter.message.EMAFileMessageBody, com.hyphenate.chat.adapter.message.EMAImageMessageBody] */
    public EMImageMessageBody(String str, String str2, String str3) {
        super((Uri) null, 1);
        this.sendOriginalImage = false;
        ?? eMAImageMessageBody = new EMAImageMessageBody((Uri) null, (Uri) null);
        this.emaObject = eMAImageMessageBody;
        eMAImageMessageBody.setDisplayName(str);
        ((EMAImageMessageBody) this.emaObject).setRemotePath(str2);
        ((EMAImageMessageBody) this.emaObject).setThumbnailRemotePath(str3);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.hyphenate.chat.EMFileMessageBody
    public String getFileName() {
        return ((EMAImageMessageBody) this.emaObject).displayName();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public long getFileSize() {
        return ((EMAImageMessageBody) this.emaObject).fileLength();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int getHeight() {
        return ((EMAImageMessageBody) this.emaObject).height();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getThumbnailSecret() {
        return ((EMAImageMessageBody) this.emaObject).thumbnailSecretKey();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getThumbnailUrl() {
        return ((EMAImageMessageBody) this.emaObject).thumbnailRemotePath();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int getWidth() {
        return ((EMAImageMessageBody) this.emaObject).width();
    }

    public boolean isSendOriginalImage() {
        return this.sendOriginalImage;
    }

    public void setSendOriginalImage(boolean z2) {
        this.sendOriginalImage = z2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setSize(int i2, int i3) {
        ((EMAImageMessageBody) this.emaObject).setSize(i2, i3);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setThumbnailDownloadStatus(EMFileMessageBody.EMDownloadStatus eMDownloadStatus) {
        ((EMAImageMessageBody) this.emaObject).setThumbnailDownloadStatus(EMAFileMessageBody.EMADownloadStatus.valueOf(eMDownloadStatus.name()));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setThumbnailLocalPath(Uri uri) {
        ((EMAImageMessageBody) this.emaObject).setThumbnailLocalPath(EMFileHelper.getInstance().formatInUriToString(uri));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setThumbnailLocalPath(String str) {
        ((EMAImageMessageBody) this.emaObject).setThumbnailLocalPath(EMFileHelper.getInstance().formatInUriToString(str));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setThumbnailSecret(String str) {
        ((EMAImageMessageBody) this.emaObject).setThumbnailSecretKey(str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setThumbnailSize(int i2, int i3) {
        ((EMAImageMessageBody) this.emaObject).setThumbnailSize(i2, i3);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setThumbnailUrl(String str) {
        ((EMAImageMessageBody) this.emaObject).setThumbnailRemotePath(str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public EMFileMessageBody.EMDownloadStatus thumbnailDownloadStatus() {
        int i2 = AnonymousClass2.$SwitchMap$com$hyphenate$chat$adapter$message$EMAFileMessageBody$EMADownloadStatus[((EMAImageMessageBody) this.emaObject).thumbnailDownloadStatus().ordinal()];
        return i2 != 1 ? i2 != 2 ? i2 != 3 ? i2 != 4 ? EMFileMessageBody.EMDownloadStatus.SUCCESSED : EMFileMessageBody.EMDownloadStatus.PENDING : EMFileMessageBody.EMDownloadStatus.FAILED : EMFileMessageBody.EMDownloadStatus.SUCCESSED : EMFileMessageBody.EMDownloadStatus.DOWNLOADING;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String thumbnailLocalPath() {
        return EMFileHelper.getInstance().formatOutLocalUrl(((EMAImageMessageBody) this.emaObject).thumbnailLocalPath());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Uri thumbnailLocalUri() {
        return EMFileHelper.getInstance().formatOutUri(((EMAImageMessageBody) this.emaObject).thumbnailLocalPath());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String toString() {
        return "image: " + ((EMAImageMessageBody) this.emaObject).displayName() + ", localurl: " + ((EMAImageMessageBody) this.emaObject).getLocalUrl() + ", remoteurl: " + ((EMAImageMessageBody) this.emaObject).getRemoteUrl() + ", thumbnail: " + ((EMAImageMessageBody) this.emaObject).thumbnailRemotePath();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(((EMAImageMessageBody) this.emaObject).displayName());
        parcel.writeString(((EMAImageMessageBody) this.emaObject).getLocalUrl());
        parcel.writeString(((EMAImageMessageBody) this.emaObject).getRemoteUrl());
        parcel.writeString(((EMAImageMessageBody) this.emaObject).thumbnailRemotePath());
        parcel.writeInt(((EMAImageMessageBody) this.emaObject).width());
        parcel.writeInt(((EMAImageMessageBody) this.emaObject).height());
    }
}
