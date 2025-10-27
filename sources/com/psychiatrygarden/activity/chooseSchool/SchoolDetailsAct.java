package com.psychiatrygarden.activity.chooseSchool;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.aliyun.svideo.common.utils.FastClickUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hjq.permissions.Permission;
import com.hyphenate.easeui.utils.StatusBarCompat;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.WebLongSaveActivity;
import com.psychiatrygarden.activity.chooseSchool.SchoolDetailsAct;
import com.psychiatrygarden.activity.chooseSchool.util.AliYunLogUtil;
import com.psychiatrygarden.activity.vip.Utils.MemInterface;
import com.psychiatrygarden.bean.ChooseSchoolServerCustomer;
import com.psychiatrygarden.bean.EnrollmentData;
import com.psychiatrygarden.bean.GetPermissionBean;
import com.psychiatrygarden.bean.NewHomeKingKongItem;
import com.psychiatrygarden.bean.OnlineServiceBean;
import com.psychiatrygarden.bean.SchoolDetailsBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.BuyVipSuccessEvent;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.AliyunEvent;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.PublicMethodActivity;
import com.psychiatrygarden.utils.QrCodeUtils;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.utils.weblong.SizeUtil;
import com.psychiatrygarden.widget.ChooseSchoolFormLeftItemView;
import com.psychiatrygarden.widget.ChooseSchoolOpenMajorItemView;
import com.psychiatrygarden.widget.ChooseSchoolScoreLineItemView;
import com.psychiatrygarden.widget.CircleImageView;
import com.psychiatrygarden.widget.ReadMoreArrowIcon;
import com.psychiatrygarden.widget.RequestMediaPermissionPop;
import com.psychiatrygarden.widget.ViewPager2Indicator;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.umeng.socialize.Config;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMMin;
import com.yikaobang.yixue.R;
import com.ykb.common_share_lib.CommonConfig;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class SchoolDetailsAct extends BaseActivity {
    private String activityId;
    private String admissionWebsite;
    private TextView mBtnCancel;
    private LinearLayout mBtnEnrollmentLink;
    private LinearLayout mBtnQq;
    private LinearLayout mBtnSave;
    private LinearLayout mBtnSchoolLink;
    private LinearLayout mBtnSina;
    private LinearLayout mBtnWechat;
    private LinearLayout mBtnWxCircle;
    private ImageView mImgBack;
    private View mImgBg;
    private ImageView mImgCodeBig;
    private ImageView mImgCollection;
    private CircleImageView mImgShareSchoolCover;
    private CircleImageView mImgUserCover;
    private View mLineOne;
    private View mLineTwo;
    private LinearLayout mLyAddLeftView;
    private LinearLayout mLyAddLeftViewTwo;
    private LinearLayout mLyAddMajorView;
    private LinearLayout mLyAddView;
    private LinearLayout mLyAddViewTwo;
    private LinearLayout mLyBottom;
    private LinearLayout mLyBottomShareView;
    private LinearLayout mLyCollection;
    private LinearLayout mLyFeedBack;
    private RelativeLayout mLyFeedBackView;
    private RelativeLayout mLyLine;
    private RelativeLayout mLyLineTwo;
    private LinearLayout mLyMore;
    private LinearLayout mLyMoreMajor;
    private LinearLayout mLyMoreTwo;
    private LinearLayout mLyOpenMajorView;
    private LinearLayout mLyScoreOneView;
    private LinearLayout mLyScoreTwoView;
    private LinearLayout mLyShare;
    private LinearLayout mLyShareContentView;
    private LinearLayout mLyShareTag;
    private RelativeLayout mLyShareView;
    private LinearLayout mLyTag;
    private View mOpenVipThreeView;
    private View mOpenVipTwoView;
    private View mOpenVipView;
    private CircleImageView mSchoolHead;
    private String mSchoolId;
    private NestedScrollView mScrollView;
    private TextView mTvAddressDetail;
    private ReadMoreArrowIcon mTvDesc;
    private TextView mTvEmail;
    private TextView mTvEmailCode;
    private TextView mTvFollowCount;
    private TextView mTvFollowCountSeven;
    private TextView mTvMajorCount;
    private TextView mTvSchoolAddress;
    private TextView mTvSchoolCode;
    private TextView mTvSchoolName;
    private TextView mTvShareAddress;
    private TextView mTvShareCode;
    private TextView mTvShareInfo;
    private TextView mTvShareSchoolName;
    private TextView mTvStudentCount;
    private TextView mTvUserName;
    private TextView mTvViewCount;
    private View mViewShadow;
    private View mViewShadowTwo;
    private String officialWebsite;
    private String permission;
    private String schoolCover;
    private String schoolDesc;
    private String schoolName;
    private HorizontalScrollView scoreLineScroll;
    private HorizontalScrollView scoreLineScrollTwo;
    private OnlineServiceBean serviceBean;
    private ViewPager2Indicator treeIndicator;
    private ViewPager2 vpKingKong;
    private final Map<Integer, View> kingkongItemsViews = new ArrayMap();
    private String mFollowStatus = "0";
    private final BaseQuickAdapter<List<NewHomeKingKongItem>, BaseViewHolder> mAdapter = new BaseQuickAdapter<List<NewHomeKingKongItem>, BaseViewHolder>(R.layout.home_kk_tab) { // from class: com.psychiatrygarden.activity.chooseSchool.SchoolDetailsAct.7
        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NotNull BaseViewHolder holder, List<NewHomeKingKongItem> item) {
            RecyclerView recyclerView = (RecyclerView) holder.getView(R.id.rvKingKong);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 5, 1, false));
            BaseQuickAdapter adapter = SchoolDetailsAct.this.getAdapter();
            adapter.setList(item);
            recyclerView.setAdapter(adapter);
        }
    };
    private boolean str1v1Success = false;
    private String str1v1QrCode = "";
    private String str1v1QrId = "";

    /* renamed from: com.psychiatrygarden.activity.chooseSchool.SchoolDetailsAct$3, reason: invalid class name */
    public class AnonymousClass3 extends AjaxCallBack<String> {
        public AnonymousClass3() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onSuccess$0(int i2) {
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String s2) {
            List list;
            super.onSuccess((AnonymousClass3) s2);
            try {
                JSONObject jSONObject = new JSONObject(s2);
                if (!TextUtils.equals(jSONObject.optString("code"), "200") || (list = (List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<NewHomeKingKongItem>>() { // from class: com.psychiatrygarden.activity.chooseSchool.SchoolDetailsAct.3.1
                }.getType())) == null || list.size() <= 0) {
                    return;
                }
                ArrayList arrayList = new ArrayList();
                if (list.size() <= 5) {
                    arrayList.add(0, list);
                } else if (list.size() > 10) {
                    int i2 = 0;
                    while (i2 < list.size()) {
                        int i3 = i2 + 10;
                        arrayList.add(list.subList(i2, Math.min(list.size(), i3)));
                        i2 = i3;
                    }
                } else if (list.size() < 7) {
                    arrayList.add(list.subList(0, 5));
                    arrayList.add(list.subList(5, list.size()));
                } else {
                    arrayList.add(list);
                }
                if (list.size() == 6 || list.size() > 10) {
                    SchoolDetailsAct.this.treeIndicator.setVisibility(0);
                    SchoolDetailsAct.this.treeIndicator.bindViewPager2(SchoolDetailsAct.this.vpKingKong, new ViewPager2Indicator.PageSelectListener() { // from class: com.psychiatrygarden.activity.chooseSchool.i5
                        @Override // com.psychiatrygarden.widget.ViewPager2Indicator.PageSelectListener
                        public final void onPageSelect(int i4) {
                            SchoolDetailsAct.AnonymousClass3.lambda$onSuccess$0(i4);
                        }
                    });
                } else {
                    SchoolDetailsAct.this.treeIndicator.setVisibility(8);
                }
                SchoolDetailsAct.this.mAdapter.setList(arrayList);
                SchoolDetailsAct.this.vpKingKong.setAdapter(SchoolDetailsAct.this.mAdapter);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* renamed from: com.psychiatrygarden.activity.chooseSchool.SchoolDetailsAct$8, reason: invalid class name */
    public class AnonymousClass8 extends BaseQuickAdapter<NewHomeKingKongItem, BaseViewHolder> {
        final /* synthetic */ int val$itemWidth;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass8(int layoutResId, final int val$itemWidth) {
            super(layoutResId);
            this.val$itemWidth = val$itemWidth;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(NewHomeKingKongItem newHomeKingKongItem, View view) {
            if (CommonUtil.isFastClick()) {
                return;
            }
            newHomeKingKongItem.setSchool_id(SchoolDetailsAct.this.mSchoolId);
            SchoolDetailsAct schoolDetailsAct = SchoolDetailsAct.this;
            schoolDetailsAct.jumpToDetail(newHomeKingKongItem, schoolDetailsAct.permission);
            AliYunLogUtil.getInstance().addLogTouchName(AliyunEvent.UniversityBannerArea, newHomeKingKongItem.getName());
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NotNull BaseViewHolder holder, final NewHomeKingKongItem item) {
            if (((View) SchoolDetailsAct.this.kingkongItemsViews.get(Integer.valueOf(holder.getLayoutPosition()))) == null) {
                SchoolDetailsAct.this.kingkongItemsViews.put(Integer.valueOf(holder.getLayoutPosition()), holder.itemView);
            }
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
            ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = holder.getLayoutPosition() > 4 ? SizeUtil.dp2px(SchoolDetailsAct.this.mContext, 8) : 0;
            ((ViewGroup.MarginLayoutParams) layoutParams).width = this.val$itemWidth;
            holder.itemView.setLayoutParams(layoutParams);
            holder.setText(R.id.tv_name, item.getName());
            TextView textView = (TextView) holder.getView(R.id.tv_tag);
            CircleImageView circleImageView = (CircleImageView) holder.getView(R.id.img_icon);
            SizeUtil.dp2px(SchoolDetailsAct.this.mContext, 5);
            SizeUtil.dp2px(SchoolDetailsAct.this.mContext, 11);
            if (TextUtils.isEmpty(item.getLabel())) {
                textView.setVisibility(8);
            } else {
                textView.setVisibility(0);
                textView.setText(item.getLabel());
            }
            GlideApp.with(SchoolDetailsAct.this.mContext).load((Object) GlideUtils.generateUrl(item.getIcon())).placeholder(R.mipmap.ic_order_default).into(circleImageView);
            holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.j5
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f11330c.lambda$convert$0(item, view);
                }
            });
        }
    }

    private void addScoreLineOneView(List<EnrollmentData> dataList) {
        this.mLyScoreOneView.setVisibility(0);
        this.mLyAddLeftView.removeAllViews();
        for (int i2 = 0; i2 < dataList.size(); i2++) {
            ChooseSchoolFormLeftItemView chooseSchoolFormLeftItemView = new ChooseSchoolFormLeftItemView(this.mContext, true);
            chooseSchoolFormLeftItemView.setData(i2, dataList.get(i2), false);
            this.mLyAddLeftView.addView(chooseSchoolFormLeftItemView);
        }
        this.mLyAddView.removeAllViews();
        for (int i3 = 0; i3 < dataList.size(); i3++) {
            ChooseSchoolScoreLineItemView chooseSchoolScoreLineItemView = new ChooseSchoolScoreLineItemView(this.mContext, true);
            chooseSchoolScoreLineItemView.setData(i3, dataList.get(i3), false, false);
            this.mLyAddView.addView(chooseSchoolScoreLineItemView);
        }
    }

    private void addScoreLineTwoView(List<EnrollmentData> dataList) {
        this.mLyScoreTwoView.setVisibility(0);
        this.mLyAddLeftViewTwo.removeAllViews();
        for (int i2 = 0; i2 < dataList.size(); i2++) {
            ChooseSchoolFormLeftItemView chooseSchoolFormLeftItemView = new ChooseSchoolFormLeftItemView(this.mContext, true);
            chooseSchoolFormLeftItemView.setData(i2, dataList.get(i2), true);
            this.mLyAddLeftViewTwo.addView(chooseSchoolFormLeftItemView);
        }
        this.mLyAddViewTwo.removeAllViews();
        for (int i3 = 0; i3 < dataList.size(); i3++) {
            ChooseSchoolScoreLineItemView chooseSchoolScoreLineItemView = new ChooseSchoolScoreLineItemView(this.mContext, true);
            chooseSchoolScoreLineItemView.setData(i3, dataList.get(i3), false, true);
            this.mLyAddViewTwo.addView(chooseSchoolScoreLineItemView);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addTagView(List<String> attrs, LinearLayout view) {
        int pxByDp = ScreenUtil.getPxByDp((Context) this, 4);
        int pxByDp2 = ScreenUtil.getPxByDp((Context) this, 1);
        for (int i2 = 0; i2 < attrs.size(); i2++) {
            TextView textView = new TextView(this);
            textView.setTextSize(10.0f);
            textView.setText(attrs.get(i2));
            textView.setTextColor(getColor(SkinManager.getCurrentSkinType(this) == 1 ? R.color.main_theme_color_night : R.color.main_theme_color));
            textView.setPadding(pxByDp, pxByDp2, pxByDp, pxByDp2);
            textView.setBackgroundResource(R.drawable.shape_computer_time_bg);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
            layoutParams.rightMargin = ScreenUtil.getPxByDp((Context) this, 8);
            textView.setLayoutParams(layoutParams);
            view.addView(textView);
        }
    }

    private void cancleShare() {
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.mLyBottomShareView, "translationY", 0.0f, r0.getHeight());
        objectAnimatorOfFloat.setDuration(200L);
        objectAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: com.psychiatrygarden.activity.chooseSchool.SchoolDetailsAct.1
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                SchoolDetailsAct.this.mLyShareContentView.setVisibility(8);
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
            }
        });
        objectAnimatorOfFloat.start();
    }

    private void followOrCancelSchool(final boolean isFollow) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("target_id", this.mSchoolId);
        String str = NetworkRequestsURL.followSchool;
        if (isFollow) {
            str = NetworkRequestsURL.cancelFollowSchool;
        }
        ajaxParams.put("type", "1");
        YJYHttpUtils.post(this.mContext, str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.SchoolDetailsAct.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass4) s2);
                try {
                    if ("200".equals(new JSONObject(s2).optString("code"))) {
                        if (isFollow) {
                            SchoolDetailsAct.this.mFollowStatus = "0";
                            ToastUtil.shortToast(SchoolDetailsAct.this, "已取消");
                            SchoolDetailsAct.this.mImgCollection.setImageResource(SkinManager.getCurrentSkinType(SchoolDetailsAct.this.mContext) == 1 ? R.mipmap.ic_choose_school_collection_night : R.mipmap.ic_choose_school_collection);
                        } else {
                            SchoolDetailsAct.this.mFollowStatus = "1";
                            ToastUtil.shortToast(SchoolDetailsAct.this, "已关注");
                            SchoolDetailsAct.this.mImgCollection.setImageResource(SkinManager.getCurrentSkinType(SchoolDetailsAct.this.mContext) == 1 ? R.mipmap.ic_choose_school_had_collection_night : R.mipmap.ic_choose_school_had_collection);
                        }
                        AliYunLogUtil.getInstance().addLog(isFollow ? AliyunEvent.CancelFolloweSchool : AliyunEvent.FollowedInstitutions, SchoolDetailsAct.this.mSchoolId, SchoolDetailsAct.this.mTvSchoolName.getText().toString());
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public BaseQuickAdapter<NewHomeKingKongItem, BaseViewHolder> getAdapter() {
        return new AnonymousClass8(R.layout.item_school_detail_menu, getResources().getDisplayMetrics().widthPixels / 5);
    }

    private void getData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("school_id", this.mSchoolId);
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.schoolMenuList, ajaxParams, new AnonymousClass3());
    }

    private void getFeedBack() {
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.chooseSchoolFeedBackCS, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.SchoolDetailsAct.5
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass5) s2);
                try {
                    if ("200".equals(new JSONObject(s2).optString("code"))) {
                        ChooseSchoolServerCustomer chooseSchoolServerCustomer = (ChooseSchoolServerCustomer) new Gson().fromJson(new JSONObject(s2).optString("data"), ChooseSchoolServerCustomer.class);
                        if (TextUtils.isEmpty(chooseSchoolServerCustomer.is_show()) || !chooseSchoolServerCustomer.is_show().equals("1")) {
                            SchoolDetailsAct.this.mLyFeedBackView.setVisibility(8);
                        } else {
                            SchoolDetailsAct.this.mLyFeedBackView.setVisibility(0);
                            SchoolDetailsAct.this.serviceBean = chooseSchoolServerCustomer.getFeedback();
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private void getPermission(NewHomeKingKongItem item, String permission) {
        if (!TextUtils.isEmpty(permission) && permission.equals("1")) {
            PublicMethodActivity.getInstance().mToActivity(new Gson().toJson(item));
            return;
        }
        if (!item.getPush_type().equals("53")) {
            this.activityId = SharePreferencesUtils.readStrConfig(CommonParameter.CHOOSE_SCHOOL_ACTIVITY_ID, this);
            openVip();
        } else if (SharePreferencesUtils.readIntConfig(CommonParameter.CHOOSE_SCHOOL_HAVE_FREE_COUNT, this.mContext, 0) > 0) {
            PublicMethodActivity.getInstance().mToActivity(new Gson().toJson(item));
        } else {
            this.activityId = SharePreferencesUtils.readStrConfig(CommonParameter.CHOOSE_SCHOOL_ACTIVITY_ID, this);
            openVip();
        }
    }

    private void getSchoolDetails() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("school_id", this.mSchoolId);
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.schoolDetails, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.SchoolDetailsAct.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    SchoolDetailsBean schoolDetailsBean = (SchoolDetailsBean) new Gson().fromJson(s2, SchoolDetailsBean.class);
                    if (!schoolDetailsBean.getCode().equals("200")) {
                        ToastUtil.shortToast(SchoolDetailsAct.this.mContext, schoolDetailsBean.getMessage());
                        return;
                    }
                    if (schoolDetailsBean.getData() != null) {
                        SchoolDetailsAct.this.schoolName = schoolDetailsBean.getData().getTitle();
                        SchoolDetailsAct.this.schoolDesc = schoolDetailsBean.getData().getDesc();
                        SchoolDetailsAct.this.schoolCover = schoolDetailsBean.getData().getCover();
                        SharePreferencesUtils.writeIntConfig(CommonParameter.CHOOSE_SCHOOL_HAVE_FREE_COUNT, TextUtils.isEmpty(schoolDetailsBean.getData().getLast_count()) ? 0 : Integer.parseInt(schoolDetailsBean.getData().getLast_count()), SchoolDetailsAct.this.mContext);
                        SchoolDetailsAct.this.admissionWebsite = schoolDetailsBean.getData().getAdmission_website();
                        SchoolDetailsAct.this.officialWebsite = schoolDetailsBean.getData().getOfficial_website();
                        SchoolDetailsAct.this.mFollowStatus = schoolDetailsBean.getData().getFollow_status();
                        GlideApp.with(SchoolDetailsAct.this.mContext).load((Object) GlideUtils.generateUrl(SchoolDetailsAct.this.schoolCover)).placeholder(R.mipmap.ic_avatar_def).into(SchoolDetailsAct.this.mSchoolHead);
                        GlideApp.with(SchoolDetailsAct.this.mContext).load((Object) GlideUtils.generateUrl(SchoolDetailsAct.this.schoolCover)).placeholder(R.mipmap.ic_avatar_def).into(SchoolDetailsAct.this.mImgShareSchoolCover);
                        SchoolDetailsAct.this.mTvSchoolName.setText(SchoolDetailsAct.this.schoolName);
                        SchoolDetailsAct.this.mTvShareSchoolName.setText(SchoolDetailsAct.this.schoolName);
                        SchoolDetailsAct.this.mTvFollowCount.setText(schoolDetailsBean.getData().getFollow_count());
                        SchoolDetailsAct.this.mTvFollowCountSeven.setText(schoolDetailsBean.getData().getRecent_7days_follow());
                        SchoolDetailsAct.this.mTvViewCount.setText(schoolDetailsBean.getData().getView_count());
                        SchoolDetailsAct.this.mTvStudentCount.setText(schoolDetailsBean.getData().getStudent_count());
                        SchoolDetailsAct.this.mTvMajorCount.setText(schoolDetailsBean.getData().getMajor_count());
                        SchoolDetailsAct.this.mTvShareInfo.setText("为您推荐了\n" + SchoolDetailsAct.this.schoolName);
                        if (!TextUtils.isEmpty(SchoolDetailsAct.this.mFollowStatus) && SchoolDetailsAct.this.mFollowStatus.equals("1")) {
                            SchoolDetailsAct.this.mImgCollection.setImageResource(SkinManager.getCurrentSkinType(SchoolDetailsAct.this.mContext) == 1 ? R.mipmap.ic_choose_school_had_collection_night : R.mipmap.ic_choose_school_had_collection);
                        } else {
                            SchoolDetailsAct.this.mImgCollection.setImageResource(SkinManager.getCurrentSkinType(SchoolDetailsAct.this.mContext) == 1 ? R.mipmap.ic_choose_school_collection_night : R.mipmap.ic_choose_school_collection);
                        }
                        SchoolDetailsAct.this.mLyTag.removeAllViews();
                        if (schoolDetailsBean.getData().getAttr() == null || schoolDetailsBean.getData().getAttr().size() <= 0) {
                            SchoolDetailsAct.this.mLyTag.setVisibility(8);
                        } else {
                            SchoolDetailsAct.this.mLyTag.setVisibility(0);
                            SchoolDetailsAct.this.mLyShareTag.removeAllViews();
                            SchoolDetailsAct.this.addTagView(schoolDetailsBean.getData().getAttr(), SchoolDetailsAct.this.mLyTag);
                            SchoolDetailsAct.this.addTagView(schoolDetailsBean.getData().getAttr(), SchoolDetailsAct.this.mLyShareTag);
                        }
                        String address = "--";
                        String city_title = TextUtils.isEmpty(schoolDetailsBean.getData().getCity_title()) ? "--" : schoolDetailsBean.getData().getCity_title();
                        SchoolDetailsAct.this.mTvSchoolAddress.setVisibility(0);
                        SchoolDetailsAct.this.mTvSchoolAddress.setText("地址：" + city_title);
                        SchoolDetailsAct.this.mTvShareAddress.setText("地址：" + city_title);
                        String code = TextUtils.isEmpty(schoolDetailsBean.getData().getCode()) ? "--" : schoolDetailsBean.getData().getCode();
                        SchoolDetailsAct.this.mTvSchoolCode.setVisibility(0);
                        SchoolDetailsAct.this.mTvSchoolCode.setText("代码：" + code);
                        SchoolDetailsAct.this.mTvShareCode.setText("代码：" + code);
                        if (TextUtils.isEmpty(SchoolDetailsAct.this.schoolDesc)) {
                            SchoolDetailsAct.this.mTvDesc.setVisibility(8);
                        } else {
                            SchoolDetailsAct.this.mTvDesc.setVisibility(0);
                            SchoolDetailsAct.this.mTvDesc.setText(SchoolDetailsAct.this.schoolDesc);
                        }
                        SchoolDetailsAct.this.mTvEmailCode.setText(TextUtils.isEmpty(schoolDetailsBean.getData().getPostcode()) ? "--" : schoolDetailsBean.getData().getPostcode());
                        SchoolDetailsAct.this.mTvEmail.setText(TextUtils.isEmpty(schoolDetailsBean.getData().getEmail()) ? "--" : schoolDetailsBean.getData().getEmail());
                        TextView textView = SchoolDetailsAct.this.mTvAddressDetail;
                        if (!TextUtils.isEmpty(schoolDetailsBean.getData().getAddress())) {
                            address = schoolDetailsBean.getData().getAddress();
                        }
                        textView.setText(address);
                        SchoolDetailsAct.this.mLyAddMajorView.removeAllViews();
                        if (schoolDetailsBean.getData().getMajor_list() == null || schoolDetailsBean.getData().getMajor_list().size() <= 0) {
                            SchoolDetailsAct.this.mLyOpenMajorView.setVisibility(8);
                        } else {
                            SchoolDetailsAct.this.mLyOpenMajorView.setVisibility(0);
                            for (int i2 = 0; i2 < schoolDetailsBean.getData().getMajor_list().size(); i2++) {
                                ChooseSchoolOpenMajorItemView chooseSchoolOpenMajorItemView = new ChooseSchoolOpenMajorItemView(SchoolDetailsAct.this);
                                chooseSchoolOpenMajorItemView.setData(schoolDetailsBean.getData().getMajor_list().get(i2), SchoolDetailsAct.this.mSchoolId);
                                SchoolDetailsAct.this.mLyAddMajorView.addView(chooseSchoolOpenMajorItemView);
                            }
                        }
                        if (TextUtils.isEmpty(SchoolDetailsAct.this.schoolName) || TextUtils.isEmpty(SchoolDetailsAct.this.mSchoolId)) {
                            return;
                        }
                        AliYunLogUtil.getInstance().addLog(AliyunEvent.PreviewUniversities, SchoolDetailsAct.this.mSchoolId, SchoolDetailsAct.this.schoolName);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void jumpToDetail(NewHomeKingKongItem item, String permission) {
        if ("53".equals(item.getPush_type())) {
            getPermission(item, permission);
            return;
        }
        if ("54".equals(item.getPush_type())) {
            AliYunLogUtil.getInstance().addLog(AliyunEvent.CheckTheOfferedMajors, this.mSchoolId, this.mTvSchoolName.getText().toString());
            getPermission(item, permission);
        } else {
            if ("55".equals(item.getPush_type())) {
                getPermission(item, permission);
                return;
            }
            if ("56".equals(item.getPush_type())) {
                getPermission(item, permission);
            } else if ("57".equals(item.getPush_type())) {
                getPermission(item, permission);
            } else {
                PublicMethodActivity.getInstance().mToActivity(new Gson().toJson(item));
            }
        }
    }

    private void jumpToScoreLine() {
        FractionBarAct.newIntent(this, this.mSchoolId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(int i2, TextView textView, View view, NestedScrollView nestedScrollView, int i3, int i4, int i5, int i6) {
        if (i4 > i2) {
            if (this.mImgBg.getVisibility() == 0) {
                this.mImgBg.setVisibility(8);
                textView.setText(this.schoolName);
                view.setBackgroundColor(getColor(SkinManager.getCurrentSkinType(this.mContext) == 1 ? R.color.new_bg_one_color_night : R.color.new_bg_one_color));
                return;
            }
            return;
        }
        if (this.mImgBg.getVisibility() == 8) {
            textView.setText("院校详情");
            view.setBackgroundColor(0);
            this.mImgBg.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(View view, int i2, int i3, int i4, int i5) {
        if (i2 > 20) {
            if (this.mViewShadow.getVisibility() == 8) {
                this.mLyLine.setVisibility(8);
                this.mViewShadow.setVisibility(0);
                return;
            }
            return;
        }
        if (this.mViewShadow.getVisibility() == 0) {
            this.mViewShadow.setVisibility(8);
            this.mLyLine.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(View view, int i2, int i3, int i4, int i5) {
        if (i2 > 20) {
            if (this.mViewShadowTwo.getVisibility() == 8) {
                this.mLyLineTwo.setVisibility(8);
                this.mViewShadowTwo.setVisibility(0);
                return;
            }
            return;
        }
        if (this.mViewShadowTwo.getVisibility() == 0) {
            this.mViewShadowTwo.setVisibility(8);
            this.mLyLineTwo.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveFile$28() {
        ActivityCompat.requestPermissions(this, new String[]{Permission.CAMERA, Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE}, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$10(View view) {
        this.mLyShareContentView.setVisibility(0);
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.mLyBottomShareView, "translationY", 800.0f, 0.0f);
        objectAnimatorOfFloat.setDuration(200L);
        objectAnimatorOfFloat.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$11(View view) {
        jumpToScoreLine();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$12(View view) {
        jumpToScoreLine();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$13(View view) {
        SchoolOpenMajorAct.newIntent(this, this.mSchoolId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$14(View view) {
        openVip();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$15(View view) {
        openVip();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$16(View view) {
        openVip();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$17(View view) throws IOException {
        saveFile();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$18(View view) {
        shareAppControl(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$19(View view) {
        shareAppControl(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$20(View view) {
        shareAppControl(2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$21(View view) {
        shareAppControl(3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$22(View view) {
        cancleShare();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$23(View view) {
        cancleShare();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$3(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$4(View view) {
        AliYunLogUtil.getInstance().addLog(AliyunEvent.LookSchoolDetail, this.mSchoolId, this.mTvSchoolName.getText().toString());
        SchoolIntroductionAct.newIntent(this.mContext, true, this.schoolDesc, this.mTvSchoolName.getText().toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$5(View view) {
        if (TextUtils.isEmpty(this.admissionWebsite)) {
            ToastUtil.shortToast(this.mContext, "地址错误");
            return;
        }
        Intent intent = new Intent(this.mContext, (Class<?>) WebLongSaveActivity.class);
        intent.putExtra("title", "招生官网");
        intent.putExtra("web_url", this.admissionWebsite);
        this.mContext.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$6(View view) {
        if (TextUtils.isEmpty(this.officialWebsite)) {
            ToastUtil.shortToast(this.mContext, "地址错误");
            return;
        }
        Intent intent = new Intent(this.mContext, (Class<?>) WebLongSaveActivity.class);
        intent.putExtra("title", "学校官网");
        intent.putExtra("web_url", this.officialWebsite);
        this.mContext.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$7(View view) {
        if (this.str1v1Success) {
            CommonUtil.jump1v1(this.mContext, this.str1v1QrCode, this.str1v1QrId);
        } else {
            load1v1QrCode();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$8(View view) {
        if (FastClickUtil.isFastClick()) {
            return;
        }
        AliYunLogUtil.getInstance().addLog(AliyunEvent.EnterpriseWeChatFeedback);
        OnlineServiceBean onlineServiceBean = this.serviceBean;
        if (onlineServiceBean != null) {
            CommonUtil.onlineService(this, onlineServiceBean);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$9(View view) {
        followOrCancelSchool(!TextUtils.isEmpty(this.mFollowStatus) && this.mFollowStatus.equals("1"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setQrCode$26(Bitmap bitmap) {
        this.mImgCodeBig.setImageBitmap(bitmap);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setQrCode$27(int i2, String str, Bitmap bitmap) {
        final Bitmap bitmapCreateQRcodeImage = QrCodeUtils.createQRcodeImage(i2, i2, str, bitmap);
        runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.chooseSchool.z4
            @Override // java.lang.Runnable
            public final void run() {
                this.f11479c.lambda$setQrCode$26(bitmapCreateQRcodeImage);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$shareAppControl$25() {
        this.mLyShareContentView.setVisibility(8);
    }

    private void load1v1QrCode() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.chooseSchool1V1, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.SchoolDetailsAct.10
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass10) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if ("200".equals(jSONObject.optString("code"))) {
                        SchoolDetailsAct.this.str1v1Success = true;
                        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
                        if (jSONObjectOptJSONObject != null) {
                            SchoolDetailsAct.this.str1v1QrCode = jSONObjectOptJSONObject.optString("qr_code");
                            SchoolDetailsAct.this.str1v1QrId = jSONObjectOptJSONObject.optString("we_chat_id");
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public static void newIntent(Context context, String schoolId) {
        Intent intent = new Intent(context, (Class<?>) SchoolDetailsAct.class);
        intent.putExtra("schoolId", schoolId);
        context.startActivity(intent);
    }

    private void openVip() {
        if (TextUtils.isEmpty(this.activityId)) {
            return;
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, this.activityId);
        MemInterface.getInstance().setDisType(1);
        MemInterface.getInstance().getMemData((Activity) this, ajaxParams, NetworkRequestsURL.vipApi, 0, false);
        MemInterface.getInstance().setmUShareListener(new MemInterface.UShareListener() { // from class: com.psychiatrygarden.activity.chooseSchool.v4
            @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.UShareListener
            public final void mUShareListener() {
                this.f11437a.lambda$openVip$24();
            }
        });
    }

    private void saveFile() throws IOException {
        if (ContextCompat.checkSelfPermission(this, Permission.CAMERA) == 0 && ContextCompat.checkSelfPermission(this, Permission.WRITE_EXTERNAL_STORAGE) == 0) {
            saveViewToGallery(this, this.mLyShareView);
        } else {
            new XPopup.Builder(this).asCustom(new RequestMediaPermissionPop(this, new OnConfirmListener() { // from class: com.psychiatrygarden.activity.chooseSchool.w4
                @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                public final void onConfirm() {
                    this.f11443a.lambda$saveFile$28();
                }
            })).show();
        }
    }

    private void saveViewToGallery(Context context, View view) throws IOException {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(bitmapCreateBitmap));
        String str = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/" + ("ykb_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".jpg");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(str);
            bitmapCreateBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            MediaScannerConnection.scanFile(context, new String[]{str}, null, null);
            ToastUtil.shortToast(context, "保存成功");
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    private void setQrCode() {
        final Bitmap bitmapDecodeResource = BitmapFactory.decodeResource(getResources(), R.drawable.app_icon);
        final int iDp2px = SizeUtil.dp2px(this, 80);
        final String str = "https://m.yikaobang.com.cn/download/yikaobang.html";
        new Thread(new Runnable() { // from class: com.psychiatrygarden.activity.chooseSchool.x4
            @Override // java.lang.Runnable
            public final void run() {
                this.f11460c.lambda$setQrCode$27(iDp2px, str, bitmapDecodeResource);
            }
        }).start();
    }

    private void shareToFriend(int i2, String page) {
        UMImage uMImage = TextUtils.isEmpty(this.schoolCover) ? new UMImage(this.mContext, R.drawable.ic_launcher) : new UMImage(this.mContext, this.schoolCover);
        UMMin uMMin = new UMMin(this.schoolCover);
        uMMin.setThumb(uMImage);
        uMMin.setTitle(this.schoolName);
        uMMin.setDescription(this.schoolDesc);
        uMMin.setPath(page);
        uMMin.setUserName("gh_14d59acf6877");
        if (CommonConfig.INSTANCE.getYI_KAO_BANG_NETWORK() == 0) {
            Config.setMiniPreView();
        }
        new ShareAction(this).withMedia(uMMin).setPlatform(BaseActivity.platforms.get(i2).mPlatform).share();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: updateUiByPermission, reason: merged with bridge method [inline-methods] */
    public void lambda$openVip$24() {
        SharePreferencesUtils.writeStrConfig(CommonParameter.CHOOSE_SCHOOL_HAVE_PERMISSION, "1", this);
        getData();
        this.permission = "1";
        this.mOpenVipView.setVisibility(8);
        this.mOpenVipTwoView.setVisibility(8);
        this.mOpenVipThreeView.setVisibility(8);
        this.mLyMore.setVisibility(0);
        this.mLyMoreTwo.setVisibility(0);
        this.mLyMoreMajor.setVisibility(0);
        this.mLineOne.setVisibility(8);
        this.mLineTwo.setVisibility(8);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.mSchoolId = getIntent().getStringExtra("schoolId");
        final TextView textView = (TextView) findViewById(R.id.txt_actionbar_title);
        this.mImgBack = (ImageView) findViewById(R.id.iv_actionbar_back);
        final View viewFindViewById = findViewById(R.id.tabbar);
        this.mScrollView = (NestedScrollView) findViewById(R.id.scroll_view);
        this.mSchoolHead = (CircleImageView) findViewById(R.id.school_head);
        this.mTvSchoolName = (TextView) findViewById(R.id.tv_school_name);
        this.mLyTag = (LinearLayout) findViewById(R.id.ly_tag);
        this.mTvSchoolAddress = (TextView) findViewById(R.id.tv_school_address);
        this.mTvSchoolCode = (TextView) findViewById(R.id.tv_school_code);
        this.vpKingKong = (ViewPager2) findViewById(R.id.vpKingKong);
        this.treeIndicator = (ViewPager2Indicator) findViewById(R.id.tree_indicator);
        this.mTvDesc = (ReadMoreArrowIcon) findViewById(R.id.tv_desc);
        this.mTvEmailCode = (TextView) findViewById(R.id.tv_email_code);
        this.mTvEmail = (TextView) findViewById(R.id.tv_email);
        this.mTvAddressDetail = (TextView) findViewById(R.id.tv_address_detail);
        this.mBtnEnrollmentLink = (LinearLayout) findViewById(R.id.btn_enrollment_link);
        this.mBtnSchoolLink = (LinearLayout) findViewById(R.id.btn_school_link);
        this.mLyBottom = (LinearLayout) findViewById(R.id.ly_bottom);
        this.mLyFeedBackView = (RelativeLayout) findViewById(R.id.ly_feedback_view);
        this.mLyFeedBack = (LinearLayout) findViewById(R.id.ly_feedback);
        this.mLyCollection = (LinearLayout) findViewById(R.id.ly_collection);
        this.mLyShare = (LinearLayout) findViewById(R.id.ly_share);
        this.mImgBg = findViewById(R.id.img_bg);
        this.mLyAddLeftView = (LinearLayout) findViewById(R.id.ly_add_left_view);
        this.mLyAddView = (LinearLayout) findViewById(R.id.ly_add_view);
        this.scoreLineScroll = (HorizontalScrollView) findViewById(R.id.score_line_scroll);
        this.mViewShadow = findViewById(R.id.line_shadow);
        this.mLyLine = (RelativeLayout) findViewById(R.id.ly_line);
        this.mLyAddLeftViewTwo = (LinearLayout) findViewById(R.id.ly_add_left_view_two);
        this.mLyAddViewTwo = (LinearLayout) findViewById(R.id.ly_add_view_two);
        this.scoreLineScrollTwo = (HorizontalScrollView) findViewById(R.id.score_line_scroll_two);
        this.mViewShadowTwo = findViewById(R.id.line_shadow_two);
        this.mLyLineTwo = (RelativeLayout) findViewById(R.id.ly_line_two);
        this.mLyAddMajorView = (LinearLayout) findViewById(R.id.ly_add_major);
        this.mLyMore = (LinearLayout) findViewById(R.id.ly_more);
        this.mLyMoreTwo = (LinearLayout) findViewById(R.id.ly_more_two);
        this.mLyMoreMajor = (LinearLayout) findViewById(R.id.ly_more_major);
        this.mLyOpenMajorView = (LinearLayout) findViewById(R.id.ly_open_major);
        this.mImgCollection = (ImageView) findViewById(R.id.img_collection);
        this.mLyScoreOneView = (LinearLayout) findViewById(R.id.ly_score_one);
        this.mLyScoreTwoView = (LinearLayout) findViewById(R.id.ly_score_two);
        this.mLineOne = findViewById(R.id.line_one);
        this.mLineTwo = findViewById(R.id.line_two);
        this.mOpenVipView = findViewById(R.id.ly_open_vip);
        this.mOpenVipTwoView = findViewById(R.id.ly_open_vip_two);
        this.mOpenVipThreeView = findViewById(R.id.ly_open_vip_three);
        this.mImgUserCover = (CircleImageView) findViewById(R.id.user_cover);
        this.mTvUserName = (TextView) findViewById(R.id.tv_username);
        this.mImgShareSchoolCover = (CircleImageView) findViewById(R.id.img_share_school_cover);
        this.mTvShareSchoolName = (TextView) findViewById(R.id.tv_share_school_name);
        this.mLyShare = (LinearLayout) findViewById(R.id.ly_share);
        this.mTvShareAddress = (TextView) findViewById(R.id.tv_share_school_address);
        this.mTvShareCode = (TextView) findViewById(R.id.tv_share_school_code);
        this.mTvFollowCount = (TextView) findViewById(R.id.tv_follow_count);
        this.mTvFollowCountSeven = (TextView) findViewById(R.id.tv_follow_count_7);
        this.mTvViewCount = (TextView) findViewById(R.id.tv_view_count);
        this.mTvStudentCount = (TextView) findViewById(R.id.tv_student_count);
        this.mTvMajorCount = (TextView) findViewById(R.id.tv_major_count);
        this.mImgCodeBig = (ImageView) findViewById(R.id.img_code_big);
        this.mLyShareTag = (LinearLayout) findViewById(R.id.ly_share_tag);
        this.mLyShareView = (RelativeLayout) findViewById(R.id.ly_share_view);
        this.mBtnSave = (LinearLayout) findViewById(R.id.btn_save);
        this.mBtnWechat = (LinearLayout) findViewById(R.id.btn_wechat);
        this.mBtnWxCircle = (LinearLayout) findViewById(R.id.btn_wxcircle);
        this.mBtnQq = (LinearLayout) findViewById(R.id.btn_qq);
        this.mBtnSina = (LinearLayout) findViewById(R.id.btn_sina);
        this.mLyShareContentView = (LinearLayout) findViewById(R.id.ly_share_content);
        this.mLyBottomShareView = (LinearLayout) findViewById(R.id.ly_bottom_share);
        this.mTvShareInfo = (TextView) findViewById(R.id.tv_share_info);
        this.mBtnCancel = (TextView) findViewById(R.id.btn_cancel);
        int statusBarHeight = StatusBarUtil.getStatusBarHeight(this.mContext);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewFindViewById.getLayoutParams();
        layoutParams.topMargin = statusBarHeight;
        viewFindViewById.setLayoutParams(layoutParams);
        this.treeIndicator.setVisibility(8);
        if (SharePreferencesUtils.readStrConfig(CommonParameter.CHOOSE_SCHOOL_1v1, this, "0").equals("1")) {
            this.mLyBottom.setVisibility(0);
        } else {
            this.mLyBottom.setVisibility(8);
        }
        if (UserConfig.isLogin()) {
            this.mTvUserName.setText(UserConfig.getInstance().getUser().getNickname());
            GlideApp.with(this.mContext).load((Object) GlideUtils.generateUrl(UserConfig.getInstance().getUser().getAvatar())).placeholder(R.mipmap.ic_avatar_def).into(this.mImgUserCover);
        }
        final int pxByDp = ScreenUtil.getPxByDp(this.mContext, 140) + statusBarHeight;
        this.mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() { // from class: com.psychiatrygarden.activity.chooseSchool.s4
            @Override // androidx.core.widget.NestedScrollView.OnScrollChangeListener
            public final void onScrollChange(NestedScrollView nestedScrollView, int i2, int i3, int i4, int i5) {
                this.f11405a.lambda$init$0(pxByDp, textView, viewFindViewById, nestedScrollView, i2, i3, i4, i5);
            }
        });
        this.scoreLineScroll.setOnScrollChangeListener(new View.OnScrollChangeListener() { // from class: com.psychiatrygarden.activity.chooseSchool.t4
            @Override // android.view.View.OnScrollChangeListener
            public final void onScrollChange(View view, int i2, int i3, int i4, int i5) {
                this.f11415c.lambda$init$1(view, i2, i3, i4, i5);
            }
        });
        this.scoreLineScrollTwo.setOnScrollChangeListener(new View.OnScrollChangeListener() { // from class: com.psychiatrygarden.activity.chooseSchool.u4
            @Override // android.view.View.OnScrollChangeListener
            public final void onScrollChange(View view, int i2, int i3, int i4, int i5) {
                this.f11426c.lambda$init$2(view, i2, i3, i4, i5);
            }
        });
        getPermission();
        getSchoolDetails();
        getData();
        getFeedBack();
        setQrCode();
        load1v1QrCode();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setStatusBarTranslucent(this, false);
        StatusBarCompat.setLightStatusBar(this, true);
        this.mActionBar.hide();
    }

    @Subscribe
    public void onEventMainThread(BuyVipSuccessEvent event) {
        if (event.getSuccess()) {
            UserConfig.getInstance().getUser().setIs_vip("1");
            lambda$openVip$24();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (1 == requestCode && grantResults.length > 0 && grantResults[0] == -1) {
            boolean zShouldShowRequestPermissionRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, Permission.CAMERA);
            boolean zShouldShowRequestPermissionRationale2 = ActivityCompat.shouldShowRequestPermissionRationale(this, Permission.WRITE_EXTERNAL_STORAGE);
            if (zShouldShowRequestPermissionRationale || zShouldShowRequestPermissionRationale2) {
                return;
            }
            ToastUtil.shortToast(this, "请检查app相机及存储权限是否打开！");
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layout_school_details);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mImgBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.f4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11259c.lambda$setListenerForWidget$3(view);
            }
        });
        this.mTvDesc.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.h4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11306c.lambda$setListenerForWidget$4(view);
            }
        });
        this.mBtnEnrollmentLink.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.j4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11329c.lambda$setListenerForWidget$5(view);
            }
        });
        this.mBtnSchoolLink.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.k4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11338c.lambda$setListenerForWidget$6(view);
            }
        });
        this.mLyBottom.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.l4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11346c.lambda$setListenerForWidget$7(view);
            }
        });
        this.mLyFeedBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.m4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11354c.lambda$setListenerForWidget$8(view);
            }
        });
        this.mLyCollection.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.n4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11365c.lambda$setListenerForWidget$9(view);
            }
        });
        this.mLyShare.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.o4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11373c.lambda$setListenerForWidget$10(view);
            }
        });
        this.mLyMore.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.p4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11381c.lambda$setListenerForWidget$11(view);
            }
        });
        this.mLyMoreTwo.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.r4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11398c.lambda$setListenerForWidget$12(view);
            }
        });
        this.mLyMoreMajor.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.q4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11389c.lambda$setListenerForWidget$13(view);
            }
        });
        this.mOpenVipView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.a5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11188c.lambda$setListenerForWidget$14(view);
            }
        });
        this.mOpenVipTwoView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.b5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11220c.lambda$setListenerForWidget$15(view);
            }
        });
        this.mOpenVipThreeView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.c5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11232c.lambda$setListenerForWidget$16(view);
            }
        });
        this.mBtnSave.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.d5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws IOException {
                this.f11241c.lambda$setListenerForWidget$17(view);
            }
        });
        this.mBtnWechat.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.e5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11251c.lambda$setListenerForWidget$18(view);
            }
        });
        this.mBtnWxCircle.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.f5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11260c.lambda$setListenerForWidget$19(view);
            }
        });
        this.mBtnQq.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.g5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11297c.lambda$setListenerForWidget$20(view);
            }
        });
        this.mBtnSina.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.h5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11307c.lambda$setListenerForWidget$21(view);
            }
        });
        this.mLyShareContentView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.g4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11296c.lambda$setListenerForWidget$22(view);
            }
        });
        this.mBtnCancel.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.i4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11321c.lambda$setListenerForWidget$23(view);
            }
        });
    }

    public void shareAppControl(int i2) {
        this.mLyShareContentView.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.chooseSchool.y4
            @Override // java.lang.Runnable
            public final void run() {
                this.f11472c.lambda$shareAppControl$25();
            }
        }, 1000L);
        if (i2 == 0) {
            shareToFriend(i2, "pages/index/startPage");
            return;
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(this.mLyShareView.getWidth(), this.mLyShareView.getHeight(), Bitmap.Config.ARGB_8888);
        this.mLyShareView.draw(new Canvas(bitmapCreateBitmap));
        new ShareAction(this).withMedia(new UMImage(this.mContext, bitmapCreateBitmap)).setPlatform(BaseActivity.platforms.get(i2).mPlatform).setCallback(new UMShareListener() { // from class: com.psychiatrygarden.activity.chooseSchool.SchoolDetailsAct.9
            @Override // com.umeng.socialize.UMShareListener
            public void onCancel(SHARE_MEDIA shareMedia) {
                ToastUtil.shortToast(SchoolDetailsAct.this.mContext, "用户取消分享");
            }

            @Override // com.umeng.socialize.UMShareListener
            public void onError(SHARE_MEDIA shareMedia, Throwable throwable) {
                ToastUtil.shortToast(SchoolDetailsAct.this.mContext, "未检测到QQ/微信应用或请安装正式版QQ/微信分享！");
            }

            @Override // com.umeng.socialize.UMShareListener
            public void onResult(SHARE_MEDIA shareMedia) {
                ToastUtil.shortToast(SchoolDetailsAct.this.mContext, "分享成功");
            }

            @Override // com.umeng.socialize.UMShareListener
            public void onStart(SHARE_MEDIA shareMedia) {
            }
        }).share();
    }

    private void getPermission() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.chooseSchoolPermissionBtn, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.SchoolDetailsAct.6
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass6) s2);
                try {
                    GetPermissionBean getPermissionBean = (GetPermissionBean) new Gson().fromJson(s2, GetPermissionBean.class);
                    if (!getPermissionBean.getCode().equals("200") || getPermissionBean.getData() == null) {
                        return;
                    }
                    String free_times = getPermissionBean.getData().getFree_times();
                    if (TextUtils.isEmpty(free_times) || Integer.parseInt(free_times) <= 0) {
                        SchoolDetailsAct.this.permission = getPermissionBean.getData().getPermission();
                    } else {
                        SchoolDetailsAct.this.permission = "1";
                    }
                    boolean zEquals = SchoolDetailsAct.this.permission.equals("1");
                    SharePreferencesUtils.writeStrConfig(CommonParameter.CHOOSE_SCHOOL_HAVE_PERMISSION, SchoolDetailsAct.this.permission, SchoolDetailsAct.this.mContext);
                    if (zEquals) {
                        SchoolDetailsAct.this.mOpenVipView.setVisibility(8);
                        SchoolDetailsAct.this.mOpenVipTwoView.setVisibility(8);
                        SchoolDetailsAct.this.mOpenVipThreeView.setVisibility(8);
                        return;
                    }
                    SchoolDetailsAct.this.activityId = getPermissionBean.getData().getActivity_id();
                    SchoolDetailsAct.this.mLineOne.setVisibility(0);
                    SchoolDetailsAct.this.mLineTwo.setVisibility(0);
                    SchoolDetailsAct.this.mLyMore.setVisibility(8);
                    SchoolDetailsAct.this.mLyMoreTwo.setVisibility(8);
                    SchoolDetailsAct.this.mLyMoreMajor.setVisibility(8);
                    SchoolDetailsAct.this.mOpenVipView.setVisibility(0);
                    SchoolDetailsAct.this.mOpenVipTwoView.setVisibility(0);
                    SchoolDetailsAct.this.mOpenVipThreeView.setVisibility(0);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }
}
