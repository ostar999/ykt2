package android.view;

import android.view.View;
import androidx.core.view.GravityCompat;
import com.google.android.material.badge.BadgeDrawable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b+\"\u0016\u0010\u0000\u001a\u00020\u0001*\u00020\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0016\u0010\u0005\u001a\u00020\u0001*\u00020\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0004\"\u0016\u0010\u0007\u001a\u00020\u0001*\u00020\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\b\u0010\u0004\"\u0016\u0010\t\u001a\u00020\u0001*\u00020\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\n\u0010\u0004\"\u0016\u0010\u000b\u001a\u00020\u0001*\u00020\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\f\u0010\u0004\"\u0016\u0010\r\u001a\u00020\u0001*\u00020\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u0004\"\u0016\u0010\u000f\u001a\u00020\u0001*\u00020\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0004\"\u0016\u0010\u0011\u001a\u00020\u0001*\u00020\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0004\"\u0016\u0010\u0013\u001a\u00020\u0001*\u00020\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0004\"\u0016\u0010\u0015\u001a\u00020\u0001*\u00020\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0004\"\u0016\u0010\u0017\u001a\u00020\u0001*\u00020\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0004\"\u0016\u0010\u0019\u001a\u00020\u0001*\u00020\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u0004\"\u0016\u0010\u001b\u001a\u00020\u0001*\u00020\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u0004\"\u0016\u0010\u001d\u001a\u00020\u0001*\u00020\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u0004\"\u0016\u0010\u001f\u001a\u00020\u0001*\u00020\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b \u0010\u0004\"\u0016\u0010!\u001a\u00020\u0001*\u00020\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\"\u0010\u0004\"\u0016\u0010#\u001a\u00020\u0001*\u00020\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b$\u0010\u0004\"\u0016\u0010%\u001a\u00020\u0001*\u00020\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b&\u0010\u0004\"\u0016\u0010'\u001a\u00020\u0001*\u00020\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b(\u0010\u0004\"\u0016\u0010)\u001a\u00020\u0001*\u00020\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b*\u0010\u0004\"\u0016\u0010+\u001a\u00020\u0001*\u00020\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b,\u0010\u0004¨\u0006-"}, d2 = {"gravityBottom", "", "Landroid/view/View;", "getGravityBottom", "(Landroid/view/View;)I", "gravityBottomCenter", "getGravityBottomCenter", "gravityBottomEnd", "getGravityBottomEnd", "gravityBottomStart", "getGravityBottomStart", "gravityCenter", "getGravityCenter", "gravityCenterHorizontal", "getGravityCenterHorizontal", "gravityCenterVertical", "getGravityCenterVertical", "gravityEnd", "getGravityEnd", "gravityEndBottom", "getGravityEndBottom", "gravityEndCenter", "getGravityEndCenter", "gravityEndTop", "getGravityEndTop", "gravityHorizontalCenter", "getGravityHorizontalCenter", "gravityStart", "getGravityStart", "gravityStartBottom", "getGravityStartBottom", "gravityStartCenter", "getGravityStartCenter", "gravityStartTop", "getGravityStartTop", "gravityTop", "getGravityTop", "gravityTopCenter", "getGravityTopCenter", "gravityTopEnd", "getGravityTopEnd", "gravityTopStart", "getGravityTopStart", "gravityVerticalCenter", "getGravityVerticalCenter", "splitties-views_release"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes9.dex */
public final class GravityKt {
    public static final int getGravityBottom(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        return 80;
    }

    public static final int getGravityBottomCenter(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        return 81;
    }

    public static final int getGravityBottomEnd(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        return BadgeDrawable.BOTTOM_END;
    }

    public static final int getGravityBottomStart(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        return BadgeDrawable.BOTTOM_START;
    }

    public static final int getGravityCenter(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        return 17;
    }

    public static final int getGravityCenterHorizontal(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        return 1;
    }

    public static final int getGravityCenterVertical(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        return 16;
    }

    public static final int getGravityEnd(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        return GravityCompat.END;
    }

    public static final int getGravityEndBottom(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        return BadgeDrawable.BOTTOM_END;
    }

    public static final int getGravityEndCenter(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        return 8388629;
    }

    public static final int getGravityEndTop(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        return BadgeDrawable.TOP_END;
    }

    public static final int getGravityHorizontalCenter(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        return 1;
    }

    public static final int getGravityStart(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        return GravityCompat.START;
    }

    public static final int getGravityStartBottom(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        return BadgeDrawable.BOTTOM_START;
    }

    public static final int getGravityStartCenter(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        return 8388627;
    }

    public static final int getGravityStartTop(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        return BadgeDrawable.TOP_START;
    }

    public static final int getGravityTop(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        return 48;
    }

    public static final int getGravityTopCenter(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        return 49;
    }

    public static final int getGravityTopEnd(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        return BadgeDrawable.TOP_END;
    }

    public static final int getGravityTopStart(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        return BadgeDrawable.TOP_START;
    }

    public static final int getGravityVerticalCenter(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        return 16;
    }
}
