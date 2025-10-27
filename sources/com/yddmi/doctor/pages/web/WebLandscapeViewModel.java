package com.yddmi.doctor.pages.web;

import com.catchpig.mvvm.base.viewmodel.BaseViewModel;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\b¨\u0006\f"}, d2 = {"Lcom/yddmi/doctor/pages/web/WebLandscapeViewModel;", "Lcom/catchpig/mvvm/base/viewmodel/BaseViewModel;", "()V", "id", "", "getId", "()Ljava/lang/String;", "setId", "(Ljava/lang/String;)V", "loadUri", "getLoadUri", "setLoadUri", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class WebLandscapeViewModel extends BaseViewModel {

    @NotNull
    private String loadUri = "";

    @Nullable
    private String id = "";

    @Nullable
    public final String getId() {
        return this.id;
    }

    @NotNull
    public final String getLoadUri() {
        return this.loadUri;
    }

    public final void setId(@Nullable String str) {
        this.id = str;
    }

    public final void setLoadUri(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.loadUri = str;
    }
}
