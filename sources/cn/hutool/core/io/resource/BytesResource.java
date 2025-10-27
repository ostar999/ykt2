package cn.hutool.core.io.resource;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.util.StrUtil;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;
import java.nio.charset.Charset;

/* loaded from: classes.dex */
public class BytesResource implements Resource, Serializable {
    private static final long serialVersionUID = 1;
    private final byte[] bytes;
    private final String name;

    public BytesResource(byte[] bArr) {
        this(bArr, null);
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
        return new ByteArrayInputStream(this.bytes);
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
    public byte[] readBytes() throws IORuntimeException {
        return this.bytes;
    }

    @Override // cn.hutool.core.io.resource.Resource
    public String readStr(Charset charset) throws IORuntimeException {
        return StrUtil.str(this.bytes, charset);
    }

    @Override // cn.hutool.core.io.resource.Resource
    public /* synthetic */ String readUtf8Str() {
        return c.e(this);
    }

    @Override // cn.hutool.core.io.resource.Resource
    public /* synthetic */ void writeTo(OutputStream outputStream) throws IOException, IORuntimeException {
        c.f(this, outputStream);
    }

    public BytesResource(byte[] bArr, String str) {
        this.bytes = bArr;
        this.name = str;
    }
}
