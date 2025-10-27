package com.psychiatrygarden.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.alibaba.sdk.android.vod.upload.VODSVideoUploadCallback;
import com.alibaba.sdk.android.vod.upload.VODSVideoUploadClient;
import com.alibaba.sdk.android.vod.upload.model.SvideoInfo;
import com.alibaba.sdk.android.vod.upload.session.VodHttpClientConfig;
import com.alibaba.sdk.android.vod.upload.session.VodSessionCreateInfo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.hjq.permissions.Permission;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.PopupInfo;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.bean.ImageItem;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.online.AnswerQuestionActivity;
import com.psychiatrygarden.activity.purchase.util.FileUtils;
import com.psychiatrygarden.bean.AliYunKeyInfoBean;
import com.psychiatrygarden.bean.EventBusMessage;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.onDialogClickListener;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.DesUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ImageFactory;
import com.psychiatrygarden.utils.ImageLoaderUtilsCustom;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.utils.weblong.SizeUtil;
import com.yikaobang.yixue.R;
import com.yikaobang.yixue.R2;
import de.greenrobot.event.EventBus;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class DialogInput extends AlertDialog implements View.OnClickListener {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public int FullSCREEN_Y_N;
    private String b_img;
    private onDialogClickListener clickListener;
    private String content;
    private Context context;
    private EditText et_content;
    private boolean fromVideo;
    private RoundedImageView imgshown;
    private boolean isErrorCorrection;
    private boolean isNewComzantong;
    private boolean landScape;
    private LinearLayout llTitle;
    public int postType;
    private TextView processTextView;
    private TextView pushBtm;
    private RelativeLayout relimg;
    private String s_img;
    private ImageView screenview;
    private String title;
    private TextView toTxt;
    private int typelet;
    private RelativeLayout uploadprogrss;
    private final List<String> urlList;
    private ImageView vbimg;
    private String videoId;
    public String videoImage;
    private String videoImagePath;
    public String videoPath;

    /* renamed from: com.psychiatrygarden.widget.DialogInput$7, reason: invalid class name */
    public class AnonymousClass7 implements VODSVideoUploadCallback {
        public AnonymousClass7() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUploadFailed$1() {
            DialogInput.this.relimg.setVisibility(8);
            ToastUtil.shortToast(DialogInput.this.context, "视频上传失败！");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUploadProgress$2(long j2) {
            DialogInput.this.processTextView.setText(String.valueOf(j2));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUploadSucceed$0() {
            DialogInput.this.uploadprogrss.setVisibility(8);
            DialogInput.this.relimg.setVisibility(0);
            DialogInput.this.vbimg.setVisibility(8);
            ((TextView) DialogInput.this.findViewById(R.id.dialog_input_btn_comment_ok)).setTextColor(ContextCompat.getColor(DialogInput.this.context, R.color.question_color));
            if (DialogInput.this.fromVideo) {
                DialogInput.this.pushBtm.setSelected(true);
            } else {
                DialogInput.this.pushBtm.setBackground(ContextCompat.getDrawable(DialogInput.this.context, R.drawable.shape_round_red));
            }
        }

        @Override // com.alibaba.sdk.android.vod.upload.VODSVideoUploadCallback
        public void onSTSTokenExpried() {
            DialogInput.this.getCourseAk(2);
        }

        @Override // com.alibaba.sdk.android.vod.upload.VODSVideoUploadCallback
        public void onUploadFailed(String code, String message) {
            DialogInput.this.relimg.post(new Runnable() { // from class: com.psychiatrygarden.widget.r7
                @Override // java.lang.Runnable
                public final void run() {
                    this.f16855c.lambda$onUploadFailed$1();
                }
            });
            if (DialogInput.this.context instanceof AnswerQuestionActivity) {
                ((AnswerQuestionActivity) DialogInput.this.context).setVideoId("");
            }
        }

        @Override // com.alibaba.sdk.android.vod.upload.VODSVideoUploadCallback
        public void onUploadProgress(long uploadedSize, long totalSize) {
            final long j2 = (uploadedSize * 100) / totalSize;
            DialogInput.this.processTextView.post(new Runnable() { // from class: com.psychiatrygarden.widget.q7
                @Override // java.lang.Runnable
                public final void run() {
                    this.f16817c.lambda$onUploadProgress$2(j2);
                }
            });
        }

        @Override // com.alibaba.sdk.android.vod.upload.VODSVideoUploadCallback
        public void onUploadRetry(String code, String message) {
        }

        @Override // com.alibaba.sdk.android.vod.upload.VODSVideoUploadCallback
        public void onUploadRetryResume() {
        }

        @Override // com.alibaba.sdk.android.vod.upload.VODSVideoUploadCallback
        public void onUploadSucceed(String vId, String imageUrl) {
            DialogInput.this.videoId = vId;
            DialogInput.this.videoImagePath = imageUrl;
            DialogInput dialogInput = DialogInput.this;
            dialogInput.postType = 2;
            dialogInput.relimg.post(new Runnable() { // from class: com.psychiatrygarden.widget.s7
                @Override // java.lang.Runnable
                public final void run() {
                    this.f16894c.lambda$onUploadSucceed$0();
                }
            });
            if (DialogInput.this.context instanceof AnswerQuestionActivity) {
                ((AnswerQuestionActivity) DialogInput.this.context).setVideoId(DialogInput.this.videoId);
            }
        }
    }

    public DialogInput(Context context) {
        super(context);
        this.content = "";
        this.title = "";
        this.b_img = "";
        this.s_img = "";
        this.typelet = 0;
        this.urlList = new ArrayList();
        this.FullSCREEN_Y_N = 0;
        this.videoImage = "";
        this.videoPath = "";
        this.postType = 0;
        this.isNewComzantong = false;
        this.isErrorCorrection = false;
        this.videoId = "";
        this.videoImagePath = "";
    }

    private boolean hasRequiredPermissions(Context context) {
        return Build.VERSION.SDK_INT >= 33 ? ContextCompat.checkSelfPermission(context, Permission.CAMERA) == 0 && ContextCompat.checkSelfPermission(context, Permission.READ_MEDIA_IMAGES) == 0 : ContextCompat.checkSelfPermission(context, Permission.CAMERA) == 0 && ContextCompat.checkSelfPermission(context, Permission.READ_EXTERNAL_STORAGE) == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$5() {
        ActivityCompat.requestPermissions((Activity) this.context, new String[]{Permission.CAMERA, Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE}, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$6() {
        ActivityCompat.requestPermissions((Activity) this.context, Build.VERSION.SDK_INT >= 33 ? new String[]{Permission.CAMERA, Permission.READ_MEDIA_IMAGES, Permission.READ_EXTERNAL_STORAGE} : new String[]{Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE}, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$7(List list) {
        if (list == null || list.size() <= 0) {
            this.relimg.setVisibility(8);
            this.uploadprogrss.setVisibility(8);
            return;
        }
        String str = ((ImageItem) list.get(0)).path;
        float imageSize = ImageFactory.getImageSize(str);
        LogUtils.d("imgSize", "" + imageSize);
        if (imageSize > 20.0f) {
            NewToast.showShort(this.context, "请选择小于10M的图片上传", 0).show();
            return;
        }
        this.postType = 1;
        getImageData(str);
        this.urlList.clear();
        this.urlList.add(((ImageItem) list.get(0)).path);
        this.uploadprogrss.setVisibility(0);
        this.relimg.setVisibility(0);
        Glide.with(this.context).load(((ImageItem) list.get(0)).path).apply((BaseRequestOptions<?>) new RequestOptions().override(Integer.MIN_VALUE)).placeholder(new ColorDrawable(ContextCompat.getColor(this.imgshown.getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).into(this.imgshown);
        this.toTxt.setVisibility(8);
        TextView textView = (TextView) findViewById(R.id.dialog_input_btn_comment_ok);
        Context context = this.context;
        textView.setTextColor(ContextCompat.getColor(context, SkinManager.getCurrentSkinType(context) == 0 ? R.color.back_font_gray : R.color.first_text_color_night));
        this.pushBtm.setBackground(ContextCompat.getDrawable(this.context, R.drawable.shape_round_red));
        this.pushBtm.setTextColor(ContextCompat.getColor(this.context, R.color.white));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0039  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ boolean lambda$onCreate$1(android.view.View r5, int[] r6, android.view.View r7, android.view.MotionEvent r8) {
        /*
            r4 = this;
            int r7 = r8.getAction()
            r0 = 0
            r1 = 1
            if (r7 != r1) goto L3c
            r5.getLocationOnScreen(r6)
            float r7 = r8.getRawX()
            float r8 = r8.getRawY()
            r2 = r6[r0]
            float r3 = (float) r2
            int r3 = (r7 > r3 ? 1 : (r7 == r3 ? 0 : -1))
            if (r3 < 0) goto L36
            int r3 = r5.getWidth()
            int r2 = r2 + r3
            float r2 = (float) r2
            int r7 = (r7 > r2 ? 1 : (r7 == r2 ? 0 : -1))
            if (r7 > 0) goto L36
            r6 = r6[r1]
            float r7 = (float) r6
            int r7 = (r8 > r7 ? 1 : (r8 == r7 ? 0 : -1))
            if (r7 < 0) goto L36
            int r5 = r5.getHeight()
            int r6 = r6 + r5
            float r5 = (float) r6
            int r5 = (r8 > r5 ? 1 : (r8 == r5 ? 0 : -1))
            if (r5 > 0) goto L36
            goto L37
        L36:
            r1 = r0
        L37:
            if (r1 != 0) goto L3c
            r4.dismiss()
        L3c:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.widget.DialogInput.lambda$onCreate$1(android.view.View, int[], android.view.View, android.view.MotionEvent):boolean");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$2(View view) {
        if (this.FullSCREEN_Y_N == 1) {
            this.FullSCREEN_Y_N = 0;
        } else {
            this.FullSCREEN_Y_N = 1;
        }
        setScreen();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$3() {
        EditText editText = this.et_content;
        if (editText != null) {
            editText.setFocusable(true);
            this.et_content.setFocusableInTouchMode(true);
            this.et_content.requestFocus();
            ((InputMethodManager) this.context.getSystemService("input_method")).showSoftInput(this.et_content, 2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$4(DialogInterface dialogInterface) {
        if (this.isNewComzantong && TextUtils.isEmpty(this.title)) {
            ProjectApp.comment_content = this.et_content.getText().toString();
            ProjectApp.comment_b_img = this.b_img;
            ProjectApp.comment_s_img = this.s_img;
            ProjectApp.commentvideoPath = this.videoPath;
            ProjectApp.commentvideoImage = this.videoImage;
            ProjectApp.commentvideoId = this.videoId;
            ProjectApp.commentvideoImagePath = this.videoImagePath;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setScreen$0() {
        if (getWindow() != null) {
            getWindow().setLayout(-1, -1);
        }
    }

    public void CommOkData() {
        View viewPeekDecorView = getWindow().peekDecorView();
        if (viewPeekDecorView != null) {
            ((InputMethodManager) this.context.getSystemService("input_method")).hideSoftInputFromWindow(viewPeekDecorView.getWindowToken(), 0);
        }
        dismiss();
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        View viewPeekDecorView = getWindow().peekDecorView();
        if (viewPeekDecorView != null) {
            ((InputMethodManager) this.context.getSystemService("input_method")).hideSoftInputFromWindow(viewPeekDecorView.getWindowToken(), 0);
        }
        super.dismiss();
    }

    public void getCourseAk(final int type) {
        YJYHttpUtils.post(this.context, NetworkRequestsURL.getCourseAkApi, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.widget.DialogInput.6
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
                try {
                    AliYunKeyInfoBean aliYunKeyInfoBean = (AliYunKeyInfoBean) new Gson().fromJson(s2, AliYunKeyInfoBean.class);
                    if (!"200".equals(aliYunKeyInfoBean.getCode()) || aliYunKeyInfoBean.getData() == null) {
                        ToastUtil.shortToast(DialogInput.this.context, aliYunKeyInfoBean.getMessage());
                    } else {
                        String strDecode = DesUtil.decode(CommonParameter.DES_KEY_ALI, aliYunKeyInfoBean.getData().getAkId());
                        String strDecode2 = DesUtil.decode(CommonParameter.DES_KEY_ALI, aliYunKeyInfoBean.getData().getAkSecret());
                        String strDecode3 = DesUtil.decode(CommonParameter.DES_KEY_ALI, aliYunKeyInfoBean.getData().getSt());
                        if (type == 1) {
                            DialogInput.this.uploadData(strDecode, strDecode2, strDecode3);
                        } else {
                            ProjectApp.vodsVideoUploadClient.refreshSTSToken(strDecode, strDecode2, strDecode3, "30");
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void getImageData(String path) {
        AjaxParams ajaxParams = new AjaxParams();
        try {
            ajaxParams.put("file", new File(path));
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        }
        YJYHttpUtils.postImage(this.context, NetworkRequestsURL.uploadForComment, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.widget.DialogInput.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                NewToast.showShort(DialogInput.this.context, "上传失败！", 0).show();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        DialogInput.this.b_img = new JSONObject(s2).optJSONObject("data").optString("b_img");
                        DialogInput.this.s_img = new JSONObject(s2).optJSONObject("data").optString("s_img");
                        DialogInput.this.uploadprogrss.setVisibility(8);
                    } else {
                        NewToast.showShort(DialogInput.this.context, new JSONObject(s2).optString("message"), 0).show();
                        AndroidImagePicker.getInstance().clearImageSets();
                        DialogInput.this.relimg.setVisibility(8);
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        });
    }

    @Override // android.view.View.OnClickListener
    @SuppressLint({"NonConstantResourceId"})
    public void onClick(View v2) {
        switch (v2.getId()) {
            case R.id.deleteimg /* 2131362880 */:
                AndroidImagePicker.getInstance().clearImageSets();
                this.relimg.setVisibility(8);
                this.uploadprogrss.setVisibility(8);
                this.toTxt.setVisibility(8);
                if (this.et_content.getText().length() < 5) {
                    if (this.fromVideo) {
                        TextView textView = (TextView) findViewById(R.id.dialog_input_btn_comment_ok);
                        Context context = this.context;
                        textView.setTextColor(ContextCompat.getColor(context, SkinManager.getCurrentSkinType(context) == 0 ? R.color.forth_txt_color : R.color.forth_txt_color_night));
                        this.pushBtm.setTextColor(ContextCompat.getColor(this.context, R.color.white));
                        this.pushBtm.setSelected(false);
                    } else {
                        this.pushBtm.setBackground(ContextCompat.getDrawable(this.context, R.drawable.shape_round_red_default));
                        ((TextView) findViewById(R.id.dialog_input_btn_comment_ok)).setTextColor(ContextCompat.getColor(this.context, R.color.back_font_gray));
                    }
                }
                this.urlList.clear();
                this.b_img = "";
                this.s_img = "";
                this.videoPath = "";
                this.videoImage = "";
                this.videoId = "";
                this.videoImagePath = "";
                this.postType = 0;
                VODSVideoUploadClient vODSVideoUploadClient = ProjectApp.vodsVideoUploadClient;
                if (vODSVideoUploadClient != null) {
                    vODSVideoUploadClient.pause();
                    ProjectApp.vodsVideoUploadClient.cancel();
                }
                Context context2 = this.context;
                if (context2 instanceof AnswerQuestionActivity) {
                    ((AnswerQuestionActivity) context2).setVideoId("");
                    break;
                }
                break;
            case R.id.dialog_input_btn_comment_cancel /* 2131362911 */:
                this.FullSCREEN_Y_N = 0;
                setScreen();
                break;
            case R.id.dialog_input_btn_comment_ok /* 2131362912 */:
            case R.id.pushBtm /* 2131366158 */:
                if (!CommonUtil.isFastClick()) {
                    submitComment();
                    break;
                }
                break;
            case R.id.imgshown /* 2131363814 */:
                if (this.uploadprogrss.getVisibility() == 0) {
                    NewToast.showShort(this.context, "上传中", 0).show();
                    break;
                } else {
                    try {
                        if (this.typelet != 1) {
                            List<String> list = this.urlList;
                            if (list != null && list.size() > 0) {
                                ImageViewerPopupViewCustom xPopupImageLoader = new ImageViewerPopupViewCustom(this.context).setSingleSrcView(null, Integer.valueOf(R.drawable.imgplacehodel_image)).setXPopupImageLoader(new ImageLoaderUtilsCustom());
                                xPopupImageLoader.popupInfo = new PopupInfo();
                                xPopupImageLoader.setImageUrls(new ArrayList(this.urlList)).setSrcView(null, 0).show();
                            }
                        } else if (!TextUtils.isEmpty(this.b_img)) {
                            this.urlList.clear();
                            this.urlList.add(this.b_img);
                            Glide.with(this.context).asBitmap().load((Object) GlideUtils.generateUrl(this.b_img)).apply((BaseRequestOptions<?>) new RequestOptions().override(40, 40)).into((RequestBuilder<Bitmap>) new SimpleTarget<Bitmap>() { // from class: com.psychiatrygarden.widget.DialogInput.3
                                @Override // com.bumptech.glide.request.target.Target
                                public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object bitmap, Transition glideAnimation) {
                                    onResourceReady((Bitmap) bitmap, (Transition<? super Bitmap>) glideAnimation);
                                }

                                public void onResourceReady(@NonNull Bitmap bitmap, Transition<? super Bitmap> glideAnimation) {
                                    ImageViewerPopupViewCustom xPopupImageLoader2 = new ImageViewerPopupViewCustom(DialogInput.this.context).setSingleSrcView(null, Integer.valueOf(R.drawable.imgplacehodel_image)).setXPopupImageLoader(new ImageLoaderUtilsCustom());
                                    xPopupImageLoader2.popupInfo = new PopupInfo();
                                    xPopupImageLoader2.setImageUrls(new ArrayList(DialogInput.this.urlList)).setSrcView(null, 0).show();
                                }
                            });
                        }
                        break;
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        return;
                    }
                }
            case R.id.upimg /* 2131368941 */:
                if (!hasRequiredPermissions(this.context)) {
                    new XPopup.Builder(this.context).asCustom(new RequestMediaPermissionPop(this.context, new OnConfirmListener() { // from class: com.psychiatrygarden.widget.n7
                        @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                        public final void onConfirm() {
                            this.f16730a.lambda$onClick$6();
                        }
                    })).show();
                    break;
                } else {
                    AndroidImagePicker.getInstance().setSelectLimit(1);
                    AndroidImagePicker.getInstance().pickMulti((Activity) this.context, true, new AndroidImagePicker.OnImagePickCompleteListener() { // from class: com.psychiatrygarden.widget.o7
                        @Override // com.pizidea.imagepicker.AndroidImagePicker.OnImagePickCompleteListener
                        public final void onImagePickComplete(List list2) {
                            this.f16755a.lambda$onClick$7(list2);
                        }
                    });
                    break;
                }
            case R.id.upvideoimg /* 2131368946 */:
                if (ContextCompat.checkSelfPermission(this.context, Permission.CAMERA) != 0 || ContextCompat.checkSelfPermission(this.context, Permission.WRITE_EXTERNAL_STORAGE) != 0) {
                    new XPopup.Builder(this.context).asCustom(new RequestMediaPermissionPop(this.context, new OnConfirmListener() { // from class: com.psychiatrygarden.widget.m7
                        @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                        public final void onConfirm() {
                            this.f16701a.lambda$onClick$5();
                        }
                    })).show();
                    break;
                }
                break;
        }
    }

    @Override // android.app.AlertDialog, android.app.Dialog
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SkinManager.onActivityCreateSetSkin((Activity) this.context);
        setContentView(!this.fromVideo ? R.layout.dialog_input : !this.landScape ? R.layout.dialog_input_vodeo : R.layout.dialog_input_vodeo_land);
        if (getWindow() != null) {
            getWindow().setWindowAnimations(R.style.popupAnimation);
            getWindow().setGravity(80);
            getWindow().clearFlags(131072);
            getWindow().setSoftInputMode(5);
        }
        setCanceledOnTouchOutside(true);
        this.processTextView = (TextView) findViewById(R.id.processTextView);
        this.vbimg = (ImageView) findViewById(R.id.vbimg);
        TextView textView = (TextView) findViewById(R.id.tv_title);
        this.et_content = (EditText) findViewById(R.id.dialog_input_et_comment);
        this.uploadprogrss = (RelativeLayout) findViewById(R.id.uploadprogrss);
        TextView textView2 = (TextView) findViewById(R.id.pushBtm);
        this.pushBtm = textView2;
        textView2.setSelected(false);
        this.toTxt = (TextView) findViewById(R.id.toTxt);
        final int currentSkinType = SkinManager.getCurrentSkinType(getContext());
        boolean z2 = this.fromVideo;
        int i2 = R.drawable.shape_round_red_default_btn;
        if (!z2) {
            GradientDrawable gradientDrawable = (GradientDrawable) ContextCompat.getDrawable(this.context, currentSkinType == 0 ? R.drawable.shape_round_red_default_btn : R.drawable.bg_write_comment_publish_btn);
            gradientDrawable.setCornerRadius(SizeUtil.dp2px(this.context, 6));
            if (currentSkinType == 0) {
                gradientDrawable.setColor(Color.parseColor("#eeeeee"));
                gradientDrawable.setStroke(1, Color.parseColor("#eeeeee"));
                this.pushBtm.setTextColor(Color.parseColor("#C2C6CB"));
            } else {
                gradientDrawable.setColor(Color.parseColor("#252C46"));
                gradientDrawable.setStroke(1, Color.parseColor("#252C46"));
                this.pushBtm.setTextColor(Color.parseColor("#454C64"));
            }
            this.pushBtm.setBackground(gradientDrawable);
        }
        if (this.fromVideo && this.landScape) {
            final View viewFindViewById = findViewById(R.id.commentList_bottom_layout);
            final int[] iArr = new int[2];
            getWindow().getDecorView().setOnTouchListener(new View.OnTouchListener() { // from class: com.psychiatrygarden.widget.i7
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return this.f16580c.lambda$onCreate$1(viewFindViewById, iArr, view, motionEvent);
                }
            });
        }
        this.et_content.addTextChangedListener(new TextWatcher() { // from class: com.psychiatrygarden.widget.DialogInput.1
            static final /* synthetic */ boolean $assertionsDisabled = false;

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s2) {
                String string = s2.toString();
                int size = DialogInput.this.urlList.size();
                int i3 = R.drawable.bg_write_comment_publish_btn;
                int i4 = R.color.first_txt_color_night;
                if (size <= 0 && (string.length() < 5 || string.length() > 5000)) {
                    Context context = DialogInput.this.context;
                    if (currentSkinType == 0) {
                        i3 = R.drawable.shape_round_red_default_btn;
                    }
                    GradientDrawable gradientDrawable2 = (GradientDrawable) ContextCompat.getDrawable(context, i3);
                    if (currentSkinType == 0) {
                        gradientDrawable2.setColor(Color.parseColor("#eeeeee"));
                        gradientDrawable2.setStroke(1, Color.parseColor("#eeeeee"));
                        DialogInput.this.pushBtm.setTextColor(Color.parseColor("#C2C6CB"));
                    } else {
                        gradientDrawable2.setColor(Color.parseColor("#252C46"));
                        gradientDrawable2.setStroke(1, Color.parseColor("#252C46"));
                        DialogInput.this.pushBtm.setTextColor(Color.parseColor("#454C64"));
                    }
                    if (DialogInput.this.fromVideo) {
                        DialogInput.this.pushBtm.setSelected(false);
                        ((TextView) DialogInput.this.findViewById(R.id.dialog_input_btn_comment_ok)).setTextColor(ContextCompat.getColor(DialogInput.this.context, currentSkinType == 0 ? R.color.forth_txt_color : R.color.forth_txt_color_night));
                        return;
                    }
                    DialogInput.this.pushBtm.setBackground(gradientDrawable2);
                    TextView textView3 = (TextView) DialogInput.this.findViewById(R.id.dialog_input_btn_comment_ok);
                    Context context2 = DialogInput.this.context;
                    if (currentSkinType == 0) {
                        i4 = R.color.back_font_gray;
                    }
                    textView3.setTextColor(ContextCompat.getColor(context2, i4));
                    return;
                }
                Context context3 = DialogInput.this.context;
                if (currentSkinType == 0) {
                    i3 = R.drawable.shape_round_red_btn;
                }
                GradientDrawable gradientDrawable3 = (GradientDrawable) ContextCompat.getDrawable(context3, i3);
                if (currentSkinType == 0) {
                    DialogInput.this.pushBtm.setTextColor(-1);
                    gradientDrawable3.setColor(Color.parseColor("#F95843"));
                    gradientDrawable3.setStroke(1, Color.parseColor("#F95843"));
                } else {
                    DialogInput.this.pushBtm.setTextColor(Color.parseColor("#121622"));
                }
                if (DialogInput.this.fromVideo) {
                    TextView textView4 = (TextView) DialogInput.this.findViewById(R.id.dialog_input_btn_comment_ok);
                    Context context4 = DialogInput.this.context;
                    if (currentSkinType == 0) {
                        i4 = R.color.first_txt_color;
                    }
                    textView4.setTextColor(ContextCompat.getColor(context4, i4));
                    DialogInput.this.pushBtm.setSelected(true);
                    return;
                }
                TextView textView5 = (TextView) DialogInput.this.findViewById(R.id.dialog_input_btn_comment_ok);
                Context context5 = DialogInput.this.context;
                if (currentSkinType == 0) {
                    i4 = R.color.question_color;
                }
                textView5.setTextColor(ContextCompat.getColor(context5, i4));
                DialogInput.this.pushBtm.setBackground(gradientDrawable3);
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s2, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s2, int start, int before, int count) {
            }
        });
        this.screenview = (ImageView) findViewById(R.id.screenview);
        if (SkinManager.getCurrentSkinType(getContext()) == 1) {
            this.screenview.setImageResource(R.drawable.ic_scale_night);
        }
        this.screenview.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.j7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16611c.lambda$onCreate$2(view);
            }
        });
        if (this.landScape) {
            this.screenview.setVisibility(8);
            this.et_content.performClick();
        }
        ImageView imageView = (ImageView) findViewById(R.id.upimg);
        ImageView imageView2 = (ImageView) findViewById(R.id.upvideoimg);
        this.relimg = (RelativeLayout) findViewById(R.id.relimg);
        this.imgshown = (RoundedImageView) findViewById(R.id.imgshown);
        ImageView imageView3 = (ImageView) findViewById(R.id.deleteimg);
        imageView.setOnClickListener(this);
        imageView3.setOnClickListener(this);
        this.imgshown.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        if (TextUtils.isEmpty(this.b_img)) {
            this.relimg.setVisibility(8);
            this.uploadprogrss.setVisibility(8);
        } else {
            this.relimg.setVisibility(0);
            this.uploadprogrss.setVisibility(8);
            Glide.with(this.context).load((Object) GlideUtils.generateUrl(this.b_img)).placeholder(new ColorDrawable(ContextCompat.getColor(this.imgshown.getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).into(this.imgshown);
            if (TextUtils.isEmpty(this.videoId)) {
                this.vbimg.setVisibility(8);
            } else {
                this.vbimg.setVisibility(0);
            }
            if (this.fromVideo) {
                this.pushBtm.setSelected(true);
            }
        }
        if (this.isNewComzantong) {
            this.et_content.setHint("提示: 1.复制考点还原与答案解析，复制他人评论，求赞、mark标记等无意义评论将会被删除,并且相关账号将会被封禁。2.点赞可以收藏他人评论");
        } else {
            this.et_content.setHint("");
            if (this.fromVideo || this.landScape) {
                this.et_content.setHint("写评论");
            }
        }
        if (this.isErrorCorrection) {
            imageView.setVisibility(8);
        }
        if (!this.title.equals("")) {
            textView.setText(this.title);
            if (this.fromVideo && this.landScape) {
                this.et_content.setHint(this.title);
            }
        }
        if (!this.content.equals("")) {
            if (!this.fromVideo) {
                TextView textView3 = this.pushBtm;
                Context context = this.context;
                if (currentSkinType != 0) {
                    i2 = R.drawable.bg_write_comment_publish_btn;
                }
                textView3.setBackground(ContextCompat.getDrawable(context, i2));
            }
            ((TextView) findViewById(R.id.dialog_input_btn_comment_ok)).setTextColor(-1);
            this.et_content.setText(this.content);
            this.et_content.setSelection(this.content.length());
        }
        if (this.fromVideo && TextUtils.isEmpty(this.content)) {
            this.et_content.setText("");
        }
        this.pushBtm.setOnClickListener(this);
        this.toTxt.setOnClickListener(this);
        findViewById(R.id.dialog_input_btn_comment_ok).setOnClickListener(this);
        findViewById(R.id.dialog_input_btn_comment_cancel).setOnClickListener(this);
        setScreen();
        this.et_content.postDelayed(new Runnable() { // from class: com.psychiatrygarden.widget.k7
            @Override // java.lang.Runnable
            public final void run() {
                this.f16641c.lambda$onCreate$3();
            }
        }, 200L);
        setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.psychiatrygarden.widget.l7
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                this.f16671c.lambda$onCreate$4(dialogInterface);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(String isDismiss) {
        if (isDismiss.equals(EventBusConstant.EVENT_DIALOG_INPUT_DISMISS)) {
            this.et_content.setText("");
            this.b_img = "";
            this.s_img = "";
            this.videoPath = "";
            this.videoImage = "";
            this.videoId = "";
            this.videoImagePath = "";
            CommOkData();
        }
    }

    @Override // android.app.Dialog
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    public void setFromVideo(boolean fromVideo) {
        this.fromVideo = fromVideo;
    }

    public void setScreen() {
        if (this.FullSCREEN_Y_N != 0) {
            this.screenview.setVisibility(8);
            this.pushBtm.setVisibility(8);
            findViewById(R.id.dialog_input_btn_comment_ok).setVisibility(0);
            findViewById(R.id.dialog_input_btn_comment_cancel).setVisibility(0);
            getWindow().setLayout(-1, ScreenUtil.getScreenHeight((Activity) this.context));
            new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.widget.p7
                @Override // java.lang.Runnable
                public final void run() {
                    this.f16784c.lambda$setScreen$0();
                }
            }, 200L);
            return;
        }
        if (this.landScape) {
            this.screenview.setVisibility(8);
        } else {
            this.screenview.setVisibility(0);
        }
        this.pushBtm.setVisibility(0);
        findViewById(R.id.dialog_input_btn_comment_ok).setVisibility(8);
        findViewById(R.id.dialog_input_btn_comment_cancel).setVisibility(8);
        if (this.fromVideo && this.landScape) {
            getWindow().setLayout(getContext().getResources().getDisplayMetrics().widthPixels, ScreenUtil.getPxByDp(this.context, 115));
        } else {
            getWindow().setLayout(getContext().getResources().getDisplayMetrics().widthPixels, ScreenUtil.getPxByDp(this.context, R2.attr.actionModeCloseDrawable));
        }
    }

    @Override // android.app.Dialog
    public void show() {
        super.show();
        EventBus.getDefault().register(this);
    }

    public void submitComment() {
        int i2 = this.postType;
        if (i2 == 1) {
            if (TextUtils.isEmpty(this.b_img) || TextUtils.isEmpty(this.s_img)) {
                NewToast.showShort(this.context, "图片未上传成功，请重新选择上传图片！", 0).show();
                return;
            } else if (this.et_content.getText().toString().trim().length() > 5000) {
                NewToast.showShort(this.context, "评论长度不能超过5000字", 0).show();
                return;
            } else {
                this.clickListener.onclickStringBack(this.et_content.getText().toString(), this.b_img, this.s_img);
                return;
            }
        }
        if (i2 == 2) {
            if (TextUtils.isEmpty(this.videoId)) {
                NewToast.showShort(this.context, "视频未上传成功，请重新选择上传视频！", 0).show();
                return;
            } else {
                this.clickListener.onclickStringBack(this.et_content.getText().toString(), this.videoId, this.videoImagePath);
                return;
            }
        }
        if (this.et_content.getText().toString().trim().length() < 5) {
            NewToast.showShort(this.context, "评论至少要输入5个字", 0).show();
        } else if (this.et_content.getText().toString().trim().length() > 5000) {
            NewToast.showShort(this.context, "评论长度不能超过5000字", 0).show();
        } else {
            this.clickListener.onclickStringBack(this.et_content.getText().toString(), this.b_img, this.s_img);
        }
    }

    public void uploadData(String accessKeyId, String accessKeySecret, String securityToken) {
        VodHttpClientConfig vodHttpClientConfigBuild = new VodHttpClientConfig.Builder().setMaxRetryCount(2).setConnectionTimeout(60000).setSocketTimeout(60000).build();
        SvideoInfo svideoInfo = new SvideoInfo();
        svideoInfo.setTitle("评论短视频上传");
        svideoInfo.setDesc("");
        svideoInfo.setCateId(1000394832);
        ProjectApp.vodsVideoUploadClient.uploadWithVideoAndImg(new VodSessionCreateInfo.Builder().setImagePath(this.videoImage).setVideoPath(this.videoPath).setAccessKeyId(accessKeyId).setAccessKeySecret(accessKeySecret).setSecurityToken(securityToken).setExpriedTime("30").setIsTranscode(Boolean.TRUE).setSvideoInfo(svideoInfo).setTemplateGroupId("04465c7d634faf99e74f203c3f7bf689").setVodHttpClientConfig(vodHttpClientConfigBuild).build(), new AnonymousClass7());
    }

    public void setFromVideo(boolean fromVideo, boolean landScape) {
        this.fromVideo = fromVideo;
        this.landScape = landScape;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBusMessage<String> eve) {
        if (eve.getKey().equals("evevideo")) {
            this.videoPath = eve.getValueObj();
            Glide.with(this.context).asBitmap().load((Object) GlideUtils.generateUrl(eve.getValueObj())).apply((BaseRequestOptions<?>) new RequestOptions().override(Integer.MIN_VALUE)).listener(new RequestListener<Bitmap>() { // from class: com.psychiatrygarden.widget.DialogInput.5
                @Override // com.bumptech.glide.request.RequestListener
                public boolean onLoadFailed(@Nullable GlideException e2, Object model, Target<Bitmap> target, boolean isFirstResource) {
                    return false;
                }

                @Override // com.bumptech.glide.request.RequestListener
                public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                    return false;
                }
            }).into((RequestBuilder<Bitmap>) new SimpleTarget<Bitmap>() { // from class: com.psychiatrygarden.widget.DialogInput.4
                @Override // com.bumptech.glide.request.target.Target
                public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object bitmap, @Nullable Transition transition) {
                    onResourceReady((Bitmap) bitmap, (Transition<? super Bitmap>) transition);
                }

                public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                    if (bitmap == null) {
                        ToastUtil.shortToast(DialogInput.this.context, "暂不支持此类型视频！");
                        DialogInput.this.relimg.setVisibility(8);
                        DialogInput.this.uploadprogrss.setVisibility(8);
                        return;
                    }
                    DialogInput dialogInput = DialogInput.this;
                    dialogInput.postType = 2;
                    dialogInput.imgshown.setImageBitmap(bitmap);
                    DialogInput.this.videoImage = FileUtils.saveBitmap(bitmap, System.currentTimeMillis() + "_ykb");
                    DialogInput.this.uploadprogrss.setVisibility(0);
                    DialogInput.this.relimg.setVisibility(0);
                    DialogInput.this.toTxt.setVisibility(8);
                    ((TextView) DialogInput.this.findViewById(R.id.dialog_input_btn_comment_ok)).setTextColor(ContextCompat.getColor(DialogInput.this.context, R.color.question_color));
                    DialogInput.this.pushBtm.setBackground(ContextCompat.getDrawable(DialogInput.this.context, R.drawable.actionsheet_bottom_normal));
                    DialogInput.this.getCourseAk(1);
                }
            });
        }
    }

    public DialogInput(Context context, onDialogClickListener clickListener) {
        super(context, R.style.MyDialog);
        this.content = "";
        this.title = "";
        this.b_img = "";
        this.s_img = "";
        this.typelet = 0;
        this.urlList = new ArrayList();
        this.FullSCREEN_Y_N = 0;
        this.videoImage = "";
        this.videoPath = "";
        this.postType = 0;
        this.isNewComzantong = false;
        this.isErrorCorrection = false;
        this.videoId = "";
        this.videoImagePath = "";
        this.context = context;
        this.clickListener = clickListener;
    }

    public DialogInput(Context context, onDialogClickListener clickListener, boolean isNewComzantong) {
        super(context, R.style.MyDialog);
        this.content = "";
        this.title = "";
        this.b_img = "";
        this.s_img = "";
        this.typelet = 0;
        this.urlList = new ArrayList();
        this.FullSCREEN_Y_N = 0;
        this.videoImage = "";
        this.videoPath = "";
        this.postType = 0;
        this.isErrorCorrection = false;
        this.videoId = "";
        this.videoImagePath = "";
        this.context = context;
        this.clickListener = clickListener;
        this.isNewComzantong = isNewComzantong;
        if (isNewComzantong) {
            this.content = ProjectApp.comment_content;
            this.b_img = ProjectApp.comment_b_img;
            this.s_img = ProjectApp.comment_s_img;
            this.videoPath = ProjectApp.commentvideoPath;
            this.videoImage = ProjectApp.commentvideoImage;
            String str = ProjectApp.commentvideoId;
            this.videoId = str;
            this.videoImagePath = ProjectApp.commentvideoImagePath;
            if (!TextUtils.isEmpty(str)) {
                this.postType = 2;
                this.b_img = ProjectApp.commentvideoImagePath;
            } else if (!TextUtils.isEmpty(this.b_img)) {
                this.postType = 1;
            } else {
                this.postType = 0;
            }
        }
    }

    public DialogInput(Context context, onDialogClickListener clickListener, String content, String title, boolean isNewComzantong) {
        super(context, R.style.MyDialog);
        this.content = "";
        this.title = "";
        this.b_img = "";
        this.s_img = "";
        this.typelet = 0;
        this.urlList = new ArrayList();
        this.FullSCREEN_Y_N = 0;
        this.videoImage = "";
        this.videoPath = "";
        this.postType = 0;
        this.isErrorCorrection = false;
        this.videoId = "";
        this.videoImagePath = "";
        this.context = context;
        this.clickListener = clickListener;
        this.content = content;
        this.title = title;
        this.isNewComzantong = isNewComzantong;
        if (isNewComzantong) {
            this.content = ProjectApp.comment_content;
            this.b_img = ProjectApp.comment_b_img;
            this.s_img = ProjectApp.comment_s_img;
            this.videoPath = ProjectApp.commentvideoPath;
            this.videoImage = ProjectApp.commentvideoImage;
            String str = ProjectApp.commentvideoId;
            this.videoId = str;
            this.videoImagePath = ProjectApp.commentvideoImagePath;
            if (!TextUtils.isEmpty(str)) {
                this.postType = 2;
                this.b_img = ProjectApp.commentvideoImagePath;
            } else if (!TextUtils.isEmpty(this.b_img)) {
                this.postType = 1;
            } else {
                this.postType = 0;
            }
        }
    }

    public DialogInput(boolean fromVideo, Context context, onDialogClickListener clickListener, String content, String title, boolean isNewComzantong, boolean landScape) {
        super(context, R.style.MyDialog);
        this.content = "";
        this.title = "";
        this.b_img = "";
        this.s_img = "";
        this.typelet = 0;
        this.urlList = new ArrayList();
        this.FullSCREEN_Y_N = 0;
        this.videoImage = "";
        this.videoPath = "";
        this.postType = 0;
        this.isErrorCorrection = false;
        this.videoId = "";
        this.videoImagePath = "";
        this.context = context;
        this.landScape = landScape;
        this.fromVideo = fromVideo;
        this.clickListener = clickListener;
        this.content = content;
        this.title = title;
        this.isNewComzantong = isNewComzantong;
        if (isNewComzantong) {
            this.content = ProjectApp.comment_content;
            this.b_img = ProjectApp.comment_b_img;
            this.s_img = ProjectApp.comment_s_img;
            this.videoPath = ProjectApp.commentvideoPath;
            this.videoImage = ProjectApp.commentvideoImage;
            String str = ProjectApp.commentvideoId;
            this.videoId = str;
            this.videoImagePath = ProjectApp.commentvideoImagePath;
            if (!TextUtils.isEmpty(str)) {
                this.postType = 2;
                this.b_img = ProjectApp.commentvideoImagePath;
            } else if (!TextUtils.isEmpty(this.b_img)) {
                this.postType = 1;
            } else {
                this.postType = 0;
            }
        }
    }

    public DialogInput(boolean fromVideo, Context context, onDialogClickListener clickListener, String content, String title, boolean isNewComzantong) {
        super(context, R.style.MyDialog);
        this.content = "";
        this.title = "";
        this.b_img = "";
        this.s_img = "";
        this.typelet = 0;
        this.urlList = new ArrayList();
        this.FullSCREEN_Y_N = 0;
        this.videoImage = "";
        this.videoPath = "";
        this.postType = 0;
        this.isErrorCorrection = false;
        this.videoId = "";
        this.videoImagePath = "";
        this.context = context;
        this.fromVideo = fromVideo;
        this.clickListener = clickListener;
        this.content = content;
        this.title = title;
        this.isNewComzantong = isNewComzantong;
        if (isNewComzantong) {
            this.content = ProjectApp.comment_content;
            this.b_img = ProjectApp.comment_b_img;
            this.s_img = ProjectApp.comment_s_img;
            this.videoPath = ProjectApp.commentvideoPath;
            this.videoImage = ProjectApp.commentvideoImage;
            String str = ProjectApp.commentvideoId;
            this.videoId = str;
            this.videoImagePath = ProjectApp.commentvideoImagePath;
            if (!TextUtils.isEmpty(str)) {
                this.postType = 2;
                this.b_img = ProjectApp.commentvideoImagePath;
            } else if (!TextUtils.isEmpty(this.b_img)) {
                this.postType = 1;
            } else {
                this.postType = 0;
            }
        }
    }

    public DialogInput(Context context, onDialogClickListener clickListener, String content, String title, boolean isNewComzantong, boolean isErrorCorrection) {
        super(context, R.style.MyDialog);
        this.content = "";
        this.title = "";
        this.b_img = "";
        this.s_img = "";
        this.typelet = 0;
        this.urlList = new ArrayList();
        this.FullSCREEN_Y_N = 0;
        this.videoImage = "";
        this.videoPath = "";
        this.postType = 0;
        this.videoId = "";
        this.videoImagePath = "";
        this.context = context;
        this.clickListener = clickListener;
        this.content = content;
        this.title = title;
        this.isNewComzantong = isNewComzantong;
        this.isErrorCorrection = isErrorCorrection;
        if (isNewComzantong) {
            this.content = ProjectApp.comment_content;
            this.b_img = ProjectApp.comment_b_img;
            this.s_img = ProjectApp.comment_s_img;
            this.videoPath = ProjectApp.commentvideoPath;
            this.videoImage = ProjectApp.commentvideoImage;
            String str = ProjectApp.commentvideoId;
            this.videoId = str;
            this.videoImagePath = ProjectApp.commentvideoImagePath;
            if (!TextUtils.isEmpty(str)) {
                this.postType = 2;
                this.b_img = ProjectApp.commentvideoImagePath;
            } else if (!TextUtils.isEmpty(this.b_img)) {
                this.postType = 1;
            } else {
                this.postType = 0;
            }
        }
    }

    public DialogInput(Context context, onDialogClickListener clickListener, String content, String title, boolean isNewComzantong, String b_img, String s_img, int typelet, String videoId, boolean landScape) {
        super(context, R.style.MyDialog);
        this.content = "";
        this.title = "";
        this.b_img = "";
        this.s_img = "";
        this.typelet = 0;
        this.urlList = new ArrayList();
        this.FullSCREEN_Y_N = 0;
        this.videoImage = "";
        this.videoPath = "";
        this.postType = 0;
        this.isErrorCorrection = false;
        this.landScape = landScape;
        this.context = context;
        this.clickListener = clickListener;
        this.content = content;
        this.title = title;
        this.isNewComzantong = isNewComzantong;
        this.b_img = b_img;
        this.s_img = s_img;
        this.typelet = typelet;
        this.videoId = videoId;
        this.videoImagePath = b_img;
        if (!TextUtils.isEmpty(videoId)) {
            this.postType = 2;
            this.b_img = CommonUtil.getVideoMd5key(b_img);
        } else if (!TextUtils.isEmpty(b_img)) {
            this.postType = 1;
        } else {
            this.postType = 0;
        }
    }

    public DialogInput(Context context, onDialogClickListener clickListener, String content, String title, boolean isNewComzantong, String b_img, String s_img, int typelet, String videoId) {
        super(context, R.style.MyDialog);
        this.content = "";
        this.title = "";
        this.b_img = "";
        this.s_img = "";
        this.typelet = 0;
        this.urlList = new ArrayList();
        this.FullSCREEN_Y_N = 0;
        this.videoImage = "";
        this.videoPath = "";
        this.postType = 0;
        this.isErrorCorrection = false;
        this.context = context;
        this.clickListener = clickListener;
        this.content = content;
        this.title = title;
        this.isNewComzantong = isNewComzantong;
        this.b_img = b_img;
        this.s_img = s_img;
        this.typelet = typelet;
        this.videoId = videoId;
        this.videoImagePath = b_img;
        if (!TextUtils.isEmpty(videoId)) {
            this.postType = 2;
            this.b_img = CommonUtil.getVideoMd5key(b_img);
        } else if (!TextUtils.isEmpty(b_img)) {
            this.postType = 1;
        } else {
            this.postType = 0;
        }
    }

    public DialogInput(Context context, onDialogClickListener clickListener, String content, String title) {
        super(context, R.style.MyDialog);
        this.content = "";
        this.title = "";
        this.b_img = "";
        this.s_img = "";
        this.typelet = 0;
        this.urlList = new ArrayList();
        this.FullSCREEN_Y_N = 0;
        this.videoImage = "";
        this.videoPath = "";
        this.postType = 0;
        this.isNewComzantong = false;
        this.isErrorCorrection = false;
        this.videoId = "";
        this.videoImagePath = "";
        this.context = context;
        this.clickListener = clickListener;
        this.content = content;
        this.title = title;
    }
}
