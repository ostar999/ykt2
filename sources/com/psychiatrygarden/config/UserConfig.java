package com.psychiatrygarden.config;

import android.content.Context;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.bean.UserInfoBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.FileUtil;
import com.ykb.common_share_lib.CommonConfig;

/* loaded from: classes5.dex */
public class UserConfig {
    private static final String USER_INFO = "user_info";
    private static UserConfig sInstance;
    private UserInfoBean.DataBean mUser;

    private UserConfig() {
        initUser();
    }

    public static UserConfig getInstance() {
        if (sInstance == null) {
            sInstance = new UserConfig();
        }
        return sInstance;
    }

    public static String getUserId() {
        return getInstance().mUser != null ? getInstance().mUser.getUser_id() : "";
    }

    public static String getWxUserName() {
        UserInfoBean.DataBean dataBean = getInstance().mUser;
        if (dataBean == null) {
            return null;
        }
        return dataBean.getUsername();
    }

    private void initUser() {
        String file = FileUtil.readFile(ProjectApp.instance(), USER_INFO);
        if (TextUtils.isEmpty(file)) {
            this.mUser = null;
            return;
        }
        try {
            if (TextUtils.isEmpty(file)) {
                throw new NullPointerException("json格式错误！");
            }
            this.mUser = (UserInfoBean.DataBean) new Gson().fromJson(file, UserInfoBean.DataBean.class);
        } catch (Exception unused) {
            this.mUser = null;
        }
    }

    public static boolean isBindWx() {
        UserInfoBean.DataBean dataBean = getInstance().mUser;
        if (dataBean == null) {
            return false;
        }
        return TextUtils.equals(dataBean.getLogin_status(), "login_success");
    }

    public static boolean isCanDownloadBy4g(Context context) {
        return SharePreferencesUtils.readBooleanConfig(CommonParameter.ALLOW_OPERATOR_DOWNLOAD_CONFIG, false, context);
    }

    public static boolean isCanPlayBy4g(Context context) {
        return SharePreferencesUtils.readBooleanConfig(CommonParameter.ALLOW_OPERATOR_PLAY_CONFIG, false, context);
    }

    public static boolean isLogin() {
        return getInstance().mUser != null;
    }

    public UserInfoBean.DataBean getUser() {
        return this.mUser;
    }

    public void logOut() {
        getInstance().saveUser(null);
    }

    public void saveUser(UserInfoBean.DataBean userInfo) {
        try {
            FileUtil.writeFile(ProjectApp.instance(), USER_INFO, new Gson().toJson(userInfo));
            CommonConfig.INSTANCE.setUuid(userInfo.getUser_uuid());
        } catch (Exception unused) {
        }
        initUser();
    }

    public void unbindWx() {
        UserInfoBean.DataBean dataBean = getInstance().mUser;
        dataBean.setOpen_id("");
        dataBean.setUsername("");
        saveUser(dataBean);
    }
}
