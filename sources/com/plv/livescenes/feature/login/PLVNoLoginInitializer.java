package com.plv.livescenes.feature.login;

import android.text.TextUtils;
import com.easefun.polyv.livescenes.config.PolyvLiveSDKClient;
import com.plv.foundationsdk.manager.PLVChatDomainManager;
import com.plv.foundationsdk.model.domain.PLVChatDomain;
import com.plv.linkmic.PLVLinkMicConstant;
import com.plv.livescenes.linkmic.manager.PLVLinkMicConfig;

/* loaded from: classes4.dex */
public class PLVNoLoginInitializer {
    public static boolean initLive(String str, String str2, String str3, String str4, int i2, PLVChatDomain pLVChatDomain) {
        String str5;
        if (isEmpty(str2) || isEmpty(str4) || isEmpty(str) || isEmpty(str3)) {
            return false;
        }
        if (i2 == 1) {
            str5 = PLVLinkMicConstant.RtcType.RTC_TYPE_U;
        } else {
            if (i2 != 2) {
                return false;
            }
            str5 = PLVLinkMicConstant.RtcType.RTC_TYPE_A;
        }
        PolyvLiveSDKClient.getInstance().setAppIdSecret(str2, str3, str4);
        PolyvLiveSDKClient.getInstance().setChannelId(str);
        PLVLinkMicConfig.getInstance().setRtcType(str5);
        if (pLVChatDomain != null) {
            PLVChatDomainManager.getInstance().setChatDomain(pLVChatDomain);
        }
        return true;
    }

    public static boolean initPlayback(String str, String str2, String str3, String str4) {
        if (isEmpty(str2) || isEmpty(str4) || isEmpty(str) || isEmpty(str3)) {
            return false;
        }
        PolyvLiveSDKClient.getInstance().setAppIdSecret(str2, str3, str4);
        PolyvLiveSDKClient.getInstance().setChannelId(str);
        return true;
    }

    private static boolean isEmpty(String str) {
        return TextUtils.isEmpty(str);
    }
}
