package com.plv.linkmic.processor.c;

import com.plv.rtc.urtc.enummeration.URTCSdkMediaType;
import com.plv.rtc.urtc.model.URTCSdkStreamInfo;
import com.plv.rtc.urtc.view.URTCRendererViewWrapper;

/* loaded from: classes4.dex */
public class d {
    private String T;
    private URTCRendererViewWrapper aM;
    private URTCRendererViewWrapper aN;
    private URTCRendererViewWrapper aO;
    private URTCSdkStreamInfo aP;
    private URTCSdkStreamInfo aQ;
    private int aR;
    private boolean aL = false;
    private int renderMode = 1;

    /* renamed from: com.plv.linkmic.processor.c.d$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] aG;

        static {
            int[] iArr = new int[URTCSdkMediaType.values().length];
            aG = iArr;
            try {
                iArr[URTCSdkMediaType.UCLOUD_RTC_SDK_MEDIA_TYPE_VIDEO.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                aG[URTCSdkMediaType.UCLOUD_RTC_SDK_MEDIA_TYPE_SCREEN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public d(String str) {
        this.T = str;
    }

    public void a(String str) {
        this.T = str;
    }

    public void b(URTCSdkStreamInfo uRTCSdkStreamInfo) {
        this.aQ = uRTCSdkStreamInfo;
    }

    public void c(URTCRendererViewWrapper uRTCRendererViewWrapper) {
        this.aO = uRTCRendererViewWrapper;
    }

    public void e(int i2) {
        if (i2 == 1) {
            this.aN = null;
        } else if (i2 != 2) {
            this.aM = null;
        } else {
            this.aO = null;
        }
    }

    public String f() {
        return this.T;
    }

    public int getRenderMode() {
        return this.renderMode;
    }

    public URTCSdkStreamInfo m() {
        return this.aP;
    }

    public URTCSdkStreamInfo n() {
        return this.aQ;
    }

    public URTCRendererViewWrapper o() {
        return this.aM;
    }

    public URTCRendererViewWrapper p() {
        return this.aN;
    }

    public URTCRendererViewWrapper q() {
        return this.aO;
    }

    public boolean r() {
        return this.aL;
    }

    public URTCRendererViewWrapper s() {
        return f(this.aR);
    }

    public void setRenderMode(int i2) {
        this.renderMode = i2;
    }

    public void a(URTCSdkStreamInfo uRTCSdkStreamInfo) {
        this.aP = uRTCSdkStreamInfo;
    }

    public void b(URTCRendererViewWrapper uRTCRendererViewWrapper) {
        this.aN = uRTCRendererViewWrapper;
    }

    public void c(boolean z2) {
        this.aL = z2;
    }

    public URTCRendererViewWrapper f(int i2) {
        return i2 != 1 ? i2 != 2 ? this.aM : this.aO : this.aN;
    }

    public void a(URTCRendererViewWrapper uRTCRendererViewWrapper) {
        this.aM = uRTCRendererViewWrapper;
    }

    public void c(URTCSdkStreamInfo uRTCSdkStreamInfo) {
        URTCSdkMediaType mediaType = uRTCSdkStreamInfo.getMediaType();
        if (mediaType == null) {
            mediaType = URTCSdkMediaType.UCLOUD_RTC_SDK_MEDIA_TYPE_NULL;
        }
        int i2 = AnonymousClass1.aG[mediaType.ordinal()];
        if (i2 == 1) {
            this.aQ = uRTCSdkStreamInfo;
        } else {
            if (i2 != 2) {
                return;
            }
            this.aP = uRTCSdkStreamInfo;
        }
    }

    public void a(int i2, URTCRendererViewWrapper uRTCRendererViewWrapper) {
        this.aR = i2;
        if (i2 == 1) {
            this.aN = uRTCRendererViewWrapper;
        } else if (i2 != 2) {
            this.aM = uRTCRendererViewWrapper;
        } else {
            this.aO = uRTCRendererViewWrapper;
        }
    }
}
