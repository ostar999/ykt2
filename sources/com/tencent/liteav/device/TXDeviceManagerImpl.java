package com.tencent.liteav.device;

import android.os.Handler;
import android.os.Looper;
import com.tencent.liteav.audio.TXCAudioEngine;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.module.Monitor;
import com.tencent.liteav.d;
import com.tencent.liteav.device.TXDeviceManager;
import com.tencent.liteav.g;

/* loaded from: classes6.dex */
public class TXDeviceManagerImpl implements TXDeviceManager {
    public static final int AUDIO_ROUTE_EARPIECE = 1;
    public static final int AUDIO_ROUTE_SPEAKER = 0;
    public static final int SystemVolumeTypeAuto = 0;
    public static final int SystemVolumeTypeMedia = 1;
    public static final int SystemVolumeTypeVOIP = 2;
    public static final String TAG = "TXDeviceManagerImpl";
    protected d mCaptureAndEnc;
    private TXDeviceManagerListener mDeviceManagerListener;
    private boolean mIsFrontCamera;
    protected Handler mSDKHandler;

    /* renamed from: com.tencent.liteav.device.TXDeviceManagerImpl$8, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass8 {
        static final /* synthetic */ int[] $SwitchMap$com$tencent$liteav$device$TXDeviceManager$TXAudioRoute;
        static final /* synthetic */ int[] $SwitchMap$com$tencent$liteav$device$TXDeviceManager$TXCameraCaptureMode;
        static final /* synthetic */ int[] $SwitchMap$com$tencent$liteav$device$TXDeviceManager$TXSystemVolumeType;

