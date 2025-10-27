package com.psychiatrygarden.activity.vip.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import com.bumptech.glide.Glide;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.SmartGlideImageLoader;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.purchase.adapter.CommAdapter;
import com.psychiatrygarden.activity.purchase.adapter.ViewHolder;
import com.psychiatrygarden.activity.vip.Utils.MemInterface;
import com.psychiatrygarden.activity.vip.bean.MemCenterBean;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.weblong.SizeUtil;
import com.psychiatrygarden.widget.MyListView;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.List;

/* loaded from: classes5.dex */
public class MemberPopupwindow extends BottomPopupView {
    private CommAdapter<MemCenterBean.DataBeanX.WaysBean> adapter;
    private TextView cancelTv;
    private int disType;
    private boolean gift;
    private String img;
    private boolean isStatistics;
    private String mImgHeight;
    private String mImgWidth;
    private String name;
    private List<MemCenterBean.DataBeanX.WaysBean> ways;

    /* renamed from: com.psychiatrygarden.activity.vip.pop.MemberPopupwindow$1, reason: invalid class name */
    public class AnonymousClass1 extends CommAdapter<MemCenterBean.DataBeanX.WaysBean> {
        public AnonymousClass1(List mData, Context mcontext, int layoutId) {
            super(mData, mcontext, layoutId);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(int i2, View view) {
            if (!"com.psychiatrygarden.activity.rank.EntranceResultsActivity".equals(((Activity) MemberPopupwindow.this.getContext()).getLocalClassName()) || ((MemCenterBean.DataBeanX.WaysBean) MemberPopupwindow.this.ways.get(i2)).getWay().equals("share")) {
                MemberPopupwindow.this.dismiss();
            }
            MemInterface.getInstance().putData((Activity) MemberPopupwindow.this.getContext(), (MemCenterBean.DataBeanX.WaysBean) MemberPopupwindow.this.ways.get(i2), 0, MemberPopupwindow.this.gift);
        }

        @Override // com.psychiatrygarden.activity.purchase.adapter.CommAdapter
        public void convert(ViewHolder vHolder, MemCenterBean.DataBeanX.WaysBean waysBean, final int position) {
            ImageView imageView = (ImageView) vHolder.getView(R.id.leftimg);
            TextView textView = (TextView) vHolder.getView(R.id.mgdsimg);
            TextView textView2 = (TextView) vHolder.getView(R.id.tv_to_lock);
            TextView textView3 = (TextView) vHolder.getView(R.id.mfristTxt);
            TextView textView4 = (TextView) vHolder.getView(R.id.mDofristTxt);
            ((LinearLayout) vHolder.getView(R.id.ly_item)).setSelected(SkinManager.getCurrentSkinType(MemberPopupwindow.this.getContext()) != 1);
            textView3.setText(waysBean.getTitle());
            textView4.setText(waysBean.getDescription());
            if (waysBean.getData() == null) {
                textView2.setText("立即解锁");
            } else if (!TextUtils.isEmpty(waysBean.getData().getPrice())) {
                textView2.setText("￥" + waysBean.getData().getPrice());
            } else if ("buy_goods".equals(waysBean.getWay()) || "buy_course".equals(waysBean.getWay())) {
                textView2.setText("立即购买");
            } else {
                textView2.setText("立即解锁");
            }
            Glide.with(MemberPopupwindow.this.getContext()).load((Object) GlideUtils.generateUrl(waysBean.getIcon())).error(R.mipmap.lock_way).placeholder(new ColorDrawable(ContextCompat.getColor(imageView.getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).into(imageView);
            if (TextUtils.isEmpty(waysBean.getRecommend()) || "0".equals(waysBean.getRecommend())) {
                textView.setVisibility(8);
            } else {
                textView.setVisibility(0);
            }
            if (!TextUtils.isEmpty(waysBean.getRecommend_label())) {
                textView.setText(waysBean.getRecommend_label());
            }
            textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.vip.pop.f
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f14110c.lambda$convert$0(position, view);
                }
            });
        }
    }

