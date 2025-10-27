package com.ykb.ebook.common_interface;

import android.view.View;
import androidx.annotation.IdRes;
import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.unity3d.splash.services.ads.adunit.AdUnitActivity;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u0015\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J%\u0010\u0002\u001a\u0004\u0018\u0001H\u0003\"\n\b\u0000\u0010\u0003*\u0004\u0018\u00010\u00042\b\b\u0001\u0010\u0005\u001a\u00020\u0006H&¢\u0006\u0002\u0010\u0007J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0004H\u0016J/\u0010\u000b\u001a\u00020\t2\b\u0010\f\u001a\u0004\u0018\u00010\u00012\u0016\u0010\r\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00040\u000e\"\u0004\u0018\u00010\u0004H\u0016¢\u0006\u0002\u0010\u000fJ \u0010\u000b\u001a\u00020\t2\b\u0010\f\u001a\u0004\u0018\u00010\u00012\f\b\u0001\u0010\u0010\u001a\u00020\u0011\"\u00020\u0006H\u0016J%\u0010\u000b\u001a\u00020\t2\u0016\u0010\r\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00040\u000e\"\u0004\u0018\u00010\u0004H\u0016¢\u0006\u0002\u0010\u0012J\u0016\u0010\u000b\u001a\u00020\t2\f\b\u0001\u0010\u0010\u001a\u00020\u0011\"\u00020\u0006H\u0016¨\u0006\u0013"}, d2 = {"Lcom/ykb/ebook/common_interface/ClickAction;", "Landroid/view/View$OnClickListener;", "findViewById", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Landroid/view/View;", "id", "", "(I)Landroid/view/View;", "onClick", "", "view", "setOnClickListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, AdUnitActivity.EXTRA_VIEWS, "", "(Landroid/view/View$OnClickListener;[Landroid/view/View;)V", "ids", "", "([Landroid/view/View;)V", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public interface ClickAction extends View.OnClickListener {

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public static final class DefaultImpls {
        public static void onClick(@NotNull ClickAction clickAction, @NotNull View view) {
            Intrinsics.checkNotNullParameter(view, "view");
        }

        public static void setOnClickListener(@NotNull ClickAction clickAction, @IdRes @NotNull int... ids) {
            Intrinsics.checkNotNullParameter(ids, "ids");
            clickAction.setOnClickListener(clickAction, Arrays.copyOf(ids, ids.length));
        }

        public static void setOnClickListener(@NotNull ClickAction clickAction, @Nullable View.OnClickListener onClickListener, @IdRes @NotNull int... ids) {
            Intrinsics.checkNotNullParameter(ids, "ids");
            for (int i2 : ids) {
                View viewFindViewById = clickAction.findViewById(i2);
                if (viewFindViewById != null) {
                    viewFindViewById.setOnClickListener(onClickListener);
                }
            }
        }

        public static void setOnClickListener(@NotNull ClickAction clickAction, @NotNull View... views) {
            Intrinsics.checkNotNullParameter(views, "views");
            clickAction.setOnClickListener(clickAction, (View[]) Arrays.copyOf(views, views.length));
        }

        public static void setOnClickListener(@NotNull ClickAction clickAction, @Nullable View.OnClickListener onClickListener, @NotNull View... views) {
            Intrinsics.checkNotNullParameter(views, "views");
            for (View view : views) {
                if (view != null) {
                    view.setOnClickListener(onClickListener);
                }
            }
        }
    }

    @Nullable
    <V extends View> V findViewById(@IdRes int id);

    @Override // android.view.View.OnClickListener
    void onClick(@NotNull View view);

    void setOnClickListener(@Nullable View.OnClickListener listener, @IdRes @NotNull int... ids);

    void setOnClickListener(@Nullable View.OnClickListener listener, @NotNull View... views);

    void setOnClickListener(@IdRes @NotNull int... ids);

    void setOnClickListener(@NotNull View... views);
}
