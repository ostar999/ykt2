package com.psychiatrygarden.activity.material;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.core.provider.FontsContractCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import cn.hutool.core.text.StrPool;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadEntity;
import com.arialyy.aria.core.task.DownloadTask;
import com.google.gson.Gson;
import com.hjq.permissions.Permission;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.CommentNewActivity;
import com.psychiatrygarden.activity.CorpCupActivity;
import com.psychiatrygarden.activity.mine.setting.DownloadSetting;
import com.psychiatrygarden.activity.vip.Utils.MemInterface;
import com.psychiatrygarden.bean.ExitFromPipEvent;
import com.psychiatrygarden.bean.HandoutEvent;
import com.psychiatrygarden.bean.MaterialListBean;
import com.psychiatrygarden.bean.ResourceBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.LoadFileModel;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.onDialogClickListener;
import com.psychiatrygarden.service.ResourceDownloadService;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.MimeTypes;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.PopupShowManager;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SdkConstant;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.CancelConfrimPop;
import com.psychiatrygarden.widget.CusomNewDialog;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.psychiatrygarden.widget.DialogInput;
import com.psychiatrygarden.widget.DownloadTipPop;
import com.psychiatrygarden.widget.RequestMediaPermissionPop;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.tencent.open.SocialConstants;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.io.File;
import java.util.List;
import java.util.Locale;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import okhttp3.ResponseBody;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;

/* loaded from: classes5.dex */
public class InformationPreviewAct extends BaseActivity implements ResourceDownloadService.DownloadCallBack {
    private static final String TAG = "wwwwww InformationPreviewAct";
    private String bookAuthor;
    private int downX;
    private int downY;
    private String downloadCount;
    private String fileId;
    private String fileName;
    private String fileSize;
    private boolean fromLive;
    private boolean hasSend;
    private boolean isLocal;
    private LinearLayout mBtnCollection;
    private TextView mBtnComment;
    private LinearLayout mBtnShow;
    private View mDesLine;
    private ResourceDownloadService.DownloadBinder mDownloadBinder;
    private DownloadTipPop mDownloadTipPop;
    private ImageView mImgBack;
    private ImageView mImgCollection;
    private ImageView mImgComment;
    private ImageView mImgDetailColl;
    private ImageView mImgDownload;
    private RoundedImageView mImgFilePic;
    private ImageView mImgPraise;
    private ImageView mImgScreen;
    private ImageView mImgShare;
    private CustomEmptyView mLoadingView;
    private LinearLayout mLyBottom;
    private LinearLayout mLyCollection;
    private RelativeLayout mLyComment;
    private LinearLayout mLyCommentMsg;
    private View mLyDetailsView;
    private LinearLayout mLyLoadingView;
    private LinearLayout mLyPraise;
    private LinearLayout mLyPreview;
    private View mNavBarView;
    private TextView mTvCommentCount;
    private TextView mTvFileAuther;
    private TextView mTvFileDesc;
    private TextView mTvFileName;
    private TextView mTvTitle;
    private String mUrl;
    private WebView mWebView;
    private long startTime;
    private String title;
    private final int REQUEST_CODE_PERMISSION_READ_OR_WRITE = 1;
    private boolean isCollection = false;
    private boolean isLandSpance = false;
    private boolean isFromJangYI = false;

    /* renamed from: com.psychiatrygarden.activity.material.InformationPreviewAct$1, reason: invalid class name */
    public class AnonymousClass1 extends WebViewClient {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPageFinished$1(WebView webView) {
            try {
                InformationPreviewAct.this.injectJavaScriptAsync(webView);
                InformationPreviewAct.this.updateUIAfterPageLoad();
                InformationPreviewAct.this.sendHandoutEventIfNeeded();
            } catch (Exception e2) {
                Log.e(InformationPreviewAct.TAG, "页面加载完成处理异常", e2);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPageStarted$0() {
            InformationPreviewAct.this.mLyLoadingView.setVisibility(0);
            InformationPreviewAct.this.mWebView.setVisibility(8);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceivedError$2() {
            InformationPreviewAct.this.mLyLoadingView.setVisibility(8);
            ToastUtil.shortToast(InformationPreviewAct.this, "页面加载失败，请检查网络");
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(final WebView view, String url) {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.material.q0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f12738c.lambda$onPageFinished$1(view);
                }
            }, 100L);
            super.onPageFinished(view, url);
        }

