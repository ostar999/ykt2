package com.psychiatrygarden.activity.answer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.psychiatrygarden.activity.CommentReplyActivity;
import com.psychiatrygarden.activity.answer.bean.AnalysisBean;
import com.psychiatrygarden.activity.answer.compose.adapter.GridImageAdapter;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.FullyGridLayoutManager;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.CircleImageView;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.yikaobang.yixue.R;
import java.util.List;
import java.util.Locale;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.eclipse.jetty.servlet.ServletHandler;

/* loaded from: classes5.dex */
public class AnalysisAdapter extends BaseMultiItemQuickAdapter<AnalysisBean.DataBean, BaseViewHolder> {
    private AnaImClickIml mAnaImClickIml;
    private TextView tv_oppose;
    private TextView tv_reply;
    private TextView tv_support;

    public interface AnaImClickIml {
        void gotoAnalysisView();

        void showToastView(View v2);
    }

    public AnalysisAdapter(@Nullable List<AnalysisBean.DataBean> data) {
        super(data);
        addItemType(0, R.layout.layout_ansn_title_item);
        addItemType(1, R.layout.layout_ansn_item);
        addItemType(2, R.layout.layout_ansn_all_title_item);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$covAnsAllTitleItemView$0(View view) {
        this.mAnaImClickIml.showToastView(view);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$covAnsItmeView$2(View view) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$covAnsItmeView$3(AnalysisBean.DataBean dataBean, View view) {
        if (dataBean.getIs_opposed().equals("1")) {
            return;
        }
        if (dataBean.getIs_agree().equals("1")) {
            putPraise(dataBean.getId(), dataBean.getQuestion_id());
            dataBean.setIs_agree("0");
            try {
                if (dataBean.getAgree_num() > 0) {
                    dataBean.setAgree_num(dataBean.getAgree_num() - 1);
                }
                this.tv_support.setText(String.format(Locale.CHINA, "赞同(%d)", Integer.valueOf(dataBean.getAgree_num())));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } else {
            CommonUtil.Toast_pop(this.tv_support, 0);
            putPraise(dataBean.getId(), dataBean.getQuestion_id());
            dataBean.setIs_agree("1");
            try {
                dataBean.setAgree_num(dataBean.getAgree_num() + 1);
                this.tv_support.setText(String.format(Locale.CHINA, "已赞同(%d)", Integer.valueOf(dataBean.getAgree_num())));
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        initSupport(dataBean);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$covAnsItmeView$4(AnalysisBean.DataBean dataBean, View view) {
        if (dataBean.getIs_agree().equals("1")) {
            return;
        }
        if (dataBean.getIs_opposed().equals("1")) {
            putOppose(dataBean.getId(), dataBean.getQuestion_id());
            dataBean.setIs_opposed("0");
            try {
                if (dataBean.getOpposed_num() > 0) {
                    dataBean.setOpposed_num(dataBean.getOpposed_num() - 1);
                }
                this.tv_oppose.setText(String.format(Locale.CHINA, "反对(%d)", Integer.valueOf(dataBean.getOpposed_num())));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } else {
            CommonUtil.Toast_pop(this.tv_oppose, 1);
            putOppose(dataBean.getId(), dataBean.getQuestion_id());
            dataBean.setIs_opposed("1");
            try {
                dataBean.setOpposed_num(dataBean.getOpposed_num() + 1);
                this.tv_oppose.setText(String.format(Locale.CHINA, "已反对(%d)", Integer.valueOf(dataBean.getOpposed_num())));
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        initSupport(dataBean);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$covAnsTitleItemView$1(View view) {
        this.mAnaImClickIml.gotoAnalysisView();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initSupport$5(AnalysisBean.DataBean dataBean, View view) {
        mcommActivity(dataBean);
    }

    public void covAnsAllTitleItemView(BaseViewHolder helper, AnalysisBean.DataBean mAnalysisBean) {
        TextView textView = (TextView) helper.getView(R.id.paixutv);
        TextView textView2 = (TextView) helper.getView(R.id.anstotal);
        if (ServletHandler.__DEFAULT_SERVLET.equals(mAnalysisBean.getTypeStr())) {
            textView.setText("默认排序");
        } else if ("hot".equals(mAnalysisBean.getTypeStr())) {
            textView.setText("热度排序");
        } else if (CrashHianalyticsData.TIME.equals(mAnalysisBean.getTypeStr())) {
            textView.setText("时间排序");
        } else if ("my_agree".equals(mAnalysisBean.getTypeStr())) {
            textView.setText("我赞同的");
        }
        float intConfig = (SharePreferencesUtils.readIntConfig(CommonParameter.QUESTION_FONT_SIZE, getContext(), 2) - 2) * ScreenUtil.getPxBySp(getContext(), 2);
        textView.setTextSize(0, textView.getTextSize() - intConfig);
        textView2.setTextSize(0, textView2.getTextSize() - intConfig);
        textView.setCompoundDrawablePadding(ScreenUtil.getPxByDp(getContext(), 4));
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.arrow_down_title_icon);
        drawable.setBounds(0, 0, ScreenUtil.getPxByDp(getContext(), 9), ScreenUtil.getPxByDp(getContext(), 5));
        textView.setCompoundDrawables(null, null, drawable, null);
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.adapter.a
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11003c.lambda$covAnsAllTitleItemView$0(view);
            }
        });
    }

    public void covAnsItmeView(BaseViewHolder helper, final AnalysisBean.DataBean mAnalysisBean) {
        RelativeLayout relativeLayout = (RelativeLayout) helper.getView(R.id.relView);
        LinearLayout linearLayout = (LinearLayout) helper.getView(R.id.itemviewid);
        CircleImageView circleImageView = (CircleImageView) helper.getView(R.id.header);
        TextView textView = (TextView) helper.getView(R.id.name);
        this.tv_support = (TextView) helper.getView(R.id.tv_support);
        this.tv_oppose = (TextView) helper.getView(R.id.tv_oppose);
        this.tv_reply = (TextView) helper.getView(R.id.tv_reply);
        LinearLayout linearLayout2 = (LinearLayout) helper.getView(R.id.llay_support);
        TextView textView2 = (TextView) helper.getView(R.id.anacontentview);
        RecyclerView recyclerView = (RecyclerView) helper.getView(R.id.recyimg);
        float intConfig = (SharePreferencesUtils.readIntConfig(CommonParameter.QUESTION_FONT_SIZE, getContext(), 2) - 2) * ScreenUtil.getPxBySp(getContext(), 2);
        textView.setTextSize(0, textView.getTextSize() - intConfig);
        textView2.setTextSize(0, textView2.getTextSize() - intConfig);
        TextView textView3 = this.tv_support;
        textView3.setTextSize(0, textView3.getTextSize() - intConfig);
        TextView textView4 = this.tv_oppose;
        textView4.setTextSize(0, textView4.getTextSize() - intConfig);
        TextView textView5 = this.tv_reply;
        textView5.setTextSize(0, textView5.getTextSize() - intConfig);
        if (helper.getAdapterPosition() == 1) {
            relativeLayout.setVisibility(8);
        } else {
            relativeLayout.setVisibility(0);
            GlideApp.with(circleImageView.getContext()).load((Object) GlideUtils.generateUrl(mAnalysisBean.getAvatar())).into(circleImageView);
            textView.setText(mAnalysisBean.getNickname());
            try {
                textView.setTextColor(Color.parseColor(mAnalysisBean.getIdentity().getUser_identity_color()));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.adapter.c
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AnalysisAdapter.lambda$covAnsItmeView$2(view);
            }
        });
        recyclerView.setLayoutManager(new FullyGridLayoutManager(getContext(), 3, 1, false));
        if (mAnalysisBean == null || mAnalysisBean.getId() == null) {
            return;
        }
        textView2.setText(mAnalysisBean.getAnalysis());
        if (mAnalysisBean.getImages() == null || mAnalysisBean.getImages().size() <= 0) {
            recyclerView.setVisibility(8);
        } else {
            recyclerView.setVisibility(0);
            recyclerView.setAdapter(new GridImageAdapter(mAnalysisBean.getImages(), mAnalysisBean.getImages().size(), null));
        }
        if ("1".equals(mAnalysisBean.getIs_hidden())) {
            linearLayout2.setVisibility(8);
        } else {
            linearLayout2.setVisibility(8);
        }
        initSupport(mAnalysisBean);
        this.tv_support.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.adapter.d
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11005c.lambda$covAnsItmeView$3(mAnalysisBean, view);
            }
        });
        this.tv_oppose.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.adapter.e
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11007c.lambda$covAnsItmeView$4(mAnalysisBean, view);
            }
        });
    }