    public MemberPopupwindow(@NonNull Context context, String name, List<MemCenterBean.DataBeanX.WaysBean> ways, int disType, boolean isStatistics) {
        super(context);
        this.ways = ways;
        this.name = name;
        this.disType = disType;
        this.isStatistics = isStatistics;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(ImageView imageView, View view) {
        new XPopup.Builder(view.getContext()).asImageViewer(imageView, this.img, new SmartGlideImageLoader()).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(View view) {
        if (this.isStatistics) {
            dismiss();
            EventBus.getDefault().post("closePage");
            return;
        }
        if ("com.psychiatrygarden.activity.HandoutsInfoActivity".equals(((Activity) getContext()).getLocalClassName())) {
            if (this.disType == 1) {
                dismiss();
            } else {
                ((Activity) getContext()).finish();
            }
        } else if ("com.psychiatrygarden.activity.rank.EntranceResultsActivity".equals(((Activity) getContext()).getLocalClassName()) || "com.psychiatrygarden.activity.EbookUnlockActivity".equals(((Activity) getContext()).getLocalClassName())) {
            dismiss();
            ((Activity) getContext()).finish();
        } else {
            dismiss();
        }
        EventBus.getDefault().post("cancelUnlock");
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public void doAfterDismiss() {
        super.doAfterDismiss();
        if ("com.psychiatrygarden.activity.EbookUnlockActivity".equals(((Activity) getContext()).getLocalClassName())) {
            dismiss();
            ((Activity) getContext()).finish();
        }
        EventBus.getDefault().post("cancelUnlock");
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.item_mem_pop;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getMaxHeight() {
        return ScreenUtil.getScreenHeight((Activity) getContext()) - SizeUtil.dp2px(getContext(), 96);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() throws NumberFormatException {
        double d3;
        double d4;
        super.onCreate();
        TextView textView = (TextView) findViewById(R.id.cancelTv);
        MyListView myListView = (MyListView) findViewById(R.id.listview);
        TextView textView2 = (TextView) findViewById(R.id.title);
        final ImageView imageView = (ImageView) findViewById(R.id.iv_img);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.root_view);
        NestedScrollView nestedScrollView = (NestedScrollView) findViewById(R.id.scroll_view);
        int screenHeight = ScreenUtil.getScreenHeight((Activity) getContext()) - SizeUtil.dp2px(getContext(), 96);
        textView2.setText(this.name);
        if (TextUtils.isEmpty(this.img)) {
            imageView.setVisibility(8);
        } else {
            if (TextUtils.isEmpty(this.mImgHeight)) {
                d3 = 1428.0d;
                d4 = 1428.0d;
            } else {
                d3 = Double.parseDouble(this.mImgWidth);
                d4 = Double.parseDouble(this.mImgHeight);
            }
            int screenWidth = ScreenUtil.getScreenWidth((Activity) getContext()) - SizeUtil.dp2px(getContext(), 32);
            int i2 = (int) (screenWidth * (d4 / d3));
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.vip.pop.d
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f14107c.lambda$onCreate$0(imageView, view);
                }
            });
            GlideUtils.loadImage(getContext(), this.img, imageView);
            int iDp2px = SizeUtil.dp2px(getContext(), 16);
            int pxByDp = ScreenUtil.getPxByDp(getContext(), 114) + i2 + (ScreenUtil.getPxByDp(getContext(), 80) * this.ways.size());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(screenWidth, i2);
            layoutParams.setMargins(iDp2px, 0, iDp2px, iDp2px);
            imageView.setLayoutParams(layoutParams);
            imageView.setVisibility(0);
            if (pxByDp > screenHeight) {
                linearLayout.getLayoutParams().height = screenHeight;
                int pxByDp2 = screenHeight - ScreenUtil.getPxByDp(getContext(), 114);
                LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) nestedScrollView.getLayoutParams();
                layoutParams2.height = pxByDp2;
                nestedScrollView.setLayoutParams(layoutParams2);
                LogUtils.e("view_heee", "if:scrollViewH=" + nestedScrollView.getLayoutParams().height);
            } else {
                linearLayout.getLayoutParams().height = pxByDp;
                int pxByDp3 = pxByDp - ScreenUtil.getPxByDp(getContext(), 114);
                LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) nestedScrollView.getLayoutParams();
                layoutParams3.height = pxByDp3;
                nestedScrollView.setLayoutParams(layoutParams3);
                LogUtils.e("view_heee", "else:scrollViewH=" + nestedScrollView.getLayoutParams().height);
            }
            textView2.setText("分层刷题优势对比");
        }
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.vip.pop.e
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14109c.lambda$onCreate$1(view);
            }
        });
        myListView.setAdapter((ListAdapter) new AnonymousClass1(this.ways, getContext(), R.layout.layout_mem_item));
    }

    public void setImg(String img, String width, String imgHeight) {
        this.img = img;
        this.mImgWidth = width;
        this.mImgHeight = imgHeight;
    }

    public MemberPopupwindow(@NonNull Context context, String name, List<MemCenterBean.DataBeanX.WaysBean> ways, int disType, boolean isStatistics, boolean gift) {
        super(context);
        this.ways = ways;
        this.name = name;
        this.disType = disType;
        this.isStatistics = isStatistics;
        this.gift = gift;
    }

    public MemberPopupwindow(@NonNull Context context, String name, List<MemCenterBean.DataBeanX.WaysBean> ways, int disType, String img) {
        super(context);
        this.ways = ways;
        this.name = name;
        this.disType = disType;
        this.img = img;
    }
}
