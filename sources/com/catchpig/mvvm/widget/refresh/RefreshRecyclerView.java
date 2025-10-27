package com.catchpig.mvvm.widget.refresh;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import com.catchpig.mvvm.R;
import com.catchpig.mvvm.base.adapter.RecyclerAdapter;
import com.catchpig.utils.ext.LogExtKt;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;
import com.umeng.analytics.pro.d;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\u0002\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\"\u0010\u000b\u001a\u00020\f\"\u0004\b\u0000\u0010\r2\u0014\u0010\u000e\u001a\u0010\u0012\u0004\u0012\u0002H\r\u0012\u0006\b\u0001\u0012\u00020\u00100\u000fJ\u000e\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u0013J\u001c\u0010\u0014\u001a\u00020\f\"\u0004\b\u0000\u0010\r2\u000e\u0010\u0015\u001a\n\u0012\u0004\u0012\u0002H\r\u0018\u00010\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lcom/catchpig/mvvm/widget/refresh/RefreshRecyclerView;", "Lcom/catchpig/mvvm/widget/refresh/RefreshLayoutWrapper;", d.R, "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "recyclerBackground", "", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "setAdapter", "", "M", "adapter", "Lcom/catchpig/mvvm/base/adapter/RecyclerAdapter;", "Landroidx/viewbinding/ViewBinding;", "setLayoutManager", "layoutManage", "Landroidx/recyclerview/widget/RecyclerView$LayoutManager;", "updateData", "data", "", "Companion", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class RefreshRecyclerView extends RefreshLayoutWrapper {

    @NotNull
    private static final String TAG = "RefreshRecyclerView";
    private int recyclerBackground;

    @NotNull
    private final RecyclerView recyclerView;

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[RefreshState.values().length];
            try {
                iArr[RefreshState.Refreshing.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[RefreshState.Loading.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RefreshRecyclerView(@NotNull Context context, @NotNull AttributeSet attrs) {
        super(context, attrs);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(attrs, "attrs");
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.RefreshRecyclerView);
        Intrinsics.checkNotNullExpressionValue(typedArrayObtainStyledAttributes, "context.obtainStyledAttr…able.RefreshRecyclerView)");
        this.recyclerBackground = typedArrayObtainStyledAttributes.getColor(R.styleable.RefreshRecyclerView_recycler_background, 0);
        typedArrayObtainStyledAttributes.recycle();
        RecyclerView recyclerView = new RecyclerView(context, attrs);
        this.recyclerView = recyclerView;
        recyclerView.setLayoutParams(new SmartRefreshLayout.LayoutParams(-1, -1));
        addView(recyclerView);
        recyclerView.setBackgroundColor(this.recyclerBackground);
    }

    public final <M> void setAdapter(@NotNull RecyclerAdapter<M, ? extends ViewBinding> adapter) {
        Intrinsics.checkNotNullParameter(adapter, "adapter");
        this.recyclerView.setAdapter(adapter);
    }

    public final void setLayoutManager(@NotNull RecyclerView.LayoutManager layoutManage) {
        Intrinsics.checkNotNullParameter(layoutManage, "layoutManage");
        this.recyclerView.setLayoutManager(layoutManage);
    }

    public final <M> void updateData(@Nullable List<M> data) {
        RecyclerView.Adapter adapter = this.recyclerView.getAdapter();
        Intrinsics.checkNotNull(adapter, "null cannot be cast to non-null type com.catchpig.mvvm.base.adapter.RecyclerAdapter<M of com.catchpig.mvvm.widget.refresh.RefreshRecyclerView.updateData, out androidx.viewbinding.ViewBinding>");
        RecyclerAdapter recyclerAdapter = (RecyclerAdapter) adapter;
        int i2 = WhenMappings.$EnumSwitchMapping$0[getState().ordinal()];
        if (i2 == 1) {
            recyclerAdapter.set(data);
        } else if (i2 != 2) {
            LogExtKt.logi("state:" + getState(), TAG);
        } else {
            recyclerAdapter.add(data);
        }
        updateSuccess(data);
    }
}
