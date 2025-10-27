package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import java.util.Set;
import kotlin.collections.SetsKt__SetsJVMKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.jvm.internal.impl.builtins.StandardNames;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.ClassDeserializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension({"SMAP\nClassDeserializer.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ClassDeserializer.kt\norg/jetbrains/kotlin/serialization/deserialization/ClassDeserializer\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,91:1\n1#2:92\n288#3,2:93\n*S KotlinDebug\n*F\n+ 1 ClassDeserializer.kt\norg/jetbrains/kotlin/serialization/deserialization/ClassDeserializer\n*L\n57#1:93,2\n*E\n"})
/* loaded from: classes8.dex */
public final class ClassDeserializer {

    @NotNull
    private final Function1<ClassKey, ClassDescriptor> classes;

    @NotNull
    private final DeserializationComponents components;

    @NotNull
    public static final Companion Companion = new Companion(null);

    @NotNull
    private static final Set<ClassId> BLACK_LIST = SetsKt__SetsJVMKt.setOf(ClassId.topLevel(StandardNames.FqNames.cloneable.toSafe()));

    public static final class ClassKey {

        @Nullable
        private final ClassData classData;

        @NotNull
        private final ClassId classId;

        public ClassKey(@NotNull ClassId classId, @Nullable ClassData classData) {
            Intrinsics.checkNotNullParameter(classId, "classId");
            this.classId = classId;
            this.classData = classData;
        }

        public boolean equals(@Nullable Object obj) {
            return (obj instanceof ClassKey) && Intrinsics.areEqual(this.classId, ((ClassKey) obj).classId);
        }

        @Nullable
        public final ClassData getClassData() {
            return this.classData;
        }

        @NotNull
        public final ClassId getClassId() {
            return this.classId;
        }

        public int hashCode() {
            return this.classId.hashCode();
        }
    }

    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final Set<ClassId> getBLACK_LIST() {
            return ClassDeserializer.BLACK_LIST;
        }
    }

    public ClassDeserializer(@NotNull DeserializationComponents components) {
        Intrinsics.checkNotNullParameter(components, "components");
        this.components = components;
        this.classes = components.getStorageManager().createMemoizedFunctionWithNullableValues(new Function1<ClassKey, ClassDescriptor>() { // from class: kotlin.reflect.jvm.internal.impl.serialization.deserialization.ClassDeserializer$classes$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            @Nullable
            public final ClassDescriptor invoke(@NotNull ClassDeserializer.ClassKey key) {
                Intrinsics.checkNotNullParameter(key, "key");
                return this.this$0.createClass(key);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00b6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor createClass(kotlin.reflect.jvm.internal.impl.serialization.deserialization.ClassDeserializer.ClassKey r13) {
        /*
            Method dump skipped, instructions count: 243
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.serialization.deserialization.ClassDeserializer.createClass(kotlin.reflect.jvm.internal.impl.serialization.deserialization.ClassDeserializer$ClassKey):kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor");
    }

    public static /* synthetic */ ClassDescriptor deserializeClass$default(ClassDeserializer classDeserializer, ClassId classId, ClassData classData, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            classData = null;
        }
        return classDeserializer.deserializeClass(classId, classData);
    }

    @Nullable
    public final ClassDescriptor deserializeClass(@NotNull ClassId classId, @Nullable ClassData classData) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        return this.classes.invoke(new ClassKey(classId, classData));
    }
}
