package io.agora.rtc.audio;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.audiofx.AcousticEchoCanceler;
import android.media.audiofx.AudioEffect;
import android.os.Build;
import android.os.Process;
import com.hjq.permissions.Permission;
import com.yikaobang.yixue.R2;
import io.agora.rtc.internal.Logging;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes8.dex */
class AudioDevice {
    private AudioManager _audioManager;
    private Context _context;
    private ByteBuffer _playBuffer;
    private ByteBuffer _recBuffer;
    private byte[] _tempBufPlay;
    private byte[] _tempBufRec;
    final String TAG = "AudioDevice Java";
    private final int _MaxRecPlay10msBlocks = 4;
    private AudioTrack _audioTrack = null;
    private AudioRecord _audioRecord = null;
    private final ReentrantLock _playLock = new ReentrantLock();
    private final ReentrantLock _recLock = new ReentrantLock();
    private boolean _doPlayInit = true;
    private boolean _doRecInit = true;
    private boolean _isRecording = false;
    private boolean _isPlaying = false;
    private int _bufferedRecSamples = 0;
    private int _bufferedPlaySamples = 0;
    private int _playPosition = 0;
    private int _playbackSampleRate = 0;
    private int _playBufSize = 0;
    private int _playbackRestartCount = 0;
    private int _recordSampleRate = 0;
    private int _recordChannel = 0;
    private int _playChannel = 0;
    private int _recordBufSize = 0;
    private int _recordSource = 0;
    private int _recordRestartCount = 0;
    private int _playPreviousUnderrun = 0;
    private AcousticEchoCanceler aec = null;
    private boolean useBuiltInAEC = false;
    private int _streamType = 0;

    public AudioDevice() {
        try {
            this._playBuffer = ByteBuffer.allocateDirect(R2.dimen.dp_m_8);
            this._recBuffer = ByteBuffer.allocateDirect(R2.dimen.dp_m_8);
        } catch (Exception e2) {
            Logging.e("AudioDevice Java", "failed to allocate bytebuffer", e2);
        }
        this._tempBufPlay = new byte[R2.dimen.dp_m_8];
        this._tempBufRec = new byte[R2.dimen.dp_m_8];
    }

    private boolean BuiltInAECIsAvailable() {
        try {
            return AcousticEchoCanceler.isAvailable();
        } catch (Exception unused) {
            Logging.e("AudioDevice Java", "Unable to query Audio Effect: Acoustic Echo Cancellation");
            return false;
        } catch (ExceptionInInitializerError e2) {
            Logging.e("AudioDevice Java", "Unable to create AEC object ", e2);
            return false;
        }
    }

    private boolean BuiltInAECIsEnabled() {
        return this.useBuiltInAEC;
    }

    private int CheckAudioStatus(int isPlayOut) {
        int i2 = 0;
        if (Build.VERSION.SDK_INT >= 24) {
            if (this._audioManager == null) {
                Context context = this._context;
                if (context == null) {
                    Logging.e("AudioDevice Java", "CheckAudioStatus error");
                    return -1;
                }
                this._audioManager = (AudioManager) context.getSystemService("audio");
            }
            if (isPlayOut == 0) {
                if (this._audioManager == null) {
                    Logging.e("AudioDevice Java", "CheckAudioStatus unkonwn error");
                    return -1;
                }
                AudioRecord audioRecord = this._audioRecord;
                int audioSessionId = audioRecord != null ? audioRecord.getAudioSessionId() : -1;
                Iterator it = this._audioManager.getActiveRecordingConfigurations().iterator();
                while (it.hasNext()) {
                    if (((android.media.AudioRecordingConfiguration) it.next()).getClientAudioSessionId() != audioSessionId) {
                        i2 = 1033;
                    }
                }
            }
        }
        return i2;
    }

    private boolean EnableBuiltInAEC(boolean enable) {
        this.useBuiltInAEC = enable;
        AcousticEchoCanceler acousticEchoCanceler = this.aec;
        if (acousticEchoCanceler == null) {
            return true;
        }
        if (acousticEchoCanceler.setEnabled(enable) != 0) {
            Logging.e("AudioDevice Java", "AcousticEchoCanceler.setEnabled failed");
            return false;
        }
        Logging.e("AudioDevice Java", "AcousticEchoCanceler.getEnabled: " + this.aec.getEnabled());
        return true;
    }

