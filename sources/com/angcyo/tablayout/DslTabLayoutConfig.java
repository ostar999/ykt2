package com.angcyo.tablayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.IdRes;
import com.angcyo.tablayout.DslTabLayout;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u001b\n\u0002\u0018\u0002\n\u0002\b'\n\u0002\u0010\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0016\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J*\u0010h\u001a\u00020i2\b\u0010j\u001a\u0004\u0018\u00010\u00142\u0006\u0010k\u001a\u00020\u00072\u0006\u0010l\u001a\u00020\u00072\u0006\u0010m\u001a\u00020\fH\u0016J*\u0010n\u001a\u00020i2\b\u0010j\u001a\u0004\u0018\u00010\u00142\u0006\u0010k\u001a\u00020\u00072\u0006\u0010l\u001a\u00020\u00072\u0006\u0010m\u001a\u00020\fH\u0016J*\u0010o\u001a\u00020i2\b\u0010j\u001a\u0004\u0018\u00010\u00142\u0006\u0010p\u001a\u00020\f2\u0006\u0010q\u001a\u00020\f2\u0006\u0010m\u001a\u00020\fH\u0016J*\u0010r\u001a\u00020i2\b\u0010j\u001a\u0004\u0018\u00010\u001c2\u0006\u0010s\u001a\u00020\f2\u0006\u0010t\u001a\u00020\f2\u0006\u0010m\u001a\u00020\fH\u0016J\u001a\u0010u\u001a\u00020i2\b\u0010j\u001a\u0004\u0018\u00010\u00142\u0006\u0010v\u001a\u00020\u0007H\u0016J\u001c\u0010w\u001a\u00020i2\u0006\u0010x\u001a\u00020y2\n\b\u0002\u0010z\u001a\u0004\u0018\u00010{H\u0016J \u0010|\u001a\u00020i2\u0006\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\fH\u0016J\"\u0010}\u001a\u00020i2\b\u0010~\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u007f\u001a\u00020\u00142\u0006\u0010\r\u001a\u00020\fH\u0016J\"\u0010\u0080\u0001\u001a\u00020i2\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u00072\u0007\u0010\u0081\u0001\u001a\u00020%H\u0016R_\u0010\u0005\u001aG\u0012\u0013\u0012\u00110\u0007¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u00110\u0007¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u00110\f¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\r\u0012\u0004\u0012\u00020\u00070\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011RL\u0010\u0012\u001a4\u0012\u0013\u0012\u00110\u0014¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0007¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u00140\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aRL\u0010\u001b\u001a4\u0012\u0013\u0012\u00110\u0014¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0007¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u001c0\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u0018\"\u0004\b\u001e\u0010\u001aR\u001a\u0010\u001f\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R$\u0010&\u001a\u00020%2\u0006\u0010$\u001a\u00020%@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010(\"\u0004\b)\u0010*R\u001a\u0010+\u001a\u00020%X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010(\"\u0004\b-\u0010*R\u001a\u0010.\u001a\u00020%X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b/\u0010(\"\u0004\b0\u0010*R\u001a\u00101\u001a\u00020%X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b2\u0010(\"\u0004\b3\u0010*R\u001a\u00104\u001a\u00020%X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b5\u0010(\"\u0004\b6\u0010*R\u001a\u00107\u001a\u00020%X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b8\u0010(\"\u0004\b9\u0010*R\u001a\u0010:\u001a\u00020%X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b;\u0010(\"\u0004\b<\u0010*R$\u0010=\u001a\u00020%2\u0006\u0010$\u001a\u00020%@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b>\u0010(\"\u0004\b?\u0010*R\u001a\u0010@\u001a\u00020AX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bB\u0010C\"\u0004\bD\u0010ER\u001c\u0010F\u001a\u00020\u00078FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bG\u0010!\"\u0004\bH\u0010#R\u001c\u0010I\u001a\u00020\u00078FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bJ\u0010!\"\u0004\bK\u0010#R\u001e\u0010L\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bM\u0010!\"\u0004\bN\u0010#R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\bO\u0010PR\u001a\u0010Q\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bR\u0010S\"\u0004\bT\u0010UR\u001a\u0010V\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bW\u0010S\"\u0004\bX\u0010UR\u001a\u0010Y\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bZ\u0010!\"\u0004\b[\u0010#R\u001a\u0010\\\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b]\u0010S\"\u0004\b^\u0010UR\u001a\u0010_\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b`\u0010S\"\u0004\ba\u0010UR\u001e\u0010b\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bc\u0010!\"\u0004\bd\u0010#R\u001a\u0010e\u001a\u00020%X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bf\u0010(\"\u0004\bg\u0010*¨\u0006\u0082\u0001"}, d2 = {"Lcom/angcyo/tablayout/DslTabLayoutConfig;", "Lcom/angcyo/tablayout/DslSelectorConfig;", "tabLayout", "Lcom/angcyo/tablayout/DslTabLayout;", "(Lcom/angcyo/tablayout/DslTabLayout;)V", "onGetGradientIndicatorColor", "Lkotlin/Function3;", "", "Lkotlin/ParameterName;", "name", "fromIndex", "toIndex", "", "positionOffset", "getOnGetGradientIndicatorColor", "()Lkotlin/jvm/functions/Function3;", "setOnGetGradientIndicatorColor", "(Lkotlin/jvm/functions/Function3;)V", "onGetIcoStyleView", "Lkotlin/Function2;", "Landroid/view/View;", "itemView", "index", "getOnGetIcoStyleView", "()Lkotlin/jvm/functions/Function2;", "setOnGetIcoStyleView", "(Lkotlin/jvm/functions/Function2;)V", "onGetTextStyleView", "Landroid/widget/TextView;", "getOnGetTextStyleView", "setOnGetTextStyleView", "tabDeselectColor", "getTabDeselectColor", "()I", "setTabDeselectColor", "(I)V", "value", "", "tabEnableGradientColor", "getTabEnableGradientColor", "()Z", "setTabEnableGradientColor", "(Z)V", "tabEnableGradientScale", "getTabEnableGradientScale", "setTabEnableGradientScale", "tabEnableGradientTextSize", "getTabEnableGradientTextSize", "setTabEnableGradientTextSize", "tabEnableIcoColor", "getTabEnableIcoColor", "setTabEnableIcoColor", "tabEnableIcoGradientColor", "getTabEnableIcoGradientColor", "setTabEnableIcoGradientColor", "tabEnableIndicatorGradientColor", "getTabEnableIndicatorGradientColor", "setTabEnableIndicatorGradientColor", "tabEnableTextBold", "getTabEnableTextBold", "setTabEnableTextBold", "tabEnableTextColor", "getTabEnableTextColor", "setTabEnableTextColor", "tabGradientCallback", "Lcom/angcyo/tablayout/TabGradientCallback;", "getTabGradientCallback", "()Lcom/angcyo/tablayout/TabGradientCallback;", "setTabGradientCallback", "(Lcom/angcyo/tablayout/TabGradientCallback;)V", "tabIcoDeselectColor", "getTabIcoDeselectColor", "setTabIcoDeselectColor", "tabIcoSelectColor", "getTabIcoSelectColor", "setTabIcoSelectColor", "tabIconViewId", "getTabIconViewId", "setTabIconViewId", "getTabLayout", "()Lcom/angcyo/tablayout/DslTabLayout;", "tabMaxScale", "getTabMaxScale", "()F", "setTabMaxScale", "(F)V", "tabMinScale", "getTabMinScale", "setTabMinScale", "tabSelectColor", "getTabSelectColor", "setTabSelectColor", "tabTextMaxSize", "getTabTextMaxSize", "setTabTextMaxSize", "tabTextMinSize", "getTabTextMinSize", "setTabTextMinSize", "tabTextViewId", "getTabTextViewId", "setTabTextViewId", "tabUseTypefaceBold", "getTabUseTypefaceBold", "setTabUseTypefaceBold", "_gradientColor", "", "view", "startColor", "endColor", "percent", "_gradientIcoColor", "_gradientScale", "startScale", "endScale", "_gradientTextSize", "startTextSize", "endTextSize", "_updateIcoColor", "color", "initAttribute", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "attributeSet", "Landroid/util/AttributeSet;", "onPageIndexScrolled", "onPageViewScrolled", "fromView", "toView", "onUpdateItemStyle", "select", "TabLayout_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes2.dex */
public class DslTabLayoutConfig extends DslSelectorConfig {

