package com.unity3d.player;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

/* loaded from: classes6.dex */
public final class l extends View {

    /* renamed from: a, reason: collision with root package name */
    final int f24155a;

    /* renamed from: b, reason: collision with root package name */
    final int f24156b;

    /* renamed from: c, reason: collision with root package name */
    Bitmap f24157c;

    /* renamed from: d, reason: collision with root package name */
    Bitmap f24158d;

    /* renamed from: com.unity3d.player.l$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f24159a;

        static {
            int[] iArr = new int[a.a().length];
            f24159a = iArr;
            try {
                iArr[a.f24160a - 1] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f24159a[a.f24161b - 1] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f24159a[a.f24162c - 1] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    /* JADX WARN: $VALUES field not found */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    public static final class a {

        /* renamed from: a, reason: collision with root package name */
        public static final int f24160a = 1;

        /* renamed from: b, reason: collision with root package name */
        public static final int f24161b = 2;

        /* renamed from: c, reason: collision with root package name */
        public static final int f24162c = 3;

        /* renamed from: d, reason: collision with root package name */
        private static final /* synthetic */ int[] f24163d = {1, 2, 3};

        public static int[] a() {
            return (int[]) f24163d.clone();
        }
    }

    public l(Context context, int i2) {
        super(context);
        this.f24155a = i2;
        int identifier = getResources().getIdentifier("unity_static_splash", "drawable", getContext().getPackageName());
        this.f24156b = identifier;
        if (identifier != 0) {
            forceLayout();
        }
    }

    @Override // android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Bitmap bitmap = this.f24157c;
        if (bitmap != null) {
            bitmap.recycle();
            this.f24157c = null;
        }
        Bitmap bitmap2 = this.f24158d;
        if (bitmap2 != null) {
            bitmap2.recycle();
            this.f24158d = null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x006c  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onLayout(boolean r10, int r11, int r12, int r13, int r14) {
        /*
            r9 = this;
            int r10 = r9.f24156b
            if (r10 != 0) goto L5
            return
        L5:
            android.graphics.Bitmap r10 = r9.f24157c
            r11 = 0
            if (r10 != 0) goto L1d
            android.graphics.BitmapFactory$Options r10 = new android.graphics.BitmapFactory$Options
            r10.<init>()
            r10.inScaled = r11
            android.content.res.Resources r12 = r9.getResources()
            int r13 = r9.f24156b
            android.graphics.Bitmap r10 = android.graphics.BitmapFactory.decodeResource(r12, r13, r10)
            r9.f24157c = r10
        L1d:
            android.graphics.Bitmap r10 = r9.f24157c
            int r10 = r10.getWidth()
            android.graphics.Bitmap r12 = r9.f24157c
            int r12 = r12.getHeight()
            int r13 = r9.getWidth()
            int r14 = r9.getHeight()
            if (r13 == 0) goto Lc8
            if (r14 != 0) goto L37
            goto Lc8
        L37:
            float r0 = (float) r10
            float r1 = (float) r12
            float r0 = r0 / r1
            float r1 = (float) r13
            float r2 = (float) r14
            float r3 = r1 / r2
            int r3 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1))
            r4 = 1
            if (r3 > 0) goto L45
            r3 = r4
            goto L46
        L45:
            r3 = r11
        L46:
            int[] r5 = com.unity3d.player.l.AnonymousClass1.f24159a
            int r6 = r9.f24155a
            int r7 = r6 + (-1)
            r5 = r5[r7]
            r7 = 2
            if (r5 == r4) goto L65
            if (r5 == r7) goto L57
            r8 = 3
            if (r5 == r8) goto L57
            goto L6f
        L57:
            int r10 = com.unity3d.player.l.a.f24162c
            if (r6 != r10) goto L5d
            r10 = r4
            goto L5e
        L5d:
            r10 = r11
        L5e:
            r10 = r10 ^ r3
            if (r10 == 0) goto L6c
            float r1 = r1 / r0
            int r12 = (int) r1
            r10 = r13
            goto L6f
        L65:
            if (r13 >= r10) goto L6a
            float r1 = r1 / r0
            int r12 = (int) r1
            r10 = r13
        L6a:
            if (r14 >= r12) goto L6f
        L6c:
            float r2 = r2 * r0
            int r10 = (int) r2
            r12 = r14
        L6f:
            android.graphics.Bitmap r13 = r9.f24158d
            if (r13 == 0) goto L8e
            int r13 = r13.getWidth()
            if (r13 != r10) goto L82
            android.graphics.Bitmap r13 = r9.f24158d
            int r13 = r13.getHeight()
            if (r13 != r12) goto L82
            return
        L82:
            android.graphics.Bitmap r13 = r9.f24158d
            android.graphics.Bitmap r14 = r9.f24157c
            if (r13 == r14) goto L8e
            r13.recycle()
            r13 = 0
            r9.f24158d = r13
        L8e:
            android.graphics.Bitmap r13 = r9.f24157c
            android.graphics.Bitmap r10 = android.graphics.Bitmap.createScaledBitmap(r13, r10, r12, r4)
            r9.f24158d = r10
            android.content.res.Resources r12 = r9.getResources()
            android.util.DisplayMetrics r12 = r12.getDisplayMetrics()
            int r12 = r12.densityDpi
            r10.setDensity(r12)
            android.graphics.drawable.ColorDrawable r10 = new android.graphics.drawable.ColorDrawable
            r12 = -16777216(0xffffffffff000000, float:-1.7014118E38)
            r10.<init>(r12)
            android.graphics.drawable.BitmapDrawable r12 = new android.graphics.drawable.BitmapDrawable
            android.content.res.Resources r13 = r9.getResources()
            android.graphics.Bitmap r14 = r9.f24158d
            r12.<init>(r13, r14)
            r13 = 17
            r12.setGravity(r13)
            android.graphics.drawable.LayerDrawable r13 = new android.graphics.drawable.LayerDrawable
            android.graphics.drawable.Drawable[] r14 = new android.graphics.drawable.Drawable[r7]
            r14[r11] = r10
            r14[r4] = r12
            r13.<init>(r14)
            r9.setBackground(r13)
        Lc8:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.unity3d.player.l.onLayout(boolean, int, int, int, int):void");
    }
}
