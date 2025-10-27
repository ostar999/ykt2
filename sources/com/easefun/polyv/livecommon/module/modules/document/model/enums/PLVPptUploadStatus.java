package com.easefun.polyv.livecommon.module.modules.document.model.enums;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public class PLVPptUploadStatus {
    public static final int STATUS_CONVERTING = 5;
    public static final int STATUS_CONVERT_ANIMATE_LOSS = 7;
    public static final int STATUS_CONVERT_FAILED = 6;
    public static final int STATUS_CONVERT_SUCCESS = 8;
    public static final int STATUS_PREPARED = 1;
    public static final int STATUS_UNPREPARED = 0;
    public static final int STATUS_UPLOADING = 2;
    public static final int STATUS_UPLOAD_FAILED = 3;
    public static final int STATUS_UPLOAD_SUCCESS = 4;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Range {
    }

    public static boolean isStatusConvertSuccess(Integer status) {
        return status == null || status.intValue() >= 8;
    }

    public static boolean isStatusUploadSuccess(Integer status) {
        return status == null || status.intValue() >= 4;
    }
}
