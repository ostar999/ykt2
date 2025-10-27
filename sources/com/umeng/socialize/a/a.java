package com.umeng.socialize.a;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.util.SparseArray;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.SocializeException;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.UmengErrorCode;
import com.umeng.socialize.common.QueuedWork;
import com.umeng.socialize.handler.UMMoreHandler;
import com.umeng.socialize.handler.UMSSOHandler;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.media.UMediaObject;
import com.umeng.socialize.media.UMusic;
import com.umeng.socialize.net.analytics.SocialAnalytics;
import com.umeng.socialize.net.dplus.CommonNetImpl;
import com.umeng.socialize.net.dplus.DplusApi;
import com.umeng.socialize.net.dplus.cache.DplusCacheApi;
import com.umeng.socialize.net.utils.SocializeNetUtils;
import com.umeng.socialize.utils.ContextUtil;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.SocializeUtils;
import com.umeng.socialize.utils.UmengText;
import com.umeng.socialize.utils.UrlUtil;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes6.dex */
public final class a {

    /* renamed from: b, reason: collision with root package name */
    private static final String f23605b = "umeng_share_platform";

    /* renamed from: c, reason: collision with root package name */
    private static final String f23606c = "share_action";

    /* renamed from: a, reason: collision with root package name */
    private SHARE_MEDIA f23607a;

    /* renamed from: d, reason: collision with root package name */
    private String f23608d = "7.1.7";

    /* renamed from: e, reason: collision with root package name */
    private final Map<SHARE_MEDIA, UMSSOHandler> f23609e;

    /* renamed from: f, reason: collision with root package name */
    private final List<Pair<SHARE_MEDIA, String>> f23610f;

    /* renamed from: g, reason: collision with root package name */
    private C0391a f23611g;

    /* renamed from: h, reason: collision with root package name */
    private Context f23612h;

    /* renamed from: i, reason: collision with root package name */
    private SparseArray<UMAuthListener> f23613i;

    /* renamed from: j, reason: collision with root package name */
    private SparseArray<UMShareListener> f23614j;

    /* renamed from: k, reason: collision with root package name */
    private SparseArray<UMAuthListener> f23615k;

