package org.wrtca.record;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaMetadataRetriever;
import android.text.TextUtils;
import core.interfaces.AudioResample;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
import org.wrtca.record.model.LocalMediaConfig;
import org.wrtca.record.model.MediaObject;
import org.wrtca.record.model.OnlyCompressOverBean;
import org.wrtca.util.FileUtils;

/* loaded from: classes9.dex */
public class LocalMediaCompress extends MediaRecorderBase {
    private final LocalMediaConfig localMediaConfig;
    private final String mNeedCompressVideo;
    private final OnlyCompressOverBean mOnlyCompressOverBean;
    public String scaleWH = "";

    public LocalMediaCompress(LocalMediaConfig localMediaConfig) {
        this.localMediaConfig = localMediaConfig;
        this.compressConfig = localMediaConfig.getCompressConfig();
        MediaRecorderBase.CAPTURE_THUMBNAILS_TIME = localMediaConfig.getCaptureThumbnailsTime();
        if (localMediaConfig.getFrameRate() > 0) {
            setTranscodingFrameRate(localMediaConfig.getFrameRate());
        }
        String videoPath = localMediaConfig.getVideoPath();
        this.mNeedCompressVideo = videoPath;
        OnlyCompressOverBean onlyCompressOverBean = new OnlyCompressOverBean();
        this.mOnlyCompressOverBean = onlyCompressOverBean;
        onlyCompressOverBean.setVideoPath(videoPath);
    }

    private String checkPicRotaing(int i2, String str) {
        return savePhoto(rotaingImageView(i2, BitmapFactory.decodeFile(str)));
    }

    private void correcAttribute(String str, String str2) throws IllegalArgumentException {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(str);
        String strExtractMetadata = mediaMetadataRetriever.extractMetadata(24);
        String strExtractMetadata2 = mediaMetadataRetriever.extractMetadata(18);
        String strExtractMetadata3 = mediaMetadataRetriever.extractMetadata(19);
        if (strExtractMetadata.equals("90") || strExtractMetadata.equals("270")) {
            MediaRecorderBase.SMALL_VIDEO_WIDTH = Integer.valueOf(strExtractMetadata2).intValue();
            MediaRecorderBase.SMALL_VIDEO_HEIGHT = Integer.valueOf(strExtractMetadata3).intValue();
            String strCheckPicRotaing = checkPicRotaing(Integer.valueOf(strExtractMetadata).intValue(), str2);
            if (TextUtils.isEmpty(strCheckPicRotaing)) {
                return;
            }
            this.mOnlyCompressOverBean.setPicPath(strCheckPicRotaing);
            return;
        }
        if (strExtractMetadata.equals("0") || strExtractMetadata.equals("180") || strExtractMetadata.equals("360")) {
            MediaRecorderBase.SMALL_VIDEO_HEIGHT = Integer.valueOf(strExtractMetadata2).intValue();
            MediaRecorderBase.SMALL_VIDEO_WIDTH = Integer.valueOf(strExtractMetadata3).intValue();
        }
    }

    private String getScaleWH(String str, float f2) throws IllegalArgumentException {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(str);
        String strExtractMetadata = mediaMetadataRetriever.extractMetadata(24);
        String strExtractMetadata2 = mediaMetadataRetriever.extractMetadata(18);
        String strExtractMetadata3 = mediaMetadataRetriever.extractMetadata(19);
        int iIntValue = (int) (Integer.valueOf(strExtractMetadata2).intValue() / f2);
        int iIntValue2 = (int) (Integer.valueOf(strExtractMetadata3).intValue() / f2);
        if (iIntValue2 % 2 != 0) {
            iIntValue2++;
        }
        if (iIntValue % 2 != 0) {
            iIntValue++;
        }
        return (strExtractMetadata.equals("90") || strExtractMetadata.equals("270")) ? String.format("%dx%d", Integer.valueOf(iIntValue2), Integer.valueOf(iIntValue)) : (strExtractMetadata.equals("0") || strExtractMetadata.equals("180") || strExtractMetadata.equals("360")) ? String.format("%dx%d", Integer.valueOf(iIntValue), Integer.valueOf(iIntValue2)) : "";
    }

    private Bitmap rotaingImageView(int i2, Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate(i2);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v1, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v4, types: [java.io.FileOutputStream, java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r6v0, types: [android.graphics.Bitmap] */
    private String savePhoto(Bitmap bitmap) throws Throwable {
        FileOutputStream fileOutputStream;
        String str = UUID.randomUUID().toString() + ".jpg";
        ?? outputDirectory = this.mMediaObject.getOutputDirectory();
        File file = new File((String) outputDirectory, str);
        try {
            try {
                fileOutputStream = new FileOutputStream(file);
            } catch (FileNotFoundException e2) {
                e = e2;
                fileOutputStream = null;
            } catch (Throwable th) {
                th = th;
                outputDirectory = 0;
                try {
                    outputDirectory.flush();
                    outputDirectory.close();
                    throw th;
                } catch (IOException e3) {
                    e3.printStackTrace();
                    return null;
                }
            }
            try {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    return file.toString();
                } catch (IOException e4) {
                    e4.printStackTrace();
                    return null;
                }
            } catch (FileNotFoundException e5) {
                e = e5;
                e.printStackTrace();
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    return null;
                } catch (IOException e6) {
                    e6.printStackTrace();
                    return null;
                }
            }
        } catch (Throwable th2) {
            th = th2;
            outputDirectory.flush();
            outputDirectory.close();
            throw th;
        }
    }

    @Override // org.wrtca.record.MediaRecorder
    public void startAudioResample(AudioResample audioResample, FileOutputStream fileOutputStream) {
    }

    public OnlyCompressOverBean startCompress() throws IllegalArgumentException {
        if (TextUtils.isEmpty(this.mNeedCompressVideo)) {
            return this.mOnlyCompressOverBean;
        }
        File file = new File(RtcRecordManager.getVideoCachePath());
        if (!FileUtils.checkFile(file)) {
            file.mkdirs();
        }
        String.valueOf(System.currentTimeMillis());
        MediaObject outputDirectory = setOutputDirectory(RtcRecordManager.getVideoCachePath());
        this.mMediaObject = outputDirectory;
        outputDirectory.setOutputTempVideoPath(this.mNeedCompressVideo);
        float scale = this.localMediaConfig.getScale();
        if (scale > 1.0f) {
            this.scaleWH = getScaleWH(this.mNeedCompressVideo, scale);
        }
        boolean zBooleanValue = doCompress(true).booleanValue();
        this.mOnlyCompressOverBean.setSucceed(zBooleanValue);
        if (zBooleanValue) {
            this.mOnlyCompressOverBean.setVideoPath(this.mMediaObject.getOutputTempTranscodingVideoPath());
            this.mOnlyCompressOverBean.setPicPath(this.mMediaObject.getOutputVideoThumbPath());
            correcAttribute(this.mMediaObject.getOutputTempTranscodingVideoPath(), this.mMediaObject.getOutputVideoThumbPath());
        }
        return this.mOnlyCompressOverBean;
    }

    @Override // org.wrtca.record.MediaRecorder
    public MediaObject.MediaPart startRecord(int i2, String str, long j2) {
        return null;
    }

    @Override // org.wrtca.record.MediaRecorder
    public void stopAudioResample() {
    }

    @Override // org.wrtca.record.MediaRecorderBase
    public String getScaleWH() {
        return this.scaleWH;
    }
}
