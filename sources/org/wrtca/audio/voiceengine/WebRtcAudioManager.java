package org.wrtca.audio.voiceengine;

import android.annotation.TargetApi;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.os.Build;
import c.h;
import d.b;
import java.util.Timer;
import java.util.TimerTask;
import org.wrtca.log.Logging;
import org.wrtca.util.ContextUtils;

/* loaded from: classes9.dex */
public class WebRtcAudioManager {
    private static final int BITS_PER_SAMPLE = 16;
    private static final boolean DEBUG = false;
    private static final int DEFAULT_FRAME_PER_BUFFER = 256;
    private static final String TAG = "WebRtcAudioManager";
    private static boolean blacklistDeviceForOpenSLESUsage = false;
    private static boolean blacklistDeviceForOpenSLESUsageIsOverridden = false;
    private static boolean useStereoInput = false;
    private static boolean useStereoOutput = false;
    private final AudioManager audioManager;
    private boolean hardwareAEC;
    private boolean hardwareAGC;
    private boolean hardwareNS;
    private boolean initialized = false;
    private int inputBufferSize;
    private int inputChannels;
    private boolean lowLatencyInput;
    private boolean lowLatencyOutput;
    private final long nativeAudioManager;
    private int nativeChannels;
    private int nativeSampleRate;
    private int outputBufferSize;
    private int outputChannels;
    private boolean proAudio;
    private int sampleRate;
    private final VolumeLogger volumeLogger;

    public static class VolumeLogger {
        private static final String THREAD_NAME = "WebRtcVolumeLevelLoggerThread";
        private static final int TIMER_PERIOD_IN_SECONDS = 30;
        private final AudioManager audioManager;
        private Timer timer;

        public class LogVolumeTask extends TimerTask {
            private final int maxRingVolume;
            private final int maxVoiceCallVolume;

            public LogVolumeTask(int i2, int i3) {
                this.maxRingVolume = i2;
                this.maxVoiceCallVolume = i3;
            }

            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                int mode = VolumeLogger.this.audioManager.getMode();
                if (mode == 1) {
                    Logging.d(WebRtcAudioManager.TAG, "STREAM_RING stream volume: " + VolumeLogger.this.audioManager.getStreamVolume(2) + " (max=" + this.maxRingVolume + ")");
                    return;
                }
                if (mode == 3) {
                    Logging.d(WebRtcAudioManager.TAG, "VOICE_CALL stream volume: " + VolumeLogger.this.audioManager.getStreamVolume(0) + " (max=" + this.maxVoiceCallVolume + ")");
                }
            }
        }

        public VolumeLogger(AudioManager audioManager) {
            this.audioManager = audioManager;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void stop() {
            Timer timer = this.timer;
            if (timer != null) {
                timer.cancel();
                this.timer = null;
            }
        }

        public void start() {
            Timer timer = new Timer(THREAD_NAME);
            this.timer = timer;
            timer.schedule(new LogVolumeTask(this.audioManager.getStreamMaxVolume(2), this.audioManager.getStreamMaxVolume(0)), 0L, 30000L);
        }
    }

    public WebRtcAudioManager(long j2) {
        h.a(TAG, "WebRtcAudioManager ctor" + WebRtcAudioUtils.getThreadInfo());
        this.nativeAudioManager = j2;
        AudioManager audioManager = (AudioManager) ContextUtils.getApplicationContext().getSystemService("audio");
        this.audioManager = audioManager;
        this.volumeLogger = new VolumeLogger(audioManager);
        storeAudioParameters();
        nativeCacheAudioParameters(this.sampleRate, this.outputChannels, this.inputChannels, this.hardwareAEC, this.hardwareAGC, this.hardwareNS, this.lowLatencyOutput, this.lowLatencyInput, this.proAudio, this.outputBufferSize, this.inputBufferSize, j2);
    }

    private static void assertTrue(boolean z2) {
        if (!z2) {
            throw new AssertionError("Expected condition to be true");
        }
    }

    private void dispose() {
        Logging.d(TAG, "dispose" + WebRtcAudioUtils.getThreadInfo());
        if (this.initialized) {
            this.volumeLogger.stop();
        }
    }

    private int getLowLatencyInputFramesPerBuffer() {
        assertTrue(isLowLatencyInputSupported());
        return getLowLatencyOutputFramesPerBuffer();
    }

    @TargetApi(17)
    private int getLowLatencyOutputFramesPerBuffer() {
        String property;
        assertTrue(isLowLatencyOutputSupported());
        if (WebRtcAudioUtils.runningOnJellyBeanMR1OrHigher() && (property = this.audioManager.getProperty("android.media.property.OUTPUT_FRAMES_PER_BUFFER")) != null) {
            return Integer.parseInt(property);
        }
        return 256;
    }

    private static int getMinInputFrameSize(int i2, int i3) {
        return AudioRecord.getMinBufferSize(i2, i3 == 1 ? 16 : 12, 2) / (i3 * 2);
    }

    private static int getMinOutputFrameSize(int i2, int i3) {
        return AudioTrack.getMinBufferSize(i2, i3 == 1 ? 4 : 12, 2) / (i3 * 2);
    }

