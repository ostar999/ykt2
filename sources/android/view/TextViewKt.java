package android.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.ColorResourcesKt;
import android.widget.TextView;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import androidx.core.view.GravityCompat;
import com.plv.business.sub.danmaku.entity.PLVDanmakuInfo;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000&\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\u001a\n\u0010\u0011\u001a\u00020\u0012*\u00020\u0003\u001a\n\u0010\u0013\u001a\u00020\u0012*\u00020\u0003\u001a\n\u0010\u0014\u001a\u00020\u0012*\u00020\u0003\u001a\r\u0010\u0015\u001a\u00020\u0012*\u00020\u0003H\u0086\b\u001aD\u0010\u0016\u001a\u00020\u0012*\u00020\u00032\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u00182\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u00182\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u00182\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u00182\b\b\u0002\u0010\u001c\u001a\u00020\u001d\u001a5\u0010\u0016\u001a\u00020\u0012*\u00020\u00032\b\b\u0003\u0010\u0017\u001a\u00020\u00012\b\b\u0003\u0010\u0019\u001a\u00020\u00012\b\b\u0003\u0010\u001a\u001a\u00020\u00012\b\b\u0003\u0010\u001b\u001a\u00020\u0001H\u0086\b\"*\u0010\u0002\u001a\u00020\u0001*\u00020\u00032\u0006\u0010\u0000\u001a\u00020\u00018Ç\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007\"*\u0010\b\u001a\u00020\u0001*\u00020\u00032\b\b\u0001\u0010\u0000\u001a\u00020\u00018G@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\t\u0010\u0005\"\u0004\b\n\u0010\u0007\",\u0010\u000b\u001a\u00020\u0001*\u00020\u00032\b\b\u0001\u0010\u0000\u001a\u00020\u00018Ç\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\b\f\u0010\u0005\"\u0004\b\r\u0010\u0007\",\u0010\u000e\u001a\u00020\u0001*\u00020\u00032\b\b\u0001\u0010\u0000\u001a\u00020\u00018Ç\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\b\u000f\u0010\u0005\"\u0004\b\u0010\u0010\u0007¨\u0006\u001e"}, d2 = {"value", "", "lines", "Landroid/widget/TextView;", "getLines", "(Landroid/widget/TextView;)I", "setLines", "(Landroid/widget/TextView;I)V", "textAppearance", "getTextAppearance", "setTextAppearance", "textColorResource", "getTextColorResource", "setTextColorResource", "textResource", "getTextResource", "setTextResource", "alignTextToEnd", "", "alignTextToStart", "centerText", "clearCompoundDrawables", "setCompoundDrawables", "start", "Landroid/graphics/drawable/Drawable;", PLVDanmakuInfo.FONTMODE_TOP, "end", PLVDanmakuInfo.FONTMODE_BOTTOM, "intrinsicBounds", "", "splitties-views_release"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes9.dex */
public final class TextViewKt {
    public static final void alignTextToEnd(@NotNull TextView textView) {
        Intrinsics.checkNotNullParameter(textView, "<this>");
        textView.setTextAlignment(6);
        textView.setGravity(GravityCompat.END);
    }

    public static final void alignTextToStart(@NotNull TextView textView) {
        Intrinsics.checkNotNullParameter(textView, "<this>");
        textView.setTextAlignment(5);
        textView.setGravity(GravityCompat.START);
    }

    public static final void centerText(@NotNull TextView textView) {
        Intrinsics.checkNotNullParameter(textView, "<this>");
        textView.setTextAlignment(4);
        textView.setGravity(17);
    }

    public static final void clearCompoundDrawables(@NotNull TextView textView) {
        Intrinsics.checkNotNullParameter(textView, "<this>");
        textView.setCompoundDrawables(null, null, null, null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = NoGetterKt.NO_GETTER)
    public static final /* synthetic */ int getLines(TextView textView) {
        Intrinsics.checkNotNullParameter(textView, "<this>");
        throw new UnsupportedOperationException(NoGetterKt.NO_GETTER);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = NoGetterKt.NO_GETTER)
    public static final /* synthetic */ int getTextAppearance(TextView textView) {
        Intrinsics.checkNotNullParameter(textView, "<this>");
        throw new UnsupportedOperationException(NoGetterKt.NO_GETTER);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = NoGetterKt.NO_GETTER)
    public static final /* synthetic */ int getTextColorResource(TextView textView) {
        Intrinsics.checkNotNullParameter(textView, "<this>");
        throw new UnsupportedOperationException(NoGetterKt.NO_GETTER);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = NoGetterKt.NO_GETTER)
    public static final /* synthetic */ int getTextResource(TextView textView) {
        Intrinsics.checkNotNullParameter(textView, "<this>");
        throw new UnsupportedOperationException(NoGetterKt.NO_GETTER);
    }

    public static final void setCompoundDrawables(@NotNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4, boolean z2) {
        Intrinsics.checkNotNullParameter(textView, "<this>");
        Drawable drawable5 = textView.getLayoutDirection() == 0 ? drawable : drawable3;
        if (!(!(textView.getLayoutDirection() == 0))) {
            drawable = drawable3;
        }
        if (z2) {
            textView.setCompoundDrawablesWithIntrinsicBounds(drawable5, drawable2, drawable, drawable4);
        } else {
            textView.setCompoundDrawables(drawable5, drawable2, drawable, drawable4);
        }
    }

    public static /* synthetic */ void setCompoundDrawables$default(TextView textView, Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4, boolean z2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            drawable = null;
        }
        if ((i2 & 2) != 0) {
            drawable2 = null;
        }
        if ((i2 & 4) != 0) {
            drawable3 = null;
        }
        if ((i2 & 8) != 0) {
            drawable4 = null;
        }
        if ((i2 & 16) != 0) {
            z2 = false;
        }
        setCompoundDrawables(textView, drawable, drawable2, drawable3, drawable4, z2);
    }

    public static final void setLines(@NotNull TextView textView, int i2) {
        Intrinsics.checkNotNullParameter(textView, "<this>");
        textView.setLines(i2);
    }

    public static final void setTextAppearance(@NotNull TextView textView, @StyleRes int i2) {
        Intrinsics.checkNotNullParameter(textView, "<this>");
        textView.setTextAppearance(i2);
    }

    public static final void setTextColorResource(@NotNull TextView textView, @ColorRes int i2) {
        Intrinsics.checkNotNullParameter(textView, "<this>");
        Context context = textView.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        textView.setTextColor(ColorResourcesKt.color(context, i2));
    }

    public static final void setTextResource(@NotNull TextView textView, @StringRes int i2) {
        Intrinsics.checkNotNullParameter(textView, "<this>");
        textView.setText(i2);
    }

    public static /* synthetic */ void setCompoundDrawables$default(TextView textView, int i2, int i3, int i4, int i5, int i6, Object obj) {
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
        Intrinsics.checkNotNullParameter(textView, "<this>");
        int i7 = textView.getLayoutDirection() == 0 ? i2 : i4;
        if (!(true ^ (textView.getLayoutDirection() == 0))) {
            i2 = i4;
        }
        textView.setCompoundDrawablesWithIntrinsicBounds(i7, i3, i2, i5);
    }

    public static final void setCompoundDrawables(@NotNull TextView textView, @DrawableRes int i2, @DrawableRes int i3, @DrawableRes int i4, @DrawableRes int i5) {
        Intrinsics.checkNotNullParameter(textView, "<this>");
        int i6 = textView.getLayoutDirection() == 0 ? i2 : i4;
        if (!(!(textView.getLayoutDirection() == 0))) {
            i2 = i4;
        }
        textView.setCompoundDrawablesWithIntrinsicBounds(i6, i3, i2, i5);
    }
}
