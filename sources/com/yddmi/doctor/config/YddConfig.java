package com.yddmi.doctor.config;

import com.catchpig.utils.ext.LogExtKt;
import com.catchpig.utils.manager.ContextManager;
import com.github.gzuliyujiang.oaid.DeviceIdentifier;
import com.yddmi.doctor.config.YddUserManager;
import com.yddmi.doctor.entity.result.AppConfig;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import k.a;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.TypeIntrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b7\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010L\u001a\u00020\u00042\b\b\u0002\u0010M\u001a\u00020NJ\u000e\u0010O\u001a\u00020P2\u0006\u0010Q\u001a\u00020\u0004J\u0014\u0010R\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00010<J\u0006\u0010S\u001a\u00020\u0004J\u0006\u0010T\u001a\u00020\u0004J\u0006\u0010U\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u00100\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u00101\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u00102\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u00103\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u00104\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u00105\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u00106\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u00107\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u00108\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u00109\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010:\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u001c\u0010;\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00010<X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010=\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010>\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010?\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010@\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010A\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010B\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010C\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010D\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010E\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010F\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010G\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010H\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010I\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010J\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010K\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006V"}, d2 = {"Lcom/yddmi/doctor/config/YddConfig;", "", "()V", "ConfigCustomer", "", "ConfigJtjyZydyz", "ConfigNodeLogin", "ConfigWachat", "KV_0", "KV_AD_CANCEL_BUY", "KV_AD_FIRST_BUY", "KV_CONFIG", "KV_HEARTLUNG_CORRECTION", "KV_MSG_DETAILS", "KV_MSG_DETAILS_AD", "KV_START_AD", "KV_WX_TOKEN", "KV_WX_USERINFO", "SP_3DBODY_URL", "SP_AID", "SP_FIRST_GUIDE", "SP_FIRST_START", "SP_HOST_TYPE", "SP_LOGIN_SHARE_CODE", "SP_NOTICE", "SP_NOTICE_TIP", "SP_OAID", "SP_POINT_SAVE", "SP_PRIVATE", "SP_START_AD", "SP_U3D_VERSION", "SP_USER_AVATAR", "SP_USER_ID", "SP_USER_INTEGRAL", "SP_USER_NAME", "SP_USER_NICK_NAME", "SP_USER_ORGID", "SP_USER_PHONE", "SP_USER_PSW", "SP_USER_PUSH", "SP_USER_PUSH_ID", "SP_USER_PUSH_T0KEN", "SP_USER_STUDY_MODE", "SP_USER_TOKEN", "SP_USER_TOKEN_TIME", "SP_USER_WX_HEADIMAGEURL", "SP_USER_WX_NIKENAME", "SP_USER_WX_UNIONFLAG", "TAG", "aiuiAppId", "aiuiAppIdTest", "aiuiAppKey", "aiuiAppKeyTest", "aliOneLoginSecret", "buglyAppId", "flksaO8o", "flksaO8or", "floatingTag", "harmonyOs", "kvData", "Ljava/util/concurrent/ConcurrentHashMap;", "loginSecret", "platform", "pushMiAppId", "pushMiAppKey", "pushOppoAppKey", "pushOppoAppSecret", "shareCodeHost", "shareCodeHostMore", "wxAppGoProduct", "wxAppGoStartup", "wxAppId", "wxMerchant", "wxSecret", "yddCodeSecret", "yddCodeSecret1", "getAndroidInfo", "type", "", "getConfig", "", "key", "getKvData", "getWebAgreeUrl", "getWebPrivacyUrl", "getWebService", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nYddConfig.kt\nKotlin\n*S Kotlin\n*F\n+ 1 YddConfig.kt\ncom/yddmi/doctor/config/YddConfig\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,221:1\n1855#2,2:222\n*S KotlinDebug\n*F\n+ 1 YddConfig.kt\ncom/yddmi/doctor/config/YddConfig\n*L\n32#1:222,2\n*E\n"})
/* loaded from: classes6.dex */
public final class YddConfig {

    @NotNull
    public static final String ConfigCustomer = "sys_customer";

    @NotNull
    public static final String ConfigJtjyZydyz = "jtjnb_zydyz";

    @NotNull
    public static final String ConfigNodeLogin = "sys_node_login";

    @NotNull
    public static final String ConfigWachat = "sys_wathat_login";

    @NotNull
    public static final String KV_0 = "key0";

    @NotNull
    public static final String KV_AD_CANCEL_BUY = "kvAdCancelBuy";

    @NotNull
    public static final String KV_AD_FIRST_BUY = "kvAdFirstBuy";

