package com.hyphenate.chat.adapter.message;

import android.net.Uri;
import com.hyphenate.chat.adapter.EMACallback;
import com.hyphenate.util.EMFileHelper;

/* loaded from: classes4.dex */
public class EMAFileMessageBody extends EMAMessageBody {
    public static final int EMDownloadStatus_DOWNLOADING = 0;
    public static final int EMDownloadStatus_FAILED = 2;
    public static final int EMDownloadStatus_PENDING = 3;
    public static final int EMDownloadStatus_SUCCESSED = 1;

    public enum EMADownloadStatus {
        DOWNLOADING,
        SUCCESSED,
        FAILED,
        PENDING
    }

    private EMAFileMessageBody() {
        this("", 5);
    }

    public EMAFileMessageBody(Uri uri) {
        this(uri, 5);
    }

    public EMAFileMessageBody(Uri uri, int i2) {
        if (i2 == 5) {
            nativeInit(EMFileHelper.getInstance().uriToString(uri), i2);
        }
        this.type = i2;
    }

    public EMAFileMessageBody(EMAFileMessageBody eMAFileMessageBody) {
        nativeInit(eMAFileMessageBody);
    }

    public EMAFileMessageBody(String str) {
        this(EMFileHelper.getInstance().formatInUri(str));
    }

    public EMAFileMessageBody(String str, int i2) {
        this(EMFileHelper.getInstance().formatInUri(str), i2);
    }

    public String displayName() {
        return nativedisplayName();
    }

    public EMADownloadStatus downloadStatus() {
        int iNativedownloadStatus = nativedownloadStatus();
        EMADownloadStatus eMADownloadStatus = EMADownloadStatus.DOWNLOADING;
        if (iNativedownloadStatus == eMADownloadStatus.ordinal()) {
            return eMADownloadStatus;
        }
        EMADownloadStatus eMADownloadStatus2 = EMADownloadStatus.SUCCESSED;
        if (iNativedownloadStatus == eMADownloadStatus2.ordinal()) {
            return eMADownloadStatus2;
        }
        EMADownloadStatus eMADownloadStatus3 = EMADownloadStatus.FAILED;
        return iNativedownloadStatus == eMADownloadStatus3.ordinal() ? eMADownloadStatus3 : EMADownloadStatus.PENDING;
    }

    public long fileLength() {
        return nativefileLength();
    }

    public void finalize() throws Throwable {
        if (this.type == 5) {
            nativeFinalize();
        }
        super.finalize();
    }

    public String getLocalUrl() {
        return nativelocalPath();
    }

    public String getRemoteUrl() {
        return nativeremotePath();
    }

    public String getSecret() {
        return nativesecretKey();
    }

    public native void nativeFinalize();

    public native void nativeInit(EMAFileMessageBody eMAFileMessageBody);

    public native void nativeInit(String str, int i2);

    public native void nativeSetDownloadCallback(EMACallback eMACallback);

    public native String nativedisplayName();

    public native int nativedownloadStatus();

    public native long nativefileLength();

    public native String nativelocalPath();

    public native String nativeremotePath();

    public native String nativesecretKey();

    public native void nativesetDisplayName(String str);

    public native void nativesetDownloadStatus(int i2);

    public native void nativesetFileLength(long j2);

    public native void nativesetLocalPath(String str);

    public native void nativesetRemotePath(String str);

    public native void nativesetSecretKey(String str);

    public void setDisplayName(String str) {
        nativesetDisplayName(str);
    }

    public void setDownloadCallback(EMACallback eMACallback) {
        nativeSetDownloadCallback(eMACallback);
    }

    public void setDownloadStatus(EMADownloadStatus eMADownloadStatus) {
        nativesetDownloadStatus(eMADownloadStatus.ordinal());
    }

    public void setFileLength(long j2) {
        nativesetFileLength(j2);
    }

    public void setLocalPath(String str) {
        nativesetLocalPath(str);
    }

    public void setRemotePath(String str) {
        nativesetRemotePath(str);
    }

    public void setSecretKey(String str) {
        nativesetSecretKey(str);
    }
}
