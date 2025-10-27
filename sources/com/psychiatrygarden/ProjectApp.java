package com.psychiatrygarden;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;
import androidx.fragment.app.FragmentActivity;
import androidx.multidex.MultiDexApplication;
import com.alibaba.sdk.android.vod.upload.VODSVideoUploadClient;
import com.alibaba.sdk.android.vod.upload.VODSVideoUploadClientImpl;
import com.aliyun.downloader.DownloaderConfig;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadInfoListener;
import com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadManager;
import com.aliyun.private_service.PrivateService;
import com.android.volley.RequestQueue;
import com.beizi.fusion.BeiZis;
import com.easefun.polyv.livecommon.module.config.PLVLiveSDKConfig;
import com.google.android.exoplayer2.C;
import com.google.gson.Gson;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.chat.EMUserInfo;
import com.hyphenate.easeui.EaseIM;
import com.hyphenate.push.EMPushConfig;
import com.lxj.xpopup.core.BasePopupView;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.psychiatrygarden.activity.ActivityLifecycleCallbacks;
import com.psychiatrygarden.activity.HomePageNewActivity;
import com.psychiatrygarden.activity.comment.alipler.AliPlayerVideoPlayActivity;
import com.psychiatrygarden.activity.courselist.YkBManager;
import com.psychiatrygarden.activity.courselist.bean.CurriculumScheduleCardBean;
import com.psychiatrygarden.activity.courselist.bean.VideoDownTempBean;
import com.psychiatrygarden.activity.courselist.roomDB.DbManager;
import com.psychiatrygarden.aliPlayer.utils.MyRefreshStsCallback;
import com.psychiatrygarden.bean.AnsweredQuestionBean;
import com.psychiatrygarden.bean.DaoSession;
import com.psychiatrygarden.bean.DaoSessionBei;
import com.psychiatrygarden.bean.DaoSessionTiKu;
import com.psychiatrygarden.bean.ExesQuestionBean;
import com.psychiatrygarden.bean.NextChapterInfo;
import com.psychiatrygarden.bean.NextNodeData;
import com.psychiatrygarden.bean.SubjectiveListBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.utils.AsyncHandler;
import com.psychiatrygarden.utils.ComDBSelectUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.MyDownloadInfoListener;
import com.psychiatrygarden.utils.SdkConstant;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.Storage;
import com.psychiatrygarden.widget.CustomRefreshHeaderView;
import com.psychiatrygarden.widget.LoadDialogFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsDownloader;
import com.tencent.smtt.sdk.TbsListener;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.vivo.push.IPushActionListener;
import com.vivo.push.PushClient;
import com.vivo.push.ups.TokenResult;
import com.vivo.push.ups.UPSRegisterCallback;
import com.vivo.push.ups.VUpsManager;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.yddmi.doctor.app.KotlinMvpApp;
import com.yikaobang.yixue.BuildConfig;
import com.yikaobang.yixue.R;
import com.ykb.common_share_lib.CommonConfig;
import com.ykb.common_share_lib.GlobalApplication;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import kotlin.Pair;

