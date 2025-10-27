package cn.hutool.core.io.resource;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IORuntimeException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class MultiResource implements Resource, Iterable<Resource>, Iterator<Resource>, Serializable {
    private static final long serialVersionUID = 1;
    private int cursor;
    private final List<Resource> resources;

    public MultiResource(Resource... resourceArr) {
        this(CollUtil.newArrayList(resourceArr));
    }

    public MultiResource add(Resource resource) {
        this.resources.add(resource);
        return this;
    }

    @Override // cn.hutool.core.io.resource.Resource
    public String getName() {
        return this.resources.get(this.cursor).getName();
    }

    @Override // cn.hutool.core.io.resource.Resource
    public BufferedReader getReader(Charset charset) {
        return this.resources.get(this.cursor).getReader(charset);
    }

    @Override // cn.hutool.core.io.resource.Resource
    public InputStream getStream() {
        return this.resources.get(this.cursor).getStream();
    }

    @Override // cn.hutool.core.io.resource.Resource
    public URL getUrl() {
        return this.resources.get(this.cursor).getUrl();
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.cursor < this.resources.size();
    }

    @Override // cn.hutool.core.io.resource.Resource
    public boolean isModified() {
        return this.resources.get(this.cursor).isModified();
    }

    @Override // java.lang.Iterable
    public Iterator<Resource> iterator() {
        return this.resources.iterator();
    }

    @Override // cn.hutool.core.io.resource.Resource
    public byte[] readBytes() throws IORuntimeException {
        return this.resources.get(this.cursor).readBytes();
    }

    @Override // cn.hutool.core.io.resource.Resource
    public String readStr(Charset charset) throws IORuntimeException {
        return this.resources.get(this.cursor).readStr(charset);
    }

    @Override // cn.hutool.core.io.resource.Resource
    public String readUtf8Str() throws IORuntimeException {
        return this.resources.get(this.cursor).readUtf8Str();
    }

    @Override // java.util.Iterator
    public void remove() {
        this.resources.remove(this.cursor);
    }

    public synchronized void reset() {
        this.cursor = 0;
    }

    @Override // cn.hutool.core.io.resource.Resource
    public /* synthetic */ void writeTo(OutputStream outputStream) throws IOException, IORuntimeException {
        c.f(this, outputStream);
    }

    public MultiResource(Collection<Resource> collection) {
        if (collection instanceof List) {
            this.resources = (List) collection;
        } else {
            this.resources = CollUtil.newArrayList((Collection) collection);
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.Iterator
    public synchronized Resource next() {
        if (this.cursor >= this.resources.size()) {
            throw new ConcurrentModificationException();
        }
        this.cursor++;
        return this;
    }
}
