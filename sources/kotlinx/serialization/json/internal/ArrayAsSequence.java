package kotlinx.serialization.json.internal;

import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\r\n\u0000\n\u0002\u0010\u0019\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\f\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0011\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0006H\u0096\u0002J\u0018\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u0006H\u0016J\u0016\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u0006J\u000e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0006R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u00020\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n¨\u0006\u0016"}, d2 = {"Lkotlinx/serialization/json/internal/ArrayAsSequence;", "", "buffer", "", "([C)V", SessionDescription.ATTR_LENGTH, "", "getLength", "()I", "setLength", "(I)V", "get", "", "index", "subSequence", "startIndex", "endIndex", "substring", "", "trim", "", "newSize", "kotlinx-serialization-json"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes8.dex */
public final class ArrayAsSequence implements CharSequence {

    @NotNull
    private final char[] buffer;
    private int length;

    public ArrayAsSequence(@NotNull char[] buffer) {
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        this.buffer = buffer;
        this.length = buffer.length;
    }

    @Override // java.lang.CharSequence
    public final /* bridge */ char charAt(int i2) {
        return get(i2);
    }

    public char get(int index) {
        return this.buffer[index];
    }

    public int getLength() {
        return this.length;
    }

    @Override // java.lang.CharSequence
    public final /* bridge */ int length() {
        return getLength();
    }

    public void setLength(int i2) {
        this.length = i2;
    }

    @Override // java.lang.CharSequence
    @NotNull
    public CharSequence subSequence(int startIndex, int endIndex) {
        return StringsKt__StringsJVMKt.concatToString(this.buffer, startIndex, Math.min(endIndex, length()));
    }

    @NotNull
    public final String substring(int startIndex, int endIndex) {
        return StringsKt__StringsJVMKt.concatToString(this.buffer, startIndex, Math.min(endIndex, length()));
    }

    public final void trim(int newSize) {
        setLength(Math.min(this.buffer.length, newSize));
    }
}