/* loaded from: classes5.dex */
public class ProjectApp extends MultiDexApplication implements GlobalApplication {
    public static final int ENV_PRE_PUBLISH = 1;
    public static final int ENV_RELEASE = 2;
    public static final int ENV_TEST = 0;
    public static final String LOG_TAG = "ProjectApp";
    public static final String TAG = "nomedia 加载实现";
    public static int android_open = 0;
    public static DbManager database = null;
    public static AliyunDownloadInfoListener downloadInfoListener = null;
    public static AliyunDownloadManager downloadManager = null;
    public static ProjectApp instance = null;
    public static boolean isRecreate = false;
    public static boolean isjingyan = false;
    public static ComDBSelectUtils mComDb = null;
    public static DaoSession mDaoSession = null;
    public static DaoSessionBei mDaoSessionBei = null;
    private static NextChapterInfo mNextChapterInfo = null;
    public static DaoSessionTiKu mTiKuDaoSession = null;
    public static String mUpDataSource = "";
    public static boolean newHomeStyle = false;
    public static RequestQueue requestQueue;
    public static VODSVideoUploadClient vodsVideoUploadClient;
    public static ArrayList<SnsPlatform> platforms = new ArrayList<>();
    public static List<ExesQuestionBean> questExamList = new ArrayList();
    public static List<ExesQuestionBean> questExamDataList = new ArrayList();
    public static boolean isForeground = false;
    public static final List<NextNodeData> nodeList = new ArrayList();
    public static List<AnsweredQuestionBean> listSubmitData = new ArrayList();
    public static List<VideoDownTempBean> vids = new ArrayList();
    public static boolean isDoSubjective = false;
    public static final List<BasePopupView> couponPopList = new ArrayList();
    public static List<SubjectiveListBean.DataBean> dataList = new ArrayList();
    public static List<CurriculumScheduleCardBean.DataDTO.ChildrenDTO> mPlayerVideo = new ArrayList();
    public static String showQuestionList = "";
    public static boolean isShowUpdateVersion = false;
    public static List<String> analysisImageStr = new ArrayList();
    public static String analysisContent = "";
    public static boolean analysisIsHidde = false;
    public static String noteBigImage = "";
    public static String noteSmellImage = "";
    public static String noteContent = "";
    public static String comment_content = "";
    public static String comment_b_img = "";
    public static String comment_s_img = "";
    public static String commentvideoPath = "";
    public static String commentvideoImage = "";
    public static String commentvideoId = "";
    public static String commentvideoImagePath = "";
    public static Map<String, EMUserInfo> hxUser = new HashMap();
    public static boolean isSerachClick = false;
    public static boolean isHaveFilter = false;
    public static Gson gson = new Gson();
    public static String module_type = "";
    public static String unit_title = "";
    public static String unit_id = "";
    public static String exam_title = "";
    public static String identity_title = "";
    public static String identity_id = "";
    public static String chapter_title = "";
    public static String chapter_id = "";
    public static String chapter_parent_title = "";
    public static String chapter_parent_id = "";
    private final List<Activity> activityList = new LinkedList();
    private boolean connectUrl = true;
    public Application.ActivityLifecycleCallbacks activityLifecycleCallbacks = new ActivityLifecycleCallbacks();
    private String apiUrl = BuildConfig.RELEASE_API_URL;
    private String apiNewUrl = BuildConfig.RELEASE_API_URL_NEW;
    private String apiH5Url = BuildConfig.RELEASE_H5_URL;

    public static void clearNodeList() {
        nodeList.clear();
    }

    private void copyEncryptedFile() {
        AsyncHandler.post(new Runnable() { // from class: com.psychiatrygarden.l
            @Override // java.lang.Runnable
            public final void run() {
                this.f16189c.lambda$copyEncryptedFile$2();
            }
        });
    }

