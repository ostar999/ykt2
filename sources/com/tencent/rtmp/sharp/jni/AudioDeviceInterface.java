package com.tencent.rtmp.sharp.jni;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.os.Process;
import android.os.SystemClock;
import cn.hutool.core.text.StrPool;
import com.google.android.exoplayer2.util.MimeTypes;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.liteav.basic.util.TXCBuild;
import com.tencent.rtmp.sharp.jni.TraeAudioCodecList;
import com.tencent.rtmp.sharp.jni.TraeAudioSession;
import com.umeng.analytics.pro.am;
import com.yikaobang.yixue.R2;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@TargetApi(16)
/* loaded from: classes6.dex */
public class AudioDeviceInterface {
    private static final FileFilter CPU_FILTER = new FileFilter() { // from class: com.tencent.rtmp.sharp.jni.AudioDeviceInterface.4
        @Override // java.io.FileFilter
        public boolean accept(File file) {
            String name = file.getName();
            if (!name.startsWith(am.f22460w)) {
                return false;
            }
            for (int i2 = 3; i2 < name.length(); i2++) {
                if (name.charAt(i2) < '0' || name.charAt(i2) > '9') {
                    return false;
                }
            }
            return true;
        }
    };
    public static final int OUTPUT_MODE_HEADSET = 0;
    public static final int OUTPUT_MODE_SPEAKER = 1;
    private static final String TAG = "AudioDeviceInterface";
    private static boolean _dumpEnable = false;
    private static boolean _logEnable = true;
    private static boolean isSupportVivoKTVHelper = false;
    private static String[] mDeviceList;
    private static VivoKTVHelper mVivoKTVHelper;
    private TraeAudioSession _asAudioManager;
    private ByteBuffer _decBuffer0;
    private ByteBuffer _decBuffer1;
    private ByteBuffer _decBuffer10;
    private ByteBuffer _decBuffer2;
    private ByteBuffer _decBuffer3;
    private ByteBuffer _decBuffer4;
    private ByteBuffer _decBuffer5;
    private ByteBuffer _decBuffer6;
    private ByteBuffer _decBuffer7;
    private ByteBuffer _decBuffer8;
    private ByteBuffer _decBuffer9;
    private ByteBuffer _playBuffer;
    private boolean _preDone;
    private Condition _precon;
    private ReentrantLock _prelock;
    private ByteBuffer _recBuffer;
    private byte[] _tempBufPlay;
    private byte[] _tempBufRec;
    private TraeAudioCodecList _traeAudioCodecList;
    private int switchState;
    private boolean usingJava;
    private AudioTrack _audioTrack = null;
    private AudioRecord _audioRecord = null;
    private int _streamType = 0;
    private int _playSamplerate = 8000;
    private int _channelOutType = 4;
    private int _audioSource = 0;
    private int _deviceStat = 0;
    private int _sceneMode = 0;
    private int _sessionId = 0;
    private Context _context = null;
    private int _modePolicy = -1;
    private int _audioSourcePolicy = -1;
    private int _audioStreamTypePolicy = -1;
    private AudioManager _audioManager = null;
    private boolean _doPlayInit = true;
    private boolean _doRecInit = true;
    private boolean _isRecording = false;
    private boolean _isPlaying = false;
    private int _bufferedRecSamples = 0;
    private int _bufferedPlaySamples = 0;
    private int _playPosition = 0;
    private File _rec_dump = null;
    private File _play_dump = null;
    private FileOutputStream _rec_out = null;
    private FileOutputStream _play_out = null;
    private int nRecordLengthMs = 0;
    private int nPlayLengthMs = 0;
    private TraeAudioSession _as = null;
    private String _connectedDev = TraeAudioManager.DEVICE_NONE;
    private boolean _audioRouteChanged = false;

    public AudioDeviceInterface() {
        this._traeAudioCodecList = null;
        ReentrantLock reentrantLock = new ReentrantLock();
        this._prelock = reentrantLock;
        this._precon = reentrantLock.newCondition();
        this._preDone = false;
        this.usingJava = true;
        this.switchState = 0;
        this._asAudioManager = null;
        try {
            this._playBuffer = ByteBuffer.allocateDirect(R2.attr.iconTint);
            this._recBuffer = ByteBuffer.allocateDirect(R2.attr.iconTint);
            this._decBuffer0 = ByteBuffer.allocateDirect(R2.attr.triangleHeight);
            this._decBuffer1 = ByteBuffer.allocateDirect(R2.attr.triangleHeight);
            this._decBuffer2 = ByteBuffer.allocateDirect(R2.attr.triangleHeight);
            this._decBuffer3 = ByteBuffer.allocateDirect(R2.attr.triangleHeight);
            this._decBuffer4 = ByteBuffer.allocateDirect(R2.attr.triangleHeight);
            this._decBuffer5 = ByteBuffer.allocateDirect(R2.attr.triangleHeight);
            this._decBuffer6 = ByteBuffer.allocateDirect(R2.attr.triangleHeight);
            this._decBuffer7 = ByteBuffer.allocateDirect(R2.attr.triangleHeight);
            this._decBuffer8 = ByteBuffer.allocateDirect(R2.attr.triangleHeight);
            this._decBuffer9 = ByteBuffer.allocateDirect(R2.attr.triangleHeight);
            this._decBuffer10 = ByteBuffer.allocateDirect(R2.attr.triangleHeight);
        } catch (Exception e2) {
            if (QLog.isColorLevel()) {
                QLog.w(TAG, 2, e2.getMessage());
            }
        }
        this._tempBufPlay = new byte[R2.attr.iconTint];
        this._tempBufRec = new byte[R2.attr.iconTint];
        this._traeAudioCodecList = new TraeAudioCodecList();
        int iVersionInt = TXCBuild.VersionInt();
        if (QLog.isColorLevel()) {
            QLog.w(TAG, 2, "AudioDeviceInterface apiLevel:" + iVersionInt);
        }
        if (QLog.isColorLevel()) {
            QLog.w(TAG, 2, " SDK_INT:" + TXCBuild.VersionInt());
        }
        if (QLog.isColorLevel()) {
            QLog.w(TAG, 2, "manufacture:" + TXCBuild.Manufacturer());
        }
        if (QLog.isColorLevel()) {
            QLog.w(TAG, 2, "MODEL:" + TXCBuild.Model());
        }
    }

    private int CloseMp3File(int i2) {
        long j2 = i2;
        TraeAudioCodecList.CodecInfo codecInfoFind = this._traeAudioCodecList.find(j2);
        if (codecInfoFind == null) {
            return -1;
        }
        codecInfoFind.audioDecoder.release();
        codecInfoFind.audioDecoder = null;
        this._traeAudioCodecList.remove(j2);
        return 0;
    }

    private int EnableVivoKTVLoopback(int i2) {
        if (QLog.isColorLevel()) {
            QLog.w(TAG, 2, "EnableVivoKTVLoopback: " + i2 + " isSupportVivoKTVHelper:" + isSupportVivoKTVHelper + " mVivoKTVHelper:" + mVivoKTVHelper);
        }
        VivoKTVHelper vivoKTVHelper = mVivoKTVHelper;
        if (vivoKTVHelper == null || !isSupportVivoKTVHelper) {
            return -1;
        }
        vivoKTVHelper.setPlayFeedbackParam(i2);
        return 0;
    }

