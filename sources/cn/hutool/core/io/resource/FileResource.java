package cn.hutool.core.io.resource;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.URLUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public class FileResource implements Resource, Serializable {
    private static final long serialVersionUID = 1;
    private final File file;
    private final long lastModified;
    private final String name;

    public FileResource(String str) {
        this(FileUtil.file(str));
    }

    public File getFile() {
        return this.file;
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
    public InputStream getStream() throws NoResourceException {
        return FileUtil.getInputStream(this.file);
    }

    @Override // cn.hutool.core.io.resource.Resource
    public URL getUrl() {
        return URLUtil.getURL(this.file);
    }

    @Override // cn.hutool.core.io.resource.Resource
    public boolean isModified() {
        return this.lastModified != this.file.lastModified();
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

    public String toString() {
        return this.file.toString();
    }

    @Override // cn.hutool.core.io.resource.Resource
    public /* synthetic */ void writeTo(OutputStream outputStream) throws IOException, IORuntimeException {
        c.f(this, outputStream);
    }

    public FileResource(Path path) {
        this(path.toFile());
    }

    public FileResource(File file) {
        this(file, null);
    }

    public FileResource(final File file, String str) throws IllegalArgumentException {
        Assert.notNull(file, "File must be not null !", new Object[0]);
        this.file = file;
        this.lastModified = file.lastModified();
        this.name = (String) ObjectUtil.defaultIfNull(str, (Supplier<? extends String>) new Supplier() { // from class: cn.hutool.core.io.resource.b
            @Override // java.util.function.Supplier
            public final Object get() {
                return file.getName();
            }
        });
    }
}
