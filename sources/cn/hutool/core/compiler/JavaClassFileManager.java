package cn.hutool.core.compiler;

import cn.hutool.core.io.resource.FileObjectResource;
import cn.hutool.core.lang.ResourceClassLoader;
import cn.hutool.core.util.ObjectUtil;
import java.util.HashMap;
import java.util.Map;
import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;

/* loaded from: classes.dex */
class JavaClassFileManager extends ForwardingJavaFileManager<JavaFileManager> {
    private final Map<String, FileObjectResource> classFileObjectMap;
    private final ClassLoader parent;

    public JavaClassFileManager(ClassLoader classLoader, JavaFileManager javaFileManager) {
        super(javaFileManager);
        this.classFileObjectMap = new HashMap();
        this.parent = (ClassLoader) ObjectUtil.defaultIfNull(classLoader, new b());
    }

    public ClassLoader getClassLoader(JavaFileManager.Location location) {
        return new ResourceClassLoader(this.parent, this.classFileObjectMap);
    }

    public JavaFileObject getJavaFileForOutput(JavaFileManager.Location location, String str, JavaFileObject.Kind kind, FileObject fileObject) {
        JavaClassFileObject javaClassFileObject = new JavaClassFileObject(str);
        this.classFileObjectMap.put(str, new FileObjectResource(javaClassFileObject));
        return javaClassFileObject;
    }
}
