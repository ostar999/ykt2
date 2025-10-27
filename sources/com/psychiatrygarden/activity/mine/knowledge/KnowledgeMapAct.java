package com.psychiatrygarden.activity.mine.knowledge;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.easeui.utils.StatusBarCompat;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.vip.MemberCenterActivity;
import com.psychiatrygarden.bean.ChartFilterBean;
import com.psychiatrygarden.bean.KnowledgeMapBean;
import com.psychiatrygarden.bean.KnowledgeMapPointBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.BuyVipSuccessEvent;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.AndroidBaseUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.DesUtil;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.widget.KnowledgeMapDifficultyView;
import com.psychiatrygarden.widget.KnowledgeMapFrequencyView;
import com.psychiatrygarden.widget.KnowledgeMaskView;
import com.psychiatrygarden.widget.KnowledgePartItemView;
import com.psychiatrygarden.widget.KnowledgePercentItemView;
import com.psychiatrygarden.widget.KnowledgePointsProgressView;
import com.psychiatrygarden.widget.PopKnowledgeChartFilter;
import com.yikaobang.yixue.R;
import com.yikaobang.yixue.R2;
import de.greenrobot.event.EventBus;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class KnowledgeMapAct extends BaseActivity {
    private KnowledgeFilterItemAdp filterItemAdp;
    private HorizontalScrollView horScorll;
    private HorizontalScrollView horScorllDifficult;
    private TextView mBtnDoQuestion;
    private ImageView mBtnFilter;
    private TextView mBtnOpenVip;
    private ImageView mImgBack;
    private ImageView mImgPercent;
    private ImageView mImgRightPercent;
    private boolean mIsVip;
    private LinearLayout mLyAddDifficultView;
    private LinearLayout mLyAddFrequencyView;
    private LinearLayout mLyAddPartView;
    private LinearLayout mLyAddPercentView;
    private RelativeLayout mLyBtn;
    private LinearLayout mLyDesc;
    private LinearLayout mLyOpenVip;
    private LinearLayout mLyPart;
    private LinearLayout mLyPercent;
    private LinearLayout mLyPoint;
    private LinearLayout mLyRightPercent;
    private RelativeLayout mLyWebView;
    private TextView mNavTitle;
    private NestedScrollView mScrollView;
    private RelativeLayout mTabbar;
    private TextView mTvDescInfo;
    private TextView mTvFilterDesc;
    private TextView mTvPointAllNum;
    private TextView mTvPointPercent;
    private TextView mTvPointStudyNum;
    private TextView mTvPointUserPercent;
    private int mType;
    private RelativeLayout mViewWeb;
    private WebView mWebView;
    private KnowledgePointsProgressView pointsProgressView;
    private RecyclerView rvFilter;
    private KnowledgeMaskView viewDifficultVip;
    private KnowledgeMaskView viewFrequencyVip;
    private KnowledgeMaskView viewPartVip;
    private KnowledgeMaskView viewPointVip;
    private String mParentId = "0";
    private String mPartId = "";
    private String mChapterId = "";
    private List<ChartFilterBean> chartFilterList = new ArrayList();
    private List<KnowledgeMapBean.KnowledgeMapItemDataBean> partList = new ArrayList();
    private boolean isSortPoint = true;
    private boolean isSortPercent = true;
    private ArrayMap<String, List<String>> defaultFilterMap = new ArrayMap<>();

    /* renamed from: com.psychiatrygarden.activity.mine.knowledge.KnowledgeMapAct$2, reason: invalid class name */
    public class AnonymousClass2 extends AjaxCallBack<String> {
        public AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0(int i2, int i3, int i4) {
            KnowledgeMapAct.this.pointsProgressView.setCurrentPercent(i2, i3, i4 + "");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$1(int i2, int i3) {
            KnowledgeMapAct.this.pointsProgressView.setCurrentPercent(i2, i3, "0");
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String s2) {
            super.onSuccess((AnonymousClass2) s2);
            try {
                KnowledgeMapPointBean knowledgeMapPointBean = (KnowledgeMapPointBean) new Gson().fromJson(s2, KnowledgeMapPointBean.class);
                if (!knowledgeMapPointBean.getCode().equals("200") || knowledgeMapPointBean.getData() == null) {
                    return;
                }
                KnowledgeMapAct.this.mTvPointAllNum.setText(knowledgeMapPointBean.getData().getKnowledge_count());
                KnowledgeMapAct.this.mTvPointStudyNum.setText(knowledgeMapPointBean.getData().getPractice_count());
                KnowledgeMapAct.this.mTvPointUserPercent.setText(knowledgeMapPointBean.getData().getUser_correct_rate().replace("%", ""));
                KnowledgeMapAct.this.mTvPointPercent.setText(knowledgeMapPointBean.getData().getAll_correct_rate().replace("%", ""));
                final int i2 = TextUtils.isEmpty(knowledgeMapPointBean.getData().getPractice_count()) ? 0 : Integer.parseInt(knowledgeMapPointBean.getData().getPractice_count());
                final int i3 = TextUtils.isEmpty(knowledgeMapPointBean.getData().getKnowledge_count()) ? 100 : Integer.parseInt(knowledgeMapPointBean.getData().getKnowledge_count());
                if (i3 != 0) {
                    final int i4 = (i2 * 100) / i3;
                    KnowledgeMapAct.this.pointsProgressView.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.mine.knowledge.x
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f12910c.lambda$onSuccess$0(i2, i3, i4);
                        }
                    }, 300L);
                } else {
                    KnowledgeMapAct.this.pointsProgressView.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.mine.knowledge.y
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f12914c.lambda$onSuccess$1(i2, i3);
                        }
                    }, 300L);
                }
                if (knowledgeMapPointBean.getData().getCategory_list() == null || knowledgeMapPointBean.getData().getCategory_list().size() <= 0) {
                    KnowledgeMapAct.this.mLyAddPercentView.removeAllViews();
                } else {
                    KnowledgeMapAct.this.mLyAddPercentView.removeAllViews();
                    for (int i5 = 0; i5 < 5; i5++) {
                        KnowledgePercentItemView knowledgePercentItemView = new KnowledgePercentItemView(KnowledgeMapAct.this);
                        knowledgePercentItemView.setData(i5, knowledgeMapPointBean.getData().getCategory_list().get(i5));
                        KnowledgeMapAct.this.mLyAddPercentView.addView(knowledgePercentItemView);
                    }
                }
                if (knowledgeMapPointBean.getData().getFrequency_list() == null || knowledgeMapPointBean.getData().getFrequency_list().size() <= 0) {
                    KnowledgeMapAct.this.mLyAddFrequencyView.removeAllViews();
                } else {
                    KnowledgeMapAct.this.mLyAddFrequencyView.removeAllViews();
                    int size = knowledgeMapPointBean.getData().getFrequency_list().size();
                    int i6 = 0;
                    while (i6 < size) {
                        KnowledgeMapFrequencyView knowledgeMapFrequencyView = new KnowledgeMapFrequencyView(KnowledgeMapAct.this, i6 == size + (-1));
                        knowledgeMapFrequencyView.setData(knowledgeMapPointBean.getData().getFrequency_list().get(i6));
                        KnowledgeMapAct.this.mLyAddFrequencyView.addView(knowledgeMapFrequencyView);
                        i6++;
                    }
                }
                if (knowledgeMapPointBean.getData().getStar_list() == null || knowledgeMapPointBean.getData().getStar_list().size() <= 0) {
                    KnowledgeMapAct.this.mLyAddDifficultView.removeAllViews();
                    return;
                }
                KnowledgeMapAct.this.mLyAddDifficultView.removeAllViews();
                int size2 = knowledgeMapPointBean.getData().getStar_list().size();
                int i7 = 0;
                while (i7 < size2) {
                    KnowledgeMapDifficultyView knowledgeMapDifficultyView = new KnowledgeMapDifficultyView(KnowledgeMapAct.this, i7 == size2 + (-1));
                    knowledgeMapDifficultyView.setData(knowledgeMapPointBean.getData().getStar_list().get(i7));
                    KnowledgeMapAct.this.mLyAddDifficultView.addView(knowledgeMapDifficultyView);
                    i7++;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public class JavaScriptInterface {
        Context context;

        public JavaScriptInterface(Context context) {
            this.context = context;
        }
    }

    public class MyWebViewClient extends WebViewClient {
        private MyWebViewClient() {
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            KnowledgeMapAct.this.mLyWebView.getLayoutParams().height = -2;
            KnowledgeMapAct.this.mViewWeb.setVisibility(8);
        }
    }

    private void getData(String searchData) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("id", this.mParentId);
        ajaxParams.put("type", this.mType + "");
        if (!TextUtils.isEmpty(searchData)) {
            ajaxParams.put("search", searchData);
        }
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.knowlwdgeMapOne, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.mine.knowledge.KnowledgeMapAct.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                try {
                    KnowledgeMapBean knowledgeMapBean = (KnowledgeMapBean) new Gson().fromJson(s2, KnowledgeMapBean.class);
                    if (!knowledgeMapBean.getCode().equals("200") || knowledgeMapBean.getData() == null) {
                        return;
                    }
                    if (KnowledgeMapAct.this.filterItemAdp.getData().isEmpty()) {
                        if (TextUtils.isEmpty(knowledgeMapBean.getData().getFilter_desc())) {
                            KnowledgeMapAct.this.mTvFilterDesc.setVisibility(8);
                        } else {
                            KnowledgeMapAct.this.mTvFilterDesc.setVisibility(0);
                            KnowledgeMapAct.this.mTvFilterDesc.setText(knowledgeMapBean.getData().getFilter_desc());
                            KnowledgeMapAct.this.mTvFilterDesc.requestFocus();
                            KnowledgeMapAct.this.mTvFilterDesc.setSelected(true);
                        }
                    }
                    String strDecode = DesUtil.decode(CommonParameter.DES_KEY_VERIFY, knowledgeMapBean.getData().getIs_vip());
                    KnowledgeMapAct.this.mIsVip = strDecode.equals("1");
                    KnowledgeMapAct knowledgeMapAct = KnowledgeMapAct.this;
                    knowledgeMapAct.setUpUi(knowledgeMapAct.mIsVip);
                    if (TextUtils.isEmpty(knowledgeMapBean.getData().getTop_name())) {
                        KnowledgeMapAct.this.mNavTitle.setText("知识地图");
                    } else {
                        KnowledgeMapAct.this.mNavTitle.setText(knowledgeMapBean.getData().getTop_name());
                    }
                    if (TextUtils.isEmpty(knowledgeMapBean.getData().getTop_desc())) {
                        KnowledgeMapAct.this.mLyDesc.setVisibility(8);
                    } else {
                        KnowledgeMapAct.this.mLyDesc.setVisibility(0);
                        KnowledgeMapAct.this.mTvDescInfo.setText(knowledgeMapBean.getData().getTop_desc());
                    }
                    if (knowledgeMapBean.getData().getList() == null || knowledgeMapBean.getData().getList().size() <= 0) {
                        KnowledgeMapAct.this.partList.clear();
                        KnowledgeMapAct.this.mLyAddPartView.removeAllViews();
                        return;
                    }
                    KnowledgeMapAct.this.partList.clear();
                    KnowledgeMapAct.this.partList.addAll(knowledgeMapBean.getData().getList());
                    KnowledgeMapAct.this.mLyAddPartView.removeAllViews();
                    for (int i2 = 0; i2 < knowledgeMapBean.getData().getList().size(); i2++) {
                        KnowledgePartItemView knowledgePartItemView = new KnowledgePartItemView(KnowledgeMapAct.this);
                        knowledgePartItemView.setData(KnowledgeMapAct.this.mType, knowledgeMapBean.getData().getList().get(i2));
                        KnowledgeMapAct.this.mLyAddPartView.addView(knowledgePartItemView);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
        getPointData();
    }

    private void getFilterData() {
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.questionChartFilter, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.mine.knowledge.KnowledgeMapAct.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                try {
                    JSONObject jSONObjectOptJSONObject = new JSONObject(s2).optJSONObject("data");
                    String strOptString = jSONObjectOptJSONObject.optString("avatar");
                    String strOptString2 = jSONObjectOptJSONObject.optString("describe");
                    String strOptString3 = jSONObjectOptJSONObject.optString("knowledge_img_dark");
                    String strOptString4 = jSONObjectOptJSONObject.optString("knowledge_img");
                    String strOptString5 = jSONObjectOptJSONObject.optString("detail_img");
                    String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, KnowledgeMapAct.this);
                    if (!TextUtils.isEmpty(strOptString5)) {
                        SharePreferencesUtils.writeStrConfig(CommonParameter.FILTER_OPTION_DETAIL_IMG + strConfig, strOptString5, KnowledgeMapAct.this);
                    }
                    SharePreferencesUtils.writeStrConfig(CommonParameter.KNOWLEDGE_FILTER_TIPS_ICON, strOptString, KnowledgeMapAct.this);
                    SharePreferencesUtils.writeStrConfig(CommonParameter.KNOWLEDGE_FILTER_TIPS_TEXT, strOptString2, KnowledgeMapAct.this);
                    SharePreferencesUtils.writeStrConfig(CommonParameter.KNOWLEDGE_FILTER_TIPS_DESC, strOptString4, KnowledgeMapAct.this);
                    SharePreferencesUtils.writeStrConfig(CommonParameter.KNOWLEDGE_FILTER_TIPS_DESC_DARK, strOptString3, KnowledgeMapAct.this);
                    List list = (List) new Gson().fromJson(jSONObjectOptJSONObject.optString("list"), new TypeToken<List<ChartFilterBean>>() { // from class: com.psychiatrygarden.activity.mine.knowledge.KnowledgeMapAct.3.1
                    }.getType());
                    for (int i2 = 0; i2 < list.size(); i2++) {
                        List<ChartFilterBean.ChartFilterValue> value = ((ChartFilterBean) list.get(i2)).getValue();
                        String type = ((ChartFilterBean) list.get(i2)).getType();
                        for (int i3 = 0; i3 < value.size(); i3++) {
                            value.get(i3).setType(type);
                        }
                    }
                    KnowledgeMapAct.this.chartFilterList.addAll(list);
                    KnowledgeMapAct.this.showFilterPoP();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private void getPointData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("id", this.mParentId);
        ajaxParams.put("type", this.mType + "");
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.knowlwdgeMapPoint, ajaxParams, new AnonymousClass2());
    }

    private void initTableTitle(TextView mTvAllNumName, TextView mTvStudyNumName, TextView mTvPercent, TextView mTvRightPercent) {
        if (ScreenUtil.isTablet(this)) {
            mTvAllNumName.setText("考点总数");
            mTvStudyNumName.setText("已学考点数");
            mTvPercent.setText("考点覆盖率");
            mTvRightPercent.setText("刷题正确率");
            return;
        }
        mTvAllNumName.setText("考点\n总数");
        mTvStudyNumName.setText("已学考\n点数");
        mTvPercent.setText("考点\n覆盖率");
        mTvRightPercent.setText("刷题正\n确率");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$putDataH5$12(String str) {
        LogUtils.e("reveice_data_from_js", "获取到的参数====》" + str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$0(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$1(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        if (this.chartFilterList.size() == 0) {
            getFilterData();
        } else {
            showFilterPoP();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$10(View view) {
        openVip();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$setListenerForWidget$2(KnowledgeMapBean.KnowledgeMapItemDataBean knowledgeMapItemDataBean, KnowledgeMapBean.KnowledgeMapItemDataBean knowledgeMapItemDataBean2) throws NumberFormatException {
        int i2 = Integer.parseInt(knowledgeMapItemDataBean.getPractice_rate().replace("%", ""));
        int i3 = Integer.parseInt(knowledgeMapItemDataBean2.getPractice_rate().replace("%", ""));
        return this.isSortPoint ? i2 - i3 : i3 - i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$3(View view) {
        this.mImgRightPercent.setImageResource(R.mipmap.ic_knowlwdge_filter_default);
        this.isSortPercent = true;
        Collections.sort(this.partList, new Comparator() { // from class: com.psychiatrygarden.activity.mine.knowledge.n
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return this.f12898c.lambda$setListenerForWidget$2((KnowledgeMapBean.KnowledgeMapItemDataBean) obj, (KnowledgeMapBean.KnowledgeMapItemDataBean) obj2);
            }
        });
        this.mLyAddPartView.removeAllViews();
        for (int i2 = 0; i2 < this.partList.size(); i2++) {
            KnowledgePartItemView knowledgePartItemView = new KnowledgePartItemView(this);
            knowledgePartItemView.setData(this.mType, this.partList.get(i2));
            this.mLyAddPartView.addView(knowledgePartItemView);
        }
        this.mImgPercent.setImageResource(this.isSortPoint ? R.mipmap.ic_knowlwdge_filter_down : R.mipmap.ic_knowlwdge_filter_up);
        this.isSortPoint = true ^ this.isSortPoint;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$setListenerForWidget$4(KnowledgeMapBean.KnowledgeMapItemDataBean knowledgeMapItemDataBean, KnowledgeMapBean.KnowledgeMapItemDataBean knowledgeMapItemDataBean2) throws NumberFormatException {
        int i2 = Integer.parseInt(knowledgeMapItemDataBean.getUser_correct_rate().replace("%", ""));
        int i3 = Integer.parseInt(knowledgeMapItemDataBean2.getUser_correct_rate().replace("%", ""));
        return this.isSortPercent ? i2 - i3 : i3 - i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$5(View view) {
        this.mImgPercent.setImageResource(R.mipmap.ic_knowlwdge_filter_default);
        this.isSortPoint = true;
        Collections.sort(this.partList, new Comparator() { // from class: com.psychiatrygarden.activity.mine.knowledge.m
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return this.f12895c.lambda$setListenerForWidget$4((KnowledgeMapBean.KnowledgeMapItemDataBean) obj, (KnowledgeMapBean.KnowledgeMapItemDataBean) obj2);
            }
        });
        this.mLyAddPartView.removeAllViews();
        for (int i2 = 0; i2 < this.partList.size(); i2++) {
            KnowledgePartItemView knowledgePartItemView = new KnowledgePartItemView(this);
            knowledgePartItemView.setData(this.mType, this.partList.get(i2));
            this.mLyAddPartView.addView(knowledgePartItemView);
        }
        this.mImgRightPercent.setImageResource(this.isSortPercent ? R.mipmap.ic_knowlwdge_filter_down : R.mipmap.ic_knowlwdge_filter_up);
        this.isSortPercent = true ^ this.isSortPercent;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$6(View view) {
        if (TextUtils.isEmpty(this.mPartId)) {
            return;
        }
        SharePreferencesUtils.writeStrConfig(CommonParameter.KNOWLEDGE_PART_ID, this.mPartId, this);
        SharePreferencesUtils.writeStrConfig("KNOWLEDGE_CHAPTER_ID", this.mChapterId, this);
        EventBus.getDefault().post("jump2KnowledgeChapter");
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$7(View view) {
        openVip();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$8(View view) {
        openVip();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$9(View view) {
        openVip();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showFilterPoP$11(Map map) throws JSONException {
        Iterator it;
        Iterator it2;
        this.defaultFilterMap.clear();
        if (map.isEmpty()) {
            this.defaultFilterMap.clear();
            this.filterItemAdp.setNewData(new ArrayList());
        } else {
            ArrayList arrayList = new ArrayList();
            Iterator it3 = map.entrySet().iterator();
            while (it3.hasNext()) {
                Map.Entry entry = (Map.Entry) it3.next();
                String str = (String) entry.getKey();
                String str2 = (String) entry.getValue();
                ArrayList arrayList2 = new ArrayList();
                if (str2.contains(",")) {
                    for (String str3 : str2.split(",")) {
                        arrayList2.add(str3);
                    }
                } else {
                    arrayList2.add(str2);
                }
                this.defaultFilterMap.put(str, arrayList2);
                for (ChartFilterBean chartFilterBean : this.chartFilterList) {
                    if (!chartFilterBean.getType().equals(str)) {
                        it = it3;
                    } else if (str2.contains(",")) {
                        String[] strArrSplit = str2.split(",");
                        ArrayList arrayList3 = new ArrayList();
                        String str4 = "";
                        int i2 = 0;
                        while (i2 < strArrSplit.length) {
                            String str5 = strArrSplit[i2];
                            for (ChartFilterBean.ChartFilterValue chartFilterValue : chartFilterBean.getValue()) {
                                if (chartFilterValue.getKey().equals(str5)) {
                                    StringBuilder sb = new StringBuilder();
                                    it2 = it3;
                                    sb.append(chartFilterValue.getName());
                                    sb.append("/");
                                    String string = sb.toString();
                                    if (i2 == strArrSplit.length - 1) {
                                        string = chartFilterValue.getName() + chartFilterValue.getTitle();
                                    }
                                    str4 = str4 + string;
                                } else {
                                    it2 = it3;
                                }
                                it3 = it2;
                            }
                            LogUtils.e("filter_value", "value_list====>" + new Gson().toJson(arrayList3));
                            i2++;
                            it3 = it3;
                        }
                        it = it3;
                        arrayList.add(str4);
                    } else {
                        it = it3;
                        for (int i3 = 0; i3 < chartFilterBean.getValue().size(); i3++) {
                            if (chartFilterBean.getValue().get(i3).getKey().equals(str2)) {
                                String str6 = chartFilterBean.getValue().get(i3).getName() + chartFilterBean.getValue().get(i3).getTitle();
                                LogUtils.e("filter_value", "value====>" + str6);
                                arrayList.add(str6);
                            }
                        }
                    }
                    it3 = it;
                }
            }
            this.filterItemAdp.setNewData(arrayList);
        }
        if (this.filterItemAdp.getData().isEmpty()) {
            this.rvFilter.setVisibility(8);
            this.mTvFilterDesc.setVisibility(0);
        } else {
            this.rvFilter.setVisibility(0);
            this.mTvFilterDesc.setVisibility(8);
        }
        JSONObject jSONObject = new JSONObject();
        for (Map.Entry entry2 : map.entrySet()) {
            try {
                jSONObject.put((String) entry2.getKey(), (String) entry2.getValue());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        if (jSONObject.length() > 0) {
            getData(jSONObject.toString());
            putDataH5(jSONObject.toString());
        } else {
            getData("");
            putDataH5("");
        }
    }

    public static void newIntent(Context context, int type, String parentId, String partId, String chapterId) {
        Intent intent = new Intent(context, (Class<?>) KnowledgeMapAct.class);
        intent.putExtra("type", type);
        intent.putExtra("parentId", parentId);
        intent.putExtra("partId", partId);
        intent.putExtra("chapterId", chapterId);
        context.startActivity(intent);
    }

    private void openVip() {
        Intent intent = new Intent(this, (Class<?>) MemberCenterActivity.class);
        intent.putExtra("psotision", 0);
        startActivity(intent);
    }

    private void putDataH5(String str) {
        this.mWebView.evaluateJavascript("javascript:changeEvt(" + str + ")", new ValueCallback() { // from class: com.psychiatrygarden.activity.mine.knowledge.k
            @Override // android.webkit.ValueCallback
            public final void onReceiveValue(Object obj) {
                KnowledgeMapAct.lambda$putDataH5$12((String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setUpUi(boolean isVip) {
        if (!isVip) {
            this.mLyPart.setVisibility(8);
            this.mLyPoint.setVisibility(8);
            this.horScorll.setVisibility(8);
            this.horScorllDifficult.setVisibility(8);
            this.mLyOpenVip.setVisibility(0);
            this.viewPartVip.setVisibility(0);
            this.viewPointVip.setVisibility(0);
            this.viewFrequencyVip.setVisibility(0);
            this.viewDifficultVip.setVisibility(0);
            this.mLyBtn.setVisibility(8);
            return;
        }
        this.mLyPart.setVisibility(0);
        this.mLyPoint.setVisibility(0);
        this.horScorll.setVisibility(0);
        this.horScorllDifficult.setVisibility(0);
        this.mLyOpenVip.setVisibility(8);
        this.viewPartVip.setVisibility(8);
        this.viewPointVip.setVisibility(8);
        this.viewFrequencyVip.setVisibility(8);
        this.viewDifficultVip.setVisibility(8);
        if (this.mType == 3) {
            this.mLyBtn.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showFilterPoP() {
        new XPopup.Builder(this.mContext).asCustom(new PopKnowledgeChartFilter(this.mContext, this.chartFilterList, this.mTvFilterDesc.getText().toString(), this.defaultFilterMap, new PopKnowledgeChartFilter.ConfirmListener() { // from class: com.psychiatrygarden.activity.mine.knowledge.o
            @Override // com.psychiatrygarden.widget.PopKnowledgeChartFilter.ConfirmListener
            public final void onConfirm(Map map) throws JSONException {
                this.f12901a.lambda$showFilterPoP$11(map);
            }
        })).show();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        int aPPVersionCode;
        int i2;
        this.mType = getIntent().getIntExtra("type", 1);
        this.mParentId = getIntent().getStringExtra("parentId");
        this.mPartId = getIntent().getStringExtra("partId");
        this.mChapterId = getIntent().getStringExtra("chapterId");
        this.mTabbar = (RelativeLayout) findViewById(R.id.tabbar);
        this.mImgBack = (ImageView) findViewById(R.id.iv_actionbar_back);
        this.mNavTitle = (TextView) findViewById(R.id.nav_title_name);
        this.mScrollView = (NestedScrollView) findViewById(R.id.scrollview);
        this.mBtnFilter = (ImageView) findViewById(R.id.btn_filter);
        this.mWebView = (WebView) findViewById(R.id.webview);
        this.mViewWeb = (RelativeLayout) findViewById(R.id.view_web);
        this.mLyWebView = (RelativeLayout) findViewById(R.id.ly_webview);
        this.rvFilter = (RecyclerView) findViewById(R.id.rvFilter);
        this.mTvFilterDesc = (TextView) findViewById(R.id.tv_kc_desc);
        int statusBarHeight = StatusBarUtil.getStatusBarHeight(this.mContext);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mTabbar.getLayoutParams();
        layoutParams.topMargin = statusBarHeight;
        this.mTabbar.setLayoutParams(layoutParams);
        TextView textView = (TextView) findViewById(R.id.tv_part);
        TextView textView2 = (TextView) findViewById(R.id.tv_all_num);
        TextView textView3 = (TextView) findViewById(R.id.tv_study_num);
        TextView textView4 = (TextView) findViewById(R.id.tv_percent);
        TextView textView5 = (TextView) findViewById(R.id.tv_right_percent);
        this.mLyPercent = (LinearLayout) findViewById(R.id.ly_percent);
        this.mImgPercent = (ImageView) findViewById(R.id.img_percent);
        this.mLyRightPercent = (LinearLayout) findViewById(R.id.ly_right_percent);
        this.mImgRightPercent = (ImageView) findViewById(R.id.img_right_percent);
        this.mLyAddPartView = (LinearLayout) findViewById(R.id.ly_add_part_view);
        this.mLyAddPercentView = (LinearLayout) findViewById(R.id.ly_add_percent_view);
        TextView textView6 = (TextView) findViewById(R.id.tv_part_category);
        TextView textView7 = (TextView) findViewById(R.id.tv_knowledge_point);
        TextView textView8 = (TextView) findViewById(R.id.tv_study_progress);
        this.mLyDesc = (LinearLayout) findViewById(R.id.ly_desc);
        this.mTvDescInfo = (TextView) findViewById(R.id.tv_desc_info);
        this.mLyBtn = (RelativeLayout) findViewById(R.id.ly_btn);
        this.mLyPoint = (LinearLayout) findViewById(R.id.ly_point);
        this.mBtnDoQuestion = (TextView) findViewById(R.id.btn_do);
        this.horScorll = (HorizontalScrollView) findViewById(R.id.hor_frequency);
        this.horScorllDifficult = (HorizontalScrollView) findViewById(R.id.hor_difficult);
        this.viewDifficultVip = (KnowledgeMaskView) findViewById(R.id.view_difficulty_vip);
        this.mLyAddDifficultView = (LinearLayout) findViewById(R.id.ly_add_difficulty);
        this.pointsProgressView = (KnowledgePointsProgressView) findViewById(R.id.progress_view);
        this.mTvPointAllNum = (TextView) findViewById(R.id.tv_all_number);
        this.mTvPointStudyNum = (TextView) findViewById(R.id.tv_study_num_two);
        this.mTvPointUserPercent = (TextView) findViewById(R.id.tv_personal_percent);
        this.mTvPointPercent = (TextView) findViewById(R.id.tv_all_percent);
        this.mLyAddFrequencyView = (LinearLayout) findViewById(R.id.ly_add_frequency);
        this.mLyOpenVip = (LinearLayout) findViewById(R.id.ly_open_vip);
        this.mBtnOpenVip = (TextView) findViewById(R.id.btn_open_vip);
        this.mLyPart = (LinearLayout) findViewById(R.id.ly_part);
        this.viewPartVip = (KnowledgeMaskView) findViewById(R.id.view_part_vip);
        this.viewPointVip = (KnowledgeMaskView) findViewById(R.id.view_point_vip);
        this.viewFrequencyVip = (KnowledgeMaskView) findViewById(R.id.view_frequency_vip);
        this.viewPartVip.setData(1);
        this.viewPointVip.setData(2);
        this.viewFrequencyVip.setData(3);
        int screenWidth = (UIUtil.getScreenWidth(this) - UIUtil.dip2px(this, 48.0d)) / 6;
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) textView.getLayoutParams();
        layoutParams2.width = screenWidth * 2;
        textView.setLayoutParams(layoutParams2);
        LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) textView2.getLayoutParams();
        layoutParams3.width = screenWidth;
        textView2.setLayoutParams(layoutParams3);
        textView3.setLayoutParams(layoutParams3);
        this.mLyPercent.setLayoutParams(layoutParams3);
        this.mLyRightPercent.setLayoutParams(layoutParams3);
        this.mWebView.getSettings().setJavaScriptEnabled(true);
        this.mWebView.getSettings().setUseWideViewPort(true);
        this.mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        this.mWebView.getSettings().setDisplayZoomControls(false);
        this.mWebView.getSettings().setAllowFileAccess(true);
        this.mWebView.getSettings().setBuiltInZoomControls(true);
        this.mWebView.getSettings().setLoadWithOverviewMode(true);
        this.mWebView.setHorizontalScrollBarEnabled(true);
        this.mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.mWebView.setVerticalScrollBarEnabled(false);
        this.mWebView.getSettings().setSavePassword(false);
        this.mWebView.getSettings().setCacheMode(2);
        this.mWebView.getSettings().setDomStorageEnabled(true);
        this.mWebView.addJavascriptInterface(new JavaScriptInterface(this.mContext), "Android");
        KnowledgeFilterItemAdp knowledgeFilterItemAdp = new KnowledgeFilterItemAdp();
        this.filterItemAdp = knowledgeFilterItemAdp;
        this.rvFilter.setAdapter(knowledgeFilterItemAdp);
        String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext);
        String strConfig2 = SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, this.mContext);
        String secret = UserConfig.getInstance().getUser().getSecret();
        String token = UserConfig.getInstance().getUser().getToken();
        try {
            aPPVersionCode = AndroidBaseUtils.getAPPVersionCode(this);
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            aPPVersionCode = R2.attr.plvDrag;
        }
        this.mWebView.loadUrl(NetworkRequestsURL.statisticalChatInfoUrl + "pieChart.html?token=" + token + "&app_id=" + strConfig + "&secret=" + secret + "&id=" + this.mParentId + "&subject_id=" + strConfig2 + "&type=" + this.mType + "&user_id=" + UserConfig.getUserId() + "&uuid=" + AndroidBaseUtils.getIMEI(ProjectApp.instance()) + "&version=" + aPPVersionCode);
        this.mWebView.setWebViewClient(new MyWebViewClient());
        LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) this.pointsProgressView.getLayoutParams();
        if (UIUtil.getScreenWidth(this) < CommonUtil.dip2px(this, 320.0f)) {
            layoutParams4.width = UIUtil.getScreenWidth(this);
            i2 = 2;
            layoutParams4.height = UIUtil.getScreenWidth(this) / 2;
            this.pointsProgressView.setLayoutParams(layoutParams4);
        } else {
            i2 = 2;
        }
        int i3 = this.mType;
        if (i3 == 1) {
            textView.setText("部分");
            initTableTitle(textView2, textView3, textView4, textView5);
        } else if (i3 == i2) {
            textView.setText("章");
            initTableTitle(textView2, textView3, textView4, textView5);
        } else if (i3 == 3) {
            textView.setText("节");
            initTableTitle(textView2, textView3, textView4, textView5);
        }
        int screenWidth2 = (UIUtil.getScreenWidth(this) - UIUtil.dip2px(this, 32.0d)) / 5;
        LinearLayout.LayoutParams layoutParams5 = (LinearLayout.LayoutParams) textView6.getLayoutParams();
        layoutParams5.width = (screenWidth2 * 3) / 2;
        textView6.setLayoutParams(layoutParams5);
        textView7.setLayoutParams(layoutParams5);
        LinearLayout.LayoutParams layoutParams6 = (LinearLayout.LayoutParams) textView8.getLayoutParams();
        layoutParams6.width = screenWidth2 * 2;
        textView8.setLayoutParams(layoutParams6);
        getData("");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setStatusBarTranslucent(this, false);
        StatusBarCompat.setLightStatusBar(this, false);
        this.mActionBar.hide();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        WebView webView = this.mWebView;
        if (webView != null) {
            ViewParent parent = webView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(this.mWebView);
            }
            this.mWebView.stopLoading();
            this.mWebView.getSettings().setJavaScriptEnabled(false);
            this.mWebView.clearHistory();
            this.mWebView.clearView();
            this.mWebView.removeAllViews();
            this.mWebView.destroy();
            this.mWebView = null;
        }
        super.onDestroy();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if (str.equals("jump2KnowledgeChapter")) {
            finish();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layout_knowledge_map);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mImgBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.knowledge.p
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12902c.lambda$setListenerForWidget$0(view);
            }
        });
        this.mBtnFilter.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.knowledge.q
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12903c.lambda$setListenerForWidget$1(view);
            }
        });
        this.mLyPercent.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.knowledge.r
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12904c.lambda$setListenerForWidget$3(view);
            }
        });
        this.mLyRightPercent.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.knowledge.s
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12905c.lambda$setListenerForWidget$5(view);
            }
        });
        this.mBtnDoQuestion.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.knowledge.t
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12906c.lambda$setListenerForWidget$6(view);
            }
        });
        this.mBtnOpenVip.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.knowledge.u
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12907c.lambda$setListenerForWidget$7(view);
            }
        });
        this.viewPartVip.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.knowledge.v
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12908c.lambda$setListenerForWidget$8(view);
            }
        });
        this.viewPointVip.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.knowledge.w
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12909c.lambda$setListenerForWidget$9(view);
            }
        });
        this.viewFrequencyVip.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.knowledge.l
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12893c.lambda$setListenerForWidget$10(view);
            }
        });
    }

    @Subscribe
    public void onEventMainThread(BuyVipSuccessEvent event) {
        if (event.getSuccess()) {
            UserConfig.getInstance().getUser().setIs_vip("1");
            this.mIsVip = true;
            this.mLyPart.setVisibility(0);
            this.mLyPoint.setVisibility(0);
            this.horScorll.setVisibility(0);
            this.mLyOpenVip.setVisibility(8);
            this.viewPartVip.setVisibility(8);
            this.viewPointVip.setVisibility(8);
            this.viewFrequencyVip.setVisibility(8);
            if (this.mType == 3) {
                this.mLyBtn.setVisibility(0);
            }
        }
    }
}
