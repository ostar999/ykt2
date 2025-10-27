package com.lxj.easyadapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.exifinterface.media.ExifInterface;
import androidx.recyclerview.widget.RecyclerView;
import com.umeng.analytics.pro.d;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\r\n\u0002\b\u0002\u0018\u0000 \u00142\u00020\u0001:\u0001\u0014B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001d\u0010\t\u001a\u0002H\n\"\b\b\u0000\u0010\n*\u00020\u00032\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ\u001f\u0010\u000e\u001a\u0004\u0018\u0001H\n\"\b\b\u0000\u0010\n*\u00020\u00032\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ\u0016\u0010\u000f\u001a\u00020\u00002\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\fJ\u0016\u0010\u0011\u001a\u00020\u00002\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u0013R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lcom/lxj/easyadapter/ViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "convertView", "Landroid/view/View;", "(Landroid/view/View;)V", "getConvertView", "()Landroid/view/View;", "mViews", "Landroid/util/SparseArray;", "getView", ExifInterface.GPS_DIRECTION_TRUE, "viewId", "", "(I)Landroid/view/View;", "getViewOrNull", "setImageResource", "resId", "setText", "text", "", "Companion", "easy-adapter_release"}, k = 1, mv = {1, 1, 15})
/* loaded from: classes4.dex */
public final class ViewHolder extends RecyclerView.ViewHolder {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private final View convertView;
    private final SparseArray<View> mViews;

    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\f¨\u0006\r"}, d2 = {"Lcom/lxj/easyadapter/ViewHolder$Companion;", "", "()V", "createViewHolder", "Lcom/lxj/easyadapter/ViewHolder;", d.R, "Landroid/content/Context;", "parent", "Landroid/view/ViewGroup;", "layoutId", "", "itemView", "Landroid/view/View;", "easy-adapter_release"}, k = 1, mv = {1, 1, 15})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ViewHolder createViewHolder(@NotNull View itemView) {
            Intrinsics.checkParameterIsNotNull(itemView, "itemView");
            return new ViewHolder(itemView);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final ViewHolder createViewHolder(@NotNull Context context, @NotNull ViewGroup parent, int layoutId) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            Intrinsics.checkParameterIsNotNull(parent, "parent");
            View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
            Intrinsics.checkExpressionValueIsNotNull(itemView, "itemView");
            return new ViewHolder(itemView);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ViewHolder(@NotNull View convertView) {
        super(convertView);
        Intrinsics.checkParameterIsNotNull(convertView, "convertView");
        this.convertView = convertView;
        this.mViews = new SparseArray<>();
    }

    @NotNull
    public final View getConvertView() {
        return this.convertView;
    }

    @NotNull
    public final <T extends View> T getView(int viewId) {
        T t2 = (T) this.mViews.get(viewId);
        if (t2 == null) {
            t2 = (T) this.convertView.findViewById(viewId);
            this.mViews.put(viewId, t2);
        }
        if (t2 != null) {
            return t2;
        }
        throw new TypeCastException("null cannot be cast to non-null type T");
    }

    @Nullable
    public final <T extends View> T getViewOrNull(int viewId) {
        T t2 = (T) this.mViews.get(viewId);
        if (t2 == null) {
            t2 = (T) this.convertView.findViewById(viewId);
            this.mViews.put(viewId, t2);
        }
        if (t2 instanceof View) {
            return t2;
        }
        return null;
    }

    @NotNull
    public final ViewHolder setImageResource(int viewId, int resId) {
        ImageView imageView = (ImageView) getView(viewId);
        if (imageView != null) {
            imageView.setImageResource(resId);
        }
        return this;
    }

    @NotNull
    public final ViewHolder setText(int viewId, @NotNull CharSequence text) {
        Intrinsics.checkParameterIsNotNull(text, "text");
        TextView textView = (TextView) getView(viewId);
        if (textView != null) {
            textView.setText(text);
        }
        return this;
    }
}
