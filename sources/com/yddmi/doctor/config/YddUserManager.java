package com.yddmi.doctor.config;

import android.content.Context;
import com.blankj.utilcode.util.BusUtils;
import com.catchpig.mvvm.utils.DataStoreUtils;
import com.catchpig.mvvm.utils.DateUtil;
import com.catchpig.utils.ext.LogExtKt;
import com.catchpig.utils.manager.ContextManager;
import com.psychiatrygarden.utils.CommonParameter;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yddmi.doctor.config.YddHostConfig;
import com.yddmi.doctor.entity.result.AuthLoginResult;
import com.yddmi.doctor.entity.result.MeProfile;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\r\u0018\u0000 D2\u00020\u0001:\u0001DB\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0006J\u0006\u0010\n\u001a\u00020\u0006J\u000e\u0010\u000b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0006J\u0006\u0010\f\u001a\u00020\rJ\u0006\u0010\u000e\u001a\u00020\rJ\u0006\u0010\u000f\u001a\u00020\rJ\u0006\u0010\u0010\u001a\u00020\rJ\u0006\u0010\u0011\u001a\u00020\u0012J\u0006\u0010\u0013\u001a\u00020\u0004J\u0006\u0010\u0014\u001a\u00020\rJ\u0010\u0010\u0015\u001a\u00020\b2\b\u0010\u0016\u001a\u0004\u0018\u00010\rJ\u0010\u0010\u0017\u001a\u00020\b2\b\b\u0002\u0010\u0018\u001a\u00020\u0006J\u0006\u0010\u0019\u001a\u00020\u0006J\u000e\u0010\u001a\u001a\u00020\b2\u0006\u0010\u001b\u001a\u00020\u0006J\u0006\u0010\u001c\u001a\u00020\bJ\u0010\u0010\u001d\u001a\u00020\b2\b\u0010\u001e\u001a\u0004\u0018\u00010\rJ\u0010\u0010\u001f\u001a\u00020\b2\b\u0010 \u001a\u0004\u0018\u00010\rJ\u000e\u0010!\u001a\u00020\b2\u0006\u0010\"\u001a\u00020\u0012J\u0006\u0010#\u001a\u00020\rJ\u0010\u0010$\u001a\u00020\b2\b\u0010%\u001a\u0004\u0018\u00010\rJ\u0006\u0010&\u001a\u00020\rJ\u000e\u0010'\u001a\u00020\b2\u0006\u0010(\u001a\u00020\rJ\u0006\u0010)\u001a\u00020\u0006J\u000e\u0010*\u001a\u00020\b2\u0006\u0010+\u001a\u00020\u0006J\u0006\u0010,\u001a\u00020\u0012J\u001e\u0010-\u001a\u00020\b2\n\b\u0002\u0010.\u001a\u0004\u0018\u00010/2\n\b\u0002\u00100\u001a\u0004\u0018\u000101J\u0006\u00102\u001a\u00020\u0012J\u000e\u00103\u001a\u00020\b2\u0006\u00104\u001a\u00020\u0012J\u0006\u00105\u001a\u00020\u0006J\u000e\u00106\u001a\u00020\u00062\u0006\u00107\u001a\u000208J\u0006\u00109\u001a\u00020\rJ\u0006\u0010:\u001a\u00020\rJ\u000e\u0010:\u001a\u00020\b2\u0006\u0010;\u001a\u00020\rJ\u0006\u0010<\u001a\u00020\u0012J\u0006\u0010=\u001a\u00020\rJ\u000e\u0010>\u001a\u00020\b2\u0006\u0010?\u001a\u00020\u0012J\u0006\u0010@\u001a\u00020\u0012J\u0006\u0010A\u001a\u00020\rJ\u0006\u0010B\u001a\u00020\rJ\u0010\u0010C\u001a\u00020\b2\b\u0010%\u001a\u0004\u0018\u00010\rR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000¨\u0006E"}, d2 = {"Lcom/yddmi/doctor/config/YddUserManager;", "", "()V", "mWxApi", "Lcom/tencent/mm/opensdk/openapi/IWXAPI;", "firstGuideGet", "", "firstGuideSet", "", "first", "firstStartGet", "firstStartSet", "getAndroidId", "", "getOaid", "getWxHeadImgUrl", "getWxNickName", "getWxUnionFlag", "", "getmWxApi", "loginShareCodeGet", "loginShareCodeSet", "code", "logoutUser", "sendBus", "noticeTipGet", "noticeTipSet", CommonParameter.notice, "registToWx", "setAndroidId", "aId", "setOaid", "oaid", "setWxUnionFlag", "flag", "u3dVersionGet", "u3dVersionSet", "pushToken", "userAvatar", "userAvatarSet", "url", "userCanTpush", "userCanTpushSet", "push", "userId", "userInfoSave", "data", "Lcom/yddmi/doctor/entity/result/AuthLoginResult;", "meProfile", "Lcom/yddmi/doctor/entity/result/MeProfile;", "userIntegralGet", "userIntegralSet", "integral", "userIsLogin", "userIsLoginGo", "content", "Landroid/content/Context;", "userName", "userNickName", "str", "userOrgIdGet", "userPhone", "userStudyMode", "studyMode", "userStudyModeGet", "userToken", "userTpushTokenGet", "userTpushTokenSet", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class YddUserManager {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private static final Lazy<YddUserManager> instance$delegate = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.SYNCHRONIZED, (Function0) new Function0<YddUserManager>() { // from class: com.yddmi.doctor.config.YddUserManager$Companion$instance$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        @NotNull
        public final YddUserManager invoke() {
            return new YddUserManager();
        }
    });
    private IWXAPI mWxApi;

    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001b\u0010\u0003\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006¨\u0006\t"}, d2 = {"Lcom/yddmi/doctor/config/YddUserManager$Companion;", "", "()V", "instance", "Lcom/yddmi/doctor/config/YddUserManager;", "getInstance", "()Lcom/yddmi/doctor/config/YddUserManager;", "instance$delegate", "Lkotlin/Lazy;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final YddUserManager getInstance() {
            return (YddUserManager) YddUserManager.instance$delegate.getValue();
        }
    }

    public static /* synthetic */ void logoutUser$default(YddUserManager yddUserManager, boolean z2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z2 = true;
        }
        yddUserManager.logoutUser(z2);
    }

    public static /* synthetic */ void userInfoSave$default(YddUserManager yddUserManager, AuthLoginResult authLoginResult, MeProfile meProfile, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            authLoginResult = null;
        }
        if ((i2 & 2) != 0) {
            meProfile = null;
        }
        yddUserManager.userInfoSave(authLoginResult, meProfile);
    }

    public final boolean firstGuideGet() {
        return ((Boolean) DataStoreUtils.INSTANCE.getSyncData(YddConfig.SP_FIRST_GUIDE, Boolean.TRUE)).booleanValue();
    }

    public final void firstGuideSet(boolean first) {
        DataStoreUtils.INSTANCE.putSyncData(YddConfig.SP_FIRST_GUIDE, Boolean.valueOf(first));
    }

    public final boolean firstStartGet() {
        return ((Boolean) DataStoreUtils.INSTANCE.getSyncData(YddConfig.SP_FIRST_START, Boolean.TRUE)).booleanValue();
    }

    public final void firstStartSet(boolean first) {
        DataStoreUtils.INSTANCE.putSyncData(YddConfig.SP_FIRST_START, Boolean.valueOf(first));
    }

    @NotNull
    public final String getAndroidId() {
        return (String) DataStoreUtils.INSTANCE.getSyncData(YddConfig.SP_AID, "");
    }

    @NotNull
    public final String getOaid() {
        return (String) DataStoreUtils.INSTANCE.getSyncData(YddConfig.SP_OAID, "");
    }

    @NotNull
    public final String getWxHeadImgUrl() {
        return (String) DataStoreUtils.INSTANCE.getSyncData(YddConfig.SP_USER_WX_HEADIMAGEURL, "");
    }

    @NotNull
    public final String getWxNickName() {
        return (String) DataStoreUtils.INSTANCE.getSyncData(YddConfig.SP_USER_WX_NIKENAME, "");
    }

    public final int getWxUnionFlag() {
        return ((Number) DataStoreUtils.INSTANCE.getSyncData(YddConfig.SP_USER_WX_UNIONFLAG, 0)).intValue();
    }

    @NotNull
    public final IWXAPI getmWxApi() {
        IWXAPI iwxapi = this.mWxApi;
        if (iwxapi != null) {
            return iwxapi;
        }
        Intrinsics.throwUninitializedPropertyAccessException("mWxApi");
        return null;
    }

    @NotNull
    public final String loginShareCodeGet() {
        return "";
    }

    public final void loginShareCodeSet(@Nullable String code) {
        LogExtKt.logd("loginShareCodeSet 写入：" + code, YddConfig.TAG);
        DataStoreUtils dataStoreUtils = DataStoreUtils.INSTANCE;
        if (code == null) {
            code = "";
        }
        dataStoreUtils.putSyncData(YddConfig.SP_LOGIN_SHARE_CODE, code);
    }

    public final void logoutUser(boolean sendBus) {
        userCanTpushSet(false);
        YddHostConfig.Companion companion = YddHostConfig.INSTANCE;
        YddHostConfig.YddHost currentHost = companion.getInstance().getCurrentHost();
        String strLoginShareCodeGet = loginShareCodeGet();
        String strServicePrivateGetSp = companion.getInstance().servicePrivateGetSp();
        DataStoreUtils.INSTANCE.clearSync();
        noticeTipSet(true);
        loginShareCodeSet(strLoginShareCodeGet);
        companion.getInstance().servicePrivate(strServicePrivateGetSp);
        companion.getInstance().dealChangeHost(currentHost);
        GlobalAction globalAction = GlobalAction.INSTANCE;
        globalAction.setLoginFromLogout(true);
        globalAction.setHomeDataRefresh(true);
        globalAction.setHomeActRefresh(true);
        if (sendBus) {
            BusUtils.post(GlobalAction.eventLogout);
        }
    }

    public final boolean noticeTipGet() {
        return ((Boolean) DataStoreUtils.INSTANCE.getSyncData(YddConfig.SP_NOTICE_TIP, Boolean.FALSE)).booleanValue();
    }

    public final void noticeTipSet(boolean notice) {
        DataStoreUtils.INSTANCE.putSyncData(YddConfig.SP_NOTICE_TIP, Boolean.valueOf(notice));
    }

    public final void registToWx() {
        IWXAPI iwxapiCreateWXAPI = WXAPIFactory.createWXAPI(ContextManager.INSTANCE.getInstance().getContext(), YddConfig.wxAppId, false);
        Intrinsics.checkNotNullExpressionValue(iwxapiCreateWXAPI, "createWXAPI(\n           ….wxAppId, false\n        )");
        this.mWxApi = iwxapiCreateWXAPI;
        if (iwxapiCreateWXAPI == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mWxApi");
            iwxapiCreateWXAPI = null;
        }
        iwxapiCreateWXAPI.registerApp(YddConfig.wxAppId);
    }

    public final void setAndroidId(@Nullable String aId) {
        DataStoreUtils dataStoreUtils = DataStoreUtils.INSTANCE;
        if (aId == null) {
            aId = "";
        }
        dataStoreUtils.putSyncData(YddConfig.SP_AID, aId);
    }

    public final void setOaid(@Nullable String oaid) {
        DataStoreUtils dataStoreUtils = DataStoreUtils.INSTANCE;
        if (oaid == null) {
            oaid = "";
        }
        dataStoreUtils.putSyncData(YddConfig.SP_OAID, oaid);
    }

    public final void setWxUnionFlag(int flag) {
        DataStoreUtils.INSTANCE.putSyncData(YddConfig.SP_USER_WX_UNIONFLAG, Integer.valueOf(flag));
    }

    @NotNull
    public final String u3dVersionGet() {
        return (String) DataStoreUtils.INSTANCE.getSyncData(YddConfig.SP_U3D_VERSION, "");
    }

    public final void u3dVersionSet(@Nullable String pushToken) {
        DataStoreUtils.INSTANCE.putSyncData(YddConfig.SP_U3D_VERSION, pushToken);
    }

    @NotNull
    public final String userAvatar() {
        return (String) DataStoreUtils.INSTANCE.getSyncData(YddConfig.SP_USER_AVATAR, "");
    }

    public final void userAvatarSet(@NotNull String url) {
        Intrinsics.checkNotNullParameter(url, "url");
        DataStoreUtils.INSTANCE.putSyncData(YddConfig.SP_USER_AVATAR, url);
    }

    public final boolean userCanTpush() {
        return true;
    }

    public final void userCanTpushSet(boolean push) {
        DataStoreUtils.INSTANCE.putSyncData(YddConfig.SP_USER_PUSH, Boolean.valueOf(push));
    }

    public final int userId() {
        return ((Number) DataStoreUtils.INSTANCE.getSyncData(YddConfig.SP_USER_ID, -1)).intValue();
    }

    public final void userInfoSave(@Nullable AuthLoginResult data, @Nullable MeProfile meProfile) {
        if (data != null) {
            DataStoreUtils dataStoreUtils = DataStoreUtils.INSTANCE;
            dataStoreUtils.putSyncData(YddConfig.SP_USER_TOKEN, data.getAccess_token());
            dataStoreUtils.putSyncData(YddConfig.SP_USER_TOKEN_TIME, Long.valueOf(DateUtil.getTimeInMillisLong()));
            userCanTpushSet(true);
            GlobalAction globalAction = GlobalAction.INSTANCE;
            globalAction.setHomeDataRefresh(true);
            globalAction.setMeInfoGet(true);
            globalAction.setMsgRefresh(true);
            globalAction.setMRecommendRefresh(true);
            BusUtils.post(GlobalAction.eventLogIn);
            BusUtils.post(GlobalAction.eventMsgNum);
        }
        if (meProfile != null) {
            DataStoreUtils dataStoreUtils2 = DataStoreUtils.INSTANCE;
            dataStoreUtils2.putSyncData(YddConfig.SP_USER_ID, Integer.valueOf(meProfile.getUserId()));
            String userName = meProfile.getUserName();
            if (userName == null) {
                userName = "";
            }
            dataStoreUtils2.putSyncData(YddConfig.SP_USER_NAME, userName);
            String nickName = meProfile.getNickName();
            if (nickName == null) {
                nickName = "";
            }
            dataStoreUtils2.putSyncData(YddConfig.SP_USER_NICK_NAME, nickName);
            String phone = meProfile.getPhone();
            if (phone == null) {
                phone = "";
            }
            dataStoreUtils2.putSyncData(YddConfig.SP_USER_PHONE, phone);
            dataStoreUtils2.putSyncData(YddConfig.SP_USER_WX_UNIONFLAG, Integer.valueOf(meProfile.getWxUnionFlag()));
            String wxNickName = meProfile.getWxNickName();
            if (wxNickName == null) {
                wxNickName = "";
            }
            dataStoreUtils2.putSyncData(YddConfig.SP_USER_WX_NIKENAME, wxNickName);
            String wxHeadImgUrl = meProfile.getWxHeadImgUrl();
            if (wxHeadImgUrl == null) {
                wxHeadImgUrl = "";
            }
            dataStoreUtils2.putSyncData(YddConfig.SP_USER_WX_HEADIMAGEURL, wxHeadImgUrl);
            dataStoreUtils2.putSyncData(YddConfig.SP_USER_ORGID, Integer.valueOf(meProfile.getOrgId()));
            String avatar = meProfile.getAvatar();
            dataStoreUtils2.putSyncData(YddConfig.SP_USER_AVATAR, avatar != null ? avatar : "");
            userStudyMode(meProfile.getStudyMode());
            LogExtKt.logd("推送用户绑定 " + dataStoreUtils2.readIntData(YddConfig.SP_USER_PUSH_ID, -1) + " " + meProfile.getUserId(), YddConfig.TAG);
            if (dataStoreUtils2.readIntData(YddConfig.SP_USER_PUSH_ID, -1) != meProfile.getUserId()) {
                dataStoreUtils2.putSyncData(YddConfig.SP_USER_PUSH_ID, Integer.valueOf(meProfile.getUserId()));
                BusUtils.post(GlobalAction.eventUserTpushBind);
            }
        }
    }

    public final int userIntegralGet() {
        return ((Number) DataStoreUtils.INSTANCE.getSyncData(YddConfig.SP_USER_INTEGRAL, 0)).intValue();
    }

    public final void userIntegralSet(int integral) {
        DataStoreUtils.INSTANCE.putSyncData(YddConfig.SP_USER_INTEGRAL, Integer.valueOf(integral));
    }

    public final boolean userIsLogin() {
        String str = (String) DataStoreUtils.INSTANCE.getSyncData(YddConfig.SP_USER_TOKEN, "");
        return !(str == null || str.length() == 0);
    }

    public final boolean userIsLoginGo(@NotNull Context content) {
        Intrinsics.checkNotNullParameter(content, "content");
        if (userIsLogin()) {
            return true;
        }
        BusUtils.post(GlobalAction.eventLogout401);
        return false;
    }

    @NotNull
    public final String userName() {
        return (String) DataStoreUtils.INSTANCE.getSyncData(YddConfig.SP_USER_NAME, "");
    }

    @NotNull
    public final String userNickName() {
        return (String) DataStoreUtils.INSTANCE.getSyncData(YddConfig.SP_USER_NICK_NAME, "");
    }

    public final int userOrgIdGet() {
        return ((Number) DataStoreUtils.INSTANCE.getSyncData(YddConfig.SP_USER_ORGID, 0)).intValue();
    }

    @NotNull
    public final String userPhone() {
        return (String) DataStoreUtils.INSTANCE.getSyncData(YddConfig.SP_USER_PHONE, "");
    }

    public final void userStudyMode(int studyMode) {
        DataStoreUtils.INSTANCE.putSyncData(YddConfig.SP_USER_STUDY_MODE, Integer.valueOf(studyMode));
    }

    public final int userStudyModeGet() {
        return ((Number) DataStoreUtils.INSTANCE.getSyncData(YddConfig.SP_USER_STUDY_MODE, 1)).intValue();
    }

    @NotNull
    public final String userToken() {
        return DataStoreUtils.INSTANCE.readStringData(YddConfig.SP_USER_TOKEN, "");
    }

    @NotNull
    public final String userTpushTokenGet() {
        return (String) DataStoreUtils.INSTANCE.getSyncData(YddConfig.SP_USER_PUSH_T0KEN, "");
    }

    public final void userTpushTokenSet(@Nullable String pushToken) {
        DataStoreUtils.INSTANCE.putSyncData(YddConfig.SP_USER_PUSH_T0KEN, pushToken);
    }

    public final void userNickName(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "str");
        DataStoreUtils.INSTANCE.putSyncData(YddConfig.SP_USER_NICK_NAME, str);
    }
}
