package kotlinx.serialization.json.internal;

import kotlin.Metadata;
import kotlin.UByte;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.UShort;
import kotlin.jvm.internal.Intrinsics;
import net.lingala.zip4j.util.InternalZipConstants;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0005\n\u0002\u0010\b\n\u0002\u0010\t\n\u0002\u0010\n\n\u0000\b\u0001\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u000bH\u0016J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\fH\u0016J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\rH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lkotlinx/serialization/json/internal/ComposerForUnsignedNumbers;", "Lkotlinx/serialization/json/internal/Composer;", "writer", "Lkotlinx/serialization/json/internal/JsonWriter;", "forceQuoting", "", "(Lkotlinx/serialization/json/internal/JsonWriter;Z)V", "print", "", "v", "", "", "", "", "kotlinx-serialization-json"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SuppressAnimalSniffer
/* loaded from: classes8.dex */
public final class ComposerForUnsignedNumbers extends Composer {
    private final boolean forceQuoting;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ComposerForUnsignedNumbers(@NotNull JsonWriter writer, boolean z2) {
        super(writer);
        Intrinsics.checkNotNullParameter(writer, "writer");
        this.forceQuoting = z2;
    }

    @Override // kotlinx.serialization.json.internal.Composer
    public void print(int v2) {
        boolean z2 = this.forceQuoting;
        int iM878constructorimpl = UInt.m878constructorimpl(v2);
        if (z2) {
            printQuoted(Long.toString(iM878constructorimpl & InternalZipConstants.ZIP_64_LIMIT, 10));
        } else {
            print(Long.toString(iM878constructorimpl & InternalZipConstants.ZIP_64_LIMIT, 10));
        }
    }

    @Override // kotlinx.serialization.json.internal.Composer
    public void print(long v2) {
        boolean z2 = this.forceQuoting;
        long jM957constructorimpl = ULong.m957constructorimpl(v2);
        if (z2) {
            printQuoted(j.a(jM957constructorimpl, 10));
        } else {
            print(i.a(jM957constructorimpl, 10));
        }
    }

    @Override // kotlinx.serialization.json.internal.Composer
    public void print(byte v2) {
        boolean z2 = this.forceQuoting;
        String strM845toStringimpl = UByte.m845toStringimpl(UByte.m801constructorimpl(v2));
        if (z2) {
            printQuoted(strM845toStringimpl);
        } else {
            print(strM845toStringimpl);
        }
    }

    @Override // kotlinx.serialization.json.internal.Composer
    public void print(short v2) {
        boolean z2 = this.forceQuoting;
        String strM1108toStringimpl = UShort.m1108toStringimpl(UShort.m1064constructorimpl(v2));
        if (z2) {
            printQuoted(strM1108toStringimpl);
        } else {
            print(strM1108toStringimpl);
        }
    }
}
