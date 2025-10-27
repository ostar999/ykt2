package com.plv.foundationsdk.log.viewlog;

import android.text.TextUtils;
import android.util.Base64;
import androidx.annotation.NonNull;
import com.plv.foundationsdk.log.PLVAnalyticsBase;
import com.plv.foundationsdk.net.PLVResponseExcutor;
import com.plv.foundationsdk.net.api.PLVFoundationApiManager;
import com.plv.foundationsdk.utils.PLVUtils;

/* loaded from: classes4.dex */
public abstract class PLVVodViewLog extends PLVAnalyticsBase {
    private static String base64Encoder(String str) {
        return TextUtils.isEmpty(str) ? str : Base64.encodeToString(str.getBytes(), 10);
    }

    private static void sendHttpRequest(String str) {
        PLVResponseExcutor.excuteResponseBodyData(PLVFoundationApiManager.getPlvUrlApi().requestUrl(str), null);
    }

    public void statVodPlay(@NonNull String str, @NonNull String str2, long j2, int i2, int i3, int i4, int i5, long j3, @NonNull String str3, @NonNull String str4, @NonNull String str5, @NonNull String str6, @NonNull String str7, @NonNull String str8, String str9) {
        sendHttpRequest("https://prtas.videocc.net/v2/view?pid=" + str + "&uid=" + createProjectInfo().getSDKUserId() + "&vid=" + str2 + "&flow=" + j2 + "&pd=" + i2 + "&sd=" + i3 + "&cts=" + i4 + "&duration=" + i5 + "&cataid=" + j3 + "&href=" + base64Encoder(str9) + "&pn=" + createProjectInfo().getSDKName() + "&pv=" + createProjectInfo().getSDKVersion() + "&sign=" + PLVUtils.MD5("rtas.net" + str + str2 + j2 + i2 + i4) + "&sid=" + base64Encoder(createProjectInfo().getSDKViewerId()) + "&param1=" + base64Encoder(str4) + "&param2=" + base64Encoder(str5) + "&param3=" + base64Encoder(str6) + "&param4=" + base64Encoder(str7) + "&param5=" + base64Encoder(str8));
    }
}
