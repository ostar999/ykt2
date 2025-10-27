package com.tencent.live2.impl;

import cn.hutool.core.text.StrPool;

/* loaded from: classes6.dex */
public class a {

    /* renamed from: com.tencent.live2.impl.a$a, reason: collision with other inner class name */
    public static class C0343a {

        /* renamed from: a, reason: collision with root package name */
        public int f20406a;

        /* renamed from: b, reason: collision with root package name */
        public int f20407b;

        public String toString() {
            return "[width:" + this.f20406a + "][height:" + this.f20407b + StrPool.BRACKET_END;
        }
    }

    public enum b {
        TXLiveAsyncState_None,
        TXLiveAsyncState_Starting,
        TXLiveAsyncState_Stopping
    }

    public enum c {
        V2TXLiveProtocolTypeROOM,
        V2TXLiveProtocolTypeTRTC,
        V2TXLiveProtocolTypeRTMP,
        V2TXLiveProtocolTypeWEBRTC
    }
}
