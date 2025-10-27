package com.psychiatrygarden.activity.chooseSchool;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.lxj.xpopup.XPopup;
import com.mobile.auth.gatewayauth.Constant;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.utils.StringUtil;
import com.psychiatrygarden.widget.ChooseSchoolTypePopupWindow;
import com.yikaobang.yixue.R;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import de.greenrobot.event.EventBus;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 (2\u00020\u0001:\u0001(B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0017\u001a\u00020\u0018H\u0002J\b\u0010\u0019\u001a\u00020\u0018H\u0016J\b\u0010\u001a\u001a\u00020\u001bH\u0002J\"\u0010\u001c\u001a\u00020\u00182\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u001e2\b\u0010 \u001a\u0004\u0018\u00010!H\u0014J\u0012\u0010\"\u001a\u00020\u00182\b\u0010#\u001a\u0004\u0018\u00010\u0006H\u0016J\b\u0010$\u001a\u00020\u0018H\u0002J\b\u0010%\u001a\u00020\u0018H\u0016J\b\u0010&\u001a\u00020\u0018H\u0016J\b\u0010'\u001a\u00020\u0018H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\rX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\rX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\rX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000¨\u0006)"}, d2 = {"Lcom/psychiatrygarden/activity/chooseSchool/ChooseSchoolEditMsgActivity;", "Lcom/psychiatrygarden/activity/BaseActivity;", "()V", "layoutKe2", "Landroid/widget/RelativeLayout;", "majorId", "", "majorTitle", "majorType", "tvCancel", "Landroid/widget/TextView;", "tvCount", "tvEng", "Landroid/widget/EditText;", "tvHin1", "tvHin2", "tvHin3", "tvKe1", "tvKe2", "tvLeiBie", "tvSubmit", "tvZhengZhi", "tvZhuanYe", "countScore", "", "init", "isNight", "", "onActivityResult", Constant.LOGIN_ACTIVITY_REQUEST_CODE, "", "resultCode", "data", "Landroid/content/Intent;", "onEventMainThread", "str", "saveData", "setContentView", "setListenerForWidget", "showTypeDialog", "Companion", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class ChooseSchoolEditMsgActivity extends BaseActivity {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private RelativeLayout layoutKe2;
    private TextView tvCancel;
    private TextView tvCount;
    private EditText tvEng;
    private TextView tvHin1;
    private TextView tvHin2;
    private TextView tvHin3;
    private EditText tvKe1;
    private EditText tvKe2;
    private TextView tvLeiBie;
    private TextView tvSubmit;
    private EditText tvZhengZhi;
    private TextView tvZhuanYe;

    @NotNull
    private String majorType = "";

    @Nullable
    private String majorTitle = "";

    @Nullable
    private String majorId = "";

    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lcom/psychiatrygarden/activity/chooseSchool/ChooseSchoolEditMsgActivity$Companion;", "", "()V", "navigationToChooseSchoolActivity", "", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void navigationToChooseSchoolActivity(@NotNull Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            context.startActivity(new Intent(context, (Class<?>) ChooseSchoolEditMsgActivity.class));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void countScore() {
        EditText editText = this.tvZhengZhi;
        TextView textView = null;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvZhengZhi");
            editText = null;
        }
        String string = editText.getText().toString();
        EditText editText2 = this.tvEng;
        if (editText2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvEng");
            editText2 = null;
        }
        String string2 = editText2.getText().toString();
        EditText editText3 = this.tvKe1;
        if (editText3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvKe1");
            editText3 = null;
        }
        String string3 = editText3.getText().toString();
        EditText editText4 = this.tvKe2;
        if (editText4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvKe2");
            editText4 = null;
        }
        String string4 = editText4.getText().toString();
        float f2 = string.length() > 0 ? 0.0f + Float.parseFloat(string) : 0.0f;
        if (string2.length() > 0) {
            f2 += Float.parseFloat(string2);
        }
        if (string3.length() > 0) {
            f2 += Float.parseFloat(string3);
        }
        float f3 = f2 + (((string4.length() > 0) && Intrinsics.areEqual(this.majorType, "2")) ? Float.parseFloat(string4) : 0);
        TextView textView2 = this.tvCount;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvCount");
        } else {
            textView = textView2;
        }
        textView.setText(String.valueOf(f3));
    }

    private final boolean isNight() {
        return SkinManager.getCurrentSkinType(this) == 1;
    }

    private final void saveData() {
        AjaxParams ajaxParams = new AjaxParams();
        if (this.majorType.length() == 0) {
            NewToast.showShort(this, "请选择专业类型");
            return;
        }
        String str = this.majorId;
        if (str == null || str.length() == 0) {
            NewToast.showShort(this, "请选择专业");
            return;
        }
        ajaxParams.put("major_id", this.majorId);
        ajaxParams.put("major_type", this.majorType);
        EditText editText = this.tvZhengZhi;
        EditText editText2 = null;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvZhengZhi");
            editText = null;
        }
        String string = editText.getText().toString();
        EditText editText3 = this.tvEng;
        if (editText3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvEng");
            editText3 = null;
        }
        String string2 = editText3.getText().toString();
        EditText editText4 = this.tvKe1;
        if (editText4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvKe1");
            editText4 = null;
        }
        String string3 = editText4.getText().toString();
        EditText editText5 = this.tvKe2;
        if (editText5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvKe2");
        } else {
            editText2 = editText5;
        }
        String string4 = editText2.getText().toString();
        if (string.length() == 0) {
            NewToast.showShort(this, "请输入政治分数");
            return;
        }
        if (string2.length() == 0) {
            NewToast.showShort(this, "请输入英语分数");
            return;
        }
        if (string3.length() == 0) {
            NewToast.showShort(this, "请输入专业课一分数");
            return;
        }
        if (Float.parseFloat(string) > 100.0f) {
            NewToast.showShort(this, "政治分值不能超过100");
            return;
        }
        if (Float.parseFloat(string2) > 100.0f) {
            NewToast.showShort(this, "英语分值不能超过100");
            return;
        }
        if (Float.parseFloat(string3) > 300.0f) {
            NewToast.showShort(this, "专业课一分值不能超过300");
            return;
        }
        if (Intrinsics.areEqual(this.majorType, "2")) {
            if (string4.length() == 0) {
                NewToast.showShort(this, "请输入专业课二分数");
                return;
            }
        }
        if (Intrinsics.areEqual(this.majorType, "2") && Float.parseFloat(string4) > 300.0f) {
            NewToast.showShort(this, "专业课二分值不能超过300");
            return;
        }
        if (Intrinsics.areEqual(this.majorType, "2") && Float.parseFloat(string3) + Float.parseFloat(string4) > 300.0f) {
            NewToast.showShort(this, "专业课总分不能超过300");
            return;
        }
        ajaxParams.put("politics", string);
        ajaxParams.put("english", string2);
        ajaxParams.put("major_1", string3);
        if (Intrinsics.areEqual("2", this.majorType)) {
            ajaxParams.put("major_2", string4);
        } else {
            ajaxParams.put("major_2", "0");
        }
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.chooseSchoolSaveScore, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.ChooseSchoolEditMsgActivity.saveData.1
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
                Intrinsics.checkNotNullParameter(t2, "t");
                super.onSuccess((AnonymousClass1) t2);
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (Intrinsics.areEqual("200", jSONObject.optString("code"))) {
                        ChooseSchoolEditMsgActivity.this.finish();
                        EventBus.getDefault().post(EventBusConstant.EVENT_CHOOSE_SCHOOL_SAVE_SCORE);
                    } else {
                        NewToast.showShort(ChooseSchoolEditMsgActivity.this.mContext, jSONObject.optString("message"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListenerForWidget$lambda$0(ChooseSchoolEditMsgActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListenerForWidget$lambda$1(ChooseSchoolEditMsgActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListenerForWidget$lambda$2(ChooseSchoolEditMsgActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.showTypeDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListenerForWidget$lambda$3(ChooseSchoolEditMsgActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.majorType.length() == 0) {
            NewToast.showShort(this$0, "请先选择专业类别");
        } else {
            ChooseMajorActivity.INSTANCE.navigationToChooseMajorActivity(this$0, this$0.majorType);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListenerForWidget$lambda$4(ChooseSchoolEditMsgActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.saveData();
    }

    private final void showTypeDialog() {
        new XPopup.Builder(this).asCustom(new ChooseSchoolTypePopupWindow(this, new ChooseSchoolTypePopupWindow.ClickIml() { // from class: com.psychiatrygarden.activity.chooseSchool.n
            @Override // com.psychiatrygarden.widget.ChooseSchoolTypePopupWindow.ClickIml
            public final void mClickIml(int i2) {
                ChooseSchoolEditMsgActivity.showTypeDialog$lambda$5(this.f11357a, i2);
            }
        })).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showTypeDialog$lambda$5(ChooseSchoolEditMsgActivity this$0, int i2) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        EditText editText = null;
        if (i2 == 0) {
            TextView textView = this$0.tvLeiBie;
            if (textView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvLeiBie");
                textView = null;
            }
            textView.setText("专硕");
            this$0.majorType = "1";
            RelativeLayout relativeLayout = this$0.layoutKe2;
            if (relativeLayout == null) {
                Intrinsics.throwUninitializedPropertyAccessException("layoutKe2");
                relativeLayout = null;
            }
            ViewExtensionsKt.gone(relativeLayout);
            EditText editText2 = this$0.tvKe2;
            if (editText2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvKe2");
            } else {
                editText = editText2;
            }
            editText.setText("");
            this$0.countScore();
            return;
        }
        if (i2 != 1) {
            return;
        }
        TextView textView2 = this$0.tvLeiBie;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvLeiBie");
            textView2 = null;
        }
        textView2.setText("学硕");
        this$0.majorType = "2";
        RelativeLayout relativeLayout2 = this$0.layoutKe2;
        if (relativeLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutKe2");
            relativeLayout2 = null;
        }
        ViewExtensionsKt.visible(relativeLayout2);
        EditText editText3 = this$0.tvKe2;
        if (editText3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvKe2");
        } else {
            editText = editText3;
        }
        editText.setText("");
        this$0.countScore();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        View viewFindViewById = findViewById(R.id.tvCancel);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.tvCancel)");
        this.tvCancel = (TextView) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.tvCount);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.tvCount)");
        this.tvCount = (TextView) viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.tvSubmit);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.tvSubmit)");
        this.tvSubmit = (TextView) viewFindViewById3;
        View viewFindViewById4 = findViewById(R.id.layoutKe2);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(R.id.layoutKe2)");
        this.layoutKe2 = (RelativeLayout) viewFindViewById4;
        View viewFindViewById5 = findViewById(R.id.tvLeiBie);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(R.id.tvLeiBie)");
        this.tvLeiBie = (TextView) viewFindViewById5;
        View viewFindViewById6 = findViewById(R.id.tvZhuanYe);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "findViewById(R.id.tvZhuanYe)");
        this.tvZhuanYe = (TextView) viewFindViewById6;
        View viewFindViewById7 = findViewById(R.id.tvZhengZhi);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById7, "findViewById(R.id.tvZhengZhi)");
        this.tvZhengZhi = (EditText) viewFindViewById7;
        View viewFindViewById8 = findViewById(R.id.tvEng);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById8, "findViewById(R.id.tvEng)");
        this.tvEng = (EditText) viewFindViewById8;
        View viewFindViewById9 = findViewById(R.id.tvKe1);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById9, "findViewById(R.id.tvKe1)");
        this.tvKe1 = (EditText) viewFindViewById9;
        View viewFindViewById10 = findViewById(R.id.tvKe2);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById10, "findViewById(R.id.tvKe2)");
        this.tvKe2 = (EditText) viewFindViewById10;
        View viewFindViewById11 = findViewById(R.id.tvHin1);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById11, "findViewById(R.id.tvHin1)");
        this.tvHin1 = (TextView) viewFindViewById11;
        View viewFindViewById12 = findViewById(R.id.tvHin2);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById12, "findViewById(R.id.tvHin2)");
        this.tvHin2 = (TextView) viewFindViewById12;
        View viewFindViewById13 = findViewById(R.id.tvHin3);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById13, "findViewById(R.id.tvHin3)");
        this.tvHin3 = (TextView) viewFindViewById13;
        TextView textView = this.tvHin1;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvHin1");
            textView = null;
        }
        TextView textView2 = this.tvHin1;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvHin1");
            textView2 = null;
        }
        StringUtil.setTargetTextColor(textView, textView2.getText().toString(), CollectionsKt__CollectionsKt.mutableListOf("*"), isNight() ? getResources().getColor(R.color.main_theme_color_night, null) : getResources().getColor(R.color.main_theme_color, null));
        TextView textView3 = this.tvHin2;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvHin2");
            textView3 = null;
        }
        TextView textView4 = this.tvHin2;
        if (textView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvHin2");
            textView4 = null;
        }
        StringUtil.setTargetTextColor(textView3, textView4.getText().toString(), CollectionsKt__CollectionsKt.mutableListOf("*"), isNight() ? getResources().getColor(R.color.main_theme_color_night, null) : getResources().getColor(R.color.main_theme_color, null));
        TextView textView5 = this.tvHin3;
        if (textView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvHin3");
            textView5 = null;
        }
        TextView textView6 = this.tvHin3;
        if (textView6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvHin3");
            textView6 = null;
        }
        StringUtil.setTargetTextColor(textView5, textView6.getText().toString(), CollectionsKt__CollectionsKt.mutableListOf("*"), isNight() ? getResources().getColor(R.color.main_theme_color_night, null) : getResources().getColor(R.color.main_theme_color, null));
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11 && resultCode == -1 && data != null) {
            this.majorId = data.getStringExtra("major_id");
            this.majorTitle = data.getStringExtra("major_title");
            TextView textView = this.tvZhuanYe;
            if (textView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvZhuanYe");
                textView = null;
            }
            textView.setText(this.majorTitle);
            Log.d(this.TAG, "onActivityResult: " + this.majorId + "  " + ((Object) getTitle()));
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(@Nullable String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        this.mActionBar.hide();
        setContentView(R.layout.activity_choose_school);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, isNight() ? R.color.zx_color_edit_score_bg : R.color.zx_color_choose_school_bg_color_start), 0);
        getWindow().getDecorView().setSystemUiVisibility(8192);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        TextView textView = this.tvCancel;
        TextView textView2 = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvCancel");
            textView = null;
        }
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.o
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChooseSchoolEditMsgActivity.setListenerForWidget$lambda$0(this.f11368c, view);
            }
        });
        EditText editText = this.tvZhengZhi;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvZhengZhi");
            editText = null;
        }
        editText.addTextChangedListener(new TextWatcher() { // from class: com.psychiatrygarden.activity.chooseSchool.ChooseSchoolEditMsgActivity.setListenerForWidget.2
            @Override // android.text.TextWatcher
            public void afterTextChanged(@Nullable Editable s2) {
                ChooseSchoolEditMsgActivity.this.countScore();
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(@Nullable CharSequence s2, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(@Nullable CharSequence s2, int start, int before, int count) {
            }
        });
        EditText editText2 = this.tvEng;
        if (editText2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvEng");
            editText2 = null;
        }
        editText2.addTextChangedListener(new TextWatcher() { // from class: com.psychiatrygarden.activity.chooseSchool.ChooseSchoolEditMsgActivity.setListenerForWidget.3
            @Override // android.text.TextWatcher
            public void afterTextChanged(@Nullable Editable s2) {
                ChooseSchoolEditMsgActivity.this.countScore();
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(@Nullable CharSequence s2, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(@Nullable CharSequence s2, int start, int before, int count) {
            }
        });
        EditText editText3 = this.tvKe1;
        if (editText3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvKe1");
            editText3 = null;
        }
        editText3.addTextChangedListener(new TextWatcher() { // from class: com.psychiatrygarden.activity.chooseSchool.ChooseSchoolEditMsgActivity.setListenerForWidget.4
            @Override // android.text.TextWatcher
            public void afterTextChanged(@Nullable Editable s2) {
                ChooseSchoolEditMsgActivity.this.countScore();
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(@Nullable CharSequence s2, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(@Nullable CharSequence s2, int start, int before, int count) {
            }
        });
        EditText editText4 = this.tvKe2;
        if (editText4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvKe2");
            editText4 = null;
        }
        editText4.addTextChangedListener(new TextWatcher() { // from class: com.psychiatrygarden.activity.chooseSchool.ChooseSchoolEditMsgActivity.setListenerForWidget.5
            @Override // android.text.TextWatcher
            public void afterTextChanged(@Nullable Editable s2) {
                ChooseSchoolEditMsgActivity.this.countScore();
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(@Nullable CharSequence s2, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(@Nullable CharSequence s2, int start, int before, int count) {
            }
        });
        ((ImageView) findViewById(R.id.actionbarBack)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.p
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChooseSchoolEditMsgActivity.setListenerForWidget$lambda$1(this.f11376c, view);
            }
        });
        ((RelativeLayout) findViewById(R.id.layoutLeiBie)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.q
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChooseSchoolEditMsgActivity.setListenerForWidget$lambda$2(this.f11384c, view);
            }
        });
        ((RelativeLayout) findViewById(R.id.layoutZhuanYe)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.r
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChooseSchoolEditMsgActivity.setListenerForWidget$lambda$3(this.f11392c, view);
            }
        });
        TextView textView3 = this.tvSubmit;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvSubmit");
        } else {
            textView2 = textView3;
        }
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.s
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChooseSchoolEditMsgActivity.setListenerForWidget$lambda$4(this.f11400c, view);
            }
        });
    }
}
