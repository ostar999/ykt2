package com.ykb.ebook.base;

import android.os.Bundle;
import androidx.lifecycle.ViewModel;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\r\u0010\r\u001a\u00028\u0000H$¢\u0006\u0002\u0010\tJ\u0012\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0016R\u001c\u0010\u0007\u001a\u00028\u0000X\u0086.¢\u0006\u0010\n\u0002\u0010\f\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000b¨\u0006\u0012"}, d2 = {"Lcom/ykb/ebook/base/BaseVmFragment;", "VM", "Landroidx/lifecycle/ViewModel;", "Lcom/ykb/ebook/base/BaseFragment;", "layoutId", "", "(I)V", "viewModel", "getViewModel", "()Landroidx/lifecycle/ViewModel;", "setViewModel", "(Landroidx/lifecycle/ViewModel;)V", "Landroidx/lifecycle/ViewModel;", "initViewModel", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public abstract class BaseVmFragment<VM extends ViewModel> extends BaseFragment {
    public VM viewModel;

    public BaseVmFragment(int i2) {
        super(i2);
    }

    @NotNull
    public final VM getViewModel() {
        VM vm = this.viewModel;
        if (vm != null) {
            return vm;
        }
        Intrinsics.throwUninitializedPropertyAccessException("viewModel");
        return null;
    }

    @NotNull
    public abstract VM initViewModel();

    @Override // androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setViewModel(initViewModel());
        observeViewModel();
    }

    public final void setViewModel(@NotNull VM vm) {
        Intrinsics.checkNotNullParameter(vm, "<set-?>");
        this.viewModel = vm;
    }
}
