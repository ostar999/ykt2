package com.psychiatrygarden.activity.vip.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.ActCourseOrGoodsDetail;
import com.psychiatrygarden.activity.ActCourseStructure;
import com.psychiatrygarden.activity.LoginActivity;
import com.psychiatrygarden.activity.WebLongSaveActivity;
import com.psychiatrygarden.activity.courselist.YkBManager;
import com.psychiatrygarden.activity.mine.order.OrderConfirmActivity;
import com.psychiatrygarden.activity.online.ChartAnswerSheetActivity;
import com.psychiatrygarden.activity.rank.bean.ActivityBean;
import com.psychiatrygarden.activity.rank.pop.ShareBoxPopWindow;
import com.psychiatrygarden.activity.rank.utils.UMShareListenerIml;
import com.psychiatrygarden.activity.vip.MemberCenterActivity;
import com.psychiatrygarden.activity.vip.Utils.MemInterface;
import com.psychiatrygarden.activity.vip.bean.MemCenterBean;
import com.psychiatrygarden.activity.vip.pop.MemberPopupwindow;
import com.psychiatrygarden.bean.OrderConfirmParams;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.ShareEvent;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.LocalBroadcastManager;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.Md5Util;
import com.psychiatrygarden.utils.NavigationUtilKt;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.ChatGroupRulePopwindow;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yddmi.doctor.pages.physical.PhysicalActivity;
import de.greenrobot.event.EventBus;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class MemInterface {
    private static volatile MemInterface mMemInterface;
    private int disType;
    private JumpCourseListener mJumpCourseListener;
    private MoreMethodLisener mMoreLockMethod;
    private ShareSuccessListener mShareSuccessListener;
    private UShareListener mUShareListener;
    private MemberPopupwindow popupwindow;
    private ShowOrHideView showOrHideView;
    private String activity_id = "";
    private String item_id = "";
    private String category = "";

    /* renamed from: com.psychiatrygarden.activity.vip.Utils.MemInterface$10, reason: invalid class name */
    public class AnonymousClass10 implements ShareBoxPopWindow.ShareListener {
        final /* synthetic */ Activity val$activity;
        final /* synthetic */ ShareBoxPopWindow val$msharebox;
        final /* synthetic */ ActivityBean.DataBean.ShareInfoBean val$shareInfoBean;

        public AnonymousClass10(final Activity val$activity, final ShareBoxPopWindow val$msharebox, final ActivityBean.DataBean.ShareInfoBean val$shareInfoBean) {
            this.val$activity = val$activity;
            this.val$msharebox = val$msharebox;
            this.val$shareInfoBean = val$shareInfoBean;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$mShareDataListener$0() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$mShareDataListener$1(Activity activity, ShareBoxPopWindow shareBoxPopWindow) {
            EventBus.getDefault().post("shareClick");
            SharePreferencesUtils.writeStrConfig("statisticsPermission", "1", activity);
            shareBoxPopWindow.dismiss();
        }

        @Override // com.psychiatrygarden.activity.rank.pop.ShareBoxPopWindow.ShareListener
        public void mShareCloseListener() {
            if (!"com.psychiatrygarden.activity.HomePageNewActivity".equals(this.val$activity.getLocalClassName()) && !"com.psychiatrygarden.activity.CombineQuestionActivity".equals(this.val$activity.getLocalClassName())) {
                Activity activity = this.val$activity;
                if (!(activity instanceof ActCourseOrGoodsDetail) && !(activity instanceof ChartAnswerSheetActivity)) {
                    if (MemInterface.this.disType != 1) {
                        this.val$activity.finish();
                    }
                    this.val$msharebox.dismiss();
                    EventBus.getDefault().post("closePage");
                    return;
                }
            }
            this.val$msharebox.dismiss();
        }

        @Override // com.psychiatrygarden.activity.rank.pop.ShareBoxPopWindow.ShareListener
        public void mShareDataListener() {
        }

        @Override // com.psychiatrygarden.activity.rank.pop.ShareBoxPopWindow.ShareListener
        public void mShareDataListener(int type) {
            if (type != 4) {
                Activity activity = this.val$activity;
                String str = MemInterface.this.activity_id;
                ActivityBean.DataBean.ShareInfoBean shareInfoBean = this.val$shareInfoBean;
                UMShareListenerIml uMShareListenerIml = new UMShareListenerIml() { // from class: com.psychiatrygarden.activity.vip.Utils.MemInterface.10.1
                    @Override // com.psychiatrygarden.activity.rank.utils.UMShareListenerIml, com.umeng.socialize.UMShareListener
                    public void onCancel(SHARE_MEDIA share_media) {
                        ToastUtil.shortToast(AnonymousClass10.this.val$activity, "用户取消分享");
                    }

                    @Override // com.psychiatrygarden.activity.rank.utils.UMShareListenerIml, com.umeng.socialize.UMShareListener
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        ToastUtil.shortToast(AnonymousClass10.this.val$activity, "温馨提示：您中途取消分享，或者没安装正式版qq可能导致分享失败");
                    }

                    @Override // com.psychiatrygarden.activity.rank.utils.UMShareListenerIml, com.umeng.socialize.UMShareListener
                    public void onResult(SHARE_MEDIA share_media) {
                        AnonymousClass10.this.val$msharebox.dismiss();
                        MemInterface.this.mUShareListener.mUShareListener();
                        ToastUtil.shortToast(AnonymousClass10.this.val$activity, "分享成功");
                        if (MemInterface.this.popupwindow != null && MemInterface.this.popupwindow.isShow()) {
                            MemInterface.this.popupwindow.dismiss();
                        }
                        if (MemInterface.this.mShareSuccessListener != null) {
                            MemInterface.this.mShareSuccessListener.shareSuccess(MemInterface.this.activity_id);
                        }
                        LocalBroadcastManager.getInstance(AnonymousClass10.this.val$activity).sendBroadcast(new Intent().setAction("SHARE_SUCCESS"));
                    }

                    @Override // com.psychiatrygarden.activity.rank.utils.UMShareListenerIml, com.umeng.socialize.UMShareListener
                    public void onStart(SHARE_MEDIA share_media) {
                    }
                };
                final Activity activity2 = this.val$activity;
                final ShareBoxPopWindow shareBoxPopWindow = this.val$msharebox;
                CommonUtil.mShareData(activity, str, shareInfoBean, uMShareListenerIml, new CommonUtil.ClickShareLisenter() { // from class: com.psychiatrygarden.activity.vip.Utils.b
                    @Override // com.psychiatrygarden.utils.CommonUtil.ClickShareLisenter
                    public final void refreshData() {
                        MemInterface.AnonymousClass10.lambda$mShareDataListener$1(activity2, shareBoxPopWindow);
                    }
                }, MemInterface.this.mShareSuccessListener);
                return;
            }
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + this.val$activity.getPackageName()));
            intent.addFlags(268435456);
            this.val$activity.startActivity(intent);
            MemInterface.this.mUShareListener.mUShareListener();
            this.val$msharebox.dismiss();
            CommonUtil.mPutShareData(this.val$activity, MemInterface.this.activity_id, "", "", new CommonUtil.ClickShareLisenter() { // from class: com.psychiatrygarden.activity.vip.Utils.a
                @Override // com.psychiatrygarden.utils.CommonUtil.ClickShareLisenter
                public final void refreshData() {
                    MemInterface.AnonymousClass10.lambda$mShareDataListener$0();
                }
            });
        }
    }

    /* renamed from: com.psychiatrygarden.activity.vip.Utils.MemInterface$8, reason: invalid class name */
    public class AnonymousClass8 implements ShareBoxPopWindow.ShareListener {
        final /* synthetic */ Activity val$activity;
        final /* synthetic */ String val$activity_id;
        final /* synthetic */ ShareBoxPopWindow val$msharebox;
        final /* synthetic */ ActivityBean.DataBean.ShareInfoBean val$shareInfoBean;

        public AnonymousClass8(final Activity val$activity, final String val$activity_id, final ShareBoxPopWindow val$msharebox, final ActivityBean.DataBean.ShareInfoBean val$shareInfoBean) {
            this.val$activity = val$activity;
            this.val$activity_id = val$activity_id;
            this.val$msharebox = val$msharebox;
            this.val$shareInfoBean = val$shareInfoBean;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$mShareDataListener$0() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$mShareDataListener$1(String str, Activity activity, ShareBoxPopWindow shareBoxPopWindow) {
            EventBus.getDefault().post("shareClick");
            EventBus.getDefault().post(new ShareEvent(str));
            SharePreferencesUtils.writeStrConfig("statisticsPermission", "1", activity);
            shareBoxPopWindow.dismiss();
        }

        @Override // com.psychiatrygarden.activity.rank.pop.ShareBoxPopWindow.ShareListener
        public void mShareCloseListener() {
            if (!"com.psychiatrygarden.activity.HomePageNewActivity".equals(this.val$activity.getLocalClassName()) && !"com.psychiatrygarden.activity.CombineQuestionActivity".equals(this.val$activity.getLocalClassName())) {
                Activity activity = this.val$activity;
                if (!(activity instanceof ActCourseOrGoodsDetail) && !(activity instanceof ChartAnswerSheetActivity)) {
                    if (MemInterface.this.disType != 1) {
                        this.val$activity.finish();
                    }
                    this.val$msharebox.dismiss();
                    EventBus.getDefault().post("closePage");
                    return;
                }
            }
            this.val$msharebox.dismiss();
        }

        @Override // com.psychiatrygarden.activity.rank.pop.ShareBoxPopWindow.ShareListener
        public void mShareDataListener() {
        }

        @Override // com.psychiatrygarden.activity.rank.pop.ShareBoxPopWindow.ShareListener
        public void mShareDataListener(int type) {
            if (type != 4) {
                Activity activity = this.val$activity;
                String str = this.val$activity_id;
                ActivityBean.DataBean.ShareInfoBean shareInfoBean = this.val$shareInfoBean;
                UMShareListenerIml uMShareListenerIml = new UMShareListenerIml() { // from class: com.psychiatrygarden.activity.vip.Utils.MemInterface.8.1
                    @Override // com.psychiatrygarden.activity.rank.utils.UMShareListenerIml, com.umeng.socialize.UMShareListener
                    public void onCancel(SHARE_MEDIA share_media) {
                        ToastUtil.shortToast(AnonymousClass8.this.val$activity, "用户取消分享");
                    }

                    @Override // com.psychiatrygarden.activity.rank.utils.UMShareListenerIml, com.umeng.socialize.UMShareListener
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        ToastUtil.shortToast(AnonymousClass8.this.val$activity, "温馨提示：您中途取消分享，或者没安装正式版qq可能导致分享失败");
                    }

                    @Override // com.psychiatrygarden.activity.rank.utils.UMShareListenerIml, com.umeng.socialize.UMShareListener
                    public void onResult(SHARE_MEDIA share_media) {
                        AnonymousClass8.this.val$msharebox.dismiss();
                        MemInterface.this.mUShareListener.mUShareListener();
                        ToastUtil.shortToast(AnonymousClass8.this.val$activity, "分享成功");
                        if (MemInterface.this.popupwindow != null && MemInterface.this.popupwindow.isShow()) {
                            MemInterface.this.popupwindow.dismiss();
                        }
                        if (MemInterface.this.mShareSuccessListener != null) {
                            MemInterface.this.mShareSuccessListener.shareSuccess(AnonymousClass8.this.val$activity_id);
                        }
                        LocalBroadcastManager.getInstance(AnonymousClass8.this.val$activity).sendBroadcast(new Intent().setAction("SHARE_SUCCESS"));
                    }

                    @Override // com.psychiatrygarden.activity.rank.utils.UMShareListenerIml, com.umeng.socialize.UMShareListener
                    public void onStart(SHARE_MEDIA share_media) {
                        LogUtils.d("share_start", share_media.getName());
                    }
                };
                final String str2 = this.val$activity_id;
                final Activity activity2 = this.val$activity;
                final ShareBoxPopWindow shareBoxPopWindow = this.val$msharebox;
                CommonUtil.mShareData(activity, str, shareInfoBean, uMShareListenerIml, new CommonUtil.ClickShareLisenter() { // from class: com.psychiatrygarden.activity.vip.Utils.d
                    @Override // com.psychiatrygarden.utils.CommonUtil.ClickShareLisenter
                    public final void refreshData() {
                        MemInterface.AnonymousClass8.lambda$mShareDataListener$1(str2, activity2, shareBoxPopWindow);
                    }
                }, MemInterface.this.mShareSuccessListener);
                return;
            }
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + this.val$activity.getPackageName()));
            intent.addFlags(268435456);
            this.val$activity.startActivity(intent);
            MemInterface.this.mUShareListener.mUShareListener();
            CommonUtil.mPutShareData(this.val$activity, this.val$activity_id, "", "", new CommonUtil.ClickShareLisenter() { // from class: com.psychiatrygarden.activity.vip.Utils.c
                @Override // com.psychiatrygarden.utils.CommonUtil.ClickShareLisenter
                public final void refreshData() {
                    MemInterface.AnonymousClass8.lambda$mShareDataListener$0();
                }
            });
            this.val$msharebox.dismiss();
        }
    }

    /* renamed from: com.psychiatrygarden.activity.vip.Utils.MemInterface$9, reason: invalid class name */
    public class AnonymousClass9 implements ShareBoxPopWindow.ShareListener {
        final /* synthetic */ Activity val$activity;
        final /* synthetic */ ShareBoxPopWindow val$msharebox;
        final /* synthetic */ ActivityBean.DataBean.ShareInfoBean val$shareInfoBean;

        public AnonymousClass9(final Activity val$activity, final ShareBoxPopWindow val$msharebox, final ActivityBean.DataBean.ShareInfoBean val$shareInfoBean) {
            this.val$activity = val$activity;
            this.val$msharebox = val$msharebox;
            this.val$shareInfoBean = val$shareInfoBean;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$mShareDataListener$0(Activity activity, ShareBoxPopWindow shareBoxPopWindow) {
            EventBus.getDefault().post("shareClick");
            SharePreferencesUtils.writeStrConfig("statisticsPermission", "1", activity);
            shareBoxPopWindow.dismiss();
        }

        @Override // com.psychiatrygarden.activity.rank.pop.ShareBoxPopWindow.ShareListener
        public void mShareCloseListener() {
            if (!"com.psychiatrygarden.activity.HomePageNewActivity".equals(this.val$activity.getLocalClassName()) && !"com.psychiatrygarden.activity.CombineQuestionActivity".equals(this.val$activity.getLocalClassName())) {
                Activity activity = this.val$activity;
                if (!(activity instanceof ActCourseOrGoodsDetail) && !(activity instanceof ChartAnswerSheetActivity) && !(activity instanceof PhysicalActivity)) {
                    if (MemInterface.this.disType != 1) {
                        this.val$activity.finish();
                    }
                    this.val$msharebox.dismiss();
                    EventBus.getDefault().post("closePage");
                    return;
                }
            }
            this.val$msharebox.dismiss();
        }

        @Override // com.psychiatrygarden.activity.rank.pop.ShareBoxPopWindow.ShareListener
        public void mShareDataListener() {
        }

        @Override // com.psychiatrygarden.activity.rank.pop.ShareBoxPopWindow.ShareListener
        public void mShareDataListener(int type) {
            if (type != 4) {
                Activity activity = this.val$activity;
                String str = MemInterface.this.activity_id;
                ActivityBean.DataBean.ShareInfoBean shareInfoBean = this.val$shareInfoBean;
                UMShareListenerIml uMShareListenerIml = new UMShareListenerIml() { // from class: com.psychiatrygarden.activity.vip.Utils.MemInterface.9.1
                    @Override // com.psychiatrygarden.activity.rank.utils.UMShareListenerIml, com.umeng.socialize.UMShareListener
                    public void onCancel(SHARE_MEDIA share_media) {
                        ToastUtil.shortToast(AnonymousClass9.this.val$activity, "用户取消分享");
                    }

                    @Override // com.psychiatrygarden.activity.rank.utils.UMShareListenerIml, com.umeng.socialize.UMShareListener
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        ToastUtil.shortToast(AnonymousClass9.this.val$activity, "温馨提示：您中途取消分享，或者没安装正式版qq可能导致分享失败");
                    }

                    @Override // com.psychiatrygarden.activity.rank.utils.UMShareListenerIml, com.umeng.socialize.UMShareListener
                    public void onResult(SHARE_MEDIA share_media) {
                        AnonymousClass9.this.val$msharebox.dismiss();
                        if (MemInterface.this.mUShareListener != null) {
                            MemInterface.this.mUShareListener.mUShareListener();
                        }
                        ToastUtil.shortToast(AnonymousClass9.this.val$activity, "分享成功");
                        if (MemInterface.this.popupwindow != null && MemInterface.this.popupwindow.isShow()) {
                            MemInterface.this.popupwindow.dismiss();
                        }
                        if (MemInterface.this.mShareSuccessListener != null) {
                            MemInterface.this.mShareSuccessListener.shareSuccess(MemInterface.this.activity_id);
                        }
                        LocalBroadcastManager.getInstance(AnonymousClass9.this.val$activity).sendBroadcast(new Intent().setAction("SHARE_SUCCESS"));
                    }

                    @Override // com.psychiatrygarden.activity.rank.utils.UMShareListenerIml, com.umeng.socialize.UMShareListener
                    public void onStart(SHARE_MEDIA share_media) {
                    }
                };
                final Activity activity2 = this.val$activity;
                final ShareBoxPopWindow shareBoxPopWindow = this.val$msharebox;
                CommonUtil.mShareData(activity, str, shareInfoBean, uMShareListenerIml, new CommonUtil.ClickShareLisenter() { // from class: com.psychiatrygarden.activity.vip.Utils.e
                    @Override // com.psychiatrygarden.utils.CommonUtil.ClickShareLisenter
                    public final void refreshData() {
                        MemInterface.AnonymousClass9.lambda$mShareDataListener$0(activity2, shareBoxPopWindow);
                    }
                }, MemInterface.this.mShareSuccessListener);
                return;
            }
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + this.val$activity.getPackageName()));
            intent.addFlags(268435456);
            this.val$activity.startActivity(intent);
            MemInterface.this.mUShareListener.mUShareListener();
            this.val$msharebox.dismiss();
        }
    }

    public interface JumpCourseListener {
        void jump2CourseDetail(String goodsId);
    }

    public interface MoreMethodLisener {
        void mMoreMethodLock();
    }

    public interface ShareSuccessListener {
        void shareSuccess(String activity_id);
    }

    public interface ShowOrHideView {
        void oncomplete();
    }

    public interface UShareListener {
        void mUShareListener();
    }

    private void chatGroupDialog(Activity activity, String community_id, String ykb_community_id, String popup_img) {
        ChatGroupRulePopwindow chatGroupRulePopwindow = new ChatGroupRulePopwindow(activity, community_id, ykb_community_id, popup_img);
        XPopup.Builder builder = new XPopup.Builder(activity);
        Boolean bool = Boolean.FALSE;
        builder.dismissOnTouchOutside(bool).dismissOnBackPressed(bool).asCustom(chatGroupRulePopwindow).show();
    }

    public static MemInterface getInstance() {
        if (mMemInterface == null) {
            synchronized (YkBManager.class) {
                if (mMemInterface == null) {
                    mMemInterface = new MemInterface();
                }
            }
        }
        return mMemInterface;
    }

    public void ClearVariables() {
        if (mMemInterface != null) {
            mMemInterface.setDisType(0);
            mMemInterface.setItem_id("");
            mMemInterface.setCategory("");
        }
    }

    public void courseGetMemData(final Activity mActivity, final AjaxParams params, String url, final int isShowMethodDialog, final boolean isStatistics) {
        YJYHttpUtils.post(mActivity, url, params, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.vip.Utils.MemInterface.6
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ProjectApp.instance().hideDialogWindow();
                if (MemInterface.this.showOrHideView != null) {
                    MemInterface.this.showOrHideView.oncomplete();
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                int i2 = isShowMethodDialog;
                if (i2 == 1 || i2 == 2) {
                    ProjectApp.instance().showDialogWindow();
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                String strMD5Encode;
                super.onSuccess((AnonymousClass6) s2);
                try {
                    ProjectApp.instance().hideDialogWindow();
                    MemCenterBean memCenterBean = (MemCenterBean) new Gson().fromJson(s2, MemCenterBean.class);
                    if (memCenterBean.getCode() == 200) {
                        MemInterface.this.activity_id = memCenterBean.getData().getId() + "";
                        String str = "" + memCenterBean.getData().getPass();
                        if (params.getParam().get("module_name") == null || "".equals(params.getParam().get("module_name"))) {
                            strMD5Encode = "1";
                        } else {
                            String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, mActivity);
                            strMD5Encode = Md5Util.MD5Encode(Md5Util.MD5Encode(UserConfig.getUserId() + params.getParam().get("id") + strConfig + params.getParam().get(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID)) + "SmpaaK0eEwCxdiU9sGkbI7GvEQ0WbaJD1");
                            str = memCenterBean.getData().getPermission() + "";
                        }
                        if (str.equals(strMD5Encode)) {
                            if (MemInterface.this.popupwindow != null && MemInterface.this.popupwindow.isShow()) {
                                MemInterface.this.popupwindow.dismiss();
                            }
                            MemInterface.this.mUShareListener.mUShareListener();
                        } else if ("1".equals(params.getParam().get("giveWayType"))) {
                            MemInterface.this.putMemean(mActivity, memCenterBean.getData().getName(), memCenterBean.getData().getWays(), isStatistics, isShowMethodDialog, memCenterBean.getData().getId() + "", true);
                        } else {
                            MemInterface.this.putMemean(mActivity, memCenterBean.getData().getName(), memCenterBean.getData().getWays(), isStatistics, isShowMethodDialog, memCenterBean.getData().getId() + "");
                        }
                    } else {
                        ToastUtil.shortToast(mActivity, memCenterBean.getMessage() + "");
                        if ("请登录".equals(memCenterBean.getMessage())) {
                            Intent intent = new Intent(ProjectApp.instance(), (Class<?>) LoginActivity.class);
                            intent.setFlags(268435456);
                            ProjectApp.instance().startActivity(intent);
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                if (MemInterface.this.showOrHideView != null) {
                    MemInterface.this.showOrHideView.oncomplete();
                }
            }
        });
    }

    public String getCategory() {
        return this.category;
    }

    public int getDisType() {
        return this.disType;
    }

    public void getFileFee(Activity mActivity, String price, String fileId, String way) {
        OrderConfirmParams orderConfirmParams = new OrderConfirmParams();
        if (way.equals("buy_ebook")) {
            orderConfirmParams.setEbook_id(fileId).setPrice(price).setGoodType("4");
        } else {
            orderConfirmParams.setEnclosure_id(fileId).setPrice(price).setGoodType("5");
        }
        OrderConfirmActivity.goToOrderConfirmEntrance(mActivity, orderConfirmParams);
    }

    public void getFilePermission(final Activity mActivity, AjaxParams params) {
        YJYHttpUtils.post(mActivity, NetworkRequestsURL.buyFilePermission, params, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.vip.Utils.MemInterface.7
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ProjectApp.instance().hideDialogWindow();
                if (MemInterface.this.showOrHideView != null) {
                    MemInterface.this.showOrHideView.oncomplete();
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                ProjectApp.instance().showDialogWindow();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass7) s2);
                try {
                    ProjectApp.instance().hideDialogWindow();
                    MemCenterBean memCenterBean = (MemCenterBean) new Gson().fromJson(s2, MemCenterBean.class);
                    if (memCenterBean.getCode() == 200) {
                        MemInterface.this.putMemean(mActivity, memCenterBean.getData().getName(), memCenterBean.getData().getWays(), false, 0);
                    } else {
                        ToastUtil.shortToast(mActivity, memCenterBean.getMessage() + "");
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                if (MemInterface.this.showOrHideView != null) {
                    MemInterface.this.showOrHideView.oncomplete();
                }
            }
        });
    }

    public String getItem_id() {
        return this.item_id;
    }

    public void getMemData(final Activity mActivity, final AjaxParams params, final boolean isStatistics, final int isShowMethodDialog, String url) {
        YJYHttpUtils.post(mActivity, url, params, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.vip.Utils.MemInterface.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ProjectApp.instance().hideDialogWindow();
                if (MemInterface.this.showOrHideView != null) {
                    MemInterface.this.showOrHideView.oncomplete();
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                int i2 = isShowMethodDialog;
                if (i2 == 1 || i2 == 2) {
                    ProjectApp.instance().showDialogWindow();
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                try {
                    ProjectApp.instance().hideDialogWindow();
                    MemCenterBean memCenterBean = (MemCenterBean) new Gson().fromJson(s2, MemCenterBean.class);
                    if (memCenterBean.getCode() == 200) {
                        MemInterface.this.activity_id = memCenterBean.getData().getId() + "";
                        String strMD5Encode = "1";
                        String str = "" + memCenterBean.getData().getPass();
                        if (params.getParam().get("module_name") != null && !"".equals(params.getParam().get("module_name"))) {
                            String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, mActivity);
                            strMD5Encode = Md5Util.MD5Encode(Md5Util.MD5Encode(UserConfig.getUserId() + params.getParam().get("id") + strConfig + params.getParam().get(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID)) + "SmpaaK0eEwCxdiU9sGkbI7GvEQ0WbaJD1");
                            str = memCenterBean.getData().getPermission() + "";
                        }
                        if (str.equals(strMD5Encode)) {
                            if (MemInterface.this.popupwindow != null && MemInterface.this.popupwindow.isShow()) {
                                MemInterface.this.popupwindow.dismiss();
                            }
                            MemInterface.this.mUShareListener.mUShareListener();
                        } else {
                            MemInterface.this.putMemean(mActivity, memCenterBean.getData().getName(), memCenterBean.getData().getWays(), isStatistics, isShowMethodDialog);
                        }
                    } else {
                        ToastUtil.shortToast(mActivity, memCenterBean.getMessage() + "");
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                if (MemInterface.this.showOrHideView != null) {
                    MemInterface.this.showOrHideView.oncomplete();
                }
            }
        });
    }

    public void getShareInfo(Activity activity, int type, ActivityBean.DataBean.ShareInfoBean shareInfoBean, String imgurl, String activity_id) {
        ShareBoxPopWindow shareBoxPopWindow = new ShareBoxPopWindow(activity, type, 2, imgurl);
        XPopup.Builder builder = new XPopup.Builder(activity);
        Boolean bool = Boolean.FALSE;
        builder.dismissOnTouchOutside(bool).dismissOnBackPressed(bool).moveUpToKeyboard(bool).asCustom(shareBoxPopWindow).show();
        shareBoxPopWindow.setmShareListener(new AnonymousClass8(activity, activity_id, shareBoxPopWindow, shareInfoBean));
    }

    public MoreMethodLisener getmMoreLockMethod() {
        return this.mMoreLockMethod;
    }

    public UShareListener getmUShareListener() {
        return this.mUShareListener;
    }

    public void putData(Activity activity, MemCenterBean.DataBeanX.WaysBean waysBean, int isShowMethodDialog, String activity_id, boolean gift) {
        if (waysBean.getWay().equals("join_us")) {
            if (!waysBean.getData().getJoin_us_type().equals("0")) {
                if (waysBean.getData().getJoin_us_type().equals("1")) {
                    ToastUtil.shortToast(activity, "群聊功能暂不开放");
                    return;
                }
                return;
            }
            Intent intent = new Intent(activity, (Class<?>) WebLongSaveActivity.class);
            intent.putExtra("title", "活动");
            intent.putExtra("web_url", waysBean.getData().getPoster_html() + "");
            activity.startActivity(intent);
            EventBus.getDefault().post("closePage");
            return;
        }
        if (!waysBean.getWay().equals("buy_it")) {
            if (!waysBean.getWay().equals("share")) {
                if (waysBean.getWay().equals("vip_enable") || waysBean.getWay().equals("svip_enable")) {
                    activity.startActivity(new Intent(activity, (Class<?>) MemberCenterActivity.class));
                    return;
                } else if (waysBean.getWay().equals("buy_enclosure")) {
                    getFileFee(activity, waysBean.getData().getPrice(), waysBean.getData().getEnclosure_id(), "");
                    return;
                } else {
                    if (waysBean.getWay().equals("buy_ebook")) {
                        getFileFee(activity, waysBean.getData().getPrice(), waysBean.getData().getBook_id(), "buy_ebook");
                        return;
                    }
                    return;
                }
            }
            if (isShowMethodDialog == 2) {
                this.mMoreLockMethod.mMoreMethodLock();
                return;
            }
            ActivityBean.DataBean.ShareInfoBean shareInfoBean = new ActivityBean.DataBean.ShareInfoBean();
            shareInfoBean.setShare_title(waysBean.getData().getShare_title());
            shareInfoBean.setShare_content(waysBean.getData().getShare_content());
            shareInfoBean.setShare_img(waysBean.getData().getShare_img());
            shareInfoBean.setShare_type(waysBean.getData().getShare_type());
            shareInfoBean.setShare_url(waysBean.getData().getShare_url());
            if (!this.category.equals("") && !this.item_id.equals("") && "com.psychiatrygarden.activity.HandoutsInfoActivity".equals(activity.getLocalClassName())) {
                shareInfoBean.setCategory(this.category);
                shareInfoBean.setItem_id(this.item_id);
            }
            getShareInfo(activity, waysBean.getData().getShare_type(), shareInfoBean, waysBean.getData().getShare_popup(), activity_id);
            return;
        }
        JumpCourseListener jumpCourseListener = this.mJumpCourseListener;
        if (jumpCourseListener != null) {
            jumpCourseListener.jump2CourseDetail(waysBean.getData().getGoods_id());
            return;
        }
        if (!(activity instanceof ActCourseOrGoodsDetail) && !(activity instanceof ActCourseStructure)) {
            Intent intent2 = new Intent(activity, (Class<?>) ActCourseOrGoodsDetail.class);
            intent2.putExtra("goods_id", waysBean.getData().getGoods_id());
            intent2.putExtra("detailType", 2);
            activity.startActivity(intent2);
            return;
        }
        if ("1".equals(waysBean.getRecommend()) && waysBean.getData() != null) {
            String course_id = waysBean.getData().getCourse_id();
            String ori_course_id = waysBean.getData().getOri_course_id();
            if (!TextUtils.isEmpty(course_id) && !TextUtils.isEmpty(ori_course_id) && !TextUtils.equals(ori_course_id, course_id)) {
                activity.startActivity(new Intent(activity, (Class<?>) ActCourseOrGoodsDetail.class).putExtra("goods_id", waysBean.getData().course_id));
                return;
            }
        }
        String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.COURSE_PRICE, activity, "");
        OrderConfirmParams orderConfirmParams = new OrderConfirmParams();
        if (TextUtils.isEmpty(strConfig)) {
            orderConfirmParams.setPrice("");
        } else {
            orderConfirmParams.setPrice(Double.valueOf(Double.parseDouble(strConfig) * 100.0d).longValue() + "");
        }
        orderConfirmParams.setCourse_id(waysBean.getData().course_id).setGoodType("2").setCourseHaveAddress(gift).setIs_promotion("1".equals(waysBean.getData().getIs_promotion()));
        OrderConfirmActivity.goToOrderConfirmEntrance(activity, orderConfirmParams);
    }

    public void putMemean(Activity mActivity, String name, List<MemCenterBean.DataBeanX.WaysBean> ways, boolean isStatistics, int isShowMethodDialog, boolean query) {
        if (ways.size() <= 1) {
            if (ways.size() == 1) {
                putData(query, mActivity, ways.get(0), isShowMethodDialog);
            }
        } else if (isShowMethodDialog != 0) {
            this.mMoreLockMethod.mMoreMethodLock();
        } else {
            this.popupwindow = new MemberPopupwindow(mActivity, name, ways, this.disType, isStatistics);
            new XPopup.Builder(mActivity).dismissOnBackPressed(Boolean.valueOf(!isStatistics)).enableDrag(false).dismissOnTouchOutside(Boolean.valueOf(!isStatistics)).asCustom(this.popupwindow).show();
        }
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDisType(int disType) {
        this.disType = disType;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public void setJumpCourseListener(JumpCourseListener l2) {
        this.mJumpCourseListener = l2;
    }

    public void setShareSuccessListener(ShareSuccessListener successListener) {
        this.mShareSuccessListener = successListener;
    }

    public MemInterface setShowOrHideView(ShowOrHideView showOrHideView) {
        this.showOrHideView = showOrHideView;
        return this;
    }

    public void setmMoreLockListener(MoreMethodLisener mMoreLockListener) {
        this.mMoreLockMethod = mMoreLockListener;
    }

    public void setmUShareListener(UShareListener mUShareListener) {
        this.mUShareListener = mUShareListener;
    }

    public void shengYunShare(final Context context, final boolean query) {
        YJYHttpUtils.get(context, NetworkRequestsURL.shengYunShareInfo, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.vip.Utils.MemInterface.11
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass11) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if ("200".equals(jSONObject.optString("code"))) {
                        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
                        if (jSONObjectOptJSONObject != null) {
                            String strOptString = jSONObjectOptJSONObject.optString(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID);
                            String strOptString2 = jSONObjectOptJSONObject.optString(PushConstants.INTENT_ACTIVITY_NAME);
                            AjaxParams ajaxParams = new AjaxParams();
                            ajaxParams.put(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, strOptString);
                            ajaxParams.put("module_name", strOptString2);
                            MemInterface.this.getMemData((Activity) context, ajaxParams, false, 0, query);
                        } else {
                            context.sendBroadcast(new Intent().setAction("SHARE_SUCCESS"));
                        }
                    }
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void getMemData(final Activity mActivity, final AjaxParams params, final boolean isStatistics, final int isShowMethodDialog, final boolean query) {
        YJYHttpUtils.get(mActivity, NetworkRequestsURL.vipApi, params, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.vip.Utils.MemInterface.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ProjectApp.instance().hideDialogWindow();
                if (MemInterface.this.showOrHideView != null) {
                    MemInterface.this.showOrHideView.oncomplete();
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                int i2 = isShowMethodDialog;
                if (i2 == 1 || i2 == 2) {
                    ProjectApp.instance().showDialogWindow();
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                String strMD5Encode;
                super.onSuccess((AnonymousClass2) s2);
                try {
                    ProjectApp.instance().hideDialogWindow();
                    MemCenterBean memCenterBean = (MemCenterBean) new Gson().fromJson(s2, MemCenterBean.class);
                    if (memCenterBean.getCode() == 200) {
                        MemInterface.this.activity_id = memCenterBean.getData().getId() + "";
                        String str = "" + memCenterBean.getData().getPass();
                        if (params.getParam().get("module_name") == null || "".equals(params.getParam().get("module_name"))) {
                            strMD5Encode = "1";
                        } else {
                            String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, mActivity);
                            strMD5Encode = Md5Util.MD5Encode(Md5Util.MD5Encode(UserConfig.getUserId() + params.getParam().get("id") + strConfig + params.getParam().get(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID)) + "SmpaaK0eEwCxdiU9sGkbI7GvEQ0WbaJD1");
                            str = memCenterBean.getData().getPermission() + "";
                        }
                        if (str.equals(strMD5Encode) || "1".equals(memCenterBean.getData().getPass())) {
                            if (MemInterface.this.popupwindow != null && MemInterface.this.popupwindow.isShow()) {
                                MemInterface.this.popupwindow.dismiss();
                            }
                            MemInterface.this.mUShareListener.mUShareListener();
                        } else {
                            MemInterface.this.putMemean(mActivity, memCenterBean.getData().getName(), memCenterBean.getData().getWays(), isStatistics, isShowMethodDialog, query);
                        }
                    } else {
                        ToastUtil.shortToast(mActivity, memCenterBean.getMessage() + "");
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                if (MemInterface.this.showOrHideView != null) {
                    MemInterface.this.showOrHideView.oncomplete();
                }
            }
        });
    }

    public void getMemData(final Activity mActivity, AjaxParams params, final int isShowMethodDialog) {
        YJYHttpUtils.get(mActivity, NetworkRequestsURL.vipApi, params, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.vip.Utils.MemInterface.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ProjectApp.instance().hideDialogWindow();
                if (MemInterface.this.showOrHideView != null) {
                    MemInterface.this.showOrHideView.oncomplete();
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                int i2 = isShowMethodDialog;
                if (i2 == 1 || i2 == 2) {
                    ProjectApp.instance().showDialogWindow();
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                try {
                    ProjectApp.instance().hideDialogWindow();
                    MemCenterBean memCenterBean = (MemCenterBean) new Gson().fromJson(s2, MemCenterBean.class);
                    if (memCenterBean.getCode() == 200) {
                        MemInterface.this.activity_id = memCenterBean.getData().getId() + "";
                        if (memCenterBean.getData().getPass().equals("1")) {
                            if (MemInterface.this.popupwindow != null && MemInterface.this.popupwindow.isShow()) {
                                MemInterface.this.popupwindow.dismiss();
                            }
                            MemInterface.this.mUShareListener.mUShareListener();
                        } else {
                            MemInterface.this.putMemean(mActivity, memCenterBean.getData().getName(), memCenterBean.getData().getWays(), false, isShowMethodDialog);
                        }
                    } else {
                        ToastUtil.shortToast(mActivity, memCenterBean.getMessage() + "");
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                if (MemInterface.this.showOrHideView != null) {
                    MemInterface.this.showOrHideView.oncomplete();
                }
            }
        });
    }

    public void getMemData(final Activity mActivity, final AjaxParams params, final boolean isStatistics, final int isShowMethodDialog) {
        YJYHttpUtils.get(mActivity, NetworkRequestsURL.vipApi, params, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.vip.Utils.MemInterface.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ProjectApp.instance().hideDialogWindow();
                if (MemInterface.this.showOrHideView != null) {
                    MemInterface.this.showOrHideView.oncomplete();
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                int i2 = isShowMethodDialog;
                if (i2 == 1 || i2 == 2) {
                    ProjectApp.instance().showDialogWindow();
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass4) s2);
                try {
                    ProjectApp.instance().hideDialogWindow();
                    MemCenterBean memCenterBean = (MemCenterBean) new Gson().fromJson(s2, MemCenterBean.class);
                    if (memCenterBean.getCode() == 200) {
                        MemInterface.this.activity_id = memCenterBean.getData().getId() + "";
                        String strMD5Encode = "1";
                        String str = "" + memCenterBean.getData().getPass();
                        if (!TextUtils.isEmpty(params.getParam().get("module_name")) && !params.getParam().get("module_name").equals("question_unit") && !params.getParam().get("module_name").equals("exam")) {
                            String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, mActivity);
                            strMD5Encode = Md5Util.MD5Encode(Md5Util.MD5Encode(UserConfig.getUserId() + params.getParam().get("id") + strConfig + params.getParam().get(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID)) + "SmpaaK0eEwCxdiU9sGkbI7GvEQ0WbaJD1");
                            str = memCenterBean.getData().getPermission() + "";
                        }
                        if (str.equals(strMD5Encode)) {
                            if (MemInterface.this.popupwindow != null && MemInterface.this.popupwindow.isShow()) {
                                MemInterface.this.popupwindow.dismiss();
                            }
                            MemInterface.this.mUShareListener.mUShareListener();
                        } else {
                            MemInterface.this.putMemean(mActivity, memCenterBean.getData().getName(), memCenterBean.getData().getWays(), isStatistics, isShowMethodDialog);
                        }
                    } else {
                        ToastUtil.shortToast(mActivity, memCenterBean.getMessage() + "");
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                if (MemInterface.this.showOrHideView != null) {
                    MemInterface.this.showOrHideView.oncomplete();
                }
            }
        });
    }

    public void getShareInfo(Activity activity, int type, ActivityBean.DataBean.ShareInfoBean shareInfoBean, String imgurl, boolean query) {
        ShareBoxPopWindow shareBoxPopWindow = new ShareBoxPopWindow(activity, type, 2, imgurl);
        if (query) {
            return;
        }
        XPopup.Builder builderIsViewMode = new XPopup.Builder(activity).isViewMode(true);
        Boolean bool = Boolean.FALSE;
        builderIsViewMode.dismissOnTouchOutside(bool).dismissOnBackPressed(bool).moveUpToKeyboard(bool).asCustom(shareBoxPopWindow).show();
        shareBoxPopWindow.setmShareListener(new AnonymousClass9(activity, shareBoxPopWindow, shareInfoBean));
    }

    public void getMemData(final Activity mActivity, final AjaxParams params, String url, final int isShowMethodDialog, final boolean isStatistics) {
        final String str = params.getParam().get("alwaysShow");
        params.remove("alwaysShow");
        YJYHttpUtils.get(mActivity, url, params, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.vip.Utils.MemInterface.5
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ProjectApp.instance().hideDialogWindow();
                if (MemInterface.this.showOrHideView != null) {
                    MemInterface.this.showOrHideView.oncomplete();
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                int i2 = isShowMethodDialog;
                if (i2 == 1 || i2 == 2) {
                    ProjectApp.instance().showDialogWindow();
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                String strMD5Encode;
                super.onSuccess((AnonymousClass5) s2);
                try {
                    ProjectApp.instance().hideDialogWindow();
                    MemCenterBean memCenterBean = (MemCenterBean) new Gson().fromJson(s2, MemCenterBean.class);
                    if (memCenterBean.getCode() == 200) {
                        MemInterface.this.activity_id = memCenterBean.getData().getId() + "";
                        String str2 = "" + memCenterBean.getData().getPass();
                        if (params.getParam().get("module_name") == null || "".equals(params.getParam().get("module_name"))) {
                            strMD5Encode = "1";
                        } else {
                            String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, mActivity);
                            strMD5Encode = Md5Util.MD5Encode(Md5Util.MD5Encode(UserConfig.getUserId() + params.getParam().get("id") + strConfig + params.getParam().get(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID)) + "SmpaaK0eEwCxdiU9sGkbI7GvEQ0WbaJD1");
                            str2 = memCenterBean.getData().getPermission() + "";
                        }
                        if (str2.equals(strMD5Encode)) {
                            if (MemInterface.this.popupwindow != null && MemInterface.this.popupwindow.isShow()) {
                                MemInterface.this.popupwindow.dismiss();
                            }
                            MemInterface.this.mUShareListener.mUShareListener();
                        } else if ("1".equals(params.getParam().get("giveWayType"))) {
                            MemInterface.this.putMemean(mActivity, memCenterBean.getData().getName(), memCenterBean.getData().getWays(), isStatistics, isShowMethodDialog, memCenterBean.getData().getId() + "", true);
                        } else if ("1".equals(str)) {
                            MemInterface.this.putMemean(true, mActivity, memCenterBean.getData().getName(), memCenterBean.getData().getWays(), isStatistics, isShowMethodDialog, memCenterBean.getData().getKnowledge_img(), memCenterBean.getData().getKnowledge_img_width(), memCenterBean.getData().getKnowledge_img_height());
                        } else {
                            MemInterface.this.putMemean(mActivity, memCenterBean.getData().getName(), memCenterBean.getData().getWays(), isStatistics, isShowMethodDialog, memCenterBean.getData().getId() + "");
                        }
                    } else {
                        ToastUtil.shortToast(mActivity, memCenterBean.getMessage() + "");
                        if ("请登录".equals(memCenterBean.getMessage())) {
                            Intent intent = new Intent(ProjectApp.instance(), (Class<?>) LoginActivity.class);
                            intent.setFlags(268435456);
                            ProjectApp.instance().startActivity(intent);
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                if (MemInterface.this.showOrHideView != null) {
                    MemInterface.this.showOrHideView.oncomplete();
                }
            }
        });
    }

    public void getShareInfo(Activity activity, int type, ActivityBean.DataBean.ShareInfoBean shareInfoBean, String imgurl) {
        ShareBoxPopWindow shareBoxPopWindow = new ShareBoxPopWindow(activity, type, 2, imgurl);
        XPopup.Builder builderIsViewMode = new XPopup.Builder(activity).isViewMode(true);
        Boolean bool = Boolean.FALSE;
        builderIsViewMode.dismissOnTouchOutside(bool).dismissOnBackPressed(bool).moveUpToKeyboard(bool).asCustom(shareBoxPopWindow).show();
        shareBoxPopWindow.setmShareListener(new AnonymousClass10(activity, shareBoxPopWindow, shareInfoBean));
    }

    public void putMemean(Activity mActivity, String name, List<MemCenterBean.DataBeanX.WaysBean> ways, boolean isStatistics, int isShowMethodDialog) {
        if (ways.size() <= 1) {
            if (ways.size() == 1) {
                putData(mActivity, ways.get(0), isShowMethodDialog);
            }
        } else if (isShowMethodDialog == 0) {
            this.popupwindow = new MemberPopupwindow(mActivity, name, ways, this.disType, isStatistics);
            new XPopup.Builder(mActivity).dismissOnBackPressed(Boolean.valueOf(!isStatistics)).enableDrag(false).dismissOnTouchOutside(Boolean.valueOf(!isStatistics)).asCustom(this.popupwindow).show();
        } else {
            this.mMoreLockMethod.mMoreMethodLock();
        }
    }

    public void putMemean(Activity mActivity, String name, List<MemCenterBean.DataBeanX.WaysBean> ways, boolean isStatistics, int isShowMethodDialog, String activity_id) {
        if (ways.size() <= 1) {
            if (ways.size() == 1) {
                putData(mActivity, ways.get(0), isShowMethodDialog, activity_id, false);
            }
        } else if (isShowMethodDialog == 0) {
            this.popupwindow = new MemberPopupwindow(mActivity, name, ways, this.disType, isStatistics);
            new XPopup.Builder(mActivity).dismissOnBackPressed(Boolean.valueOf(!isStatistics)).enableDrag(false).dismissOnTouchOutside(Boolean.valueOf(!isStatistics)).asCustom(this.popupwindow).show();
        } else {
            this.mMoreLockMethod.mMoreMethodLock();
        }
    }

    public void putMemean(Activity mActivity, String name, List<MemCenterBean.DataBeanX.WaysBean> ways, boolean isStatistics, int isShowMethodDialog, String activity_id, boolean gift) {
        if (ways.size() <= 1) {
            if (ways.size() == 1) {
                putData(mActivity, ways.get(0), isShowMethodDialog, activity_id, gift);
            }
        } else if (isShowMethodDialog == 0) {
            this.popupwindow = new MemberPopupwindow(mActivity, name, ways, this.disType, isStatistics, gift);
            new XPopup.Builder(mActivity).dismissOnBackPressed(Boolean.valueOf(!isStatistics)).enableDrag(false).dismissOnTouchOutside(Boolean.valueOf(!isStatistics)).asCustom(this.popupwindow).show();
        } else {
            this.mMoreLockMethod.mMoreMethodLock();
        }
    }

    public void putMemean(boolean alwaysShowPop, Activity mActivity, String name, List<MemCenterBean.DataBeanX.WaysBean> ways, boolean isStatistics, int isShowMethodDialog) {
        if (isShowMethodDialog == 0) {
            this.popupwindow = new MemberPopupwindow(mActivity, name, ways, this.disType, isStatistics, false);
            new XPopup.Builder(mActivity).dismissOnBackPressed(Boolean.valueOf(!isStatistics)).enableDrag(false).dismissOnTouchOutside(Boolean.valueOf(!isStatistics)).asCustom(this.popupwindow).show();
        } else {
            this.mMoreLockMethod.mMoreMethodLock();
        }
    }

    public void putMemean(boolean show, Activity mActivity, String name, List<MemCenterBean.DataBeanX.WaysBean> ways, boolean isStatistics, int isShowMethodDialog, String img, String imgWidth, String imgHeight) {
        if (isShowMethodDialog == 0) {
            MemberPopupwindow memberPopupwindow = new MemberPopupwindow(mActivity, name, ways, this.disType, isStatistics, false);
            memberPopupwindow.setImg(img, imgWidth, imgHeight);
            new XPopup.Builder(mActivity).dismissOnBackPressed(Boolean.valueOf(!isStatistics)).enableDrag(false).dismissOnTouchOutside(Boolean.valueOf(!isStatistics)).asCustom(memberPopupwindow).show();
            return;
        }
        this.mMoreLockMethod.mMoreMethodLock();
    }

    public void putData(Activity activity, MemCenterBean.DataBeanX.WaysBean waysBean, int isShowMethodDialog, boolean gift) {
        if (waysBean.getWay().equals("join_us")) {
            if (waysBean.getData().getJoin_us_type().equals("0")) {
                Intent intent = new Intent(activity, (Class<?>) WebLongSaveActivity.class);
                intent.putExtra("title", "活动");
                intent.putExtra("web_url", waysBean.getData().getPoster_html() + "");
                activity.startActivity(intent);
                EventBus.getDefault().post("closePage");
                return;
            }
            if (waysBean.getData().getJoin_us_type().equals("1")) {
                ToastUtil.shortToast(activity, "群聊功能暂不开放");
                return;
            }
            return;
        }
        if (waysBean.getWay().equals("buy_it")) {
            JumpCourseListener jumpCourseListener = this.mJumpCourseListener;
            if (jumpCourseListener != null) {
                jumpCourseListener.jump2CourseDetail(waysBean.getData().getGoods_id());
                return;
            }
            if (activity instanceof ActCourseOrGoodsDetail) {
                if ("1".equals(waysBean.getRecommend()) && waysBean.getData() != null) {
                    String course_id = waysBean.getData().getCourse_id();
                    String ori_course_id = waysBean.getData().getOri_course_id();
                    if (!TextUtils.isEmpty(course_id) && !TextUtils.isEmpty(ori_course_id) && !TextUtils.equals(ori_course_id, course_id)) {
                        activity.startActivity(new Intent(activity, (Class<?>) ActCourseOrGoodsDetail.class).putExtra("goods_id", waysBean.getData().course_id));
                        return;
                    }
                }
                OrderConfirmParams orderConfirmParams = new OrderConfirmParams();
                orderConfirmParams.setCourse_id(waysBean.getData().course_id).setPrice(waysBean.getPrice()).setGoodType("2").setCourseHaveAddress(gift).setIs_promotion("1".equals(waysBean.getData().getIs_promotion()));
                OrderConfirmActivity.goToOrderConfirmEntrance(activity, orderConfirmParams);
                return;
            }
            Intent intent2 = new Intent(activity, (Class<?>) ActCourseOrGoodsDetail.class);
            intent2.putExtra("goods_id", waysBean.getData().getGoods_id());
            intent2.putExtra("detailType", 2);
            activity.startActivity(intent2);
            return;
        }
        if (waysBean.getWay().equals("share")) {
            if (isShowMethodDialog != 2) {
                ActivityBean.DataBean.ShareInfoBean shareInfoBean = new ActivityBean.DataBean.ShareInfoBean();
                shareInfoBean.setShare_title(waysBean.getData().getShare_title());
                shareInfoBean.setShare_content(waysBean.getData().getShare_content());
                shareInfoBean.setShare_img(waysBean.getData().getShare_img());
                shareInfoBean.setShare_type(waysBean.getData().getShare_type());
                shareInfoBean.setShare_url(waysBean.getData().getShare_url());
                if (!this.category.equals("") && !this.item_id.equals("") && "com.psychiatrygarden.activity.HandoutsInfoActivity".equals(activity.getLocalClassName())) {
                    shareInfoBean.setCategory(this.category);
                    shareInfoBean.setItem_id(this.item_id);
                }
                getShareInfo(activity, waysBean.getData().getShare_type(), shareInfoBean, waysBean.getData().getShare_popup());
                return;
            }
            this.mMoreLockMethod.mMoreMethodLock();
            return;
        }
        if (!waysBean.getWay().equals("vip_enable") && !waysBean.getWay().equals("svip_enable")) {
            if (waysBean.getWay().equals("buy_enclosure")) {
                getFileFee(activity, waysBean.getData().getPrice(), waysBean.getData().getEnclosure_id(), "");
                return;
            }
            if (waysBean.getWay().equals("buy_ebook")) {
                getFileFee(activity, waysBean.getData().getPrice(), waysBean.getData().getBook_id(), "buy_ebook");
                return;
            }
            if ("buy_goods".equals(waysBean.getWay())) {
                if (TextUtils.isEmpty(waysBean.getData().goods_id)) {
                    return;
                }
                NavigationUtilKt.goToCourseOrGoodsDetailNewTask(activity, "", waysBean.getData().goods_id);
                return;
            } else {
                if (!"buy_course".equals(waysBean.getWay()) || TextUtils.isEmpty(waysBean.getData().course_id)) {
                    return;
                }
                NavigationUtilKt.goToCourseOrGoodsDetailNewTask(activity, waysBean.getData().course_id, "");
                return;
            }
        }
        Intent intent3 = new Intent(activity, (Class<?>) MemberCenterActivity.class);
        if ("vip_enable".equals(waysBean.getWay())) {
            intent3.putExtra("psotision", 0);
        } else if ("svip_enable".equals(waysBean.getWay())) {
            intent3.putExtra("psotision", 1);
        }
        activity.startActivity(intent3);
    }

    public void putData(boolean isQuery, Activity activity, MemCenterBean.DataBeanX.WaysBean waysBean, int isShowMethodDialog) {
        if (waysBean.getWay().equals("join_us")) {
            if (waysBean.getData().getJoin_us_type().equals("0")) {
                Intent intent = new Intent(activity, (Class<?>) WebLongSaveActivity.class);
                intent.putExtra("title", "活动");
                intent.putExtra("web_url", waysBean.getData().getPoster_html() + "");
                activity.startActivity(intent);
                EventBus.getDefault().post("closePage");
                return;
            }
            if (waysBean.getData().getJoin_us_type().equals("1")) {
                ToastUtil.shortToast(activity, "群聊功能暂不开放");
                return;
            }
            return;
        }
        if (waysBean.getWay().equals("buy_it")) {
            JumpCourseListener jumpCourseListener = this.mJumpCourseListener;
            if (jumpCourseListener != null) {
                jumpCourseListener.jump2CourseDetail(waysBean.getData().getGoods_id());
                return;
            }
            if (activity instanceof ActCourseOrGoodsDetail) {
                if ("1".equals(waysBean.getRecommend()) && waysBean.getData() != null) {
                    String course_id = waysBean.getData().getCourse_id();
                    String ori_course_id = waysBean.getData().getOri_course_id();
                    if (!TextUtils.isEmpty(course_id) && !TextUtils.isEmpty(ori_course_id) && !TextUtils.equals(ori_course_id, course_id)) {
                        activity.startActivity(new Intent(activity, (Class<?>) ActCourseOrGoodsDetail.class).putExtra("goods_id", waysBean.getData().course_id));
                        return;
                    }
                }
                OrderConfirmParams orderConfirmParams = new OrderConfirmParams();
                orderConfirmParams.setCourse_id(waysBean.getData().course_id).setPrice(waysBean.getPrice()).setGoodType("2").setIs_promotion("1".equals(waysBean.getData().getIs_promotion()));
                OrderConfirmActivity.goToOrderConfirmEntrance(activity, orderConfirmParams);
                return;
            }
            Intent intent2 = new Intent(activity, (Class<?>) ActCourseOrGoodsDetail.class);
            intent2.putExtra("goods_id", waysBean.getData().getGoods_id());
            intent2.putExtra("detailType", 2);
            activity.startActivity(intent2);
            return;
        }
        if (waysBean.getWay().equals("share")) {
            if (isShowMethodDialog != 2) {
                ActivityBean.DataBean.ShareInfoBean shareInfoBean = new ActivityBean.DataBean.ShareInfoBean();
                shareInfoBean.setShare_title(waysBean.getData().getShare_title());
                shareInfoBean.setShare_content(waysBean.getData().getShare_content());
                shareInfoBean.setShare_img(waysBean.getData().getShare_img());
                shareInfoBean.setShare_type(waysBean.getData().getShare_type());
                shareInfoBean.setShare_url(waysBean.getData().getShare_url());
                if (!this.category.equals("") && !this.item_id.equals("") && "com.psychiatrygarden.activity.HandoutsInfoActivity".equals(activity.getLocalClassName())) {
                    shareInfoBean.setCategory(this.category);
                    shareInfoBean.setItem_id(this.item_id);
                }
                getShareInfo(activity, waysBean.getData().getShare_type(), shareInfoBean, waysBean.getData().getShare_popup(), isQuery);
                return;
            }
            this.mMoreLockMethod.mMoreMethodLock();
            return;
        }
        if (!waysBean.getWay().equals("vip_enable") && !waysBean.getWay().equals("svip_enable")) {
            if (waysBean.getWay().equals("buy_enclosure")) {
                getFileFee(activity, waysBean.getData().getPrice(), waysBean.getData().getEnclosure_id(), "");
                return;
            } else {
                if (waysBean.getWay().equals("buy_ebook")) {
                    getFileFee(activity, waysBean.getData().getPrice(), waysBean.getData().getBook_id(), "buy_ebook");
                    return;
                }
                return;
            }
        }
        Intent intent3 = new Intent(activity, (Class<?>) MemberCenterActivity.class);
        if ("vip_enable".equals(waysBean.getWay())) {
            intent3.putExtra("psotision", 0);
        } else if ("svip_enable".equals(waysBean.getWay())) {
            intent3.putExtra("psotision", 1);
        }
        activity.startActivity(intent3);
    }

    public void putData(Activity activity, MemCenterBean.DataBeanX.WaysBean waysBean, int isShowMethodDialog) {
        if (waysBean.getWay().equals("join_us")) {
            if (waysBean.getData().getJoin_us_type().equals("0")) {
                Intent intent = new Intent(activity, (Class<?>) WebLongSaveActivity.class);
                intent.putExtra("title", "活动");
                intent.putExtra("web_url", waysBean.getData().getPoster_html() + "");
                activity.startActivity(intent);
                EventBus.getDefault().post("closePage");
                return;
            }
            if (waysBean.getData().getJoin_us_type().equals("1")) {
                ToastUtil.shortToast(activity, "群聊功能暂不开放");
                return;
            }
            return;
        }
        if (waysBean.getWay().equals("buy_it")) {
            JumpCourseListener jumpCourseListener = this.mJumpCourseListener;
            if (jumpCourseListener != null) {
                jumpCourseListener.jump2CourseDetail(waysBean.getData().getGoods_id());
                return;
            }
            if (activity instanceof ActCourseOrGoodsDetail) {
                if ("1".equals(waysBean.getRecommend()) && waysBean.getData() != null) {
                    String course_id = waysBean.getData().getCourse_id();
                    String ori_course_id = waysBean.getData().getOri_course_id();
                    if (!TextUtils.isEmpty(course_id) && !TextUtils.isEmpty(ori_course_id) && !TextUtils.equals(ori_course_id, course_id)) {
                        activity.startActivity(new Intent(activity, (Class<?>) ActCourseOrGoodsDetail.class).putExtra("goods_id", waysBean.getData().course_id));
                        return;
                    }
                }
                OrderConfirmParams orderConfirmParams = new OrderConfirmParams();
                orderConfirmParams.setCourse_id(waysBean.getData().course_id).setPrice(waysBean.getPrice()).setGoodType("2").setIs_promotion("1".equals(waysBean.getData().getIs_promotion()));
                OrderConfirmActivity.goToOrderConfirmEntrance(activity, orderConfirmParams);
                return;
            }
            Intent intent2 = new Intent(activity, (Class<?>) ActCourseOrGoodsDetail.class);
            intent2.putExtra("goods_id", waysBean.getData().getGoods_id());
            intent2.putExtra("detailType", 2);
            activity.startActivity(intent2);
            return;
        }
        if (waysBean.getWay().equals("share")) {
            if (isShowMethodDialog != 2) {
                ActivityBean.DataBean.ShareInfoBean shareInfoBean = new ActivityBean.DataBean.ShareInfoBean();
                shareInfoBean.setShare_title(waysBean.getData().getShare_title());
                shareInfoBean.setShare_content(waysBean.getData().getShare_content());
                shareInfoBean.setShare_img(waysBean.getData().getShare_img());
                shareInfoBean.setShare_type(waysBean.getData().getShare_type());
                shareInfoBean.setShare_url(waysBean.getData().getShare_url());
                if (!this.category.equals("") && !this.item_id.equals("") && "com.psychiatrygarden.activity.HandoutsInfoActivity".equals(activity.getLocalClassName())) {
                    shareInfoBean.setCategory(this.category);
                    shareInfoBean.setItem_id(this.item_id);
                }
                getShareInfo(activity, waysBean.getData().getShare_type(), shareInfoBean, waysBean.getData().getShare_popup());
                return;
            }
            this.mMoreLockMethod.mMoreMethodLock();
            return;
        }
        if (!waysBean.getWay().equals("vip_enable") && !waysBean.getWay().equals("svip_enable")) {
            if (waysBean.getWay().equals("buy_enclosure")) {
                getFileFee(activity, waysBean.getData().getPrice(), waysBean.getData().getEnclosure_id(), "");
                return;
            }
            if (waysBean.getWay().equals("buy_ebook")) {
                getFileFee(activity, waysBean.getData().getPrice(), waysBean.getData().getBook_id(), "buy_ebook");
                return;
            }
            if ("buy_goods".equals(waysBean.getWay())) {
                if (TextUtils.isEmpty(waysBean.getData().goods_id)) {
                    return;
                }
                NavigationUtilKt.goToCourseOrGoodsDetailNewTask(activity, "", waysBean.getData().goods_id);
                return;
            } else {
                if (!"buy_course".equals(waysBean.getWay()) || TextUtils.isEmpty(waysBean.getData().course_id)) {
                    return;
                }
                NavigationUtilKt.goToCourseOrGoodsDetailNewTask(activity, waysBean.getData().course_id, "");
                return;
            }
        }
        Intent intent3 = new Intent(activity, (Class<?>) MemberCenterActivity.class);
        if ("vip_enable".equals(waysBean.getWay())) {
            intent3.putExtra("psotision", 0);
        } else if ("svip_enable".equals(waysBean.getWay())) {
            intent3.putExtra("psotision", 1);
        }
        activity.startActivity(intent3);
    }
}
