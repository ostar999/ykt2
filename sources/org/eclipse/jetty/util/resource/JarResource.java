package org.eclipse.jetty.util.resource;

import cn.hutool.core.util.URLUtil;
import java.io.File;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import org.eclipse.jetty.util.IO;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
public class JarResource extends URLResource {
    private static final Logger LOG = Log.getLogger((Class<?>) JarResource.class);
    protected JarURLConnection _jarConnection;

    public JarResource(URL url) {
        super(url, null);
    }

    public static Resource newJarResource(Resource resource) throws IOException {
        if (resource instanceof JarResource) {
            return resource;
        }
        return Resource.newResource(URLUtil.JAR_URL_PREFIX + resource + URLUtil.JAR_URL_SEPARATOR);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x001b  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x001d  */
    @Override // org.eclipse.jetty.util.resource.URLResource
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized boolean checkConnection() {
        /*
            r2 = this;
            monitor-enter(r2)
            super.checkConnection()     // Catch: java.lang.Throwable -> L20
            java.net.JarURLConnection r0 = r2._jarConnection     // Catch: java.io.IOException -> Le java.lang.Throwable -> L20
            java.net.URLConnection r1 = r2._connection     // Catch: java.io.IOException -> Le java.lang.Throwable -> L20
            if (r0 == r1) goto L17
            r2.newConnection()     // Catch: java.io.IOException -> Le java.lang.Throwable -> L20
            goto L17
        Le:
            r0 = move-exception
            org.eclipse.jetty.util.log.Logger r1 = org.eclipse.jetty.util.resource.JarResource.LOG     // Catch: java.lang.Throwable -> L20
            r1.ignore(r0)     // Catch: java.lang.Throwable -> L20
            r0 = 0
            r2._jarConnection = r0     // Catch: java.lang.Throwable -> L20
        L17:
            java.net.JarURLConnection r0 = r2._jarConnection     // Catch: java.lang.Throwable -> L20
            if (r0 == 0) goto L1d
            r0 = 1
            goto L1e
        L1d:
            r0 = 0
        L1e:
            monitor-exit(r2)
            return r0
        L20:
            r0 = move-exception
            monitor-exit(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.resource.JarResource.checkConnection():boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:46:0x00df A[PHI: r7 r8
      0x00df: PHI (r7v5 boolean) = (r7v2 boolean), (r7v6 boolean) binds: [B:45:0x00dd, B:41:0x00d4] A[DONT_GENERATE, DONT_INLINE]
      0x00df: PHI (r8v10 java.lang.String) = (r8v1 java.lang.String), (r8v11 java.lang.String) binds: [B:45:0x00dd, B:41:0x00d4] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // org.eclipse.jetty.util.resource.Resource
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void copyTo(java.io.File r15) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 454
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.resource.JarResource.copyTo(java.io.File):void");
    }

    @Override // org.eclipse.jetty.util.resource.URLResource, org.eclipse.jetty.util.resource.Resource
    public boolean exists() {
        return this._urlString.endsWith(URLUtil.JAR_URL_SEPARATOR) ? checkConnection() : super.exists();
    }

    @Override // org.eclipse.jetty.util.resource.URLResource, org.eclipse.jetty.util.resource.Resource
    public File getFile() throws IOException {
        return null;
    }

    @Override // org.eclipse.jetty.util.resource.URLResource, org.eclipse.jetty.util.resource.Resource
    public InputStream getInputStream() throws IOException {
        checkConnection();
        if (!this._urlString.endsWith(URLUtil.JAR_URL_SEPARATOR)) {
            return new FilterInputStream(super.getInputStream()) { // from class: org.eclipse.jetty.util.resource.JarResource.1
                @Override // java.io.FilterInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
                public void close() throws IOException {
                    ((FilterInputStream) this).in = IO.getClosedStream();
                }
            };
        }
        return new URL(this._urlString.substring(4, r1.length() - 2)).openStream();
    }

    public void newConnection() throws IOException {
        this._jarConnection = (JarURLConnection) this._connection;
    }

    @Override // org.eclipse.jetty.util.resource.URLResource, org.eclipse.jetty.util.resource.Resource
    public synchronized void release() {
        this._jarConnection = null;
        super.release();
    }

    public JarResource(URL url, boolean z2) {
        super(url, null, z2);
    }
}
