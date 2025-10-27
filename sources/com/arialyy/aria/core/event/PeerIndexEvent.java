package com.arialyy.aria.core.event;

/* loaded from: classes2.dex */
public class PeerIndexEvent {
    public long createTime = System.currentTimeMillis();
    public String key;
    public int peerIndex;

    public PeerIndexEvent(String str, int i2) {
        this.peerIndex = i2;
        this.key = str;
    }
}
