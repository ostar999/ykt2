package com.ykb.ebook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.tencent.rtmp.sharp.jni.QLog;
import com.ykb.ebook.adapter.base.BaseQuickAdapter;
import com.ykb.ebook.adapter.base.QuickViewHolder;
import com.ykb.ebook.common_interface.AdapterConvertListener;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010\u0000\n\u0002\b\r\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u0000*\u0004\b\u0000\u0010\u00012\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u00020\u00030\u0002B!\u0012\b\b\u0001\u0010\u0004\u001a\u00020\u0005\u0012\u0010\b\u0002\u0010\u0006\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ'\u0010\u0018\u001a\u00020\u000b2\u0006\u0010\u0019\u001a\u00020\u00032\u0006\u0010\u001a\u001a\u00020\u00052\b\u0010\u001b\u001a\u0004\u0018\u00018\u0000H\u0014¢\u0006\u0002\u0010\u001cJ5\u0010\u0018\u001a\u00020\u000b2\u0006\u0010\u0019\u001a\u00020\u00032\u0006\u0010\u001a\u001a\u00020\u00052\b\u0010\u001b\u001a\u0004\u0018\u00018\u00002\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012H\u0014¢\u0006\u0002\u0010\u001eJ \u0010\u001f\u001a\u00020\u00032\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020\u0005H\u0014R6\u0010\t\u001a\u001e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00018\u0000\u0012\u0004\u0012\u00020\u000b\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fRB\u0010\u0010\u001a*\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00018\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u0012\u0012\u0004\u0012\u00020\u000b\u0018\u00010\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0006\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lcom/ykb/ebook/adapter/CommonAdapter;", QLog.TAG_REPORTLEVEL_DEVELOPER, "Lcom/ykb/ebook/adapter/base/BaseQuickAdapter;", "Lcom/ykb/ebook/adapter/base/QuickViewHolder;", "layoutRes", "", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "Lcom/ykb/ebook/common_interface/AdapterConvertListener;", "(ILcom/ykb/ebook/common_interface/AdapterConvertListener;)V", "convert", "Lkotlin/Function3;", "", "getConvert", "()Lkotlin/jvm/functions/Function3;", "setConvert", "(Lkotlin/jvm/functions/Function3;)V", "convertPayload", "Lkotlin/Function4;", "", "", "getConvertPayload", "()Lkotlin/jvm/functions/Function4;", "setConvertPayload", "(Lkotlin/jvm/functions/Function4;)V", "onBindViewHolder", "holder", "position", "item", "(Lcom/ykb/ebook/adapter/base/QuickViewHolder;ILjava/lang/Object;)V", "payloads", "(Lcom/ykb/ebook/adapter/base/QuickViewHolder;ILjava/lang/Object;Ljava/util/List;)V", "onCreateViewHolder", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "parent", "Landroid/view/ViewGroup;", "viewType", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public class CommonAdapter<D> extends BaseQuickAdapter<D, QuickViewHolder> {

    @Nullable
    private Function3<? super QuickViewHolder, ? super Integer, ? super D, Unit> convert;

    @Nullable
    private Function4<? super QuickViewHolder, ? super Integer, ? super D, ? super List<? extends Object>, Unit> convertPayload;
    private final int layoutRes;

    @Nullable
    private final AdapterConvertListener<D> listener;

    public /* synthetic */ CommonAdapter(int i2, AdapterConvertListener adapterConvertListener, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i2, (i3 & 2) != 0 ? null : adapterConvertListener);
    }

    @Nullable
    public final Function3<QuickViewHolder, Integer, D, Unit> getConvert() {
        return this.convert;
    }

    @Nullable
    public final Function4<QuickViewHolder, Integer, D, List<? extends Object>, Unit> getConvertPayload() {
        return this.convertPayload;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.ykb.ebook.adapter.base.BaseQuickAdapter
    public /* bridge */ /* synthetic */ void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i2, Object obj) {
        onBindViewHolder((QuickViewHolder) viewHolder, i2, (int) obj);
    }

    public final void setConvert(@Nullable Function3<? super QuickViewHolder, ? super Integer, ? super D, Unit> function3) {
        this.convert = function3;
    }

    public final void setConvertPayload(@Nullable Function4<? super QuickViewHolder, ? super Integer, ? super D, ? super List<? extends Object>, Unit> function4) {
        this.convertPayload = function4;
    }

    public CommonAdapter(@LayoutRes int i2, @Nullable AdapterConvertListener<D> adapterConvertListener) {
        super(null, 1, null);
        this.layoutRes = i2;
        this.listener = adapterConvertListener;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.ykb.ebook.adapter.base.BaseQuickAdapter
    public /* bridge */ /* synthetic */ void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i2, Object obj, List list) {
        onBindViewHolder((QuickViewHolder) viewHolder, i2, (int) obj, (List<? extends Object>) list);
    }

    @Override // com.ykb.ebook.adapter.base.BaseQuickAdapter
    @NotNull
    public QuickViewHolder onCreateViewHolder(@NotNull Context context, @NotNull ViewGroup parent, int viewType) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(parent, "parent");
        View view = LayoutInflater.from(context).inflate(this.layoutRes, parent, false);
        Intrinsics.checkNotNullExpressionValue(view, "view");
        return new QuickViewHolder(view);
    }

    public void onBindViewHolder(@NotNull QuickViewHolder holder, int position, @Nullable D item) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        AdapterConvertListener<D> adapterConvertListener = this.listener;
        if (adapterConvertListener != null) {
            Intrinsics.checkNotNull(item);
            adapterConvertListener.convert(holder, position, item);
        }
        Function3<? super QuickViewHolder, ? super Integer, ? super D, Unit> function3 = this.convert;
        if (function3 != null) {
            function3.invoke(holder, Integer.valueOf(position), item);
        }
    }

    public void onBindViewHolder(@NotNull QuickViewHolder holder, int position, @Nullable D item, @NotNull List<? extends Object> payloads) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(payloads, "payloads");
        AdapterConvertListener<D> adapterConvertListener = this.listener;
        if (adapterConvertListener != null) {
            Intrinsics.checkNotNull(item);
            adapterConvertListener.convert(holder, position, item, payloads);
        }
        Function4<? super QuickViewHolder, ? super Integer, ? super D, ? super List<? extends Object>, Unit> function4 = this.convertPayload;
        if (function4 != null) {
            function4.invoke(holder, Integer.valueOf(position), item, payloads);
        }
    }
}
