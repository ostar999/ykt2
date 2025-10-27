package cn.hutool.core.compiler;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Locale;
import javax.tools.DiagnosticListener;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

/* loaded from: classes.dex */
public class CompilerUtil {
    public static final JavaCompiler SYSTEM_COMPILER = ToolProvider.getSystemJavaCompiler();

    public static boolean compile(String... strArr) {
        return SYSTEM_COMPILER.run((InputStream) null, (OutputStream) null, (OutputStream) null, strArr) == 0;
    }

    public static JavaSourceCompiler getCompiler(ClassLoader classLoader) {
        return JavaSourceCompiler.create(classLoader);
    }

    public static StandardJavaFileManager getFileManager() {
        return getFileManager(null);
    }

    public static JavaCompiler.CompilationTask getTask(JavaFileManager javaFileManager, DiagnosticListener<? super JavaFileObject> diagnosticListener, Iterable<String> iterable, Iterable<? extends JavaFileObject> iterable2) {
        return SYSTEM_COMPILER.getTask((Writer) null, javaFileManager, diagnosticListener, iterable, (Iterable) null, iterable2);
    }

    public static StandardJavaFileManager getFileManager(DiagnosticListener<? super JavaFileObject> diagnosticListener) {
        return SYSTEM_COMPILER.getStandardFileManager(diagnosticListener, (Locale) null, (Charset) null);
    }
}
