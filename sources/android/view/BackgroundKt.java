package android.view;

import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.annotation.ColorInt;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\",\u0010\u0002\u001a\u00020\u0001*\u00020\u00032\b\b\u0001\u0010\u0000\u001a\u00020\u00018Ç\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007\".\u0010\n\u001a\u0004\u0018\u00010\t*\u00020\u00032\b\u0010\b\u001a\u0004\u0018\u00010\t8Æ\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"colorInt", "", TtmlNode.ATTR_TTS_BACKGROUND_COLOR, "Landroid/view/View;", "getBackgroundColor", "(Landroid/view/View;)I", "setBackgroundColor", "(Landroid/view/View;I)V", "value", "Landroid/graphics/drawable/Drawable;", "bg", "getBg", "(Landroid/view/View;)Landroid/graphics/drawable/Drawable;", "setBg", "(Landroid/view/View;Landroid/graphics/drawable/Drawable;)V", "splitties-views_release"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes9.dex */
public final class BackgroundKt {
    @Deprecated(level = DeprecationLevel.HIDDEN, message = NoGetterKt.NO_GETTER)
    public static final /* synthetic */ int getBackgroundColor(View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        throw new UnsupportedOperationException(NoGetterKt.NO_GETTER);
    }

    @Nullable
    public static final Drawable getBg(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        return view.getBackground();
    }

    public static final void setBackgroundColor(@NotNull View view, @ColorInt int i2) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        view.setBackgroundColor(i2);
    }

    public static final void setBg(@NotNull View view, @Nullable Drawable drawable) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        view.setBackground(drawable);
    }
}
