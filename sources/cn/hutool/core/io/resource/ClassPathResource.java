package cn.hutool.core.io.resource;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.URLUtil;
import java.net.URL;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public class ClassPathResource extends UrlResource {
    private static final long serialVersionUID = 1;
    private final ClassLoader classLoader;
    private final Class<?> clazz;
    private final String path;

    public ClassPathResource(String str) {
        this(str, null, null);
    }

    private void initUrl() {
        Class<?> cls = this.clazz;
        if (cls != null) {
            this.url = cls.getResource(this.path);
        } else {
            ClassLoader classLoader = this.classLoader;
            if (classLoader != null) {
                this.url = classLoader.getResource(this.path);
            } else {
                this.url = ClassLoader.getSystemResource(this.path);
            }
        }
        if (this.url == null) {
            throw new NoResourceException("Resource of path [{}] not exist!", this.path);
        }
    }

    private String normalizePath(String str) throws Throwable {
        String strRemovePrefix = CharSequenceUtil.removePrefix(FileUtil.normalize(str), "/");
        Assert.isFalse(FileUtil.isAbsolutePath(strRemovePrefix), "Path [{}] must be a relative path !", strRemovePrefix);
        return strRemovePrefix;
    }

    public final String getAbsolutePath() {
        return FileUtil.isAbsolutePath(this.path) ? this.path : FileUtil.normalize(URLUtil.getDecodedPath(this.url));
    }

    public final ClassLoader getClassLoader() {
        return this.classLoader;
    }

    public final String getPath() {
        return this.path;
    }

    @Override // cn.hutool.core.io.resource.UrlResource
    public String toString() {
        if (this.path == null) {
            return super.toString();
        }
        return URLUtil.CLASSPATH_URL_PREFIX + this.path;
    }

    public ClassPathResource(String str, ClassLoader classLoader) {
        this(str, classLoader, null);
    }

    public ClassPathResource(String str, Class<?> cls) {
        this(str, null, cls);
    }

    public ClassPathResource(String str, ClassLoader classLoader, Class<?> cls) throws Throwable {
        super((URL) null);
        Assert.notNull(str, "Path must not be null", new Object[0]);
        String strNormalizePath = normalizePath(str);
        this.path = strNormalizePath;
        this.name = CharSequenceUtil.isBlank(strNormalizePath) ? null : FileUtil.getName(strNormalizePath);
        this.classLoader = (ClassLoader) ObjectUtil.defaultIfNull(classLoader, (Supplier<? extends ClassLoader>) new Supplier() { // from class: cn.hutool.core.io.resource.a
            @Override // java.util.function.Supplier
            public final Object get() {
                return ClassUtil.getClassLoader();
            }
        });
        this.clazz = cls;
        initUrl();
    }
}