        @Override // android.webkit.WebViewClient
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            InformationPreviewAct.this.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.material.r0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f12741c.lambda$onPageStarted$0();
                }
            });
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            InformationPreviewAct.this.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.material.s0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f12743c.lambda$onReceivedError$2();
                }
            });
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    /* renamed from: com.psychiatrygarden.activity.material.InformationPreviewAct$9, reason: invalid class name */
    public class AnonymousClass9 implements Callback<ResponseBody> {
        final /* synthetic */ String val$url;

        public AnonymousClass9(final String val$url) {
            this.val$url = val$url;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onResponse$0(String str) {
            InformationPreviewAct.this.showDialog(str);
        }

        @Override // retrofit2.Callback
        public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t2) {
            InformationPreviewAct.this.hideProgressDialog();
        }

        /* JADX WARN: Removed duplicated region for block: B:45:0x00cc A[Catch: IOException -> 0x00c8, TRY_LEAVE, TryCatch #3 {IOException -> 0x00c8, blocks: (B:41:0x00c4, B:45:0x00cc), top: B:51:0x00c4 }] */
        /* JADX WARN: Removed duplicated region for block: B:51:0x00c4 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        @Override // retrofit2.Callback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onResponse(@androidx.annotation.NonNull retrofit2.Call<okhttp3.ResponseBody> r11, @androidx.annotation.NonNull retrofit2.Response<okhttp3.ResponseBody> r12) throws java.lang.Throwable {
            /*
                Method dump skipped, instructions count: 212
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.material.InformationPreviewAct.AnonymousClass9.onResponse(retrofit2.Call, retrofit2.Response):void");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void collectionMsgUI(boolean isCollection) {
        if (isCollection) {
            this.mImgCollection.setImageResource(SkinManager.getCurrentSkinType(this) == 1 ? R.drawable.icon_collect_yes_night : R.drawable.icon_collect_yes);
            this.mImgDetailColl.setImageResource(SkinManager.getCurrentSkinType(this) == 1 ? R.mipmap.ic_info_desc_had_collection_night : R.mipmap.ic_info_desc_had_collection);
        } else {
            this.mImgCollection.setImageResource(SkinManager.getCurrentSkinType(this) == 1 ? R.drawable.icon_collect_no_night : R.drawable.icon_collect_no);
            this.mImgDetailColl.setImageResource(SkinManager.getCurrentSkinType(this) == 1 ? R.mipmap.ic_info_desc_no_collection_night : R.mipmap.ic_info_desc_no_collection);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void commentMsgUI(boolean isComment) {
        if (isComment) {
            this.mImgComment.setImageResource(SkinManager.getCurrentSkinType(this) == 1 ? R.drawable.youpinglun_night : R.drawable.youpinglun);
        } else {
            this.mImgComment.setImageResource(SkinManager.getCurrentSkinType(this) == 1 ? R.drawable.question_msg_night : R.drawable.question_msg);
        }
    }

    private void configureWebSettings(WebSettings webSettings) {
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setCacheMode(2);
        this.mWebView.setLayerType(2, null);
        webSettings.setPluginState(WebSettings.PluginState.OFF);
        webSettings.setGeolocationEnabled(false);
        webSettings.setNeedInitialFocus(false);
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setDisplayZoomControls(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        webSettings.setMixedContentMode(0);
        webSettings.setAllowFileAccess(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        this.mWebView.removeJavascriptInterface("searchBoxJavaBredge_");
        if (SkinManager.getCurrentSkinType(this) == 1) {
            this.mWebView.setBackgroundColor(Color.parseColor("#121622"));
        } else {
            this.mWebView.setBackgroundColor(-1);
        }
    }

    private WebViewClient createOptimizedWebViewClient() {
        return new AnonymousClass1();
    }

    private View.OnTouchListener createTouchListener() {
        return new View.OnTouchListener() { // from class: com.psychiatrygarden.activity.material.x
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return this.f12753c.lambda$createTouchListener$11(view, motionEvent);
            }
        };
    }

    private void downLoadFromNet(final String url) {
        final Intent intent = new Intent(this, (Class<?>) ResourceDownloadService.class);
        String str = getDownloadPath() + getFileName(url);
        List<DownloadEntity> taskList = Aria.download(this).getTaskList();
        if (taskList != null && !taskList.isEmpty()) {
            for (DownloadEntity downloadEntity : taskList) {
                if (TextUtils.equals(downloadEntity.getFilePath(), str)) {
                    DownloadTipPop downloadTipPop = this.mDownloadTipPop;
                    if (downloadTipPop != null && downloadTipPop.isShow()) {
                        this.mDownloadTipPop.dismiss();
                    }
                    if (downloadEntity.getState() == 4) {
                        this.mDownloadTipPop = (DownloadTipPop) new XPopup.Builder(this).hasShadowBg(Boolean.FALSE).popupAnimation(PopupAnimation.NoAnimation).asCustom(new DownloadTipPop(this, "已在下载列表，请耐心等待", false)).show();
                        return;
                    } else if (downloadEntity.getState() == 1) {
                        this.mDownloadTipPop = (DownloadTipPop) new XPopup.Builder(this).hasShadowBg(Boolean.FALSE).popupAnimation(PopupAnimation.NoAnimation).asCustom(new DownloadTipPop(this, "已下载过此资料  |")).show();
                        return;
                    }
                }
            }
        }
        bindService(intent, new ServiceConnection() { // from class: com.psychiatrygarden.activity.material.InformationPreviewAct.6
            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName name, IBinder service) {
                InformationPreviewAct.this.mDownloadBinder = (ResourceDownloadService.DownloadBinder) service;
                InformationPreviewAct.this.mDownloadBinder.getService().setCallBack(InformationPreviewAct.this);
                ResourceBean resourceBean = new ResourceBean(InformationPreviewAct.this.bookAuthor, InformationPreviewAct.this.downloadCount == null ? 0 : Integer.parseInt(InformationPreviewAct.this.downloadCount), url, InformationPreviewAct.this.fileId, InformationPreviewAct.this.getFileName(url), InformationPreviewAct.this.fileSize);
                resourceBean.setShowFileName(InformationPreviewAct.this.fileName + StrPool.DOT + InformationPreviewAct.this.getFileType(url));
                InformationPreviewAct.this.startService(intent.putExtra("data", resourceBean));
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName name) {
            }
        }, 1);
    }

    private void downloadCount() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(FontsContractCompat.Columns.FILE_ID, this.fileId);
        YJYHttpUtils.post(this.mContext.getApplicationContext(), NetworkRequestsURL.downloadFileCount, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.material.InformationPreviewAct.5
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass5) s2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getBottomInfo() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(FontsContractCompat.Columns.FILE_ID, this.fileId);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.soutceStatusInfo, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.material.InformationPreviewAct.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) throws NumberFormatException {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    if (!new JSONObject(s2).optString("code").equals("200")) {
                        ToastUtil.shortToast(InformationPreviewAct.this, new JSONObject(s2).optString("message"));
                        InformationPreviewAct.this.finish();
                        return;
                    }
                    MaterialListBean.MaterialListData materialListData = (MaterialListBean.MaterialListData) new Gson().fromJson(new JSONObject(s2).optString("data"), MaterialListBean.MaterialListData.class);
                    String is_collect = materialListData.getIs_collect();
                    String is_support = materialListData.getIs_support();
                    String is_review = materialListData.getIs_review();
                    String review_count = materialListData.getReview_count();
                    String is_rights = materialListData.getIs_rights();
                    String desc = materialListData.getDesc();
                    String img_url = materialListData.getImg_url();
                    InformationPreviewAct.this.fileName = materialListData.getTitle();
                    InformationPreviewAct.this.fileSize = materialListData.getSize();
                    InformationPreviewAct.this.bookAuthor = materialListData.getNickname();
                    InformationPreviewAct.this.downloadCount = materialListData.getDownload_count();
                    InformationPreviewAct.this.mTvTitle.setText(InformationPreviewAct.this.fileName);
                    InformationPreviewAct.this.isCollection = (TextUtils.isEmpty(is_collect) || is_collect.equals("0")) ? false : true;
                    boolean z2 = (TextUtils.isEmpty(is_support) || is_support.equals("0")) ? false : true;
                    boolean z3 = (TextUtils.isEmpty(is_review) || is_review.equals("0")) ? false : true;
                    InformationPreviewAct.this.mLoadingView.stopAnim();
                    InformationPreviewAct.this.mLoadingView.setVisibility(8);
                    if (is_rights.equals("0")) {
                        InformationPreviewAct.this.mImgShare.setVisibility(8);
                        InformationPreviewAct.this.mImgScreen.setVisibility(8);
                        InformationPreviewAct.this.mLyDetailsView.setVisibility(0);
                        InformationPreviewAct.this.mLyPreview.setVisibility(8);
                        InformationPreviewAct.this.mTvFileAuther.setText(InformationPreviewAct.this.bookAuthor);
                        InformationPreviewAct.this.mTvFileName.setText(InformationPreviewAct.this.fileName + materialListData.getType_name());
                        InformationPreviewAct.this.mImgDownload.setVisibility(8);
                        GlideApp.with((FragmentActivity) InformationPreviewAct.this).load((Object) GlideUtils.generateUrl(img_url)).placeholder(R.mipmap.ic_order_default).into(InformationPreviewAct.this.mImgFilePic);
                        if (TextUtils.isEmpty(desc)) {
                            InformationPreviewAct.this.mTvFileDesc.setVisibility(8);
                            InformationPreviewAct.this.mDesLine.setVisibility(8);
                            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) InformationPreviewAct.this.mImgFilePic.getLayoutParams();
                            layoutParams.topMargin = ScreenUtil.getPxByDp((Context) InformationPreviewAct.this, 116);
                            InformationPreviewAct.this.mImgFilePic.setLayoutParams(layoutParams);
                        } else {
                            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) InformationPreviewAct.this.mImgFilePic.getLayoutParams();
                            layoutParams2.topMargin = ScreenUtil.getPxByDp((Context) InformationPreviewAct.this, 8);
                            InformationPreviewAct.this.mImgFilePic.setLayoutParams(layoutParams2);
                            InformationPreviewAct.this.mTvFileDesc.setVisibility(0);
                            InformationPreviewAct.this.mDesLine.setVisibility(0);
                            InformationPreviewAct.this.mTvFileDesc.setText(desc);
                        }
                        PopupShowManager popupShowManager = PopupShowManager.getInstance(InformationPreviewAct.this.mContext);
                        InformationPreviewAct informationPreviewAct = InformationPreviewAct.this;
                        popupShowManager.checkShowCoupon(informationPreviewAct.mContext, "ENTER_INFO_NO_PERMISSION_VIEW_DETAIL", "1", "5", informationPreviewAct.fileId);
                    } else {
                        InformationPreviewAct.this.mUrl = materialListData.getUrl();
                        if (InformationPreviewAct.this.mUrl.endsWith(".pdf")) {
                            InformationPreviewAct.this.mWebView.loadUrl("file:///android_asset/pdfjs/web/viewer.html?file=" + Uri.encode(InformationPreviewAct.this.mUrl));
                        } else {
                            InformationPreviewAct.this.mWebView.loadUrl(NetworkRequestsURL.infoUrl + "?url=" + InformationPreviewAct.this.mUrl);
                        }
                        String is_prohibit = materialListData.getIs_prohibit();
                        if (TextUtils.isEmpty(is_prohibit) || is_prohibit.equals("1")) {
                            InformationPreviewAct.this.mImgDownload.setVisibility(8);
                            InformationPreviewAct.this.mImgShare.setVisibility(8);
                        } else {
                            InformationPreviewAct.this.mImgDownload.setVisibility(0);
                            InformationPreviewAct.this.mImgShare.setVisibility(0);
                        }
                        InformationPreviewAct.this.mLyDetailsView.setVisibility(8);
                        InformationPreviewAct.this.mLyPreview.setVisibility(0);
                    }
                    InformationPreviewAct.this.praiseMsgUI(z2);
                    InformationPreviewAct informationPreviewAct2 = InformationPreviewAct.this;
                    informationPreviewAct2.collectionMsgUI(informationPreviewAct2.isCollection);
                    InformationPreviewAct.this.commentMsgUI(z3);
                    if (TextUtils.isEmpty(review_count)) {
                        InformationPreviewAct.this.mTvCommentCount.setText("0");
                        return;
                    }
                    int i2 = Integer.parseInt(review_count);
                    if (i2 > 10000) {
                        InformationPreviewAct.this.mTvCommentCount.setText(String.format(Locale.CHINA, "%d.%d万", Integer.valueOf(i2 / 10000), Integer.valueOf((i2 % 10000) / 1000)));
                    } else {
                        InformationPreviewAct.this.mTvCommentCount.setText(String.valueOf(i2));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private File getCacheFile(String url) {
        if (Build.VERSION.SDK_INT >= 29) {
            return new File(ProjectApp.instance().getExternalFilesDir(null).getAbsolutePath() + "/TbsReaderTemp/" + getFileName(url));
        }
        return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/TbsReaderTemp/" + getFileName(url));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getDownloadPath() {
        StringBuilder sb = new StringBuilder();
        String str = File.separator;
        sb.append(str);
        sb.append(SdkConstant.UMENG_ALIS);
        sb.append(str);
        sb.append("ResourceDownload/");
        String string = sb.toString();
        if (Build.VERSION.SDK_INT >= 29) {
            return ProjectApp.instance().getExternalFilesDir(null).getAbsolutePath() + string;
        }
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + string;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public File getFileCacheDirs() {
        StringBuilder sb = new StringBuilder();
        String str = File.separator;
        sb.append(str);
        sb.append(SdkConstant.UMENG_ALIS);
        sb.append(str);
        sb.append("ResourceDownload/");
        String string = sb.toString();
        if (Build.VERSION.SDK_INT >= 29) {
            return new File(ProjectApp.instance().getExternalFilesDir(null).getAbsolutePath() + string);
        }
        return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + string);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getFileName(String url) {
        if (this.fileName.contains(getFileType(url))) {
            return this.fileName;
        }
        return this.fileName + StrPool.DOT + getFileType(url);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getFileType(String paramString) {
        int iLastIndexOf;
        return (TextUtils.isEmpty(paramString) || (iLastIndexOf = paramString.lastIndexOf(46)) == -1) ? "" : paramString.substring(iLastIndexOf + 1);
    }

    private void getMessageNotice() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(FontsContractCompat.Columns.FILE_ID, this.fileId);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.infomationShareCount, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.material.InformationPreviewAct.8
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
            }
        });
    }

    private void goToCommentList(String type) {
        if (type.equals("0")) {
            Intent intent = new Intent(this.mContext, (Class<?>) InformationCommentListAct.class);
            intent.putExtra("question_id", Long.parseLong(this.fileId));
            intent.putExtra(com.alipay.sdk.authjs.a.f3174g, SocialConstants.PARAM_SOURCE);
            intent.putExtra("search_type", type);
            startActivity(intent);
            return;
        }
        Intent intent2 = new Intent(this.mContext, (Class<?>) CommentNewActivity.class);
        intent2.putExtra("question_id", Long.parseLong(this.fileId));
        intent2.putExtra(com.alipay.sdk.authjs.a.f3174g, SocialConstants.PARAM_SOURCE);
        intent2.putExtra("search_type", type);
        startActivity(intent2);
    }

    private void infoCollectionAction() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(FontsContractCompat.Columns.FILE_ID, this.fileId);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.infoCollection, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.material.InformationPreviewAct.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                try {
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        EventBus.getDefault().post("onRefreshInfo");
                        InformationPreviewAct informationPreviewAct = InformationPreviewAct.this;
                        informationPreviewAct.isCollection = !informationPreviewAct.isCollection;
                        InformationPreviewAct informationPreviewAct2 = InformationPreviewAct.this;
                        informationPreviewAct2.collectionMsgUI(informationPreviewAct2.isCollection);
                    }
                    InformationPreviewAct.this.AlertToast(new JSONObject(s2).optString("message"));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: initWebView, reason: merged with bridge method [inline-methods] */
    public void lambda$init$0() {
        try {
            configureWebSettings(this.mWebView.getSettings());
            this.mWebView.setWebViewClient(createOptimizedWebViewClient());
            this.mWebView.setOnTouchListener(createTouchListener());
            if (!TextUtils.isEmpty(this.mUrl)) {
                if (this.mUrl.endsWith(".pdf")) {
                    this.mWebView.loadUrl("file:///android_asset/pdfjs/web/viewer.html?file=" + Uri.encode(this.mUrl));
                } else {
                    this.mWebView.loadUrl(NetworkRequestsURL.infoUrl + "?url=" + this.mUrl);
                }
            }
        } catch (Exception e2) {
            Log.e(TAG, "WebView初始化异常", e2);
            runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.material.c0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f12700c.lambda$initWebView$2();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void injectJavaScriptAsync(final WebView view) {
        new Thread(new Runnable() { // from class: com.psychiatrygarden.activity.material.h0
            @Override // java.lang.Runnable
            public final void run() throws InterruptedException {
                this.f12715c.lambda$injectJavaScriptAsync$7(view);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createTouchListener$10() {
        if (this.mLyBottom.getVisibility() == 8) {
            this.mLyBottom.setVisibility(0);
            this.mNavBarView.setVisibility(0);
        } else {
            this.mLyBottom.setVisibility(8);
            this.mNavBarView.setVisibility(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$createTouchListener$11(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            this.downX = (int) motionEvent.getX();
            this.downY = (int) motionEvent.getY();
            this.startTime = System.currentTimeMillis();
        } else if (action == 1) {
            float x2 = motionEvent.getX();
            float y2 = motionEvent.getY();
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (this.isFromJangYI) {
                return true;
            }
            if (Math.abs(x2 - this.downX) >= 10.0f || Math.abs(y2 - this.downY) >= 10.0f || jCurrentTimeMillis - this.startTime >= 200) {
                return false;
            }
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.psychiatrygarden.activity.material.f0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f12709c.lambda$createTouchListener$10();
                }
            });
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1() {
        post(new Runnable() { // from class: com.psychiatrygarden.activity.material.b0
            @Override // java.lang.Runnable
            public final void run() {
                this.f12697c.lambda$init$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initWebView$2() {
        ToastUtil.shortToast(this, "页面加载异常，请重试");
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$injectJavaScriptAsync$3(WebView webView, String str) {
        webView.loadUrl(str);
        webView.loadUrl("javascript:setBackgroundColor();");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$injectJavaScriptAsync$7(final WebView webView) throws InterruptedException {
        try {
            if (SkinManager.getCurrentSkinType(this) == 1) {
                final String str = "javascript:function setBackgroundColor() {document.querySelector('body').style.backgroundColor='#121622';document.querySelector('body').style.color='#7380A9';}";
                post(new Runnable() { // from class: com.psychiatrygarden.activity.material.y
                    @Override // java.lang.Runnable
                    public final void run() {
                        InformationPreviewAct.lambda$injectJavaScriptAsync$3(webView, str);
                    }
                });
            }
            post(new Runnable() { // from class: com.psychiatrygarden.activity.material.i0
                @Override // java.lang.Runnable
                public final void run() {
                    webView.loadUrl("javascript:(function() {document.body.addEventListener('click', function(event) {event.preventDefault(); event.stopPropagation();return false;}, true);})()");
                }
            });
            Thread.sleep(50L);
            post(new Runnable() { // from class: com.psychiatrygarden.activity.material.j0
                @Override // java.lang.Runnable
                public final void run() {
                    webView.loadUrl("javascript:(function() {var inputs = document.getElementsByTagName('input');for (var i = 0; i < inputs.length; i++) {inputs[i].setAttribute('readonly', ''); }var textareas = document.getElementsByTagName('textarea');for (var i = 0; i < textareas.length; i++) {textareas[i].setAttribute('readonly', ''); }})()");
                }
            });
            Thread.sleep(50L);
            post(new Runnable() { // from class: com.psychiatrygarden.activity.material.k0
                @Override // java.lang.Runnable
                public final void run() {
                    webView.loadUrl("javascript:(function() {document.body.addEventListener('contextmenu', function(event) {event.preventDefault(); return false;}, true);})()");
                }
            });
        } catch (Exception e2) {
            Log.e(TAG, "JavaScript注入异常", e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendHandoutEventIfNeeded$9() {
        try {
            HandoutEvent handoutEvent = new HandoutEvent();
            handoutEvent.setHandoutId(this.fileId);
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent().putExtra("data", new Gson().toJson(handoutEvent)).setAction("HANDOUT_VIEW"));
            this.hasSend = true;
        } catch (Exception e2) {
            Log.e(TAG, "发送埋点事件异常", e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$12(View view) {
        if (this.isLandSpance) {
            setRequestedOrientation(1);
            this.isLandSpance = false;
            return;
        }
        if (this.fromLive || this.isFromJangYI) {
            HandoutEvent handoutEvent = new HandoutEvent();
            handoutEvent.setHandoutId(this.fileId);
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent().putExtra("data", new Gson().toJson(handoutEvent)).setAction("HANDOUT_QUIT"));
        }
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$13() {
        ActivityCompat.requestPermissions(this, new String[]{Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE}, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$14(View view) {
        if (!CommonUtil.hasRequiredPermissionsWriteStorage(this)) {
            new XPopup.Builder(this).asCustom(new RequestMediaPermissionPop(this, new OnConfirmListener() { // from class: com.psychiatrygarden.activity.material.g0
                @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                public final void onConfirm() {
                    this.f12712a.lambda$setListenerForWidget$13();
                }
            })).show();
        } else if (this.isLocal) {
            showDialog(this.mUrl);
        } else {
            getInfoByShare(this.mUrl);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$15() {
        ActivityCompat.requestPermissions(this, new String[]{Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE}, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$16(View view) {
        if (!CommonUtil.hasRequiredPermissionsWriteStorage(this)) {
            new XPopup.Builder(this).asCustom(new RequestMediaPermissionPop(this, new OnConfirmListener() { // from class: com.psychiatrygarden.activity.material.d0
                @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                public final void onConfirm() {
                    this.f12703a.lambda$setListenerForWidget$15();
                }
            })).show();
            return;
        }
        if (!CommonUtil.isNetworkConnected(this.mContext)) {
            NewToast.showShort(this.mContext, "当前无网络连接", 0).show();
        } else if (CommonUtil.isWifi(this.mContext) || UserConfig.isCanDownloadBy4g(this.mContext)) {
            downLoadFromNet(this.mUrl);
        } else {
            showOpenDownloadDialog();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$17(String str, String str2, String str3) {
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
    public /* synthetic */ void lambda$setListenerForWidget$18(View view) {
        if (isLogin()) {
            new DialogInput(this.mContext, new onDialogClickListener() { // from class: com.psychiatrygarden.activity.material.z
                @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                public final void onclickStringBack(String str, String str2, String str3) {
                    this.f12758a.lambda$setListenerForWidget$17(str, str2, str3);
                }
            }, true).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$19(View view) {
        goToCommentList("1");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$20(View view) {
        goToCommentList("2");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$21(View view) {
        infoCollectionAction();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$22(View view) {
        goToCommentList("0");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$23(View view) {
        boolean z2 = !this.isLandSpance;
        this.isLandSpance = z2;
        if (z2) {
            setRequestedOrientation(0);
        } else {
            setRequestedOrientation(1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$24(View view) {
        infoCollectionAction();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$25() {
        this.mLyPreview.setVisibility(0);
        this.mLyDetailsView.setVisibility(8);
        this.mImgShare.setVisibility(0);
        this.mImgScreen.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$26(View view) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("enclosure_id", this.fileId);
        MemInterface.getInstance().getFilePermission(this, ajaxParams);
        MemInterface.getInstance().setmUShareListener(new MemInterface.UShareListener() { // from class: com.psychiatrygarden.activity.material.v
            @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.UShareListener
            public final void mUShareListener() {
                this.f12749a.lambda$setListenerForWidget$25();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$shareSizeLimit$28() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showOpenDownloadDialog$27() {
        if (isLogin()) {
            goActivity(DownloadSetting.class);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateUIAfterPageLoad$8() {
        this.mLyLoadingView.setVisibility(8);
        this.mWebView.setVisibility(0);
    }

    public static void newIntent(Context context, String fileId, String fileUrl, boolean isLocal) {
        Intent intent = new Intent(context, (Class<?>) InformationPreviewAct.class);
        intent.putExtra("fileId", fileId);
        intent.putExtra("fileUrl", fileUrl);
        intent.putExtra("isLocal", isLocal);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void praiseMsgUI(boolean isPraise) {
        if (isPraise) {
            this.mImgPraise.setImageResource(SkinManager.getCurrentSkinType(this) == 1 ? R.drawable.youdianzan_night : R.drawable.youdianzan);
        } else {
            this.mImgPraise.setImageResource(SkinManager.getCurrentSkinType(this) == 1 ? R.drawable.dianzancourse_night : R.drawable.dianzancourse);
        }
    }

    private void pushComment(Bundle b3) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(FontsContractCompat.Columns.FILE_ID, this.fileId);
        ajaxParams.put("content", b3.getString("content"));
        String string = b3.getString("parent_id");
        if (!TextUtils.isEmpty(string) && !"0".equals(string)) {
            ajaxParams.put("parent_id", string);
        }
        String string2 = b3.getString("reply_primary_id");
        if (!TextUtils.isEmpty(string2) && !"0".equals(string2)) {
            ajaxParams.put("reply_primary_id", string2);
        }
        String string3 = b3.getString("b_img");
        String string4 = b3.getString("s_img");
        if (!TextUtils.isEmpty(string3)) {
            if (string3.contains("http")) {
                ajaxParams.put("b_img", string3);
                ajaxParams.put("s_img", string4);
            } else {
                ajaxParams.put("video_id", b3.getString("b_img"));
            }
        }
        if (UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            ajaxParams.put("virtual_user_id", b3.getString("virtual_user_id"));
        }
        showProgressDialog("发布中");
        YJYHttpUtils.post(this.mContext.getApplicationContext(), NetworkRequestsURL.submitComment, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.material.InformationPreviewAct.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                InformationPreviewAct.this.hideProgressDialog();
                InformationPreviewAct.this.AlertToast("请求服务器失败！");
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) throws JSONException {
                super.onSuccess((AnonymousClass4) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        ToastUtil.shortToast(InformationPreviewAct.this, jSONObject.optString("message"));
                        InformationPreviewAct.this.getBottomInfo();
                        boolean z2 = true;
                        InformationPreviewAct.this.commentMsgUI(true);
                        ProjectApp.comment_content = "";
                        ProjectApp.comment_b_img = "";
                        ProjectApp.comment_s_img = "";
                        ProjectApp.commentvideoPath = "";
                        ProjectApp.commentvideoImage = "";
                        ProjectApp.commentvideoId = "";
                        ProjectApp.commentvideoImagePath = "";
                        if (jSONObject.has("is_collection")) {
                            String string5 = jSONObject.getString("is_collection");
                            InformationPreviewAct.this.isCollection = (TextUtils.isEmpty(string5) || string5.equals("0")) ? false : true;
                            InformationPreviewAct informationPreviewAct = InformationPreviewAct.this;
                            informationPreviewAct.collectionMsgUI(informationPreviewAct.isCollection);
                        }
                        if (jSONObject.has("is_praise")) {
                            String string6 = jSONObject.getString("is_praise");
                            if (TextUtils.isEmpty(string6) || string6.equals("0")) {
                                z2 = false;
                            }
                            InformationPreviewAct.this.praiseMsgUI(z2);
                        }
                        EventBus.getDefault().post(EventBusConstant.EVENT_DIALOG_INPUT_DISMISS);
                        CommonUtil.showFristDialog(jSONObject);
                    } else if (jSONObject.optString("code").equals("401")) {
                        new CusomNewDialog(InformationPreviewAct.this.mContext).setMessage(jSONObject.optString("message")).show();
                    } else {
                        InformationPreviewAct.this.AlertToast(jSONObject.optString("message"));
                    }
                } catch (Exception e2) {
                    InformationPreviewAct.this.AlertToast("数据异常！");
                    e2.printStackTrace();
                }
                InformationPreviewAct.this.hideProgressDialog();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendHandoutEventIfNeeded() {
        if ((this.fromLive || this.isFromJangYI) && !this.hasSend) {
            new Thread(new Runnable() { // from class: com.psychiatrygarden.activity.material.e0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f12706c.lambda$sendHandoutEventIfNeeded$9();
                }
            }).start();
        }
    }

    private void shareHandout() {
        String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.LIVE_COURSE_ID, this);
        if (TextUtils.isEmpty(strConfig) || TextUtils.isEmpty(this.fileId)) {
            return;
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("course_id", strConfig);
        ajaxParams.put("handout_id", this.fileId);
        YJYHttpUtils.post(this.mContext.getApplicationContext(), NetworkRequestsURL.shareHandout, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.material.InformationPreviewAct.7
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass7) s2);
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0053  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean shareSizeLimit(double r10) {
        /*
            r9 = this;
            java.lang.String r0 = "\\d+(\\.\\d+)?"
            java.util.regex.Pattern r0 = java.util.regex.Pattern.compile(r0)
            java.lang.String r1 = r9.fileSize
            java.util.regex.Matcher r0 = r0.matcher(r1)
            boolean r1 = r0.find()
            if (r1 == 0) goto L53
            java.lang.String r0 = r0.group()
            java.lang.String r1 = r9.fileSize
            java.lang.String r1 = r1.toLowerCase()
            java.lang.String r2 = "kb"
            boolean r1 = r1.contains(r2)
            if (r1 == 0) goto L2c
            double r0 = java.lang.Double.parseDouble(r0)
            r2 = 4652218415073722368(0x4090000000000000, double:1024.0)
            double r0 = r0 / r2
            goto L55
        L2c:
            java.lang.String r1 = r9.fileSize
            java.lang.String r1 = r1.toLowerCase()
            java.lang.String r2 = "mb"
            boolean r1 = r1.contains(r2)
            if (r1 == 0) goto L3f
            double r0 = java.lang.Double.parseDouble(r0)
            goto L55
        L3f:
            java.lang.String r0 = r9.fileSize
            java.lang.String r0 = r0.toLowerCase()
            java.lang.String r1 = "gb"
            boolean r0 = r0.contains(r1)
            if (r0 == 0) goto L53
            r0 = 4652007308841189376(0x408f400000000000, double:1000.0)
            goto L55
        L53:
            r0 = 0
        L55:
            int r0 = (r0 > r10 ? 1 : (r0 == r10 ? 0 : -1))
            if (r0 <= 0) goto L94
            com.lxj.xpopup.XPopup$Builder r0 = new com.lxj.xpopup.XPopup$Builder
            r0.<init>(r9)
            com.psychiatrygarden.widget.DeleteDownloadDialog r8 = new com.psychiatrygarden.widget.DeleteDownloadDialog
            com.psychiatrygarden.activity.material.n r3 = new com.psychiatrygarden.activity.material.n
            r3.<init>()
            android.text.SpannableStringBuilder r4 = new android.text.SpannableStringBuilder
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "分享文件大小不得超过"
            r1.append(r2)
            r1.append(r10)
            java.lang.String r10 = "M请重新选择"
            r1.append(r10)
            java.lang.String r10 = r1.toString()
            r4.<init>(r10)
            java.lang.String r5 = "温馨提示"
            java.lang.String r6 = "取消"
            java.lang.String r7 = "确定"
            r1 = r8
            r2 = r9
            r1.<init>(r2, r3, r4, r5, r6, r7)
            com.lxj.xpopup.core.BasePopupView r10 = r0.asCustom(r8)
            r10.show()
            r10 = 1
            return r10
        L94:
            r10 = 0
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.material.InformationPreviewAct.shareSizeLimit(double):boolean");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showDialog(String filepath) {
        hideProgressDialog();
        Log.d(TAG, "showDialog: " + filepath);
        if (Build.VERSION.SDK_INT > 23) {
            Uri uriForFile = FileProvider.getUriForFile(this, getPackageName() + ".fileProvider", new File(getDownloadPath() + getFileName(filepath)));
            Intent intent = new Intent("android.intent.action.SEND");
            intent.putExtra("android.intent.extra.STREAM", uriForFile);
            intent.setType(MimeTypes.ANY_TYPE);
            startActivity(Intent.createChooser(intent, "分享到："));
        } else {
            Intent intent2 = new Intent("android.intent.action.SEND");
            intent2.putExtra("android.intent.extra.STREAM", Uri.fromFile(getCacheFile(filepath)));
            intent2.setType(MimeTypes.ANY_TYPE);
            startActivity(Intent.createChooser(intent2, "分享到："));
        }
        getMessageNotice();
        if (this.isFromJangYI) {
            shareHandout();
        }
    }

    private void showOpenDownloadDialog() {
        new XPopup.Builder(this.mContext).asCustom(new CancelConfrimPop(this.mContext, new CancelConfrimPop.ClickIml() { // from class: com.psychiatrygarden.activity.material.a0
            @Override // com.psychiatrygarden.widget.CancelConfrimPop.ClickIml
            public final void mClickIml() {
                this.f12695a.lambda$showOpenDownloadDialog$27();
            }
        }, "当前设置不允许流量下载，如仍需下载可以到【设置-下载设置】里开启", "温馨提示", "暂不开启", "去开启")).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateUIAfterPageLoad() {
        runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.material.u
            @Override // java.lang.Runnable
            public final void run() {
                this.f12747c.lambda$updateUIAfterPageLoad$8();
            }
        });
    }

    public void getInfoByShare(String url) {
        showProgressDialog();
        try {
            LoadFileModel.getRetrofitManager().loadPdfFile(url, new AnonymousClass9(url));
        } catch (Exception e2) {
            e2.printStackTrace();
            hideProgressDialog();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.fileId = getIntent().getStringExtra("fileId");
        this.fromLive = getIntent().getBooleanExtra("fromLive", false);
        this.isFromJangYI = getIntent().getBooleanExtra("fromJiangYi", false);
        this.isLocal = getIntent().getBooleanExtra("isLocal", false);
        this.mTvTitle = (TextView) findViewById(R.id.txt_actionbar_title);
        this.mImgDownload = (ImageView) findViewById(R.id.iv_download);
        this.mWebView = (WebView) findViewById(R.id.webView);
        this.mBtnComment = (TextView) findViewById(R.id.btn_comment);
        this.mLyCommentMsg = (LinearLayout) findViewById(R.id.ly_comment_msg);
        this.mLyComment = (RelativeLayout) findViewById(R.id.ll_question_comment);
        this.mTvCommentCount = (TextView) findViewById(R.id.questiondetails_btn_commentNum);
        this.mLyCollection = (LinearLayout) findViewById(R.id.ly_collection);
        this.mLyPraise = (LinearLayout) findViewById(R.id.ly_praise);
        this.mImgScreen = (ImageView) findViewById(R.id.iv_search);
        this.mImgBack = (ImageView) findViewById(R.id.iv_actionbar_back);
        this.mLyBottom = (LinearLayout) findViewById(R.id.ly_bottom);
        this.mNavBarView = findViewById(R.id.ly_nav);
        this.mImgCollection = (ImageView) findViewById(R.id.img_collection);
        this.mImgPraise = (ImageView) findViewById(R.id.img_praise);
        this.mImgComment = (ImageView) findViewById(R.id.img_comment);
        this.mLyLoadingView = (LinearLayout) findViewById(R.id.ly_progress);
        this.mImgShare = (ImageView) findViewById(R.id.iv_share);
        this.mLyPreview = (LinearLayout) findViewById(R.id.ly_preview);
        this.mLyDetailsView = findViewById(R.id.ly_details);
        this.mDesLine = findViewById(R.id.desc_line);
        this.mTvFileName = (TextView) findViewById(R.id.tv_title);
        this.mImgFilePic = (RoundedImageView) findViewById(R.id.img_pic);
        this.mTvFileAuther = (TextView) findViewById(R.id.tv_author);
        this.mTvFileDesc = (TextView) findViewById(R.id.tv_desc);
        this.mBtnShow = (LinearLayout) findViewById(R.id.btn_show);
        this.mBtnCollection = (LinearLayout) findViewById(R.id.btn_collection);
        this.mImgDetailColl = (ImageView) findViewById(R.id.img_detail_collection);
        this.mLoadingView = (CustomEmptyView) findViewById(R.id.loading);
        this.mImgShare.setVisibility(0);
        if (SkinManager.getCurrentSkinType(this) == 1) {
            this.mImgScreen.setImageResource(R.mipmap.ic_screen_night);
        } else {
            this.mImgScreen.setImageResource(R.mipmap.ic_screen_day);
        }
        getWindow().setFlags(16777216, 16777216);
        new Thread(new Runnable() { // from class: com.psychiatrygarden.activity.material.w
            @Override // java.lang.Runnable
            public final void run() {
                this.f12751c.lambda$init$1();
            }
        }).start();
        if (!this.isFromJangYI) {
            getBottomInfo();
            return;
        }
        String str = this.title;
        this.fileName = str;
        this.mTvTitle.setText(str);
        this.mLoadingView.stopAnim();
        this.mLoadingView.setVisibility(8);
        this.mLyPreview.setVisibility(0);
        this.mLyDetailsView.setVisibility(8);
        this.mWebView.setVisibility(0);
        this.mLyBottom.setVisibility(8);
        this.mImgDownload.setVisibility(8);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == 1) {
            pushComment(data.getBundleExtra("bundleIntent"));
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        SkinManager.onActivityCreateSetSkin(this);
        this.mUrl = getIntent().getStringExtra("fileUrl");
        this.title = getIntent().getStringExtra("title");
        super.onCreate(savedInstanceState);
        this.mActionBar.hide();
        setNewStyleStatusBarColor();
        this.mContext = this;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        try {
            WebView webView = this.mWebView;
            if (webView != null) {
                webView.stopLoading();
                this.mWebView.setWebViewClient(null);
                this.mWebView.setWebChromeClient(null);
                this.mWebView.clearHistory();
                this.mWebView.clearCache(true);
                this.mWebView.loadUrl("about:blank");
                this.mWebView.destroy();
                this.mWebView = null;
            }
        } catch (Exception e2) {
            Log.e(TAG, "WebView销毁异常", e2);
        }
        super.onDestroy();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if ("BuySuccess".equals(str) && !this.isFromJangYI) {
            getBottomInfo();
        }
        if ("mCommentResult".equals(str) || "refresh_praise".equals(str) || TextUtils.equals(EventBusConstant.EVENT_REFRESH_COMMENT_NUM, str)) {
            getBottomInfo();
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4) {
            return false;
        }
        if (this.fromLive || this.isFromJangYI) {
            HandoutEvent handoutEvent = new HandoutEvent();
            handoutEvent.setHandoutId(this.fileId);
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent().putExtra("data", new Gson().toJson(handoutEvent)).setAction("HANDOUT_QUIT"));
        }
        finish();
        return true;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (1 == requestCode && grantResults[0] == -1 && !ActivityCompat.shouldShowRequestPermissionRationale(this, Permission.WRITE_EXTERNAL_STORAGE)) {
            ToastUtil.shortToast(this, "请检查app存储权限是否打开，否则会影响查看！");
        }
    }

    @Override // com.psychiatrygarden.service.ResourceDownloadService.DownloadCallBack
    public void onServiceStartSuccess() {
    }

    @Override // com.psychiatrygarden.service.ResourceDownloadService.DownloadCallBack
    public void onStatusChange(DownloadTask task) {
        int state = task.getState();
        if (state == 5) {
            this.mDownloadTipPop = (DownloadTipPop) new XPopup.Builder(this).hasShadowBg(Boolean.FALSE).popupAnimation(PopupAnimation.NoAnimation).asCustom(new DownloadTipPop(this, "正在下载  |")).show();
            return;
        }
        if (state != 1) {
            if (state == 0) {
                ToastUtil.shortToast(this, "下载失败");
                return;
            }
            return;
        }
        DownloadTipPop downloadTipPop = this.mDownloadTipPop;
        if (downloadTipPop != null && downloadTipPop.isShow()) {
            this.mDownloadTipPop.dismiss();
        }
        this.mDownloadTipPop = (DownloadTipPop) new XPopup.Builder(this).hasShadowBg(Boolean.FALSE).popupAnimation(PopupAnimation.NoAnimation).asCustom(new DownloadTipPop(this, "下载完成  |")).show();
        downloadCount();
        this.isLocal = true;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layout_information_preview);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mImgBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.material.l0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12728c.lambda$setListenerForWidget$12(view);
            }
        });
        this.mImgShare.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.material.n0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12732c.lambda$setListenerForWidget$14(view);
            }
        });
        this.mImgDownload.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.material.o0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12734c.lambda$setListenerForWidget$16(view);
            }
        });
        this.mBtnComment.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.material.p0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12736c.lambda$setListenerForWidget$18(view);
            }
        });
        this.mLyCommentMsg.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.material.o
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12733c.lambda$setListenerForWidget$19(view);
            }
        });
        this.mLyPraise.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.material.p
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12735c.lambda$setListenerForWidget$20(view);
            }
        });
        this.mLyCollection.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.material.q
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12737c.lambda$setListenerForWidget$21(view);
            }
        });
        this.mLyComment.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.material.r
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12740c.lambda$setListenerForWidget$22(view);
            }
        });
        this.mImgScreen.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.material.s
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12742c.lambda$setListenerForWidget$23(view);
            }
        });
        this.mBtnCollection.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.material.t
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12744c.lambda$setListenerForWidget$24(view);
            }
        });
        this.mBtnShow.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.material.m0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12731c.lambda$setListenerForWidget$26(view);
            }
        });
    }

    public static void newIntent(Context context, String fileId, String fileUrl, boolean isLocal, boolean fromJiangYi, String title) {
        Intent intent = new Intent(context, (Class<?>) InformationPreviewAct.class);
        intent.putExtra("fileId", fileId);
        intent.putExtra("fileUrl", fileUrl);
        intent.putExtra("isLocal", isLocal);
        intent.putExtra("fromJiangYi", fromJiangYi);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    @Subscribe
    public void onEventMainThread(ExitFromPipEvent event) {
        finish();
    }

    public static void newIntent(Context context, String fileId, String fileUrl, boolean isLocal, boolean fromJiangYi, String title, int intentFlag) {
        Intent intent = new Intent(context, (Class<?>) InformationPreviewAct.class);
        intent.putExtra("fileId", fileId);
        intent.putExtra("fromLive", true);
        intent.putExtra("fileUrl", fileUrl);
        intent.putExtra("isLocal", isLocal);
        intent.putExtra("fromJiangYi", fromJiangYi);
        intent.putExtra("title", title);
        intent.addFlags(intentFlag);
        context.startActivity(intent);
    }
}
