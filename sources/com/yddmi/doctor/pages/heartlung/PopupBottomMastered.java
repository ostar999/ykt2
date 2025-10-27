package com.yddmi.doctor.pages.heartlung;

import android.content.Context;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.lxj.xpopup.core.BottomPopupView;
import com.plv.foundationsdk.rx.PLVRxEncryptDataFunction;
import com.yddmi.doctor.R;
import com.yddmi.doctor.entity.result.HeartDetail;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001:\u0001\u001aB\u000f\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0012\u001a\u00020\u0013H\u0014J\b\u0010\u0014\u001a\u00020\u0015H\u0014J\u0016\u0010\u0016\u001a\u00020\u00002\u000e\u0010\u0017\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\bJ\u000e\u0010\u0018\u001a\u00020\u00152\u0006\u0010\u0019\u001a\u00020\u000fR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\"\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lcom/yddmi/doctor/pages/heartlung/PopupBottomMastered;", "Lcom/lxj/xpopup/core/BottomPopupView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "mAdapter", "Lcom/yddmi/doctor/pages/heartlung/AdapterMastered;", "mData", "", "Lcom/yddmi/doctor/entity/result/HeartDetail;", "getMData", "()Ljava/util/List;", "setMData", "(Ljava/util/List;)V", "mListener", "Lcom/yddmi/doctor/pages/heartlung/PopupBottomMastered$OnPopupMasteredClickListener;", "rv", "Landroidx/recyclerview/widget/RecyclerView;", "getImplLayoutId", "", "onCreate", "", PLVRxEncryptDataFunction.SET_DATA_METHOD, "data", "setOnPopupMasteredClickListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "OnPopupMasteredClickListener", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class PopupBottomMastered extends BottomPopupView {
    private AdapterMastered mAdapter;

    @Nullable
    private List<HeartDetail> mData;

    @Nullable
    private OnPopupMasteredClickListener mListener;
    private RecyclerView rv;

    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&¨\u0006\b"}, d2 = {"Lcom/yddmi/doctor/pages/heartlung/PopupBottomMastered$OnPopupMasteredClickListener;", "", "onClick", "", "m", "Lcom/yddmi/doctor/entity/result/HeartDetail;", "index", "", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnPopupMasteredClickListener {
        void onClick(@NotNull HeartDetail m2, int index);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PopupBottomMastered(@NonNull @NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.garbled_heartlung_popup_mastered;
    }

    @Nullable
    public final List<HeartDetail> getMData() {
        return this.mData;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        View viewFindViewById = findViewById(R.id.rv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.rv)");
        this.rv = (RecyclerView) viewFindViewById;
        AdapterMastered adapterMastered = null;
        this.mAdapter = new AdapterMastered(false, 1, null);
        RecyclerView recyclerView = this.rv;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rv");
            recyclerView = null;
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        linearLayoutManager.setOrientation(1);
        recyclerView.setLayoutManager(linearLayoutManager);
        AdapterMastered adapterMastered2 = this.mAdapter;
        if (adapterMastered2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
            adapterMastered2 = null;
        }
        recyclerView.setAdapter(adapterMastered2);
        AdapterMastered adapterMastered3 = this.mAdapter;
        if (adapterMastered3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
            adapterMastered3 = null;
        }
        adapterMastered3.setOnItemClickListener(new Function2<HeartDetail, Integer, Unit>() { // from class: com.yddmi.doctor.pages.heartlung.PopupBottomMastered.onCreate.2
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(HeartDetail heartDetail, Integer num) {
                invoke(heartDetail, num.intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(@NotNull HeartDetail m2, int i2) {
                Intrinsics.checkNotNullParameter(m2, "m");
                OnPopupMasteredClickListener onPopupMasteredClickListener = PopupBottomMastered.this.mListener;
                if (onPopupMasteredClickListener != null) {
                    onPopupMasteredClickListener.onClick(m2, i2);
                }
            }
        });
        AdapterMastered adapterMastered4 = this.mAdapter;
        if (adapterMastered4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
        } else {
            adapterMastered = adapterMastered4;
        }
        adapterMastered.set(this.mData);
    }

    @NotNull
    public final PopupBottomMastered setData(@Nullable List<HeartDetail> data) {
        this.mData = data;
        return this;
    }

    public final void setMData(@Nullable List<HeartDetail> list) {
        this.mData = list;
    }

    public final void setOnPopupMasteredClickListener(@NotNull OnPopupMasteredClickListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mListener = listener;
    }
}
