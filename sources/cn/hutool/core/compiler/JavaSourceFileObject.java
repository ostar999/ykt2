package cn.hutool.core.compiler;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.URLUtil;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.Charset;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;

/* loaded from: classes.dex */
class JavaSourceFileObject extends SimpleJavaFileObject {
    private InputStream inputStream;
    private String sourceCode;

    public JavaSourceFileObject(URI uri) {
        super(uri, JavaFileObject.Kind.SOURCE);
    }

    public CharSequence getCharContent(boolean z2) throws IOException {
        if (this.sourceCode == null) {
            InputStream inputStreamOpenInputStream = openInputStream();
            try {
                this.sourceCode = IoUtil.readUtf8(inputStreamOpenInputStream);
                if (inputStreamOpenInputStream != null) {
                    inputStreamOpenInputStream.close();
                }
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    if (inputStreamOpenInputStream != null) {
                        try {
                            inputStreamOpenInputStream.close();
                        } catch (Throwable th3) {
                            th.addSuppressed(th3);
                        }
                    }
                    throw th2;
                }
            }
        }
        return this.sourceCode;
    }

    public InputStream openInputStream() throws IOException {
        if (this.inputStream == null) {
            this.inputStream = toUri().toURL().openStream();
        }
        return new BufferedInputStream(this.inputStream);
    }

    public JavaSourceFileObject(String str, String str2, Charset charset) {
        this(str, IoUtil.toStream(str2, charset));
    }

    public JavaSourceFileObject(String str, InputStream inputStream) {
        this(URLUtil.getStringURI(str.replace('.', '/') + JavaFileObject.Kind.SOURCE.extension));
        this.inputStream = inputStream;
    }
}
