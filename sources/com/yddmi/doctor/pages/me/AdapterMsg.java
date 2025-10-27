package com.yddmi.doctor.pages.me;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import com.catchpig.mvvm.base.adapter.CommonViewHolder;
import com.catchpig.mvvm.base.adapter.RecyclerAdapter;
import com.psychiatrygarden.utils.CommonParameter;
import com.yddmi.doctor.R;
import com.yddmi.doctor.databinding.MeItemMsgBinding;
import com.yddmi.doctor.entity.result.HomeMsg;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0010\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0004\u0012\u00020\u00030\u0001B!\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007¢\u0006\u0002\u0010\tJ(\u0010\u0017\u001a\u00020\u00182\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00030\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u001c\u001a\u00020\u0005H\u0016J\u0010\u0010\u001d\u001a\u00020\u00032\u0006\u0010\u001e\u001a\u00020\u001fH\u0016R\u001a\u0010\n\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\f\"\u0004\b\u0011\u0010\u000eR\u001a\u0010\u0012\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016¨\u0006 "}, d2 = {"Lcom/yddmi/doctor/pages/me/AdapterMsg;", "Lcom/catchpig/mvvm/base/adapter/RecyclerAdapter;", "Lcom/yddmi/doctor/entity/result/HomeMsg;", "Lcom/yddmi/doctor/databinding/MeItemMsgBinding;", "type", "", "noDot", "", CommonParameter.notice, "(IZZ)V", "mNoDot", "getMNoDot", "()Z", "setMNoDot", "(Z)V", "mNotice", "getMNotice", "setMNotice", "mType", "getMType", "()I", "setMType", "(I)V", "bindViewHolder", "", "holder", "Lcom/catchpig/mvvm/base/adapter/CommonViewHolder;", "m", "position", "viewBinding", "parent", "Landroid/view/ViewGroup;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class AdapterMsg extends RecyclerAdapter<HomeMsg, MeItemMsgBinding> {
    private boolean mNoDot;
    private boolean mNotice;
    private int mType;

    public /* synthetic */ AdapterMsg(int i2, boolean z2, boolean z3, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i2, (i3 & 2) != 0 ? false : z2, (i3 & 4) != 0 ? false : z3);
    }

    @Override // com.catchpig.mvvm.base.adapter.RecyclerAdapter
    public /* bridge */ /* synthetic */ void bindViewHolder(CommonViewHolder commonViewHolder, HomeMsg homeMsg, int i2) {
        bindViewHolder2((CommonViewHolder<MeItemMsgBinding>) commonViewHolder, homeMsg, i2);
    }

    public final boolean getMNoDot() {
        return this.mNoDot;
    }

    public final boolean getMNotice() {
        return this.mNotice;
    }

    public final int getMType() {
        return this.mType;
    }

    public final void setMNoDot(boolean z2) {
        this.mNoDot = z2;
    }

    public final void setMNotice(boolean z2) {
        this.mNotice = z2;
    }

    public final void setMType(int i2) {
        this.mType = i2;
    }

    public AdapterMsg(int i2, boolean z2, boolean z3) {
        this.mType = i2;
        this.mNoDot = z2;
        this.mNotice = z3;
    }

    /* renamed from: bindViewHolder, reason: avoid collision after fix types in other method */
    public void bindViewHolder2(@NotNull CommonViewHolder<MeItemMsgBinding> holder, @Nullable final HomeMsg m2, int position) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        holder.viewBanding(new Function1<MeItemMsgBinding, Unit>() { // from class: com.yddmi.doctor.pages.me.AdapterMsg.bindViewHolder.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(MeItemMsgBinding meItemMsgBinding) {
                invoke2(meItemMsgBinding);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull MeItemMsgBinding viewBanding) {
                Intrinsics.checkNotNullParameter(viewBanding, "$this$viewBanding");
                HomeMsg homeMsg = m2;
                if (homeMsg != null) {
                    AdapterMsg adapterMsg = this;
                    TextView textView = viewBanding.timeTv;
                    String createTime = homeMsg.getCreateTime();
                    if (createTime.length() == 0) {
                        createTime = homeMsg.getReleaseTime();
                    }
                    textView.setText(createTime);
                    if (adapterMsg.getMNotice()) {
                        TextView textView2 = viewBanding.titleTv;
                        String typeName = homeMsg.getTypeName();
                        if (typeName == null) {
                            typeName = "";
                        }
                        textView2.setText(typeName);
                    } else {
                        TextView textView3 = viewBanding.titleTv;
                        String name = homeMsg.getName();
                        textView3.setText(name == null || name.length() == 0 ? homeMsg.getTitle() : homeMsg.getName());
                    }
                    viewBanding.detailTv.setText(homeMsg.getContent());
                    if (adapterMsg.getMNoDot() || 1 == homeMsg.isRead()) {
                        viewBanding.dotV.setVisibility(4);
                    } else {
                        viewBanding.dotV.setVisibility(0);
                    }
                    switch (homeMsg.getType()) {
                        case 1:
                            viewBanding.imgv.setImageResource(R.drawable.me_msg_icon);
                            break;
                        case 2:
                            viewBanding.imgv.setImageResource(R.drawable.me_msg_icon);
                            break;
                        case 3:
                            viewBanding.imgv.setImageResource(R.drawable.me_msg_icon);
                            break;
                        case 4:
                            viewBanding.imgv.setImageResource(R.drawable.me_msg_icon);
                            break;
                        case 5:
                            viewBanding.imgv.setImageResource(R.drawable.me_msg_icon5);
                            break;
                        case 6:
                            viewBanding.imgv.setImageResource(R.drawable.me_msg_icon6);
                            break;
                        case 7:
                            viewBanding.imgv.setImageResource(R.drawable.me_msg_icon7);
                            break;
                        default:
                            viewBanding.imgv.setImageResource(R.drawable.me_msg_icon);
                            break;
                    }
                }
            }
        });
    }

    @Override // com.catchpig.mvvm.base.adapter.RecyclerAdapter
    @NotNull
    public MeItemMsgBinding viewBinding(@NotNull ViewGroup parent) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        MeItemMsgBinding meItemMsgBindingInflate = MeItemMsgBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        Intrinsics.checkNotNullExpressionValue(meItemMsgBindingInflate, "inflate(LayoutInflater.f….context), parent, false)");
        return meItemMsgBindingInflate;
    }
}
