package com.tencent.liteav;

import android.content.Context;
import android.content.SharedPreferences;
import com.tencent.liteav.basic.util.TXCBuild;
import java.util.concurrent.TimeUnit;

/* loaded from: classes6.dex */
public class AudioServerConfig {
    private static final String AUDIO_SERVER_CONFIG = "trtc_audio_server_config";
    private static final int DEFAULT_16K_PACKAGE_STRATEGY = 0;
    private static final int DEFAULT_AUDIO_BITRATE = 0;
    private static final int DEFAULT_AUDIO_CHANNEL = 0;
    private static final int DEFAULT_AUDIO_SAMPLE_RATE = 0;
    private static final int DEFAULT_DEVICE_AUTO_RESTART_MIN_INTERVAL = 5000;
    private static final boolean DEFAULT_ENABLE_AUTO_RESTART_DEVICE = false;
    private static final int DEFAULT_ENABLE_DEVICE_ABNORMAL_DETECTION = 1;
    private static final boolean DEFAULT_ENABLE_OPENSL = false;
    private static final int DEFAULT_ENCODE_MODE = 0;
    private static final int DEFAULT_ENGINE_SOLUTION = 0;
    private static final int DEFAULT_FORCE_ENGINE_SOLUTION = 0;
    private static final int DEFAULT_IS_ENABLE_INBAND_FEC = 0;
    private static final boolean DEFAULT_IS_LOW_LATENCY_SAMPLERATE_SUPPORTED = false;
    private static final long DEFAULT_LOW_LATENCY_SAMPLERATE_BLOCK_TIME = TimeUnit.DAYS.toMillis(7);
    private static final int DEFAULT_MAX_SELECTED_PLAY_STREAMS = 0;
    private static final boolean DEFAULT_SUPPORT_AAUDIO;
    private static final int DEFAULT_SYSTEM_VOLUME_TYPE = 0;
    private static final String KEY_16K_PACKAGE_STRATEGY = "16k_package_strategy";
    private static final String KEY_AUDIO_QUALITY_CONFIG_BITRATE = "audio_quality_config_bitrate";
    private static final String KEY_AUDIO_QUALITY_CONFIG_CHANNEL = "audio_quality_config_channel";
    private static final String KEY_AUDIO_QUALITY_CONFIG_ENCODE_MODE = "audio_quality_config_encode_mode";
    private static final String KEY_AUDIO_QUALITY_CONFIG_SAMPLERATE = "audio_quality_config_samplerate";
    private static final String KEY_AUDIO_QUALITY_CONFIG_SYSTEM_VOLUME_TYPE = "audio_quality_config_system_volume_type";
    private static final String KEY_DEVICE_AUTO_RESTART_MIN_INTERVAL = "device_auto_restart_interval";
    private static final String KEY_ENABLE_AUTO_RESTART_DEVICE = "enable_auto_restart_device";
    private static final String KEY_ENABLE_DEVICE_ABNORMAL_DETECTION = "enable_device_abnormal_detection";
    private static final String KEY_ENABLE_INBAND_FEC = "enable_inband_fec";
    private static final String KEY_ENABLE_OPENSL = "enable_opensl";
    private static final String KEY_ENGINE_SOLUTION = "engine_solution";
    private static final String KEY_FORCE_USE_ENGINE_SOLUTION = "force_use_engine_solution";
    private static final String KEY_IS_LOW_LATENCY_SAMPLERATE_SUPPORTED = "is_low_latency_samplerate_supported";
    private static final String KEY_LOW_LATENCY_SAMPLERATE_BLOCK_TIME = "low_latency_samplerate_block_time";
    private static final String KEY_MAX_SELECTED_PLAY_STREAMS = "max_selected_play_streams";
    private static final String KEY_SUPPORT_AAUDIO = "support_aaudio";
    public boolean enableOpenSL = false;
    public boolean enableAutoRestartDevice = false;
    public int deviceAutoRestartMinInterval = 5000;
    public int audio16KPackageStrategy = 0;
    public int maxSelectedPlayStreams = 0;
    public boolean isLowLatencySampleRateSupported = false;
    public long lowLatencySampleRateBlockTime = DEFAULT_LOW_LATENCY_SAMPLERATE_BLOCK_TIME;
    public int enableInbandFEC = 0;
    public int enableDeviceAbnormalDetection = 1;
    public int engineSolution = 0;
    public int forceUseEngineSolution = 0;
    public boolean supportAAudio = false;
    public int audioSampleRate = 0;
    public int audioChannel = 0;
    public int audioBitrate = 0;
    public int encodeMode = 0;
    public int systemVolumeType = 0;

