package com.psychiatrygarden.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.huawei.hms.push.HmsMessageService;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.adapter.SubjectiveQuestionAnswerAdapter;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.onDialogClickListener;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.widget.CusomNewDialog;
import com.psychiatrygarden.widget.DialogInput;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class SubjectiveQuestionAnswerActivity extends BaseActivity {
    private Button btn_comment;
    private int indext = 0;

    @SuppressLint({"HandlerLeak"})
    private final Handler mHandler = new Handler() { // from class: com.psychiatrygarden.activity.SubjectiveQuestionAnswerActivity.1
        @Override // android.os.Handler
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            int i2 = msg.what;
            if (i2 == 1) {
                SubjectiveQuestionAnswerActivity.this.questiondetails_bottom_layout.setVisibility(0);
            } else {
                if (i2 != 2) {
                    return;
                }
                SubjectiveQuestionAnswerActivity.this.questiondetails_bottom_layout.setVisibility(8);
            }
        }
    };
    View.OnClickListener mOnclick = new View.OnClickListener() { // from class: com.psychiatrygarden.activity.fo
        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            this.f12364c.lambda$new$2(view);
        }
    };
    private LinearLayout questiondetails_bottom_layout;
    private ImageView questiondetails_btn_centerMsg;
    private ImageView questiondetails_btn_collect;
    private ImageView questiondetails_btn_edit;
    private ImageView questiondetails_btn_share;
    private ImageView questiondetails_btn_zantong;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(String str, String str2, String str3) {
        Bundle bundle = new Bundle();
        bundle.putString("content", str);
        bundle.putInt("result", 1);
        bundle.putString("b_img", str2);
        bundle.putString("s_img", str3);
        if (!UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            pushComment(bundle);
            return;
        }
        Intent intent = new Intent(this.mContext, (Class<?>) CorpCupActivity.class);
        intent.putExtra("bundleIntent", bundle);
        startActivityForResult(intent, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(View view) {
        if (!CommonUtil.isFastClick() && view.getId() == R.id.btn_comment && isLogin()) {
            new DialogInput(this.mContext, new onDialogClickListener() { // from class: com.psychiatrygarden.activity.go
                @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                public final void onclickStringBack(String str, String str2, String str3) {
                    this.f12453a.lambda$new$1(str, str2, str3);
                }
            }, true).show();
        }
    }

    public static void launch(Activity activity, Bundle bundle) {
        Intent intent = new Intent(activity, (Class<?>) SubjectiveQuestionAnswerActivity.class);
        intent.putExtra("bundle", bundle);
        activity.startActivity(intent);
    }

    private void pushComment(Bundle b3) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("obj_id", ProjectApp.dataList.get(this.indext).getQuestion_id());
        ajaxParams.put("content", b3.getString("content"));
        ajaxParams.put("module_type", "1");
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
        YJYHttpUtils.post(this.mContext.getApplicationContext(), NetworkRequestsURL.mPutComment, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.SubjectiveQuestionAnswerActivity.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                SubjectiveQuestionAnswerActivity.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        SharePreferencesUtils.writeStrConfig(CommonParameter.PINGLUNTXT, "", SubjectiveQuestionAnswerActivity.this.mContext);
                        SubjectiveQuestionAnswerActivity.this.AlertToast(jSONObject.optString("message"));
                        EventBus.getDefault().post(EventBusConstant.EVENT_DIALOG_INPUT_DISMISS);
                        CommonUtil.showFristDialog(jSONObject);
                    } else if (jSONObject.optString("code").equals("401")) {
                        new CusomNewDialog(SubjectiveQuestionAnswerActivity.this.mContext).setMessage(jSONObject.optString("message")).show();
                    } else {
                        SubjectiveQuestionAnswerActivity.this.AlertToast(jSONObject.optString("message"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                SubjectiveQuestionAnswerActivity.this.hideProgressDialog();
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() throws IllegalStateException {
        this.indext = getIntent().getBundleExtra("bundle").getInt("position", 0);
        String string = getIntent().getBundleExtra("bundle").getString("anspost");
        setSwipeBackEnable(false);
        this.mActionBar.hide();
        ((ImageView) findViewById(R.id.include_btn_left)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.eo
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12327c.lambda$init$0(view);
            }
        });
        TextView textView = (TextView) findViewById(R.id.include_title_center);
        ((TextView) findViewById(R.id.include_btn_right_tv)).setVisibility(8);
        textView.setText(getIntent().getBundleExtra("bundle").getString("subject_name"));
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_subjective);
        this.questiondetails_bottom_layout = (LinearLayout) findViewById(R.id.questiondetails_bottom_layout);
        this.btn_comment = (Button) findViewById(R.id.btn_comment);
        this.questiondetails_btn_edit = (ImageView) findViewById(R.id.questiondetails_btn_edit);
        this.questiondetails_btn_collect = (ImageView) findViewById(R.id.questiondetails_btn_collect);
        this.questiondetails_btn_share = (ImageView) findViewById(R.id.questiondetails_btn_share);
        this.questiondetails_btn_centerMsg = (ImageView) findViewById(R.id.questiondetails_btn_centerMsg);
        this.questiondetails_btn_zantong = (ImageView) findViewById(R.id.questiondetails_btn_zantong);
        for (int i2 = 0; i2 < ProjectApp.dataList.size(); i2++) {
            ProjectApp.dataList.get(i2).setQuestionurl(NetworkRequestsURL.msubjecturl + "?device_info=android&app_id=" + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this) + "&user_id=" + UserConfig.getUserId() + "&question_id=" + ProjectApp.dataList.get(i2).getQuestion_id());
            ProjectApp.dataList.get(i2).setSubject_id(getIntent().getBundleExtra("bundle").getString(HmsMessageService.SUBJECT_ID));
        }
        recyclerView.setNestedScrollingEnabled(false);
        SubjectiveQuestionAnswerAdapter subjectiveQuestionAnswerAdapter = new SubjectiveQuestionAnswerAdapter(R.layout.item_subjective_question, ProjectApp.dataList, this.mHandler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(0);
        recyclerView.setLayoutManager(linearLayoutManager);
        new PagerSnapHelper().attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(subjectiveQuestionAnswerAdapter);
        recyclerView.scrollToPosition(this.indext);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.psychiatrygarden.activity.SubjectiveQuestionAnswerActivity.2
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView2, int newState) {
                super.onScrollStateChanged(recyclerView2, newState);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(@NonNull RecyclerView recyclerView2, int dx, int dy) {
                super.onScrolled(recyclerView2, dx, dy);
                try {
                    if (TextUtils.equals(ProjectApp.dataList.get(((LinearLayoutManager) recyclerView2.getLayoutManager()).findFirstVisibleItemPosition()).getAnswer_status(), "0")) {
                        SubjectiveQuestionAnswerActivity.this.mHandler.sendEmptyMessage(2);
                    } else {
                        SubjectiveQuestionAnswerActivity.this.mHandler.sendEmptyMessage(1);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
        if (TextUtils.equals(string, "0")) {
            this.questiondetails_bottom_layout.setVisibility(8);
        } else {
            this.questiondetails_bottom_layout.setVisibility(0);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_subjective_question_answer);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.btn_comment.setOnClickListener(this.mOnclick);
        this.questiondetails_btn_edit.setOnClickListener(this.mOnclick);
        this.questiondetails_btn_collect.setOnClickListener(this.mOnclick);
        this.questiondetails_btn_share.setOnClickListener(this.mOnclick);
        this.questiondetails_btn_centerMsg.setOnClickListener(this.mOnclick);
        this.questiondetails_btn_zantong.setOnClickListener(this.mOnclick);
    }
}
