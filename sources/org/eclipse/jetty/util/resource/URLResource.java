package org.eclipse.jetty.util.resource;

import java.io.File;
import java.io.FilePermission;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.Permission;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
public class URLResource extends Resource {
    private static final Logger LOG = Log.getLogger((Class<?>) URLResource.class);
    protected URLConnection _connection;
    protected InputStream _in;
    protected URL _url;
    protected String _urlString;
    transient boolean _useCaches;

    public URLResource(URL url, URLConnection uRLConnection) {
        this._in = null;
        this._useCaches = Resource.__defaultUseCaches;
        this._url = url;
        this._urlString = url.toString();
        this._connection = uRLConnection;
    }

    @Override // org.eclipse.jetty.util.resource.Resource
    public Resource addPath(String str) throws IOException {
        if (str == null) {
            return null;
        }
        return Resource.newResource(URIUtil.addPaths(this._url.toExternalForm(), URIUtil.canonicalPath(str)));
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x001d  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x001f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized boolean checkConnection() {
        /*
            r2 = this;
            monitor-enter(r2)
            java.net.URLConnection r0 = r2._connection     // Catch: java.lang.Throwable -> L22
            if (r0 != 0) goto L19
            java.net.URL r0 = r2._url     // Catch: java.io.IOException -> L13 java.lang.Throwable -> L22
            java.net.URLConnection r0 = r0.openConnection()     // Catch: java.io.IOException -> L13 java.lang.Throwable -> L22
            r2._connection = r0     // Catch: java.io.IOException -> L13 java.lang.Throwable -> L22
            boolean r1 = r2._useCaches     // Catch: java.io.IOException -> L13 java.lang.Throwable -> L22
            r0.setUseCaches(r1)     // Catch: java.io.IOException -> L13 java.lang.Throwable -> L22
            goto L19
        L13:
            r0 = move-exception
            org.eclipse.jetty.util.log.Logger r1 = org.eclipse.jetty.util.resource.URLResource.LOG     // Catch: java.lang.Throwable -> L22
            r1.ignore(r0)     // Catch: java.lang.Throwable -> L22
        L19:
            java.net.URLConnection r0 = r2._connection     // Catch: java.lang.Throwable -> L22
            if (r0 == 0) goto L1f
            r0 = 1
            goto L20
        L1f:
            r0 = 0
        L20:
            monitor-exit(r2)
            return r0
        L22:
            r0 = move-exception
            monitor-exit(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.resource.URLResource.checkConnection():boolean");
    }

    @Override // org.eclipse.jetty.util.resource.Resource
    public boolean delete() throws SecurityException {
        throw new SecurityException("Delete not supported");
    }

    public boolean equals(Object obj) {
        return (obj instanceof URLResource) && this._urlString.equals(((URLResource) obj)._urlString);
    }

    @Override // org.eclipse.jetty.util.resource.Resource
    public boolean exists() {
        try {
            synchronized (this) {
                if (checkConnection() && this._in == null) {
                    this._in = this._connection.getInputStream();
                }
            }
        } catch (IOException e2) {
            LOG.ignore(e2);
        }
        return this._in != null;
    }

    @Override // org.eclipse.jetty.util.resource.Resource
    public File getFile() throws IOException {
        if (checkConnection()) {
            Permission permission = this._connection.getPermission();
            if (permission instanceof FilePermission) {
                return new File(permission.getName());
            }
        }
        try {
            return new File(this._url.getFile());
        } catch (Exception e2) {
            LOG.ignore(e2);
            return null;
        }
    }

    @Override // org.eclipse.jetty.util.resource.Resource
    public synchronized InputStream getInputStream() throws IOException {
        if (!checkConnection()) {
            throw new IOException("Invalid resource");
        }
        try {
            InputStream inputStream = this._in;
            if (inputStream != null) {
                this._in = null;
                return inputStream;
            }
            return this._connection.getInputStream();
        } finally {
            this._connection = null;
        }
    }

    @Override // org.eclipse.jetty.util.resource.Resource
    public String getName() {
        return this._url.toExternalForm();
    }

    @Override // org.eclipse.jetty.util.resource.Resource
    public OutputStream getOutputStream() throws IOException, SecurityException {
        throw new IOException("Output not supported");
    }

    @Override // org.eclipse.jetty.util.resource.Resource
    public URL getURL() {
        return this._url;
    }

    public boolean getUseCaches() {
        return this._useCaches;
    }

    public int hashCode() {
        return this._urlString.hashCode();
    }

    @Override // org.eclipse.jetty.util.resource.Resource
    public boolean isContainedIn(Resource resource) throws MalformedURLException {
        return false;
    }

    @Override // org.eclipse.jetty.util.resource.Resource
    public boolean isDirectory() {
        return exists() && this._url.toString().endsWith("/");
    }

    @Override // org.eclipse.jetty.util.resource.Resource
    public long lastModified() {
        if (checkConnection()) {
            return this._connection.getLastModified();
        }
        return -1L;
    }

    @Override // org.eclipse.jetty.util.resource.Resource
    public long length() {
        if (checkConnection()) {
            return this._connection.getContentLength();
        }
        return -1L;
    }

    @Override // org.eclipse.jetty.util.resource.Resource
    public String[] list() {
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0016 A[Catch: all -> 0x001a, TRY_LEAVE, TryCatch #1 {, blocks: (B:3:0x0001, B:6:0x0006, B:10:0x0010, B:11:0x0012, B:13:0x0016, B:9:0x000b), top: B:21:0x0001, inners: #0 }] */
    @Override // org.eclipse.jetty.util.resource.Resource
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized void release() {
        /*
            r3 = this;
            monitor-enter(r3)
            java.io.InputStream r0 = r3._in     // Catch: java.lang.Throwable -> L1a
            r1 = 0
            if (r0 == 0) goto L12
            r0.close()     // Catch: java.io.IOException -> La java.lang.Throwable -> L1a
            goto L10
        La:
            r0 = move-exception
            org.eclipse.jetty.util.log.Logger r2 = org.eclipse.jetty.util.resource.URLResource.LOG     // Catch: java.lang.Throwable -> L1a
            r2.ignore(r0)     // Catch: java.lang.Throwable -> L1a
        L10:
            r3._in = r1     // Catch: java.lang.Throwable -> L1a
        L12:
            java.net.URLConnection r0 = r3._connection     // Catch: java.lang.Throwable -> L1a
            if (r0 == 0) goto L18
            r3._connection = r1     // Catch: java.lang.Throwable -> L1a
        L18:
            monitor-exit(r3)
            return
        L1a:
            r0 = move-exception
            monitor-exit(r3)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.resource.URLResource.release():void");
    }

    @Override // org.eclipse.jetty.util.resource.Resource
    public boolean renameTo(Resource resource) throws SecurityException {
        throw new SecurityException("RenameTo not supported");
    }

    public String toString() {
        return this._urlString;
    }

    public URLResource(URL url, URLConnection uRLConnection, boolean z2) {
        this(url, uRLConnection);
        this._useCaches = z2;
    }
}
