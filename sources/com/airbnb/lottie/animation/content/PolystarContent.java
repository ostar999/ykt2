package com.airbnb.lottie.animation.content;

import android.graphics.Path;
import android.graphics.PointF;
import androidx.annotation.Nullable;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.content.PolystarShape;
import com.airbnb.lottie.model.content.ShapeTrimPath;
import com.airbnb.lottie.model.layer.BaseLayer;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.List;

/* loaded from: classes.dex */
public class PolystarContent implements PathContent, BaseKeyframeAnimation.AnimationListener, KeyPathElementContent {
    private static final float POLYGON_MAGIC_NUMBER = 0.25f;
    private static final float POLYSTAR_MAGIC_NUMBER = 0.47829f;
    private final boolean hidden;

    @Nullable
    private final BaseKeyframeAnimation<?, Float> innerRadiusAnimation;

    @Nullable
    private final BaseKeyframeAnimation<?, Float> innerRoundednessAnimation;
    private boolean isPathValid;
    private final LottieDrawable lottieDrawable;
    private final String name;
    private final BaseKeyframeAnimation<?, Float> outerRadiusAnimation;
    private final BaseKeyframeAnimation<?, Float> outerRoundednessAnimation;
    private final BaseKeyframeAnimation<?, Float> pointsAnimation;
    private final BaseKeyframeAnimation<?, PointF> positionAnimation;
    private final BaseKeyframeAnimation<?, Float> rotationAnimation;
    private final PolystarShape.Type type;
    private final Path path = new Path();
    private CompoundTrimPathContent trimPaths = new CompoundTrimPathContent();

