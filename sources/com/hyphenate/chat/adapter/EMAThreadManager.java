package com.hyphenate.chat.adapter;

import com.hyphenate.chat.EMCursorResult;
import com.hyphenate.chat.adapter.message.EMAMessage;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class EMAThreadManager extends EMABase {
    public void addListener(EMAThreadManagerListener eMAThreadManagerListener) {
        nativeAddListener(eMAThreadManagerListener);
    }

    public EMAThreadInfo createThread(String str, String str2, String str3, EMAError eMAError) {
        return nativeCreateThread(str, str2, str3, eMAError);
    }

    public void destroyThread(String str, EMAError eMAError) {
        nativeDestroyThread(str, eMAError);
    }

    public EMCursorResult<String> fetchThreadMembers(String str, int i2, String str2, EMAError eMAError) {
        return nativeFetchThreadMembers(str, i2, str2, eMAError);
    }

    public EMCursorResult<EMAThreadInfo> getJoinedThreadsFromServer(int i2, String str, EMAError eMAError) {
        return nativeGetJoinedThreadsFromServer(i2, str, eMAError);
    }

    public EMCursorResult<EMAThreadInfo> getJoinedThreadsFromServer(String str, int i2, String str2, EMAError eMAError) {
        return nativeGetJoinedThreadsFromServer(str, i2, str2, eMAError);
    }

    public EMAThreadInfo getThreadFromServer(String str, EMAError eMAError) {
        return nativeGetThreadFromServer(str, eMAError);
    }

    public EMCursorResult<EMAThreadInfo> getThreadsFromServer(String str, int i2, String str2, EMAError eMAError) {
        return nativeGetThreadsFromServer(str, i2, str2, eMAError);
    }

    public Map<String, EMAMessage> getThreadsLatestMessage(List<String> list, EMAError eMAError) {
        return nativeGetThreadsLatestMessage(list, eMAError);
    }

    public EMAThreadInfo joinThread(String str, EMAError eMAError) {
        return nativeJoinThread(str, eMAError);
    }

    public void leaveThread(String str, EMAError eMAError) {
        nativeLeaveThread(str, eMAError);
    }

    public native void nativeAddListener(EMAThreadManagerListener eMAThreadManagerListener);

    public native void nativeChangeThreadName(String str, String str2, EMAError eMAError);

    public native EMAThreadInfo nativeCreateThread(String str, String str2, String str3, EMAError eMAError);

    public native void nativeDestroyThread(String str, EMAError eMAError);

    public native EMCursorResult<String> nativeFetchThreadMembers(String str, int i2, String str2, EMAError eMAError);

    public native EMCursorResult<EMAThreadInfo> nativeGetJoinedThreadsFromServer(int i2, String str, EMAError eMAError);

    public native EMCursorResult<EMAThreadInfo> nativeGetJoinedThreadsFromServer(String str, int i2, String str2, EMAError eMAError);

    public native EMAThreadInfo nativeGetThreadFromServer(String str, EMAError eMAError);

    public native EMCursorResult<EMAThreadInfo> nativeGetThreadsFromServer(String str, int i2, String str2, EMAError eMAError);

    public native Map<String, EMAMessage> nativeGetThreadsLatestMessage(List<String> list, EMAError eMAError);

    public native EMAThreadInfo nativeJoinThread(String str, EMAError eMAError);

    public native void nativeLeaveThread(String str, EMAError eMAError);

    public native void nativeRemoveListener(EMAThreadManagerListener eMAThreadManagerListener);

    public native void nativeRemoveMemberFromThread(String str, String str2, EMAError eMAError);

    public native void nativeRemoveMembersFromThread(String str, List<String> list, EMAError eMAError);

    public void removeListener(EMAThreadManagerListener eMAThreadManagerListener) {
        nativeRemoveListener(eMAThreadManagerListener);
    }

    public void removeMemberFromThread(String str, String str2, EMAError eMAError) {
        nativeRemoveMemberFromThread(str, str2, eMAError);
    }

    public void removeMembersFromThread(String str, List<String> list, EMAError eMAError) {
        nativeRemoveMembersFromThread(str, list, eMAError);
    }

    public void updateChatThreadName(String str, String str2, EMAError eMAError) {
        nativeChangeThreadName(str, str2, eMAError);
    }
}
