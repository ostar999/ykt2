package com.psychiatrygarden.activity.online;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.online.adapter.QuestionRestoreTypeAdapter;
import com.psychiatrygarden.activity.online.bean.ErrorCorrectionGettypeBean;
import com.psychiatrygarden.bean.EventBusMessage;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.QuestionDataRequest;
import com.psychiatrygarden.interfaceclass.QuestionDataCallBack;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class QuestionRestoreSubmitActivity extends BaseActivity implements QuestionDataCallBack<String> {
    private EditText et_restore_type;
    private QuestionRestoreTypeAdapter restoreTypeAdapter;
    private TextView tv_font_num;
    private TextView tv_to_submit;
    private String restore_analyze = "";
    private String content = "";
    private String question_id = "";
    private String identity_id = "";
    private String type = "";
    private List<ErrorCorrectionGettypeBean.DataDTO> stringList = new ArrayList();

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$0(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        this.restoreTypeAdapter.setSelectPosition(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$1(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        try {
            AjaxParams ajaxParams = new AjaxParams();
            ajaxParams.put("question_id", "" + this.question_id);
            ajaxParams.put("identity_unit", "" + this.identity_id);
            ajaxParams.put("type", "" + this.type);
            ajaxParams.put("content", "" + this.content);
            if (!TextUtils.isEmpty(this.et_restore_type.getText().toString())) {
                ajaxParams.put("content_explain", "" + this.et_restore_type.getText().toString());
            }
            if (this.restore_analyze.equals(CommonParameter.QUESTION_RESTORE)) {
                ajaxParams.put("question_column", RequestParameters.X_OSS_RESTORE);
            } else if (this.restore_analyze.equals(CommonParameter.QUESTION_ANALYZE)) {
                ajaxParams.put("question_column", "explain");
            } else {
                ajaxParams.put("question_column", "option_analysis");
            }
            ajaxParams.put("error_type_id", this.stringList.get(this.restoreTypeAdapter.getSelectPosition()).getId());
            QuestionDataRequest.getIntance(this.mContext).errorCorrectionSave(ajaxParams, this);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.identity_id = getIntent().getStringExtra("identity_id");
        this.type = getIntent().getStringExtra("type");
        this.question_id = getIntent().getStringExtra("question_id");
        this.content = getIntent().getStringExtra("content");
        String stringExtra = getIntent().getStringExtra(CommonParameter.QUESTION_RESTORE_ANALYZE);
        this.restore_analyze = stringExtra;
        if (TextUtils.equals(stringExtra, CommonParameter.QUESTION_RESTORE)) {
            setTitle("考点还原");
        } else {
            setTitle("答案解析");
        }
        if (SkinManager.getCurrentSkinType(this) == 0) {
            findViewById(R.id.iv_bottom_shadow).setVisibility(0);
        } else {
            findViewById(R.id.iv_bottom_shadow).setVisibility(4);
        }
        this.tv_to_submit = (TextView) findViewById(R.id.tv_to_submit);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_restore_type);
        this.et_restore_type = (EditText) findViewById(R.id.et_restore_type);
        this.tv_font_num = (TextView) findViewById(R.id.tv_font_num);
        recyclerView.setLayoutManager(new GridLayoutManager(this.mContext, 3));
        QuestionRestoreTypeAdapter questionRestoreTypeAdapter = new QuestionRestoreTypeAdapter(this.stringList);
        this.restoreTypeAdapter = questionRestoreTypeAdapter;
        recyclerView.setAdapter(questionRestoreTypeAdapter);
        QuestionDataRequest.getIntance(this.mContext).errorCorrectionGettype(new AjaxParams(), this);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onFailure(Throwable t2, int errorNo, String strMsg, String requstUrl) {
        ToastUtil.shortToast(this.mContext, strMsg);
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onStart(String requstUrl) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_question_restore_submit);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.restoreTypeAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.online.s1
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f13480c.lambda$setListenerForWidget$0(baseQuickAdapter, view, i2);
            }
        });
        this.et_restore_type.addTextChangedListener(new TextWatcher() { // from class: com.psychiatrygarden.activity.online.QuestionRestoreSubmitActivity.1
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s2) {
                if (s2.length() < 100) {
                    QuestionRestoreSubmitActivity.this.tv_font_num.setText(String.format(Locale.CHINA, "%d/100", Integer.valueOf(s2.length())));
                } else {
                    ToastUtil.shortToast(QuestionRestoreSubmitActivity.this.mContext, "超出文字限制");
                    QuestionRestoreSubmitActivity.this.tv_font_num.setText("100/100");
                }
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s2, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s2, int start, int before, int count) {
            }
        });
        this.tv_to_submit.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.t1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13485c.lambda$setListenerForWidget$1(view);
            }
        });
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onSuccess(String s2, String requstUrl) {
        if (requstUrl.equals(NetworkRequestsURL.errorCorrectionGettypeURL)) {
            ErrorCorrectionGettypeBean errorCorrectionGettypeBean = (ErrorCorrectionGettypeBean) new Gson().fromJson(s2, ErrorCorrectionGettypeBean.class);
            if (!errorCorrectionGettypeBean.getCode().equals("200")) {
                ToastUtil.shortToast(this.mContext, errorCorrectionGettypeBean.getMessage());
                return;
            }
            List<ErrorCorrectionGettypeBean.DataDTO> data = errorCorrectionGettypeBean.getData();
            this.stringList = data;
            this.restoreTypeAdapter.setList(data);
            return;
        }
        if (requstUrl.equals(NetworkRequestsURL.errorCorrectionSaveURL)) {
            try {
                JSONObject jSONObject = new JSONObject(s2);
                if (!jSONObject.optString("code").equals("200")) {
                    ToastUtil.shortToast(this.mContext, jSONObject.optString("message", ""));
                    return;
                }
                if (this.restore_analyze.equals(CommonParameter.QUESTION_RESTORE)) {
                    EventBus.getDefault().post(new EventBusMessage(EventBusConstant.EVENT_QUESTION_ERROR_CORRECTION_OK, this.question_id, CommonParameter.QUESTION_RESTORE));
                } else {
                    EventBus.getDefault().post(new EventBusMessage(EventBusConstant.EVENT_QUESTION_ERROR_CORRECTION_OK, this.question_id, CommonParameter.QUESTION_ANALYZE));
                }
                finish();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}