    private int GetAudioMode() {
        Context context;
        if (this._audioManager == null && (context = this._context) != null) {
            this._audioManager = (AudioManager) context.getSystemService("audio");
        }
        AudioManager audioManager = this._audioManager;
        if (audioManager != null) {
            return audioManager.getMode();
        }
        Logging.e("AudioDevice Java", "Could not change audio routing - no audio manager");
        return -1;
    }

    private int GetNativeSampleRate() {
        Context context;
        if (this._audioManager == null && (context = this._context) != null) {
            this._audioManager = (AudioManager) context.getSystemService("audio");
        }
        AudioManager audioManager = this._audioManager;
        if (audioManager == null) {
            Logging.w("AudioDevice Java", "Could not set audio mode - no audio manager");
            return 44100;
        }
        String property = audioManager.getProperty("android.media.property.OUTPUT_SAMPLE_RATE");
        if (property != null) {
            return Integer.parseInt(property);
        }
        return 44100;
    }

    private int GetPlayoutMaxVolume() {
        Context context;
        if (this._audioManager == null && (context = this._context) != null) {
            this._audioManager = (AudioManager) context.getSystemService("audio");
        }
        AudioManager audioManager = this._audioManager;
        if (audioManager != null) {
            return audioManager.getStreamMaxVolume(this._streamType);
        }
        return -1;
    }

    private int GetPlayoutVolume() {
        Context context;
        if (this._audioManager == null && (context = this._context) != null) {
            this._audioManager = (AudioManager) context.getSystemService("audio");
        }
        AudioManager audioManager = this._audioManager;
        if (audioManager != null) {
            return audioManager.getStreamVolume(this._streamType);
        }
        return -1;
    }

    private int GetPreferedSampleRate() throws NumberFormatException {
        int i2;
        Context context;
        try {
            if (this._audioManager == null && (context = this._context) != null) {
                this._audioManager = (AudioManager) context.getSystemService("audio");
            }
            i2 = Integer.parseInt(this._audioManager.getProperty("android.media.property.OUTPUT_SAMPLE_RATE"));
        } catch (Exception e2) {
            Logging.e("AudioDevice Java", "GetPreferedSampleRate error", e2);
            i2 = 0;
        }
        if (i2 == 0) {
            return 16000;
        }
        return i2;
    }

    private int GetUnderrunCount() {
        return Build.VERSION.SDK_INT >= 24 ? GetUnderrunCountOnNougatOrHigher() : GetUnderrunCountOnLowerThanNougat();
    }

    private int GetUnderrunCountOnLowerThanNougat() {
        return -1;
    }

