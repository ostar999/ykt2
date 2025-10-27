package com.plv.linkmic.processor.b;

import android.os.Bundle;
import cn.hutool.core.text.StrPool;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.linkmic.PLVLinkMicEventHandler;
import com.plv.linkmic.screenshare.IPLVScreenShareListener;
import com.plv.rtc.trtc.PLVTRTCDef;
import com.plv.rtc.trtc.PLVTRTCEventListener;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes4.dex */
public class a extends com.plv.linkmic.processor.d {
    private static final String TAG = "a";
    private String uid;

    /* renamed from: z, reason: collision with root package name */
    private PLVTRTCEventListener f10809z = new PLVTRTCEventListener() { // from class: com.plv.linkmic.processor.b.a.1
        @Override // com.plv.rtc.trtc.PLVTRTCEventListener
        public void onEnterRoom(long j2) {
            PLVCommonLog.d(a.TAG, "onEnterRoom() called with: l = [" + j2 + StrPool.BRACKET_END);
            ((com.plv.linkmic.processor.d) a.this).f10819b.post(new Runnable() { // from class: com.plv.linkmic.processor.b.a.1.5
                @Override // java.lang.Runnable
                public void run() {
                    Iterator it = ((com.plv.linkmic.processor.d) a.this).f10818a.keySet().iterator();
                    while (it.hasNext()) {
                        ((PLVLinkMicEventHandler) it.next()).onJoinChannelSuccess(a.this.uid);
                    }
                }
            });
        }

        @Override // com.plv.rtc.trtc.PLVTRTCEventListener
        public void onError(int i2, String str, Bundle bundle) {
            PLVCommonLog.d(a.TAG, "onError() called with: i = [" + i2 + "], s = [" + str + "], bundle = [" + bundle + StrPool.BRACKET_END);
            final int i3 = 1060501;
            if (i2 != -1308 && i2 != -1309) {
                i3 = (i2 == -102015 || i2 == -102016 || i2 == -7001) ? IPLVScreenShareListener.PLV_SCREEN_SHARE_ERR : Integer.MAX_VALUE;
            }
            if (i3 != Integer.MAX_VALUE) {
                ((com.plv.linkmic.processor.d) a.this).f10819b.post(new Runnable() { // from class: com.plv.linkmic.processor.b.a.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Iterator it = ((com.plv.linkmic.processor.d) a.this).f10818a.keySet().iterator();
                        while (it.hasNext()) {
                            ((PLVLinkMicEventHandler) it.next()).onScreenShare(false, i3);
                        }
                    }
                });
            }
        }

        @Override // com.plv.rtc.trtc.PLVTRTCEventListener
        public void onExitRoom(int i2) {
            PLVCommonLog.d(a.TAG, "onExitRoom() called with: i = [" + i2 + StrPool.BRACKET_END);
        }

        @Override // com.plv.rtc.trtc.PLVTRTCEventListener
        public void onNetworkQuality(PLVTRTCDef.TRTCQuality tRTCQuality, ArrayList<PLVTRTCDef.TRTCQuality> arrayList) {
            int i2 = arrayList.isEmpty() ? tRTCQuality.quality : arrayList.get(0).quality;
            a aVar = a.this;
            aVar.a(aVar.a(tRTCQuality.quality), a.this.a(i2));
        }

        @Override // com.plv.rtc.trtc.PLVTRTCEventListener
        public void onRemoteUserEnterRoom(final String str) {
            PLVCommonLog.d(a.TAG, "onRemoteUserEnterRoom() called with: userId = [" + str + StrPool.BRACKET_END);
            ((com.plv.linkmic.processor.d) a.this).f10819b.post(new Runnable() { // from class: com.plv.linkmic.processor.b.a.1.6
                @Override // java.lang.Runnable
                public void run() {
                    for (PLVLinkMicEventHandler pLVLinkMicEventHandler : ((com.plv.linkmic.processor.d) a.this).f10818a.keySet()) {
                        pLVLinkMicEventHandler.onUserJoined(str);
                        pLVLinkMicEventHandler.onUserMuteVideo(str, true);
                        pLVLinkMicEventHandler.onUserMuteAudio(str, true);
                    }
                }
            });
        }

        @Override // com.plv.rtc.trtc.PLVTRTCEventListener
        public void onRemoteUserLeaveRoom(final String str, int i2) {
            PLVCommonLog.d(a.TAG, "onRemoteUserLeaveRoom() called with: userId = [" + str + "], reason = [" + i2 + StrPool.BRACKET_END);
            ((com.plv.linkmic.processor.d) a.this).f10819b.post(new Runnable() { // from class: com.plv.linkmic.processor.b.a.1.7
                @Override // java.lang.Runnable
                public void run() {
                    Iterator it = ((com.plv.linkmic.processor.d) a.this).f10818a.keySet().iterator();
                    while (it.hasNext()) {
                        ((PLVLinkMicEventHandler) it.next()).onUserOffline(str);
                    }
                }
            });
        }

