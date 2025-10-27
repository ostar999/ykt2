package org.eclipse.jetty.util.resource;

import cn.hutool.core.util.URLUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import org.eclipse.jetty.util.B64Code;
import org.eclipse.jetty.util.IO;
import org.eclipse.jetty.util.Loader;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
public abstract class Resource implements ResourceFactory {
    private static final Logger LOG = Log.getLogger((Class<?>) Resource.class);
    public static boolean __defaultUseCaches = true;
    volatile Object _associate;

    private static String deTag(String str) {
        return StringUtil.replace(StringUtil.replace(str, "<", "&lt;"), ">", "&gt;");
    }

    public static boolean getDefaultUseCaches() {
        return __defaultUseCaches;
    }

    private static String hrefEncodeURI(String str) {
        StringBuffer stringBuffer;
        for (int i2 = 0; i2 < str.length(); i2++) {
            char cCharAt = str.charAt(i2);
            if (cCharAt == '\"' || cCharAt == '\'' || cCharAt == '<' || cCharAt == '>') {
                stringBuffer = new StringBuffer(str.length() << 1);
                break;
            }
        }
        stringBuffer = null;
        if (stringBuffer == null) {
            return str;
        }
        for (int i3 = 0; i3 < str.length(); i3++) {
            char cCharAt2 = str.charAt(i3);
            if (cCharAt2 == '\"') {
                stringBuffer.append("%22");
            } else if (cCharAt2 == '\'') {
                stringBuffer.append("%27");
            } else if (cCharAt2 == '<') {
                stringBuffer.append("%3C");
            } else if (cCharAt2 != '>') {
                stringBuffer.append(cCharAt2);
            } else {
                stringBuffer.append("%3E");
            }
        }
        return stringBuffer.toString();
    }

    public static boolean isContainedIn(Resource resource, Resource resource2) throws MalformedURLException {
        return resource.isContainedIn(resource2);
    }

    public static Resource newClassPathResource(String str) {
        return newClassPathResource(str, true, false);
    }

    public static Resource newResource(URI uri) throws IOException {
        return newResource(uri.toURL());
    }

    public static Resource newSystemResource(String str) throws IOException {
        URL resource;
        ClassLoader classLoader;
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        if (contextClassLoader != null) {
            try {
                resource = contextClassLoader.getResource(str);
                if (resource == null && str.startsWith("/")) {
                    resource = contextClassLoader.getResource(str.substring(1));
                }
            } catch (IllegalArgumentException unused) {
            }
        } else {
            resource = null;
        }
        if (resource == null && (classLoader = Resource.class.getClassLoader()) != null && (resource = classLoader.getResource(str)) == null && str.startsWith("/")) {
            resource = classLoader.getResource(str.substring(1));
        }
        if (resource == null && (resource = ClassLoader.getSystemResource(str)) == null && str.startsWith("/")) {
            resource = ClassLoader.getSystemResource(str.substring(1));
        }
        if (resource == null) {
            return null;
        }
        return newResource(resource);
    }

    public static void setDefaultUseCaches(boolean z2) {
        __defaultUseCaches = z2;
    }

    public static URL toURL(File file) throws MalformedURLException {
        return file.toURI().toURL();
    }

    public abstract Resource addPath(String str) throws IOException;

    public void copyTo(File file) throws IOException {
        if (!file.exists()) {
            writeTo(new FileOutputStream(file), 0L, -1L);
            return;
        }
        throw new IllegalArgumentException(file + " exists");
    }

    public abstract boolean delete() throws SecurityException;

    public String encode(String str) {
        return URIUtil.encodePath(str);
    }

    public abstract boolean exists();

    public void finalize() {
        release();
    }

    public URL getAlias() {
        return null;
    }

    public Object getAssociate() {
        return this._associate;
    }

    public abstract File getFile() throws IOException;

    public abstract InputStream getInputStream() throws IOException;

    public String getListHTML(String str, boolean z2) throws IOException {
        String[] list;
        String strCanonicalPath = URIUtil.canonicalPath(str);
        if (strCanonicalPath == null || !isDirectory() || (list = list()) == null) {
            return null;
        }
        Arrays.sort(list);
        String str2 = "Directory: " + deTag(URIUtil.decodePath(strCanonicalPath));
        StringBuilder sb = new StringBuilder(4096);
        sb.append("<HTML><HEAD>");
        sb.append("<LINK HREF=\"");
        sb.append("jetty-dir.css");
        sb.append("\" REL=\"stylesheet\" TYPE=\"text/css\"/><TITLE>");
        sb.append(str2);
        sb.append("</TITLE></HEAD><BODY>\n<H1>");
        sb.append(str2);
        sb.append("</H1>\n<TABLE BORDER=0>\n");
        if (z2) {
            sb.append("<TR><TD><A HREF=\"");
            sb.append(URIUtil.addPaths(strCanonicalPath, "../"));
            sb.append("\">Parent Directory</A></TD><TD></TD><TD></TD></TR>\n");
        }
        String strHrefEncodeURI = hrefEncodeURI(strCanonicalPath);
        DateFormat dateTimeInstance = DateFormat.getDateTimeInstance(2, 2);
        for (int i2 = 0; i2 < list.length; i2++) {
            Resource resourceAddPath = addPath(list[i2]);
            sb.append("\n<TR><TD><A HREF=\"");
            String strAddPaths = URIUtil.addPaths(strHrefEncodeURI, URIUtil.encodePath(list[i2]));
            sb.append(strAddPaths);
            if (resourceAddPath.isDirectory() && !strAddPaths.endsWith("/")) {
                sb.append("/");
            }
            sb.append("\">");
            sb.append(deTag(list[i2]));
            sb.append("&nbsp;");
            sb.append("</A></TD><TD ALIGN=right>");
            sb.append(resourceAddPath.length());
            sb.append(" bytes&nbsp;</TD><TD>");
            sb.append(dateTimeInstance.format(new Date(resourceAddPath.lastModified())));
            sb.append("</TD></TR>");
        }
        sb.append("</TABLE>\n");
        sb.append("</BODY></HTML>\n");
        return sb.toString();
    }

