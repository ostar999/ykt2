package com.hyphenate.chat.adapter;

import com.hyphenate.chat.EMCursorResult;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes4.dex */
public class EMAReactionManager extends EMABase {
    private final Set<EMAReactionManagerListener> mListeners = new HashSet();

    public void addListener(EMAReactionManagerListener eMAReactionManagerListener) {
        this.mListeners.add(eMAReactionManagerListener);
        nativeAddListener(eMAReactionManagerListener);
    }

    public void addReaction(String str, String str2, EMAError eMAError) {
        nativeAddReaction(str, str2, eMAError);
    }

    public void clearListeners() {
        this.mListeners.clear();
        nativeClearListeners();
    }

    public EMCursorResult<EMAMessageReaction> getReactionDetail(String str, String str2, String str3, int i2, EMAError eMAError) {
        return nativeGetReactionDetail(str, str2, str3, i2, eMAError);
    }

    public Map<String, List<EMAMessageReaction>> getReactionList(List<String> list, String str, String str2, EMAError eMAError) {
        return nativeGetReactionList(list, str, str2, eMAError);
    }

    public native void nativeAddListener(EMAReactionManagerListener eMAReactionManagerListener);

    public native void nativeAddReaction(String str, String str2, EMAError eMAError);

    public native void nativeClearListeners();

    public native EMCursorResult<EMAMessageReaction> nativeGetReactionDetail(String str, String str2, String str3, int i2, EMAError eMAError);

    public native Map<String, List<EMAMessageReaction>> nativeGetReactionList(List<String> list, String str, String str2, EMAError eMAError);

    public native void nativeRemoveListener(EMAReactionManagerListener eMAReactionManagerListener);

    public native void nativeRemoveReaction(String str, String str2, EMAError eMAError);

    public void removeListener(EMAReactionManagerListener eMAReactionManagerListener) {
        this.mListeners.remove(eMAReactionManagerListener);
        nativeRemoveListener(eMAReactionManagerListener);
    }

    public void removeReaction(String str, String str2, EMAError eMAError) {
        nativeRemoveReaction(str, str2, eMAError);
    }
}
