package org.wrtca.record.model;

import cn.hutool.core.text.StrPool;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import org.wrtca.util.FileUtils;
import org.wrtca.util.StringUtils;

/* loaded from: classes9.dex */
public class MediaObject implements Serializable {
    public static final int DEFAULT_MAX_DURATION = 10000;
    public static final int DEFAULT_VIDEO_BITRATE = 800;
    public static final int MEDIA_PART_TYPE_IMPORT_IMAGE = 2;
    public static final int MEDIA_PART_TYPE_IMPORT_VIDEO = 1;
    public static final int MEDIA_PART_TYPE_RECORD = 0;
    public static final int MEDIA_PART_TYPE_RECORD_MP4 = 3;
    private volatile transient MediaPart mCurrentPart;
    private String mKey;
    private int mMaxDuration;
    private LinkedList<MediaPart> mMediaList;
    private String mOutputDirectory;
    private String mOutputObjectPath;
    private String mOutputVideoPath;
    private String mOutputVideoThumbPath;
    public MediaThemeObject mThemeObject;
    private int mVideoBitrate;
    private String outputTempVideoPath;

    public static class MediaPart implements Serializable {
        public String audioPath;
        public int cameraId;
        public int cutEndTime;
        public int cutStartTime;
        public int duration;
        public transient long endTime;
        public int index;
        public transient FileOutputStream mCurrentOutputAudio;
        public transient FileOutputStream mCurrentOutputVideo;
        public String mediaPath;
        public int position;
        public volatile transient boolean recording;
        public transient boolean remove;
        public transient long startTime;
        public String tempAudioPath;
        public String tempMediaPath;
        public String tempPath;
        public String thumbPath;
        public int yuvHeight;
        public int yuvWidth;
        public int type = 0;
        public int speed = 10;

        public void delete() {
            FileUtils.deleteFile(this.mediaPath);
            FileUtils.deleteFile(this.audioPath);
            FileUtils.deleteFile(this.thumbPath);
            FileUtils.deleteFile(this.tempMediaPath);
            FileUtils.deleteFile(this.tempAudioPath);
        }

        public int getDuration() {
            int i2 = this.duration;
            return i2 > 0 ? i2 : (int) (System.currentTimeMillis() - this.startTime);
        }

        public void prepare() {
            try {
                this.mCurrentOutputVideo = new FileOutputStream(this.mediaPath);
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            prepareAudio();
        }

        public void prepareAudio() {
            try {
                this.mCurrentOutputAudio = new FileOutputStream(this.audioPath);
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }

        public void stop() throws IOException {
            FileOutputStream fileOutputStream = this.mCurrentOutputVideo;
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.flush();
                    this.mCurrentOutputVideo.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                this.mCurrentOutputVideo = null;
            }
            FileOutputStream fileOutputStream2 = this.mCurrentOutputAudio;
            if (fileOutputStream2 != null) {
                try {
                    fileOutputStream2.flush();
                    this.mCurrentOutputAudio.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
                this.mCurrentOutputAudio = null;
            }
        }

        public void writeAudioData(byte[] bArr) throws IOException {
            FileOutputStream fileOutputStream = this.mCurrentOutputAudio;
            if (fileOutputStream != null) {
                fileOutputStream.write(bArr);
            }
        }

        public void writeVideoData(byte[] bArr) throws IOException {
            FileOutputStream fileOutputStream = this.mCurrentOutputVideo;
            if (fileOutputStream != null) {
                fileOutputStream.write(bArr);
            }
        }
    }

    public MediaObject(String str) {
        this(str, 800);
    }

    public static void preparedMediaObject(MediaObject mediaObject) {
        LinkedList<MediaPart> linkedList;
        if (mediaObject == null || (linkedList = mediaObject.mMediaList) == null) {
            return;
        }
        Iterator<MediaPart> it = linkedList.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            MediaPart next = it.next();
            long j2 = i2;
            next.startTime = j2;
            int i3 = next.duration;
            next.endTime = j2 + i3;
            i2 += i3;
        }
    }

    public MediaPart buildMediaPart(int i2) {
        this.mCurrentPart = new MediaPart();
        this.mCurrentPart.position = getDuration();
        this.mCurrentPart.index = this.mMediaList.size();
        MediaPart mediaPart = this.mCurrentPart;
        StringBuilder sb = new StringBuilder();
        sb.append(this.mOutputDirectory);
        String str = File.separator;
        sb.append(str);
        sb.append(this.mCurrentPart.index);
        sb.append(".v");
        mediaPart.mediaPath = sb.toString();
        this.mCurrentPart.audioPath = this.mOutputDirectory + str + this.mCurrentPart.index + ".a";
        this.mCurrentPart.thumbPath = this.mOutputDirectory + str + this.mCurrentPart.index + ".jpg";
        this.mCurrentPart.cameraId = i2;
        this.mCurrentPart.prepare();
        this.mCurrentPart.recording = true;
        this.mCurrentPart.startTime = System.currentTimeMillis();
        this.mCurrentPart.type = 1;
        this.mMediaList.add(this.mCurrentPart);
        return this.mCurrentPart;
    }

