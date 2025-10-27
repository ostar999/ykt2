package android.view;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import androidx.annotation.DrawableRes;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0006\"*\u0010\u0002\u001a\u00020\u0001*\u00020\u00032\u0006\u0010\u0000\u001a\u00020\u00018Ç\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007\".\u0010\t\u001a\u0004\u0018\u00010\b*\u00020\u00032\b\u0010\u0000\u001a\u0004\u0018\u00010\b8Æ\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\r\",\u0010\u000f\u001a\u00020\u000e*\u00020\u00032\b\b\u0001\u0010\u0000\u001a\u00020\u000e8Ç\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013¨\u0006\u0014"}, d2 = {"value", "Landroid/graphics/Bitmap;", "imageBitmap", "Landroid/widget/ImageView;", "getImageBitmap", "(Landroid/widget/ImageView;)Landroid/graphics/Bitmap;", "setImageBitmap", "(Landroid/widget/ImageView;Landroid/graphics/Bitmap;)V", "Landroid/graphics/drawable/Drawable;", "imageDrawable", "getImageDrawable", "(Landroid/widget/ImageView;)Landroid/graphics/drawable/Drawable;", "setImageDrawable", "(Landroid/widget/ImageView;Landroid/graphics/drawable/Drawable;)V", "", "imageResource", "getImageResource", "(Landroid/widget/ImageView;)I", "setImageResource", "(Landroid/widget/ImageView;I)V", "splitties-views_release"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes9.dex */
public final class ImageViewKt {
    @Deprecated(level = DeprecationLevel.HIDDEN, message = NoGetterKt.NO_GETTER)
    public static final /* synthetic */ Bitmap getImageBitmap(ImageView imageView) {
        Intrinsics.checkNotNullParameter(imageView, "<this>");
        throw new UnsupportedOperationException(NoGetterKt.NO_GETTER);
    }

    @Nullable
    public static final Drawable getImageDrawable(@NotNull ImageView imageView) {
        Intrinsics.checkNotNullParameter(imageView, "<this>");
        return imageView.getDrawable();
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = NoGetterKt.NO_GETTER)
    public static final /* synthetic */ int getImageResource(ImageView imageView) {
        Intrinsics.checkNotNullParameter(imageView, "<this>");
        throw new UnsupportedOperationException(NoGetterKt.NO_GETTER);
    }

    public static final void setImageBitmap(@NotNull ImageView imageView, @NotNull Bitmap value) {
        Intrinsics.checkNotNullParameter(imageView, "<this>");
        Intrinsics.checkNotNullParameter(value, "value");
        imageView.setImageBitmap(value);
    }

    public static final void setImageDrawable(@NotNull ImageView imageView, @Nullable Drawable drawable) {
        Intrinsics.checkNotNullParameter(imageView, "<this>");
        imageView.setImageDrawable(drawable);
    }

    public static final void setImageResource(@NotNull ImageView imageView, @DrawableRes int i2) {
        Intrinsics.checkNotNullParameter(imageView, "<this>");
        imageView.setImageResource(i2);
    }
}
