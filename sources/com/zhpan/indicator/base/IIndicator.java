package com.zhpan.indicator.base;

import androidx.viewpager.widget.ViewPager;
import com.zhpan.indicator.option.IndicatorOptions;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u0010\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&¨\u0006\u0007"}, d2 = {"Lcom/zhpan/indicator/base/IIndicator;", "Landroidx/viewpager/widget/ViewPager$OnPageChangeListener;", "notifyDataChanged", "", "setIndicatorOptions", "options", "Lcom/zhpan/indicator/option/IndicatorOptions;", "indicator_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes8.dex */
public interface IIndicator extends ViewPager.OnPageChangeListener {
    void notifyDataChanged();

    void setIndicatorOptions(@NotNull IndicatorOptions options);
}
