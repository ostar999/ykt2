package cn.hutool.core.io.resource;

import cn.hutool.core.io.IORuntimeException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;
import java.nio.charset.Charset;

/* loaded from: classes.dex */
public class InputStreamResource implements Resource, Serializable {
    private static final long serialVersionUID = 1;
    private final InputStream in;
    private final String name;

    public InputStreamResource(InputStream inputStream) {
        this(inputStream, null);
    }

    @Override // cn.hutool.core.io.resource.Resource
    public String getName() {
        return this.name;
    }

    @Override // cn.hutool.core.io.resource.Resource
    public /* synthetic */ BufferedReader getReader(Charset charset) {
        return c.a(this, charset);
    }

    @Override // cn.hutool.core.io.resource.Resource
    public InputStream getStream() {
        return this.in;
    }

    @Override // cn.hutool.core.io.resource.Resource
    public URL getUrl() {
        return null;
    }

    @Override // cn.hutool.core.io.resource.Resource
    public /* synthetic */ boolean isModified() {
        return c.b(this);
    }

    @Override // cn.hutool.core.io.resource.Resource
    public /* synthetic */ byte[] readBytes() {
        return c.c(this);
    }

    @Override // cn.hutool.core.io.resource.Resource
    public /* synthetic */ String readStr(Charset charset) {
        return c.d(this, charset);
    }

    @Override // cn.hutool.core.io.resource.Resource
    public /* synthetic */ String readUtf8Str() {
        return c.e(this);
    }

    @Override // cn.hutool.core.io.resource.Resource
    public /* synthetic */ void writeTo(OutputStream outputStream) throws IOException, IORuntimeException {
        c.f(this, outputStream);
    }

    public InputStreamResource(InputStream inputStream, String str) {
        this.in = inputStream;
        this.name = str;
    }
}
