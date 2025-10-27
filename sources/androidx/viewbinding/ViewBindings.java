package androidx.viewbinding;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.IdRes;
import androidx.annotation.Nullable;

/* loaded from: classes.dex */
public class ViewBindings {
    private ViewBindings() {
    }

    @Nullable
    public static <T extends View> T findChildViewById(View view, @IdRes int i2) {
        if (!(view instanceof ViewGroup)) {
            return null;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        int childCount = viewGroup.getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            T t2 = (T) viewGroup.getChildAt(i3).findViewById(i2);
            if (t2 != null) {
                return t2;
            }
        }
        return null;
    }
}
