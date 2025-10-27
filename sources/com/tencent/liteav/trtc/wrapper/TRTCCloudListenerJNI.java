package com.tencent.liteav.trtc.wrapper;

import android.os.Bundle;
import com.tencent.trtc.TRTCCloudDef;
import com.tencent.trtc.TRTCCloudListener;
import com.tencent.trtc.TRTCStatistics;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* loaded from: classes6.dex */
public class TRTCCloudListenerJNI extends TRTCCloudListener {
    private Set<Long> mCallbackSet = new HashSet();

    private native void nativeOnCameraDidReady(long j2);

    private native void nativeOnConnectOtherRoom(long j2, String str, int i2, String str2);

    private native void nativeOnConnectionLost(long j2);

    private native void nativeOnConnectionRecovery(long j2);

    private native void nativeOnDisConnectOtherRoom(long j2, int i2, String str);

    private native void nativeOnEnterRoom(long j2, long j3);

    private native void nativeOnError(long j2, int i2, String str, Bundle bundle);

    private native void nativeOnExitRoom(long j2, int i2);

    private native void nativeOnFirstAudioFrame(long j2, String str);

    private native void nativeOnFirstVideoFrame(long j2, String str, int i2, int i3, int i4);

    private native void nativeOnMicDidReady(long j2);

    private native void nativeOnMissCustomCmdMsg(long j2, String str, int i2, int i3, int i4);

    private native void nativeOnNetworkQuality(long j2, TRTCCloudDef.TRTCQuality tRTCQuality, ArrayList<TRTCCloudDef.TRTCQuality> arrayList);

    private native void nativeOnRecvCustomCmdMsg(long j2, String str, int i2, int i3, byte[] bArr);

    private native void nativeOnRecvSEIMsg(long j2, String str, byte[] bArr);

    private native void nativeOnRemoteUserEnterRoom(long j2, String str);

    private native void nativeOnRemoteUserLeaveRoom(long j2, String str, int i2);

    private native void nativeOnRemoteVideoStatusUpdated(long j2, String str, int i2, int i3, int i4, Bundle bundle);

    private native void nativeOnScreenCapturePaused(long j2);

    private native void nativeOnScreenCaptureResumed(long j2);

    private native void nativeOnScreenCaptureStarted(long j2);

    private native void nativeOnScreenCaptureStopped(long j2, int i2);

    private native void nativeOnSendFirstLocalAudioFrame(long j2);

    private native void nativeOnSendFirstLocalVideoFrame(long j2, int i2);

    private native void nativeOnSetMixTranscodingConfig(long j2, int i2, String str);

    private native void nativeOnSpeedTest(long j2, TRTCCloudDef.TRTCSpeedTestResult tRTCSpeedTestResult, int i2, int i3);

    private native void nativeOnSpeedTestResult(long j2, TRTCCloudDef.TRTCSpeedTestResult tRTCSpeedTestResult);

    private native void nativeOnStartPublishCDNStream(long j2, int i2, String str);

    private native void nativeOnStartPublishing(long j2, int i2, String str);

    private native void nativeOnStatistics(long j2, TRTCStatistics tRTCStatistics);

    private native void nativeOnStopPublishCDNStream(long j2, int i2, String str);

    private native void nativeOnStopPublishing(long j2, int i2, String str);

    private native void nativeOnSwitchRole(long j2, int i2, String str);

    private native void nativeOnSwitchRoom(long j2, int i2, String str);

    private native void nativeOnTryToReconnect(long j2);

    private native void nativeOnUserAudioAvailable(long j2, String str, boolean z2);

    private native void nativeOnUserSubStreamAvailable(long j2, String str, boolean z2);

    private native void nativeOnUserVideoAvailable(long j2, String str, boolean z2);

    private native void nativeOnUserVoiceVolume(long j2, ArrayList<TRTCCloudDef.TRTCVolumeInfo> arrayList, int i2);

    private native void nativeOnWarning(long j2, int i2, String str, Bundle bundle);

