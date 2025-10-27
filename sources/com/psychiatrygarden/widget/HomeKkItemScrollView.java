package com.psychiatrygarden.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B!\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lcom/psychiatrygarden/widget/HomeKkItemScrollView;", "Landroid/widget/HorizontalScrollView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "attributeSet", "Landroid/util/AttributeSet;", "def", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "isDragging", "", "lastX", "", "onTouchEvent", "ev", "Landroid/view/MotionEvent;", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class HomeKkItemScrollView extends HorizontalScrollView {
    private boolean isDragging;
    private float lastX;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public HomeKkItemScrollView(@NotNull Context context, @NotNull AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(attributeSet, "attributeSet");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public HomeKkItemScrollView(@NotNull Context context, @NotNull AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(attributeSet, "attributeSet");
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0061  */
    @Override // android.widget.HorizontalScrollView, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(@org.jetbrains.annotations.NotNull android.view.MotionEvent r8) {
        /*
            r7 = this;
            java.lang.String r0 = "ev"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r8, r0)
            int r0 = r8.getActionMasked()
            float r1 = r8.getX()
            r2 = 1
            r3 = 0
            if (r0 == 0) goto L6f
            if (r0 == r2) goto L61
            r4 = 2
            if (r0 == r4) goto L1a
            r1 = 3
            if (r0 == r1) goto L61
            goto L6a
        L1a:
            float r0 = r7.lastX
            float r0 = r1 - r0
            float r0 = java.lang.Math.abs(r0)
            r4 = 1084227584(0x40a00000, float:5.0)
            int r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r0 <= 0) goto L5e
            r7.isDragging = r2
            int r0 = r7.getScrollX()
            if (r0 <= 0) goto L32
            r0 = r2
            goto L33
        L32:
            r0 = r3
        L33:
            int r4 = r7.getScrollX()
            android.view.View r5 = r7.getChildAt(r3)
            int r5 = r5.getWidth()
            int r6 = r7.getWidth()
            int r5 = r5 - r6
            if (r4 >= r5) goto L47
            goto L48
        L47:
            r2 = r3
        L48:
            float r4 = r7.lastX
            int r5 = (r1 > r4 ? 1 : (r1 == r4 ? 0 : -1))
            if (r5 <= 0) goto L50
            if (r0 == 0) goto L56
        L50:
            int r0 = (r1 > r4 ? 1 : (r1 == r4 ? 0 : -1))
            if (r0 >= 0) goto L5e
            if (r2 != 0) goto L5e
        L56:
            android.view.ViewParent r8 = r7.getParent()
            r8.requestDisallowInterceptTouchEvent(r3)
            return r3
        L5e:
            r7.lastX = r1
            goto L6a
        L61:
            r7.isDragging = r3
            android.view.ViewParent r0 = r7.getParent()
            r0.requestDisallowInterceptTouchEvent(r3)
        L6a:
            boolean r8 = super.onTouchEvent(r8)
            return r8
        L6f:
            android.view.ViewParent r0 = r7.getParent()
            r0.requestDisallowInterceptTouchEvent(r2)
            float r8 = r8.getX()
            r7.lastX = r8
            r7.isDragging = r3
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.widget.HomeKkItemScrollView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public /* synthetic */ HomeKkItemScrollView(Context context, AttributeSet attributeSet, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, attributeSet, (i3 & 4) != 0 ? 0 : i2);
    }
}
