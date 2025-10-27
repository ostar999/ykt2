package kotlinx.serialization.json;

import com.google.android.exoplayer2.text.ttml.TtmlNode;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.json.internal.StringOpsKt;
import kotlinx.serialization.json.internal.SuppressAnimalSniffer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import r1.a;

@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B#\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\u0013\u0010\u0010\u001a\u00020\u00052\b\u0010\u0011\u001a\u0004\u0018\u00010\u0003H\u0096\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0017J\b\u0010\u0014\u001a\u00020\fH\u0016R\u0016\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u000f¨\u0006\u0015"}, d2 = {"Lkotlinx/serialization/json/JsonLiteral;", "Lkotlinx/serialization/json/JsonPrimitive;", TtmlNode.TAG_BODY, "", "isString", "", "coerceToInlineType", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "(Ljava/lang/Object;ZLkotlinx/serialization/descriptors/SerialDescriptor;)V", "getCoerceToInlineType$kotlinx_serialization_json", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "content", "", "getContent", "()Ljava/lang/String;", "()Z", "equals", "other", "hashCode", "", "toString", "kotlinx-serialization-json"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nJsonElement.kt\nKotlin\n*S Kotlin\n*F\n+ 1 JsonElement.kt\nkotlinx/serialization/json/JsonLiteral\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,296:1\n1#2:297\n*E\n"})
/* loaded from: classes8.dex */
public final class JsonLiteral extends JsonPrimitive {

    @Nullable
    private final SerialDescriptor coerceToInlineType;

    @NotNull
    private final String content;
    private final boolean isString;

    public /* synthetic */ JsonLiteral(Object obj, boolean z2, SerialDescriptor serialDescriptor, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(obj, z2, (i2 & 4) != 0 ? null : serialDescriptor);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || JsonLiteral.class != other.getClass()) {
            return false;
        }
        JsonLiteral jsonLiteral = (JsonLiteral) other;
        return getIsString() == jsonLiteral.getIsString() && Intrinsics.areEqual(getContent(), jsonLiteral.getContent());
    }

    @Nullable
    /* renamed from: getCoerceToInlineType$kotlinx_serialization_json, reason: from getter */
    public final SerialDescriptor getCoerceToInlineType() {
        return this.coerceToInlineType;
    }

    @Override // kotlinx.serialization.json.JsonPrimitive
    @NotNull
    public String getContent() {
        return this.content;
    }

    @SuppressAnimalSniffer
    public int hashCode() {
        return (a.a(getIsString()) * 31) + getContent().hashCode();
    }

    @Override // kotlinx.serialization.json.JsonPrimitive
    /* renamed from: isString, reason: from getter */
    public boolean getIsString() {
        return this.isString;
    }

    @Override // kotlinx.serialization.json.JsonPrimitive
    @NotNull
    public String toString() {
        if (!getIsString()) {
            return getContent();
        }
        StringBuilder sb = new StringBuilder();
        StringOpsKt.printQuoted(sb, getContent());
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JsonLiteral(@NotNull Object body, boolean z2, @Nullable SerialDescriptor serialDescriptor) {
        super(null);
        Intrinsics.checkNotNullParameter(body, "body");
        this.isString = z2;
        this.coerceToInlineType = serialDescriptor;
        this.content = body.toString();
        if (serialDescriptor != null && !serialDescriptor.isInline()) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
    }
}
