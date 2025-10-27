package com.google.android.gms.internal.base;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;

/* loaded from: classes3.dex */
public final class zae extends Drawable implements Drawable.Callback {
    private int mAlpha;
    private int mFrom;
    private boolean zand;
    private int zanl;
    private long zanm;
    private int zann;
    private int zano;
    private int zanp;
    private boolean zanq;
    private zah zanr;
    private Drawable zans;
    private Drawable zant;
    private boolean zanu;
    private boolean zanv;
    private boolean zanw;
    private int zanx;

    public zae(Drawable drawable, Drawable drawable2) {
        this(null);
        drawable = drawable == null ? zaf.zany : drawable;
        this.zans = drawable;
        drawable.setCallback(this);
        zah zahVar = this.zanr;
        zahVar.zaoa = drawable.getChangingConfigurations() | zahVar.zaoa;
        drawable2 = drawable2 == null ? zaf.zany : drawable2;
        this.zant = drawable2;
        drawable2.setCallback(this);
        zah zahVar2 = this.zanr;
        zahVar2.zaoa = drawable2.getChangingConfigurations() | zahVar2.zaoa;
    }

    private final boolean canConstantState() {
        if (!this.zanu) {
            this.zanv = (this.zans.getConstantState() == null || this.zant.getConstantState() == null) ? false : true;
            this.zanu = true;
        }
        return this.zanv;
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        int i2 = this.zanl;
        boolean z2 = false;
        if (i2 != 1) {
            if (i2 == 2 && this.zanm >= 0) {
                float fUptimeMillis = (SystemClock.uptimeMillis() - this.zanm) / this.zanp;
                z = fUptimeMillis >= 1.0f;
                if (z) {
                    this.zanl = 0;
                }
                this.mAlpha = (int) ((this.zann * Math.min(fUptimeMillis, 1.0f)) + 0.0f);
            }
            z2 = z;
        } else {
            this.zanm = SystemClock.uptimeMillis();
            this.zanl = 2;
        }
        int i3 = this.mAlpha;
        boolean z3 = this.zand;
        Drawable drawable = this.zans;
        Drawable drawable2 = this.zant;
        if (z2) {
            if (!z3 || i3 == 0) {
                drawable.draw(canvas);
            }
            int i4 = this.zano;
            if (i3 == i4) {
                drawable2.setAlpha(i4);
                drawable2.draw(canvas);
                return;
            }
            return;
        }
        if (z3) {
            drawable.setAlpha(this.zano - i3);
        }
        drawable.draw(canvas);
        if (z3) {
            drawable.setAlpha(this.zano);
        }
        if (i3 > 0) {
            drawable2.setAlpha(i3);
            drawable2.draw(canvas);
            drawable2.setAlpha(this.zano);
        }
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public final int getChangingConfigurations() {
        int changingConfigurations = super.getChangingConfigurations();
        zah zahVar = this.zanr;
        return changingConfigurations | zahVar.mChangingConfigurations | zahVar.zaoa;
    }

    @Override // android.graphics.drawable.Drawable
    public final Drawable.ConstantState getConstantState() {
        if (!canConstantState()) {
            return null;
        }
        this.zanr.mChangingConfigurations = getChangingConfigurations();
        return this.zanr;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicHeight() {
        return Math.max(this.zans.getIntrinsicHeight(), this.zant.getIntrinsicHeight());
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicWidth() {
        return Math.max(this.zans.getIntrinsicWidth(), this.zant.getIntrinsicWidth());
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        if (!this.zanw) {
            this.zanx = Drawable.resolveOpacity(this.zans.getOpacity(), this.zant.getOpacity());
            this.zanw = true;
        }
        return this.zanx;
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public final void invalidateDrawable(Drawable drawable) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final Drawable mutate() {
        if (!this.zanq && super.mutate() == this) {
            if (!canConstantState()) {
                throw new IllegalStateException("One or more children of this LayerDrawable does not have constant state; this drawable cannot be mutated.");
            }
            this.zans.mutate();
            this.zant.mutate();
            this.zanq = true;
        }
        return this;
    }

    @Override // android.graphics.drawable.Drawable
    public final void onBoundsChange(Rect rect) {
        this.zans.setBounds(rect);
        this.zant.setBounds(rect);
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public final void scheduleDrawable(Drawable drawable, Runnable runnable, long j2) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.scheduleDrawable(this, runnable, j2);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i2) {
        if (this.mAlpha == this.zano) {
            this.mAlpha = i2;
        }
        this.zano = i2;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
        this.zans.setColorFilter(colorFilter);
        this.zant.setColorFilter(colorFilter);
    }

    public final void startTransition(int i2) {
        this.mFrom = 0;
        this.zann = this.zano;
        this.mAlpha = 0;
        this.zanp = 250;
        this.zanl = 1;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public final void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.unscheduleDrawable(this, runnable);
        }
    }

    public final Drawable zacd() {
        return this.zant;
    }

    public zae(zah zahVar) {
        this.zanl = 0;
        this.zano = 255;
        this.mAlpha = 0;
        this.zand = true;
        this.zanr = new zah(zahVar);
    }
}
