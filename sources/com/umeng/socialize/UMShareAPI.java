package com.umeng.socialize;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.os.Process;
import android.text.TextUtils;
import android.widget.Toast;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.framework.UMFrUtils;
import com.umeng.commonsdk.framework.UMWorkDispatch;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.common.QueuedWork;
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.handler.UMSSOHandler;
import com.umeng.socialize.net.ActionBarRequest;
import com.umeng.socialize.net.RestAPI;
import com.umeng.socialize.net.analytics.SocialAnalytics;
import com.umeng.socialize.net.dplus.CommonNetImpl;
import com.umeng.socialize.net.dplus.DplusApi;
import com.umeng.socialize.uploadlog.UMLog;
import com.umeng.socialize.utils.ContextUtil;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.SocializeSpUtils;
import com.umeng.socialize.utils.SocializeUtils;
import com.umeng.socialize.utils.UmengText;
import com.umeng.socialize.utils.UrlUtil;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class UMShareAPI {

    /* renamed from: a, reason: collision with root package name */
    private static UMShareAPI f23582a;

    /* renamed from: b, reason: collision with root package name */
    private com.umeng.socialize.a.a f23583b;

    /* renamed from: c, reason: collision with root package name */
    private UMShareConfig f23584c = new UMShareConfig();
    public boolean isZyb = true;

    public static class a extends QueuedWork.UMAsyncTask<Void> {

        /* renamed from: a, reason: collision with root package name */
        private Context f23602a;

        /* renamed from: b, reason: collision with root package name */
        private boolean f23603b;

        /* renamed from: c, reason: collision with root package name */
        private boolean f23604c;

        public a(Context context) {
            this.f23603b = false;
            this.f23604c = false;
            this.f23602a = context;
            this.f23603b = SocializeUtils.isToday(SocializeSpUtils.getTime(context));
            this.f23604c = SocializeUtils.isHasDplusCache();
        }

        private boolean c() {
            return this.f23602a.getSharedPreferences(SocializeConstants.SOCIAL_PREFERENCE_NAME, 0).getBoolean("newinstall", false);
        }

        @Override // com.umeng.socialize.common.QueuedWork.UMAsyncTask
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public Void doInBackground() {
            boolean zC = c();
            SLog.E(UmengText.CHECK.SDKVERSION + "7.1.7");
            if (!this.f23603b) {
                RestAPI.queryShareId(new ActionBarRequest(this.f23602a, zC));
            }
            if (!this.f23603b) {
                SocializeSpUtils.putTime(this.f23602a);
                DplusApi.uploadDAU(ContextUtil.getContext());
                SocialAnalytics.dauStats(this.f23602a, true);
            } else if (this.f23604c) {
                DplusApi.uploadDAU(ContextUtil.getContext());
                SocialAnalytics.dauStats(this.f23602a, true);
            }
            SocialAnalytics.verifyStats(this.f23602a);
            return null;
        }

        public void b() {
            SharedPreferences.Editor editorEdit = this.f23602a.getSharedPreferences(SocializeConstants.SOCIAL_PREFERENCE_NAME, 0).edit();
            editorEdit.putBoolean("newinstall", true);
            editorEdit.commit();
        }
    }

    private UMShareAPI(Context context) {
        ContextUtil.setContext(context.getApplicationContext());
        this.f23583b = new com.umeng.socialize.a.a(context.getApplicationContext());
        String currentProcessName = UMFrUtils.getCurrentProcessName(context);
        if (TextUtils.isEmpty(currentProcessName) || !currentProcessName.equals(ContextUtil.getPackageName())) {
            return;
        }
        new a(context.getApplicationContext()).execute();
    }

    private static boolean b(final Context context) {
        if (a()) {
            return true;
        }
        new Thread() { // from class: com.umeng.socialize.UMShareAPI.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                try {
                    Looper.prepare();
                    Toast.makeText(context.getApplicationContext(), UmengText.CHECK.DEPENDENT_COMMON_NOT_MATCH, 1).show();
                    Looper.loop();
                } catch (Throwable unused) {
                }
            }
        }.start();
        SLog.E(UmengText.CHECK.DEPENDENT_COMMON_NOT_MATCH);
        return false;
    }

    public static UMShareAPI get(Context context) {
        if (!b(context)) {
            return null;
        }
        UMShareAPI uMShareAPI = f23582a;
        if (uMShareAPI == null || uMShareAPI.f23583b == null) {
            f23582a = new UMShareAPI(context);
            SLog.welcome();
        }
        f23582a.f23583b.a(context);
        return f23582a;
    }

    public static void init(Context context, String str) {
        if (b(context)) {
            SocializeConstants.APPKEY = str;
            get(context);
            UMWorkDispatch.sendEvent(context, 24592, CommonNetImpl.get(context), null);
        }
    }

    public void deleteOauth(final Activity activity, final SHARE_MEDIA share_media, final UMAuthListener uMAuthListener) {
        if (activity == null) {
            SLog.E(UmengText.CHECK.ACTIVITYNULL);
        } else {
            f23582a.f23583b.a(activity);
            new QueuedWork.DialogThread<Void>(activity) { // from class: com.umeng.socialize.UMShareAPI.3
                @Override // com.umeng.socialize.common.QueuedWork.UMAsyncTask
                public Object doInBackground() {
                    if (UMShareAPI.this.f23583b == null) {
                        return null;
                    }
                    UMShareAPI.this.f23583b.a(activity, share_media, uMAuthListener);
                    return null;
                }
            }.execute();
        }
    }

    @Deprecated
    public void doOauthVerify(final Activity activity, final SHARE_MEDIA share_media, final UMAuthListener uMAuthListener) {
        UMLog.putAuth();
        if (!UMConfigure.getInitStatus()) {
            SLog.selfLog(UmengText.CHECK.NOINT);
            return;
        }
        f23582a.f23583b.a(activity);
        if (!SLog.isDebug() || a(activity, share_media)) {
            if (activity != null) {
                new QueuedWork.DialogThread<Void>(activity) { // from class: com.umeng.socialize.UMShareAPI.2
                    @Override // com.umeng.socialize.common.QueuedWork.UMAsyncTask
                    /* renamed from: a, reason: merged with bridge method [inline-methods] */
                    public Void doInBackground() {
                        if (UMShareAPI.this.f23583b == null) {
                            UMShareAPI.this.f23583b = new com.umeng.socialize.a.a(activity);
                        }
                        UMShareAPI.this.f23583b.c(activity, share_media, uMAuthListener);
                        return null;
                    }
                }.execute();
            } else {
                SLog.E(UmengText.CHECK.ACTIVITYNULL);
            }
        }
    }

    public void doShare(Activity activity, final ShareAction shareAction, final UMShareListener uMShareListener) {
        UMLog.putShare();
        if (!UMConfigure.getInitStatus()) {
            SLog.selfLog(UmengText.CHECK.NOINT);
            return;
        }
        final WeakReference weakReference = new WeakReference(activity);
        if (SLog.isDebug()) {
            if (!a(activity, shareAction.getPlatform())) {
                return;
            } else {
                UrlUtil.sharePrint(shareAction.getPlatform());
            }
        }
        if (weakReference.get() == null || ((Activity) weakReference.get()).isFinishing()) {
            SLog.E(UmengText.CHECK.ACTIVITYNULL);
        } else {
            f23582a.f23583b.a(activity);
            new QueuedWork.DialogThread<Void>((Context) weakReference.get()) { // from class: com.umeng.socialize.UMShareAPI.5
                @Override // com.umeng.socialize.common.QueuedWork.UMAsyncTask
                /* renamed from: a, reason: merged with bridge method [inline-methods] */
                public Void doInBackground() throws PackageManager.NameNotFoundException {
                    if (weakReference.get() != null && !((Activity) weakReference.get()).isFinishing()) {
                        if (UMShareAPI.this.f23583b != null) {
                            UMShareAPI.this.f23583b.a((Activity) weakReference.get(), shareAction, uMShareListener);
                        } else {
                            UMShareAPI.this.f23583b = new com.umeng.socialize.a.a((Context) weakReference.get());
                            UMShareAPI.this.f23583b.a((Activity) weakReference.get(), shareAction, uMShareListener);
                        }
                    }
                    return null;
                }
            }.execute();
        }
    }

    public void fetchAuthResultWithBundle(Activity activity, Bundle bundle, UMAuthListener uMAuthListener) {
        this.f23583b.a(activity, bundle, uMAuthListener);
    }

    public UMSSOHandler getHandler(SHARE_MEDIA share_media) {
        com.umeng.socialize.a.a aVar = this.f23583b;
        if (aVar != null) {
            return aVar.a(share_media);
        }
        return null;
    }

    public void getPlatformInfo(final Activity activity, final SHARE_MEDIA share_media, final UMAuthListener uMAuthListener) {
        if (activity == null) {
            SLog.E(UmengText.CHECK.ACTIVITYNULL);
            return;
        }
        if (!UMConfigure.getInitStatus()) {
            SLog.selfLog(UmengText.CHECK.NOINT);
            return;
        }
        UMLog.putAuth();
        if (SLog.isDebug()) {
            if (!a(activity, share_media)) {
                return;
            } else {
                UrlUtil.getInfoPrint(share_media);
            }
        }
        f23582a.f23583b.a(activity);
        new QueuedWork.DialogThread<Void>(activity) { // from class: com.umeng.socialize.UMShareAPI.4
            @Override // com.umeng.socialize.common.QueuedWork.UMAsyncTask
            public Object doInBackground() {
                if (UMShareAPI.this.f23583b == null) {
                    return null;
                }
                UMShareAPI.this.f23583b.b(activity, share_media, uMAuthListener);
                return null;
            }
        }.execute();
    }

    public String getversion(Activity activity, SHARE_MEDIA share_media) {
        com.umeng.socialize.a.a aVar = this.f23583b;
        if (aVar != null) {
            return aVar.c(activity, share_media);
        }
        com.umeng.socialize.a.a aVar2 = new com.umeng.socialize.a.a(activity);
        this.f23583b = aVar2;
        return aVar2.c(activity, share_media);
    }

    public boolean isAuthorize(Activity activity, SHARE_MEDIA share_media) {
        com.umeng.socialize.a.a aVar = this.f23583b;
        if (aVar != null) {
            return aVar.d(activity, share_media);
        }
        com.umeng.socialize.a.a aVar2 = new com.umeng.socialize.a.a(activity);
        this.f23583b = aVar2;
        return aVar2.d(activity, share_media);
    }

    public boolean isInstall(Activity activity, SHARE_MEDIA share_media) {
        com.umeng.socialize.a.a aVar = this.f23583b;
        if (aVar != null) {
            return aVar.a(activity, share_media);
        }
        com.umeng.socialize.a.a aVar2 = new com.umeng.socialize.a.a(activity);
        this.f23583b = aVar2;
        return aVar2.a(activity, share_media);
    }

    public boolean isSupport(Activity activity, SHARE_MEDIA share_media) {
        com.umeng.socialize.a.a aVar = this.f23583b;
        if (aVar != null) {
            return aVar.b(activity, share_media);
        }
        com.umeng.socialize.a.a aVar2 = new com.umeng.socialize.a.a(activity);
        this.f23583b = aVar2;
        return aVar2.b(activity, share_media);
    }

    public void onActivityResult(int i2, int i3, Intent intent) {
        com.umeng.socialize.a.a aVar = this.f23583b;
        if (aVar != null) {
            aVar.a(i2, i3, intent);
        } else {
            SLog.E(UmengText.CHECK.ROUTERNULL);
        }
        SLog.I(UmengText.CHECK.getActivityResult(i2, i3));
    }

    public void onSaveInstanceState(Bundle bundle) {
        this.f23583b.a(bundle);
    }

    public void release() {
        this.f23583b.a();
    }

    public void setShareConfig(UMShareConfig uMShareConfig) {
        this.f23583b.a(uMShareConfig);
    }

    private String a(Context context) {
        int iMyPid = Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME);
        if (activityManager == null || activityManager.getRunningAppProcesses() == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : activityManager.getRunningAppProcesses()) {
            if (runningAppProcessInfo.pid == iMyPid) {
                return runningAppProcessInfo.processName;
            }
        }
        return null;
    }

    private static Class<?> a(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException unused) {
            return null;
        }
    }

    private static boolean a() {
        try {
            Class<?> clsA = a("com.umeng.commonsdk.framework.UMEnvelopeBuild");
            if (clsA != null) {
                return clsA.getDeclaredMethod("buildEnvelopeWithExtHeader", Context.class, JSONObject.class, JSONObject.class, String.class, String.class, String.class) != null;
            }
            return false;
        } catch (Throwable unused) {
            return false;
        }
    }

    private boolean a(Activity activity, SHARE_MEDIA share_media) throws SecurityException {
        boolean z2 = false;
        for (Method method : activity.getClass().getDeclaredMethods()) {
            if (method.getName().equals("onActivityResult")) {
                z2 = true;
            }
        }
        if (!z2) {
            SLog.mutlE(UmengText.CHECK.ALL_NO_ONACTIVITY, UrlUtil.ALL_NO_ONACTIVITY);
        }
        if (share_media == SHARE_MEDIA.QQ) {
            SLog.E(UmengTool.checkQQByself(activity));
            return true;
        }
        if (share_media == SHARE_MEDIA.WEIXIN) {
            SLog.E(UmengTool.checkWxBySelf(activity));
            return true;
        }
        if (share_media == SHARE_MEDIA.SINA) {
            SLog.E(UmengTool.checkSinaBySelf(activity));
            return true;
        }
        if (share_media == SHARE_MEDIA.FACEBOOK) {
            SLog.E(UmengTool.checkFBByself(activity));
            return true;
        }
        if (share_media == SHARE_MEDIA.VKONTAKTE) {
            SLog.E(UmengTool.checkVKByself(activity));
        }
        if (share_media == SHARE_MEDIA.LINKEDIN) {
            SLog.E(UmengTool.checkLinkin(activity));
        }
        if (share_media == SHARE_MEDIA.KAKAO) {
            SLog.E(UmengTool.checkKakao(activity));
        }
        return true;
    }
}
