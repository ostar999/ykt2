package androidx.core.graphics;

import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.RegionIterator;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00004\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010(\n\u0002\b\u0007\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0086\f\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0086\f\u001a\u0015\u0010\u0004\u001a\u00020\u0005*\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0007H\u0086\n\u001a3\u0010\b\u001a\u00020\t*\u00020\u00012!\u0010\n\u001a\u001d\u0012\u0013\u0012\u00110\u0003¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u00020\t0\u000bH\u0086\bø\u0001\u0000\u001a\u0013\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00030\u0010*\u00020\u0001H\u0086\u0002\u001a\u0015\u0010\u0011\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0086\n\u001a\u0015\u0010\u0011\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0086\n\u001a\r\u0010\u0012\u001a\u00020\u0001*\u00020\u0001H\u0086\n\u001a\u0015\u0010\u0013\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0086\f\u001a\u0015\u0010\u0013\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0086\f\u001a\u0015\u0010\u0014\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0086\n\u001a\u0015\u0010\u0014\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0086\n\u001a\r\u0010\u0015\u001a\u00020\u0001*\u00020\u0001H\u0086\n\u001a\u0015\u0010\u0016\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0086\f\u001a\u0015\u0010\u0016\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0086\f\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0017"}, d2 = {"and", "Landroid/graphics/Region;", "r", "Landroid/graphics/Rect;", "contains", "", "p", "Landroid/graphics/Point;", "forEach", "", "action", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "rect", "iterator", "", "minus", "not", "or", "plus", "unaryMinus", "xor", "core-ktx_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nRegion.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Region.kt\nandroidx/core/graphics/RegionKt\n*L\n1#1,158:1\n71#1,3:159\n35#1,3:162\n44#1,3:165\n*S KotlinDebug\n*F\n+ 1 Region.kt\nandroidx/core/graphics/RegionKt\n*L\n79#1:159,3\n84#1:162,3\n89#1:165,3\n*E\n"})
/* loaded from: classes.dex */
public final class RegionKt {

    @Metadata(d1 = {"\u0000\u001d\n\u0000\n\u0002\u0010(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\t\u0010\b\u001a\u00020\u0004H\u0096\u0002J\t\u0010\t\u001a\u00020\u0002H\u0096\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0002X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"androidx/core/graphics/RegionKt$iterator$1", "", "Landroid/graphics/Rect;", "hasMore", "", "iterator", "Landroid/graphics/RegionIterator;", "rect", "hasNext", "next", "core-ktx_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* renamed from: androidx.core.graphics.RegionKt$iterator$1, reason: invalid class name */
    public static final class AnonymousClass1 implements Iterator<Rect>, KMappedMarker {
        private boolean hasMore;

        @NotNull
        private final RegionIterator iterator;

        @NotNull
        private final Rect rect;

        public AnonymousClass1(Region region) {
            RegionIterator regionIterator = new RegionIterator(region);
            this.iterator = regionIterator;
            Rect rect = new Rect();
            this.rect = rect;
            this.hasMore = regionIterator.next(rect);
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.hasMore;
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.Iterator
        @NotNull
        public Rect next() {
            if (!this.hasMore) {
                throw new IndexOutOfBoundsException();
            }
            Rect rect = new Rect(this.rect);
            this.hasMore = this.iterator.next(this.rect);
            return rect;
        }
    }

    @NotNull
    public static final Region and(@NotNull Region region, @NotNull Rect r2) {
        Intrinsics.checkNotNullParameter(region, "<this>");
        Intrinsics.checkNotNullParameter(r2, "r");
        Region region2 = new Region(region);
        region2.op(r2, Region.Op.INTERSECT);
        return region2;
    }

    public static final boolean contains(@NotNull Region region, @NotNull Point p2) {
        Intrinsics.checkNotNullParameter(region, "<this>");
        Intrinsics.checkNotNullParameter(p2, "p");
        return region.contains(p2.x, p2.y);
    }

    public static final void forEach(@NotNull Region region, @NotNull Function1<? super Rect, Unit> action) {
        Intrinsics.checkNotNullParameter(region, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        RegionIterator regionIterator = new RegionIterator(region);
        while (true) {
            Rect rect = new Rect();
            if (!regionIterator.next(rect)) {
                return;
            } else {
                action.invoke(rect);
            }
        }
    }

    @NotNull
    public static final Iterator<Rect> iterator(@NotNull Region region) {
        Intrinsics.checkNotNullParameter(region, "<this>");
        return new AnonymousClass1(region);
    }

    @NotNull
    public static final Region minus(@NotNull Region region, @NotNull Rect r2) {
        Intrinsics.checkNotNullParameter(region, "<this>");
        Intrinsics.checkNotNullParameter(r2, "r");
        Region region2 = new Region(region);
        region2.op(r2, Region.Op.DIFFERENCE);
        return region2;
    }

    @NotNull
    public static final Region not(@NotNull Region region) {
        Intrinsics.checkNotNullParameter(region, "<this>");
        Region region2 = new Region(region.getBounds());
        region2.op(region, Region.Op.DIFFERENCE);
        return region2;
    }

    @NotNull
    public static final Region or(@NotNull Region region, @NotNull Rect r2) {
        Intrinsics.checkNotNullParameter(region, "<this>");
        Intrinsics.checkNotNullParameter(r2, "r");
        Region region2 = new Region(region);
        region2.union(r2);
        return region2;
    }

    @NotNull
    public static final Region plus(@NotNull Region region, @NotNull Rect r2) {
        Intrinsics.checkNotNullParameter(region, "<this>");
        Intrinsics.checkNotNullParameter(r2, "r");
        Region region2 = new Region(region);
        region2.union(r2);
        return region2;
    }

    @NotNull
    public static final Region unaryMinus(@NotNull Region region) {
        Intrinsics.checkNotNullParameter(region, "<this>");
        Region region2 = new Region(region.getBounds());
        region2.op(region, Region.Op.DIFFERENCE);
        return region2;
    }

    @NotNull
    public static final Region xor(@NotNull Region region, @NotNull Rect r2) {
        Intrinsics.checkNotNullParameter(region, "<this>");
        Intrinsics.checkNotNullParameter(r2, "r");
        Region region2 = new Region(region);
        region2.op(r2, Region.Op.XOR);
        return region2;
    }

    @NotNull
    public static final Region and(@NotNull Region region, @NotNull Region r2) {
        Intrinsics.checkNotNullParameter(region, "<this>");
        Intrinsics.checkNotNullParameter(r2, "r");
        Region region2 = new Region(region);
        region2.op(r2, Region.Op.INTERSECT);
        return region2;
    }

    @NotNull
    public static final Region minus(@NotNull Region region, @NotNull Region r2) {
        Intrinsics.checkNotNullParameter(region, "<this>");
        Intrinsics.checkNotNullParameter(r2, "r");
        Region region2 = new Region(region);
        region2.op(r2, Region.Op.DIFFERENCE);
        return region2;
    }

    @NotNull
    public static final Region or(@NotNull Region region, @NotNull Region r2) {
        Intrinsics.checkNotNullParameter(region, "<this>");
        Intrinsics.checkNotNullParameter(r2, "r");
        Region region2 = new Region(region);
        region2.op(r2, Region.Op.UNION);
        return region2;
    }

    @NotNull
    public static final Region plus(@NotNull Region region, @NotNull Region r2) {
        Intrinsics.checkNotNullParameter(region, "<this>");
        Intrinsics.checkNotNullParameter(r2, "r");
        Region region2 = new Region(region);
        region2.op(r2, Region.Op.UNION);
        return region2;
    }

    @NotNull
    public static final Region xor(@NotNull Region region, @NotNull Region r2) {
        Intrinsics.checkNotNullParameter(region, "<this>");
        Intrinsics.checkNotNullParameter(r2, "r");
        Region region2 = new Region(region);
        region2.op(r2, Region.Op.XOR);
        return region2;
    }
}
