package cn.hutool.core.lang;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.EnumerationIter;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ClassLoaderUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.URLUtil;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/* loaded from: classes.dex */
public class ClassScanner implements Serializable {
    private static final long serialVersionUID = 1;
    private final Charset charset;
    private final Filter<Class<?>> classFilter;
    private ClassLoader classLoader;
    private final Set<Class<?>> classes;
    private final Set<String> classesOfLoadError;
    private boolean ignoreLoadError;
    private boolean initialize;
    private final String packageDirName;
    private final String packageName;
    private final String packageNameWithDot;
    private final String packagePath;

    public ClassScanner() {
        this(null);
    }

    private void addIfAccept(String str) {
        if (CharSequenceUtil.isBlank(str)) {
            return;
        }
        int length = str.length();
        int length2 = this.packageName.length();
        if (length == length2) {
            if (str.equals(this.packageName)) {
                addIfAccept(loadClass(str));
            }
        } else if (length > length2) {
            if (StrPool.DOT.equals(this.packageNameWithDot) || str.startsWith(this.packageNameWithDot)) {
                addIfAccept(loadClass(str));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$scanAllPackageByAnnotation$0(Class cls, Class cls2) {
        return cls2.isAnnotationPresent(cls);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$scanAllPackageBySuper$2(Class cls, Class cls2) {
        return cls.isAssignableFrom(cls2) && !cls.equals(cls2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$scanPackageByAnnotation$1(Class cls, Class cls2) {
        return cls2.isAnnotationPresent(cls);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$scanPackageBySuper$3(Class cls, Class cls2) {
        return cls.isAssignableFrom(cls2) && !cls.equals(cls2);
    }

    public static Set<Class<?>> scanAllPackage() {
        return scanAllPackage("", null);
    }

    public static Set<Class<?>> scanAllPackageByAnnotation(String str, final Class<? extends Annotation> cls) {
        return scanAllPackage(str, new Filter() { // from class: cn.hutool.core.lang.s
            @Override // cn.hutool.core.lang.Filter
            public final boolean accept(Object obj) {
                return ClassScanner.lambda$scanAllPackageByAnnotation$0(cls, (Class) obj);
            }
        });
    }

    public static Set<Class<?>> scanAllPackageBySuper(String str, final Class<?> cls) {
        return scanAllPackage(str, new Filter() { // from class: cn.hutool.core.lang.t
            @Override // cn.hutool.core.lang.Filter
            public final boolean accept(Object obj) {
                return ClassScanner.lambda$scanAllPackageBySuper$2(cls, (Class) obj);
            }
        });
    }

    private void scanFile(File file, String str) {
        File[] fileArrListFiles;
        if (!file.isFile()) {
            if (!file.isDirectory() || (fileArrListFiles = file.listFiles()) == null) {
                return;
            }
            for (File file2 : fileArrListFiles) {
                scanFile(file2, str == null ? subPathBeforePackage(file) : str);
            }
            return;
        }
        String absolutePath = file.getAbsolutePath();
        if (absolutePath.endsWith(".class")) {
            addIfAccept(absolutePath.substring(str.length(), absolutePath.length() - 6).replace(File.separatorChar, '.'));
        } else if (absolutePath.endsWith(".jar")) {
            try {
                scanJar(new JarFile(file));
            } catch (IOException e2) {
                throw new IORuntimeException(e2);
            }
        }
    }

    private void scanJar(JarFile jarFile) {
        Iterator it = new EnumerationIter(jarFile.entries()).iterator();
        while (it.hasNext()) {
            JarEntry jarEntry = (JarEntry) it.next();
            String strRemovePrefix = CharSequenceUtil.removePrefix(jarEntry.getName(), "/");
            if (CharSequenceUtil.isEmpty(this.packagePath) || strRemovePrefix.startsWith(this.packagePath)) {
                if (strRemovePrefix.endsWith(".class") && !jarEntry.isDirectory()) {
                    addIfAccept(loadClass(strRemovePrefix.substring(0, strRemovePrefix.length() - 6).replace('/', '.')));
                }
            }
        }
    }

    private void scanJavaClassPaths() {
        for (String str : ClassUtil.getJavaClassPaths()) {
            scanFile(new File(URLUtil.decode(str, CharsetUtil.systemCharsetName())), null);
        }
    }

    public static Set<Class<?>> scanPackage() {
        return scanPackage("", null);
    }

    public static Set<Class<?>> scanPackageByAnnotation(String str, final Class<? extends Annotation> cls) {
        return scanPackage(str, new Filter() { // from class: cn.hutool.core.lang.r
            @Override // cn.hutool.core.lang.Filter
            public final boolean accept(Object obj) {
                return ClassScanner.lambda$scanPackageByAnnotation$1(cls, (Class) obj);
            }
        });
    }

    public static Set<Class<?>> scanPackageBySuper(String str, final Class<?> cls) {
        return scanPackage(str, new Filter() { // from class: cn.hutool.core.lang.q
            @Override // cn.hutool.core.lang.Filter
            public final boolean accept(Object obj) {
                return ClassScanner.lambda$scanPackageBySuper$3(cls, (Class) obj);
            }
        });
    }

    private String subPathBeforePackage(File file) {
        String absolutePath = file.getAbsolutePath();
        if (CharSequenceUtil.isNotEmpty(this.packageDirName)) {
            absolutePath = CharSequenceUtil.subBefore((CharSequence) absolutePath, (CharSequence) this.packageDirName, true);
        }
        return CharSequenceUtil.addSuffixIfNot(absolutePath, File.separator);
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Set<String> getClassesOfLoadError() {
        return Collections.unmodifiableSet(this.classesOfLoadError);
    }

    public Class<?> loadClass(String str) {
        ClassLoader classLoader = this.classLoader;
        if (classLoader == null) {
            classLoader = ClassLoaderUtil.getClassLoader();
            this.classLoader = classLoader;
        }
        try {
            return Class.forName(str, this.initialize, classLoader);
        } catch (ClassNotFoundException | NoClassDefFoundError unused) {
            this.classesOfLoadError.add(str);
            return null;
        } catch (UnsupportedClassVersionError unused2) {
            this.classesOfLoadError.add(str);
            return null;
        } catch (Throwable th) {
            if (!this.ignoreLoadError) {
                throw ExceptionUtil.wrapRuntime(th);
            }
            this.classesOfLoadError.add(str);
            return null;
        }
    }

    public Set<Class<?>> scan() {
        return scan(false);
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public ClassScanner setIgnoreLoadError(boolean z2) {
        this.ignoreLoadError = z2;
        return this;
    }

    public void setInitialize(boolean z2) {
        this.initialize = z2;
    }

    public ClassScanner(String str) {
        this(str, null);
    }

    public static Set<Class<?>> scanAllPackage(String str, Filter<Class<?>> filter) {
        return new ClassScanner(str, filter).scan(true);
    }

    public static Set<Class<?>> scanPackage(String str) {
        return scanPackage(str, null);
    }

    public Set<Class<?>> scan(boolean z2) {
        this.classes.clear();
        this.classesOfLoadError.clear();
        Iterator it = ResourceUtil.getResourceIter(this.packagePath, this.classLoader).iterator();
        while (it.hasNext()) {
            URL url = (URL) it.next();
            String protocol = url.getProtocol();
            protocol.hashCode();
            if (protocol.equals("jar")) {
                scanJar(URLUtil.getJarFile(url));
            } else if (protocol.equals("file")) {
                scanFile(new File(URLUtil.decode(url.getFile(), this.charset.name())), null);
            }
        }
        if (z2 || CollUtil.isEmpty((Collection<?>) this.classes)) {
            scanJavaClassPaths();
        }
        return Collections.unmodifiableSet(this.classes);
    }

    public ClassScanner(String str, Filter<Class<?>> filter) {
        this(str, filter, CharsetUtil.CHARSET_UTF_8);
    }

    public static Set<Class<?>> scanPackage(String str, Filter<Class<?>> filter) {
        return new ClassScanner(str, filter).scan();
    }

    public ClassScanner(String str, Filter<Class<?>> filter, Charset charset) {
        this.classes = new HashSet();
        this.ignoreLoadError = false;
        this.classesOfLoadError = new HashSet();
        String strNullToEmpty = CharSequenceUtil.nullToEmpty(str);
        this.packageName = strNullToEmpty;
        this.packageNameWithDot = CharSequenceUtil.addSuffixIfNot(strNullToEmpty, StrPool.DOT);
        this.packageDirName = strNullToEmpty.replace('.', File.separatorChar);
        this.packagePath = strNullToEmpty.replace('.', '/');
        this.classFilter = filter;
        this.charset = charset;
    }

    private void addIfAccept(Class<?> cls) {
        if (cls != null) {
            Filter<Class<?>> filter = this.classFilter;
            if (filter == null || filter.accept(cls)) {
                this.classes.add(cls);
            }
        }
    }
}
