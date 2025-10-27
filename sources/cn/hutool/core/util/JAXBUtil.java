package cn.hutool.core.util;

import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

/* loaded from: classes.dex */
public class JAXBUtil {
    public static String beanToXml(Object obj) {
        return beanToXml(obj, CharsetUtil.CHARSET_UTF_8, true);
    }

    public static <T> T xmlToBean(String str, Class<T> cls) {
        return (T) xmlToBean(StrUtil.getReader(str), cls);
    }

    public static String beanToXml(Object obj, Charset charset, boolean z2) {
        try {
            Marshaller marshallerCreateMarshaller = JAXBContext.newInstance(new Class[]{obj.getClass()}).createMarshaller();
            marshallerCreateMarshaller.setProperty("jaxb.formatted.output", Boolean.valueOf(z2));
            marshallerCreateMarshaller.setProperty("jaxb.encoding", charset.name());
            StringWriter stringWriter = new StringWriter();
            marshallerCreateMarshaller.marshal(obj, stringWriter);
            return stringWriter.toString();
        } catch (Exception e2) {
            throw new UtilException("convertToXml 错误：" + e2.getMessage(), e2);
        }
    }

    public static <T> T xmlToBean(File file, Charset charset, Class<T> cls) {
        return (T) xmlToBean(FileUtil.getReader(file, charset), cls);
    }

    public static <T> T xmlToBean(Reader reader, Class<T> cls) throws IOException {
        try {
            try {
                return (T) JAXBContext.newInstance(new Class[]{cls}).createUnmarshaller().unmarshal(reader);
            } catch (Exception e2) {
                throw new RuntimeException("convertToJava2 错误：" + e2.getMessage(), e2);
            }
        } finally {
            IoUtil.close((Closeable) reader);
        }
    }
}
