package cn.hutool.core.io.resource;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ClassLoaderUtil;
import cn.hutool.core.util.ReflectUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.charset.Charset;

/* loaded from: classes.dex */
public class VfsResource implements Resource {
    private static final String VFS3_PKG = "org.jboss.vfs.";
    private static final Method VIRTUAL_FILE_METHOD_EXISTS;
    private static final Method VIRTUAL_FILE_METHOD_GET_INPUT_STREAM;
    private static final Method VIRTUAL_FILE_METHOD_GET_LAST_MODIFIED;
    private static final Method VIRTUAL_FILE_METHOD_GET_NAME;
    private static final Method VIRTUAL_FILE_METHOD_GET_SIZE;
    private static final Method VIRTUAL_FILE_METHOD_TO_URL;
    private final long lastModified;
    private final Object virtualFile;

    static {
        Class<?> clsLoadClass = ClassLoaderUtil.loadClass("org.jboss.vfs.VirtualFile");
        try {
            VIRTUAL_FILE_METHOD_EXISTS = clsLoadClass.getMethod("exists", new Class[0]);
            VIRTUAL_FILE_METHOD_GET_INPUT_STREAM = clsLoadClass.getMethod("openStream", new Class[0]);
            VIRTUAL_FILE_METHOD_GET_SIZE = clsLoadClass.getMethod("getSize", new Class[0]);
            VIRTUAL_FILE_METHOD_GET_LAST_MODIFIED = clsLoadClass.getMethod("getLastModified", new Class[0]);
            VIRTUAL_FILE_METHOD_TO_URL = clsLoadClass.getMethod("toURL", new Class[0]);
            VIRTUAL_FILE_METHOD_GET_NAME = clsLoadClass.getMethod("getName", new Class[0]);
        } catch (NoSuchMethodException e2) {
            throw new IllegalStateException("Could not detect JBoss VFS infrastructure", e2);
        }
    }

    public VfsResource(Object obj) throws IllegalArgumentException {
        Assert.notNull(obj, "VirtualFile must not be null", new Object[0]);
        this.virtualFile = obj;
        this.lastModified = getLastModified();
    }

    public boolean exists() {
        return ((Boolean) ReflectUtil.invoke(this.virtualFile, VIRTUAL_FILE_METHOD_EXISTS, new Object[0])).booleanValue();
    }

    public long getLastModified() {
        return ((Long) ReflectUtil.invoke(this.virtualFile, VIRTUAL_FILE_METHOD_GET_LAST_MODIFIED, new Object[0])).longValue();
    }

    @Override // cn.hutool.core.io.resource.Resource
    public String getName() {
        return (String) ReflectUtil.invoke(this.virtualFile, VIRTUAL_FILE_METHOD_GET_NAME, new Object[0]);
    }

    @Override // cn.hutool.core.io.resource.Resource
    public /* synthetic */ BufferedReader getReader(Charset charset) {
        return c.a(this, charset);
    }

    @Override // cn.hutool.core.io.resource.Resource
    public InputStream getStream() {
        return (InputStream) ReflectUtil.invoke(this.virtualFile, VIRTUAL_FILE_METHOD_GET_INPUT_STREAM, new Object[0]);
    }

    @Override // cn.hutool.core.io.resource.Resource
    public URL getUrl() {
        return (URL) ReflectUtil.invoke(this.virtualFile, VIRTUAL_FILE_METHOD_TO_URL, new Object[0]);
    }

    @Override // cn.hutool.core.io.resource.Resource
    public boolean isModified() {
        return this.lastModified != getLastModified();
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
        return ((Long) ReflectUtil.invoke(this.virtualFile, VIRTUAL_FILE_METHOD_GET_SIZE, new Object[0])).longValue();
    }

    @Override // cn.hutool.core.io.resource.Resource
    public /* synthetic */ void writeTo(OutputStream outputStream) throws IOException, IORuntimeException {
        c.f(this, outputStream);
    }
}
