package androidx.recyclerview.widget;

import android.graphics.Canvas;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.R;

/* loaded from: classes.dex */
class ItemTouchUIUtilImpl implements ItemTouchUIUtil {
    static final ItemTouchUIUtil INSTANCE = new ItemTouchUIUtilImpl();

    private static float findMaxElevation(RecyclerView recyclerView, View view) {
        int childCount = recyclerView.getChildCount();
        float f2 = 0.0f;
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = recyclerView.getChildAt(i2);
            if (childAt != view) {
                float elevation = ViewCompat.getElevation(childAt);
                if (elevation > f2) {
                    f2 = elevation;
                }
            }
        }
        return f2;
    }

    @Override // androidx.recyclerview.widget.ItemTouchUIUtil
    public void clearView(@NonNull View view) {
        int i2 = R.id.item_touch_helper_previous_elevation;
        Object tag = view.getTag(i2);
        if (tag instanceof Float) {
            ViewCompat.setElevation(view, ((Float) tag).floatValue());
        }
        view.setTag(i2, null);
        view.setTranslationX(0.0f);
        view.setTranslationY(0.0f);
    }

    @Override // androidx.recyclerview.widget.ItemTouchUIUtil
    public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView recyclerView, @NonNull View view, float f2, float f3, int i2, boolean z2) {
        if (z2) {
            int i3 = R.id.item_touch_helper_previous_elevation;
            if (view.getTag(i3) == null) {
                Float fValueOf = Float.valueOf(ViewCompat.getElevation(view));
                ViewCompat.setElevation(view, findMaxElevation(recyclerView, view) + 1.0f);
                view.setTag(i3, fValueOf);
            }
        }
        view.setTranslationX(f2);
        view.setTranslationY(f3);
    }

    @Override // androidx.recyclerview.widget.ItemTouchUIUtil
    public void onDrawOver(@NonNull Canvas canvas, @NonNull RecyclerView recyclerView, @NonNull View view, float f2, float f3, int i2, boolean z2) {
    }

    @Override // androidx.recyclerview.widget.ItemTouchUIUtil
    public void onSelected(@NonNull View view) {
    }
}