    private int InitPlayback(int i2, int i3) {
        AudioManager audioManager;
        Object obj;
        Object obj2;
        if (QLog.isColorLevel()) {
            QLog.w(TAG, 2, "InitPlayback entry: sampleRate " + i2);
        }
        if (this._isPlaying || this._audioTrack != null || i3 > 2) {
            if (!QLog.isColorLevel()) {
                return -1;
            }
            QLog.e(TAG, 2, "InitPlayback _isPlaying:" + this._isPlaying);
            return -1;
        }
        if (this._audioManager == null) {
            try {
                this._audioManager = (AudioManager) this._context.getSystemService("audio");
            } catch (Exception e2) {
                if (QLog.isColorLevel()) {
                    QLog.w(TAG, 2, e2.getMessage());
                }
                return -1;
            }
        }
        if (i3 == 2) {
            this._channelOutType = 12;
        } else {
            this._channelOutType = 4;
        }
        this._playSamplerate = i2;
        int minBufferSize = AudioTrack.getMinBufferSize(i2, this._channelOutType, 2);
        if (this._channelOutType == 12) {
            if (QLog.isColorLevel()) {
                QLog.w(TAG, 2, "InitPlayback, _channelOutType stero");
            } else if (this._channelOutType == 4 && QLog.isColorLevel()) {
                QLog.w(TAG, 2, "InitPlayback, _channelOutType Mono");
            }
        }
        int i4 = (((i2 * 20) * 1) * 2) / 1000;
        if (this._channelOutType == 12) {
            i4 *= 2;
        }
        if (QLog.isColorLevel()) {
            QLog.w(TAG, 2, "InitPlayback: minPlayBufSize:" + minBufferSize + " 20msFz:" + i4);
        }
        this._bufferedPlaySamples = 0;
        AudioTrack audioTrack = this._audioTrack;
        Object obj3 = null;
        if (audioTrack != null) {
            audioTrack.release();
            this._audioTrack = null;
        }
        int[] iArr = {0, 0, 3, 1};
        this._streamType = TraeAudioManager.getAudioStreamType(this._audioStreamTypePolicy);
        if (this._audioRouteChanged) {
            if (QLog.isColorLevel()) {
                QLog.w(TAG, 2, "_audioRouteChanged:" + this._audioRouteChanged + " _streamType:" + this._streamType);
            }
            if (this._audioManager.getMode() == 0 && this._connectedDev.equals(TraeAudioManager.DEVICE_SPEAKERPHONE)) {
                this._streamType = 3;
            } else {
                this._streamType = 0;
            }
            this._audioRouteChanged = false;
        }
        iArr[0] = this._streamType;
        int i5 = minBufferSize;
        int i6 = 0;
        for (int i7 = 4; i6 < i7 && this._audioTrack == null; i7 = 4) {
            this._streamType = iArr[i6];
            if (QLog.isColorLevel()) {
                QLog.w(TAG, 2, "InitPlayback: min play buf size is " + minBufferSize + " hw_sr:" + AudioTrack.getNativeOutputSampleRate(this._streamType));
            }
            int i8 = 1;
            while (true) {
                if (i8 > 2) {
                    obj = obj3;
                    break;
                }
                int i9 = minBufferSize * i8;
                if (i9 >= i4 * 4 || i8 >= 2) {
                    try {
                        this.nPlayLengthMs = (i9 * 500) / (i2 * i3);
                        AudioTrack audioTrack2 = new AudioTrack(this._streamType, this._playSamplerate, this._channelOutType, 2, i9, 1);
                        this._audioTrack = audioTrack2;
                        if (audioTrack2.getState() == 1) {
                            i5 = i9;
                            obj = null;
                            break;
                        }
                        if (QLog.isColorLevel()) {
                            QLog.w(TAG, 2, "InitPlayback: play not initialized playBufSize:" + i9 + " sr:" + this._playSamplerate);
                        }
                        this._audioTrack.release();
                        obj2 = null;
                        this._audioTrack = null;
                    } catch (Exception e3) {
                        if (QLog.isColorLevel()) {
                            QLog.w(TAG, 2, e3.getMessage() + " _audioTrack:" + this._audioTrack);
                        }
                        AudioTrack audioTrack3 = this._audioTrack;
                        if (audioTrack3 != null) {
                            audioTrack3.release();
                        }
                        obj2 = null;
                        this._audioTrack = null;
                    }
                } else {
                    obj2 = obj3;
                }
                i8++;
                obj3 = obj2;
                i5 = i9;
            }
            i6++;
            obj3 = obj;
        }
        if (this._audioTrack == null) {
            if (!QLog.isColorLevel()) {
                return -1;
            }
            QLog.w(TAG, 2, "InitPlayback fail!!!");
            return -1;
        }
        TraeAudioSession traeAudioSession = this._as;
        if (traeAudioSession != null && (audioManager = this._audioManager) != null) {
            traeAudioSession.voiceCallAudioParamChanged(audioManager.getMode(), this._streamType);
        }
        this._playPosition = this._audioTrack.getPlaybackHeadPosition();
        if (QLog.isColorLevel()) {
            QLog.w(TAG, 2, "InitPlayback exit: streamType:" + this._streamType + " samplerate:" + this._playSamplerate + " _playPosition:" + this._playPosition + " playBufSize:" + i5 + " nPlayLengthMs:" + this.nPlayLengthMs);
        }
        TraeAudioManager.forceVolumeControlStream(this._audioManager, this._connectedDev.equals(TraeAudioManager.DEVICE_BLUETOOTHHEADSET) ? 6 : this._audioTrack.getStreamType());
        return 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:55:0x016f  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0191  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int InitRecording(int r23, int r24) {
        /*
            Method dump skipped, instructions count: 584
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.rtmp.sharp.jni.AudioDeviceInterface.InitRecording(int, int):int");
    }

    private int InitSetting(int i2, int i3, int i4, int i5, int i6) {
        this._audioSourcePolicy = i2;
        this._audioStreamTypePolicy = i3;
        this._modePolicy = i4;
        this._deviceStat = i5;
        this._sceneMode = i6;
        if (i5 == 1 || i5 == 5 || i5 == 2 || i5 == 3) {
            TraeAudioManager.IsMusicScene = true;
        } else {
            TraeAudioManager.IsMusicScene = false;
        }
        if (i6 == 0 || i6 == 4) {
            TraeAudioManager.IsEarPhoneSupported = true;
        } else {
            TraeAudioManager.IsEarPhoneSupported = false;
        }
        TraeAudioManager.IsUpdateSceneFlag = true;
        if (QLog.isColorLevel()) {
            QLog.w(TAG, 2, "InitSetting: _audioSourcePolicy:" + this._audioSourcePolicy + " _audioStreamTypePolicy:" + this._audioStreamTypePolicy + " _modePolicy:" + this._modePolicy + " DeviceStat:" + i5 + " isSupportVivoKTVHelper:" + isSupportVivoKTVHelper);
        }
        return 0;
    }

    public static final void LogTraceEntry(String str) {
        if (_logEnable) {
            String str2 = getTraceInfo() + " entry:" + str;
            if (QLog.isColorLevel()) {
                QLog.w(TAG, 2, str2);
            }
        }
    }

    public static final void LogTraceExit() {
        if (_logEnable) {
            String str = getTraceInfo() + " exit";
            if (QLog.isColorLevel()) {
                QLog.w(TAG, 2, str);
            }
        }
    }

    private int OpenMp3File(String str, int i2, int i3) {
        long j2 = i2;
        if (this._traeAudioCodecList.find(j2) != null) {
            return -1;
        }
        TraeAudioCodecList.CodecInfo codecInfoAdd = this._traeAudioCodecList.add(j2);
        codecInfoAdd.audioDecoder.setIOPath(str);
        codecInfoAdd.audioDecoder.setIndex(i2);
        int iPrepare = codecInfoAdd.audioDecoder.prepare(i3);
        if (iPrepare == 0) {
            return 0;
        }
        if (QLog.isColorLevel()) {
            QLog.w(TAG, 2, "openFile mp3 Failed!!!");
        }
        codecInfoAdd.audioDecoder.release();
        codecInfoAdd.audioDecoder = null;
        this._traeAudioCodecList.remove(j2);
        return iPrepare;
    }

    private int OpenslesNeedResetAudioTrack(boolean z2) {
        Context context;
        try {
        } catch (Exception e2) {
            if (QLog.isColorLevel()) {
                QLog.e(TAG, 2, "PlayAudio Exception: " + e2.getMessage());
            }
        }
        if (!TraeAudioManager.isCloseSystemAPM(this._modePolicy)) {
            return -1;
        }
        if (this._audioRouteChanged || z2) {
            if (this._audioManager == null && (context = this._context) != null) {
                this._audioManager = (AudioManager) context.getSystemService("audio");
            }
            AudioManager audioManager = this._audioManager;
            if (audioManager == null) {
                return 0;
            }
            if (audioManager.getMode() == 0 && this._connectedDev.equals(TraeAudioManager.DEVICE_SPEAKERPHONE)) {
                this._audioStreamTypePolicy = 3;
            } else {
                this._audioStreamTypePolicy = 0;
            }
            this._audioRouteChanged = false;
        }
        return this._audioStreamTypePolicy;
    }

    private int PlayAudio(int i2) {
        boolean z2;
        Context context;
        int i3;
        Object obj;
        Object obj2;
        FileOutputStream fileOutputStream;
        int i4 = i2;
        int i5 = 1;
        boolean z3 = !this._isPlaying;
        AudioTrack audioTrack = this._audioTrack;
        int i6 = 0;
        int i7 = 2;
        if (z3 || (audioTrack == null)) {
            if (QLog.isColorLevel()) {
                QLog.e(TAG, 2, "PlayAudio: _isPlaying " + this._isPlaying + " " + this._audioTrack);
            }
            return -1;
        }
        if (audioTrack == null) {
            return -2;
        }
        try {
            if (this._doPlayInit) {
                try {
                    Process.setThreadPriority(-19);
                    Thread.currentThread().setName("TRAEAudioPlay");
                } catch (Exception e2) {
                    if (QLog.isColorLevel()) {
                        QLog.w(TAG, 2, "Set play thread priority failed: " + e2.getMessage());
                    }
                }
                this._doPlayInit = false;
            }
            if (_dumpEnable && (fileOutputStream = this._play_out) != null) {
                try {
                    fileOutputStream.write(this._tempBufPlay, 0, 0);
                } catch (IOException unused) {
                    if (QLog.isColorLevel()) {
                        QLog.e(TAG, 2, "write data failed.");
                    }
                }
            }
            if (this._audioRouteChanged) {
                if (this._audioManager == null && (context = this._context) != null) {
                    this._audioManager = (AudioManager) context.getSystemService("audio");
                }
                AudioManager audioManager = this._audioManager;
                if (audioManager != null && audioManager.getMode() == 0 && this._connectedDev.equals(TraeAudioManager.DEVICE_SPEAKERPHONE)) {
                    this._streamType = 3;
                } else {
                    this._streamType = 0;
                }
                z2 = this._streamType != this._audioTrack.getStreamType();
                this._audioRouteChanged = false;
            } else {
                z2 = false;
            }
            this._playBuffer.get(this._tempBufPlay);
            if (z2) {
                try {
                    this._playBuffer.rewind();
                    long jElapsedRealtime = SystemClock.elapsedRealtime();
                    if (QLog.isColorLevel()) {
                        QLog.w(TAG, 2, " track resting: _streamType:" + this._streamType + " at.st:" + this._audioTrack.getStreamType());
                    }
                    Object obj3 = null;
                    if (this._audioTrack.getPlayState() == 3) {
                        try {
                            if (QLog.isColorLevel()) {
                                QLog.w(TAG, 2, "StopPlayback stoping...");
                            }
                            this._audioTrack.stop();
                            this._audioTrack.flush();
                            if (QLog.isColorLevel()) {
                                QLog.w(TAG, 2, "StopPlayback flushing... state:" + this._audioTrack.getPlayState());
                            }
                            this._audioTrack.release();
                            if (QLog.isColorLevel()) {
                                QLog.w(TAG, 2, "StopPlayback releaseing... state:" + this._audioTrack.getPlayState());
                            }
                            this._audioTrack = null;
                        } catch (IllegalStateException unused2) {
                            if (QLog.isColorLevel()) {
                                QLog.e(TAG, 2, "StopPlayback err");
                            }
                        }
                    }
                    int minBufferSize = AudioTrack.getMinBufferSize(this._playSamplerate, this._channelOutType, 2);
                    int i8 = 4;
                    int[] iArr = {0, 0, 3, 1};
                    iArr[0] = this._streamType;
                    int i9 = (((this._playSamplerate * 20) * 1) * 2) / 1000;
                    if (this._channelOutType == 12) {
                        i9 *= 2;
                    }
                    int i10 = i9;
                    while (i6 < i8 && this._audioTrack == null) {
                        this._streamType = iArr[i6];
                        if (QLog.isColorLevel()) {
                            QLog.w(TAG, i7, "InitPlayback: min play buf size is " + minBufferSize + " hw_sr:" + AudioTrack.getNativeOutputSampleRate(this._streamType));
                        }
                        int i11 = i5;
                        while (true) {
                            if (i11 > i7) {
                                Object obj4 = obj3;
                                i3 = i5;
                                obj = obj4;
                                break;
                            }
                            int i12 = minBufferSize * i11;
                            if (i12 >= i10 * 4 || i11 >= i7) {
                                try {
                                    try {
                                        this._audioTrack = new AudioTrack(this._streamType, this._playSamplerate, this._channelOutType, 2, i12, 1);
                                        if (QLog.isColorLevel()) {
                                            QLog.w(TAG, 2, " _audioTrack:" + this._audioTrack);
                                        }
                                        i3 = 1;
                                        if (this._audioTrack.getState() == 1) {
                                            obj = null;
                                            break;
                                        }
                                        if (QLog.isColorLevel()) {
                                            QLog.w(TAG, 2, "InitPlayback: play not initialized playBufSize:" + i12 + " sr:" + this._playSamplerate);
                                        }
                                        this._audioTrack.release();
                                        this._audioTrack = null;
                                    } catch (Exception e3) {
                                        e = e3;
                                        i3 = 1;
                                        if (QLog.isColorLevel()) {
                                            QLog.w(TAG, 2, e.getMessage() + " _audioTrack:" + this._audioTrack);
                                        }
                                        AudioTrack audioTrack2 = this._audioTrack;
                                        if (audioTrack2 != null) {
                                            audioTrack2.release();
                                        }
                                        obj2 = null;
                                        this._audioTrack = null;
                                        i11++;
                                        i7 = 2;
                                        int i13 = i3;
                                        obj3 = obj2;
                                        i5 = i13;
                                    }
                                } catch (Exception e4) {
                                    e = e4;
                                    i3 = i5;
                                }
                            } else {
                                i3 = i5;
                            }
                            obj2 = null;
                            i11++;
                            i7 = 2;
                            int i132 = i3;
                            obj3 = obj2;
                            i5 = i132;
                        }
                        i6++;
                        i7 = 2;
                        i8 = 4;
                        int i14 = i3;
                        obj3 = obj;
                        i5 = i14;
                    }
                    AudioTrack audioTrack3 = this._audioTrack;
                    if (audioTrack3 != null) {
                        try {
                            audioTrack3.play();
                            this._as.voiceCallAudioParamChanged(this._audioManager.getMode(), this._streamType);
                            TraeAudioManager.forceVolumeControlStream(this._audioManager, this._connectedDev.equals(TraeAudioManager.DEVICE_BLUETOOTHHEADSET) ? 6 : this._audioTrack.getStreamType());
                        } catch (Exception unused3) {
                            if (QLog.isColorLevel()) {
                                QLog.e(TAG, 2, "start play failed.");
                            }
                        }
                    }
                    if (!QLog.isColorLevel()) {
                        return i4;
                    }
                    QLog.e(TAG, 2, "  track reset used:" + (SystemClock.elapsedRealtime() - jElapsedRealtime) + "ms");
                    return i4;
                } catch (Exception e5) {
                    e = e5;
                }
            } else {
                int iWrite = this._audioTrack.write(this._tempBufPlay, 0, i4);
                try {
                    this._playBuffer.rewind();
                    if (iWrite < 0) {
                        if (QLog.isColorLevel()) {
                            QLog.e(TAG, 2, "Could not write data from sc (write = " + iWrite + ", length = " + i4 + ")");
                        }
                        return -1;
                    }
                    if (iWrite != i4 && QLog.isColorLevel()) {
                        QLog.e(TAG, 2, "Could not write all data from sc (write = " + iWrite + ", length = " + i4 + ")");
                    }
                    this._bufferedPlaySamples += iWrite >> 1;
                    int playbackHeadPosition = this._audioTrack.getPlaybackHeadPosition();
                    if (playbackHeadPosition < this._playPosition) {
                        this._playPosition = 0;
                    }
                    this._bufferedPlaySamples -= playbackHeadPosition - this._playPosition;
                    this._playPosition = playbackHeadPosition;
                    boolean z4 = this._isRecording;
                    return iWrite;
                } catch (Exception e6) {
                    e = e6;
                    i4 = iWrite;
                }
            }
        } catch (Exception e7) {
            e = e7;
            i4 = 0;
        }
        if (!QLog.isColorLevel()) {
            return i4;
        }
        QLog.e(TAG, 2, "PlayAudio Exception: " + e.getMessage());
        return i4;
    }

    private int ReadMp3File(int i2) throws MediaCodec.CryptoException {
        ByteBuffer decBuffer;
        TraeAudioCodecList.CodecInfo codecInfoFind = this._traeAudioCodecList.find(i2);
        if (codecInfoFind == null || (decBuffer = getDecBuffer(i2)) == null) {
            return -1;
        }
        decBuffer.rewind();
        int frameSize = codecInfoFind.audioDecoder.getFrameSize();
        int iReadOneFrame = codecInfoFind.audioDecoder.ReadOneFrame(codecInfoFind._tempBufdec, frameSize);
        decBuffer.put(codecInfoFind._tempBufdec, 0, frameSize);
        return iReadOneFrame;
    }

    private int RecordAudio(int i2) {
        FileOutputStream fileOutputStream;
        if (!this._isRecording) {
            if (QLog.isColorLevel()) {
                QLog.e(TAG, 2, "RecordAudio: _isRecording " + this._isRecording);
            }
            return -1;
        }
        int i3 = 0;
        try {
            if (this._audioRecord == null) {
                return -2;
            }
            if (this._doRecInit) {
                try {
                    Process.setThreadPriority(-19);
                    Thread.currentThread().setName("TRAEAudioRecord");
                } catch (Exception e2) {
                    if (QLog.isColorLevel()) {
                        QLog.w(TAG, 2, "Set rec thread priority failed: " + e2.getMessage());
                    }
                }
                this._doRecInit = false;
            }
            this._recBuffer.rewind();
            int i4 = this._audioRecord.read(this._tempBufRec, 0, i2);
            try {
                if (i4 < 0) {
                    if (QLog.isColorLevel()) {
                        QLog.e(TAG, 2, "Could not read data from sc (read = " + i4 + ", length = " + i2 + ")");
                    }
                    return -1;
                }
                this._recBuffer.put(this._tempBufRec, 0, i4);
                if (_dumpEnable && (fileOutputStream = this._rec_out) != null) {
                    try {
                        fileOutputStream.write(this._tempBufRec, 0, i4);
                    } catch (IOException unused) {
                        if (QLog.isColorLevel()) {
                            QLog.e(TAG, 2, "write rec buffer failed.");
                        }
                    }
                }
                if (i4 == i2) {
                    return i4;
                }
                if (QLog.isColorLevel()) {
                    QLog.e(TAG, 2, "Could not read all data from sc (read = " + i4 + ", length = " + i2 + ")");
                }
                return -1;
            } catch (Exception e3) {
                e = e3;
                i3 = i4;
                if (QLog.isColorLevel()) {
                    QLog.e(TAG, 2, "RecordAudio Exception: " + e.getMessage());
                }
                return i3;
            }
        } catch (Exception e4) {
            e = e4;
        }
    }

    private int SeekMp3To(int i2, int i3) {
        TraeAudioCodecList.CodecInfo codecInfoFind = this._traeAudioCodecList.find(i2);
        if (codecInfoFind != null) {
            return codecInfoFind.audioDecoder.SeekTo(i3);
        }
        return 0;
    }

    private int SetAudioOutputMode(int i2) {
        TraeAudioSession traeAudioSession;
        if (QLog.isColorLevel()) {
            QLog.w(TAG, 2, "TraeAudioSession SetAudioOutputMode: " + i2);
        }
        if (i2 != 0) {
            if (1 != i2 || (traeAudioSession = this._asAudioManager) == null) {
                return -1;
            }
            traeAudioSession.connectDevice(TraeAudioManager.DEVICE_SPEAKERPHONE);
            return 0;
        }
        if (mDeviceList == null || this._asAudioManager == null) {
            return -1;
        }
        boolean z2 = false;
        do {
            int i3 = 0;
            while (true) {
                String[] strArr = mDeviceList;
                if (i3 >= strArr.length || z2) {
                    break;
                }
                if (TraeAudioManager.DEVICE_WIREDHEADSET.equals(strArr[i3])) {
                    this._asAudioManager.connectDevice(TraeAudioManager.DEVICE_WIREDHEADSET);
                    z2 = true;
                    break;
                }
                i3++;
            }
            int i4 = 0;
            while (true) {
                String[] strArr2 = mDeviceList;
                if (i4 >= strArr2.length || z2) {
                    break;
                }
                if (TraeAudioManager.DEVICE_BLUETOOTHHEADSET.equals(strArr2[i4])) {
                    this._asAudioManager.connectDevice(TraeAudioManager.DEVICE_BLUETOOTHHEADSET);
                    z2 = true;
                    break;
                }
                i4++;
            }
            int i5 = 0;
            while (true) {
                String[] strArr3 = mDeviceList;
                if (i5 >= strArr3.length || z2) {
                    break;
                }
                if (TraeAudioManager.DEVICE_EARPHONE.equals(strArr3[i5])) {
                    this._asAudioManager.connectDevice(TraeAudioManager.DEVICE_EARPHONE);
                    z2 = true;
                    break;
                }
                i5++;
            }
        } while (!z2);
        return 0;
    }

    private int SetPlayoutVolume(int i2) {
        Context context;
        if (this._audioManager == null && (context = this._context) != null) {
            this._audioManager = (AudioManager) context.getSystemService("audio");
        }
        AudioManager audioManager = this._audioManager;
        if (audioManager == null) {
            return -1;
        }
        audioManager.setStreamVolume(0, i2, 0);
        return 0;
    }

    private int StartPlayback() throws IllegalStateException {
        if (this._isPlaying) {
            if (QLog.isColorLevel()) {
                QLog.e(TAG, 2, "StartPlayback _isPlaying");
            }
            return -1;
        }
        AudioTrack audioTrack = this._audioTrack;
        if (audioTrack == null) {
            if (QLog.isColorLevel()) {
                QLog.e(TAG, 2, "StartPlayback _audioTrack:" + this._audioTrack);
            }
            return -1;
        }
        try {
            audioTrack.play();
            if (_dumpEnable) {
                AudioManager audioManager = this._audioManager;
                this._play_dump = new File(getDumpFilePath("jniplay.pcm", audioManager != null ? audioManager.getMode() : -1));
                try {
                    this._play_out = new FileOutputStream(this._play_dump);
                } catch (FileNotFoundException unused) {
                    if (QLog.isColorLevel()) {
                        QLog.e(TAG, 2, "open play dump file failed.");
                    }
                }
            }
            this._isPlaying = true;
            if (!QLog.isColorLevel()) {
                return 0;
            }
            QLog.w(TAG, 2, "StartPlayback ok");
            return 0;
        } catch (IllegalStateException unused2) {
            if (QLog.isColorLevel()) {
                QLog.e(TAG, 2, "StartPlayback fail");
            }
            return -1;
        }
    }

    private int StartRecording() throws IllegalStateException {
        if (QLog.isColorLevel()) {
            QLog.w(TAG, 2, "StartRecording entry");
        }
        if (this._isRecording) {
            if (QLog.isColorLevel()) {
                QLog.e(TAG, 2, "StartRecording _isRecording:" + this._isRecording);
            }
            return -1;
        }
        AudioRecord audioRecord = this._audioRecord;
        if (audioRecord == null) {
            if (QLog.isColorLevel()) {
                QLog.e(TAG, 2, "StartRecording _audioRecord:" + this._audioRecord);
            }
            return -1;
        }
        try {
            audioRecord.startRecording();
            if (_dumpEnable) {
                AudioManager audioManager = this._audioManager;
                this._rec_dump = new File(getDumpFilePath("jnirecord.pcm", audioManager != null ? audioManager.getMode() : -1));
                try {
                    this._rec_out = new FileOutputStream(this._rec_dump);
                } catch (FileNotFoundException unused) {
                    if (QLog.isColorLevel()) {
                        QLog.e(TAG, 2, "open rec dump file failed.");
                    }
                }
            }
            this._isRecording = true;
            if (!QLog.isColorLevel()) {
                return 0;
            }
            QLog.w(TAG, 2, "StartRecording ok");
            return 0;
        } catch (IllegalStateException e2) {
            if (QLog.isColorLevel()) {
                QLog.e(TAG, 2, "StartRecording fail");
            }
            e2.printStackTrace();
            return -1;
        }
    }

    private int StopPlayback() throws IllegalStateException {
        if (QLog.isColorLevel()) {
            QLog.w(TAG, 2, "StopPlayback entry _isPlaying:" + this._isPlaying);
        }
        AudioTrack audioTrack = this._audioTrack;
        if (audioTrack == null) {
            if (QLog.isColorLevel()) {
                QLog.e(TAG, 2, "StopPlayback _isPlaying:" + this._isPlaying + " " + this._audioTrack);
            }
            return -1;
        }
        if (audioTrack.getPlayState() == 3) {
            try {
                if (QLog.isColorLevel()) {
                    QLog.w(TAG, 2, "StopPlayback stoping...");
                }
                this._audioTrack.stop();
                if (QLog.isColorLevel()) {
                    QLog.w(TAG, 2, "StopPlayback flushing... state:" + this._audioTrack.getPlayState());
                }
                this._audioTrack.flush();
            } catch (IllegalStateException unused) {
                if (QLog.isColorLevel()) {
                    QLog.e(TAG, 2, "StopPlayback err");
                }
                return -1;
            }
        }
        if (QLog.isColorLevel()) {
            QLog.w(TAG, 2, "StopPlayback releaseing... state:" + this._audioTrack.getPlayState());
        }
        this._audioTrack.release();
        this._audioTrack = null;
        this._isPlaying = false;
        if (QLog.isColorLevel()) {
            QLog.w(TAG, 2, "StopPlayback exit ok");
        }
        return 0;
    }

    private int StopRecording() throws IllegalStateException {
        if (QLog.isColorLevel()) {
            QLog.w(TAG, 2, "StopRecording entry");
        }
        AudioRecord audioRecord = this._audioRecord;
        if (audioRecord == null) {
            if (QLog.isColorLevel()) {
                QLog.e(TAG, 2, "UnintRecord:" + this._audioRecord);
            }
            return -1;
        }
        if (audioRecord.getRecordingState() == 3) {
            try {
                if (QLog.isColorLevel()) {
                    QLog.w(TAG, 2, "StopRecording stop... state:" + this._audioRecord.getRecordingState());
                }
                this._audioRecord.stop();
            } catch (IllegalStateException unused) {
                if (QLog.isColorLevel()) {
                    QLog.e(TAG, 2, "StopRecording  err");
                }
                return -1;
            }
        }
        if (QLog.isColorLevel()) {
            QLog.w(TAG, 2, "StopRecording releaseing... state:" + this._audioRecord.getRecordingState());
        }
        this._audioRecord.release();
        this._audioRecord = null;
        this._isRecording = false;
        if (QLog.isColorLevel()) {
            QLog.w(TAG, 2, "StopRecording exit ok");
        }
        return 0;
    }

    private int getAndroidSdkVersion() {
        return TXCBuild.VersionInt();
    }

    @TargetApi(16)
    private int getAudioSessionId(AudioRecord audioRecord) {
        return 0;
    }

    private ByteBuffer getDecBuffer(int i2) {
        switch (i2) {
            case 0:
                return this._decBuffer0;
            case 1:
                return this._decBuffer1;
            case 2:
                return this._decBuffer2;
            case 3:
                return this._decBuffer3;
            case 4:
                return this._decBuffer4;
            case 5:
                return this._decBuffer5;
            case 6:
                return this._decBuffer6;
            case 7:
                return this._decBuffer7;
            case 8:
                return this._decBuffer8;
            case 9:
                return this._decBuffer9;
            case 10:
                return this._decBuffer10;
            default:
                QLog.w(TAG, 2, "getDecBuffer failed!! index:" + i2);
                return null;
        }
    }

    private String getDumpFilePath(String str, int i2) {
        File externalFilesDir;
        if (QLog.isColorLevel()) {
            QLog.w(TAG, 2, "manufacture:" + TXCBuild.Manufacturer());
        }
        if (QLog.isColorLevel()) {
            QLog.w(TAG, 2, "MODEL:" + TXCBuild.Model());
        }
        Context context = this._context;
        if (context == null || (externalFilesDir = context.getExternalFilesDir(null)) == null) {
            return null;
        }
        String str2 = externalFilesDir.getPath() + "/MF-" + TXCBuild.Manufacturer() + "-M-" + TXCBuild.Model() + "-as-" + TraeAudioManager.getAudioSource(this._audioSourcePolicy) + "-st-" + this._streamType + "-m-" + i2 + "-" + str;
        File file = new File(str2);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (QLog.isColorLevel()) {
            QLog.w(TAG, 2, "dump:" + str2);
        }
        if (QLog.isColorLevel()) {
            QLog.w(TAG, 2, "dump replace:" + str2.replace(" ", StrPool.UNDERLINE));
        }
        return str2.replace(" ", StrPool.UNDERLINE);
    }

    private int getLowlatencyFramesPerBuffer() {
        if (this._context == null || TXCBuild.VersionInt() < 9) {
            if (QLog.isColorLevel()) {
                QLog.e(TAG, 2, "getLowlatencySamplerate err, _context:" + this._context + " api:" + TXCBuild.VersionInt());
            }
            return 0;
        }
        boolean zHasSystemFeature = this._context.getPackageManager().hasSystemFeature("android.hardware.audio.low_latency");
        if (QLog.isColorLevel()) {
            StringBuilder sb = new StringBuilder();
            sb.append("LOW_LATENCY:");
            sb.append(zHasSystemFeature ? "Y" : "N");
            QLog.w(TAG, 2, sb.toString());
        }
        if (TXCBuild.VersionInt() < 17 && QLog.isColorLevel()) {
            QLog.e(TAG, 2, "API Level too low not support PROPERTY_OUTPUT_SAMPLE_RATE");
        }
        return 0;
    }

    private int getLowlatencySamplerate() {
        if (this._context == null || TXCBuild.VersionInt() < 9) {
            if (QLog.isColorLevel()) {
                QLog.e(TAG, 2, "getLowlatencySamplerate err, _context:" + this._context + " api:" + TXCBuild.VersionInt());
            }
            return 0;
        }
        boolean zHasSystemFeature = this._context.getPackageManager().hasSystemFeature("android.hardware.audio.low_latency");
        if (QLog.isColorLevel()) {
            StringBuilder sb = new StringBuilder();
            sb.append("LOW_LATENCY:");
            sb.append(zHasSystemFeature ? "Y" : "N");
            QLog.w(TAG, 2, sb.toString());
        }
        if (TXCBuild.VersionInt() < 17) {
            if (QLog.isColorLevel()) {
                QLog.e(TAG, 2, "API Level too low not support PROPERTY_OUTPUT_SAMPLE_RATE");
            }
            return 0;
        }
        if (QLog.isColorLevel()) {
            QLog.e(TAG, 2, "getLowlatencySamplerate not support right now!");
        }
        return 0;
    }

    private int getMp3Channels(int i2) {
        TraeAudioCodecList.CodecInfo codecInfoFind = this._traeAudioCodecList.find(i2);
        if (codecInfoFind != null) {
            return codecInfoFind.audioDecoder.getChannels();
        }
        return -1;
    }

    private long getMp3FileTotalMs(int i2) {
        TraeAudioCodecList.CodecInfo codecInfoFind = this._traeAudioCodecList.find(i2);
        if (codecInfoFind != null) {
            return codecInfoFind.audioDecoder.getFileTotalMs();
        }
        return -1L;
    }

    private int getMp3SampleRate(int i2) {
        TraeAudioCodecList.CodecInfo codecInfoFind = this._traeAudioCodecList.find(i2);
        if (codecInfoFind != null) {
            return codecInfoFind.audioDecoder.getSampleRate();
        }
        return -1;
    }

    private int getPlayRecordSysBufferMs() {
        return (this.nRecordLengthMs + this.nPlayLengthMs) * 2;
    }

    public static String getTraceInfo() {
        StringBuffer stringBuffer = new StringBuffer();
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        int length = stackTrace.length;
        stringBuffer.append("");
        stringBuffer.append(stackTrace[2].getClassName());
        stringBuffer.append(StrPool.DOT);
        stringBuffer.append(stackTrace[2].getMethodName());
        stringBuffer.append(": ");
        stringBuffer.append(stackTrace[2].getLineNumber());
        return stringBuffer.toString();
    }

    private void initTRAEAudioManager() {
        Context context = this._context;
        if (context != null) {
            TraeAudioManager.init(context);
            if (QLog.isColorLevel()) {
                QLog.w(TAG, 2, "initTRAEAudioManager , TraeAudioSession create");
            }
            if (this._asAudioManager == null) {
                this._asAudioManager = new TraeAudioSession(this._context, new TraeAudioSession.ITraeAudioCallback() { // from class: com.tencent.rtmp.sharp.jni.AudioDeviceInterface.3
                    @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                    public void onAudioRouteSwitchEnd(String str, long j2) {
                    }

                    @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                    public void onAudioRouteSwitchStart(String str, String str2) {
                    }

                    @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                    public void onConnectDeviceRes(int i2, String str, boolean z2) {
                    }

                    @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                    public void onDeviceChangabledUpdate(boolean z2) {
                    }

                    @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                    public void onDeviceListUpdate(String[] strArr, String str, String str2, String str3) {
                        String[] unused = AudioDeviceInterface.mDeviceList = strArr;
                        if (AudioDeviceInterface.this.usingJava) {
                            AudioDeviceInterface.this.onOutputChanage(str);
                        }
                    }

                    @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                    public void onGetConnectedDeviceRes(int i2, String str) {
                        if (i2 == 0) {
                            AudioDeviceInterface.this.onOutputChanage(str);
                        }
                    }

                    @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                    public void onGetConnectingDeviceRes(int i2, String str) {
                    }

                    @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                    public void onGetDeviceListRes(int i2, String[] strArr, String str, String str2, String str3) {
                        String[] unused = AudioDeviceInterface.mDeviceList = strArr;
                    }

                    @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                    public void onGetStreamTypeRes(int i2, int i3) {
                    }

                    @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                    public void onIsDeviceChangabledRes(int i2, boolean z2) {
                    }

                    @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                    public void onRingCompletion(int i2, String str) {
                    }

                    @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                    public void onServiceStateUpdate(boolean z2) {
                    }

                    @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                    public void onStreamTypeUpdate(int i2) {
                    }

                    @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                    public void onVoicecallPreprocessRes(int i2) {
                    }
                });
            }
            this._asAudioManager.startService(TraeAudioManager.VIDEO_CONFIG);
        }
    }

    public static boolean isHardcodeOpenSles() {
        return TXCBuild.Manufacturer().equals("Xiaomi") ? TXCBuild.Model().equals("MI 5") || TXCBuild.Model().equals("MI 5s") || TXCBuild.Model().equals("MI 5s Plus") : TXCBuild.Manufacturer().equals("samsung") && TXCBuild.Model().equals("SM-G9350");
    }

    private int isSupportLowLatency() {
        if (!isHardcodeOpenSles()) {
            return 0;
        }
        if (!QLog.isColorLevel()) {
            return 1;
        }
        QLog.w(TAG, 2, "hardcode FEATURE_AUDIO_LOW_LATENCY: " + TXCBuild.Manufacturer() + StrPool.UNDERLINE + TXCBuild.Model());
        return 1;
    }

    private int isSupportVivoKTVHelper() {
        Context context = this._context;
        if (context != null) {
            VivoKTVHelper vivoKTVHelper = VivoKTVHelper.getInstance(context);
            mVivoKTVHelper = vivoKTVHelper;
            if (vivoKTVHelper != null) {
                isSupportVivoKTVHelper = vivoKTVHelper.isDeviceSupportKaraoke();
            }
        }
        return isSupportVivoKTVHelper ? 1 : 0;
    }

    private int isVivoKTVLoopback() {
        VivoKTVHelper vivoKTVHelper = mVivoKTVHelper;
        if (vivoKTVHelper == null || !isSupportVivoKTVHelper) {
            return 0;
        }
        return vivoKTVHelper.getPlayFeedbackParam();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onOutputChanage(String str) {
        int i2;
        String str2;
        String str3;
        String str4;
        if (QLog.isColorLevel()) {
            QLog.w(TAG, 2, " onOutputChanage:" + str);
        }
        setAudioRouteSwitchState(str);
        if (!TraeAudioManager.isCloseSystemAPM(this._modePolicy) || (i2 = this._deviceStat) == 1 || i2 == 5 || i2 == 2 || i2 == 3) {
            return;
        }
        this._connectedDev = str;
        if (QLog.isColorLevel()) {
            StringBuilder sb = new StringBuilder();
            sb.append(" onOutputChanage:");
            sb.append(str);
            if (this._audioManager == null) {
                str3 = " am==null";
            } else {
                str3 = " mode:" + this._audioManager.getMode();
            }
            sb.append(str3);
            sb.append(" st:");
            sb.append(this._streamType);
            if (this._audioTrack == null) {
                str4 = "_audioTrack==null";
            } else {
                str4 = " at.st:" + this._audioTrack.getStreamType();
            }
            sb.append(str4);
            QLog.w(TAG, 2, sb.toString());
        }
        try {
            if (this._audioManager == null) {
                this._audioManager = (AudioManager) this._context.getSystemService("audio");
            }
            if (QLog.isColorLevel()) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(" curr mode:");
                sb2.append(str);
                if (this._audioManager == null) {
                    str2 = "am==null";
                } else {
                    str2 = " mode:" + this._audioManager.getMode();
                }
                sb2.append(str2);
                QLog.w(TAG, 2, sb2.toString());
            }
            if (this._audioManager != null && this._connectedDev.equals(TraeAudioManager.DEVICE_SPEAKERPHONE)) {
                this._audioManager.setMode(0);
            }
        } catch (Exception e2) {
            if (QLog.isColorLevel()) {
                QLog.w(TAG, 2, e2.getMessage());
            }
        }
        this._audioRouteChanged = true;
    }

    private void setAudioRouteSwitchState(String str) {
        if (str.equals(TraeAudioManager.DEVICE_EARPHONE)) {
            this.switchState = 1;
            return;
        }
        if (str.equals(TraeAudioManager.DEVICE_SPEAKERPHONE)) {
            this.switchState = 2;
            return;
        }
        if (str.equals(TraeAudioManager.DEVICE_WIREDHEADSET)) {
            this.switchState = 3;
        } else if (str.equals(TraeAudioManager.DEVICE_BLUETOOTHHEADSET)) {
            this.switchState = 4;
        } else {
            this.switchState = 0;
        }
    }

    private int startService(String str) {
        if (QLog.isColorLevel()) {
            QLog.w(TAG, 2, "initTRAEAudioManager , TraeAudioSession startService: " + this._asAudioManager + " deviceConfig:" + str);
        }
        TraeAudioSession traeAudioSession = this._asAudioManager;
        if (traeAudioSession != null) {
            return traeAudioSession.startService(str);
        }
        return -1;
    }

    private int stopService() {
        if (QLog.isColorLevel()) {
            QLog.w(TAG, 2, "initTRAEAudioManager , TraeAudioSession stopService: " + this._asAudioManager);
        }
        TraeAudioSession traeAudioSession = this._asAudioManager;
        if (traeAudioSession != null) {
            return traeAudioSession.stopService();
        }
        return -1;
    }

    private void uninitTRAEAudioManager() {
        if (this._context == null) {
            if (QLog.isColorLevel()) {
                QLog.w(TAG, 2, "uninitTRAEAudioManager , context null");
                return;
            }
            return;
        }
        if (QLog.isColorLevel()) {
            QLog.w(TAG, 2, "uninitTRAEAudioManager , stopService");
        }
        TraeAudioSession traeAudioSession = this._asAudioManager;
        if (traeAudioSession != null) {
            traeAudioSession.stopService();
            this._asAudioManager.release();
            this._asAudioManager = null;
        }
    }

    public int GetPlayoutVolume() {
        Context context;
        try {
            if (this._audioManager == null && (context = this._context) != null) {
                this._audioManager = (AudioManager) context.getSystemService("audio");
            }
            AudioManager audioManager = this._audioManager;
            if (audioManager != null) {
                return audioManager.getStreamVolume(this._streamType);
            }
            return -1;
        } catch (Exception e2) {
            if (!QLog.isColorLevel()) {
                return -1;
            }
            QLog.w("TRAE GetPlayoutVolume", 2, e2.getMessage());
            return -1;
        }
    }

    public int call_postprocess() {
        LogTraceEntry("");
        this.switchState = 0;
        TraeAudioSession traeAudioSession = this._as;
        if (traeAudioSession != null) {
            traeAudioSession.voiceCallPostprocess();
            this._as.release();
            this._as = null;
        }
        TraeAudioManager.IsUpdateSceneFlag = false;
        LogTraceExit();
        return 0;
    }

    public int call_postprocess_media() {
        LogTraceEntry("");
        this.switchState = 0;
        TraeAudioSession traeAudioSession = this._as;
        if (traeAudioSession != null) {
            traeAudioSession.release();
            this._as = null;
        }
        TraeAudioManager.IsUpdateSceneFlag = false;
        VivoKTVHelper vivoKTVHelper = mVivoKTVHelper;
        if (vivoKTVHelper != null && isSupportVivoKTVHelper) {
            vivoKTVHelper.closeKTVDevice();
        }
        LogTraceExit();
        return 0;
    }

    public int call_preprocess() {
        LogTraceEntry("");
        this.switchState = 0;
        this._streamType = TraeAudioManager.getAudioStreamType(this._audioStreamTypePolicy);
        if (this._as == null) {
            this._as = new TraeAudioSession(this._context, new TraeAudioSession.ITraeAudioCallback() { // from class: com.tencent.rtmp.sharp.jni.AudioDeviceInterface.1
                @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                public void onAudioRouteSwitchEnd(String str, long j2) {
                }

                @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                public void onAudioRouteSwitchStart(String str, String str2) {
                }

                @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                public void onConnectDeviceRes(int i2, String str, boolean z2) {
                }

                @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                public void onDeviceChangabledUpdate(boolean z2) {
                }

                @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                public void onDeviceListUpdate(String[] strArr, String str, String str2, String str3) {
                    String[] unused = AudioDeviceInterface.mDeviceList = strArr;
                    if (AudioDeviceInterface.this.usingJava) {
                        AudioDeviceInterface.this.onOutputChanage(str);
                    }
                }

                @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                public void onGetConnectedDeviceRes(int i2, String str) {
                    if (i2 == 0) {
                        AudioDeviceInterface.this.onOutputChanage(str);
                    }
                }

                @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                public void onGetConnectingDeviceRes(int i2, String str) {
                }

                @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                public void onGetDeviceListRes(int i2, String[] strArr, String str, String str2, String str3) {
                    String[] unused = AudioDeviceInterface.mDeviceList = strArr;
                }

                @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                public void onGetStreamTypeRes(int i2, int i3) {
                }

                @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                public void onIsDeviceChangabledRes(int i2, boolean z2) {
                }

                @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                public void onRingCompletion(int i2, String str) {
                }

                @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                public void onServiceStateUpdate(boolean z2) {
                    if (z2) {
                        return;
                    }
                    try {
                        AudioDeviceInterface.this._prelock.lock();
                        AudioDeviceInterface.this._preDone = true;
                        if (QLog.isColorLevel()) {
                            QLog.e(AudioDeviceInterface.TAG, 2, "onServiceStateUpdate signalAll");
                        }
                        AudioDeviceInterface.this._precon.signalAll();
                        AudioDeviceInterface.this._prelock.unlock();
                    } catch (Exception unused) {
                    }
                }

                @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                public void onStreamTypeUpdate(int i2) {
                }

                @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                public void onVoicecallPreprocessRes(int i2) {
                    try {
                        AudioDeviceInterface.this._prelock.lock();
                        AudioDeviceInterface.this._preDone = true;
                        if (QLog.isColorLevel()) {
                            QLog.e(AudioDeviceInterface.TAG, 2, "onVoicecallPreprocessRes signalAll");
                        }
                        AudioDeviceInterface.this._precon.signalAll();
                        AudioDeviceInterface.this._prelock.unlock();
                    } catch (Exception unused) {
                    }
                }
            });
        }
        this._preDone = false;
        if (this._as != null) {
            this._prelock.lock();
            try {
                try {
                    if (this._audioManager == null) {
                        this._audioManager = (AudioManager) this._context.getSystemService("audio");
                    }
                    AudioManager audioManager = this._audioManager;
                    if (audioManager != null) {
                        if (audioManager.getMode() == 2) {
                            int i2 = 5;
                            while (true) {
                                int i3 = i2 - 1;
                                if (i2 <= 0 || this._audioManager.getMode() != 2) {
                                    break;
                                }
                                if (QLog.isColorLevel()) {
                                    QLog.e(TAG, 2, "call_preprocess waiting...  mode:" + this._audioManager.getMode());
                                }
                                Thread.sleep(500L);
                                i2 = i3;
                            }
                        }
                        if (this._audioManager.isMicrophoneMute()) {
                            this._audioManager.setMicrophoneMute(false);
                            if (QLog.isColorLevel()) {
                                QLog.e(TAG, 2, "media call_preprocess setMicrophoneMute false");
                            }
                        }
                    }
                    this._as.voiceCallPreprocess(this._modePolicy, this._streamType);
                    int i4 = 7;
                    while (true) {
                        int i5 = i4 - 1;
                        if (i4 <= 0) {
                            break;
                        }
                        try {
                            if (this._preDone) {
                                break;
                            }
                            this._precon.await(1L, TimeUnit.SECONDS);
                            if (QLog.isColorLevel()) {
                                QLog.e(TAG, 2, "call_preprocess waiting...  as:" + this._as);
                            }
                            i4 = i5;
                        } catch (InterruptedException unused) {
                        }
                    }
                    if (QLog.isColorLevel()) {
                        QLog.e(TAG, 2, "call_preprocess done!");
                    }
                    this._as.getConnectedDevice();
                } catch (InterruptedException unused2) {
                }
            } finally {
                this._prelock.unlock();
            }
        }
        LogTraceExit();
        return 0;
    }

    public int call_preprocess_media() throws InterruptedException {
        LogTraceEntry("");
        this.switchState = 0;
        VivoKTVHelper vivoKTVHelper = mVivoKTVHelper;
        if (vivoKTVHelper != null && isSupportVivoKTVHelper) {
            vivoKTVHelper.openKTVDevice();
            mVivoKTVHelper.setPreModeParam(1);
            mVivoKTVHelper.setPlayFeedbackParam(1);
            mVivoKTVHelper.setPlayFeedbackParam(0);
        }
        if (this._as == null) {
            this._as = new TraeAudioSession(this._context, new TraeAudioSession.ITraeAudioCallback() { // from class: com.tencent.rtmp.sharp.jni.AudioDeviceInterface.2
                @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                public void onAudioRouteSwitchEnd(String str, long j2) {
                }

                @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                public void onAudioRouteSwitchStart(String str, String str2) {
                }

                @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                public void onConnectDeviceRes(int i2, String str, boolean z2) {
                }

                @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                public void onDeviceChangabledUpdate(boolean z2) {
                }

                @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                public void onDeviceListUpdate(String[] strArr, String str, String str2, String str3) {
                    String[] unused = AudioDeviceInterface.mDeviceList = strArr;
                    if (AudioDeviceInterface.this.usingJava) {
                        AudioDeviceInterface.this.onOutputChanage(str);
                    }
                }

                @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                public void onGetConnectedDeviceRes(int i2, String str) {
                    if (i2 == 0) {
                        AudioDeviceInterface.this.onOutputChanage(str);
                    }
                }

                @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                public void onGetConnectingDeviceRes(int i2, String str) {
                }

                @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                public void onGetDeviceListRes(int i2, String[] strArr, String str, String str2, String str3) {
                    String[] unused = AudioDeviceInterface.mDeviceList = strArr;
                }

                @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                public void onGetStreamTypeRes(int i2, int i3) {
                }

                @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                public void onIsDeviceChangabledRes(int i2, boolean z2) {
                }

                @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                public void onRingCompletion(int i2, String str) {
                }

                @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                public void onServiceStateUpdate(boolean z2) {
                }

                @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                public void onStreamTypeUpdate(int i2) {
                }

                @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.ITraeAudioCallback
                public void onVoicecallPreprocessRes(int i2) {
                }
            });
        }
        if (this._as != null) {
            try {
                if (this._audioManager == null) {
                    this._audioManager = (AudioManager) this._context.getSystemService("audio");
                }
                AudioManager audioManager = this._audioManager;
                if (audioManager != null) {
                    if (audioManager.getMode() == 2) {
                        int i2 = 5;
                        while (true) {
                            int i3 = i2 - 1;
                            if (i2 <= 0 || this._audioManager.getMode() != 2) {
                                break;
                            }
                            if (QLog.isColorLevel()) {
                                QLog.e(TAG, 2, "media call_preprocess_media waiting...  mode:" + this._audioManager.getMode());
                            }
                            Thread.sleep(500L);
                            i2 = i3;
                        }
                    }
                    if (this._audioManager.getMode() != 0) {
                        this._audioManager.setMode(0);
                    }
                    if (this._audioManager.isMicrophoneMute()) {
                        this._audioManager.setMicrophoneMute(false);
                        if (QLog.isColorLevel()) {
                            QLog.e(TAG, 2, "media call_preprocess_media setMicrophoneMute false");
                        }
                    }
                }
                this._as.getConnectedDevice();
                if (QLog.isColorLevel()) {
                    QLog.e(TAG, 2, "call_preprocess_media done!");
                }
            } catch (InterruptedException unused) {
            }
        }
        LogTraceExit();
        return 0;
    }

    @SuppressLint({"NewApi"})
    public int checkAACMediaCodecSupported(boolean z2) {
        try {
            if (TXCBuild.VersionInt() >= 21) {
                for (MediaCodecInfo mediaCodecInfo : new MediaCodecList(1).getCodecInfos()) {
                    if (mediaCodecInfo.isEncoder() != z2) {
                        if (mediaCodecInfo.getName().toLowerCase().contains("nvidia")) {
                            return 0;
                        }
                        for (String str : mediaCodecInfo.getSupportedTypes()) {
                            if (str.equalsIgnoreCase(MimeTypes.AUDIO_AAC)) {
                                if (QLog.isColorLevel()) {
                                    QLog.w(TAG, 2, "checkAACSupported support!, " + mediaCodecInfo.getName());
                                }
                                return 1;
                            }
                        }
                    }
                }
            } else {
                int codecCount = MediaCodecList.getCodecCount();
                for (int i2 = 0; i2 < codecCount; i2++) {
                    MediaCodecInfo codecInfoAt = MediaCodecList.getCodecInfoAt(i2);
                    if (codecInfoAt.isEncoder() != z2) {
                        if (codecInfoAt.getName().toLowerCase().contains("nvidia")) {
                            return 0;
                        }
                        for (String str2 : codecInfoAt.getSupportedTypes()) {
                            if (str2.equalsIgnoreCase(MimeTypes.AUDIO_AAC)) {
                                if (QLog.isColorLevel()) {
                                    QLog.w(TAG, 2, "checkAACSupported support!, " + codecInfoAt.getName());
                                }
                                return 1;
                            }
                        }
                    }
                }
            }
        } catch (Exception unused) {
            if (QLog.isColorLevel()) {
                QLog.e(TAG, 2, "check if support aac codec failed.");
            }
        }
        if (QLog.isColorLevel()) {
            QLog.e(TAG, 2, "Error when checking aac codec availability");
        }
        return 0;
    }

    public int checkAACSupported() {
        int iCheckAACMediaCodecSupported = checkAACMediaCodecSupported(false);
        int iCheckAACMediaCodecSupported2 = checkAACMediaCodecSupported(true);
        if (QLog.isColorLevel()) {
            QLog.w(TAG, 2, "checkAACSupported isSupportEncoder: " + iCheckAACMediaCodecSupported + ", isSupportDecoder:" + iCheckAACMediaCodecSupported2);
        }
        return (iCheckAACMediaCodecSupported == 1 && iCheckAACMediaCodecSupported2 == 1) ? 1 : 0;
    }

    public int getAudioRouteSwitchState() {
        return this.switchState;
    }

    public int getMode() {
        Context context;
        try {
            if (this._audioManager == null && (context = this._context) != null) {
                this._audioManager = (AudioManager) context.getSystemService("audio");
            }
            AudioManager audioManager = this._audioManager;
            if (audioManager != null) {
                return audioManager.getMode();
            }
            return -1;
        } catch (Exception e2) {
            if (!QLog.isColorLevel()) {
                return -1;
            }
            QLog.w("TRAE getMode", 2, e2.getMessage());
            return -1;
        }
    }

    public int getNumberOfCPUCores() {
        if (TXCBuild.VersionInt() <= 10) {
            return 1;
        }
        try {
            return new File("/sys/devices/system/cpu/").listFiles(CPU_FILTER).length;
        } catch (NullPointerException | SecurityException unused) {
            return -1;
        }
    }

    public int hasLightSensorManager() {
        Context context = this._context;
        if (context == null) {
            return 1;
        }
        try {
            if (((SensorManager) context.getSystemService(am.ac)).getDefaultSensor(5) != null) {
                if (QLog.isColorLevel()) {
                    QLog.w(TAG, 2, "hasLightSensorManager");
                }
                return 1;
            }
            if (!QLog.isColorLevel()) {
                return 0;
            }
            QLog.w(TAG, 2, "not hasLightSensorManager null == sensor8");
            return 0;
        } catch (Exception e2) {
            if (QLog.isColorLevel()) {
                QLog.w(TAG, 2, e2.getMessage());
            }
            return 1;
        }
    }

    public int isBackground() {
        Context context = this._context;
        if (context == null) {
            return 0;
        }
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME);
            if (activityManager.getRunningTasks(1) == null) {
                if (QLog.isColorLevel()) {
                    QLog.e(TAG, 2, "running task is null, ams is abnormal!!!");
                }
                return 0;
            }
            ActivityManager.RunningTaskInfo runningTaskInfo = activityManager.getRunningTasks(1).get(0);
            if (runningTaskInfo != null && runningTaskInfo.topActivity != null) {
                return runningTaskInfo.topActivity.getPackageName().equals(this._context.getPackageName()) ^ true ? 1 : 0;
            }
            if (QLog.isColorLevel()) {
                QLog.e(TAG, 2, "failed to get RunningTaskInfo");
            }
            return 0;
        } catch (Exception e2) {
            if (QLog.isColorLevel()) {
                QLog.w("TRAE isBackground", 2, e2.getMessage());
            }
            return 0;
        }
    }

    public void setContext(Context context) {
        this._context = context;
    }

    public void setJavaInterface(int i2) {
        if (i2 == 0) {
            this.usingJava = false;
        } else {
            this.usingJava = true;
        }
        if (QLog.isColorLevel()) {
            QLog.w(TAG, 2, "setJavaInterface flg:" + i2);
        }
    }
}
