package kotlin.reflect.jvm.internal.impl.name;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt__StringsJVMKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension({"SMAP\nFqNamesUtil.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FqNamesUtil.kt\norg/jetbrains/kotlin/name/FqNamesUtilKt\n+ 2 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,86:1\n515#2:87\n500#2,6:88\n1#3:94\n*S KotlinDebug\n*F\n+ 1 FqNamesUtil.kt\norg/jetbrains/kotlin/name/FqNamesUtilKt\n*L\n73#1:87\n73#1:88,6\n*E\n"})
/* loaded from: classes8.dex */
public final class FqNamesUtilKt {

    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[State.values().length];
            try {
                iArr[State.BEGINNING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[State.AFTER_DOT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[State.MIDDLE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @Nullable
    public static final <V> V findValueForMostSpecificFqname(@NotNull FqName fqName, @NotNull Map<FqName, ? extends V> values) {
        Object next;
        Intrinsics.checkNotNullParameter(fqName, "<this>");
        Intrinsics.checkNotNullParameter(values, "values");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator<Map.Entry<FqName, ? extends V>> it = values.entrySet().iterator();
        while (true) {
            boolean z2 = true;
            if (!it.hasNext()) {
                break;
            }
            Map.Entry<FqName, ? extends V> next2 = it.next();
            FqName key = next2.getKey();
            if (!Intrinsics.areEqual(fqName, key) && !isChildOf(fqName, key)) {
                z2 = false;
            }
            if (z2) {
                linkedHashMap.put(next2.getKey(), next2.getValue());
            }
        }
        if (!(!linkedHashMap.isEmpty())) {
            linkedHashMap = null;
        }
        if (linkedHashMap == null) {
            return null;
        }
        Iterator it2 = linkedHashMap.entrySet().iterator();
        if (it2.hasNext()) {
            next = it2.next();
            if (it2.hasNext()) {
                int length = tail((FqName) ((Map.Entry) next).getKey(), fqName).asString().length();
                do {
                    Object next3 = it2.next();
                    int length2 = tail((FqName) ((Map.Entry) next3).getKey(), fqName).asString().length();
                    if (length > length2) {
                        next = next3;
                        length = length2;
                    }
                } while (it2.hasNext());
            }
        } else {
            next = null;
        }
        Map.Entry entry = (Map.Entry) next;
        if (entry != null) {
            return (V) entry.getValue();
        }
        return null;
    }

    public static final boolean isChildOf(@NotNull FqName fqName, @NotNull FqName packageName) {
        Intrinsics.checkNotNullParameter(fqName, "<this>");
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        return Intrinsics.areEqual(parentOrNull(fqName), packageName);
    }

    public static final boolean isSubpackageOf(@NotNull FqName fqName, @NotNull FqName packageName) {
        Intrinsics.checkNotNullParameter(fqName, "<this>");
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        if (Intrinsics.areEqual(fqName, packageName) || packageName.isRoot()) {
            return true;
        }
        String strAsString = fqName.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "this.asString()");
        String strAsString2 = packageName.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString2, "packageName.asString()");
        return isSubpackageOf(strAsString, strAsString2);
    }

    public static final boolean isValidJavaFqName(@Nullable String str) {
        if (str == null) {
            return false;
        }
        State state = State.BEGINNING;
        for (int i2 = 0; i2 < str.length(); i2++) {
            char cCharAt = str.charAt(i2);
            int i3 = WhenMappings.$EnumSwitchMapping$0[state.ordinal()];
            if (i3 == 1 || i3 == 2) {
                if (!Character.isJavaIdentifierStart(cCharAt)) {
                    return false;
                }
                state = State.MIDDLE;
            } else if (i3 != 3) {
                continue;
            } else if (cCharAt == '.') {
                state = State.AFTER_DOT;
            } else if (!Character.isJavaIdentifierPart(cCharAt)) {
                return false;
            }
        }
        return state != State.AFTER_DOT;
    }

    @Nullable
    public static final FqName parentOrNull(@NotNull FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "<this>");
        if (fqName.isRoot()) {
            return null;
        }
        return fqName.parent();
    }

    @NotNull
    public static final FqName tail(@NotNull FqName fqName, @NotNull FqName prefix) {
        Intrinsics.checkNotNullParameter(fqName, "<this>");
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        if (!isSubpackageOf(fqName, prefix) || prefix.isRoot()) {
            return fqName;
        }
        if (Intrinsics.areEqual(fqName, prefix)) {
            FqName ROOT = FqName.ROOT;
            Intrinsics.checkNotNullExpressionValue(ROOT, "ROOT");
            return ROOT;
        }
        String strAsString = fqName.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString()");
        String strSubstring = strAsString.substring(prefix.asString().length() + 1);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String).substring(startIndex)");
        return new FqName(strSubstring);
    }

    private static final boolean isSubpackageOf(String str, String str2) {
        return StringsKt__StringsJVMKt.startsWith$default(str, str2, false, 2, null) && str.charAt(str2.length()) == '.';
    }
}
