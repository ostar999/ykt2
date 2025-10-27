package com.psychiatrygarden.activity.chooseSchool;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.yikaobang.yixue.R;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\n\u0018\u0000 \u00122\u00020\u0001:\u0001\u0012B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\b\u0010\n\u001a\u00020\tH\u0016J\u0012\u0010\u000b\u001a\u00020\t2\b\u0010\f\u001a\u0004\u0018\u00010\u0006H\u0016J\u001c\u0010\r\u001a\u00020\t2\b\u0010\u000e\u001a\u0004\u0018\u00010\u00062\b\u0010\u000f\u001a\u0004\u0018\u00010\u0006H\u0002J\b\u0010\u0010\u001a\u00020\tH\u0016J\b\u0010\u0011\u001a\u00020\tH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lcom/psychiatrygarden/activity/chooseSchool/ChooseSchoolQuestionDetailActivity;", "Lcom/psychiatrygarden/activity/BaseActivity;", "()V", "questionContent", "Landroid/widget/TextView;", "questionId", "", "questionTitle", "getQuestionDetail", "", "init", "onEventMainThread", "str", "setContent", "title", "content", "setContentView", "setListenerForWidget", "Companion", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class ChooseSchoolQuestionDetailActivity extends BaseActivity {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private TextView questionContent;

    @Nullable
    private String questionId = "";
    private TextView questionTitle;

    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b¨\u0006\t"}, d2 = {"Lcom/psychiatrygarden/activity/chooseSchool/ChooseSchoolQuestionDetailActivity$Companion;", "", "()V", "navigationToChooseSchoolQuestionDetailActivity", "", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "questionId", "", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void navigationToChooseSchoolQuestionDetailActivity(@NotNull Context context, @NotNull String questionId) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(questionId, "questionId");
            Intent intent = new Intent(context, (Class<?>) ChooseSchoolQuestionDetailActivity.class);
            intent.putExtra("questionId", questionId);
            context.startActivity(intent);
        }
    }

    private final void getQuestionDetail(String questionId) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", questionId);
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.chooseSchoolQuestionDetail, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.ChooseSchoolQuestionDetailActivity.getQuestionDetail.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@NotNull Throwable t2, int errorNo, @NotNull String strMsg) {
                Intrinsics.checkNotNullParameter(t2, "t");
                Intrinsics.checkNotNullParameter(strMsg, "strMsg");
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String t2) {
                JSONObject jSONObjectOptJSONObject;
                Intrinsics.checkNotNullParameter(t2, "t");
                super.onSuccess((AnonymousClass1) t2);
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (!Intrinsics.areEqual("200", jSONObject.optString("code")) || (jSONObjectOptJSONObject = jSONObject.optJSONObject("data")) == null) {
                        return;
                    }
                    ChooseSchoolQuestionDetailActivity.this.setContent(jSONObjectOptJSONObject.optString("title"), jSONObjectOptJSONObject.optString("content"));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setContent(String title, String content) {
        TextView textView = this.questionTitle;
        TextView textView2 = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("questionTitle");
            textView = null;
        }
        if (title == null) {
            title = "";
        }
        textView.setText(title);
        TextView textView3 = this.questionContent;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("questionContent");
        } else {
            textView2 = textView3;
        }
        if (content == null) {
            content = "";
        }
        textView2.setText(content);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.questionId = getIntent().getStringExtra("questionId");
        View viewFindViewById = findViewById(R.id.questionTitle);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.questionTitle)");
        this.questionTitle = (TextView) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.questionContent);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.questionContent)");
        this.questionContent = (TextView) viewFindViewById2;
        String str = this.questionId;
        if (str != null) {
            getQuestionDetail(str);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(@Nullable String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_choose_school_question_detail);
        setTitle("问题详情");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
