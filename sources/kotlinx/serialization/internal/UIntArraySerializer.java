package kotlinx.serialization.internal;

import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.UInt;
import kotlin.UIntArray;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.serialization.ExperimentalSerializationApi;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.builtins.BuiltinSerializersKt;
import kotlinx.serialization.encoding.CompositeDecoder;
import kotlinx.serialization.encoding.CompositeEncoder;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\bÁ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u0014\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00050\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0006J\u0018\u0010\u0007\u001a\u00020\u0002H\u0014ø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\u0004\b\b\u0010\tJ(\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u0012H\u0014J-\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u000fH\u0014ø\u0001\u0001ø\u0001\u0002¢\u0006\u0004\b\u0018\u0010\u0019J\u0019\u0010\u001a\u001a\u00020\u000f*\u00020\u0002H\u0014ø\u0001\u0001ø\u0001\u0002¢\u0006\u0004\b\u001b\u0010\u001cJ\u0019\u0010\u001d\u001a\u00020\u0005*\u00020\u0002H\u0014ø\u0001\u0001ø\u0001\u0002¢\u0006\u0004\b\u001e\u0010\u001fø\u0001\u0002\u0082\u0002\u000f\n\u0002\b!\n\u0005\b¡\u001e0\u0001\n\u0002\b\u0019¨\u0006 "}, d2 = {"Lkotlinx/serialization/internal/UIntArraySerializer;", "Lkotlinx/serialization/KSerializer;", "Lkotlin/UIntArray;", "Lkotlinx/serialization/internal/PrimitiveArraySerializer;", "Lkotlin/UInt;", "Lkotlinx/serialization/internal/UIntArrayBuilder;", "()V", "empty", "empty--hP7Qyg", "()[I", "readElement", "", "decoder", "Lkotlinx/serialization/encoding/CompositeDecoder;", "index", "", "builder", "checkIndex", "", "writeContent", "encoder", "Lkotlinx/serialization/encoding/CompositeEncoder;", "content", DatabaseManager.SIZE, "writeContent-CPlH8fI", "(Lkotlinx/serialization/encoding/CompositeEncoder;[II)V", "collectionSize", "collectionSize--ajY-9A", "([I)I", "toBuilder", "toBuilder--ajY-9A", "([I)Lkotlinx/serialization/internal/UIntArrayBuilder;", "kotlinx-serialization-core"}, k = 1, mv = {1, 8, 0}, xi = 48)
@ExperimentalSerializationApi
@ExperimentalUnsignedTypes
@PublishedApi
/* loaded from: classes8.dex */
public final class UIntArraySerializer extends PrimitiveArraySerializer<UInt, UIntArray, UIntArrayBuilder> implements KSerializer<UIntArray> {

    @NotNull
    public static final UIntArraySerializer INSTANCE = new UIntArraySerializer();

    private UIntArraySerializer() {
        super(BuiltinSerializersKt.serializer(UInt.INSTANCE));
    }

    @Override // kotlinx.serialization.internal.AbstractCollectionSerializer
    public /* bridge */ /* synthetic */ int collectionSize(Object obj) {
        return m2390collectionSizeajY9A(((UIntArray) obj).getStorage());
    }

    /* renamed from: collectionSize--ajY-9A, reason: not valid java name */
    public int m2390collectionSizeajY9A(@NotNull int[] collectionSize) {
        Intrinsics.checkNotNullParameter(collectionSize, "$this$collectionSize");
        return UIntArray.m939getSizeimpl(collectionSize);
    }

    @Override // kotlinx.serialization.internal.PrimitiveArraySerializer
    public /* bridge */ /* synthetic */ UIntArray empty() {
        return UIntArray.m931boximpl(m2391emptyhP7Qyg());
    }

    @NotNull
    /* renamed from: empty--hP7Qyg, reason: not valid java name */
    public int[] m2391emptyhP7Qyg() {
        return UIntArray.m932constructorimpl(0);
    }

    @Override // kotlinx.serialization.internal.AbstractCollectionSerializer
    public /* bridge */ /* synthetic */ Object toBuilder(Object obj) {
        return m2392toBuilderajY9A(((UIntArray) obj).getStorage());
    }

    @NotNull
    /* renamed from: toBuilder--ajY-9A, reason: not valid java name */
    public UIntArrayBuilder m2392toBuilderajY9A(@NotNull int[] toBuilder) {
        Intrinsics.checkNotNullParameter(toBuilder, "$this$toBuilder");
        return new UIntArrayBuilder(toBuilder, null);
    }

    @Override // kotlinx.serialization.internal.PrimitiveArraySerializer
    public /* bridge */ /* synthetic */ void writeContent(CompositeEncoder compositeEncoder, UIntArray uIntArray, int i2) {
        m2393writeContentCPlH8fI(compositeEncoder, uIntArray.getStorage(), i2);
    }

    /* renamed from: writeContent-CPlH8fI, reason: not valid java name */
    public void m2393writeContentCPlH8fI(@NotNull CompositeEncoder encoder, @NotNull int[] content, int size) {
        Intrinsics.checkNotNullParameter(encoder, "encoder");
        Intrinsics.checkNotNullParameter(content, "content");
        for (int i2 = 0; i2 < size; i2++) {
            encoder.encodeInlineElement(getDescriptor(), i2).encodeInt(UIntArray.m938getpVg5ArA(content, i2));
        }
    }

    @Override // kotlinx.serialization.internal.PrimitiveArraySerializer
    public void readElement(@NotNull CompositeDecoder decoder, int index, @NotNull UIntArrayBuilder builder, boolean checkIndex) {
        Intrinsics.checkNotNullParameter(decoder, "decoder");
        Intrinsics.checkNotNullParameter(builder, "builder");
        builder.m2388appendWZ4Q5Ns$kotlinx_serialization_core(UInt.m878constructorimpl(decoder.decodeInlineElement(getDescriptor(), index).decodeInt()));
    }
}
