package cn.hutool.core.util;

import cn.hutool.core.io.FastByteArrayOutputStream;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.ValidateObjectInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;

/* loaded from: classes.dex */
public class SerializeUtil {
    public static <T> T clone(T t2) {
        if (t2 instanceof Serializable) {
            return (T) deserialize(serialize(t2), new Class[0]);
        }
        return null;
    }

    public static <T> T deserialize(byte[] bArr, Class<?>... clsArr) {
        try {
            return (T) IoUtil.readObj(new ValidateObjectInputStream(new ByteArrayInputStream(bArr), clsArr));
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static <T> byte[] serialize(T t2) throws IOException, IORuntimeException {
        if (!(t2 instanceof Serializable)) {
            return null;
        }
        FastByteArrayOutputStream fastByteArrayOutputStream = new FastByteArrayOutputStream();
        IoUtil.writeObjects(fastByteArrayOutputStream, false, (Serializable) t2);
        return fastByteArrayOutputStream.toByteArray();
    }
}
