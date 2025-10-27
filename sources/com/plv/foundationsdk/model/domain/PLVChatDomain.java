package com.plv.foundationsdk.model.domain;

import android.text.TextUtils;
import com.plv.foundationsdk.model.PLVFoundationVO;

/* loaded from: classes4.dex */
public class PLVChatDomain implements PLVFoundationVO {
    private static final String DEFAULT_CHAT_API_DOMAIN = "apichat.polyv.net";
    private static final String DEFAULT_CHAT_DOMAIN = "chat.polyv.net";
    private static final String HOST = "https://";
    private String chatApiDomain;
    private String chatDomain;

    public PLVChatDomain() {
        this.chatDomain = "https://chat.polyv.net";
        this.chatApiDomain = "https://apichat.polyv.net/";
    }

    public String getChatApiDomain() {
        if (TextUtils.isEmpty(this.chatApiDomain)) {
            return "https://apichat.polyv.net";
        }
        if (this.chatApiDomain.startsWith("http")) {
            return this.chatApiDomain;
        }
        return "https://" + this.chatApiDomain;
    }

    public String getChatDomain() {
        if (TextUtils.isEmpty(this.chatDomain)) {
            return "https://chat.polyv.net";
        }
        if (this.chatDomain.startsWith("http")) {
            return this.chatDomain;
        }
        return "https://" + this.chatDomain;
    }

    public void setChatApiDomain(String str) {
        this.chatApiDomain = str;
    }

    public void setChatDomain(String str) {
        this.chatDomain = str;
    }

    public PLVChatDomain(String str, String str2) {
        this.chatDomain = str;
        this.chatApiDomain = str2;
    }
}
