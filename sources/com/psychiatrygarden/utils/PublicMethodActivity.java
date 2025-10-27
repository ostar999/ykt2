package com.psychiatrygarden.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.Log;
import cn.hutool.core.text.StrPool;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.plv.socket.user.PLVSocketUserConstant;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.ActCourseOrGoodsDetail;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.CombineQuestionMainNewActivity;
import com.psychiatrygarden.activity.CustomStudyPlanActivity;
import com.psychiatrygarden.activity.EstimateExplainActivity;
import com.psychiatrygarden.activity.HandoutsInfoActivity;
import com.psychiatrygarden.activity.HomePageNewActivity;
import com.psychiatrygarden.activity.LoginActivity;
import com.psychiatrygarden.activity.MoreTiDanActivity;
import com.psychiatrygarden.activity.MyCommentListActivity;
import com.psychiatrygarden.activity.QuestionSetListActivity;
import com.psychiatrygarden.activity.StudyPlanListActivity;
import com.psychiatrygarden.activity.WebLongSaveActivity;
import com.psychiatrygarden.activity.chooseSchool.AdmissionBrochureAct;
import com.psychiatrygarden.activity.chooseSchool.ChooseSchoolMainActivity;
import com.psychiatrygarden.activity.chooseSchool.EnrollmentNumberAct;
import com.psychiatrygarden.activity.chooseSchool.FractionBarAct;
import com.psychiatrygarden.activity.chooseSchool.MyFollowSchoolsActivity;
import com.psychiatrygarden.activity.chooseSchool.PostgraduateCalendarActivity;
import com.psychiatrygarden.activity.chooseSchool.RecruitmentDepartmentAct;
import com.psychiatrygarden.activity.chooseSchool.RecruitmentInformationAct;
import com.psychiatrygarden.activity.chooseSchool.SchoolDetailsAct;
import com.psychiatrygarden.activity.chooseSchool.SchoolMajorRankingActivity;
import com.psychiatrygarden.activity.chooseSchool.SchoolOpenMajorAct;
import com.psychiatrygarden.activity.circleactivity.CircleInfoActivity;
import com.psychiatrygarden.activity.comment.alipler.AliPlayerVideoPlayActivity;
import com.psychiatrygarden.activity.comment.bean.DiscussStatus;
import com.psychiatrygarden.activity.courselist.CourseListChpterActivity;
import com.psychiatrygarden.activity.courselist.course.CourseDirectoryActivity;
import com.psychiatrygarden.activity.ebook.BookStoreActivity;
import com.psychiatrygarden.activity.forum.Circle24HotListActivity;
import com.psychiatrygarden.activity.knowledge.DailyTasksActivity;
import com.psychiatrygarden.activity.material.InformationPreviewAct;
import com.psychiatrygarden.activity.material.InformationProjectAct;
import com.psychiatrygarden.activity.mine.coupons.RedEnvelopeCouponsAct;
import com.psychiatrygarden.activity.mine.knowledge.KnowledgeMapAct;
import com.psychiatrygarden.activity.mine.order.OrderDetailActivity;
import com.psychiatrygarden.activity.mine.report.StudyReportAct;
import com.psychiatrygarden.activity.online.AnswerQuestionActivity;
import com.psychiatrygarden.activity.online.bean.QuestionDetailBean;
import com.psychiatrygarden.activity.purchase.activity.ActSubmitGoodsComment;
import com.psychiatrygarden.activity.setting.FeedbackReponseAct;
import com.psychiatrygarden.activity.setting.NewFriendsAct;
import com.psychiatrygarden.activity.setting.SystemNoticeAct;
import com.psychiatrygarden.activity.vip.MemberCenterActivity;
import com.psychiatrygarden.bean.CourseList2Bean;
import com.psychiatrygarden.bean.RedEnvelopeCouponsBean;
import com.psychiatrygarden.bean.SelectIdentityBean;
import com.psychiatrygarden.bean.ShengYunRegisterBean;
import com.psychiatrygarden.bean.TarGetParamBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.GoToLivingEvent;
import com.psychiatrygarden.forum.VolunteerRankingAct;
import com.psychiatrygarden.forum.experience.SearchExperienceAct;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.QuestionDataRequest;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.QuestionDataCallBack;
import com.psychiatrygarden.ranking.StatisticsMainActivity;
import com.psychiatrygarden.widget.CommonTwoBtnDialog;
import com.yddmi.doctor.pages.home.HomeActivity;
import com.yikaobang.yixue.R;
import com.ykb.ebook.activity.ReadBookActivity;
import com.ykb.ebook.common.PreferKeyKt;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class PublicMethodActivity implements QuestionDataCallBack<String> {
    private static volatile PublicMethodActivity instance;
    private String childrenIdentityId;
    private String childrenIdentityId2;
    private String childrenIdentityId3;
    private QuestionDetailBean questionDetailBean;
    private String module_type = "";
    Context mContext = ProjectApp.instance().getTopActivity();
    int position = 0;

    public static PublicMethodActivity getInstance() {
        if (instance == null) {
            synchronized (PublicMethodActivity.class) {
                if (instance == null) {
                    instance = new PublicMethodActivity();
                }
            }
        }
        return instance;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getLastIdentityData(String targetIdentityId, List<SelectIdentityBean.DataBean> dataBeanList) {
        for (SelectIdentityBean.DataBean dataBean : dataBeanList) {
            String category = dataBean.getCategory();
            String identity_id = dataBean.getIdentity_id();
            if (targetIdentityId.startsWith(identity_id)) {
                SharePreferencesUtils.writeStrConfig("0", identity_id, this.mContext);
                if ((!TextUtils.isEmpty(category) || "1".equals(dataBean.getIs_last())) && identity_id.equals(targetIdentityId)) {
                    this.position = 0;
                    setData(dataBean);
                } else {
                    for (SelectIdentityBean.DataBean dataBean2 : dataBean.getChildren()) {
                        this.childrenIdentityId = dataBean2.getIdentity_id();
                        if ((!TextUtils.isEmpty(dataBean2.getCategory()) || "1".equals(dataBean2.getIs_last())) && dataBean2.getIdentity_id().equals(targetIdentityId)) {
                            this.position = 1;
                            setData(dataBean2);
                        } else {
                            for (SelectIdentityBean.DataBean dataBean3 : dataBean2.getChildren()) {
                                this.childrenIdentityId2 = dataBean3.getIdentity_id();
                                if ((!TextUtils.isEmpty(dataBean3.getCategory()) || "1".equals(dataBean3.getIs_last())) && dataBean3.getIdentity_id().equals(targetIdentityId)) {
                                    this.position = 2;
                                    setData(dataBean3);
                                } else {
                                    for (SelectIdentityBean.DataBean dataBean4 : dataBean3.getChildren()) {
                                        this.childrenIdentityId3 = dataBean4.getIdentity_id();
                                        if (!TextUtils.isEmpty(dataBean4.getCategory()) || "1".equals(dataBean4.getIs_last())) {
                                            if (dataBean4.getIdentity_id().equals(targetIdentityId)) {
                                                this.position = 3;
                                                setData(dataBean4);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void goToLiving(Context context, String target_params) {
        try {
            JSONObject jSONObject = new JSONObject(target_params);
            CommonUtil.launchLiving(context, jSONObject.optString("polyv_user_id"), jSONObject.optString("polyv_app_id"), jSONObject.optString("polyv_app_secret"), jSONObject.optString("room_id"), jSONObject.optString("course_id"), jSONObject.optString("live_id"));
        } catch (Exception e2) {
            Log.d("Error 794", "goToLiving: " + e2.getMessage());
        }
    }

    private boolean isLogin() {
        if (!"".equals(UserConfig.getUserId())) {
            return true;
        }
        ProjectApp.instance().getTopActivity().startActivity(new Intent(ProjectApp.instance().getTopActivity(), (Class<?>) LoginActivity.class));
        return false;
    }

    private void jumpInfoDetails(String id, String url) {
        Intent intent = new Intent(ProjectApp.instance().getTopActivity(), (Class<?>) InformationPreviewAct.class);
        intent.putExtra("fileId", id);
        intent.putExtra("fileUrl", url);
        intent.putExtra("isLocal", false);
        ProjectApp.instance().getTopActivity().startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$mToActivity$0(Context context, String str) {
        try {
            ((BaseActivity) context).showProgressDialog("加载中...");
        } catch (Exception unused) {
            Log.e("ad_clock_error", "广告点击loading窗错误");
        }
        getData(str);
    }

    private void saveApptitle(String app_id) {
        String str;
        app_id.hashCode();
        switch (app_id) {
            case "10":
                str = "专硕";
                break;
            case "12":
                str = "执业医师";
                break;
            case "13":
                str = "执业助理医师";
                break;
            case "20":
                str = "中医专硕";
                break;
            case "21":
                str = "中医执业医师";
                break;
            case "40":
                str = "本科";
                break;
            default:
                str = "";
                break;
        }
        SharePreferencesUtils.writeStrConfig(CommonParameter.app_title, str, this.mContext);
    }

    private void setData(SelectIdentityBean.DataBean dataBean) {
        SharePreferencesUtils.writeStrConfig((this.position + 1) + "", "", this.mContext);
        SharePreferencesUtils.writeStrConfig((this.position + 2) + "", "", this.mContext);
        SharePreferencesUtils.writeStrConfig("1", this.childrenIdentityId, this.mContext);
        SharePreferencesUtils.writeStrConfig("2", this.childrenIdentityId2, this.mContext);
        SharePreferencesUtils.writeStrConfig("3", this.childrenIdentityId3, this.mContext);
        SharePreferencesUtils.writeStrConfig(CommonParameter.identity_id, dataBean.getIdentity_id() + "", this.mContext);
        SharePreferencesUtils.writeStrConfig(CommonParameter.App_Id, dataBean.getApp_id() + "", this.mContext);
        SharePreferencesUtils.writeStrConfig(CommonParameter.app_mark, "", this.mContext);
        SharePreferencesUtils.writeStrConfig(CommonParameter.Student_Type, dataBean.getApp_type() + "", this.mContext);
        SharePreferencesUtils.writeStrConfig(CommonParameter.App_Name, dataBean.getTitle() + "", this.mContext);
        SharePreferencesUtils.writeStrConfig(CommonParameter.am_pm, dataBean.getAm_pm() + "", this.mContext);
        SharePreferencesUtils.writeStrConfig(CommonParameter.is_hidden_unit, dataBean.getIs_hidden_unit() + "", this.mContext);
        SharePreferencesUtils.writeStrConfig(CommonParameter.isHideExp, dataBean.getIs_hidden_exp() + "", this.mContext);
        SharePreferencesUtils.writeStrConfig(CommonParameter.is_hidden_course, dataBean.getIs_hidden_course() + "", this.mContext);
        SharePreferencesUtils.writeStrConfig(CommonParameter.is_hidden_shop, dataBean.getIs_hidden_shop() + "", this.mContext);
        SharePreferencesUtils.writeStrConfig(CommonParameter.is_hidden_restore_img, dataBean.getIs_hidden_restore_img() + "", this.mContext);
        SharePreferencesUtils.writeStrConfig(CommonParameter.is_hidden_question_type, dataBean.getIs_hidden_question_type() + "", this.mContext);
        SharePreferencesUtils.writeStrConfig(CommonParameter.is_hidden_correction_error, dataBean.getIs_hidden_correction_error() + "", this.mContext);
        SharePreferencesUtils.writeStrConfig(CommonParameter.catalogue_q, new Gson().toJson(dataBean.getChildren()), this.mContext);
        SharePreferencesUtils.writeStrConfig(CommonParameter.is_hidden_difficulty, dataBean.getIs_hidden_difficulty() + "", this.mContext);
        SharePreferencesUtils.writeStrConfig(CommonParameter.is_hidden_stat, dataBean.getIs_hidden_stat() + "", this.mContext);
        SharePreferencesUtils.writeStrConfig(CommonParameter.is_hidden_label, dataBean.getIs_hidden_label() + "", this.mContext);
        SharePreferencesUtils.writeStrConfig(CommonParameter.is_hidden_outlines, dataBean.getIs_hidden_outlines() + "", this.mContext);
        SharePreferencesUtils.writeStrConfig(CommonParameter.is_hidden_restore, dataBean.getIs_hidden_restore() + "", this.mContext);
        SharePreferencesUtils.writeStrConfig(CommonParameter.is_hidden_analysis, dataBean.getIs_hidden_analysis() + "", this.mContext);
        SharePreferencesUtils.writeStrConfig(CommonParameter.is_hidden_options, dataBean.getIs_hidden_options() + "", this.mContext);
        SharePreferencesUtils.writeStrConfig(CommonParameter.is_hidden_options_update, dataBean.getIs_hidden_options_update() + "", this.mContext);
        SharePreferencesUtils.writeStrConfig(CommonParameter.is_hidden_analysis_update, dataBean.getIs_hidden_analysis_update() + "", this.mContext);
        SharePreferencesUtils.removeConfig(CommonParameter.SEARCH_QUESTION_UNIT_ID, this.mContext);
        SharePreferencesUtils.removeContainConfig(CommonParameter.SEARCH_QUESTION_ID + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext), this.mContext);
        SharePreferencesUtils.removeContainConfig(CommonParameter.SEARCH_CUT_QUESTION_ID + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext), this.mContext);
        SharePreferencesUtils.removeContainConfig(CommonParameter.FILTER_YEAR_TO_QUETION_DATA + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext), this.mContext);
        SharePreferencesUtils.removeContainConfig(CommonParameter.FILTER_YEAR_DATA + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext), this.mContext);
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.SHOW_NEW_HOME, TextUtils.equals("0", dataBean.getIs_hidden_index()), this.mContext);
        saveApptitle(dataBean.getApp_id());
        Intent intent = new Intent(this.mContext, (Class<?>) HomePageNewActivity.class);
        intent.addFlags(268468224);
        ProjectApp.instance().getTopActivity().startActivity(intent);
        ProjectApp.instance().getTopActivity().overridePendingTransition(R.anim.start_anim, R.anim.out_anim);
    }

    private void shengYunRegister(final int show_type) {
        YJYHttpUtils.post(ProjectApp.instance().getTopActivity(), NetworkRequestsURL.shengYunRegister, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.utils.PublicMethodActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                String sy_mobile = ((ShengYunRegisterBean) new Gson().fromJson(s2, ShengYunRegisterBean.class)).getData().getSy_mobile();
                Intent intent = new Intent(ProjectApp.instance().getTopActivity(), (Class<?>) HomeActivity.class);
                intent.putExtra("phoneNumber", sy_mobile);
                intent.putExtra("show_type", show_type);
                ProjectApp.instance().getTopActivity().startActivity(intent);
            }
        });
    }

    public void couponsJump(RedEnvelopeCouponsBean.CouponsJumpBean jumpBean) {
        if (jumpBean != null) {
            if (!jumpBean.getStatus().equals("1")) {
                ToastUtil.shortToast(ProjectApp.instance(), jumpBean.getToast());
            }
            if (jumpBean.getApply_range().equals("0")) {
                if (ProjectApp.newHomeStyle) {
                    EventBus.getDefault().post("jumpToNewHome");
                    ProjectApp.instance().closeAllActivityWithoutHome();
                    return;
                } else {
                    EventBus.getDefault().post("jumpToHome");
                    ProjectApp.instance().closeAllActivityWithoutHome();
                    return;
                }
            }
            String apply_type = jumpBean.getApply_type();
            apply_type.hashCode();
            switch (apply_type) {
                case "1":
                    if (!TextUtils.equals("1", SharePreferencesUtils.readStrConfig(CommonParameter.is_hidden_shop, ProjectApp.instance()))) {
                        if (!jumpBean.getApply_range().equals("1")) {
                            EventBus.getDefault().post("jumpToStore");
                            ProjectApp.instance().closeAllActivityWithoutHome();
                            break;
                        } else {
                            Intent intent = new Intent(ProjectApp.instance(), (Class<?>) ActCourseOrGoodsDetail.class);
                            intent.putExtra("goods_id", jumpBean.getObj_id());
                            intent.putExtra("detailType", 2);
                            intent.setFlags(268435456);
                            ProjectApp.instance().startActivity(intent);
                            break;
                        }
                    } else {
                        ToastUtil.shortToast(ProjectApp.instance(), "暂无商城商品上架，暂无法使用");
                        break;
                    }
                case "2":
                    if (!TextUtils.equals("1", SharePreferencesUtils.readStrConfig(CommonParameter.is_hidden_course, ProjectApp.instance()))) {
                        if (!jumpBean.getApply_range().equals("1")) {
                            EventBus.getDefault().post("JumpZHibo");
                            ProjectApp.instance().closeAllActivityWithoutHome();
                            break;
                        } else {
                            Intent intent2 = new Intent(ProjectApp.instance(), (Class<?>) ActCourseOrGoodsDetail.class);
                            intent2.putExtra("course_id", jumpBean.getObj_id());
                            intent2.setFlags(268435456);
                            ProjectApp.instance().startActivity(intent2);
                            break;
                        }
                    } else {
                        ToastUtil.shortToast(ProjectApp.instance(), "暂无课程商品上架，暂无法使用");
                        break;
                    }
                case "3":
                    Intent intent3 = new Intent(ProjectApp.instance(), (Class<?>) MemberCenterActivity.class);
                    intent3.setFlags(268435456);
                    ProjectApp.instance().startActivity(intent3);
                    break;
                case "4":
                    if (!jumpBean.getApply_range().equals("1")) {
                        BookStoreActivity.newIntent(ProjectApp.instance().getTopActivity());
                        break;
                    } else {
                        String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance(), "1");
                        String admin = UserConfig.getInstance().getUser().getAdmin();
                        String avatar = UserConfig.getInstance().getUser().getAvatar();
                        Intent intent4 = new Intent(ProjectApp.instance().getTopActivity(), (Class<?>) ReadBookActivity.class);
                        intent4.putExtra("book_id", jumpBean.getObj_id());
                        intent4.putExtra("user_id", UserConfig.getUserId());
                        intent4.putExtra("app_id", strConfig);
                        intent4.putExtra(PLVSocketUserConstant.ROLE_ADMIN, admin);
                        intent4.putExtra("avatar", avatar);
                        intent4.putExtra("token", UserConfig.getInstance().getUser().getToken());
                        intent4.putExtra("secret", UserConfig.getInstance().getUser().getSecret());
                        intent4.setFlags(268435456);
                        ProjectApp.instance().startActivity(intent4);
                        break;
                    }
                case "5":
                    if (!jumpBean.getApply_range().equals("1")) {
                        InformationProjectAct.newIntent(ProjectApp.instance().getTopActivity());
                        break;
                    } else {
                        Intent intent5 = new Intent(ProjectApp.instance().getTopActivity(), (Class<?>) InformationPreviewAct.class);
                        intent5.putExtra("fileId", jumpBean.getObj_id());
                        intent5.putExtra("fileUrl", jumpBean.getFile_url());
                        intent5.putExtra("isLocal", false);
                        intent5.setFlags(268435456);
                        ProjectApp.instance().startActivity(intent5);
                        break;
                    }
            }
        }
    }

    public void getData(final String targetIdentityId) {
        AjaxParams ajaxParams = new AjaxParams();
        if (!TextUtils.isEmpty(UserConfig.getUserId())) {
            ajaxParams.put("user_id", UserConfig.getUserId());
        }
        YJYHttpUtils.get(ProjectApp.instance().getTopActivity(), NetworkRequestsURL.subjectV2Api, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.utils.PublicMethodActivity.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                try {
                    ((BaseActivity) ProjectApp.instance().getTopActivity()).hideProgressDialog();
                } catch (Exception unused) {
                    Log.e("ad_clock_error", "广告点击loading窗错误");
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                try {
                    PublicMethodActivity.this.getLastIdentityData(targetIdentityId, ((SelectIdentityBean) new Gson().fromJson(s2, SelectIdentityBean.class)).getData());
                    try {
                        ((BaseActivity) ProjectApp.instance().getTopActivity()).hideProgressDialog();
                    } catch (Exception unused) {
                        Log.e("ad_clock_error", "广告点击loading窗错误");
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void mCommentMethod(String module_type, TarGetParamBean target_params) {
        if (target_params != null) {
            this.module_type = module_type;
            if (module_type.equals("1") || module_type.equals("4")) {
                QuestionDataRequest.getIntance(ProjectApp.instance()).questionInfo(target_params.getId() + "", module_type, this);
                return;
            }
            if (module_type.equals("3")) {
                if (TextUtils.isEmpty(target_params.getJson_path())) {
                    NewToast.showShort(ProjectApp.instance().getTopActivity(), "原文已删除", 0).show();
                    return;
                }
                Intent intent = new Intent(ProjectApp.instance().getTopActivity(), (Class<?>) HandoutsInfoActivity.class);
                intent.putExtra("cat_id", "");
                intent.putExtra("article", target_params.getId());
                intent.putExtra("json_path", target_params.getJson_path());
                intent.putExtra("html_path", "");
                intent.putExtra("index", 0);
                intent.putExtra("h5_path", target_params.getH5_path());
                intent.putExtra("is_rich_text", target_params.getIs_rich_text());
                ProjectApp.instance().getTopActivity().startActivity(intent);
                return;
            }
            if (module_type.equals(com.tencent.connect.common.Constants.VIA_REPORT_TYPE_SET_AVATAR)) {
                Intent intent2 = new Intent(ProjectApp.instance().getTopActivity(), (Class<?>) CircleInfoActivity.class);
                intent2.putExtra("article_id", "" + target_params.getId());
                intent2.putExtra("commentCount", "0");
                intent2.putExtra("channel_id", "0");
                intent2.putExtra("module_type", 12);
                ProjectApp.instance().getTopActivity().startActivity(intent2);
                return;
            }
            if (module_type.equals("16")) {
                Intent intent3 = new Intent(ProjectApp.instance().getTopActivity(), (Class<?>) CircleInfoActivity.class);
                intent3.putExtra("article_id", "" + target_params.getId());
                intent3.putExtra("commentCount", "0");
                intent3.putExtra("channel_id", "0");
                intent3.putExtra("module_type", 16);
                intent3.putExtra("group_id", "" + target_params.getGroup_id());
                ProjectApp.instance().getTopActivity().startActivity(intent3);
                return;
            }
            if (module_type.equals(com.tencent.connect.common.Constants.VIA_REPORT_TYPE_CHAT_AUDIO)) {
                this.module_type = "1";
                QuestionDataRequest.getIntance(ProjectApp.instance()).questionInfo(target_params.getId() + "", "1", this);
                return;
            }
            if (module_type.equals(com.tencent.connect.common.Constants.VIA_REPORT_TYPE_MAKE_FRIEND) || module_type.equals(com.tencent.connect.common.Constants.VIA_REPORT_TYPE_WPA_STATE)) {
                CourseList2Bean.DataBean.DataChildBean dataChildBean = new CourseList2Bean.DataBean.DataChildBean();
                dataChildBean.setId(target_params.getId());
                dataChildBean.setSeries(target_params.getSeries());
                dataChildBean.setTitle(target_params.getTitle());
                dataChildBean.setDescription(target_params.getDescription());
                dataChildBean.setCover_img(target_params.getCover_img());
                Intent intent4 = new Intent(ProjectApp.instance().getTopActivity(), (Class<?>) CourseListChpterActivity.class);
                intent4.putExtra("vidteaching", dataChildBean);
                intent4.setFlags(268435456);
                ProjectApp.instance().getTopActivity().startActivity(intent4);
                return;
            }
            if (module_type.equals("100")) {
                if (target_params.getIs_del().equals("1")) {
                    ToastUtil.shortToast(ProjectApp.instance(), "抱歉，图书已隐藏或删除");
                    return;
                }
                String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance(), "1");
                String admin = UserConfig.getInstance().getUser().getAdmin();
                String avatar = UserConfig.getInstance().getUser().getAvatar();
                Intent intent5 = new Intent(ProjectApp.instance().getTopActivity(), (Class<?>) ReadBookActivity.class);
                intent5.putExtra("book_id", target_params.getId());
                intent5.putExtra("user_id", UserConfig.getUserId());
                intent5.putExtra("app_id", strConfig);
                intent5.putExtra(PLVSocketUserConstant.ROLE_ADMIN, admin);
                intent5.putExtra("avatar", avatar);
                intent5.putExtra("token", UserConfig.getInstance().getUser().getToken());
                intent5.putExtra("secret", UserConfig.getInstance().getUser().getSecret());
                ProjectApp.instance().getTopActivity().startActivity(intent5);
                return;
            }
            if (module_type.equals("101")) {
                jumpInfoDetails(target_params.getId(), target_params.getWeb_link());
                return;
            }
            if (com.tencent.connect.common.Constants.VIA_SHARE_TYPE_PUBLISHVIDEO.equals(module_type)) {
                if (TextUtils.equals("1", target_params.getCourse_is_del())) {
                    ToastUtil.shortToast(ProjectApp.instance(), "抱歉，课程已下架");
                    return;
                }
                if (TextUtils.equals("1", target_params.getIs_del())) {
                    ToastUtil.shortToast(ProjectApp.instance(), "抱歉，视频已下架");
                    return;
                }
                if (!TextUtils.equals("1", target_params.getCourse_is_del()) && !"1".equals(target_params.getIs_permission())) {
                    Intent intent6 = new Intent(ProjectApp.instance().getTopActivity(), (Class<?>) ActCourseOrGoodsDetail.class);
                    intent6.putExtra("course_id", target_params.getCourse_id());
                    ProjectApp.instance.getTopActivity().startActivity(intent6);
                } else {
                    if (TextUtils.equals("1", target_params.getCourse_is_del()) || TextUtils.equals("1", target_params.getIs_del()) || !"1".equals(target_params.getIs_permission())) {
                        return;
                    }
                    Intent intent7 = new Intent(ProjectApp.instance().getTopActivity(), (Class<?>) AliPlayerVideoPlayActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("course_id", target_params.getCourse_id());
                    bundle.putString("obj_id", target_params.getId());
                    bundle.putInt("module_type", 8);
                    bundle.putString("watch_permission", "1");
                    bundle.putSerializable("commentEnum", DiscussStatus.CourseReview);
                    intent7.putExtras(bundle);
                    ProjectApp.instance.getTopActivity().startActivity(intent7);
                }
            }
        }
    }

    public void mToActivity(String s2) {
        try {
            JSONObject jSONObject = new JSONObject(s2);
            int iOptInt = jSONObject.optInt(PushConstants.PUSH_TYPE);
            if (iOptInt == 1) {
                if (TextUtils.isEmpty(UserConfig.getUserId())) {
                    ProjectApp.instance().getTopActivity().startActivity(new Intent(ProjectApp.instance().getTopActivity(), (Class<?>) LoginActivity.class));
                    return;
                }
                return;
            }
            if (iOptInt == 2) {
                if (isLogin()) {
                    Intent intent = new Intent(ProjectApp.instance().getTopActivity(), (Class<?>) MyCommentListActivity.class);
                    intent.putExtra("title", "评论我");
                    intent.putExtra("type", 1);
                    intent.putExtra("comment_type", "2");
                    intent.putExtra("module_type", jSONObject.optInt("module_type"));
                    intent.putExtra("is_callMe", true);
                    intent.putExtra("id", jSONObject.optInt("id"));
                    ProjectApp.instance().getTopActivity().startActivity(intent);
                    return;
                }
                return;
            }
            if (iOptInt == 100) {
                if (isLogin()) {
                    Intent intent2 = new Intent();
                    intent2.setClass(ProjectApp.instance().getTopActivity(), FeedbackReponseAct.class);
                    intent2.putExtra("id", jSONObject.optString("id"));
                    ProjectApp.instance().getTopActivity().startActivity(intent2);
                    return;
                }
                return;
            }
            if (iOptInt == 110) {
                CourseList2Bean.DataBean.DataChildBean dataChildBean = new CourseList2Bean.DataBean.DataChildBean();
                dataChildBean.setId(jSONObject.optString("id"));
                dataChildBean.setSeries(jSONObject.optString("series"));
                dataChildBean.setTitle(jSONObject.optString("title"));
                dataChildBean.setDescription(jSONObject.optString("description"));
                dataChildBean.setCover_img(jSONObject.optString("cover_img"));
                Intent intent3 = new Intent();
                intent3.setClass(ProjectApp.instance(), CourseListChpterActivity.class);
                intent3.putExtra("vidteaching", dataChildBean);
                intent3.setFlags(268435456);
                return;
            }
            String str = QuestionSetListActivity.TYPE_VALUE_MO_KAO;
            switch (iOptInt) {
                case 4:
                    if (isLogin()) {
                        ProjectApp.questExamDataList.clear();
                        ProjectApp.questExamList.clear();
                        Intent intent4 = new Intent(ProjectApp.instance().getTopActivity(), (Class<?>) EstimateExplainActivity.class);
                        intent4.putExtra("exam_id", jSONObject.optString("id"));
                        intent4.putExtra("question_file", jSONObject.optString("question_file"));
                        intent4.putExtra("title", jSONObject.optString("show_title"));
                        intent4.putExtra("collection_id", jSONObject.optString("collection_id"));
                        ProjectApp.instance().getTopActivity().startActivity(intent4);
                        return;
                    }
                    return;
                case 5:
                    Intent intent5 = new Intent(ProjectApp.instance().getTopActivity(), (Class<?>) WebLongSaveActivity.class);
                    intent5.putExtra("title", ProjectApp.instance().getResources().getString(R.string.app_name));
                    intent5.putExtra("web_url", jSONObject.optString("web_link"));
                    ProjectApp.instance().getTopActivity().startActivity(intent5);
                    return;
                case 6:
                    if (!isLogin() || "1".equals(SharePreferencesUtils.readStrConfig(CommonParameter.isHideExp, ProjectApp.instance()))) {
                        return;
                    }
                    ProjectApp.instance().getTopActivity().startActivity(new Intent(ProjectApp.instance(), (Class<?>) SearchExperienceAct.class));
                    return;
                case 7:
                    EventBus.getDefault().post("JumpZHibo");
                    ProjectApp.instance().closeAllActivityWithoutHome();
                    return;
                case 8:
                    if (isLogin()) {
                        SharePreferencesUtils.writeBooleanConfig(CommonParameter.SYStem_Red_Dot, false, ProjectApp.instance());
                        EventBus.getDefault().post(CommonParameter.SYStem_Red_Dot);
                        ProjectApp.instance().getTopActivity().startActivity(new Intent(ProjectApp.instance().getTopActivity(), (Class<?>) SystemNoticeAct.class));
                        return;
                    }
                    return;
                case 9:
                    if (isLogin()) {
                        Intent intent6 = new Intent(ProjectApp.instance().getTopActivity(), (Class<?>) ActCourseOrGoodsDetail.class);
                        intent6.putExtra("goods_id", jSONObject.optString("id"));
                        intent6.putExtra("detailType", 2);
                        ProjectApp.instance().getTopActivity().startActivity(intent6);
                        return;
                    }
                    return;
                case 10:
                    break;
                case 11:
                    if (isLogin()) {
                        boolean zEquals = "2".equals(jSONObject.optString("show_type"));
                        Activity topActivity = ProjectApp.instance().getTopActivity();
                        String strOptString = jSONObject.optString("id");
                        if (!zEquals) {
                            str = QuestionSetListActivity.TYPE_VALUE_TI_DAN;
                        }
                        MoreTiDanActivity.gotoMoreTiDanActivity(topActivity, strOptString, str, jSONObject.optString("show_title"));
                        return;
                    }
                    return;
                case 12:
                    if (isLogin()) {
                        Intent intent7 = new Intent(ProjectApp.instance().getTopActivity(), (Class<?>) VolunteerRankingAct.class);
                        intent7.putExtra("flag", "2");
                        ProjectApp.instance().getTopActivity().startActivity(intent7);
                        return;
                    }
                    return;
                case 13:
                    if (isLogin()) {
                        ProjectApp.instance().getTopActivity().startActivity(new Intent(ProjectApp.instance().getTopActivity(), (Class<?>) MemberCenterActivity.class));
                        return;
                    }
                    return;
                case 14:
                case 16:
                    String strOptString2 = jSONObject.optString("id");
                    String strOptString3 = jSONObject.optString("module_type");
                    if (TextUtils.isEmpty(strOptString3)) {
                        return;
                    }
                    this.module_type = strOptString3;
                    QuestionDataRequest.getIntance(ProjectApp.instance()).questionInfo(strOptString2, strOptString3, this);
                    return;
                case 15:
                    if (isLogin()) {
                        Intent intent8 = new Intent(ProjectApp.instance().getTopActivity(), (Class<?>) VolunteerRankingAct.class);
                        intent8.putExtra("flag", "1");
                        ProjectApp.instance().getTopActivity().startActivity(intent8);
                        return;
                    }
                    return;
                default:
                    switch (iOptInt) {
                        case 18:
                            if (isLogin()) {
                                if (!jSONObject.optString("is_vip").equals("1") || !UserConfig.getInstance().getUser().getIs_vip().equals("0")) {
                                    Intent intent9 = new Intent(ProjectApp.instance().getTopActivity(), (Class<?>) CircleInfoActivity.class);
                                    intent9.putExtra("article_id", jSONObject.optString("id"));
                                    intent9.putExtra("commentCount", "0");
                                    intent9.putExtra("channel_id", "0");
                                    intent9.putExtra("module_type", 16);
                                    intent9.putExtra("group_id", "" + jSONObject.optString("group_id"));
                                    ProjectApp.instance().getTopActivity().startActivity(intent9);
                                    break;
                                } else {
                                    ProjectApp.instance().getTopActivity().startActivity(new Intent(ProjectApp.instance().getTopActivity(), (Class<?>) MemberCenterActivity.class));
                                    break;
                                }
                            }
                            break;
                        case 19:
                            if (isLogin()) {
                                EventBus.getDefault().post("jumpToCourseList");
                                ProjectApp.instance().closeAllActivityWithoutHome();
                                break;
                            }
                            break;
                        case 20:
                            break;
                        case 21:
                            if (isLogin()) {
                                EventBus.getDefault().post(new GoToLivingEvent("1", s2));
                                break;
                            }
                            break;
                        case 22:
                            if (isLogin()) {
                                Intent intent10 = new Intent(ProjectApp.instance().getTopActivity(), (Class<?>) ActCourseOrGoodsDetail.class);
                                intent10.putExtra("course_id", jSONObject.optString("id"));
                                ProjectApp.instance().getTopActivity().startActivity(intent10);
                                break;
                            }
                            break;
                        case 23:
                            if (isLogin()) {
                                Intent intent11 = new Intent(ProjectApp.instance().getTopActivity(), (Class<?>) MemberCenterActivity.class);
                                intent11.putExtra("psotision", 1);
                                ProjectApp.instance().getTopActivity().startActivity(intent11);
                                break;
                            }
                            break;
                        case 24:
                            boolean zEquals2 = "1".equals(SharePreferencesUtils.readStrConfig(CommonParameter.isHideExp, ProjectApp.instance()));
                            if (isLogin() && !zEquals2) {
                                Intent intent12 = new Intent(ProjectApp.instance().getTopActivity(), (Class<?>) HandoutsInfoActivity.class);
                                intent12.putExtra("cat_id", jSONObject.optString("cid"));
                                intent12.putExtra("article", jSONObject.optString("id"));
                                intent12.putExtra("json_path", jSONObject.optString("json_path"));
                                intent12.putExtra("h5_path", jSONObject.optString("h5_path"));
                                intent12.putExtra("is_rich_text", jSONObject.optString("is_rich_text"));
                                intent12.putExtra("html_path", "");
                                intent12.putExtra("index", jSONObject.optString("cid") + StrPool.UNDERLINE + jSONObject.optString("id"));
                                ProjectApp.instance().getTopActivity().startActivity(intent12);
                                break;
                            }
                            break;
                        case 25:
                            QuestionDataRequest.getIntance(ProjectApp.instance()).getCircleAccess(jSONObject.optString("id"), this);
                            break;
                        case 26:
                            EventBus.getDefault().post("jumpToStore");
                            ProjectApp.instance().closeAllActivityWithoutHome();
                            break;
                        case 27:
                        case 28:
                            Intent intent13 = new Intent();
                            intent13.setClass(ProjectApp.instance().getTopActivity(), ActCourseOrGoodsDetail.class);
                            intent13.putExtra("course_id", jSONObject.optString("id"));
                            ProjectApp.instance().getTopActivity().startActivity(intent13);
                            break;
                        case 29:
                            if (isLogin()) {
                                EventBus.getDefault().post("jumpToQuestionList");
                                QuestionSetListActivity.INSTANCE.navigationToQuestionListActivity(ProjectApp.instance().getTopActivity(), QuestionSetListActivity.TYPE_VALUE_TI_DAN);
                                break;
                            }
                            break;
                        case 30:
                            ProjectApp.instance().getTopActivity().startActivity(new Intent(ProjectApp.instance().getTopActivity(), (Class<?>) InformationProjectAct.class));
                            break;
                        case 31:
                            String strOptString4 = jSONObject.optString("is_del");
                            String strOptString5 = jSONObject.optString("id");
                            if (!strOptString4.equals("1")) {
                                jumpInfoDetails(strOptString5, jSONObject.optString("web_link"));
                                break;
                            } else {
                                ToastUtil.shortToast(ProjectApp.instance(), "抱歉，资料已隐藏或删除");
                                break;
                            }
                        case 32:
                            ProjectApp.instance().getTopActivity().startActivity(new Intent(ProjectApp.instance().getTopActivity(), (Class<?>) BookStoreActivity.class));
                            break;
                        case 33:
                            String strOptString6 = jSONObject.optString("is_del");
                            String strOptString7 = jSONObject.optString("id");
                            if (!strOptString6.equals("1")) {
                                String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance(), "1");
                                String admin = UserConfig.getInstance().getUser().getAdmin();
                                String avatar = UserConfig.getInstance().getUser().getAvatar();
                                Intent intent14 = new Intent(ProjectApp.instance().getTopActivity(), (Class<?>) ReadBookActivity.class);
                                intent14.putExtra("book_id", strOptString7);
                                intent14.putExtra("user_id", UserConfig.getUserId());
                                intent14.putExtra("app_id", strConfig);
                                intent14.putExtra(PLVSocketUserConstant.ROLE_ADMIN, admin);
                                intent14.putExtra("avatar", avatar);
                                intent14.putExtra("token", UserConfig.getInstance().getUser().getToken());
                                intent14.putExtra("secret", UserConfig.getInstance().getUser().getSecret());
                                ProjectApp.instance().getTopActivity().startActivity(intent14);
                                break;
                            } else {
                                ToastUtil.shortToast(ProjectApp.instance(), "抱歉，图书已隐藏或删除");
                                break;
                            }
                        case 34:
                            ProjectApp.instance().getTopActivity().startActivity(new Intent(ProjectApp.instance().getTopActivity(), (Class<?>) Circle24HotListActivity.class));
                            break;
                        case 35:
                            String strOptString8 = jSONObject.optString("course_is_del");
                            String strOptString9 = jSONObject.optString("is_del");
                            String strOptString10 = jSONObject.optString("is_permission");
                            String strOptString11 = jSONObject.optString("course_id");
                            if (!TextUtils.equals("1", strOptString8)) {
                                if (!TextUtils.equals("1", strOptString9)) {
                                    if (!TextUtils.equals("1", strOptString8) && !"1".equals(strOptString10)) {
                                        Intent intent15 = new Intent(ProjectApp.instance().getTopActivity(), (Class<?>) ActCourseOrGoodsDetail.class);
                                        intent15.putExtra("course_id", strOptString11);
                                        ProjectApp.instance.getTopActivity().startActivity(intent15);
                                        break;
                                    } else if (!TextUtils.equals("1", strOptString8) && !TextUtils.equals("1", strOptString9) && "1".equals(strOptString10)) {
                                        Intent intent16 = new Intent(ProjectApp.instance(), (Class<?>) AliPlayerVideoPlayActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("course_id", strOptString11);
                                        bundle.putString("obj_id", jSONObject.optString("id"));
                                        bundle.putInt("module_type", 8);
                                        bundle.putString("watch_permission", "1");
                                        bundle.putSerializable("commentEnum", DiscussStatus.CourseReview);
                                        intent16.putExtras(bundle);
                                        ProjectApp.instance.getTopActivity().startActivity(intent16);
                                        break;
                                    }
                                } else {
                                    ToastUtil.shortToast(ProjectApp.instance(), "抱歉，视频已下架");
                                    break;
                                }
                            } else {
                                ToastUtil.shortToast(ProjectApp.instance(), "抱歉，课程已下架");
                                break;
                            }
                            break;
                        case 36:
                            String strOptString12 = jSONObject.optString("is_permission");
                            String strOptString13 = jSONObject.optString("chapter_id");
                            String strOptString14 = jSONObject.optString("id");
                            String strOptString15 = jSONObject.optString("type");
                            if (!"1".equals(strOptString12)) {
                                NavigationUtilKt.goToCourseOrGoodsDetailNewTask(ProjectApp.instance().getTopActivity(), strOptString14, "");
                                break;
                            } else {
                                CourseDirectoryActivity.goToCourseDirectoryActivity(ProjectApp.instance().getTopActivity(), strOptString14, strOptString15, strOptString13);
                                break;
                            }
                        case 37:
                            if (isLogin()) {
                                ProjectApp.instance.getTopActivity().startActivity(RedEnvelopeCouponsAct.newIntent(ProjectApp.instance().getTopActivity(), false));
                                break;
                            }
                            break;
                        case 38:
                            if (isLogin()) {
                                CombineQuestionMainNewActivity.gotoCombineQuestionMain(ProjectApp.instance().getTopActivity(), null);
                                break;
                            }
                            break;
                        case 39:
                            if (isLogin()) {
                                StudyReportAct.newIntent(ProjectApp.instance().getTopActivity());
                                break;
                            }
                            break;
                        case 40:
                            if (isLogin()) {
                                KnowledgeMapAct.newIntent(ProjectApp.instance().getTopActivity(), 1, "0", "", "");
                                break;
                            }
                            break;
                        case 41:
                            if (isLogin()) {
                                QuestionSetListActivity.INSTANCE.navigationToQuestionListActivity(ProjectApp.instance().getTopActivity(), QuestionSetListActivity.TYPE_VALUE_MO_KAO);
                                break;
                            }
                            break;
                        case 42:
                            if (isLogin()) {
                                String strOptString16 = jSONObject.optString("id");
                                String strOptString17 = jSONObject.optString("title");
                                if (TextUtils.isEmpty(strOptString17)) {
                                    strOptString17 = jSONObject.optString("name");
                                }
                                MoreTiDanActivity.gotoMoreTiDanActivity(ProjectApp.instance().getTopActivity(), strOptString16, QuestionSetListActivity.TYPE_VALUE_MO_KAO, strOptString17);
                                break;
                            }
                            break;
                        case 43:
                            if (isLogin()) {
                                StatisticsMainActivity.INSTANCE.navigationToStatisticsMain(ProjectApp.instance().getTopActivity());
                                break;
                            }
                            break;
                        case 44:
                            if (isLogin()) {
                                SharePreferencesUtils.writeStrConfig("KNOWLEDGE_CHAPTER_ID", "-1", ProjectApp.instance());
                                EventBus.getDefault().post("jump2KnowledgeChapter");
                                ProjectApp.instance().return2Home();
                                break;
                            }
                            break;
                        case 45:
                            if (isLogin()) {
                                String strOptString18 = jSONObject.optString("daily_task");
                                if (!strOptString18.equals("1")) {
                                    if (!strOptString18.equals("2")) {
                                        ProjectApp.instance().getTopActivity().startActivity(new Intent(ProjectApp.instance().getTopActivity(), (Class<?>) StudyPlanListActivity.class));
                                        break;
                                    } else {
                                        ProjectApp.instance().getTopActivity().startActivity(new Intent(ProjectApp.instance().getTopActivity(), (Class<?>) CustomStudyPlanActivity.class));
                                        break;
                                    }
                                } else {
                                    ProjectApp.instance().getTopActivity().startActivity(new Intent(ProjectApp.instance().getTopActivity(), (Class<?>) DailyTasksActivity.class));
                                    break;
                                }
                            }
                            break;
                        default:
                            switch (iOptInt) {
                                case 47:
                                    if (isLogin()) {
                                        ProjectApp.instance().getTopActivity().startActivity(MyFollowSchoolsActivity.newIntent(ProjectApp.instance().getTopActivity()));
                                        break;
                                    }
                                    break;
                                case 48:
                                    if (isLogin()) {
                                        ProjectApp.instance().getTopActivity().startActivity(SchoolMajorRankingActivity.newIntent(ProjectApp.instance().getTopActivity(), 0));
                                        break;
                                    }
                                    break;
                                case 49:
                                    if (isLogin()) {
                                        ProjectApp.instance().getTopActivity().startActivity(SchoolMajorRankingActivity.newIntent(ProjectApp.instance().getTopActivity(), 1));
                                        break;
                                    }
                                    break;
                                case 50:
                                    CommonUtil.jump1v1(ProjectApp.instance().getTopActivity(), jSONObject.optString("qr_code"), jSONObject.optString("we_chat_id"));
                                    break;
                                case 51:
                                    if (isLogin()) {
                                        PostgraduateCalendarActivity.INSTANCE.goToPostgraduateCalendarActivity(ProjectApp.instance().getTopActivity());
                                        break;
                                    }
                                    break;
                                case 52:
                                    if (isLogin()) {
                                        AdmissionBrochureAct.newIntent(ProjectApp.instance().getTopActivity(), jSONObject.optString("school_id"));
                                        break;
                                    }
                                    break;
                                case 53:
                                    if (isLogin()) {
                                        RecruitmentInformationAct.newIntent(ProjectApp.instance().getTopActivity(), jSONObject.optString("school_id"));
                                        break;
                                    }
                                    break;
                                case 54:
                                    if (isLogin()) {
                                        SchoolOpenMajorAct.newIntent(ProjectApp.instance().getTopActivity(), jSONObject.optString("school_id"));
                                        break;
                                    }
                                    break;
                                case 55:
                                    if (isLogin()) {
                                        RecruitmentDepartmentAct.newIntent(ProjectApp.instance().getTopActivity(), jSONObject.optString("school_id"));
                                        break;
                                    }
                                    break;
                                case 56:
                                    if (isLogin()) {
                                        FractionBarAct.newIntent(ProjectApp.instance().getTopActivity(), jSONObject.optString("school_id"));
                                        break;
                                    }
                                    break;
                                case 57:
                                    if (isLogin()) {
                                        EnrollmentNumberAct.newIntent(ProjectApp.instance().getTopActivity(), jSONObject.optString("school_id"));
                                        break;
                                    }
                                    break;
                                default:
                                    switch (iOptInt) {
                                        case 59:
                                            if (isLogin()) {
                                                SchoolDetailsAct.newIntent(ProjectApp.instance().getTopActivity(), jSONObject.optString("id"));
                                                break;
                                            }
                                            break;
                                        case 60:
                                            if (isLogin()) {
                                                ChooseSchoolMainActivity.INSTANCE.navigationToChooseSchoolMainActivity(ProjectApp.instance().getTopActivity());
                                                break;
                                            }
                                            break;
                                        case 61:
                                            if (isLogin()) {
                                                String strOptString19 = jSONObject.optString("web_link");
                                                if (TextUtils.isEmpty(strOptString19)) {
                                                    strOptString19 = "pages/index/startPage";
                                                }
                                                CommonUtil.jumpWxProgram(this.mContext, strOptString19);
                                                break;
                                            }
                                            break;
                                        default:
                                            switch (iOptInt) {
                                                case 150:
                                                    if (isLogin()) {
                                                        OrderDetailActivity.INSTANCE.getToOrderDetailNewTask(ProjectApp.instance().getTopActivity(), jSONObject.optString(ActSubmitGoodsComment.EXTRA_DATA_ORDER_NO));
                                                        break;
                                                    }
                                                    break;
                                                case 151:
                                                    if (isLogin()) {
                                                        NavigationUtilKt.goToActSubmitGoodsComment(ProjectApp.instance().getTopActivity(), jSONObject.optString("goods_id"), jSONObject.optString(ActSubmitGoodsComment.EXTRA_DATA_ORDER_NO), "5".equals(jSONObject.optString("goods_type")), false);
                                                        break;
                                                    }
                                                    break;
                                                case 152:
                                                    if (isLogin()) {
                                                        Intent intent17 = new Intent();
                                                        intent17.setClass(ProjectApp.instance().getTopActivity(), NewFriendsAct.class);
                                                        ProjectApp.instance().getTopActivity().startActivity(intent17);
                                                        break;
                                                    }
                                                    break;
                                                default:
                                                    switch (iOptInt) {
                                                        case 155:
                                                            if (!jSONObject.optString("is_del").equals("1")) {
                                                                String strOptString20 = jSONObject.optString("book_id");
                                                                String strOptString21 = jSONObject.optString("chapter_id");
                                                                int iOptInt2 = jSONObject.optInt(PreferKeyKt.START_POSITION);
                                                                String strConfig2 = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance(), "1");
                                                                String admin2 = UserConfig.getInstance().getUser().getAdmin();
                                                                String avatar2 = UserConfig.getInstance().getUser().getAvatar();
                                                                String token = UserConfig.getInstance().getUser().getToken();
                                                                String secret = UserConfig.getInstance().getUser().getSecret();
                                                                Intent intent18 = new Intent(ProjectApp.instance().getTopActivity(), (Class<?>) ReadBookActivity.class);
                                                                intent18.putExtra("book_id", strOptString20);
                                                                intent18.putExtra("user_id", UserConfig.getUserId());
                                                                intent18.putExtra("app_id", strConfig2);
                                                                intent18.putExtra(PLVSocketUserConstant.ROLE_ADMIN, admin2);
                                                                intent18.putExtra("avatar", avatar2);
                                                                intent18.putExtra("token", token);
                                                                intent18.putExtra("secret", secret);
                                                                intent18.putExtra("chapterId", strOptString21);
                                                                intent18.putExtra("position", iOptInt2);
                                                                ProjectApp.instance().getTopActivity().startActivity(intent18);
                                                                break;
                                                            } else {
                                                                ToastUtil.shortToast(ProjectApp.instance(), "抱歉，图书已隐藏或删除");
                                                                break;
                                                            }
                                                        case 156:
                                                            if (isLogin()) {
                                                                shengYunRegister(156);
                                                                break;
                                                            }
                                                            break;
                                                        case 157:
                                                            if (isLogin()) {
                                                                shengYunRegister(157);
                                                                break;
                                                            }
                                                            break;
                                                        case 158:
                                                            final String strOptString22 = jSONObject.optString("identity_id");
                                                            String strOptString23 = jSONObject.optString("identity_name");
                                                            final Activity aliPlayerVideoPlayActivity = ProjectApp.instance().getTopActivityName().equals("AliPlayerVideoPlayActivity") ? AliPlayerVideoPlayActivity.getInstance() : ProjectApp.instance().getTopActivity();
                                                            CommonTwoBtnDialog commonTwoBtnDialog = new CommonTwoBtnDialog(aliPlayerVideoPlayActivity, new CommonTwoBtnDialog.ClickIml() { // from class: com.psychiatrygarden.utils.v
                                                                @Override // com.psychiatrygarden.widget.CommonTwoBtnDialog.ClickIml
                                                                public final void mClickIml() {
                                                                    this.f16252a.lambda$mToActivity$0(aliPlayerVideoPlayActivity, strOptString22);
                                                                }
                                                            }, new SpannableStringBuilder("是否切换至" + strOptString23 + "题库？"), "温馨提示", "取消", "确定");
                                                            if (!ProjectApp.instance().getTopActivityName().equals("AliPlayerVideoPlayActivity")) {
                                                                new XPopup.Builder(ProjectApp.instance().getTopActivity()).asCustom(commonTwoBtnDialog).show();
                                                                break;
                                                            } else {
                                                                new XPopup.Builder(aliPlayerVideoPlayActivity).asCustom(commonTwoBtnDialog).show();
                                                                break;
                                                            }
                                                    }
                                            }
                                    }
                            }
                    }
                    return;
            }
            if (isLogin()) {
                EventBus.getDefault().post("jumpForum");
                ProjectApp.instance().closeAllActivityWithoutHome();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onFailure(Throwable t2, int errorNo, String strMsg, String requstUrl) {
        ToastUtil.shortToast(ProjectApp.instance(), "获取题失败！");
        ProjectApp.instance().hideDialogWindow();
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onStart(String requstUrl) {
        if (requstUrl.equals(NetworkRequestsURL.questionUserAnswerApi)) {
            return;
        }
        ProjectApp.instance().showDialogWindow();
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onSuccess(String str, String requestUrl) {
        ProjectApp.instance().hideDialogWindow();
        if (requestUrl.equals(NetworkRequestsURL.questionUserAnswerApi)) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.optInt("code") != 200) {
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(this.questionDetailBean);
                    ProjectApp.showQuestionList = new Gson().toJson(arrayList);
                    Bundle bundle = new Bundle();
                    bundle.putInt("position", 0);
                    bundle.putString("module_type", this.module_type + "");
                    bundle.putString("subject_title", this.questionDetailBean.getChapter_parent_title());
                    bundle.putString("chapter_title", this.questionDetailBean.getChapter_title());
                    AnswerQuestionActivity.gotoActivity(ProjectApp.instance(), bundle);
                    return;
                }
                ArrayList arrayList2 = new ArrayList();
                if (jSONObject.getJSONArray("data").length() > 0) {
                    this.questionDetailBean.setUser_answer(jSONObject.getJSONArray("data").getJSONObject(0).optString("answer"));
                }
                arrayList2.add(this.questionDetailBean);
                ProjectApp.showQuestionList = new Gson().toJson(arrayList2);
                Bundle bundle2 = new Bundle();
                bundle2.putInt("position", 0);
                bundle2.putString("module_type", this.module_type + "");
                bundle2.putString("subject_title", this.questionDetailBean.getChapter_parent_title());
                bundle2.putString("chapter_title", this.questionDetailBean.getChapter_title());
                AnswerQuestionActivity.gotoActivity(ProjectApp.instance(), bundle2);
                return;
            } catch (Exception e2) {
                e2.printStackTrace();
                return;
            }
        }
        if (requestUrl.equals(NetworkRequestsURL.questionInfoApi)) {
            try {
                JSONObject jSONObject2 = new JSONObject(str);
                if (jSONObject2.optInt("code") != 200) {
                    ToastUtil.shortToast(ProjectApp.instance(), jSONObject2.optString("message"));
                    return;
                }
                QuestionDetailBean questionDetailBean = (QuestionDetailBean) new Gson().fromJson(DesUtil.decode(CommonParameter.DES_KEY_VERIFY, jSONObject2.optString("data")), QuestionDetailBean.class);
                this.questionDetailBean = questionDetailBean;
                if (TextUtils.isEmpty(questionDetailBean.getSort())) {
                    this.questionDetailBean.setSort("1");
                }
                AjaxParams ajaxParams = new AjaxParams();
                ajaxParams.put("question_id", this.questionDetailBean.getId());
                ajaxParams.put("module_type", this.module_type + "");
                QuestionDataRequest.getIntance(ProjectApp.instance()).questionUserAnswer(ajaxParams, this);
                return;
            } catch (Exception e3) {
                e3.printStackTrace();
                return;
            }
        }
        if (requestUrl.equals(NetworkRequestsURL.getCircleAccess)) {
            try {
                JSONObject jSONObject3 = new JSONObject(str);
                if (jSONObject3.optInt("code") != 200) {
                    ToastUtil.shortToast(ProjectApp.instance(), jSONObject3.optString("message"));
                    return;
                }
                String strOptString = jSONObject3.optJSONObject("data").optString("no_access");
                if (!TextUtils.isEmpty(strOptString) && strOptString.equals("1")) {
                    Intent intent = new Intent(ProjectApp.instance().getTopActivity(), (Class<?>) MemberCenterActivity.class);
                    intent.putExtra("psotision", 0);
                    ProjectApp.instance().getTopActivity().startActivity(intent);
                } else {
                    Intent intent2 = new Intent(ProjectApp.instance().getTopActivity(), (Class<?>) CircleInfoActivity.class);
                    intent2.putExtra("article_id", jSONObject3.optJSONObject("data").optString("id"));
                    intent2.putExtra("commentCount", "0");
                    intent2.putExtra("channel_id", "0");
                    intent2.putExtra("module_type", 12);
                    ProjectApp.instance().getTopActivity().startActivity(intent2);
                }
            } catch (Exception e4) {
                e4.printStackTrace();
            }
        }
    }
}
