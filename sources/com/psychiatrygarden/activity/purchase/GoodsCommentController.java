package com.psychiatrygarden.activity.purchase;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.core.ImageViewerPopupView;
import com.lxj.xpopup.core.PopupInfo;
import com.lxj.xpopup.interfaces.OnSrcViewUpdateListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.purchase.GoodsCommentController;
import com.psychiatrygarden.activity.purchase.activity.GoodsCommentActivity;
import com.psychiatrygarden.activity.purchase.beans.CommodityEvaluationBean;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ImageLoaderUtilsCustom;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.CircleImageView;
import com.psychiatrygarden.widget.ImageViewerPopupViewCustom;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes5.dex */
public class GoodsCommentController {
    public BaseQuickAdapter<CommodityEvaluationBean.DataBean.TimeLineBean, BaseViewHolder> adapter;
    public TextView all_say_tv;
    public TextView cat_all_tv;
    private CommodityEvaluationBean.DataBean commentData;
    public Context context;
    public String goods_id;
    public BaseViewHolder holder;
    public RecyclerView recycleview;
    public RelativeLayout rl_dingwei;
    public TextView textView6;
    public TextView tv_all_haoping;

    public GoodsCommentController(BaseViewHolder holder, CommodityEvaluationBean.DataBean commentData, Context context, String goods_id) {
        this.holder = holder;
        this.commentData = commentData;
        this.context = context;
        this.goods_id = goods_id;
        initView();
    }