    static {
        DEFAULT_SUPPORT_AAUDIO = TXCBuild.VersionInt() > 26;
    }

    public static AudioServerConfig loadFromSharedPreferences(Context context) {
        AudioServerConfig audioServerConfig;
        synchronized (AudioServerConfig.class) {
            audioServerConfig = new AudioServerConfig();
            SharedPreferences sharedPreferences = context.getSharedPreferences(AUDIO_SERVER_CONFIG, 0);
            audioServerConfig.enableOpenSL = sharedPreferences.getBoolean(KEY_ENABLE_OPENSL, false);
            audioServerConfig.enableAutoRestartDevice = sharedPreferences.getBoolean(KEY_ENABLE_AUTO_RESTART_DEVICE, false);
            audioServerConfig.deviceAutoRestartMinInterval = sharedPreferences.getInt(KEY_DEVICE_AUTO_RESTART_MIN_INTERVAL, 5000);
            audioServerConfig.audio16KPackageStrategy = sharedPreferences.getInt(KEY_16K_PACKAGE_STRATEGY, 0);
            audioServerConfig.maxSelectedPlayStreams = sharedPreferences.getInt(KEY_MAX_SELECTED_PLAY_STREAMS, 0);
            audioServerConfig.isLowLatencySampleRateSupported = sharedPreferences.getBoolean(KEY_IS_LOW_LATENCY_SAMPLERATE_SUPPORTED, false);
            audioServerConfig.lowLatencySampleRateBlockTime = sharedPreferences.getLong(KEY_LOW_LATENCY_SAMPLERATE_BLOCK_TIME, DEFAULT_LOW_LATENCY_SAMPLERATE_BLOCK_TIME);
            audioServerConfig.enableInbandFEC = sharedPreferences.getInt(KEY_ENABLE_INBAND_FEC, 0);
            audioServerConfig.enableDeviceAbnormalDetection = sharedPreferences.getInt(KEY_ENABLE_DEVICE_ABNORMAL_DETECTION, 1);
            audioServerConfig.engineSolution = sharedPreferences.getInt(KEY_ENGINE_SOLUTION, 0);
            audioServerConfig.audioSampleRate = sharedPreferences.getInt(KEY_AUDIO_QUALITY_CONFIG_SAMPLERATE, 0);
            audioServerConfig.audioChannel = sharedPreferences.getInt(KEY_AUDIO_QUALITY_CONFIG_CHANNEL, 0);
            audioServerConfig.audioBitrate = sharedPreferences.getInt(KEY_AUDIO_QUALITY_CONFIG_BITRATE, 0);
            audioServerConfig.encodeMode = sharedPreferences.getInt(KEY_AUDIO_QUALITY_CONFIG_ENCODE_MODE, 0);
            audioServerConfig.systemVolumeType = sharedPreferences.getInt(KEY_AUDIO_QUALITY_CONFIG_SYSTEM_VOLUME_TYPE, 0);
            audioServerConfig.forceUseEngineSolution = sharedPreferences.getInt(KEY_FORCE_USE_ENGINE_SOLUTION, 0);
            audioServerConfig.supportAAudio = sharedPreferences.getBoolean(KEY_SUPPORT_AAUDIO, DEFAULT_SUPPORT_AAUDIO);
        }
        return audioServerConfig;
    }

