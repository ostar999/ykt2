package com.psychiatrygarden.widget;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.CenterPopupView;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.bean.EventBusMessage;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.QuestionDataRequest;
import com.psychiatrygarden.interfaceclass.QuestionDataCallBack;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class PopClearAnswerRecord extends CenterPopupView implements View.OnClickListener, QuestionDataCallBack<String> {
    private TextView tv_cancel;
    private TextView tv_ok;
    private TextView tv_question_content;
    private TextView tv_question_name;

    public PopClearAnswerRecord(@NonNull Context context) {
        super(context);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void dismiss() {
        super.dismiss();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void doAfterDismiss() {
        super.doAfterDismiss();
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.pop_clear_answer_record;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        int id = v2.getId();
        if (id == R.id.tv_cancel) {
            dismiss();
        } else {
            if (id != R.id.tv_ok) {
                return;
            }
            dismiss();
            ((BaseActivity) getContext()).showProgressDialog();
            QuestionDataRequest.getIntance(getContext()).clearAllAnswer(this);
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        this.tv_question_name = (TextView) findViewById(R.id.tv_question_name);
        this.tv_question_content = (TextView) findViewById(R.id.tv_question_content);
        this.tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        this.tv_ok = (TextView) findViewById(R.id.tv_ok);
        this.tv_cancel.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.yb
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f17125c.onClick(view);
            }
        });
        this.tv_ok.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.yb
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f17125c.onClick(view);
            }
        });
        QuestionDataRequest.getIntance(getContext()).clearAllAnswerNotice(this);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onDismiss() {
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onFailure(Throwable t2, int errorNo, String strMsg, String requstUrl) {
        ((BaseActivity) getContext()).hideProgressDialog();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onShow() {
        super.onShow();
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onStart(String requstUrl) {
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onSuccess(String s2, String requstUrl) {
        try {
            JSONObject jSONObject = new JSONObject(s2);
            if (requstUrl.equals(NetworkRequestsURL.clearAllAnswerNoticeApi)) {
                if (jSONObject.optString("code").equals("200")) {
                    if (SkinManager.getCurrentSkinType(getContext()) == 0) {
                        this.tv_question_name.setText(Html.fromHtml("当前身份为：<font color='#F95843'>" + jSONObject.optJSONObject("data").optString("name") + "</font>"));
                    } else {
                        this.tv_question_name.setText(Html.fromHtml("当前身份为：<font color='#B2575C'>" + jSONObject.optJSONObject("data").optString("name") + "</font>"));
                    }
                    this.tv_question_content.setText(jSONObject.optJSONObject("data").optString("describe"));
                } else {
                    ToastUtil.shortToast(getContext(), jSONObject.optString("message"));
                }
            } else if (requstUrl.equals(NetworkRequestsURL.clearAllAnswerApi)) {
                if (jSONObject.optString("code").equals("200")) {
                    EventBus.getDefault().post(new EventBusMessage(EventBusConstant.EVENT_QUESTION_ALL_CLEAR, ""));
                } else {
                    ToastUtil.shortToast(getContext(), jSONObject.optString("message"));
                }
            }
            ((BaseActivity) getContext()).hideProgressDialog();
        } catch (Exception e2) {
            ((BaseActivity) getContext()).hideProgressDialog();
            e2.printStackTrace();
        }
    }
}
