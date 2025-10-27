package com.aliyun.player.nativeclass;

import android.content.Context;
import com.aliyun.player.source.PlayAuthInfo;
import com.aliyun.player.source.StsInfo;
import com.aliyun.utils.f;

/* loaded from: classes2.dex */
public class JniSaasListPlayer extends JniUrlListPlayer {
    private static final String TAG = "JniSaasListPlayer";

    static {
        f.b();
    }

    public JniSaasListPlayer(Context context, long j2, long j3) {
        super(context, j2, j3);
    }

    public static void loadClass() {
    }

    public void addVid(String str, String str2) {
        nAddVid(str, str2);
    }

    public boolean moveTo(String str, PlayAuthInfo playAuthInfo) {
        return nMoveToWithPlayAuth(str, playAuthInfo);
    }

    public boolean moveTo(String str, StsInfo stsInfo) {
        return nMoveToWithSts(str, stsInfo);
    }

    public boolean moveToNext(PlayAuthInfo playAuthInfo, boolean z2) {
        return nMoveToNextWithPlayAuth(playAuthInfo, z2);
    }

    public boolean moveToNext(StsInfo stsInfo, boolean z2) {
        return nMoveToNextWithSts(stsInfo, z2);
    }

    public boolean moveToPrev(PlayAuthInfo playAuthInfo) {
        return nMoveToPrevWithPlayAuth(playAuthInfo);
    }

    public boolean moveToPrev(StsInfo stsInfo) {
        return nMoveToPrevWithSts(stsInfo);
    }

    public native void nAddVid(String str, String str2);

    public native boolean nMoveToNextWithPlayAuth(PlayAuthInfo playAuthInfo, boolean z2);

    public native boolean nMoveToNextWithSts(StsInfo stsInfo, boolean z2);

    public native boolean nMoveToPrevWithPlayAuth(PlayAuthInfo playAuthInfo);

    public native boolean nMoveToPrevWithSts(StsInfo stsInfo);

    public native boolean nMoveToWithPlayAuth(String str, PlayAuthInfo playAuthInfo);

    public native boolean nMoveToWithSts(String str, StsInfo stsInfo);

    public native void nSetDefinition(String str);

    public void setDefinition(String str) {
        nSetDefinition(str);
    }
}
