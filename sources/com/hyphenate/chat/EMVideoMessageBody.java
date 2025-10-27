package com.hyphenate.chat;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.hyphenate.chat.EMFileMessageBody;
import com.hyphenate.chat.adapter.message.EMAFileMessageBody;
import com.hyphenate.chat.adapter.message.EMAVideoMessageBody;
import com.hyphenate.util.EMFileHelper;
import com.hyphenate.util.EMLog;

/* loaded from: classes4.dex */
public class EMVideoMessageBody extends EMFileMessageBody implements Parcelable {
    public static final Parcelable.Creator<EMVideoMessageBody> CREATOR = new Parcelable.Creator<EMVideoMessageBody>() { // from class: com.hyphenate.chat.EMVideoMessageBody.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EMVideoMessageBody createFromParcel(Parcel parcel) {
            return new EMVideoMessageBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EMVideoMessageBody[] newArray(int i2) {
            return new EMVideoMessageBody[i2];
        }
    };
    private static final String TAG = "EMVideoMessageBody";

    /* renamed from: com.hyphenate.chat.EMVideoMessageBody$2, reason: invalid class name */
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

    public EMVideoMessageBody() {
        super("", 2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public EMVideoMessageBody(Uri uri, Uri uri2, int i2, long j2) {
        super(uri, 2);
        ((EMAVideoMessageBody) this.emaObject).setThumbnailLocalPath(EMFileHelper.getInstance().formatInUriToString(uri2));
        ((EMAVideoMessageBody) this.emaObject).setDuration(i2);
        ((EMAVideoMessageBody) this.emaObject).setDisplayName(EMFileHelper.getInstance().getFilename(uri));
        ((EMAVideoMessageBody) this.emaObject).setFileLength(j2);
        EMLog.d("videomsg", "create video, message body for:" + uri + " filename = " + EMFileHelper.getInstance().getFilename(uri));
        StringBuilder sb = new StringBuilder();
        sb.append("EMVideoMessageBody thumbPath = ");
        sb.append(uri2);
        EMLog.d(EMClient.TAG, sb.toString());
    }

    /* JADX WARN: Multi-variable type inference failed */
    private EMVideoMessageBody(Parcel parcel) {
        super("", 2);
        ((EMAVideoMessageBody) this.emaObject).setDisplayName(parcel.readString());
        ((EMAVideoMessageBody) this.emaObject).setLocalPath(parcel.readString());
        ((EMAVideoMessageBody) this.emaObject).setRemotePath(parcel.readString());
        ((EMAVideoMessageBody) this.emaObject).setThumbnailRemotePath(parcel.readString());
        ((EMAVideoMessageBody) this.emaObject).setThumbnailLocalPath(parcel.readString());
        ((EMAVideoMessageBody) this.emaObject).setDuration(parcel.readInt());
        ((EMAVideoMessageBody) this.emaObject).setFileLength(parcel.readLong());
        ((EMAVideoMessageBody) this.emaObject).setSize(parcel.readInt(), parcel.readInt());
    }

    public EMVideoMessageBody(EMAVideoMessageBody eMAVideoMessageBody) {
        super(eMAVideoMessageBody);
    }

    public EMVideoMessageBody(String str, String str2, int i2, long j2) {
        this(EMFileHelper.getInstance().formatInUri(str), EMFileHelper.getInstance().formatInUri(str2), i2, j2);
        EMLog.d(EMClient.TAG, "EMVideoMessageBody thumbPath = " + str2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public EMVideoMessageBody(String str, String str2, String str3, int i2) {
        super(str, 2);
        ((EMAVideoMessageBody) this.emaObject).setThumbnailLocalPath(str3);
        ((EMAVideoMessageBody) this.emaObject).setLocalPath(str);
        ((EMAVideoMessageBody) this.emaObject).setRemotePath(str2);
        ((EMAVideoMessageBody) this.emaObject).setThumbnailRemotePath(str3);
        ((EMAVideoMessageBody) this.emaObject).setFileLength(i2);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int getDuration() {
        return ((EMAVideoMessageBody) this.emaObject).duration();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getLocalThumb() {
        return EMFileHelper.getInstance().formatOutLocalUrl(((EMAVideoMessageBody) this.emaObject).thumbnailLocalPath());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Uri getLocalThumbUri() {
        return EMFileHelper.getInstance().formatOutUri(((EMAVideoMessageBody) this.emaObject).thumbnailLocalPath());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int getThumbnailHeight() {
        return ((EMAVideoMessageBody) this.emaObject).height();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getThumbnailSecret() {
        return ((EMAVideoMessageBody) this.emaObject).thumbnailSecretKey();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getThumbnailUrl() {
        return ((EMAVideoMessageBody) this.emaObject).thumbnailRemotePath();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int getThumbnailWidth() {
        return ((EMAVideoMessageBody) this.emaObject).width();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public long getVideoFileLength() {
        return ((EMAVideoMessageBody) this.emaObject).fileLength();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setLocalThumb(Uri uri) {
        ((EMAVideoMessageBody) this.emaObject).setThumbnailLocalPath(EMFileHelper.getInstance().formatInUriToString(uri));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setLocalThumb(String str) {
        ((EMAVideoMessageBody) this.emaObject).setThumbnailLocalPath(EMFileHelper.getInstance().formatInUriToString(str));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setThumbnailDownloadStatus(EMFileMessageBody.EMDownloadStatus eMDownloadStatus) {
        ((EMAVideoMessageBody) this.emaObject).setThumbnailDownloadStatus(EMAFileMessageBody.EMADownloadStatus.valueOf(eMDownloadStatus.name()));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setThumbnailSecret(String str) {
        ((EMAVideoMessageBody) this.emaObject).setThumbnailSecretKey(str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setThumbnailSize(int i2, int i3) {
        ((EMAVideoMessageBody) this.emaObject).setSize(i2, i3);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setThumbnailUrl(String str) {
        ((EMAVideoMessageBody) this.emaObject).setThumbnailRemotePath(str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setVideoFileLength(long j2) {
        ((EMAVideoMessageBody) this.emaObject).setFileLength(j2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public EMFileMessageBody.EMDownloadStatus thumbnailDownloadStatus() {
        int i2 = AnonymousClass2.$SwitchMap$com$hyphenate$chat$adapter$message$EMAFileMessageBody$EMADownloadStatus[((EMAVideoMessageBody) this.emaObject).thumbnailDownloadStatus().ordinal()];
        return i2 != 1 ? i2 != 2 ? i2 != 3 ? i2 != 4 ? EMFileMessageBody.EMDownloadStatus.SUCCESSED : EMFileMessageBody.EMDownloadStatus.PENDING : EMFileMessageBody.EMDownloadStatus.FAILED : EMFileMessageBody.EMDownloadStatus.SUCCESSED : EMFileMessageBody.EMDownloadStatus.DOWNLOADING;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String toString() {
        return "video: " + ((EMAVideoMessageBody) this.emaObject).displayName() + ", localUrl: " + ((EMAVideoMessageBody) this.emaObject).getLocalUrl() + ", remoteUrl: " + ((EMAVideoMessageBody) this.emaObject).getRemoteUrl() + ", thumbnailUrl: " + ((EMAVideoMessageBody) this.emaObject).thumbnailLocalPath() + ", length: " + ((EMAVideoMessageBody) this.emaObject).fileLength();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(((EMAVideoMessageBody) this.emaObject).displayName());
        parcel.writeString(((EMAVideoMessageBody) this.emaObject).getLocalUrl());
        parcel.writeString(((EMAVideoMessageBody) this.emaObject).getRemoteUrl());
        parcel.writeString(((EMAVideoMessageBody) this.emaObject).thumbnailLocalPath());
        parcel.writeString(((EMAVideoMessageBody) this.emaObject).thumbnailLocalPath());
        parcel.writeInt(((EMAVideoMessageBody) this.emaObject).duration());
        parcel.writeLong(((EMAVideoMessageBody) this.emaObject).fileLength());
        parcel.writeInt(((EMAVideoMessageBody) this.emaObject).width());
        parcel.writeInt(((EMAVideoMessageBody) this.emaObject).height());
    }
}
