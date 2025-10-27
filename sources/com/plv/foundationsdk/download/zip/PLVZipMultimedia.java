package com.plv.foundationsdk.download.zip;

import cn.hutool.core.text.CharPool;

/* loaded from: classes4.dex */
public class PLVZipMultimedia {
    private final int entriesTotal;
    private final String fileDir;
    private final String fileName;
    private final String url;

    public PLVZipMultimedia(int i2, String str, String str2, String str3) {
        this.entriesTotal = i2;
        this.url = str;
        this.fileDir = str2;
        this.fileName = str3;
    }

    public int getEntriesTotal() {
        return this.entriesTotal;
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
        return "PLVZipMultimedia{entriesTotal=" + this.entriesTotal + ", url='" + this.url + CharPool.SINGLE_QUOTE + ", fileDir='" + this.fileDir + CharPool.SINGLE_QUOTE + ", fileName='" + this.fileName + CharPool.SINGLE_QUOTE + '}';
    }
}
