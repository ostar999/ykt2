package cn.hutool.core.io;

import cn.hutool.core.io.resource.ResourceUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URLConnection;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/* loaded from: classes.dex */
public class ManifestUtil {
    private static final String[] MANIFEST_NAMES = {"Manifest.mf", "manifest.mf", "MANIFEST.MF"};

    public static Manifest getManifest(Class<?> cls) throws IOException, IORuntimeException {
        try {
            URLConnection uRLConnectionOpenConnection = ResourceUtil.getResource(null, cls).openConnection();
            if (uRLConnectionOpenConnection instanceof JarURLConnection) {
                return getManifest((JarURLConnection) uRLConnectionOpenConnection);
            }
            return null;
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static Manifest getManifest(File file) throws IOException, IORuntimeException {
        File file2;
        if (file.isFile()) {
            try {
                JarFile jarFile = new JarFile(file);
                try {
                    Manifest manifest = getManifest(jarFile);
                    jarFile.close();
                    return manifest;
                } finally {
                }
            } catch (IOException e2) {
                throw new IORuntimeException(e2);
            }
        } else {
            File file3 = new File(file, "META-INF");
            if (file3.isDirectory()) {
                for (String str : MANIFEST_NAMES) {
                    file2 = new File(file3, str);
                    if (file2.isFile()) {
                        break;
                    }
                }
                file2 = null;
            } else {
                file2 = null;
            }
            if (file2 == null) {
                return null;
            }
            try {
                FileInputStream fileInputStream = new FileInputStream(file2);
                try {
                    Manifest manifest2 = new Manifest(fileInputStream);
                    fileInputStream.close();
                    return manifest2;
                } finally {
                }
            } catch (IOException e3) {
                throw new IORuntimeException(e3);
            }
        }
    }

    public static Manifest getManifest(JarURLConnection jarURLConnection) throws IORuntimeException {
        try {
            return getManifest(jarURLConnection.getJarFile());
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static Manifest getManifest(JarFile jarFile) throws IORuntimeException {
        try {
            return jarFile.getManifest();
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }
}
