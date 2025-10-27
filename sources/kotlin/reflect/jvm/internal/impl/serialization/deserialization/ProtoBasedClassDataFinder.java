package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt___RangesKt;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.BinaryVersion;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension({"SMAP\nProtoBasedClassDataFinder.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ProtoBasedClassDataFinder.kt\norg/jetbrains/kotlin/serialization/deserialization/ProtoBasedClassDataFinder\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,43:1\n1194#2,2:44\n1222#2,4:46\n*S KotlinDebug\n*F\n+ 1 ProtoBasedClassDataFinder.kt\norg/jetbrains/kotlin/serialization/deserialization/ProtoBasedClassDataFinder\n*L\n32#1:44,2\n32#1:46,4\n*E\n"})
/* loaded from: classes8.dex */
public final class ProtoBasedClassDataFinder implements ClassDataFinder {

    @NotNull
    private final Map<ClassId, ProtoBuf.Class> classIdToProto;

    @NotNull
    private final Function1<ClassId, SourceElement> classSource;

    @NotNull
    private final BinaryVersion metadataVersion;

    @NotNull
    private final NameResolver nameResolver;

    /* JADX WARN: Multi-variable type inference failed */
    public ProtoBasedClassDataFinder(@NotNull ProtoBuf.PackageFragment proto, @NotNull NameResolver nameResolver, @NotNull BinaryVersion metadataVersion, @NotNull Function1<? super ClassId, ? extends SourceElement> classSource) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(metadataVersion, "metadataVersion");
        Intrinsics.checkNotNullParameter(classSource, "classSource");
        this.nameResolver = nameResolver;
        this.metadataVersion = metadataVersion;
        this.classSource = classSource;
        List<ProtoBuf.Class> class_List = proto.getClass_List();
        Intrinsics.checkNotNullExpressionValue(class_List, "proto.class_List");
        List<ProtoBuf.Class> list = class_List;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt___RangesKt.coerceAtLeast(MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10)), 16));
        for (Object obj : list) {
            linkedHashMap.put(NameResolverUtilKt.getClassId(this.nameResolver, ((ProtoBuf.Class) obj).getFqName()), obj);
        }
        this.classIdToProto = linkedHashMap;
    }

    @Override // kotlin.reflect.jvm.internal.impl.serialization.deserialization.ClassDataFinder
    @Nullable
    public ClassData findClassData(@NotNull ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        ProtoBuf.Class r02 = this.classIdToProto.get(classId);
        if (r02 == null) {
            return null;
        }
        return new ClassData(this.nameResolver, r02, this.metadataVersion, this.classSource.invoke(classId));
    }

    @NotNull
    public final Collection<ClassId> getAllClassIds() {
        return this.classIdToProto.keySet();
    }
}
