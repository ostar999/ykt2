package com.psychiatrygarden.utils;

import android.content.Context;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.bean.DaoMaster;
import com.psychiatrygarden.bean.DaoMasterBei;
import com.psychiatrygarden.bean.DaoMasterTiKu;
import com.psychiatrygarden.bean.DaoSession;
import com.psychiatrygarden.bean.DaoSessionBei;
import com.psychiatrygarden.bean.DaoSessionTiKu;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;

/* loaded from: classes6.dex */
public class ComDBSelectUtils {
    private static ComDBSelectUtils instanceManger;
    private static int postion;
    private String DB_Name;
    public Context context;
    private DaoMaster.OpenHelper helper;
    private DaoMasterBei.OpenHelper helperBei;
    private DaoMasterTiKu.OpenHelper helperTiKu;
    private DaoMaster mDaoMaster;
    private DaoMasterBei mDaoMasterBei;
    private DaoSession mDaoSession;
    private DaoSessionBei mDaoSessionBei;
    private DaoMasterTiKu mTiKuDaoMaster;
    private DaoSessionTiKu mTiKuDaoSession;
    private String DB_NAME_SELF = "yikaobang_self";
    private String DB_NAME_BEI = "yikaobang_bei";
    private String[] DB_str = {"yikaobang_tiku", "zhiyeyishi_tiku", "zhongyizhiyeyishi_tiku", "zhongyizonghe_tiku", "zhuliyishi_tiku", "benketiku_tiku"};

    private DaoMaster getDaoMaster() {
        if (this.mDaoMaster == null) {
            DaoMaster.OpenHelper openHelper = this.helper;
            if (openHelper != null) {
                openHelper.getWritableDatabase().close();
                this.helper = null;
            }
            if (SharePreferencesUtils.readStrConfig(CommonParameter.LOGIN_DATABASE_UID, this.context, "").equals("")) {
                this.helper = new DaoMaster.DevOpenHelper(this.context, this.DB_NAME_SELF, null);
            } else {
                this.helper = new DaoMaster.DevOpenHelper(this.context, this.DB_NAME_SELF + UserConfig.getUserId(), null);
            }
            this.mDaoMaster = new DaoMaster(this.helper.getWritableDatabase());
        }
        return this.mDaoMaster;
    }

    private DaoMasterBei getDaoMasterBei() {
        if (this.mDaoMasterBei == null) {
            DaoMasterBei.OpenHelper openHelper = this.helperBei;
            if (openHelper != null) {
                openHelper.getWritableDatabase().close();
                this.helperBei = null;
            }
            DaoMasterBei.DevOpenHelper devOpenHelper = new DaoMasterBei.DevOpenHelper(this.context, this.DB_NAME_BEI, null);
            this.helperBei = devOpenHelper;
            this.mDaoMasterBei = new DaoMasterBei(devOpenHelper.getWritableDatabase());
        }
        return this.mDaoMasterBei;
    }

    public static ComDBSelectUtils getInstanceManger() {
        postion = SharePreferencesUtils.readIntConfig(CommonParameter.App_Position, ProjectApp.instance(), 0);
        if (instanceManger == null) {
            instanceManger = new ComDBSelectUtils();
        }
        return instanceManger;
    }

    private DaoMasterTiKu getTiKuDaoMaster() {
        if (this.mTiKuDaoMaster == null) {
            DaoMasterTiKu.OpenHelper openHelper = this.helperTiKu;
            if (openHelper != null) {
                openHelper.getWritableDatabase().close();
                this.helperTiKu = null;
            }
            DaoMasterTiKu.DevOpenHelper devOpenHelper = new DaoMasterTiKu.DevOpenHelper(this.context, this.DB_str[postion], null);
            this.helperTiKu = devOpenHelper;
            this.mTiKuDaoMaster = new DaoMasterTiKu(devOpenHelper.getWritableDatabase());
        }
        return this.mTiKuDaoMaster;
    }

    public DaoSession getDaoSession() {
        if (SharePreferencesUtils.readStrConfig(CommonParameter.LOGIN_DATABASE_IS_CHANGE, this.context, "0").equals("1")) {
            SharePreferencesUtils.writeStrConfig(CommonParameter.LOGIN_DATABASE_IS_CHANGE, "0", this.context);
            this.mDaoSession = null;
            this.mDaoMaster = null;
        }
        if (this.mDaoSession == null) {
            if (this.mDaoMaster == null) {
                getDaoMaster();
            }
            this.mDaoSession = this.mDaoMaster.newSession();
        }
        return this.mDaoSession;
    }

    public DaoSessionBei getDaoSessionBei() {
        if (this.mDaoSessionBei == null) {
            if (this.mDaoMasterBei == null) {
                getDaoMasterBei();
            }
            this.mDaoSessionBei = this.mDaoMasterBei.newSession();
        }
        return this.mDaoSessionBei;
    }

    public DaoSessionTiKu getTiKuDaoSession() {
        if (SharePreferencesUtils.readBooleanConfig(CommonParameter.App_ISTIKU, true, this.context)) {
            SharePreferencesUtils.writeBooleanConfig(CommonParameter.App_ISTIKU, false, this.context);
            this.mTiKuDaoSession = null;
            this.mTiKuDaoMaster = null;
        }
        if (this.mTiKuDaoSession == null) {
            if (this.mTiKuDaoMaster == null) {
                getTiKuDaoMaster();
            }
            this.mTiKuDaoSession = this.mTiKuDaoMaster.newSession();
        }
        return this.mTiKuDaoSession;
    }

    public void initContext(Context context) {
        this.context = context.getApplicationContext();
    }

    public void mClearData() {
        postion = SharePreferencesUtils.readIntConfig(CommonParameter.App_Position, ProjectApp.instance(), 0);
        DaoSession daoSession = this.mDaoSession;
        if (daoSession != null) {
            daoSession.clear();
            this.mDaoSession = null;
            if (this.mDaoMaster != null) {
                this.mDaoMaster = null;
            }
        }
        DaoSessionTiKu daoSessionTiKu = this.mTiKuDaoSession;
        if (daoSessionTiKu != null) {
            daoSessionTiKu.clear();
            this.mTiKuDaoSession = null;
            if (this.mTiKuDaoMaster != null) {
                this.mTiKuDaoMaster = null;
            }
        }
        DaoSessionBei daoSessionBei = this.mDaoSessionBei;
        if (daoSessionBei != null) {
            daoSessionBei.clear();
            this.mDaoSessionBei = null;
            if (this.mDaoMasterBei != null) {
                this.mDaoMasterBei = null;
            }
        }
        ProjectApp.mDaoSession = instanceManger.getDaoSession();
        ProjectApp.mTiKuDaoSession = instanceManger.getTiKuDaoSession();
        ProjectApp.mDaoSessionBei = instanceManger.getDaoSessionBei();
    }

    public void mClearLoginData() {
        postion = SharePreferencesUtils.readIntConfig(CommonParameter.App_Position, ProjectApp.instance(), 0);
        DaoSession daoSession = this.mDaoSession;
        if (daoSession != null) {
            daoSession.clear();
            this.mDaoSession = null;
            if (this.mDaoMaster != null) {
                this.mDaoMaster = null;
            }
        }
        ProjectApp.mDaoSession = instanceManger.getDaoSession();
    }
}
