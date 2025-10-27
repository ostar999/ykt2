package org.wrtca.record;

import android.text.TextUtils;
import c.h;
import core.interfaces.AudioResample;
import core.interfaces.RecordListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.wrtca.record.model.MediaObject;
import org.wrtca.video.RtcFFmpegBridge;

/* loaded from: classes9.dex */
public class RtcRecordManager {
    public static final String FFMPEG_LOG_FILENAME_TEMP = "jx_ffmpeg.log";
    private static final String TAG = "RTCRecordManager";
    private static RtcRecordManager instance;
    private static int mAppVersionCode;
    private static String mAppVersionName;
    private static String mPackageName;
    private static String mVideoCachePath;
    private boolean mDebug;
    private String mLogPath;
    private MediaObject mMediaObject;
    private MediaRecorderBase mMediaRecorder;
    private String mPcmPath;

    public RtcRecordManager(boolean z2, String str, String str2) {
        this.mDebug = z2;
        this.mLogPath = str;
        initialize(z2, str, str2);
    }

    public static RtcRecordManager getInstance() {
        return instance;
    }

    public static String getVideoCachePath() {
        return mVideoCachePath;
    }

    public static synchronized void init(String str) {
        if (instance == null) {
            instance = new RtcRecordManager(false, "", str);
        }
    }

    public static void setVideoCachePath(String str) {
        if (TextUtils.isEmpty(str)) {
            mVideoCachePath = "";
            return;
        }
        File file = new File(str);
        if (!file.exists()) {
            file.mkdirs();
        }
        mVideoCachePath = str;
    }

    public void changeDirectory(String str) {
        setVideoCachePath(str);
        this.mMediaObject = this.mMediaRecorder.setOutputDirectory(str);
        h.a(TAG, "mediaObeject create: " + this.mMediaObject);
    }

    public void initialize(boolean z2, String str, String str2) {
        this.mMediaRecorder = new MediaRecorderNative();
        setVideoCachePath(str2);
        this.mMediaObject = this.mMediaRecorder.setOutputDirectory(str2);
        h.a(TAG, "mediaObeject create: " + this.mMediaObject);
        RtcFFmpegBridge.nativeInitFFmpeg(this.mDebug, this.mLogPath);
    }

    public void startAudioResample(AudioResample audioResample, String str) throws IOException {
        FileOutputStream fileOutputStream;
        boolean zCreateNewFile = false;
        if (!TextUtils.isEmpty(str)) {
            this.mPcmPath = str;
            File file = new File(this.mPcmPath);
            if (file.exists()) {
                zCreateNewFile = true;
            } else {
                try {
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdir();
                    }
                    zCreateNewFile = file.createNewFile();
                    h.a(TAG, "audioPcm create: " + zCreateNewFile);
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        }
        if (zCreateNewFile) {
            try {
                fileOutputStream = new FileOutputStream(this.mPcmPath);
            } catch (FileNotFoundException e3) {
                e3.printStackTrace();
            }
        } else {
            fileOutputStream = null;
        }
        this.mMediaRecorder.startAudioResample(audioResample, fileOutputStream);
    }

    public void startRecord(int i2, String str, RecordListener recordListener, long j2) {
        this.mMediaRecorder.setRecordListener(recordListener);
        this.mMediaRecorder.startRecord(i2, str, j2);
    }

    public void stopAudioResample() {
        this.mPcmPath = null;
        this.mMediaRecorder.stopAudioResample();
    }

    public void stopRecord() {
        this.mMediaRecorder.stopRecord();
    }
}