    private String getAppName(int pID) {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningAppProcesses();
        if (runningAppProcesses == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            try {
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            if (runningAppProcessInfo.pid == pID) {
                return runningAppProcessInfo.processName;
            }
            continue;
        }
        return null;
    }

    public static NextChapterInfo getChapterInfo() {
        return mNextChapterInfo;
    }

    public static Pair<String, String> getNextNode() {
        List<NextNodeData> list = nodeList;
        if (list.isEmpty()) {
            return null;
        }
        return new Pair<>(list.get(0).getId(), list.get(0).getNodeTitle());
    }

    private void initHx() {
        EMPushConfig.Builder builder = new EMPushConfig.Builder(getApplicationContext());
        builder.enableVivoPush().enableMeiZuPush(SdkConstant.MEIZU_APP_ID, SdkConstant.MEIZU_APP_KEY).enableMiPush(SdkConstant.MI_APP_ID, SdkConstant.MI_APP_KEY).enableOppoPush(SdkConstant.OPPO_APP_KEY, SdkConstant.OPPO_APP_SECRET).enableHWPush();
        EMOptions eMOptions = new EMOptions();
        eMOptions.setPushConfig(builder.build());
        eMOptions.setAcceptInvitationAlways(false);
        eMOptions.setAutoTransferMessageAttachments(true);
        eMOptions.setAutoDownloadThumbnail(true);
        String appName = getAppName(Process.myPid());
        if (appName == null || !appName.equalsIgnoreCase(getApplicationContext().getPackageName())) {
            return;
        }
        if (EaseIM.getInstance().init(getApplicationContext(), eMOptions)) {
            EMClient.getInstance().setDebugMode(true);
        }
        try {
            PushClient.getInstance(getApplicationContext()).initialize();
            PushClient.getInstance(getApplicationContext()).turnOnPush(new IPushActionListener() { // from class: com.psychiatrygarden.k
                @Override // com.vivo.push.IPushActionListener
                public final void onStateChanged(int i2) {
                    ProjectApp.lambda$initHx$1(i2);
                }
            });
            VUpsManager.getInstance().registerToken(this, "100165857", "7e7047127778a98f5b01e351651039e4", "e2c097aa-c047-4355-9e27-41dde0547857", new UPSRegisterCallback() { // from class: com.psychiatrygarden.ProjectApp.3
                @Override // com.vivo.push.ups.ICallbackResult
                public void onResult(TokenResult tokenResult) {
                    tokenResult.getReturnCode();
                }
            });
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void initPlatforms() {
        platforms.clear();
        platforms.add(SHARE_MEDIA.WEIXIN.toSnsPlatform());
        platforms.add(SHARE_MEDIA.WEIXIN_CIRCLE.toSnsPlatform());
        platforms.add(SHARE_MEDIA.QQ.toSnsPlatform());
        platforms.add(SHARE_MEDIA.QZONE.toSnsPlatform());
    }

    public static ProjectApp instance() {
        return instance;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$copyEncryptedFile$2() {
        if (!Storage.moveEncryptedToStorage(getAssets())) {
            Log.e("saveFile", "onFailed: 复制失败");
        } else {
            PrivateService.initService(getApplicationContext(), Storage.getEncryptedFile().getAbsolutePath());
            Log.e("saveFile", "onSuccess: 复制成功");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$initHx$1(int i2) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ RefreshHeader lambda$onCreate$0(Context context, RefreshLayout refreshLayout) {
        return new CustomRefreshHeaderView(context);
    }

    public static void removeNode() {
        List<NextNodeData> list = nodeList;
        if (list.isEmpty()) {
            return;
        }
        list.remove(0);
    }

    public static void saveNChapterInfo(NextChapterInfo chapterInfo) {
        mNextChapterInfo = chapterInfo;
    }

    public static void setNodeList(List<NextNodeData> l2) {
        if (l2 == null || l2.size() <= 0) {
            return;
        }
        List<NextNodeData> list = nodeList;
        list.clear();
        list.addAll(l2);
    }

    public void addActivity(Activity activity) {
        this.activityList.add(activity);
    }

    public void addCouponPopupView(BasePopupView view) {
        List<BasePopupView> list = couponPopList;
        if (list.contains(view)) {
            return;
        }
        list.add(view);
    }

    public void closeAllActivityWithoutHome() {
        for (Activity activity : this.activityList) {
            if (!activity.getClass().getSimpleName().equals("HomePageNewActivity")) {
                activity.finish();
            }
        }
    }

    public void copynomediaFile() {
        try {
            File noMediaFile = Storage.getNoMediaFile();
            if (noMediaFile.exists() || !noMediaFile.createNewFile()) {
                return;
            }
            LogUtils.d("copynomediaFile", "success");
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @TargetApi(26)
    public void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);
        notificationChannel.setShowBadge(true);
        ((NotificationManager) getSystemService(RemoteMessageConst.NOTIFICATION)).createNotificationChannel(notificationChannel);
    }

    public void dismissAllPop() {
        Iterator<BasePopupView> it = couponPopList.iterator();
        while (it.hasNext()) {
            it.next().dismiss();
        }
        couponPopList.clear();
    }

    public void dismissPop(BasePopupView p2) {
        for (BasePopupView basePopupView : couponPopList) {
            if (p2 == basePopupView) {
                basePopupView.dismiss();
            }
        }
        couponPopList.remove(p2);
    }

    public void exit() {
        Iterator<Activity> it = this.activityList.iterator();
        while (it.hasNext()) {
            it.next().finish();
        }
        Process.killProcess(Process.myPid());
    }

    public void finishall() throws Throwable {
        LogUtils.i(LOG_TAG, "activity 个数" + this.activityList.size());
        Iterator<Activity> it = this.activityList.iterator();
        while (it.hasNext()) {
            it.next().finish();
        }
    }

    public String getCurrentEnvH5Url() {
        return this.apiH5Url;
    }

    public String getCurrentEnvNewUrl() {
        return this.apiNewUrl;
    }

    public String getCurrentEnvUrl() {
        return this.apiUrl;
    }

    public Activity getCurrentLastActivity() {
        if (this.activityList.size() < 2) {
            return null;
        }
        List<Activity> list = this.activityList;
        return list.get(list.size() - 2);
    }

    public Activity getCurrentResumeAct() {
        List<Activity> list = ((ActivityLifecycleCallbacks) this.activityLifecycleCallbacks).resumeActivity;
        if (list == null || list.size() <= 0) {
            return null;
        }
        return list.get(list.size() - 1);
    }

    public Activity getResumeActivity() {
        List<Activity> list = ((ActivityLifecycleCallbacks) this.activityLifecycleCallbacks).resumeActivity;
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(list.size() - 1);
    }

    public String getStoragePath() {
        return (Build.VERSION.SDK_INT >= 29 ? getExternalFilesDir(null) : Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)).getPath();
    }

    public Activity getTopActivity() {
        if (this.activityList.size() <= 0) {
            return new HomePageNewActivity();
        }
        Activity activity = this.activityList.get(r0.size() - 1);
        return activity instanceof AliPlayerVideoPlayActivity ? this.activityList.get(0) : activity;
    }

    public String getTopActivityName() {
        if (this.activityList.size() <= 0) {
            return "";
        }
        return this.activityList.get(r0.size() - 1).getClass().getSimpleName();
    }

    public int getUnreadMessageCount() {
        Iterator<EMConversation> it = EMClient.getInstance().chatManager().getConversationsByType(EMConversation.EMConversationType.Chat).iterator();
        int unreadMsgCount = 0;
        while (it.hasNext()) {
            unreadMsgCount += it.next().getUnreadMsgCount();
        }
        return unreadMsgCount;
    }

    public void hideDialogWindow() {
        if (LoadDialogFragment.getInstence() != null) {
            try {
                LoadDialogFragment.getInstence().dismissAllowingStateLoss();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void initSdkPload() {
        copyEncryptedFile();
        copynomediaFile();
        LogUtils.SetLogType(0);
        PlatformConfig.setWeixin(SdkConstant.WEIXIN_KEY, SdkConstant.WEIXIN_SECRET);
        PlatformConfig.setWXFileProvider(getPackageName() + ".fileprovider");
        PlatformConfig.setQQZone(SdkConstant.QZONE_KEY, SdkConstant.QZONE_SECRET);
        PlatformConfig.setQQFileProvider(getPackageName() + ".fileprovider");
        PlatformConfig.setSinaWeibo("1692801888", "edd9ad699bb0829b5f4abde3094c29d4", "http://open.weibo.com/apps/1692801888/privilege/oauth");
        PlatformConfig.setSinaFileProvider("com.tencent.sample2.fileprovider");
        CrashHandler.getInstance().init(this);
        HashMap map = new HashMap();
        Boolean bool = Boolean.TRUE;
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, bool);
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE, bool);
        QbSdk.initTbsSettings(map);
        QbSdk.setDownloadWithoutWifi(true);
        QbSdk.initX5Environment(this, new QbSdk.PreInitCallback() { // from class: com.psychiatrygarden.ProjectApp.1
            @Override // com.tencent.smtt.sdk.QbSdk.PreInitCallback
            public void onCoreInitFinished() {
            }

            @Override // com.tencent.smtt.sdk.QbSdk.PreInitCallback
            public void onViewInitFinished(boolean isX5) {
                Log.d(ProjectApp.TAG, "onViewInitFinished: " + isX5);
            }
        });
        LogUtils.e("sbsdk_version", "version=====>" + QbSdk.getTbsSdkVersion());
        QbSdk.setTbsListener(new TbsListener() { // from class: com.psychiatrygarden.ProjectApp.2
            @Override // com.tencent.smtt.sdk.TbsListener
            public void onDownloadFinish(int i2) {
            }

            @Override // com.tencent.smtt.sdk.TbsListener
            public void onDownloadProgress(int i2) {
            }

            @Override // com.tencent.smtt.sdk.TbsListener
            public void onInstallFinish(int i2) {
                Log.e(ProjectApp.TAG, "onInstallFinish: 内核下载成功");
            }
        });
        boolean zNeedDownload = TbsDownloader.needDownload(this, TbsDownloader.DOWNLOAD_OVERSEA_TBS);
        Log.e(TAG, "onCreate: " + zNeedDownload);
        if (zNeedDownload) {
            TbsDownloader.startDownload(this);
        }
        initPlatforms();
        PLVLiveSDKConfig.init(new PLVLiveSDKConfig.Parameter(this).isOpenDebugLog(false).setEnableBugly(false).isEnableHttpDns(false));
        DatabaseManager.getInstance().createDataBase(this);
        downloadManager = AliyunDownloadManager.getInstance(this);
        MyDownloadInfoListener myDownloadInfoListener = new MyDownloadInfoListener();
        downloadInfoListener = myDownloadInfoListener;
        downloadManager.addDownloadInfoListener(myDownloadInfoListener);
        downloadManager.setRefreshStsCallback(new MyRefreshStsCallback());
        YkBManager.getInstance().getCaCheVideoData();
        registerActivityLifecycleCallbacks(this.activityLifecycleCallbacks);
        CrashReport.initCrashReport(getApplicationContext(), SdkConstant.BUGLY_APP_ID, false);
        UMConfigure.init(this, SdkConstant.UMENG_KEY, SdkConstant.UMENG_ALIS, 1, "");
        MiPushClient.getRegId(getApplicationContext());
        mNotificationinit();
        ImageLoader.getInstance().init(new ImageLoaderConfiguration.Builder(this).defaultDisplayImageOptions(new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.imgplacehodel_image).showImageOnFail(R.drawable.imgplacehodel_image).cacheInMemory(true).cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565).build()).build());
        ComDBSelectUtils instanceManger = ComDBSelectUtils.getInstanceManger();
        mComDb = instanceManger;
        instanceManger.initContext(this);
        mDaoSession = mComDb.getDaoSession();
        mTiKuDaoSession = mComDb.getTiKuDaoSession();
        mDaoSessionBei = mComDb.getDaoSessionBei();
        downloadManager.setMaxNum(4);
        DownloaderConfig downloaderConfig = new DownloaderConfig();
        downloaderConfig.mConnectTimeoutS = 3;
        downloaderConfig.mNetworkTimeoutMs = C.DEFAULT_SEEK_FORWARD_INCREMENT_MS;
        downloadManager.setDownloaderConfig(downloaderConfig);
        if (Build.VERSION.SDK_INT >= 29) {
            downloadManager.setDownloadDir(getExternalFilesDir(null) + File.separator + SdkConstant.UMENG_ALIS + "/VideoDownload2/");
        } else {
            File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            if (externalStoragePublicDirectory != null) {
                downloadManager.setDownloadDir(externalStoragePublicDirectory.getAbsolutePath() + File.separator + SdkConstant.UMENG_ALIS + "/VideoDownload2/");
            } else if (TextUtils.equals(Environment.getExternalStorageState(), "mounted")) {
                downloadManager.setDownloadDir(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + SdkConstant.UMENG_ALIS + "/VideoDownload2/");
            } else {
                downloadManager.setDownloadDir(getExternalFilesDir(null).getAbsolutePath() + File.separator + SdkConstant.UMENG_ALIS + "/VideoDownload2/");
            }
        }
        VODSVideoUploadClientImpl vODSVideoUploadClientImpl = new VODSVideoUploadClientImpl(this);
        vodsVideoUploadClient = vODSVideoUploadClientImpl;
        vODSVideoUploadClientImpl.init();
        database = DbManager.getInstance(this);
        BeiZis.init(this, "20826");
        initHx();
    }

    public boolean isConnectUrl() {
        return TextUtils.equals(this.apiUrl, BuildConfig.RELEASE_API_URL);
    }

    public void mNotificationinit() {
        if (Build.VERSION.SDK_INT >= 26) {
            createNotificationChannel("ykbnotid", String.format(Locale.CHINA, "%s推送通知", getResources().getString(R.string.app_name)), 4);
        }
    }

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        GlobalApplication.INSTANCE.setInstance(this);
        KotlinMvpApp.INSTANCE.onCreate(this);
        if (CommonUtil.isMainProcess(this)) {
            instance = this;
            UMConfigure.setLogEnabled(false);
            UMConfigure.preInit(this, SdkConstant.UMENG_KEY, SdkConstant.UMENG_ALIS);
            SkinManager.setThemeID(R.style.DayTheme, R.style.NightTheme);
            if (SharePreferencesUtils.readBooleanConfig(CommonParameter.agree, false, this)) {
                initSdkPload();
            }
        } else if (Build.VERSION.SDK_INT >= 28) {
            if (CommonUtil.getProcessName() == null || "".equals(CommonUtil.getProcessName())) {
                WebView.setDataDirectorySuffix(getPackageName() + ":ykb_web");
            } else {
                WebView.setDataDirectorySuffix(CommonUtil.getProcessName());
            }
        }
        ZXingLibrary.initDisplayOpinion(this);
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.COURSE_VIDEO, false, this);
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() { // from class: com.psychiatrygarden.j
            @Override // com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator
            public final RefreshHeader createRefreshHeader(Context context, RefreshLayout refreshLayout) {
                return ProjectApp.lambda$onCreate$0(context, refreshLayout);
            }
        });
        CommonConfig commonConfig = CommonConfig.INSTANCE;
        commonConfig.setYI_KAO_BANG(true);
        commonConfig.setFLAVOR(BuildConfig.FLAVOR);
        commonConfig.setApplicationId(BuildConfig.APPLICATION_ID);
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.IS_SHOW_HOME_AD_POP, false, this);
    }

