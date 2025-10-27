package cn.hutool.core.compiler;

import cn.hutool.core.util.URLUtil;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;

/* loaded from: classes.dex */
class JavaClassFileObject extends SimpleJavaFileObject {
    private final ByteArrayOutputStream byteArrayOutputStream;

    public JavaClassFileObject(String str) {
        super(URLUtil.getStringURI(str.replace('.', '/') + JavaFileObject.Kind.CLASS.extension), JavaFileObject.Kind.CLASS);
        this.byteArrayOutputStream = new ByteArrayOutputStream();
    }

    public InputStream openInputStream() {
        return new ByteArrayInputStream(this.byteArrayOutputStream.toByteArray());
    }

    public OutputStream openOutputStream() {
        return this.byteArrayOutputStream;
    }
}
