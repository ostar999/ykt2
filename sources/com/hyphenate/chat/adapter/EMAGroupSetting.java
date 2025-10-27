package com.hyphenate.chat.adapter;

/* loaded from: classes4.dex */
public class EMAGroupSetting extends EMABase {
    public static final int EMAGroupStyle_PRIVATE_MEMBER_INVITE = 1;
    public static final int EMAGroupStyle_PRIVATE_OWNER_INVITE = 0;
    public static final int EMAGroupStyle_PUBLIC_ANONYMOUS = 4;
    public static final int EMAGroupStyle_PUBLIC_JOIN_APPROVAL = 2;
    public static final int EMAGroupStyle_PUBLIC_JOIN_OPEN = 3;

    public EMAGroupSetting() {
        nativeInit(0, 200, false, "");
    }

    public EMAGroupSetting(int i2, int i3, boolean z2, String str) {
        nativeInit(i2, i3, z2, str);
    }

    public EMAGroupSetting(EMAGroupSetting eMAGroupSetting) {
        nativeInit(eMAGroupSetting);
    }

    public String extension() {
        return nativeExtension();
    }

    public void finalize() throws Throwable {
        nativeFinalize();
        super.finalize();
    }

    public boolean inviteNeedConfirm() {
        return nativeInviteNeedConfirm();
    }

    public int maxUserCount() {
        return nativeMaxUserCount();
    }

    public native String nativeExtension();

    public native void nativeFinalize();

    public native void nativeInit(int i2, int i3, boolean z2, String str);

    public native void nativeInit(EMAGroupSetting eMAGroupSetting);

    public native boolean nativeInviteNeedConfirm();

    public native int nativeMaxUserCount();

    public native void nativeSetMaxUserCount(int i2);

    public native void nativeSetStyle(int i2);

    public native int nativeStyle();

    public void setMaxUserCount(int i2) {
        nativeSetMaxUserCount(i2);
    }

    public void setStyle(int i2) {
        nativeSetStyle(i2);
    }

    public int style() {
        return nativeStyle();
    }
}
