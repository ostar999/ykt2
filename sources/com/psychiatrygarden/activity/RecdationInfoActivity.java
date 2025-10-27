package com.psychiatrygarden.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.hutool.core.text.StrPool;
import com.aliyun.vod.common.utils.UriUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.makeramen.roundedimageview.RoundedImageView;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.comment.DiscussPublicActivity;
import com.psychiatrygarden.activity.comment.bean.DiscussStatus;
import com.psychiatrygarden.activity.online.AnswerQuestionActivity;
import com.psychiatrygarden.activity.online.AnswerSheetActivity;
import com.psychiatrygarden.activity.online.adapter.AnswerSheetAdapter;
import com.psychiatrygarden.activity.online.bean.QuestionDetailBean;
import com.psychiatrygarden.activity.online.bean.QuestionListBean;
import com.psychiatrygarden.bean.EventBusMessage;
import com.psychiatrygarden.bean.QuestioBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.QuestionDataRequest;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.QuestionDataCallBack;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.DateTimeUtilKt;
import com.psychiatrygarden.utils.DesUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.utils.WeakHandler;
import com.psychiatrygarden.widget.CommonLoadingPop;
import com.psychiatrygarden.widget.CustomDialog;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.psychiatrygarden.widget.SelectModePop;
import com.psychiatrygarden.widget.ShowVideoDialog;
import com.psychiatrygarden.widget.glideUtil.GlideImageView;
import com.psychiatrygarden.widget.glideUtil.transformation.BlurTransformation;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.apache.http.cookie.ClientCookie;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class RecdationInfoActivity extends BaseActivity implements QuestionDataCallBack<String>, OnItemClickListener {
    private static final int UPDATE_TIME_WHAT = 10011;
    private AnswerSheetAdapter answerSheetAdapter;
    private CustomEmptyView emptyView;
    private String is_show_number;
    private boolean iscoment;
    private boolean ispraise;
    private LinearLayout layoutLiveBottom;
    private LinearLayout llay_top_one;
    private CommonLoadingPop loadingPop;
    private WeakHandler mHandler;
    private QuestioBean questioBean;
    private RecyclerView questionList_GridView;
    private RelativeLayout reldddd;
    private GlideImageView shoucang;
    private TextView timrs;
    private TextView tvLiveButton;
    private TextView unreadnum;
    private List<QuestionDetailBean> showList = new ArrayList();
    private String is_collected = "0";
    double errorCount = 0.0d;
    double rightCount = 0.0d;
    int noDo = 0;

    private void dialogDismiss() {
        CommonLoadingPop commonLoadingPop = this.loadingPop;
        if (commonLoadingPop != null) {
            commonLoadingPop.dismiss();
        }
    }

    private void getPingyuData(String zhengquelvValue, final TextView descr) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("scoring_rate", "" + zhengquelvValue);
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.mremarkUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.RecdationInfoActivity.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                try {
                    descr.setText(new JSONObject(s2).optJSONObject("data").optString("remark").toString());
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private void getScoreNewData(String totalValue, String zuoguo, String rightValue, String wrongValue, String zhengquelvValue) {
        ColorStateList colorStateList;
        ColorStateList colorStateList2;
        SpannableStringBuilder spannableStringBuilder;
        String str;
        String string;
        TextView textView;
        SpannableStringBuilder spannableStringBuilder2;
        final AlertDialog alertDialogCreate = new AlertDialog.Builder(this.mContext, R.style.MyDialog).create();
        alertDialogCreate.show();
        alertDialogCreate.setCanceledOnTouchOutside(false);
        Window window = alertDialogCreate.getWindow();
        window.setGravity(17);
        window.setContentView(R.layout.acticity_xuekeceping);
        WindowManager.LayoutParams attributes = alertDialogCreate.getWindow().getAttributes();
        attributes.height = CommonUtil.getScreenHeight(this.mContext);
        attributes.width = CommonUtil.getScreenWidth(this.mContext);
        alertDialogCreate.getWindow().setAttributes(attributes);
        TextView textView2 = (TextView) alertDialogCreate.findViewById(R.id.xuekeid);
        TextView textView3 = (TextView) alertDialogCreate.findViewById(R.id.chapter_id);
        TextView textView4 = (TextView) alertDialogCreate.findViewById(R.id.total);
        TextView textView5 = (TextView) alertDialogCreate.findViewById(R.id.right);
        TextView textView6 = (TextView) alertDialogCreate.findViewById(R.id.wrong);
        TextView textView7 = (TextView) alertDialogCreate.findViewById(R.id.zhengquelv);
        TextView textView8 = (TextView) alertDialogCreate.findViewById(R.id.descr);
        TextView textView9 = (TextView) alertDialogCreate.findViewById(R.id.zuoguo);
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            colorStateList = ContextCompat.getColorStateList(this.mContext, R.color.app_theme_red);
            colorStateList2 = ContextCompat.getColorStateList(this.mContext, R.color.green);
        } else {
            colorStateList = ContextCompat.getColorStateList(this.mContext, R.color.red_theme_night);
            colorStateList2 = ContextCompat.getColorStateList(this.mContext, R.color.green_theme_night);
        }
        ColorStateList colorStateList3 = colorStateList2;
        ((ImageView) alertDialogCreate.findViewById(R.id.close)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.qg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                alertDialogCreate.dismiss();
            }
        });
        getPingyuData(zhengquelvValue + "", textView8);
        SpannableStringBuilder spannableStringBuilder3 = new SpannableStringBuilder("做 " + zuoguo + " 题");
        spannableStringBuilder3.setSpan(new TextAppearanceSpan(null, 0, 0, colorStateList3, null), (r2.length() - 2) - zuoguo.length(), r2.length() - 1, 34);
        SpannableStringBuilder spannableStringBuilder4 = new SpannableStringBuilder("对 " + rightValue + " 题");
        spannableStringBuilder4.setSpan(new TextAppearanceSpan(null, 0, 0, colorStateList3, null), (r2.length() - 2) - rightValue.length(), r2.length() - 1, 34);
        SpannableStringBuilder spannableStringBuilder5 = new SpannableStringBuilder("错 " + wrongValue + " 题");
        spannableStringBuilder5.setSpan(new TextAppearanceSpan(null, 0, 0, colorStateList, null), (r2.length() - 2) - wrongValue.length(), r2.length() - 1, 34);
        if (Double.parseDouble(zhengquelvValue) >= 60.0d) {
            spannableStringBuilder2 = new SpannableStringBuilder("正确率 " + zhengquelvValue + " %");
            spannableStringBuilder = spannableStringBuilder5;
            spannableStringBuilder2.setSpan(new TextAppearanceSpan(null, 0, 0, colorStateList3, null), (r2.length() - 2) - zhengquelvValue.length(), r2.length() - 1, 34);
            textView = textView9;
            str = "";
        } else {
            spannableStringBuilder = spannableStringBuilder5;
            if (Double.parseDouble(zhengquelvValue) != 0.0d) {
                str = "";
                string = (100.0d - Double.parseDouble(zhengquelvValue)) + str;
            } else if (Integer.parseInt(wrongValue) == 0) {
                string = "0";
                str = "";
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(100.0d - Double.parseDouble(zhengquelvValue));
                str = "";
                sb.append(str);
                string = sb.toString();
            }
            SpannableStringBuilder spannableStringBuilder6 = new SpannableStringBuilder("错误率 " + string + " %");
            textView = textView9;
            spannableStringBuilder6.setSpan(new TextAppearanceSpan(null, 0, 0, colorStateList, null), (r2.length() - 2) - string.length(), r2.length() - 1, 34);
            spannableStringBuilder2 = spannableStringBuilder6;
        }
        textView2.setText(getIntent().getStringExtra("title") + str);
        textView3.setText(str);
        textView4.setText("共 " + totalValue + " 题");
        textView5.setText(spannableStringBuilder4);
        textView6.setText(spannableStringBuilder);
        textView7.setText(spannableStringBuilder2);
        textView.setText(spannableStringBuilder3);
        textView8.setText("革命尚未成功，同志仍需努力！");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$clearnDialog$15(CustomDialog customDialog, View view) {
        postRedoValue();
        customDialog.dismissNoAnimaltion();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDataView$1(View view) {
        if (!isLogin() || TextUtils.equals(getIntent().getExtras().getString("author_id"), "0")) {
            return;
        }
        Intent intent = new Intent(this, (Class<?>) UserCommentInfoActivity.class);
        intent.putExtra("user_id", getIntent().getExtras().getString("author_id"));
        intent.putExtra("jiav", "");
        intent.addFlags(67108864);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDataView$10(View view) {
        mJumpToQList("praise", "我的点赞");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDataView$11(View view) {
        mJumpToQList("note", "我的笔记");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDataView$12(View view) throws JSONException {
        if (SharePreferencesUtils.readBooleanConfig(CommonParameter.isceshitidan, false, this.mContext)) {
            submitAnswer("1");
        } else {
            finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDataView$13(View view) {
        if (this.is_collected.equals("1")) {
            putCollectData("0");
            this.shoucang.setImageResource(R.drawable.shoucang);
            this.is_collected = "0";
        } else {
            putCollectData("1");
            this.shoucang.setImageResource(R.drawable.shoucangtwo);
            this.is_collected = "1";
        }
        EventBus.getDefault().post(EventBusConstant.EVENT_QUESTION_SHEET_COLLECT);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDataView$2(View view) {
        Intent intent = new Intent(this, (Class<?>) DiscussPublicActivity.class);
        intent.putExtra("obj_id", getIntent().getExtras().getString("collection_id", "0") + "");
        intent.putExtra("module_type", 13);
        intent.putExtra("comment_type", "2");
        intent.putExtra("isCommentTrue", this.iscoment);
        intent.putExtra("isZantongTrue", this.ispraise);
        intent.putExtra("title", "" + getIntent().getExtras().getString("title"));
        intent.putExtra("commentEnum", DiscussStatus.CommentsOnTheEaluationModelTestQuestionnaire);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDataView$3(View view) {
        Intent intent = new Intent(this, (Class<?>) RecdationDetailActivity.class);
        intent.putExtra("title", "" + getIntent().getStringExtra("title"));
        intent.putExtra("description", "" + getIntent().getStringExtra("description"));
        intent.putExtra("cover_img", "" + getIntent().getStringExtra("cover_img"));
        intent.putExtra("times", "" + this.timrs.getText().toString());
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDataView$4(View view) {
        clearnDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDataView$5(View view) {
        new XPopup.Builder(this.mContext).moveUpToKeyboard(Boolean.FALSE).asCustom(new SelectModePop(this.mContext)).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDataView$6(TextView textView, AppBarLayout appBarLayout, int i2) {
        float f2 = i2 * 1.0f;
        this.llay_top_one.setAlpha(1.0f - Math.abs(f2 / appBarLayout.getTotalScrollRange()));
        this.reldddd.setAlpha(1.0f - Math.abs(f2 / appBarLayout.getTotalScrollRange()));
        if (1.0f - Math.abs(f2 / appBarLayout.getTotalScrollRange()) == 0.0f) {
            this.llay_top_one.setVisibility(8);
            this.reldddd.setVisibility(8);
        } else {
            this.llay_top_one.setVisibility(0);
            this.reldddd.setVisibility(0);
        }
        int totalScrollRange = appBarLayout.getTotalScrollRange();
        if (i2 == 0) {
            textView.setVisibility(4);
        } else if (Math.abs(i2) >= totalScrollRange) {
            textView.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDataView$7(View view) {
        mJumpToQList("error", "我的错题");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDataView$8(View view) {
        mJumpToQList("collection", "我的收藏");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDataView$9(View view) {
        mJumpToQList(ClientCookie.COMMENT_ATTR, "我的评论");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(Message message) {
        if (message.what == 10011) {
            try {
                QuestioBean questioBean = this.questioBean;
                if (questioBean != null && "0".equals(questioBean.getData().getLive_about().getLive_status()) && this.tvLiveButton != null) {
                    this.tvLiveButton.setText(DateTimeUtilKt.getTimeFromInt(Long.parseLong(this.questioBean.getData().getLive_about().getLive_info().getStart_live_time()) - (System.currentTimeMillis() / 1000)));
                }
            } catch (Exception e2) {
                Log.d("RedactionActivity", "handlerMessage: " + e2.getMessage());
            }
            this.mHandler.sendEmptyMessageDelayed(10011, 1000L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setLiveButton$19(String str, QuestioBean questioBean, String str2, View view) {
        if ("3".equals(str)) {
            ToastUtil.shortToast(this, "剪辑未完成");
            return;
        }
        if ("2".equals(str)) {
            if (TextUtils.isEmpty(questioBean.getData().getLive_about().getLive_info().getVid())) {
                return;
            }
            ShowVideoDialog.newInstance(this.mContext, questioBean.getData().getLive_about().getLive_info().getVid(), "0").showDialog(this.mContext, getWindow().getDecorView());
        } else {
            boolean z2 = "0".equals(str) && (((Long.parseLong(questioBean.getData().getLive_about().getLive_info().getStart_live_time()) - (System.currentTimeMillis() / 1000)) > 1800L ? 1 : ((Long.parseLong(questioBean.getData().getLive_about().getLive_info().getStart_live_time()) - (System.currentTimeMillis() / 1000)) == 1800L ? 0 : -1)) <= 0);
            if ("1".equals(str) || z2) {
                CommonUtil.launchLiving(this, questioBean.getData().getLive_about().getLive_ini().getPolyv_user_id(), questioBean.getData().getLive_about().getLive_ini().getPolyv_app_id(), questioBean.getData().getLive_about().getLive_ini().getPolyv_app_secret(), questioBean.getData().getLive_about().getLive_info().getRoom_id(), "0", questioBean.getData().getLive_about().getLive_info().getLive_id());
            } else {
                NewToast.showShort(this, str2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$submitAnswer$16(CustomDialog customDialog, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        customDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$submitAnswer$17(CustomDialog customDialog, DecimalFormat decimalFormat, double d3, JSONArray jSONArray, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        customDialog.dismissNoAnimaltion();
        getScoreNewData(this.showList.size() + "", replacevalue((this.errorCount + this.rightCount) + ""), replacevalue(this.rightCount + ""), replacevalue(this.errorCount + ""), replacevalue(decimalFormat.format(d3)));
        postAnswer(jSONArray.toString());
    }

    private void postAnswer(String answer) {
        this.answerSheetAdapter.notifyDataSetChanged();
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("answer", answer);
        ajaxParams.put("module_type", "1");
        QuestionDataRequest.getIntance(this).questionPutAnswerData(ajaxParams, this);
    }

    private void postRedoValue() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(UriUtil.QUERY_CATEGORY, "unit");
        ajaxParams.put("module_type", "1");
        ajaxParams.put("obj_id", "0");
        ajaxParams.put("unit_id", "" + getIntent().getStringExtra("collection_id"));
        QuestionDataRequest.getIntance(this).clearAnswer(ajaxParams, this);
    }

    private void setEmptyView(boolean isError) {
        if (isError) {
            this.emptyView.setLoadFileResUi(this);
            this.answerSheetAdapter.setEmptyView(this.emptyView);
        } else {
            this.emptyView.uploadEmptyViewResUi();
            this.answerSheetAdapter.setEmptyView(this.emptyView);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setLiveButton(final QuestioBean dataBean) {
        if (dataBean != null) {
            try {
                if (dataBean.getData() != null && dataBean.getData().getLive_about() != null) {
                    boolean z2 = SkinManager.getCurrentSkinType(this) == 0;
                    final String live_status = dataBean.getData().getLive_about().getLive_status();
                    String show_live_button = dataBean.getData().getLive_about().getShow_live_button();
                    final String live_tips = dataBean.getData().getLive_about().getLive_tips();
                    if ("".equals(live_status) || "0".equals(show_live_button)) {
                        this.layoutLiveBottom.setVisibility(8);
                    } else {
                        this.layoutLiveBottom.setVisibility(0);
                        if ("1".equals(live_status) || "2".equals(live_status)) {
                            this.tvLiveButton.setBackgroundResource(z2 ? R.drawable.linetype3_new : R.drawable.linetype3_night_new);
                            this.tvLiveButton.setTextColor(ContextCompat.getColor(this, z2 ? R.color.white : R.color.line_txt_color_night));
                        } else {
                            this.tvLiveButton.setBackgroundResource(R.drawable.shape_mo_kao_btn_bg_gray);
                            this.tvLiveButton.setTextColor(SkinManager.getThemeColor(this, R.attr.forth_txt_color));
                        }
                    }
                    if (!TextUtils.isEmpty(live_tips) && !"0".equals(live_status)) {
                        this.tvLiveButton.setText(live_tips);
                    }
                    if ("0".equals(live_status)) {
                        this.mHandler.sendEmptyMessageDelayed(10011, 1000L);
                    }
                    this.tvLiveButton.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.xg
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f14182c.lambda$setLiveButton$19(live_status, dataBean, live_tips, view);
                        }
                    });
                }
            } catch (Exception e2) {
                Log.d(this.TAG, "setLiveButton: " + e2.getMessage());
            }
        }
    }

    private void showDialog() {
        if (this.loadingPop == null) {
            this.loadingPop = (CommonLoadingPop) new XPopup.Builder(this).dismissOnBackPressed(Boolean.TRUE).dismissOnTouchOutside(Boolean.FALSE).asCustom(new CommonLoadingPop(this));
        }
        this.loadingPop.show();
    }

    private void submitAnswer(String todo) throws JSONException {
        if (!CommonUtil.isNetworkConnected(this)) {
            AlertToast("请您检查网络是否连接");
            return;
        }
        final JSONArray jSONArray = new JSONArray();
        this.errorCount = 0.0d;
        this.rightCount = 0.0d;
        int i2 = 0;
        this.noDo = 0;
        int i3 = 0;
        while (i3 < this.showList.size()) {
            try {
                String str = "0";
                if (TextUtils.isEmpty(this.showList.get(i3).getUser_answer())) {
                    String str2 = "";
                    for (int i4 = i2; i4 < this.showList.get(i3).getOption().size(); i4++) {
                        if (this.showList.get(i3).getOption().get(i4).getType().equals("1")) {
                            str2 = str2 + this.showList.get(i3).getOption().get(i4).getKey();
                        }
                    }
                    this.showList.get(i3).setUser_answer(str2);
                    if (TextUtils.isEmpty(str2)) {
                        this.noDo++;
                    } else {
                        JSONObject jSONObject = new JSONObject();
                        String strTrim = this.showList.get(i3).getAnswer().replace(",", "").trim();
                        jSONObject.put("answer", str2);
                        jSONObject.put("question_id", this.showList.get(i3).getId());
                        if (strTrim.equals(str2)) {
                            this.rightCount += 1.0d;
                            str = "1";
                        } else {
                            this.errorCount += 1.0d;
                        }
                        jSONObject.put("is_right", str);
                        jSONObject.put("app_id", this.showList.get(i3).getApp_id());
                        jSONArray.put(jSONObject);
                    }
                } else if (this.showList.get(i3).getIs_right().equals("0")) {
                    this.errorCount += 1.0d;
                } else if (this.showList.get(i3).getIs_right().equals("1")) {
                    this.rightCount += 1.0d;
                }
                i3++;
                i2 = 0;
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        double d3 = this.errorCount;
        double d4 = this.rightCount;
        final double d5 = d3 + d4 == 0.0d ? 0.0d : (d4 / (d3 + d4)) * 100.0d;
        final DecimalFormat decimalFormat = new DecimalFormat("#0.0");
        if (!todo.equals("1")) {
            if (todo.equals("2")) {
                if (jSONArray.length() > 0) {
                    postAnswer(jSONArray.toString());
                    return;
                }
                return;
            }
            if (todo.equals("3")) {
                getScoreNewData(this.showList.size() + "", replacevalue((this.errorCount + this.rightCount) + ""), replacevalue(this.rightCount + ""), replacevalue(this.errorCount + ""), replacevalue(decimalFormat.format(d5)));
                postAnswer(jSONArray.toString());
                return;
            }
        } else if (jSONArray.length() == 0) {
            finish();
            return;
        }
        final CustomDialog customDialog = new CustomDialog(this.mContext, 2);
        customDialog.setCancelable(false);
        customDialog.isOutTouchDismiss(false);
        if (this.noDo == 0) {
            customDialog.setMessage("确定要交卷吗");
        } else {
            customDialog.setMessage("您还有" + this.noDo + "题没做，确定要交卷吗？");
        }
        customDialog.setNegativeBtn(R.string.cancel, new View.OnClickListener() { // from class: com.psychiatrygarden.activity.vg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                RecdationInfoActivity.lambda$submitAnswer$16(customDialog, view);
            }
        });
        customDialog.setPositiveBtn(R.string.ok, new View.OnClickListener() { // from class: com.psychiatrygarden.activity.wg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14149c.lambda$submitAnswer$17(customDialog, decimalFormat, d5, jSONArray, view);
            }
        });
        customDialog.show();
    }

    public void clearnDialog() {
        final CustomDialog customDialog = new CustomDialog(this.mContext, 2);
        customDialog.setCancelable(false);
        customDialog.setMessage("确定重做本题单下所有题？");
        customDialog.setNegativeBtn(R.string.cancel, new View.OnClickListener() { // from class: com.psychiatrygarden.activity.zg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                customDialog.dismiss();
            }
        });
        customDialog.setPositiveBtn(R.string.ok, new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ah
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f10993c.lambda$clearnDialog$15(customDialog, view);
            }
        });
        customDialog.show();
    }

    public void getCommingNum() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("unit_id", "" + getIntent().getStringExtra("collection_id"));
        YJYHttpUtils.get(this, NetworkRequestsURL.mInfourl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.RecdationInfoActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        RecdationInfoActivity.this.questioBean = (QuestioBean) new Gson().fromJson(s2, QuestioBean.class);
                        int iOptInt = new JSONObject(s2).optJSONObject("data").optInt("comment_count");
                        if (iOptInt == 0) {
                            RecdationInfoActivity.this.unreadnum.setVisibility(8);
                        } else {
                            RecdationInfoActivity.this.unreadnum.setVisibility(0);
                            if (iOptInt > 99) {
                                RecdationInfoActivity.this.unreadnum.setText("99+");
                            } else {
                                RecdationInfoActivity.this.unreadnum.setText(String.valueOf(iOptInt));
                            }
                        }
                        int iOptInt2 = new JSONObject(s2).optJSONObject("data").optInt("is_comment");
                        int iOptInt3 = new JSONObject(s2).optJSONObject("data").optInt("is_praise");
                        RecdationInfoActivity.this.iscoment = iOptInt2 != 0;
                        RecdationInfoActivity.this.ispraise = iOptInt3 != 0;
                        RecdationInfoActivity recdationInfoActivity = RecdationInfoActivity.this;
                        recdationInfoActivity.setLiveButton(recdationInfoActivity.questioBean);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void getQuestionData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("unit_id", "" + getIntent().getStringExtra("collection_id"));
        ajaxParams.put("type", "all");
        ajaxParams.put(UriUtil.QUERY_CATEGORY, "unit");
        ajaxParams.put("primary_id", "0");
        ajaxParams.put("am_pm", "0");
        ajaxParams.put("module_type", "1");
        QuestionDataRequest.getIntance(this).questionList(ajaxParams, "unit", false, this);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.is_collected = getIntent().getExtras().getString("is_collected");
        this.is_show_number = getIntent().getExtras().getString("is_show_number", "");
    }

    public void initDataView() throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        this.emptyView = new CustomEmptyView(this, 0, "暂无数据", R.layout.layout_empty_common_view_half);
        this.layoutLiveBottom = (LinearLayout) findViewById(R.id.layoutLiveBottom);
        this.questionList_GridView = (RecyclerView) findViewById(R.id.questionList_GridView);
        this.tvLiveButton = (TextView) findViewById(R.id.tvLiveButton);
        this.reldddd = (RelativeLayout) findViewById(R.id.reldddd);
        RoundedImageView roundedImageView = (RoundedImageView) findViewById(R.id.headermin);
        GlideImageView glideImageView = (GlideImageView) findViewById(R.id.headerimg);
        GlideImageView glideImageView2 = (GlideImageView) findViewById(R.id.chongzuoimg);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.pinglunrel);
        this.unreadnum = (TextView) findViewById(R.id.unreadnum);
        final TextView textView = (TextView) findViewById(R.id.tv_title);
        GlideImageView glideImageView3 = (GlideImageView) findViewById(R.id.icon_left_back);
        TextView textView2 = (TextView) findViewById(R.id.tv_question_cuoti);
        TextView textView3 = (TextView) findViewById(R.id.tv_question_shoucang);
        TextView textView4 = (TextView) findViewById(R.id.pinglun);
        TextView textView5 = (TextView) findViewById(R.id.dianzan);
        TextView textView6 = (TextView) findViewById(R.id.tv_question_biji);
        GlideImageView glideImageView4 = (GlideImageView) findViewById(R.id.qiehuan);
        this.shoucang = (GlideImageView) findViewById(R.id.shoucang);
        TextView textView7 = (TextView) findViewById(R.id.title);
        TextView textView8 = (TextView) findViewById(R.id.desc);
        this.timrs = (TextView) findViewById(R.id.timrs);
        textView.setText(getIntent().getStringExtra("title"));
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.llay_top_one = (LinearLayout) findViewById(R.id.llay_top_one);
        setStatusBarTranslucent();
        int statusBarHeight = StatusBarUtil.getStatusBarHeight(this.mContext);
        CollapsingToolbarLayout.LayoutParams layoutParams = new CollapsingToolbarLayout.LayoutParams(toolbar.getLayoutParams());
        layoutParams.setMargins(0, statusBarHeight, 0, 0);
        layoutParams.setCollapseMode(1);
        toolbar.setLayoutParams(layoutParams);
        int iDip2px = CommonUtil.dip2px(this.mContext, 68.0f) + statusBarHeight;
        CollapsingToolbarLayout.LayoutParams layoutParams2 = new CollapsingToolbarLayout.LayoutParams(this.reldddd.getLayoutParams());
        layoutParams2.setMargins(0, iDip2px, 0, 0);
        layoutParams2.setCollapseMode(1);
        this.reldddd.setLayoutParams(layoutParams2);
        int iDip2px2 = CommonUtil.dip2px(this.mContext, 100.0f) + CommonUtil.dip2px(this.mContext, 98.0f) + statusBarHeight;
        this.llay_top_one.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        CoordinatorLayout.LayoutParams layoutParams3 = new CoordinatorLayout.LayoutParams(-1, iDip2px2 + this.llay_top_one.getMeasuredHeight() + CommonUtil.dip2px(this.mContext, 20.0f));
        layoutParams3.setBehavior(new AppBarLayout.Behavior());
        appBarLayout.setLayoutParams(layoutParams3);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        toolbar.setTitle("");
        toolbar.setSubtitle("");
        glideImageView2.setImageResource(R.drawable.chonzuoimg);
        collapsingToolbarLayout.setTitle("");
        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this.mContext, R.color.transparent));
        collapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this.mContext, R.color.transparent));
        GlideUtils.loadImage(this, getIntent().getStringExtra("cover_img"), roundedImageView);
        glideImageView.fitCenter().load(getIntent().getStringExtra("cover_img"), R.drawable.imgplacehodel, new BlurTransformation(this, 25, 10));
        textView7.setText(getIntent().getStringExtra("title"));
        textView8.setText(getIntent().getStringExtra("description"));
        if (TextUtils.isEmpty(getIntent().getExtras().getString(SocializeProtocolConstants.AUTHOR))) {
            this.timrs.setVisibility(4);
            if (!TextUtils.isEmpty(getIntent().getExtras().getString("update_time"))) {
                this.timrs.setText("更新时间：" + getIntent().getExtras().getString("update_time"));
            } else if (!TextUtils.isEmpty(getIntent().getExtras().getString("create_time"))) {
                this.timrs.setText("创建时间：" + getIntent().getExtras().getString("create_time"));
            }
        } else {
            this.timrs.setText("题单作者：" + getIntent().getExtras().getString(SocializeProtocolConstants.AUTHOR));
            this.timrs.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.bh
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f11111c.lambda$initDataView$1(view);
                }
            });
        }
        relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.fh
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12357c.lambda$initDataView$2(view);
            }
        });
        textView8.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.gh
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12443c.lambda$initDataView$3(view);
            }
        });
        glideImageView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.hh
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12478c.lambda$initDataView$4(view);
            }
        });
        if (this.is_collected.equals("1")) {
            this.shoucang.setImageResource(R.drawable.shoucangtwo);
        } else {
            this.shoucang.setImageResource(R.drawable.shoucang);
        }
        glideImageView4.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ih
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12512c.lambda$initDataView$5(view);
            }
        });
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() { // from class: com.psychiatrygarden.activity.jh
            @Override // com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener, com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
            public final void onOffsetChanged(AppBarLayout appBarLayout2, int i2) {
                this.f12560a.lambda$initDataView$6(textView, appBarLayout2, i2);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.rg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13799c.lambda$initDataView$7(view);
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.sg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13920c.lambda$initDataView$8(view);
            }
        });
        textView4.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.tg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13953c.lambda$initDataView$9(view);
            }
        });
        textView5.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ug
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13989c.lambda$initDataView$10(view);
            }
        });
        textView6.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ch
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11147c.lambda$initDataView$11(view);
            }
        });
        glideImageView3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.dh
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws JSONException {
                this.f12246c.lambda$initDataView$12(view);
            }
        });
        this.shoucang.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.eh
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12317c.lambda$initDataView$13(view);
            }
        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.questionList_GridView);
        this.questionList_GridView = recyclerView;
        recyclerView.setLayoutManager(new GridLayoutManager(this, 6));
        AnswerSheetAdapter answerSheetAdapter = new AnswerSheetAdapter(this.showList);
        this.answerSheetAdapter = answerSheetAdapter;
        answerSheetAdapter.setOnItemClickListener(this);
        showProgressDialog();
        getQuestionData();
    }

    public void mJumpToQList(String type, String myTitle) {
        if (isLogin()) {
            Bundle bundle = new Bundle();
            bundle.putString("primary_id", "0");
            bundle.putString("unit_id", "" + getIntent().getStringExtra("collection_id"));
            bundle.putString(UriUtil.QUERY_CATEGORY, "unit");
            bundle.putString("module_type", "1");
            bundle.putString("type", type);
            bundle.putString("subject_title", myTitle);
            bundle.putString("chapter_title", "");
            bundle.putString("is_show_number", this.is_show_number);
            AnswerSheetActivity.gotoActivity(this, bundle);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        super.onCreate(savedInstanceState);
        this.mActionBar.hide();
        initDataView();
        this.mHandler = new WeakHandler(this, new WeakHandler.HandlerCallback() { // from class: com.psychiatrygarden.activity.yg
            @Override // com.psychiatrygarden.utils.WeakHandler.HandlerCallback
            public final void handlerMessage(Message message) {
                this.f14217a.lambda$onCreate$0(message);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.mHandler.removeCallbacksAndMessages(null);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBusMessage event) throws JSONException {
        int i2;
        String key = event.getKey();
        key.hashCode();
        i2 = 0;
        switch (key) {
            case "EVENT_QUESTION_HOME_PAGE_REFRESH":
                getQuestionData();
                break;
            case "EVENT_QUESTION_ANSWER_OPTION":
                QuestionDetailBean questionDetailBean = (QuestionDetailBean) event.getValueObj();
                if (questionDetailBean != null) {
                    while (true) {
                        if (i2 < this.showList.size()) {
                            if (questionDetailBean.getId().equals(this.showList.get(i2).getId())) {
                                this.showList.get(i2).setUser_answer(questionDetailBean.getUser_answer());
                                this.showList.get(i2).setStatData(questionDetailBean.getStatData());
                                this.showList.get(i2).setNote(questionDetailBean.getNote());
                                this.showList.get(i2).setOption(questionDetailBean.getOption());
                                this.showList.get(i2).setDoDuration(questionDetailBean.getDoDuration());
                            } else {
                                i2++;
                            }
                        }
                    }
                    this.answerSheetAdapter.notifyDataSetChanged();
                    break;
                }
                break;
            case "EVENT_QUESTION_ANSWER_TEST_STATISTICS":
                submitAnswer("3");
                break;
            case "EVENT_QUESTION_ANSWER_REFRESH":
                QuestionDetailBean questionDetailBean2 = (QuestionDetailBean) event.getValueObj();
                if (questionDetailBean2 != null) {
                    while (true) {
                        if (i2 < this.showList.size()) {
                            if (questionDetailBean2.getId().equals(this.showList.get(i2).getId())) {
                                this.showList.get(i2).setUser_answer(questionDetailBean2.getUser_answer());
                                this.showList.get(i2).setStatData(questionDetailBean2.getStatData());
                                this.showList.get(i2).setNote(questionDetailBean2.getNote());
                            } else {
                                i2++;
                            }
                        }
                    }
                    this.answerSheetAdapter.notifyDataSetChanged();
                    break;
                }
                break;
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onFailure(Throwable t2, int errorNo, String strMsg, String requstUrl) {
        hideProgressDialog();
        AlertToast("请求失败");
    }

    @Override // com.chad.library.adapter.base.listener.OnItemClickListener
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        ProjectApp.showQuestionList = new Gson().toJson(this.showList);
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putString("module_type", "1");
        bundle.putString("subject_title", getIntent().getExtras().getString("title"));
        bundle.putString("type", "all");
        bundle.putString(UriUtil.QUERY_CATEGORY, "unit");
        bundle.putString("total", "" + this.showList.size());
        bundle.putString("unit_id", "" + getIntent().getStringExtra("collection_id"));
        bundle.putString("category_id", "" + getIntent().getStringExtra("category_id"));
        bundle.putString("chapter_id", this.showList.get(position).getChapter_id());
        if (SharePreferencesUtils.readBooleanConfig(CommonParameter.isceshitidan, false, this.mContext)) {
            bundle.putString("answerMode", Constants.ANSWER_MODE.TEST_MODE);
        } else {
            bundle.putString("answerMode", Constants.ANSWER_MODE.PRACTICE_MODE);
        }
        AnswerQuestionActivity.gotoActivity(this, bundle);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) throws JSONException {
        if (keyCode != 4 || event.getRepeatCount() != 0) {
            return false;
        }
        if (SharePreferencesUtils.readBooleanConfig(CommonParameter.isceshitidan, false, this.mContext)) {
            submitAnswer("1");
            return true;
        }
        finish();
        return true;
    }

    @Override // android.app.Activity
    public void onRestart() {
        super.onRestart();
        getQuestionData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        getCommingNum();
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onStart(String requstUrl) {
    }

    public void putCollectData(String operation) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("collection_id", "" + getIntent().getStringExtra("collection_id"));
        ajaxParams.put("operation", "" + operation);
        YJYHttpUtils.post(this, NetworkRequestsURL.mCollectSetUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.RecdationInfoActivity.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                try {
                    ToastUtil.shortToast(RecdationInfoActivity.this, new JSONObject(s2).optString("message"));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public String replacevalue(String s2) {
        return (s2 == null || s2.equals("")) ? "0" : s2.indexOf(StrPool.DOT) > 0 ? s2.replaceAll("0+?$", "").replaceAll("[.]$", "") : s2;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_recdinfo);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }

    public void setStatusBarTranslucent() throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        StatusBarUtil.setStatusBarTranslucent(this, false);
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:46:0x01c6 -> B:48:0x01c9). Please report as a decompilation issue!!! */
    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onSuccess(String str, String requstUrl) throws JSONException {
        if (requstUrl.equals(NetworkRequestsURL.questionListApi) || requstUrl.equals(NetworkRequestsURL.questionSetsList)) {
            try {
                String strDecode = DesUtil.decode(CommonParameter.DES_KEY_VERIFY, new JSONObject(str).optString("data"));
                QuestionListBean questionListBean = new QuestionListBean();
                questionListBean.setCode(new JSONObject(str).optString("code"));
                questionListBean.setData((QuestionListBean.DataDTO) new Gson().fromJson(strDecode, QuestionListBean.DataDTO.class));
                questionListBean.setServer_time(new JSONObject(str).optString("server_time"));
                questionListBean.setMessage(new JSONObject(str).optString("message"));
                if (questionListBean.getCode().equals("200")) {
                    this.showList.clear();
                    this.showList = questionListBean.getData().getList();
                    AjaxParams ajaxParams = new AjaxParams();
                    ajaxParams.put("unit_id", "" + getIntent().getStringExtra("collection_id"));
                    ajaxParams.put("type", "all");
                    ajaxParams.put(UriUtil.QUERY_CATEGORY, "unit");
                    ajaxParams.put("obj_id", "0");
                    ajaxParams.put("module_type", "1");
                    QuestionDataRequest.getIntance(this).questionUserAnswer(ajaxParams, this);
                } else {
                    hideProgressDialog();
                    AlertToast(questionListBean.getMessage());
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            return;
        }
        if (!requstUrl.equals(NetworkRequestsURL.questionUserAnswerApi)) {
            if (requstUrl.equals(NetworkRequestsURL.clearAnswerApi)) {
                hideProgressDialog();
                for (int i2 = 0; i2 < this.showList.size(); i2++) {
                    this.showList.get(i2).setUser_answer("");
                    for (int i3 = 0; i3 < this.showList.get(i2).getOption().size(); i3++) {
                        this.showList.get(i2).getOption().get(i3).setType("0");
                    }
                }
                this.answerSheetAdapter.notifyDataSetChanged();
                return;
            }
            return;
        }
        try {
            hideProgressDialog();
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.optInt("code") != 200) {
                setEmptyView(false);
                AlertToast(jSONObject.optString("message"));
                return;
            }
            JSONArray jSONArray = jSONObject.getJSONArray("data");
            for (int i4 = 0; i4 < jSONArray.length(); i4++) {
                JSONObject jSONObject2 = jSONArray.getJSONObject(i4);
                int i5 = 0;
                while (true) {
                    if (i5 >= this.showList.size()) {
                        break;
                    }
                    if (jSONObject2.optString("question_id").equals(this.showList.get(i5).getId())) {
                        this.showList.get(i5).setUser_answer(jSONObject2.optString("answer"));
                        this.showList.get(i5).setUser_answer(jSONObject2.optString("answer"));
                        break;
                    }
                    i5++;
                }
            }
            this.questionList_GridView.setAdapter(this.answerSheetAdapter);
            this.answerSheetAdapter.setList(this.showList);
            if (this.showList.isEmpty()) {
                setEmptyView(false);
            }
        } catch (Exception e3) {
            setEmptyView(false);
            e3.printStackTrace();
        }
    }
}
