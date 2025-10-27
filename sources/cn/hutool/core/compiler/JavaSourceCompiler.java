package cn.hutool.core.compiler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.resource.FileResource;
import cn.hutool.core.io.resource.Resource;
import cn.hutool.core.io.resource.StringResource;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.URLUtil;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;

/* loaded from: classes.dex */
public class JavaSourceCompiler {
    private final ClassLoader parentClassLoader;
    private final List<Resource> sourceList = new ArrayList();
    private final List<File> libraryFileList = new ArrayList();

    private JavaSourceCompiler(ClassLoader classLoader) {
        this.parentClassLoader = (ClassLoader) ObjectUtil.defaultIfNull(classLoader, new b());
    }

    public static JavaSourceCompiler create(ClassLoader classLoader) {
        return new JavaSourceCompiler(classLoader);
    }

    private List<File> getClassPath() {
        ArrayList arrayList = new ArrayList();
        for (File file : this.libraryFileList) {
            arrayList.addAll(FileUtil.loopFiles(file, new FileFilter() { // from class: cn.hutool.core.compiler.g
                @Override // java.io.FileFilter
                public final boolean accept(File file2) {
                    return JavaSourceCompiler.lambda$getClassPath$0(file2);
                }
            }));
            if (file.isDirectory()) {
                arrayList.add(file);
            }
        }
        return arrayList;
    }

    private List<JavaFileObject> getJavaFileObject() {
        final ArrayList arrayList = new ArrayList();
        for (Resource resource : this.sourceList) {
            if (resource instanceof FileResource) {
                final File file = ((FileResource) resource).getFile();
                FileUtil.walkFiles(file, (Consumer<File>) new Consumer() { // from class: cn.hutool.core.compiler.h
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        JavaSourceCompiler.lambda$getJavaFileObject$1(arrayList, file, (File) obj);
                    }
                });
            } else {
                arrayList.add(new JavaSourceFileObject(resource.getName(), resource.getStream()));
            }
        }
        return arrayList;
    }

    private JavaFileObject getJavaFileObjectByJavaFile(File file) {
        return new JavaSourceFileObject(file.toURI());
    }

    private Collection<JavaFileObject> getJavaFileObjectByMap(Map<String, String> map) {
        return MapUtil.isNotEmpty(map) ? (Collection) map.entrySet().stream().map(new Function() { // from class: cn.hutool.core.compiler.i
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return JavaSourceCompiler.lambda$getJavaFileObjectByMap$2((Map.Entry) obj);
            }
        }).collect(Collectors.toList()) : Collections.emptySet();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getClassPath$0(File file) {
        return JavaFileObjectUtil.isJarOrZipFile(file.getName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getJavaFileObject$1(List list, File file, File file2) {
        list.addAll(JavaFileObjectUtil.getJavaFileObjects(file));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ JavaSourceFileObject lambda$getJavaFileObjectByMap$2(Map.Entry entry) {
        return new JavaSourceFileObject((String) entry.getKey(), (String) entry.getValue(), CharsetUtil.CHARSET_UTF_8);
    }

    public JavaSourceCompiler addLibrary(File... fileArr) {
        if (ArrayUtil.isNotEmpty((Object[]) fileArr)) {
            this.libraryFileList.addAll(Arrays.asList(fileArr));
        }
        return this;
    }

    public JavaSourceCompiler addSource(Resource... resourceArr) {
        if (ArrayUtil.isNotEmpty((Object[]) resourceArr)) {
            this.sourceList.addAll(Arrays.asList(resourceArr));
        }
        return this;
    }

    public ClassLoader compile() {
        return compile(null);
    }

    public ClassLoader compile(List<String> list) throws IOException {
        List<File> classPath = getClassPath();
        URLClassLoader uRLClassLoaderNewInstance = URLClassLoader.newInstance(URLUtil.getURLs((File[]) classPath.toArray(new File[0])), this.parentClassLoader);
        if (this.sourceList.isEmpty()) {
            return uRLClassLoaderNewInstance;
        }
        JavaFileManager javaClassFileManager = new JavaClassFileManager(uRLClassLoaderNewInstance, CompilerUtil.getFileManager());
        if (list == null) {
            list = new ArrayList<>();
        }
        if (!classPath.isEmpty()) {
            List map = CollUtil.map(classPath, new Function() { // from class: cn.hutool.core.compiler.e
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((File) obj).getAbsolutePath();
                }
            }, true);
            list.add("-cp");
            list.add(CollUtil.join(map, FileUtil.isWindows() ? com.alipay.sdk.util.h.f3376b : ":"));
        }
        DiagnosticCollector diagnosticCollector = new DiagnosticCollector();
        try {
            if (CompilerUtil.getTask(javaClassFileManager, diagnosticCollector, list, getJavaFileObject()).call().booleanValue()) {
                return javaClassFileManager.getClassLoader(StandardLocation.CLASS_OUTPUT);
            }
            IoUtil.close((Closeable) javaClassFileManager);
            throw new CompilerException(DiagnosticUtil.getMessages(diagnosticCollector));
        } finally {
            IoUtil.close((Closeable) javaClassFileManager);
        }
    }

    public JavaSourceCompiler addSource(File... fileArr) {
        if (ArrayUtil.isNotEmpty((Object[]) fileArr)) {
            for (File file : fileArr) {
                this.sourceList.add(new FileResource(file));
            }
        }
        return this;
    }

    public JavaSourceCompiler addSource(Map<String, String> map) {
        if (MapUtil.isNotEmpty(map)) {
            map.forEach(new BiConsumer() { // from class: cn.hutool.core.compiler.f
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    this.f2449c.addSource((String) obj, (String) obj2);
                }
            });
        }
        return this;
    }

    public JavaSourceCompiler addSource(String str, String str2) {
        if (str != null && str2 != null) {
            this.sourceList.add(new StringResource(str2, str));
        }
        return this;
    }
}
