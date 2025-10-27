package com.petterp.floatingx.view;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.StringRes;
import androidx.exifinterface.media.ExifInterface;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.tencent.open.SocialConstants;
import com.unity3d.splash.services.ads.adunit.AdUnitActivity;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\r\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u001f\u0010\u0007\u001a\u0002H\b\"\b\b\u0000\u0010\b*\u00020\u00032\b\b\u0001\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ!\u0010\f\u001a\u0004\u0018\u0001H\b\"\b\b\u0000\u0010\b*\u00020\u00032\b\b\u0001\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u001a\u0010\r\u001a\u00020\u00002\b\b\u0001\u0010\u000e\u001a\u00020\n2\b\b\u0001\u0010\u000f\u001a\u00020\nJ\u001a\u0010\u0010\u001a\u00020\u00002\b\b\u0001\u0010\u000e\u001a\u00020\n2\b\b\u0001\u0010\u0011\u001a\u00020\nJ\u0018\u0010\u0012\u001a\u00020\u00002\b\b\u0001\u0010\t\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\u0014J\u0018\u0010\u0015\u001a\u00020\u00002\b\b\u0001\u0010\t\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\u0014J\u001a\u0010\u0017\u001a\u00020\u00002\b\b\u0001\u0010\t\u001a\u00020\n2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019J\u001a\u0010\u001a\u001a\u00020\u00002\b\b\u0001\u0010\t\u001a\u00020\n2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cJ\u001a\u0010\u001d\u001a\u00020\u00002\b\b\u0001\u0010\t\u001a\u00020\n2\b\b\u0001\u0010\u0011\u001a\u00020\nJ\u0018\u0010\u001e\u001a\u00020\u00002\b\b\u0001\u0010\t\u001a\u00020\n2\u0006\u0010\u001f\u001a\u00020 J\u001a\u0010!\u001a\u00020\u00002\b\b\u0001\u0010\t\u001a\u00020\n2\b\u0010\"\u001a\u0004\u0018\u00010#J\u001a\u0010!\u001a\u00020\u00002\b\b\u0001\u0010\t\u001a\u00020\n2\b\b\u0001\u0010$\u001a\u00020\nJ\u0018\u0010%\u001a\u00020\u00002\b\b\u0001\u0010\t\u001a\u00020\n2\u0006\u0010&\u001a\u00020'J \u0010%\u001a\u00020\u00002\b\b\u0001\u0010\t\u001a\u00020\n2\u0006\u0010(\u001a\u00020\n2\u0006\u0010&\u001a\u00020'R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006)"}, d2 = {"Lcom/petterp/floatingx/view/FxViewHolder;", "", "itemView", "Landroid/view/View;", "(Landroid/view/View;)V", AdUnitActivity.EXTRA_VIEWS, "Landroid/util/SparseArray;", "getView", ExifInterface.GPS_DIRECTION_TRUE, "viewId", "", "(I)Landroid/view/View;", "getViewOrNull", "setBackgroundColor", "id", "color", "setBackgroundResource", SocialConstants.PARAM_SOURCE, "setEnabled", "isEnabled", "", "setGone", "isGone", "setImageBitMap", "bitmap", "Landroid/graphics/Bitmap;", "setImageDrawable", "drawable", "Landroid/graphics/drawable/Drawable;", "setImageResource", "setOnClickListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "Landroid/view/View$OnClickListener;", "setText", "value", "", "resId", "setTextSize", DatabaseManager.SIZE, "", "unit", "floatingx_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes4.dex */
public final class FxViewHolder {

    @Nullable
    private final View itemView;

    @NotNull
    private final SparseArray<View> views = new SparseArray<>();

    public FxViewHolder(@Nullable View view) {
        this.itemView = view;
    }

    @NotNull
    public final <T extends View> T getView(@IdRes int viewId) {
        T t2 = (T) getViewOrNull(viewId);
        if (t2 != null) {
            return t2;
        }
        throw new IllegalStateException(Intrinsics.stringPlus("No view found with id ", Integer.valueOf(viewId)).toString());
    }

    @Nullable
    public final <T extends View> T getViewOrNull(@IdRes int viewId) {
        T t2;
        T t3 = (T) this.views.get(viewId);
        if (t3 != null) {
            return t3;
        }
        View view = this.itemView;
        if (view == null || (t2 = (T) view.findViewById(viewId)) == null) {
            return null;
        }
        this.views.put(viewId, t2);
        return t2;
    }

    @NotNull
    public final FxViewHolder setBackgroundColor(@IdRes int id, @ColorInt int color) {
        getView(id).setBackgroundColor(color);
        return this;
    }

    @NotNull
    public final FxViewHolder setBackgroundResource(@IdRes int id, @DrawableRes int source) {
        getView(id).setBackgroundResource(source);
        return this;
    }

    @NotNull
    public final FxViewHolder setEnabled(@IdRes int viewId, boolean isEnabled) {
        getView(viewId).setEnabled(isEnabled);
        return this;
    }

    @NotNull
    public final FxViewHolder setGone(@IdRes int viewId, boolean isGone) {
        getView(viewId).setVisibility(isGone ? 8 : 0);
        return this;
    }

    @NotNull
    public final FxViewHolder setImageBitMap(@IdRes int viewId, @Nullable Bitmap bitmap) {
        ((ImageView) getView(viewId)).setImageBitmap(bitmap);
        return this;
    }

    @NotNull
    public final FxViewHolder setImageDrawable(@IdRes int viewId, @Nullable Drawable drawable) {
        ((ImageView) getView(viewId)).setImageDrawable(drawable);
        return this;
    }

    @NotNull
    public final FxViewHolder setImageResource(@IdRes int viewId, @DrawableRes int source) {
        ((ImageView) getView(viewId)).setImageResource(source);
        return this;
    }

    @NotNull
    public final FxViewHolder setOnClickListener(@IdRes int viewId, @NotNull View.OnClickListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        getView(viewId).setOnClickListener(listener);
        return this;
    }

    @NotNull
    public final FxViewHolder setText(@IdRes int viewId, @Nullable CharSequence value) {
        ((TextView) getView(viewId)).setText(value);
        return this;
    }

    @NotNull
    public final FxViewHolder setTextSize(@IdRes int viewId, float size) {
        ((TextView) getView(viewId)).setTextSize(size);
        return this;
    }

    @NotNull
    public final FxViewHolder setText(@IdRes int viewId, @StringRes int resId) {
        ((TextView) getView(viewId)).setText(resId);
        return this;
    }

    @NotNull
    public final FxViewHolder setTextSize(@IdRes int viewId, int unit, float size) {
        ((TextView) getView(viewId)).setTextSize(unit, size);
        return this;
    }
}
