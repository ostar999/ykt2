package com.yddmi.doctor.config;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.viewbinding.ViewBinding;
import com.catchpig.annotation.GlobalConfig;
import com.catchpig.mvvm.base.activity.BaseActivity;
import com.catchpig.mvvm.base.fragment.BaseFragment;
import com.catchpig.mvvm.interfaces.IGlobalConfig;
import com.catchpig.utils.ext.ViewGroupExtKt;
import com.yddmi.doctor.R;
import com.yddmi.doctor.databinding.LayoutActivityErrorBinding;
import com.yddmi.doctor.databinding.LayoutEmptyBinding;
import com.yddmi.doctor.databinding.LayoutFragmentErrorBinding;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.codec.language.bm.Languages;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@GlobalConfig
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\nH\u0016J\b\u0010\u000b\u001a\u00020\nH\u0016J\b\u0010\f\u001a\u00020\nH\u0016J\u0010\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\nH\u0016J\b\u0010\u0011\u001a\u00020\nH\u0016J\b\u0010\u0012\u001a\u00020\nH\u0016J\b\u0010\u0013\u001a\u00020\nH\u0016J\b\u0010\u0014\u001a\u00020\nH\u0016J\b\u0010\u0015\u001a\u00020\nH\u0016J\b\u0010\u0016\u001a\u00020\nH\u0016J\b\u0010\u0017\u001a\u00020\nH\u0016J\b\u0010\u0018\u001a\u00020\u0019H\u0016J\b\u0010\u001a\u001a\u00020\nH\u0016¨\u0006\u001b"}, d2 = {"Lcom/yddmi/doctor/config/MvvmGlobalConfig;", "Lcom/catchpig/mvvm/interfaces/IGlobalConfig;", "()V", "getFailedBinding", "Landroidx/viewbinding/ViewBinding;", "layoutInflater", "Landroid/view/LayoutInflater;", Languages.ANY, "", "getLoadingBackground", "", "getLoadingColor", "getPageSize", "getRecyclerEmptyBanding", "parent", "Landroid/view/ViewGroup;", "getStartPageIndex", "getStatusBackgroud", "getTitleBackIcon", "getTitleBackground", "getTitleHeight", "getTitleLineColor", "getTitleRightFirstTextColor", "getTitleTextColor", "isShowTitleLine", "", "onFailedReloadClickId", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class MvvmGlobalConfig implements IGlobalConfig {
    @Override // com.catchpig.mvvm.interfaces.IGlobalConfig
    @Nullable
    public ViewBinding getFailedBinding(@NotNull LayoutInflater layoutInflater, @NotNull Object any) {
        Intrinsics.checkNotNullParameter(layoutInflater, "layoutInflater");
        Intrinsics.checkNotNullParameter(any, "any");
        if (any instanceof BaseActivity) {
            return LayoutActivityErrorBinding.inflate(layoutInflater);
        }
        if (any instanceof BaseFragment) {
            return LayoutFragmentErrorBinding.inflate(layoutInflater);
        }
        return null;
    }

    @Override // com.catchpig.mvvm.interfaces.IGlobalConfig
    public int getLoadingBackground() {
        return R.color.color_transparent;
    }

    @Override // com.catchpig.mvvm.interfaces.IGlobalConfig
    public int getLoadingColor() {
        return R.color.color_0000FF;
    }

    @Override // com.catchpig.mvvm.interfaces.IGlobalConfig
    public int getPageSize() {
        return 10;
    }

    @Override // com.catchpig.mvvm.interfaces.IGlobalConfig
    @NotNull
    public ViewBinding getRecyclerEmptyBanding(@NotNull ViewGroup parent) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        LayoutEmptyBinding layoutEmptyBindingInflate = LayoutEmptyBinding.inflate(ViewGroupExtKt.layoutInflater(parent), parent, false);
        Intrinsics.checkNotNullExpressionValue(layoutEmptyBindingInflate, "inflate(parent.layoutInflater(), parent, false)");
        return layoutEmptyBindingInflate;
    }

    @Override // com.catchpig.mvvm.interfaces.IGlobalConfig
    public int getStartPageIndex() {
        return 1;
    }

    @Override // com.catchpig.mvvm.interfaces.IGlobalConfig
    public int getStatusBackgroud() {
        return R.color.color_transparent;
    }

    @Override // com.catchpig.mvvm.interfaces.IGlobalConfig
    public int getTitleBackIcon() {
        return R.drawable.clinic_back_black;
    }

    @Override // com.catchpig.mvvm.interfaces.IGlobalConfig
    public int getTitleBackground() {
        return R.color.color_white;
    }

    @Override // com.catchpig.mvvm.interfaces.IGlobalConfig
    public int getTitleHeight() {
        return R.dimen.title_bar_height;
    }

    @Override // com.catchpig.mvvm.interfaces.IGlobalConfig
    public int getTitleLineColor() {
        return R.color.c_f2f2f2;
    }

    @Override // com.catchpig.mvvm.interfaces.IGlobalConfig
    public int getTitleRightFirstTextColor() {
        return R.color.c_1772fb;
    }

    @Override // com.catchpig.mvvm.interfaces.IGlobalConfig
    public int getTitleTextColor() {
        return R.color.color_333;
    }

    @Override // com.catchpig.mvvm.interfaces.IGlobalConfig
    public boolean isShowTitleLine() {
        return true;
    }

    @Override // com.catchpig.mvvm.interfaces.IGlobalConfig
    public int onFailedReloadClickId() {
        return R.id.failed_reload;
    }
}
