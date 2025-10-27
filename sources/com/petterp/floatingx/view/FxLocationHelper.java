package com.petterp.floatingx.view;

import android.content.res.Configuration;
import com.petterp.floatingx.assist.helper.BasisHelper;
import com.petterp.floatingx.util.FxExtKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\r0\u00102\u0006\u0010\u0011\u001a\u00020\u0012J\u0018\u0010\u0013\u001a\u00020\r2\u0006\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\rH\u0002J\u0018\u0010\u0016\u001a\u00020\r2\u0006\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\rH\u0002J\u000e\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0003\u001a\u00020\u0004J\u0006\u0010\u0005\u001a\u00020\u0006J\u0006\u0010\u0019\u001a\u00020\u0006J\u001e\u0010\u001a\u001a\u00020\u00002\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u001b\u001a\u00020\u0012J\u000e\u0010\u001c\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u001dR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lcom/petterp/floatingx/view/FxLocationHelper;", "", "()V", "config", "Lcom/petterp/floatingx/assist/helper/BasisHelper;", "isInitLocation", "", "isNearestLeft", "screenChanged", "screenH", "", "screenW", "x", "", "y", "getLocation", "Lkotlin/Pair;", "viewConfig", "Lcom/petterp/floatingx/view/FxViewConfigHelper;", "getX", "min", "max", "getY", "initConfig", "", "isRestoreLocation", "saveLocation", "configHelper", "updateConfig", "Landroid/content/res/Configuration;", "floatingx_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes4.dex */
public final class FxLocationHelper {
    private BasisHelper config;
    private boolean isInitLocation = true;
    private boolean isNearestLeft;
    private boolean screenChanged;
    private int screenH;
    private int screenW;
    private float x;
    private float y;

    private final float getX(float min, float max) {
        BasisHelper basisHelper = this.config;
        if (basisHelper != null) {
            return basisHelper.enableEdgeAdsorption ? this.isNearestLeft ? min : max : FxExtKt.coerceInFx(this.x, min, max);
        }
        Intrinsics.throwUninitializedPropertyAccessException("config");
        throw null;
    }

    private final float getY(float min, float max) {
        return FxExtKt.coerceInFx(this.y, min, max);
    }

    @NotNull
    public final Pair<Float, Float> getLocation(@NotNull FxViewConfigHelper viewConfig) {
        Intrinsics.checkNotNullParameter(viewConfig, "viewConfig");
        float x2 = getX(viewConfig.getMinWBoundary(), viewConfig.getMinWBoundary());
        float y2 = getY(viewConfig.getMinHBoundary(), viewConfig.getMaxHBoundary());
        this.screenChanged = false;
        return TuplesKt.to(Float.valueOf(x2), Float.valueOf(y2));
    }

    public final void initConfig(@NotNull BasisHelper config) {
        Intrinsics.checkNotNullParameter(config, "config");
        this.config = config;
    }

    public final boolean isInitLocation() {
        if (!this.isInitLocation) {
            return false;
        }
        this.isInitLocation = false;
        return true;
    }

    /* renamed from: isRestoreLocation, reason: from getter */
    public final boolean getScreenChanged() {
        return this.screenChanged;
    }

    @NotNull
    public final FxLocationHelper saveLocation(float x2, float y2, @NotNull FxViewConfigHelper configHelper) {
        Intrinsics.checkNotNullParameter(configHelper, "configHelper");
        this.x = x2;
        this.y = y2;
        this.isNearestLeft = configHelper.isNearestLeft(x2);
        return this;
    }

    public final boolean updateConfig(@NotNull Configuration config) {
        boolean z2;
        Intrinsics.checkNotNullParameter(config, "config");
        int i2 = config.screenWidthDp;
        if (i2 == this.screenW && config.screenHeightDp == this.screenH) {
            z2 = false;
        } else {
            this.screenW = i2;
            this.screenH = config.screenHeightDp;
            z2 = true;
        }
        this.screenChanged = z2;
        return z2;
    }
}
