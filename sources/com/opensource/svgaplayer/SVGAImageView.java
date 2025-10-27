package com.opensource.svgaplayer;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.app.NotificationCompat;
import com.alipay.sdk.authjs.a;
import com.easefun.polyv.livecommon.module.modules.document.model.enums.PLVDocumentMarkToolType;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.just.agentweb.DefaultWebClient;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.utils.SVGARange;
import com.opensource.svgaplayer.utils.log.LogUtils;
import com.tencent.open.SocialConstants;
import com.umeng.analytics.pro.d;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u009e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\b\u0016\u0018\u00002\u00020\u0001:\u0003_`aB%\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0006\u00105\u001a\u000206J\u0016\u00107\u001a\u0002082\f\u00109\u001a\b\u0012\u0004\u0012\u00020\u00000:H\u0002J\b\u0010;\u001a\u00020<H\u0002J\n\u0010=\u001a\u0004\u0018\u00010>H\u0002J\u0010\u0010?\u001a\u0002062\u0006\u0010\u0004\u001a\u00020\u0005H\u0002J\u0012\u0010@\u001a\u0002062\b\u0010A\u001a\u0004\u0018\u00010BH\u0002J\u0012\u0010C\u001a\u0002062\b\u0010D\u001a\u0004\u0018\u00010*H\u0002J\b\u0010E\u001a\u000206H\u0014J\u0012\u0010F\u001a\u00020\u00122\b\u0010G\u001a\u0004\u0018\u00010HH\u0017J\u0010\u0010I\u001a\u0002062\u0006\u0010J\u001a\u00020\nH\u0002J\u0006\u0010K\u001a\u000206J\u001a\u0010L\u001a\u0002062\b\u0010M\u001a\u0004\u0018\u00010N2\u0006\u0010O\u001a\u00020\u0012H\u0002J\u000e\u0010P\u001a\u0002062\u0006\u0010Q\u001a\u000203J\u0010\u0010R\u001a\u0002062\b\u0010S\u001a\u0004\u0018\u00010TJ\u001a\u0010R\u001a\u0002062\b\u0010S\u001a\u0004\u0018\u00010T2\b\u0010U\u001a\u0004\u0018\u00010VJ\b\u0010W\u001a\u000206H\u0002J\u0006\u0010X\u001a\u000206J\u0010\u0010X\u001a\u0002062\u0006\u0010S\u001a\u00020TH\u0002J\u001a\u0010X\u001a\u0002062\b\u0010M\u001a\u0004\u0018\u00010N2\b\b\u0002\u0010O\u001a\u00020\u0012J\u0016\u0010Y\u001a\u0002062\u0006\u0010Z\u001a\u00020\u00072\u0006\u0010[\u001a\u00020\u0012J\u0016\u0010\\\u001a\u0002062\u0006\u0010]\u001a\u00020<2\u0006\u0010[\u001a\u00020\u0012J\u0006\u0010^\u001a\u000206J\u000e\u0010^\u001a\u0002062\u0006\u00105\u001a\u00020\u0012R\u000e\u0010\t\u001a\u00020\nX\u0082D¢\u0006\u0002\n\u0000R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R$\u0010\u0017\u001a\u00020\u00128\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u0014\"\u0004\b\u001b\u0010\u0016R\u001a\u0010\u001c\u001a\u00020\u001dX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u001e\u0010#\u001a\u00020\u00122\u0006\u0010\"\u001a\u00020\u0012@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0014R\u001a\u0010$\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R\u0010\u0010)\u001a\u0004\u0018\u00010*X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020,X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020.X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00100\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00101\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00102\u001a\u0004\u0018\u000103X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00104\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006b"}, d2 = {"Lcom/opensource/svgaplayer/SVGAImageView;", "Landroid/widget/ImageView;", d.R, "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "TAG", "", a.f3170c, "Lcom/opensource/svgaplayer/SVGACallback;", "getCallback", "()Lcom/opensource/svgaplayer/SVGACallback;", "setCallback", "(Lcom/opensource/svgaplayer/SVGACallback;)V", "clearsAfterDetached", "", "getClearsAfterDetached", "()Z", "setClearsAfterDetached", "(Z)V", "clearsAfterStop", "clearsAfterStop$annotations", "()V", "getClearsAfterStop", "setClearsAfterStop", "fillMode", "Lcom/opensource/svgaplayer/SVGAImageView$FillMode;", "getFillMode", "()Lcom/opensource/svgaplayer/SVGAImageView$FillMode;", "setFillMode", "(Lcom/opensource/svgaplayer/SVGAImageView$FillMode;)V", "<set-?>", "isAnimating", "loops", "getLoops", "()I", "setLoops", "(I)V", "mAnimator", "Landroid/animation/ValueAnimator;", "mAnimatorListener", "Lcom/opensource/svgaplayer/SVGAImageView$AnimatorListener;", "mAnimatorUpdateListener", "Lcom/opensource/svgaplayer/SVGAImageView$AnimatorUpdateListener;", "mAntiAlias", "mAutoPlay", "mEndFrame", "mItemClickAreaListener", "Lcom/opensource/svgaplayer/SVGAClickAreaListener;", "mStartFrame", PLVDocumentMarkToolType.CLEAR, "", "createParseCompletion", "Lcom/opensource/svgaplayer/SVGAParser$ParseCompletion;", "ref", "Ljava/lang/ref/WeakReference;", "generateScale", "", "getSVGADrawable", "Lcom/opensource/svgaplayer/SVGADrawable;", "loadAttrs", "onAnimationEnd", "animation", "Landroid/animation/Animator;", "onAnimatorUpdate", "animator", "onDetachedFromWindow", "onTouchEvent", NotificationCompat.CATEGORY_EVENT, "Landroid/view/MotionEvent;", "parserSource", SocialConstants.PARAM_SOURCE, "pauseAnimation", "play", SessionDescription.ATTR_RANGE, "Lcom/opensource/svgaplayer/utils/SVGARange;", "reverse", "setOnAnimKeyClickListener", "clickListener", "setVideoItem", "videoItem", "Lcom/opensource/svgaplayer/SVGAVideoEntity;", "dynamicItem", "Lcom/opensource/svgaplayer/SVGADynamicEntity;", "setupDrawable", "startAnimation", "stepToFrame", TypedValues.AttributesType.S_FRAME, "andPlay", "stepToPercentage", "percentage", "stopAnimation", "AnimatorListener", "AnimatorUpdateListener", "FillMode", BuildConfig.LIBRARY_PACKAGE_NAME}, k = 1, mv = {1, 1, 15})
/* loaded from: classes4.dex */
public class SVGAImageView extends ImageView {
    private final String TAG;
    private HashMap _$_findViewCache;

    @Nullable
    private SVGACallback callback;
    private boolean clearsAfterDetached;
    private boolean clearsAfterStop;

    @NotNull
    private FillMode fillMode;
    private boolean isAnimating;
    private int loops;
    private ValueAnimator mAnimator;
    private final AnimatorListener mAnimatorListener;
    private final AnimatorUpdateListener mAnimatorUpdateListener;
    private boolean mAntiAlias;
    private boolean mAutoPlay;
    private int mEndFrame;
    private SVGAClickAreaListener mItemClickAreaListener;
    private int mStartFrame;

    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0016J\u0012\u0010\u000b\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0016J\u0012\u0010\f\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0016J\u0012\u0010\r\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0016R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lcom/opensource/svgaplayer/SVGAImageView$AnimatorListener;", "Landroid/animation/Animator$AnimatorListener;", "view", "Lcom/opensource/svgaplayer/SVGAImageView;", "(Lcom/opensource/svgaplayer/SVGAImageView;)V", "weakReference", "Ljava/lang/ref/WeakReference;", "onAnimationCancel", "", "animation", "Landroid/animation/Animator;", "onAnimationEnd", "onAnimationRepeat", "onAnimationStart", BuildConfig.LIBRARY_PACKAGE_NAME}, k = 1, mv = {1, 1, 15})
    public static final class AnimatorListener implements Animator.AnimatorListener {
        private final WeakReference<SVGAImageView> weakReference;

        public AnimatorListener(@NotNull SVGAImageView view) {
            Intrinsics.checkParameterIsNotNull(view, "view");
            this.weakReference = new WeakReference<>(view);
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationCancel(@Nullable Animator animation) {
            SVGAImageView sVGAImageView = this.weakReference.get();
            if (sVGAImageView != null) {
                sVGAImageView.isAnimating = false;
            }
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(@Nullable Animator animation) {
            SVGAImageView sVGAImageView = this.weakReference.get();
            if (sVGAImageView != null) {
                sVGAImageView.onAnimationEnd(animation);
            }
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(@Nullable Animator animation) {
            SVGACallback callback;
            SVGAImageView sVGAImageView = this.weakReference.get();
            if (sVGAImageView == null || (callback = sVGAImageView.getCallback()) == null) {
                return;
            }
            callback.onRepeat();
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(@Nullable Animator animation) {
            SVGAImageView sVGAImageView = this.weakReference.get();
            if (sVGAImageView != null) {
                sVGAImageView.isAnimating = true;
            }
        }
    }

    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0016R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lcom/opensource/svgaplayer/SVGAImageView$AnimatorUpdateListener;", "Landroid/animation/ValueAnimator$AnimatorUpdateListener;", "view", "Lcom/opensource/svgaplayer/SVGAImageView;", "(Lcom/opensource/svgaplayer/SVGAImageView;)V", "weakReference", "Ljava/lang/ref/WeakReference;", "onAnimationUpdate", "", "animation", "Landroid/animation/ValueAnimator;", BuildConfig.LIBRARY_PACKAGE_NAME}, k = 1, mv = {1, 1, 15})
    public static final class AnimatorUpdateListener implements ValueAnimator.AnimatorUpdateListener {
        private final WeakReference<SVGAImageView> weakReference;

        public AnimatorUpdateListener(@NotNull SVGAImageView view) {
            Intrinsics.checkParameterIsNotNull(view, "view");
            this.weakReference = new WeakReference<>(view);
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(@Nullable ValueAnimator animation) {
            SVGAImageView sVGAImageView = this.weakReference.get();
            if (sVGAImageView != null) {
                sVGAImageView.onAnimatorUpdate(animation);
            }
        }
    }

    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lcom/opensource/svgaplayer/SVGAImageView$FillMode;", "", "(Ljava/lang/String;I)V", "Backward", "Forward", "Clear", BuildConfig.LIBRARY_PACKAGE_NAME}, k = 1, mv = {1, 1, 15})
    public enum FillMode {
        Backward,
        Forward,
        Clear
    }

    @Metadata(bv = {1, 0, 3}, k = 3, mv = {1, 1, 15})
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[FillMode.values().length];
            $EnumSwitchMapping$0 = iArr;
            iArr[FillMode.Backward.ordinal()] = 1;
            iArr[FillMode.Forward.ordinal()] = 2;
            iArr[FillMode.Clear.ordinal()] = 3;
        }
    }

    @JvmOverloads
    public SVGAImageView(@NotNull Context context) {
        this(context, null, 0, 6, null);
    }

    @JvmOverloads
    public SVGAImageView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
    }

    public /* synthetic */ SVGAImageView(Context context, AttributeSet attributeSet, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i2);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "It is recommended to use clearAfterDetached, or manually call to SVGAVideoEntity#clear.If you just consider cleaning up the canvas after playing, you can use FillMode#Clear.")
    public static /* synthetic */ void clearsAfterStop$annotations() {
    }

    private final SVGAParser.ParseCompletion createParseCompletion(final WeakReference<SVGAImageView> ref) {
        return new SVGAParser.ParseCompletion() { // from class: com.opensource.svgaplayer.SVGAImageView.createParseCompletion.1
            @Override // com.opensource.svgaplayer.SVGAParser.ParseCompletion
            public void onComplete(@NotNull SVGAVideoEntity videoItem) {
                Intrinsics.checkParameterIsNotNull(videoItem, "videoItem");
                SVGAImageView sVGAImageView = (SVGAImageView) ref.get();
                if (sVGAImageView != null) {
                    sVGAImageView.startAnimation(videoItem);
                }
            }

            @Override // com.opensource.svgaplayer.SVGAParser.ParseCompletion
            public void onError() {
            }
        };
    }

    private final double generateScale() throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        double d3 = 1.0d;
        try {
            Class<?> cls = Class.forName("android.animation.ValueAnimator");
            Method declaredMethod = cls.getDeclaredMethod("getDurationScale", new Class[0]);
            if (declaredMethod == null) {
                return 1.0d;
            }
            Object objInvoke = declaredMethod.invoke(cls, new Object[0]);
            if (objInvoke == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Float");
            }
            double dFloatValue = ((Float) objInvoke).floatValue();
            if (dFloatValue != 0.0d) {
                return dFloatValue;
            }
            try {
                Method declaredMethod2 = cls.getDeclaredMethod("setDurationScale", Float.TYPE);
                if (declaredMethod2 == null) {
                    return dFloatValue;
                }
                declaredMethod2.setAccessible(true);
                declaredMethod2.invoke(cls, Float.valueOf(1.0f));
                LogUtils.INSTANCE.info(this.TAG, "The animation duration scale has been reset to 1.0x, because you closed it on developer options.");
                return 1.0d;
            } catch (Exception e2) {
                e = e2;
                d3 = dFloatValue;
                e.printStackTrace();
                return d3;
            }
        } catch (Exception e3) {
            e = e3;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final SVGADrawable getSVGADrawable() {
        Drawable drawable = getDrawable();
        if (!(drawable instanceof SVGADrawable)) {
            drawable = null;
        }
        return (SVGADrawable) drawable;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    private final void loadAttrs(AttributeSet attrs) {
        Context context = getContext();
        Intrinsics.checkExpressionValueIsNotNull(context, "context");
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SVGAImageView, 0, 0);
        this.loops = typedArrayObtainStyledAttributes.getInt(R.styleable.SVGAImageView_loopCount, 0);
        this.clearsAfterStop = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SVGAImageView_clearsAfterStop, false);
        this.clearsAfterDetached = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SVGAImageView_clearsAfterDetached, false);
        this.mAntiAlias = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SVGAImageView_antiAlias, true);
        this.mAutoPlay = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SVGAImageView_autoPlay, true);
        String string = typedArrayObtainStyledAttributes.getString(R.styleable.SVGAImageView_fillMode);
        if (string != null) {
            switch (string.hashCode()) {
                case 48:
                    if (string.equals("0")) {
                        this.fillMode = FillMode.Backward;
                        break;
                    }
                    break;
                case 49:
                    if (string.equals("1")) {
                        this.fillMode = FillMode.Forward;
                        break;
                    }
                    break;
                case 50:
                    if (string.equals("2")) {
                        this.fillMode = FillMode.Clear;
                        break;
                    }
                    break;
            }
        }
        String string2 = typedArrayObtainStyledAttributes.getString(R.styleable.SVGAImageView_source);
        if (string2 != null) {
            parserSource(string2);
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onAnimationEnd(Animator animation) {
        this.isAnimating = false;
        stopAnimation();
        SVGADrawable sVGADrawable = getSVGADrawable();
        if (sVGADrawable != null) {
            int i2 = WhenMappings.$EnumSwitchMapping$0[this.fillMode.ordinal()];
            if (i2 == 1) {
                sVGADrawable.setCurrentFrame$com_opensource_svgaplayer(this.mStartFrame);
            } else if (i2 == 2) {
                sVGADrawable.setCurrentFrame$com_opensource_svgaplayer(this.mEndFrame);
            } else if (i2 == 3) {
                sVGADrawable.setCleared$com_opensource_svgaplayer(true);
            }
        }
        SVGACallback sVGACallback = this.callback;
        if (sVGACallback != null) {
            sVGACallback.onFinished();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onAnimatorUpdate(ValueAnimator animator) {
        SVGADrawable sVGADrawable = getSVGADrawable();
        if (sVGADrawable != null) {
            Object animatedValue = animator != null ? animator.getAnimatedValue() : null;
            if (animatedValue == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
            }
            sVGADrawable.setCurrentFrame$com_opensource_svgaplayer(((Integer) animatedValue).intValue());
            double currentFrame = (sVGADrawable.getCurrentFrame() + 1) / sVGADrawable.getVideoItem().getFrames();
            SVGACallback sVGACallback = this.callback;
            if (sVGACallback != null) {
                sVGACallback.onStep(sVGADrawable.getCurrentFrame(), currentFrame);
            }
        }
    }

    private final void parserSource(String source) {
        WeakReference<SVGAImageView> weakReference = new WeakReference<>(this);
        SVGAParser sVGAParser = new SVGAParser(getContext());
        if (StringsKt__StringsJVMKt.startsWith$default(source, DefaultWebClient.HTTP_SCHEME, false, 2, null) || StringsKt__StringsJVMKt.startsWith$default(source, DefaultWebClient.HTTPS_SCHEME, false, 2, null)) {
            SVGAParser.decodeFromURL$default(sVGAParser, new URL(source), createParseCompletion(weakReference), null, 4, null);
        } else {
            SVGAParser.decodeFromAssets$default(sVGAParser, source, createParseCompletion(weakReference), null, 4, null);
        }
    }

    private final void play(SVGARange range, boolean reverse) {
        LogUtils.INSTANCE.info(this.TAG, "================ start animation ================");
        SVGADrawable sVGADrawable = getSVGADrawable();
        if (sVGADrawable != null) {
            setupDrawable();
            this.mStartFrame = Math.max(0, range != null ? range.getLocation() : 0);
            int iMin = Math.min(sVGADrawable.getVideoItem().getFrames() - 1, ((range != null ? range.getLocation() : 0) + (range != null ? range.getLength() : Integer.MAX_VALUE)) - 1);
            this.mEndFrame = iMin;
            ValueAnimator animator = ValueAnimator.ofInt(this.mStartFrame, iMin);
            Intrinsics.checkExpressionValueIsNotNull(animator, "animator");
            animator.setInterpolator(new LinearInterpolator());
            animator.setDuration((long) ((((this.mEndFrame - this.mStartFrame) + 1) * (1000 / r0.getFPS())) / generateScale()));
            int i2 = this.loops;
            animator.setRepeatCount(i2 <= 0 ? 99999 : i2 - 1);
            animator.addUpdateListener(this.mAnimatorUpdateListener);
            animator.addListener(this.mAnimatorListener);
            if (reverse) {
                animator.reverse();
            } else {
                animator.start();
            }
            this.mAnimator = animator;
        }
    }

    private final void setupDrawable() {
        SVGADrawable sVGADrawable = getSVGADrawable();
        if (sVGADrawable != null) {
            sVGADrawable.setCleared$com_opensource_svgaplayer(false);
            ImageView.ScaleType scaleType = getScaleType();
            Intrinsics.checkExpressionValueIsNotNull(scaleType, "scaleType");
            sVGADrawable.setScaleType(scaleType);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void startAnimation(final SVGAVideoEntity videoItem) {
        post(new Runnable() { // from class: com.opensource.svgaplayer.SVGAImageView.startAnimation.1
            @Override // java.lang.Runnable
            public final void run() {
                videoItem.setAntiAlias(SVGAImageView.this.mAntiAlias);
                SVGAImageView.this.setVideoItem(videoItem);
                SVGADrawable sVGADrawable = SVGAImageView.this.getSVGADrawable();
                if (sVGADrawable != null) {
                    ImageView.ScaleType scaleType = SVGAImageView.this.getScaleType();
                    Intrinsics.checkExpressionValueIsNotNull(scaleType, "scaleType");
                    sVGADrawable.setScaleType(scaleType);
                }
                if (SVGAImageView.this.mAutoPlay) {
                    SVGAImageView.this.startAnimation();
                }
            }
        });
    }

    public static /* synthetic */ void startAnimation$default(SVGAImageView sVGAImageView, SVGARange sVGARange, boolean z2, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: startAnimation");
        }
        if ((i2 & 2) != 0) {
            z2 = false;
        }
        sVGAImageView.startAnimation(sVGARange, z2);
    }

    public void _$_clearFindViewByIdCache() {
        HashMap map = this._$_findViewCache;
        if (map != null) {
            map.clear();
        }
    }

    public View _$_findCachedViewById(int i2) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i2));
        if (view != null) {
            return view;
        }
        View viewFindViewById = findViewById(i2);
        this._$_findViewCache.put(Integer.valueOf(i2), viewFindViewById);
        return viewFindViewById;
    }

    public final void clear() {
        SVGADrawable sVGADrawable = getSVGADrawable();
        if (sVGADrawable != null) {
            sVGADrawable.setCleared$com_opensource_svgaplayer(true);
        }
        SVGADrawable sVGADrawable2 = getSVGADrawable();
        if (sVGADrawable2 != null) {
            sVGADrawable2.clear();
        }
        setImageDrawable(null);
    }

    @Nullable
    public final SVGACallback getCallback() {
        return this.callback;
    }

    public final boolean getClearsAfterDetached() {
        return this.clearsAfterDetached;
    }

    public final boolean getClearsAfterStop() {
        return this.clearsAfterStop;
    }

    @NotNull
    public final FillMode getFillMode() {
        return this.fillMode;
    }

    public final int getLoops() {
        return this.loops;
    }

    /* renamed from: isAnimating, reason: from getter */
    public final boolean getIsAnimating() {
        return this.isAnimating;
    }

    @Override // android.widget.ImageView, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnimation(this.clearsAfterDetached);
        if (this.clearsAfterDetached) {
            clear();
        }
    }

    @Override // android.view.View
    @SuppressLint({"ClickableViewAccessibility"})
    public boolean onTouchEvent(@Nullable MotionEvent event) {
        SVGAClickAreaListener sVGAClickAreaListener;
        if (event == null || event.getAction() != 0) {
            return super.onTouchEvent(event);
        }
        SVGADrawable sVGADrawable = getSVGADrawable();
        if (sVGADrawable == null) {
            return super.onTouchEvent(event);
        }
        for (Map.Entry<String, int[]> entry : sVGADrawable.getDynamicItem().getMClickMap$com_opensource_svgaplayer().entrySet()) {
            String key = entry.getKey();
            int[] value = entry.getValue();
            if (event.getX() >= value[0] && event.getX() <= value[2] && event.getY() >= value[1] && event.getY() <= value[3] && (sVGAClickAreaListener = this.mItemClickAreaListener) != null) {
                sVGAClickAreaListener.onClick(key);
                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    public final void pauseAnimation() {
        stopAnimation(false);
        SVGACallback sVGACallback = this.callback;
        if (sVGACallback != null) {
            sVGACallback.onPause();
        }
    }

    public final void setCallback(@Nullable SVGACallback sVGACallback) {
        this.callback = sVGACallback;
    }

    public final void setClearsAfterDetached(boolean z2) {
        this.clearsAfterDetached = z2;
    }

    public final void setClearsAfterStop(boolean z2) {
        this.clearsAfterStop = z2;
    }

    public final void setFillMode(@NotNull FillMode fillMode) {
        Intrinsics.checkParameterIsNotNull(fillMode, "<set-?>");
        this.fillMode = fillMode;
    }

    public final void setLoops(int i2) {
        this.loops = i2;
    }

    public final void setOnAnimKeyClickListener(@NotNull SVGAClickAreaListener clickListener) {
        Intrinsics.checkParameterIsNotNull(clickListener, "clickListener");
        this.mItemClickAreaListener = clickListener;
    }

    public final void setVideoItem(@Nullable SVGAVideoEntity videoItem) {
        setVideoItem(videoItem, new SVGADynamicEntity());
    }

    public final void stepToFrame(int frame, boolean andPlay) {
        pauseAnimation();
        SVGADrawable sVGADrawable = getSVGADrawable();
        if (sVGADrawable != null) {
            sVGADrawable.setCurrentFrame$com_opensource_svgaplayer(frame);
            if (andPlay) {
                startAnimation();
                ValueAnimator valueAnimator = this.mAnimator;
                if (valueAnimator != null) {
                    valueAnimator.setCurrentPlayTime((long) (Math.max(0.0f, Math.min(1.0f, frame / sVGADrawable.getVideoItem().getFrames())) * valueAnimator.getDuration()));
                }
            }
        }
    }

    public final void stepToPercentage(double percentage, boolean andPlay) {
        Drawable drawable = getDrawable();
        if (!(drawable instanceof SVGADrawable)) {
            drawable = null;
        }
        SVGADrawable sVGADrawable = (SVGADrawable) drawable;
        if (sVGADrawable != null) {
            int frames = (int) (sVGADrawable.getVideoItem().getFrames() * percentage);
            if (frames >= sVGADrawable.getVideoItem().getFrames() && frames > 0) {
                frames = sVGADrawable.getVideoItem().getFrames() - 1;
            }
            stepToFrame(frames, andPlay);
        }
    }

    public final void stopAnimation() {
        stopAnimation(this.clearsAfterStop);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public SVGAImageView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        Intrinsics.checkParameterIsNotNull(context, "context");
        this.TAG = "SVGAImageView";
        this.fillMode = FillMode.Forward;
        this.mAntiAlias = true;
        this.mAutoPlay = true;
        this.mAnimatorListener = new AnimatorListener(this);
        this.mAnimatorUpdateListener = new AnimatorUpdateListener(this);
        if (attributeSet != null) {
            loadAttrs(attributeSet);
        }
    }

    public final void setVideoItem(@Nullable SVGAVideoEntity videoItem, @Nullable SVGADynamicEntity dynamicItem) {
        if (videoItem == null) {
            setImageDrawable(null);
            return;
        }
        if (dynamicItem == null) {
            dynamicItem = new SVGADynamicEntity();
        }
        SVGADrawable sVGADrawable = new SVGADrawable(videoItem, dynamicItem);
        sVGADrawable.setCleared$com_opensource_svgaplayer(true);
        setImageDrawable(sVGADrawable);
    }

    public final void startAnimation() {
        startAnimation(null, false);
    }

    public final void stopAnimation(boolean clear) {
        ValueAnimator valueAnimator = this.mAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        ValueAnimator valueAnimator2 = this.mAnimator;
        if (valueAnimator2 != null) {
            valueAnimator2.removeAllListeners();
        }
        ValueAnimator valueAnimator3 = this.mAnimator;
        if (valueAnimator3 != null) {
            valueAnimator3.removeAllUpdateListeners();
        }
        SVGADrawable sVGADrawable = getSVGADrawable();
        if (sVGADrawable != null) {
            sVGADrawable.stop();
        }
        SVGADrawable sVGADrawable2 = getSVGADrawable();
        if (sVGADrawable2 != null) {
            sVGADrawable2.setCleared$com_opensource_svgaplayer(clear);
        }
    }

    public final void startAnimation(@Nullable SVGARange range, boolean reverse) {
        stopAnimation(false);
        play(range, reverse);
    }
}
