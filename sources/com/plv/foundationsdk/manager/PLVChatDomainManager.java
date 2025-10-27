package com.plv.foundationsdk.manager;

import androidx.annotation.NonNull;
import com.plv.foundationsdk.model.domain.PLVChatDomain;

/* loaded from: classes4.dex */
public class PLVChatDomainManager {
    private static PLVChatDomainManager instance;
    private PLVChatDomain chatDomain = new PLVChatDomain();

    public static PLVChatDomainManager getInstance() {
        if (instance == null) {
            instance = new PLVChatDomainManager();
        }
        return instance;
    }

    public PLVChatDomain getChatDomain() {
        PLVChatDomain pLVChatDomain = this.chatDomain;
        return pLVChatDomain == null ? new PLVChatDomain() : pLVChatDomain;
    }

    public void setChatDomain(@NonNull PLVChatDomain pLVChatDomain) {
        this.chatDomain = pLVChatDomain;
    }
}
