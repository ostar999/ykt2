package com.psychiatrygarden.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.mine.knowledge.MockKnowledgePointStatisticsAct;
import com.psychiatrygarden.activity.vip.Utils.MemInterface;
import com.psychiatrygarden.adapter.QuestListAdapter;
import com.psychiatrygarden.adapter.SubQueDaAdapter;
import com.psychiatrygarden.bean.BatchDataBean;
import com.psychiatrygarden.bean.ExesQuestionBean;
import com.psychiatrygarden.bean.StatDataBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.onDialogClickListener;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.DesUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.CusomNewDialog;
import com.psychiatrygarden.widget.DialogInput;
import com.psychiatrygarden.widget.ViewPagerCompat;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class SubQuestionCeshiDaActivity extends BaseActivity {
    private TextView btn_comment;
    private boolean from_my_exam;
    private boolean isSuccess;
    private QuestListAdapter mAdapter;
    private BatchDataBean mBatchDataBean;
    private StatDataBean mStatDataBean;
    private SubQueDaAdapter msAdapter;
    private ViewPagerCompat questiondetails_viewPager;
    private RelativeLayout relgufenlb;
    private RelativeLayout relgufenpm;
    private RelativeLayout relgufentj;
    private ArrayMap<String, List<ExesQuestionBean>> shareStemMap;
    List<ExesQuestionBean> questBeans = new ArrayList();
    List<ExesQuestionBean> questBeansOPtion = new ArrayList();
    List<ExesQuestionBean> questBeansshow = new ArrayList();
    private int position = 0;
    private int moudle_type = 5;
    private int typePoition = 1;
    private String bindSchool = "";
    private int right = 0;
    private List<ExesQuestionBean> shareStemExamList = new ArrayList();

    @SuppressLint({"HandlerLeak"})
    private final Handler mHandler = new Handler() { // from class: com.psychiatrygarden.activity.SubQuestionCeshiDaActivity.1
        @Override // android.os.Handler
        public void handleMessage(@NonNull Message msg) throws Resources.NotFoundException {
            int i2;
            List list;
            int actualStemIndex;
            super.handleMessage(msg);
            if (msg.what != 2 || (i2 = msg.arg1) >= SubQuestionCeshiDaActivity.this.questBeans.size() || SubQuestionCeshiDaActivity.this.shareStemExamList == null || SubQuestionCeshiDaActivity.this.shareStemExamList.isEmpty()) {
                return;
            }
            String public_number = SubQuestionCeshiDaActivity.this.questBeans.get(i2).getPublic_number();
            if (TextUtils.isEmpty(public_number)) {
                for (int i3 = 0; i3 < SubQuestionCeshiDaActivity.this.shareStemExamList.size(); i3++) {
                    if (TextUtils.equals(((ExesQuestionBean) SubQuestionCeshiDaActivity.this.shareStemExamList.get(i3)).getQuestion_id(), SubQuestionCeshiDaActivity.this.questBeans.get(i2).getQuestion_id())) {
                        SubQuestionCeshiDaActivity.this.questiondetails_viewPager.setCurrentItem(i3, false);
                        return;
                    }
                }
                return;
            }
            for (int i4 = 0; i4 < SubQuestionCeshiDaActivity.this.shareStemExamList.size(); i4++) {
                ExesQuestionBean exesQuestionBean = (ExesQuestionBean) SubQuestionCeshiDaActivity.this.shareStemExamList.get(i4);
                View pageView = SubQuestionCeshiDaActivity.this.msAdapter.getPageView(i4);
                if (TextUtils.equals(exesQuestionBean.getPublic_number(), public_number) && (list = (List) SubQuestionCeshiDaActivity.this.shareStemMap.get(public_number)) != null) {
                    Iterator it = list.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            actualStemIndex = 0;
                            break;
                        }
                        ExesQuestionBean exesQuestionBean2 = (ExesQuestionBean) it.next();
                        if (TextUtils.equals(exesQuestionBean2.getQuestion_id(), SubQuestionCeshiDaActivity.this.questBeans.get(i2).getQuestion_id())) {
                            actualStemIndex = exesQuestionBean2.getActualStemIndex();
                            break;
                        }
                    }
                    if (pageView == null) {
                        ((ExesQuestionBean) SubQuestionCeshiDaActivity.this.shareStemExamList.get(i4)).setJumpPosition(actualStemIndex);
                        SubQuestionCeshiDaActivity.this.questiondetails_viewPager.setCurrentItem(i4, false);
                    } else {
                        ViewPager viewPager = (ViewPager) pageView.findViewById(R.id.viewpager);
                        List list2 = (List) SubQuestionCeshiDaActivity.this.shareStemMap.get(public_number);
                        if (list2 == null) {
                            return;
                        }
                        int i5 = 0;
                        while (true) {
                            if (i5 >= list2.size()) {
                                break;
                            }
                            if (((ExesQuestionBean) list2.get(i5)).getActualStemIndex() == SubQuestionCeshiDaActivity.this.questBeans.get(i2).getActualStemIndex()) {
                                SubQuestionCeshiDaActivity.this.questiondetails_viewPager.setCurrentItem(i4, false);
                                viewPager.setCurrentItem(i5, false);
                                break;
                            }
                            i5++;
                        }
                    }
                }
            }
        }
    };
    private boolean loadFinish = true;

    private void clickBtnAction(boolean isRanking) {
        if (SharePreferencesUtils.readStrConfig("statisticsPermission", this, "0").equals("0")) {
            String strConfig = SharePreferencesUtils.readStrConfig("statisticsActiveId", this, "");
            AjaxParams ajaxParams = new AjaxParams();
            ajaxParams.put(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, strConfig);
            MemInterface.getInstance().getMemData(this, ajaxParams, true, 0);
            MemInterface.getInstance().setDisType(1);
            MemInterface.getInstance().setmUShareListener(new MemInterface.UShareListener() { // from class: com.psychiatrygarden.activity.zk
                @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.UShareListener
                public final void mUShareListener() {
                    this.f14254a.lambda$clickBtnAction$1();
                }
            });
            return;
        }
        if (!isRanking) {
            getScore();
            return;
        }
        Intent intent = new Intent(this.mContext, (Class<?>) RankingActivity.class);
        intent.putExtra("title", getIntent().getExtras().getString("title"));
        intent.putExtra("exam_id", getIntent().getExtras().getString("exam_id"));
        intent.putExtra("is_school_rank", getIntent().getExtras().getString("is_school_rank") + "");
        intent.putExtra("bindSchool", this.bindSchool);
        intent.putExtra("from_my_exam", this.from_my_exam);
        startActivity(intent);
    }

    private String getCurrentQuestionId() {
        ExesQuestionBean exesQuestionBean = this.shareStemExamList.get(this.questiondetails_viewPager.getCurrentItem());
        if (TextUtils.isEmpty(exesQuestionBean.getPublic_number())) {
            return exesQuestionBean.getQuestion_id();
        }
        int currentShareStemQuestionPosition = this.msAdapter.getCurrentShareStemQuestionPosition();
        List<ExesQuestionBean> list = this.shareStemMap.get(exesQuestionBean.getPublic_number());
        if (list != null) {
            for (int i2 = 0; i2 < list.size(); i2++) {
                if (i2 == currentShareStemQuestionPosition) {
                    return list.get(i2).getQuestion_id();
                }
            }
        }
        return null;
    }

    private void getQuestionData() {
        YJYHttpUtils.get(getApplicationContext(), getIntent().getExtras().getString("question_file"), new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.SubQuestionCeshiDaActivity.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                SubQuestionCeshiDaActivity.this.hideProgressDialog();
                SubQuestionCeshiDaActivity.this.AlertToast("请求服务器失败，请重新打开本页");
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                SubQuestionCeshiDaActivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                super.onSuccess((AnonymousClass3) t2);
                try {
                    if (SubQuestionCeshiDaActivity.this.from_my_exam) {
                        JSONArray jSONArray = new JSONArray(t2);
                        int length = jSONArray.length();
                        Gson gson = new Gson();
                        ArrayList arrayList = null;
                        int i2 = 0;
                        while (i2 < length) {
                            JSONObject jSONObjectOptJSONObject = jSONArray.optJSONObject(i2);
                            String strOptString = jSONObjectOptJSONObject.optString(Constants.PARAMS_CONSTANTS.PARAMS_TOPIC_OPTION);
                            if (!TextUtils.isEmpty(strOptString)) {
                                arrayList = (ArrayList) gson.fromJson(strOptString, new TypeToken<ArrayList<ExesQuestionBean.OptionBean>>() { // from class: com.psychiatrygarden.activity.SubQuestionCeshiDaActivity.3.1
                                }.getType());
                                jSONObjectOptJSONObject.remove(Constants.PARAMS_CONSTANTS.PARAMS_TOPIC_OPTION);
                            }
                            ExesQuestionBean exesQuestionBean = (ExesQuestionBean) gson.fromJson(jSONObjectOptJSONObject.toString(), ExesQuestionBean.class);
                            if (arrayList != null) {
                                exesQuestionBean.setOption(arrayList);
                            }
                            i2++;
                            exesQuestionBean.setNumber(String.valueOf(i2));
                            SubQuestionCeshiDaActivity.this.questBeans.add(exesQuestionBean);
                        }
                    } else {
                        String strDecode = DesUtil.decode(CommonParameter.DES_KEY_VERIFY, new JSONObject(t2).optString("data"));
                        Gson gson2 = new Gson();
                        SubQuestionCeshiDaActivity.this.questBeans = (List) gson2.fromJson(strDecode, new TypeToken<List<ExesQuestionBean>>() { // from class: com.psychiatrygarden.activity.SubQuestionCeshiDaActivity.3.2
                        }.getType());
                        Collections.sort(SubQuestionCeshiDaActivity.this.questBeans);
                    }
                    SubQuestionCeshiDaActivity.this.getBatchData();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleExamQuestion() {
        for (int i2 = 0; i2 < this.questBeans.size(); i2++) {
            String public_number = this.questBeans.get(i2).getPublic_number();
            this.questBeans.get(i2).setActualStemIndex(i2);
            if (TextUtils.isEmpty(public_number)) {
                this.shareStemExamList.add(this.questBeans.get(i2));
            } else {
                if (this.shareStemMap.get(public_number) == null) {
                    this.shareStemExamList.add(this.questBeans.get(i2));
                    this.shareStemMap.put(public_number, new ArrayList());
                }
                this.shareStemMap.get(public_number).add(this.questBeans.get(i2));
            }
        }
    }

    public static void i(String tag, String msg) {
        int length = 2001 - tag.length();
        while (msg.length() > length) {
            Log.i(tag, msg.substring(0, length));
            msg = msg.substring(length);
        }
        Log.i(tag, msg);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$clickBtnAction$1() {
        SharePreferencesUtils.writeStrConfig("statisticsPermission", "1", this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$2(View view) {
        showSelectPop();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$3(View view) {
        clickBtnAction(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$4(View view) {
        clickBtnAction(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$5(String str, String str2, String str3) {
        Bundle bundle = new Bundle();
        bundle.putString("b_img", str2);
        bundle.putString("s_img", str3);
        bundle.putString("content", str);
        bundle.putInt("result", 1);
        if (!UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            pushComment(bundle);
            return;
        }
        Intent intent = new Intent(this.mContext, (Class<?>) CorpCupActivity.class);
        intent.putExtra("bundleIntent", bundle);
        startActivityForResult(intent, 1, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$6(View view) {
        Context context = this.mContext;
        onDialogClickListener ondialogclicklistener = new onDialogClickListener() { // from class: com.psychiatrygarden.activity.al
            @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
            public final void onclickStringBack(String str, String str2, String str3) {
                this.f10998a.lambda$setListenerForWidget$5(str, str2, str3);
            }
        };
        int i2 = this.moudle_type;
        boolean z2 = true;
        if (i2 != 1 && i2 != 4) {
            z2 = false;
        }
        new DialogInput(context, ondialogclicklistener, z2).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showGrade$0(AlertDialog alertDialog, View view) {
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.isTrueCeshi, false, this.mContext);
        alertDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSelectPop$7(TextView textView, View view) {
        if (this.typePoition == 1) {
            this.mAdapter.updateData(this.questBeansOPtion);
            this.msAdapter.updateData(this.questBeansOPtion);
            this.typePoition = 0;
            textView.setText("查看全部");
            textView.setTextColor(ContextCompat.getColor(this, SkinManager.getCurrentSkinType(this.mContext) == 0 ? R.color.gray_line_new2 : R.color.jiucuo_night));
            return;
        }
        this.mAdapter.updateData(this.questBeansshow);
        this.msAdapter.updateData(this.questBeansshow);
        this.typePoition = 1;
        textView.setText("只看错题");
        textView.setTextColor(ContextCompat.getColor(this, SkinManager.getCurrentSkinType(this.mContext) == 0 ? R.color.app_theme_red : R.color.red_theme_night));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSelectPop$8(AlertDialog alertDialog, AdapterView adapterView, View view, int i2, long j2) {
        Message message = new Message();
        message.what = 2;
        message.arg1 = i2;
        this.mHandler.sendMessage(message);
        alertDialog.dismiss();
    }

    private void pushComment(Bundle b3) {
        AjaxParams ajaxParams = new AjaxParams();
        String currentQuestionId = getCurrentQuestionId();
        if (currentQuestionId == null) {
            return;
        }
        ajaxParams.put("obj_id", currentQuestionId);
        ajaxParams.put("content", b3.getString("content"));
        ajaxParams.put("module_type", String.valueOf(this.moudle_type));
        ajaxParams.put("comment_type", "2");
        String string = b3.getString("b_img");
        String string2 = b3.getString("s_img");
        if (!TextUtils.isEmpty(string)) {
            if (string.contains("http")) {
                ajaxParams.put("b_img", string);
                ajaxParams.put("s_img", string2);
            } else {
                ajaxParams.put("video_id", b3.getString("b_img"));
            }
        }
        if (UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            ajaxParams.put("virtual_user_id", b3.getString("virtual_user_id"));
        }
        showProgressDialog("发布中");
        YJYHttpUtils.post(this.mContext.getApplicationContext(), NetworkRequestsURL.mPutComment, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.SubQuestionCeshiDaActivity.6
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                SubQuestionCeshiDaActivity.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass6) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        SharePreferencesUtils.writeStrConfig(CommonParameter.PINGLUNTXT, "", SubQuestionCeshiDaActivity.this.mContext);
                        SubQuestionCeshiDaActivity.this.mHandler.sendEmptyMessage(5);
                        SubQuestionCeshiDaActivity.this.AlertToast(jSONObject.optString("message"));
                        EventBus.getDefault().post(EventBusConstant.EVENT_DIALOG_INPUT_DISMISS);
                        CommonUtil.showFristDialog(jSONObject);
                    } else if (jSONObject.optString("code").equals("401")) {
                        new CusomNewDialog(SubQuestionCeshiDaActivity.this.mContext).setMessage(jSONObject.optString("message")).show();
                    } else {
                        SubQuestionCeshiDaActivity.this.AlertToast(jSONObject.optString("message"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                SubQuestionCeshiDaActivity.this.hideProgressDialog();
            }
        });
    }

    private void showGrade() {
        final AlertDialog alertDialogCreate = new AlertDialog.Builder(this.mContext, R.style.MyDialog).create();
        alertDialogCreate.show();
        alertDialogCreate.setCanceledOnTouchOutside(false);
        Window window = alertDialogCreate.getWindow();
        window.setGravity(17);
        window.setContentView(R.layout.activity_showceshi);
        WindowManager.LayoutParams attributes = alertDialogCreate.getWindow().getAttributes();
        attributes.height = -1;
        attributes.width = -1;
        alertDialogCreate.getWindow().setAttributes(attributes);
        LogUtils.e("right", "dis====>" + this.right + ";===" + this.relgufenpm.getRight());
        TextView textView = (TextView) alertDialogCreate.findViewById(R.id.tv_ok);
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.yk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14221c.lambda$showGrade$0(alertDialogCreate, view);
            }
        });
    }

    public void getBatchData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("exam_id", getIntent().getExtras().getString("exam_id"));
        YJYHttpUtils.post(this.mContext, this.from_my_exam ? NetworkRequestsURL.GET_USER_ANSWER_RECORD : NetworkRequestsURL.mgetBatchUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.SubQuestionCeshiDaActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                SubQuestionCeshiDaActivity.this.hideProgressDialog();
                SubQuestionCeshiDaActivity.this.AlertToast("请求服务器失败，请重新打开本页");
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    SubQuestionCeshiDaActivity.this.mBatchDataBean = (BatchDataBean) new Gson().fromJson(s2, BatchDataBean.class);
                    if (SubQuestionCeshiDaActivity.this.mBatchDataBean.getCode().equals("200")) {
                        List<BatchDataBean.DataBean> data = SubQuestionCeshiDaActivity.this.mBatchDataBean.getData();
                        for (int i2 = 0; i2 < data.size(); i2++) {
                            for (int i3 = 0; i3 < SubQuestionCeshiDaActivity.this.questBeans.size(); i3++) {
                                if (data.get(i2).getQuestion_id().equals(SubQuestionCeshiDaActivity.this.questBeans.get(i3).getQuestion_id())) {
                                    char[] charArray = data.get(i2).getAnswer().replace(",", "").toCharArray();
                                    char[] charArray2 = SubQuestionCeshiDaActivity.this.questBeans.get(i3).getAnswer().replace(",", "").toCharArray();
                                    Arrays.sort(charArray);
                                    Arrays.sort(charArray2);
                                    SubQuestionCeshiDaActivity.this.questBeans.get(i3).setIsRight(TextUtils.equals(new String(charArray), new String(charArray2)) ? "1" : "0");
                                    SubQuestionCeshiDaActivity.this.questBeans.get(i3).setOwnAns(data.get(i2).getAnswer());
                                }
                            }
                        }
                        SubQuestionCeshiDaActivity.this.questBeansshow.clear();
                        SubQuestionCeshiDaActivity subQuestionCeshiDaActivity = SubQuestionCeshiDaActivity.this;
                        subQuestionCeshiDaActivity.questBeansshow.addAll(subQuestionCeshiDaActivity.questBeans);
                        new Gson().toJson(SubQuestionCeshiDaActivity.this.questBeans);
                        SubQuestionCeshiDaActivity.this.handleExamQuestion();
                        Log.e("review_question", "shareStemExamList_size=" + SubQuestionCeshiDaActivity.this.shareStemExamList.size() + ";questionSize=" + SubQuestionCeshiDaActivity.this.questBeans.size());
                        SubQuestionCeshiDaActivity subQuestionCeshiDaActivity2 = SubQuestionCeshiDaActivity.this;
                        subQuestionCeshiDaActivity2.msAdapter = new SubQueDaAdapter(subQuestionCeshiDaActivity2.mContext, subQuestionCeshiDaActivity2.shareStemExamList, SubQuestionCeshiDaActivity.this.moudle_type, SubQuestionCeshiDaActivity.this.from_my_exam);
                        SubQuestionCeshiDaActivity.this.msAdapter.setShareStemMap(SubQuestionCeshiDaActivity.this.shareStemMap);
                        SubQuestionCeshiDaActivity.this.questiondetails_viewPager.setAdapter(SubQuestionCeshiDaActivity.this.msAdapter);
                        SubQuestionCeshiDaActivity.this.questiondetails_viewPager.setOffscreenPageLimit(1);
                        List<ExesQuestionBean> list = SubQuestionCeshiDaActivity.this.questBeans;
                        if (list != null && list.size() > 0) {
                            SubQuestionCeshiDaActivity subQuestionCeshiDaActivity3 = SubQuestionCeshiDaActivity.this;
                            subQuestionCeshiDaActivity3.getStatsData(subQuestionCeshiDaActivity3.questBeans.get(0).getQuestion_id());
                        }
                    } else {
                        SubQuestionCeshiDaActivity subQuestionCeshiDaActivity4 = SubQuestionCeshiDaActivity.this;
                        subQuestionCeshiDaActivity4.AlertToast(subQuestionCeshiDaActivity4.mBatchDataBean.getMessage());
                    }
                    SubQuestionCeshiDaActivity.this.hideProgressDialog();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public String[] getExamQuestionInfo() {
        String[] strArr = new String[3];
        this.questBeansOPtion.clear();
        int i2 = 0;
        int i3 = 0;
        for (ExesQuestionBean exesQuestionBean : this.questBeansshow) {
            if ("1".equals(exesQuestionBean.getIsRight())) {
                i2++;
            } else {
                i3++;
                this.questBeansOPtion.add(exesQuestionBean);
            }
        }
        strArr[0] = String.valueOf(0.0d);
        strArr[1] = String.valueOf(i2);
        strArr[2] = String.valueOf(i3);
        return strArr;
    }

    public void getScore() {
        ProjectApp.questExamList.clear();
        ProjectApp.questExamList.addAll(this.questBeans);
        MockKnowledgePointStatisticsAct.newIntent(this, getIntent().getExtras().getString("exam_id"), this.bindSchool, getIntent().getExtras().getString("title"), getIntent().getExtras().getString("is_esaydta") + "", getIntent().getExtras().getString("is_school_rank") + "");
    }

    public void getStatsData(String question_id) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("exam_id", getIntent().getExtras().getString("exam_id"));
        ajaxParams.put("question_id", question_id);
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.statOneUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.SubQuestionCeshiDaActivity.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass4) s2);
                try {
                    SubQuestionCeshiDaActivity.this.mStatDataBean = (StatDataBean) new Gson().fromJson(s2, StatDataBean.class);
                    if (SubQuestionCeshiDaActivity.this.mStatDataBean.getCode().equals("200")) {
                        SubQuestionCeshiDaActivity.this.isSuccess = true;
                        StatDataBean.DataBean data = SubQuestionCeshiDaActivity.this.mStatDataBean.getData();
                        for (int i2 = 0; i2 < SubQuestionCeshiDaActivity.this.questBeans.size(); i2++) {
                            if (data.getQuestion_id().equals(SubQuestionCeshiDaActivity.this.questBeans.get(i2).getQuestion_id())) {
                                SubQuestionCeshiDaActivity.this.questBeans.get(i2).setRight_count(data.getRight_count());
                                SubQuestionCeshiDaActivity.this.questBeans.get(i2).setWrong_count(data.getWrong_count());
                                SubQuestionCeshiDaActivity.this.questBeans.get(i2).setComment_count(data.getComment_count());
                            }
                        }
                    }
                    if (SubQuestionCeshiDaActivity.this.loadFinish) {
                        SubQuestionCeshiDaActivity.this.msAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e2) {
                    SubQuestionCeshiDaActivity.this.isSuccess = false;
                    e2.printStackTrace();
                }
                SubQuestionCeshiDaActivity.this.btn_comment.setEnabled(SubQuestionCeshiDaActivity.this.isSuccess);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() throws Resources.NotFoundException {
        if (this.from_my_exam) {
            this.relgufentj.setVisibility(8);
        } else if (getIntent().getBooleanExtra("isTestEntrance", false)) {
            this.questBeans.clear();
            this.questBeans.addAll(ProjectApp.questExamList);
            this.questBeansshow.clear();
            this.questBeansshow.addAll(this.questBeans);
            handleExamQuestion();
            SubQueDaAdapter subQueDaAdapter = new SubQueDaAdapter(this.mContext, this.shareStemExamList, this.moudle_type, this.from_my_exam);
            this.msAdapter = subQueDaAdapter;
            subQueDaAdapter.setShareStemMap(this.shareStemMap);
            this.questiondetails_viewPager.setAdapter(this.msAdapter);
            this.questiondetails_viewPager.setOffscreenPageLimit(1);
            List<ExesQuestionBean> list = this.questBeans;
            if (list != null && list.size() > 0) {
                getStatsData(this.questBeans.get(0).getQuestion_id());
            }
            this.relgufentj.setVisibility(8);
            this.relgufenpm.setVisibility(8);
        } else {
            List<ExesQuestionBean> list2 = ProjectApp.questExamDataList;
            if (list2 == null || list2.size() <= 0) {
                getQuestionData();
            } else {
                this.questBeans.clear();
                this.questBeans.addAll(ProjectApp.questExamDataList);
                showProgressDialog();
                getBatchData();
            }
        }
        this.questiondetails_viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.activity.SubQuestionCeshiDaActivity.5
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int state) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int arg0) {
                Log.e("review_question", "id=" + ((ExesQuestionBean) SubQuestionCeshiDaActivity.this.shareStemExamList.get(arg0)).getQuestion_id() + ",pos=" + arg0 + ";index=" + ((ExesQuestionBean) SubQuestionCeshiDaActivity.this.shareStemExamList.get(arg0)).getActualStemIndex());
                SubQuestionCeshiDaActivity.this.position = arg0;
                if (SubQuestionCeshiDaActivity.this.shareStemExamList == null || SubQuestionCeshiDaActivity.this.shareStemExamList.size() <= 0) {
                    return;
                }
                String public_number = ((ExesQuestionBean) SubQuestionCeshiDaActivity.this.shareStemExamList.get(SubQuestionCeshiDaActivity.this.position)).getPublic_number();
                if (TextUtils.isEmpty(public_number)) {
                    SubQuestionCeshiDaActivity.this.loadFinish = true;
                    if (((ExesQuestionBean) SubQuestionCeshiDaActivity.this.shareStemExamList.get(SubQuestionCeshiDaActivity.this.position)).getWrong_count() == 0 || ((ExesQuestionBean) SubQuestionCeshiDaActivity.this.shareStemExamList.get(SubQuestionCeshiDaActivity.this.position)).getRight_count() == 0) {
                        SubQuestionCeshiDaActivity subQuestionCeshiDaActivity = SubQuestionCeshiDaActivity.this;
                        subQuestionCeshiDaActivity.getStatsData(((ExesQuestionBean) subQuestionCeshiDaActivity.shareStemExamList.get(SubQuestionCeshiDaActivity.this.position)).getQuestion_id());
                        return;
                    }
                    return;
                }
                List<ExesQuestionBean> stemQuestionList = SubQuestionCeshiDaActivity.this.msAdapter.getStemQuestionList(public_number);
                int i2 = 0;
                while (i2 < stemQuestionList.size()) {
                    if (stemQuestionList.get(i2).getWrong_count() == 0 || stemQuestionList.get(i2).getRight_count() == 0) {
                        SubQuestionCeshiDaActivity.this.loadFinish = i2 == stemQuestionList.size() - 1;
                        SubQuestionCeshiDaActivity.this.getStatsData(stemQuestionList.get(i2).getQuestion_id());
                    }
                    i2++;
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == 1) {
            pushComment(data.getBundleExtra("bundleIntent"));
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.mHandler.removeCallbacksAndMessages(null);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if (str.equals("bindSuccess")) {
            this.bindSchool = "1";
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setSwipeBackEnable(false);
        this.shareStemMap = new ArrayMap<>();
        setTitle(getIntent().getExtras().getString("title"));
        this.from_my_exam = getIntent().getBooleanExtra("from_my_exam", false);
        this.bindSchool = getIntent().getExtras().getString("bindSchool");
        this.mBtnActionbarRight.setVisibility(8);
        setContentView(R.layout.activity_sub_question_ceshi_da);
        this.btn_comment = (TextView) findViewById(R.id.btn_comment);
        ViewPagerCompat viewPagerCompat = (ViewPagerCompat) findViewById(R.id.questiondetails_viewPager);
        this.questiondetails_viewPager = viewPagerCompat;
        viewPagerCompat.setSaveEnabled(false);
        this.relgufentj = (RelativeLayout) findViewById(R.id.relgufentj);
        this.relgufenpm = (RelativeLayout) findViewById(R.id.relgufenpm);
        this.relgufenlb = (RelativeLayout) findViewById(R.id.relgufenlb);
        this.btn_comment.setVisibility(this.from_my_exam ? 8 : 0);
        this.moudle_type = 5;
        if (getIntent().getExtras().getBoolean("istongji", false) || !SharePreferencesUtils.readBooleanConfig(CommonParameter.isTrueCeshi, true, this.mContext)) {
            return;
        }
        showGrade();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.relgufenlb.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.dl
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12250c.lambda$setListenerForWidget$2(view);
            }
        });
        this.relgufenpm.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.el
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12325c.lambda$setListenerForWidget$3(view);
            }
        });
        this.relgufentj.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.fl
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12361c.lambda$setListenerForWidget$4(view);
            }
        });
        this.btn_comment.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.gl
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12449c.lambda$setListenerForWidget$6(view);
            }
        });
    }

    public void showSelectPop() {
        List<ExesQuestionBean> list;
        final AlertDialog alertDialogCreate = new AlertDialog.Builder(this.mContext, R.style.MyDialog).create();
        alertDialogCreate.show();
        WindowManager.LayoutParams attributes = alertDialogCreate.getWindow().getAttributes();
        attributes.width = -1;
        if (getIntent().getBooleanExtra("isTestEntrance", false)) {
            attributes.height = ((CommonUtil.getScreenHeight(this.mContext) / 4) * 2) + 100;
        } else {
            attributes.height = ((CommonUtil.getScreenHeight(this.mContext) / 5) * 3) + 100;
        }
        alertDialogCreate.getWindow().setAttributes(attributes);
        Window window = alertDialogCreate.getWindow();
        alertDialogCreate.setCanceledOnTouchOutside(true);
        window.setContentView(R.layout.activity_questlist);
        window.setGravity(80);
        window.setWindowAnimations(R.style.mystyle);
        GridView gridView = (GridView) alertDialogCreate.findViewById(R.id.gridView1);
        RelativeLayout relativeLayout = (RelativeLayout) alertDialogCreate.findViewById(R.id.relda);
        RelativeLayout relativeLayout2 = (RelativeLayout) alertDialogCreate.findViewById(R.id.reljj);
        relativeLayout.setVisibility(0);
        relativeLayout2.setVisibility(8);
        LinearLayout linearLayout = (LinearLayout) alertDialogCreate.findViewById(R.id.linview);
        TextView textView = (TextView) alertDialogCreate.findViewById(R.id.scoreStr);
        TextView textView2 = (TextView) alertDialogCreate.findViewById(R.id.rightNum);
        TextView textView3 = (TextView) alertDialogCreate.findViewById(R.id.wrongNum);
        final TextView textView4 = (TextView) alertDialogCreate.findViewById(R.id.seeWrong);
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            linearLayout.setBackgroundResource(R.drawable.background_view_rounded_top);
        } else {
            linearLayout.setBackgroundResource(R.drawable.background_view_rounded_top_night);
        }
        List<ExesQuestionBean> list2 = this.questBeansshow;
        if (list2 != null && list2.size() > 0) {
            String stringExtra = getIntent().getStringExtra("score");
            Locale locale = Locale.CHINA;
            Object[] objArr = new Object[1];
            if (stringExtra == null) {
                stringExtra = "0";
            }
            objArr[0] = stringExtra;
            textView.setText(String.format(locale, "%s分", objArr));
            textView2.setText(getExamQuestionInfo()[1]);
            textView3.setText(getExamQuestionInfo()[2]);
            textView4.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.bl
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f11121c.lambda$showSelectPop$7(textView4, view);
                }
            });
        }
        if (this.typePoition == 1) {
            textView4.setText("只看错题");
            list = this.questBeansshow;
        } else {
            textView4.setText("查看全部");
            list = this.questBeansOPtion;
        }
        QuestListAdapter questListAdapter = new QuestListAdapter(this.mContext, list, this.mHandler, 2);
        this.mAdapter = questListAdapter;
        gridView.setAdapter((ListAdapter) questListAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.psychiatrygarden.activity.cl
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i2, long j2) {
                this.f11628c.lambda$showSelectPop$8(alertDialogCreate, adapterView, view, i2, j2);
            }
        });
    }
}
