package com.petterp.floatingx.listener;

import android.view.MotionEvent;
import androidx.core.app.NotificationCompat;
import com.beizi.fusion.widget.ScrollClickView;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J \u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH&J\u0010\u0010\n\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u000b\u001a\u00020\u0003H&¨\u0006\f"}, d2 = {"Lcom/petterp/floatingx/listener/IFxScrollListener;", "", ScrollClickView.DIR_DOWN, "", "dragIng", NotificationCompat.CATEGORY_EVENT, "Landroid/view/MotionEvent;", "x", "", "y", "eventIng", "up", "floatingx_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes4.dex */
public interface IFxScrollListener {
    void down();

    void dragIng(@NotNull MotionEvent event, float x2, float y2);

    void eventIng(@NotNull MotionEvent event);

    void up();
}