    @TargetApi(24)
    private int GetUnderrunCountOnNougatOrHigher() {
        int underrunCount;
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                underrunCount = this._audioTrack.getUnderrunCount();
            } catch (Exception e2) {
                Logging.e("AudioDevice Java", "getUnderrun fail ", e2);
                underrunCount = 0;
            }
            int i2 = underrunCount - this._playPreviousUnderrun;
            i = i2 >= 0 ? i2 : 0;
            this._playPreviousUnderrun = underrunCount;
            if (i > 0) {
                Logging.d("AudioDevice Java", "Android AudioTrack underrun count: " + i);
            }
        }
        return i;
    }

    private int InitPlayback(int sampleRate, int playChannel, int streamType, int profiledMiniOutBufferMs) {
        Context context;
        this._streamType = streamType;
        int i2 = (((profiledMiniOutBufferMs * sampleRate) * playChannel) * 2) / 1000;
        int minBufferSize = AudioTrack.getMinBufferSize(sampleRate, playChannel == 2 ? 12 : 4, 2);
        Logging.d("AudioDevice Java", "Java minimum playback buffer size is " + minBufferSize + ", profiledMiniOutBufferSize is " + i2 + " stream type " + this._streamType);
        int i3 = minBufferSize * 2;
        int i4 = i3 < i2 ? i2 : i3;
        this._bufferedPlaySamples = 0;
        Logging.d("AudioDevice Java", "Java playback buffer size is " + i4 + ", duration is " + ((i4 * 1000) / ((sampleRate * playChannel) * 2)) + " ms");
        AudioTrack audioTrack = this._audioTrack;
        if (audioTrack != null) {
            audioTrack.release();
            this._audioTrack = null;
        }
        try {
            AudioTrack audioTrack2 = new AudioTrack(this._streamType, sampleRate, playChannel == 2 ? 12 : 4, 2, i4, 1);
            this._audioTrack = audioTrack2;
            this._playbackSampleRate = sampleRate;
            this._playChannel = playChannel;
            this._playBufSize = i4;
            this._playbackRestartCount = 0;
            if (audioTrack2.getState() != 1) {
                Logging.e("AudioDevice Java", "Java playback not initialized " + sampleRate);
                return -1;
            }
            Logging.d("AudioDevice Java", "Java play sample rate is set to " + sampleRate);
            if (this._audioManager == null && (context = this._context) != null) {
                this._audioManager = (AudioManager) context.getSystemService("audio");
            }
            AudioManager audioManager = this._audioManager;
            if (audioManager == null) {
                return 0;
            }
            return audioManager.getStreamMaxVolume(this._streamType);
        } catch (Exception e2) {
            Logging.e("AudioDevice Java", "Unable to new AudioTrack: ", e2);
            return -1;
        }
    }

    private int InitRecording(int audioSource, int sampleRate, int recChannel) throws IllegalStateException {
        int minBufferSize = AudioRecord.getMinBufferSize(sampleRate, recChannel == 2 ? 12 : 16, 2);
        Logging.d("AudioDevice Java", "Java minimum recording buffer size is " + minBufferSize);
        int i2 = minBufferSize * 2;
        this._bufferedRecSamples = (sampleRate * 5) / 200;
        AcousticEchoCanceler acousticEchoCanceler = this.aec;
        if (acousticEchoCanceler != null) {
            acousticEchoCanceler.release();
            this.aec = null;
        }
        AudioRecord audioRecord = this._audioRecord;
        if (audioRecord != null) {
            audioRecord.release();
            this._audioRecord = null;
        }
        try {
            AudioRecord audioRecord2 = new AudioRecord(audioSource, sampleRate, recChannel == 2 ? 12 : 16, 2, i2);
            this._audioRecord = audioRecord2;
            if (audioRecord2.getState() != 1) {
                Logging.e("AudioDevice Java", "Java recording not initialized " + sampleRate);
                return -2;
            }
            this._recordSampleRate = sampleRate;
            this._recordChannel = recChannel;
            this._recordSource = audioSource;
            this._recordBufSize = i2;
            this._recordRestartCount = 0;
            Logging.d("AudioDevice Java", "Java recording sample rate set to " + sampleRate);
            Logging.d("AudioDevice Java", "AcousticEchoCanceler.isAvailable: " + BuiltInAECIsAvailable());
            if (!BuiltInAECIsAvailable()) {
                return this._bufferedRecSamples;
            }
            AcousticEchoCanceler acousticEchoCancelerCreate = AcousticEchoCanceler.create(this._audioRecord.getAudioSessionId());
            this.aec = acousticEchoCancelerCreate;
            if (acousticEchoCancelerCreate == null) {
                Logging.e("AudioDevice Java", "AcousticEchoCanceler.create failed");
            } else {
                try {
                    AudioEffect.Descriptor descriptor = acousticEchoCancelerCreate.getDescriptor();
                    Logging.d("AudioDevice Java", "AcousticEchoCanceler name: " + descriptor.name + ", implementor: " + descriptor.implementor + ", uuid: " + descriptor.uuid);
                    EnableBuiltInAEC(this.useBuiltInAEC);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            return this._bufferedRecSamples;
        } catch (Exception e3) {
            Logging.e("AudioDevice Java", "Unable to new AudioRecord: ", e3);
            return -1;
        }
    }

    private int PlayAudio(int lengthInBytes) {
        this._playLock.lock();
        try {
            try {
            } catch (Exception e2) {
                Logging.e("AudioDevice Java", "PlayAudio got fatal error ", e2);
            }
            if (this._audioTrack == null) {
                this._playLock.unlock();
                return -2;
            }
            if (this._doPlayInit) {
                try {
                    Process.setThreadPriority(-19);
                } catch (Exception e3) {
                    Logging.e("AudioDevice Java", "Set play thread priority failed: ", e3);
                }
                this._doPlayInit = false;
            }
            this._playBuffer.get(this._tempBufPlay);
            int iWrite = this._audioTrack.write(this._tempBufPlay, 0, lengthInBytes);
            this._playBuffer.rewind();
            this._bufferedPlaySamples += iWrite >> 1;
            int playbackHeadPosition = this._audioTrack.getPlaybackHeadPosition() * this._playChannel;
            if (playbackHeadPosition < this._playPosition) {
                this._playPosition = 0;
            }
            int i2 = this._bufferedPlaySamples - (playbackHeadPosition - this._playPosition);
            this._bufferedPlaySamples = i2;
            this._playPosition = playbackHeadPosition;
            i = this._isRecording ? 0 : i2;
            if (iWrite != lengthInBytes) {
                if (this._playbackRestartCount <= 20) {
                    Logging.e("AudioDevice Java", "Error writing AudioTrack! Restart AudioTrack " + this._playbackRestartCount);
                    this._playbackRestartCount = this._playbackRestartCount + 1;
                    this._audioTrack.stop();
                    this._audioTrack.release();
                    this._audioTrack = null;
                    try {
                        AudioTrack audioTrack = new AudioTrack(this._streamType, this._playbackSampleRate, this._playChannel == 2 ? 12 : 4, 2, this._playBufSize, 1);
                        this._audioTrack = audioTrack;
                        audioTrack.play();
                    } catch (Exception e4) {
                        Logging.e("AudioDevice Java", "restart audio fail", e4);
                    }
                }
                return iWrite;
            }
            return i;
        } finally {
            this._playLock.unlock();
        }
    }

    private int QuerySpeakerStatus() {
        Context context;
        if (this._audioManager == null && (context = this._context) != null) {
            this._audioManager = (AudioManager) context.getSystemService("audio");
        }
        AudioManager audioManager = this._audioManager;
        if (audioManager == null) {
            Logging.e("AudioDevice Java", "Could not get audio routing - no audio manager");
            return -1;
        }
        if (audioManager.isBluetoothA2dpOn()) {
            return 5;
        }
        if (this._audioManager.isSpeakerphoneOn()) {
            return 3;
        }
        if (this._audioManager.isBluetoothScoOn()) {
            return 5;
        }
        return this._audioManager.isWiredHeadsetOn() ? 0 : 1;
    }

    private int RecordAudio(int lengthInBytes) {
        this._recLock.lock();
        int i2 = this._bufferedPlaySamples;
        try {
            try {
                if (this._audioRecord == null) {
                    this._recLock.unlock();
                    return -4;
                }
                if (this._doRecInit) {
                    try {
                        Process.setThreadPriority(-19);
                    } catch (Exception e2) {
                        Logging.e("AudioDevice Java", "Set rec thread priority failed: ", e2);
                    }
                    this._doRecInit = false;
                }
                this._recBuffer.rewind();
                int i3 = this._audioRecord.read(this._tempBufRec, 0, lengthInBytes);
                this._recBuffer.put(this._tempBufRec);
                if (i3 == lengthInBytes) {
                    return i2;
                }
                if (this._recordRestartCount % 10 == 0) {
                    Logging.e("AudioDevice Java", "Error reading AudioRecord! AudioRecord.read returns " + i3);
                }
                this._recordRestartCount++;
                this._audioRecord.stop();
                this._audioRecord.release();
                this._audioRecord = null;
                AudioRecord audioRecord = new AudioRecord(this._recordSource, this._recordSampleRate, this._recordChannel == 2 ? 12 : 16, 2, this._recordBufSize);
                this._audioRecord = audioRecord;
                audioRecord.startRecording();
                return i3;
            } catch (Exception e3) {
                Logging.e("AudioDevice Java", "RecordAudio try failed: ", e3);
                this._recLock.unlock();
                return -10;
            }
        } finally {
            this._recLock.unlock();
        }
    }

    private int SetAudioMode(int mode) {
        AudioManager audioManager;
        int i2;
        Context context;
        try {
            if (this._audioManager == null && (context = this._context) != null) {
                this._audioManager = (AudioManager) context.getSystemService("audio");
            }
            audioManager = this._audioManager;
        } catch (Exception unused) {
            Logging.e("AudioDevice Java", "set audio mode failed! ");
        }
        if (audioManager == null) {
            Logging.e("AudioDevice Java", "Could not change audio routing - no audio manager");
            return -1;
        }
        int streamMaxVolume = audioManager.getStreamMaxVolume(3);
        int streamVolume = this._audioManager.getStreamVolume(3);
        int streamMaxVolume2 = this._audioManager.getStreamMaxVolume(0);
        int streamVolume2 = this._audioManager.getStreamVolume(0);
        int i3 = streamMaxVolume - streamMaxVolume2;
        double d3 = streamMaxVolume2 / streamMaxVolume;
        if (this._audioManager.getMode() == mode) {
            return 0;
        }
        if (this._isPlaying) {
            Logging.e("AudioDevice Java", "_audioManager.getMode() = " + this._audioManager.getMode() + " target mode = " + mode + "factorX = " + i3 + "mMediaMaxVolume=" + streamMaxVolume + "mCommMaxVolume=" + streamMaxVolume2 + "mCurrMediaVolume=" + streamVolume + "mCurrCommVolume=" + streamVolume2 + "delta" + d3);
            if (mode == 3) {
                if (i3 < 12) {
                    i2 = streamVolume - i3;
                    if (i2 < 1) {
                        i2 = 1;
                    }
                } else {
                    i2 = (int) ((streamVolume * d3) + 0.5d);
                }
                if (i2 < 1) {
                    i2 = 1;
                }
                Logging.d("[Java AudioDevice] set voice call vol = " + i2);
                this._audioManager.setStreamVolume(0, i2, 0);
            } else if (mode == 0) {
                if (i3 < 12) {
                    int i4 = streamVolume2 + i3;
                    if (i4 < streamMaxVolume) {
                        streamMaxVolume = i4;
                    }
                } else {
                    streamMaxVolume = (int) ((streamVolume2 / d3) + 0.5d);
                }
                if (streamMaxVolume < 1) {
                    streamMaxVolume = 1;
                }
                this._audioManager.setStreamVolume(3, streamMaxVolume, 0);
                Logging.d("[Java AudioDevice] set music vol = " + streamMaxVolume);
            }
        }
        if (mode == 0) {
            this._audioManager.setMode(0);
        } else if (mode == 1) {
            this._audioManager.setMode(1);
        } else if (mode == 2) {
            this._audioManager.setMode(2);
        } else if (mode != 3) {
            this._audioManager.setMode(0);
        } else {
            this._audioManager.setMode(3);
        }
        return 0;
    }

    private int SetPlayoutSpeaker(boolean loudspeakerOn) {
        Context context;
        if (this._audioManager == null && (context = this._context) != null) {
            this._audioManager = (AudioManager) context.getSystemService("audio");
        }
        AudioManager audioManager = this._audioManager;
        if (audioManager == null) {
            Logging.e("AudioDevice Java", "Could not change audio routing - no audio manager");
            return -1;
        }
        audioManager.setSpeakerphoneOn(loudspeakerOn);
        return 0;
    }

    private int SetPlayoutVolume(int level) {
        Context context;
        if (this._audioManager == null && (context = this._context) != null) {
            this._audioManager = (AudioManager) context.getSystemService("audio");
        }
        AudioManager audioManager = this._audioManager;
        if (audioManager == null) {
            return -1;
        }
        int streamMaxVolume = audioManager.getStreamMaxVolume(this._streamType);
        if (level < 255) {
            streamMaxVolume = (level * streamMaxVolume) / 255;
        }
        this._audioManager.setStreamVolume(this._streamType, streamMaxVolume, 0);
        return 0;
    }

    private int StartPlayback() throws IllegalStateException {
        try {
            this._audioTrack.setVolume(1.0f);
            this._audioTrack.play();
            this._isPlaying = true;
            return 0;
        } catch (IllegalStateException e2) {
            e2.printStackTrace();
            return -1;
        } catch (Exception e3) {
            Logging.e("AudioDevice Java", "startplayback fail", e3);
            return -1;
        }
    }

    private int StartRecording() throws IllegalStateException {
        try {
            AudioRecord audioRecord = this._audioRecord;
            if (audioRecord == null) {
                return -2;
            }
            audioRecord.startRecording();
            this._isRecording = true;
            return 0;
        } catch (IllegalStateException e2) {
            Logging.e("AudioDevice Java", "failed to startRecording", e2);
            return -1;
        } catch (Exception e3) {
            Logging.e("AudioDevice Java", "failed to startRecording Exception", e3);
            return -2;
        }
    }

    private int StopPlayback() {
        this._playLock.lock();
        try {
            try {
                this._audioTrack.setVolume(0.0f);
                if (this._audioTrack.getPlayState() == 3) {
                    this._audioTrack.stop();
                    this._audioTrack.flush();
                }
                this._audioTrack.release();
                this._audioTrack = null;
            } catch (IllegalStateException e2) {
                Logging.e("AudioDevice Java", "Unable to stop playback: ", e2);
                AudioTrack audioTrack = this._audioTrack;
                if (audioTrack != null) {
                    audioTrack.flush();
                    this._audioTrack.release();
                    this._audioTrack = null;
                }
                this._doPlayInit = true;
                this._playLock.unlock();
                return -1;
            } catch (Exception e3) {
                Logging.e("AudioDevice Java", "Stop playback fail", e3);
                AudioTrack audioTrack2 = this._audioTrack;
                if (audioTrack2 != null) {
                    audioTrack2.flush();
                    this._audioTrack.release();
                    this._audioTrack = null;
                }
            }
            this._doPlayInit = true;
            this._playLock.unlock();
            this._isPlaying = false;
            return 0;
        } catch (Throwable th) {
            AudioTrack audioTrack3 = this._audioTrack;
            if (audioTrack3 != null) {
                audioTrack3.flush();
                this._audioTrack.release();
                this._audioTrack = null;
            }
            this._doPlayInit = true;
            this._playLock.unlock();
            throw th;
        }
    }

    private int StopRecording() {
        this._recLock.lock();
        try {
            try {
                if (this._audioRecord.getRecordingState() == 3) {
                    this._audioRecord.stop();
                }
                AcousticEchoCanceler acousticEchoCanceler = this.aec;
                if (acousticEchoCanceler != null) {
                    acousticEchoCanceler.release();
                    this.aec = null;
                }
                this._audioRecord.release();
                this._audioRecord = null;
            } catch (Exception e2) {
                Logging.e("AudioDevice Java", "error in StopRecording ", e2);
                AudioRecord audioRecord = this._audioRecord;
                if (audioRecord != null) {
                    audioRecord.release();
                    this._audioRecord = null;
                }
            }
            this._doRecInit = true;
            this._recLock.unlock();
            this._isRecording = false;
            return 0;
        } catch (Throwable th) {
            AudioRecord audioRecord2 = this._audioRecord;
            if (audioRecord2 != null) {
                audioRecord2.release();
                this._audioRecord = null;
            }
            this._doRecInit = true;
            this._recLock.unlock();
            throw th;
        }
    }

    private boolean checkAudioRecordPermission() {
        Context context = this._context;
        if (context != null && context.checkCallingOrSelfPermission(Permission.RECORD_AUDIO) == 0 && this._context.checkCallingOrSelfPermission("android.permission.MODIFY_AUDIO_SETTINGS") == 0) {
            return true;
        }
        Logging.e("Android Audio Device", "android.permission.RECORD_AUDIO is not granted");
        return false;
    }
}
