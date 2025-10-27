package com.psychiatrygarden.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.psychiatrygarden.activity.LoginActivity;
import com.psychiatrygarden.activity.MoreTiDanActivity;
import com.psychiatrygarden.activity.QuestionSetListActivity;
import com.psychiatrygarden.bean.QuestionSetListBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes5.dex */
public class QuestionSheetListAdapter extends BaseQuickAdapter<QuestionSetListBean.DataBeanX, BaseViewHolder> {
    private boolean isMore;
    private boolean isTiDan;
    public OnItemChildClickListenr onItemChildClickListenr;

    public interface OnItemChildClickListenr {
        void onItemChildClickMethod(QuestionSetListBean.DataBeanX dataBean, int position);
    }

    public QuestionSheetListAdapter(@Nullable List<QuestionSetListBean.DataBeanX> data) {
        super(R.layout.layout_setitem, data);
        this.isMore = false;
        this.isTiDan = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void anim(Context context, ImageView imageView) {
        if (SkinManager.getCurrentSkinType(context) == 1) {
            imageView.setBackgroundResource(R.drawable.live_calendar_living_animation_night);
        } else {
            imageView.setBackgroundResource(R.drawable.live_calendar_living_animation);
        }
        ((AnimationDrawable) imageView.getBackground()).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$0(Context context, QuestionSetListBean.DataBeanX dataBeanX, View view) {
        MoreTiDanActivity.gotoMoreTiDanActivity(context, dataBeanX.getC_id(), this.isTiDan ? QuestionSetListActivity.TYPE_VALUE_TI_DAN : QuestionSetListActivity.TYPE_VALUE_MO_KAO, dataBeanX.getLabel());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$1(QuestionSetListBean.DataBeanX dataBeanX, BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        OnItemChildClickListenr onItemChildClickListenr;
        if (CommonUtil.isFastClick() || (onItemChildClickListenr = this.onItemChildClickListenr) == null) {
            return;
        }
        onItemChildClickListenr.onItemChildClickMethod(dataBeanX, i2);
    }

    public boolean isLogin(Context mContext) {
        if (!UserConfig.getUserId().equals("")) {
            return true;
        }
        mContext.startActivity(new Intent(mContext, (Class<?>) LoginActivity.class));
        return false;
    }

    public void setOnItemChildClickListenr(OnItemChildClickListenr onItemChildClickListenr) {
        this.onItemChildClickListenr = onItemChildClickListenr;
    }

    public void setTiDan(boolean tiDan) {
        this.isTiDan = tiDan;
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder vHolder, final QuestionSetListBean.DataBeanX dataBean) {
        final Context context = getContext();
        TextView textView = (TextView) vHolder.getView(R.id.uploadtext);
        RelativeLayout relativeLayout = (RelativeLayout) vHolder.getView(R.id.relview);
        TextView textView2 = (TextView) vHolder.getView(R.id.uploadjumtxt);
        RecyclerView recyclerView = (RecyclerView) vHolder.getView(R.id.gridview);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        if ("0".equals(dataBean.getMore())) {
            textView2.setVisibility(8);
        } else {
            textView2.setVisibility(0);
        }
        if (this.isMore) {
            relativeLayout.setVisibility(8);
        } else {
            relativeLayout.setVisibility(0);
        }
        Drawable drawable = SkinManager.getCurrentSkinType(context) == 0 ? context.getResources().getDrawable(R.drawable.icon_indicator_right) : context.getResources().getDrawable(R.drawable.icon_indicator_right_night);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView2.setText("更多题单");
        textView2.setCompoundDrawables(null, null, drawable, null);
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.me
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14773c.lambda$convert$0(context, dataBean, view);
            }
        });
        textView.setText(dataBean.getLabel());
        BaseQuickAdapter<QuestionSetListBean.DataBeanX.DataBean, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<QuestionSetListBean.DataBeanX.DataBean, BaseViewHolder>(R.layout.layout_setchilditem, dataBean.getData()) { // from class: com.psychiatrygarden.adapter.QuestionSheetListAdapter.1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, QuestionSetListBean.DataBeanX.DataBean dataBean2) {
                LinearLayout linearLayout = (LinearLayout) helper.getView(R.id.layoutLivingStatus);
                ImageView imageView = (ImageView) helper.getView(R.id.liveCalendarAnim);
                String is_live_broadcast = dataBean2.getIs_live_broadcast();
                TextView textView3 = (TextView) helper.getView(R.id.itemtext);
                RoundedImageView roundedImageView = (RoundedImageView) helper.getView(R.id.imgitem);
                roundedImageView.setLayoutParams(new RelativeLayout.LayoutParams((CommonUtil.getScreenWidth(context) - (CommonUtil.dip2px(context, 10.0f) * 4)) / 3, (CommonUtil.getScreenWidth(context) - (CommonUtil.dip2px(context, 10.0f) * 4)) / 3));
                ImageView imageView2 = (ImageView) helper.getView(R.id.fufeijingpin);
                textView3.setText(dataBean2.getTitle());
                GlideUtils.loadImageDef(getContext(), dataBean2.getCover_img(), roundedImageView);
                try {
                    if (dataBean2.getLabel() == null || dataBean2.getLabel().equals("")) {
                        imageView2.setVisibility(8);
                    } else {
                        imageView2.setVisibility(0);
                        GlideApp.with(context).load((Object) GlideUtils.generateUrl(dataBean2.getLabel())).override(Integer.MIN_VALUE).into(imageView2);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                if (!"1".equals(is_live_broadcast)) {
                    linearLayout.setVisibility(8);
                    return;
                }
                QuestionSheetListAdapter.this.anim(context, imageView);
                linearLayout.setVisibility(0);
                imageView2.setVisibility(8);
            }
        };
        recyclerView.setAdapter(baseQuickAdapter);
        baseQuickAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.adapter.ne
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i2) {
                this.f14813c.lambda$convert$1(dataBean, baseQuickAdapter2, view, i2);
            }
        });
    }

    public QuestionSheetListAdapter(@Nullable List<QuestionSetListBean.DataBeanX> data, boolean isMore) {
        super(R.layout.layout_setitem, data);
        this.isTiDan = true;
        this.isMore = isMore;
    }
}
