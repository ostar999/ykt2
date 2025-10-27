package com.petterp.floatingx.impl;

import android.view.MotionEvent;
import androidx.core.app.NotificationCompat;
import com.beizi.fusion.widget.ScrollClickView;
import com.petterp.floatingx.listener.IFxScrollListener;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J \u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tH\u0016J\u0010\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\b\u0010\f\u001a\u00020\u0004H\u0016¨\u0006\r"}, d2 = {"Lcom/petterp/floatingx/impl/FxScrollImpl;", "Lcom/petterp/floatingx/listener/IFxScrollListener;", "()V", ScrollClickView.DIR_DOWN, "", "dragIng", NotificationCompat.CATEGORY_EVENT, "Landroid/view/MotionEvent;", "x", "", "y", "eventIng", "up", "floatingx_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes4.dex */
public class FxScrollImpl implements IFxScrollListener {
    @Override // com.petterp.floatingx.listener.IFxScrollListener
    public void down() {
    }

    @Override // com.petterp.floatingx.listener.IFxScrollListener
    public void dragIng(@NotNull MotionEvent event, float x2, float y2) {
        Intrinsics.checkNotNullParameter(event, "event");
    }

    @Override // com.petterp.floatingx.listener.IFxScrollListener
    public void eventIng(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
    }

    @Override // com.petterp.floatingx.listener.IFxScrollListener
    public void up() {
    }
}
