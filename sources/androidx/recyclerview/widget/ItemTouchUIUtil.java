package androidx.recyclerview.widget;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.view.View;

/* loaded from: classes.dex */
public interface ItemTouchUIUtil {
    @SuppressLint({"UnknownNullness"})
    void clearView(View view);

    @SuppressLint({"UnknownNullness"})
    void onDraw(Canvas canvas, RecyclerView recyclerView, View view, float f2, float f3, int i2, boolean z2);

    @SuppressLint({"UnknownNullness"})
    void onDrawOver(Canvas canvas, RecyclerView recyclerView, View view, float f2, float f3, int i2, boolean z2);

    @SuppressLint({"UnknownNullness"})
    void onSelected(View view);
}
