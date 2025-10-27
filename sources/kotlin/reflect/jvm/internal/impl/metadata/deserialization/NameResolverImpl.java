package kotlin.reflect.jvm.internal.impl.metadata.deserialization;

import cn.hutool.core.text.StrPool;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import kotlin.Triple;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes8.dex */
public final class NameResolverImpl implements NameResolver {

    @NotNull
    private final ProtoBuf.QualifiedNameTable qualifiedNames;

    @NotNull
    private final ProtoBuf.StringTable strings;

    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ProtoBuf.QualifiedNameTable.QualifiedName.Kind.values().length];
            try {
                iArr[ProtoBuf.QualifiedNameTable.QualifiedName.Kind.CLASS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ProtoBuf.QualifiedNameTable.QualifiedName.Kind.PACKAGE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ProtoBuf.QualifiedNameTable.QualifiedName.Kind.LOCAL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public NameResolverImpl(@NotNull ProtoBuf.StringTable strings, @NotNull ProtoBuf.QualifiedNameTable qualifiedNames) {
        Intrinsics.checkNotNullParameter(strings, "strings");
        Intrinsics.checkNotNullParameter(qualifiedNames, "qualifiedNames");
        this.strings = strings;
        this.qualifiedNames = qualifiedNames;
    }

    private final Triple<List<String>, List<String>, Boolean> traverseIds(int i2) {
        LinkedList linkedList = new LinkedList();
        LinkedList linkedList2 = new LinkedList();
        boolean z2 = false;
        while (i2 != -1) {
            ProtoBuf.QualifiedNameTable.QualifiedName qualifiedName = this.qualifiedNames.getQualifiedName(i2);
            String string = this.strings.getString(qualifiedName.getShortName());
            ProtoBuf.QualifiedNameTable.QualifiedName.Kind kind = qualifiedName.getKind();
            Intrinsics.checkNotNull(kind);
            int i3 = WhenMappings.$EnumSwitchMapping$0[kind.ordinal()];
            if (i3 == 1) {
                linkedList2.addFirst(string);
            } else if (i3 == 2) {
                linkedList.addFirst(string);
            } else if (i3 == 3) {
                linkedList2.addFirst(string);
                z2 = true;
            }
            i2 = qualifiedName.getParentQualifiedName();
        }
        return new Triple<>(linkedList, linkedList2, Boolean.valueOf(z2));
    }

    @Override // kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver
    @NotNull
    public String getQualifiedClassName(int i2) throws IOException {
        Triple<List<String>, List<String>, Boolean> tripleTraverseIds = traverseIds(i2);
        List<String> listComponent1 = tripleTraverseIds.component1();
        String strJoinToString$default = CollectionsKt___CollectionsKt.joinToString$default(tripleTraverseIds.component2(), StrPool.DOT, null, null, 0, null, null, 62, null);
        if (listComponent1.isEmpty()) {
            return strJoinToString$default;
        }
        return CollectionsKt___CollectionsKt.joinToString$default(listComponent1, "/", null, null, 0, null, null, 62, null) + '/' + strJoinToString$default;
    }

    @Override // kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver
    @NotNull
    public String getString(int i2) {
        String string = this.strings.getString(i2);
        Intrinsics.checkNotNullExpressionValue(string, "strings.getString(index)");
        return string;
    }

    @Override // kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver
    public boolean isLocalClassName(int i2) {
        return traverseIds(i2).getThird().booleanValue();
    }
}
