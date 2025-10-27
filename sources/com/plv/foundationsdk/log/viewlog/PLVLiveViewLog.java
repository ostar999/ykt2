package com.plv.foundationsdk.log.viewlog;

import android.text.TextUtils;
import android.util.Base64;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.plv.foundationsdk.log.PLVAnalyticsBase;
import com.plv.foundationsdk.net.PLVResponseExcutor;
import com.plv.foundationsdk.net.api.PLVFoundationApiManager;
import com.plv.foundationsdk.utils.PLVUtils;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public abstract class PLVLiveViewLog extends PLVAnalyticsBase {
    private static String base64Encoder(String str) {
        return TextUtils.isEmpty(str) ? str : Base64.encodeToString(str.getBytes(), 10);
    }

    private static void sendHttpRequest(String str) {
        PLVResponseExcutor.excuteResponseBodyData(PLVFoundationApiManager.getPlvUrlApi().requestUrl(str), null);
    }

    public Map<String, String> createStaticNormalInfo(@NonNull String str, @NonNull String str2, @NonNull String str3, @NonNull String str4, @Nullable String str5, @NonNull String str6, @NonNull String str7, @NonNull String str8, String str9, String str10, @Nullable String str11, @Nullable String str12, @Nullable String str13, @NonNull String str14, int i2, @Nullable String str15) {
        HashMap map = new HashMap();
        map.put("pid", str);
        map.put("uid", str2);
        map.put("cid", str3);
        map.put("ts", String.valueOf(System.currentTimeMillis()));
        map.put("pn", createProjectInfo().getSDKName());
        map.put(SocializeProtocolConstants.PROTOCOL_KEY_PV, createProjectInfo().getSDKVersion());
        map.put(SocializeProtocolConstants.PROTOCOL_KEY_SID, str4);
        if (!TextUtils.isEmpty(str5)) {
            map.put("csid", str5);
        }
        map.put("p1", base64Encoder(str6));
        map.put("p2", base64Encoder(str7));
        map.put("p3", base64Encoder(str8));
        map.put("p4", base64Encoder(str9));
        map.put("p5", base64Encoder(str10));
        if (!TextUtils.isEmpty(str11)) {
            map.put("ptype", str11);
        }
        if (!TextUtils.isEmpty(str12)) {
            map.put("pb", str12 + "");
        }
        if (!TextUtils.isEmpty(str13)) {
            map.put("fid", str13);
        }
        map.put("playerid", str14);
        map.put(AliyunLogKey.KEY_PRODUCT, i2 + "");
        if (!TextUtils.isEmpty(str15)) {
            map.put("cts", str15);
        }
        return map;
    }

    public void statCast(@NonNull String str, @NonNull String str2, @NonNull String str3, long j2, int i2, int i3, @NonNull String str4, @NonNull String str5, @NonNull String str6, @NonNull String str7, @NonNull String str8, @NonNull String str9) {
        sendHttpRequest("https://rtas.videocc.net/v2/wdview?pid=" + str + "&uid=" + str2 + "&cid=" + str3 + "&flow=" + j2 + "&pd=" + i2 + "&sd=" + i3 + "&ts=" + String.valueOf(System.currentTimeMillis()) + "&sign=" + PLVUtils.MD5("rtas.net" + str + str3 + j2 + i2) + "&session_id=" + base64Encoder(str4) + "&pn=" + createProjectInfo().getSDKName() + "&pv=" + createProjectInfo().getSDKVersion() + "&param1=" + base64Encoder(str5) + "&param2=" + base64Encoder(str6) + "&param3=" + base64Encoder(str7) + "&param4=" + base64Encoder(str8) + "&param5=" + base64Encoder(str9));
    }

    public void statLive(@NonNull String str, @NonNull String str2, @NonNull String str3, long j2, int i2, int i3, @NonNull String str4, @NonNull String str5, @NonNull String str6, @NonNull String str7, @NonNull String str8, @NonNull String str9, @NonNull String str10, @Nullable String str11) {
        String str12 = "https://rtas.videocc.net/v2/view?pid=" + str + "&uid=" + str2 + "&cid=" + str3 + "&flow=" + j2 + "&pd=" + i2 + "&sd=" + i3 + "&ts=" + String.valueOf(System.currentTimeMillis()) + "&sign=" + PLVUtils.MD5("rtas.net" + str + str3 + j2 + i2) + "&session_id=" + base64Encoder(str4) + "&pn=" + createProjectInfo().getSDKName() + "&pv=" + createProjectInfo().getSDKVersion() + "&ptype=" + str5 + "&param1=" + base64Encoder(str6) + "&param2=" + base64Encoder(str7) + "&param3=" + base64Encoder(str8) + "&param4=" + base64Encoder(str9) + "&param5=" + base64Encoder(str10);
        if (str11 != null) {
            str12 = str12 + "&pb=" + str11;
        }
        sendHttpRequest(str12);
    }
}