    @NotNull
    private Function3<? super Integer, ? super Integer, ? super Float, Integer> onGetGradientIndicatorColor;

    @NotNull
    private Function2<? super View, ? super Integer, ? extends View> onGetIcoStyleView;

    @NotNull
    private Function2<? super View, ? super Integer, ? extends TextView> onGetTextStyleView;
    private int tabDeselectColor;
    private boolean tabEnableGradientColor;
    private boolean tabEnableGradientScale;
    private boolean tabEnableGradientTextSize;
    private boolean tabEnableIcoColor;
    private boolean tabEnableIcoGradientColor;
    private boolean tabEnableIndicatorGradientColor;
    private boolean tabEnableTextBold;
    private boolean tabEnableTextColor;

    @NotNull
    private TabGradientCallback tabGradientCallback;
    private int tabIcoDeselectColor;
    private int tabIcoSelectColor;

    @IdRes
    private int tabIconViewId;

    @NotNull
    private final DslTabLayout tabLayout;
    private float tabMaxScale;
    private float tabMinScale;
    private int tabSelectColor;
    private float tabTextMaxSize;
    private float tabTextMinSize;

    @IdRes
    private int tabTextViewId;
    private boolean tabUseTypefaceBold;

    public DslTabLayoutConfig(@NotNull DslTabLayout tabLayout) {
        Intrinsics.checkNotNullParameter(tabLayout, "tabLayout");
        this.tabLayout = tabLayout;
        this.tabEnableTextColor = true;
        this.tabSelectColor = -1;
        this.tabDeselectColor = Color.parseColor("#999999");
        this.tabEnableIcoColor = true;
        this.tabIcoSelectColor = -2;
        this.tabIcoDeselectColor = -2;
        this.tabMinScale = 0.8f;
        this.tabMaxScale = 1.2f;
        this.tabEnableGradientTextSize = true;
        this.tabTextMinSize = -1.0f;
        this.tabTextMaxSize = -1.0f;
        this.tabGradientCallback = new TabGradientCallback();
        this.tabTextViewId = -1;
        this.tabIconViewId = -1;
        this.onGetTextStyleView = new Function2<View, Integer, TextView>() { // from class: com.angcyo.tablayout.DslTabLayoutConfig$onGetTextStyleView$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ TextView invoke(View view, Integer num) {
                return invoke(view, num.intValue());
            }

            @Nullable
            public final TextView invoke(@NotNull View itemView, int i2) {
                KeyEvent.Callback callbackFindViewById;
                KeyEvent.Callback childOrNull;
                KeyEvent.Callback callbackFindViewById2;
                KeyEvent.Callback childOrNull2;
                KeyEvent.Callback callbackFindViewById3;
                KeyEvent.Callback childOrNull3;
                Intrinsics.checkNotNullParameter(itemView, "itemView");
                if (this.this$0.getTabTextViewId() != -1) {
                    return (TextView) itemView.findViewById(this.this$0.getTabTextViewId());
                }
                KeyEvent.Callback callback = itemView instanceof TextView ? (TextView) itemView : null;
                if (this.this$0.getTabLayout().getTabIndicator().getIndicatorContentIndex() != -1 && (childOrNull3 = LibExKt.getChildOrNull(itemView, this.this$0.getTabLayout().getTabIndicator().getIndicatorContentIndex())) != null && (childOrNull3 instanceof TextView)) {
                    callback = childOrNull3;
                }
                if (this.this$0.getTabLayout().getTabIndicator().getIndicatorContentId() != -1 && (callbackFindViewById3 = itemView.findViewById(this.this$0.getTabLayout().getTabIndicator().getIndicatorContentId())) != null && (callbackFindViewById3 instanceof TextView)) {
                    callback = callbackFindViewById3;
                }
                ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
                if (layoutParams instanceof DslTabLayout.LayoutParams) {
                    DslTabLayout.LayoutParams layoutParams2 = (DslTabLayout.LayoutParams) layoutParams;
                    if (layoutParams2.getIndicatorContentIndex() != -1 && (itemView instanceof ViewGroup) && (childOrNull2 = LibExKt.getChildOrNull(itemView, layoutParams2.getIndicatorContentIndex())) != null && (childOrNull2 instanceof TextView)) {
                        callback = childOrNull2;
                    }
                    if (layoutParams2.getIndicatorContentId() != -1 && (callbackFindViewById2 = itemView.findViewById(layoutParams2.getIndicatorContentId())) != null && (callbackFindViewById2 instanceof TextView)) {
                        callback = callbackFindViewById2;
                    }
                    if (layoutParams2.getContentTextViewIndex() != -1 && (itemView instanceof ViewGroup) && (childOrNull = LibExKt.getChildOrNull(itemView, layoutParams2.getContentTextViewIndex())) != null && (childOrNull instanceof TextView)) {
                        callback = childOrNull;
                    }
                    if (layoutParams2.getContentTextViewId() != -1 && (callbackFindViewById = itemView.findViewById(layoutParams2.getContentTextViewId())) != null && (callbackFindViewById instanceof TextView)) {
                        callback = callbackFindViewById;
                    }
                }
                return (TextView) callback;
            }
        };
        this.onGetIcoStyleView = new Function2<View, Integer, View>() { // from class: com.angcyo.tablayout.DslTabLayoutConfig$onGetIcoStyleView$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ View invoke(View view, Integer num) {
                return invoke(view, num.intValue());
            }