    @NotNull
    public static final String KV_CONFIG = "kvYddConfig";

    @NotNull
    public static final String KV_HEARTLUNG_CORRECTION = "kvHeartlungCorrection";

    @NotNull
    public static final String KV_MSG_DETAILS = "kvMsgDetails";

    @NotNull
    public static final String KV_MSG_DETAILS_AD = "kvMsgDetailsAd";

    @NotNull
    public static final String KV_START_AD = "kvMsgStartAd";

    @NotNull
    public static final String KV_WX_TOKEN = "kvWxToken";

    @NotNull
    public static final String KV_WX_USERINFO = "kvWxUserInfo";

    @NotNull
    public static final String SP_3DBODY_URL = "sp_3dbody_url";

    @NotNull
    public static final String SP_AID = "sp_1aid";

    @NotNull
    public static final String SP_FIRST_GUIDE = "sp_guide_showed";

    @NotNull
    public static final String SP_FIRST_START = "sp_first_start";

    @NotNull
    public static final String SP_HOST_TYPE = "sp_host_type";

    @NotNull
    public static final String SP_LOGIN_SHARE_CODE = "sp_login_share_code";

    @NotNull
    public static final String SP_NOTICE = "sp_notice";

    @NotNull
    public static final String SP_NOTICE_TIP = "sp_notice_tip";

    @NotNull
    public static final String SP_OAID = "sp_0aid";

    @NotNull
    public static final String SP_POINT_SAVE = "sp_point_save";

    @NotNull
    public static final String SP_PRIVATE = "sp_private";

    @NotNull
    public static final String SP_START_AD = "sp_start_ad";

    @NotNull
    public static final String SP_U3D_VERSION = "sp_u3d_version";

    @NotNull
    public static final String SP_USER_AVATAR = "sp_user_avatar";

    @NotNull
    public static final String SP_USER_ID = "sp_user_id";

    @NotNull
    public static final String SP_USER_INTEGRAL = "sp_user_integral";

    @NotNull
    public static final String SP_USER_NAME = "sp_user_name";

    @NotNull
    public static final String SP_USER_NICK_NAME = "sp_user_nick_name";

    @NotNull
    public static final String SP_USER_ORGID = "sp_user_orgid";

    @NotNull
    public static final String SP_USER_PHONE = "sp_user_phone";

    @NotNull
    public static final String SP_USER_PSW = "sp_user_psw";

    @NotNull
    public static final String SP_USER_PUSH = "sp_user_push";

    @NotNull
    public static final String SP_USER_PUSH_ID = "sp_user_push_id";

    @NotNull
    public static final String SP_USER_PUSH_T0KEN = "sp_user_push_t0ken";

    @NotNull
    public static final String SP_USER_STUDY_MODE = "sp_user_studymode";

    @NotNull
    public static final String SP_USER_TOKEN = "sp_access_token";

    @NotNull
    public static final String SP_USER_TOKEN_TIME = "sp_token_time";

    @NotNull
    public static final String SP_USER_WX_HEADIMAGEURL = "sp_wx_headimageurl";

    @NotNull
    public static final String SP_USER_WX_NIKENAME = "sp_wx_nikename";

    @NotNull
    public static final String SP_USER_WX_UNIONFLAG = "sp_wx_unionflag";

    @NotNull
    public static final String TAG = "h0xta";

    @NotNull
    public static final String aiuiAppId = "76c30656";

    @NotNull
    public static final String aiuiAppIdTest = "dc01a8e9";

    @NotNull
    public static final String aiuiAppKey = "ea70b1e526620da44c32b157d05f9833";

    @NotNull
    public static final String aiuiAppKeyTest = "aef87f1e571bb224668109777271a7cb";

    @NotNull
    public static final String aliOneLoginSecret = "zOCG1fVfagYP9E5QMtxwavRjppwRTGan44zP52gqnBRwKiSyfYgUJUMZLIBib74VwJrae/nZcJBoVqvpCZ6v9bhaeCZmRTsSrH/RzZeBDqP6e6ek2QC0NA3FhTzJqOU/ceCe7j5yTJi8RPApsh3ZX8z23v7b+0w6qaDXfbWJjaissp5Vku5D2aRH9mAGp2IFGHSNd902x/JJx2clg8Bcqczc9JD0AY4Hf0ufTeqRMO9m6RtOSYC97c1BxVQEY23Ld+1tthyDVhPIZCKP1P4wYymCi0H1STNmBQGX1mA6ZEQ=";

