package androidx.transition;

import android.animation.ObjectAnimator;
import android.animation.TypeConverter;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.Property;

/* loaded from: classes.dex */
class ObjectAnimatorUtils {
    private ObjectAnimatorUtils() {
    }

    public static <T> ObjectAnimator ofPointF(T t2, Property<T, PointF> property, Path path) {
        return ObjectAnimator.ofObject(t2, property, (TypeConverter) null, path);
    }
}