    public static void saveToSharedPreferences(Context context, AudioServerConfig audioServerConfig) {
        synchronized (AudioServerConfig.class) {
            SharedPreferences.Editor editorEdit = context.getSharedPreferences(AUDIO_SERVER_CONFIG, 0).edit();
            editorEdit.putBoolean(KEY_ENABLE_OPENSL, audioServerConfig.enableOpenSL);
            editorEdit.putBoolean(KEY_ENABLE_AUTO_RESTART_DEVICE, audioServerConfig.enableAutoRestartDevice);
            editorEdit.putInt(KEY_DEVICE_AUTO_RESTART_MIN_INTERVAL, audioServerConfig.deviceAutoRestartMinInterval);
            editorEdit.putInt(KEY_16K_PACKAGE_STRATEGY, audioServerConfig.audio16KPackageStrategy);
            editorEdit.putInt(KEY_MAX_SELECTED_PLAY_STREAMS, audioServerConfig.maxSelectedPlayStreams);
            editorEdit.putBoolean(KEY_IS_LOW_LATENCY_SAMPLERATE_SUPPORTED, audioServerConfig.isLowLatencySampleRateSupported);
            editorEdit.putLong(KEY_LOW_LATENCY_SAMPLERATE_BLOCK_TIME, audioServerConfig.lowLatencySampleRateBlockTime);
            editorEdit.putInt(KEY_ENABLE_INBAND_FEC, audioServerConfig.enableInbandFEC);
            editorEdit.putInt(KEY_ENABLE_DEVICE_ABNORMAL_DETECTION, audioServerConfig.enableDeviceAbnormalDetection);
            editorEdit.putInt(KEY_ENGINE_SOLUTION, audioServerConfig.engineSolution);
            editorEdit.putInt(KEY_AUDIO_QUALITY_CONFIG_SAMPLERATE, audioServerConfig.audioSampleRate);
            editorEdit.putInt(KEY_AUDIO_QUALITY_CONFIG_CHANNEL, audioServerConfig.audioChannel);
            editorEdit.putInt(KEY_AUDIO_QUALITY_CONFIG_BITRATE, audioServerConfig.audioBitrate);
            editorEdit.putInt(KEY_AUDIO_QUALITY_CONFIG_ENCODE_MODE, audioServerConfig.encodeMode);
            editorEdit.putInt(KEY_AUDIO_QUALITY_CONFIG_SYSTEM_VOLUME_TYPE, audioServerConfig.systemVolumeType);
            editorEdit.putBoolean(KEY_SUPPORT_AAUDIO, audioServerConfig.supportAAudio);
            editorEdit.apply();
        }
    }

    public boolean isAudioDeviceDSPEnabled() {
        int i2 = this.forceUseEngineSolution;
        return i2 == 0 ? this.engineSolution == 1 : i2 == 1;
    }

    public String toString() {
        return "enableOpenSL: " + this.enableOpenSL + ", enableAutoRestartDevice: " + this.enableAutoRestartDevice + ", deviceAutoRestartMinInterval: " + this.deviceAutoRestartMinInterval + ", audio16KPackageStrategy: " + this.audio16KPackageStrategy + ", isLowLatencySampleRateSupported: " + this.isLowLatencySampleRateSupported + ", lowLatencySampleRateBlockTime: " + this.lowLatencySampleRateBlockTime + ", enableDeviceAbnormalDetection: " + this.enableDeviceAbnormalDetection + ", engineSolution: " + this.engineSolution + ", audioSampleRate: " + this.audioSampleRate + ", audioChannel: " + this.audioChannel + ", audioBitrate: " + this.audioBitrate + ", encodeMode: " + this.encodeMode + ", systemVolumeType: " + this.systemVolumeType;
    }
}
