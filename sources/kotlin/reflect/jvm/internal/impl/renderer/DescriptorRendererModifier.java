package kotlin.reflect.jvm.internal.impl.renderer;

import java.util.ArrayList;
import java.util.Set;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension({"SMAP\nDescriptorRenderer.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DescriptorRenderer.kt\norg/jetbrains/kotlin/renderer/DescriptorRendererModifier\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,318:1\n3792#2:319\n4307#2,2:320\n*S KotlinDebug\n*F\n+ 1 DescriptorRenderer.kt\norg/jetbrains/kotlin/renderer/DescriptorRendererModifier\n*L\n312#1:319\n312#1:320,2\n*E\n"})
/* loaded from: classes8.dex */
public enum DescriptorRendererModifier {
    VISIBILITY(true),
    MODALITY(true),
    OVERRIDE(true),
    ANNOTATIONS(false),
    INNER(true),
    MEMBER_KIND(true),
    DATA(true),
    INLINE(true),
    EXPECT(true),
    ACTUAL(true),
    CONST(true),
    LATEINIT(true),
    FUN(true),
    VALUE(true);


    @JvmField
    @NotNull
    public static final Set<DescriptorRendererModifier> ALL;

    @JvmField
    @NotNull
    public static final Set<DescriptorRendererModifier> ALL_EXCEPT_ANNOTATIONS;

    @NotNull
    public static final Companion Companion = new Companion(null);
    private final boolean includeByDefault;

    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        DescriptorRendererModifier[] descriptorRendererModifierArrValues = values();
        ArrayList arrayList = new ArrayList();
        for (DescriptorRendererModifier descriptorRendererModifier : descriptorRendererModifierArrValues) {
            if (descriptorRendererModifier.includeByDefault) {
                arrayList.add(descriptorRendererModifier);
            }
        }
        ALL_EXCEPT_ANNOTATIONS = CollectionsKt___CollectionsKt.toSet(arrayList);
        ALL = ArraysKt___ArraysKt.toSet(values());
    }

    DescriptorRendererModifier(boolean z2) {
        this.includeByDefault = z2;
    }
}
