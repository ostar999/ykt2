package cn.hutool.core.io.resource;

import cn.hutool.core.io.IORuntimeException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.Charset;

/* loaded from: classes.dex */
public interface Resource {
    String getName();

    BufferedReader getReader(Charset charset);

    InputStream getStream();

    URL getUrl();

    boolean isModified();

    byte[] readBytes() throws IORuntimeException;

    String readStr(Charset charset) throws IORuntimeException;

    String readUtf8Str() throws IORuntimeException;

    void writeTo(OutputStream outputStream) throws IORuntimeException;
}