    private int getNativeOutputSampleRate() {
        if (WebRtcAudioUtils.runningOnEmulator()) {
            Logging.d(TAG, "Running emulator, overriding sample rate to 8 kHz.");
            return 8000;
        }
        if (WebRtcAudioUtils.isDefaultSampleRateOverridden()) {
            Logging.d(TAG, "Default sample rate is overriden to " + WebRtcAudioUtils.getDefaultSampleRateHz() + " Hz");
            return WebRtcAudioUtils.getDefaultSampleRateHz();
        }
        int sampleRateOnJellyBeanMR10OrHigher = WebRtcAudioUtils.runningOnJellyBeanMR1OrHigher() ? getSampleRateOnJellyBeanMR10OrHigher() : WebRtcAudioUtils.getDefaultSampleRateHz();
        Logging.d(TAG, "Sample rate is set to " + sampleRateOnJellyBeanMR10OrHigher + " Hz");
        return sampleRateOnJellyBeanMR10OrHigher;
    }

    @TargetApi(17)
    private int getSampleRateOnJellyBeanMR10OrHigher() {
        String property = this.audioManager.getProperty("android.media.property.OUTPUT_SAMPLE_RATE");
        return property == null ? WebRtcAudioUtils.getDefaultSampleRateHz() : Integer.parseInt(property);
    }

    public static synchronized boolean getStereoInput() {
        return useStereoInput;
    }

    public static synchronized boolean getStereoOutput() {
        return useStereoOutput;
    }

    private boolean hasEarpiece() {
        return ContextUtils.getApplicationContext().getPackageManager().hasSystemFeature("android.hardware.telephony");
    }

    private boolean init() {
        Logging.d(TAG, "init" + WebRtcAudioUtils.getThreadInfo());
        if (this.initialized) {
            return true;
        }
        Logging.d(TAG, "audio mode is: " + WebRtcAudioUtils.modeToString(this.audioManager.getMode()));
        this.initialized = true;
        this.volumeLogger.start();
        return true;
    }

    private static boolean isAcousticEchoCancelerSupported() {
        return WebRtcAudioEffects.canUseAcousticEchoCanceler();
    }

    private boolean isCommunicationModeEnabled() {
        return this.audioManager.getMode() == 3;
    }

    private boolean isDeviceBlacklistedForOpenSLESUsage() {
        boolean zDeviceIsBlacklistedForOpenSLESUsage = blacklistDeviceForOpenSLESUsageIsOverridden ? blacklistDeviceForOpenSLESUsage : WebRtcAudioUtils.deviceIsBlacklistedForOpenSLESUsage();
        if (zDeviceIsBlacklistedForOpenSLESUsage) {
            Logging.d(TAG, Build.MODEL + " is blacklisted for OpenSL ES usage!");
        }
        return zDeviceIsBlacklistedForOpenSLESUsage;
    }

    private boolean isLowLatencyOutputSupported() {
        return ContextUtils.getApplicationContext().getPackageManager().hasSystemFeature("android.hardware.audio.low_latency");
    }

    private static boolean isNoiseSuppressorSupported() {
        return WebRtcAudioEffects.canUseNoiseSuppressor();
    }

    @TargetApi(23)
    private boolean isProAudioSupported() {
        return WebRtcAudioUtils.runningOnMarshmallowOrHigher() && ContextUtils.getApplicationContext().getPackageManager().hasSystemFeature("android.hardware.audio.pro");
    }

    private native void nativeCacheAudioParameters(int i2, int i3, int i4, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, int i5, int i6, long j2);

    public static synchronized void setBlacklistDeviceForOpenSLESUsage(boolean z2) {
        blacklistDeviceForOpenSLESUsageIsOverridden = true;
        blacklistDeviceForOpenSLESUsage = z2;
    }

    public static synchronized void setStereoInput(boolean z2) {
        Logging.w(TAG, "Overriding default input behavior: setStereoInput(" + z2 + ')');
        useStereoInput = z2;
    }

    public static synchronized void setStereoOutput(boolean z2) {
        Logging.w(TAG, "Overriding default output behavior: setStereoOutput(" + z2 + ')');
        useStereoOutput = z2;
    }

    private void storeAudioParameters() {
        this.outputChannels = getStereoOutput() ? 2 : 1;
        this.inputChannels = getStereoInput() ? 2 : 1;
        this.sampleRate = getNativeOutputSampleRate();
        this.hardwareAEC = false;
        if (b.n() != 0) {
            h.a(TAG, "user set AECMode: " + b.n());
            boolean zIsAcousticEchoCancelerSupported = isAcousticEchoCancelerSupported();
            if (b.n() == 2 && zIsAcousticEchoCancelerSupported) {
                h.a(TAG, " user specified hard aec and support");
                this.hardwareAEC = true;
            }
            if (b.n() == 1) {
                h.a(TAG, " user specified soft aec");
                this.hardwareAEC = false;
            }
        } else {
            this.hardwareAEC = isAcousticEchoCancelerSupported();
            h.a(TAG, "default sdk version > m hardwareAEC: " + this.hardwareAEC);
        }
        this.hardwareNS = false;
        this.lowLatencyOutput = isLowLatencyOutputSupported();
        h.a(TAG, "lowLatencyOutput: " + this.lowLatencyOutput);
        this.lowLatencyInput = isLowLatencyInputSupported();
        h.a(TAG, "lowLatencyInput: " + this.lowLatencyInput);
        this.proAudio = isProAudioSupported();
        this.outputBufferSize = this.lowLatencyOutput ? getLowLatencyOutputFramesPerBuffer() : getMinOutputFrameSize(this.sampleRate, this.outputChannels);
        this.inputBufferSize = this.lowLatencyInput ? getLowLatencyInputFramesPerBuffer() : getMinInputFrameSize(this.sampleRate, this.inputChannels);
    }

    public boolean isLowLatencyInputSupported() {
        return WebRtcAudioUtils.runningOnLollipopOrHigher() && isLowLatencyOutputSupported();
    }
}
