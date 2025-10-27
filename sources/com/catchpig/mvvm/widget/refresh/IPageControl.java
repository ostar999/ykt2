package com.catchpig.mvvm.widget.refresh;

import java.util.List;
import kotlin.Metadata;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0003H&J\b\u0010\u0005\u001a\u00020\u0003H&J\u0016\u0010\u0006\u001a\u00020\u00032\f\u0010\u0007\u001a\b\u0012\u0002\b\u0003\u0018\u00010\bH&¨\u0006\t"}, d2 = {"Lcom/catchpig/mvvm/widget/refresh/IPageControl;", "", "loadNextPageIndex", "", "resetPageIndex", "updateError", "updateSuccess", "list", "", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public interface IPageControl {
    void loadNextPageIndex();

    void resetPageIndex();

    void updateError();

    void updateSuccess(@Nullable List<?> list);
}
