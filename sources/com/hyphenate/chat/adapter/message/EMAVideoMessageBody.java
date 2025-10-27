package com.hyphenate.chat.adapter.message;

import android.net.Uri;
import com.hyphenate.chat.adapter.message.EMAFileMessageBody;
import com.hyphenate.util.EMFileHelper;

/* loaded from: classes4.dex */
public class EMAVideoMessageBody extends EMAFileMessageBody {
    private EMAVideoMessageBody() {
        super("", 2);
        nativeInit("", "");
    }

    public EMAVideoMessageBody(Uri uri, Uri uri2) {
        super(uri, 2);
        nativeInit(EMFileHelper.getInstance().uriToString(uri), EMFileHelper.getInstance().uriToString(uri2));
    }

    public EMAVideoMessageBody(EMAVideoMessageBody eMAVideoMessageBody) {
        super("", 2);
        nativeInit(eMAVideoMessageBody);
    }

    public EMAVideoMessageBody(String str, String str2) {
        super(str, 2);
        nativeInit(str, str2);
    }

    public int duration() {
        return nativeduration();
    }

    @Override // com.hyphenate.chat.adapter.message.EMAFileMessageBody
    public void finalize() throws Throwable {
        nativeFinalize();
        super.finalize();
    }

    public int height() {
        return nativeheight();
    }

    @Override // com.hyphenate.chat.adapter.message.EMAFileMessageBody
    public native void nativeFinalize();

    public native void nativeInit(EMAVideoMessageBody eMAVideoMessageBody);

    public native void nativeInit(String str, String str2);

    public native int nativeduration();

    public native int nativeheight();

    public native void nativesetDuration(int i2);

    public native void nativesetSize(int i2, int i3);

    public native void nativesetThumbnailDownloadStatus(int i2);

    public native void nativesetThumbnailLocalPath(String str);

    public native void nativesetThumbnailRemotePath(String str);

    public native void nativesetThumbnailSecretKey(String str);

    public native int nativethumbnailDownloadStatus();

    public native String nativethumbnailLocalPath();

    public native String nativethumbnailRemotePath();

    public native String nativethumbnailSecretKey();

    public native int nativewidth();

    public void setDuration(int i2) {
        nativesetDuration(i2);
    }

    public void setSize(int i2, int i3) {
        nativesetSize(i2, i3);
    }

    public void setThumbnailDownloadStatus(EMAFileMessageBody.EMADownloadStatus eMADownloadStatus) {
        nativesetThumbnailDownloadStatus(eMADownloadStatus.ordinal());
    }

    public void setThumbnailLocalPath(String str) {
        nativesetThumbnailLocalPath(str);
    }

    public void setThumbnailRemotePath(String str) {
        nativesetThumbnailRemotePath(str);
    }

    public void setThumbnailSecretKey(String str) {
        nativesetThumbnailSecretKey(str);
    }

    public EMAFileMessageBody.EMADownloadStatus thumbnailDownloadStatus() {
        int iNativethumbnailDownloadStatus = nativethumbnailDownloadStatus();
        EMAFileMessageBody.EMADownloadStatus eMADownloadStatus = EMAFileMessageBody.EMADownloadStatus.DOWNLOADING;
        if (iNativethumbnailDownloadStatus == eMADownloadStatus.ordinal()) {
            return eMADownloadStatus;
        }
        EMAFileMessageBody.EMADownloadStatus eMADownloadStatus2 = EMAFileMessageBody.EMADownloadStatus.SUCCESSED;
        if (iNativethumbnailDownloadStatus == eMADownloadStatus2.ordinal()) {
            return eMADownloadStatus2;
        }
        EMAFileMessageBody.EMADownloadStatus eMADownloadStatus3 = EMAFileMessageBody.EMADownloadStatus.FAILED;
        return iNativethumbnailDownloadStatus == eMADownloadStatus3.ordinal() ? eMADownloadStatus3 : EMAFileMessageBody.EMADownloadStatus.PENDING;
    }

    public String thumbnailLocalPath() {
        return nativethumbnailLocalPath();
    }

    public String thumbnailRemotePath() {
        return nativethumbnailRemotePath();
    }

    public String thumbnailSecretKey() {
        return nativethumbnailSecretKey();
    }

    public int width() {
        return nativewidth();
    }
}
