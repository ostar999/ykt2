package com.google.android.exoplayer2.util;

import android.net.Uri;
import androidx.annotation.VisibleForTesting;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public final class FileTypes {
    public static final int AC3 = 0;
    public static final int AC4 = 1;
    public static final int ADTS = 2;
    public static final int AMR = 3;
    private static final String EXTENSION_AAC = ".aac";
    private static final String EXTENSION_AC3 = ".ac3";
    private static final String EXTENSION_AC4 = ".ac4";
    private static final String EXTENSION_ADTS = ".adts";
    private static final String EXTENSION_AMR = ".amr";
    private static final String EXTENSION_EC3 = ".ec3";
    private static final String EXTENSION_FLAC = ".flac";
    private static final String EXTENSION_FLV = ".flv";
    private static final String EXTENSION_JPEG = ".jpeg";
    private static final String EXTENSION_JPG = ".jpg";
    private static final String EXTENSION_M2P = ".m2p";
    private static final String EXTENSION_MP3 = ".mp3";
    private static final String EXTENSION_MP4 = ".mp4";
    private static final String EXTENSION_MPEG = ".mpeg";
    private static final String EXTENSION_MPG = ".mpg";
    private static final String EXTENSION_OPUS = ".opus";
    private static final String EXTENSION_PREFIX_CMF = ".cmf";
    private static final String EXTENSION_PREFIX_M4 = ".m4";
    private static final String EXTENSION_PREFIX_MK = ".mk";
    private static final String EXTENSION_PREFIX_MP4 = ".mp4";
    private static final String EXTENSION_PREFIX_OG = ".og";
    private static final String EXTENSION_PREFIX_TS = ".ts";
    private static final String EXTENSION_PS = ".ps";
    private static final String EXTENSION_TS = ".ts";
    private static final String EXTENSION_VTT = ".vtt";
    private static final String EXTENSION_WAV = ".wav";
    private static final String EXTENSION_WAVE = ".wave";
    private static final String EXTENSION_WEBM = ".webm";
    private static final String EXTENSION_WEBVTT = ".webvtt";
    public static final int FLAC = 4;
    public static final int FLV = 5;

    @VisibleForTesting
    static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final int JPEG = 14;
    public static final int MATROSKA = 6;
    public static final int MP3 = 7;
    public static final int MP4 = 8;
    public static final int OGG = 9;
    public static final int PS = 10;
    public static final int TS = 11;
    public static final int UNKNOWN = -1;
    public static final int WAV = 12;
    public static final int WEBVTT = 13;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    private FileTypes() {
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0028  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int inferFileTypeFromMimeType(@androidx.annotation.Nullable java.lang.String r17) {
        /*
            Method dump skipped, instructions count: 516
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.util.FileTypes.inferFileTypeFromMimeType(java.lang.String):int");
    }

    public static int inferFileTypeFromResponseHeaders(Map<String, List<String>> map) {
        List<String> list = map.get("Content-Type");
        return inferFileTypeFromMimeType((list == null || list.isEmpty()) ? null : list.get(0));
    }

    public static int inferFileTypeFromUri(Uri uri) {
        String lastPathSegment = uri.getLastPathSegment();
        if (lastPathSegment == null) {
            return -1;
        }
        if (lastPathSegment.endsWith(EXTENSION_AC3) || lastPathSegment.endsWith(EXTENSION_EC3)) {
            return 0;
        }
        if (lastPathSegment.endsWith(EXTENSION_AC4)) {
            return 1;
        }
        if (lastPathSegment.endsWith(EXTENSION_ADTS) || lastPathSegment.endsWith(EXTENSION_AAC)) {
            return 2;
        }
        if (lastPathSegment.endsWith(".amr")) {
            return 3;
        }
        if (lastPathSegment.endsWith(EXTENSION_FLAC)) {
            return 4;
        }
        if (lastPathSegment.endsWith(EXTENSION_FLV)) {
            return 5;
        }
        if (lastPathSegment.startsWith(EXTENSION_PREFIX_MK, lastPathSegment.length() - 4) || lastPathSegment.endsWith(EXTENSION_WEBM)) {
            return 6;
        }
        if (lastPathSegment.endsWith(".mp3")) {
            return 7;
        }
        if (lastPathSegment.endsWith(".mp4") || lastPathSegment.startsWith(EXTENSION_PREFIX_M4, lastPathSegment.length() - 4) || lastPathSegment.startsWith(".mp4", lastPathSegment.length() - 5) || lastPathSegment.startsWith(EXTENSION_PREFIX_CMF, lastPathSegment.length() - 5)) {
            return 8;
        }
        if (lastPathSegment.startsWith(EXTENSION_PREFIX_OG, lastPathSegment.length() - 4) || lastPathSegment.endsWith(EXTENSION_OPUS)) {
            return 9;
        }
        if (lastPathSegment.endsWith(EXTENSION_PS) || lastPathSegment.endsWith(EXTENSION_MPEG) || lastPathSegment.endsWith(EXTENSION_MPG) || lastPathSegment.endsWith(EXTENSION_M2P)) {
            return 10;
        }
        if (lastPathSegment.endsWith(".ts") || lastPathSegment.startsWith(".ts", lastPathSegment.length() - 4)) {
            return 11;
        }
        if (lastPathSegment.endsWith(".wav") || lastPathSegment.endsWith(EXTENSION_WAVE)) {
            return 12;
        }
        if (lastPathSegment.endsWith(EXTENSION_VTT) || lastPathSegment.endsWith(EXTENSION_WEBVTT)) {
            return 13;
        }
        return (lastPathSegment.endsWith(".jpg") || lastPathSegment.endsWith(".jpeg")) ? 14 : -1;
    }
}
