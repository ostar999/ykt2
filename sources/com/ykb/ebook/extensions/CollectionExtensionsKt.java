package com.ykb.ebook.extensions;

import androidx.exifinterface.media.ExifInterface;
import java.util.List;
import kotlin.Metadata;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000&\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000f\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0000\u001aD\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00012\b\b\u0002\u0010\u0005\u001a\u00020\u00012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00010\u0007H\u0086\bø\u0001\u0000\u001ag\u0010\b\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u000e\b\u0001\u0010\t*\b\u0012\u0004\u0012\u0002H\t0\n*\b\u0012\u0004\u0012\u0002H\u00020\u00032\b\u0010\u000b\u001a\u0004\u0018\u0001H\t2\b\b\u0002\u0010\u0004\u001a\u00020\u00012\b\b\u0002\u0010\u0005\u001a\u00020\u00012\u0016\b\u0004\u0010\f\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0006\u0012\u0004\u0018\u0001H\t0\u0007H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\r\u001a\u0010\u0010\u000e\u001a\u00020\u000f*\b\u0012\u0004\u0012\u00020\u000f0\u0003\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0010"}, d2 = {"fastBinarySearch", "", ExifInterface.GPS_DIRECTION_TRUE, "", "fromIndex", "toIndex", "comparison", "Lkotlin/Function1;", "fastBinarySearchBy", "K", "", "key", "selector", "(Ljava/util/List;Ljava/lang/Comparable;IILkotlin/jvm/functions/Function1;)I", "fastSum", "", "ebook_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nCollectionExtensions.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CollectionExtensions.kt\ncom/ykb/ebook/extensions/CollectionExtensionsKt\n*L\n1#1,50:1\n20#1,22:51\n*S KotlinDebug\n*F\n+ 1 CollectionExtensions.kt\ncom/ykb/ebook/extensions/CollectionExtensionsKt\n*L\n13#1:51,22\n*E\n"})
/* loaded from: classes7.dex */
public final class CollectionExtensionsKt {
    public static final <T> int fastBinarySearch(@NotNull List<? extends T> list, int i2, int i3, @NotNull Function1<? super T, Integer> comparison) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(comparison, "comparison");
        if (i2 > i3) {
            throw new IllegalArgumentException("fromIndex (" + i2 + ") is greater than toIndex (" + i3 + ").");
        }
        if (i2 < 0) {
            throw new IndexOutOfBoundsException("fromIndex (" + i2 + ") is less than zero.");
        }
        if (i3 > list.size()) {
            throw new IndexOutOfBoundsException("toIndex (" + i3 + ") is greater than size (" + list.size() + ").");
        }
        int i4 = i3 - 1;
        while (i2 <= i4) {
            int i5 = (i2 + i4) >>> 1;
            int iIntValue = comparison.invoke(list.get(i5)).intValue();
            if (iIntValue < 0) {
                i2 = i5 + 1;
            } else {
                if (iIntValue <= 0) {
                    return i5;
                }
                i4 = i5 - 1;
            }
        }
        return -(i2 + 1);
    }

    public static /* synthetic */ int fastBinarySearch$default(List list, int i2, int i3, Function1 comparison, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            i2 = 0;
        }
        if ((i4 & 2) != 0) {
            i3 = list.size();
        }
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(comparison, "comparison");
        if (i2 > i3) {
            throw new IllegalArgumentException("fromIndex (" + i2 + ") is greater than toIndex (" + i3 + ").");
        }
        if (i2 < 0) {
            throw new IndexOutOfBoundsException("fromIndex (" + i2 + ") is less than zero.");
        }
        if (i3 > list.size()) {
            throw new IndexOutOfBoundsException("toIndex (" + i3 + ") is greater than size (" + list.size() + ").");
        }
        int i5 = i3 - 1;
        while (i2 <= i5) {
            int i6 = (i2 + i5) >>> 1;
            int iIntValue = ((Number) comparison.invoke(list.get(i6))).intValue();
            if (iIntValue < 0) {
                i2 = i6 + 1;
            } else {
                if (iIntValue <= 0) {
                    return i6;
                }
                i5 = i6 - 1;
            }
        }
        return -(i2 + 1);
    }

    public static final <T, K extends Comparable<? super K>> int fastBinarySearchBy(@NotNull List<? extends T> list, @Nullable K k2, int i2, int i3, @NotNull Function1<? super T, ? extends K> selector) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(selector, "selector");
        if (i2 > i3) {
            throw new IllegalArgumentException("fromIndex (" + i2 + ") is greater than toIndex (" + i3 + ").");
        }
        if (i2 < 0) {
            throw new IndexOutOfBoundsException("fromIndex (" + i2 + ") is less than zero.");
        }
        if (i3 > list.size()) {
            throw new IndexOutOfBoundsException("toIndex (" + i3 + ") is greater than size (" + list.size() + ").");
        }
        int i4 = i3 - 1;
        while (i2 <= i4) {
            int i5 = (i2 + i4) >>> 1;
            int iCompareValues = ComparisonsKt__ComparisonsKt.compareValues(selector.invoke(list.get(i5)), k2);
            if (iCompareValues < 0) {
                i2 = i5 + 1;
            } else {
                if (iCompareValues <= 0) {
                    return i5;
                }
                i4 = i5 - 1;
            }
        }
        return -(i2 + 1);
    }

    public static /* synthetic */ int fastBinarySearchBy$default(List list, Comparable comparable, int i2, int i3, Function1 selector, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i2 = 0;
        }
        if ((i4 & 4) != 0) {
            i3 = list.size();
        }
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(selector, "selector");
        if (i2 > i3) {
            throw new IllegalArgumentException("fromIndex (" + i2 + ") is greater than toIndex (" + i3 + ").");
        }
        if (i2 < 0) {
            throw new IndexOutOfBoundsException("fromIndex (" + i2 + ") is less than zero.");
        }
        if (i3 > list.size()) {
            throw new IndexOutOfBoundsException("toIndex (" + i3 + ") is greater than size (" + list.size() + ").");
        }
        int i5 = i3 - 1;
        while (i2 <= i5) {
            int i6 = (i2 + i5) >>> 1;
            int iCompareValues = ComparisonsKt__ComparisonsKt.compareValues((Comparable) selector.invoke(list.get(i6)), comparable);
            if (iCompareValues < 0) {
                i2 = i6 + 1;
            } else {
                if (iCompareValues <= 0) {
                    return i6;
                }
                i5 = i6 - 1;
            }
        }
        return -(i2 + 1);
    }

    public static final float fastSum(@NotNull List<Float> list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        int size = list.size();
        float fFloatValue = 0.0f;
        for (int i2 = 0; i2 < size; i2++) {
            fFloatValue += list.get(i2).floatValue();
        }
        return fFloatValue;
    }
}
