package com.psychiatrygarden.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import androidx.annotation.ColorInt;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt___RangesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 :2\u00020\u0001:\u0001:B#\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u000e\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u0007J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u001d\u001a\u00020\u0007H\u0002J\u0010\u0010 \u001a\u00020\u001f2\u0006\u0010\u001d\u001a\u00020\u0007H\u0002J\u000e\u0010!\u001a\u00020\u001c2\u0006\u0010\"\u001a\u00020#J\u000e\u0010$\u001a\u00020\u001c2\u0006\u0010%\u001a\u00020&J\u0010\u0010'\u001a\u00020\u000b2\u0006\u0010\u001d\u001a\u00020\u0007H\u0002J\u0010\u0010(\u001a\u00020\u000b2\u0006\u0010\u001d\u001a\u00020\u0007H\u0002J\u0010\u0010)\u001a\u00020\u000b2\u0006\u0010\u001d\u001a\u00020\u0007H\u0002J\u0010\u0010*\u001a\u00020\u000b2\u0006\u0010\u001d\u001a\u00020\u0007H\u0002J\b\u0010+\u001a\u00020\u001cH\u0002J\u0010\u0010,\u001a\u00020\u00072\u0006\u0010-\u001a\u00020\u0007H\u0002J\"\u0010.\u001a\u00020\u001c2\u0006\u0010/\u001a\u00020\u00072\b\b\u0001\u00100\u001a\u00020\u00072\b\b\u0001\u00101\u001a\u00020\u0007J\u0010\u00102\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u0007H\u0002J\u0010\u00103\u001a\u00020\u001f2\u0006\u0010\u001d\u001a\u00020\u0007H\u0002J\u0012\u00104\u001a\u00020\u000b2\b\u00105\u001a\u0004\u0018\u000106H\u0016J\u0010\u00107\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u0007H\u0002J\u0010\u00108\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u0007H\u0002J\u0010\u00109\u001a\u00020\u001f2\u0006\u0010\u001d\u001a\u00020\u0007H\u0002R\u001a\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u000e0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006;"}, d2 = {"Lcom/psychiatrygarden/widget/DotIndicatorScrollView;", "Landroid/widget/HorizontalScrollView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "attributeSet", "Landroid/util/AttributeSet;", "def", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "bigSizeMap", "Landroid/util/ArrayMap;", "", "dotMargin", "indicatorViewMap", "Landroid/view/View;", "isBindViewPager", "mCurrentPosition", "normalColorMap", "normalDotHeight", "normalDotWidth", "normalIndicatorBg", "selectColorMap", "selectIndicatorBg", "smallDotHeight", "smallDotWidth", "smallSizeMap", "totalIndicatorCount", "bannerPageChange", "", "position", "beBig", "Landroid/animation/Animator;", "beSmall", "bindViewPager", "viewPager", "Landroidx/viewpager/widget/ViewPager;", "bindViewPager2", "viewPager2", "Landroidx/viewpager2/widget/ViewPager2;", "checkIsBigSize", "checkIsNormalColor", "checkIsSelectColor", "checkIsSmallSize", "createIndicatorDots", "dp2Px", "value", "initIndicator", "total", "selectBg", "normalBg", "leftScroll", "normalColor", "onTouchEvent", "ev", "Landroid/view/MotionEvent;", "rightScroll", "scrollChange", "selectColor", "Companion", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nDotIndicatorScrollView.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DotIndicatorScrollView.kt\ncom/psychiatrygarden/widget/DotIndicatorScrollView\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,468:1\n1#2:469\n1855#3,2:470\n1855#3,2:472\n766#3:474\n857#3,2:475\n1855#3,2:477\n*S KotlinDebug\n*F\n+ 1 DotIndicatorScrollView.kt\ncom/psychiatrygarden/widget/DotIndicatorScrollView\n*L\n92#1:470,2\n208#1:472,2\n296#1:474\n296#1:475,2\n298#1:477,2\n*E\n"})
/* loaded from: classes6.dex */
public final class DotIndicatorScrollView extends HorizontalScrollView {
    public static final int MIN_SCROLL_COUNT = 5;

    @NotNull
    private final ArrayMap<Integer, Boolean> bigSizeMap;
    private int dotMargin;

    @NotNull
    private final ArrayMap<Integer, View> indicatorViewMap;
    private boolean isBindViewPager;
    private int mCurrentPosition;

