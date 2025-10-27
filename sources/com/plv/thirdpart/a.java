package com.plv.thirdpart;

import android.R;
import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

/* loaded from: classes5.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private View f10951a;

    /* renamed from: b, reason: collision with root package name */
    private int f10952b;

    /* renamed from: c, reason: collision with root package name */
    private FrameLayout.LayoutParams f10953c;

    /* renamed from: d, reason: collision with root package name */
    private boolean f10954d = true;

    private a(Activity activity) {
        View childAt = ((FrameLayout) activity.findViewById(R.id.content)).getChildAt(0);
        this.f10951a = childAt;
        childAt.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.plv.thirdpart.a.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                if (a.this.f10954d) {
                    a.this.a();
                }
            }
        });
        this.f10953c = (FrameLayout.LayoutParams) this.f10951a.getLayoutParams();
    }

    public static a a(Activity activity) {
        return new a(activity);
    }

    private int b() {
        Rect rect = new Rect();
        this.f10951a.getWindowVisibleDisplayFrame(rect);
        return rect.bottom;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        int iB = b();
        if (iB != this.f10952b) {
            int height = this.f10951a.getRootView().getHeight();
            int i2 = height - iB;
            if (i2 > height / 4) {
                this.f10953c.height = height - i2;
            } else {
                this.f10953c.height = height;
            }
            this.f10951a.requestLayout();
            this.f10952b = iB;
        }
    }

    public void a(boolean z2) {
        this.f10954d = z2;
    }
}
