package android.view;

import android.content.Context;
import android.view.View;
import androidx.annotation.Px;
import com.plv.business.sub.danmaku.entity.PLVDanmakuInfo;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001d\n\u0002\u0010\u0002\n\u0002\b\u0005\u001a2\u0010 \u001a\u00020!*\u00020\u00032\b\b\u0002\u0010\"\u001a\u00020\u00012\b\b\u0002\u0010#\u001a\u00020\u00012\b\b\u0002\u0010$\u001a\u00020\u00012\b\b\u0002\u0010%\u001a\u00020\u0001\",\u0010\u0002\u001a\u00020\u0001*\u00020\u00032\b\b\u0001\u0010\u0000\u001a\u00020\u00018Æ\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007\",\u0010\b\u001a\u00020\u0001*\u00020\u00032\b\b\u0001\u0010\u0000\u001a\u00020\u00018Æ\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\b\t\u0010\u0005\"\u0004\b\n\u0010\u0007\",\u0010\u000b\u001a\u00020\u0001*\u00020\u00032\b\b\u0001\u0010\u0000\u001a\u00020\u00018Ç\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\b\f\u0010\u0005\"\u0004\b\r\u0010\u0007\",\u0010\u000e\u001a\u00020\u0001*\u00020\u00032\b\b\u0001\u0010\u0000\u001a\u00020\u00018Æ\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\b\u000f\u0010\u0005\"\u0004\b\u0010\u0010\u0007\",\u0010\u0011\u001a\u00020\u0001*\u00020\u00032\b\b\u0001\u0010\u0000\u001a\u00020\u00018Ç\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\b\u0012\u0010\u0005\"\u0004\b\u0013\u0010\u0007\",\u0010\u0014\u001a\u00020\u0001*\u00020\u00032\b\b\u0001\u0010\u0000\u001a\u00020\u00018Æ\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\b\u0015\u0010\u0005\"\u0004\b\u0016\u0010\u0007\",\u0010\u0017\u001a\u00020\u0001*\u00020\u00032\b\b\u0001\u0010\u0000\u001a\u00020\u00018Æ\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\b\u0018\u0010\u0005\"\u0004\b\u0019\u0010\u0007\",\u0010\u001a\u001a\u00020\u0001*\u00020\u00032\b\b\u0001\u0010\u0000\u001a\u00020\u00018Æ\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\b\u001b\u0010\u0005\"\u0004\b\u001c\u0010\u0007\",\u0010\u001d\u001a\u00020\u0001*\u00020\u00032\b\b\u0001\u0010\u0000\u001a\u00020\u00018Ç\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\b\u001e\u0010\u0005\"\u0004\b\u001f\u0010\u0007¨\u0006&"}, d2 = {"value", "", "bottomPadding", "Landroid/view/View;", "getBottomPadding", "(Landroid/view/View;)I", "setBottomPadding", "(Landroid/view/View;I)V", "endPadding", "getEndPadding", "setEndPadding", "horizontalPadding", "getHorizontalPadding", "setHorizontalPadding", "leftPadding", "getLeftPadding", "setLeftPadding", "padding", "getPadding", "setPadding", "rightPadding", "getRightPadding", "setRightPadding", "startPadding", "getStartPadding", "setStartPadding", "topPadding", "getTopPadding", "setTopPadding", "verticalPadding", "getVerticalPadding", "setVerticalPadding", "setPaddingDp", "", "start", PLVDanmakuInfo.FONTMODE_TOP, "end", PLVDanmakuInfo.FONTMODE_BOTTOM, "splitties-views_release"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes9.dex */
public final class PaddingKt {
    public static final int getBottomPadding(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        return view.getPaddingBottom();
    }

    public static final int getEndPadding(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        return view.getPaddingEnd();
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = NoGetterKt.NO_GETTER)
    public static final /* synthetic */ int getHorizontalPadding(View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        throw new UnsupportedOperationException(NoGetterKt.NO_GETTER);
    }

    public static final int getLeftPadding(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        return view.getPaddingLeft();
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = NoGetterKt.NO_GETTER)
    public static final /* synthetic */ int getPadding(View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        throw new UnsupportedOperationException(NoGetterKt.NO_GETTER);
    }

    public static final int getRightPadding(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        return view.getPaddingRight();
    }

    public static final int getStartPadding(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        return view.getPaddingStart();
    }

    public static final int getTopPadding(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        return view.getPaddingTop();
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = NoGetterKt.NO_GETTER)
    public static final /* synthetic */ int getVerticalPadding(View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        throw new UnsupportedOperationException(NoGetterKt.NO_GETTER);
    }

    public static final void setBottomPadding(@NotNull View view, @Px int i2) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), i2);
    }

    public static final void setEndPadding(@NotNull View view, @Px int i2) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        view.setPaddingRelative(view.getPaddingStart(), view.getPaddingTop(), i2, view.getPaddingBottom());
    }

    public static final void setHorizontalPadding(@NotNull View view, @Px int i2) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        view.setPadding(i2, view.getPaddingTop(), i2, view.getPaddingBottom());
    }

    public static final void setLeftPadding(@NotNull View view, @Px int i2) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        view.setPadding(i2, view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());
    }

    public static final void setPadding(@NotNull View view, @Px int i2) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        view.setPadding(i2, i2, i2, i2);
    }

    public static final void setPaddingDp(@NotNull View view, int i2, int i3, int i4, int i5) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        int i6 = view.getLayoutDirection() == 0 ? i2 : i4;
        if (view.getLayoutDirection() == 0) {
            i2 = i4;
        }
        Context context = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        int i7 = (int) (i6 * context.getResources().getDisplayMetrics().density);
        Context context2 = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "context");
        int i8 = (int) (i3 * context2.getResources().getDisplayMetrics().density);
        Context context3 = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context3, "context");
        int i9 = (int) (i2 * context3.getResources().getDisplayMetrics().density);
        Context context4 = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context4, "context");
        view.setPadding(i7, i8, i9, (int) (i5 * context4.getResources().getDisplayMetrics().density));
    }

    public static /* synthetic */ void setPaddingDp$default(View view, int i2, int i3, int i4, int i5, int i6, Object obj) {
        if ((i6 & 1) != 0) {
            i2 = 0;
        }
        if ((i6 & 2) != 0) {
            i3 = 0;
        }
        if ((i6 & 4) != 0) {
            i4 = 0;
        }
        if ((i6 & 8) != 0) {
            i5 = 0;
        }
        setPaddingDp(view, i2, i3, i4, i5);
    }

    public static final void setRightPadding(@NotNull View view, @Px int i2) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), i2, view.getPaddingBottom());
    }

    public static final void setStartPadding(@NotNull View view, @Px int i2) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        view.setPaddingRelative(i2, view.getPaddingTop(), view.getPaddingEnd(), view.getPaddingBottom());
    }

    public static final void setTopPadding(@NotNull View view, @Px int i2) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        view.setPadding(view.getPaddingLeft(), i2, view.getPaddingRight(), view.getPaddingBottom());
    }

    public static final void setVerticalPadding(@NotNull View view, @Px int i2) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        view.setPadding(view.getPaddingLeft(), i2, view.getPaddingRight(), i2);
    }
}
