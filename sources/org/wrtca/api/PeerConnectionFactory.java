package org.wrtca.api;

import android.content.Context;
import android.util.Log;
import java.util.List;
import org.wrtca.api.EglBase;
import org.wrtca.api.PeerConnection;
import org.wrtca.jni.CalledByNative;
import org.wrtca.jni.JNINamespace;
import org.wrtca.log.Logging;
import org.wrtca.util.ContextUtils;
import org.wrtca.util.NativeLibrary;
import org.wrtca.video.AndroidVideoTrackSourceObserver;

@JNINamespace("webrtc::jni")
/* loaded from: classes9.dex */
public class PeerConnectionFactory {
    private static final String TAG = "PeerConnectionFactory";
    public static final String TRIAL_ENABLED = "Enabled";
    private static final String VIDEO_CAPTURER_THREAD_NAME = "VideoCapturerThread";

    @Deprecated
    public static final String VIDEO_FRAME_EMIT_TRIAL = "VideoFrameEmit";
    private static Context applicationContext = null;
    private static volatile boolean internalTracerInitialized = false;
    private static Thread networkThread;
    private static Thread signalingThread;
    private static Thread workerThread;
    private EglBase localEglbase;
    private long nativeFactory;
    private EglBase remoteEglbase;

    public static class Builder {
        private AudioProcessingFactory audioProcessingFactory;
        private VideoDecoderFactory decoderFactory;
        private VideoEncoderFactory encoderFactory;
        private FecControllerFactoryFactoryInterface fecControllerFactoryFactory;
        private Options options;

        public PeerConnectionFactory createPeerConnectionFactory() {
            return new PeerConnectionFactory(this.options, this.encoderFactory, this.decoderFactory, this.audioProcessingFactory, this.fecControllerFactoryFactory);
        }

        public Builder setAudioProcessingFactory(AudioProcessingFactory audioProcessingFactory) {
            if (audioProcessingFactory == null) {
                throw new NullPointerException("PeerConnectionFactory builder does not accept a null AudioProcessingFactory.");
            }
            this.audioProcessingFactory = audioProcessingFactory;
            return this;
        }

        public Builder setFecControllerFactoryFactoryInterface(FecControllerFactoryFactoryInterface fecControllerFactoryFactoryInterface) {
            this.fecControllerFactoryFactory = fecControllerFactoryFactoryInterface;
            return this;
        }

        public Builder setOptions(Options options) {
            this.options = options;
            return this;
        }

        public Builder setVideoDecoderFactory(VideoDecoderFactory videoDecoderFactory) {
            this.decoderFactory = videoDecoderFactory;
            return this;
        }

        public Builder setVideoEncoderFactory(VideoEncoderFactory videoEncoderFactory) {
            this.encoderFactory = videoEncoderFactory;
            return this;
        }

        private Builder() {
        }
    }

    public static class InitializationOptions {
        public final Context applicationContext;
        public final boolean enableInternalTracer;
        public final boolean enableVideoHwAcceleration;
        public final String fieldTrials;
        public final NativeLibraryLoader nativeLibraryLoader;

        public static class Builder {
            private final Context applicationContext;
            private String fieldTrials = "";
            private boolean enableInternalTracer = false;
            private boolean enableVideoHwAcceleration = true;
            private NativeLibraryLoader nativeLibraryLoader = new NativeLibrary.DefaultLoader();

            public Builder(Context context) {
                this.applicationContext = context;
            }

            public InitializationOptions createInitializationOptions() {
                return new InitializationOptions(this.applicationContext, this.fieldTrials, this.enableInternalTracer, this.enableVideoHwAcceleration, this.nativeLibraryLoader);
            }

            public Builder setEnableInternalTracer(boolean z2) {
                this.enableInternalTracer = z2;
                return this;
            }

            public Builder setEnableVideoHwAcceleration(boolean z2) {
                this.enableVideoHwAcceleration = z2;
                return this;
            }

            public Builder setFieldTrials(String str) {
                this.fieldTrials = str;
                return this;
            }

            public Builder setNativeLibraryLoader(NativeLibraryLoader nativeLibraryLoader) {
                this.nativeLibraryLoader = nativeLibraryLoader;
                return this;
            }
        }

        public static Builder builder(Context context) {
            return new Builder(context);
        }

        private InitializationOptions(Context context, String str, boolean z2, boolean z3, NativeLibraryLoader nativeLibraryLoader) {
            this.applicationContext = context;
            this.fieldTrials = str;
            this.enableInternalTracer = z2;
            this.enableVideoHwAcceleration = z3;
            this.nativeLibraryLoader = nativeLibraryLoader;
        }
    }

