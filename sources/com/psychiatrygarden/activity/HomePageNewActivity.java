package com.psychiatrygarden.activity;

import android.app.Activity;
import android.app.UiModeManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import cn.hutool.core.lang.RegexPool;
import cn.hutool.core.text.StrPool;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.aliyun.sls.android.producer.LogProducerCallback;
import com.aliyun.sls.android.producer.LogProducerClient;
import com.aliyun.sls.android.producer.LogProducerConfig;
import com.aliyun.sls.android.producer.LogProducerException;
import com.aliyun.svideo.common.utils.ThreadUtils;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.heytap.msp.push.HeytapPushManager;
import com.hjq.permissions.Permission;
import com.huawei.hms.push.HmsMessaging;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMPushManager;
import com.hyphenate.chat.EMUserInfo;
import com.hyphenate.easeui.EaseIM;
import com.hyphenate.easeui.constants.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.manager.EaseThreadManager;
import com.hyphenate.easeui.provider.EaseUserProfileProvider;
import com.hyphenate.push.EMPushConfig;
import com.hyphenate.push.EMPushHelper;
import com.hyphenate.push.EMPushType;
import com.hyphenate.push.PushListener;
import com.just.agentweb.DefaultWebClient;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.psychiatrygarden.NetworkCallBack;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.HomePageNewActivity;
import com.psychiatrygarden.activity.chat.ChatActivity;
import com.psychiatrygarden.activity.chooseSchool.util.AliYunLogUtil;
import com.psychiatrygarden.activity.courselist.bean.VideoDownTempBean;
import com.psychiatrygarden.activity.courselist.roomDB.dataBean.CourseCoverBean;
import com.psychiatrygarden.activity.courselist.roomDB.dataBean.CourseDirectoryBean;
import com.psychiatrygarden.activity.courselist.roomDB.dataBean.VideoDownBean;
import com.psychiatrygarden.activity.material.InformationPreviewAct;
import com.psychiatrygarden.activity.vip.Utils.MemInterface;
import com.psychiatrygarden.activity.vip.bean.VipTipsBean;
import com.psychiatrygarden.bean.AliyunLogCredentials;
import com.psychiatrygarden.bean.EnterHandoutPreviewEvent;
import com.psychiatrygarden.bean.EventBusMessage;
import com.psychiatrygarden.bean.ExitFromPipEvent;
import com.psychiatrygarden.bean.GroupChatListBean;
import com.psychiatrygarden.bean.HandoutEvent;
import com.psychiatrygarden.bean.HomeTabStatus;
import com.psychiatrygarden.bean.HomepageSmallAdBean;
import com.psychiatrygarden.bean.SelectIdentityBean;
import com.psychiatrygarden.bean.VersionUpdateBean;
import com.psychiatrygarden.bean.VideoHandout;
import com.psychiatrygarden.bean.VidteachingBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.ExitUpdateEvent;
import com.psychiatrygarden.event.GoToLivingEvent;
import com.psychiatrygarden.event.LocateChapterEvent;
import com.psychiatrygarden.event.LocationEvent;
import com.psychiatrygarden.fragmenthome.HomePageNewFragment;
import com.psychiatrygarden.http.ChatRequest;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.QuestionDataCallBack;
import com.psychiatrygarden.receiver.NetworkChangeReceiver;
import com.psychiatrygarden.service.HMSPushHelper;
import com.psychiatrygarden.service.YkbLocationService;
import com.psychiatrygarden.utils.AndroidBaseUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.DesUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.PopupShowManager;
import com.psychiatrygarden.utils.PublicMethodActivity;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.utils.ThemeInterface;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.AdMainHomeDialog;
import com.psychiatrygarden.widget.CustomDialog;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.yddmi.doctor.config.GlobalAction;
import com.yddmi.doctor.entity.request.ShengYunSetsBean;
import com.yddmi.doctor.pages.physical.PhysicalActivity;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class HomePageNewActivity extends BaseActivity implements ThemeInterface {
    private static final int REQUEST_CODE_PERMISSION_LOCATION = 274;
    private static final int REQUEST_CODE_PERMISSION_READ_OR_WRITE = 273;
    public static LogProducerClient client;
    private Disposable disposable;
    private ConnectivityManager.NetworkCallback mNetworkCallback;
    private NetworkChangeReceiver mNetworkChangeReceiver;
    private final Handler mHandler = new Handler(new Handler.Callback() { // from class: com.psychiatrygarden.activity.nc
        @Override // android.os.Handler.Callback
        public final boolean handleMessage(Message message) {
            return this.f13043c.lambda$new$0(message);
        }
    });
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.psychiatrygarden.activity.HomePageNewActivity.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String stringExtra = intent.getStringExtra("data");
            if ("HANDOUT_VIEW".equals(action)) {
                if (TextUtils.isEmpty(stringExtra)) {
                    return;
                }
                HomePageNewActivity.this.videoBuryPoint("1", ((HandoutEvent) new Gson().fromJson(stringExtra, HandoutEvent.class)).getHandoutId());
                return;
            }
            if ("HANDOUT_QUIT".equals(action)) {
                if (TextUtils.isEmpty(stringExtra)) {
                    return;
                }
                HomePageNewActivity.this.videoBuryPoint("2", ((HandoutEvent) new Gson().fromJson(stringExtra, HandoutEvent.class)).getHandoutId());
                return;
            }
            if ("JOIN_LIVE".equals(action)) {
                HomePageNewActivity.this.videoBuryPoint("1", null);
                return;
            }
            if ("QUIT_LIVE".equals(action)) {
                HomePageNewActivity.this.videoBuryPoint("2", null);
                return;
            }
            if ("VIEW_HANDOUT".equals(action)) {
                if (TextUtils.isEmpty(stringExtra)) {
                    return;
                }
                VideoHandout videoHandout = (VideoHandout) new Gson().fromJson(stringExtra, VideoHandout.class);
                InformationPreviewAct.newIntent(ProjectApp.instance(), videoHandout.getId(), videoHandout.getUrl(), false, true, videoHandout.getTitle(), 268435456);
                return;
            }
            if ("SHARE_SHENG_YUN".equals(action)) {
                MemInterface.getInstance().setmUShareListener(new MemInterface.UShareListener() { // from class: com.psychiatrygarden.activity.HomePageNewActivity.1.1
                    @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.UShareListener
                    public void mUShareListener() {
                        HomePageNewActivity.this.sendBroadcast(new Intent().setAction("SHARE_SUCCESS"));
                    }
                });
                if (ProjectApp.instance().getCurrentResumeAct() != null) {
                    MemInterface.getInstance().shengYunShare(ProjectApp.instance().getCurrentResumeAct(), intent.getBooleanExtra("query", true));
                    return;
                }
                return;
            }
            if (!"DOCTOR_TOP".equals(action)) {
                if ("EXIT_LOGIN".equals(action)) {
                    LocalBroadcastManager.getInstance(HomePageNewActivity.this.mContext).unregisterReceiver(HomePageNewActivity.this.mReceiver);
                }
            } else {
                try {
                    if (intent.getBooleanExtra("create", true)) {
                        ProjectApp.instance().activityLifecycleCallbacks.onActivityCreated((Activity) PhysicalActivity.class.newInstance(), null);
                    } else {
                        ProjectApp.instance().activityLifecycleCallbacks.onActivityDestroyed((Activity) PhysicalActivity.class.newInstance());
                    }
                } catch (IllegalAccessException | InstantiationException e2) {
                    e2.printStackTrace();
                }
            }
        }
    };
    private BroadcastReceiver shengyunRequestReceiver = new BroadcastReceiver() { // from class: com.psychiatrygarden.activity.HomePageNewActivity.13
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (GlobalAction.ACTION_REQUEST_SHENGYUN_SETS.equals(intent.getAction())) {
                Log.e("wwwwwwwwwwww", "接收到获取圣运数据的请求:题单");
                HomePageNewActivity.this.getShengYunSets();
            } else if (GlobalAction.ACTION_REQUEST_SHENGYUN_GOODS.equals(intent.getAction())) {
                Log.e("wwwwwwwwwwww", "接收到获取圣运数据的请求:商品");
                HomePageNewActivity.this.getShengYunGoods();
            }
        }
    };
    private final BroadcastReceiver refreshHxLoginReceiver = new BroadcastReceiver() { // from class: com.psychiatrygarden.activity.HomePageNewActivity.17
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (UserConfig.isLogin()) {
                HomePageNewActivity.this.loginHuanxin();
            }
        }
    };
    boolean aliyunKeyLoding = false;
    int aliyunKeyLoadTimes = 0;
    public LogProducerConfig config = null;

    /* renamed from: com.psychiatrygarden.activity.HomePageNewActivity$14, reason: invalid class name */
    public class AnonymousClass14 extends AjaxCallBack<String> {
        public AnonymousClass14() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ ArrayList lambda$onSuccess$0(List list) throws Exception {
            ArrayList arrayList = new ArrayList(list.size());
            Iterator it = list.iterator();
            while (it.hasNext()) {
                File file = Glide.with((FragmentActivity) HomePageNewActivity.this).load((Object) GlideUtils.generateUrl((String) it.next())).diskCacheStrategy(DiskCacheStrategy.ALL).downloadOnly(Integer.MIN_VALUE, Integer.MIN_VALUE).get();
                if (file != null && file.exists()) {
                    arrayList.add(file.getPath());
                }
            }
            return arrayList;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$1(List list, JSONObject jSONObject, ArrayList arrayList) throws Exception {
            if (arrayList.size() == list.size()) {
                AdMainHomeDialog adMainHomeDialogNewInstance = AdMainHomeDialog.newInstance(arrayList, jSONObject.optString("noticeList"));
                HomePageNewActivity.this.getSupportFragmentManager().executePendingTransactions();
                if (!adMainHomeDialogNewInstance.isAdded() && !adMainHomeDialogNewInstance.isRemoving() && !adMainHomeDialogNewInstance.isVisible()) {
                    adMainHomeDialogNewInstance.show(HomePageNewActivity.this.getSupportFragmentManager(), "homeadfragment");
                }
                SharePreferencesUtils.writeLongConfig(CommonParameter.DISMESS_TIME_QUESTION_HOME_AD, Long.valueOf(System.currentTimeMillis()), HomePageNewActivity.this.mContext);
            }
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onStart() {
            super.onStart();
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String t2) {
            super.onSuccess((AnonymousClass14) t2);
            try {
                JSONObject jSONObject = new JSONObject(t2);
                if (jSONObject.optString("code").equals("200")) {
                    final JSONObject jSONObject2 = new JSONObject(jSONObject.optString("data"));
                    if (jSONObject2.optJSONObject("noticeList") != null) {
                        if (!"".equals(SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance()))) {
                            HomepageSmallAdBean.DataDTO.DataAd dataAd = (HomepageSmallAdBean.DataDTO.DataAd) new Gson().fromJson(jSONObject2.optString("noticeList"), HomepageSmallAdBean.DataDTO.DataAd.class);
                            long jLongValue = SharePreferencesUtils.readLongConfig(CommonParameter.DISMESS_TIME_QUESTION_HOME_AD, HomePageNewActivity.this, 0L).longValue();
                            if ((jLongValue != 0 ? ((System.currentTimeMillis() - jLongValue) / 1000) - dataAd.getTime_interval() : 0L) < 0) {
                                return;
                            }
                            final List<HomepageSmallAdBean.DataDTO.DataAd.AdsDTO> ads = ((HomepageSmallAdBean.DataDTO.DataAd) new Gson().fromJson(jSONObject2.optString("noticeList"), HomepageSmallAdBean.DataDTO.DataAd.class)).getAds();
                            if (ads != null && ads.size() > 0) {
                                ArrayList arrayList = new ArrayList(ads.size());
                                for (HomepageSmallAdBean.DataDTO.DataAd.AdsDTO adsDTO : ads) {
                                    if (!TextUtils.isEmpty(adsDTO.getImg()) && adsDTO.getImg().startsWith("http")) {
                                        arrayList.add(adsDTO.getImg());
                                    }
                                }
                                HomePageNewActivity.this.disposable = Observable.just(arrayList).observeOn(Schedulers.io()).map(new Function() { // from class: com.psychiatrygarden.activity.uc
                                    @Override // io.reactivex.functions.Function
                                    public final Object apply(Object obj) {
                                        return this.f13983c.lambda$onSuccess$0((List) obj);
                                    }
                                }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.vc
                                    @Override // io.reactivex.functions.Consumer
                                    public final void accept(Object obj) throws Exception {
                                        this.f14023c.lambda$onSuccess$1(ads, jSONObject2, (ArrayList) obj);
                                    }
                                }, new Consumer() { // from class: com.psychiatrygarden.activity.wc
                                    @Override // io.reactivex.functions.Consumer
                                    public final void accept(Object obj) {
                                        ((Throwable) obj).printStackTrace();
                                    }
                                });
                            }
                        }
                        SharePreferencesUtils.writeBooleanConfig(CommonParameter.IS_SHOW_HOME_AD_POP, true, HomePageNewActivity.this.mContext);
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void appTestVersion() {
        ProjectApp.isShowUpdateVersion = true;
        AjaxParams ajaxParams = new AjaxParams();
        try {
            ajaxParams.put("system", "2");
            ajaxParams.put("code", String.valueOf(AndroidBaseUtils.getAPPVersionCode(getApplicationContext())));
            ajaxParams.put("beta_version", "0");
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        YJYHttpUtils.get(getApplicationContext(), NetworkRequestsURL.appTestVersionInfo, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.HomePageNewActivity.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                Log.e("checkversion", s2);
                super.onSuccess((AnonymousClass3) s2);
                try {
                    VersionUpdateBean versionUpdateBean = (VersionUpdateBean) new Gson().fromJson(s2, VersionUpdateBean.class);
                    if (versionUpdateBean.getCode().equals("200")) {
                        VersionUpdateBean.VersionBean data = versionUpdateBean.getData();
                        if (data != null) {
                            SharePreferencesUtils.writeBooleanConfig("isOpenAppVersion", data.getIs_open().equals("1"), HomePageNewActivity.this);
                            SharePreferencesUtils.writeStrConfig("appTestVersion", data.getVersion(), HomePageNewActivity.this);
                            if (TextUtils.isEmpty(data.getAppUrl())) {
                                SharePreferencesUtils.writeBooleanConfig("haveNewVersion", false, HomePageNewActivity.this);
                                SharePreferencesUtils.writeBooleanConfig("showVersionUpdate", false, HomePageNewActivity.this);
                            } else {
                                try {
                                    AndroidBaseUtils.getAPPVersionCode(HomePageNewActivity.this.mContext);
                                } catch (Exception unused) {
                                }
                            }
                        } else {
                            SharePreferencesUtils.writeBooleanConfig("haveNewVersion", false, HomePageNewActivity.this);
                            SharePreferencesUtils.removeConfig("isOpenAppVersion", HomePageNewActivity.this);
                            SharePreferencesUtils.removeConfig("appTestVersion", HomePageNewActivity.this);
                        }
                    } else {
                        SharePreferencesUtils.removeConfig("isOpenAppVersion", HomePageNewActivity.this);
                        SharePreferencesUtils.removeConfig("appTestVersion", HomePageNewActivity.this);
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        });
    }

    private void checkMergeOldVersionDownloadDbData() {
        ThreadUtils.runOnSubThread(new Runnable() { // from class: com.psychiatrygarden.activity.HomePageNewActivity.15

            /* renamed from: com.psychiatrygarden.activity.HomePageNewActivity$15$1, reason: invalid class name */
            public class AnonymousClass1 extends AjaxCallBack<String> {
                public AnonymousClass1() {
                }

                /* JADX INFO: Access modifiers changed from: private */
                public static /* synthetic */ void lambda$onSuccess$0(VidteachingBean vidteachingBean) {
                    List<VidteachingBean.DataDTO> data;
                    if (!vidteachingBean.getCode().equals("200") || (data = vidteachingBean.getData()) == null || data.size() <= 0) {
                        return;
                    }
                    for (int i2 = 0; i2 < data.size(); i2++) {
                        VidteachingBean.DataDTO dataDTO = data.get(i2);
                        String id = dataDTO.getId();
                        if (!TextUtils.isEmpty(id) && id.matches(RegexPool.NUMBERS)) {
                            CourseCoverBean courseCoverBean = new CourseCoverBean();
                            courseCoverBean.setId(Integer.parseInt(id));
                            courseCoverBean.setTitle(dataDTO.getTitle());
                            courseCoverBean.setCover(dataDTO.getCover_img());
                            courseCoverBean.setActivity_id(dataDTO.getActivity_id());
                            courseCoverBean.setSort(i2);
                            ProjectApp.database.getCourseCoverDao().insertTopic(courseCoverBean);
                            ArrayList arrayList = new ArrayList();
                            CourseDirectoryBean courseDirectoryBean = new CourseDirectoryBean();
                            courseDirectoryBean.setId(Integer.parseInt(id) * 8);
                            courseDirectoryBean.setPid(id);
                            courseDirectoryBean.setSort(0);
                            courseDirectoryBean.setTitle(dataDTO.getTitle());
                            arrayList.add(courseDirectoryBean);
                            ProjectApp.database.getCourseDirectoryDao().insertTopicList(arrayList);
                            List<VideoDownBean> videoDownLoadInfo = ProjectApp.database.getVideoDownDao().getVideoDownLoadInfo(id);
                            if (videoDownLoadInfo != null && videoDownLoadInfo.size() > 0) {
                                for (VideoDownBean videoDownBean : videoDownLoadInfo) {
                                    videoDownBean.parent_id = id;
                                    videoDownBean.hasPermission = "1";
                                    videoDownBean.videoType = 1;
                                    videoDownBean.chapter_id = id;
                                    videoDownBean.cId = "course_" + id;
                                    ProjectApp.database.getVideoDownDao().deleteData(videoDownBean.vid);
                                    ProjectApp.database.getVideoDownDao().insertTopicList(videoDownBean);
                                }
                                LogUtils.d("migrate", "合并课单下载数据完成");
                            }
                        }
                    }
                }

                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onFailure(Throwable t2, int errorNo, String strMsg) {
                    super.onFailure(t2, errorNo, strMsg);
                }

                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onSuccess(String s2) {
                    super.onSuccess((AnonymousClass1) s2);
                    try {
                        final VidteachingBean vidteachingBean = (VidteachingBean) new Gson().fromJson(s2, VidteachingBean.class);
                        ThreadUtils.runOnSubThread(new Runnable() { // from class: com.psychiatrygarden.activity.xc
                            @Override // java.lang.Runnable
                            public final void run() {
                                HomePageNewActivity.AnonymousClass15.AnonymousClass1.lambda$onSuccess$0(vidteachingBean);
                            }
                        });
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }

            @Override // java.lang.Runnable
            public void run() {
                List<String> cids = ProjectApp.database.getVideoDownDao().getCids();
                AjaxParams ajaxParams = new AjaxParams();
                if (cids.isEmpty()) {
                    return;
                }
                ajaxParams.put("course_id", new Gson().toJson(cids));
                YJYHttpUtils.post(ProjectApp.instance(), NetworkRequestsURL.courseDetailInfo, ajaxParams, new AnonymousClass1());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkVersion() {
        ProjectApp.isShowUpdateVersion = true;
        AjaxParams ajaxParams = new AjaxParams();
        try {
            ajaxParams.put("system", "2");
            ajaxParams.put("code", String.valueOf(AndroidBaseUtils.getAPPVersionCode(getApplicationContext())));
            ajaxParams.put("beta_version", "0");
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        YJYHttpUtils.post(getApplicationContext(), NetworkRequestsURL.mCheckVersionUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.HomePageNewActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                PopupShowManager.getInstance(HomePageNewActivity.this.mContext).checkShowCoupon(HomePageNewActivity.this.mContext, PopupShowManager.FLAG_ENTER_APP_HOME, "0", null, null);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                VersionUpdateBean.VersionBean data;
                super.onSuccess((AnonymousClass2) s2);
                try {
                    VersionUpdateBean versionUpdateBean = (VersionUpdateBean) new Gson().fromJson(s2, VersionUpdateBean.class);
                    if (!versionUpdateBean.getCode().equals("200") || (data = versionUpdateBean.getData()) == null) {
                        SharePreferencesUtils.writeBooleanConfig("haveNewVersion", false, HomePageNewActivity.this);
                        PopupShowManager.getInstance(HomePageNewActivity.this.mContext).checkShowCoupon(HomePageNewActivity.this.mContext, PopupShowManager.FLAG_ENTER_APP_HOME, "0", null, null);
                    } else if (TextUtils.isEmpty(data.getIsForce()) || TextUtils.isEmpty(data.getAppUrl())) {
                        SharePreferencesUtils.writeBooleanConfig("haveNewVersion", true, HomePageNewActivity.this);
                        SharePreferencesUtils.writeBooleanConfig("showVersionUpdate", false, HomePageNewActivity.this);
                        PopupShowManager.getInstance(HomePageNewActivity.this.mContext).checkShowCoupon(HomePageNewActivity.this.mContext, PopupShowManager.FLAG_ENTER_APP_HOME, "0", null, null);
                    } else {
                        SharePreferencesUtils.writeBooleanConfig("haveNewVersion", true, HomePageNewActivity.this);
                        Intent intent = new Intent(HomePageNewActivity.this, (Class<?>) ForceUpdateActivity.class);
                        intent.putExtra("is_force_update", data.getIsForce());
                        intent.putExtra("message", data.getMessage());
                        intent.putExtra("app_link", data.getAppUrl());
                        intent.putExtra("verCode", data.getCode());
                        intent.setFlags(268435456);
                        intent.setFlags(4194304);
                        HomePageNewActivity.this.startActivity(intent);
                        SharePreferencesUtils.writeBooleanConfig("showVersionUpdate", true, HomePageNewActivity.this);
                        SharePreferencesUtils.writeStrConfig(CommonParameter.NEW_VERSION_CODE, data.getCode(), HomePageNewActivity.this);
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        });
    }

    private void getAdPic() {
        AjaxParams ajaxParams = new AjaxParams();
        if (CommonUtil.getScreenHeight(this) % 16 == 0) {
            ajaxParams.put(DatabaseManager.SIZE, "1080_1920");
        } else {
            ajaxParams.put(DatabaseManager.SIZE, "1080_2340");
        }
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "index");
        YJYHttpUtils.get(this, NetworkRequestsURL.mAdUrl, ajaxParams, new AnonymousClass14());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getAliyunCredentials() {
        this.aliyunKeyLoding = true;
        this.aliyunKeyLoadTimes++;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("user_id", UserConfig.getUserId());
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.aliyunCredentials, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.HomePageNewActivity.19
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                Log.e("获取阿里云log数据。getAliyunCredentials", "onFailure");
                HomePageNewActivity.this.aliyunKeyLoding = false;
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass19) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    Log.e("获取阿里云log数据。getAliyunCredentials 成功：", s2);
                    if (jSONObject.optString("code").equals("200")) {
                        String strDecode = DesUtil.decode(CommonParameter.DES_KEY_ALI, jSONObject.optJSONObject("data").optString(SocializeProtocolConstants.PROTOCOL_KEY_EN));
                        Log.e("获取阿里云log数据。解密：", strDecode);
                        AliyunLogCredentials aliyunLogCredentials = (AliyunLogCredentials) new Gson().fromJson(strDecode, AliyunLogCredentials.class);
                        HomePageNewActivity.this.initProducer(aliyunLogCredentials.getEndpoint(), aliyunLogCredentials.getProject(), aliyunLogCredentials.getLogstore());
                        HomePageNewActivity.this.updateAccessKey(aliyunLogCredentials.getAccessKeyId(), aliyunLogCredentials.getAccessKeySecret(), aliyunLogCredentials.getSecurityToken());
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                HomePageNewActivity.this.aliyunKeyLoding = false;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getChatUserInfo(String[] userId) {
        EMClient.getInstance().userInfoManager().fetchUserInfoByUserId(userId, new EMValueCallBack<Map<String, EMUserInfo>>() { // from class: com.psychiatrygarden.activity.HomePageNewActivity.7
            @Override // com.hyphenate.EMValueCallBack
            public void onError(int error, String errorMsg) {
            }

            @Override // com.hyphenate.EMValueCallBack
            public void onSuccess(Map<String, EMUserInfo> value) {
                ProjectApp.hxUser.putAll(value);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getShengYunGoods() {
        Log.e("wwwwwwwwwwww", "开始获取圣运数据:获取跳转的商品信息");
        YJYHttpUtils.get(this, NetworkRequestsURL.getShengYunGoodsNet, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.HomePageNewActivity.11
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass11) s2);
                Log.e("wwwwwwwwwwww", "获取圣运数据成功:" + s2);
                HomePageNewActivity.this.sendShengYunGoodsData(s2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getShengYunSets() {
        Log.e("wwwwwwwwwwww", "开始获取圣运数据:获取跳转的题单id");
        YJYHttpUtils.get(this, NetworkRequestsURL.getShengYunSet, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.HomePageNewActivity.12
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                List list;
                super.onSuccess((AnonymousClass12) s2);
                Log.e("wwwwwwwwwwww", "获取圣运数据成功 获取跳转的题单id:" + s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (!"200".equals(jSONObject.optString("code")) || (list = (List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<ShengYunSetsBean.DataBean>>() { // from class: com.psychiatrygarden.activity.HomePageNewActivity.12.1
                    }.getType())) == null || list.size() <= 0) {
                        return;
                    }
                    HomePageNewActivity.this.sendShengYunSets(s2);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private void getToken() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("user_id", UserConfig.getUserId());
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mGetTokenDataUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.HomePageNewActivity.9
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                if (ProjectApp.isShowUpdateVersion) {
                    return;
                }
                HomePageNewActivity.this.checkVersion();
                HomePageNewActivity.this.appTestVersion();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass9) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        String strOptString = jSONObject.optJSONObject("data").optString("encryption");
                        if (TextUtils.isEmpty(strOptString)) {
                            return;
                        }
                        JSONObject jSONObject2 = new JSONObject(DesUtil.decode(CommonParameter.DES_KEY_VERIFYS, strOptString));
                        if (!jSONObject2.optString("secret").equals(UserConfig.getInstance().getUser().getSecret())) {
                            Intent intent = new Intent(HomePageNewActivity.this.mContext, (Class<?>) ExitLoginDialogActivity.class);
                            intent.putExtra("message", "您的账号已在其他设备登录");
                            intent.putExtra("secret", jSONObject2.optString("secret"));
                            intent.setFlags(268435456);
                            HomePageNewActivity.this.startActivity(intent);
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                if (ProjectApp.isShowUpdateVersion) {
                    PopupShowManager.getInstance(HomePageNewActivity.this.mContext).checkShowCoupon(HomePageNewActivity.this.mContext, PopupShowManager.FLAG_ENTER_APP_HOME, "0", null, null);
                } else {
                    HomePageNewActivity.this.checkVersion();
                    HomePageNewActivity.this.appTestVersion();
                }
            }
        });
    }

    private void getVip() {
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.vipToApi, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.HomePageNewActivity.18
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                String str = "1";
                super.onSuccess((AnonymousClass18) s2);
                try {
                    VipTipsBean vipTipsBean = (VipTipsBean) new Gson().fromJson(s2, VipTipsBean.class);
                    if (vipTipsBean.getCode().equals("200")) {
                        String available = vipTipsBean.getData().getVip().getAvailable();
                        if (TextUtils.isEmpty(available)) {
                            available = "0";
                        }
                        SharePreferencesUtils.writeStrConfig(CommonParameter.vip_available, available, HomePageNewActivity.this.mContext);
                        if ("1".equals(vipTipsBean.getData().getVip().getAvailable())) {
                            UserConfig.getInstance().getUser().setIs_vip(1 == vipTipsBean.getData().getVip().getIs_vip() ? "1" : "0");
                            UserConfig.getInstance().saveUser(UserConfig.getInstance().getUser());
                        }
                        if (!"1".equals(vipTipsBean.getData().getSvip().getAvailable())) {
                            SharePreferencesUtils.writeStrConfig(CommonParameter.issvip, "2", HomePageNewActivity.this.mContext);
                            return;
                        }
                        boolean z2 = 1 == vipTipsBean.getData().getSvip().getIs_vip();
                        if (!z2) {
                            str = "0";
                        }
                        SharePreferencesUtils.writeStrConfig(CommonParameter.issvip, str, HomePageNewActivity.this.mContext);
                        if (z2) {
                            SharePreferencesUtils.writeStrConfig(CommonParameter.svip_available, vipTipsBean.getData().getSvip().getAvailable() + "", HomePageNewActivity.this.mContext);
                            SharePreferencesUtils.writeStrConfig(CommonParameter.svip_due_soon, vipTipsBean.getData().getSvip().getVip_due_soon() + "", HomePageNewActivity.this.mContext);
                            SharePreferencesUtils.writeStrConfig(CommonParameter.show_line_svip, vipTipsBean.getData().getSvip().getVip_deadline() + "", HomePageNewActivity.this.mContext);
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public EaseUser getuserInfio(String username) {
        if (ProjectApp.hxUser.get(username) == null) {
            return null;
        }
        EaseUser easeUser = new EaseUser(username);
        EMUserInfo eMUserInfo = ProjectApp.hxUser.get(username);
        if (eMUserInfo != null) {
            String nickname = eMUserInfo.getNickname();
            String avatarUrl = eMUserInfo.getAvatarUrl();
            easeUser.setNickname(nickname);
            easeUser.setAvatar(avatarUrl);
        }
        return easeUser;
    }

    private void initHxInfo() {
        EaseIM.getInstance().setUserProvider(new EaseUserProfileProvider() { // from class: com.psychiatrygarden.activity.sc
            @Override // com.hyphenate.easeui.provider.EaseUserProfileProvider
            public final EaseUser getUser(String str) {
                return this.f13826a.getuserInfio(str);
            }
        });
        EMClient.getInstance().chatManager().asyncFetchConversationsFromServer(new EMValueCallBack<Map<String, EMConversation>>() { // from class: com.psychiatrygarden.activity.HomePageNewActivity.5
            @Override // com.hyphenate.EMValueCallBack
            public void onError(int error, String errorMsg) {
            }

            @Override // com.hyphenate.EMValueCallBack
            public void onSuccess(Map<String, EMConversation> value) throws JSONException {
                String[] strArr = new String[value.size()];
                Iterator<Map.Entry<String, EMConversation>> it = value.entrySet().iterator();
                int i2 = 0;
                while (it.hasNext()) {
                    strArr[i2] = it.next().getValue().conversationId();
                    i2++;
                }
                HomePageNewActivity.this.getChatUserInfo(strArr);
                HomePageNewActivity.this.updateHxUserInfo();
            }
        });
        EMPushHelper.getInstance().setPushListener(new PushListener() { // from class: com.psychiatrygarden.activity.HomePageNewActivity.6
            @Override // com.hyphenate.push.PushListener
            public boolean isSupportPush(EMPushType pushType, EMPushConfig pushConfig) {
                return super.isSupportPush(pushType, pushConfig);
            }

            @Override // com.hyphenate.push.PushListener
            public void onError(EMPushType pushType, long errorCode) {
            }
        });
        EaseThreadManager.getInstance().runOnIOThread(new Runnable() { // from class: com.psychiatrygarden.activity.tc
            @Override // java.lang.Runnable
            public final void run() {
                HomePageNewActivity.lambda$initHxInfo$2();
            }
        });
        HmsMessaging.getInstance(this).setAutoInitEnabled(true);
        HMSPushHelper.getInstance().getHMSToken(this);
        HeytapPushManager.init(this, true);
    }

    private void initLocationOption() {
        try {
            startService(new Intent(this, (Class<?>) YkbLocationService.class));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void initPermissionForReadOrWrite() {
        if (CommonUtil.hasRequiredPermissionsWriteStorage(this)) {
            installApp();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Permission.READ_EXTERNAL_STORAGE}, 273);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initProducer(String endpoint, String project, String logstore) {
        try {
            LogProducerConfig logProducerConfig = new LogProducerConfig(getApplicationContext(), DefaultWebClient.HTTPS_SCHEME + endpoint, project, logstore);
            this.config = logProducerConfig;
            logProducerConfig.setDropDelayLog(0);
            this.config.setDropUnauthorizedLog(0);
            client = new LogProducerClient(this.config, new LogProducerCallback() { // from class: com.psychiatrygarden.activity.HomePageNewActivity.20
                /* JADX WARN: Removed duplicated region for block: B:9:0x0012  */
                @Override // com.aliyun.sls.android.producer.LogProducerCallback
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public void onCall(int r1, java.lang.String r2, java.lang.String r3, int r4, int r5) {
                    /*
                        r0 = this;
                        r2 = 6
                        if (r1 != r2) goto L12
                        com.psychiatrygarden.activity.HomePageNewActivity r2 = com.psychiatrygarden.activity.HomePageNewActivity.this
                        boolean r3 = r2.aliyunKeyLoding
                        if (r3 != 0) goto L12
                        int r3 = r2.aliyunKeyLoadTimes
                        r4 = 1
                        if (r3 >= r4) goto L12
                        com.psychiatrygarden.activity.HomePageNewActivity.access$1500(r2)
                        goto L19
                    L12:
                        if (r1 != 0) goto L19
                        com.psychiatrygarden.activity.HomePageNewActivity r2 = com.psychiatrygarden.activity.HomePageNewActivity.this
                        r3 = 0
                        r2.aliyunKeyLoadTimes = r3
                    L19:
                        com.aliyun.sls.android.producer.LogProducerResult r1 = com.aliyun.sls.android.producer.LogProducerResult.fromInt(r1)
                        com.aliyun.sls.android.producer.LogProducerResult r2 = com.aliyun.sls.android.producer.LogProducerResult.LOG_PRODUCER_SEND_UNAUTHORIZED
                        if (r2 == r1) goto L23
                        com.aliyun.sls.android.producer.LogProducerResult r1 = com.aliyun.sls.android.producer.LogProducerResult.LOG_PRODUCER_PARAMETERS_INVALID
                    L23:
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.HomePageNewActivity.AnonymousClass20.onCall(int, java.lang.String, java.lang.String, int, int):void");
                }
            });
        } catch (LogProducerException e2) {
            e2.printStackTrace();
        }
    }

    private void installApp() {
        String storagePath = ProjectApp.instance().getStoragePath();
        String simpleName = getClass().getSimpleName();
        StringBuilder sb = new StringBuilder();
        sb.append("SDK=");
        int i2 = Build.VERSION.SDK_INT;
        sb.append(i2);
        LogUtils.d(simpleName, sb.toString());
        File file = new File(storagePath, "yikaobang.apk");
        Uri uriForFile = FileProvider.getUriForFile(this, getPackageName() + ".fileProvider", file);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(uriForFile, "application/vnd.android.package-archive");
        intent.addFlags(268435456);
        if (i2 > 24) {
            intent.addFlags(1);
        } else {
            intent.setDataAndType(Uri.parse("file://" + file.getPath()), "application/vnd.android.package-archive");
        }
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dialog$3(CustomDialog customDialog, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        customDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dialog$4(CustomDialog customDialog, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        customDialog.dismissNoAnimaltion();
        ProjectApp.instance().exit();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$initHxInfo$2() {
        try {
            EMClient.getInstance().pushManager().updatePushDisplayStyle(EMPushManager.DisplayStyle.MessageSummary);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loginHuanxin$1() {
        EMClient.getInstance().login(UserConfig.getInstance().getUser().getUser_uuid(), "335fda7863dc40619f2847d67f7b034b", new EMCallBack() { // from class: com.psychiatrygarden.activity.HomePageNewActivity.4
            @Override // com.hyphenate.EMCallBack
            public void onError(int code, String message) throws JSONException {
                Log.e("login_hx", "环信登录失败" + message);
                if (code == 204) {
                    try {
                        AjaxParams ajaxParams = new AjaxParams();
                        ajaxParams.put("user_uuid", UserConfig.getInstance().getUser().getUser_uuid());
                        ajaxParams.put("nickname", UserConfig.getInstance().getUser().getNickname());
                        ajaxParams.put("avatar", UserConfig.getInstance().getUser().getAvatar());
                        ajaxParams.put("user_type", UserConfig.getInstance().getUser().getUser_type());
                        ChatRequest.getIntance(HomePageNewActivity.this).updateUserToEasemob(ajaxParams, new QuestionDataCallBack<String>() { // from class: com.psychiatrygarden.activity.HomePageNewActivity.4.1
                            @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
                            public void onFailure(Throwable t2, int errorNo, String strMsg, String requstUrl) {
                            }

                            @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
                            public void onStart(String requstUrl) {
                            }

                            @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
                            public void onSuccess(String s2, String requstUrl) {
                            }
                        });
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
                HomePageNewActivity.this.updateHxUserInfo();
            }

            @Override // com.hyphenate.EMCallBack
            public void onProgress(int progress, String status) {
            }

            @Override // com.hyphenate.EMCallBack
            public void onSuccess() throws JSONException {
                Log.e("login_hx", "环信登录成功");
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                HMSPushHelper.getInstance().getHMSToken(HomePageNewActivity.this);
                HeytapPushManager.init(HomePageNewActivity.this, true);
                HomePageNewActivity.this.updateHxUserInfo();
                EventBus.getDefault().post(EventBusConstant.EVENT_HX_LOGIN_SUCCESS);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$0(Message message) {
        int i2 = message.what;
        if (i2 == 1) {
            getToken();
            return false;
        }
        if (i2 == 6) {
            getIdentityLabel();
            return false;
        }
        if (i2 != 7) {
            return false;
        }
        getAliyunCredentials();
        AliYunLogUtil.getInstance().getAliYunChooseSchoolCredentials(this);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onEventMainThread$5(String str, String str2) {
        LogUtils.d("onResponse", str + StrPool.TAB + str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onEventMainThread$6(VolleyError volleyError, String str) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadFragmentShow() {
        int i2 = 0;
        ProjectApp.newHomeStyle = SharePreferencesUtils.readBooleanConfig(CommonParameter.SHOW_NEW_HOME, false, this);
        if (findFragment(HomePageNewFragment.class) == null) {
            if (!TextUtils.isEmpty(getIntent().getStringExtra(EventBusConstant.EVENT_HX_PUSH_DATA))) {
                try {
                    String stringExtra = getIntent().getStringExtra(EventBusConstant.EVENT_HX_PUSH_DATA);
                    if (stringExtra != null) {
                        if (new JSONObject(stringExtra).optInt(PushConstants.PUSH_TYPE) == 7) {
                            i2 = 1;
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            loadRootFragment(R.id.layout_main, HomePageNewFragment.newInstance(i2));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loginHuanxin() {
        LogUtils.e(AliyunLogKey.KEY_UUID, UserConfig.getInstance().getUser().getUser_uuid());
        new Handler().post(new Runnable() { // from class: com.psychiatrygarden.activity.mc
            @Override // java.lang.Runnable
            public final void run() {
                this.f12761c.lambda$loginHuanxin$1();
            }
        });
        initHxInfo();
    }

    private void registerNetworkReceiver() {
        try {
            if (Build.VERSION.SDK_INT >= 24) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService("connectivity");
                if (connectivityManager != null) {
                    NetworkCallBack networkCallBack = new NetworkCallBack();
                    this.mNetworkCallback = networkCallBack;
                    connectivityManager.registerDefaultNetworkCallback(networkCallBack);
                }
            } else {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
                NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();
                this.mNetworkChangeReceiver = networkChangeReceiver;
                registerReceiver(networkChangeReceiver, intentFilter);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendShengYunGoodsData(String data) {
        Intent intent = new Intent(GlobalAction.ACTION_RECEIVE_SHENGYUN_GOODS);
        intent.putExtra(GlobalAction.EXTRA_SHENGYUN_GOODS_DATA, data);
        sendBroadcast(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendShengYunSets(String data) {
        Intent intent = new Intent(GlobalAction.ACTION_RECEIVE_SHENGYUN_SETS);
        intent.putExtra(GlobalAction.EXTRA_SHENGYUN_SETS_DATA, data);
        sendBroadcast(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAccessKey(String accessKeyId, String accessKeySecret, String securityToken) {
        if (securityToken != null && !"".equals(securityToken)) {
            this.config.resetSecurityToken(accessKeyId, accessKeySecret, securityToken);
        } else {
            this.config.setAccessKeyId(accessKeyId);
            this.config.setAccessKeySecret(accessKeySecret);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateHxUserInfo() throws JSONException {
        if (UserConfig.isLogin()) {
            EMUserInfo eMUserInfo = new EMUserInfo();
            eMUserInfo.setUserId(EMClient.getInstance().getCurrentUser());
            eMUserInfo.setNickname(UserConfig.getInstance().getUser().getNickname());
            eMUserInfo.setAvatarUrl(UserConfig.getInstance().getUser().getAvatar());
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("app", "yi_kao_bang");
                jSONObject.put(com.alipay.sdk.packet.d.f3298n, "android");
                eMUserInfo.setExt(jSONObject.toString());
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            EMClient.getInstance().userInfoManager().updateOwnInfo(eMUserInfo, new EMValueCallBack<String>() { // from class: com.psychiatrygarden.activity.HomePageNewActivity.8
                @Override // com.hyphenate.EMValueCallBack
                public void onError(int error, String errorMsg) {
                    Log.e("login_hx", "update_user_info_error:" + error + errorMsg);
                    if (error == 401) {
                        HomePageNewActivity.this.loginHuanxin();
                    }
                }

                @Override // com.hyphenate.EMValueCallBack
                public void onSuccess(String value) {
                    Log.e("login_hx", "update_user_info_success:" + value);
                }
            });
            if (getIntent().getBundleExtra(EventBusConstant.EVENT_HX_MESSAGE_DATA) != null) {
                mPutHxMsgData(getIntent().getBundleExtra(EventBusConstant.EVENT_HX_MESSAGE_DATA));
            }
            try {
                EMClient.getInstance().pushManager().updatePushNickname(UserConfig.getInstance().getUser().getNickname());
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void videoBuryPoint(String type, String id) {
        AjaxParams ajaxParams = new AjaxParams();
        String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.LIVE_COURSE_ID, this);
        String strConfig2 = SharePreferencesUtils.readStrConfig(CommonParameter.LIVE_ROOM_ID, this);
        if (strConfig == null) {
            return;
        }
        if (!TextUtils.isEmpty(id)) {
            strConfig2 = id;
        }
        ajaxParams.put("current_time", String.valueOf(System.currentTimeMillis() / 1000));
        ajaxParams.put("course_id", strConfig);
        ajaxParams.put("obj_id", strConfig2);
        ajaxParams.put("obj_type", TextUtils.isEmpty(id) ? "2" : "4");
        ajaxParams.put("type", type);
        if ("2".equals(type) && TextUtils.isEmpty(id)) {
            SharePreferencesUtils.writeStrConfig(CommonParameter.LIVE_COURSE_ID, "", this);
            SharePreferencesUtils.writeStrConfig(CommonParameter.LIVE_ROOM_ID, "", this);
        }
        YJYHttpUtils.post(this, NetworkRequestsURL.videoBuryPoint, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.HomePageNewActivity.16
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass16) s2);
                try {
                    if ("200".equals(new JSONObject(s2).optString("code"))) {
                        LogUtils.d("onSuccess", "buryPoint success 2");
                    }
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void dialog(int str) {
        final CustomDialog customDialog = new CustomDialog(this.mContext, 2);
        customDialog.setCancelable(false);
        customDialog.isOutTouchDismiss(false);
        customDialog.setMessage(this.mContext.getString(str));
        customDialog.setNegativeBtn(R.string.cancel, new View.OnClickListener() { // from class: com.psychiatrygarden.activity.qc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                HomePageNewActivity.lambda$dialog$3(customDialog, view);
            }
        });
        customDialog.setPositiveBtn(R.string.ok, new View.OnClickListener() { // from class: com.psychiatrygarden.activity.rc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                HomePageNewActivity.lambda$dialog$4(customDialog, view);
            }
        });
        customDialog.show();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void dismissCouponPop() {
        super.dismissCouponPop();
        if (SharePreferencesUtils.readIntConfig(CommonParameter.HOME_CURRENT_POSITION, this, 0) == 0 && !SharePreferencesUtils.readBooleanConfig(CommonParameter.IS_SHOW_HOME_AD_POP, false, this)) {
            getAdPic();
        }
        EventBus.getDefault().post("SHOW_GUIDE");
    }

    public void getIdentityLabel() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("last_update_time", "" + SharePreferencesUtils.readStrConfig(CommonParameter.last_update_time, this, ""));
        YJYHttpUtils.get(this, NetworkRequestsURL.getIdentityLabel, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.HomePageNewActivity.10
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
                super.onSuccess((AnonymousClass10) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        SelectIdentityBean.DataBean dataBean = (SelectIdentityBean.DataBean) new Gson().fromJson(jSONObject.optString("data"), SelectIdentityBean.DataBean.class);
                        SharePreferencesUtils.writeStrConfig(CommonParameter.identity_id, dataBean.getIdentity_id() + "", HomePageNewActivity.this.mContext);
                        SharePreferencesUtils.writeStrConfig(CommonParameter.App_Id, dataBean.getApp_id() + "", HomePageNewActivity.this.mContext);
                        SharePreferencesUtils.writeStrConfig(CommonParameter.app_mark, "", HomePageNewActivity.this.mContext);
                        SharePreferencesUtils.writeStrConfig(CommonParameter.Student_Type, dataBean.getApp_type() + "", HomePageNewActivity.this.mContext);
                        SharePreferencesUtils.writeStrConfig(CommonParameter.App_Name, dataBean.getTitle() + "", HomePageNewActivity.this.mContext);
                        SharePreferencesUtils.writeStrConfig(CommonParameter.am_pm, dataBean.getAm_pm() + "", HomePageNewActivity.this.mContext);
                        SharePreferencesUtils.writeStrConfig(CommonParameter.is_hidden_unit, dataBean.getIs_hidden_unit() + "", HomePageNewActivity.this.mContext);
                        SharePreferencesUtils.writeStrConfig(CommonParameter.isHideExp, dataBean.getIs_hidden_exp() + "", HomePageNewActivity.this.mContext);
                        SharePreferencesUtils.writeStrConfig(CommonParameter.is_hidden_course, dataBean.getIs_hidden_course() + "", HomePageNewActivity.this.mContext);
                        SharePreferencesUtils.writeStrConfig(CommonParameter.is_hidden_shop, dataBean.getIs_hidden_shop() + "", HomePageNewActivity.this.mContext);
                        SharePreferencesUtils.writeStrConfig(CommonParameter.is_hidden_restore_img, dataBean.getIs_hidden_restore_img() + "", HomePageNewActivity.this.mContext);
                        SharePreferencesUtils.writeStrConfig(CommonParameter.is_hidden_question_type, dataBean.getIs_hidden_question_type() + "", HomePageNewActivity.this.mContext);
                        SharePreferencesUtils.writeStrConfig(CommonParameter.is_hidden_correction_error, dataBean.getIs_hidden_correction_error() + "", HomePageNewActivity.this.mContext);
                        SharePreferencesUtils.writeStrConfig(CommonParameter.catalogue_q, new Gson().toJson(dataBean.getChildren()), HomePageNewActivity.this.mContext);
                        SharePreferencesUtils.writeStrConfig(CommonParameter.last_update_time, dataBean.getLast_update_time() + "", HomePageNewActivity.this.mContext);
                        SharePreferencesUtils.writeStrConfig(CommonParameter.is_hidden_easy_option, dataBean.getIs_hidden_wrong_option() + "", HomePageNewActivity.this.mContext);
                        SharePreferencesUtils.writeStrConfig(CommonParameter.is_hidden_statistics_card, dataBean.getIs_hidden_statistics_card() + "", HomePageNewActivity.this.mContext);
                        SharePreferencesUtils.writeStrConfig(CommonParameter.is_hidden_difficulty, dataBean.getIs_hidden_difficulty() + "", HomePageNewActivity.this.mContext);
                        SharePreferencesUtils.writeStrConfig(CommonParameter.is_hidden_stat, dataBean.getIs_hidden_stat() + "", HomePageNewActivity.this.mContext);
                        SharePreferencesUtils.writeStrConfig(CommonParameter.is_hidden_label, dataBean.getIs_hidden_label() + "", HomePageNewActivity.this.mContext);
                        SharePreferencesUtils.writeStrConfig(CommonParameter.is_hidden_outlines, dataBean.getIs_hidden_outlines() + "", HomePageNewActivity.this.mContext);
                        SharePreferencesUtils.writeStrConfig(CommonParameter.is_hidden_restore, dataBean.getIs_hidden_restore() + "", HomePageNewActivity.this.mContext);
                        SharePreferencesUtils.writeStrConfig(CommonParameter.is_hidden_analysis, dataBean.getIs_hidden_analysis() + "", HomePageNewActivity.this.mContext);
                        SharePreferencesUtils.writeStrConfig(CommonParameter.is_hidden_options, dataBean.getIs_hidden_options() + "", HomePageNewActivity.this.mContext);
                        SharePreferencesUtils.writeStrConfig(CommonParameter.is_hidden_options_update, dataBean.getIs_hidden_options_update() + "", HomePageNewActivity.this.mContext);
                        SharePreferencesUtils.writeStrConfig(CommonParameter.is_hidden_analysis_update, dataBean.getIs_hidden_analysis_update() + "", HomePageNewActivity.this.mContext);
                        SharePreferencesUtils.writeBooleanConfig(CommonParameter.SHOW_NEW_HOME, TextUtils.equals("0", dataBean.getIs_hidden_index()), HomePageNewActivity.this.mContext);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                HomePageNewActivity.this.loadFragmentShow();
            }
        });
    }

    public void gotoActivity() {
        try {
            PublicMethodActivity.getInstance().mToActivity(SharePreferencesUtils.readStrConfig(CommonParameter.boot_page, this.mContext));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        SkinManager.setmThemeInterface(this);
        if (!SharePreferencesUtils.readBooleanConfig("onRestart", false, this)) {
            SharePreferencesUtils.writeBooleanConfig("onRestart", true, this);
        }
        LogUtils.e("isBySystem", "初始化主题");
    }

    public void initData() {
        if (UserConfig.isLogin()) {
            initHxInfo();
            getVip();
        }
        if (!TextUtils.isEmpty(getIntent().getStringExtra(EventBusConstant.EVENT_HX_PUSH_DATA))) {
            PublicMethodActivity.getInstance().mToActivity(getIntent().getStringExtra(EventBusConstant.EVENT_HX_PUSH_DATA));
        }
        try {
            if (!"".equals(getIntent().getStringExtra("scheme")) && "yikaobang.app".equals(getIntent().getStringExtra("scheme"))) {
                Uri data = getIntent().getData();
                if (SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this).equals(data.getQueryParameter("app_id"))) {
                    Intent intent = new Intent(this, (Class<?>) HandoutsInfoActivity.class);
                    intent.putExtra("cat_id", data.getQueryParameter("cid"));
                    intent.putExtra("article", data.getQueryParameter("id"));
                    intent.putExtra("json_path", data.getQueryParameter("json_path"));
                    intent.putExtra("html_path", "");
                    intent.putExtra("h5_path", data.getQueryParameter("h5_path"));
                    intent.putExtra("is_rich_text", data.getQueryParameter("is_rich_text"));
                    intent.putExtra("index", data.getQueryParameter("cid") + StrPool.UNDERLINE + data.getQueryParameter("id"));
                    startActivity(intent);
                } else if (!data.getQueryParameter("isH5").equals("1")) {
                    ToastUtil.shortToast(this, "请切换您的身份至'" + data.getQueryParameter("app_name") + "'才能查看此文章");
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            if (TextUtils.isEmpty(getIntent().getStringExtra("fromWechatOpenUrl"))) {
                return;
            }
            Intent intent2 = new Intent(ProjectApp.instance(), (Class<?>) WebLongSaveActivity.class);
            intent2.putExtra("title", ProjectApp.instance().getResources().getString(R.string.app_name));
            intent2.putExtra("web_url", getIntent().getStringExtra("fromWechatOpenUrl"));
            intent2.setFlags(268435456);
            ProjectApp.instance().startActivity(intent2);
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void initStatusBar() throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        StatusBarUtil.setDarkMode(this);
        int currentSkinType = SkinManager.getCurrentSkinType(this);
        this.mBaseTheme = currentSkinType;
        if (currentSkinType != 0) {
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.new_bg_one_color_night), 0);
            getWindow().setNavigationBarColor(Color.parseColor("#1C2134"));
        } else {
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.new_bg_one_color), 0);
            getWindow().getDecorView().setSystemUiVisibility(8192);
            getWindow().setNavigationBarColor(Color.parseColor("#FBFBFB"));
        }
    }

    @Override // com.psychiatrygarden.utils.ThemeInterface
    public void mChageThemeView(int themeid) {
        LogUtils.e("isBySystem", "更改主题回调");
        EventBus.getDefault().post(EventBusConstant.EVENT_CHAGE_THEME);
        ProjectApp.isRecreate = true;
    }

    public void mPutHxMsgData(Bundle bundle) {
        try {
            String string = bundle.getString("f");
            String string2 = bundle.getString("g");
            if (TextUtils.isEmpty(string2)) {
                if (TextUtils.isEmpty(string)) {
                    return;
                }
                Intent intent = new Intent(this, (Class<?>) ChatActivity.class);
                intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, 1);
                intent.putExtra(EaseConstant.EXTRA_CONVERSATION_ID, string);
                intent.setFlags(268435456);
                startActivity(intent);
                return;
            }
            Intent intent2 = new Intent(this, (Class<?>) ChatActivity.class);
            intent2.putExtra(EaseConstant.EXTRA_CHAT_TYPE, 2);
            GroupChatListBean groupChatListBean = (GroupChatListBean) new Gson().fromJson(SharePreferencesUtils.readStrConfig(CommonParameter.MY_GROUP_CHAT_LIST, this, ""), GroupChatListBean.class);
            if (groupChatListBean.getCode().equals("200")) {
                int i2 = 0;
                while (true) {
                    if (i2 >= groupChatListBean.getData().size()) {
                        break;
                    }
                    if (groupChatListBean.getData().get(i2).getCommunity_id().equals(string2)) {
                        intent2.putExtra("group_img", groupChatListBean.getData().get(i2).getLogo());
                        intent2.putExtra("name", groupChatListBean.getData().get(i2).getName());
                        break;
                    }
                    i2++;
                }
            }
            intent2.putExtra(EaseConstant.EXTRA_CONVERSATION_ID, string2);
            intent2.setFlags(268435456);
            startActivity(intent2);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this.mContext).onActivityResult(requestCode, resultCode, data);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        EventBus.getDefault().post(CommonParameter.HOME_ACTIVITY_CONFIGURATION_CHANGE);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this);
        SharePreferencesUtils.writeStrConfig(CommonParameter.FILTER_OPTION_DETAIL_IMG + strConfig, "", this);
        SharePreferencesUtils.writeStrConfig(CommonParameter.CURRENT_QUESTION_CATEGORY_ID + strConfig, "", this);
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.DETAIL_SUBMIT_STATISTICS, false, this);
        setSwipeBackEnable(false);
        this.mActionBar.hide();
        setContentView(R.layout.activity_main);
        com.psychiatrygarden.interfaceclass.LocalBroadcastManager.getInstance(this.mContext).registerReceiver(this.refreshHxLoginReceiver, new IntentFilter("refreshPersonal"));
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("JOIN_LIVE");
        intentFilter.addAction("EXIT_LOGIN");
        intentFilter.addAction("DOCTOR_TOP");
        intentFilter.addAction("SHARE_SHENG_YUN");
        intentFilter.addAction("QUIT_LIVE");
        intentFilter.addAction("VIEW_HANDOUT");
        intentFilter.addAction("HANDOUT_VIEW");
        intentFilter.addAction("HANDOUT_QUIT");
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mReceiver, intentFilter);
        registerNetworkReceiver();
        this.mHandler.sendEmptyMessage(1);
        this.mHandler.sendEmptyMessage(7);
        this.mHandler.sendEmptyMessage(6);
        initData();
        SharePreferencesUtils.writeStrConfig(CommonParameter.KNOWLEDGE_FILTER_TIPS_ICON, "", this);
        SharePreferencesUtils.writeStrConfig(CommonParameter.KNOWLEDGE_FILTER_TIPS_TEXT, "", this);
        UiModeManager uiModeManager = (UiModeManager) getSystemService("uimode");
        int i2 = getResources().getConfiguration().uiMode;
        boolean booleanConfig = SharePreferencesUtils.readBooleanConfig(CommonParameter.IS_BY_SYS_SKIN_MODE, false, this);
        if (!SharePreferencesUtils.readBooleanConfig("onRestart", false, this) && booleanConfig) {
            if ((i2 & 48) == 32 || uiModeManager.getNightMode() == 2) {
                LogUtils.e("isBySystem", "跟随系统====》当前系统为深色模式当前主题=" + SkinManager.getCurrentSkinType(this));
                if (SkinManager.getCurrentSkinType(this) == 0) {
                    SkinManager.changeSkin(this, 1);
                }
            } else {
                LogUtils.e("isBySystem", "跟随系统====》当前系统为正常模式");
                if (SkinManager.getCurrentSkinType(this) == 1) {
                    SkinManager.changeSkin(this, 0);
                }
            }
        }
        try {
            checkMergeOldVersionDownloadDbData();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        registerReceiver(this.shengyunRequestReceiver, new IntentFilter(GlobalAction.ACTION_REQUEST_SHENGYUN_GOODS));
        registerReceiver(this.shengyunRequestReceiver, new IntentFilter(GlobalAction.ACTION_REQUEST_SHENGYUN_SETS));
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        ConnectivityManager connectivityManager;
        super.onDestroy();
        try {
            Disposable disposable = this.disposable;
            if (disposable != null && !disposable.isDisposed()) {
                this.disposable.dispose();
            }
            NetworkChangeReceiver networkChangeReceiver = this.mNetworkChangeReceiver;
            if (networkChangeReceiver != null) {
                unregisterReceiver(networkChangeReceiver);
            }
            if (this.mNetworkCallback != null && (connectivityManager = (ConnectivityManager) getSystemService("connectivity")) != null) {
                connectivityManager.unregisterNetworkCallback(this.mNetworkCallback);
            }
            if (!isDestroyed()) {
                LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mReceiver);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.mHandler.removeCallbacksAndMessages(null);
        com.psychiatrygarden.interfaceclass.LocalBroadcastManager.getInstance(this.mContext).unregisterReceiver(this.refreshHxLoginReceiver);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        str.hashCode();
        switch (str) {
            case "LoginSuccess":
                this.mHandler.sendEmptyMessage(7);
                break;
            case "EVENT_CHAGE_THEME":
                Intent intent = new Intent(this, (Class<?>) HomePageNewActivity.class);
                intent.addFlags(268468224);
                startActivity(intent);
                overridePendingTransition(R.anim.start_anim, R.anim.out_anim);
                break;
            case "DOWNLOAD_SUCCESS":
                initPermissionForReadOrWrite();
                break;
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4 || event.getRepeatCount() != 0) {
            return false;
        }
        List<VideoDownTempBean> list = ProjectApp.vids;
        if (list == null || list.size() <= 0) {
            dialog(R.string.app_close);
            return true;
        }
        dialog(R.string.app_chone);
        return true;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        this.mHandler.sendEmptyMessage(1);
        try {
            if (!TextUtils.isEmpty(getIntent().getStringExtra(EventBusConstant.EVENT_HX_PUSH_DATA))) {
                PublicMethodActivity.getInstance().mToActivity(getIntent().getStringExtra(EventBusConstant.EVENT_HX_PUSH_DATA));
            }
            if (getIntent().getBundleExtra(EventBusConstant.EVENT_HX_MESSAGE_DATA) != null) {
                mPutHxMsgData(getIntent().getBundleExtra(EventBusConstant.EVENT_HX_MESSAGE_DATA));
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            if (!"".equals(getIntent().getStringExtra("scheme")) && "yikaobang.app".equals(getIntent().getStringExtra("scheme"))) {
                Uri data = getIntent().getData();
                if (SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this).equals(data.getQueryParameter("app_id"))) {
                    Intent intent2 = new Intent(this, (Class<?>) HandoutsInfoActivity.class);
                    intent2.putExtra("cat_id", data.getQueryParameter("cid"));
                    intent2.putExtra("article", data.getQueryParameter("id"));
                    intent2.putExtra("json_path", data.getQueryParameter("json_path"));
                    intent2.putExtra("html_path", "");
                    intent2.putExtra("h5_path", data.getQueryParameter("h5_path"));
                    intent2.putExtra("is_rich_text", data.getQueryParameter("is_rich_text"));
                    intent2.putExtra("index", data.getQueryParameter("cid") + StrPool.UNDERLINE + data.getQueryParameter("id"));
                    startActivity(intent2);
                } else if (!data.getQueryParameter("isH5").equals("1")) {
                    ToastUtil.shortToast(this, "请切换您的身份至'" + data.getQueryParameter("app_name") + "'才能查看此文章");
                }
            }
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        String stringExtra = intent.getStringExtra("chapter_id");
        if (TextUtils.isEmpty(stringExtra)) {
            return;
        }
        EventBus.getDefault().post(new LocateChapterEvent(stringExtra));
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (273 == requestCode) {
            if (grantResults.length > 0) {
                int i2 = grantResults[0];
                if (i2 != -1) {
                    if (i2 != 0) {
                        return;
                    }
                    installApp();
                    return;
                } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, Permission.READ_EXTERNAL_STORAGE)) {
                    ActivityCompat.requestPermissions(this, new String[]{Permission.READ_EXTERNAL_STORAGE}, 273);
                    return;
                } else {
                    NewToast.showShort(this, "数据写入应用权限被禁用，请在权限管理修改", 0).show();
                    return;
                }
            }
            return;
        }
        if (274 != requestCode || grantResults.length <= 0) {
            return;
        }
        int i3 = grantResults[0];
        if (i3 != -1) {
            if (i3 != 0) {
                return;
            }
            try {
                initLocationOption();
                return;
            } catch (Exception e2) {
                e2.printStackTrace();
                return;
            }
        }
        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Permission.ACCESS_COARSE_LOCATION)) {
            NewToast.showShort(this, "位置权限被禁用，请在权限管理修改", 0).show();
        }
        try {
            initLocationOption();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        ProjectApp.isSerachClick = false;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }

    public void onEventMainThread(HomeTabStatus homeTabStatus) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if ("homeTab".equals(homeTabStatus.mEvestr)) {
            CommonUtil.putTimeData();
            int i2 = homeTabStatus.position;
            if (i2 != 1) {
                if (i2 != -1) {
                    initStatusBar();
                } else {
                    setNewStyleStatusBarColor2();
                }
            }
        }
        if (homeTabStatus.position == 0 && ProjectApp.newHomeStyle) {
            TypedArray typedArrayObtainStyledAttributes = this.mContext.getTheme().obtainStyledAttributes(new int[]{R.attr.new_bg_two_color});
            getWindow().setStatusBarColor(typedArrayObtainStyledAttributes.getColor(0, this.mContext.getColor(R.color.new_bg_two_color)));
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    @Subscribe
    public void onEventMainThread(GoToLivingEvent event) {
        try {
            PublicMethodActivity.goToLiving(this, event.getMessage());
        } catch (Exception e2) {
            Log.d(this.TAG, "onEventMainThread: " + e2.getMessage());
        }
    }

    @Subscribe
    public void onEventMainThread(LocationEvent event) {
        com.alibaba.fastjson.JSONObject jSONObject = new com.alibaba.fastjson.JSONObject();
        if (event.getLatitude() <= 0.0d || event.getLongitude() <= 0.0d) {
            return;
        }
        jSONObject.put("longitude", (Object) Double.valueOf(event.getLongitude()));
        jSONObject.put("latitude", (Object) Double.valueOf(event.getLatitude()));
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put("location", jSONObject.toString());
        YJYHttpUtils.post(this, NetworkRequestsURL.accessLogUrl, arrayMap, new Response.Listener() { // from class: com.psychiatrygarden.activity.oc
            @Override // com.android.volley.Response.Listener
            public final void onResponse(Object obj, String str) {
                HomePageNewActivity.lambda$onEventMainThread$5((String) obj, str);
            }
        }, new Response.ErrorListener() { // from class: com.psychiatrygarden.activity.pc
            @Override // com.android.volley.Response.ErrorListener
            public final void onErrorResponse(VolleyError volleyError, String str) {
                HomePageNewActivity.lambda$onEventMainThread$6(volleyError, str);
            }
        });
    }

    @Subscribe
    public void onEventMainThread(EnterHandoutPreviewEvent event) {
        VideoHandout handout = event.getHandout();
        EventBus.getDefault().post(new ExitFromPipEvent());
        InformationPreviewAct.newIntent(this, handout.getId(), handout.getUrl(), handout.isLocalFile(), true, handout.getTitle());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBusMessage event) {
        if (EventBusConstant.EVENT_HX_PUSH_DATA.equals(event.getKey())) {
            PublicMethodActivity.getInstance().mToActivity((String) event.getValueObj());
        }
    }

    @Subscribe
    public void onEventMainThread(ExitUpdateEvent e2) {
        PopupShowManager.getInstance(this.mContext).checkShowCoupon(this.mContext, PopupShowManager.FLAG_ENTER_APP_HOME, "0", null, null);
    }
}
