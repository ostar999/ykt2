package com.plv.linkmic.processor.a;

import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.utils.PLVFormatUtils;
import com.plv.linkmic.PLVLinkMicEventHandler;
import com.plv.linkmic.processor.d;
import com.plv.linkmic.screenshare.IPLVScreenShareListener;
import com.plv.rtc.IPLVARtcEngineEventHandler;
import com.plv.rtc.PLVARTCAudioVolumeInfo;
import java.util.Iterator;
import net.lingala.zip4j.util.InternalZipConstants;

/* loaded from: classes4.dex */
public class a extends d {
    private static final String TAG = "PLVLinkMicAgoraEventDispatcher";

    /* renamed from: d, reason: collision with root package name */
    private static final int f10771d = 255;

    /* renamed from: e, reason: collision with root package name */
    private int f10772e;

    /* renamed from: f, reason: collision with root package name */
    private IPLVARtcEngineEventHandler f10773f = new IPLVARtcEngineEventHandler() { // from class: com.plv.linkmic.processor.a.a.1
        @Override // com.plv.rtc.IPLVARtcEngineEventHandler
        public void onAudioQuality(int i2, int i3, short s2, short s3) {
            super.onAudioQuality(i2, i3, s2, s3);
        }

        @Override // com.plv.rtc.IPLVARtcEngineEventHandler
        public void onAudioVolumeIndication(PLVARTCAudioVolumeInfo[] pLVARTCAudioVolumeInfoArr, int i2) {
            super.onAudioVolumeIndication(pLVARTCAudioVolumeInfoArr, i2);
            if (((d) a.this).f10818a.isEmpty()) {
                return;
            }
            final PLVLinkMicEventHandler.PLVAudioVolumeInfo[] pLVAudioVolumeInfoArr = new PLVLinkMicEventHandler.PLVAudioVolumeInfo[pLVARTCAudioVolumeInfoArr.length];
            for (int i3 = 0; i3 < pLVARTCAudioVolumeInfoArr.length; i3++) {
                PLVARTCAudioVolumeInfo pLVARTCAudioVolumeInfo = pLVARTCAudioVolumeInfoArr[i3];
                pLVAudioVolumeInfoArr[i3] = new PLVLinkMicEventHandler.PLVAudioVolumeInfo(a.this.b(pLVARTCAudioVolumeInfo.uid), (pLVARTCAudioVolumeInfo.volume * 100) / 255);
            }
            if (pLVARTCAudioVolumeInfoArr.length == 1 && pLVARTCAudioVolumeInfoArr[0].uid == 0) {
                ((d) a.this).f10819b.post(new Runnable() { // from class: com.plv.linkmic.processor.a.a.1.12
                    @Override // java.lang.Runnable
                    public void run() {
                        Iterator it = ((d) a.this).f10818a.keySet().iterator();
                        while (it.hasNext()) {
                            ((PLVLinkMicEventHandler) it.next()).onLocalAudioVolumeIndication(pLVAudioVolumeInfoArr[0]);
                        }
                    }
                });
            } else {
                ((d) a.this).f10819b.post(new Runnable() { // from class: com.plv.linkmic.processor.a.a.1.13
                    @Override // java.lang.Runnable
                    public void run() {
                        Iterator it = ((d) a.this).f10818a.keySet().iterator();
                        while (it.hasNext()) {
                            ((PLVLinkMicEventHandler) it.next()).onRemoteAudioVolumeIndication(pLVAudioVolumeInfoArr);
                        }
                    }
                });
            }
        }

        @Override // com.plv.rtc.IPLVARtcEngineEventHandler
        public void onError(int i2) {
            super.onError(i2);
            PLVCommonLog.e(a.TAG, "onError " + i2);
        }

        @Override // com.plv.rtc.IPLVARtcEngineEventHandler
        public void onFirstLocalVideoFrame(int i2, int i3, int i4) {
            PLVCommonLog.d(a.TAG, "onFirstLocalVideoFrame " + i2 + " " + i3 + " " + i4);
        }

        @Override // com.plv.rtc.IPLVARtcEngineEventHandler
        public void onFirstRemoteVideoDecoded(final int i2, final int i3, final int i4, int i5) {
            PLVCommonLog.d(a.TAG, "onFirstRemoteVideoDecoded " + (i2 & InternalZipConstants.ZIP_64_LIMIT) + " " + i3 + " " + i4 + " " + i5);
            if (((d) a.this).f10818a.isEmpty()) {
                return;
            }
            ((d) a.this).f10819b.post(new Runnable() { // from class: com.plv.linkmic.processor.a.a.1.1
                @Override // java.lang.Runnable
                public void run() {
                    Iterator it = ((d) a.this).f10818a.keySet().iterator();
                    while (it.hasNext()) {
                        ((PLVLinkMicEventHandler) it.next()).onFirstRemoteVideoDecoded(a.this.b(i2), i3, i4);
                    }
                }
            });
        }

        @Override // com.plv.rtc.IPLVARtcEngineEventHandler
        public void onJoinChannelSuccess(String str, final int i2, int i3) {
            PLVCommonLog.d(a.TAG, "onJoinChannelSuccess " + str + " " + i2 + " " + (i2 & InternalZipConstants.ZIP_64_LIMIT) + " " + i3);
            if (((d) a.this).f10818a.isEmpty()) {
                return;
            }
            ((d) a.this).f10819b.post(new Runnable() { // from class: com.plv.linkmic.processor.a.a.1.3
                @Override // java.lang.Runnable
                public void run() {
                    Iterator it = ((d) a.this).f10818a.keySet().iterator();
                    while (it.hasNext()) {
                        ((PLVLinkMicEventHandler) it.next()).onJoinChannelSuccess(i2 + "");
                    }
                }
            });
        }

        @Override // com.plv.rtc.IPLVARtcEngineEventHandler
        public void onLastmileQuality(int i2) {
            PLVCommonLog.d(a.TAG, "onLastmileQuality " + i2);
        }

        @Override // com.plv.rtc.IPLVARtcEngineEventHandler
        public void onLeaveChannel() {
            if (((d) a.this).f10818a.isEmpty()) {
                return;
            }
            ((d) a.this).f10819b.post(new Runnable() { // from class: com.plv.linkmic.processor.a.a.1.14
                @Override // java.lang.Runnable
                public void run() {
                    Iterator it = ((d) a.this).f10818a.keySet().iterator();
                    while (it.hasNext()) {
                        ((PLVLinkMicEventHandler) it.next()).onLeaveChannel();
                    }
                }
            });
        }

        @Override // com.plv.rtc.IPLVARtcEngineEventHandler
        public void onNetworkQuality(int i2, int i3, int i4) {
            if (((d) a.this).f10818a.isEmpty()) {
                return;
            }
            if (i2 == 0 || i2 == a.this.f10772e) {
                a aVar = a.this;
                aVar.a(aVar.a(i3), a.this.a(i4));
            }
        }

        @Override // com.plv.rtc.IPLVARtcEngineEventHandler
        public void onRejoinChannelSuccess(final String str, final int i2, int i3) {
            PLVCommonLog.d(a.TAG, "onRejoinChannelSuccess " + str + " " + i2 + " " + i3);
            if (((d) a.this).f10818a.isEmpty()) {
                return;
            }
            ((d) a.this).f10819b.post(new Runnable() { // from class: com.plv.linkmic.processor.a.a.1.4
                @Override // java.lang.Runnable
                public void run() {
                    Iterator it = ((d) a.this).f10818a.keySet().iterator();
                    while (it.hasNext()) {
                        ((PLVLinkMicEventHandler) it.next()).onRejoinChannelSuccess(str, i2 + "");
                    }
                }
            });
        }

        @Override // com.plv.rtc.IPLVARtcEngineEventHandler
        public void onRequestToken() {
            super.onRequestToken();
            if (((d) a.this).f10818a.isEmpty()) {
                return;
            }
            ((d) a.this).f10819b.post(new Runnable() { // from class: com.plv.linkmic.processor.a.a.1.15
                @Override // java.lang.Runnable
                public void run() {
                    Iterator it = ((d) a.this).f10818a.keySet().iterator();
                    while (it.hasNext()) {
                        ((PLVLinkMicEventHandler) it.next()).onTokenExpired();
                    }
                }
            });
        }

        @Override // com.plv.rtc.IPLVARtcEngineEventHandler
        public void onScreenShare(final boolean z2, int i2) {
            PLVCommonLog.d(a.TAG, "onScreenShare , isShare=" + z2 + " extra=" + i2);
            if (((d) a.this).f10818a.isEmpty()) {
                return;
            }
            ((d) a.this).f10819b.post(new Runnable() { // from class: com.plv.linkmic.processor.a.a.1.7
                @Override // java.lang.Runnable
                public void run() {
                    Iterator it = ((d) a.this).f10818a.keySet().iterator();
                    while (it.hasNext()) {
                        ((PLVLinkMicEventHandler) it.next()).onScreenShare(z2, IPLVScreenShareListener.PLV_SCREEN_SHARE_OK);
                    }
                }
            });
        }

        @Override // com.plv.rtc.IPLVARtcEngineEventHandler
        public void onStreamPublished(final String str, final int i2) {
            PLVCommonLog.d(a.TAG, "onStreamPublished , url=" + str + "\nerror=" + i2);
            if (((d) a.this).f10818a.isEmpty()) {
                return;
            }
            ((d) a.this).f10819b.post(new Runnable() { // from class: com.plv.linkmic.processor.a.a.1.5
                @Override // java.lang.Runnable
                public void run() {
                    Iterator it = ((d) a.this).f10818a.keySet().iterator();
                    while (it.hasNext()) {
                        ((PLVLinkMicEventHandler) it.next()).onStreamPublished(str, i2);
                    }
                }
            });
        }

        @Override // com.plv.rtc.IPLVARtcEngineEventHandler
        public void onStreamUnpublished(final String str) {
            PLVCommonLog.d(a.TAG, "onStreamUnpublished , url=" + str);
            if (((d) a.this).f10818a.isEmpty()) {
                return;
            }
            ((d) a.this).f10819b.post(new Runnable() { // from class: com.plv.linkmic.processor.a.a.1.6
                @Override // java.lang.Runnable
                public void run() {
                    Iterator it = ((d) a.this).f10818a.keySet().iterator();
                    while (it.hasNext()) {
                        ((PLVLinkMicEventHandler) it.next()).onStreamUnpublished(str);
                    }
                }
            });
        }

        @Override // com.plv.rtc.IPLVARtcEngineEventHandler
        public void onTokenPrivilegeWillExpire(String str) {
            super.onTokenPrivilegeWillExpire(str);
            PLVCommonLog.d(a.TAG, "request token :" + str);
            if (((d) a.this).f10818a.isEmpty()) {
                return;
            }
            ((d) a.this).f10819b.post(new Runnable() { // from class: com.plv.linkmic.processor.a.a.1.2
                @Override // java.lang.Runnable
                public void run() {
                    Iterator it = ((d) a.this).f10818a.keySet().iterator();
                    while (it.hasNext()) {
                        ((PLVLinkMicEventHandler) it.next()).onTokenExpired();
                    }
                }
            });
        }

        @Override // com.plv.rtc.IPLVARtcEngineEventHandler
        public void onUserJoined(final int i2, int i3) {
            PLVCommonLog.d(a.TAG, "onUserJoined " + (i2 & InternalZipConstants.ZIP_64_LIMIT) + " " + i3);
            if (((d) a.this).f10818a.isEmpty()) {
                return;
            }
            ((d) a.this).f10819b.post(new Runnable() { // from class: com.plv.linkmic.processor.a.a.1.8
                @Override // java.lang.Runnable
                public void run() {
                    Iterator it = ((d) a.this).f10818a.keySet().iterator();
                    while (it.hasNext()) {
                        ((PLVLinkMicEventHandler) it.next()).onUserJoined(a.this.b(i2));
                    }
                }
            });
        }

        @Override // com.plv.rtc.IPLVARtcEngineEventHandler
        public void onUserMuteAudio(final int i2, final boolean z2) {
            super.onUserMuteAudio(i2, z2);
            PLVCommonLog.d(a.TAG, "onUserMuteAudio:" + z2);
            if (((d) a.this).f10818a.isEmpty()) {
                return;
            }
            ((d) a.this).f10819b.post(new Runnable() { // from class: com.plv.linkmic.processor.a.a.1.11
                @Override // java.lang.Runnable
                public void run() {
                    Iterator it = ((d) a.this).f10818a.keySet().iterator();
                    while (it.hasNext()) {
                        ((PLVLinkMicEventHandler) it.next()).onUserMuteAudio(a.this.b(i2), z2);
                    }
                }
            });
        }

        @Override // com.plv.rtc.IPLVARtcEngineEventHandler
        public void onUserMuteVideo(final int i2, final boolean z2) {
            PLVCommonLog.d(a.TAG, "onUserMuteVideo:" + z2);
            if (((d) a.this).f10818a.isEmpty()) {
                return;
            }
            ((d) a.this).f10819b.post(new Runnable() { // from class: com.plv.linkmic.processor.a.a.1.10
                @Override // java.lang.Runnable
                public void run() {
                    Iterator it = ((d) a.this).f10818a.keySet().iterator();
                    while (it.hasNext()) {
                        ((PLVLinkMicEventHandler) it.next()).onUserMuteVideo(a.this.b(i2), z2);
                    }
                }
            });
        }

        @Override // com.plv.rtc.IPLVARtcEngineEventHandler
        public void onUserOffline(final int i2, int i3) {
            if (((d) a.this).f10818a.isEmpty()) {
                return;
            }
            ((d) a.this).f10819b.post(new Runnable() { // from class: com.plv.linkmic.processor.a.a.1.9
                @Override // java.lang.Runnable
                public void run() {
                    Iterator it = ((d) a.this).f10818a.keySet().iterator();
                    while (it.hasNext()) {
                        ((PLVLinkMicEventHandler) it.next()).onUserOffline(a.this.b(i2));
                    }
                }
            });
        }

        @Override // com.plv.rtc.IPLVARtcEngineEventHandler
        public void onWarning(int i2) {
            PLVCommonLog.d(a.TAG, "onWarning " + i2);
        }
    };

    public a(String str) {
        this.f10772e = PLVFormatUtils.parseInt(str);
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
        return this.f10773f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String b(int i2) {
        if (i2 == 0) {
            i2 = this.f10772e;
        }
        return String.valueOf(i2 & InternalZipConstants.ZIP_64_LIMIT);
    }
}
