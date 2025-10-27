package com.psychiatrygarden.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.ArrayMap;
import androidx.annotation.NonNull;
import cn.hutool.core.text.StrPool;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.interfaces.SimpleCallback;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.bean.CouponItems;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.widget.PopCouponRedPacket;
import com.psychiatrygarden.widget.PopMultiCoupon;
import com.psychiatrygarden.widget.PopMultiRedPacket;
import com.psychiatrygarden.widget.PopSingleCoupon;
import com.psychiatrygarden.widget.PopSingleRedPacket;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class PopupShowManager {
    private static final int DAY_MAX_SHOW_COUNT = 3;
    public static final String FLAG_ENTER_APP_HOME = "FLAG_ENTER_APP_HOME";
    public static final String FLAG_ENTER_BOOK_HOME = "ENTER_BOOK_HOME";
    public static final String FLAG_ENTER_BOOK_READ = "ENTER_BOOK_READ";
    public static final String FLAG_ENTER_COURSE_DETAIL = "ENTER_COURSE_DETAIL";
    public static final String FLAG_ENTER_GOODS_DETAIL = "ENTER_GOODS_DETAIL";
    public static final String FLAG_ENTER_INFO_HOME = "ENTER_INFO_HOME";
    public static final String FLAG_ENTER_INFO_NO_PERMISSION_VIEW_DETAIL = "ENTER_INFO_NO_PERMISSION_VIEW_DETAIL";
    public static final String FLAG_ENTER_MEMBER_CENTER = "ENTER_MEMBER_CENTER";
    public static final String FLAG_ENTER_PAGE_COURSE_HOME = "ENTER_PAGE_COURSE_HOME";
    public static final String FLAG_ENTER_SHOP_HOME = "ENTER_SHOP_HOME";
    private static final String PREFS_NAME = "popup_show_manager_prefs";
    public static final int TYPE_COUPON_ALL = 1;
    public static final int TYPE_ONLY_COUPON = 3;
    public static final int TYPE_ONLY_RED_PACKET = 2;
    private static PopupShowManager instance;
    private final Map<String, PagePopupRecord> pageRecords = new ArrayMap();
    private SharedPreferences sharedPreferences;

    public static class PagePopupRecord {
        private long todayDateMillis = getTodayDateMillis();
        private int todayShowCount = 0;
        private long lastShowTime = 0;

        private long getTodayDateMillisForTime(long currentTime) {
            return currentTime - (currentTime % 86400000);
        }

        public long getLastShowTime() {
            return this.lastShowTime;
        }

        public long getTodayDateMillis() {
            return this.todayDateMillis;
        }

        public int getTodayShowCount(long currentTime) {
            if (getTodayDateMillis() != getTodayDateMillisForTime(currentTime)) {
                this.todayDateMillis = getTodayDateMillisForTime(currentTime);
                this.todayShowCount = 0;
            }
            return this.todayShowCount;
        }

        public void recordShow(long showTime) {
            this.todayShowCount++;
            this.lastShowTime = showTime;
        }

        public void setLastShowTime(long lastShowTime) {
            this.lastShowTime = lastShowTime;
        }

        public void setTodayDateMillis(long todayDateMillis) {
            this.todayDateMillis = todayDateMillis;
        }

        public void setTodayShowCount(int todayShowCount) {
            this.todayShowCount = todayShowCount;
        }

        @NonNull
        public String toString() {
            return this.todayDateMillis + "," + this.todayShowCount + "," + this.lastShowTime;
        }
    }

    private PopupShowManager(Context context) {
        this.sharedPreferences = context.getSharedPreferences("popup_show_manager_prefs_" + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, context) + StrPool.UNDERLINE + UserConfig.getUserId(), 0);
    }

    private boolean canShowPopup(String pageId) throws NumberFormatException {
        this.sharedPreferences = ProjectApp.instance().getSharedPreferences("popup_show_manager_prefs_" + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance()) + StrPool.UNDERLINE + UserConfig.getUserId(), 0);
        loadLocalSaveData();
        long jCurrentTimeMillis = System.currentTimeMillis();
        PagePopupRecord pagePopupRecord = this.pageRecords.get(pageId);
        if (pagePopupRecord == null) {
            pagePopupRecord = new PagePopupRecord();
        }
        this.pageRecords.put(pageId, pagePopupRecord);
        if (pagePopupRecord.getTodayShowCount(jCurrentTimeMillis) >= 3) {
            saveToSharedPreferences(pageId, pagePopupRecord);
            return false;
        }
        if (jCurrentTimeMillis - pagePopupRecord.getLastShowTime() < com.heytap.mcssdk.constant.a.f7150n) {
            saveToSharedPreferences(pageId, pagePopupRecord);
            return false;
        }
        pagePopupRecord.recordShow(jCurrentTimeMillis);
        saveToSharedPreferences(pageId, pagePopupRecord);
        return true;
    }

    public static synchronized PopupShowManager getInstance(Context context) {
        if (instance == null) {
            instance = new PopupShowManager(context.getApplicationContext());
        }
        return instance;
    }

    private void loadLocalSaveData() throws NumberFormatException {
        Map<String, ?> all = this.sharedPreferences.getAll();
        this.pageRecords.clear();
        Iterator<Map.Entry<String, ?>> it = all.entrySet().iterator();
        while (it.hasNext()) {
            String key = it.next().getKey();
            String[] strArrSplit = this.sharedPreferences.getString(key, "").split(",");
            if (strArrSplit != null && strArrSplit.length == 3) {
                long j2 = Long.parseLong(strArrSplit[0]);
                int i2 = Integer.parseInt(strArrSplit[1]);
                long j3 = Long.parseLong(strArrSplit[2]);
                PagePopupRecord pagePopupRecord = new PagePopupRecord();
                pagePopupRecord.setTodayDateMillis(j2);
                pagePopupRecord.setTodayShowCount(i2);
                pagePopupRecord.setLastShowTime(j3);
                this.pageRecords.put(key, pagePopupRecord);
            }
        }
    }

    private void saveToSharedPreferences(String pageId, PagePopupRecord record) {
        SharedPreferences.Editor editorEdit = this.sharedPreferences.edit();
        editorEdit.putString(pageId, record.toString());
        editorEdit.apply();
    }

    public void checkShowCoupon(final Context context, String pageId, String apply_range, String apply_type, String objId) {
        if (CommonUtil.isNetworkConnected(context)) {
            AjaxParams ajaxParams = new AjaxParams();
            ajaxParams.put("apply_range", apply_range);
            if (!TextUtils.isEmpty(apply_type)) {
                ajaxParams.put("apply_type", apply_type);
            }
            if (!TextUtils.isEmpty(objId)) {
                ajaxParams.put("obj_id", objId);
            }
            LogUtils.d("COUPON_PARAMS", ajaxParams.toString());
            YJYHttpUtils.post(context, NetworkRequestsURL.checkCouponUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.utils.PopupShowManager.1
                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onFailure(Throwable t2, int errorNo, String strMsg) {
                    super.onFailure(t2, errorNo, strMsg);
                    Context context2 = context;
                    if (context2 instanceof BaseActivity) {
                        ((BaseActivity) context2).dismissCouponPop();
                    }
                }

                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onSuccess(String s2) {
                    BasePopupView basePopupViewShow;
                    super.onSuccess((AnonymousClass1) s2);
                    try {
                        JSONObject jSONObject = new JSONObject(s2);
                        if (!"200".equals(jSONObject.optString("code"))) {
                            Context context2 = context;
                            if (context2 instanceof BaseActivity) {
                                ((BaseActivity) context2).dismissCouponPop();
                                return;
                            }
                            return;
                        }
                        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
                        if (jSONObjectOptJSONObject == null) {
                            return;
                        }
                        List<? extends CouponItems> list = (List) new Gson().fromJson(jSONObjectOptJSONObject.optString("items"), new TypeToken<List<CouponItems>>() { // from class: com.psychiatrygarden.utils.PopupShowManager.1.1
                        }.getType());
                        if (list == null || list.size() <= 0) {
                            Context context3 = context;
                            if (context3 instanceof BaseActivity) {
                                ((BaseActivity) context3).dismissCouponPop();
                                return;
                            }
                            return;
                        }
                        ProjectApp.instance().dismissAllPop();
                        int iCheckCouponItemsShowType = CouponUtils.INSTANCE.checkCouponItemsShowType(list);
                        if (iCheckCouponItemsShowType == 1) {
                            XPopup.Builder builder = new XPopup.Builder(context);
                            Boolean bool = Boolean.FALSE;
                            basePopupViewShow = builder.dismissOnTouchOutside(bool).dismissOnBackPressed(bool).setPopupCallback(new SimpleCallback() { // from class: com.psychiatrygarden.utils.PopupShowManager.1.2
                                @Override // com.lxj.xpopup.interfaces.SimpleCallback, com.lxj.xpopup.interfaces.XPopupCallback
                                public void onDismiss(BasePopupView popupView) {
                                    super.onDismiss(popupView);
                                    Context context4 = context;
                                    if (context4 instanceof BaseActivity) {
                                        ((BaseActivity) context4).dismissCouponPop();
                                    }
                                    ProjectApp.instance.dismissPop(popupView);
                                }
                            }).asCustom(new PopCouponRedPacket(context, list)).show();
                        } else if (iCheckCouponItemsShowType == 3) {
                            XPopup.Builder builder2 = new XPopup.Builder(context);
                            Boolean bool2 = Boolean.FALSE;
                            basePopupViewShow = builder2.dismissOnTouchOutside(bool2).dismissOnBackPressed(bool2).setPopupCallback(new SimpleCallback() { // from class: com.psychiatrygarden.utils.PopupShowManager.1.3
                                @Override // com.lxj.xpopup.interfaces.SimpleCallback, com.lxj.xpopup.interfaces.XPopupCallback
                                public void onDismiss(BasePopupView popupView) {
                                    super.onDismiss(popupView);
                                    Context context4 = context;
                                    if (context4 instanceof BaseActivity) {
                                        ((BaseActivity) context4).dismissCouponPop();
                                    }
                                    ProjectApp.instance.dismissPop(popupView);
                                }
                            }).asCustom(list.size() > 1 ? new PopMultiCoupon(context, list) : new PopSingleCoupon(context, list.get(0))).show();
                        } else if (iCheckCouponItemsShowType == 2) {
                            XPopup.Builder builder3 = new XPopup.Builder(context);
                            Boolean bool3 = Boolean.FALSE;
                            basePopupViewShow = builder3.dismissOnTouchOutside(bool3).dismissOnBackPressed(bool3).setPopupCallback(new SimpleCallback() { // from class: com.psychiatrygarden.utils.PopupShowManager.1.4
                                @Override // com.lxj.xpopup.interfaces.SimpleCallback, com.lxj.xpopup.interfaces.XPopupCallback
                                public void onDismiss(BasePopupView popupView) {
                                    super.onDismiss(popupView);
                                    Context context4 = context;
                                    if (context4 instanceof BaseActivity) {
                                        ((BaseActivity) context4).dismissCouponPop();
                                    }
                                    ProjectApp.instance.dismissPop(popupView);
                                }
                            }).asCustom(list.size() > 1 ? new PopMultiRedPacket(context, list) : new PopSingleRedPacket(context, list.get(0))).show();
                        } else {
                            basePopupViewShow = null;
                        }
                        if (basePopupViewShow != null) {
                            ProjectApp.instance.addCouponPopupView(basePopupViewShow);
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        Context context4 = context;
                        if (context4 instanceof BaseActivity) {
                            ((BaseActivity) context4).dismissCouponPop();
                        }
                    }
                }
            });
        }
    }
}
