package com.tencent.liteav.qos;

import android.os.Bundle;
import android.os.Handler;
import com.caverock.androidsvg.SVGParser;
import com.tencent.liteav.basic.b.b;
import com.tencent.liteav.basic.enums.c;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.liteav.basic.util.h;
import com.tencent.rtmp.TXLiveConstants;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public class TXCQoS {
    public static final int AUTO_ADJUST_LIVEPUSH_RESOLUTION_STRATEGY = 1;
    public static final int AUTO_ADJUST_REALTIME_VIDEOCHAT_STRATEGY = 5;
    private static final Map<Integer, c> RESOLUTION_MAP;
    static final String TAG = "TXCQos";
    private long mInstance;
    private a mListener;
    private b mNotifyListener;
    private long mInterval = 1000;
    private String mUserID = "";
    private boolean mIsEnableDrop = false;
    private int mBitrate = 0;
    private int mWidth = 0;
    private int mHeight = 0;
    private int mAutoStrategy = -1;
    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() { // from class: com.tencent.liteav.qos.TXCQoS.1
        @Override // java.lang.Runnable
        public void run() {
            if (TXCQoS.this.mListener != null) {
                int iA = TXCQoS.this.mListener.a();
                int iB = TXCQoS.this.mListener.b();
                int iC = TXCQoS.this.mListener.c();
                int iD = TXCQoS.this.mListener.d();
                int iE = TXCQoS.this.mListener.e();
                int iF = TXCQoS.this.mListener.f();
                int iG = TXCQoS.this.mListener.g();
                TXCQoS tXCQoS = TXCQoS.this;
                tXCQoS.nativeSetVideoRealBitrate(tXCQoS.mInstance, iA);
                TXCQoS tXCQoS2 = TXCQoS.this;
                tXCQoS2.nativeAdjustBitrate(tXCQoS2.mInstance, iD, iE, iF, iC, iB, iG);
                TXCQoS tXCQoS3 = TXCQoS.this;
                boolean zNativeIsEnableDrop = tXCQoS3.nativeIsEnableDrop(tXCQoS3.mInstance);
                if (TXCQoS.this.mIsEnableDrop != zNativeIsEnableDrop) {
                    TXCQoS.this.mIsEnableDrop = zNativeIsEnableDrop;
                    TXCQoS.this.mListener.a(zNativeIsEnableDrop);
                }
                TXCQoS tXCQoS4 = TXCQoS.this;
                int iNativeGetBitrate = tXCQoS4.nativeGetBitrate(tXCQoS4.mInstance);
                TXCQoS tXCQoS5 = TXCQoS.this;
                int iNativeGetWidth = tXCQoS5.nativeGetWidth(tXCQoS5.mInstance);
                TXCQoS tXCQoS6 = TXCQoS.this;
                int iNativeGetHeight = tXCQoS6.nativeGetHeight(tXCQoS6.mInstance);
                if (iNativeGetWidth == TXCQoS.this.mWidth && iNativeGetHeight == TXCQoS.this.mHeight) {
                    if (iNativeGetBitrate != TXCQoS.this.mBitrate) {
                        TXCQoS.this.mListener.a(iNativeGetBitrate, 0, 0);
                        if (TXCQoS.this.mNotifyListener != null) {
                            Bundle bundle = new Bundle();
                            bundle.putCharSequence(TXLiveConstants.EVT_DESCRIPTION, "Adjust encoding bitrate:new bitrate:" + iNativeGetBitrate);
                            bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
                            bundle.putString("EVT_USERID", TXCQoS.this.mUserID);
                            TXCQoS.this.mNotifyListener.onNotifyEvent(1006, bundle);
                        }
                    }
                } else if (TXCQoS.this.mAutoStrategy == 1 || TXCQoS.this.mAutoStrategy == 5) {
                    TXCQoS.this.mListener.a(iNativeGetBitrate, iNativeGetWidth, iNativeGetHeight);
                    if (TXCQoS.this.mNotifyListener != null) {
                        Bundle bundle2 = new Bundle();
                        bundle2.putCharSequence(TXLiveConstants.EVT_DESCRIPTION, "Adjust resolution:new bitrate:" + iNativeGetBitrate + " new resolution:" + iNativeGetWidth + "*" + iNativeGetHeight);
                        bundle2.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
                        TXCQoS.this.mNotifyListener.onNotifyEvent(1005, bundle2);
                    }
                }
                TXCQoS.this.mBitrate = iNativeGetBitrate;
                TXCQoS.this.mWidth = iNativeGetWidth;
                TXCQoS.this.mHeight = iNativeGetHeight;
            }
            TXCQoS.this.mHandler.postDelayed(this, TXCQoS.this.mInterval);
        }
    };

    static {
        HashMap map = new HashMap();
        map.put(0, c.RESOLUTION_TYPE_360_640);
        map.put(1, c.RESOLUTION_TYPE_540_960);
        map.put(2, c.RESOLUTION_TYPE_720_1280);
        map.put(3, c.RESOLUTION_TYPE_640_360);
        map.put(4, c.RESOLUTION_TYPE_960_540);
        map.put(5, c.RESOLUTION_TYPE_1280_720);
        map.put(6, c.RESOLUTION_TYPE_320_480);
        map.put(7, c.RESOLUTION_TYPE_180_320);
        map.put(8, c.RESOLUTION_TYPE_270_480);
        map.put(9, c.RESOLUTION_TYPE_320_180);
        map.put(10, c.RESOLUTION_TYPE_480_270);
        map.put(11, c.RESOLUTION_TYPE_240_320);
        map.put(12, c.RESOLUTION_TYPE_360_480);
        map.put(13, c.RESOLUTION_TYPE_480_640);
        map.put(14, c.RESOLUTION_TYPE_320_240);
        map.put(15, c.RESOLUTION_TYPE_480_360);
        map.put(16, c.RESOLUTION_TYPE_640_480);
        map.put(17, c.RESOLUTION_TYPE_480_480);
        map.put(18, c.RESOLUTION_TYPE_270_270);
        map.put(19, c.RESOLUTION_TYPE_160_160);
        RESOLUTION_MAP = Collections.unmodifiableMap(map);
        h.d();
    }

    public TXCQoS(boolean z2) {
        this.mInstance = nativeInit(z2);
    }

    public static c getProperResolutionByVideoBitrate(boolean z2, int i2, int i3) {
        return RESOLUTION_MAP.get(Integer.valueOf(nativeGetProperResolutionByVideoBitrate(z2, i2, i3)));
    }

    private native void nativeAddQueueInputSize(long j2, int i2);

    private native void nativeAddQueueOutputSize(long j2, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeAdjustBitrate(long j2, int i2, int i3, int i4, int i5, int i6, int i7);

    private native void nativeDeinit(long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public native int nativeGetBitrate(long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public native int nativeGetHeight(long j2);

    private static native int nativeGetProperResolutionByVideoBitrate(boolean z2, int i2, int i3);

    /* JADX INFO: Access modifiers changed from: private */
    public native int nativeGetWidth(long j2);

    private native long nativeInit(boolean z2);

    /* JADX INFO: Access modifiers changed from: private */
    public native boolean nativeIsEnableDrop(long j2);

    private native void nativeReset(long j2, boolean z2);

    private native void nativeSetAutoAdjustBitrate(long j2, boolean z2);

    private native void nativeSetAutoAdjustStrategy(long j2, int i2);

    private native void nativeSetHasVideo(long j2, boolean z2);

    private native void nativeSetVideoDefaultResolution(long j2, int i2);

    private native void nativeSetVideoEncBitrate(long j2, int i2, int i3, int i4);

    private native void nativeSetVideoExpectBitrate(long j2, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeSetVideoRealBitrate(long j2, int i2);

    public void finalize() throws Throwable {
        try {
            nativeDeinit(this.mInstance);
        } finally {
            super.finalize();
        }
    }

    public String getUserID() {
        return this.mUserID;
    }

    public boolean isEnableDrop() {
        return nativeIsEnableDrop(this.mInstance);
    }

    public void reset(boolean z2) {
        nativeReset(this.mInstance, z2);
    }

    public void setAutoAdjustBitrate(boolean z2) {
        StringBuilder sb = new StringBuilder();
        sb.append("autoAdjustBitrate is ");
        sb.append(z2 ? "yes" : SVGParser.XML_STYLESHEET_ATTR_ALTERNATE_NO);
        TXCLog.i(TAG, sb.toString());
        nativeSetAutoAdjustBitrate(this.mInstance, z2);
    }

    public void setAutoAdjustStrategy(int i2) {
        TXCLog.i(TAG, "autoAdjustStrategy is " + i2);
        nativeSetAutoAdjustStrategy(this.mInstance, i2);
        this.mAutoStrategy = i2;
    }

    public void setDefaultVideoResolution(c cVar) {
        TXCLog.i(TAG, "DefaultVideoResolution is " + cVar);
        int iIntValue = 0;
        this.mWidth = 0;
        this.mHeight = 0;
        for (Map.Entry<Integer, c> entry : RESOLUTION_MAP.entrySet()) {
            if (entry.getValue() == cVar) {
                iIntValue = entry.getKey().intValue();
            }
        }
        nativeSetVideoDefaultResolution(this.mInstance, iIntValue);
    }

    public void setHasVideo(boolean z2) {
        nativeSetHasVideo(this.mInstance, z2);
    }

    public void setListener(a aVar) {
        this.mListener = aVar;
    }

    public void setNotifyListener(b bVar) {
        this.mNotifyListener = bVar;
    }

    public void setUserID(String str) {
        this.mUserID = str;
    }

    public void setVideoEncBitrate(int i2, int i3, int i4) {
        this.mBitrate = 0;
        nativeSetVideoEncBitrate(this.mInstance, i2, i3, i4);
    }

    public void setVideoExpectBitrate(int i2) {
        nativeSetVideoExpectBitrate(this.mInstance, i2);
    }

    public void start(long j2) {
        this.mInterval = j2;
        this.mHandler.postDelayed(this.mRunnable, j2);
    }

    public void stop() {
        this.mHandler.removeCallbacks(this.mRunnable);
        this.mAutoStrategy = -1;
    }
}
