package cn.hutool.core.lang;

import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.URLUtil;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class JarClassLoader extends URLClassLoader {
    public JarClassLoader() {
        this(new URL[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isJarFile(File file) {
        if (FileUtil.isFile(file)) {
            return file.getPath().toLowerCase().endsWith(".jar");
        }
        return false;
    }

    public static JarClassLoader load(File file) {
        JarClassLoader jarClassLoader = new JarClassLoader();
        jarClassLoader.addJar(file);
        jarClassLoader.addURL(file);
        return jarClassLoader;
    }

    public static JarClassLoader loadJar(File file) {
        JarClassLoader jarClassLoader = new JarClassLoader();
        jarClassLoader.addJar(file);
        return jarClassLoader;
    }

    public static URLClassLoader loadJarToSystemClassLoader(File file) throws UtilException, SecurityException {
        URLClassLoader uRLClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        loadJar(uRLClassLoader, file);
        return uRLClassLoader;
    }

    private static List<File> loopJar(File file) {
        return FileUtil.loopFiles(file, new FileFilter() { // from class: cn.hutool.core.lang.y
            @Override // java.io.FileFilter
            public final boolean accept(File file2) {
                return JarClassLoader.isJarFile(file2);
            }
        });
    }

    public JarClassLoader addJar(File file) {
        if (isJarFile(file)) {
            return addURL(file);
        }
        Iterator<File> it = loopJar(file).iterator();
        while (it.hasNext()) {
            addURL(it.next());
        }
        return this;
    }

    @Override // java.net.URLClassLoader
    public void addURL(URL url) {
        super.addURL(url);
    }

    public JarClassLoader(URL[] urlArr) {
        super(urlArr, ClassUtil.getClassLoader());
    }

    public JarClassLoader addURL(File file) {
        super.addURL(URLUtil.getURL(file));
        return this;
    }

    public JarClassLoader(URL[] urlArr, ClassLoader classLoader) {
        super(urlArr, classLoader);
    }

    public static void loadJar(URLClassLoader uRLClassLoader, File file) throws UtilException, SecurityException {
        try {
            Method declaredMethod = ClassUtil.getDeclaredMethod(URLClassLoader.class, "addURL", URL.class);
            if (declaredMethod != null) {
                declaredMethod.setAccessible(true);
                Iterator<File> it = loopJar(file).iterator();
                while (it.hasNext()) {
                    ReflectUtil.invoke(uRLClassLoader, declaredMethod, it.next().toURI().toURL());
                }
            }
        } catch (IOException e2) {
            throw new UtilException(e2);
        }
    }
}
