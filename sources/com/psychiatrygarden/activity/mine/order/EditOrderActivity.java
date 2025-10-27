package com.psychiatrygarden.activity.mine.order;

import android.view.View;
import android.widget.ImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.activity.BaseActivity;
import com.yikaobang.yixue.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u00002\u00020\u00012\u00020\u0002:\u0002\u0014\u0015B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0012\u0010\u000f\u001a\u00020\u000b2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u000bH\u0016J\b\u0010\u0013\u001a\u00020\u000bH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lcom/psychiatrygarden/activity/mine/order/EditOrderActivity;", "Lcom/psychiatrygarden/activity/BaseActivity;", "Landroid/view/View$OnClickListener;", "()V", "adapter", "Lcom/psychiatrygarden/activity/mine/order/EditOrderActivity$EditOrderAddressAdapter;", "imgBack", "Landroid/widget/ImageView;", "recyclerViewEditOrder", "Landroidx/recyclerview/widget/RecyclerView;", "init", "", "onClick", "v", "Landroid/view/View;", "onEventMainThread", "str", "", "setContentView", "setListenerForWidget", "DataAddress", "EditOrderAddressAdapter", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class EditOrderActivity extends BaseActivity implements View.OnClickListener {
    private EditOrderAddressAdapter adapter;
    private ImageView imgBack;
    private RecyclerView recyclerViewEditOrder;

    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lcom/psychiatrygarden/activity/mine/order/EditOrderActivity$DataAddress;", "", "()V", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class DataAddress {
    }

    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0005¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\u0002H\u0014¨\u0006\t"}, d2 = {"Lcom/psychiatrygarden/activity/mine/order/EditOrderActivity$EditOrderAddressAdapter;", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "Lcom/psychiatrygarden/activity/mine/order/EditOrderActivity$DataAddress;", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "()V", "convert", "", "holder", "item", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class EditOrderAddressAdapter extends BaseQuickAdapter<DataAddress, BaseViewHolder> {
        public EditOrderAddressAdapter() {
            super(R.layout.item_edit_order, null, 2, null);
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NotNull BaseViewHolder holder, @NotNull DataAddress item) {
            Intrinsics.checkNotNullParameter(holder, "holder");
            Intrinsics.checkNotNullParameter(item, "item");
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        View viewFindViewById = findViewById(R.id.recyclerViewEditOrder);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.recyclerViewEditOrder)");
        this.recyclerViewEditOrder = (RecyclerView) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.imgBack);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.imgBack)");
        ImageView imageView = (ImageView) viewFindViewById2;
        this.imgBack = imageView;
        EditOrderAddressAdapter editOrderAddressAdapter = null;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imgBack");
            imageView = null;
        }
        imageView.setOnClickListener(this);
        this.adapter = new EditOrderAddressAdapter();
        RecyclerView recyclerView = this.recyclerViewEditOrder;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recyclerViewEditOrder");
            recyclerView = null;
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        RecyclerView recyclerView2 = this.recyclerViewEditOrder;
        if (recyclerView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recyclerViewEditOrder");
            recyclerView2 = null;
        }
        EditOrderAddressAdapter editOrderAddressAdapter2 = this.adapter;
        if (editOrderAddressAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
        } else {
            editOrderAddressAdapter = editOrderAddressAdapter2;
        }
        recyclerView2.setAdapter(editOrderAddressAdapter);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(@NotNull View v2) {
        Intrinsics.checkNotNullParameter(v2, "v");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(@Nullable String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_edit_order);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
