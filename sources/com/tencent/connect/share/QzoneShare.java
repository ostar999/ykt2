package com.tencent.connect.share;

import android.content.Context;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;

/* loaded from: classes6.dex */
public class QzoneShare extends BaseApi {
    public static final String SHARE_TO_QQ_APP_NAME = "appName";
    public static final String SHARE_TO_QQ_AUDIO_URL = "audio_url";
    public static final String SHARE_TO_QQ_EXT_INT = "cflag";
    public static final String SHARE_TO_QQ_EXT_STR = "share_qq_ext_str";
    public static final String SHARE_TO_QQ_IMAGE_LOCAL_URL = "imageLocalUrl";
    public static final String SHARE_TO_QQ_IMAGE_URL = "imageUrl";
    public static final String SHARE_TO_QQ_SITE = "site";
    public static final String SHARE_TO_QQ_SUMMARY = "summary";
    public static final String SHARE_TO_QQ_TARGET_URL = "targetUrl";
    public static final String SHARE_TO_QQ_TITLE = "title";
    public static final String SHARE_TO_QZONE_EXTMAP = "extMap";
    public static final String SHARE_TO_QZONE_KEY_TYPE = "req_type";
    public static final int SHARE_TO_QZONE_TYPE_IMAGE = 5;
    public static final int SHARE_TO_QZONE_TYPE_IMAGE_TEXT = 1;
    public static final int SHARE_TO_QZONE_TYPE_MINI_PROGRAM = 7;
    public static final int SHARE_TO_QZONE_TYPE_NO_TYPE = 0;

    /* renamed from: a, reason: collision with root package name */
    private boolean f18124a;

    /* renamed from: d, reason: collision with root package name */
    private boolean f18125d;

    /* renamed from: e, reason: collision with root package name */
    private boolean f18126e;

    /* renamed from: f, reason: collision with root package name */
    private boolean f18127f;
    public String mViaShareQzoneType;

    public QzoneShare(Context context, QQToken qQToken) {
        super(qQToken);
        this.mViaShareQzoneType = "";
        this.f18124a = true;
        this.f18125d = false;
        this.f18126e = false;
        this.f18127f = false;
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0126  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0156  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0178  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x019a  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x01b4  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x01d6  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x01f8  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x021a  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x025c  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0282  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x02a8  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x02ce  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x02f0  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x037a  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0392  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x03bc  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0404  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void b(android.app.Activity r26, android.os.Bundle r27, com.tencent.tauth.IUiListener r28) throws org.json.JSONException, java.lang.IllegalAccessException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
        /*
            Method dump skipped, instructions count: 1107
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.connect.share.QzoneShare.b(android.app.Activity, android.os.Bundle, com.tencent.tauth.IUiListener):void");
    }

    @Override // com.tencent.connect.common.BaseApi
    public void releaseResource() {
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x036b  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x036f  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x03b2  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x03bd  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00d9  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x018a  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x01e5  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x026b  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0278  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x02f5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void shareToQzone(android.app.Activity r29, android.os.Bundle r30, com.tencent.tauth.IUiListener r31) {
        /*
            Method dump skipped, instructions count: 1121
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.connect.share.QzoneShare.shareToQzone(android.app.Activity, android.os.Bundle, com.tencent.tauth.IUiListener):void");
    }
}