            @Nullable
            public final View invoke(@NotNull View itemView, int i2) {
                View childOrNull;
                View viewFindViewById;
                View viewFindViewById2;
                View viewFindViewById3;
                Intrinsics.checkNotNullParameter(itemView, "itemView");
                if (this.this$0.getTabIconViewId() != -1) {
                    return itemView.findViewById(this.this$0.getTabIconViewId());
                }
                if (this.this$0.getTabLayout().getTabIndicator().getIndicatorContentIndex() == -1 || (childOrNull = LibExKt.getChildOrNull(itemView, this.this$0.getTabLayout().getTabIndicator().getIndicatorContentIndex())) == null) {
                    childOrNull = itemView;
                }
                if (this.this$0.getTabLayout().getTabIndicator().getIndicatorContentId() != -1 && (viewFindViewById3 = itemView.findViewById(this.this$0.getTabLayout().getTabIndicator().getIndicatorContentId())) != null) {
                    childOrNull = viewFindViewById3;
                }
                ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
                if (!(layoutParams instanceof DslTabLayout.LayoutParams)) {
                    return childOrNull;
                }
                DslTabLayout.LayoutParams layoutParams2 = (DslTabLayout.LayoutParams) layoutParams;
                if (layoutParams2.getIndicatorContentIndex() != -1 && (itemView instanceof ViewGroup)) {
                    childOrNull = LibExKt.getChildOrNull(itemView, layoutParams2.getIndicatorContentIndex());
                }
                if (layoutParams2.getIndicatorContentId() != -1 && (viewFindViewById2 = itemView.findViewById(layoutParams2.getIndicatorContentId())) != null) {
                    childOrNull = viewFindViewById2;
                }
                if (layoutParams2.getContentIconViewIndex() != -1 && (itemView instanceof ViewGroup)) {
                    childOrNull = LibExKt.getChildOrNull(itemView, layoutParams2.getContentIconViewIndex());
                }
                return (layoutParams2.getContentIconViewId() == -1 || (viewFindViewById = itemView.findViewById(layoutParams2.getContentIconViewId())) == null) ? childOrNull : viewFindViewById;
            }
        };
        this.onGetGradientIndicatorColor = new Function3<Integer, Integer, Float, Integer>() { // from class: com.angcyo.tablayout.DslTabLayoutConfig$onGetGradientIndicatorColor$1
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Integer invoke(Integer num, Integer num2, Float f2) {
                return invoke(num.intValue(), num2.intValue(), f2.floatValue());
            }

            @NotNull
            public final Integer invoke(int i2, int i3, float f2) {
                return Integer.valueOf(this.this$0.getTabLayout().getTabIndicator().getIndicatorColor());
            }
        };
        setOnStyleItemView(new Function3<View, Integer, Boolean, Unit>() { // from class: com.angcyo.tablayout.DslTabLayoutConfig.1
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(View view, Integer num, Boolean bool) {
                invoke(view, num.intValue(), bool.booleanValue());
                return Unit.INSTANCE;
            }

            public final void invoke(@NotNull View itemView, int i2, boolean z2) {
                Intrinsics.checkNotNullParameter(itemView, "itemView");
                DslTabLayoutConfig.this.onUpdateItemStyle(itemView, i2, z2);
            }
        });
        setOnSelectIndexChange(new Function4<Integer, List<? extends Integer>, Boolean, Boolean, Unit>() { // from class: com.angcyo.tablayout.DslTabLayoutConfig.2
            {
                super(4);
            }

            @Override // kotlin.jvm.functions.Function4
            public /* bridge */ /* synthetic */ Unit invoke(Integer num, List<? extends Integer> list, Boolean bool, Boolean bool2) {
                invoke(num.intValue(), (List<Integer>) list, bool.booleanValue(), bool2.booleanValue());
                return Unit.INSTANCE;
            }

            public final void invoke(int i2, @NotNull List<Integer> selectIndexList, boolean z2, boolean z3) {
                Intrinsics.checkNotNullParameter(selectIndexList, "selectIndexList");
                int iIntValue = ((Number) CollectionsKt___CollectionsKt.last((List) selectIndexList)).intValue();
                ViewPagerDelegate viewPagerDelegate = DslTabLayoutConfig.this.getTabLayout().get_viewPagerDelegate();
                if (viewPagerDelegate != null) {
                    viewPagerDelegate.onSetCurrentItem(i2, iIntValue, z2, z3);
                }
            }
        });
    }

    public static /* synthetic */ void initAttribute$default(DslTabLayoutConfig dslTabLayoutConfig, Context context, AttributeSet attributeSet, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: initAttribute");
        }
        if ((i2 & 2) != 0) {
            attributeSet = null;
        }
        dslTabLayoutConfig.initAttribute(context, attributeSet);
    }

    public void _gradientColor(@Nullable View view, int startColor, int endColor, float percent) {
        this.tabGradientCallback.onGradientColor(view, startColor, endColor, percent);
    }

    public void _gradientIcoColor(@Nullable View view, int startColor, int endColor, float percent) {
        this.tabGradientCallback.onGradientIcoColor(view, startColor, endColor, percent);
    }

    public void _gradientScale(@Nullable View view, float startScale, float endScale, float percent) {
        this.tabGradientCallback.onGradientScale(view, startScale, endScale, percent);
    }

    public void _gradientTextSize(@Nullable TextView view, float startTextSize, float endTextSize, float percent) {
        this.tabGradientCallback.onGradientTextSize(view, startTextSize, endTextSize, percent);
    }

    public void _updateIcoColor(@Nullable View view, int color) {
        this.tabGradientCallback.onUpdateIcoColor(view, color);
    }

    @NotNull
    public final Function3<Integer, Integer, Float, Integer> getOnGetGradientIndicatorColor() {
        return this.onGetGradientIndicatorColor;
    }

    @NotNull
    public final Function2<View, Integer, View> getOnGetIcoStyleView() {
        return this.onGetIcoStyleView;
    }

    @NotNull
    public final Function2<View, Integer, TextView> getOnGetTextStyleView() {
        return this.onGetTextStyleView;
    }

    public final int getTabDeselectColor() {
        return this.tabDeselectColor;
    }

    public final boolean getTabEnableGradientColor() {
        return this.tabEnableGradientColor;
    }

    public final boolean getTabEnableGradientScale() {
        return this.tabEnableGradientScale;
    }

    public final boolean getTabEnableGradientTextSize() {
        return this.tabEnableGradientTextSize;
    }

    public final boolean getTabEnableIcoColor() {
        return this.tabEnableIcoColor;
    }

    public final boolean getTabEnableIcoGradientColor() {
        return this.tabEnableIcoGradientColor;
    }

    public final boolean getTabEnableIndicatorGradientColor() {
        return this.tabEnableIndicatorGradientColor;
    }

    public final boolean getTabEnableTextBold() {
        return this.tabEnableTextBold;
    }

    public final boolean getTabEnableTextColor() {
        return this.tabEnableTextColor;
    }

    @NotNull
    public final TabGradientCallback getTabGradientCallback() {
        return this.tabGradientCallback;
    }

    public final int getTabIcoDeselectColor() {
        int i2 = this.tabIcoDeselectColor;
        return i2 == -2 ? this.tabDeselectColor : i2;
    }

    public final int getTabIcoSelectColor() {
        int i2 = this.tabIcoSelectColor;
        return i2 == -2 ? this.tabSelectColor : i2;
    }

    public final int getTabIconViewId() {
        return this.tabIconViewId;
    }

    @NotNull
    public final DslTabLayout getTabLayout() {
        return this.tabLayout;
    }

    public final float getTabMaxScale() {
        return this.tabMaxScale;
    }

    public final float getTabMinScale() {
        return this.tabMinScale;
    }

    public final int getTabSelectColor() {
        return this.tabSelectColor;
    }

    public final float getTabTextMaxSize() {
        return this.tabTextMaxSize;
    }

    public final float getTabTextMinSize() {
        return this.tabTextMinSize;
    }

    public final int getTabTextViewId() {
        return this.tabTextViewId;
    }

    public final boolean getTabUseTypefaceBold() {
        return this.tabUseTypefaceBold;
    }

    public void initAttribute(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        Intrinsics.checkNotNullParameter(context, "context");
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.DslTabLayout);
        Intrinsics.checkNotNullExpressionValue(typedArrayObtainStyledAttributes, "context.obtainStyledAttr…R.styleable.DslTabLayout)");
        this.tabSelectColor = typedArrayObtainStyledAttributes.getColor(R.styleable.DslTabLayout_tab_select_color, this.tabSelectColor);
        this.tabDeselectColor = typedArrayObtainStyledAttributes.getColor(R.styleable.DslTabLayout_tab_deselect_color, this.tabDeselectColor);
        this.tabIcoSelectColor = typedArrayObtainStyledAttributes.getColor(R.styleable.DslTabLayout_tab_ico_select_color, -2);
        this.tabIcoDeselectColor = typedArrayObtainStyledAttributes.getColor(R.styleable.DslTabLayout_tab_ico_deselect_color, -2);
        setTabEnableTextColor(typedArrayObtainStyledAttributes.getBoolean(R.styleable.DslTabLayout_tab_enable_text_color, this.tabEnableTextColor));
        this.tabEnableIndicatorGradientColor = typedArrayObtainStyledAttributes.getBoolean(R.styleable.DslTabLayout_tab_enable_indicator_gradient_color, this.tabEnableIndicatorGradientColor);
        setTabEnableGradientColor(typedArrayObtainStyledAttributes.getBoolean(R.styleable.DslTabLayout_tab_enable_gradient_color, this.tabEnableGradientColor));
        this.tabEnableIcoColor = typedArrayObtainStyledAttributes.getBoolean(R.styleable.DslTabLayout_tab_enable_ico_color, this.tabEnableIcoColor);
        this.tabEnableIcoGradientColor = typedArrayObtainStyledAttributes.getBoolean(R.styleable.DslTabLayout_tab_enable_ico_gradient_color, this.tabEnableIcoGradientColor);
        this.tabEnableTextBold = typedArrayObtainStyledAttributes.getBoolean(R.styleable.DslTabLayout_tab_enable_text_bold, this.tabEnableTextBold);
        this.tabUseTypefaceBold = typedArrayObtainStyledAttributes.getBoolean(R.styleable.DslTabLayout_tab_use_typeface_bold, this.tabUseTypefaceBold);
        this.tabEnableGradientScale = typedArrayObtainStyledAttributes.getBoolean(R.styleable.DslTabLayout_tab_enable_gradient_scale, this.tabEnableGradientScale);
        this.tabMinScale = typedArrayObtainStyledAttributes.getFloat(R.styleable.DslTabLayout_tab_min_scale, this.tabMinScale);
        this.tabMaxScale = typedArrayObtainStyledAttributes.getFloat(R.styleable.DslTabLayout_tab_max_scale, this.tabMaxScale);
        this.tabEnableGradientTextSize = typedArrayObtainStyledAttributes.getBoolean(R.styleable.DslTabLayout_tab_enable_gradient_text_size, this.tabEnableGradientTextSize);
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.DslTabLayout_tab_text_min_size)) {
            this.tabTextMinSize = typedArrayObtainStyledAttributes.getDimensionPixelOffset(r3, (int) this.tabTextMinSize);
        }
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.DslTabLayout_tab_text_max_size)) {
            this.tabTextMaxSize = typedArrayObtainStyledAttributes.getDimensionPixelOffset(r3, (int) this.tabTextMaxSize);
        }
        this.tabTextViewId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.DslTabLayout_tab_text_view_id, this.tabTextViewId);
        this.tabIconViewId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.DslTabLayout_tab_icon_view_id, this.tabIconViewId);
        typedArrayObtainStyledAttributes.recycle();
    }

    public void onPageIndexScrolled(int fromIndex, int toIndex, float positionOffset) {
    }

    public void onPageViewScrolled(@Nullable View fromView, @NotNull View toView, float positionOffset) {
        Intrinsics.checkNotNullParameter(toView, "toView");
        if (Intrinsics.areEqual(fromView, toView)) {
            return;
        }
        int currentIndex = this.tabLayout.getTabIndicator().getCurrentIndex();
        int i2 = this.tabLayout.getTabIndicator().get_targetIndex();
        if (this.tabEnableIndicatorGradientColor) {
            this.tabLayout.getTabIndicator().setIndicatorColor(LibExKt.evaluateColor(positionOffset, this.onGetGradientIndicatorColor.invoke(Integer.valueOf(currentIndex), Integer.valueOf(currentIndex), Float.valueOf(0.0f)).intValue(), this.onGetGradientIndicatorColor.invoke(Integer.valueOf(currentIndex), Integer.valueOf(i2), Float.valueOf(positionOffset)).intValue()));
        }
        if (this.tabEnableGradientColor) {
            if (fromView != null) {
                _gradientColor(this.onGetTextStyleView.invoke(fromView, Integer.valueOf(currentIndex)), this.tabSelectColor, this.tabDeselectColor, positionOffset);
            }
            _gradientColor(this.onGetTextStyleView.invoke(toView, Integer.valueOf(i2)), this.tabDeselectColor, this.tabSelectColor, positionOffset);
        }
        if (this.tabEnableIcoGradientColor) {
            if (fromView != null) {
                _gradientIcoColor(this.onGetIcoStyleView.invoke(fromView, Integer.valueOf(currentIndex)), getTabIcoSelectColor(), getTabIcoDeselectColor(), positionOffset);
            }
            _gradientIcoColor(this.onGetIcoStyleView.invoke(toView, Integer.valueOf(i2)), getTabIcoDeselectColor(), getTabIcoSelectColor(), positionOffset);
        }
        if (this.tabEnableGradientScale) {
            _gradientScale(fromView, this.tabMaxScale, this.tabMinScale, positionOffset);
            _gradientScale(toView, this.tabMinScale, this.tabMaxScale, positionOffset);
        }
        if (this.tabEnableGradientTextSize) {
            float f2 = this.tabTextMaxSize;
            if (f2 > 0.0f) {
                float f3 = this.tabTextMinSize;
                if (f3 > 0.0f) {
                    if (f3 == f2) {
                        return;
                    }
                    _gradientTextSize(fromView != null ? this.onGetTextStyleView.invoke(fromView, Integer.valueOf(currentIndex)) : null, this.tabTextMaxSize, this.tabTextMinSize, positionOffset);
                    _gradientTextSize(this.onGetTextStyleView.invoke(toView, Integer.valueOf(i2)), this.tabTextMinSize, this.tabTextMaxSize, positionOffset);
                    if (i2 == CollectionsKt__CollectionsKt.getLastIndex(this.tabLayout.getDslSelector().getVisibleViewList()) || i2 == 0) {
                        this.tabLayout._scrollToTarget(i2, false);
                    }
                }
            }
        }
    }

    public void onUpdateItemStyle(@NotNull View itemView, int index, boolean select) {
        DslTabBorder tabBorder;
        View viewInvoke;
        Intrinsics.checkNotNullParameter(itemView, "itemView");
        TextView textViewInvoke = this.onGetTextStyleView.invoke(itemView, Integer.valueOf(index));
        if (textViewInvoke != null) {
            TextPaint paint = textViewInvoke.getPaint();
            if (paint != null) {
                Intrinsics.checkNotNullExpressionValue(paint, "paint");
                if (this.tabEnableTextBold && select) {
                    if (this.tabUseTypefaceBold) {
                        paint.setTypeface(Typeface.defaultFromStyle(1));
                    } else {
                        paint.setFlags(paint.getFlags() | 32);
                        paint.setFakeBoldText(true);
                    }
                } else if (this.tabUseTypefaceBold) {
                    paint.setTypeface(Typeface.defaultFromStyle(0));
                } else {
                    paint.setFlags(paint.getFlags() & (-33));
                    paint.setFakeBoldText(false);
                }
            }
            if (this.tabEnableTextColor) {
                textViewInvoke.setTextColor(select ? this.tabSelectColor : this.tabDeselectColor);
            }
            float f2 = this.tabTextMaxSize;
            if (f2 > 0.0f || this.tabTextMinSize > 0.0f) {
                float fMin = Math.min(this.tabTextMinSize, f2);
                float fMax = Math.max(this.tabTextMinSize, this.tabTextMaxSize);
                if (select) {
                    fMin = fMax;
                }
                textViewInvoke.setTextSize(0, fMin);
            }
        }
        if (this.tabEnableIcoColor && (viewInvoke = this.onGetIcoStyleView.invoke(itemView, Integer.valueOf(index))) != null) {
            _updateIcoColor(viewInvoke, select ? getTabIcoSelectColor() : getTabIcoDeselectColor());
        }
        if (this.tabEnableGradientScale) {
            itemView.setScaleX(select ? this.tabMaxScale : this.tabMinScale);
            itemView.setScaleY(select ? this.tabMaxScale : this.tabMinScale);
        }
        if (!this.tabLayout.getDrawBorder() || (tabBorder = this.tabLayout.getTabBorder()) == null) {
            return;
        }
        tabBorder.updateItemBackground(this.tabLayout, itemView, index, select);
    }

    public final void setOnGetGradientIndicatorColor(@NotNull Function3<? super Integer, ? super Integer, ? super Float, Integer> function3) {
        Intrinsics.checkNotNullParameter(function3, "<set-?>");
        this.onGetGradientIndicatorColor = function3;
    }

    public final void setOnGetIcoStyleView(@NotNull Function2<? super View, ? super Integer, ? extends View> function2) {
        Intrinsics.checkNotNullParameter(function2, "<set-?>");
        this.onGetIcoStyleView = function2;
    }

    public final void setOnGetTextStyleView(@NotNull Function2<? super View, ? super Integer, ? extends TextView> function2) {
        Intrinsics.checkNotNullParameter(function2, "<set-?>");
        this.onGetTextStyleView = function2;
    }

    public final void setTabDeselectColor(int i2) {
        this.tabDeselectColor = i2;
    }

    public final void setTabEnableGradientColor(boolean z2) {
        this.tabEnableGradientColor = z2;
        if (z2) {
            this.tabEnableIcoGradientColor = true;
        }
    }

    public final void setTabEnableGradientScale(boolean z2) {
        this.tabEnableGradientScale = z2;
    }

    public final void setTabEnableGradientTextSize(boolean z2) {
        this.tabEnableGradientTextSize = z2;
    }

    public final void setTabEnableIcoColor(boolean z2) {
        this.tabEnableIcoColor = z2;
    }

    public final void setTabEnableIcoGradientColor(boolean z2) {
        this.tabEnableIcoGradientColor = z2;
    }

    public final void setTabEnableIndicatorGradientColor(boolean z2) {
        this.tabEnableIndicatorGradientColor = z2;
    }

    public final void setTabEnableTextBold(boolean z2) {
        this.tabEnableTextBold = z2;
    }

    public final void setTabEnableTextColor(boolean z2) {
        this.tabEnableTextColor = z2;
        if (z2) {
            this.tabEnableIcoColor = true;
        }
    }

    public final void setTabGradientCallback(@NotNull TabGradientCallback tabGradientCallback) {
        Intrinsics.checkNotNullParameter(tabGradientCallback, "<set-?>");
        this.tabGradientCallback = tabGradientCallback;
    }

    public final void setTabIcoDeselectColor(int i2) {
        this.tabIcoDeselectColor = i2;
    }

    public final void setTabIcoSelectColor(int i2) {
        this.tabIcoSelectColor = i2;
    }

    public final void setTabIconViewId(int i2) {
        this.tabIconViewId = i2;
    }

    public final void setTabMaxScale(float f2) {
        this.tabMaxScale = f2;
    }

    public final void setTabMinScale(float f2) {
        this.tabMinScale = f2;
    }

    public final void setTabSelectColor(int i2) {
        this.tabSelectColor = i2;
    }

    public final void setTabTextMaxSize(float f2) {
        this.tabTextMaxSize = f2;
    }

    public final void setTabTextMinSize(float f2) {
        this.tabTextMinSize = f2;
    }

    public final void setTabTextViewId(int i2) {
        this.tabTextViewId = i2;
    }

    public final void setTabUseTypefaceBold(boolean z2) {
        this.tabUseTypefaceBold = z2;
    }
}
