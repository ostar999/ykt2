package com.psychiatrygarden.activity.mine.errorquestion;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import androidx.core.app.ActivityCompat;
import com.aliyun.vod.common.utils.UriUtil;
import com.hjq.permissions.Permission;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.mobile.auth.gatewayauth.Constant;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.RequestMediaPermissionPop;
import com.yikaobang.yixue.R;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0005\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0004J\u0010\u0010\t\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u0004H\u0002J\b\u0010\u000b\u001a\u00020\u0007H\u0016J\u0012\u0010\f\u001a\u00020\u00072\b\u0010\r\u001a\u0004\u0018\u00010\u0004H\u0016J-\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u00102\u000e\u0010\u0011\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016¢\u0006\u0002\u0010\u0015J\b\u0010\u0016\u001a\u00020\u0007H\u0016J\b\u0010\u0017\u001a\u00020\u0007H\u0016R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lcom/psychiatrygarden/activity/mine/errorquestion/ExportQuestionNewActivity;", "Lcom/psychiatrygarden/activity/BaseActivity;", "()V", "noteFilePath", "", "type", "getChapterIdData", "", "chapterIds", "getNoteDownLoad", "chapters", "init", "onEventMainThread", "str", "onRequestPermissionsResult", Constant.LOGIN_ACTIVITY_REQUEST_CODE, "", "permissions", "", "grantResults", "", "(I[Ljava/lang/String;[I)V", "setContentView", "setListenerForWidget", "Companion", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class ExportQuestionNewActivity extends BaseActivity {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private String noteFilePath;

    @Nullable
    private String type;

    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b¨\u0006\t"}, d2 = {"Lcom/psychiatrygarden/activity/mine/errorquestion/ExportQuestionNewActivity$Companion;", "", "()V", "navigationToExportQuestionActivity", "", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "bundle", "Landroid/os/Bundle;", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void navigationToExportQuestionActivity(@NotNull Context context, @NotNull Bundle bundle) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intent intent = new Intent(context, (Class<?>) ExportQuestionNewActivity.class);
            intent.putExtra("bundle", bundle);
            context.startActivity(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void getChapterIdData$lambda$0(ExportQuestionNewActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ActivityCompat.requestPermissions(this$0, new String[]{Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE}, 1);
    }

    private final void getNoteDownLoad(String chapters) {
        String noteFileApi;
        String str = this.noteFilePath;
        Intrinsics.checkNotNull(str);
        File file = new File(str);
        if (!file.exists()) {
            file.mkdirs();
        }
        String str2 = this.noteFilePath;
        Intrinsics.checkNotNull(str2);
        CommonUtil.deleteFile(new File(str2));
        AjaxParams ajaxParams = new AjaxParams();
        String str3 = this.type;
        if (Intrinsics.areEqual(str3, "error")) {
            noteFileApi = NetworkRequestsURL.wcApi;
            Intrinsics.checkNotNullExpressionValue(noteFileApi, "wcApi");
            ajaxParams.put("type", "wrong");
        } else if (Intrinsics.areEqual(str3, "collection")) {
            noteFileApi = NetworkRequestsURL.wcApi;
            Intrinsics.checkNotNullExpressionValue(noteFileApi, "wcApi");
            ajaxParams.put("type", "collection");
        } else {
            noteFileApi = NetworkRequestsURL.noteFileApi;
            Intrinsics.checkNotNullExpressionValue(noteFileApi, "noteFileApi");
        }
        ajaxParams.put("chapters", "" + chapters);
        ajaxParams.put("version", "2");
        StringBuilder sb = new StringBuilder();
        sb.append("");
        Bundle bundleExtra = getIntent().getBundleExtra("bundle");
        Intrinsics.checkNotNull(bundleExtra);
        sb.append(bundleExtra.getString("identity_id"));
        ajaxParams.put("identity_id", sb.toString());
        ajaxParams.put("am_pm", SharePreferencesUtils.readStrConfig(CommonParameter.am_pm, this, "0"));
        if (Intrinsics.areEqual(SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext), "40")) {
            Bundle bundleExtra2 = getIntent().getBundleExtra("bundle");
            Intrinsics.checkNotNull(bundleExtra2);
            ajaxParams.put("school_year", bundleExtra2.getString(UriUtil.QUERY_CATEGORY));
        } else {
            ajaxParams.put("school_year", "");
        }
        ajaxParams.put("uid", UserConfig.getUserId());
        ajaxParams.put("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this, "1"));
        LogUtils.d("export__", ajaxParams.getParamString() + ",url=" + noteFileApi);
        YJYHttpUtils.getNoteFile(this, noteFileApi, this.noteFilePath, ajaxParams, new AjaxCallBack<File>() { // from class: com.psychiatrygarden.activity.mine.errorquestion.ExportQuestionNewActivity.getNoteDownLoad.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@NotNull Throwable t2, int errorNo, @NotNull String strMsg) {
                Intrinsics.checkNotNullParameter(t2, "t");
                Intrinsics.checkNotNullParameter(strMsg, "strMsg");
                super.onFailure(t2, errorNo, strMsg);
                ExportQuestionNewActivity.this.hideProgressDialog();
                if (errorNo == 0) {
                    ExportQuestionNewActivity.this.AlertToast("无法导出,请检查app存储权限是否打开！");
                    return;
                }
                if (errorNo == 200) {
                    ExportQuestionNewActivity.this.AlertToast(strMsg);
                    return;
                }
                ExportQuestionNewActivity.this.AlertToast(strMsg + "");
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                ExportQuestionNewActivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@Nullable File file2) {
                super.onSuccess((AnonymousClass1) file2);
                ExportQuestionNewActivity.this.hideProgressDialog();
                if (file2 != null) {
                    CommonUtil.showDialog(ExportQuestionNewActivity.this, file2.getAbsolutePath());
                }
            }
        });
    }

    public final void getChapterIdData(@NotNull String chapterIds) {
        Intrinsics.checkNotNullParameter(chapterIds, "chapterIds");
        if (CommonUtil.hasRequiredPermissionsWriteStorage(this.mContext)) {
            getNoteDownLoad(chapterIds);
            return;
        }
        XPopup.Builder builder = new XPopup.Builder(this.mContext);
        Context mContext = this.mContext;
        Intrinsics.checkNotNullExpressionValue(mContext, "mContext");
        builder.asCustom(new RequestMediaPermissionPop(mContext, new OnConfirmListener() { // from class: com.psychiatrygarden.activity.mine.errorquestion.a
            @Override // com.lxj.xpopup.interfaces.OnConfirmListener
            public final void onConfirm() {
                ExportQuestionNewActivity.getChapterIdData$lambda$0(this.f12835a);
            }
        })).show();
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0058  */
    @Override // com.psychiatrygarden.activity.BaseActivity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void init() {
        /*
            r7 = this;
            java.lang.Class<com.psychiatrygarden.activity.mine.errorquestion.QuestionBankNew2Fragment> r0 = com.psychiatrygarden.activity.mine.errorquestion.QuestionBankNew2Fragment.class
            cn.webdemo.com.supporfragment.ISupportFragment r0 = r7.findFragment(r0)
            if (r0 != 0) goto Ld3
            com.psychiatrygarden.activity.mine.errorquestion.QuestionBankNew2Fragment r0 = new com.psychiatrygarden.activity.mine.errorquestion.QuestionBankNew2Fragment
            r0.<init>()
            android.os.Bundle r1 = new android.os.Bundle
            r1.<init>()
            android.content.Intent r2 = r7.getIntent()
            java.lang.String r3 = "bundle"
            android.os.Bundle r2 = r2.getBundleExtra(r3)
            kotlin.jvm.internal.Intrinsics.checkNotNull(r2)
            java.lang.String r4 = "category"
            java.lang.String r2 = r2.getString(r4)
            java.lang.String r5 = "year"
            boolean r2 = kotlin.jvm.internal.Intrinsics.areEqual(r2, r5)
            if (r2 != 0) goto L58
            android.content.Intent r2 = r7.getIntent()
            android.os.Bundle r2 = r2.getBundleExtra(r3)
            kotlin.jvm.internal.Intrinsics.checkNotNull(r2)
            java.lang.String r2 = r2.getString(r4)
            java.lang.String r5 = "part"
            boolean r2 = kotlin.jvm.internal.Intrinsics.areEqual(r2, r5)
            if (r2 == 0) goto L45
            goto L58
        L45:
            android.content.Intent r2 = r7.getIntent()
            android.os.Bundle r2 = r2.getBundleExtra(r3)
            kotlin.jvm.internal.Intrinsics.checkNotNull(r2)
            java.lang.String r2 = r2.getString(r4)
            r1.putString(r4, r2)
            goto L5d
        L58:
            java.lang.String r2 = "chapter"
            r1.putString(r4, r2)
        L5d:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r4 = ""
            r2.append(r4)
            android.content.Intent r5 = r7.getIntent()
            android.os.Bundle r5 = r5.getBundleExtra(r3)
            kotlin.jvm.internal.Intrinsics.checkNotNull(r5)
            java.lang.String r6 = "identity_id"
            java.lang.String r5 = r5.getString(r6)
            r2.append(r5)
            java.lang.String r2 = r2.toString()
            r1.putString(r6, r2)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r4)
            android.content.Intent r4 = r7.getIntent()
            android.os.Bundle r4 = r4.getBundleExtra(r3)
            kotlin.jvm.internal.Intrinsics.checkNotNull(r4)
            java.lang.String r5 = "module_type"
            java.lang.String r4 = r4.getString(r5)
            r2.append(r4)
            java.lang.String r2 = r2.toString()
            r1.putString(r5, r2)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r4 = "export_"
            r2.append(r4)
            android.content.Intent r4 = r7.getIntent()
            android.os.Bundle r3 = r4.getBundleExtra(r3)
            kotlin.jvm.internal.Intrinsics.checkNotNull(r3)
            java.lang.String r4 = "type"
            java.lang.String r3 = r3.getString(r4)
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.putString(r4, r2)
            r0.setArguments(r1)
            r1 = 2131363342(0x7f0a060e, float:1.834649E38)
            r7.loadRootFragment(r1, r0)
        Ld3:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.mine.errorquestion.ExportQuestionNewActivity.init():void");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(@Nullable String str) {
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        Intrinsics.checkNotNullParameter(permissions, "permissions");
        Intrinsics.checkNotNullParameter(grantResults, "grantResults");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (1 == requestCode && grantResults[0] == -1 && !ActivityCompat.shouldShowRequestPermissionRationale(this, Permission.WRITE_EXTERNAL_STORAGE)) {
            ToastUtil.shortToast(this, "无法导出，请检查app存储权限是否打开！");
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        String path;
        this.mActionBar.hide();
        Bundle bundleExtra = getIntent().getBundleExtra("bundle");
        Intrinsics.checkNotNull(bundleExtra);
        this.type = bundleExtra.getString("type");
        setContentView(R.layout.activity_export_question_new);
        if (Build.VERSION.SDK_INT >= 29) {
            File externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
            Intrinsics.checkNotNull(externalFilesDir);
            path = externalFilesDir.getPath();
        } else {
            path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath();
        }
        this.noteFilePath = path;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
