package com.catchpig.mvvm.interfaces;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.viewbinding.ViewBinding;
import kotlin.Metadata;
import org.apache.commons.codec.language.bm.Languages;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u001a\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0001H&J\b\u0010\u0007\u001a\u00020\bH'J\b\u0010\t\u001a\u00020\bH'J\b\u0010\n\u001a\u00020\bH&J\u0010\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\rH&J\b\u0010\u000e\u001a\u00020\bH&J\b\u0010\u000f\u001a\u00020\bH'J\b\u0010\u0010\u001a\u00020\bH'J\b\u0010\u0011\u001a\u00020\bH'J\b\u0010\u0012\u001a\u00020\bH'J\b\u0010\u0013\u001a\u00020\bH'J\b\u0010\u0014\u001a\u00020\bH'J\b\u0010\u0015\u001a\u00020\bH'J\b\u0010\u0016\u001a\u00020\u0017H&J\b\u0010\u0018\u001a\u00020\bH'¨\u0006\u0019"}, d2 = {"Lcom/catchpig/mvvm/interfaces/IGlobalConfig;", "", "getFailedBinding", "Landroidx/viewbinding/ViewBinding;", "layoutInflater", "Landroid/view/LayoutInflater;", Languages.ANY, "getLoadingBackground", "", "getLoadingColor", "getPageSize", "getRecyclerEmptyBanding", "parent", "Landroid/view/ViewGroup;", "getStartPageIndex", "getStatusBackgroud", "getTitleBackIcon", "getTitleBackground", "getTitleHeight", "getTitleLineColor", "getTitleRightFirstTextColor", "getTitleTextColor", "isShowTitleLine", "", "onFailedReloadClickId", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public interface IGlobalConfig {
    @Nullable
    ViewBinding getFailedBinding(@NotNull LayoutInflater layoutInflater, @NotNull Object any);

    @ColorRes
    int getLoadingBackground();

    @ColorRes
    int getLoadingColor();

    int getPageSize();

    @NotNull
    ViewBinding getRecyclerEmptyBanding(@NotNull ViewGroup parent);

    int getStartPageIndex();

    @ColorRes
    int getStatusBackgroud();

    @DrawableRes
    int getTitleBackIcon();

    @ColorRes
    int getTitleBackground();

    @DimenRes
    int getTitleHeight();

    @ColorRes
    int getTitleLineColor();

    @ColorRes
    int getTitleRightFirstTextColor();

    @ColorRes
    int getTitleTextColor();

    boolean isShowTitleLine();

    @IdRes
    int onFailedReloadClickId();
}
