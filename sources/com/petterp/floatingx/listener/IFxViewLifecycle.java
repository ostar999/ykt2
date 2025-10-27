package com.petterp.floatingx.listener;

import android.view.View;
import com.petterp.floatingx.view.FxViewHolder;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016J\b\u0010\u0004\u001a\u00020\u0003H\u0016J\u0010\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\u0005\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u0016J\b\u0010\n\u001a\u00020\u0003H\u0016J\b\u0010\u000b\u001a\u00020\u0003H\u0016J\u0010\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000eH\u0016¨\u0006\u000f"}, d2 = {"Lcom/petterp/floatingx/listener/IFxViewLifecycle;", "", "attach", "", "detached", "initView", "view", "Landroid/view/View;", "holder", "Lcom/petterp/floatingx/view/FxViewHolder;", "postAttach", "postDetached", "windowsVisibility", "visibility", "", "floatingx_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes4.dex */
public interface IFxViewLifecycle {

    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    public static final class DefaultImpls {
        public static void attach(@NotNull IFxViewLifecycle iFxViewLifecycle) {
            Intrinsics.checkNotNullParameter(iFxViewLifecycle, "this");
        }

        public static void detached(@NotNull IFxViewLifecycle iFxViewLifecycle) {
            Intrinsics.checkNotNullParameter(iFxViewLifecycle, "this");
        }

        public static void initView(@NotNull IFxViewLifecycle iFxViewLifecycle, @NotNull View view) {
            Intrinsics.checkNotNullParameter(iFxViewLifecycle, "this");
            Intrinsics.checkNotNullParameter(view, "view");
        }

        public static void initView(@NotNull IFxViewLifecycle iFxViewLifecycle, @NotNull FxViewHolder holder) {
            Intrinsics.checkNotNullParameter(iFxViewLifecycle, "this");
            Intrinsics.checkNotNullParameter(holder, "holder");
        }

        public static void postAttach(@NotNull IFxViewLifecycle iFxViewLifecycle) {
            Intrinsics.checkNotNullParameter(iFxViewLifecycle, "this");
        }

        public static void postDetached(@NotNull IFxViewLifecycle iFxViewLifecycle) {
            Intrinsics.checkNotNullParameter(iFxViewLifecycle, "this");
        }

        public static void windowsVisibility(@NotNull IFxViewLifecycle iFxViewLifecycle, int i2) {
            Intrinsics.checkNotNullParameter(iFxViewLifecycle, "this");
        }
    }

    void attach();

    void detached();

    void initView(@NotNull View view);

    void initView(@NotNull FxViewHolder holder);

    void postAttach();

    void postDetached();

    void windowsVisibility(int visibility);
}
