package com.psychiatrygarden.adapter;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Animatable;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter;
import com.catchpig.mvvm.utils.DateUtil;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.activity.ActCourseOrGoodsDetail;
import com.psychiatrygarden.activity.LoginActivity;
import com.psychiatrygarden.activity.courselist.CourseCombineDireListActivity;
import com.psychiatrygarden.activity.courselist.bean.CurriculumItemBean;
import com.psychiatrygarden.activity.courselist.course.CourseDirectoryActivity;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.utils.BaseViewHolderUtilKt;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.FlowLayout;
import com.psychiatrygarden.utils.FlowLayoutUtil;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.CircleImageView;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.psychiatrygarden.widget.showSalesedPopWindow;
import com.yikaobang.yixue.R;
import com.ykb.ebook.util.DateUtilKt;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes5.dex */
public class CourseStructureAdapter extends BaseMultiItemQuickAdapter<CurriculumItemBean.DataDTO, BaseViewHolder> {
    private boolean haveLine;
    private String keyword;
    private boolean lastItemAddMarginBottom;
    private Activity mActivity;
    public String title;

    /* renamed from: com.psychiatrygarden.adapter.CourseStructureAdapter$1, reason: invalid class name */
    public class AnonymousClass1 extends BaseQuickAdapter<CurriculumItemBean.DataDTO.TeacherDTO, com.aliyun.svideo.common.baseAdapter.BaseViewHolder> {
        final /* synthetic */ CurriculumItemBean.DataDTO val$dataDTO;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(int layoutResId, List data, final CurriculumItemBean.DataDTO val$dataDTO) {
            super(layoutResId, data);
            this.val$dataDTO = val$dataDTO;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(CurriculumItemBean.DataDTO dataDTO, View view) {
            if (CourseStructureAdapter.this.isLogin()) {
                if ("1".equals(dataDTO.getIs_sale())) {
                    new XPopup.Builder(CourseStructureAdapter.this.mActivity).asCustom(new showSalesedPopWindow(CourseStructureAdapter.this.mActivity)).show();
                } else if ("1".equals(dataDTO.getIs_alone_sale())) {
                    CourseStructureAdapter.this.goToSchedule(dataDTO);
                } else {
                    CourseStructureAdapter.this.goToSchedule(dataDTO);
                }
            }
        }

        @Override // com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            CurriculumItemBean.DataDTO dataDTO = this.val$dataDTO;
            if (dataDTO == null || dataDTO.getTeacher() == null || this.val$dataDTO.getTeacher().size() <= 3) {
                return super.getItemCount();
            }
            return 3;
        }

