package com.psychiatrygarden.activity.purchase.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.lxj.xpopup.core.ImageViewerPopupView;
import com.lxj.xpopup.core.PopupInfo;
import com.lxj.xpopup.interfaces.OnSrcViewUpdateListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.purchase.beans.CommodityEvaluationBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ImageLoaderUtilsCustom;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.CircleImageView;
import com.psychiatrygarden.widget.ImageViewerPopupViewCustom;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.connect.common.Constants;
import com.yikaobang.yixue.R;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes5.dex */
public class GoodsCommentActivity extends BaseActivity {
    public BaseQuickAdapter<CommodityEvaluationBean.DataBean.TimeLineBean, BaseViewHolder> adaComment;
    public BaseQuickAdapter<HeaderDataBean, BaseViewHolder> adapter;
    public RecyclerView headerList;
    public ImageView iconBack;
    public RecyclerView list;
    public SmartRefreshLayout refreshview;
    public TextView title;

    /* renamed from: w, reason: collision with root package name */
    public int f13561w;
    public String typeStr = "all";
    public String goods_id = "";
    public int page = 1;

    /* renamed from: com.psychiatrygarden.activity.purchase.activity.GoodsCommentActivity$1, reason: invalid class name */
    public class AnonymousClass1 extends BaseQuickAdapter<HeaderDataBean, BaseViewHolder> {
        public AnonymousClass1(int layoutResId, List data) {
            super(layoutResId, data);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(HeaderDataBean headerDataBean, View view) {
            GoodsCommentActivity.this.typeStr = headerDataBean.getName() + "";
            GoodsCommentActivity.this.refreshview.autoRefresh();
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NotNull BaseViewHolder baseViewHolder, final HeaderDataBean headerDataBean) {
            TextView textView = (TextView) baseViewHolder.getView(R.id.f26075t1);
            TextView textView2 = (TextView) baseViewHolder.getView(R.id.tt1);
            textView.setText(String.format("%s", headerDataBean.getNameLabel()));
            textView2.setText(String.format("%s", headerDataBean.getCount()));
            if (GoodsCommentActivity.this.typeStr.equals(headerDataBean.getName())) {
                textView.setSelected(true);
                textView2.setSelected(true);
            } else {
                textView.setSelected(false);
                textView2.setSelected(false);
            }
            baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.t
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f13654c.lambda$convert$0(headerDataBean, view);
                }
            });
        }
    }

    public static class HeaderDataBean implements Serializable {
        public String name;
        public int select = 1;
        public String count = "";
        public String nameLabel = "";

        public String getCount() {
            return this.count;
        }

        public String getName() {
            return this.name;
        }

        public String getNameLabel() {
            return this.nameLabel;
        }

        public int getSelect() {
            return this.select;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setNameLabel(String nameLabel) {
            this.nameLabel = nameLabel;
        }

        public void setSelect(int select) {
            this.select = select;
        }
    }

    public static boolean isFloat(String input) {
        return input.matches("\\d+\\.\\d*");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(RefreshLayout refreshLayout) {
        this.page = 1;
        getCommentList(this.typeStr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(RefreshLayout refreshLayout) {
        this.page++;
        getCommentList(this.typeStr);
    }

    public void addGradren(String grade, LinearLayout mlinrojut) throws NumberFormatException {
        mlinrojut.removeAllViews();
        float f2 = Float.parseFloat(grade);
        int i2 = 0;
        if (f2 - 1.0f <= 0.0f) {
            while (i2 < 5) {
                View imageView = new ImageView(this.mContext);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(CommonUtil.dip2px(this.mContext, 11.0f), CommonUtil.dip2px(this.mContext, 11.0f));
                layoutParams.rightMargin = CommonUtil.dip2px(this.mContext, 5.0f);
                imageView.setLayoutParams(layoutParams);
                if (i2 == 0) {
                    if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                        imageView.setBackgroundResource(R.drawable.xing);
                    } else {
                        imageView.setBackgroundResource(R.drawable.xing_night);
                    }
                } else if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                    imageView.setBackgroundResource(R.drawable.kongxing);
                } else {
                    imageView.setBackgroundResource(R.drawable.kongxing_night);
                }
                mlinrojut.addView(imageView);
                i2++;
            }
            return;
        }
        if (!isFloat(grade)) {
            while (i2 < 5) {
                View imageView2 = new ImageView(this.mContext);
                LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(CommonUtil.dip2px(this.mContext, 11.0f), CommonUtil.dip2px(this.mContext, 11.0f));
                layoutParams2.rightMargin = CommonUtil.dip2px(this.mContext, 5.0f);
                imageView2.setLayoutParams(layoutParams2);
                if (f2 > i2) {
                    if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                        imageView2.setBackgroundResource(R.drawable.xing);
                    } else {
                        imageView2.setBackgroundResource(R.drawable.xing_night);
                    }
                } else if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                    imageView2.setBackgroundResource(R.drawable.kongxing);
                } else {
                    imageView2.setBackgroundResource(R.drawable.kongxing_night);
                }
                mlinrojut.addView(imageView2);
                i2++;
            }
            return;
        }
        float f3 = (float) (f2 - 0.5d);
        while (i2 < 5) {
            View imageView3 = new ImageView(this.mContext);
            LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(CommonUtil.dip2px(this.mContext, 11.0f), CommonUtil.dip2px(this.mContext, 11.0f));
            layoutParams3.rightMargin = CommonUtil.dip2px(this.mContext, 5.0f);
            imageView3.setLayoutParams(layoutParams3);
            float f4 = i2;
            if (f3 > f4) {
                if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                    imageView3.setBackgroundResource(R.drawable.xing);
                } else {
                    imageView3.setBackgroundResource(R.drawable.xing_night);
                }
            } else if (f4 == f3) {
                if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                    imageView3.setBackgroundResource(R.drawable.xing);
                } else {
                    imageView3.setBackgroundResource(R.drawable.xing_night);
                }
            } else if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                imageView3.setBackgroundResource(R.drawable.kongxing);
            } else {
                imageView3.setBackgroundResource(R.drawable.kongxing_night);
            }
            mlinrojut.addView(imageView3);
            i2++;
        }
    }

    public void addHeaderData() {
        ArrayList arrayList = new ArrayList();
        HeaderDataBean headerDataBean = new HeaderDataBean();
        headerDataBean.setName("all");
        headerDataBean.setNameLabel("全部");
        headerDataBean.setSelect(0);
        arrayList.add(headerDataBean);
        HeaderDataBean headerDataBean2 = new HeaderDataBean();
        headerDataBean2.setName("fine");
        headerDataBean2.setNameLabel("好评");
        headerDataBean2.setSelect(0);
        arrayList.add(headerDataBean2);
        HeaderDataBean headerDataBean3 = new HeaderDataBean();
        headerDataBean3.setName("notBad");
        headerDataBean3.setNameLabel("中评");
        headerDataBean3.setSelect(0);
        arrayList.add(headerDataBean3);
        HeaderDataBean headerDataBean4 = new HeaderDataBean();
        headerDataBean4.setName("bad");
        headerDataBean4.setNameLabel("差评");
        headerDataBean4.setSelect(0);
        arrayList.add(headerDataBean4);
        HeaderDataBean headerDataBean5 = new HeaderDataBean();
        headerDataBean5.setName("picture");
        headerDataBean5.setNameLabel("晒图");
        headerDataBean5.setSelect(0);
        arrayList.add(headerDataBean5);
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(R.layout.layout_goods_header, arrayList);
        this.adapter = anonymousClass1;
        this.headerList.setAdapter(anonymousClass1);
    }

    public void getCommentList(String type) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("obj_id", this.goods_id);
        ajaxParams.put("module_type", Constants.VIA_SHARE_TYPE_MINI_PROGRAM);
        ajaxParams.put("comment_type", "1");
        if ("all".equals(type)) {
            ajaxParams.put("type", "");
        } else {
            ajaxParams.put("type", type + "");
        }
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, this.page + "");
        YJYHttpUtils.post(this, NetworkRequestsURL.mCommentList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.purchase.activity.GoodsCommentActivity.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                GoodsCommentActivity.this.refreshview.finishRefresh(false);
                GoodsCommentActivity.this.refreshview.finishLoadMore(false);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                super.onSuccess((AnonymousClass3) t2);
                try {
                    GoodsCommentActivity.this.refreshview.finishRefresh();
                    GoodsCommentActivity.this.refreshview.finishLoadMore();
                    CommodityEvaluationBean commodityEvaluationBean = (CommodityEvaluationBean) new Gson().fromJson(t2, CommodityEvaluationBean.class);
                    GoodsCommentActivity goodsCommentActivity = GoodsCommentActivity.this;
                    if (goodsCommentActivity.page > 1) {
                        if (commodityEvaluationBean.getData().getTime_line() == null || commodityEvaluationBean.getData().getTime_line().size() <= 0) {
                            GoodsCommentActivity.this.refreshview.finishLoadMoreWithNoMoreData();
                            return;
                        } else {
                            GoodsCommentActivity.this.adaComment.addData(commodityEvaluationBean.getData().getTime_line());
                            GoodsCommentActivity.this.refreshview.finishLoadMore();
                            return;
                        }
                    }
                    goodsCommentActivity.adaComment.setList(commodityEvaluationBean.getData().getTime_line());
                    if (GoodsCommentActivity.this.adapter == null || commodityEvaluationBean.getData() == null || commodityEvaluationBean.getData().getCount() == null) {
                        return;
                    }
                    if (GoodsCommentActivity.this.adapter.getData().size() > 0) {
                        GoodsCommentActivity.this.title.setText(String.format("评价（%s)", commodityEvaluationBean.getData().getCount().getAll()));
                    }
                    GoodsCommentActivity.this.adapter.notifyDataSetChanged();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.goods_id = getIntent().getExtras().getString("goods_id");
        this.headerList = (RecyclerView) findViewById(R.id.headerList);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        this.list = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.headerList.setLayoutManager(new GridLayoutManager(this, 5));
        this.refreshview = (SmartRefreshLayout) findViewById(R.id.refreshview);
        ImageView imageView = (ImageView) findViewById(R.id.iconBack);
        this.iconBack = imageView;
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.q
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13644c.lambda$init$0(view);
            }
        });
        this.title = (TextView) findViewById(R.id.title);
        addHeaderData();
        this.refreshview.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.activity.purchase.activity.r
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                this.f13647c.lambda$init$1(refreshLayout);
            }
        });
        this.refreshview.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.psychiatrygarden.activity.purchase.activity.s
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public final void onLoadMore(RefreshLayout refreshLayout) {
                this.f13649c.lambda$init$2(refreshLayout);
            }
        });
        BaseQuickAdapter<CommodityEvaluationBean.DataBean.TimeLineBean, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<CommodityEvaluationBean.DataBean.TimeLineBean, BaseViewHolder>(R.layout.layout_goods_comment_item) { // from class: com.psychiatrygarden.activity.purchase.activity.GoodsCommentActivity.2

            /* renamed from: com.psychiatrygarden.activity.purchase.activity.GoodsCommentActivity$2$1, reason: invalid class name */
            public class AnonymousClass1 extends BaseQuickAdapter<String, BaseViewHolder> {
                public AnonymousClass1(int layoutResId, List data) {
                    super(layoutResId, data);
                }

                /* JADX INFO: Access modifiers changed from: private */
                public /* synthetic */ void lambda$convert$0(RoundedImageView roundedImageView, final BaseViewHolder baseViewHolder, View view) {
                    ArrayList arrayList = new ArrayList();
                    getData();
                    if (getData().size() > 0) {
                        arrayList.addAll(getData());
                    }
                    if (arrayList.size() > 0) {
                        ImageViewerPopupViewCustom longPressListener = new ImageViewerPopupViewCustom(GoodsCommentActivity.this).setSrcView(roundedImageView, baseViewHolder.getBindingAdapterPosition()).setImageUrls(arrayList).isInfinite(false).isShowPlaceholder(true).setPlaceholderColor(-1).setPlaceholderStrokeColor(-1).setPlaceholderRadius(-1).isShowSaveButton(true).setBgColor(Color.rgb(32, 36, 46)).setSrcViewUpdateListener(new OnSrcViewUpdateListener() { // from class: com.psychiatrygarden.activity.purchase.activity.GoodsCommentActivity.2.1.1
                            @Override // com.lxj.xpopup.interfaces.OnSrcViewUpdateListener
                            public void onSrcViewUpdate(@NonNull @NotNull ImageViewerPopupView popupView, int position) {
                                popupView.updateSrcView((ImageView) ((RecyclerView) baseViewHolder.itemView.getParent()).getChildAt(position));
                            }
                        }).setXPopupImageLoader(new ImageLoaderUtilsCustom()).setLongPressListener(null);
                        longPressListener.popupInfo = new PopupInfo();
                        longPressListener.show();
                    }
                }

                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(@NotNull final BaseViewHolder baseHolder, String s2) {
                    final RoundedImageView roundedImageView = (RoundedImageView) baseHolder.findView(R.id.img);
                    Glide.with((FragmentActivity) GoodsCommentActivity.this).load((Object) GlideUtils.generateUrl(s2)).apply((BaseRequestOptions<?>) new RequestOptions().override(Integer.MIN_VALUE)).placeholder(new ColorDrawable(ContextCompat.getColor(getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).into(roundedImageView);
                    roundedImageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.u
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f13659c.lambda$convert$0(roundedImageView, baseHolder, view);
                        }
                    });
                }
            }

            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(@NotNull BaseViewHolder baseViewHolder, CommodityEvaluationBean.DataBean.TimeLineBean timeLineBean) throws NumberFormatException {
                CircleImageView circleImageView = (CircleImageView) baseViewHolder.getView(R.id.headerimg);
                TextView textView = (TextView) baseViewHolder.getView(R.id.nickname);
                TextView textView2 = (TextView) baseViewHolder.getView(R.id.content);
                TextView textView3 = (TextView) baseViewHolder.getView(R.id.source);
                LinearLayout linearLayout = (LinearLayout) baseViewHolder.getView(R.id.lingradview);
                RecyclerView recyclerView2 = (RecyclerView) baseViewHolder.getView(R.id.imageRecycleview);
                recyclerView2.setLayoutManager(new GridLayoutManager(GoodsCommentActivity.this, 3));
                recyclerView2.setAdapter(new AnonymousClass1(R.layout.layout_goods_comment_img, timeLineBean.getImgs()));
                Glide.with(circleImageView.getContext()).load((Object) GlideUtils.generateUrl(timeLineBean.getAvatar())).placeholder(new ColorDrawable(ContextCompat.getColor(getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).into(circleImageView);
                textView.setText(timeLineBean.getNickname());
                textView2.setText(timeLineBean.getContent());
                textView3.setText(timeLineBean.getBuy_time());
                GoodsCommentActivity.this.addGradren(timeLineBean.getGrade(), linearLayout);
                if (timeLineBean.getImgs().size() > 0) {
                    recyclerView2.setVisibility(0);
                } else {
                    recyclerView2.setVisibility(8);
                }
            }
        };
        this.adaComment = baseQuickAdapter;
        this.list.setAdapter(baseQuickAdapter);
        this.refreshview.autoRefresh();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initwriteStatusBar();
        this.mActionBar.hide();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_goods_comment);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
