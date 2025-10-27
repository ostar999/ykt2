package com.plv.foundationsdk.download.bean;

import cn.hutool.core.text.CharPool;

/* loaded from: classes4.dex */
public class PLVMultimedia {
    private final String fileDir;
    private final String fileName;
    private final String url;

    public PLVMultimedia(String str, String str2, String str3) {
        this.url = str;
        this.fileDir = str2;
        this.fileName = str3;
    }

    public String getFileDir() {
        return this.fileDir;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getUrl() {
        return this.url;
    }

    public String toString() {
        return "PLVMultimedia{url='" + this.url + CharPool.SINGLE_QUOTE + ", fileDir='" + this.fileDir + CharPool.SINGLE_QUOTE + ", fileName='" + this.fileName + CharPool.SINGLE_QUOTE + '}';
    }
}
