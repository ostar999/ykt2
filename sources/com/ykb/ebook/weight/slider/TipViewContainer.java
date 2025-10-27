package com.ykb.ebook.weight.slider;

import android.R;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.ColorInt;
import androidx.core.math.MathUtils;
import androidx.transition.Fade;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;
import com.ykb.ebook.extensions.ContextExtensionsKt;
import com.ykb.ebook.extensions.ConvertExtensionsKt;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0012\n\u0002\u0010\r\n\u0002\b\u0007\u0018\u0000 V2\u00020\u0001:\u0001VB%\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0006\u00104\u001a\u000205J\u000e\u00106\u001a\u0002052\u0006\u00107\u001a\u00020*J\u000e\u00108\u001a\u0002052\u0006\u00107\u001a\u00020\u0010J\b\u00109\u001a\u000205H\u0002J\u0014\u0010:\u001a\u0004\u0018\u00010;2\b\u00107\u001a\u0004\u0018\u00010\u0010H\u0002J\b\u0010<\u001a\u00020=H\u0002J\b\u0010>\u001a\u00020=H\u0002J\u0006\u0010?\u001a\u000205J\u001e\u0010@\u001a\u0002052\u0006\u0010A\u001a\u00020=2\u0006\u0010B\u001a\u00020=2\u0006\u0010C\u001a\u00020=J(\u0010D\u001a\u0002052\u0006\u0010E\u001a\u00020\u00072\u0006\u0010F\u001a\u00020\u00072\u0006\u0010G\u001a\u00020\u00072\u0006\u0010H\u001a\u00020\u0007H\u0014J\u0016\u0010I\u001a\u0002052\u0006\u0010J\u001a\u00020\u00072\u0006\u0010K\u001a\u00020\u0007J\u0010\u0010L\u001a\u0002052\b\b\u0001\u0010M\u001a\u00020\u0007J\u000e\u0010N\u001a\u0002052\u0006\u0010O\u001a\u00020PJ\u0010\u0010Q\u001a\u0002052\b\b\u0001\u0010M\u001a\u00020\u0007J\u0006\u0010R\u001a\u000205J\u001c\u0010S\u001a\u0002052\b\b\u0002\u0010A\u001a\u00020=2\b\b\u0002\u0010B\u001a\u00020=H\u0002J\u0012\u0010T\u001a\u0002052\b\u00107\u001a\u0004\u0018\u00010\u0010H\u0002J\b\u0010U\u001a\u000205H\u0002R\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0015\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u001a\u001a\u00020\u001bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u001a\u0010\u001f\u001a\u00020\u001bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u001c\"\u0004\b \u0010\u001eR\u001a\u0010!\u001a\u00020\u001bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\u001c\"\u0004\b\"\u0010\u001eR\u000e\u0010#\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010%\u001a\u00020&¢\u0006\b\n\u0000\u001a\u0004\b'\u0010(R\u0010\u0010)\u001a\u0004\u0018\u00010*X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010,\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010.\"\u0004\b/\u00100R\u001a\u00101\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b2\u0010.\"\u0004\b3\u00100¨\u0006W"}, d2 = {"Lcom/ykb/ebook/weight/slider/TipViewContainer;", "Landroid/widget/FrameLayout;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "animator", "Lcom/ykb/ebook/weight/slider/TipViewAnimator;", "getAnimator", "()Lcom/ykb/ebook/weight/slider/TipViewAnimator;", "setAnimator", "(Lcom/ykb/ebook/weight/slider/TipViewAnimator;)V", "customTipView", "Landroid/view/View;", "getCustomTipView", "()Landroid/view/View;", "setCustomTipView", "(Landroid/view/View;)V", "defaultSpace", "defaultTipView", "Lcom/ykb/ebook/weight/slider/DefaultTipView;", "defaultTransition", "Landroidx/transition/Fade;", "isAttached", "", "()Z", "setAttached", "(Z)V", "isClippingEnabled", "setClippingEnabled", "isTipTextAutoChange", "setTipTextAutoChange", "locationOnScreenX", "locationOnScreenY", "showRunnable", "Ljava/lang/Runnable;", "getShowRunnable", "()Ljava/lang/Runnable;", "slider", "Lcom/ykb/ebook/weight/slider/BaseSlider;", "tipViewId", "verticalOffset", "getVerticalOffset", "()I", "setVerticalOffset", "(I)V", "windowWidth", "getWindowWidth", "setWindowWidth", "addTipViewIfNeed", "", "attachTipView", "view", "detachTipView", "executeTransition", "getContentView", "Landroid/view/ViewGroup;", "getRelativeCX", "", "getRelativeCY", "hide", "onLocationChanged", "cx", "cy", "value", "onSizeChanged", "w", "h", "oldw", "oldh", "setSize", "width", "height", "setTipBackground", "color", "setTipText", "text", "", "setTipTextColor", "show", "updateLocation", "updateLocationOnScreen", "updateParams", "Companion", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes8.dex */
public final class TipViewContainer extends FrameLayout {

    @NotNull
    public static final String TAG = "TipViewContainer";

    @Nullable
    private TipViewAnimator animator;

    @Nullable
    private View customTipView;
    private final int defaultSpace;

    @NotNull
    private DefaultTipView defaultTipView;

    @NotNull
    private Fade defaultTransition;
    private boolean isAttached;
    private boolean isClippingEnabled;
    private boolean isTipTextAutoChange;
    private int locationOnScreenX;
    private int locationOnScreenY;

    @NotNull
    private final Runnable showRunnable;

    @Nullable
    private BaseSlider slider;
    private final int tipViewId;
    private int verticalOffset;
    private int windowWidth;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public TipViewContainer(@NotNull Context context) {
        this(context, null, 0, 6, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public TipViewContainer(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public /* synthetic */ TipViewContainer(Context context, AttributeSet attributeSet, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i2);
    }

    private final void executeTransition() {
        TipViewAnimator tipViewAnimator = this.animator;
        Transition transitionCreateTransition = tipViewAnimator != null ? tipViewAnimator.createTransition() : null;
        if (transitionCreateTransition == null) {
            transitionCreateTransition = this.defaultTransition;
        } else if (transitionCreateTransition instanceof ITipTransition) {
            ((ITipTransition) transitionCreateTransition).updateLocation(this.locationOnScreenY, this.locationOnScreenY + getRelativeCY() + this.verticalOffset);
        }
        TransitionManager.beginDelayedTransition(this, transitionCreateTransition);
    }

    private final ViewGroup getContentView(View view) {
        if (view == null) {
            return null;
        }
        View rootView = view.getRootView();
        ViewGroup viewGroup = (ViewGroup) rootView.findViewById(R.id.content);
        if (viewGroup != null) {
            return viewGroup;
        }
        if (rootView == view || !(rootView instanceof ViewGroup)) {
            return null;
        }
        return (ViewGroup) rootView;
    }

    private final float getRelativeCX() {
        BaseSlider baseSlider = this.slider;
        if (baseSlider != null) {
            return baseSlider.getThumbCenterX();
        }
        return 0.0f;
    }

    private final float getRelativeCY() {
        BaseSlider baseSlider = this.slider;
        if (baseSlider != null) {
            return baseSlider.getThumbCenterY();
        }
        return 0.0f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showRunnable$lambda$0(TipViewContainer this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.addTipViewIfNeed();
        this$0.updateLocationOnScreen(this$0.slider);
        updateLocation$default(this$0, 0.0f, 0.0f, 3, null);
        this$0.updateParams();
        this$0.executeTransition();
        this$0.setVisibility(0);
    }

    private final void updateLocation(float cx, float cy) {
        float width = (this.locationOnScreenX + cx) - (getWidth() / 2);
        if (this.isClippingEnabled) {
            width = MathUtils.clamp(width, 0.0f, this.windowWidth - getWidth());
        }
        setX(width);
        setY(((this.locationOnScreenY + cy) - getHeight()) + this.verticalOffset);
    }

    public static /* synthetic */ void updateLocation$default(TipViewContainer tipViewContainer, float f2, float f3, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            f2 = tipViewContainer.getRelativeCX();
        }
        if ((i2 & 2) != 0) {
            f3 = tipViewContainer.getRelativeCY();
        }
        tipViewContainer.updateLocation(f2, f3);
    }

    private final void updateLocationOnScreen(View view) {
        if (view != null) {
            int[] iArr = new int[2];
            Rect rect = new Rect();
            ViewGroup contentView = getContentView(view);
            if (contentView != null) {
                contentView.getGlobalVisibleRect(rect);
            }
            view.getLocationOnScreen(iArr);
            this.locationOnScreenX = iArr[0];
            this.locationOnScreenY = iArr[1] - rect.top;
        }
    }

    private final void updateParams() {
        if (this.verticalOffset == 0) {
            BaseSlider baseSlider = this.slider;
            this.verticalOffset = (-(baseSlider != null ? baseSlider.getThumbRadius() : 0)) + this.defaultSpace;
        }
    }

    public final void addTipViewIfNeed() {
        if (this.customTipView == null) {
            if (getChildCount() == 0) {
                addView(this.defaultTipView, new FrameLayout.LayoutParams(-2, -2));
            }
        } else if (getChildCount() == 0) {
            addView(this.customTipView, new FrameLayout.LayoutParams(-2, -2));
        }
    }

    public final void attachTipView(@NotNull BaseSlider view) {
        Intrinsics.checkNotNullParameter(view, "view");
        ViewGroup contentView = getContentView(view);
        this.slider = view;
        if (contentView != null) {
            if (((TipViewContainer) contentView.findViewById(this.tipViewId)) == null) {
                contentView.addView(this);
            }
            this.isAttached = true;
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "context");
            this.windowWidth = ContextExtensionsKt.getWindowWidth(context);
        }
    }

    public final void detachTipView(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        ViewGroup contentView = getContentView(view);
        if (contentView != null) {
            contentView.removeView(this);
        }
    }

    @Nullable
    public final TipViewAnimator getAnimator() {
        return this.animator;
    }

    @Nullable
    public final View getCustomTipView() {
        return this.customTipView;
    }

    @NotNull
    public final Runnable getShowRunnable() {
        return this.showRunnable;
    }

    public final int getVerticalOffset() {
        return this.verticalOffset;
    }

    public final int getWindowWidth() {
        return this.windowWidth;
    }

    public final void hide() {
        removeCallbacks(this.showRunnable);
        if (this.isAttached) {
            updateLocationOnScreen(this.slider);
            executeTransition();
            setVisibility(8);
        }
    }

    /* renamed from: isAttached, reason: from getter */
    public final boolean getIsAttached() {
        return this.isAttached;
    }

    /* renamed from: isClippingEnabled, reason: from getter */
    public final boolean getIsClippingEnabled() {
        return this.isClippingEnabled;
    }

    /* renamed from: isTipTextAutoChange, reason: from getter */
    public final boolean getIsTipTextAutoChange() {
        return this.isTipTextAutoChange;
    }

    public final void onLocationChanged(float cx, float cy, float value) {
        if (this.isAttached) {
            updateLocation(cx, cy);
            if (this.isTipTextAutoChange) {
                StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                String str = String.format("%.0f", Arrays.copyOf(new Object[]{Float.valueOf(value)}, 1));
                Intrinsics.checkNotNullExpressionValue(str, "format(format, *args)");
                setTipText(str);
            }
        }
    }

    @Override // android.view.View
    public void onSizeChanged(int w2, int h2, int oldw, int oldh) {
        super.onSizeChanged(w2, h2, oldw, oldh);
        updateLocation$default(this, 0.0f, 0.0f, 3, null);
    }

    public final void setAnimator(@Nullable TipViewAnimator tipViewAnimator) {
        this.animator = tipViewAnimator;
    }

    public final void setAttached(boolean z2) {
        this.isAttached = z2;
    }

    public final void setClippingEnabled(boolean z2) {
        this.isClippingEnabled = z2;
    }

    public final void setCustomTipView(@Nullable View view) {
        this.customTipView = view;
    }

    public final void setSize(int width, int height) {
        setLayoutParams(new FrameLayout.LayoutParams(width, height));
    }

    public final void setTipBackground(@ColorInt int color) {
        this.defaultTipView.setTipBackground(color);
    }

    public final void setTipText(@NotNull CharSequence text) {
        Intrinsics.checkNotNullParameter(text, "text");
        this.defaultTipView.setTipText(text);
    }

    public final void setTipTextAutoChange(boolean z2) {
        this.isTipTextAutoChange = z2;
    }

    public final void setTipTextColor(@ColorInt int color) {
        this.defaultTipView.setTipTextColor(color);
    }

    public final void setVerticalOffset(int i2) {
        this.verticalOffset = i2;
    }

    public final void setWindowWidth(int i2) {
        this.windowWidth = i2;
    }

    public final void show() {
        removeCallbacks(this.showRunnable);
        if (this.isAttached) {
            postDelayed(this.showRunnable, 200L);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public TipViewContainer(@NotNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        Intrinsics.checkNotNullParameter(context, "context");
        int i3 = com.ykb.ebook.R.id.nifty_slider_tip_view;
        this.tipViewId = i3;
        this.defaultSpace = ConvertExtensionsKt.dpToPx(-8);
        this.defaultTipView = new DefaultTipView(context, null, 0, 6, null);
        this.defaultTransition = new Fade();
        this.isTipTextAutoChange = true;
        this.showRunnable = new Runnable() { // from class: com.ykb.ebook.weight.slider.e
            @Override // java.lang.Runnable
            public final void run() {
                TipViewContainer.showRunnable$lambda$0(this.f26531c);
            }
        };
        setId(i3 + hashCode());
        setVisibility(4);
        setSize(-2, -2);
    }
}
