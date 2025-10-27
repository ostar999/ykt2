package com.psychiatrygarden.activity.courselist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.courselist.bean.QRCode;
import com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryCombineListFragment;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.widget.CourseWxPopupNew;
import com.yikaobang.yixue.R;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0004H\u0002J\b\u0010\u000e\u001a\u00020\fH\u0016J\u0012\u0010\u000f\u001a\u00020\f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0014J\u0012\u0010\u0012\u001a\u00020\f2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0004H\u0016J\b\u0010\u0014\u001a\u00020\fH\u0016J\b\u0010\u0015\u001a\u00020\fH\u0016J\u001a\u0010\u0016\u001a\u00020\f2\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\r\u001a\u00020\u0004H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lcom/psychiatrygarden/activity/courselist/CourseCombineDireListActivity;", "Lcom/psychiatrygarden/activity/BaseActivity;", "()V", "KEEP_QRCODE", "", "imgQRCode", "Landroid/widget/ImageView;", "isFirstEntry", "", "qrCode", "Lcom/psychiatrygarden/activity/courselist/bean/QRCode;", "getQRCode", "", "courseId", "init", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onEventMainThread", "str", "setContentView", "setListenerForWidget", "showWxWRCode", "Companion", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class CourseCombineDireListActivity extends BaseActivity {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private final String KEEP_QRCODE = "keep_qrcode";
    private ImageView imgQRCode;
    private boolean isFirstEntry;

    @Nullable
    private QRCode qrCode;

    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b¨\u0006\t"}, d2 = {"Lcom/psychiatrygarden/activity/courselist/CourseCombineDireListActivity$Companion;", "", "()V", "startActivity", "", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "courseId", "", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void startActivity(@NotNull Context context, @NotNull String courseId) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(courseId, "courseId");
            Intent intent = new Intent(context, (Class<?>) CourseCombineDireListActivity.class);
            intent.putExtra("courseId", courseId);
            context.startActivity(intent);
        }
    }

    private final void getQRCode(final String courseId) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("course_id", courseId);
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.courseQRCode, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.courselist.CourseCombineDireListActivity.getQRCode.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@NotNull Throwable t2, int errorNo, @NotNull String strMsg) {
                Intrinsics.checkNotNullParameter(t2, "t");
                Intrinsics.checkNotNullParameter(strMsg, "strMsg");
                super.onFailure(t2, errorNo, strMsg);
                ImageView imageView = CourseCombineDireListActivity.this.imgQRCode;
                if (imageView == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("imgQRCode");
                    imageView = null;
                }
                imageView.setVisibility(8);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@Nullable String t2) {
                super.onSuccess((AnonymousClass1) t2);
                if (t2 != null) {
                    CourseCombineDireListActivity courseCombineDireListActivity = CourseCombineDireListActivity.this;
                    String str = courseId;
                    ImageView imageView = null;
                    try {
                        JSONObject jSONObject = new JSONObject(t2);
                        if (!Intrinsics.areEqual(jSONObject.optString("code"), "200")) {
                            ImageView imageView2 = courseCombineDireListActivity.imgQRCode;
                            if (imageView2 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("imgQRCode");
                                imageView2 = null;
                            }
                            imageView2.setVisibility(8);
                            return;
                        }
                        courseCombineDireListActivity.qrCode = (QRCode) new Gson().fromJson(jSONObject.optString("data"), QRCode.class);
                        if (courseCombineDireListActivity.qrCode != null) {
                            QRCode qRCode = courseCombineDireListActivity.qrCode;
                            if (Intrinsics.areEqual("1", qRCode != null ? qRCode.is_open_qrcode() : null)) {
                                ImageView imageView3 = courseCombineDireListActivity.imgQRCode;
                                if (imageView3 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("imgQRCode");
                                    imageView3 = null;
                                }
                                imageView3.setVisibility(0);
                                if (courseCombineDireListActivity.isFirstEntry) {
                                    courseCombineDireListActivity.showWxWRCode(courseCombineDireListActivity.qrCode, str);
                                    return;
                                }
                                return;
                            }
                        }
                        ImageView imageView4 = courseCombineDireListActivity.imgQRCode;
                        if (imageView4 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("imgQRCode");
                            imageView4 = null;
                        }
                        imageView4.setVisibility(8);
                    } catch (Exception e2) {
                        String message = e2.getMessage();
                        Intrinsics.checkNotNull(message);
                        Log.e("Error", message);
                        ImageView imageView5 = courseCombineDireListActivity.imgQRCode;
                        if (imageView5 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("imgQRCode");
                        } else {
                            imageView = imageView5;
                        }
                        imageView.setVisibility(8);
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$0(CourseCombineDireListActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$2$lambda$1(CourseCombineDireListActivity this$0, String str, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        QRCode qRCode = this$0.qrCode;
        if (qRCode != null) {
            this$0.showWxWRCode(qRCode, str);
        } else {
            this$0.isFirstEntry = true;
            this$0.getQRCode(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showWxWRCode(QRCode qrCode, String courseId) {
        if (qrCode != null) {
            new XPopup.Builder(this).asCustom(new CourseWxPopupNew(this, qrCode.getWechat_qrcode(), qrCode.getWechat_tips(), qrCode.getWechat_number())).toggle();
            SharePreferencesUtils.writeBooleanConfig(this.KEEP_QRCODE + courseId, false, this);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        final String stringExtra = getIntent().getStringExtra("courseId");
        this.isFirstEntry = SharePreferencesUtils.readBooleanConfig(this.KEEP_QRCODE + stringExtra, true, this);
        ImageView imageView = (ImageView) findViewById(R.id.imgBack);
        View viewFindViewById = findViewById(R.id.imgQRCode);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.imgQRCode)");
        this.imgQRCode = (ImageView) viewFindViewById;
        ((TextView) findViewById(R.id.title)).setText("课程体系");
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.g
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CourseCombineDireListActivity.init$lambda$0(this.f12085c, view);
            }
        });
        if (stringExtra != null) {
            getQRCode(stringExtra);
            ImageView imageView2 = this.imgQRCode;
            if (imageView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("imgQRCode");
                imageView2 = null;
            }
            imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.h
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    CourseCombineDireListActivity.init$lambda$2$lambda$1(this.f12090c, stringExtra, view);
                }
            });
            CourseDirectoryCombineListFragment courseDirectoryCombineListFragment = new CourseDirectoryCombineListFragment();
            Bundle bundle = new Bundle();
            bundle.putString("course_id", stringExtra);
            courseDirectoryCombineListFragment.setArguments(bundle);
            if (findFragment(CourseDirectoryCombineListFragment.class) == null) {
                loadRootFragment(R.id.fragmentSys, courseDirectoryCombineListFragment);
            } else {
                replaceFragment(courseDirectoryCombineListFragment, false);
            }
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActionBar.hide();
        setNewStyleStatusBarColor();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(@Nullable String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_course_combine_dire_list);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
