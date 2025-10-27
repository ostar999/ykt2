package com.hyphenate.easeui.manager;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.format.Time;
import com.hyphenate.chat.EMClient;
import com.hyphenate.util.EMLog;
import com.hyphenate.util.PathUtil;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/* loaded from: classes4.dex */
public class EaseVoiceRecorder {
    static final String EXTENSION = ".amr";
    static final String PREFIX = "voice";
    private File file;
    private Handler handler;
    MediaRecorder recorder;
    private long startTime;
    private boolean isRecording = false;
    private String voiceFilePath = null;
    private String voiceFileName = null;

    public EaseVoiceRecorder(Handler handler) {
        this.handler = handler;
    }

    private String getVoiceFileName(String str) {
        Time time = new Time();
        time.setToNow();
        return str + time.toString().substring(0, 15) + ".amr";
    }

    public void discardRecording() throws IllegalStateException {
        MediaRecorder mediaRecorder = this.recorder;
        if (mediaRecorder != null) {
            try {
                mediaRecorder.stop();
                this.recorder.release();
                this.recorder = null;
                File file = this.file;
                if (file != null && file.exists() && !this.file.isDirectory()) {
                    this.file.delete();
                }
            } catch (IllegalStateException | RuntimeException unused) {
            }
            this.isRecording = false;
        }
    }

    public void finalize() throws Throwable {
        super.finalize();
        MediaRecorder mediaRecorder = this.recorder;
        if (mediaRecorder != null) {
            mediaRecorder.release();
        }
    }

    public String getVoiceFilePath() {
        return this.voiceFilePath;
    }

    public boolean isRecording() {
        return this.isRecording;
    }

    public String startRecording(Context context) throws IllegalStateException, IOException {
        this.file = null;
        try {
            MediaRecorder mediaRecorder = this.recorder;
            if (mediaRecorder != null) {
                mediaRecorder.release();
                this.recorder = null;
            }
            MediaRecorder mediaRecorder2 = new MediaRecorder();
            this.recorder = mediaRecorder2;
            mediaRecorder2.setAudioSource(1);
            this.recorder.setOutputFormat(3);
            this.recorder.setAudioEncoder(1);
            this.recorder.setAudioChannels(1);
            this.recorder.setAudioSamplingRate(8000);
            this.recorder.setAudioEncodingBitRate(64);
            this.voiceFileName = getVoiceFileName(EMClient.getInstance().getCurrentUser());
            this.voiceFilePath = PathUtil.getInstance().getVoicePath() + "/" + this.voiceFileName;
            File file = new File(this.voiceFilePath);
            this.file = file;
            this.recorder.setOutputFile(file.getAbsolutePath());
            this.recorder.prepare();
            this.isRecording = true;
            this.recorder.start();
        } catch (IOException unused) {
            EMLog.e("voice", "prepare() failed");
        }
        new Thread(new Runnable() { // from class: com.hyphenate.easeui.manager.EaseVoiceRecorder.1
            @Override // java.lang.Runnable
            public void run() {
                while (EaseVoiceRecorder.this.isRecording) {
                    try {
                        Message message = new Message();
                        message.what = (EaseVoiceRecorder.this.recorder.getMaxAmplitude() * 13) / 32767;
                        EaseVoiceRecorder.this.handler.sendMessage(message);
                        SystemClock.sleep(100L);
                    } catch (Exception e2) {
                        EMLog.e("voice", e2.toString());
                        return;
                    }
                }
            }
        }).start();
        this.startTime = new Date().getTime();
        EMLog.d("voice", "start voice recording to file:" + this.file.getAbsolutePath());
        File file2 = this.file;
        if (file2 == null) {
            return null;
        }
        return file2.getAbsolutePath();
    }

    public int stopRecoding() throws IllegalStateException {
        MediaRecorder mediaRecorder = this.recorder;
        int i2 = 0;
        if (mediaRecorder != null) {
            this.isRecording = false;
            mediaRecorder.stop();
            this.recorder.release();
            this.recorder = null;
            File file = this.file;
            i2 = 401;
            if (file != null && file.exists() && this.file.isFile()) {
                if (this.file.length() == 0) {
                    this.file.delete();
                    return 401;
                }
                int time = ((int) (new Date().getTime() - this.startTime)) / 1000;
                EMLog.d("voice", "voice recording finished. seconds:" + time + " file length:" + this.file.length());
                return time;
            }
        }
        return i2;
    }

    public String getVoiceFileName() {
        return this.voiceFileName;
    }
}