    public void cleanTheme() {
        this.mThemeObject = null;
        LinkedList<MediaPart> linkedList = this.mMediaList;
        if (linkedList != null) {
            Iterator<MediaPart> it = linkedList.iterator();
            while (it.hasNext()) {
                MediaPart next = it.next();
                next.cutStartTime = 0;
                next.cutEndTime = next.duration;
            }
        }
    }

    public void delete() throws IOException {
        LinkedList<MediaPart> linkedList = this.mMediaList;
        if (linkedList != null) {
            Iterator<MediaPart> it = linkedList.iterator();
            while (it.hasNext()) {
                it.next().stop();
            }
        }
        FileUtils.deleteDir(this.mOutputDirectory);
    }

    public String getConcatPCM() {
        StringBuilder sb = new StringBuilder();
        LinkedList<MediaPart> linkedList = this.mMediaList;
        if (linkedList != null && linkedList.size() > 0) {
            int i2 = 0;
            if (this.mMediaList.size() != 1) {
                sb.append("concat:");
                int size = this.mMediaList.size();
                while (i2 < size) {
                    MediaPart mediaPart = this.mMediaList.get(i2);
                    if (StringUtils.isEmpty(mediaPart.tempAudioPath)) {
                        sb.append(mediaPart.audioPath);
                    } else {
                        sb.append(mediaPart.tempAudioPath);
                    }
                    i2++;
                    if (i2 < size) {
                        sb.append(HiAnalyticsConstant.REPORT_VAL_SEPARATOR);
                    }
                }
            } else if (StringUtils.isEmpty(this.mMediaList.get(0).tempAudioPath)) {
                sb.append(this.mMediaList.get(0).audioPath);
            } else {
                sb.append(this.mMediaList.get(0).tempAudioPath);
            }
        }
        return sb.toString();
    }

    public String getConcatYUV() {
        StringBuilder sb = new StringBuilder();
        LinkedList<MediaPart> linkedList = this.mMediaList;
        if (linkedList != null && linkedList.size() > 0) {
            int i2 = 0;
            if (this.mMediaList.size() != 1) {
                sb.append("concat:");
                int size = this.mMediaList.size();
                while (i2 < size) {
                    MediaPart mediaPart = this.mMediaList.get(i2);
                    if (StringUtils.isEmpty(mediaPart.tempMediaPath)) {
                        sb.append(mediaPart.mediaPath);
                    } else {
                        sb.append(mediaPart.tempMediaPath);
                    }
                    i2++;
                    if (i2 < size) {
                        sb.append(HiAnalyticsConstant.REPORT_VAL_SEPARATOR);
                    }
                }
            } else if (StringUtils.isEmpty(this.mMediaList.get(0).tempMediaPath)) {
                sb.append(this.mMediaList.get(0).mediaPath);
            } else {
                sb.append(this.mMediaList.get(0).tempMediaPath);
            }
        }
        return sb.toString();
    }

    public int getCurrentIndex() {
        MediaPart currentPart = getCurrentPart();
        if (currentPart != null) {
            return currentPart.index;
        }
        return 0;
    }

    public MediaPart getCurrentPart() {
        if (this.mCurrentPart != null) {
            return this.mCurrentPart;
        }
        LinkedList<MediaPart> linkedList = this.mMediaList;
        if (linkedList != null && linkedList.size() > 0) {
            this.mCurrentPart = this.mMediaList.get(r0.size() - 1);
        }
        return this.mCurrentPart;
    }

    public int getCutDuration() {
        LinkedList<MediaPart> linkedList = this.mMediaList;
        int i2 = 0;
        if (linkedList != null) {
            Iterator<MediaPart> it = linkedList.iterator();
            while (it.hasNext()) {
                MediaPart next = it.next();
                int i3 = next.cutEndTime - next.cutStartTime;
                int i4 = next.speed;
                if (i4 != 10) {
                    i3 = (int) (i3 * (10.0f / i4));
                }
                i2 += i3;
            }
        }
        return i2;
    }

    public int getDuration() {
        LinkedList<MediaPart> linkedList = this.mMediaList;
        int duration = 0;
        if (linkedList != null) {
            Iterator<MediaPart> it = linkedList.iterator();
            while (it.hasNext()) {
                duration += it.next().getDuration();
            }
        }
        return duration;
    }

    public String getKeyName() {
        return this.mKey;
    }

    public int getMaxDuration() {
        return this.mMaxDuration;
    }

    public LinkedList<MediaPart> getMedaParts() {
        return this.mMediaList;
    }

    public String getObjectFilePath() {
        if (StringUtils.isEmpty(this.mOutputObjectPath)) {
            this.mOutputObjectPath = this.mOutputDirectory + File.separator + new File(this.mOutputVideoPath).getName() + ".obj";
        }
        return this.mOutputObjectPath;
    }

    public String getOutputDirectory() {
        return this.mOutputDirectory;
    }

    public String getOutputTempTranscodingVideoPath() {
        return this.mOutputDirectory + File.separator + this.mKey + ".mp4";
    }

