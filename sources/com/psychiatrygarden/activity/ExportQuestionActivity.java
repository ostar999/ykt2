package com.psychiatrygarden.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import com.aliyun.vod.common.utils.UriUtil;
import com.hjq.permissions.Permission;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.psychiatrygarden.activity.online.fragment.QuestionBankNewFragment;
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
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class ExportQuestionActivity extends BaseActivity {
    private String type;
    private String notefileurl = Environment.getExternalStorageDirectory().getAbsoluteFile() + "/yikaobang/" + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext) + "/";
    private String noteFilePath = null;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getChapterIdData$0() {
        ActivityCompat.requestPermissions(this, new String[]{Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE}, 1);
    }

    public void getChapterIdData(String chapterids) {
        if (CommonUtil.hasRequiredPermissionsWriteStorage(this)) {
            getNoteDownLoad(chapterids);
        } else {
            new XPopup.Builder(this).asCustom(new RequestMediaPermissionPop(this, new OnConfirmListener() { // from class: com.psychiatrygarden.activity.ka
                @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                public final void onConfirm() {
                    this.f12584a.lambda$getChapterIdData$0();
                }
            })).show();
        }
    }

    public void getNoteDownLoad(String chapters) {
        String str;
        File file = new File(this.noteFilePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        CommonUtil.deleteFile(new File(this.noteFilePath));
        AjaxParams ajaxParams = new AjaxParams();
        if (this.type.equals("error")) {
            str = NetworkRequestsURL.wcApi;
            ajaxParams.put("type", "wrong");
        } else if (this.type.equals("collection")) {
            str = NetworkRequestsURL.wcApi;
            ajaxParams.put("type", "collection");
        } else {
            str = NetworkRequestsURL.noteFileApi;
        }
        ajaxParams.put("chapters", "" + chapters);
        ajaxParams.put("version", "2");
        ajaxParams.put("identity_id", "" + getIntent().getBundleExtra("bundle").getString("identity_id"));
        ajaxParams.put("am_pm", SharePreferencesUtils.readStrConfig(CommonParameter.am_pm, this, "0"));
        if (SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext).equals("40")) {
            ajaxParams.put("school_year", getIntent().getBundleExtra("bundle").getString(UriUtil.QUERY_CATEGORY));
        } else {
            ajaxParams.put("school_year", "");
        }
        ajaxParams.put("uid", UserConfig.getUserId());
        ajaxParams.put("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this, "1"));
        LogUtils.d("export__", ajaxParams.getParamString() + ",url=" + str);
        YJYHttpUtils.getNoteFile(this, str, this.noteFilePath, ajaxParams, new AjaxCallBack<File>() { // from class: com.psychiatrygarden.activity.ExportQuestionActivity.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ExportQuestionActivity.this.hideProgressDialog();
                if (errorNo == 0) {
                    ExportQuestionActivity.this.AlertToast("无法导出,请检查app存储权限是否打开！");
                    return;
                }
                if (errorNo == 200) {
                    ExportQuestionActivity.this.AlertToast(strMsg);
                    return;
                }
                ExportQuestionActivity.this.AlertToast(strMsg + "");
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onLoading(long count, long current) {
                super.onLoading(count, current);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                ExportQuestionActivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(File file2) {
                super.onSuccess((AnonymousClass1) file2);
                ExportQuestionActivity.this.hideProgressDialog();
                CommonUtil.showDialog(ExportQuestionActivity.this, file2.getAbsolutePath());
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        if (findFragment(QuestionBankNewFragment.class) == null) {
            QuestionBankNewFragment questionBankNewFragment = new QuestionBankNewFragment();
            Bundle bundle = new Bundle();
            if (getIntent().getBundleExtra("bundle").getString(UriUtil.QUERY_CATEGORY).equals("year") || getIntent().getBundleExtra("bundle").getString(UriUtil.QUERY_CATEGORY).equals("part")) {
                bundle.putString(UriUtil.QUERY_CATEGORY, "chapter");
            } else {
                bundle.putString(UriUtil.QUERY_CATEGORY, getIntent().getBundleExtra("bundle").getString(UriUtil.QUERY_CATEGORY));
            }
            bundle.putString("identity_id", "" + getIntent().getBundleExtra("bundle").getString("identity_id"));
            bundle.putString("module_type", "" + getIntent().getBundleExtra("bundle").getString("module_type"));
            bundle.putString("type", "export_" + getIntent().getBundleExtra("bundle").getString("type"));
            questionBankNewFragment.setArguments(bundle);
            loadRootFragment(R.id.fragmentexport, questionBankNewFragment);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (1 == requestCode && grantResults[0] == -1 && !ActivityCompat.shouldShowRequestPermissionRationale(this, Permission.WRITE_EXTERNAL_STORAGE)) {
            ToastUtil.shortToast(this, "无法导出，请检查app存储权限是否打开！");
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        String string = getIntent().getBundleExtra("bundle").getString("type");
        this.type = string;
        string.hashCode();
        switch (string) {
            case "collection":
                setTitle("收藏导出");
                break;
            case "note":
                setTitle("笔记导出");
                break;
            case "error":
                setTitle("错题导出");
                break;
            default:
                this.type = "";
                break;
        }
        setContentView(R.layout.activity_export_question);
        if (Build.VERSION.SDK_INT >= 29) {
            this.noteFilePath = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getPath();
        } else {
            this.noteFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
