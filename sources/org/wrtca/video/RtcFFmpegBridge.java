package org.wrtca.video;

import c.h;
import com.aliyun.vod.log.core.AliyunLogCommon;
import core.interfaces.AudioResample;
import core.interfaces.RecordListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import org.wrtca.jni.CalledByNative;
import org.wrtca.jni.JNINamespace;

@JNINamespace("webrtc::jni")
/* loaded from: classes9.dex */
public class RtcFFmpegBridge {
    public static final int ALL_RECORD_END = 1;
    public static final int ROTATE_0_CROP_LF = 0;
    public static final int ROTATE_180 = 2;
    public static final int ROTATE_270_CROP_LT_MIRROR_LR = 3;
    public static final int ROTATE_90_CROP_LT = 1;
    private static final String TAG = "RtcFFmpegBridge";
    public static long period = 2000;
    private static ArrayList<FFmpegStateListener> listeners = new ArrayList<>();
    private static ArrayList<RecordListener> recordListeners = new ArrayList<>();
    private static ArrayList<AudioResample> resampleListeners = new ArrayList<>();
    private static Map<RecordListener, Timer> timers = new HashMap();
    private static FileOutputStream mPcmOutputStream = null;

    public interface FFmpegStateListener {
        void allRecordEnd();
    }

    @CalledByNative
    public static void audioResample(byte[] bArr, int i2, int i3, int i4, int i5) throws IOException {
        if (bArr != null) {
            if (!resampleListeners.isEmpty()) {
                try {
                    FileOutputStream fileOutputStream = mPcmOutputStream;
                    if (fileOutputStream != null) {
                        fileOutputStream.write(bArr);
                    }
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            Iterator<AudioResample> it = resampleListeners.iterator();
            while (it.hasNext()) {
                AudioResample next = it.next();
                if (next != null) {
                    next.onAudioRecordCallback(bArr, i2, i3, i4, i5);
                }
            }
        }
    }

    public static void clearRTCAudioResampleListener() {
        resampleListeners.clear();
    }

    public static void flushPcmFileAndClearStream() throws IOException {
        try {
            try {
                FileOutputStream fileOutputStream = mPcmOutputStream;
                if (fileOutputStream != null) {
                    fileOutputStream.flush();
                    h.a(TAG, "RtcFFmpegBridgesave pcm finish ");
                }
                try {
                    FileOutputStream fileOutputStream2 = mPcmOutputStream;
                    if (fileOutputStream2 != null) {
                        fileOutputStream2.close();
                        mPcmOutputStream = null;
                    }
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            } catch (Throwable th) {
                try {
                    FileOutputStream fileOutputStream3 = mPcmOutputStream;
                    if (fileOutputStream3 != null) {
                        fileOutputStream3.close();
                        mPcmOutputStream = null;
                    }
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
                throw th;
            }
        } catch (IOException e4) {
            e4.printStackTrace();
            try {
                FileOutputStream fileOutputStream4 = mPcmOutputStream;
                if (fileOutputStream4 != null) {
                    fileOutputStream4.close();
                    mPcmOutputStream = null;
                }
            } catch (IOException e5) {
                e5.printStackTrace();
            }
        }
    }

    public static native int nativeEncodeFrame2AAC(byte[] bArr);

    public static native int nativeEncodeFrame2H264(byte[] bArr);

    public static native int nativeGetDuration();

    public static native void nativeInitFFmpeg(boolean z2, String str);

    public static native int nativePrepareFFmpegEncoder(String str, String str2, int i2, int i3, int i4, int i5, int i6, int i7, long j2);

    public static native int nativeRecordEnd();

    public static native void nativeRelease();

    public static native void nativeSwitchAudioResample(boolean z2);

    public static synchronized void notifyState(int i2, float f2) {
        Iterator<FFmpegStateListener> it = listeners.iterator();
        while (it.hasNext()) {
            FFmpegStateListener next = it.next();
            if (next != null && i2 == 1) {
                next.allRecordEnd();
            }
        }
    }

    public static void registerFFmpegStateListener(FFmpegStateListener fFmpegStateListener) {
        if (listeners.contains(fFmpegStateListener)) {
            return;
        }
        listeners.add(fFmpegStateListener);
    }

    public static void registerRTCAudioResampleListener(AudioResample audioResample) {
        if (resampleListeners.contains(audioResample)) {
            return;
        }
        resampleListeners.add(audioResample);
    }

    public static void registerRTCRecordListener(RecordListener recordListener) {
        if (recordListeners.contains(recordListener)) {
            return;
        }
        recordListeners.add(recordListener);
        timers.put(recordListener, new Timer(AliyunLogCommon.SubModule.RECORD + recordListener));
    }

    @CalledByNative
    public static void reportError(int i2) {
        h.a(TAG, "recordFailed for : " + i2);
    }

    public static void setPcmOutputStream(FileOutputStream fileOutputStream) {
        mPcmOutputStream = fileOutputStream;
    }

    @CalledByNative
    public static void startRecordResult(final String str, int i2, String str2) {
        h.a(TAG, "bridge start record: " + str + "code: " + i2 + "msg " + str2);
        Iterator<RecordListener> it = recordListeners.iterator();
        while (it.hasNext()) {
            final RecordListener next = it.next();
            if (next != null) {
                next.onLocalRecordStart(str, i2, str2);
                if (timers.get(next) != null) {
                    timers.get(next).schedule(new TimerTask() { // from class: org.wrtca.video.RtcFFmpegBridge.1
                        @Override // java.util.TimerTask, java.lang.Runnable
                        public void run() {
                            next.onRecordStatusCallBack(RtcFFmpegBridge.nativeGetDuration(), new File(str).length());
                        }
                    }, 0L, period);
                }
            }
        }
    }

    @CalledByNative
    public static void stopRecord(String str, int i2) {
        h.a(TAG, "bridge receive record stop: " + str);
        Iterator<RecordListener> it = recordListeners.iterator();
        while (it.hasNext()) {
            RecordListener next = it.next();
            if (next != null) {
                File file = new File(str);
                if (file.exists()) {
                    next.onLocalRecordStop(str, file.length(), i2);
                    timers.get(next).cancel();
                }
            }
        }
        recordListeners.clear();
        timers.clear();
    }

    public static void unregisterFFmpegStateListener(FFmpegStateListener fFmpegStateListener) {
        if (listeners.contains(fFmpegStateListener)) {
            listeners.remove(fFmpegStateListener);
        }
    }

    public static void unregisterRTCAudioResampleListener(AudioResample audioResample) {
        if (resampleListeners.contains(audioResample)) {
            resampleListeners.remove(audioResample);
        }
    }

    public static void unregisterRTCRecordListener(RecordListener recordListener) {
        if (recordListeners.contains(recordListener)) {
            recordListeners.remove(recordListener);
            timers.remove(recordListener);
        }
    }
}
