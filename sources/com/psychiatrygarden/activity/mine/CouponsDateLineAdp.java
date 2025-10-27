package com.psychiatrygarden.activity.mine;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.bean.RedEnvelopeCouponsBean;
import com.psychiatrygarden.utils.LogUtils;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes5.dex */
public class CouponsDateLineAdp extends BaseQuickAdapter<RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem, BaseViewHolder> {
    private OnItemActionLisenter onItemActionLisenter;

    public static abstract class OnItemActionLisenter {
        public abstract void btnToUs(RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem item);
    }

    public CouponsDateLineAdp() {
        super(R.layout.item_coupons_time);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$0(RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem redEnvelopeCouponsDataItem, View view) {
        OnItemActionLisenter onItemActionLisenter = this.onItemActionLisenter;
        if (onItemActionLisenter != null) {
            onItemActionLisenter.btnToUs(redEnvelopeCouponsDataItem);
        }
    }

    public void setOnItemActionLisenter(OnItemActionLisenter lisenter) {
        this.onItemActionLisenter = lisenter;
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public /* bridge */ /* synthetic */ void convert(@NonNull BaseViewHolder holder, RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem item, @NonNull List payloads) {
        convert2(holder, item, (List<?>) payloads);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder helper, final RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem item) {
        String strValueOf;
        String strValueOf2;
        String strValueOf3;
        TextView textView = (TextView) helper.getView(R.id.tv_coupons_name);
        TextView textView2 = (TextView) helper.getView(R.id.tv_time);
        TextView textView3 = (TextView) helper.getView(R.id.btn_use);
        textView.setText(item.getTitle());
        long j2 = (!TextUtils.isEmpty(item.getCoupon_end()) ? Long.parseLong(item.getCoupon_end()) : 0L) - (System.currentTimeMillis() / 1000);
        int i2 = (int) (j2 / 3600);
        int i3 = (int) ((j2 / 60) % 60);
        int i4 = (int) (j2 % 60);
        if (i2 < 10) {
            strValueOf = "0" + i2;
        } else {
            strValueOf = String.valueOf(i2);
        }
        if (i3 < 10) {
            strValueOf2 = "0" + i3;
        } else {
            strValueOf2 = String.valueOf(i3);
        }
        if (i4 < 10) {
            strValueOf3 = "0" + i4;
        } else {
            strValueOf3 = String.valueOf(i4);
        }
        textView2.setText("倒计时  " + strValueOf + "：" + strValueOf2 + "：" + strValueOf3);
        textView3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.b
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12775c.lambda$convert$0(item, view);
            }
        });
    }

    /* renamed from: convert, reason: avoid collision after fix types in other method */
    public void convert2(@NonNull BaseViewHolder holder, RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem item, @NonNull List<?> payloads) {
        String strValueOf;
        String strValueOf2;
        String strValueOf3;
        super.convert((CouponsDateLineAdp) holder, (BaseViewHolder) item, (List<? extends Object>) payloads);
        LogUtils.e("person_recycler_vis", "payloadspayloadspayloadspayloadspayloads");
        if (payloads == null || ((Integer) payloads.get(0)).intValue() != 1) {
            return;
        }
        TextView textView = (TextView) holder.getView(R.id.tv_time);
        long j2 = (!TextUtils.isEmpty(item.getCoupon_end()) ? Long.parseLong(item.getCoupon_end()) : 0L) - (System.currentTimeMillis() / 1000);
        int i2 = (int) (j2 / 3600);
        int i3 = (int) ((j2 / 60) % 60);
        int i4 = (int) (j2 % 60);
        if (i2 < 10) {
            strValueOf = "0" + i2;
        } else {
            strValueOf = String.valueOf(i2);
        }
        if (i3 < 10) {
            strValueOf2 = "0" + i3;
        } else {
            strValueOf2 = String.valueOf(i3);
        }
        if (i4 < 10) {
            strValueOf3 = "0" + i4;
        } else {
            strValueOf3 = String.valueOf(i4);
        }
        textView.setText("倒计时  " + strValueOf + "：" + strValueOf2 + "：" + strValueOf3);
    }
}