        @Override // com.plv.rtc.trtc.PLVTRTCEventListener
        public void onScreenCapturePaused() {
            ((com.plv.linkmic.processor.d) a.this).f10819b.post(new Runnable() { // from class: com.plv.linkmic.processor.b.a.1.3
                @Override // java.lang.Runnable
                public void run() {
                    Iterator it = ((com.plv.linkmic.processor.d) a.this).f10818a.keySet().iterator();
                    while (it.hasNext()) {
                        ((PLVLinkMicEventHandler) it.next()).onScreenShare(false, IPLVScreenShareListener.PLV_SCREEN_SHARE_OK);
                    }
                }
            });
        }

        @Override // com.plv.rtc.trtc.PLVTRTCEventListener
        public void onScreenCaptureResumed() {
            ((com.plv.linkmic.processor.d) a.this).f10819b.post(new Runnable() { // from class: com.plv.linkmic.processor.b.a.1.2
                @Override // java.lang.Runnable
                public void run() {
                    Iterator it = ((com.plv.linkmic.processor.d) a.this).f10818a.keySet().iterator();
                    while (it.hasNext()) {
                        ((PLVLinkMicEventHandler) it.next()).onScreenShare(true, IPLVScreenShareListener.PLV_SCREEN_SHARE_OK);
                    }
                }
            });
        }

        @Override // com.plv.rtc.trtc.PLVTRTCEventListener
        public void onScreenCaptureStarted() {
            ((com.plv.linkmic.processor.d) a.this).f10819b.post(new Runnable() { // from class: com.plv.linkmic.processor.b.a.1.12
                @Override // java.lang.Runnable
                public void run() {
                    Iterator it = ((com.plv.linkmic.processor.d) a.this).f10818a.keySet().iterator();
                    while (it.hasNext()) {
                        ((PLVLinkMicEventHandler) it.next()).onScreenShare(true, IPLVScreenShareListener.PLV_SCREEN_SHARE_OK);
                    }
                }
            });
        }

        @Override // com.plv.rtc.trtc.PLVTRTCEventListener
        public void onScreenCaptureStopped(final int i2) {
            ((com.plv.linkmic.processor.d) a.this).f10819b.post(new Runnable() { // from class: com.plv.linkmic.processor.b.a.1.4
                @Override // java.lang.Runnable
                public void run() {
                    for (PLVLinkMicEventHandler pLVLinkMicEventHandler : ((com.plv.linkmic.processor.d) a.this).f10818a.keySet()) {
                        if (i2 == 0) {
                            pLVLinkMicEventHandler.onScreenShare(false, IPLVScreenShareListener.PLV_SCREEN_SHARE_OK);
                        } else {
                            pLVLinkMicEventHandler.onScreenShare(false, IPLVScreenShareListener.PLV_SCREEN_SHARE_ERR);
                        }
                    }
                }
            });
        }

        @Override // com.plv.rtc.trtc.PLVTRTCEventListener
        public void onSendFirstLocalAudioFrame() {
            PLVCommonLog.d(a.TAG, "onSendFirstLocalAudioFrame() called");
        }

        @Override // com.plv.rtc.trtc.PLVTRTCEventListener
        public void onSendFirstLocalVideoFrame(int i2) {
            PLVCommonLog.d(a.TAG, "onSendFirstLocalVideoFrame() called with: i = [" + i2 + StrPool.BRACKET_END);
        }

        @Override // com.plv.rtc.trtc.PLVTRTCEventListener
        public void onSetMixTranscodingConfig(int i2, String str) {
            PLVCommonLog.d(a.TAG, "onSetMixTranscodingConfig() called with: err = [" + i2 + "], errMsg = [" + str + StrPool.BRACKET_END);
        }

        @Override // com.plv.rtc.trtc.PLVTRTCEventListener
        public void onStartPublishCDNStream(int i2, String str) {
            PLVCommonLog.d(a.TAG, "onStartPublishCDNStream() called with: err = [" + i2 + "], errMsg = [" + str + StrPool.BRACKET_END);
            ((com.plv.linkmic.processor.d) a.this).f10819b.post(new Runnable() { // from class: com.plv.linkmic.processor.b.a.1.11
                @Override // java.lang.Runnable
                public void run() {
                    Iterator it = ((com.plv.linkmic.processor.d) a.this).f10818a.keySet().iterator();
                    while (it.hasNext()) {
                        ((PLVLinkMicEventHandler) it.next()).onStreamPublished("", 0);
                    }
                }
            });
        }

        @Override // com.plv.rtc.trtc.PLVTRTCEventListener
        public void onStartPublishing(int i2, String str) {
            PLVCommonLog.d(a.TAG, "onStartPublishing() called with: var1 = [" + i2 + "], var2 = [" + str + StrPool.BRACKET_END);
        }

