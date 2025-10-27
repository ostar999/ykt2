package kotlinx.serialization.json.internal;

import cn.hutool.core.text.StrPool;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.descriptors.StructureKind;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\b\u0000\u0018\u00002\u00020\u0001:\u0001\u001aB\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\n\u001a\u00020\u000bJ\u0006\u0010\f\u001a\u00020\rJ\u0012\u0010\u000e\u001a\u00020\u000b2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0001H\u0002J\u000e\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u0012J\u0006\u0010\u0013\u001a\u00020\rJ\b\u0010\u0014\u001a\u00020\rH\u0002J\b\u0010\u0015\u001a\u00020\u000bH\u0016J\u0010\u0010\u0016\u001a\u00020\r2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001J\u000e\u0010\u0018\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0006X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u0007R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lkotlinx/serialization/json/internal/JsonPath;", "", "()V", "currentDepth", "", "currentObjectPath", "", "[Ljava/lang/Object;", "indicies", "", "getPath", "", "popDescriptor", "", "prettyString", AdvanceSetting.NETWORK_TYPE, "pushDescriptor", "sd", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "resetCurrentMapKey", "resize", "toString", "updateCurrentMapKey", "key", "updateDescriptorIndex", "index", "Tombstone", "kotlinx-serialization-json"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes8.dex */
public final class JsonPath {
    private int currentDepth;

    @NotNull
    private Object[] currentObjectPath = new Object[8];

    @NotNull
    private int[] indicies;

    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lkotlinx/serialization/json/internal/JsonPath$Tombstone;", "", "()V", "kotlinx-serialization-json"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Tombstone {

        @NotNull
        public static final Tombstone INSTANCE = new Tombstone();

        private Tombstone() {
        }
    }

    public JsonPath() {
        int[] iArr = new int[8];
        for (int i2 = 0; i2 < 8; i2++) {
            iArr[i2] = -1;
        }
        this.indicies = iArr;
        this.currentDepth = -1;
    }

    private final String prettyString(Object it) {
        String serialName;
        SerialDescriptor serialDescriptor = it instanceof SerialDescriptor ? (SerialDescriptor) it : null;
        return (serialDescriptor == null || (serialName = serialDescriptor.getSerialName()) == null) ? String.valueOf(it) : serialName;
    }

    private final void resize() {
        int i2 = this.currentDepth * 2;
        Object[] objArrCopyOf = Arrays.copyOf(this.currentObjectPath, i2);
        Intrinsics.checkNotNullExpressionValue(objArrCopyOf, "copyOf(this, newSize)");
        this.currentObjectPath = objArrCopyOf;
        int[] iArrCopyOf = Arrays.copyOf(this.indicies, i2);
        Intrinsics.checkNotNullExpressionValue(iArrCopyOf, "copyOf(this, newSize)");
        this.indicies = iArrCopyOf;
    }

    @NotNull
    public final String getPath() {
        StringBuilder sb = new StringBuilder();
        sb.append("$");
        int i2 = this.currentDepth + 1;
        for (int i3 = 0; i3 < i2; i3++) {
            Object obj = this.currentObjectPath[i3];
            if (obj instanceof SerialDescriptor) {
                SerialDescriptor serialDescriptor = (SerialDescriptor) obj;
                if (!Intrinsics.areEqual(serialDescriptor.getKind(), StructureKind.LIST.INSTANCE)) {
                    int i4 = this.indicies[i3];
                    if (i4 >= 0) {
                        sb.append(StrPool.DOT);
                        sb.append(serialDescriptor.getElementName(i4));
                    }
                } else if (this.indicies[i3] != -1) {
                    sb.append(StrPool.BRACKET_START);
                    sb.append(this.indicies[i3]);
                    sb.append(StrPool.BRACKET_END);
                }
            } else if (obj != Tombstone.INSTANCE) {
                sb.append(StrPool.BRACKET_START);
                sb.append("'");
                sb.append(obj);
                sb.append("'");
                sb.append(StrPool.BRACKET_END);
            }
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }

    public final void popDescriptor() {
        int i2 = this.currentDepth;
        int[] iArr = this.indicies;
        if (iArr[i2] == -2) {
            iArr[i2] = -1;
            this.currentDepth = i2 - 1;
        }
        int i3 = this.currentDepth;
        if (i3 != -1) {
            this.currentDepth = i3 - 1;
        }
    }

    public final void pushDescriptor(@NotNull SerialDescriptor sd) {
        Intrinsics.checkNotNullParameter(sd, "sd");
        int i2 = this.currentDepth + 1;
        this.currentDepth = i2;
        if (i2 == this.currentObjectPath.length) {
            resize();
        }
        this.currentObjectPath[i2] = sd;
    }

    public final void resetCurrentMapKey() {
        int[] iArr = this.indicies;
        int i2 = this.currentDepth;
        if (iArr[i2] == -2) {
            this.currentObjectPath[i2] = Tombstone.INSTANCE;
        }
    }

    @NotNull
    public String toString() {
        return getPath();
    }

    public final void updateCurrentMapKey(@Nullable Object key) {
        int[] iArr = this.indicies;
        int i2 = this.currentDepth;
        if (iArr[i2] != -2) {
            int i3 = i2 + 1;
            this.currentDepth = i3;
            if (i3 == this.currentObjectPath.length) {
                resize();
            }
        }
        Object[] objArr = this.currentObjectPath;
        int i4 = this.currentDepth;
        objArr[i4] = key;
        this.indicies[i4] = -2;
    }

    public final void updateDescriptorIndex(int index) {
        this.indicies[this.currentDepth] = index;
    }
}
