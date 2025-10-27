package com.plv.rtc.b;

import android.content.Context;
import android.content.Intent;
import android.view.SurfaceView;
import androidx.annotation.FloatRange;
import androidx.annotation.Nullable;
import com.plv.rtc.IPLVARtcEngineEventHandler;
import com.plv.rtc.PLVARTCAudioVolumeInfo;
import com.plv.rtc.PLVARTCEngine;
import com.plv.rtc.a.c;
import com.plv.rtc.model.PLVARTCEncoderConfiguration;
import com.plv.rtc.transcode.IPLVARTCTranscoding;
import io.agora.rtc.IAudioFrameObserver;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.mediaio.AgoraDefaultSource;
import io.agora.rtc.mediaio.AgoraSurfaceView;
import io.agora.rtc.mediaio.MediaIO;
import io.agora.rtc.video.VideoCanvas;
import io.agora.rtc.video.VideoEncoderConfiguration;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/* loaded from: classes5.dex */
public class a extends PLVARTCEngine {

    /* renamed from: e, reason: collision with root package name */
    private static final boolean f10879e = true;

    /* renamed from: f, reason: collision with root package name */
    private static final double f10880f = Math.log10(32767.0d) * 20.0d;

    /* renamed from: a, reason: collision with root package name */
    private RtcEngine f10881a;

    /* renamed from: b, reason: collision with root package name */
    private IPLVARtcEngineEventHandler f10882b;

    /* renamed from: c, reason: collision with root package name */
    private a.a.b.c.a f10883c;

    /* renamed from: d, reason: collision with root package name */
    @Nullable
    private com.plv.rtc.a.c f10884d;

    /* renamed from: com.plv.rtc.b.a$a, reason: collision with other inner class name */
    public class C0228a implements c.f {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ AgoraSurfaceView f10885a;

        public C0228a(AgoraSurfaceView agoraSurfaceView) {
            this.f10885a = agoraSurfaceView;
        }

        @Override // com.plv.rtc.a.c.f
        public void a(MediaIO.PixelFormat pixelFormat) {
            this.f10885a.setPixelFormat(pixelFormat);
            a.this.f10881a.setLocalVideoRenderer(this.f10885a);
        }
    }