    public void addCallback(long j2) {
        synchronized (this) {
            this.mCallbackSet.add(Long.valueOf(j2));
        }
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onCameraDidReady() {
        synchronized (this) {
            Iterator<Long> it = this.mCallbackSet.iterator();
            while (it.hasNext()) {
                nativeOnCameraDidReady(it.next().longValue());
            }
        }
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onConnectOtherRoom(String str, int i2, String str2) {
        synchronized (this) {
            Iterator<Long> it = this.mCallbackSet.iterator();
            while (it.hasNext()) {
                nativeOnConnectOtherRoom(it.next().longValue(), str, i2, str2);
            }
        }
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onConnectionLost() {
        synchronized (this) {
            Iterator<Long> it = this.mCallbackSet.iterator();
            while (it.hasNext()) {
                nativeOnConnectionLost(it.next().longValue());
            }
        }
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onConnectionRecovery() {
        synchronized (this) {
            Iterator<Long> it = this.mCallbackSet.iterator();
            while (it.hasNext()) {
                nativeOnConnectionRecovery(it.next().longValue());
            }
        }
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onDisConnectOtherRoom(int i2, String str) {
        synchronized (this) {
            Iterator<Long> it = this.mCallbackSet.iterator();
            while (it.hasNext()) {
                nativeOnDisConnectOtherRoom(it.next().longValue(), i2, str);
            }
        }
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onEnterRoom(long j2) {
        synchronized (this) {
            Iterator<Long> it = this.mCallbackSet.iterator();
            while (it.hasNext()) {
                nativeOnEnterRoom(it.next().longValue(), j2);
            }
        }
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onError(int i2, String str, Bundle bundle) {
        synchronized (this) {
            Iterator<Long> it = this.mCallbackSet.iterator();
            while (it.hasNext()) {
                nativeOnError(it.next().longValue(), i2, str, bundle);
            }
        }
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onExitRoom(int i2) {
        synchronized (this) {
            Iterator<Long> it = this.mCallbackSet.iterator();
            while (it.hasNext()) {
                nativeOnExitRoom(it.next().longValue(), i2);
            }
        }
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onFirstAudioFrame(String str) {
        synchronized (this) {
            Iterator<Long> it = this.mCallbackSet.iterator();
            while (it.hasNext()) {
                nativeOnFirstAudioFrame(it.next().longValue(), str);
            }
        }
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onFirstVideoFrame(String str, int i2, int i3, int i4) {
        synchronized (this) {
            Iterator<Long> it = this.mCallbackSet.iterator();
            while (it.hasNext()) {
                nativeOnFirstVideoFrame(it.next().longValue(), str, i2, i3, i4);
            }
        }
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onMicDidReady() {
        synchronized (this) {
            Iterator<Long> it = this.mCallbackSet.iterator();
            while (it.hasNext()) {
                nativeOnMicDidReady(it.next().longValue());
            }
        }
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onMissCustomCmdMsg(String str, int i2, int i3, int i4) {
        synchronized (this) {
            Iterator<Long> it = this.mCallbackSet.iterator();
            while (it.hasNext()) {
                nativeOnMissCustomCmdMsg(it.next().longValue(), str, i2, i3, i4);
            }
        }
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onNetworkQuality(TRTCCloudDef.TRTCQuality tRTCQuality, ArrayList<TRTCCloudDef.TRTCQuality> arrayList) {
        synchronized (this) {
            Iterator<Long> it = this.mCallbackSet.iterator();
            while (it.hasNext()) {
                nativeOnNetworkQuality(it.next().longValue(), tRTCQuality, arrayList);
            }
        }
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onRecvCustomCmdMsg(String str, int i2, int i3, byte[] bArr) {
        synchronized (this) {
            Iterator<Long> it = this.mCallbackSet.iterator();
            while (it.hasNext()) {
                nativeOnRecvCustomCmdMsg(it.next().longValue(), str, i2, i3, bArr);
            }
        }
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onRecvSEIMsg(String str, byte[] bArr) {
        synchronized (this) {
            Iterator<Long> it = this.mCallbackSet.iterator();
            while (it.hasNext()) {
                nativeOnRecvSEIMsg(it.next().longValue(), str, bArr);
            }
        }
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onRemoteUserEnterRoom(String str) {
        synchronized (this) {
            Iterator<Long> it = this.mCallbackSet.iterator();
            while (it.hasNext()) {
                nativeOnRemoteUserEnterRoom(it.next().longValue(), str);
            }
        }
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onRemoteUserLeaveRoom(String str, int i2) {
        synchronized (this) {
            Iterator<Long> it = this.mCallbackSet.iterator();
            while (it.hasNext()) {
                nativeOnRemoteUserLeaveRoom(it.next().longValue(), str, i2);
            }
        }
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onRemoteVideoStatusUpdated(String str, int i2, int i3, int i4, Bundle bundle) {
        synchronized (this) {
            Iterator<Long> it = this.mCallbackSet.iterator();
            while (it.hasNext()) {
                nativeOnRemoteVideoStatusUpdated(it.next().longValue(), str, i2, i3, i4, bundle);
            }
        }
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onScreenCapturePaused() {
        synchronized (this) {
            Iterator<Long> it = this.mCallbackSet.iterator();
            while (it.hasNext()) {
                nativeOnScreenCapturePaused(it.next().longValue());
            }
        }
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onScreenCaptureResumed() {
        synchronized (this) {
            Iterator<Long> it = this.mCallbackSet.iterator();
            while (it.hasNext()) {
                nativeOnScreenCaptureResumed(it.next().longValue());
            }
        }
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onScreenCaptureStarted() {
        synchronized (this) {
            Iterator<Long> it = this.mCallbackSet.iterator();
            while (it.hasNext()) {
                nativeOnScreenCaptureStarted(it.next().longValue());
            }
        }
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onScreenCaptureStopped(int i2) {
        synchronized (this) {
            Iterator<Long> it = this.mCallbackSet.iterator();
            while (it.hasNext()) {
                nativeOnScreenCaptureStopped(it.next().longValue(), i2);
            }
        }
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onSendFirstLocalAudioFrame() {
        synchronized (this) {
            Iterator<Long> it = this.mCallbackSet.iterator();
            while (it.hasNext()) {
                nativeOnSendFirstLocalAudioFrame(it.next().longValue());
            }
        }
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onSendFirstLocalVideoFrame(int i2) {
        synchronized (this) {
            Iterator<Long> it = this.mCallbackSet.iterator();
            while (it.hasNext()) {
                nativeOnSendFirstLocalVideoFrame(it.next().longValue(), i2);
            }
        }
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onSetMixTranscodingConfig(int i2, String str) {
        synchronized (this) {
            Iterator<Long> it = this.mCallbackSet.iterator();
            while (it.hasNext()) {
                nativeOnSetMixTranscodingConfig(it.next().longValue(), i2, str);
            }
        }
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onSpeedTest(TRTCCloudDef.TRTCSpeedTestResult tRTCSpeedTestResult, int i2, int i3) {
        synchronized (this) {
            Iterator<Long> it = this.mCallbackSet.iterator();
            while (it.hasNext()) {
                nativeOnSpeedTest(it.next().longValue(), tRTCSpeedTestResult, i2, i3);
            }
        }
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onSpeedTestResult(TRTCCloudDef.TRTCSpeedTestResult tRTCSpeedTestResult) {
        synchronized (this) {
            Iterator<Long> it = this.mCallbackSet.iterator();
            while (it.hasNext()) {
                nativeOnSpeedTestResult(it.next().longValue(), tRTCSpeedTestResult);
            }
        }
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onStartPublishCDNStream(int i2, String str) {
        synchronized (this) {
            Iterator<Long> it = this.mCallbackSet.iterator();
            while (it.hasNext()) {
                nativeOnStartPublishCDNStream(it.next().longValue(), i2, str);
            }
        }
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onStartPublishing(int i2, String str) {
        synchronized (this) {
            Iterator<Long> it = this.mCallbackSet.iterator();
            while (it.hasNext()) {
                nativeOnStartPublishing(it.next().longValue(), i2, str);
            }
        }
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onStatistics(TRTCStatistics tRTCStatistics) {
        synchronized (this) {
            Iterator<Long> it = this.mCallbackSet.iterator();
            while (it.hasNext()) {
                nativeOnStatistics(it.next().longValue(), tRTCStatistics);
            }
        }
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onStopPublishCDNStream(int i2, String str) {
        synchronized (this) {
            Iterator<Long> it = this.mCallbackSet.iterator();
            while (it.hasNext()) {
                nativeOnStopPublishCDNStream(it.next().longValue(), i2, str);
            }
        }
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onStopPublishing(int i2, String str) {
        synchronized (this) {
            Iterator<Long> it = this.mCallbackSet.iterator();
            while (it.hasNext()) {
                nativeOnStopPublishing(it.next().longValue(), i2, str);
            }
        }
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onSwitchRole(int i2, String str) {
        synchronized (this) {
            Iterator<Long> it = this.mCallbackSet.iterator();
            while (it.hasNext()) {
                nativeOnSwitchRole(it.next().longValue(), i2, str);
            }
        }
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onSwitchRoom(int i2, String str) {
        synchronized (this) {
            Iterator<Long> it = this.mCallbackSet.iterator();
            while (it.hasNext()) {
                nativeOnSwitchRoom(it.next().longValue(), i2, str);
            }
        }
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onTryToReconnect() {
        synchronized (this) {
            Iterator<Long> it = this.mCallbackSet.iterator();
            while (it.hasNext()) {
                nativeOnTryToReconnect(it.next().longValue());
            }
        }
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onUserAudioAvailable(String str, boolean z2) {
        synchronized (this) {
            Iterator<Long> it = this.mCallbackSet.iterator();
            while (it.hasNext()) {
                nativeOnUserAudioAvailable(it.next().longValue(), str, z2);
            }
        }
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onUserSubStreamAvailable(String str, boolean z2) {
        synchronized (this) {
            Iterator<Long> it = this.mCallbackSet.iterator();
            while (it.hasNext()) {
                nativeOnUserSubStreamAvailable(it.next().longValue(), str, z2);
            }
        }
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onUserVideoAvailable(String str, boolean z2) {
        synchronized (this) {
            Iterator<Long> it = this.mCallbackSet.iterator();
            while (it.hasNext()) {
                nativeOnUserVideoAvailable(it.next().longValue(), str, z2);
            }
        }
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onUserVoiceVolume(ArrayList<TRTCCloudDef.TRTCVolumeInfo> arrayList, int i2) {
        synchronized (this) {
            Iterator<Long> it = this.mCallbackSet.iterator();
            while (it.hasNext()) {
                nativeOnUserVoiceVolume(it.next().longValue(), arrayList, i2);
            }
        }
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onWarning(int i2, String str, Bundle bundle) {
        synchronized (this) {
            Iterator<Long> it = this.mCallbackSet.iterator();
            while (it.hasNext()) {
                nativeOnWarning(it.next().longValue(), i2, str, bundle);
            }
        }
    }

    public void removeCallback(long j2) {
        synchronized (this) {
            this.mCallbackSet.remove(Long.valueOf(j2));
        }
    }
}
