package com.psychiatrygarden.activity.chooseSchool;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.MediaScannerConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
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
import com.aliyun.svideo.common.utils.FastClickUtil;
import com.aliyun.vod.common.utils.UriUtil;
import com.google.gson.Gson;
import com.hjq.permissions.Permission;
import com.hyphenate.easeui.utils.StatusBarCompat;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.chooseSchool.MajorBySchoolAct;
import com.psychiatrygarden.activity.chooseSchool.adapter.ChooseSchoolSearchAdp;
import com.psychiatrygarden.activity.chooseSchool.util.AliYunLogUtil;
import com.psychiatrygarden.bean.ChooseSchoolFilterBeanList;
import com.psychiatrygarden.bean.ChooseSchoolServerCustomer;
import com.psychiatrygarden.bean.EnrollmentData;
import com.psychiatrygarden.bean.ForumFilterBean;
import com.psychiatrygarden.bean.OnlineServiceBean;
import com.psychiatrygarden.bean.SchoolEnrollmentDepartmentBean;
import com.psychiatrygarden.bean.SchoolScoreLineBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.AliyunEvent;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.QrCodeUtils;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.utils.weblong.SizeUtil;
import com.psychiatrygarden.widget.ChooseFilterPopuWindow;
import com.psychiatrygarden.widget.ChooseSchoolFormLeftItemView;
import com.psychiatrygarden.widget.CircleImageView;
import com.psychiatrygarden.widget.GetMajorScoreInfoItemView;
import com.psychiatrygarden.widget.RecruitmentInformationItemView;
import com.psychiatrygarden.widget.RecruitmentInformationLeftItemView;
import com.psychiatrygarden.widget.RequestMediaPermissionPop;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
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
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class MajorBySchoolAct extends BaseActivity {
    private int actionbar;
    private String mBatch;
    private TextView mBtnCancel;
    private LinearLayout mBtnQq;
    private LinearLayout mBtnSave;
    private LinearLayout mBtnSina;
    private LinearLayout mBtnWechat;
    private LinearLayout mBtnWxCircle;
    private String mDepartment;
    private ImageView mImgBack;
    private ImageView mImgCodeBig;
    private ImageView mImgCollection;
    private CircleImageView mImgShareSchoolCover;
    private CircleImageView mImgUserCover;
    private LinearLayout mLyAddLeftView;
    private LinearLayout mLyAddLeftViewTwo;
    private LinearLayout mLyAddView;
    private LinearLayout mLyAddViewTwo;
    private LinearLayout mLyBottomShareView;
    private LinearLayout mLyCollection;
    private LinearLayout mLyFeedBack;
    private RelativeLayout mLyFeedBackView;
    private RelativeLayout mLyInfoView;
    private RelativeLayout mLyLine;
    private RelativeLayout mLyScoreLineView;
    private LinearLayout mLyShare;
    private RelativeLayout mLyShareView;
    private String mMajorId;
    private CircleImageView mSchoolCover;
    private String mSchoolId;
    private NestedScrollView mScrollView;
    private ChooseSchoolSearchAdp mSearchAdp;
    private ChooseSchoolSearchAdp mSearchInfoAdp;
    private RecyclerView mSearchInfoRecycler;
    private RecyclerView mSearchRecycler;
    private TextView mTvCode;
    private TextView mTvSchoolName;
    private TextView mTvShareMajorCode;
    private TextView mTvShareMajorDesc;
    private TextView mTvShareMajorInfo;
    private TextView mTvShareMajorType;
    private TextView mTvShareSchoolName;
    private TextView mTvTitle;
    private TextView mTvType;
    private TextView mTvUserName;
    private String mType;
    private View mViewEmpty;
    private View mViewEmptyTwo;
    private View mViewShadow;
    private String mYear;
    private String majorName;
    private TextView navTitle;
    private String schoolCover;
    private String schoolName;
    private String scoreDepaerment;
    private HorizontalScrollView scoreLineScroll;
    private String scoreYear;
    private OnlineServiceBean serviceBean;
    private String typeDescribe;
    private String mFollowStatus = "0";
    private int viewHieght = 0;
    private List<ForumFilterBean.FilterDataBean> yearList = new ArrayList();
    private List<ForumFilterBean.FilterDataBean> departmentList = new ArrayList();
    private List<ForumFilterBean.FilterDataBean> batchList = new ArrayList();
    private List<ForumFilterBean.FilterDataBean> typeList = new ArrayList();

    /* renamed from: com.psychiatrygarden.activity.chooseSchool.MajorBySchoolAct$4, reason: invalid class name */
    public class AnonymousClass4 extends AjaxCallBack<String> {
        public AnonymousClass4() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onSuccess$0(View view) {
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String s2) {
            super.onSuccess((AnonymousClass4) s2);
            try {
                SchoolScoreLineBean schoolScoreLineBean = (SchoolScoreLineBean) new Gson().fromJson(s2, SchoolScoreLineBean.class);
                if (!schoolScoreLineBean.getCode().equals("200") || schoolScoreLineBean.getData() == null) {
                    return;
                }
                MajorBySchoolAct.this.schoolCover = schoolScoreLineBean.getData().getCover();
                MajorBySchoolAct.this.majorName = schoolScoreLineBean.getData().getMajor_title();
                MajorBySchoolAct.this.schoolName = schoolScoreLineBean.getData().getSchool_title();
                MajorBySchoolAct.this.mFollowStatus = schoolScoreLineBean.getData().getFollow_status();
                String major_code = schoolScoreLineBean.getData().getMajor_code();
                int i2 = 8;
                if (TextUtils.isEmpty(major_code)) {
                    MajorBySchoolAct.this.mTvCode.setVisibility(8);
                } else {
                    MajorBySchoolAct.this.mTvCode.setVisibility(0);
                    MajorBySchoolAct.this.mTvCode.setText(major_code);
                    major_code = major_code + "-";
                }
                String str = major_code + MajorBySchoolAct.this.majorName;
                MajorBySchoolAct.this.navTitle.setText(str);
                MajorBySchoolAct.this.mTvTitle.setText(str);
                MajorBySchoolAct.this.mTvType.setText(schoolScoreLineBean.getData().getMajor_type());
                MajorBySchoolAct.this.mTvShareMajorInfo.setText("为您推荐了\\n北京大学-" + schoolScoreLineBean.getData().getMajor_code() + "-" + MajorBySchoolAct.this.majorName);
                MajorBySchoolAct.this.mTvShareMajorDesc.setText(schoolScoreLineBean.getData().getMajor_code() + "-" + MajorBySchoolAct.this.majorName);
                MajorBySchoolAct.this.mTvShareMajorType.setText(schoolScoreLineBean.getData().getMajor_type());
                MajorBySchoolAct.this.mTvShareMajorCode.setText(schoolScoreLineBean.getData().getMajor_code());
                MajorBySchoolAct.this.mTvShareSchoolName.setText(schoolScoreLineBean.getData().getSchool_title());
                GlideApp.with(MajorBySchoolAct.this.mContext).load((Object) GlideUtils.generateUrl(MajorBySchoolAct.this.schoolCover)).placeholder(R.mipmap.ic_avatar_def).into(MajorBySchoolAct.this.mImgShareSchoolCover);
                GlideApp.with(MajorBySchoolAct.this.mContext).load((Object) GlideUtils.generateUrl(MajorBySchoolAct.this.schoolCover)).placeholder(R.mipmap.ic_avatar_def).into(MajorBySchoolAct.this.mSchoolCover);
                MajorBySchoolAct.this.mTvSchoolName.setText(MajorBySchoolAct.this.schoolName);
                if (!TextUtils.isEmpty(MajorBySchoolAct.this.mFollowStatus) && MajorBySchoolAct.this.mFollowStatus.equals("1")) {
                    MajorBySchoolAct.this.mImgCollection.setImageResource(SkinManager.getCurrentSkinType(MajorBySchoolAct.this.mContext) == 1 ? R.mipmap.ic_choose_school_had_collection_night : R.mipmap.ic_choose_school_had_collection);
                } else {
                    MajorBySchoolAct.this.mImgCollection.setImageResource(SkinManager.getCurrentSkinType(MajorBySchoolAct.this.mContext) == 1 ? R.mipmap.ic_choose_school_collection_night : R.mipmap.ic_choose_school_collection);
                }
                List<EnrollmentData> listHandleDataList = MajorBySchoolAct.this.handleDataList(schoolScoreLineBean.getData().getList());
                MajorBySchoolAct.this.mLyAddLeftView.removeAllViews();
                for (int i3 = 0; i3 < listHandleDataList.size(); i3++) {
                    RecruitmentInformationLeftItemView recruitmentInformationLeftItemView = new RecruitmentInformationLeftItemView(MajorBySchoolAct.this.mContext, false);
                    recruitmentInformationLeftItemView.setData(i3, listHandleDataList.get(i3));
                    MajorBySchoolAct.this.mLyAddLeftView.addView(recruitmentInformationLeftItemView);
                }
                MajorBySchoolAct.this.mLyAddView.removeAllViews();
                for (int i4 = 0; i4 < listHandleDataList.size(); i4++) {
                    RecruitmentInformationItemView recruitmentInformationItemView = new RecruitmentInformationItemView(MajorBySchoolAct.this.mContext, false, listHandleDataList.get(0).getMajor_direction_id().equals("1"));
                    recruitmentInformationItemView.setData(i4, listHandleDataList.get(i4), true, listHandleDataList.get(0).getMajor_direction_id().equals("1"), new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.w2
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            MajorBySchoolAct.AnonymousClass4.lambda$onSuccess$0(view);
                        }
                    });
                    MajorBySchoolAct.this.mLyAddView.addView(recruitmentInformationItemView);
                }
                View view = MajorBySchoolAct.this.mViewEmpty;
                if (listHandleDataList.size() <= 1) {
                    i2 = 0;
                }
                view.setVisibility(i2);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void addViewCount() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("type", "2");
        ajaxParams.put("id", this.mMajorId);
        YJYHttpUtils.post(this, NetworkRequestsURL.incrViewCount, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.MajorBySchoolAct.9
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass9) s2);
            }
        });
    }

    private void followOrCancelSchool(final boolean isFollow) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("target_id", this.mMajorId);
        String str = NetworkRequestsURL.followSchool;
        if (isFollow) {
            str = NetworkRequestsURL.cancelFollowSchool;
        }
        ajaxParams.put("type", "2");
        YJYHttpUtils.post(this.mContext, str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.MajorBySchoolAct.6
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass6) s2);
                try {
                    if ("200".equals(new JSONObject(s2).optString("code"))) {
                        if (isFollow) {
                            MajorBySchoolAct.this.mFollowStatus = "0";
                            ToastUtil.shortToast(MajorBySchoolAct.this, "已取消");
                            MajorBySchoolAct.this.mImgCollection.setImageResource(SkinManager.getCurrentSkinType(MajorBySchoolAct.this.mContext) == 1 ? R.mipmap.ic_choose_school_collection_night : R.mipmap.ic_choose_school_collection);
                        } else {
                            MajorBySchoolAct.this.mFollowStatus = "1";
                            ToastUtil.shortToast(MajorBySchoolAct.this, "已关注");
                            MajorBySchoolAct.this.mImgCollection.setImageResource(SkinManager.getCurrentSkinType(MajorBySchoolAct.this.mContext) == 1 ? R.mipmap.ic_choose_school_had_collection_night : R.mipmap.ic_choose_school_had_collection);
                        }
                        AliYunLogUtil.getInstance().addLog(isFollow ? AliyunEvent.CancelFolloweSchool : AliyunEvent.FollowedInstitutions, MajorBySchoolAct.this.mMajorId, MajorBySchoolAct.this.majorName);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("school_id", this.mSchoolId);
        ajaxParams.put("major_id", this.mMajorId);
        if (!TextUtils.isEmpty(this.mYear)) {
            ajaxParams.put("year", this.mYear);
        }
        if (!TextUtils.isEmpty(this.mBatch)) {
            ajaxParams.put("batch", this.mBatch);
        }
        if (!TextUtils.isEmpty(this.mDepartment)) {
            ajaxParams.put("school_department_id", this.mDepartment);
        }
        if (!TextUtils.isEmpty(this.mType)) {
            ajaxParams.put("type", this.mType);
        }
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.getMajorRecruitInfo, ajaxParams, new AnonymousClass4());
    }

    private void getFeedBack() {
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.chooseSchoolFeedBackCS, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.MajorBySchoolAct.7
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass7) s2);
                try {
                    if ("200".equals(new JSONObject(s2).optString("code"))) {
                        ChooseSchoolServerCustomer chooseSchoolServerCustomer = (ChooseSchoolServerCustomer) new Gson().fromJson(new JSONObject(s2).optString("data"), ChooseSchoolServerCustomer.class);
                        if (TextUtils.isEmpty(chooseSchoolServerCustomer.is_show()) || !chooseSchoolServerCustomer.is_show().equals("1")) {
                            MajorBySchoolAct.this.mLyFeedBackView.setVisibility(8);
                        } else {
                            MajorBySchoolAct.this.mLyFeedBackView.setVisibility(0);
                            MajorBySchoolAct.this.serviceBean = chooseSchoolServerCustomer.getFeedback();
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getScoreData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("school_id", this.mSchoolId);
        ajaxParams.put("major_id", this.mMajorId);
        if (!TextUtils.isEmpty(this.scoreYear)) {
            ajaxParams.put("year", this.scoreYear);
        }
        if (!TextUtils.isEmpty(this.scoreDepaerment)) {
            ajaxParams.put("school_department_id", this.scoreDepaerment);
        }
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.getMajorScoreInfo, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.MajorBySchoolAct.5
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass5) s2);
                try {
                    SchoolEnrollmentDepartmentBean schoolEnrollmentDepartmentBean = (SchoolEnrollmentDepartmentBean) new Gson().fromJson(s2, SchoolEnrollmentDepartmentBean.class);
                    if (!schoolEnrollmentDepartmentBean.getCode().equals("200") || schoolEnrollmentDepartmentBean.getData() == null) {
                        return;
                    }
                    List<EnrollmentData> listHandleDataList = MajorBySchoolAct.this.handleDataList(schoolEnrollmentDepartmentBean.getData());
                    MajorBySchoolAct.this.mLyAddLeftViewTwo.removeAllViews();
                    for (int i2 = 0; i2 < listHandleDataList.size(); i2++) {
                        ChooseSchoolFormLeftItemView chooseSchoolFormLeftItemView = new ChooseSchoolFormLeftItemView(MajorBySchoolAct.this.mContext, false);
                        chooseSchoolFormLeftItemView.setData(i2, listHandleDataList.get(i2));
                        MajorBySchoolAct.this.mLyAddLeftViewTwo.addView(chooseSchoolFormLeftItemView);
                    }
                    MajorBySchoolAct.this.mLyAddViewTwo.removeAllViews();
                    for (int i3 = 0; i3 < listHandleDataList.size(); i3++) {
                        GetMajorScoreInfoItemView getMajorScoreInfoItemView = new GetMajorScoreInfoItemView(MajorBySchoolAct.this.mContext);
                        getMajorScoreInfoItemView.setData(i3, listHandleDataList.get(i3));
                        MajorBySchoolAct.this.mLyAddViewTwo.addView(getMajorScoreInfoItemView);
                    }
                    MajorBySchoolAct.this.mViewEmptyTwo.setVisibility(listHandleDataList.size() > 1 ? 8 : 0);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private void getSearchParamsData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("school_id", this.mSchoolId);
        ajaxParams.put("search_type", "1");
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.getRecruitInfoSearchParams, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.MajorBySchoolAct.8
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                MajorBySchoolAct.this.getData();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass8) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        ChooseSchoolFilterBeanList chooseSchoolFilterBeanList = (ChooseSchoolFilterBeanList) new Gson().fromJson(jSONObject.optString("data"), ChooseSchoolFilterBeanList.class);
                        MajorBySchoolAct.this.typeDescribe = chooseSchoolFilterBeanList.getType_describe();
                        if (chooseSchoolFilterBeanList.getYear() != null && chooseSchoolFilterBeanList.getYear().size() > 0) {
                            if (TextUtils.isEmpty(MajorBySchoolAct.this.mYear)) {
                                MajorBySchoolAct.this.mYear = chooseSchoolFilterBeanList.getYear().get(1).getId();
                            }
                            int i2 = 0;
                            while (i2 < chooseSchoolFilterBeanList.getYear().size()) {
                                ForumFilterBean.FilterDataBean filterDataBean = new ForumFilterBean.FilterDataBean();
                                filterDataBean.setKey(chooseSchoolFilterBeanList.getYear().get(i2).getId());
                                filterDataBean.setTitle(chooseSchoolFilterBeanList.getYear().get(i2).getTitle());
                                if (MajorBySchoolAct.this.mYear.equals(chooseSchoolFilterBeanList.getYear().get(i2).getId())) {
                                    filterDataBean.setSelected(true);
                                } else {
                                    filterDataBean.setSelected(i2 == 1 || chooseSchoolFilterBeanList.getYear().get(i2).getSelected());
                                }
                                MajorBySchoolAct.this.yearList.add(filterDataBean);
                                i2++;
                            }
                        }
                        if (chooseSchoolFilterBeanList.getSchool_department() != null && chooseSchoolFilterBeanList.getSchool_department().size() > 0) {
                            for (int i3 = 0; i3 < chooseSchoolFilterBeanList.getSchool_department().size(); i3++) {
                                ForumFilterBean.FilterDataBean filterDataBean2 = new ForumFilterBean.FilterDataBean();
                                filterDataBean2.setKey(chooseSchoolFilterBeanList.getSchool_department().get(i3).getId());
                                filterDataBean2.setTitle(chooseSchoolFilterBeanList.getSchool_department().get(i3).getTitle());
                                if (MajorBySchoolAct.this.mDepartment.equals(chooseSchoolFilterBeanList.getSchool_department().get(i3).getId())) {
                                    filterDataBean2.setSelected(true);
                                } else {
                                    filterDataBean2.setSelected(false);
                                }
                                MajorBySchoolAct.this.departmentList.add(filterDataBean2);
                            }
                        }
                        if (chooseSchoolFilterBeanList.getBatch() != null && chooseSchoolFilterBeanList.getBatch().size() > 0) {
                            for (int i4 = 0; i4 < chooseSchoolFilterBeanList.getBatch().size(); i4++) {
                                ForumFilterBean.FilterDataBean filterDataBean3 = new ForumFilterBean.FilterDataBean();
                                filterDataBean3.setKey(chooseSchoolFilterBeanList.getBatch().get(i4).getId());
                                filterDataBean3.setTitle(chooseSchoolFilterBeanList.getBatch().get(i4).getTitle());
                                if (MajorBySchoolAct.this.mBatch.equals(chooseSchoolFilterBeanList.getBatch().get(i4).getId())) {
                                    filterDataBean3.setSelected(true);
                                } else {
                                    filterDataBean3.setSelected(false);
                                }
                                MajorBySchoolAct.this.batchList.add(filterDataBean3);
                            }
                        }
                        if (chooseSchoolFilterBeanList.getType() != null && chooseSchoolFilterBeanList.getType().size() > 0) {
                            for (int i5 = 0; i5 < chooseSchoolFilterBeanList.getType().size(); i5++) {
                                ForumFilterBean.FilterDataBean filterDataBean4 = new ForumFilterBean.FilterDataBean();
                                filterDataBean4.setKey(chooseSchoolFilterBeanList.getType().get(i5).getId());
                                filterDataBean4.setTitle(chooseSchoolFilterBeanList.getType().get(i5).getTitle());
                                if (MajorBySchoolAct.this.mType.equals(chooseSchoolFilterBeanList.getType().get(i5).getId())) {
                                    filterDataBean4.setSelected(true);
                                } else {
                                    filterDataBean4.setSelected(false);
                                }
                                MajorBySchoolAct.this.typeList.add(filterDataBean4);
                            }
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                MajorBySchoolAct.this.getData();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(int i2, LinearLayout linearLayout, ImageView imageView, RelativeLayout relativeLayout, NestedScrollView nestedScrollView, int i3, int i4, int i5, int i6) {
        if (i4 > i2) {
            if (linearLayout.getVisibility() == 0) {
                if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                    imageView.setVisibility(8);
                }
                linearLayout.setVisibility(8);
                this.navTitle.setVisibility(0);
                relativeLayout.setBackgroundColor(getColor(SkinManager.getCurrentSkinType(this.mContext) == 1 ? R.color.new_bg_one_color_night : R.color.new_bg_one_color));
                return;
            }
            return;
        }
        if (linearLayout.getVisibility() == 8) {
            relativeLayout.setBackgroundColor(0);
            linearLayout.setVisibility(0);
            this.navTitle.setVisibility(8);
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                imageView.setVisibility(0);
            }
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
    public /* synthetic */ void lambda$init$2(View view) {
        SchoolDetailsAct.newIntent(this.mContext, this.mSchoolId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveFile$13() {
        ActivityCompat.requestPermissions(this, new String[]{Permission.CAMERA, Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE}, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$10(View view) {
        shareAppControl(2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$11(View view) {
        shareAppControl(3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$12(View view) {
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.mLyBottomShareView, "translationY", 0.0f, r4.getHeight());
        objectAnimatorOfFloat.setDuration(200L);
        objectAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: com.psychiatrygarden.activity.chooseSchool.MajorBySchoolAct.3
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                MajorBySchoolAct.this.finish();
                MajorBySchoolAct.this.overridePendingTransition(0, R.anim.slide_out_to_bottom);
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

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$3(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$4(View view) {
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
    public /* synthetic */ void lambda$setListenerForWidget$5(View view) {
        followOrCancelSchool(!TextUtils.isEmpty(this.mFollowStatus) && this.mFollowStatus.equals("1"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$6(View view) {
        this.mLyBottomShareView.setVisibility(0);
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.mLyBottomShareView, "translationY", 800.0f, 0.0f);
        objectAnimatorOfFloat.setDuration(200L);
        objectAnimatorOfFloat.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$7(View view) throws IOException {
        saveFile();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$8(View view) {
        shareAppControl(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$9(View view) {
        shareAppControl(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setQrCode$17(Bitmap bitmap) {
        this.mImgCodeBig.setImageBitmap(bitmap);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setQrCode$18(int i2, String str, Bitmap bitmap) {
        final Bitmap bitmapCreateQRcodeImage = QrCodeUtils.createQRcodeImage(i2, i2, str, bitmap);
        runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.chooseSchool.d2
            @Override // java.lang.Runnable
            public final void run() {
                this.f11237c.lambda$setQrCode$17(bitmapCreateQRcodeImage);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$shareAppControl$14() {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showOrHiddenArrow$15(ImageView imageView, ValueAnimator valueAnimator) {
        imageView.setRotation(((Integer) valueAnimator.getAnimatedValue()).intValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showOrHiddenArrow$16(ImageView imageView, ValueAnimator valueAnimator) {
        imageView.setRotation(((Integer) valueAnimator.getAnimatedValue()).intValue());
    }

    public static void newIntent(Context context, String schoolId, String majorId) {
        Intent intent = new Intent(context, (Class<?>) MajorBySchoolAct.class);
        intent.putExtra("schoolId", schoolId);
        intent.putExtra("majorId", majorId);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSearchItemCLick(int pos, ForumFilterBean.FilterDataBean item, ImageView imgView, boolean isScoreLine, View belowView) {
        showSearchParams(pos, belowView, item.getType(), imgView, isScoreLine);
    }

    private void saveFile() throws IOException {
        if (ContextCompat.checkSelfPermission(this, Permission.CAMERA) == 0 && ContextCompat.checkSelfPermission(this, Permission.WRITE_EXTERNAL_STORAGE) == 0) {
            saveViewToGallery(this, this.mLyShareView);
        } else {
            new XPopup.Builder(this).asCustom(new RequestMediaPermissionPop(this, new OnConfirmListener() { // from class: com.psychiatrygarden.activity.chooseSchool.o2
                @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                public final void onConfirm() {
                    this.f11371a.lambda$saveFile$13();
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
        new Thread(new Runnable() { // from class: com.psychiatrygarden.activity.chooseSchool.n2
            @Override // java.lang.Runnable
            public final void run() {
                this.f11360c.lambda$setQrCode$18(iDp2px, str, bitmapDecodeResource);
            }
        }).start();
    }

    private void shareToFriend(int i2, String page) {
        UMImage uMImage = TextUtils.isEmpty(this.schoolCover) ? new UMImage(this.mContext, R.drawable.ic_launcher) : new UMImage(this.mContext, this.schoolCover);
        UMMin uMMin = new UMMin(this.schoolCover);
        uMMin.setThumb(uMImage);
        uMMin.setTitle(this.schoolName + "-" + this.majorName);
        uMMin.setDescription(this.schoolName + "-" + this.majorName);
        uMMin.setPath(page);
        uMMin.setUserName("gh_14d59acf6877");
        if (CommonConfig.INSTANCE.getYI_KAO_BANG_NETWORK() == 0) {
            Config.setMiniPreView();
        }
        new ShareAction(this).withMedia(uMMin).setPlatform(BaseActivity.platforms.get(i2).mPlatform).share();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showOrHiddenArrow(boolean isShow, final ImageView arrowImg) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(300L);
        if (isShow) {
            ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(0, 180);
            valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.activity.chooseSchool.l2
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    MajorBySchoolAct.lambda$showOrHiddenArrow$15(arrowImg, valueAnimator);
                }
            });
            animatorSet.playTogether(valueAnimatorOfInt);
            animatorSet.start();
            return;
        }
        ValueAnimator valueAnimatorOfInt2 = ValueAnimator.ofInt(180, 0);
        valueAnimatorOfInt2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.activity.chooseSchool.m2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                MajorBySchoolAct.lambda$showOrHiddenArrow$16(arrowImg, valueAnimator);
            }
        });
        animatorSet.playTogether(valueAnimatorOfInt2);
        animatorSet.start();
    }

    private void showSearchParams(final int position, View view, final String type, final ImageView arrowImg, final boolean isScoreLine) {
        List arrayList;
        arrayList = new ArrayList();
        type.hashCode();
        switch (type) {
            case "year":
                arrayList = this.yearList;
                break;
            case "category":
                arrayList = this.typeList;
                break;
            case "batch":
                arrayList = this.batchList;
                break;
            case "department":
                arrayList = this.departmentList;
                break;
        }
        List list = arrayList;
        showOrHiddenArrow(true, arrowImg);
        new ChooseFilterPopuWindow(this, view, this.viewHieght, list, new ChooseFilterPopuWindow.ProjectChoosedInterface() { // from class: com.psychiatrygarden.activity.chooseSchool.MajorBySchoolAct.11
            @Override // com.psychiatrygarden.widget.ChooseFilterPopuWindow.ProjectChoosedInterface
            public void mItemDissmissLinsenter() {
                MajorBySchoolAct.this.showOrHiddenArrow(false, arrowImg);
            }

            @Override // com.psychiatrygarden.widget.ChooseFilterPopuWindow.ProjectChoosedInterface
            public void mItemLinsenter(int choosePos, ForumFilterBean.FilterDataBean data) {
                String key;
                key = data.isSelected() ? data.getKey() : "";
                String str = type;
                str.hashCode();
                switch (str) {
                    case "year":
                        MajorBySchoolAct majorBySchoolAct = MajorBySchoolAct.this;
                        majorBySchoolAct.updateDataList(majorBySchoolAct.yearList, data.getKey(), data.isSelected());
                        if (!isScoreLine) {
                            MajorBySchoolAct.this.mYear = key;
                            break;
                        } else {
                            MajorBySchoolAct.this.scoreYear = key;
                            break;
                        }
                    case "category":
                        MajorBySchoolAct majorBySchoolAct2 = MajorBySchoolAct.this;
                        majorBySchoolAct2.updateDataList(majorBySchoolAct2.typeList, data.getKey(), data.isSelected());
                        MajorBySchoolAct.this.mType = key;
                        break;
                    case "batch":
                        MajorBySchoolAct majorBySchoolAct3 = MajorBySchoolAct.this;
                        majorBySchoolAct3.updateDataList(majorBySchoolAct3.batchList, data.getKey(), data.isSelected());
                        MajorBySchoolAct.this.mBatch = key;
                        break;
                    case "department":
                        MajorBySchoolAct majorBySchoolAct4 = MajorBySchoolAct.this;
                        majorBySchoolAct4.updateDataList(majorBySchoolAct4.departmentList, data.getKey(), data.isSelected());
                        if (!isScoreLine) {
                            MajorBySchoolAct.this.mDepartment = key;
                            break;
                        } else {
                            MajorBySchoolAct.this.scoreDepaerment = key;
                            break;
                        }
                }
                MajorBySchoolAct.this.showOrHiddenArrow(false, arrowImg);
                if (isScoreLine) {
                    MajorBySchoolAct.this.mSearchAdp.getData().get(position).setSelected(data.isSelected());
                    MajorBySchoolAct.this.mSearchAdp.notifyDataSetChanged();
                    MajorBySchoolAct.this.getScoreData();
                } else {
                    MajorBySchoolAct.this.mSearchInfoAdp.getData().get(position).setSelected(data.isSelected());
                    MajorBySchoolAct.this.mSearchInfoAdp.notifyDataSetChanged();
                    MajorBySchoolAct.this.getData();
                }
            }
        }, true, type.equals("department"), type.equals(UriUtil.QUERY_CATEGORY) ? this.typeDescribe : "");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDataList(List<ForumFilterBean.FilterDataBean> mList, String key, boolean isSelected) {
        for (int i2 = 0; i2 < mList.size(); i2++) {
            if (mList.get(i2).getKey().equals(key)) {
                mList.get(i2).setSelected(isSelected);
            } else {
                mList.get(i2).setSelected(false);
            }
        }
    }

    public List<EnrollmentData> handleDataList(List<EnrollmentData> dataList) {
        for (int i2 = 0; i2 < dataList.size(); i2++) {
            int length = dataList.get(i2).getMajor_title().length();
            int length2 = dataList.get(i2).getMajor_direction_title().length();
            if (dataList.get(0).getMajor_direction_id().equals("1")) {
                if (length2 > length) {
                    dataList.get(i2).setMajor_title_local(dataList.get(i2).getMajor_direction_title());
                } else {
                    dataList.get(i2).setMajor_title_local(dataList.get(i2).getMajor_title());
                }
            }
        }
        return dataList;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.mSchoolId = getIntent().getStringExtra("schoolId");
        this.mMajorId = getIntent().getStringExtra("majorId");
        this.mYear = TextUtils.isEmpty(getIntent().getStringExtra("year")) ? "" : getIntent().getStringExtra("year");
        this.mDepartment = TextUtils.isEmpty(getIntent().getStringExtra("department")) ? "" : getIntent().getStringExtra("department");
        this.mBatch = TextUtils.isEmpty(getIntent().getStringExtra("batch")) ? "" : getIntent().getStringExtra("batch");
        this.mType = TextUtils.isEmpty(getIntent().getStringExtra(UriUtil.QUERY_CATEGORY)) ? "" : getIntent().getStringExtra(UriUtil.QUERY_CATEGORY);
        this.navTitle = (TextView) findViewById(R.id.txt_actionbar_title);
        this.mImgBack = (ImageView) findViewById(R.id.iv_actionbar_back);
        final RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.tabbar);
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ly_nav_search);
        this.mScrollView = (NestedScrollView) findViewById(R.id.scroll_view);
        final ImageView imageView = (ImageView) findViewById(R.id.img_bg);
        this.mLyAddLeftView = (LinearLayout) findViewById(R.id.ly_add_left_view);
        this.mLyAddView = (LinearLayout) findViewById(R.id.ly_add_view);
        this.mTvCode = (TextView) findViewById(R.id.tv_code);
        this.mTvType = (TextView) findViewById(R.id.tv_type);
        this.mTvTitle = (TextView) findViewById(R.id.tv_name);
        this.mLyAddLeftViewTwo = (LinearLayout) findViewById(R.id.ly_add_left_view_two);
        this.mLyAddViewTwo = (LinearLayout) findViewById(R.id.ly_add_view_two);
        this.mLyFeedBackView = (RelativeLayout) findViewById(R.id.ly_feedback_view);
        this.mLyFeedBack = (LinearLayout) findViewById(R.id.ly_feedback);
        this.mLyCollection = (LinearLayout) findViewById(R.id.ly_collection);
        this.mLyShare = (LinearLayout) findViewById(R.id.ly_share);
        this.mBtnSave = (LinearLayout) findViewById(R.id.btn_save);
        this.mBtnWechat = (LinearLayout) findViewById(R.id.btn_wechat);
        this.mBtnWxCircle = (LinearLayout) findViewById(R.id.btn_wxcircle);
        this.mBtnQq = (LinearLayout) findViewById(R.id.btn_qq);
        this.mBtnSina = (LinearLayout) findViewById(R.id.btn_sina);
        this.mLyBottomShareView = (LinearLayout) findViewById(R.id.ly_bottom_share);
        this.mImgUserCover = (CircleImageView) findViewById(R.id.user_cover);
        this.mTvUserName = (TextView) findViewById(R.id.tv_username);
        this.mImgShareSchoolCover = (CircleImageView) findViewById(R.id.img_share_school_cover);
        this.mTvShareSchoolName = (TextView) findViewById(R.id.tv_share_school_name);
        this.mTvShareMajorType = (TextView) findViewById(R.id.tv_share_type);
        this.mTvShareMajorCode = (TextView) findViewById(R.id.tv_share_code);
        this.mTvShareMajorInfo = (TextView) findViewById(R.id.tv_share_info);
        this.mImgCodeBig = (ImageView) findViewById(R.id.img_code_big);
        this.mTvShareMajorDesc = (TextView) findViewById(R.id.tv_share_major_info);
        this.mImgCollection = (ImageView) findViewById(R.id.img_collection);
        this.mLyShareView = (RelativeLayout) findViewById(R.id.ly_share_view);
        this.mSearchRecycler = (RecyclerView) findViewById(R.id.search_recycler);
        this.mLyScoreLineView = (RelativeLayout) findViewById(R.id.ly_score_line_view);
        this.mSearchInfoRecycler = (RecyclerView) findViewById(R.id.info_recycler);
        this.mLyInfoView = (RelativeLayout) findViewById(R.id.ly_info_view);
        this.mSchoolCover = (CircleImageView) findViewById(R.id.school_cover);
        this.mTvSchoolName = (TextView) findViewById(R.id.tv_school_name);
        this.mBtnCancel = (TextView) findViewById(R.id.btn_cancel);
        this.mViewEmpty = findViewById(R.id.ly_empty_view);
        this.mViewEmptyTwo = findViewById(R.id.ly_empty_view_two);
        this.scoreLineScroll = (HorizontalScrollView) findViewById(R.id.score_line_scroll);
        this.mViewShadow = findViewById(R.id.line_shadow);
        this.mLyLine = (RelativeLayout) findViewById(R.id.ly_line);
        this.actionbar = StatusBarUtil.getStatusBarHeight(this.mContext);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) relativeLayout.getLayoutParams();
        layoutParams.topMargin = this.actionbar;
        relativeLayout.setLayoutParams(layoutParams);
        linearLayout.setBackgroundResource(SkinManager.getCurrentSkinType(this.mContext) == 1 ? R.drawable.shape_half_deepcolor_search_bg : R.drawable.shape_translate_80_white_bg);
        imageView.setVisibility(SkinManager.getCurrentSkinType(this.mContext) == 1 ? 8 : 0);
        final int pxByDp = ScreenUtil.getPxByDp(this.mContext, 44) + this.actionbar;
        this.mSearchRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        ChooseSchoolSearchAdp chooseSchoolSearchAdp = new ChooseSchoolSearchAdp();
        this.mSearchAdp = chooseSchoolSearchAdp;
        this.mSearchRecycler.setAdapter(chooseSchoolSearchAdp);
        ArrayList arrayList = new ArrayList();
        ForumFilterBean.FilterDataBean filterDataBean = new ForumFilterBean.FilterDataBean();
        filterDataBean.setTitle("年份");
        filterDataBean.setType("year");
        filterDataBean.setSelected(true);
        arrayList.add(filterDataBean);
        ForumFilterBean.FilterDataBean filterDataBean2 = new ForumFilterBean.FilterDataBean();
        filterDataBean2.setTitle("院系");
        filterDataBean2.setType("department");
        filterDataBean2.setSelected(!TextUtils.isEmpty(this.mDepartment));
        arrayList.add(filterDataBean2);
        this.mSearchAdp.setNewInstance(arrayList);
        this.mSearchInfoRecycler.setLayoutManager(new GridLayoutManager(this, 4));
        ChooseSchoolSearchAdp chooseSchoolSearchAdp2 = new ChooseSchoolSearchAdp();
        this.mSearchInfoAdp = chooseSchoolSearchAdp2;
        this.mSearchInfoRecycler.setAdapter(chooseSchoolSearchAdp2);
        ArrayList arrayList2 = new ArrayList();
        arrayList2.addAll(arrayList);
        ForumFilterBean.FilterDataBean filterDataBean3 = new ForumFilterBean.FilterDataBean();
        filterDataBean3.setTitle("批次");
        filterDataBean3.setType("batch");
        filterDataBean3.setSelected(!TextUtils.isEmpty(this.mBatch));
        arrayList2.add(filterDataBean3);
        ForumFilterBean.FilterDataBean filterDataBean4 = new ForumFilterBean.FilterDataBean();
        filterDataBean4.setTitle("录取类别");
        filterDataBean4.setType(UriUtil.QUERY_CATEGORY);
        filterDataBean4.setSelected(!TextUtils.isEmpty(this.mType));
        arrayList2.add(filterDataBean4);
        this.mSearchInfoAdp.setNewInstance(arrayList2);
        this.mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() { // from class: com.psychiatrygarden.activity.chooseSchool.i2
            @Override // androidx.core.widget.NestedScrollView.OnScrollChangeListener
            public final void onScrollChange(NestedScrollView nestedScrollView, int i2, int i3, int i4, int i5) {
                this.f11313a.lambda$init$0(pxByDp, linearLayout, imageView, relativeLayout, nestedScrollView, i2, i3, i4, i5);
            }
        });
        this.scoreLineScroll.setOnScrollChangeListener(new View.OnScrollChangeListener() { // from class: com.psychiatrygarden.activity.chooseSchool.j2
            @Override // android.view.View.OnScrollChangeListener
            public final void onScrollChange(View view, int i2, int i3, int i4, int i5) {
                this.f11327c.lambda$init$1(view, i2, i3, i4, i5);
            }
        });
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.k2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11336c.lambda$init$2(view);
            }
        });
        if (UserConfig.isLogin()) {
            this.mTvUserName.setText(UserConfig.getInstance().getUser().getNickname());
            GlideApp.with(this.mContext).load((Object) GlideUtils.generateUrl(UserConfig.getInstance().getUser().getAvatar())).placeholder(R.mipmap.ic_avatar_def).into(this.mImgUserCover);
        }
        setQrCode();
        getScoreData();
        getFeedBack();
        getSearchParamsData();
        addViewCount();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setStatusBarTranslucent(this, false);
        StatusBarCompat.setLightStatusBar(this, true);
        this.mActionBar.hide();
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
        setContentView(R.layout.layout_major_by_school);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mImgBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.p2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11379c.lambda$setListenerForWidget$3(view);
            }
        });
        this.mLyFeedBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.q2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11387c.lambda$setListenerForWidget$4(view);
            }
        });
        this.mLyCollection.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.r2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11395c.lambda$setListenerForWidget$5(view);
            }
        });
        this.mLyShare.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.s2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11403c.lambda$setListenerForWidget$6(view);
            }
        });
        this.mBtnSave.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.t2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws IOException {
                this.f11413c.lambda$setListenerForWidget$7(view);
            }
        });
        this.mBtnWechat.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.u2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11424c.lambda$setListenerForWidget$8(view);
            }
        });
        this.mBtnWxCircle.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.v2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11435c.lambda$setListenerForWidget$9(view);
            }
        });
        this.mBtnQq.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.e2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11247c.lambda$setListenerForWidget$10(view);
            }
        });
        this.mBtnSina.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.f2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11257c.lambda$setListenerForWidget$11(view);
            }
        });
        this.mSearchAdp.setOnItemActionLisenter(new ChooseSchoolSearchAdp.OnItemActionLisenter() { // from class: com.psychiatrygarden.activity.chooseSchool.MajorBySchoolAct.1
            @Override // com.psychiatrygarden.activity.chooseSchool.adapter.ChooseSchoolSearchAdp.OnItemActionLisenter
            public void setItemClickAction(int pos, ForumFilterBean.FilterDataBean item, ImageView imgView) {
                int[] iArr = new int[2];
                MajorBySchoolAct.this.mLyScoreLineView.getLocationOnScreen(iArr);
                MajorBySchoolAct.this.viewHieght = ((Build.VERSION.SDK_INT <= 30 || ScreenUtil.isTablet(MajorBySchoolAct.this.mContext)) ? CommonUtil.getScreenHeight(MajorBySchoolAct.this.mContext) : CommonUtil.getScreenHeight(MajorBySchoolAct.this.mContext) + MajorBySchoolAct.this.actionbar) - iArr[1];
                if (CommonUtil.isFastClick()) {
                    return;
                }
                MajorBySchoolAct majorBySchoolAct = MajorBySchoolAct.this;
                majorBySchoolAct.onSearchItemCLick(pos, item, imgView, true, majorBySchoolAct.mSearchRecycler);
            }
        });
        this.mSearchInfoAdp.setOnItemActionLisenter(new ChooseSchoolSearchAdp.OnItemActionLisenter() { // from class: com.psychiatrygarden.activity.chooseSchool.MajorBySchoolAct.2
            @Override // com.psychiatrygarden.activity.chooseSchool.adapter.ChooseSchoolSearchAdp.OnItemActionLisenter
            public void setItemClickAction(int pos, ForumFilterBean.FilterDataBean item, ImageView imgView) {
                int[] iArr = new int[2];
                MajorBySchoolAct.this.mLyInfoView.getLocationOnScreen(iArr);
                MajorBySchoolAct.this.viewHieght = ((Build.VERSION.SDK_INT <= 30 || ScreenUtil.isTablet(MajorBySchoolAct.this.mContext)) ? CommonUtil.getScreenHeight(MajorBySchoolAct.this.mContext) : CommonUtil.getScreenHeight(MajorBySchoolAct.this.mContext) + MajorBySchoolAct.this.actionbar) - iArr[1];
                if (CommonUtil.isFastClick()) {
                    return;
                }
                MajorBySchoolAct majorBySchoolAct = MajorBySchoolAct.this;
                majorBySchoolAct.onSearchItemCLick(pos, item, imgView, false, majorBySchoolAct.mSearchInfoRecycler);
            }
        });
        this.mBtnCancel.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.g2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11294c.lambda$setListenerForWidget$12(view);
            }
        });
    }

    public void shareAppControl(int i2) {
        if (i2 == 0) {
            shareToFriend(i2, "pages/index/startPage");
            return;
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(this.mLyShareView.getWidth(), this.mLyShareView.getHeight(), Bitmap.Config.ARGB_8888);
        this.mLyShareView.draw(new Canvas(bitmapCreateBitmap));
        new ShareAction(this).withMedia(new UMImage(this.mContext, bitmapCreateBitmap)).setPlatform(BaseActivity.platforms.get(i2).mPlatform).setCallback(new UMShareListener() { // from class: com.psychiatrygarden.activity.chooseSchool.MajorBySchoolAct.10
            @Override // com.umeng.socialize.UMShareListener
            public void onCancel(SHARE_MEDIA shareMedia) {
                ToastUtil.shortToast(MajorBySchoolAct.this.mContext, "用户取消分享");
            }

            @Override // com.umeng.socialize.UMShareListener
            public void onError(SHARE_MEDIA shareMedia, Throwable throwable) {
                ToastUtil.shortToast(MajorBySchoolAct.this.mContext, "未检测到QQ/微信应用或请安装正式版QQ/微信分享！");
            }

            @Override // com.umeng.socialize.UMShareListener
            public void onResult(SHARE_MEDIA shareMedia) {
                ToastUtil.shortToast(MajorBySchoolAct.this.mContext, "分享成功");
            }

            @Override // com.umeng.socialize.UMShareListener
            public void onStart(SHARE_MEDIA shareMedia) {
            }
        }).share();
        this.mLyBottomShareView.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.chooseSchool.h2
            @Override // java.lang.Runnable
            public final void run() {
                this.f11303c.lambda$shareAppControl$14();
            }
        }, 1000L);
    }

    public static void newIntent(Context context, String schoolId, String majorId, String year, String department, String batch, String category) {
        Intent intent = new Intent(context, (Class<?>) MajorBySchoolAct.class);
        intent.putExtra("schoolId", schoolId);
        intent.putExtra("majorId", majorId);
        intent.putExtra("year", year);
        intent.putExtra("department", department);
        intent.putExtra("batch", batch);
        intent.putExtra(UriUtil.QUERY_CATEGORY, category);
        context.startActivity(intent);
    }
}
