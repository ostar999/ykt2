package com.hyphenate.chat.adapter;

import java.util.List;

/* loaded from: classes4.dex */
public class EMAPresenceManager extends EMABase {
    public void addListener(EMAPresenceManagerListener eMAPresenceManagerListener) {
        nativeAddListener(eMAPresenceManagerListener);
    }

    public List<EMAPresence> fetchPresenceStatus(List<String> list, EMAError eMAError) {
        return nativeFetchPresenceStatus(list, eMAError);
    }

    public List<String> fetchSubscribedMembers(int i2, int i3, EMAError eMAError) {
        return nativeFetchSubscribedMembers(i2, i3, eMAError);
    }

    public native void nativeAddListener(EMAPresenceManagerListener eMAPresenceManagerListener);

    public native void nativeClearListener();

    public native List<EMAPresence> nativeFetchPresenceStatus(List<String> list, EMAError eMAError);

    public native List<String> nativeFetchSubscribedMembers(int i2, int i3, EMAError eMAError);

    public native void nativePublishPresence(int i2, String str, EMAError eMAError);

    public native void nativeRemoveListener(EMAPresenceManagerListener eMAPresenceManagerListener);

    public native List<EMAPresence> nativeSubscribePresences(List<String> list, long j2, EMAError eMAError);

    public native void nativeUnsubscribePresences(List<String> list, EMAError eMAError);

    public void publishPresence(String str, EMAError eMAError) {
        nativePublishPresence(1, str, eMAError);
    }

    public List<EMAPresence> subscribePresences(List<String> list, long j2, EMAError eMAError) {
        return nativeSubscribePresences(list, j2, eMAError);
    }

    public void unsubscribePresences(List<String> list, EMAError eMAError) {
        nativeUnsubscribePresences(list, eMAError);
    }
}