    @NotNull
    public static final String buglyAppId = "f1fecb836e";

    @NotNull
    public static final String flksaO8o = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKxjEKbGHv43AdZo7T2X5HGRdxVBZmIhRRQt7YkjLnq4ps/IRUNhUF/LztLGvVAu+sfNvPIA8ns8a1Mxbh5NQkIm9oWWXvfG/UvK/2M4M5PSKzHlOAiGR/0BSJgeu7g/MQdGUUMTg0bjn02eb0CcL5dy/68T8fVHT7vxUYBoXg8hAgMBAAECgYBGp+YDzTGX1ZjxthkDBVYzPEGYWzFpKCTFpBt3eY2L7FIm2PmWp/THBuSTHLUsf8z3BvsaH8xDOZztwNVXIyh0A4oDLKF+Ez654fZWNB0RtXtzOZ9qZ6CpzPv8fYMRqvn8uPnn2hxa1AIlw5fl6zINvnBrmCZ/WPAn+JTXMx0UFwJBANKHUlHxAYqbyfM2fRDNMYEDma6KkfjtYqZTjUVeb0K+r99x7bqlfaSfBu94cUZnYfZ/G7WseIm/l2Ca6dQgbt8CQQDRnsr62UbQGA4wqbMl2UFZ2YAyRLldSW9G0RlNSVYZdxN76NKPzVxxpoQ5ODiyer3tjMdq7UiiQPXr4LWyN0H/AkEAwLghkmM1zwXHu72ghTXhYEHEeDPfZmkgSjPn+Z116jqW0sv/HEIMHxt4hNTTlOhkk/pNimREv4xEbanB7W53LwJBAK1us0Qupt8fOAZdMYLcKKy9PsrYf4FGc9HkyQlf5uO02KMJc8VUxOqnOfjZoldRJHk5o/i9D/5mvtGFHu+vtmMCQCaBHPXjDIFOCbMB8ZV3yySVmM/JTgYxviEa3o+YUHKRbYGNfOD7Jpb9v0KpJrRu1VmQqY1I0sHiPRsGW1dHGws=";

    @NotNull
    public static final String flksaO8or = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIi9MTX8H6HdhrlsehpSB7nlbLjeEmzyl+VwhEpyLmtM94pANQ3tde8G/DSjBhgI2NWHY3JZI8peSfzXFVS8017Bb2amQlNS60srUXEAUOsQ+3dVYzAWJFbGLv6kM+I2wWHkrrlNhM1ZkLQnCvTLXqRIThNdFNqaJTNFScFMRNvVAgMBAAECgYAoR+N5Tb5bxpBriyMlIckY4Bb5FqJC6ewJMO4nqB84iMHbPjLi2XMGaP5e2y9uYRzPOkT3aH78nG4bR5kHD2EMeJ4eDn7bbhVKZnQldU/YSthJf5zXmqT4WUtbkyiZlb04/sh3Jt7wJB136UgssfZrLWBkOb3IgZ3+bB6XUFnp+QJBAL0oC4mXU8s/nOGqcoxMIxBZoN0AWn2M67ww0sv1HJ8J5lPcx+0b0qZR0FP/CSsosRsPgIJD+vZEAGHF2IfY8QMCQQC5DzrohFvwyZAO2YQmI0+yjILM2Tss6WEJkoph76AkPKGRs9EVb6Yjv+/oXiItGRt2thRFlhn3VRCz3xfoZKxHAkBCyDqaj0TUQ1c+DRF4GwFeKmay4AcSwIgR3yLtQ1Khi7s37IDhY6tRcR0eKrKv/CPbK/PxCdM20vdXjaRPAfbFAkBm9wJfvMUrA/sTSBMu5RbC0Us1juhN5MwI92/nbdw8TT/YQaRjuOtHmNNzBm0dioqtUkGVNh7cbogMjGRQ+TKhAkEApVLl6vjhXhG2py4qvxhG69x5NLWZzEsjRTvzJnSvw/aY5dw5jnPrw95ORpekWa8NABQW6mSBeENDDzH75U2PYw==";

    @NotNull
    public static final String floatingTag = "lFSE5vuHDLX7tbih";

    @NotNull
    public static final String harmonyOs = "HarmonyOs";

    @NotNull
    public static final String loginSecret = "lskj2017lsk";

    @NotNull
    public static final String platform = "android";

    @NotNull
    public static final String pushMiAppId = "2882303761520276075";

    @NotNull
    public static final String pushMiAppKey = "5742027695075";

    @NotNull
    public static final String pushOppoAppKey = "96b4eb389dbb49339cef33d62d38b375";

