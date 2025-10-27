package cn.hutool.core.io;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.CharsetUtil;
import com.google.zxing.common.StringUtils;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/* loaded from: classes.dex */
public class CharsetDetector {
    private static final Charset[] DEFAULT_CHARSETS = (Charset[]) Convert.convert(Charset[].class, (Object) new String[]{"UTF-8", CharsetUtil.GBK, StringUtils.GB2312, "GB18030", "UTF-16BE", "UTF-16LE", "UTF-16", "BIG5", "UNICODE", "US-ASCII"});

    public static Charset detect(File file, Charset... charsetArr) {
        return detect(FileUtil.getInputStream(file), charsetArr);
    }

    private static boolean identify(byte[] bArr, CharsetDecoder charsetDecoder) throws CharacterCodingException {
        try {
            charsetDecoder.decode(ByteBuffer.wrap(bArr));
            return true;
        } catch (CharacterCodingException unused) {
            return false;
        }
    }

    public static Charset detect(InputStream inputStream, Charset... charsetArr) {
        return detect(32768, inputStream, charsetArr);
    }

    public static Charset detect(int i2, InputStream inputStream, Charset... charsetArr) throws IOException {
        if (ArrayUtil.isEmpty((Object[]) charsetArr)) {
            charsetArr = DEFAULT_CHARSETS;
        }
        byte[] bArr = new byte[i2];
        while (inputStream.read(bArr) > -1) {
            try {
                try {
                    for (Charset charset : charsetArr) {
                        if (identify(bArr, charset.newDecoder())) {
                            return charset;
                        }
                    }
                } catch (IOException e2) {
                    throw new IORuntimeException(e2);
                }
            } finally {
                IoUtil.close((Closeable) inputStream);
            }
        }
        IoUtil.close((Closeable) inputStream);
        return null;
    }
}
