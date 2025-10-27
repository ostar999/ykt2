package com.catchpig.mvvm.interfaces;

import com.catchpig.mvvm.base.activity.BaseActivity;
import com.catchpig.mvvm.base.fragment.BaseFragment;
import kotlin.Metadata;
import org.apache.commons.codec.language.bm.Languages;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u001c\u0010\u0002\u001a\u00020\u00032\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\u001c\u0010\b\u001a\u00020\u00032\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\n2\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0018\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0007H&¨\u0006\r"}, d2 = {"Lcom/catchpig/mvvm/interfaces/IFlowError;", "", "onBaseActivityError", "", "baseActivity", "Lcom/catchpig/mvvm/base/activity/BaseActivity;", "t", "", "onBaseFragmentError", "baseFragment", "Lcom/catchpig/mvvm/base/fragment/BaseFragment;", "onError", Languages.ANY, "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public interface IFlowError {
    void onBaseActivityError(@NotNull BaseActivity<?> baseActivity, @NotNull Throwable t2);

    void onBaseFragmentError(@NotNull BaseFragment<?> baseFragment, @NotNull Throwable t2);

    void onError(@NotNull Object any, @NotNull Throwable t2);
}