    public abstract String getName();

    public abstract OutputStream getOutputStream() throws IOException, SecurityException;

    @Override // org.eclipse.jetty.util.resource.ResourceFactory
    public Resource getResource(String str) {
        try {
            return addPath(str);
        } catch (Exception e2) {
            LOG.debug(e2);
            return null;
        }
    }

    public URI getURI() {
        try {
            return getURL().toURI();
        } catch (Exception e2) {
            throw new RuntimeException(e2);
        }
    }

    public abstract URL getURL();

    public String getWeakETag() {
        try {
            StringBuilder sb = new StringBuilder(32);
            sb.append("W/\"");
            long jCharAt = 0;
            for (int i2 = 0; i2 < getName().length(); i2++) {
                jCharAt = (jCharAt * 31) + r1.charAt(i2);
            }
            B64Code.encode(lastModified() ^ jCharAt, sb);
            B64Code.encode(length() ^ jCharAt, sb);
            sb.append('\"');
            return sb.toString();
        } catch (IOException e2) {
            throw new RuntimeException(e2);
        }
    }

    public abstract boolean isContainedIn(Resource resource) throws MalformedURLException;

    public abstract boolean isDirectory();

    public abstract long lastModified();

    public abstract long length();

    public abstract String[] list();

    public abstract void release();

    public abstract boolean renameTo(Resource resource) throws SecurityException;

    public void setAssociate(Object obj) {
        this._associate = obj;
    }

    public void writeTo(OutputStream outputStream, long j2, long j3) throws IOException {
        InputStream inputStream = getInputStream();
        try {
            inputStream.skip(j2);
            if (j3 < 0) {
                IO.copy(inputStream, outputStream);
            } else {
                IO.copy(inputStream, outputStream, j3);
            }
        } finally {
            inputStream.close();
        }
    }

    public static Resource newClassPathResource(String str, boolean z2, boolean z3) {
        URL resource = Resource.class.getResource(str);
        if (resource == null) {
            resource = Loader.getResource(Resource.class, str, z3);
        }
        if (resource == null) {
            return null;
        }
        return newResource(resource, z2);
    }

    public static Resource newResource(URL url) throws IOException {
        return newResource(url, __defaultUseCaches);
    }

    public static Resource newResource(URL url, boolean z2) {
        if (url == null) {
            return null;
        }
        String externalForm = url.toExternalForm();
        if (externalForm.startsWith("file:")) {
            try {
                return new FileResource(url);
            } catch (Exception e2) {
                LOG.debug(Log.EXCEPTION, e2);
                return new BadResource(url, e2.toString());
            }
        }
        if (externalForm.startsWith("jar:file:")) {
            return new JarFileResource(url, z2);
        }
        if (externalForm.startsWith(URLUtil.JAR_URL_PREFIX)) {
            return new JarResource(url, z2);
        }
        return new URLResource(url, null, z2);
    }

    public static Resource newResource(String str) throws IOException {
        return newResource(str, __defaultUseCaches);
    }

    public static Resource newResource(String str, boolean z2) throws IOException {
        try {
            return newResource(new URL(str));
        } catch (MalformedURLException e2) {
            if (!str.startsWith("ftp:") && !str.startsWith("file:") && !str.startsWith(URLUtil.JAR_URL_PREFIX)) {
                try {
                    if (str.startsWith("./")) {
                        str = str.substring(2);
                    }
                    File canonicalFile = new File(str).getCanonicalFile();
                    URL url = toURL(canonicalFile);
                    URLConnection uRLConnectionOpenConnection = url.openConnection();
                    uRLConnectionOpenConnection.setUseCaches(z2);
                    return new FileResource(url, uRLConnectionOpenConnection, canonicalFile);
                } catch (Exception e3) {
                    LOG.debug(Log.EXCEPTION, e3);
                    throw e2;
                }
            }
            LOG.warn("Bad Resource: " + str, new Object[0]);
            throw e2;
        }
    }

    public static Resource newResource(File file) throws IOException {
        File canonicalFile = file.getCanonicalFile();
        URL url = toURL(canonicalFile);
        return new FileResource(url, url.openConnection(), canonicalFile);
    }
}
