package com.psychiatrygarden.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import cn.hutool.core.text.StrPool;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.http.LoadFileModel;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.MapTable;
import com.psychiatrygarden.utils.MimeTypes;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.FileTbsFragment;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsReaderView;
import com.yikaobang.yixue.R;
import java.io.File;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

/* loaded from: classes5.dex */
public class TbsFileViewActivity extends BaseActivity {
    private String article_id;
    private String fileUrl;
    private FileTbsFragment fileview;
    private String name;

    /* renamed from: com.psychiatrygarden.activity.TbsFileViewActivity$2, reason: invalid class name */
    public class AnonymousClass2 implements Callback<ResponseBody> {
        final /* synthetic */ FileTbsFragment val$mSuperFileView2;

        public AnonymousClass2(final FileTbsFragment val$mSuperFileView2) {
            this.val$mSuperFileView2 = val$mSuperFileView2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onResponse$0(FileTbsFragment fileTbsFragment, File file) {
            fileTbsFragment.displayFile(file, TbsFileViewActivity.this.article_id);
        }

        @Override // retrofit2.Callback
        public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t2) {
        }

        /* JADX WARN: Removed duplicated region for block: B:45:0x00b6 A[Catch: IOException -> 0x00b2, TRY_LEAVE, TryCatch #3 {IOException -> 0x00b2, blocks: (B:41:0x00ae, B:45:0x00b6), top: B:49:0x00ae }] */
        /* JADX WARN: Removed duplicated region for block: B:49:0x00ae A[EXC_TOP_SPLITTER, SYNTHETIC] */
        @Override // retrofit2.Callback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onResponse(@androidx.annotation.NonNull retrofit2.Call<okhttp3.ResponseBody> r12, @androidx.annotation.NonNull retrofit2.Response<okhttp3.ResponseBody> r13) throws java.lang.Throwable {
            /*
                r11 = this;
                r12 = 2048(0x800, float:2.87E-42)
                byte[] r12 = new byte[r12]
                r0 = 0
                com.psychiatrygarden.activity.TbsFileViewActivity r1 = com.psychiatrygarden.activity.TbsFileViewActivity.this     // Catch: java.lang.Throwable -> L91 java.lang.Exception -> L94
                java.io.File r1 = com.psychiatrygarden.activity.TbsFileViewActivity.access$000(r1)     // Catch: java.lang.Throwable -> L91 java.lang.Exception -> L94
                boolean r2 = r1.exists()     // Catch: java.lang.Throwable -> L91 java.lang.Exception -> L94
                if (r2 != 0) goto L14
                r1.mkdirs()     // Catch: java.lang.Throwable -> L91 java.lang.Exception -> L94
            L14:
                java.lang.Object r13 = r13.body()     // Catch: java.lang.Throwable -> L91 java.lang.Exception -> L94
                okhttp3.ResponseBody r13 = (okhttp3.ResponseBody) r13     // Catch: java.lang.Throwable -> L91 java.lang.Exception -> L94
                java.io.InputStream r2 = r13.byteStream()     // Catch: java.lang.Throwable -> L91 java.lang.Exception -> L94
                long r3 = r13.getContentLength()     // Catch: java.lang.Throwable -> L89 java.lang.Exception -> L8d
                com.psychiatrygarden.activity.TbsFileViewActivity r13 = com.psychiatrygarden.activity.TbsFileViewActivity.this     // Catch: java.lang.Throwable -> L89 java.lang.Exception -> L8d
                java.lang.String r5 = com.psychiatrygarden.activity.TbsFileViewActivity.access$100(r13)     // Catch: java.lang.Throwable -> L89 java.lang.Exception -> L8d
                java.io.File r13 = com.psychiatrygarden.activity.TbsFileViewActivity.access$200(r13, r5)     // Catch: java.lang.Throwable -> L89 java.lang.Exception -> L8d
                java.io.FileOutputStream r5 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L89 java.lang.Exception -> L8d
                r5.<init>(r13)     // Catch: java.lang.Throwable -> L89 java.lang.Exception -> L8d
                r6 = 0
            L33:
                int r0 = r2.read(r12)     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L87
                r8 = -1
                if (r0 == r8) goto L6f
                r8 = 0
                r5.write(r12, r8, r0)     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L87
                long r8 = (long) r0     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L87
                long r6 = r6 + r8
                float r0 = (float) r6     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L87
                r8 = 1065353216(0x3f800000, float:1.0)
                float r0 = r0 * r8
                float r8 = (float) r3     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L87
                float r0 = r0 / r8
                r8 = 1120403456(0x42c80000, float:100.0)
                float r0 = r0 * r8
                int r0 = (int) r0     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L87
                com.psychiatrygarden.activity.TbsFileViewActivity r8 = com.psychiatrygarden.activity.TbsFileViewActivity.this     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L87
                java.lang.String r8 = r8.TAG     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L87
                java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L87
                r9.<init>()     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L87
                java.lang.String r10 = "写入缓存文件"
                r9.append(r10)     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L87
                java.lang.String r10 = r1.getName()     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L87
                r9.append(r10)     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L87
                java.lang.String r10 = "进度: "
                r9.append(r10)     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L87
                r9.append(r0)     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L87
                java.lang.String r0 = r9.toString()     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L87
                android.util.Log.d(r8, r0)     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L87
                goto L33
            L6f:
                r5.flush()     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L87
                com.psychiatrygarden.activity.TbsFileViewActivity r12 = com.psychiatrygarden.activity.TbsFileViewActivity.this     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L87
                com.psychiatrygarden.widget.FileTbsFragment r0 = r11.val$mSuperFileView2     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L87
                com.psychiatrygarden.activity.uo r1 = new com.psychiatrygarden.activity.uo     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L87
                r1.<init>()     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L87
                r12.runOnUiThread(r1)     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L87
                r2.close()     // Catch: java.io.IOException -> L9f
                r5.close()     // Catch: java.io.IOException -> L9f
                goto Laa
            L85:
                r12 = move-exception
                goto L8b
            L87:
                r12 = move-exception
                goto L8f
            L89:
                r12 = move-exception
                r5 = r0
            L8b:
                r0 = r2
                goto Lac
            L8d:
                r12 = move-exception
                r5 = r0
            L8f:
                r0 = r2
                goto L96
            L91:
                r12 = move-exception
                r5 = r0
                goto Lac
            L94:
                r12 = move-exception
                r5 = r0
            L96:
                r12.printStackTrace()     // Catch: java.lang.Throwable -> Lab
                if (r0 == 0) goto La1
                r0.close()     // Catch: java.io.IOException -> L9f
                goto La1
            L9f:
                r12 = move-exception
                goto La7
            La1:
                if (r5 == 0) goto Laa
                r5.close()     // Catch: java.io.IOException -> L9f
                goto Laa
            La7:
                r12.printStackTrace()
            Laa:
                return
            Lab:
                r12 = move-exception
            Lac:
                if (r0 == 0) goto Lb4
                r0.close()     // Catch: java.io.IOException -> Lb2
                goto Lb4
            Lb2:
                r13 = move-exception
                goto Lba
            Lb4:
                if (r5 == 0) goto Lbd
                r5.close()     // Catch: java.io.IOException -> Lb2
                goto Lbd
            Lba:
                r13.printStackTrace()
            Lbd:
                throw r12
            */
            throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.TbsFileViewActivity.AnonymousClass2.onResponse(retrofit2.Call, retrofit2.Response):void");
        }
    }

    /* renamed from: com.psychiatrygarden.activity.TbsFileViewActivity$3, reason: invalid class name */
    public class AnonymousClass3 implements Callback<ResponseBody> {
        final /* synthetic */ FileTbsFragment val$mSuperFileView2;
        final /* synthetic */ String val$url;

        public AnonymousClass3(final String val$url, final FileTbsFragment val$mSuperFileView2) {
            this.val$url = val$url;
            this.val$mSuperFileView2 = val$mSuperFileView2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onResponse$0(FileTbsFragment fileTbsFragment, File file) {
            fileTbsFragment.displayFile(file, TbsFileViewActivity.this.article_id);
        }

        @Override // retrofit2.Callback
        public void onFailure(Call<ResponseBody> call, Throwable t2) {
            Log.d(TbsFileViewActivity.this.TAG, "文件下载失败");
            TbsFileViewActivity.this.hideProgressDialog();
            File cacheFile = TbsFileViewActivity.this.getCacheFile(this.val$url);
            if (cacheFile.exists()) {
                return;
            }
            Log.d(TbsFileViewActivity.this.TAG, "删除下载失败文件");
            cacheFile.delete();
        }

        /* JADX WARN: Removed duplicated region for block: B:66:0x0100 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:70:0x010a A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:80:? A[SYNTHETIC] */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:46:0x00f4 -> B:63:0x00f7). Please report as a decompilation issue!!! */
        @Override // retrofit2.Callback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onResponse(@androidx.annotation.NonNull retrofit2.Call<okhttp3.ResponseBody> r11, @androidx.annotation.NonNull retrofit2.Response<okhttp3.ResponseBody> r12) throws java.lang.Throwable {
            /*
                Method dump skipped, instructions count: 275
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.TbsFileViewActivity.AnonymousClass3.onResponse(retrofit2.Call, retrofit2.Response):void");
        }
    }

    /* renamed from: com.psychiatrygarden.activity.TbsFileViewActivity$4, reason: invalid class name */
    public class AnonymousClass4 implements Callback<ResponseBody> {
        public AnonymousClass4() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onResponse$0(File file) {
            Bundle bundle = new Bundle();
            bundle.putString(TbsReaderView.KEY_FILE_PATH, TbsFileViewActivity.this.getCachePath());
            bundle.putString(TbsReaderView.KEY_TEMP_PATH, Environment.getExternalStorageDirectory() + "/TbsReaderTemp");
            QbSdk.openFileReader(TbsFileViewActivity.this, file.getPath(), null, null);
        }

        @Override // retrofit2.Callback
        public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t2) {
        }

        /* JADX WARN: Removed duplicated region for block: B:45:0x00b4 A[Catch: IOException -> 0x00b0, TRY_LEAVE, TryCatch #4 {IOException -> 0x00b0, blocks: (B:41:0x00ac, B:45:0x00b4), top: B:51:0x00ac }] */
        /* JADX WARN: Removed duplicated region for block: B:51:0x00ac A[EXC_TOP_SPLITTER, SYNTHETIC] */
        @Override // retrofit2.Callback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onResponse(@androidx.annotation.NonNull retrofit2.Call<okhttp3.ResponseBody> r11, @androidx.annotation.NonNull retrofit2.Response<okhttp3.ResponseBody> r12) throws java.lang.Throwable {
            /*
                r10 = this;
                r11 = 2048(0x800, float:2.87E-42)
                byte[] r11 = new byte[r11]
                r0 = 0
                com.psychiatrygarden.activity.TbsFileViewActivity r1 = com.psychiatrygarden.activity.TbsFileViewActivity.this     // Catch: java.lang.Throwable -> L8f java.lang.Exception -> L92
                java.io.File r1 = com.psychiatrygarden.activity.TbsFileViewActivity.access$000(r1)     // Catch: java.lang.Throwable -> L8f java.lang.Exception -> L92
                boolean r2 = r1.exists()     // Catch: java.lang.Throwable -> L8f java.lang.Exception -> L92
                if (r2 != 0) goto L14
                r1.mkdirs()     // Catch: java.lang.Throwable -> L8f java.lang.Exception -> L92
            L14:
                java.lang.Object r12 = r12.body()     // Catch: java.lang.Throwable -> L8f java.lang.Exception -> L92
                okhttp3.ResponseBody r12 = (okhttp3.ResponseBody) r12     // Catch: java.lang.Throwable -> L8f java.lang.Exception -> L92
                java.io.InputStream r2 = r12.byteStream()     // Catch: java.lang.Throwable -> L8f java.lang.Exception -> L92
                long r3 = r12.getContentLength()     // Catch: java.lang.Throwable -> L87 java.lang.Exception -> L8b
                com.psychiatrygarden.activity.TbsFileViewActivity r12 = com.psychiatrygarden.activity.TbsFileViewActivity.this     // Catch: java.lang.Throwable -> L87 java.lang.Exception -> L8b
                java.lang.String r5 = com.psychiatrygarden.activity.TbsFileViewActivity.access$100(r12)     // Catch: java.lang.Throwable -> L87 java.lang.Exception -> L8b
                java.io.File r12 = com.psychiatrygarden.activity.TbsFileViewActivity.access$200(r12, r5)     // Catch: java.lang.Throwable -> L87 java.lang.Exception -> L8b
                java.io.FileOutputStream r5 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L87 java.lang.Exception -> L8b
                r5.<init>(r12)     // Catch: java.lang.Throwable -> L87 java.lang.Exception -> L8b
                r6 = 0
            L33:
                int r12 = r2.read(r11)     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L85
                r0 = -1
                if (r12 == r0) goto L6f
                r0 = 0
                r5.write(r11, r0, r12)     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L85
                long r8 = (long) r12     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L85
                long r6 = r6 + r8
                float r12 = (float) r6     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L85
                r0 = 1065353216(0x3f800000, float:1.0)
                float r12 = r12 * r0
                float r0 = (float) r3     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L85
                float r12 = r12 / r0
                r0 = 1120403456(0x42c80000, float:100.0)
                float r12 = r12 * r0
                int r12 = (int) r12     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L85
                com.psychiatrygarden.activity.TbsFileViewActivity r0 = com.psychiatrygarden.activity.TbsFileViewActivity.this     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L85
                java.lang.String r0 = r0.TAG     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L85
                java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L85
                r8.<init>()     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L85
                java.lang.String r9 = "写入缓存文件"
                r8.append(r9)     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L85
                java.lang.String r9 = r1.getName()     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L85
                r8.append(r9)     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L85
                java.lang.String r9 = "进度: "
                r8.append(r9)     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L85
                r8.append(r12)     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L85
                java.lang.String r12 = r8.toString()     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L85
                android.util.Log.d(r0, r12)     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L85
                goto L33
            L6f:
                r5.flush()     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L85
                com.psychiatrygarden.activity.TbsFileViewActivity r11 = com.psychiatrygarden.activity.TbsFileViewActivity.this     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L85
                com.psychiatrygarden.activity.wo r12 = new com.psychiatrygarden.activity.wo     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L85
                r12.<init>()     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L85
                r11.runOnUiThread(r12)     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L85
                r2.close()     // Catch: java.io.IOException -> L9d
                r5.close()     // Catch: java.io.IOException -> L9d
                goto La8
            L83:
                r11 = move-exception
                goto L89
            L85:
                r11 = move-exception
                goto L8d
            L87:
                r11 = move-exception
                r5 = r0
            L89:
                r0 = r2
                goto Laa
            L8b:
                r11 = move-exception
                r5 = r0
            L8d:
                r0 = r2
                goto L94
            L8f:
                r11 = move-exception
                r5 = r0
                goto Laa
            L92:
                r11 = move-exception
                r5 = r0
            L94:
                r11.printStackTrace()     // Catch: java.lang.Throwable -> La9
                if (r0 == 0) goto L9f
                r0.close()     // Catch: java.io.IOException -> L9d
                goto L9f
            L9d:
                r11 = move-exception
                goto La5
            L9f:
                if (r5 == 0) goto La8
                r5.close()     // Catch: java.io.IOException -> L9d
                goto La8
            La5:
                r11.printStackTrace()
            La8:
                return
            La9:
                r11 = move-exception
            Laa:
                if (r0 == 0) goto Lb2
                r0.close()     // Catch: java.io.IOException -> Lb0
                goto Lb2
            Lb0:
                r12 = move-exception
                goto Lb8
            Lb2:
                if (r5 == 0) goto Lbb
                r5.close()     // Catch: java.io.IOException -> Lb0
                goto Lbb
            Lb8:
                r12.printStackTrace()
            Lbb:
                throw r11
            */
            throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.TbsFileViewActivity.AnonymousClass4.onResponse(retrofit2.Call, retrofit2.Response):void");
        }
    }

    private void downLoadFromNet(final String url, final FileTbsFragment mSuperFileView2) {
        showProgressDialog("加载中...");
        File cacheFile = getCacheFile(url);
        if (!cacheFile.exists() || cacheFile.length() > 0) {
            LoadFileModel.getRetrofitManager().loadPdfFile(url, new AnonymousClass3(url, mSuperFileView2));
        } else {
            cacheFile.delete();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public File getCacheFile(String url) {
        if (Build.VERSION.SDK_INT >= 29) {
            return new File(ProjectApp.instance().getExternalFilesDir(null).getAbsolutePath() + "/TbsReaderTemp/" + getFileName(url));
        }
        return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/TbsReaderTemp/" + getFileName(url));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public File getFileCacheDirs() {
        if (Build.VERSION.SDK_INT >= 29) {
            return new File(ProjectApp.instance().getExternalFilesDir(null).getAbsolutePath() + "/TbsReaderTemp/");
        }
        return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/TbsReaderTemp/");
    }

    private String getFileName(String url) {
        return this.name + StrPool.DOT + getFileType(url);
    }

    private String getFileType(String paramString) {
        int iLastIndexOf;
        return (TextUtils.isEmpty(paramString) || (iLastIndexOf = paramString.lastIndexOf(46)) == -1) ? "" : paramString.substring(iLastIndexOf + 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        OpenByOther();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(View view) {
        showDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$3(FileTbsFragment fileTbsFragment) {
        if (UserConfig.getInstance().getUser().getIs_vip().equals("1")) {
            downLoadFromNet(this.fileUrl, fileTbsFragment);
        } else {
            showLineView(this.fileUrl, fileTbsFragment);
        }
    }

    public void OpenByOther() {
        try {
            Intent intent = new Intent();
            intent.addFlags(268435456);
            intent.setAction("android.intent.action.VIEW");
            if (Build.VERSION.SDK_INT >= 24) {
                intent.setFlags(1);
                intent.setDataAndType(FileProvider.getUriForFile(this, getPackageName() + ".fileProvider", getCacheFile(getCachePath())), MapTable.getMIMEType(getCachePath()));
            } else {
                intent.setDataAndType(Uri.fromFile(new File(getCachePath())), MapTable.getMIMEType(getCachePath()));
            }
            startActivity(intent);
            Intent.createChooser(intent, "请选择对应的软件打开该附件！");
        } catch (ActivityNotFoundException unused) {
            ToastUtil.shortToast(this, "附件打不开，请下载相关软件！");
        }
    }

    public String getCachePath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/yikaobang/TbsReaderTemp/" + this.article_id + "/" + getFileName(this.fileUrl);
    }

    public void getStatistic() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("enclosure_id", "" + getIntent().getExtras().getString("id"));
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.viewApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.TbsFileViewActivity.1
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
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        String string = getIntent().getExtras().getString("type_name");
        this.fileUrl = getIntent().getExtras().getString("fileurl");
        String string2 = getIntent().getExtras().getString("title");
        this.name = getIntent().getExtras().getString("name");
        this.article_id = getIntent().getExtras().getString("article_id");
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.lineview);
        TextView textView = (TextView) findViewById(R.id.titlename);
        TextView textView2 = (TextView) findViewById(R.id.txtsize);
        TextView textView3 = (TextView) findViewById(R.id.opentxt);
        this.fileview = (FileTbsFragment) findViewById(R.id.fileview);
        ((TextView) findViewById(R.id.jmui_title)).setText(string2);
        ImageButton imageButton = (ImageButton) findViewById(R.id.jmui_return_btn);
        TextView textView4 = (TextView) findViewById(R.id.xiazai);
        textView.setText(string2);
        textView2.setText(String.format("文件大小 %s", getIntent().getExtras().getString("type_size")));
        textView3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.qo
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13748c.lambda$init$0(view);
            }
        });
        if (string.toUpperCase().contains("ZIP") || string.toUpperCase().contains("RAR")) {
            linearLayout.setVisibility(0);
            this.fileview.setVisibility(8);
        } else {
            linearLayout.setVisibility(8);
            this.fileview.setVisibility(0);
        }
        imageButton.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ro
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13806c.lambda$init$1(view);
            }
        });
        if (UserConfig.getInstance().getUser().getIs_vip().equals("1")) {
            textView4.setVisibility(0);
        } else {
            textView4.setVisibility(8);
        }
        textView4.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.so
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13929c.lambda$init$2(view);
            }
        });
        this.fileview.setOnGetFilePathListener(new FileTbsFragment.OnGetFilePathListener() { // from class: com.psychiatrygarden.activity.to
            @Override // com.psychiatrygarden.widget.FileTbsFragment.OnGetFilePathListener
            public final void onGetFilePath(FileTbsFragment fileTbsFragment) {
                this.f13964a.lambda$init$3(fileTbsFragment);
            }
        });
        if (Build.VERSION.SDK_INT > 30) {
            showLineView(this.fileUrl);
        } else {
            this.fileview.show();
        }
        getStatistic();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActionBar.hide();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        FileTbsFragment fileTbsFragment = this.fileview;
        if (fileTbsFragment != null) {
            fileTbsFragment.onStopDisplay();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_main2);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }

    public void showDialog() {
        if (Build.VERSION.SDK_INT <= 23) {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(getCacheFile(this.fileUrl)));
            intent.setType(MimeTypes.ANY_TYPE);
            startActivity(Intent.createChooser(intent, "分享到："));
            return;
        }
        Uri uriForFile = FileProvider.getUriForFile(this, getPackageName() + ".fileProvider", getCacheFile(this.fileUrl));
        Intent intent2 = new Intent("android.intent.action.SEND");
        intent2.putExtra("android.intent.extra.STREAM", uriForFile);
        intent2.setType(MimeTypes.ANY_TYPE);
        startActivity(Intent.createChooser(intent2, "分享到："));
    }

    public void showLineView(String url, final FileTbsFragment mSuperFileView2) {
        try {
            LoadFileModel.getRetrofitManager().loadPdfFile(url, new AnonymousClass2(mSuperFileView2));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void showLineView(String url) {
        try {
            LoadFileModel.getRetrofitManager().loadPdfFile(url, new AnonymousClass4());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