    @Override // android.app.Application, android.content.ComponentCallbacks2
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level >= 40) {
            ResourceBundle.clearCache();
        }
    }

    public void removeActivity(Activity activity) {
        this.activityList.remove(activity);
    }

    public void removeAllActivity() {
        Iterator<Activity> it = this.activityList.iterator();
        while (it.hasNext()) {
            it.next().finish();
        }
    }

    public void return2Home() {
        for (Activity activity : this.activityList) {
            if (!(activity instanceof HomePageNewActivity) && activity != null) {
                activity.finish();
            }
        }
    }

    public void setApiUrl(int position) {
        if (position == 0) {
            this.apiUrl = BuildConfig.TEST_API_URL;
            this.apiNewUrl = BuildConfig.TEST_API_URL_NEW;
            CommonConfig.INSTANCE.setYI_KAO_BANG_NETWORK(0);
            this.apiH5Url = BuildConfig.TEST_H5_URL;
            return;
        }
        if (position == 1) {
            this.apiUrl = BuildConfig.PREPUB_API_URL_2;
            this.apiNewUrl = BuildConfig.PREPUB_API_URL;
            CommonConfig.INSTANCE.setYI_KAO_BANG_NETWORK(1);
            this.apiH5Url = BuildConfig.PRE_H5_URL;
            return;
        }
        if (position != 2) {
            return;
        }
        this.apiUrl = BuildConfig.RELEASE_API_URL;
        this.apiNewUrl = BuildConfig.RELEASE_API_URL_NEW;
        CommonConfig.INSTANCE.setYI_KAO_BANG_NETWORK(2);
        this.apiH5Url = BuildConfig.RELEASE_H5_URL;
    }

    public void showDialogWindow() {
        Activity activityCurrent = ((ActivityLifecycleCallbacks) this.activityLifecycleCallbacks).current();
        if (activityCurrent == null || !(activityCurrent instanceof FragmentActivity) || LoadDialogFragment.getInstence().isVisible()) {
            return;
        }
        LoadDialogFragment.getInstence().show(((FragmentActivity) activityCurrent).getSupportFragmentManager(), "loadingfragment");
    }

    public int topActivityIsScreenShot() {
        if (this.activityList.size() <= 0) {
            return 2;
        }
        List<Activity> list = this.activityList;
        String simpleName = list.get(list.size() - 1).getClass().getSimpleName();
        if (simpleName.equals("ScreenShotDialogActivity")) {
            return 1;
        }
        return (simpleName.equals("ComputerMockTestAct") || simpleName.equals("ComputerStatisticsAct")) ? 0 : 2;
    }

    public void removeNode(String id) {
        ListIterator<NextNodeData> listIterator = nodeList.listIterator();
        while (listIterator.hasNext()) {
            if (TextUtils.equals(listIterator.next().getId(), id)) {
                listIterator.remove();
            }
        }
    }
}
