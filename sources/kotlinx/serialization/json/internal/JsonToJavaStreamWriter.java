package kotlinx.serialization.json.internal;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import io.socket.engineio.client.Socket;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt___RangesKt;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u0019\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\r\n\u0002\u0010\f\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0006\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0011\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\nH\u0082\bJ\u0018\u0010\u0012\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\nH\u0002J\b\u0010\u0015\u001a\u00020\fH\u0002J\b\u0010\u0016\u001a\u00020\fH\u0016J\t\u0010\u0017\u001a\u00020\nH\u0082\bJ\u0011\u0010\u0018\u001a\u00020\f2\u0006\u0010\u0019\u001a\u00020\nH\u0082\bJ\u0010\u0010\u0018\u001a\u00020\f2\u0006\u0010\u001a\u001a\u00020\u000fH\u0016J\u0010\u0010\u001b\u001a\u00020\f2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\u0010\u0010\u001e\u001a\u00020\f2\u0006\u0010\u001f\u001a\u00020 H\u0016J\u0010\u0010!\u001a\u00020\f2\u0006\u0010\u001a\u001a\u00020\u000fH\u0016J\u0018\u0010\"\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\b2\u0006\u0010#\u001a\u00020\nH\u0002J\u0010\u0010$\u001a\u00020\f2\u0006\u0010%\u001a\u00020\nH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006&"}, d2 = {"Lkotlinx/serialization/json/internal/JsonToJavaStreamWriter;", "Lkotlinx/serialization/json/internal/JsonWriter;", "stream", "Ljava/io/OutputStream;", "(Ljava/io/OutputStream;)V", "buffer", "", "charArray", "", "indexInBuffer", "", "appendStringSlowPath", "", "currentSize", TypedValues.Custom.S_STRING, "", "ensure", "bytesCount", "ensureTotalCapacity", "oldSize", "additional", Socket.EVENT_FLUSH, "release", "rest", "write", "byte", "text", "writeChar", "char", "", "writeLong", "value", "", "writeQuoted", "writeUtf8", "count", "writeUtf8CodePoint", "codePoint", "kotlinx-serialization-json"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nJvmJsonStreams.kt\nKotlin\n*S Kotlin\n*F\n+ 1 JvmJsonStreams.kt\nkotlinx/serialization/json/internal/JsonToJavaStreamWriter\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,268:1\n130#1:269\n117#1:271\n130#1:272\n118#1,3:273\n125#1,2:276\n130#1:278\n125#1,2:279\n117#1:281\n130#1:282\n118#1,3:283\n125#1,2:286\n125#1,2:288\n117#1:290\n130#1:291\n118#1,3:292\n125#1,2:295\n125#1,2:297\n125#1,2:299\n117#1:301\n130#1:302\n118#1,3:303\n125#1,2:306\n117#1:308\n130#1:309\n118#1,3:310\n125#1,2:313\n125#1,2:315\n125#1,2:317\n125#1,2:319\n117#1:321\n130#1:322\n118#1,3:323\n125#1,2:326\n117#1:328\n130#1:329\n118#1,3:330\n125#1,2:333\n125#1,2:335\n117#1:337\n130#1:338\n118#1,3:339\n125#1,2:342\n117#1:344\n130#1:345\n118#1,3:346\n125#1,2:349\n125#1,2:351\n125#1,2:353\n117#1:355\n130#1:356\n118#1,3:357\n125#1,2:360\n125#1,2:362\n125#1,2:364\n125#1,2:366\n1#2:270\n*S KotlinDebug\n*F\n+ 1 JvmJsonStreams.kt\nkotlinx/serialization/json/internal/JsonToJavaStreamWriter\n*L\n117#1:269\n148#1:271\n148#1:272\n148#1:273,3\n149#1:276,2\n151#1:278\n158#1:279,2\n165#1:281\n165#1:282\n165#1:283,3\n166#1:286,2\n167#1:288,2\n173#1:290\n173#1:291\n173#1:292,3\n174#1:295,2\n175#1:297,2\n176#1:299,2\n186#1:301\n186#1:302\n186#1:303,3\n187#1:306,2\n196#1:308\n196#1:309\n196#1:310,3\n197#1:313,2\n198#1:315,2\n199#1:317,2\n200#1:319,2\n215#1:321\n215#1:322\n215#1:323,3\n216#1:326,2\n221#1:328\n221#1:329\n221#1:330,3\n222#1:333,2\n223#1:335,2\n228#1:337\n228#1:338\n228#1:339,3\n229#1:342,2\n234#1:344\n234#1:345\n234#1:346,3\n235#1:349,2\n236#1:351,2\n237#1:353,2\n242#1:355\n242#1:356\n242#1:357,3\n243#1:360,2\n244#1:362,2\n245#1:364,2\n246#1:366,2\n*E\n"})
/* loaded from: classes8.dex */
public final class JsonToJavaStreamWriter implements JsonWriter {

    @NotNull
    private final byte[] buffer;

    @NotNull
    private char[] charArray;
    private int indexInBuffer;

    @NotNull
    private final OutputStream stream;

    public JsonToJavaStreamWriter(@NotNull OutputStream stream) {
        Intrinsics.checkNotNullParameter(stream, "stream");
        this.stream = stream;
        this.buffer = ByteArrayPool.INSTANCE.take();
        this.charArray = CharArrayPool.INSTANCE.take();
    }

    private final void appendStringSlowPath(int currentSize, String string) throws IOException {
        byte b3;
        int length = string.length();
        for (int i2 = currentSize - 1; i2 < length; i2++) {
            int iEnsureTotalCapacity = ensureTotalCapacity(currentSize, 2);
            char cCharAt = string.charAt(i2);
            if (cCharAt >= StringOpsKt.getESCAPE_MARKERS().length || (b3 = StringOpsKt.getESCAPE_MARKERS()[cCharAt]) == 0) {
                int i3 = iEnsureTotalCapacity + 1;
                this.charArray[iEnsureTotalCapacity] = cCharAt;
                currentSize = i3;
            } else {
                if (b3 == 1) {
                    String str = StringOpsKt.getESCAPE_STRINGS()[cCharAt];
                    Intrinsics.checkNotNull(str);
                    int iEnsureTotalCapacity2 = ensureTotalCapacity(iEnsureTotalCapacity, str.length());
                    str.getChars(0, str.length(), this.charArray, iEnsureTotalCapacity2);
                    currentSize = iEnsureTotalCapacity2 + str.length();
                } else {
                    char[] cArr = this.charArray;
                    cArr[iEnsureTotalCapacity] = '\\';
                    cArr[iEnsureTotalCapacity + 1] = (char) b3;
                    currentSize = iEnsureTotalCapacity + 2;
                }
            }
        }
        ensureTotalCapacity(currentSize, 1);
        char[] cArr2 = this.charArray;
        cArr2[currentSize] = '\"';
        writeUtf8(cArr2, currentSize + 1);
        flush();
    }

    private final void ensure(int bytesCount) throws IOException {
        if (this.buffer.length - this.indexInBuffer < bytesCount) {
            flush();
        }
    }

    private final int ensureTotalCapacity(int oldSize, int additional) {
        int i2 = additional + oldSize;
        char[] cArr = this.charArray;
        if (cArr.length <= i2) {
            char[] cArrCopyOf = Arrays.copyOf(cArr, RangesKt___RangesKt.coerceAtLeast(i2, oldSize * 2));
            Intrinsics.checkNotNullExpressionValue(cArrCopyOf, "copyOf(this, newSize)");
            this.charArray = cArrCopyOf;
        }
        return oldSize;
    }

    private final void flush() throws IOException {
        this.stream.write(this.buffer, 0, this.indexInBuffer);
        this.indexInBuffer = 0;
    }

    private final int rest() {
        return this.buffer.length - this.indexInBuffer;
    }

    private final void writeUtf8(char[] string, int count) throws IOException {
        if (!(count >= 0)) {
            throw new IllegalArgumentException("count < 0".toString());
        }
        if (!(count <= string.length)) {
            throw new IllegalArgumentException(("count > string.length: " + count + " > " + string.length).toString());
        }
        int i2 = 0;
        while (i2 < count) {
            char c3 = string[i2];
            if (c3 < 128) {
                if (this.buffer.length - this.indexInBuffer < 1) {
                    flush();
                }
                byte[] bArr = this.buffer;
                int i3 = this.indexInBuffer;
                int i4 = i3 + 1;
                this.indexInBuffer = i4;
                bArr[i3] = (byte) c3;
                i2++;
                int iMin = Math.min(count, (bArr.length - i4) + i2);
                while (i2 < iMin) {
                    char c4 = string[i2];
                    if (c4 < 128) {
                        byte[] bArr2 = this.buffer;
                        int i5 = this.indexInBuffer;
                        this.indexInBuffer = i5 + 1;
                        bArr2[i5] = (byte) c4;
                        i2++;
                    }
                }
            } else {
                if (c3 < 2048) {
                    if (this.buffer.length - this.indexInBuffer < 2) {
                        flush();
                    }
                    byte[] bArr3 = this.buffer;
                    int i6 = this.indexInBuffer;
                    int i7 = i6 + 1;
                    bArr3[i6] = (byte) ((c3 >> 6) | 192);
                    this.indexInBuffer = i7 + 1;
                    bArr3[i7] = (byte) ((c3 & '?') | 128);
                } else if (c3 < 55296 || c3 > 57343) {
                    if (this.buffer.length - this.indexInBuffer < 3) {
                        flush();
                    }
                    byte[] bArr4 = this.buffer;
                    int i8 = this.indexInBuffer;
                    int i9 = i8 + 1;
                    bArr4[i8] = (byte) ((c3 >> '\f') | 224);
                    int i10 = i9 + 1;
                    bArr4[i9] = (byte) (((c3 >> 6) & 63) | 128);
                    this.indexInBuffer = i10 + 1;
                    bArr4[i10] = (byte) ((c3 & '?') | 128);
                } else {
                    int i11 = i2 + 1;
                    char c5 = i11 < count ? string[i11] : (char) 0;
                    if (c3 <= 56319) {
                        if (56320 <= c5 && c5 < 57344) {
                            int i12 = (((c3 & 1023) << 10) | (c5 & 1023)) + 65536;
                            if (this.buffer.length - this.indexInBuffer < 4) {
                                flush();
                            }
                            byte[] bArr5 = this.buffer;
                            int i13 = this.indexInBuffer;
                            int i14 = i13 + 1;
                            bArr5[i13] = (byte) ((i12 >> 18) | 240);
                            int i15 = i14 + 1;
                            bArr5[i14] = (byte) (((i12 >> 12) & 63) | 128);
                            int i16 = i15 + 1;
                            bArr5[i15] = (byte) (((i12 >> 6) & 63) | 128);
                            this.indexInBuffer = i16 + 1;
                            bArr5[i16] = (byte) ((i12 & 63) | 128);
                            i2 += 2;
                        }
                    }
                    if (this.buffer.length - this.indexInBuffer < 1) {
                        flush();
                    }
                    byte[] bArr6 = this.buffer;
                    int i17 = this.indexInBuffer;
                    this.indexInBuffer = i17 + 1;
                    bArr6[i17] = (byte) 63;
                    i2 = i11;
                }
                i2++;
            }
        }
    }

    private final void writeUtf8CodePoint(int codePoint) throws IOException {
        if (codePoint < 128) {
            if (this.buffer.length - this.indexInBuffer < 1) {
                flush();
            }
            byte[] bArr = this.buffer;
            int i2 = this.indexInBuffer;
            this.indexInBuffer = i2 + 1;
            bArr[i2] = (byte) codePoint;
            return;
        }
        if (codePoint < 2048) {
            if (this.buffer.length - this.indexInBuffer < 2) {
                flush();
            }
            byte[] bArr2 = this.buffer;
            int i3 = this.indexInBuffer;
            int i4 = i3 + 1;
            bArr2[i3] = (byte) ((codePoint >> 6) | 192);
            this.indexInBuffer = i4 + 1;
            bArr2[i4] = (byte) ((codePoint & 63) | 128);
            return;
        }
        boolean z2 = false;
        if (55296 <= codePoint && codePoint < 57344) {
            z2 = true;
        }
        if (z2) {
            if (this.buffer.length - this.indexInBuffer < 1) {
                flush();
            }
            byte[] bArr3 = this.buffer;
            int i5 = this.indexInBuffer;
            this.indexInBuffer = i5 + 1;
            bArr3[i5] = (byte) 63;
            return;
        }
        if (codePoint < 65536) {
            if (this.buffer.length - this.indexInBuffer < 3) {
                flush();
            }
            byte[] bArr4 = this.buffer;
            int i6 = this.indexInBuffer;
            int i7 = i6 + 1;
            bArr4[i6] = (byte) ((codePoint >> 12) | 224);
            int i8 = i7 + 1;
            bArr4[i7] = (byte) (((codePoint >> 6) & 63) | 128);
            this.indexInBuffer = i8 + 1;
            bArr4[i8] = (byte) ((codePoint & 63) | 128);
            return;
        }
        if (codePoint > 1114111) {
            throw new JsonEncodingException("Unexpected code point: " + codePoint);
        }
        if (this.buffer.length - this.indexInBuffer < 4) {
            flush();
        }
        byte[] bArr5 = this.buffer;
        int i9 = this.indexInBuffer;
        int i10 = i9 + 1;
        bArr5[i9] = (byte) ((codePoint >> 18) | 240);
        int i11 = i10 + 1;
        bArr5[i10] = (byte) (((codePoint >> 12) & 63) | 128);
        int i12 = i11 + 1;
        bArr5[i11] = (byte) (((codePoint >> 6) & 63) | 128);
        this.indexInBuffer = i12 + 1;
        bArr5[i12] = (byte) ((codePoint & 63) | 128);
    }

    @Override // kotlinx.serialization.json.internal.JsonWriter
    public void release() throws IOException {
        flush();
        CharArrayPool.INSTANCE.release(this.charArray);
        ByteArrayPool.INSTANCE.release(this.buffer);
    }

    @Override // kotlinx.serialization.json.internal.JsonWriter
    public void write(@NotNull String text) throws IOException {
        Intrinsics.checkNotNullParameter(text, "text");
        int length = text.length();
        ensureTotalCapacity(0, length);
        text.getChars(0, length, this.charArray, 0);
        writeUtf8(this.charArray, length);
    }

    @Override // kotlinx.serialization.json.internal.JsonWriter
    public void writeChar(char c3) throws IOException {
        writeUtf8CodePoint(c3);
    }

    @Override // kotlinx.serialization.json.internal.JsonWriter
    public void writeLong(long value) throws IOException {
        write(String.valueOf(value));
    }

    @Override // kotlinx.serialization.json.internal.JsonWriter
    public void writeQuoted(@NotNull String text) throws IOException {
        Intrinsics.checkNotNullParameter(text, "text");
        ensureTotalCapacity(0, text.length() + 2);
        char[] cArr = this.charArray;
        cArr[0] = '\"';
        int length = text.length();
        text.getChars(0, length, cArr, 1);
        int i2 = length + 1;
        for (int i3 = 1; i3 < i2; i3++) {
            char c3 = cArr[i3];
            if (c3 < StringOpsKt.getESCAPE_MARKERS().length && StringOpsKt.getESCAPE_MARKERS()[c3] != 0) {
                appendStringSlowPath(i3, text);
                return;
            }
        }
        cArr[i2] = '\"';
        writeUtf8(cArr, length + 2);
        flush();
    }

    private final void write(int i2) {
        byte[] bArr = this.buffer;
        int i3 = this.indexInBuffer;
        this.indexInBuffer = i3 + 1;
        bArr[i3] = (byte) i2;
    }
}
