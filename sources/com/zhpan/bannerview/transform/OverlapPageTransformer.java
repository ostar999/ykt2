package com.zhpan.bannerview.transform;

import android.view.View;
import androidx.annotation.RequiresApi;
import androidx.viewpager2.widget.ViewPager2;
import com.catchpig.utils.ext.CommonExtKt;
import com.psychiatrygarden.utils.Constants;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@RequiresApi(api = 21)
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\u0005¢\u0006\u0002\u0010\tJ\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0005H\u0016R\u000e\u0010\b\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lcom/zhpan/bannerview/transform/OverlapPageTransformer;", "Landroidx/viewpager2/widget/ViewPager2$PageTransformer;", "orientation", "", "minScale", "", "unSelectedItemRotation", "unSelectedItemAlpha", "itemGap", "(IFFFF)V", "scalingValue", "transformPage", "", Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "Landroid/view/View;", "position", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nOverlapPageTransformer.kt\nKotlin\n*S Kotlin\n*F\n+ 1 OverlapPageTransformer.kt\ncom/zhpan/bannerview/transform/OverlapPageTransformer\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,113:1\n1#2:114\n*E\n"})
/* loaded from: classes8.dex */
public final class OverlapPageTransformer implements ViewPager2.PageTransformer {
    private final float itemGap;
    private final float minScale;
    private final int orientation;
    private float scalingValue;
    private final float unSelectedItemAlpha;
    private final float unSelectedItemRotation;

    public OverlapPageTransformer(int i2, float f2, float f3, float f4, float f5) {
        this.orientation = i2;
        this.minScale = f2;
        this.unSelectedItemRotation = f3;
        this.unSelectedItemAlpha = f4;
        this.itemGap = f5;
        if (!(0.0f <= f2 && f2 <= 1.0f)) {
            throw new IllegalArgumentException("minScale value should be between 1.0 to 0.0".toString());
        }
        if (!(0.0f <= f4 && f4 <= 1.0f)) {
            throw new IllegalArgumentException("unSelectedItemAlpha value should be between 1.0 to 0.0".toString());
        }
        this.scalingValue = 0.2f;
    }

    @Override // androidx.viewpager2.widget.ViewPager2.PageTransformer
    public void transformPage(@NotNull View page, float position) {
        Intrinsics.checkNotNullParameter(page, "page");
        float f2 = this.minScale;
        this.scalingValue = ((double) f2) >= 0.8d ? 0.2f : ((double) f2) >= 0.6d ? 0.3f : 0.4f;
        page.setElevation(-Math.abs(position));
        float fMax = Math.max(1.0f - Math.abs(position * 0.5f), 0.5f);
        float f3 = this.unSelectedItemRotation;
        if (!(f3 == 0.0f)) {
            float f4 = 1 - fMax;
            if (position <= 0.0f) {
                f3 = -f3;
            }
            page.setRotationY(f4 * f3);
        }
        float fMax2 = Math.max(1.0f - Math.abs(this.scalingValue * position), this.minScale);
        page.setScaleX(fMax2);
        page.setScaleY(fMax2);
        float fDp2px = CommonExtKt.dp2px(page, ((int) this.itemGap) / 2);
        int i2 = this.orientation;
        if (i2 == 0) {
            page.setTranslationX((fDp2px * position) + ((position > 0.0f ? -page.getWidth() : page.getWidth()) * (1.0f - fMax2)));
        } else {
            if (i2 != 1) {
                throw new IllegalArgumentException("Gives correct orientation value, ViewPager2.ORIENTATION_HORIZONTAL or ViewPager2.ORIENTATION_VERTICAL");
            }
            page.setTranslationY((fDp2px * position) + ((position > 0.0f ? -page.getWidth() : page.getWidth()) * (1.0f - fMax2)));
        }
        if (this.unSelectedItemAlpha == 1.0f) {
            return;
        }
        page.setAlpha((position < -1.0f || position > 1.0f) ? 0.5f / Math.abs(position * position) : ((1 - Math.abs(position)) * 0.5f) + 0.5f);
    }

    public /* synthetic */ OverlapPageTransformer(int i2, float f2, float f3, float f4, float f5, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i2, (i3 & 2) != 0 ? 0.0f : f2, (i3 & 4) != 0 ? 0.0f : f3, (i3 & 8) != 0 ? 0.0f : f4, (i3 & 16) != 0 ? 0.0f : f5);
    }
}