        static {
            int[] iArr = new int[TXDeviceManager.TXCameraCaptureMode.values().length];
            $SwitchMap$com$tencent$liteav$device$TXDeviceManager$TXCameraCaptureMode = iArr;
            try {
                iArr[TXDeviceManager.TXCameraCaptureMode.TXCameraResolutionStrategyPerformance.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$tencent$liteav$device$TXDeviceManager$TXCameraCaptureMode[TXDeviceManager.TXCameraCaptureMode.TXCameraResolutionStrategyHighQuality.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$tencent$liteav$device$TXDeviceManager$TXCameraCaptureMode[TXDeviceManager.TXCameraCaptureMode.TXCameraCaptureManual.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            int[] iArr2 = new int[TXDeviceManager.TXSystemVolumeType.values().length];
            $SwitchMap$com$tencent$liteav$device$TXDeviceManager$TXSystemVolumeType = iArr2;
            try {
                iArr2[TXDeviceManager.TXSystemVolumeType.TXSystemVolumeTypeAuto.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$tencent$liteav$device$TXDeviceManager$TXSystemVolumeType[TXDeviceManager.TXSystemVolumeType.TXSystemVolumeTypeVOIP.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$tencent$liteav$device$TXDeviceManager$TXSystemVolumeType[TXDeviceManager.TXSystemVolumeType.TXSystemVolumeTypeMedia.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            int[] iArr3 = new int[TXDeviceManager.TXAudioRoute.values().length];
            $SwitchMap$com$tencent$liteav$device$TXDeviceManager$TXAudioRoute = iArr3;
            try {
                iArr3[TXDeviceManager.TXAudioRoute.TXAudioRouteSpeakerphone.ordinal()] = 1;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$tencent$liteav$device$TXDeviceManager$TXAudioRoute[TXDeviceManager.TXAudioRoute.TXAudioRouteEarpiece.ordinal()] = 2;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    public interface TXDeviceManagerListener {
        void onCameraParamChange(g gVar);

        void onSwitchAutoFocus(boolean z2);

        void onSwitchCamera(boolean z2);

        void onSwitchSystemVolumeType(TXDeviceManager.TXSystemVolumeType tXSystemVolumeType);
    }

    public TXDeviceManagerImpl(Handler handler) {
        this.mSDKHandler = handler;
    }

    public void apiLog(String str) {
        TXCLog.i(TAG, "trtc_api TXDeviceManager:" + str);
    }

    public void apiOnlineLog(String str, Object... objArr) {
        Monitor.a(1, String.format(str, objArr), "", 0, "trtc_api TXDeviceManager:");
    }

    @Override // com.tencent.liteav.device.TXDeviceManager
    public int enableCameraAutoFocus(final boolean z2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.device.TXDeviceManagerImpl.3
            @Override // java.lang.Runnable
            public void run() {
                TXDeviceManagerImpl.this.apiLog("enableCameraAutoFocus " + z2);
                g gVarC = TXDeviceManagerImpl.this.mCaptureAndEnc.c();
                gVarC.N = z2 ^ true;
                TXDeviceManagerImpl.this.mCaptureAndEnc.a(gVarC);
                if (TXDeviceManagerImpl.this.mDeviceManagerListener != null) {
                    TXDeviceManagerImpl.this.mDeviceManagerListener.onSwitchAutoFocus(z2);
                }
            }
        });
        return 0;
    }

    @Override // com.tencent.liteav.device.TXDeviceManager
    public boolean enableCameraTorch(boolean z2) {
        apiLog("enableCameraTorch " + z2);
        return this.mCaptureAndEnc.e(z2);
    }

    @Override // com.tencent.liteav.device.TXDeviceManager
    public float getCameraZoomMaxRatio() {
        return this.mCaptureAndEnc.p();
    }

    @Override // com.tencent.liteav.device.TXDeviceManager
    public boolean isAutoFocusEnabled() {
        return !this.mCaptureAndEnc.c().N;
    }

    @Override // com.tencent.liteav.device.TXDeviceManager
    public boolean isFrontCamera() {
        return this.mIsFrontCamera;
    }

    public void runOnSDKThread(Runnable runnable) {
        if (this.mSDKHandler != null) {
            if (Looper.myLooper() != this.mSDKHandler.getLooper()) {
                this.mSDKHandler.post(runnable);
            } else {
                runnable.run();
            }
        }
    }

    @Override // com.tencent.liteav.device.TXDeviceManager
    public int setAudioRoute(final TXDeviceManager.TXAudioRoute tXAudioRoute) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.device.TXDeviceManagerImpl.5
            @Override // java.lang.Runnable
            public void run() {
                TXDeviceManagerImpl tXDeviceManagerImpl = TXDeviceManagerImpl.this;
                StringBuilder sb = new StringBuilder();
                sb.append("setAudioRoute route:");
                sb.append(tXAudioRoute == TXDeviceManager.TXAudioRoute.TXAudioRouteEarpiece ? "earpiece" : "speakerphone");
                tXDeviceManagerImpl.apiOnlineLog(sb.toString(), new Object[0]);
                int i2 = AnonymousClass8.$SwitchMap$com$tencent$liteav$device$TXDeviceManager$TXAudioRoute[tXAudioRoute.ordinal()];
                if (i2 == 1) {
                    TXCAudioEngine.setAudioRoute(0);
                } else {
                    if (i2 != 2) {
                        return;
                    }
                    TXCAudioEngine.setAudioRoute(1);
                }
            }
        });
        return 0;
    }

    @Override // com.tencent.liteav.device.TXDeviceManager
    public void setCameraCapturerParam(final TXDeviceManager.TXCameraCaptureParam tXCameraCaptureParam) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.device.TXDeviceManagerImpl.7
            @Override // java.lang.Runnable
            public void run() {
                if (tXCameraCaptureParam == null) {
                    TXDeviceManagerImpl.this.apiLog("setCameraCapturerParam error when params is null");
                    return;
                }
                g gVar = new g();
                int i2 = AnonymousClass8.$SwitchMap$com$tencent$liteav$device$TXDeviceManager$TXCameraCaptureMode[tXCameraCaptureParam.mode.ordinal()];
                if (i2 == 1) {
                    gVar.X = true;
                    gVar.W = false;
                } else if (i2 == 2) {
                    gVar.W = true;
                    gVar.X = false;
                } else if (i2 != 3) {
                    gVar.W = false;
                    gVar.X = false;
                } else {
                    gVar.W = false;
                    gVar.X = false;
                    TXDeviceManager.TXCameraCaptureParam tXCameraCaptureParam2 = tXCameraCaptureParam;
                    gVar.f19329c = tXCameraCaptureParam2.width;
                    gVar.f19330d = tXCameraCaptureParam2.height;
                }
                TXDeviceManagerImpl.this.apiLog("setCameraCapturerParam mode:" + tXCameraCaptureParam.mode + " width:" + tXCameraCaptureParam.width + " height:" + tXCameraCaptureParam.height);
                if (TXDeviceManagerImpl.this.mDeviceManagerListener != null) {
                    TXDeviceManagerImpl.this.mDeviceManagerListener.onCameraParamChange(gVar);
                }
            }
        });
    }

    @Override // com.tencent.liteav.device.TXDeviceManager
    public int setCameraFocusPosition(final int i2, final int i3) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.device.TXDeviceManagerImpl.4
            @Override // java.lang.Runnable
            public void run() {
                TXDeviceManagerImpl.this.apiLog("setCameraFocusPosition x:" + i2 + "y:" + i3);
                TXDeviceManagerImpl.this.mCaptureAndEnc.b(i2, i3);
            }
        });
        return 0;
    }

    @Override // com.tencent.liteav.device.TXDeviceManager
    public int setCameraZoomRatio(final float f2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.device.TXDeviceManagerImpl.2
            @Override // java.lang.Runnable
            public void run() {
                TXDeviceManagerImpl.this.apiLog("setCameraZoomRatio " + f2);
                TXDeviceManagerImpl.this.mCaptureAndEnc.i((int) f2);
            }
        });
        return 0;
    }

    public void setCaptureAndEnc(d dVar) {
        this.mCaptureAndEnc = dVar;
    }

    public void setDeviceManagerListener(TXDeviceManagerListener tXDeviceManagerListener) {
        this.mDeviceManagerListener = tXDeviceManagerListener;
    }

    public void setFrontCamera(boolean z2) {
        this.mIsFrontCamera = z2;
    }

    @Override // com.tencent.liteav.device.TXDeviceManager
    public int setSystemVolumeType(final TXDeviceManager.TXSystemVolumeType tXSystemVolumeType) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.device.TXDeviceManagerImpl.6
            @Override // java.lang.Runnable
            public void run() {
                TXDeviceManagerImpl.this.apiOnlineLog("setSystemVolumeType type:%s,  auto(0),media(1),VOIP(2)", tXSystemVolumeType.name());
                int i2 = AnonymousClass8.$SwitchMap$com$tencent$liteav$device$TXDeviceManager$TXSystemVolumeType[tXSystemVolumeType.ordinal()];
                if (i2 == 1) {
                    TXCAudioEngine.setSystemVolumeType(0);
                } else if (i2 == 2) {
                    TXCAudioEngine.setSystemVolumeType(2);
                } else if (i2 == 3) {
                    TXCAudioEngine.setSystemVolumeType(1);
                }
                if (TXDeviceManagerImpl.this.mDeviceManagerListener != null) {
                    TXDeviceManagerImpl.this.mDeviceManagerListener.onSwitchSystemVolumeType(tXSystemVolumeType);
                }
            }
        });
        return 0;
    }

    @Override // com.tencent.liteav.device.TXDeviceManager
    public int switchCamera(final boolean z2) {
        final boolean z3 = this.mIsFrontCamera;
        this.mIsFrontCamera = z2;
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.device.TXDeviceManagerImpl.1
            @Override // java.lang.Runnable
            public void run() {
                if (z3 != z2) {
                    TXDeviceManagerImpl.this.mCaptureAndEnc.j();
                    if (TXDeviceManagerImpl.this.mDeviceManagerListener != null) {
                        TXDeviceManagerImpl.this.mDeviceManagerListener.onSwitchCamera(z2);
                    }
                }
            }
        });
        return 0;
    }
}