    public String getOutputTempVideoPath() {
        return this.outputTempVideoPath;
    }

    public String getOutputVideoPath() {
        return this.mOutputVideoPath;
    }

    public String getOutputVideoThumbPath() {
        return this.mOutputVideoThumbPath;
    }

    public MediaPart getPart(int i2) {
        if (this.mCurrentPart == null || i2 >= this.mMediaList.size()) {
            return null;
        }
        return this.mMediaList.get(i2);
    }

    public int getVideoBitrate() {
        return this.mVideoBitrate;
    }

    public void removePart(MediaPart mediaPart, boolean z2) throws IOException {
        LinkedList<MediaPart> linkedList = this.mMediaList;
        if (linkedList != null) {
            linkedList.remove(mediaPart);
        }
        if (mediaPart != null) {
            mediaPart.stop();
            if (z2) {
                mediaPart.delete();
            }
            this.mMediaList.remove(mediaPart);
            if (this.mCurrentPart == null || !mediaPart.equals(this.mCurrentPart)) {
                return;
            }
            this.mCurrentPart = null;
        }
    }

    public void setKey(String str) {
        this.mKey = str;
    }

    public void setMaxDuration(int i2) {
        if (i2 >= 1000) {
            this.mMaxDuration = i2;
        }
    }

    public void setOutputDirectory(String str) {
        this.mOutputDirectory = str;
    }

    public void setOutputTempVideoPath(String str) {
        this.outputTempVideoPath = str;
    }

    public void setOutputVideoPath(String str) {
        this.mOutputVideoPath = str;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.mMediaList != null) {
            stringBuffer.append(StrPool.BRACKET_START + this.mMediaList.size() + StrPool.BRACKET_END);
            Iterator<MediaPart> it = this.mMediaList.iterator();
            while (it.hasNext()) {
                MediaPart next = it.next();
                stringBuffer.append(next.mediaPath + ":" + next.duration + "\n");
            }
        }
        return stringBuffer.toString();
    }

    public MediaObject(String str, int i2) {
        this.mMediaList = new LinkedList<>();
        this.mOutputDirectory = str;
        this.mVideoBitrate = i2;
        StringBuilder sb = new StringBuilder();
        sb.append(this.mOutputDirectory);
        String str2 = File.separator;
        sb.append(str2);
        sb.append(this.mKey);
        sb.append(".obj");
        this.mOutputObjectPath = sb.toString();
        this.mOutputVideoPath = this.mOutputDirectory + ".mp4";
        this.mOutputVideoThumbPath = this.mOutputDirectory + str2 + this.mKey + ".jpg";
        this.mMaxDuration = 10000;
        this.outputTempVideoPath = this.mOutputDirectory + str2 + this.mKey + "_temp.mp4";
    }

    public MediaPart buildMediaPart(int i2, String str) {
        this.mCurrentPart = new MediaPart();
        this.mCurrentPart.position = getDuration();
        this.mCurrentPart.index = this.mMediaList.size();
        MediaPart mediaPart = this.mCurrentPart;
        StringBuilder sb = new StringBuilder();
        sb.append(this.mOutputDirectory);
        String str2 = File.separator;
        sb.append(str2);
        sb.append(this.mCurrentPart.index);
        sb.append(str);
        mediaPart.mediaPath = sb.toString();
        this.mCurrentPart.audioPath = this.mOutputDirectory + str2 + this.mCurrentPart.index + ".a";
        this.mCurrentPart.thumbPath = this.mOutputDirectory + str2 + this.mCurrentPart.index + ".jpg";
        this.mCurrentPart.recording = true;
        this.mCurrentPart.cameraId = i2;
        this.mCurrentPart.startTime = System.currentTimeMillis();
        this.mCurrentPart.type = 1;
        this.mMediaList.add(this.mCurrentPart);
        return this.mCurrentPart;
    }

    public MediaPart buildMediaPart(String str, int i2, int i3) {
        this.mCurrentPart = new MediaPart();
        this.mCurrentPart.position = getDuration();
        this.mCurrentPart.index = this.mMediaList.size();
        MediaPart mediaPart = this.mCurrentPart;
        StringBuilder sb = new StringBuilder();
        sb.append(this.mOutputDirectory);
        String str2 = File.separator;
        sb.append(str2);
        sb.append(this.mCurrentPart.index);
        sb.append(".v");
        mediaPart.mediaPath = sb.toString();
        this.mCurrentPart.audioPath = this.mOutputDirectory + str2 + this.mCurrentPart.index + ".a";
        this.mCurrentPart.thumbPath = this.mOutputDirectory + str2 + this.mCurrentPart.index + ".jpg";
        this.mCurrentPart.duration = i2;
        this.mCurrentPart.startTime = 0L;
        this.mCurrentPart.endTime = (long) i2;
        this.mCurrentPart.cutStartTime = 0;
        this.mCurrentPart.cutEndTime = i2;
        this.mCurrentPart.tempPath = str;
        this.mCurrentPart.type = i3;
        this.mMediaList.add(this.mCurrentPart);
        return this.mCurrentPart;
    }
}
