package cn.hutool.core.io.resource;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.URLUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public class UrlResource implements Resource, Serializable {
    private static final long serialVersionUID = 1;
    private long lastModified;
    protected String name;
    protected URL url;

    public UrlResource(URI uri) {
        this(URLUtil.url(uri), null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String lambda$new$0(URL url) {
        if (url != null) {
            return FileUtil.getName(url.getPath());
        }
        return null;
    }

    public File getFile() {
        return FileUtil.file(this.url);
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
        URL url = this.url;
        if (url != null) {
            return URLUtil.getStream(url);
        }
        throw new NoResourceException("Resource URL is null!");
    }

    @Override // cn.hutool.core.io.resource.Resource
    public URL getUrl() {
        return this.url;
    }

    @Override // cn.hutool.core.io.resource.Resource
    public boolean isModified() {
        long j2 = this.lastModified;
        return (0 == j2 || j2 == getFile().lastModified()) ? false : true;
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

    public long size() {
        return URLUtil.size(this.url);
    }

    public String toString() {
        URL url = this.url;
        return url == null ? "null" : url.toString();
    }

    @Override // cn.hutool.core.io.resource.Resource
    public /* synthetic */ void writeTo(OutputStream outputStream) throws IOException, IORuntimeException {
        c.f(this, outputStream);
    }

    public UrlResource(URL url) {
        this(url, null);
    }

    public UrlResource(final URL url, String str) {
        this.lastModified = 0L;
        this.url = url;
        if (url != null && "file".equals(url.getProtocol())) {
            this.lastModified = FileUtil.file(url).lastModified();
        }
        this.name = (String) ObjectUtil.defaultIfNull(str, (Supplier<? extends String>) new Supplier() { // from class: cn.hutool.core.io.resource.d
            @Override // java.util.function.Supplier
            public final Object get() {
                return UrlResource.lambda$new$0(url);
            }
        });
    }

    @Deprecated
    public UrlResource(File file) {
        this.lastModified = 0L;
        this.url = URLUtil.getURL(file);
    }
}
