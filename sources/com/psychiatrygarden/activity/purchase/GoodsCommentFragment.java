package com.psychiatrygarden.activity.purchase;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.purchase.adapter.CommAdapter;
import com.psychiatrygarden.activity.purchase.adapter.ViewHolder;
import com.psychiatrygarden.activity.purchase.beans.CommodityEvaluationBean;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.gradview.NineGridTestLayout;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.SkinManager;
import com.tencent.connect.common.Constants;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class GoodsCommentFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    public CommAdapter<CommodityEvaluationBean.DataBean.TimeLineBean> adapter;
    private View addFooterView;
    public ListView listView1;
    public SwipeRefreshLayout mSwipeRefreshLayout;
    public CommodityEvaluationBean mcBean;

    /* renamed from: r1, reason: collision with root package name */
    public LinearLayout f13557r1;
    public LinearLayout r2;
    public LinearLayout r3;
    public LinearLayout r4;
    public LinearLayout r5;

    /* renamed from: t1, reason: collision with root package name */
    TextView f13558t1;
    TextView t2;
    TextView t3;
    TextView t4;
    TextView t5;
    TextView tt1;
    TextView tt2;
    TextView tt3;
    TextView tt4;
    TextView tt5;
    int page = 1;
    int statu = 0;
    List<CommodityEvaluationBean.DataBean.TimeLineBean> timeLineDataF = new ArrayList();
    private String goods_id = "";

    @SuppressLint({"NonConstantResourceId"})
    View.OnClickListener onclick = new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.f
        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            this.f13698c.lambda$new$0(view);
        }
    };

    /* renamed from: com.psychiatrygarden.activity.purchase.GoodsCommentFragment$2, reason: invalid class name */
    public class AnonymousClass2 implements AbsListView.OnScrollListener {
        public AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onScrollStateChanged$0() {
            GoodsCommentFragment goodsCommentFragment = GoodsCommentFragment.this;
            goodsCommentFragment.page++;
            goodsCommentFragment.getData();
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == 0 && view.getLastVisiblePosition() == view.getCount() - 1 && GoodsCommentFragment.this.listView1.getFooterViewsCount() <= 0) {
                GoodsCommentFragment goodsCommentFragment = GoodsCommentFragment.this;
                goodsCommentFragment.listView1.addFooterView(goodsCommentFragment.addFooterView);
                GoodsCommentFragment.this.listView1.setVisibility(0);
                if (GoodsCommentFragment.this.mSwipeRefreshLayout.isRefreshing()) {
                    GoodsCommentFragment.this.mSwipeRefreshLayout.setRefreshing(false);
                } else {
                    new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.purchase.g
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f13699c.lambda$onScrollStateChanged$0();
                        }
                    }, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
                }
            }
        }
    }

    public static Fragment getInstance(String goods_id) {
        GoodsCommentFragment goodsCommentFragment = new GoodsCommentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("goods_id", goods_id);
        goodsCommentFragment.setArguments(bundle);
        return goodsCommentFragment;
    }

    public static boolean isFloat(String input) {
        return input.matches("\\d+\\.\\d*");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        if (CommonUtil.isFastClick()) {
        }
        switch (view.getId()) {
            case R.id.f26073r1 /* 2131366251 */:
                Boolean bool = Boolean.TRUE;
                Boolean bool2 = Boolean.FALSE;
                isSelector(bool, bool2, bool2, bool2, bool2);
                this.statu = 0;
                this.page = 1;
                getCommentList("");
                break;
            case R.id.r2 /* 2131366252 */:
                Boolean bool3 = Boolean.FALSE;
                isSelector(bool3, Boolean.TRUE, bool3, bool3, bool3);
                this.statu = 1;
                this.page = 1;
                getCommentList("fine");
                break;
            case R.id.r3 /* 2131366254 */:
                Boolean bool4 = Boolean.FALSE;
                isSelector(bool4, bool4, Boolean.TRUE, bool4, bool4);
                this.statu = 2;
                this.page = 1;
                getCommentList("notBad");
                break;
            case R.id.r4 /* 2131366255 */:
                Boolean bool5 = Boolean.FALSE;
                isSelector(bool5, bool5, bool5, Boolean.TRUE, bool5);
                this.statu = 3;
                this.page = 1;
                getCommentList("bad");
                break;
            case R.id.r5 /* 2131366256 */:
                Boolean bool6 = Boolean.FALSE;
                isSelector(bool6, bool6, bool6, bool6, Boolean.TRUE);
                this.statu = 4;
                this.page = 1;
                getCommentList("picture");
                break;
        }
    }

    public void getCommentList(String type) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("obj_id", this.goods_id);
        ajaxParams.put("module_type", Constants.VIA_SHARE_TYPE_MINI_PROGRAM);
        ajaxParams.put("comment_type", "1");
        ajaxParams.put("type", type + "");
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, this.page + "");
        YJYHttpUtils.post(getActivity(), NetworkRequestsURL.mCommentList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.purchase.GoodsCommentFragment.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                if (GoodsCommentFragment.this.mSwipeRefreshLayout.isRefreshing()) {
                    GoodsCommentFragment.this.mSwipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                super.onSuccess((AnonymousClass3) t2);
                try {
                    GoodsCommentFragment.this.mcBean = (CommodityEvaluationBean) new Gson().fromJson(t2, CommodityEvaluationBean.class);
                    if (GoodsCommentFragment.this.mcBean.getCode().equals("200")) {
                        GoodsCommentFragment goodsCommentFragment = GoodsCommentFragment.this;
                        if (goodsCommentFragment.page == 1) {
                            goodsCommentFragment.timeLineDataF = goodsCommentFragment.mcBean.getData().getTime_line();
                            if (GoodsCommentFragment.this.getActivity() == null) {
                                return;
                            }
                            GoodsCommentFragment goodsCommentFragment2 = GoodsCommentFragment.this;
                            GoodsCommentFragment goodsCommentFragment3 = GoodsCommentFragment.this;
                            goodsCommentFragment2.adapter = new CommAdapter<CommodityEvaluationBean.DataBean.TimeLineBean>(goodsCommentFragment3.timeLineDataF, goodsCommentFragment3.getActivity(), R.layout.activity_pingjia) { // from class: com.psychiatrygarden.activity.purchase.GoodsCommentFragment.3.1
                                @Override // com.psychiatrygarden.activity.purchase.adapter.CommAdapter
                                public void convert(ViewHolder vHolder, CommodityEvaluationBean.DataBean.TimeLineBean timeLineBean, int position) throws NumberFormatException {
                                    NineGridTestLayout nineGridTestLayout = (NineGridTestLayout) vHolder.getView(R.id.ninegrid);
                                    TextView textView = (TextView) vHolder.getView(R.id.goumiashijian_tv);
                                    if (timeLineBean.getReply() == null || timeLineBean.getReply().size() <= 0) {
                                        textView.setVisibility(8);
                                    } else {
                                        textView.setVisibility(0);
                                        textView.setText(timeLineBean.getReply().get(0).getContent());
                                    }
                                    vHolder.setText(R.id.username, timeLineBean.getNickname()).setText(R.id.time, timeLineBean.getCtime()).setText(R.id.content_pingjia, timeLineBean.getContent()).setImageHeadUrl(R.id.mycenter_icon, timeLineBean.getAvatar());
                                    LinearLayout linearLayout = (LinearLayout) vHolder.getView(R.id.linearLayout1);
                                    linearLayout.removeAllViews();
                                    float f2 = Float.parseFloat(timeLineBean.getGrade());
                                    if (f2 - 1.0f <= 0.0f) {
                                        for (int i2 = 0; i2 < 5; i2++) {
                                            ImageView imageView = new ImageView(GoodsCommentFragment.this.getActivity());
                                            imageView.setLayoutParams(new LinearLayout.LayoutParams(CommonUtil.getScreenWidth(GoodsCommentFragment.this.getActivity()) / 25, CommonUtil.getScreenWidth(GoodsCommentFragment.this.getActivity()) / 25));
                                            if (SkinManager.getCurrentSkinType(GoodsCommentFragment.this.getActivity()) == 0) {
                                                if (f2 > i2) {
                                                    imageView.setBackgroundResource(R.drawable.xing);
                                                } else {
                                                    imageView.setBackgroundResource(R.drawable.kongxing);
                                                }
                                            } else if (f2 > i2) {
                                                imageView.setBackgroundResource(R.drawable.xing_night);
                                            } else {
                                                imageView.setBackgroundResource(R.drawable.kongxing_night);
                                            }
                                            linearLayout.addView(imageView);
                                        }
                                    } else if (GoodsCommentFragment.isFloat(timeLineBean.getGrade())) {
                                        float f3 = (float) (f2 - 0.5d);
                                        for (int i3 = 0; i3 < 5; i3++) {
                                            ImageView imageView2 = new ImageView(GoodsCommentFragment.this.getActivity());
                                            imageView2.setLayoutParams(new LinearLayout.LayoutParams(CommonUtil.getScreenWidth(GoodsCommentFragment.this.getActivity()) / 25, CommonUtil.getScreenWidth(GoodsCommentFragment.this.getActivity()) / 25));
                                            if (SkinManager.getCurrentSkinType(GoodsCommentFragment.this.getActivity()) == 0) {
                                                float f4 = i3;
                                                if (f3 > f4) {
                                                    imageView2.setBackgroundResource(R.drawable.xing);
                                                } else if (f4 == f3) {
                                                    imageView2.setBackgroundResource(R.drawable.xing);
                                                } else {
                                                    imageView2.setBackgroundResource(R.drawable.kongxing);
                                                }
                                            } else {
                                                float f5 = i3;
                                                if (f3 > f5) {
                                                    imageView2.setBackgroundResource(R.drawable.xing_night);
                                                } else if (f5 == f3) {
                                                    imageView2.setBackgroundResource(R.drawable.xing_night);
                                                } else {
                                                    imageView2.setBackgroundResource(R.drawable.kongxing_night);
                                                }
                                            }
                                            linearLayout.addView(imageView2);
                                        }
                                    } else {
                                        for (int i4 = 0; i4 < 5; i4++) {
                                            ImageView imageView3 = new ImageView(GoodsCommentFragment.this.getActivity());
                                            imageView3.setLayoutParams(new LinearLayout.LayoutParams(CommonUtil.getScreenWidth(GoodsCommentFragment.this.getActivity()) / 25, CommonUtil.getScreenWidth(GoodsCommentFragment.this.getActivity()) / 25));
                                            if (SkinManager.getCurrentSkinType(GoodsCommentFragment.this.getActivity()) == 0) {
                                                if (f2 > i4) {
                                                    imageView3.setBackgroundResource(R.drawable.xing);
                                                } else {
                                                    imageView3.setBackgroundResource(R.drawable.kongxing);
                                                }
                                            } else if (f2 > i4) {
                                                imageView3.setBackgroundResource(R.drawable.xing_night);
                                            } else {
                                                imageView3.setBackgroundResource(R.drawable.kongxing_night);
                                            }
                                            linearLayout.addView(imageView3);
                                        }
                                    }
                                    nineGridTestLayout.setIsShowAll(timeLineBean.getImgs().size() <= 3);
                                    nineGridTestLayout.setUrlList(timeLineBean.getImgs());
                                }
                            };
                            GoodsCommentFragment goodsCommentFragment4 = GoodsCommentFragment.this;
                            goodsCommentFragment4.listView1.setAdapter((ListAdapter) goodsCommentFragment4.adapter);
                        } else {
                            goodsCommentFragment.listView1.removeFooterView(goodsCommentFragment.addFooterView);
                            GoodsCommentFragment.this.addFooterView.setVisibility(8);
                            GoodsCommentFragment.this.timeLineDataF.addAll(((CommodityEvaluationBean) new Gson().fromJson(t2, CommodityEvaluationBean.class)).getData().getTime_line());
                            GoodsCommentFragment.this.adapter.notifyDataSetChanged();
                        }
                        GoodsCommentFragment goodsCommentFragment5 = GoodsCommentFragment.this;
                        goodsCommentFragment5.tt1.setText(goodsCommentFragment5.mcBean.getData().getCount().getAll());
                        GoodsCommentFragment goodsCommentFragment6 = GoodsCommentFragment.this;
                        goodsCommentFragment6.tt2.setText(goodsCommentFragment6.mcBean.getData().getCount().getFine());
                        GoodsCommentFragment goodsCommentFragment7 = GoodsCommentFragment.this;
                        goodsCommentFragment7.tt3.setText(goodsCommentFragment7.mcBean.getData().getCount().getNotBad());
                        GoodsCommentFragment goodsCommentFragment8 = GoodsCommentFragment.this;
                        goodsCommentFragment8.tt4.setText(goodsCommentFragment8.mcBean.getData().getCount().getBad());
                        GoodsCommentFragment goodsCommentFragment9 = GoodsCommentFragment.this;
                        goodsCommentFragment9.tt5.setText(goodsCommentFragment9.mcBean.getData().getCount().getPicture());
                    }
                    if (GoodsCommentFragment.this.mSwipeRefreshLayout.isRefreshing()) {
                        GoodsCommentFragment.this.mSwipeRefreshLayout.setRefreshing(false);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void getData() {
        int i2 = this.statu;
        if (i2 == 0) {
            getCommentList("");
            this.statu = 0;
            return;
        }
        if (i2 == 1) {
            getCommentList("fine");
            this.statu = 1;
            return;
        }
        if (i2 == 2) {
            getCommentList("notBad");
            this.statu = 2;
        } else if (i2 == 3) {
            getCommentList("bad");
            this.statu = 3;
        } else {
            if (i2 != 4) {
                return;
            }
            getCommentList("picture");
            this.statu = 4;
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        this.goods_id = getArguments().getString("goods_id", "");
        return R.layout.fragment_goods_comment;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(com.psychiatrygarden.baseview.ViewHolder holder, View root) {
        this.f13558t1 = (TextView) holder.get(R.id.f26075t1);
        this.t2 = (TextView) holder.get(R.id.t2);
        this.t3 = (TextView) holder.get(R.id.t3);
        this.t4 = (TextView) holder.get(R.id.t4);
        this.t5 = (TextView) holder.get(R.id.t5);
        this.tt1 = (TextView) holder.get(R.id.tt1);
        this.tt2 = (TextView) holder.get(R.id.tt2);
        this.tt3 = (TextView) holder.get(R.id.tt3);
        this.tt4 = (TextView) holder.get(R.id.tt4);
        this.tt5 = (TextView) holder.get(R.id.tt5);
        this.f13557r1 = (LinearLayout) holder.get(R.id.f26073r1);
        this.r2 = (LinearLayout) holder.get(R.id.r2);
        this.r3 = (LinearLayout) holder.get(R.id.r3);
        this.r4 = (LinearLayout) holder.get(R.id.r4);
        this.r5 = (LinearLayout) holder.get(R.id.r5);
        this.f13557r1.setOnClickListener(this.onclick);
        this.r2.setOnClickListener(this.onclick);
        this.r3.setOnClickListener(this.onclick);
        this.r4.setOnClickListener(this.onclick);
        this.r5.setOnClickListener(this.onclick);
        Boolean bool = Boolean.TRUE;
        Boolean bool2 = Boolean.FALSE;
        isSelector(bool, bool2, bool2, bool2, bool2);
        this.listView1 = (ListView) holder.get(R.id.pinnedSectionListView1);
        this.addFooterView = getActivity().getLayoutInflater().inflate(R.layout.activity_hideview, (ViewGroup) null);
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) holder.get(R.id.mSwipeLayput);
        this.mSwipeRefreshLayout = swipeRefreshLayout;
        swipeRefreshLayout.setOnRefreshListener(this);
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            this.mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(getActivity().getResources().getColor(R.color.white));
            this.mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_red_light, android.R.color.holo_red_light, android.R.color.holo_red_light);
        } else {
            this.mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(getActivity().getResources().getColor(R.color.input_night_color));
            this.mSwipeRefreshLayout.setColorSchemeResources(R.color.question_color_night, R.color.question_color_night, R.color.question_color_night, R.color.question_color_night);
        }
        this.mSwipeRefreshLayout.post(new Runnable() { // from class: com.psychiatrygarden.activity.purchase.GoodsCommentFragment.1
            @Override // java.lang.Runnable
            public void run() {
                GoodsCommentFragment.this.mSwipeRefreshLayout.setRefreshing(true);
                GoodsCommentFragment goodsCommentFragment = GoodsCommentFragment.this;
                goodsCommentFragment.page = 1;
                goodsCommentFragment.getData();
            }
        });
        this.listView1.setOnScrollListener(new AnonymousClass2());
    }

    public void isSelector(Boolean r12, Boolean r2, Boolean r3, Boolean r4, Boolean r5) {
        this.f13558t1.setSelected(r12.booleanValue());
        this.tt1.setSelected(r12.booleanValue());
        this.t2.setSelected(r2.booleanValue());
        this.tt2.setSelected(r2.booleanValue());
        this.t3.setSelected(r3.booleanValue());
        this.tt3.setSelected(r3.booleanValue());
        this.t4.setSelected(r4.booleanValue());
        this.tt4.setSelected(r4.booleanValue());
        this.t5.setSelected(r5.booleanValue());
        this.tt5.setSelected(r5.booleanValue());
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    @SuppressLint({"NewApi"})
    public void onEventMainThread(String str) {
    }

    @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
    public void onRefresh() {
        this.page = 1;
        getData();
    }
}