    @NotNull
    private final ArrayMap<Integer, Boolean> normalColorMap;
    private int normalDotHeight;
    private int normalDotWidth;
    private int normalIndicatorBg;

    @NotNull
    private final ArrayMap<Integer, Boolean> selectColorMap;
    private int selectIndicatorBg;
    private int smallDotHeight;
    private int smallDotWidth;

    @NotNull
    private final ArrayMap<Integer, Boolean> smallSizeMap;
    private int totalIndicatorCount;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public DotIndicatorScrollView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public DotIndicatorScrollView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        Intrinsics.checkNotNullParameter(context, "context");
        this.normalDotWidth = dp2Px(6);
        this.normalDotHeight = dp2Px(6);
        this.smallDotWidth = dp2Px(4);
        this.smallDotHeight = dp2Px(4);
        this.dotMargin = dp2Px(5);
        this.selectIndicatorBg = Color.parseColor("#F95843");
        this.normalIndicatorBg = Color.parseColor("#EEEEEE");
        this.indicatorViewMap = new ArrayMap<>();
        this.normalColorMap = new ArrayMap<>();
        this.selectColorMap = new ArrayMap<>();
        this.smallSizeMap = new ArrayMap<>();
        this.bigSizeMap = new ArrayMap<>();
    }

    private final Animator beBig(int position) {
        View view = this.indicatorViewMap.get(Integer.valueOf(position));
        Intrinsics.checkNotNull(view);
        final View view2 = view;
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(this.smallDotWidth, this.normalDotWidth);
        valueAnimatorOfInt.setDuration(200L);
        valueAnimatorOfInt.setInterpolator(new LinearInterpolator());
        ArrayMap<Integer, Boolean> arrayMap = this.smallSizeMap;
        Object tag = view2.getTag();
        Intrinsics.checkNotNull(tag, "null cannot be cast to non-null type kotlin.Int");
        arrayMap.put((Integer) tag, Boolean.FALSE);
        ArrayMap<Integer, Boolean> arrayMap2 = this.bigSizeMap;
        Object tag2 = view2.getTag();
        Intrinsics.checkNotNull(tag2, "null cannot be cast to non-null type kotlin.Int");
        arrayMap2.put((Integer) tag2, Boolean.TRUE);
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.widget.q8
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                DotIndicatorScrollView.beBig$lambda$18$lambda$17(view2, valueAnimator);
            }
        });
        Intrinsics.checkNotNullExpressionValue(valueAnimatorOfInt, "ofInt(smallDotWidth, nor…\n            }\n\n        }");
        return valueAnimatorOfInt;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void beBig$lambda$18$lambda$17(View view, ValueAnimator it) {
        Intrinsics.checkNotNullParameter(view, "$view");
        Intrinsics.checkNotNullParameter(it, "it");
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.view.ViewGroup.LayoutParams");
        Object animatedValue = it.getAnimatedValue();
        Intrinsics.checkNotNull(animatedValue, "null cannot be cast to non-null type kotlin.Int");
        layoutParams.width = ((Integer) animatedValue).intValue();
        Object animatedValue2 = it.getAnimatedValue();
        Intrinsics.checkNotNull(animatedValue2, "null cannot be cast to non-null type kotlin.Int");
        layoutParams.height = ((Integer) animatedValue2).intValue();
        view.setLayoutParams(layoutParams);
    }

    private final Animator beSmall(int position) {
        View view = this.indicatorViewMap.get(Integer.valueOf(position));
        Intrinsics.checkNotNull(view);
        final View view2 = view;
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(this.normalDotWidth, this.smallDotWidth);
        valueAnimatorOfInt.setDuration(200L);
        valueAnimatorOfInt.setInterpolator(new LinearInterpolator());
        ArrayMap<Integer, Boolean> arrayMap = this.smallSizeMap;
        Object tag = view2.getTag();
        Intrinsics.checkNotNull(tag, "null cannot be cast to non-null type kotlin.Int");
        Boolean bool = Boolean.TRUE;
        arrayMap.put((Integer) tag, bool);
        ArrayMap<Integer, Boolean> arrayMap2 = this.bigSizeMap;
        Object tag2 = view2.getTag();
        Intrinsics.checkNotNull(tag2, "null cannot be cast to non-null type kotlin.Int");
        Boolean bool2 = Boolean.FALSE;
        arrayMap2.put((Integer) tag2, bool2);
        ArrayMap<Integer, Boolean> arrayMap3 = this.normalColorMap;
        Object tag3 = view2.getTag();
        Intrinsics.checkNotNull(tag3, "null cannot be cast to non-null type kotlin.Int");
        arrayMap3.put((Integer) tag3, bool);
        ArrayMap<Integer, Boolean> arrayMap4 = this.selectColorMap;
        Object tag4 = view2.getTag();
        Intrinsics.checkNotNull(tag4, "null cannot be cast to non-null type kotlin.Int");
        arrayMap4.put((Integer) tag4, bool2);
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.widget.r8
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                DotIndicatorScrollView.beSmall$lambda$16$lambda$15(view2, valueAnimator);
            }
        });
        Intrinsics.checkNotNullExpressionValue(valueAnimatorOfInt, "ofInt(normalDotWidth, sm…\n            }\n\n        }");
        return valueAnimatorOfInt;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void beSmall$lambda$16$lambda$15(View view, ValueAnimator it) {
        Intrinsics.checkNotNullParameter(view, "$view");
        Intrinsics.checkNotNullParameter(it, "it");
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.view.ViewGroup.LayoutParams");
        Object animatedValue = it.getAnimatedValue();
        Intrinsics.checkNotNull(animatedValue, "null cannot be cast to non-null type kotlin.Int");
        layoutParams.width = ((Integer) animatedValue).intValue();
        Object animatedValue2 = it.getAnimatedValue();
        Intrinsics.checkNotNull(animatedValue2, "null cannot be cast to non-null type kotlin.Int");
        layoutParams.height = ((Integer) animatedValue2).intValue();
        view.setLayoutParams(layoutParams);
    }

    private final boolean checkIsBigSize(int position) {
        View view = this.indicatorViewMap.get(Integer.valueOf(position));
        Intrinsics.checkNotNull(view);
        View view2 = view;
        return view2.getWidth() >= this.normalDotWidth && view2.getHeight() >= this.normalDotHeight;
    }

    private final boolean checkIsNormalColor(int position) {
        View view = this.indicatorViewMap.get(Integer.valueOf(position));
        Intrinsics.checkNotNull(view);
        ArrayMap<Integer, Boolean> arrayMap = this.normalColorMap;
        Object tag = view.getTag();
        Intrinsics.checkNotNull(tag, "null cannot be cast to non-null type kotlin.Int");
        return Intrinsics.areEqual(arrayMap.get((Integer) tag), Boolean.TRUE);
    }

    private final boolean checkIsSelectColor(int position) {
        View view = this.indicatorViewMap.get(Integer.valueOf(position));
        Intrinsics.checkNotNull(view);
        ArrayMap<Integer, Boolean> arrayMap = this.selectColorMap;
        Object tag = view.getTag();
        Intrinsics.checkNotNull(tag, "null cannot be cast to non-null type kotlin.Int");
        return Intrinsics.areEqual(arrayMap.get((Integer) tag), Boolean.TRUE);
    }

    private final boolean checkIsSmallSize(int position) {
        View view = this.indicatorViewMap.get(Integer.valueOf(position));
        Intrinsics.checkNotNull(view);
        View view2 = view;
        return view2.getWidth() < this.normalDotWidth && view2.getHeight() < this.normalDotHeight;
    }

    private final void createIndicatorDots() {
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(0);
        Iterator<Integer> it = RangesKt___RangesKt.until(0, this.totalIndicatorCount).iterator();
        while (it.hasNext()) {
            int iNextInt = ((IntIterator) it).nextInt();
            LinearLayout linearLayout2 = new LinearLayout(getContext());
            linearLayout2.setOrientation(1);
            View view = new View(getContext());
            if (iNextInt == 0) {
                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setShape(1);
                gradientDrawable.setColor(ColorStateList.valueOf(this.selectIndicatorBg));
                view.setBackground(gradientDrawable);
            } else {
                GradientDrawable gradientDrawable2 = new GradientDrawable();
                gradientDrawable2.setShape(1);
                gradientDrawable2.setColor(ColorStateList.valueOf(this.normalIndicatorBg));
                view.setBackground(gradientDrawable2);
            }
            view.setTag(Integer.valueOf(iNextInt));
            this.indicatorViewMap.put(Integer.valueOf(iNextInt), view);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((iNextInt != 4 || this.totalIndicatorCount <= 5) ? this.normalDotWidth : this.smallDotWidth, (iNextInt != 4 || this.totalIndicatorCount <= 5) ? this.normalDotHeight : this.smallDotHeight);
            layoutParams.gravity = 17;
            linearLayout2.addView(view, layoutParams);
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(this.normalDotWidth, -2);
            layoutParams2.rightMargin = this.dotMargin;
            layoutParams2.gravity = 17;
            linearLayout.addView(linearLayout2, layoutParams2);
        }
        addView(linearLayout);
        ViewGroup.LayoutParams layoutParams3 = getLayoutParams();
        int i2 = this.totalIndicatorCount;
        int i3 = i2 > 5 ? 5 : i2;
        int i4 = this.normalDotWidth;
        int i5 = this.dotMargin;
        int i6 = i3 * (i4 + i5);
        layoutParams3.width = i6;
        if (i2 <= 5) {
            layoutParams3.width = i6 - i5;
        }
        setLayoutParams(layoutParams3);
        scrollTo(0, 0);
    }

    private final int dp2Px(int value) {
        return (int) TypedValue.applyDimension(1, value * 1.0f, getResources().getDisplayMetrics());
    }

    private final void leftScroll(int position) {
        View view = this.indicatorViewMap.get(Integer.valueOf(position));
        int i2 = position - 1;
        View view2 = this.indicatorViewMap.get(Integer.valueOf(i2));
        ArrayList arrayList = new ArrayList();
        if (view == null || view2 == null) {
            return;
        }
        if (position < 4) {
            if (!checkIsNormalColor(i2)) {
                arrayList.add(normalColor(i2));
            }
            if (!checkIsBigSize(i2)) {
                arrayList.add(beBig(i2));
            }
            if (!checkIsSelectColor(position)) {
                arrayList.add(selectColor(position));
            }
            if (!checkIsBigSize(position)) {
                arrayList.add(beBig(position));
            }
        } else {
            if (checkIsBigSize(this.mCurrentPosition + 1)) {
                normalColor(this.mCurrentPosition).start();
                selectColor(position).start();
                return;
            }
            if (position < this.totalIndicatorCount - 1) {
                smoothScrollBy(this.normalDotWidth + this.dotMargin, 0);
            }
            if (!checkIsBigSize(position)) {
                arrayList.add(beBig(position));
            }
            if (!checkIsSelectColor(position)) {
                arrayList.add(selectColor(position));
            }
            ArrayList arrayListArrayListOf = CollectionsKt__CollectionsKt.arrayListOf(Integer.valueOf(i2), Integer.valueOf(position - 2));
            boolean z2 = position == this.totalIndicatorCount - 1;
            if (z2) {
                arrayListArrayListOf.add(0, Integer.valueOf(position - 3));
            }
            Iterator it = arrayListArrayListOf.iterator();
            while (it.hasNext()) {
                int iIntValue = ((Number) it.next()).intValue();
                if (!checkIsBigSize(iIntValue)) {
                    arrayList.add(beBig(iIntValue));
                }
                if (!checkIsNormalColor(iIntValue)) {
                    arrayList.add(normalColor(iIntValue));
                }
            }
            if (z2) {
                int i3 = position - 4;
                if (!checkIsSmallSize(i3)) {
                    arrayList.add(beSmall(i3));
                }
                if (!checkIsNormalColor(i3)) {
                    arrayList.add(normalColor(i3));
                }
            } else {
                int i4 = position - 3;
                if (!checkIsSmallSize(i4)) {
                    arrayList.add(beSmall(i4));
                }
                if (!checkIsNormalColor(i4)) {
                    arrayList.add(normalColor(i4));
                }
            }
            int i5 = position + 1;
            int i6 = this.totalIndicatorCount;
            if (i5 == i6 - 1) {
                if (!checkIsBigSize(i5)) {
                    arrayList.add(beBig(i5));
                }
                if (!checkIsNormalColor(i5)) {
                    arrayList.add(normalColor(i5));
                }
            } else if (position < i6 - 1) {
                if (!checkIsSmallSize(i5)) {
                    arrayList.add(beSmall(i5));
                }
                if (!checkIsNormalColor(i5)) {
                    arrayList.add(normalColor(i5));
                }
            }
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(200L);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.playTogether(arrayList);
        animatorSet.start();
    }

    private final Animator normalColor(int position) {
        View view = this.indicatorViewMap.get(Integer.valueOf(position));
        Intrinsics.checkNotNull(view);
        final View view2 = view;
        final GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(1);
        ValueAnimator valueAnimatorOfObject = ValueAnimator.ofObject(new ArgbEvaluator(), Integer.valueOf(this.selectIndicatorBg), Integer.valueOf(this.normalIndicatorBg));
        valueAnimatorOfObject.setDuration(200L);
        valueAnimatorOfObject.setInterpolator(new LinearInterpolator());
        ArrayMap<Integer, Boolean> arrayMap = this.selectColorMap;
        Object tag = view2.getTag();
        Intrinsics.checkNotNull(tag, "null cannot be cast to non-null type kotlin.Int");
        arrayMap.put((Integer) tag, Boolean.FALSE);
        ArrayMap<Integer, Boolean> arrayMap2 = this.normalColorMap;
        Object tag2 = view2.getTag();
        Intrinsics.checkNotNull(tag2, "null cannot be cast to non-null type kotlin.Int");
        arrayMap2.put((Integer) tag2, Boolean.TRUE);
        valueAnimatorOfObject.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.widget.o8
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                DotIndicatorScrollView.normalColor$lambda$11$lambda$10(gradientDrawable, view2, valueAnimator);
            }
        });
        Intrinsics.checkNotNullExpressionValue(valueAnimatorOfObject, "ofObject(ArgbEvaluator()…g\n            }\n        }");
        return valueAnimatorOfObject;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void normalColor$lambda$11$lambda$10(GradientDrawable bg, View view, ValueAnimator it) {
        Intrinsics.checkNotNullParameter(bg, "$bg");
        Intrinsics.checkNotNullParameter(view, "$view");
        Intrinsics.checkNotNullParameter(it, "it");
        Object animatedValue = it.getAnimatedValue();
        Intrinsics.checkNotNull(animatedValue, "null cannot be cast to non-null type kotlin.Int");
        bg.setColor(ColorStateList.valueOf(((Integer) animatedValue).intValue()));
        view.setBackground(bg);
    }

    private final void rightScroll(int position) {
        ArrayList arrayList = new ArrayList();
        if (position <= 5) {
            if (position >= 1) {
                if (checkIsBigSize(this.mCurrentPosition - 1)) {
                    if (!checkIsNormalColor(this.mCurrentPosition)) {
                        normalColor(this.mCurrentPosition).start();
                    }
                    if (checkIsSelectColor(position)) {
                        return;
                    }
                    selectColor(position).start();
                    return;
                }
                if (!checkIsSmallSize(this.mCurrentPosition - 1)) {
                    return;
                }
                smoothScrollBy(-(this.normalDotWidth + this.dotMargin), 0);
                if (position > 1) {
                    arrayList.add(beSmall(position - 1));
                }
            }
            if (!checkIsBigSize(position)) {
                arrayList.add(beBig(position));
            }
            if (!checkIsSelectColor(position)) {
                arrayList.add(selectColor(position));
            }
            ArrayList arrayListArrayListOf = CollectionsKt__CollectionsKt.arrayListOf(Integer.valueOf(position + 1), Integer.valueOf(position + 2));
            if (position < 4) {
                List list = CollectionsKt___CollectionsKt.toList(RangesKt___RangesKt.until(0, 4));
                ArrayList arrayList2 = new ArrayList();
                for (Object obj : list) {
                    if (((Number) obj).intValue() != position + (-1)) {
                        arrayList2.add(obj);
                    }
                }
                arrayListArrayListOf.addAll(arrayList2);
            }
            Iterator it = arrayListArrayListOf.iterator();
            while (it.hasNext()) {
                int iIntValue = ((Number) it.next()).intValue();
                if (iIntValue != position) {
                    if (!checkIsBigSize(iIntValue)) {
                        arrayList.add(beBig(iIntValue));
                    }
                    if (!checkIsNormalColor(iIntValue)) {
                        arrayList.add(normalColor(iIntValue));
                    }
                }
            }
            if (position != 0) {
                int i2 = position + 3;
                if (!checkIsSmallSize(i2)) {
                    arrayList.add(beSmall(i2));
                }
                if (!checkIsNormalColor(i2)) {
                    arrayList.add(normalColor(i2));
                }
            }
        } else {
            int i3 = position + 1;
            if (!checkIsNormalColor(i3)) {
                arrayList.add(normalColor(i3));
            }
            if (!checkIsSelectColor(position)) {
                arrayList.add(selectColor(position));
            }
            if (!checkIsBigSize(position)) {
                arrayList.add(beBig(position));
            }
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.playTogether(arrayList);
        animatorSet.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void scrollChange(int position) {
        if (this.totalIndicatorCount <= 5) {
            selectColor(position).start();
            normalColor(this.mCurrentPosition).start();
        } else if (position > this.mCurrentPosition) {
            leftScroll(position);
        } else {
            rightScroll(position);
        }
        this.mCurrentPosition = position;
    }

    private final Animator selectColor(int position) {
        View view = this.indicatorViewMap.get(Integer.valueOf(position));
        Intrinsics.checkNotNull(view);
        final View view2 = view;
        final GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(1);
        ValueAnimator valueAnimatorOfObject = ValueAnimator.ofObject(new ArgbEvaluator(), Integer.valueOf(this.normalIndicatorBg), Integer.valueOf(this.selectIndicatorBg));
        valueAnimatorOfObject.setDuration(200L);
        valueAnimatorOfObject.setInterpolator(new LinearInterpolator());
        ArrayMap<Integer, Boolean> arrayMap = this.selectColorMap;
        Object tag = view2.getTag();
        Intrinsics.checkNotNull(tag, "null cannot be cast to non-null type kotlin.Int");
        Boolean bool = Boolean.TRUE;
        arrayMap.put((Integer) tag, bool);
        ArrayMap<Integer, Boolean> arrayMap2 = this.normalColorMap;
        Object tag2 = view2.getTag();
        Intrinsics.checkNotNull(tag2, "null cannot be cast to non-null type kotlin.Int");
        arrayMap2.put((Integer) tag2, Boolean.FALSE);
        ArrayMap<Integer, Boolean> arrayMap3 = this.bigSizeMap;
        Object tag3 = view2.getTag();
        Intrinsics.checkNotNull(tag3, "null cannot be cast to non-null type kotlin.Int");
        arrayMap3.put((Integer) tag3, bool);
        valueAnimatorOfObject.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.widget.p8
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                DotIndicatorScrollView.selectColor$lambda$14$lambda$13(gradientDrawable, view2, valueAnimator);
            }
        });
        Intrinsics.checkNotNullExpressionValue(valueAnimatorOfObject, "ofObject(ArgbEvaluator()…g\n            }\n        }");
        return valueAnimatorOfObject;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void selectColor$lambda$14$lambda$13(GradientDrawable bg, View view, ValueAnimator it) {
        Intrinsics.checkNotNullParameter(bg, "$bg");
        Intrinsics.checkNotNullParameter(view, "$view");
        Intrinsics.checkNotNullParameter(it, "it");
        Object animatedValue = it.getAnimatedValue();
        Intrinsics.checkNotNull(animatedValue, "null cannot be cast to non-null type kotlin.Int");
        bg.setColor(ColorStateList.valueOf(((Integer) animatedValue).intValue()));
        view.setBackground(bg);
    }

    public final void bannerPageChange(int position) {
        scrollChange(position);
    }

    public final void bindViewPager(@NotNull ViewPager viewPager) {
        Intrinsics.checkNotNullParameter(viewPager, "viewPager");
        if (this.isBindViewPager) {
            return;
        }
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.widget.DotIndicatorScrollView.bindViewPager.1
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int state) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int position) {
                DotIndicatorScrollView.this.scrollChange(position);
            }
        });
        this.isBindViewPager = true;
    }

    public final void bindViewPager2(@NotNull ViewPager2 viewPager2) {
        Intrinsics.checkNotNullParameter(viewPager2, "viewPager2");
        if (this.isBindViewPager) {
            return;
        }
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() { // from class: com.psychiatrygarden.widget.DotIndicatorScrollView.bindViewPager2.1
            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageSelected(int position) {
                DotIndicatorScrollView.this.scrollChange(position);
            }
        });
        this.isBindViewPager = true;
    }

    public final void initIndicator(int total, @ColorInt int selectBg, @ColorInt int normalBg) {
        this.totalIndicatorCount = total;
        this.selectIndicatorBg = selectBg;
        this.normalIndicatorBg = normalBg;
        if (total > 0) {
            if (getChildCount() > 0) {
                this.indicatorViewMap.clear();
                this.normalColorMap.clear();
                this.selectColorMap.clear();
                this.smallSizeMap.clear();
                this.bigSizeMap.clear();
                removeAllViews();
                this.mCurrentPosition = 0;
            }
            createIndicatorDots();
        }
    }

    @Override // android.widget.HorizontalScrollView, android.view.View
    public boolean onTouchEvent(@Nullable MotionEvent ev) {
        return false;
    }

    public /* synthetic */ DotIndicatorScrollView(Context context, AttributeSet attributeSet, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, attributeSet, (i3 & 4) != 0 ? 0 : i2);
    }
}
