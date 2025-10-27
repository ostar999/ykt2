package com.tencent.tbs.one;

import java.util.Map;

/* loaded from: classes6.dex */
public interface TBSOneEventEmitter {

    public enum UnloadedBehavior {
        IGNORE,
        LOAD,
        INSTALL
    }

    void emit(UnloadedBehavior unloadedBehavior, String str, String str2, String str3, Map<String, Object> map);

    void emit(String str, Map<String, Object> map);
}