    /* renamed from: com.airbnb.lottie.animation.content.PolystarContent$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$airbnb$lottie$model$content$PolystarShape$Type;

        static {
            int[] iArr = new int[PolystarShape.Type.values().length];
            $SwitchMap$com$airbnb$lottie$model$content$PolystarShape$Type = iArr;
            try {
                iArr[PolystarShape.Type.STAR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$model$content$PolystarShape$Type[PolystarShape.Type.POLYGON.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public PolystarContent(LottieDrawable lottieDrawable, BaseLayer baseLayer, PolystarShape polystarShape) {
        this.lottieDrawable = lottieDrawable;
        this.name = polystarShape.getName();
        PolystarShape.Type type = polystarShape.getType();
        this.type = type;
        this.hidden = polystarShape.isHidden();
        BaseKeyframeAnimation<Float, Float> baseKeyframeAnimationCreateAnimation = polystarShape.getPoints().createAnimation();
        this.pointsAnimation = baseKeyframeAnimationCreateAnimation;
        BaseKeyframeAnimation<PointF, PointF> baseKeyframeAnimationCreateAnimation2 = polystarShape.getPosition().createAnimation();
        this.positionAnimation = baseKeyframeAnimationCreateAnimation2;
        BaseKeyframeAnimation<Float, Float> baseKeyframeAnimationCreateAnimation3 = polystarShape.getRotation().createAnimation();
        this.rotationAnimation = baseKeyframeAnimationCreateAnimation3;
        BaseKeyframeAnimation<Float, Float> baseKeyframeAnimationCreateAnimation4 = polystarShape.getOuterRadius().createAnimation();
        this.outerRadiusAnimation = baseKeyframeAnimationCreateAnimation4;
        BaseKeyframeAnimation<Float, Float> baseKeyframeAnimationCreateAnimation5 = polystarShape.getOuterRoundedness().createAnimation();
        this.outerRoundednessAnimation = baseKeyframeAnimationCreateAnimation5;
        PolystarShape.Type type2 = PolystarShape.Type.STAR;
        if (type == type2) {
            this.innerRadiusAnimation = polystarShape.getInnerRadius().createAnimation();
            this.innerRoundednessAnimation = polystarShape.getInnerRoundedness().createAnimation();
        } else {
            this.innerRadiusAnimation = null;
            this.innerRoundednessAnimation = null;
        }
        baseLayer.addAnimation(baseKeyframeAnimationCreateAnimation);
        baseLayer.addAnimation(baseKeyframeAnimationCreateAnimation2);
        baseLayer.addAnimation(baseKeyframeAnimationCreateAnimation3);
        baseLayer.addAnimation(baseKeyframeAnimationCreateAnimation4);
        baseLayer.addAnimation(baseKeyframeAnimationCreateAnimation5);
        if (type == type2) {
            baseLayer.addAnimation(this.innerRadiusAnimation);
            baseLayer.addAnimation(this.innerRoundednessAnimation);
        }
        baseKeyframeAnimationCreateAnimation.addUpdateListener(this);
        baseKeyframeAnimationCreateAnimation2.addUpdateListener(this);
        baseKeyframeAnimationCreateAnimation3.addUpdateListener(this);
        baseKeyframeAnimationCreateAnimation4.addUpdateListener(this);
        baseKeyframeAnimationCreateAnimation5.addUpdateListener(this);
        if (type == type2) {
            this.innerRadiusAnimation.addUpdateListener(this);
            this.innerRoundednessAnimation.addUpdateListener(this);
        }
    }

    private void createPolygonPath() {
        int i2;
        double d3;
        double d4;
        double d5;
        int iFloor = (int) Math.floor(this.pointsAnimation.getValue().floatValue());
        double radians = Math.toRadians((this.rotationAnimation == null ? 0.0d : r2.getValue().floatValue()) - 90.0d);
        double d6 = iFloor;
        float fFloatValue = this.outerRoundednessAnimation.getValue().floatValue() / 100.0f;
        float fFloatValue2 = this.outerRadiusAnimation.getValue().floatValue();
        double d7 = fFloatValue2;
        float fCos = (float) (Math.cos(radians) * d7);
        float fSin = (float) (Math.sin(radians) * d7);
        this.path.moveTo(fCos, fSin);
        double d8 = (float) (6.283185307179586d / d6);
        double d9 = radians + d8;
        double dCeil = Math.ceil(d6);
        int i3 = 0;
        while (i3 < dCeil) {
            float fCos2 = (float) (Math.cos(d9) * d7);
            double d10 = dCeil;
            float fSin2 = (float) (d7 * Math.sin(d9));
            if (fFloatValue != 0.0f) {
                d4 = d7;
                i2 = i3;
                d3 = d9;
                double dAtan2 = (float) (Math.atan2(fSin, fCos) - 1.5707963267948966d);
                float fCos3 = (float) Math.cos(dAtan2);
                float fSin3 = (float) Math.sin(dAtan2);
                d5 = d8;
                double dAtan22 = (float) (Math.atan2(fSin2, fCos2) - 1.5707963267948966d);
                float fCos4 = (float) Math.cos(dAtan22);
                float fSin4 = (float) Math.sin(dAtan22);
                float f2 = fFloatValue2 * fFloatValue * POLYGON_MAGIC_NUMBER;
                this.path.cubicTo(fCos - (fCos3 * f2), fSin - (fSin3 * f2), fCos2 + (fCos4 * f2), fSin2 + (f2 * fSin4), fCos2, fSin2);
            } else {
                i2 = i3;
                d3 = d9;
                d4 = d7;
                d5 = d8;
                this.path.lineTo(fCos2, fSin2);
            }
            d9 = d3 + d5;
            i3 = i2 + 1;
            fSin = fSin2;
            fCos = fCos2;
            dCeil = d10;
            d7 = d4;
            d8 = d5;
        }
        PointF value = this.positionAnimation.getValue();
        this.path.offset(value.x, value.y);
        this.path.close();
    }

    private void createStarPath() {
        int i2;
        float f2;
        float f3;
        double d3;
        float fSin;
        float f4;
        float f5;
        float f6;
        double d4;
        float f7;
        float f8;
        float f9;
        double d5;
        float fFloatValue = this.pointsAnimation.getValue().floatValue();
        double radians = Math.toRadians((this.rotationAnimation == null ? 0.0d : r2.getValue().floatValue()) - 90.0d);
        double d6 = fFloatValue;
        float f10 = (float) (6.283185307179586d / d6);
        float f11 = f10 / 2.0f;
        float f12 = fFloatValue - ((int) fFloatValue);
        int i3 = (f12 > 0.0f ? 1 : (f12 == 0.0f ? 0 : -1));
        if (i3 != 0) {
            radians += (1.0f - f12) * f11;
        }
        float fFloatValue2 = this.outerRadiusAnimation.getValue().floatValue();
        float fFloatValue3 = this.innerRadiusAnimation.getValue().floatValue();
        BaseKeyframeAnimation<?, Float> baseKeyframeAnimation = this.innerRoundednessAnimation;
        float fFloatValue4 = baseKeyframeAnimation != null ? baseKeyframeAnimation.getValue().floatValue() / 100.0f : 0.0f;
        BaseKeyframeAnimation<?, Float> baseKeyframeAnimation2 = this.outerRoundednessAnimation;
        float fFloatValue5 = baseKeyframeAnimation2 != null ? baseKeyframeAnimation2.getValue().floatValue() / 100.0f : 0.0f;
        if (i3 != 0) {
            f4 = ((fFloatValue2 - fFloatValue3) * f12) + fFloatValue3;
            i2 = i3;
            double d7 = f4;
            float fCos = (float) (d7 * Math.cos(radians));
            fSin = (float) (d7 * Math.sin(radians));
            this.path.moveTo(fCos, fSin);
            d3 = radians + ((f10 * f12) / 2.0f);
            f2 = fCos;
            f3 = f11;
        } else {
            i2 = i3;
            double d8 = fFloatValue2;
            float fCos2 = (float) (Math.cos(radians) * d8);
            float fSin2 = (float) (d8 * Math.sin(radians));
            this.path.moveTo(fCos2, fSin2);
            f2 = fCos2;
            f3 = f11;
            d3 = radians + f3;
            fSin = fSin2;
            f4 = 0.0f;
        }
        double dCeil = Math.ceil(d6) * 2.0d;
        int i4 = 0;
        float f13 = f3;
        float f14 = f2;
        boolean z2 = false;
        while (true) {
            double d9 = i4;
            if (d9 >= dCeil) {
                PointF value = this.positionAnimation.getValue();
                this.path.offset(value.x, value.y);
                this.path.close();
                return;
            }
            float f15 = z2 ? fFloatValue2 : fFloatValue3;
            if (f4 == 0.0f || d9 != dCeil - 2.0d) {
                f5 = f10;
                f6 = f13;
            } else {
                f5 = f10;
                f6 = (f10 * f12) / 2.0f;
            }
            if (f4 == 0.0f || d9 != dCeil - 1.0d) {
                d4 = d9;
                f7 = f4;
                f4 = f15;
            } else {
                d4 = d9;
                f7 = f4;
            }
            double d10 = f4;
            double d11 = dCeil;
            float fCos3 = (float) (d10 * Math.cos(d3));
            float fSin3 = (float) (d10 * Math.sin(d3));
            if (fFloatValue4 == 0.0f && fFloatValue5 == 0.0f) {
                this.path.lineTo(fCos3, fSin3);
                d5 = d3;
                f8 = fFloatValue4;
                f9 = fFloatValue5;
            } else {
                f8 = fFloatValue4;
                double dAtan2 = (float) (Math.atan2(fSin, f14) - 1.5707963267948966d);
                float fCos4 = (float) Math.cos(dAtan2);
                float fSin4 = (float) Math.sin(dAtan2);
                f9 = fFloatValue5;
                d5 = d3;
                double dAtan22 = (float) (Math.atan2(fSin3, fCos3) - 1.5707963267948966d);
                float fCos5 = (float) Math.cos(dAtan22);
                float fSin5 = (float) Math.sin(dAtan22);
                float f16 = z2 ? f8 : f9;
                float f17 = z2 ? f9 : f8;
                float f18 = z2 ? fFloatValue3 : fFloatValue2;
                float f19 = z2 ? fFloatValue2 : fFloatValue3;
                float f20 = f18 * f16 * POLYSTAR_MAGIC_NUMBER;
                float f21 = fCos4 * f20;
                float f22 = f20 * fSin4;
                float f23 = f19 * f17 * POLYSTAR_MAGIC_NUMBER;
                float f24 = fCos5 * f23;
                float f25 = f23 * fSin5;
                if (i2 != 0) {
                    if (i4 == 0) {
                        f21 *= f12;
                        f22 *= f12;
                    } else if (d4 == d11 - 1.0d) {
                        f24 *= f12;
                        f25 *= f12;
                    }
                }
                this.path.cubicTo(f14 - f21, fSin - f22, fCos3 + f24, fSin3 + f25, fCos3, fSin3);
            }
            d3 = d5 + f6;
            z2 = !z2;
            i4++;
            f14 = fCos3;
            fSin = fSin3;
            fFloatValue5 = f9;
            fFloatValue4 = f8;
            f4 = f7;
            f10 = f5;
            dCeil = d11;
        }
    }

    private void invalidate() {
        this.isPathValid = false;
        this.lottieDrawable.invalidateSelf();
    }

    @Override // com.airbnb.lottie.model.KeyPathElement
    public <T> void addValueCallback(T t2, @Nullable LottieValueCallback<T> lottieValueCallback) {
        BaseKeyframeAnimation<?, Float> baseKeyframeAnimation;
        BaseKeyframeAnimation<?, Float> baseKeyframeAnimation2;
        if (t2 == LottieProperty.POLYSTAR_POINTS) {
            this.pointsAnimation.setValueCallback(lottieValueCallback);
            return;
        }
        if (t2 == LottieProperty.POLYSTAR_ROTATION) {
            this.rotationAnimation.setValueCallback(lottieValueCallback);
            return;
        }
        if (t2 == LottieProperty.POSITION) {
            this.positionAnimation.setValueCallback(lottieValueCallback);
            return;
        }
        if (t2 == LottieProperty.POLYSTAR_INNER_RADIUS && (baseKeyframeAnimation2 = this.innerRadiusAnimation) != null) {
            baseKeyframeAnimation2.setValueCallback(lottieValueCallback);
            return;
        }
        if (t2 == LottieProperty.POLYSTAR_OUTER_RADIUS) {
            this.outerRadiusAnimation.setValueCallback(lottieValueCallback);
            return;
        }
        if (t2 == LottieProperty.POLYSTAR_INNER_ROUNDEDNESS && (baseKeyframeAnimation = this.innerRoundednessAnimation) != null) {
            baseKeyframeAnimation.setValueCallback(lottieValueCallback);
        } else if (t2 == LottieProperty.POLYSTAR_OUTER_ROUNDEDNESS) {
            this.outerRoundednessAnimation.setValueCallback(lottieValueCallback);
        }
    }

    @Override // com.airbnb.lottie.animation.content.Content
    public String getName() {
        return this.name;
    }

    @Override // com.airbnb.lottie.animation.content.PathContent
    public Path getPath() {
        if (this.isPathValid) {
            return this.path;
        }
        this.path.reset();
        if (this.hidden) {
            this.isPathValid = true;
            return this.path;
        }
        int i2 = AnonymousClass1.$SwitchMap$com$airbnb$lottie$model$content$PolystarShape$Type[this.type.ordinal()];
        if (i2 == 1) {
            createStarPath();
        } else if (i2 == 2) {
            createPolygonPath();
        }
        this.path.close();
        this.trimPaths.apply(this.path);
        this.isPathValid = true;
        return this.path;
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.AnimationListener
    public void onValueChanged() {
        invalidate();
    }

    @Override // com.airbnb.lottie.model.KeyPathElement
    public void resolveKeyPath(KeyPath keyPath, int i2, List<KeyPath> list, KeyPath keyPath2) {
        MiscUtils.resolveKeyPath(keyPath, i2, list, keyPath2, this);
    }

    @Override // com.airbnb.lottie.animation.content.Content
    public void setContents(List<Content> list, List<Content> list2) {
        for (int i2 = 0; i2 < list.size(); i2++) {
            Content content = list.get(i2);
            if (content instanceof TrimPathContent) {
                TrimPathContent trimPathContent = (TrimPathContent) content;
                if (trimPathContent.getType() == ShapeTrimPath.Type.SIMULTANEOUSLY) {
                    this.trimPaths.addTrimPath(trimPathContent);
                    trimPathContent.addListener(this);
                }
            }
        }
    }
}
