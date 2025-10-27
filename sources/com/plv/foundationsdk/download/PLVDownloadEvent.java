package com.plv.foundationsdk.download;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public class PLVDownloadEvent {
    public static final String DOWNLOAD_DELETE_ERROR = "downloadDeleteError";
    public static final String DOWNLOAD_ERROR = "downloadError";
    public static final String DOWNLOAD_PPT_JS_ERROR = "downloadPptJsError";
    public static final String DOWNLOAD_PPT_JS_SAVE_ERROR = "downloadPptJsSaveError";
    public static final String DOWNLOAD_PPT_ZIP_ERROR = "downloadPptZipError";
    public static final String DOWNLOAD_TS_ERROR = "downloadTSError";
    public static final String DOWNLOAD_VIDEO_ERROR = "downloadVideoError";
    public static final String DOWNLOAD_ZIP_ERROR = "downloadZIPError";

    @Retention(RetentionPolicy.SOURCE)
    public @interface DownloadEvent {
    }
}