    public static /* synthetic */ class c {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f10891a;

        /* renamed from: b, reason: collision with root package name */
        static final /* synthetic */ int[] f10892b;

        static {
            int[] iArr = new int[PLVARTCEncoderConfiguration.DEGRADATION_PREFERENCE.values().length];
            f10892b = iArr;
            try {
                iArr[PLVARTCEncoderConfiguration.DEGRADATION_PREFERENCE.MAINTAIN_QUALITY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f10892b[PLVARTCEncoderConfiguration.DEGRADATION_PREFERENCE.MAINTAIN_BALANCED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f10892b[PLVARTCEncoderConfiguration.DEGRADATION_PREFERENCE.MAINTAIN_FRAMERATE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            int[] iArr2 = new int[PLVARTCEncoderConfiguration.ORIENTATION_MODE.values().length];
            f10891a = iArr2;
            try {
                iArr2[PLVARTCEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_ADAPTIVE.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f10891a[PLVARTCEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_FIXED_PORTRAIT.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f10891a[PLVARTCEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_FIXED_LANDSCAPE.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public a(Context context, String str, IPLVARtcEngineEventHandler iPLVARtcEngineEventHandler) {
        this.f10881a = RtcEngine.create(context, str, new com.plv.rtc.b.c(iPLVARtcEngineEventHandler));
        this.f10882b = iPLVARtcEngineEventHandler;
        this.f10883c = new a.a.b.c.a(context, this.f10881a);
        this.f10884d = new com.plv.rtc.a.c(context);
    }

    private static double c(byte[] bArr) {
        double d3 = 0.0d;
        for (int i2 = 1; i2 < bArr.length; i2 += 2) {
            short s2 = (short) ((bArr[i2] << 8) | bArr[i2 - 1]);
            d3 += s2 * s2;
        }
        return Math.max(0.0d, Math.log10(d3 / (bArr.length / 2.0d)) * 10.0d);
    }

    @Override // com.plv.rtc.PLVARTCEngine
    public int addPublishStreamUrl(String str, boolean z2) {
        return this.f10881a.addPublishStreamUrl(str, z2);
    }

    @Override // com.plv.rtc.PLVARTCEngine
    public int adjustRecordingSignalVolume(int i2) {
        return this.f10881a.adjustRecordingSignalVolume(i2);
    }

    @Override // com.plv.rtc.PLVARTCEngine
    public SurfaceView createRenderView(Context context) {
        return this.f10884d != null ? new AgoraSurfaceView(context) : RtcEngine.CreateRendererView(context);
    }

    @Override // com.plv.rtc.PLVARTCEngine
    public void destroy() {
        this.f10882b = null;
        a.a.b.c.a aVar = this.f10883c;
        if (aVar != null) {
            aVar.b();
        }
        com.plv.rtc.a.c cVar = this.f10884d;
        if (cVar != null) {
            cVar.a();
            this.f10884d.h();
        }
        RtcEngine.destroy();
    }

    @Override // com.plv.rtc.PLVARTCEngine
    public int enableAudioVolumeIndication(int i2, int i3) {
        return this.f10881a.enableAudioVolumeIndication(i2, i3, false);
    }

    @Override // com.plv.rtc.PLVARTCEngine
    public int enableDualStreamMode(boolean z2) {
        return this.f10881a.enableDualStreamMode(z2);
    }

    @Override // com.plv.rtc.PLVARTCEngine
    public int enableLocalAudio(boolean z2) {
        return this.f10881a.enableLocalAudio(z2);
    }

    @Override // com.plv.rtc.PLVARTCEngine
    public int enableLocalVideo(boolean z2) {
        return this.f10881a.enableLocalVideo(z2);
    }

    @Override // com.plv.rtc.PLVARTCEngine
    public int enableVideo() {
        int iEnableVideo = this.f10881a.enableVideo();
        com.plv.rtc.a.c cVar = this.f10884d;
        if (cVar != null) {
            this.f10881a.setVideoSource(cVar);
        }
        return iEnableVideo;
    }

    @Override // com.plv.rtc.PLVARTCEngine
    public int enableWebSdkInteroperability(boolean z2) {
        return this.f10881a.enableWebSdkInteroperability(z2);
    }

    @Override // com.plv.rtc.PLVARTCEngine
    public boolean isSpeakerphoneEnabled() {
        return this.f10881a.isSpeakerphoneEnabled();
    }

    @Override // com.plv.rtc.PLVARTCEngine
    public int joinChannel(String str, String str2, String str3, int i2) {
        return this.f10881a.joinChannel(str, str2, str3, i2);
    }

    @Override // com.plv.rtc.PLVARTCEngine
    public int leaveChannel() {
        return this.f10881a.leaveChannel();
    }

    @Override // com.plv.rtc.PLVARTCEngine
    public int muteLocalAudioStream(boolean z2) {
        a(z2);
        return this.f10881a.muteLocalAudioStream(z2);
    }

    @Override // com.plv.rtc.PLVARTCEngine
    public int muteLocalVideoStream(boolean z2) {
        return this.f10881a.muteLocalVideoStream(z2);
    }

    @Override // com.plv.rtc.PLVARTCEngine
    public int recoverDefaultSource() {
        this.f10881a.setVideoSource(new AgoraDefaultSource());
        return 0;
    }

    @Override // com.plv.rtc.PLVARTCEngine
    public int removePublishStreamUrl(String str) {
        return this.f10881a.removePublishStreamUrl(str);
    }

    @Override // com.plv.rtc.PLVARTCEngine
    public int renewToken(String str) {
        return this.f10881a.renewToken(str);
    }

    @Override // com.plv.rtc.PLVARTCEngine
    public int setAudioProfile(int i2, int i3) {
        return this.f10881a.setAudioProfile(i2, i3);
    }

    @Override // com.plv.rtc.PLVARTCEngine
    public int setCameraTorchOn(boolean z2) {
        return this.f10881a.setCameraTorchOn(z2);
    }

    @Override // com.plv.rtc.PLVARTCEngine
    public int setCameraZoomFactor(@FloatRange(from = 1.0d, to = 10.0d) float f2) {
        float fMax = Math.max(1.0f, Math.min(f2, 10.0f));
        com.plv.rtc.a.c cVar = this.f10884d;
        if (cVar != null) {
            cVar.a(fMax);
        }
        return this.f10881a.setCameraZoomFactor((fMax / 10.0f) * this.f10881a.getCameraMaxZoomFactor());
    }

    @Override // com.plv.rtc.PLVARTCEngine
    public int setChannelProfile(int i2) {
        return this.f10881a.setChannelProfile(i2);
    }

    @Override // com.plv.rtc.PLVARTCEngine
    public int setClientRole(int i2) {
        return this.f10881a.setClientRole(i2);
    }

    @Override // com.plv.rtc.PLVARTCEngine
    public int setLiveTranscoding(IPLVARTCTranscoding iPLVARTCTranscoding) {
        if (!(iPLVARTCTranscoding instanceof com.plv.rtc.b.b)) {
            return -2;
        }
        return this.f10881a.setLiveTranscoding(((com.plv.rtc.b.b) iPLVARTCTranscoding).a());
    }

    @Override // com.plv.rtc.PLVARTCEngine
    public int setLocalVideoMirrorMode(int i2) {
        com.plv.rtc.a.c cVar = this.f10884d;
        if (cVar != null) {
            cVar.a(i2 == 1);
        }
        return this.f10881a.setLocalVideoMirrorMode(i2);
    }

    @Override // com.plv.rtc.PLVARTCEngine
    public int setLogFile(String str) {
        return this.f10881a.setLogFile(str);
    }

    @Override // com.plv.rtc.PLVARTCEngine
    public int setParameters(String str) {
        return this.f10881a.setParameters(str);
    }

    @Override // com.plv.rtc.PLVARTCEngine
    public int setVideoEncoderConfiguration(PLVARTCEncoderConfiguration pLVARTCEncoderConfiguration) {
        VideoEncoderConfiguration videoEncoderConfiguration = new VideoEncoderConfiguration();
        PLVARTCEncoderConfiguration.VideoDimensions videoDimensions = pLVARTCEncoderConfiguration.dimensions;
        videoEncoderConfiguration.dimensions = new VideoEncoderConfiguration.VideoDimensions(videoDimensions.width, videoDimensions.height);
        videoEncoderConfiguration.frameRate = pLVARTCEncoderConfiguration.frameRate;
        videoEncoderConfiguration.minFrameRate = pLVARTCEncoderConfiguration.minFrameRate;
        videoEncoderConfiguration.bitrate = pLVARTCEncoderConfiguration.bitrate;
        videoEncoderConfiguration.minBitrate = pLVARTCEncoderConfiguration.minBitrate;
        int i2 = c.f10891a[pLVARTCEncoderConfiguration.orientationMode.ordinal()];
        if (i2 == 1) {
            videoEncoderConfiguration.orientationMode = VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_ADAPTIVE;
        } else if (i2 == 2) {
            videoEncoderConfiguration.orientationMode = VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_FIXED_PORTRAIT;
        } else if (i2 == 3) {
            videoEncoderConfiguration.orientationMode = VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_FIXED_LANDSCAPE;
        }
        int i3 = c.f10892b[pLVARTCEncoderConfiguration.degradationPrefer.ordinal()];
        if (i3 == 1) {
            videoEncoderConfiguration.degradationPrefer = VideoEncoderConfiguration.DEGRADATION_PREFERENCE.MAINTAIN_QUALITY;
        } else if (i3 == 2) {
            videoEncoderConfiguration.degradationPrefer = VideoEncoderConfiguration.DEGRADATION_PREFERENCE.MAINTAIN_BALANCED;
        } else if (i3 == 3) {
            videoEncoderConfiguration.degradationPrefer = VideoEncoderConfiguration.DEGRADATION_PREFERENCE.MAINTAIN_FRAMERATE;
        }
        videoEncoderConfiguration.mirrorMode = pLVARTCEncoderConfiguration.mirrorMode;
        return this.f10881a.setVideoEncoderConfiguration(videoEncoderConfiguration);
    }

    @Override // com.plv.rtc.PLVARTCEngine
    public int setupLocalVideo(SurfaceView surfaceView, int i2, int i3) {
        com.plv.rtc.a.c cVar;
        if (!(surfaceView instanceof AgoraSurfaceView) || (cVar = this.f10884d) == null) {
            return this.f10881a.setupLocalVideo(new VideoCanvas(surfaceView, i2, i3));
        }
        AgoraSurfaceView agoraSurfaceView = (AgoraSurfaceView) surfaceView;
        agoraSurfaceView.init(cVar.n());
        agoraSurfaceView.setBufferType(MediaIO.BufferType.TEXTURE);
        this.f10884d.a(new C0228a(agoraSurfaceView));
        agoraSurfaceView.setPixelFormat(this.f10884d.o());
        return this.f10881a.setLocalVideoRenderer(agoraSurfaceView);
    }

    @Override // com.plv.rtc.PLVARTCEngine
    public int setupRemoteVideo(SurfaceView surfaceView, int i2, int i3) {
        if (!(surfaceView instanceof AgoraSurfaceView)) {
            return this.f10881a.setupRemoteVideo(new VideoCanvas(surfaceView, i2, i3));
        }
        AgoraSurfaceView agoraSurfaceView = (AgoraSurfaceView) surfaceView;
        agoraSurfaceView.init(null);
        agoraSurfaceView.setBufferType(MediaIO.BufferType.BYTE_BUFFER);
        agoraSurfaceView.setPixelFormat(MediaIO.PixelFormat.I420);
        return this.f10881a.setRemoteVideoRenderer(i3, agoraSurfaceView);
    }

    @Override // com.plv.rtc.PLVARTCEngine
    public int startPreview() {
        return this.f10881a.startPreview();
    }

    @Override // com.plv.rtc.PLVARTCEngine
    public int startShareScreen(PLVARTCEncoderConfiguration pLVARTCEncoderConfiguration, Intent intent) {
        if (this.f10883c != null) {
            setVideoEncoderConfiguration(pLVARTCEncoderConfiguration);
            this.f10883c.a(intent, pLVARTCEncoderConfiguration);
            this.f10883c.a();
            this.f10882b.onScreenShare(true, 0);
        }
        return 0;
    }

    @Override // com.plv.rtc.PLVARTCEngine
    public int stopPreview() {
        return this.f10881a.stopPreview();
    }

    @Override // com.plv.rtc.PLVARTCEngine
    public int stopShareScreen() {
        a.a.b.c.a aVar = this.f10883c;
        if (aVar != null) {
            aVar.b();
            this.f10882b.onScreenShare(false, 0);
        }
        return 0;
    }

    @Override // com.plv.rtc.PLVARTCEngine
    public void switchBeauty(boolean z2) {
        com.plv.rtc.a.c cVar = this.f10884d;
        if (cVar != null) {
            cVar.b(z2);
        }
    }

    @Override // com.plv.rtc.PLVARTCEngine
    public int switchCamera() throws IOException {
        com.plv.rtc.a.c cVar = this.f10884d;
        if (cVar != null) {
            cVar.m();
        }
        return this.f10881a.switchCamera();
    }

    public class b implements IAudioFrameObserver {

        /* renamed from: a, reason: collision with root package name */
        private double f10887a;

        /* renamed from: b, reason: collision with root package name */
        private double f10888b;

        /* renamed from: c, reason: collision with root package name */
        private long f10889c;

        public b() {
        }

        private void a(double d3) {
            this.f10887a += d3;
            this.f10888b += 1.0d;
        }

        @Override // io.agora.rtc.IAudioFrameObserver
        public boolean onMixedFrame(byte[] bArr, int i2, int i3, int i4, int i5) {
            return true;
        }

        @Override // io.agora.rtc.IAudioFrameObserver
        public boolean onPlaybackFrame(byte[] bArr, int i2, int i3, int i4, int i5) {
            return true;
        }

        @Override // io.agora.rtc.IAudioFrameObserver
        public boolean onPlaybackFrameBeforeMixing(byte[] bArr, int i2, int i3, int i4, int i5, int i6) {
            return true;
        }

        @Override // io.agora.rtc.IAudioFrameObserver
        public boolean onRecordFrame(byte[] bArr, int i2, int i3, int i4, int i5) {
            a(a.b(bArr));
            a();
            return true;
        }

        private void a() {
            if (System.currentTimeMillis() - this.f10889c < TimeUnit.SECONDS.toMillis(1L)) {
                return;
            }
            this.f10889c = System.currentTimeMillis();
            int i2 = (int) (this.f10887a / this.f10888b);
            this.f10887a = 0.0d;
            this.f10888b = 0.0d;
            if (a.this.f10882b != null) {
                PLVARTCAudioVolumeInfo pLVARTCAudioVolumeInfo = new PLVARTCAudioVolumeInfo();
                pLVARTCAudioVolumeInfo.uid = 0;
                pLVARTCAudioVolumeInfo.volume = i2;
                a.this.f10882b.onAudioVolumeIndication(new PLVARTCAudioVolumeInfo[]{pLVARTCAudioVolumeInfo}, i2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static double b(byte[] bArr) {
        return a(c(bArr));
    }

    private void a(boolean z2) {
        RtcEngine rtcEngine = this.f10881a;
        if (rtcEngine == null || this.f10882b == null) {
            return;
        }
        if (!z2) {
            rtcEngine.registerAudioFrameObserver(null);
        } else {
            rtcEngine.registerAudioFrameObserver(new b());
        }
    }

    private static double a(double d3) {
        return (d3 / f10880f) * 255.0d;
    }
}
