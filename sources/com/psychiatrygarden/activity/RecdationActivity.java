package com.psychiatrygarden.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.hutool.core.text.StrPool;
import com.aliyun.vod.common.utils.UriUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.makeramen.roundedimageview.RoundedImageView;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.comment.DiscussPublicActivity;
import com.psychiatrygarden.activity.comment.bean.DiscussStatus;
import com.psychiatrygarden.activity.online.AnswerSheetActivity;
import com.psychiatrygarden.activity.online.QuestionRecordingActivity;
import com.psychiatrygarden.activity.online.adapter.QuestionBankNewAdapter;
import com.psychiatrygarden.activity.online.bean.QuestionBankNewBean;
import com.psychiatrygarden.activity.online.bean.QuestionBankNewFristBean;
import com.psychiatrygarden.activity.online.bean.QuestionBankNewSecondBean;
import com.psychiatrygarden.bean.NextChapterInfo;
import com.psychiatrygarden.bean.QuestioBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.DateTimeUtilKt;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.utils.WeakHandler;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.psychiatrygarden.widget.SelectModePop;
import com.psychiatrygarden.widget.ShowVideoDialog;
import com.psychiatrygarden.widget.glideUtil.GlideImageView;
import com.psychiatrygarden.widget.glideUtil.transformation.BlurTransformation;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.apache.http.cookie.ClientCookie;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class RecdationActivity extends BaseActivity implements QuestionBankNewAdapter.JumpToQList {
    private static final int UPDATE_TIME_WHAT = 10010;
    public QuestionBankNewAdapter adapter;
    private String category_id;
    private CustomEmptyView emptyView;
    private String is_show_number;
    private boolean iscoment;
    private boolean ispraise;
    private LinearLayout layoutLiveBottom;
    private LinearLayout llay_top_one;
    private WeakHandler mHandler;
    private View mLoadDataFailView;
    private QuestioBean questioBean;
    public RecyclerView recyexpend;
    private RelativeLayout reldddd;
    private ImageView shoucang;
    private TextView timrs;
    private TextView tvLiveButton;
    private TextView tv_title;
    private String unit_id;
    private TextView unreadnum;
    private String is_collected = "0";
    private String mCategory = "unit";
    private String module_type = "1";
    private String type = "all";
    public List<QuestionBankNewBean.DataBean> dataList = new ArrayList();

    /* JADX WARN: Removed duplicated region for block: B:131:0x0364  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x0390 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private com.psychiatrygarden.bean.NextChapterInfo checkHasNextChapter(java.lang.String r17, com.chad.library.adapter.base.entity.node.BaseNode r18) {
        /*
            Method dump skipped, instructions count: 914
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.RecdationActivity.checkHasNextChapter(java.lang.String, com.chad.library.adapter.base.entity.node.BaseNode):com.psychiatrygarden.bean.NextChapterInfo");
    }

    private void extracted(String parent_title) {
        ProjectApp.identity_title = "";
        ProjectApp.unit_title = getIntent().getStringExtra("title");
        ProjectApp.unit_id = this.unit_id;
        ProjectApp.exam_title = "";
        ProjectApp.chapter_title = "";
        ProjectApp.chapter_id = "";
        ProjectApp.chapter_parent_title = parent_title;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<BaseNode> getEntity(List<QuestionBankNewBean.DataBean> dataList) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < dataList.size(); i2++) {
            ArrayList arrayList2 = new ArrayList();
            for (int i3 = 0; i3 < dataList.get(i2).getChildren().size(); i3++) {
                arrayList2.add(new QuestionBankNewSecondBean(dataList.get(i2).getChildren().get(i3)));
            }
            QuestionBankNewFristBean questionBankNewFristBean = new QuestionBankNewFristBean(arrayList2, dataList.get(i2));
            try {
                if (!this.adapter.getData().isEmpty() && (this.adapter.getData().get(i2) instanceof QuestionBankNewFristBean)) {
                    questionBankNewFristBean.setExpanded(((QuestionBankNewFristBean) this.adapter.getData().get(i2)).getIsExpanded());
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            arrayList.add(questionBankNewFristBean);
        }
        return arrayList;
    }

    private void initRecyclerView() {
        this.mLoadDataFailView = View.inflate(this.mContext, R.layout.layout_loaddata_fail_view, null);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        this.recyexpend = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        QuestionBankNewAdapter questionBankNewAdapter = new QuestionBankNewAdapter(this);
        this.adapter = questionBankNewAdapter;
        questionBankNewAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.AlphaIn);
        this.recyexpend.setAdapter(this.adapter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDataView$1(View view) throws JSONException {
        getQuestionBankData(this.mCategory, "", this.type, this.unit_id, this.module_type);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDataView$10(View view) {
        getIntentData("praise");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDataView$11(View view) {
        getIntentData("note");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDataView$12(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDataView$13(boolean z2, View view) {
        if (this.is_collected.equals("1")) {
            putCollectData("0");
            this.shoucang.setImageResource(R.drawable.icon_collection_not_svg_new);
            this.is_collected = "0";
        } else {
            putCollectData("1");
            this.shoucang.setImageResource(z2 ? R.drawable.icon_collect_night_new : R.drawable.icon_collect_day_new);
            this.is_collected = "1";
        }
        EventBus.getDefault().post(EventBusConstant.EVENT_QUESTION_SHEET_COLLECT);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDataView$2(View view) {
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
    public /* synthetic */ void lambda$initDataView$5(View view) {
        new XPopup.Builder(this.mContext).moveUpToKeyboard(Boolean.FALSE).asCustom(new SelectModePop(this.mContext)).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDataView$6(AppBarLayout appBarLayout, int i2) {
        this.reldddd.setAlpha(1.0f - Math.abs((i2 * 1.0f) / appBarLayout.getTotalScrollRange()));
        int totalScrollRange = appBarLayout.getTotalScrollRange();
        if (i2 == 0) {
            this.tv_title.setVisibility(4);
        } else if (Math.abs(i2) >= totalScrollRange) {
            this.tv_title.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDataView$7(View view) {
        getIntentData("error");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDataView$8(View view) {
        getIntentData("collection");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDataView$9(View view) {
        getIntentData(ClientCookie.COMMENT_ATTR);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(Message message) {
        if (message.what == 10010) {
            try {
                QuestioBean questioBean = this.questioBean;
                if (questioBean != null && "0".equals(questioBean.getData().getLive_about().getLive_status()) && this.tvLiveButton != null) {
                    this.tvLiveButton.setText(DateTimeUtilKt.getTimeFromInt(Long.parseLong(this.questioBean.getData().getLive_about().getLive_info().getStart_live_time()) - (System.currentTimeMillis() / 1000)));
                }
            } catch (Exception e2) {
                Log.d("RedactionActivity", "handlerMessage: " + e2.getMessage());
            }
            this.mHandler.sendEmptyMessageDelayed(10010, 1000L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setLiveButton$14(String str, QuestioBean questioBean, String str2, View view) {
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
    public void setEmptyView(boolean isError) {
        if (isError) {
            this.emptyView.setLoadFileResUi(this);
            this.adapter.setEmptyView(this.emptyView);
        } else {
            this.emptyView.uploadEmptyViewResUi();
            this.adapter.setEmptyView(this.emptyView);
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
                        this.mHandler.sendEmptyMessageDelayed(10010, 1000L);
                    }
                    this.tvLiveButton.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.fg
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f12353c.lambda$setLiveButton$14(live_status, dataBean, live_tips, view);
                        }
                    });
                }
            } catch (Exception e2) {
                Log.d(this.TAG, "setLiveButton: " + e2.getMessage());
            }
        }
    }

    public void getCommingNum() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("unit_id", "" + getIntent().getStringExtra("collection_id"));
        YJYHttpUtils.get(this, NetworkRequestsURL.mInfourl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.RecdationActivity.1
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
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        RecdationActivity.this.questioBean = (QuestioBean) new Gson().fromJson(s2, QuestioBean.class);
                        JSONObject jSONObjectOptJSONObject = new JSONObject(s2).optJSONObject("data");
                        if (jSONObjectOptJSONObject == null) {
                            return;
                        }
                        int iOptInt = jSONObjectOptJSONObject.optInt("comment_count");
                        RecdationActivity.this.unreadnum.setVisibility(0);
                        RecdationActivity.this.unreadnum.setText(String.valueOf(iOptInt));
                        int iOptInt2 = jSONObjectOptJSONObject.optInt("is_comment");
                        int iOptInt3 = jSONObjectOptJSONObject.optInt("is_praise");
                        RecdationActivity.this.iscoment = iOptInt2 != 0;
                        RecdationActivity.this.ispraise = iOptInt3 != 0;
                        RecdationActivity recdationActivity = RecdationActivity.this;
                        recdationActivity.setLiveButton(recdationActivity.questioBean);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.online.adapter.QuestionBankNewAdapter.JumpToQList
    public void getExCo() {
    }

    public void getIntentData(String type) {
        if (isLogin()) {
            Intent intent = new Intent(this, (Class<?>) QuestionRecordingActivity.class);
            intent.putExtra(UriUtil.QUERY_CATEGORY, "unit");
            intent.putExtra("module_type", "1");
            intent.putExtra("unit_id", "" + getIntent().getStringExtra("collection_id"));
            intent.putExtra("type", type);
            startActivity(intent);
        }
    }

    @Override // com.psychiatrygarden.activity.online.adapter.QuestionBankNewAdapter.JumpToQList
    public void getPassData(String activity_id) {
    }

    public void getQuestionBankData(final String mCategory, String mIdentityId, final String mType, String mUnitId, String mModuleType) throws JSONException {
        JSONArray jSONArray;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(UriUtil.QUERY_CATEGORY, mCategory);
        ajaxParams.put("identity_id", mIdentityId);
        if (mType.contains("export_")) {
            ajaxParams.put("type", mType.split(StrPool.UNDERLINE)[1]);
        } else {
            ajaxParams.put("type", mType);
        }
        ajaxParams.put("unit_id", mUnitId);
        ajaxParams.put("module_type", mModuleType);
        try {
            if ("unit".equals(mCategory)) {
                ajaxParams.put("am_pm", "0");
                ajaxParams.put("unit_id", mUnitId);
                jSONArray = new JSONArray(SharePreferencesUtils.readStrConfig(CommonParameter.SEARCH_QUESTION_UNIT_ID, this, new JSONArray().toString()));
            } else {
                jSONArray = null;
            }
            JSONArray jSONArray2 = new JSONArray();
            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                String strOptString = jSONArray.optJSONObject(i2).optString("field");
                String strOptString2 = jSONArray.optJSONObject(i2).optString("id");
                if (!strOptString.equals("pattern") && !strOptString2.equals("-1")) {
                    if (strOptString2.equals("free_year")) {
                        jSONArray.getJSONObject(i2).put("id", jSONArray.optJSONObject(i2).optString("free_year"));
                    }
                    jSONArray2.put(jSONArray.get(i2));
                }
            }
            ajaxParams.put("search_where", jSONArray2.toString());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        String str = NetworkRequestsURL.getchapterApi;
        if ("unit".equals(mCategory)) {
            str = NetworkRequestsURL.getIdentityChapter;
        }
        YJYHttpUtils.post(this, str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.RecdationActivity.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                RecdationActivity.this.setEmptyView(true);
                RecdationActivity.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                RecdationActivity.this.hideProgressDialog();
                try {
                    QuestionBankNewBean questionBankNewBean = (QuestionBankNewBean) new Gson().fromJson(s2, QuestionBankNewBean.class);
                    if (!"200".equals(questionBankNewBean.getCode())) {
                        if ("202".equals(questionBankNewBean.getCode())) {
                            if (mCategory.equals("year")) {
                                RecdationActivity.this.dataList.clear();
                                RecdationActivity recdationActivity = RecdationActivity.this;
                                recdationActivity.adapter.setList(recdationActivity.getEntity(recdationActivity.dataList));
                            }
                            RecdationActivity.this.setEmptyView(true);
                            return;
                        }
                        return;
                    }
                    if (questionBankNewBean.getData() == null || questionBankNewBean.getData().size() <= 0) {
                        RecdationActivity.this.dataList.clear();
                        RecdationActivity recdationActivity2 = RecdationActivity.this;
                        recdationActivity2.adapter.setList(recdationActivity2.getEntity(recdationActivity2.dataList));
                        RecdationActivity.this.setEmptyView(false);
                        return;
                    }
                    RecdationActivity.this.dataList = questionBankNewBean.getData();
                    for (int i3 = 0; i3 < RecdationActivity.this.dataList.size(); i3++) {
                        ArrayList arrayList = new ArrayList();
                        if (RecdationActivity.this.dataList.get(i3).getChildren() != null && RecdationActivity.this.dataList.get(i3).getChildren().size() > 0) {
                            for (int i4 = 0; i4 < RecdationActivity.this.dataList.get(i3).getChildren().size(); i4++) {
                                RecdationActivity.this.dataList.get(i3).getChildren().get(i4).setCategory(mCategory);
                                RecdationActivity.this.dataList.get(i3).getChildren().get(i4).setPrimary_p_id(RecdationActivity.this.dataList.get(i3).getPrimary_id() + "");
                                RecdationActivity.this.dataList.get(i3).getChildren().get(i4).setType(mType);
                                RecdationActivity.this.dataList.get(i3).getChildren().get(i4).setGroupPosition(i3);
                                RecdationActivity.this.dataList.get(i3).getChildren().get(i4).setParent_title(RecdationActivity.this.dataList.get(i3).getTitle());
                                arrayList.add(RecdationActivity.this.dataList.get(i3).getChildren().get(i4));
                            }
                        }
                        RecdationActivity.this.dataList.get(i3).setChildren(arrayList);
                        RecdationActivity.this.dataList.get(i3).setType(mType);
                        if (!mType.equals("all")) {
                            RecdationActivity.this.dataList.get(i3).setPass("1");
                        }
                    }
                    RecdationActivity recdationActivity3 = RecdationActivity.this;
                    recdationActivity3.adapter.setList(recdationActivity3.getEntity(recdationActivity3.dataList));
                    try {
                        if ("all".equals(mType)) {
                            "unit".equals(mCategory);
                        }
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                } catch (Exception e4) {
                    e4.printStackTrace();
                    RecdationActivity.this.setEmptyView(true);
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.is_collected = getIntent().getExtras().getString("is_collected");
        this.is_show_number = getIntent().getExtras().getString("is_show_number", "");
    }

    public void initDataView() throws JSONException {
        CustomEmptyView customEmptyView = new CustomEmptyView(this, 0, "暂无数据", R.layout.layout_empty_common_view_half);
        this.emptyView = customEmptyView;
        customEmptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ag
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws JSONException {
                this.f10992c.lambda$initDataView$1(view);
            }
        });
        this.layoutLiveBottom = (LinearLayout) findViewById(R.id.layoutLiveBottom);
        this.tvLiveButton = (TextView) findViewById(R.id.tvLiveButton);
        this.reldddd = (RelativeLayout) findViewById(R.id.reldddd);
        RoundedImageView roundedImageView = (RoundedImageView) findViewById(R.id.headermin);
        GlideImageView glideImageView = (GlideImageView) findViewById(R.id.headerimg);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.pinglunrel);
        this.tv_title = (TextView) findViewById(R.id.tv_title);
        ImageView imageView = (ImageView) findViewById(R.id.icon_left_back);
        TextView textView = (TextView) findViewById(R.id.tv_question_cuoti);
        TextView textView2 = (TextView) findViewById(R.id.tv_question_shoucang);
        TextView textView3 = (TextView) findViewById(R.id.pinglun);
        TextView textView4 = (TextView) findViewById(R.id.dianzan);
        TextView textView5 = (TextView) findViewById(R.id.tv_question_biji);
        GlideImageView glideImageView2 = (GlideImageView) findViewById(R.id.qiehuan);
        this.unreadnum = (TextView) findViewById(R.id.unreadnum);
        this.shoucang = (ImageView) findViewById(R.id.shoucang);
        TextView textView6 = (TextView) findViewById(R.id.title);
        TextView textView7 = (TextView) findViewById(R.id.desc);
        this.timrs = (TextView) findViewById(R.id.timrs);
        this.tv_title.setText(getIntent().getStringExtra("title"));
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.llay_top_one = (LinearLayout) findViewById(R.id.llay_top_one);
        setStatusBarTranslucent();
        int statusBarHeight = StatusBarUtil.getStatusBarHeight(this.mContext);
        CollapsingToolbarLayout.LayoutParams layoutParams = new CollapsingToolbarLayout.LayoutParams(toolbar.getLayoutParams());
        layoutParams.setCollapseMode(1);
        toolbar.setLayoutParams(layoutParams);
        int iDip2px = CommonUtil.dip2px(this.mContext, 20.0f) + statusBarHeight;
        CollapsingToolbarLayout.LayoutParams layoutParams2 = new CollapsingToolbarLayout.LayoutParams(this.reldddd.getLayoutParams());
        layoutParams2.setMargins(0, iDip2px, 0, 0);
        layoutParams2.setCollapseMode(1);
        this.reldddd.setLayoutParams(layoutParams2);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        toolbar.setTitle("");
        toolbar.setSubtitle("");
        collapsingToolbarLayout.setTitle("");
        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, R.color.transparent));
        collapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.transparent));
        GlideUtils.loadImage(this, getIntent().getStringExtra("cover_img"), roundedImageView);
        glideImageView.fitCenter().load(getIntent().getStringExtra("cover_img"), R.drawable.imgplacehodel, new BlurTransformation(this, 25, 10));
        textView6.setText(getIntent().getStringExtra("title"));
        textView7.setText(getIntent().getStringExtra("description"));
        if (TextUtils.isEmpty(getIntent().getExtras().getString(SocializeProtocolConstants.AUTHOR))) {
            this.timrs.setVisibility(4);
            if (!TextUtils.isEmpty(getIntent().getExtras().getString("update_time"))) {
                this.timrs.setText("更新时间：" + getIntent().getExtras().getString("update_time"));
            } else if (!TextUtils.isEmpty(getIntent().getExtras().getString("create_time"))) {
                this.timrs.setText("创建时间：" + getIntent().getExtras().getString("create_time"));
            }
        } else {
            this.timrs.setText("题单作者：" + getIntent().getExtras().getString(SocializeProtocolConstants.AUTHOR));
            this.timrs.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.jg
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f12559c.lambda$initDataView$2(view);
                }
            });
        }
        textView7.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.kg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12589c.lambda$initDataView$3(view);
            }
        });
        relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.lg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12666c.lambda$initDataView$4(view);
            }
        });
        final boolean z2 = SkinManager.getCurrentSkinType(this) == 1;
        if (this.is_collected.equals("1")) {
            this.shoucang.setImageResource(z2 ? R.drawable.icon_collect_night_new : R.drawable.icon_collect_day_new);
        } else {
            this.shoucang.setImageResource(R.drawable.icon_collection_not_svg_new);
        }
        glideImageView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12765c.lambda$initDataView$5(view);
            }
        });
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() { // from class: com.psychiatrygarden.activity.ng
            @Override // com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener, com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
            public final void onOffsetChanged(AppBarLayout appBarLayout2, int i2) {
                this.f13049a.lambda$initDataView$6(appBarLayout2, i2);
            }
        });
        this.unit_id = getIntent().getStringExtra("collection_id");
        this.category_id = getIntent().getStringExtra("category_id");
        showProgressDialog();
        getQuestionBankData(this.mCategory, "", this.type, this.unit_id, this.module_type);
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.og
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13080c.lambda$initDataView$7(view);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.bg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11110c.lambda$initDataView$8(view);
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.cg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11146c.lambda$initDataView$9(view);
            }
        });
        textView4.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.dg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12245c.lambda$initDataView$10(view);
            }
        });
        textView5.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.gg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12442c.lambda$initDataView$11(view);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.hg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12477c.lambda$initDataView$12(view);
            }
        });
        this.shoucang.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ig
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12510c.lambda$initDataView$13(z2, view);
            }
        });
        initRecyclerView();
    }

    @Override // com.psychiatrygarden.activity.online.adapter.QuestionBankNewAdapter.JumpToQList
    public void mChildSelectClumn(int groupPosition, int childPosition) {
    }

    @Override // com.psychiatrygarden.activity.online.adapter.QuestionBankNewAdapter.JumpToQList
    public void mGroupSelectClumn(int groupPosition) {
    }

    @Override // com.psychiatrygarden.activity.online.adapter.QuestionBankNewAdapter.JumpToQList
    public void mJumpToQList(String primary_id, String unit, String parent_title, String title) {
        if (isLogin()) {
            extracted(parent_title);
            Bundle bundle = new Bundle();
            bundle.putString("primary_id", primary_id);
            bundle.putString("unit", unit);
            bundle.putString("unit_id", this.unit_id);
            bundle.putString("category_id", this.category_id);
            bundle.putString(UriUtil.QUERY_CATEGORY, this.mCategory);
            bundle.putString("module_type", this.module_type);
            bundle.putString("type", this.type);
            bundle.putString("subject_title", parent_title);
            bundle.putString("chapter_title", title);
            bundle.putString("is_show_number", this.is_show_number);
            bundle.putString("identity_id", "");
            AnswerSheetActivity.gotoActivity(this, bundle);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) throws JSONException {
        super.onCreate(savedInstanceState);
        this.mActionBar.hide();
        initDataView();
        this.mHandler = new WeakHandler(this, new WeakHandler.HandlerCallback() { // from class: com.psychiatrygarden.activity.eg
            @Override // com.psychiatrygarden.utils.WeakHandler.HandlerCallback
            public final void handlerMessage(Message message) {
                this.f12316a.lambda$onCreate$0(message);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.mHandler.removeCallbacksAndMessages(null);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // android.app.Activity
    public void onRestart() throws JSONException {
        super.onRestart();
        getQuestionBankData(this.mCategory, "", this.type, this.unit_id, this.module_type);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        getCommingNum();
    }

    public void putCollectData(String operation) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("collection_id", "" + getIntent().getStringExtra("collection_id"));
        ajaxParams.put("operation", "" + operation);
        YJYHttpUtils.post(this, NetworkRequestsURL.mCollectSetUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.RecdationActivity.2
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
                    ToastUtil.shortToast(RecdationActivity.this, new JSONObject(s2).optString("message"));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_recdation);
        this.mActionBar.hide();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }

    public void setStatusBarTranslucent() {
        setNewStyleStatusBarColor2();
    }

    @Override // com.psychiatrygarden.activity.online.adapter.QuestionBankNewAdapter.JumpToQList
    public void mJumpToQList(String primary_id, String unit, String parent_title, String title, BaseNode node) {
        if (isLogin()) {
            Bundle bundle = new Bundle();
            NextChapterInfo nextChapterInfoCheckHasNextChapter = checkHasNextChapter(primary_id, node);
            ProjectApp.saveNChapterInfo(nextChapterInfoCheckHasNextChapter);
            if (nextChapterInfoCheckHasNextChapter == null) {
                return;
            }
            extracted(parent_title);
            LogUtils.d("chapter_info", "是否有下一章节:" + nextChapterInfoCheckHasNextChapter.isHasNextChapter() + "，下一章节是否解锁：" + nextChapterInfoCheckHasNextChapter.isNextChapterIsUnlock());
            bundle.putBoolean("hasNextChapter", nextChapterInfoCheckHasNextChapter.isHasNextChapter());
            bundle.putBoolean("nextChapterUnlock", nextChapterInfoCheckHasNextChapter.isNextChapterIsUnlock());
            bundle.putStringArrayList("chapterIds", nextChapterInfoCheckHasNextChapter.getRemainChapterIds());
            bundle.putBooleanArray("chaptersUnlockArr", nextChapterInfoCheckHasNextChapter.getUnlockArr());
            bundle.putString("primary_id", primary_id);
            bundle.putString("unit", unit);
            bundle.putString("unit_id", "" + this.unit_id);
            bundle.putString("category_id", this.category_id);
            bundle.putString(UriUtil.QUERY_CATEGORY, "" + this.mCategory);
            bundle.putString("module_type", this.module_type);
            bundle.putString("type", this.type);
            bundle.putString("subject_title", parent_title);
            bundle.putString("chapter_title", title);
            bundle.putString("is_show_number", this.is_show_number);
            bundle.putString("identity_id", "");
            AnswerSheetActivity.gotoActivity(this, bundle);
        }
    }
}