    public void covAnsTitleItemView(BaseViewHolder helper, AnalysisBean.DataBean mAnalysisBean) {
        TextView textView = (TextView) helper.getView(R.id.editView);
        TextView textView2 = (TextView) helper.getView(R.id.nitv);
        float intConfig = (SharePreferencesUtils.readIntConfig(CommonParameter.QUESTION_FONT_SIZE, getContext(), 2) - 2) * ScreenUtil.getPxBySp(getContext(), 2);
        textView.setTextSize(0, textView.getTextSize() - intConfig);
        textView2.setTextSize(0, textView2.getTextSize() - intConfig);
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.adapter.b
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11004c.lambda$covAnsTitleItemView$1(view);
            }
        });
    }

    public void initSupport(final AnalysisBean.DataBean mAnalysisBean) {
        int color;
        int color2;
        if (mAnalysisBean == null) {
            return;
        }
        int agree_num = mAnalysisBean.getAgree_num();
        int opposed_num = mAnalysisBean.getOpposed_num();
        int i2 = R.color.gray_light;
        if (agree_num > opposed_num) {
            color = ContextCompat.getColor(getContext(), SkinManager.getCurrentSkinType(getContext()) == 0 ? R.color.green : R.color.green_theme_night);
            Context context = getContext();
            if (SkinManager.getCurrentSkinType(getContext()) != 0) {
                i2 = R.color.jiucuo_night;
            }
            color2 = ContextCompat.getColor(context, i2);
        } else if (mAnalysisBean.getAgree_num() < mAnalysisBean.getOpposed_num()) {
            Context context2 = getContext();
            if (SkinManager.getCurrentSkinType(getContext()) != 0) {
                i2 = R.color.jiucuo_night;
            }
            color = ContextCompat.getColor(context2, i2);
            color2 = ContextCompat.getColor(getContext(), SkinManager.getCurrentSkinType(getContext()) == 0 ? R.color.app_theme_red : R.color.red_theme_night);
        } else {
            color = ContextCompat.getColor(getContext(), SkinManager.getCurrentSkinType(getContext()) == 0 ? R.color.gray_light : R.color.jiucuo_night);
            Context context3 = getContext();
            if (SkinManager.getCurrentSkinType(getContext()) != 0) {
                i2 = R.color.jiucuo_night;
            }
            color2 = ContextCompat.getColor(context3, i2);
        }
        this.tv_support.setTextColor(color);
        this.tv_oppose.setTextColor(color2);
        this.tv_support.setText(String.format(Locale.CHINA, TextUtils.equals(mAnalysisBean.getIs_agree(), "1") ? "已赞同(%d)" : "赞同(%d)", Integer.valueOf(mAnalysisBean.getAgree_num())));
        this.tv_oppose.setText(String.format(Locale.CHINA, TextUtils.equals(mAnalysisBean.getIs_opposed(), "1") ? "已反对(%d)" : "反对(%d)", Integer.valueOf(mAnalysisBean.getOpposed_num())));
        if (SkinManager.getCurrentSkinType(getContext()) == 0) {
            this.tv_reply.setTextColor(getContext().getResources().getColor(R.color.gray_font));
            this.tv_reply.setBackgroundResource(R.drawable.gray_round_bg);
        } else {
            this.tv_reply.setTextColor(getContext().getResources().getColor(R.color.question_color_night));
            this.tv_reply.setBackgroundResource(R.drawable.gray_round_bg_night);
        }
        this.tv_reply.setText(String.format(Locale.CHINA, "%d 回复", Integer.valueOf(mAnalysisBean.getComment_number())));
        this.tv_reply.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.adapter.f
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11009c.lambda$initSupport$5(mAnalysisBean, view);
            }
        });
    }

    public void mcommActivity(AnalysisBean.DataBean mAnalysisBean) {
        Intent intent = new Intent(getContext(), (Class<?>) CommentReplyActivity.class);
        intent.putExtra("is_replybean", false);
        intent.putExtra("comment_type", "2");
        intent.putExtra("module_type", 23);
        intent.putExtra("bean", mAnalysisBean);
        intent.putExtra("isVisiable", true);
        getContext().startActivity(intent);
    }

    public void putOppose(String id, String question_id) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("analysis_id", id);
        ajaxParams.put("question_id", question_id + "");
        YJYHttpUtils.post(getContext(), NetworkRequestsURL.qopposeApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.answer.adapter.AnalysisAdapter.1
        });
    }

    public void putPraise(String id, String question_id) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", question_id + "");
        ajaxParams.put("analysis_id", id);
        YJYHttpUtils.post(getContext(), NetworkRequestsURL.qagreeApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.answer.adapter.AnalysisAdapter.2
        });
    }

    public AnalysisAdapter setmAnaImClickIml(AnaImClickIml mAnaImClickIml) {
        this.mAnaImClickIml = mAnaImClickIml;
        return this;
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder helper, AnalysisBean.DataBean item) {
        int itemViewType = helper.getItemViewType();
        if (itemViewType == 0) {
            covAnsTitleItemView(helper, item);
        } else if (itemViewType == 1) {
            covAnsItmeView(helper, item);
        } else {
            if (itemViewType != 2) {
                return;
            }
            covAnsAllTitleItemView(helper, item);
        }
    }
}