    @NotNull
    public static final String pushOppoAppSecret = "ea07bf44caf843fc93bbd14b73d2569a";

    @NotNull
    public static final String shareCodeHost = "ydddoctor://shareCode=";

    @NotNull
    public static final String shareCodeHostMore = "ydddoctor://?shareCode=";

    @NotNull
    public static final String wxAppGoProduct = "ydddoctor://product?skillid=";

    @NotNull
    public static final String wxAppGoStartup = "ydddoctor://startup";

    @NotNull
    public static final String wxAppId = "wx35f164d814467bdc";

    @NotNull
    public static final String wxMerchant = "1665567215";

    @NotNull
    public static final String wxSecret = "765778627e1a063fcc0ba715d0f7f345";

    @NotNull
    public static final String yddCodeSecret = "lskjydd";

    @NotNull
    public static final String yddCodeSecret1 = "yikaobang2025";

    @NotNull
    public static final YddConfig INSTANCE = new YddConfig();

    @NotNull
    private static final ConcurrentHashMap<String, Object> kvData = new ConcurrentHashMap<>();

    private YddConfig() {
    }

    public static /* synthetic */ String getAndroidInfo$default(YddConfig yddConfig, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i2 = 99;
        }
        return yddConfig.getAndroidInfo(i2);
    }

    @NotNull
    public final String getAndroidInfo(int type) {
        boolean z2 = true;
        try {
            if (type == 100) {
                YddUserManager.Companion companion = YddUserManager.INSTANCE;
                String oaid = companion.getInstance().getOaid();
                if (oaid.length() != 0) {
                    z2 = false;
                }
                if (!z2) {
                    return oaid;
                }
                String oaid2 = DeviceIdentifier.getOAID(ContextManager.INSTANCE.getInstance().getContext());
                Intrinsics.checkNotNullExpressionValue(oaid2, "getOAID(\n               …                        )");
                companion.getInstance().setOaid(oaid2);
                return oaid2;
            }
            if (type != 101) {
                YddUserManager.Companion companion2 = YddUserManager.INSTANCE;
                String oaid3 = companion2.getInstance().getOaid();
                if (oaid3.length() != 0) {
                    z2 = false;
                }
                if (!z2) {
                    return oaid3;
                }
                String oaid4 = DeviceIdentifier.getOAID(ContextManager.INSTANCE.getInstance().getContext());
                Intrinsics.checkNotNullExpressionValue(oaid4, "getOAID(\n               …                        )");
                companion2.getInstance().setOaid(oaid4);
                return oaid4;
            }
            YddUserManager.Companion companion3 = YddUserManager.INSTANCE;
            String androidId = companion3.getInstance().getAndroidId();
            if (androidId.length() != 0) {
                z2 = false;
            }
            if (!z2 || !companion3.getInstance().userCanTpush()) {
                return androidId;
            }
            String androidID = DeviceIdentifier.getAndroidID(ContextManager.INSTANCE.getInstance().getContext());
            Intrinsics.checkNotNullExpressionValue(androidID, "getAndroidID(ContextMana…tInstance().getContext())");
            companion3.getInstance().setAndroidId(androidID);
            return androidID;
        } catch (Throwable th) {
            LogExtKt.loge("Android_CN_OAID 异常" + th, TAG);
            return "";
        }
    }

    public final boolean getConfig(@NotNull String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        Object obj = getKvData().get(KV_CONFIG);
        List<AppConfig> list = TypeIntrinsics.isMutableList(obj) ? (List) obj : null;
        if (list == null) {
            return false;
        }
        for (AppConfig appConfig : list) {
            if (Intrinsics.areEqual(appConfig.getConfigKey(), key)) {
                return Intrinsics.areEqual(appConfig.getConfigValue(), a.f27523u);
            }
        }
        return false;
    }

    @NotNull
    public final ConcurrentHashMap<String, Object> getKvData() {
        return kvData;
    }

    @NotNull
    public final String getWebAgreeUrl() {
        return YddHostConfig.INSTANCE.getInstance().getHostWebBaseUrl() + "clinical/#/agreement?category=1&port=2&application=2";
    }

    @NotNull
    public final String getWebPrivacyUrl() {
        return YddHostConfig.INSTANCE.getInstance().getHostWebBaseUrl() + "clinical/#/agreement?category=2&port=2&application=2";
    }

    @NotNull
    public final String getWebService() {
        return YddHostConfig.INSTANCE.getInstance().getHostWebBaseUrl() + "clinical/#/artificial";
    }
}
