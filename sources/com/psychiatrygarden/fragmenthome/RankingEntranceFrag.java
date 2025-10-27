package com.psychiatrygarden.fragmenthome;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.Glide;
import com.google.android.material.timepicker.TimeModel;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.purchase.adapter.CommAdapter;
import com.psychiatrygarden.activity.purchase.adapter.ViewHolder;
import com.psychiatrygarden.activity.rank.bean.RankEntranceBean;
import com.psychiatrygarden.activity.rank.pop.ToastPopWindow;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.CircleImageView;
import com.tencent.connect.common.Constants;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* loaded from: classes5.dex */
public class RankingEntranceFrag extends BaseFragment {
    public ListView listview;
    public CommAdapter<RankEntranceBean.DataBean.RanksBean> madapter;
    public List<RankEntranceBean.DataBean.RanksBean> mreanks = new ArrayList();

    /* renamed from: com.psychiatrygarden.fragmenthome.RankingEntranceFrag$1, reason: invalid class name */
    public class AnonymousClass1 extends CommAdapter<RankEntranceBean.DataBean.RanksBean> {
        public AnonymousClass1(List mData, Context mcontext, int layoutId) {
            super(mData, mcontext, layoutId);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(RankEntranceBean.DataBean.RanksBean ranksBean, View view) {
            new XPopup.Builder(((BaseFragment) RankingEntranceFrag.this).mContext).hasShadowBg(Boolean.FALSE).asCustom(new ToastPopWindow(((BaseFragment) RankingEntranceFrag.this).mContext, ranksBean.getTotal(), ranksBean.getGrade_info(), "", ranksBean.getStatus())).show();
        }

        @Override // com.psychiatrygarden.activity.purchase.adapter.CommAdapter
        public void convert(ViewHolder vHolder, final RankEntranceBean.DataBean.RanksBean ranksBean, int position) {
            CircleImageView circleImageView = (CircleImageView) vHolder.getView(R.id.iv_header_img);
            Glide.with(((BaseFragment) RankingEntranceFrag.this).mContext).load((Object) GlideUtils.generateUrl(ranksBean.getAvatar())).placeholder(new ColorDrawable(ContextCompat.getColor(circleImageView.getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).into(circleImageView);
            ((TextView) vHolder.getView(R.id.tv_ranking_name)).setText(String.format("%s", ranksBean.getNickname()));
            TextView textView = (TextView) vHolder.getView(R.id.tv_ranking_grade);
            if (RankingEntranceFrag.this.getArguments().getString("moudle").equals(Constants.VIA_REPORT_TYPE_WPA_STATE)) {
                textView.setText(String.format("%s", ranksBean.getGrade_info()));
                textView.setCompoundDrawables(null, null, null, null);
            } else if (RankingEntranceFrag.this.getArguments().getString("moudle").equals("0")) {
                textView.setText(String.format("%s", ranksBean.getTotal()));
                textView.setCompoundDrawables(null, null, null, null);
            } else {
                textView.setText(String.format("%s", ranksBean.getTotal()));
                textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.yb
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f16143c.lambda$convert$0(ranksBean, view);
                    }
                });
            }
            CircleImageView circleImageView2 = (CircleImageView) vHolder.getView(R.id.circleimg);
            TextView textView2 = (TextView) vHolder.getView(R.id.tv_ranking_num);
            int i2 = position + 1;
            if (i2 == 1) {
                circleImageView2.setVisibility(0);
                textView2.setVisibility(8);
                circleImageView2.setImageResource(R.drawable.rankone);
            } else if (i2 == 2) {
                circleImageView2.setVisibility(0);
                textView2.setVisibility(8);
                circleImageView2.setImageResource(R.drawable.ranktwo);
            } else if (i2 != 3) {
                circleImageView2.setVisibility(8);
                textView2.setVisibility(0);
                textView2.setText(String.format(Locale.CHINA, TimeModel.NUMBER_FORMAT, Integer.valueOf(i2)));
            } else {
                circleImageView2.setVisibility(0);
                textView2.setVisibility(8);
                circleImageView2.setImageResource(R.drawable.rankthree);
            }
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.activity_ranking_entrance;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(com.psychiatrygarden.baseview.ViewHolder holder, View root) {
        this.listview = (ListView) holder.get(R.id.listview);
        this.mreanks = getArguments().getParcelableArrayList("rankList");
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.mreanks, this.mContext, R.layout.layout_rankentrance_provider);
        this.madapter = anonymousClass1;
        this.listview.setAdapter((ListAdapter) anonymousClass1);
    }
}
