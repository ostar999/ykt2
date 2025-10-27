package com.plv.livescenes.upload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes5.dex */
public class PLVDocumentUploadConstant {

    public static class ConvertStatus {
        public static final String FAIL_CONVERT = "failConvert";
        public static final String FAIL_UPLOAD = "failUpload";
        public static final String NORMAL = "normal";
        public static final String UPLOADING_LOCAL = "uploading_local";
        public static final String WAIT_CONVERT = "waitConvert";
        public static final String WAIT_UPLOAD = "waitUpload";
    }

    public static class PPTConvertType {
        public static final String ANIMATE = "animate";
        public static final String COMMON = "common";

        @Retention(RetentionPolicy.SOURCE)
        public @interface PPTConvertTypeAnno {
        }
    }
}
