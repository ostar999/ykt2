package com.hyphenate.chat;

import com.hyphenate.chat.adapter.EMAPresence;
import java.util.Map;

/* loaded from: classes4.dex */
public class EMPresence extends EMBase<EMAPresence> {
    /* JADX WARN: Multi-variable type inference failed */
    public EMPresence(EMAPresence eMAPresence) {
        this.emaObject = eMAPresence;
    }

    private String getStatuListString(Map<String, Integer> map) {
        String str = "";
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            str = str + "platform=" + entry.getKey() + ",value=" + entry.getValue() + "\n";
        }
        return str;
    }

    @Override // com.hyphenate.chat.EMBase
    public /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public long getExpiryTime() {
        return ((EMAPresence) this.emaObject).getExpiryTime();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getExt() {
        return ((EMAPresence) this.emaObject).getExt();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public long getLatestTime() {
        return ((EMAPresence) this.emaObject).getLatestTime();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getPublisher() {
        return ((EMAPresence) this.emaObject).getPublisher();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Map<String, Integer> getStatusList() {
        return ((EMAPresence) this.emaObject).getStatusList();
    }

    @Override // com.hyphenate.chat.EMBase
    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    public String toString() {
        return "EMPresence{ \n publisher=" + getPublisher() + "，\n ext=" + getExt() + "，\n latesttime=" + getLatestTime() + "，\n expirytime=" + getExpiryTime() + "，\n statusList=" + getStatuListString(getStatusList()) + " }";
    }
}
