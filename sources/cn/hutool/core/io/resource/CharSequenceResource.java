package cn.hutool.core.io.resource;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.CharsetUtil;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.StringReader;
import java.net.URL;
import java.nio.charset.Charset;

/* loaded from: classes.dex */
public class CharSequenceResource implements Resource, Serializable {
    private static final long serialVersionUID = 1;
    private final Charset charset;
    private final CharSequence data;
    private final CharSequence name;

    public CharSequenceResource(CharSequence charSequence) {
        this(charSequence, null);
    }

    @Override // cn.hutool.core.io.resource.Resource
    public String getName() {
        return CharSequenceUtil.str(this.name);
    }

    @Override // cn.hutool.core.io.resource.Resource
    public BufferedReader getReader(Charset charset) {
        return IoUtil.getReader(new StringReader(this.data.toString()));
    }

    @Override // cn.hutool.core.io.resource.Resource
    public InputStream getStream() {
        return new ByteArrayInputStream(readBytes());
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
        return this.data.toString().getBytes(this.charset);
    }

    @Override // cn.hutool.core.io.resource.Resource
    public String readStr(Charset charset) throws IORuntimeException {
        return this.data.toString();
    }

    @Override // cn.hutool.core.io.resource.Resource
    public /* synthetic */ String readUtf8Str() {
        return c.e(this);
    }

    @Override // cn.hutool.core.io.resource.Resource
    public /* synthetic */ void writeTo(OutputStream outputStream) throws IOException, IORuntimeException {
        c.f(this, outputStream);
    }

    public CharSequenceResource(CharSequence charSequence, String str) {
        this(charSequence, str, CharsetUtil.CHARSET_UTF_8);
    }

    public CharSequenceResource(CharSequence charSequence, CharSequence charSequence2, Charset charset) {
        this.data = charSequence;
        this.name = charSequence2;
        this.charset = charset;
    }
}
