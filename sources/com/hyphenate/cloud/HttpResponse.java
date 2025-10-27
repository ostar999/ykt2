package com.hyphenate.cloud;

import cn.hutool.core.text.CharPool;
import java.io.InputStream;

/* loaded from: classes4.dex */
public class HttpResponse {
    public int code;
    public String content;
    public long contentLength;
    public InputStream errorStream;
    public Exception exception;
    public InputStream inputStream;

    public String toString() {
        return "HttpResponse{contentLength=" + this.contentLength + ", code=" + this.code + ", content='" + this.content + CharPool.SINGLE_QUOTE + '}';
    }
}