    public a(Context context) {
        HashMap map = new HashMap();
        this.f23609e = map;
        ArrayList arrayList = new ArrayList();
        this.f23610f = arrayList;
        arrayList.add(new Pair(SHARE_MEDIA.LAIWANG, "com.umeng.socialize.handler.UMLWHandler"));
        arrayList.add(new Pair(SHARE_MEDIA.LAIWANG_DYNAMIC, "com.umeng.socialize.handler.UMLWHandler"));
        arrayList.add(new Pair(SHARE_MEDIA.SINA, "com.umeng.socialize.handler.SinaSimplyHandler"));
        arrayList.add(new Pair(SHARE_MEDIA.PINTEREST, "com.umeng.socialize.handler.UMPinterestHandler"));
        arrayList.add(new Pair(SHARE_MEDIA.QZONE, "com.umeng.qq.handler.UmengQZoneHandler"));
        arrayList.add(new Pair(SHARE_MEDIA.QQ, "com.umeng.qq.handler.UmengQQHandler"));
        arrayList.add(new Pair(SHARE_MEDIA.RENREN, "com.umeng.socialize.handler.RenrenSsoHandler"));
        arrayList.add(new Pair(SHARE_MEDIA.TENCENT, "com.umeng.socialize.handler.TencentWBSsoHandler"));
        arrayList.add(new Pair(SHARE_MEDIA.WEIXIN, "com.umeng.weixin.handler.UmengWXHandler"));
        arrayList.add(new Pair(SHARE_MEDIA.WEIXIN_CIRCLE, "com.umeng.weixin.handler.UmengWXHandler"));
        arrayList.add(new Pair(SHARE_MEDIA.WEIXIN_FAVORITE, "com.umeng.weixin.handler.UmengWXHandler"));
        arrayList.add(new Pair(SHARE_MEDIA.WXWORK, "com.umeng.socialize.handler.UMWXWorkHandler"));
        arrayList.add(new Pair(SHARE_MEDIA.YIXIN, "com.umeng.socialize.handler.UMYXHandler"));
        arrayList.add(new Pair(SHARE_MEDIA.YIXIN_CIRCLE, "com.umeng.socialize.handler.UMYXHandler"));
        arrayList.add(new Pair(SHARE_MEDIA.EMAIL, "com.umeng.socialize.handler.EmailHandler"));
        arrayList.add(new Pair(SHARE_MEDIA.EVERNOTE, "com.umeng.socialize.handler.UMEvernoteHandler"));
        arrayList.add(new Pair(SHARE_MEDIA.FACEBOOK, "com.umeng.socialize.handler.UMFacebookHandler"));
        arrayList.add(new Pair(SHARE_MEDIA.FACEBOOK_MESSAGER, "com.umeng.socialize.handler.UMFacebookHandler"));
        arrayList.add(new Pair(SHARE_MEDIA.FLICKR, "com.umeng.socialize.handler.UMFlickrHandler"));
        arrayList.add(new Pair(SHARE_MEDIA.FOURSQUARE, "com.umeng.socialize.handler.UMFourSquareHandler"));
        arrayList.add(new Pair(SHARE_MEDIA.GOOGLEPLUS, "com.umeng.socialize.handler.UMGooglePlusHandler"));
        arrayList.add(new Pair(SHARE_MEDIA.INSTAGRAM, "com.umeng.socialize.handler.UMInstagramHandler"));
        arrayList.add(new Pair(SHARE_MEDIA.KAKAO, "com.umeng.socialize.handler.UMKakaoHandler"));
        arrayList.add(new Pair(SHARE_MEDIA.LINE, "com.umeng.socialize.handler.UMLineHandler"));
        arrayList.add(new Pair(SHARE_MEDIA.LINKEDIN, "com.umeng.socialize.handler.UMLinkedInHandler"));
        arrayList.add(new Pair(SHARE_MEDIA.POCKET, "com.umeng.socialize.handler.UMPocketHandler"));
        arrayList.add(new Pair(SHARE_MEDIA.WHATSAPP, "com.umeng.socialize.handler.UMWhatsAppHandler"));
        arrayList.add(new Pair(SHARE_MEDIA.YNOTE, "com.umeng.socialize.handler.UMYNoteHandler"));
        arrayList.add(new Pair(SHARE_MEDIA.SMS, "com.umeng.socialize.handler.SmsHandler"));
        arrayList.add(new Pair(SHARE_MEDIA.DOUBAN, "com.umeng.socialize.handler.DoubanHandler"));
        arrayList.add(new Pair(SHARE_MEDIA.TUMBLR, "com.umeng.socialize.handler.UMTumblrHandler"));
        arrayList.add(new Pair(SHARE_MEDIA.TWITTER, "com.umeng.socialize.handler.TwitterHandler"));
        arrayList.add(new Pair(SHARE_MEDIA.ALIPAY, "com.umeng.socialize.handler.AlipayHandler"));
        arrayList.add(new Pair(SHARE_MEDIA.MORE, "com.umeng.socialize.handler.UMMoreHandler"));
        arrayList.add(new Pair(SHARE_MEDIA.DINGTALK, "com.umeng.socialize.handler.UMDingSSoHandler"));
        arrayList.add(new Pair(SHARE_MEDIA.VKONTAKTE, "com.umeng.socialize.handler.UMVKHandler"));
        arrayList.add(new Pair(SHARE_MEDIA.DROPBOX, "com.umeng.socialize.handler.UMDropBoxHandler"));
        this.f23611g = new C0391a(map);
        this.f23612h = null;
        this.f23613i = new SparseArray<>();
        this.f23614j = new SparseArray<>();
        this.f23615k = new SparseArray<>();
        this.f23612h = context;
        b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized UMShareListener e(int i2) {
        UMShareListener uMShareListener;
        uMShareListener = this.f23614j.get(i2, null);
        if (uMShareListener != null) {
            this.f23614j.remove(i2);
        }
        return uMShareListener;
    }

    public boolean d(Activity activity, SHARE_MEDIA share_media) {
        if (!this.f23611g.a(activity, share_media)) {
            return false;
        }
        this.f23609e.get(share_media).onCreate(activity, PlatformConfig.getPlatform(share_media));
        return this.f23609e.get(share_media).isAuthorize();
    }

    private void b(Context context) throws PackageManager.NameNotFoundException {
        String appkey = SocializeUtils.getAppkey(context);
        if (TextUtils.isEmpty(appkey)) {
            throw new SocializeException(UmengText.errorWithUrl(UmengText.CHECK.APPKEY_NOT_FOUND, UrlUtil.ALL_NO_APPKEY));
        }
        if (SocializeNetUtils.isConSpeCharacters(appkey)) {
            throw new SocializeException(UmengText.errorWithUrl(UmengText.CHECK.APPKEY_NOT_FOUND, UrlUtil.ALL_ERROR_APPKEY));
        }
        if (SocializeNetUtils.isSelfAppkey(appkey)) {
            throw new SocializeException(UmengText.errorWithUrl(UmengText.CHECK.APPKEY_NOT_FOUND, UrlUtil.ALL_ERROR_APPKEY));
        }
    }

    public String c(Activity activity, SHARE_MEDIA share_media) {
        if (!this.f23611g.a(activity, share_media)) {
            return "";
        }
        this.f23609e.get(share_media).onCreate(activity, PlatformConfig.getPlatform(share_media));
        return this.f23609e.get(share_media).getSDKVersion();
    }

    public void a(Context context) {
        this.f23612h = context.getApplicationContext();
    }

    private UMSSOHandler a(String str) {
        UMSSOHandler uMSSOHandler;
        try {
            uMSSOHandler = (UMSSOHandler) Class.forName(str).newInstance();
        } catch (Exception unused) {
            uMSSOHandler = null;
        }
        if (uMSSOHandler == null) {
            if (str.contains("SinaSimplyHandler")) {
                Config.isUmengSina = Boolean.FALSE;
                return a("com.umeng.socialize.handler.SinaSsoHandler");
            }
            if (str.contains("UmengQQHandler")) {
                Config.isUmengQQ = Boolean.FALSE;
                return a("com.umeng.socialize.handler.UMQQSsoHandler");
            }
            if (str.contains("UmengQZoneHandler")) {
                Config.isUmengQQ = Boolean.FALSE;
                return a("com.umeng.socialize.handler.QZoneSsoHandler");
            }
            if (str.contains("UmengWXHandler")) {
                Config.isUmengWx = Boolean.FALSE;
                return a("com.umeng.socialize.handler.UMWXHandler");
            }
        }
        return uMSSOHandler;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized UMAuthListener d(int i2) {
        UMAuthListener uMAuthListener;
        uMAuthListener = this.f23615k.get(i2, null);
        if (uMAuthListener != null) {
            this.f23615k.remove(i2);
        }
        return uMAuthListener;
    }

    /* renamed from: com.umeng.socialize.a.a$a, reason: collision with other inner class name */
    public static class C0391a {

        /* renamed from: a, reason: collision with root package name */
        private Map<SHARE_MEDIA, UMSSOHandler> f23639a;

        public C0391a(Map<SHARE_MEDIA, UMSSOHandler> map) {
            this.f23639a = map;
        }

        private boolean a(Context context) {
            return context != null;
        }

        public boolean a(Context context, SHARE_MEDIA share_media) {
            if (!a(context) || !a(share_media)) {
                return false;
            }
            if (this.f23639a.get(share_media).isSupportAuth()) {
                return true;
            }
            SLog.E(share_media.toString() + UmengText.AUTH.NOT_SUPPORT_PLATFROM);
            return false;
        }

        public boolean a(ShareAction shareAction) {
            SHARE_MEDIA platform = shareAction.getPlatform();
            if (platform == null) {
                return false;
            }
            if ((platform != SHARE_MEDIA.SINA && platform != SHARE_MEDIA.QQ) || PlatformConfig.configs.get(platform).isConfigured()) {
                return a(platform);
            }
            SLog.E(UmengText.CHECK.noKey(platform));
            return false;
        }

        private boolean a(SHARE_MEDIA share_media) {
            PlatformConfig.configs.get(share_media);
            if (this.f23639a.get(share_media) != null) {
                return true;
            }
            SLog.mutlE(UmengText.CHECK.noJar(share_media), UrlUtil.ALL_NO_JAR);
            return false;
        }
    }

    public void c(Activity activity, final SHARE_MEDIA share_media, final UMAuthListener uMAuthListener) {
        if (this.f23611g.a(activity, share_media)) {
            UMSSOHandler uMSSOHandler = this.f23609e.get(share_media);
            uMSSOHandler.onCreate(activity, PlatformConfig.getPlatform(share_media));
            String strValueOf = String.valueOf(System.currentTimeMillis());
            if (ContextUtil.getContext() != null) {
                SocialAnalytics.authstart(ContextUtil.getContext(), share_media, uMSSOHandler.getSDKVersion(), uMSSOHandler.isInstall(), strValueOf);
            }
            int iOrdinal = share_media.ordinal();
            a(iOrdinal, uMAuthListener);
            UMAuthListener uMAuthListenerA = a(iOrdinal, strValueOf, uMSSOHandler.isInstall());
            QueuedWork.runInMain(new Runnable() { // from class: com.umeng.socialize.a.a.4
                @Override // java.lang.Runnable
                public void run() {
                    uMAuthListener.onStart(share_media);
                }
            });
            uMSSOHandler.authorize(uMAuthListenerA);
            this.f23607a = share_media;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void b() {
        UMSSOHandler uMSSOHandlerA;
        for (Pair<SHARE_MEDIA, String> pair : this.f23610f) {
            Object obj = pair.first;
            if (obj != SHARE_MEDIA.WEIXIN_CIRCLE && obj != SHARE_MEDIA.WEIXIN_FAVORITE) {
                if (obj == SHARE_MEDIA.FACEBOOK_MESSAGER) {
                    uMSSOHandlerA = this.f23609e.get(SHARE_MEDIA.FACEBOOK);
                } else if (obj == SHARE_MEDIA.YIXIN_CIRCLE) {
                    uMSSOHandlerA = this.f23609e.get(SHARE_MEDIA.YIXIN);
                } else if (obj == SHARE_MEDIA.LAIWANG_DYNAMIC) {
                    uMSSOHandlerA = this.f23609e.get(SHARE_MEDIA.LAIWANG);
                } else if (obj == SHARE_MEDIA.TENCENT) {
                    uMSSOHandlerA = a((String) pair.second);
                } else if (obj == SHARE_MEDIA.MORE) {
                    uMSSOHandlerA = new UMMoreHandler();
                } else if (obj == SHARE_MEDIA.SINA) {
                    if (Config.isUmengSina.booleanValue()) {
                        uMSSOHandlerA = a((String) pair.second);
                    } else {
                        uMSSOHandlerA = a("com.umeng.socialize.handler.SinaSsoHandler");
                    }
                } else if (obj == SHARE_MEDIA.WEIXIN) {
                    if (Config.isUmengWx.booleanValue()) {
                        uMSSOHandlerA = a((String) pair.second);
                    } else {
                        uMSSOHandlerA = a("com.umeng.socialize.handler.UMWXHandler");
                    }
                } else if (obj == SHARE_MEDIA.QQ) {
                    if (Config.isUmengQQ.booleanValue()) {
                        uMSSOHandlerA = a((String) pair.second);
                    } else {
                        uMSSOHandlerA = a("com.umeng.socialize.handler.UMQQSsoHandler");
                    }
                } else if (obj == SHARE_MEDIA.QZONE) {
                    if (Config.isUmengQQ.booleanValue()) {
                        uMSSOHandlerA = a((String) pair.second);
                    } else {
                        uMSSOHandlerA = a("com.umeng.socialize.handler.QZoneSsoHandler");
                    }
                } else {
                    uMSSOHandlerA = a((String) pair.second);
                }
            } else {
                uMSSOHandlerA = this.f23609e.get(SHARE_MEDIA.WEIXIN);
            }
            this.f23609e.put(pair.first, uMSSOHandlerA);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized UMAuthListener c(int i2) {
        UMAuthListener uMAuthListener;
        this.f23607a = null;
        uMAuthListener = this.f23613i.get(i2, null);
        if (uMAuthListener != null) {
            this.f23613i.remove(i2);
        }
        return uMAuthListener;
    }

    public UMSSOHandler a(SHARE_MEDIA share_media) {
        UMSSOHandler uMSSOHandler = this.f23609e.get(share_media);
        if (uMSSOHandler != null) {
            uMSSOHandler.onCreate(this.f23612h, PlatformConfig.getPlatform(share_media));
        }
        return uMSSOHandler;
    }

    private synchronized void c() {
        this.f23613i.clear();
        this.f23614j.clear();
        this.f23615k.clear();
    }

    public void a(int i2, int i3, Intent intent) {
        UMSSOHandler uMSSOHandlerA = a(i2);
        if (uMSSOHandlerA != null) {
            uMSSOHandlerA.onActivityResult(i2, i3, intent);
        }
    }

    @Deprecated
    public void a(Activity activity, int i2, UMAuthListener uMAuthListener) {
        UMSSOHandler uMSSOHandlerA = a(i2);
        if (uMSSOHandlerA != null) {
            if (i2 == 10103 || i2 == 11101) {
                uMSSOHandlerA.onCreate(activity, PlatformConfig.getPlatform(b(i2)));
                a(SHARE_MEDIA.QQ, uMAuthListener, uMSSOHandlerA, String.valueOf(System.currentTimeMillis()));
            }
        }
    }

    private UMSSOHandler a(int i2) {
        int i3 = 10103;
        if (i2 != 10103 && i2 != 11101) {
            i3 = i2;
        }
        if (i2 == 64207 || i2 == 64206 || i2 == 64208) {
            i3 = 64206;
        }
        if (i2 == 32973 || i2 == 765) {
            i3 = 10001;
        }
        if (i2 == 5650) {
            i3 = 5659;
        }
        for (UMSSOHandler uMSSOHandler : this.f23609e.values()) {
            if (uMSSOHandler != null && i3 == uMSSOHandler.getRequestCode()) {
                return uMSSOHandler;
            }
        }
        return null;
    }

    public void a(Activity activity, SHARE_MEDIA share_media, UMAuthListener uMAuthListener) {
        if (this.f23611g.a(activity, share_media)) {
            if (uMAuthListener == null) {
                uMAuthListener = new UMAuthListener() { // from class: com.umeng.socialize.a.a.1
                    @Override // com.umeng.socialize.UMAuthListener
                    public void onCancel(SHARE_MEDIA share_media2, int i2) {
                    }

                    @Override // com.umeng.socialize.UMAuthListener
                    public void onComplete(SHARE_MEDIA share_media2, int i2, Map<String, String> map) {
                    }

                    @Override // com.umeng.socialize.UMAuthListener
                    public void onError(SHARE_MEDIA share_media2, int i2, Throwable th) {
                    }

                    @Override // com.umeng.socialize.UMAuthListener
                    public void onStart(SHARE_MEDIA share_media2) {
                    }
                };
            }
            this.f23609e.get(share_media).onCreate(activity, PlatformConfig.getPlatform(share_media));
            this.f23609e.get(share_media).deleteAuth(uMAuthListener);
        }
    }

    public boolean a(Activity activity, SHARE_MEDIA share_media) {
        this.f23609e.get(share_media).onCreate(activity, PlatformConfig.getPlatform(share_media));
        return this.f23609e.get(share_media).isInstall();
    }

    private UMAuthListener a(final int i2, final String str, final boolean z2) {
        return new UMAuthListener() { // from class: com.umeng.socialize.a.a.5
            @Override // com.umeng.socialize.UMAuthListener
            public void onCancel(SHARE_MEDIA share_media, int i3) {
                UMAuthListener uMAuthListenerC = a.this.c(i2);
                if (uMAuthListenerC != null) {
                    uMAuthListenerC.onCancel(share_media, i3);
                }
                if (ContextUtil.getContext() != null) {
                    SocialAnalytics.authendt(ContextUtil.getContext(), share_media, "cancel", z2, "", str, null);
                }
            }

            @Override // com.umeng.socialize.UMAuthListener
            public void onComplete(SHARE_MEDIA share_media, int i3, Map<String, String> map) {
                UMAuthListener uMAuthListenerC = a.this.c(i2);
                if (uMAuthListenerC != null) {
                    uMAuthListenerC.onComplete(share_media, i3, map);
                }
                if (ContextUtil.getContext() != null) {
                    SocialAnalytics.authendt(ContextUtil.getContext(), share_media, "success", z2, "", str, a.this.a(share_media, map));
                }
            }

            @Override // com.umeng.socialize.UMAuthListener
            public void onError(SHARE_MEDIA share_media, int i3, Throwable th) {
                UMAuthListener uMAuthListenerC = a.this.c(i2);
                if (uMAuthListenerC != null) {
                    uMAuthListenerC.onError(share_media, i3, th);
                }
                if (th != null) {
                    SLog.E(th.getMessage());
                    SLog.runtimePrint(th.getMessage());
                } else {
                    SLog.E("null");
                    SLog.runtimePrint("null");
                }
                if (ContextUtil.getContext() == null || th == null) {
                    return;
                }
                SocialAnalytics.authendt(ContextUtil.getContext(), share_media, "fail", z2, th.getMessage(), str, null);
            }

            @Override // com.umeng.socialize.UMAuthListener
            public void onStart(SHARE_MEDIA share_media) {
                UMAuthListener uMAuthListenerC = a.this.c(i2);
                if (uMAuthListenerC != null) {
                    uMAuthListenerC.onStart(share_media);
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Map<String, String> a(SHARE_MEDIA share_media, Map<String, String> map) {
        String appid;
        String appSecret;
        if (PlatformConfig.getPlatform(share_media) != null) {
            appid = PlatformConfig.getPlatform(share_media).getAppid();
            appSecret = PlatformConfig.getPlatform(share_media).getAppSecret();
        } else {
            appid = "";
            appSecret = "";
        }
        map.put(CommonNetImpl.AID, appid);
        map.put("as", appSecret);
        return map;
    }

    private SHARE_MEDIA b(int i2) {
        if (i2 == 10103 || i2 == 11101) {
            return SHARE_MEDIA.QQ;
        }
        if (i2 != 32973 && i2 != 765) {
            return SHARE_MEDIA.QQ;
        }
        return SHARE_MEDIA.SINA;
    }

    private void a(ShareAction shareAction) {
        ShareContent shareContent = shareAction.getShareContent();
        ArrayList arrayList = new ArrayList();
        arrayList.add(UmengText.SHARE.INFO);
        arrayList.add(UmengText.SHARE.SHAREPLAT + shareAction.getPlatform().toString());
        arrayList.add(UmengText.SHARE.SHARESTYLE + shareAction.getShareContent().getShareType());
        arrayList.add(UmengText.SHARE.SHARETEXT + shareContent.mText);
        UMediaObject uMediaObject = shareContent.mMedia;
        if (uMediaObject != null) {
            if (uMediaObject instanceof UMImage) {
                UMImage uMImage = (UMImage) uMediaObject;
                if (uMImage.isUrlMedia()) {
                    arrayList.add(UmengText.SHARE.URLIMAGE + uMImage.asUrlImage());
                } else {
                    byte[] bArrAsBinImage = uMImage.asBinImage();
                    StringBuilder sb = new StringBuilder();
                    sb.append(UmengText.SHARE.LOCALIMAGE);
                    sb.append(bArrAsBinImage == null ? 0 : bArrAsBinImage.length);
                    arrayList.add(sb.toString());
                }
                if (uMImage.getThumbImage() != null) {
                    UMImage thumbImage = uMImage.getThumbImage();
                    if (thumbImage.isUrlMedia()) {
                        arrayList.add(UmengText.SHARE.URLTHUMB + thumbImage.asUrlImage());
                    } else {
                        arrayList.add(UmengText.SHARE.LOCALTHUMB + thumbImage.asBinImage().length);
                    }
                }
            }
            UMediaObject uMediaObject2 = shareContent.mMedia;
            if (uMediaObject2 instanceof UMVideo) {
                UMVideo uMVideo = (UMVideo) uMediaObject2;
                arrayList.add(UmengText.SHARE.VIDEOURL + uMVideo.toUrl());
                arrayList.add(UmengText.SHARE.VIDEOTITLE + uMVideo.getTitle());
                arrayList.add(UmengText.SHARE.VIDEODES + uMVideo.getDescription());
                if (uMVideo.getThumbImage() != null) {
                    if (uMVideo.getThumbImage().isUrlMedia()) {
                        arrayList.add(UmengText.SHARE.URLTHUMB + uMVideo.getThumbImage().asUrlImage());
                    } else {
                        arrayList.add(UmengText.SHARE.LOCALTHUMB + uMVideo.getThumbImage().asBinImage().length);
                    }
                }
            }
            UMediaObject uMediaObject3 = shareContent.mMedia;
            if (uMediaObject3 instanceof UMusic) {
                UMusic uMusic = (UMusic) uMediaObject3;
                arrayList.add(UmengText.SHARE.MUSICURL + uMusic.toUrl() + "   " + uMusic.getmTargetUrl());
                StringBuilder sb2 = new StringBuilder();
                sb2.append(UmengText.SHARE.MUSICTITLE);
                sb2.append(uMusic.getTitle());
                arrayList.add(sb2.toString());
                arrayList.add(UmengText.SHARE.MUSICDES + uMusic.getDescription());
                if (uMusic.getThumbImage() != null) {
                    if (uMusic.getThumbImage().isUrlMedia()) {
                        arrayList.add(UmengText.SHARE.URLTHUMB + uMusic.getThumbImage().asUrlImage());
                    } else {
                        arrayList.add(UmengText.SHARE.LOCALTHUMB + uMusic.getThumbImage().asBinImage().length);
                    }
                }
            }
            UMediaObject uMediaObject4 = shareContent.mMedia;
            if (uMediaObject4 instanceof UMWeb) {
                UMWeb uMWeb = (UMWeb) uMediaObject4;
                arrayList.add(UmengText.SHARE.URLURL + uMWeb.toUrl());
                arrayList.add(UmengText.SHARE.URLTITLE + uMWeb.getTitle());
                arrayList.add(UmengText.SHARE.URLDES + uMWeb.getDescription());
                if (uMWeb.getThumbImage() != null) {
                    if (uMWeb.getThumbImage().isUrlMedia()) {
                        arrayList.add(UmengText.SHARE.URLTHUMB + uMWeb.getThumbImage().asUrlImage());
                    } else {
                        arrayList.add(UmengText.SHARE.LOCALTHUMB + uMWeb.getThumbImage().asBinImage().length);
                    }
                }
            }
        }
        if (shareContent.file != null) {
            arrayList.add(UmengText.SHARE.FILENAME + shareContent.file.getName());
        }
        SLog.mutlI((String[]) arrayList.toArray(new String[1]));
    }

    public void b(Activity activity, final SHARE_MEDIA share_media, final UMAuthListener uMAuthListener) {
        if (this.f23611g.a(activity, share_media)) {
            UMSSOHandler uMSSOHandler = this.f23609e.get(share_media);
            uMSSOHandler.onCreate(activity, PlatformConfig.getPlatform(share_media));
            final String strValueOf = String.valueOf(System.currentTimeMillis());
            if (ContextUtil.getContext() != null) {
                SocialAnalytics.getInfostart(ContextUtil.getContext(), share_media, strValueOf);
            }
            final int iOrdinal = share_media.ordinal();
            b(iOrdinal, uMAuthListener);
            UMAuthListener uMAuthListener2 = new UMAuthListener() { // from class: com.umeng.socialize.a.a.2
                @Override // com.umeng.socialize.UMAuthListener
                public void onCancel(SHARE_MEDIA share_media2, int i2) {
                    UMAuthListener uMAuthListenerD = a.this.d(iOrdinal);
                    if (uMAuthListenerD != null) {
                        uMAuthListenerD.onCancel(share_media2, i2);
                    }
                    if (ContextUtil.getContext() != null) {
                        SocialAnalytics.getInfoendt(ContextUtil.getContext(), share_media2, "cancel", "", strValueOf, null);
                    }
                }

                @Override // com.umeng.socialize.UMAuthListener
                public void onComplete(SHARE_MEDIA share_media2, int i2, Map<String, String> map) {
                    UMAuthListener uMAuthListenerD = a.this.d(iOrdinal);
                    if (uMAuthListenerD != null) {
                        uMAuthListenerD.onComplete(share_media2, i2, map);
                    }
                    if (ContextUtil.getContext() != null) {
                        SocialAnalytics.getInfoendt(ContextUtil.getContext(), share_media2, "success", "", strValueOf, map);
                    }
                }

                @Override // com.umeng.socialize.UMAuthListener
                public void onError(SHARE_MEDIA share_media2, int i2, Throwable th) {
                    UMAuthListener uMAuthListenerD = a.this.d(iOrdinal);
                    if (uMAuthListenerD != null) {
                        uMAuthListenerD.onError(share_media2, i2, th);
                    }
                    if (th != null) {
                        SLog.E(th.getMessage());
                        SLog.E(UmengText.SOLVE + UrlUtil.ALL_AUTHFAIL);
                        SLog.runtimePrint(th.getMessage());
                    } else {
                        SLog.E(UmengText.SOLVE + UrlUtil.ALL_AUTHFAIL);
                    }
                    if (ContextUtil.getContext() == null || th == null) {
                        return;
                    }
                    SocialAnalytics.getInfoendt(ContextUtil.getContext(), share_media2, "fail", th.getMessage(), strValueOf, null);
                }

                @Override // com.umeng.socialize.UMAuthListener
                public void onStart(SHARE_MEDIA share_media2) {
                    UMAuthListener uMAuthListenerD = a.this.d(iOrdinal);
                    if (uMAuthListenerD != null) {
                        uMAuthListenerD.onStart(share_media2);
                    }
                }
            };
            QueuedWork.runInMain(new Runnable() { // from class: com.umeng.socialize.a.a.3
                @Override // java.lang.Runnable
                public void run() {
                    uMAuthListener.onStart(share_media);
                }
            });
            uMSSOHandler.getPlatformInfo(uMAuthListener2);
        }
    }

    public boolean b(Activity activity, SHARE_MEDIA share_media) {
        if (!this.f23611g.a(activity, share_media)) {
            return false;
        }
        this.f23609e.get(share_media).onCreate(activity, PlatformConfig.getPlatform(share_media));
        return this.f23609e.get(share_media).isSupport();
    }

    private synchronized void b(int i2, UMAuthListener uMAuthListener) {
        this.f23615k.put(i2, uMAuthListener);
    }

    public void a(Activity activity, final ShareAction shareAction, final UMShareListener uMShareListener) throws PackageManager.NameNotFoundException {
        b(activity);
        WeakReference weakReference = new WeakReference(activity);
        if (this.f23611g.a(shareAction)) {
            if (SLog.isDebug()) {
                SLog.E(UmengText.SHARE.VERSION + this.f23608d);
                a(shareAction);
            }
            SHARE_MEDIA platform = shareAction.getPlatform();
            UMSSOHandler uMSSOHandler = this.f23609e.get(platform);
            uMSSOHandler.onCreate((Context) weakReference.get(), PlatformConfig.getPlatform(platform));
            if (!platform.toString().equals("TENCENT") && !platform.toString().equals("RENREN") && !platform.toString().equals("DOUBAN")) {
                if (platform.toString().equals("WEIXIN")) {
                    SocialAnalytics.log((Context) weakReference.get(), "wxsession", shareAction.getShareContent().mText, shareAction.getShareContent().mMedia);
                } else if (platform.toString().equals("WEIXIN_CIRCLE")) {
                    SocialAnalytics.log((Context) weakReference.get(), "wxtimeline", shareAction.getShareContent().mText, shareAction.getShareContent().mMedia);
                } else if (platform.toString().equals("WEIXIN_FAVORITE")) {
                    SocialAnalytics.log((Context) weakReference.get(), "wxfavorite", shareAction.getShareContent().mText, shareAction.getShareContent().mMedia);
                } else {
                    SocialAnalytics.log((Context) weakReference.get(), platform.toString().toLowerCase(), shareAction.getShareContent().mText, shareAction.getShareContent().mMedia);
                }
            }
            final String strValueOf = String.valueOf(System.currentTimeMillis());
            if (ContextUtil.getContext() != null) {
                DplusApi.uploadShare(ContextUtil.getContext(), shareAction.getShareContent(), uMSSOHandler.isInstall(), platform, strValueOf, shareAction.getShareContent().mMedia instanceof UMImage ? ((UMImage) shareAction.getShareContent().mMedia).isHasWaterMark() : false);
            }
            final int iOrdinal = platform.ordinal();
            a(iOrdinal, uMShareListener);
            final UMShareListener uMShareListener2 = new UMShareListener() { // from class: com.umeng.socialize.a.a.6
                @Override // com.umeng.socialize.UMShareListener
                public void onCancel(SHARE_MEDIA share_media) {
                    if (ContextUtil.getContext() != null) {
                        SocialAnalytics.shareend(ContextUtil.getContext(), share_media, "cancel", "", strValueOf);
                    }
                    UMShareListener uMShareListenerE = a.this.e(iOrdinal);
                    if (uMShareListenerE != null) {
                        uMShareListenerE.onCancel(share_media);
                    }
                }

                @Override // com.umeng.socialize.UMShareListener
                public void onError(SHARE_MEDIA share_media, Throwable th) {
                    if (ContextUtil.getContext() != null && th != null) {
                        SocialAnalytics.shareend(ContextUtil.getContext(), share_media, "fail", th.getMessage(), strValueOf);
                    }
                    UMShareListener uMShareListenerE = a.this.e(iOrdinal);
                    if (uMShareListenerE != null) {
                        uMShareListenerE.onError(share_media, th);
                    }
                    if (th != null) {
                        SLog.E(th.getMessage());
                        SLog.E(UmengText.SOLVE + UrlUtil.ALL_SHAREFAIL);
                        SLog.runtimePrint(th.getMessage());
                        return;
                    }
                    SLog.E("null");
                    SLog.E(UmengText.SOLVE + UrlUtil.ALL_SHAREFAIL);
                    SLog.runtimePrint("null");
                }

                @Override // com.umeng.socialize.UMShareListener
                public void onResult(SHARE_MEDIA share_media) {
                    if (ContextUtil.getContext() != null) {
                        SocialAnalytics.shareend(ContextUtil.getContext(), share_media, "success", "", strValueOf);
                    }
                    UMShareListener uMShareListenerE = a.this.e(iOrdinal);
                    if (uMShareListenerE != null) {
                        uMShareListenerE.onResult(share_media);
                    }
                }

                @Override // com.umeng.socialize.UMShareListener
                public void onStart(SHARE_MEDIA share_media) {
                    UMShareListener uMShareListenerE = a.this.e(iOrdinal);
                    if (uMShareListenerE != null) {
                        uMShareListenerE.onStart(share_media);
                    }
                }
            };
            if (!shareAction.getUrlValid()) {
                QueuedWork.runInMain(new Runnable() { // from class: com.umeng.socialize.a.a.7
                    @Override // java.lang.Runnable
                    public void run() {
                        uMShareListener2.onError(shareAction.getPlatform(), new Throwable(UmengErrorCode.ShareFailed.getMessage() + UmengText.SHARE.WEB_HTTP));
                    }
                });
                return;
            }
            QueuedWork.runInMain(new Runnable() { // from class: com.umeng.socialize.a.a.8
                @Override // java.lang.Runnable
                public void run() {
                    UMShareListener uMShareListener3 = uMShareListener;
                    if (uMShareListener3 != null) {
                        uMShareListener3.onStart(shareAction.getPlatform());
                    }
                }
            });
            try {
                uMSSOHandler.share(shareAction.getShareContent(), uMShareListener2);
            } catch (Throwable th) {
                SLog.error(th);
            }
        }
    }

    private synchronized void a(int i2, UMAuthListener uMAuthListener) {
        this.f23613i.put(i2, uMAuthListener);
    }

    private synchronized void a(int i2, UMShareListener uMShareListener) {
        this.f23614j.put(i2, uMShareListener);
    }

    private void a(SHARE_MEDIA share_media, UMAuthListener uMAuthListener, UMSSOHandler uMSSOHandler, String str) {
        if (uMSSOHandler.isHasAuthListener()) {
            return;
        }
        int iOrdinal = share_media.ordinal();
        a(iOrdinal, uMAuthListener);
        uMSSOHandler.setAuthListener(a(iOrdinal, str, uMSSOHandler.isInstall()));
    }

    public void a() {
        c();
        com.umeng.socialize.b.b.a.b();
        UMSSOHandler uMSSOHandler = this.f23609e.get(SHARE_MEDIA.SINA);
        if (uMSSOHandler != null) {
            uMSSOHandler.release();
        }
        UMSSOHandler uMSSOHandler2 = this.f23609e.get(SHARE_MEDIA.MORE);
        if (uMSSOHandler2 != null) {
            uMSSOHandler2.release();
        }
        UMSSOHandler uMSSOHandler3 = this.f23609e.get(SHARE_MEDIA.DINGTALK);
        if (uMSSOHandler3 != null) {
            uMSSOHandler3.release();
        }
        UMSSOHandler uMSSOHandler4 = this.f23609e.get(SHARE_MEDIA.WEIXIN);
        if (uMSSOHandler4 != null) {
            uMSSOHandler4.release();
        }
        UMSSOHandler uMSSOHandler5 = this.f23609e.get(SHARE_MEDIA.QQ);
        if (uMSSOHandler5 != null) {
            uMSSOHandler5.release();
        }
        this.f23607a = null;
        DplusCacheApi.getInstance().closeDBConnection(ContextUtil.getContext());
    }

    public void a(Bundle bundle) {
        String string;
        int i2;
        SHARE_MEDIA share_media = this.f23607a;
        if (share_media == null || !(share_media == SHARE_MEDIA.WEIXIN || share_media == SHARE_MEDIA.QQ || share_media == SHARE_MEDIA.SINA)) {
            string = "";
            i2 = -1;
        } else {
            string = share_media.toString();
            i2 = 0;
        }
        bundle.putString(f23605b, string);
        bundle.putInt(f23606c, i2);
        this.f23607a = null;
    }

    public void a(Activity activity, Bundle bundle, UMAuthListener uMAuthListener) {
        SHARE_MEDIA share_mediaConvertToEmun;
        UMSSOHandler uMSSOHandlerA;
        if (bundle == null || uMAuthListener == null) {
            return;
        }
        String string = bundle.getString(f23605b, null);
        if (bundle.getInt(f23606c, -1) != 0 || TextUtils.isEmpty(string) || (share_mediaConvertToEmun = SHARE_MEDIA.convertToEmun(string)) == null) {
            return;
        }
        if (share_mediaConvertToEmun == SHARE_MEDIA.QQ) {
            uMSSOHandlerA = this.f23609e.get(share_mediaConvertToEmun);
            uMSSOHandlerA.onCreate(activity, PlatformConfig.getPlatform(share_mediaConvertToEmun));
        } else {
            uMSSOHandlerA = a(share_mediaConvertToEmun);
        }
        if (uMSSOHandlerA != null) {
            a(share_mediaConvertToEmun, uMAuthListener, uMSSOHandlerA, String.valueOf(System.currentTimeMillis()));
        }
    }

    public void a(UMShareConfig uMShareConfig) {
        Map<SHARE_MEDIA, UMSSOHandler> map = this.f23609e;
        if (map == null || map.isEmpty()) {
            return;
        }
        Iterator<Map.Entry<SHARE_MEDIA, UMSSOHandler>> it = this.f23609e.entrySet().iterator();
        while (it.hasNext()) {
            UMSSOHandler value = it.next().getValue();
            if (value != null) {
                value.setShareConfig(uMShareConfig);
            }
        }
    }
}
