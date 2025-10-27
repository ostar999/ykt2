package com.yddmi.doctor.widget;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.catchpig.mvvm.ext.ContextExtKt;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.catchpig.mvvm.widget.BubbleAttachDirectionPopupView;
import com.catchpig.utils.manager.ContextManager;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.lxj.xpopup.util.XPopupUtils;
import com.yddmi.doctor.R;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u000e\u0018\u00002\u00020\u0001:\u0001&B\u000f\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0017\u001a\u00020\nH\u0014J\b\u0010\u0018\u001a\u00020\u0019H\u0014J<\u0010\u001a\u001a\u00020\u00002\u0006\u0010\u001b\u001a\u00020\n2\u000e\u0010\u001c\u001a\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010\r2\b\b\u0003\u0010\u001d\u001a\u00020\n2\b\b\u0003\u0010\u001e\u001a\u00020\n2\b\b\u0002\u0010\u001f\u001a\u00020\nJ\u000e\u0010 \u001a\u00020\u00192\u0006\u0010!\u001a\u00020\u0010J\u0016\u0010\"\u001a\u00020\u00002\u0006\u0010#\u001a\u00020\n2\u0006\u0010$\u001a\u00020\nJ\b\u0010%\u001a\u00020\u0019H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082.¢\u0006\u0002\n\u0000¨\u0006'"}, d2 = {"Lcom/yddmi/doctor/widget/PopupListAttach;", "Lcom/catchpig/mvvm/widget/BubbleAttachDirectionPopupView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "fl", "Landroid/widget/FrameLayout;", "mAdapter", "Lcom/yddmi/doctor/widget/PopupListAttachAdapter;", "mH", "", "mLastIndex", "mList", "", "", "mListener", "Lcom/yddmi/doctor/widget/PopupListAttach$OnPopupAttachClickListener;", "mMode", "mNomalColorInt", "mSelectColorInt", "mW", "rv", "Landroidx/recyclerview/widget/RecyclerView;", "getImplLayoutId", "onCreate", "", "setDataGo", "lastIndex", "data", "selectColor", "nomalColor", "mode", "setOnPopupAttachClickListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "setWh", "w", "h", "viewTypeShow", "OnPopupAttachClickListener", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class PopupListAttach extends BubbleAttachDirectionPopupView {
    private FrameLayout fl;
    private PopupListAttachAdapter mAdapter;
    private int mH;
    private int mLastIndex;

    @Nullable
    private List<String> mList;

    @Nullable
    private OnPopupAttachClickListener mListener;
    private int mMode;
    private int mNomalColorInt;
    private int mSelectColorInt;
    private int mW;
    private RecyclerView rv;

    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&¨\u0006\b"}, d2 = {"Lcom/yddmi/doctor/widget/PopupListAttach$OnPopupAttachClickListener;", "", "onPopupAttachClick", "", "str", "", "index", "", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnPopupAttachClickListener {
        void onPopupAttachClick(@NotNull String str, int index);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PopupListAttach(@NonNull @NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        ContextManager.Companion companion = ContextManager.INSTANCE;
        this.mSelectColorInt = ContextExtKt.getColorM(companion.getInstance().getContext(), R.color.c_006cff);
        this.mNomalColorInt = ContextExtKt.getColorM(companion.getInstance().getContext(), R.color.color_333);
        this.mMode = 100;
        this.mW = -1;
        this.mH = -2;
    }

    public static /* synthetic */ PopupListAttach setDataGo$default(PopupListAttach popupListAttach, int i2, List list, int i3, int i4, int i5, int i6, Object obj) {
        int i7 = (i6 & 4) != 0 ? 0 : i3;
        int i8 = (i6 & 8) != 0 ? 0 : i4;
        if ((i6 & 16) != 0) {
            i5 = 100;
        }
        return popupListAttach.setDataGo(i2, list, i7, i8, i5);
    }

    private final void viewTypeShow() {
        if (this.rv != null) {
            PopupListAttachAdapter popupListAttachAdapter = this.mAdapter;
            PopupListAttachAdapter popupListAttachAdapter2 = null;
            if (popupListAttachAdapter == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
                popupListAttachAdapter = null;
            }
            popupListAttachAdapter.setCurrentIndex(this.mLastIndex, this.mSelectColorInt, this.mNomalColorInt);
            PopupListAttachAdapter popupListAttachAdapter3 = this.mAdapter;
            if (popupListAttachAdapter3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
            } else {
                popupListAttachAdapter2 = popupListAttachAdapter3;
            }
            popupListAttachAdapter2.set(this.mList);
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.popup_list_attach;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        setBubbleBgColor(ContextExtKt.getColorM(context, R.color.color_white));
        setArrowWidth(XPopupUtils.dp2px(getContext(), 8.0f));
        setArrowHeight(XPopupUtils.dp2px(getContext(), 9.0f));
        setMode(this.mMode);
        View viewFindViewById = findViewById(R.id.fl);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.fl)");
        this.fl = (FrameLayout) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.rv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.rv)");
        this.rv = (RecyclerView) viewFindViewById2;
        PopupListAttachAdapter popupListAttachAdapter = new PopupListAttachAdapter();
        this.mAdapter = popupListAttachAdapter;
        popupListAttachAdapter.setOnItemClickListener(new Function2<String, Integer, Unit>() { // from class: com.yddmi.doctor.widget.PopupListAttach.onCreate.1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(String str, Integer num) {
                invoke(str, num.intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(@NotNull String m2, int i2) {
                Intrinsics.checkNotNullParameter(m2, "m");
                OnPopupAttachClickListener onPopupAttachClickListener = PopupListAttach.this.mListener;
                if (onPopupAttachClickListener != null) {
                    onPopupAttachClickListener.onPopupAttachClick(m2, i2);
                }
            }
        });
        RecyclerView recyclerView = this.rv;
        PopupListAttachAdapter popupListAttachAdapter2 = null;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rv");
            recyclerView = null;
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        linearLayoutManager.setOrientation(1);
        recyclerView.setLayoutManager(linearLayoutManager);
        PopupListAttachAdapter popupListAttachAdapter3 = this.mAdapter;
        if (popupListAttachAdapter3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
        } else {
            popupListAttachAdapter2 = popupListAttachAdapter3;
        }
        recyclerView.setAdapter(popupListAttachAdapter2);
        setWh(this.mW, this.mH);
        viewTypeShow();
    }

    @NotNull
    public final PopupListAttach setDataGo(int lastIndex, @Nullable List<String> data, @ColorInt int selectColor, @ColorInt int nomalColor, int mode) {
        this.mList = data;
        this.mLastIndex = lastIndex;
        if (selectColor != 0) {
            this.mSelectColorInt = selectColor;
        }
        if (nomalColor != 0) {
            this.mNomalColorInt = nomalColor;
        }
        this.mMode = mode;
        viewTypeShow();
        return this;
    }

    public final void setOnPopupAttachClickListener(@NotNull OnPopupAttachClickListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mListener = listener;
    }

    @NotNull
    public final PopupListAttach setWh(int w2, int h2) {
        this.mW = w2;
        this.mH = h2;
        FrameLayout frameLayout = this.fl;
        if (frameLayout != null) {
            if (frameLayout == null) {
                Intrinsics.throwUninitializedPropertyAccessException("fl");
                frameLayout = null;
            }
            ViewExtKt.setWh(frameLayout, this.mW, this.mH);
        }
        return this;
    }
}
