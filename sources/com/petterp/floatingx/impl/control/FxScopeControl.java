package com.petterp.floatingx.impl.control;

import android.app.Application;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.ViewCompat;
import androidx.exifinterface.media.ExifInterface;
import com.petterp.floatingx.assist.helper.BasisHelper;
import com.petterp.floatingx.listener.control.IFxScopeControl;
import com.petterp.floatingx.view.FxManagerView;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lcom/petterp/floatingx/impl/control/FxScopeControl;", ExifInterface.GPS_DIRECTION_TRUE, "Lcom/petterp/floatingx/impl/control/FxBasisControlImpl;", "Lcom/petterp/floatingx/listener/control/IFxScopeControl;", "helper", "Lcom/petterp/floatingx/assist/helper/BasisHelper;", "(Lcom/petterp/floatingx/assist/helper/BasisHelper;)V", "show", "", "updateView", "view", "Landroid/view/View;", "floatingx_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes4.dex */
public final class FxScopeControl<T> extends FxBasisControlImpl implements IFxScopeControl<T> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FxScopeControl(@NotNull BasisHelper helper) {
        super(helper);
        Intrinsics.checkNotNullParameter(helper, "helper");
    }

    @Override // com.petterp.floatingx.listener.control.IFxScopeControl
    public void show() {
        FxManagerView orInitManagerView;
        ViewGroup containerGroup;
        if (isShow() || (orInitManagerView = getOrInitManagerView()) == null) {
            return;
        }
        updateEnableStatus(true);
        if (!ViewCompat.isAttachedToWindow(orInitManagerView) && (containerGroup = getContainerGroup()) != null) {
            containerGroup.addView(orInitManagerView);
        }
        show(orInitManagerView);
    }

    @Override // com.petterp.floatingx.impl.control.FxBasisControlImpl, com.petterp.floatingx.listener.control.IFxControl
    public void updateView(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        if (view.getContext() instanceof Application) {
            throw new IllegalArgumentException("view == Application,Scope floating windows cannot use application-level views!");
        }
        super.updateView(view);
    }
}
