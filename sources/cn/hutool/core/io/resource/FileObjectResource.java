package cn.hutool.core.io.resource;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import javax.tools.FileObject;

/* loaded from: classes.dex */
public class FileObjectResource implements Resource {
    private final FileObject fileObject;

    public FileObjectResource(FileObject fileObject) {
        this.fileObject = fileObject;
    }

    public FileObject getFileObject() {
        return this.fileObject;
    }

    @Override // cn.hutool.core.io.resource.Resource
    public String getName() {
        return this.fileObject.getName();
    }

    @Override // cn.hutool.core.io.resource.Resource
    public BufferedReader getReader(Charset charset) {
        try {
            return IoUtil.getReader(this.fileObject.openReader(false));
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    @Override // cn.hutool.core.io.resource.Resource
    public InputStream getStream() {
        try {
            return this.fileObject.openInputStream();
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    @Override // cn.hutool.core.io.resource.Resource
    public URL getUrl() {
        try {
            return this.fileObject.toUri().toURL();
        } catch (MalformedURLException unused) {
            return null;
        }
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
}
