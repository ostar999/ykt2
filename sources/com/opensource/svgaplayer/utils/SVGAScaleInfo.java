package com.opensource.svgaplayer.utils;

import android.widget.ImageView;
import com.opensource.svgaplayer.BuildConfig;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0011\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J.\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u00042\u0006\u0010\u001e\u001a\u00020\u00042\u0006\u0010\u001f\u001a\u00020\u00042\u0006\u0010 \u001a\u00020\u00042\u0006\u0010!\u001a\u00020\"J\b\u0010#\u001a\u00020\u001cH\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001a\u0010\u0012\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\bR\u001a\u0010\u0015\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0006\"\u0004\b\u0017\u0010\bR\u001a\u0010\u0018\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0006\"\u0004\b\u001a\u0010\b¨\u0006$"}, d2 = {"Lcom/opensource/svgaplayer/utils/SVGAScaleInfo;", "", "()V", "ratio", "", "getRatio", "()F", "setRatio", "(F)V", "ratioX", "", "getRatioX", "()Z", "setRatioX", "(Z)V", "scaleFx", "getScaleFx", "setScaleFx", "scaleFy", "getScaleFy", "setScaleFy", "tranFx", "getTranFx", "setTranFx", "tranFy", "getTranFy", "setTranFy", "performScaleType", "", "canvasWidth", "canvasHeight", "videoWidth", "videoHeight", "scaleType", "Landroid/widget/ImageView$ScaleType;", "resetVar", BuildConfig.LIBRARY_PACKAGE_NAME}, k = 1, mv = {1, 1, 15})
/* loaded from: classes4.dex */
public final class SVGAScaleInfo {
    private boolean ratioX;
    private float tranFx;
    private float tranFy;
    private float scaleFx = 1.0f;
    private float scaleFy = 1.0f;
    private float ratio = 1.0f;

    @Metadata(bv = {1, 0, 3}, k = 3, mv = {1, 1, 15})
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ImageView.ScaleType.values().length];
            $EnumSwitchMapping$0 = iArr;
            iArr[ImageView.ScaleType.CENTER.ordinal()] = 1;
            iArr[ImageView.ScaleType.CENTER_CROP.ordinal()] = 2;
            iArr[ImageView.ScaleType.CENTER_INSIDE.ordinal()] = 3;
            iArr[ImageView.ScaleType.FIT_CENTER.ordinal()] = 4;
            iArr[ImageView.ScaleType.FIT_START.ordinal()] = 5;
            iArr[ImageView.ScaleType.FIT_END.ordinal()] = 6;
            iArr[ImageView.ScaleType.FIT_XY.ordinal()] = 7;
        }
    }

    private final void resetVar() {
        this.tranFx = 0.0f;
        this.tranFy = 0.0f;
        this.scaleFx = 1.0f;
        this.scaleFy = 1.0f;
        this.ratio = 1.0f;
        this.ratioX = false;
    }

    public final float getRatio() {
        return this.ratio;
    }

    public final boolean getRatioX() {
        return this.ratioX;
    }

    public final float getScaleFx() {
        return this.scaleFx;
    }

    public final float getScaleFy() {
        return this.scaleFy;
    }

    public final float getTranFx() {
        return this.tranFx;
    }

    public final float getTranFy() {
        return this.tranFy;
    }

    public final void performScaleType(float canvasWidth, float canvasHeight, float videoWidth, float videoHeight, @NotNull ImageView.ScaleType scaleType) {
        Intrinsics.checkParameterIsNotNull(scaleType, "scaleType");
        if (canvasWidth == 0.0f || canvasHeight == 0.0f || videoWidth == 0.0f || videoHeight == 0.0f) {
            return;
        }
        resetVar();
        float f2 = (canvasWidth - videoWidth) / 2.0f;
        float f3 = (canvasHeight - videoHeight) / 2.0f;
        float f4 = videoWidth / videoHeight;
        float f5 = canvasWidth / canvasHeight;
        float f6 = canvasHeight / videoHeight;
        float f7 = canvasWidth / videoWidth;
        switch (WhenMappings.$EnumSwitchMapping$0[scaleType.ordinal()]) {
            case 1:
                this.tranFx = f2;
                this.tranFy = f3;
                break;
            case 2:
                if (f4 <= f5) {
                    this.ratio = f7;
                    this.ratioX = true;
                    this.scaleFx = f7;
                    this.scaleFy = f7;
                    this.tranFy = (canvasHeight - (videoHeight * f7)) / 2.0f;
                    break;
                } else {
                    this.ratio = f6;
                    this.ratioX = false;
                    this.scaleFx = f6;
                    this.scaleFy = f6;
                    this.tranFx = (canvasWidth - (videoWidth * f6)) / 2.0f;
                    break;
                }
            case 3:
                if (videoWidth < canvasWidth && videoHeight < canvasHeight) {
                    this.tranFx = f2;
                    this.tranFy = f3;
                    break;
                } else if (f4 <= f5) {
                    this.ratio = f6;
                    this.ratioX = false;
                    this.scaleFx = f6;
                    this.scaleFy = f6;
                    this.tranFx = (canvasWidth - (videoWidth * f6)) / 2.0f;
                    break;
                } else {
                    this.ratio = f7;
                    this.ratioX = true;
                    this.scaleFx = f7;
                    this.scaleFy = f7;
                    this.tranFy = (canvasHeight - (videoHeight * f7)) / 2.0f;
                    break;
                }
            case 4:
                if (f4 <= f5) {
                    this.ratio = f6;
                    this.ratioX = false;
                    this.scaleFx = f6;
                    this.scaleFy = f6;
                    this.tranFx = (canvasWidth - (videoWidth * f6)) / 2.0f;
                    break;
                } else {
                    this.ratio = f7;
                    this.ratioX = true;
                    this.scaleFx = f7;
                    this.scaleFy = f7;
                    this.tranFy = (canvasHeight - (videoHeight * f7)) / 2.0f;
                    break;
                }
            case 5:
                if (f4 <= f5) {
                    this.ratio = f6;
                    this.ratioX = false;
                    this.scaleFx = f6;
                    this.scaleFy = f6;
                    break;
                } else {
                    this.ratio = f7;
                    this.ratioX = true;
                    this.scaleFx = f7;
                    this.scaleFy = f7;
                    break;
                }
            case 6:
                if (f4 <= f5) {
                    this.ratio = f6;
                    this.ratioX = false;
                    this.scaleFx = f6;
                    this.scaleFy = f6;
                    this.tranFx = canvasWidth - (videoWidth * f6);
                    break;
                } else {
                    this.ratio = f7;
                    this.ratioX = true;
                    this.scaleFx = f7;
                    this.scaleFy = f7;
                    this.tranFy = canvasHeight - (videoHeight * f7);
                    break;
                }
            case 7:
                this.ratio = Math.max(f7, f6);
                this.ratioX = f7 > f6;
                this.scaleFx = f7;
                this.scaleFy = f6;
                break;
            default:
                this.ratio = f7;
                this.ratioX = true;
                this.scaleFx = f7;
                this.scaleFy = f7;
                break;
        }
    }

    public final void setRatio(float f2) {
        this.ratio = f2;
    }

    public final void setRatioX(boolean z2) {
        this.ratioX = z2;
    }

    public final void setScaleFx(float f2) {
        this.scaleFx = f2;
    }

    public final void setScaleFy(float f2) {
        this.scaleFy = f2;
    }

    public final void setTranFx(float f2) {
        this.tranFx = f2;
    }

    public final void setTranFy(float f2) {
        this.tranFy = f2;
    }
}