    public static class Options {
        public static final int ADAPTER_TYPE_CELLULAR = 4;
        public static final int ADAPTER_TYPE_ETHERNET = 1;
        public static final int ADAPTER_TYPE_LOOPBACK = 16;
        public static final int ADAPTER_TYPE_UNKNOWN = 0;
        public static final int ADAPTER_TYPE_VPN = 8;
        public static final int ADAPTER_TYPE_WIFI = 2;
        public int networkIgnoreMask = 16;
        public boolean disableEncryption = false;
        public boolean disableNetworkMonitor = false;
        public String mixFilePath = "";
        public boolean mixSupport = false;
        public boolean isLoop = true;

        @CalledByNative("Options")
        public boolean getDisableEncryption() {
            return this.disableEncryption;
        }

        @CalledByNative("Options")
        public boolean getDisableNetworkMonitor() {
            return this.disableNetworkMonitor;
        }

        @CalledByNative("Options")
        public boolean getIsLoop() {
            return this.isLoop;
        }

        @CalledByNative("Options")
        public String getMixFilePath() {
            return this.mixFilePath;
        }

        @CalledByNative("Options")
        public boolean getMixSupport() {
            return this.mixSupport;
        }

        @CalledByNative("Options")
        public int getNetworkIgnoreMask() {
            return this.networkIgnoreMask;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    private boolean checkInitializeHasBeenCalled() {
        if (NativeLibrary.isLoaded() && ContextUtils.getApplicationContext() != null) {
            return true;
        }
        Logging.e(TAG, "PeerConnectionFactory.initialize was not called before creating a PeerConnectionFactory.");
        return false;
    }

    public static String fieldTrialsFindFullName(String str) {
        return NativeLibrary.isLoaded() ? nativeFindFieldTrialsFullName(str) : "";
    }

    public static void initialize(InitializationOptions initializationOptions) {
        ContextUtils.initialize(initializationOptions.applicationContext);
        if (NativeLibrary.initialize(initializationOptions.nativeLibraryLoader)) {
            nativeInitializeAndroidGlobals(initializationOptions.applicationContext, initializationOptions.enableVideoHwAcceleration);
            initializeFieldTrials(initializationOptions.fieldTrials);
            if (!initializationOptions.enableInternalTracer || internalTracerInitialized) {
                return;
            }
            initializeInternalTracer();
        }
    }

    @Deprecated
    public static void initializeFieldTrials(String str) {
        nativeInitializeFieldTrials(str);
    }

    private static void initializeInternalTracer() {
        internalTracerInitialized = true;
        nativeInitializeInternalTracer();
    }

    private static native long nativeCreateAudioSource(long j2, MediaConstraints mediaConstraints);

    private static native long nativeCreateAudioTrack(long j2, String str, long j3);

    private static native long nativeCreateLocalMediaStream(long j2, String str);

    private static native long nativeCreatePeerConnection(long j2, PeerConnection.RTCConfiguration rTCConfiguration, MediaConstraints mediaConstraints, long j3);

    private static native long nativeCreatePeerConnectionFactory(Options options, VideoEncoderFactory videoEncoderFactory, VideoDecoderFactory videoDecoderFactory, long j2, long j3);

    private static native long nativeCreateVideoSource(long j2, SurfaceTextureHelper surfaceTextureHelper, boolean z2, int i2, boolean z3);

    private static native long nativeCreateVideoTrack(long j2, String str, long j3);

    private static native String nativeFindFieldTrialsFullName(String str);

    private static native void nativeFreeFactory(long j2);

    private static native int nativeGetAudioMixingCurrentPosition(long j2);

    private static native int nativeGetAudioMixingDuration(long j2);

    private static native long nativeGetNativePeerConnectionFactory(long j2);

    private static native void nativeInitializeAndroidGlobals(Context context, boolean z2);

    private static native void nativeInitializeFieldTrials(String str);

    private static native void nativeInitializeInternalTracer();

    private static native void nativeInvokeThreadsCallbacks(long j2);

    private static native void nativePauseAudioFile(long j2);

    private static native void nativeResumeAudioFile(long j2);

    private static native void nativeSetAudioMixingPosition(long j2, int i2);

    private static native void nativeSetVideoHwAccelerationOptions(long j2, Object obj, Object obj2);

    private static native void nativeShutdownInternalTracer();

    private static native boolean nativeStartAecDump(long j2, int i2, int i3);

    private static native boolean nativeStartInternalTracingCapture(String str);

    private static native void nativeStartPlayAudioFile(long j2, String str, boolean z2, boolean z3);

    private static native void nativeStopAecDump(long j2);

    private static native void nativeStopInternalTracingCapture();

    private static native void nativeStopPlayAudioFile(long j2);

    private static native void nativeUpdateAudioMixingVolume(long j2, int i2);

    @CalledByNative
    private static void onAudioFileFinish() {
        Logging.d(TAG, "onAudioFileFinish");
        j.d.d().q();
    }

    @CalledByNative
    private static void onNetworkThreadReady() {
        networkThread = Thread.currentThread();
        Logging.d(TAG, "onNetworkThreadReady");
    }

    @CalledByNative
    private static void onSignalingThreadReady() {
        signalingThread = Thread.currentThread();
        Logging.d(TAG, "onSignalingThreadReady");
    }

    @CalledByNative
    private static void onWorkerThreadReady() {
        workerThread = Thread.currentThread();
        Logging.d(TAG, "onWorkerThreadReady");
    }

    private static void printStackTrace(Thread thread, String str) {
        if (thread != null) {
            StackTraceElement[] stackTrace = thread.getStackTrace();
            if (stackTrace.length > 0) {
                Logging.d(TAG, str + " stacks trace:");
                for (StackTraceElement stackTraceElement : stackTrace) {
                    Logging.d(TAG, stackTraceElement.toString());
                }
            }
        }
    }

    public static void printStackTraces() {
        printStackTrace(networkThread, "Network thread");
        printStackTrace(workerThread, "Worker thread");
        printStackTrace(signalingThread, "Signaling thread");
    }

    public static void shutdownInternalTracer() {
        internalTracerInitialized = false;
        nativeShutdownInternalTracer();
    }

    public static boolean startInternalTracingCapture(String str) {
        return nativeStartInternalTracingCapture(str);
    }

    public static void stopInternalTracingCapture() {
        nativeStopInternalTracingCapture();
    }

    public AudioSource createAudioSource(MediaConstraints mediaConstraints) {
        return new AudioSource(nativeCreateAudioSource(this.nativeFactory, mediaConstraints));
    }

    public AudioTrack createAudioTrack(String str, AudioSource audioSource) {
        return new AudioTrack(nativeCreateAudioTrack(this.nativeFactory, str, audioSource.nativeSource));
    }

    public MediaStream createLocalMediaStream(String str) {
        return new MediaStream(nativeCreateLocalMediaStream(this.nativeFactory, str));
    }

    @Deprecated
    public PeerConnection createPeerConnection(PeerConnection.RTCConfiguration rTCConfiguration, MediaConstraints mediaConstraints, PeerConnection.Observer observer) {
        long jCreateNativePeerConnectionObserver = PeerConnection.createNativePeerConnectionObserver(observer);
        if (jCreateNativePeerConnectionObserver == 0) {
            return null;
        }
        long jNativeCreatePeerConnection = nativeCreatePeerConnection(this.nativeFactory, rTCConfiguration, mediaConstraints, jCreateNativePeerConnectionObserver);
        if (jNativeCreatePeerConnection == 0) {
            return null;
        }
        return new PeerConnection(jNativeCreatePeerConnection);
    }

    public VideoSource createVideoSource(VideoCapturer videoCapturer, int i2, boolean z2) {
        EglBase eglBase = this.localEglbase;
        EglBase.Context eglBaseContext = eglBase == null ? null : eglBase.getEglBaseContext();
        Log.d(TAG, "createVideoSource: SurfaceTextureHelper.create");
        SurfaceTextureHelper surfaceTextureHelperCreate = SurfaceTextureHelper.create(VIDEO_CAPTURER_THREAD_NAME, eglBaseContext);
        long jNativeCreateVideoSource = nativeCreateVideoSource(this.nativeFactory, surfaceTextureHelperCreate, videoCapturer.isScreencast(), i2, z2);
        videoCapturer.initialize(surfaceTextureHelperCreate, ContextUtils.getApplicationContext(), new AndroidVideoTrackSourceObserver(jNativeCreateVideoSource));
        return new VideoSource(jNativeCreateVideoSource);
    }

    public VideoTrack createVideoTrack(String str, VideoSource videoSource) {
        return new VideoTrack(nativeCreateVideoTrack(this.nativeFactory, str, videoSource.nativeSource));
    }

    public void dispose() {
        nativeFreeFactory(this.nativeFactory);
        networkThread = null;
        workerThread = null;
        signalingThread = null;
        EglBase eglBase = this.localEglbase;
        if (eglBase != null) {
            eglBase.release();
        }
        EglBase eglBase2 = this.remoteEglbase;
        if (eglBase2 != null) {
            eglBase2.release();
        }
    }

    public long getNativeOwnedFactoryAndThreads() {
        return this.nativeFactory;
    }

    public long getNativePeerConnectionFactory() {
        return nativeGetNativePeerConnectionFactory(this.nativeFactory);
    }

    @Deprecated
    public native void nativeSetOptions(long j2, Options options);

    public void pauseAudioFile() {
        nativePauseAudioFile(this.nativeFactory);
    }

    public void resumeAudioFile() {
        nativeResumeAudioFile(this.nativeFactory);
    }

    @Deprecated
    public void setOptions(Options options) {
        nativeSetOptions(this.nativeFactory, options);
    }

    public void setVideoHwAccelerationOptions(EglBase.Context context, EglBase.Context context2) {
        if (this.localEglbase != null) {
            Logging.w(TAG, "Egl context already set.");
            this.localEglbase.release();
        }
        if (this.remoteEglbase != null) {
            Logging.w(TAG, "Egl context already set.");
            this.remoteEglbase.release();
        }
        this.localEglbase = a.b(context);
        c.h.a(TAG, "PeerConnectionFactorylocalEglbase: " + this.localEglbase);
        this.remoteEglbase = a.b(context2);
        c.h.a(TAG, "PeerConnectionFactoryremoteEglbase: " + this.remoteEglbase);
        nativeSetVideoHwAccelerationOptions(this.nativeFactory, this.localEglbase.getEglBaseContext(), this.remoteEglbase.getEglBaseContext());
    }

    public boolean startAecDump(int i2, int i3) {
        return nativeStartAecDump(this.nativeFactory, i2, i3);
    }

    public void startPlayAudioFile(String str, boolean z2, boolean z3) {
        nativeStartPlayAudioFile(this.nativeFactory, str, z2, z3);
    }

    public void stopAecDump() {
        nativeStopAecDump(this.nativeFactory);
    }

    public void stopPlayAudioFile() {
        nativeStopPlayAudioFile(this.nativeFactory);
    }

    public void threadsCallbacks() {
        nativeInvokeThreadsCallbacks(this.nativeFactory);
    }

    @Deprecated
    public PeerConnectionFactory() {
        this(null);
    }

    @Deprecated
    public PeerConnectionFactory(Options options) {
        this(options, null, null);
    }

    @Deprecated
    public PeerConnectionFactory(Options options, VideoEncoderFactory videoEncoderFactory, VideoDecoderFactory videoDecoderFactory) {
        if (checkInitializeHasBeenCalled()) {
            long jNativeCreatePeerConnectionFactory = nativeCreatePeerConnectionFactory(options, videoEncoderFactory, videoDecoderFactory, 0L, 0L);
            this.nativeFactory = jNativeCreatePeerConnectionFactory;
            if (jNativeCreatePeerConnectionFactory == 0) {
                throw new RuntimeException("Failed to initialize PeerConnectionFactory!");
            }
        }
    }

    @Deprecated
    public PeerConnection createPeerConnection(List<PeerConnection.IceServer> list, MediaConstraints mediaConstraints, PeerConnection.Observer observer) {
        return createPeerConnection(new PeerConnection.RTCConfiguration(list), mediaConstraints, observer);
    }

    public PeerConnection createPeerConnection(List<PeerConnection.IceServer> list, PeerConnection.Observer observer) {
        return createPeerConnection(new PeerConnection.RTCConfiguration(list), observer);
    }

    @Deprecated
    public PeerConnectionFactory(Options options, VideoEncoderFactory videoEncoderFactory, VideoDecoderFactory videoDecoderFactory, AudioProcessingFactory audioProcessingFactory) {
        this(options, videoEncoderFactory, videoDecoderFactory, audioProcessingFactory, null);
    }

    private PeerConnectionFactory(Options options, VideoEncoderFactory videoEncoderFactory, VideoDecoderFactory videoDecoderFactory, AudioProcessingFactory audioProcessingFactory, FecControllerFactoryFactoryInterface fecControllerFactoryFactoryInterface) {
        if (checkInitializeHasBeenCalled()) {
            long jNativeCreatePeerConnectionFactory = nativeCreatePeerConnectionFactory(options, videoEncoderFactory, videoDecoderFactory, audioProcessingFactory == null ? 0L : audioProcessingFactory.createNative(), fecControllerFactoryFactoryInterface == null ? 0L : fecControllerFactoryFactoryInterface.createNative());
            this.nativeFactory = jNativeCreatePeerConnectionFactory;
            if (jNativeCreatePeerConnectionFactory == 0) {
                throw new RuntimeException("Failed to initialize PeerConnectionFactory!");
            }
        }
    }

    public PeerConnection createPeerConnection(PeerConnection.RTCConfiguration rTCConfiguration, PeerConnection.Observer observer) {
        return createPeerConnection(rTCConfiguration, (MediaConstraints) null, observer);
    }
}