    public static boolean isFloat(String input) {
        return input.matches("\\d+\\.\\d*");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        Intent intent = new Intent(this.context, (Class<?>) GoodsCommentActivity.class);
        intent.putExtra("goods_id", "" + this.goods_id);
        this.context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(View view) {
        Intent intent = new Intent(this.context, (Class<?>) GoodsCommentActivity.class);
        intent.putExtra("goods_id", "" + this.goods_id);
        this.context.startActivity(intent);
    }

    public void addGradren(String grade, LinearLayout mlinrojut) throws NumberFormatException {
        mlinrojut.removeAllViews();
        float f2 = Float.parseFloat(grade);
        int i2 = 0;
        if (f2 - 1.0f <= 0.0f) {
            while (i2 < 5) {
                View imageView = new ImageView(this.context);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(CommonUtil.dip2px(this.context, 11.0f), CommonUtil.dip2px(this.context, 11.0f));
                layoutParams.rightMargin = CommonUtil.dip2px(this.context, 5.0f);
                imageView.setLayoutParams(layoutParams);
                if (i2 == 0) {
                    if (SkinManager.getCurrentSkinType(this.context) == 0) {
                        imageView.setBackgroundResource(R.drawable.xing);
                    } else {
                        imageView.setBackgroundResource(R.drawable.xing_night);
                    }
                } else if (SkinManager.getCurrentSkinType(this.context) == 0) {
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
                View imageView2 = new ImageView(this.context);
                LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(CommonUtil.dip2px(this.context, 11.0f), CommonUtil.dip2px(this.context, 11.0f));
                layoutParams2.rightMargin = CommonUtil.dip2px(this.context, 5.0f);
                imageView2.setLayoutParams(layoutParams2);
                if (f2 > i2) {
                    if (SkinManager.getCurrentSkinType(this.context) == 0) {
                        imageView2.setBackgroundResource(R.drawable.xing);
                    } else {
                        imageView2.setBackgroundResource(R.drawable.xing_night);
                    }
                } else if (SkinManager.getCurrentSkinType(this.context) == 0) {
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
            ImageView imageView3 = new ImageView(this.context);
            LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(CommonUtil.dip2px(this.context, 11.0f), CommonUtil.dip2px(this.context, 11.0f));
            layoutParams3.rightMargin = CommonUtil.dip2px(this.context, 5.0f);
            imageView3.setLayoutParams(layoutParams3);
            float f4 = i2;
            if (f3 > f4 || f4 == f3) {
                if (SkinManager.getCurrentSkinType(this.context) == 0) {
                    imageView3.setBackgroundResource(R.drawable.xing);
                } else {
                    Drawable drawable = ContextCompat.getDrawable(this.context, R.drawable.xing_night);
                    if (drawable != null) {
                        drawable.setColorFilter(new PorterDuffColorFilter(Color.parseColor("#B2575C"), PorterDuff.Mode.SRC_IN));
                        imageView3.setImageDrawable(drawable);
                    } else {
                        imageView3.setBackgroundResource(R.drawable.xing_night);
                    }
                }
            } else if (SkinManager.getCurrentSkinType(this.context) == 0) {
                imageView3.setBackgroundResource(R.drawable.kongxing);
            } else {
                imageView3.setBackgroundResource(R.drawable.kongxing_night);
            }
            mlinrojut.addView(imageView3);
            i2++;
        }
    }

    public void initData(CommodityEvaluationBean.DataBean commentData) {
        this.all_say_tv.setText(String.format(" (%s)", commentData.getCount().getAll()));
        try {
            if (!TextUtils.equals(commentData.getCount().getAll(), "") && !TextUtils.equals(commentData.getCount().getFine(), "")) {
                this.tv_all_haoping.setText(Math.round((Double.parseDouble(commentData.getCount().getFine()) / Double.parseDouble(commentData.getCount().getAll())) * 100.0d) + "%");
            }
            if (commentData.getTime_line().size() > 0) {
                this.cat_all_tv.setVisibility(0);
                this.tv_all_haoping.setVisibility(0);
                this.textView6.setVisibility(0);
            }
        } catch (Exception unused) {
            this.tv_all_haoping.setText("0%");
        }
        BaseQuickAdapter<CommodityEvaluationBean.DataBean.TimeLineBean, BaseViewHolder> baseQuickAdapter = this.adapter;
        if (baseQuickAdapter != null) {
            baseQuickAdapter.setList(commentData.getTime_line());
        }
    }

    public void initView() {
        this.textView6 = (TextView) this.holder.getView(R.id.textView6);
        this.rl_dingwei = (RelativeLayout) this.holder.getView(R.id.rl_dingwei);
        this.all_say_tv = (TextView) this.holder.getView(R.id.all_say_tv);
        this.tv_all_haoping = (TextView) this.holder.getView(R.id.tv_all_haoping);
        RecyclerView recyclerView = (RecyclerView) this.holder.getView(R.id.recycleview);
        this.recycleview = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this.context));
        this.cat_all_tv = (TextView) this.holder.getView(R.id.cat_all_tv);
        this.rl_dingwei.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.b
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13692c.lambda$initView$0(view);
            }
        });
        this.cat_all_tv.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.c
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13693c.lambda$initView$1(view);
            }
        });
        BaseQuickAdapter<CommodityEvaluationBean.DataBean.TimeLineBean, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<CommodityEvaluationBean.DataBean.TimeLineBean, BaseViewHolder>(R.layout.layout_goods_comment_item) { // from class: com.psychiatrygarden.activity.purchase.GoodsCommentController.1

            /* renamed from: com.psychiatrygarden.activity.purchase.GoodsCommentController$1$1, reason: invalid class name and collision with other inner class name */
            public class C02711 extends BaseQuickAdapter<String, BaseViewHolder> {
                public C02711(int layoutResId) {
                    super(layoutResId);
                }

                /* JADX INFO: Access modifiers changed from: private */
                public static /* synthetic */ void lambda$convert$0(BaseViewHolder baseViewHolder, ImageViewerPopupView imageViewerPopupView, int i2) {
                    imageViewerPopupView.updateSrcView((ImageView) ((RecyclerView) baseViewHolder.itemView.getParent()).getChildAt(i2));
                }

                /* JADX INFO: Access modifiers changed from: private */
                public /* synthetic */ void lambda$convert$1(RoundedImageView roundedImageView, final BaseViewHolder baseViewHolder, View view) {
                    ArrayList arrayList = new ArrayList();
                    if (getData().size() > 0) {
                        arrayList.addAll(getData());
                    }
                    if (arrayList.size() > 0) {
                        ImageViewerPopupViewCustom longPressListener = new ImageViewerPopupViewCustom(GoodsCommentController.this.context).setSrcView(roundedImageView, baseViewHolder.getBindingAdapterPosition()).setImageUrls(arrayList).isInfinite(false).isShowPlaceholder(true).setPlaceholderColor(-1).setPlaceholderStrokeColor(-1).setPlaceholderRadius(-1).isShowSaveButton(true).setBgColor(Color.rgb(32, 36, 46)).setSrcViewUpdateListener(new OnSrcViewUpdateListener() { // from class: com.psychiatrygarden.activity.purchase.e
                            @Override // com.lxj.xpopup.interfaces.OnSrcViewUpdateListener
                            public final void onSrcViewUpdate(ImageViewerPopupView imageViewerPopupView, int i2) {
                                GoodsCommentController.AnonymousClass1.C02711.lambda$convert$0(baseViewHolder, imageViewerPopupView, i2);
                            }
                        }).setXPopupImageLoader(new ImageLoaderUtilsCustom()).setLongPressListener(null);
                        longPressListener.popupInfo = new PopupInfo();
                        longPressListener.show();
                    }
                }

                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(@NotNull final BaseViewHolder baseViewHolder, String s2) {
                    final RoundedImageView roundedImageView = (RoundedImageView) baseViewHolder.findView(R.id.img);
                    Glide.with(GoodsCommentController.this.context).load((Object) GlideUtils.generateUrl(s2)).placeholder(new ColorDrawable(ContextCompat.getColor(getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).into(roundedImageView);
                    roundedImageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.d
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f13694c.lambda$convert$1(roundedImageView, baseViewHolder, view);
                        }
                    });
                }
            }

            @Override // com.chad.library.adapter.base.BaseQuickAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
            public int getItemCount() {
                if (getData().size() > 3) {
                    return 3;
                }
                return getData().size();
            }

            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(@NotNull BaseViewHolder baseViewHolder, CommodityEvaluationBean.DataBean.TimeLineBean timeLineBean) throws NumberFormatException {
                CircleImageView circleImageView = (CircleImageView) baseViewHolder.getView(R.id.headerimg);
                TextView textView = (TextView) baseViewHolder.getView(R.id.nickname);
                TextView textView2 = (TextView) baseViewHolder.getView(R.id.content);
                TextView textView3 = (TextView) baseViewHolder.getView(R.id.source);
                LinearLayout linearLayout = (LinearLayout) baseViewHolder.getView(R.id.lingradview);
                RecyclerView recyclerView2 = (RecyclerView) baseViewHolder.getView(R.id.imageRecycleview);
                recyclerView2.setLayoutManager(new GridLayoutManager(GoodsCommentController.this.context, 3));
                C02711 c02711 = new C02711(R.layout.layout_goods_comment_img);
                recyclerView2.setAdapter(c02711);
                Glide.with(circleImageView.getContext()).load((Object) GlideUtils.generateUrl(timeLineBean.getAvatar())).placeholder(new ColorDrawable(ContextCompat.getColor(circleImageView.getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).into(circleImageView);
                textView.setText(timeLineBean.getNickname());
                textView2.setText(timeLineBean.getContent());
                textView3.setText(timeLineBean.getBuy_time());
                GoodsCommentController.this.addGradren(timeLineBean.getGrade(), linearLayout);
                if (timeLineBean.getImgs().size() > 0) {
                    recyclerView2.setVisibility(0);
                    c02711.setList(timeLineBean.getImgs());
                } else {
                    recyclerView2.setVisibility(8);
                    c02711.setList(new ArrayList());
                }
            }
        };
        this.adapter = baseQuickAdapter;
        this.recycleview.setAdapter(baseQuickAdapter);
    }
}