        @Override // com.plv.rtc.trtc.PLVTRTCEventListener
        public void onStopPublishCDNStream(int i2, String str) {
            PLVCommonLog.d(a.TAG, "onStopPublishCDNStream() called with: err = [" + i2 + "], errMsg = [" + str + StrPool.BRACKET_END);
            Iterator it = ((com.plv.linkmic.processor.d) a.this).f10818a.keySet().iterator();
            while (it.hasNext()) {
                ((PLVLinkMicEventHandler) it.next()).onStreamUnpublished("");
            }
        }

        @Override // com.plv.rtc.trtc.PLVTRTCEventListener
        public void onStopPublishing(int i2, String str) {
            PLVCommonLog.d(a.TAG, "onStopPublishing() called with: var1 = [" + i2 + "], var2 = [" + str + StrPool.BRACKET_END);
        }

        @Override // com.plv.rtc.trtc.PLVTRTCEventListener
        public void onUserAudioAvailable(final String str, final boolean z2) {
            PLVCommonLog.d(a.TAG, "onUserAudioAvailable() called with: userId = [" + str + "], available = [" + z2 + StrPool.BRACKET_END);
            ((com.plv.linkmic.processor.d) a.this).f10819b.post(new Runnable() { // from class: com.plv.linkmic.processor.b.a.1.9
                @Override // java.lang.Runnable
                public void run() {
                    Iterator it = ((com.plv.linkmic.processor.d) a.this).f10818a.keySet().iterator();
                    while (it.hasNext()) {
                        ((PLVLinkMicEventHandler) it.next()).onUserMuteAudio(str, !z2);
                    }
                }
            });
        }

        @Override // com.plv.rtc.trtc.PLVTRTCEventListener
        public void onUserVideoAvailable(final String str, final boolean z2) {
            PLVCommonLog.d(a.TAG, "onUserVideoAvailable() called with: userId = [" + str + "], available = [" + z2 + StrPool.BRACKET_END);
            ((com.plv.linkmic.processor.d) a.this).f10819b.post(new Runnable() { // from class: com.plv.linkmic.processor.b.a.1.8
                @Override // java.lang.Runnable
                public void run() {
                    Iterator it = ((com.plv.linkmic.processor.d) a.this).f10818a.keySet().iterator();
                    while (it.hasNext()) {
                        ((PLVLinkMicEventHandler) it.next()).onUserMuteVideo(str, !z2);
                    }
                }
            });
        }

        @Override // com.plv.rtc.trtc.PLVTRTCEventListener
        public void onUserVoiceVolume(ArrayList<PLVTRTCDef.TRTCVolumeInfo> arrayList, int i2) {
            final ArrayList arrayList2 = new ArrayList(arrayList.size());
            Iterator<PLVTRTCDef.TRTCVolumeInfo> it = arrayList.iterator();
            final PLVLinkMicEventHandler.PLVAudioVolumeInfo pLVAudioVolumeInfo = null;
            while (it.hasNext()) {
                PLVTRTCDef.TRTCVolumeInfo next = it.next();
                String str = next.userId;
                int i3 = next.volume;
                if (str.equals(a.this.uid)) {
                    pLVAudioVolumeInfo = new PLVLinkMicEventHandler.PLVAudioVolumeInfo(str, i3);
                } else {
                    arrayList2.add(new PLVLinkMicEventHandler.PLVAudioVolumeInfo(str, i3));
                }
            }
            ((com.plv.linkmic.processor.d) a.this).f10819b.post(new Runnable() { // from class: com.plv.linkmic.processor.b.a.1.10
                @Override // java.lang.Runnable
                public void run() {
                    for (PLVLinkMicEventHandler pLVLinkMicEventHandler : ((com.plv.linkmic.processor.d) a.this).f10818a.keySet()) {
                        PLVLinkMicEventHandler.PLVAudioVolumeInfo pLVAudioVolumeInfo2 = pLVAudioVolumeInfo;
                        if (pLVAudioVolumeInfo2 != null) {
                            pLVLinkMicEventHandler.onLocalAudioVolumeIndication(pLVAudioVolumeInfo2);
                        }
                        pLVLinkMicEventHandler.onRemoteAudioVolumeIndication((PLVLinkMicEventHandler.PLVAudioVolumeInfo[]) arrayList2.toArray(new PLVLinkMicEventHandler.PLVAudioVolumeInfo[0]));
                    }
                }
            });
        }
    };

    public a(String str) {
        this.uid = str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int a(int i2) {
        switch (i2) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            case 6:
                return 6;
            default:
                return i2;
        }
    }

    @Override // com.plv.linkmic.processor.a
    public Object b() {
        return this.f10809z;
    }
}