        @Override // com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter
        public void convert(com.aliyun.svideo.common.baseAdapter.BaseViewHolder helper, CurriculumItemBean.DataDTO.TeacherDTO item) {
            CircleImageView circleImageView = (CircleImageView) helper.getView(R.id.pimg);
            ImageView imageView = (ImageView) helper.getView(R.id.moretview);
            TextView textView = (TextView) helper.getView(R.id.name);
            if (CourseStructureAdapter.this.mActivity != null) {
                GlideApp.with(CourseStructureAdapter.this.mActivity).load((Object) GlideUtils.generateUrl(item.getAvatar())).into(circleImageView);
            }
            if (helper.getBindingAdapterPosition() == 3) {
                circleImageView.setVisibility(4);
                textView.setVisibility(4);
                imageView.setVisibility(0);
                textView.setText("");
            } else {
                textView.setText(item.getName());
                circleImageView.setVisibility(0);
                textView.setVisibility(0);
                imageView.setVisibility(8);
            }
            View view = helper.itemView;
            final CurriculumItemBean.DataDTO dataDTO = this.val$dataDTO;
            view.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.y3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f15192c.lambda$convert$0(dataDTO, view2);
                }
            });
        }
    }

    /* renamed from: com.psychiatrygarden.adapter.CourseStructureAdapter$2, reason: invalid class name */
    public class AnonymousClass2 extends BaseQuickAdapter<CurriculumItemBean.DataDTO, com.aliyun.svideo.common.baseAdapter.BaseViewHolder> {
        final /* synthetic */ CurriculumItemBean.DataDTO val$dataDTO;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(int layoutResId, List data, final CurriculumItemBean.DataDTO val$dataDTO) {
            super(layoutResId, data);
            this.val$dataDTO = val$dataDTO;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(CurriculumItemBean.DataDTO dataDTO, View view) {
            if ("1".equals(dataDTO.getIs_sale())) {
                new XPopup.Builder(CourseStructureAdapter.this.mActivity).asCustom(new showSalesedPopWindow(CourseStructureAdapter.this.mActivity)).show();
            } else if (CourseStructureAdapter.this.isLogin()) {
                CourseStructureAdapter.this.goToSchedule(dataDTO);
            }
        }

        @Override // com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            if (this.val$dataDTO.getIsOpen() != 0 || super.getItemCount() <= 3) {
                return super.getItemCount();
            }
            return 3;
        }

        @Override // com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter
        public void convert(com.aliyun.svideo.common.baseAdapter.BaseViewHolder helper, final CurriculumItemBean.DataDTO item) throws Resources.NotFoundException {
            TextView textView = (TextView) helper.getView(R.id.title);
            ImageView imageView = (ImageView) helper.getView(R.id.statusTv);
            textView.setCompoundDrawables(null, null, null, null);
            imageView.setVisibility(8);
            if (((Animatable) imageView.getDrawable()).isRunning()) {
                ((Animatable) imageView.getDrawable()).stop();
            }
            Activity activity = CourseStructureAdapter.this.mActivity;
            boolean zIsNight = CourseStructureAdapter.this.isNight();
            int i2 = R.drawable.allsimg_night_new;
            CommonUtil.mDoDrawable(activity, textView, zIsNight ? R.drawable.allsimg_night_new : R.drawable.allsimg_new, 0);
            if (1 == item.getType() && item.getLive_start_time() > 0 && item.getLive_end_time() > 0) {
                long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
                if (jCurrentTimeMillis > item.getLive_start_time() && jCurrentTimeMillis < item.getLive_end_time()) {
                    imageView.setVisibility(0);
                    imageView.setImageResource(CourseStructureAdapter.this.isNight() ? R.drawable.load_lv_anim_new_night : R.drawable.load_lv_anim_new);
                    ((Animatable) imageView.getDrawable()).start();
                    textView.setCompoundDrawables(null, null, null, null);
                } else if (jCurrentTimeMillis < item.getLive_start_time()) {
                    CommonUtil.mDoDrawable(CourseStructureAdapter.this.mActivity, textView, CourseStructureAdapter.this.isNight() ? R.drawable.atmostimg_night_new : R.drawable.atmostimg_new, 0);
                } else {
                    Activity activity2 = CourseStructureAdapter.this.mActivity;
                    if (!CourseStructureAdapter.this.isNight()) {
                        i2 = R.drawable.allsimg_new;
                    }
                    CommonUtil.mDoDrawable(activity2, textView, i2, 0);
                }
            } else if (3 == item.getType()) {
                CommonUtil.mDoDrawable(CourseStructureAdapter.this.mActivity, textView, SkinManager.getCurrentSkinType(CourseStructureAdapter.this.mActivity) == 1 ? R.drawable.hj2img_night_new : R.drawable.hj2img_new, 0);
            }
            textView.setText(Html.fromHtml(item.getTitle()));
            helper.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.z3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f15227c.lambda$convert$0(item, view);
                }
            });
        }
    }

    public CourseStructureAdapter(Activity activity) {
        this.keyword = "";
        this.haveLine = true;
        this.lastItemAddMarginBottom = false;
        addItemType(1, R.layout.layout_curriculum_item_new);
        addItemType(2, R.layout.layout_curriculum_item_new);
        addItemType(4, R.layout.layout_curriculum_item_new);
        addItemType(3, R.layout.layout_curriculum_all_item_new);
        this.mActivity = activity;
    }

    private List<Integer> getKeywordIndex(String content, String searchContent) {
        ArrayList arrayList = new ArrayList();
        Matcher matcher = Pattern.compile(Pattern.quote(searchContent)).matcher(content);
        while (matcher.find()) {
            arrayList.add(Integer.valueOf(matcher.start()));
        }
        return arrayList;
    }

    private SpannableStringBuilder getSpannableString(SpannableStringBuilder stringBuilder, String content, String keyword) throws Resources.NotFoundException {
        if (stringBuilder == null) {
            stringBuilder = new SpannableStringBuilder(content);
        }
        if (!TextUtils.isEmpty(keyword) && !TextUtils.isEmpty(content)) {
            List<Integer> keywordIndex = getKeywordIndex(content, keyword);
            for (int i2 = 0; i2 < keywordIndex.size(); i2++) {
                int color = getContext().getResources().getColor(R.color.main_theme_color_night);
                int color2 = getContext().getResources().getColor(R.color.main_theme_color);
                if (SkinManager.getCurrentSkinType(getContext()) != 1) {
                    color = color2;
                }
                stringBuilder.setSpan(new ForegroundColorSpan(color), keywordIndex.get(i2).intValue(), keywordIndex.get(i2).intValue() + keyword.length(), 17);
            }
        }
        return stringBuilder;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isNight() {
        return SkinManager.getCurrentSkinType(this.mActivity) == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$mAllContainView$2(CurriculumItemBean.DataDTO dataDTO, View view) {
        CourseCombineDireListActivity.Companion companion = CourseCombineDireListActivity.INSTANCE;
        Activity activity = this.mActivity;
        String id = dataDTO.getId();
        Objects.requireNonNull(id);
        companion.startActivity(activity, id);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$mAllContainView$3(CurriculumItemBean.DataDTO dataDTO, ImageView imageView, BaseQuickAdapter baseQuickAdapter, View view) {
        if (dataDTO.getIsOpen() == 0) {
            dataDTO.setIsOpen(1);
            if (SkinManager.getCurrentSkinType(this.mActivity) == 1) {
                imageView.setImageResource(R.drawable.open2img_night);
            } else {
                imageView.setImageResource(R.drawable.open2img);
            }
        } else {
            dataDTO.setIsOpen(0);
            if (SkinManager.getCurrentSkinType(this.mActivity) == 1) {
                imageView.setImageResource(R.drawable.close2img_night);
            } else {
                imageView.setImageResource(R.drawable.close2img);
            }
        }
        baseQuickAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$mAllContainView$4(CurriculumItemBean.DataDTO dataDTO, ImageView imageView, BaseQuickAdapter baseQuickAdapter, View view) {
        if (dataDTO.getIsOpen() == 0) {
            dataDTO.setIsOpen(1);
            if (SkinManager.getCurrentSkinType(this.mActivity) == 1) {
                imageView.setImageResource(R.drawable.open2img_night);
            } else {
                imageView.setImageResource(R.drawable.open2img);
            }
        } else {
            dataDTO.setIsOpen(0);
            if (SkinManager.getCurrentSkinType(this.mActivity) == 1) {
                imageView.setImageResource(R.drawable.close2img_night);
            } else {
                imageView.setImageResource(R.drawable.close2img);
            }
        }
        baseQuickAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$mAllContainView$5(View view) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$mAllContainView$6(CurriculumItemBean.DataDTO dataDTO, View view) {
        if (isLogin()) {
            ActCourseOrGoodsDetail.INSTANCE.navigationToCourseOrGoodsDetail(this.mActivity, dataDTO.getId(), "");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$mFirstContainView$0(CurriculumItemBean.DataDTO dataDTO, View view) {
        CourseDirectoryActivity.goToCourseDirectoryActivity(this.mActivity, dataDTO.getId(), dataDTO.getType() + "");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$mFirstContainView$1(CurriculumItemBean.DataDTO dataDTO, View view) {
        if (isLogin()) {
            ActCourseOrGoodsDetail.INSTANCE.navigationToCourseOrGoodsDetail(this.mActivity, dataDTO.getId(), "");
        }
    }

    public void goToSchedule(CurriculumItemBean.DataDTO dataDTO) {
        if (isLogin()) {
            ActCourseOrGoodsDetail.INSTANCE.navigationToCourseOrGoodsDetail(this.mActivity, dataDTO.getId(), "");
        }
    }

    public boolean isLogin() {
        if (!UserConfig.getUserId().equals("")) {
            return true;
        }
        this.mActivity.startActivity(new Intent(this.mActivity, (Class<?>) LoginActivity.class));
        return false;
    }

    public void mAllContainView(BaseViewHolder baseViewHolder, final CurriculumItemBean.DataDTO dataDTO) {
        TextView textView;
        String string;
        Activity activity;
        int i2;
        String str;
        Activity activity2;
        int i3;
        TextView textView2;
        FlowLayout flowLayout = (FlowLayout) baseViewHolder.getView(R.id.course_combine_flags);
        LinearLayout linearLayout = (LinearLayout) baseViewHolder.getView(R.id.course_combine_flags_layout);
        TextView textView3 = (TextView) baseViewHolder.getView(R.id.titleTv);
        TextView textView4 = (TextView) baseViewHolder.getView(R.id.jidoortv);
        TextView textView5 = (TextView) baseViewHolder.getView(R.id.numTv);
        TextView textView6 = (TextView) baseViewHolder.getView(R.id.priceTv);
        TextView textView7 = (TextView) baseViewHolder.getView(R.id.originPrice);
        textView7.getPaint().setFlags(16);
        textView7.setVisibility(8);
        final ImageView imageView = (ImageView) baseViewHolder.getView(R.id.imgopenbottom);
        LinearLayout linearLayout2 = (LinearLayout) baseViewHolder.getView(R.id.line);
        RecyclerView recyclerView = (RecyclerView) baseViewHolder.getView(R.id.recyall);
        TextView textView8 = (TextView) baseViewHolder.getView(R.id.buttonStartLearn);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.mActivity));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        int customerLayoutPosition = BaseViewHolderUtilKt.getCustomerLayoutPosition(baseViewHolder) + 1;
        if (customerLayoutPosition < 10) {
            StringBuilder sb = new StringBuilder();
            textView = textView4;
            sb.append("0");
            sb.append(customerLayoutPosition);
            string = sb.toString();
        } else {
            textView = textView4;
            string = "" + customerLayoutPosition;
        }
        baseViewHolder.setText(R.id.tv_index, string);
        View viewFindView = baseViewHolder.findView(R.id.viewLine);
        if (customerLayoutPosition == getData().size()) {
            viewFindView.setVisibility(8);
        } else {
            viewFindView.setVisibility(this.haveLine ? 0 : 8);
        }
        List<CurriculumItemBean.CourseLabel> label = dataDTO.getLabel();
        flowLayout.removeAllViews();
        FlowLayoutUtil.initChildLabelViews(flowLayout, label, this.mActivity);
        linearLayout.setVisibility(flowLayout.getVisibility() == 0 ? 0 : 8);
        String str2 = "&  " + dataDTO.getTitle() + "";
        textView3.setText(getSpannableString(CommonUtil.setSpanImg(this.mActivity, SkinManager.getCurrentSkinType(this.mActivity) == 1 ? R.drawable.moreimg_night_new : R.drawable.moreimg_new, 0, 1, 33, str2), str2, this.keyword));
        if ("1".equals(dataDTO.getIs_permission())) {
            textView8.setVisibility(0);
            textView8.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.t3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f15026c.lambda$mAllContainView$2(dataDTO, view);
                }
            });
            linearLayout2.setVisibility(8);
        } else {
            textView8.setVisibility(8);
            linearLayout2.setVisibility(0);
            textView6.setText(dataDTO.getButton_text());
            if (dataDTO.getButton_text_grey() == 1) {
                if (isNight()) {
                    activity2 = this.mActivity;
                    i3 = R.color.forth_txt_color_night;
                } else {
                    activity2 = this.mActivity;
                    i3 = R.color.forth_txt_color;
                }
                textView6.setTextColor(activity2.getColor(i3));
            } else {
                if (isNight()) {
                    activity = this.mActivity;
                    i2 = R.color.main_theme_color_night;
                } else {
                    activity = this.mActivity;
                    i2 = R.color.main_theme_color;
                }
                textView6.setTextColor(activity.getColor(i2));
            }
            if (TextUtils.isEmpty(dataDTO.getSales_volume())) {
                str = "0人购买";
            } else {
                str = dataDTO.getSales_volume() + "人购买";
            }
            textView5.setText(str);
        }
        List list = (List) dataDTO.getSetMeal();
        final AnonymousClass2 anonymousClass2 = new AnonymousClass2(R.layout.layout_curriculum_all_child_item, list, dataDTO);
        recyclerView.setAdapter(anonymousClass2);
        if (list == null || list.size() <= 3) {
            textView2 = textView;
            textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.w3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    CourseStructureAdapter.lambda$mAllContainView$5(view);
                }
            });
            imageView.setVisibility(4);
        } else {
            imageView.setVisibility(0);
            if (dataDTO.getIsOpen() == 0) {
                if (SkinManager.getCurrentSkinType(this.mActivity) == 1) {
                    imageView.setImageResource(R.drawable.close2img_night);
                } else {
                    imageView.setImageResource(R.drawable.close2img);
                }
            } else if (SkinManager.getCurrentSkinType(this.mActivity) == 1) {
                imageView.setImageResource(R.drawable.open2img_night);
            } else {
                imageView.setImageResource(R.drawable.open2img);
            }
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.u3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f15060c.lambda$mAllContainView$3(dataDTO, imageView, anonymousClass2, view);
                }
            });
            textView2 = textView;
            textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.v3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f15096c.lambda$mAllContainView$4(dataDTO, imageView, anonymousClass2, view);
                }
            });
        }
        textView2.setText(list.size() + "门课程");
        if (this.lastItemAddMarginBottom) {
            LinearLayout linearLayout3 = (LinearLayout) baseViewHolder.getView(R.id.layoutRoot);
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) linearLayout3.getLayoutParams();
            if (BaseViewHolderUtilKt.getCustomerAdapterPosition(baseViewHolder) + 1 == getData().size()) {
                marginLayoutParams.bottomMargin = CommonUtil.dip2px(this.mActivity, 40.0f);
            } else {
                marginLayoutParams.bottomMargin = CommonUtil.dip2px(this.mActivity, 0.0f);
            }
            linearLayout3.setLayoutParams(marginLayoutParams);
        }
        BaseViewHolderUtilKt.getCustomerItemView(baseViewHolder).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.x3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15158c.lambda$mAllContainView$6(dataDTO, view);
            }
        });
    }

    public void mFirstContainView(BaseViewHolder baseViewHolder, final CurriculumItemBean.DataDTO dataDTO) throws Resources.NotFoundException {
        StringBuilder sb;
        Activity activity;
        int i2;
        String str;
        Activity activity2;
        int i3;
        int customerLayoutPosition = BaseViewHolderUtilKt.getCustomerLayoutPosition(baseViewHolder) + 1;
        if (customerLayoutPosition < 10) {
            sb = new StringBuilder();
            sb.append("0");
            sb.append(customerLayoutPosition);
        } else {
            sb = new StringBuilder();
            sb.append(customerLayoutPosition);
            sb.append("");
        }
        baseViewHolder.setText(R.id.tv_index, sb.toString());
        View viewFindView = baseViewHolder.findView(R.id.viewLine);
        if (customerLayoutPosition == getData().size()) {
            viewFindView.setVisibility(8);
        } else {
            viewFindView.setVisibility(this.haveLine ? 0 : 8);
        }
        FlowLayout flowLayout = (FlowLayout) baseViewHolder.getView(R.id.course_combine_flags);
        LinearLayout linearLayout = (LinearLayout) baseViewHolder.getView(R.id.course_combine_flags_layout);
        TextView textView = (TextView) baseViewHolder.getView(R.id.titleTv);
        ImageView imageView = (ImageView) baseViewHolder.getView(R.id.statusTv);
        TextView textView2 = (TextView) baseViewHolder.getView(R.id.timeTv);
        RecyclerView recyclerView = (RecyclerView) baseViewHolder.getView(R.id.recy);
        TextView textView3 = (TextView) baseViewHolder.getView(R.id.buttonStartLearn);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), 0, false));
        TextView textView4 = (TextView) baseViewHolder.getView(R.id.priceTv);
        TextView textView5 = (TextView) baseViewHolder.getView(R.id.originPrice);
        textView5.setVisibility(8);
        TextView textView6 = (TextView) baseViewHolder.getView(R.id.numTv);
        List<CurriculumItemBean.CourseLabel> label = dataDTO.getLabel();
        flowLayout.removeAllViews();
        FlowLayoutUtil.initChildLabelViews(flowLayout, label, getContext());
        linearLayout.setVisibility(flowLayout.getVisibility() == 0 ? 0 : 8);
        if ("1".equals(dataDTO.getIs_permission())) {
            textView3.setVisibility(0);
            textView3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.r3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f14946c.lambda$mFirstContainView$0(dataDTO, view);
                }
            });
            textView4.setVisibility(8);
            textView5.setVisibility(8);
            textView6.setVisibility(8);
        } else {
            textView3.setVisibility(8);
            textView4.setVisibility(0);
            textView6.setVisibility(0);
            textView4.setText(dataDTO.getButton_text());
            if (dataDTO.getButton_text_grey() == 1) {
                if (isNight()) {
                    activity2 = this.mActivity;
                    i3 = R.color.forth_txt_color_night;
                } else {
                    activity2 = this.mActivity;
                    i3 = R.color.forth_txt_color;
                }
                textView4.setTextColor(activity2.getColor(i3));
            } else {
                if (isNight()) {
                    activity = this.mActivity;
                    i2 = R.color.main_theme_color_night;
                } else {
                    activity = this.mActivity;
                    i2 = R.color.main_theme_color;
                }
                textView4.setTextColor(activity.getColor(i2));
            }
            if (TextUtils.isEmpty(dataDTO.getSales_volume())) {
                str = "0人购买";
            } else {
                str = dataDTO.getSales_volume() + "人购买";
            }
            textView6.setText(str);
        }
        if ("1".equals(dataDTO.getIs_best_sellers())) {
            String str2 = "&  " + dataDTO.getTitle();
            textView.setText(getSpannableString(CommonUtil.setSpanImg(this.mActivity, SkinManager.getCurrentSkinType(this.mActivity) == 1 ? R.drawable.bestimg_night : R.drawable.bestimg, 0, 1, 33, str2), str2, this.keyword));
        } else {
            textView.setText(getSpannableString(null, dataDTO.getTitle(), this.keyword));
        }
        textView2.setCompoundDrawables(null, null, null, null);
        String str3 = dataDTO.getDescription() + "";
        imageView.setVisibility(8);
        if (((Animatable) imageView.getDrawable()).isRunning()) {
            ((Animatable) imageView.getDrawable()).stop();
        }
        if (dataDTO.getType() != 1 || dataDTO.getLive_start_time() <= 0) {
            textView2.setText(str3);
        } else {
            long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
            if (jCurrentTimeMillis > dataDTO.getLive_start_time() && jCurrentTimeMillis < dataDTO.getLive_end_time()) {
                imageView.setVisibility(0);
                imageView.setImageResource(isNight() ? R.drawable.load_lv_anim_new_night : R.drawable.load_lv_anim_new);
                ((Animatable) imageView.getDrawable()).start();
            } else if (jCurrentTimeMillis > dataDTO.getLive_end_time()) {
                CommonUtil.mDoDrawable(this.mActivity, textView2, isNight() ? R.drawable.icon_live_cutting_night : R.drawable.icon_live_cutting_day, 0);
            } else {
                CommonUtil.mDoDrawable(this.mActivity, textView2, isNight() ? R.drawable.atmostimg_night_new : R.drawable.atmostimg_new, 0);
            }
            long live_start_time = dataDTO.getLive_start_time();
            long live_end_time = dataDTO.getLive_end_time();
            str3 = dataDTO.getLive_title() + "，" + DateUtilKt.formatLongTimeStampToString(live_start_time * 1000, DateUtil.TIME_FORMAT_MONTH_DAY_HM) + "-" + DateUtilKt.formatLongTimeStampToString(live_end_time * 1000, DateUtil.TIME_FORMAT_WITH_HM);
            textView2.setText(Html.fromHtml(str3));
        }
        textView2.setVisibility(TextUtils.isEmpty(str3) ? 8 : 0);
        recyclerView.setAdapter(new AnonymousClass1(R.layout.layout_curriculum_teacher_item, dataDTO.getTeacher(), dataDTO));
        if (dataDTO.getTeacher().isEmpty()) {
            recyclerView.setVisibility(8);
        } else {
            recyclerView.setVisibility(0);
        }
        BaseViewHolderUtilKt.getCustomerItemView(baseViewHolder).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.s3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14988c.lambda$mFirstContainView$1(dataDTO, view);
            }
        });
        RelativeLayout relativeLayout = (RelativeLayout) baseViewHolder.getView(R.id.layoutRoot);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) relativeLayout.getLayoutParams();
        if (this.lastItemAddMarginBottom) {
            if (BaseViewHolderUtilKt.getCustomerAdapterPosition(baseViewHolder) + 1 == getData().size()) {
                marginLayoutParams.bottomMargin = CommonUtil.dip2px(this.mActivity, 40.0f);
            } else {
                marginLayoutParams.bottomMargin = CommonUtil.dip2px(this.mActivity, 0.0f);
            }
            relativeLayout.setLayoutParams(marginLayoutParams);
        }
    }

    public void setHaveLine(boolean haveLine) {
        this.haveLine = haveLine;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setLastItemAddMarginBottom(boolean lastItemAddMarginBottom) {
        this.lastItemAddMarginBottom = lastItemAddMarginBottom;
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NotNull BaseViewHolder baseViewHolder, CurriculumItemBean.DataDTO dataDTO) throws Resources.NotFoundException {
        int customerItemViewType = BaseViewHolderUtilKt.getCustomerItemViewType(baseViewHolder);
        if (customerItemViewType != 1 && customerItemViewType != 2) {
            if (customerItemViewType == 3) {
                mAllContainView(baseViewHolder, dataDTO);
                return;
            } else if (customerItemViewType != 4) {
                return;
            }
        }
        mFirstContainView(baseViewHolder, dataDTO);
    }

    public CourseStructureAdapter(Activity activity, boolean haveLine) {
        this.keyword = "";
        this.haveLine = true;
        this.lastItemAddMarginBottom = false;
        addItemType(1, R.layout.layout_curriculum_item_new2);
        addItemType(2, R.layout.layout_curriculum_item_new2);
        addItemType(4, R.layout.layout_curriculum_item_new2);
        addItemType(3, R.layout.layout_curriculum_all_item_new2);
        this.mActivity = activity;
        this.haveLine = haveLine;
    }
}
