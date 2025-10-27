package cn.hutool.core.io.resource;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

/* loaded from: classes.dex */
public final /* synthetic */ class c {
    public static BufferedReader a(Resource resource, Charset charset) {
        return IoUtil.getReader(resource.getStream(), charset);
    }

    public static boolean b(Resource resource) {
        return false;
    }

    public static byte[] c(Resource resource) throws IORuntimeException {
        return IoUtil.readBytes(resource.getStream());
    }

    public static String d(Resource resource, Charset charset) throws IORuntimeException {
        return IoUtil.read(resource.getReader(charset));
    }

    public static String e(Resource resource) throws IORuntimeException {
        return resource.readStr(CharsetUtil.CHARSET_UTF_8);
    }

    public static void f(Resource resource, OutputStream outputStream) throws IOException, IORuntimeException {
        try {
            InputStream stream = resource.getStream();
            try {
                IoUtil.copy(stream, outputStream);
                if (stream != null) {
                    stream.close();
                }
            } finally {
            }
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }
}
