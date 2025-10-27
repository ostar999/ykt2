package cn.hutool.core.io;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

/* loaded from: classes.dex */
public class BufferUtil {
    public static ByteBuffer copy(ByteBuffer byteBuffer, int i2, int i3) {
        return copy(byteBuffer, ByteBuffer.allocate(i3 - i2));
    }

    public static ByteBuffer create(byte[] bArr) {
        return ByteBuffer.wrap(bArr);
    }

    public static CharBuffer createCharBuffer(int i2) {
        return CharBuffer.allocate(i2);
    }

    public static ByteBuffer createUtf8(CharSequence charSequence) {
        return create(CharSequenceUtil.utf8Bytes(charSequence));
    }

    public static int lineEnd(ByteBuffer byteBuffer) {
        return lineEnd(byteBuffer, byteBuffer.remaining());
    }

    public static byte[] readBytes(ByteBuffer byteBuffer) {
        byte[] bArr = new byte[byteBuffer.remaining()];
        byteBuffer.get(bArr);
        return bArr;
    }

    public static String readLine(ByteBuffer byteBuffer, Charset charset) {
        int iPosition = byteBuffer.position();
        int iLineEnd = lineEnd(byteBuffer);
        if (iLineEnd > iPosition) {
            return StrUtil.str(readBytes(byteBuffer, iPosition, iLineEnd), charset);
        }
        if (iLineEnd == iPosition) {
            return "";
        }
        return null;
    }

    public static String readStr(ByteBuffer byteBuffer, Charset charset) {
        return StrUtil.str(readBytes(byteBuffer), charset);
    }

    public static String readUtf8Str(ByteBuffer byteBuffer) {
        return readStr(byteBuffer, CharsetUtil.CHARSET_UTF_8);
    }

    public static ByteBuffer copy(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) {
        return copy(byteBuffer, byteBuffer2, Math.min(byteBuffer.limit(), byteBuffer2.remaining()));
    }

    public static ByteBuffer create(CharSequence charSequence, Charset charset) {
        return create(CharSequenceUtil.bytes(charSequence, charset));
    }

    public static int lineEnd(ByteBuffer byteBuffer, int i2) {
        int iPosition = byteBuffer.position();
        int i3 = iPosition;
        boolean z2 = false;
        while (byteBuffer.hasRemaining()) {
            byte b3 = byteBuffer.get();
            i3++;
            if (b3 == 13) {
                z2 = true;
            } else {
                if (b3 == 10) {
                    return z2 ? i3 - 2 : i3 - 1;
                }
                z2 = false;
            }
            if (i3 - iPosition > i2) {
                byteBuffer.position(iPosition);
                throw new IndexOutOfBoundsException(CharSequenceUtil.format("Position is out of maxLength: {}", Integer.valueOf(i2)));
            }
        }
        byteBuffer.position(iPosition);
        return -1;
    }

    public static ByteBuffer copy(ByteBuffer byteBuffer, ByteBuffer byteBuffer2, int i2) {
        return copy(byteBuffer, byteBuffer.position(), byteBuffer2, byteBuffer2.position(), i2);
    }

    public static ByteBuffer copy(ByteBuffer byteBuffer, int i2, ByteBuffer byteBuffer2, int i3, int i4) {
        System.arraycopy(byteBuffer.array(), i2, byteBuffer2.array(), i3, i4);
        return byteBuffer2;
    }

    public static byte[] readBytes(ByteBuffer byteBuffer, int i2) {
        int iRemaining = byteBuffer.remaining();
        if (i2 > iRemaining) {
            i2 = iRemaining;
        }
        byte[] bArr = new byte[i2];
        byteBuffer.get(bArr);
        return bArr;
    }

    public static byte[] readBytes(ByteBuffer byteBuffer, int i2, int i3) {
        int i4 = i3 - i2;
        byte[] bArr = new byte[i4];
        System.arraycopy(byteBuffer.array(), i2, bArr, 0, i4);
        return bArr;
    }
}
