package com.psychiatrygarden.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.alibaba.sdk.android.vod.upload.VODSVideoUploadClient;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.hjq.permissions.Permission;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.PopupInfo;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.util.XPopupUtils;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.bean.ImageItem;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.online.bean.QuestionDetailBean;
import com.psychiatrygarden.bean.ImagesBean;
import com.psychiatrygarden.bean.QuestionNoteBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.QuestionDataRequest;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.QuestionDataCallBack;
import com.psychiatrygarden.interfaceclass.onDialogNoteListener;
import com.psychiatrygarden.utils.AliyunEvent;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ImageFactory;
import com.psychiatrygarden.utils.ImageLoaderUtilsCustom;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class DialogNoteInput extends AlertDialog implements View.OnClickListener, QuestionDataCallBack<String>, DialogInterface.OnDismissListener {
    public int FullSCREEN_Y_N;
    private String b_img;
    private onDialogNoteListener clickNoteListener;
    private String content;
    private Context context;
    private EditText et_content;
    private ImageView imgshown;
    private String module_type;
    public int postType;
    private TextView pushBtm;
    private QuestionNoteBean questionNoteBean;
    private String question_id;
    private RelativeLayout relimg;
    private String s_img;
    private ImageView screenview;
    private String title;
    private RelativeLayout uploadprogrss;
    private List<String> urlList;
    private String videoId;
    public String videoImage;
    public String videoPath;

    public DialogNoteInput(Context context) {
        super(context);
        this.content = "";
        this.title = "";
        this.b_img = "";
        this.s_img = "";
        this.urlList = new ArrayList();
        this.FullSCREEN_Y_N = 0;
        this.videoImage = "";
        this.videoPath = "";
        this.postType = 0;
        this.videoId = "";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$3() {
        ActivityCompat.requestPermissions((Activity) this.context, Build.VERSION.SDK_INT >= 33 ? new String[]{Permission.CAMERA, Permission.READ_MEDIA_IMAGES, Permission.READ_EXTERNAL_STORAGE} : new String[]{Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE}, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$4(List list) {
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
        this.uploadprogrss.setVisibility(0);
        this.relimg.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(View view) {
        if (this.FullSCREEN_Y_N == 1) {
            this.FullSCREEN_Y_N = 0;
        } else {
            this.FullSCREEN_Y_N = 1;
        }
        setScreen();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$2() {
        EditText editText = this.et_content;
        if (editText != null) {
            editText.setFocusable(true);
            this.et_content.setFocusableInTouchMode(true);
            this.et_content.requestFocus();
            ((InputMethodManager) this.context.getSystemService("input_method")).showSoftInput(this.et_content, 1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setScreen$0() {
        if (getWindow() != null) {
            getWindow().setLayout(-1, -1);
        }
    }

    private void pushNote(String toString, String b_img, String s_img) throws JSONException {
        String str;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", this.question_id);
        ajaxParams.put("module_type", this.module_type);
        QuestionNoteBean questionNoteBean = this.questionNoteBean;
        if (questionNoteBean != null && !TextUtils.isEmpty(questionNoteBean.getId())) {
            ajaxParams.put("id", this.questionNoteBean.getId());
        }
        ajaxParams.put("content", toString);
        try {
            if (!TextUtils.isEmpty(b_img)) {
                JSONArray jSONArray = new JSONArray();
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("b_img", b_img);
                jSONObject.put("s_img", s_img);
                jSONArray.put(jSONObject);
                ajaxParams.put("img", jSONArray.toString());
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        QuestionDataRequest.getIntance(getContext()).pushNote(ajaxParams, this);
        QuestionNoteBean questionNoteBean2 = this.questionNoteBean;
        if (questionNoteBean2 == null || TextUtils.isEmpty(questionNoteBean2.getId())) {
            str = "";
        } else {
            str = "[\"" + this.questionNoteBean.getId() + "\"]";
        }
        String str2 = "[\"" + toString + "\"]";
        QuestionDetailBean questionDetailBean = new QuestionDetailBean();
        questionDetailBean.setModule_type(this.module_type);
        questionDetailBean.setUnit_title(ProjectApp.unit_title);
        questionDetailBean.setExam_title(ProjectApp.exam_title);
        questionDetailBean.setIdentity_title(ProjectApp.identity_title);
        questionDetailBean.setIdentity_id(SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, getContext()));
        questionDetailBean.setChapter_title(ProjectApp.chapter_title);
        questionDetailBean.setChapter_id(ProjectApp.chapter_id);
        questionDetailBean.setChapter_parent_title(ProjectApp.chapter_parent_title);
        questionDetailBean.setChapter_parent_id(ProjectApp.chapter_parent_id);
        String json = ProjectApp.gson.toJson(questionDetailBean);
        if (this.content.isEmpty()) {
            AliyunEvent aliyunEvent = AliyunEvent.AddNote;
            CommonUtil.addLog(aliyunEvent.getKey(), aliyunEvent.getValue(), System.currentTimeMillis() + "", "", "", str2, json, "2");
            return;
        }
        AliyunEvent aliyunEvent2 = AliyunEvent.EditNote;
        CommonUtil.addLog(aliyunEvent2.getKey(), aliyunEvent2.getValue(), System.currentTimeMillis() + "", "", str, str2, json, "2");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updatePublishBtn() {
        String string = this.et_content.getText().toString();
        if (TextUtils.isEmpty(string)) {
            string = "";
        }
        if (this.urlList.size() <= 0 && (string.length() < 1 || string.length() > 5000)) {
            this.pushBtm.setEnabled(false);
            this.pushBtm.setBackground(ContextCompat.getDrawable(this.context, R.drawable.shape_round_888888_default));
            ((TextView) findViewById(R.id.dialog_input_btn_comment_ok)).setTextColor(this.context.getResources().getColor(R.color.back_font_gray));
        } else {
            this.pushBtm.setBackground(ContextCompat.getDrawable(this.context, R.drawable.shape_round_dd594a_red));
            this.pushBtm.setTextColor(ContextCompat.getColor(this.context, R.color.white));
            this.pushBtm.setEnabled(true);
            TextView textView = (TextView) findViewById(R.id.dialog_input_btn_comment_ok);
            Context context = this.context;
            textView.setTextColor(ContextCompat.getColor(context, SkinManager.getCurrentSkinType(context) == 0 ? R.color.question_color : R.color.question_color_night));
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
        this.urlList.clear();
        super.dismiss();
    }

    public void getImageData(final String path) {
        AjaxParams ajaxParams = new AjaxParams();
        try {
            ajaxParams.put("file", new File(path));
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        }
        YJYHttpUtils.postImage(this.context, NetworkRequestsURL.noteUploads, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.widget.DialogNoteInput.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                NewToast.showShort(DialogNoteInput.this.context, "上传失败！", 0).show();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
                        if (jSONObjectOptJSONObject == null) {
                            return;
                        }
                        DialogNoteInput.this.b_img = jSONObjectOptJSONObject.optString("b_img");
                        DialogNoteInput.this.s_img = jSONObjectOptJSONObject.optString("s_img");
                        DialogNoteInput.this.uploadprogrss.setVisibility(8);
                        DialogNoteInput.this.urlList.clear();
                        DialogNoteInput.this.urlList.add(DialogNoteInput.this.b_img);
                        Glide.with(DialogNoteInput.this.context).load(path).apply((BaseRequestOptions<?>) new RequestOptions().override(Integer.MIN_VALUE)).into(DialogNoteInput.this.imgshown);
                        DialogNoteInput.this.updatePublishBtn();
                    } else {
                        NewToast.showShort(DialogNoteInput.this.context, new JSONObject(s2).optString("message"), 0).show();
                        AndroidImagePicker.getInstance().clearImageSets();
                        DialogNoteInput.this.relimg.setVisibility(8);
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        });
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) throws JSONException {
        switch (v2.getId()) {
            case R.id.deleteimg /* 2131362880 */:
                AndroidImagePicker.getInstance().clearImageSets();
                this.relimg.setVisibility(8);
                this.uploadprogrss.setVisibility(8);
                this.urlList.clear();
                this.b_img = "";
                this.s_img = "";
                this.videoPath = "";
                this.videoImage = "";
                this.videoId = "";
                this.postType = 0;
                VODSVideoUploadClient vODSVideoUploadClient = ProjectApp.vodsVideoUploadClient;
                if (vODSVideoUploadClient != null) {
                    vODSVideoUploadClient.pause();
                    ProjectApp.vodsVideoUploadClient.cancel();
                }
                updatePublishBtn();
                break;
            case R.id.dialog_input_btn_comment_ok /* 2131362912 */:
                submitComment();
                break;
            case R.id.imgshown /* 2131363814 */:
                if (this.uploadprogrss.getVisibility() == 0) {
                    NewToast.showShort(this.context, "上传中", 0).show();
                    break;
                } else {
                    try {
                        List<String> list = this.urlList;
                        if (list != null && list.size() > 0) {
                            ImageViewerPopupViewCustom xPopupImageLoader = new ImageViewerPopupViewCustom(this.context).setSingleSrcView(null, Integer.valueOf(R.drawable.imgplacehodel_image)).setXPopupImageLoader(new ImageLoaderUtilsCustom());
                            xPopupImageLoader.popupInfo = new PopupInfo();
                            xPopupImageLoader.setImageUrls(new ArrayList(this.urlList)).setSrcView(null, 0).show();
                            break;
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        return;
                    }
                }
                break;
            case R.id.iv_dialog_cancel /* 2131364057 */:
                dismiss();
                break;
            case R.id.pushBtm /* 2131366158 */:
                submitComment();
                break;
            case R.id.upimg /* 2131368941 */:
                if (!CommonUtil.hasRequiredPermissions(this.context)) {
                    new XPopup.Builder(this.context).asCustom(new RequestMediaPermissionPop(this.context, new OnConfirmListener() { // from class: com.psychiatrygarden.widget.x7
                        @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                        public final void onConfirm() {
                            this.f17087a.lambda$onClick$3();
                        }
                    })).show();
                    break;
                } else {
                    AndroidImagePicker.getInstance().setSelectLimit(1);
                    AndroidImagePicker.getInstance().pickMulti((Activity) this.context, true, new AndroidImagePicker.OnImagePickCompleteListener() { // from class: com.psychiatrygarden.widget.y7
                        @Override // com.pizidea.imagepicker.AndroidImagePicker.OnImagePickCompleteListener
                        public final void onImagePickComplete(List list2) {
                            this.f17120a.lambda$onClick$4(list2);
                        }
                    });
                    break;
                }
        }
    }

    @Override // android.app.AlertDialog, android.app.Dialog
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_note_input);
        CommonUtil.getScreenWidth(this.context);
        getWindow().setWindowAnimations(R.style.popupAnimation);
        getWindow().setGravity(80);
        getWindow().clearFlags(131072);
        getWindow().setSoftInputMode(5);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        QuestionNoteBean questionNoteBean = this.questionNoteBean;
        if (questionNoteBean != null) {
            this.content = questionNoteBean.getContent();
            if (this.questionNoteBean.getImg().size() > 0) {
                this.s_img = this.questionNoteBean.getImg().get(0).getS_img();
                this.b_img = this.questionNoteBean.getImg().get(0).getB_img();
            }
        }
        ImageView imageView = (ImageView) findViewById(R.id.vbimg);
        TextView textView = (TextView) findViewById(R.id.tv_title);
        this.et_content = (EditText) findViewById(R.id.dialog_input_et_comment);
        this.uploadprogrss = (RelativeLayout) findViewById(R.id.uploadprogrss);
        TextView textView2 = (TextView) findViewById(R.id.pushBtm);
        this.pushBtm = textView2;
        textView2.setEnabled(false);
        this.et_content.addTextChangedListener(new TextWatcher() { // from class: com.psychiatrygarden.widget.DialogNoteInput.1
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s2) {
                DialogNoteInput.this.updatePublishBtn();
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s2, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s2, int start, int before, int count) {
            }
        });
        ImageView imageView2 = (ImageView) findViewById(R.id.screenview);
        this.screenview = imageView2;
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.v7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16989c.lambda$onCreate$1(view);
            }
        });
        ImageView imageView3 = (ImageView) findViewById(R.id.upimg);
        this.relimg = (RelativeLayout) findViewById(R.id.relimg);
        this.imgshown = (ImageView) findViewById(R.id.imgshown);
        ImageView imageView4 = (ImageView) findViewById(R.id.deleteimg);
        imageView3.setOnClickListener(this);
        imageView4.setOnClickListener(this);
        this.imgshown.setOnClickListener(this);
        if (TextUtils.isEmpty(this.b_img)) {
            this.relimg.setVisibility(8);
            this.uploadprogrss.setVisibility(8);
        } else {
            this.relimg.setVisibility(0);
            this.uploadprogrss.setVisibility(8);
            this.urlList.clear();
            this.urlList.add(this.b_img);
            Glide.with(this.context).load((Object) GlideUtils.generateUrl(this.b_img)).placeholder(new ColorDrawable(ContextCompat.getColor(this.imgshown.getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).into(this.imgshown);
            if (TextUtils.isEmpty(this.videoId)) {
                imageView.setVisibility(8);
            } else {
                imageView.setVisibility(0);
            }
            updatePublishBtn();
        }
        if (!this.title.equals("")) {
            textView.setText(this.title);
        }
        if (!this.content.equals("")) {
            this.et_content.setText(this.content);
        }
        this.pushBtm.setOnClickListener(this);
        updatePublishBtn();
        findViewById(R.id.dialog_input_btn_comment_ok).setOnClickListener(this);
        findViewById(R.id.iv_dialog_cancel).setOnClickListener(this);
        setScreen();
        this.et_content.post(new Runnable() { // from class: com.psychiatrygarden.widget.w7
            @Override // java.lang.Runnable
            public final void run() {
                this.f17040c.lambda$onCreate$2();
            }
        });
        setOnDismissListener(this);
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialog) {
        if (this.questionNoteBean == null) {
            ProjectApp.noteContent = this.et_content.getText().toString();
            ProjectApp.noteBigImage = this.b_img;
            ProjectApp.noteSmellImage = this.s_img;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(String isDismiss) {
        if (isDismiss.equals(EventBusConstant.EVENT_DIALOG_INPUT_DISMISS)) {
            CommOkData();
        }
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onFailure(Throwable t2, int errorNo, String strMsg, String requstUrl) {
        try {
            ((BaseActivity) this.context).hideProgressDialog();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onStart(String requstUrl) {
        try {
            ((BaseActivity) this.context).showProgressDialog();
        } catch (Exception e2) {
            e2.printStackTrace();
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

    public void setScreen() {
        if (this.FullSCREEN_Y_N == 0) {
            this.screenview.setVisibility(0);
            this.pushBtm.setVisibility(0);
            findViewById(R.id.dialog_input_btn_comment_ok).setVisibility(8);
            findViewById(R.id.iv_dialog_cancel).setVisibility(8);
            getWindow().setLayout(-1, ScreenUtil.getPxByDp(this.context, 250));
            return;
        }
        this.screenview.setVisibility(8);
        this.pushBtm.setVisibility(8);
        findViewById(R.id.dialog_input_btn_comment_ok).setVisibility(0);
        findViewById(R.id.iv_dialog_cancel).setVisibility(0);
        getWindow().setLayout(-1, XPopupUtils.getScreenHeight(this.context));
        new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.widget.u7
            @Override // java.lang.Runnable
            public final void run() {
                this.f16949c.lambda$setScreen$0();
            }
        }, 200L);
    }

    @Override // android.app.Dialog
    public void show() {
        super.show();
        EventBus.getDefault().register(this);
    }

    public void submitComment() throws JSONException {
        if (this.postType != 1) {
            if (this.et_content.getText().toString().trim().length() > 5000) {
                NewToast.showShort(this.context, "笔记长度不能超过5000字", 0).show();
                return;
            } else {
                pushNote(this.et_content.getText().toString(), this.b_img, this.s_img);
                return;
            }
        }
        if (TextUtils.isEmpty(this.b_img) || TextUtils.isEmpty(this.s_img)) {
            NewToast.showShort(this.context, "图片未上传成功，请重新选择上传图片！", 0).show();
        } else if (this.et_content.getText().toString().trim().length() > 5000) {
            NewToast.showShort(this.context, "笔记长度不能超过5000字", 0).show();
        } else {
            pushNote(this.et_content.getText().toString(), this.b_img, this.s_img);
        }
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onSuccess(String s2, String requstUrl) {
        try {
            ((BaseActivity) this.context).hideProgressDialog();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (requstUrl.equals(NetworkRequestsURL.pushNoteURL)) {
            try {
                JSONObject jSONObject = new JSONObject(s2);
                if (!jSONObject.optString("code").equals("200")) {
                    ToastUtil.shortToast(this.context, jSONObject.optString("message"));
                    return;
                }
                QuestionNoteBean questionNoteBean = this.questionNoteBean;
                if (questionNoteBean != null) {
                    questionNoteBean.setContent(this.et_content.getText().toString());
                    this.questionNoteBean.setCtime((System.currentTimeMillis() / 1000) + "");
                    if (this.questionNoteBean.getImg().size() > 0) {
                        this.questionNoteBean.getImg().get(0).setB_img(this.b_img);
                        this.questionNoteBean.getImg().get(0).setS_img(this.s_img);
                    } else if (!TextUtils.isEmpty(this.s_img)) {
                        ArrayList arrayList = new ArrayList();
                        ImagesBean imagesBean = new ImagesBean();
                        imagesBean.setS_img(this.s_img);
                        imagesBean.setB_img(this.b_img);
                        arrayList.add(imagesBean);
                        this.questionNoteBean.setImg(arrayList);
                    }
                    this.clickNoteListener.onclickStringBack(this.questionNoteBean);
                } else {
                    QuestionNoteBean questionNoteBean2 = new QuestionNoteBean();
                    this.questionNoteBean = questionNoteBean2;
                    questionNoteBean2.setContent(this.et_content.getText().toString());
                    this.questionNoteBean.setCtime((System.currentTimeMillis() / 1000) + "");
                    if (!TextUtils.isEmpty(this.s_img)) {
                        ArrayList arrayList2 = new ArrayList();
                        ImagesBean imagesBean2 = new ImagesBean();
                        imagesBean2.setS_img(this.s_img);
                        imagesBean2.setB_img(this.b_img);
                        arrayList2.add(imagesBean2);
                        this.questionNoteBean.setImg(arrayList2);
                    }
                    this.clickNoteListener.onclickStringBack(this.questionNoteBean);
                }
                this.b_img = "";
                this.s_img = "";
                this.et_content.setText("");
                dismiss();
                ToastUtil.shortToast(this.context, "添加成功");
            } catch (Exception e3) {
                this.b_img = "";
                this.s_img = "";
                this.et_content.setText("");
                dismiss();
                e3.printStackTrace();
            }
        }
    }

    public DialogNoteInput(Context context, String module_type, String question_id, onDialogNoteListener clickNoteListener) {
        super(context, R.style.MyDialog);
        this.content = "";
        this.title = "";
        this.b_img = "";
        this.s_img = "";
        this.urlList = new ArrayList();
        this.FullSCREEN_Y_N = 0;
        this.videoImage = "";
        this.videoPath = "";
        this.postType = 0;
        this.videoId = "";
        this.context = context;
        this.clickNoteListener = clickNoteListener;
        this.question_id = question_id;
        this.module_type = module_type;
        this.content = ProjectApp.noteContent;
        this.b_img = ProjectApp.noteBigImage;
        this.s_img = ProjectApp.noteSmellImage;
    }

    public DialogNoteInput(Context context, String module_type, String question_id, String title, onDialogNoteListener clickNoteListener) {
        super(context, R.style.MyDialog);
        this.content = "";
        this.title = "";
        this.b_img = "";
        this.s_img = "";
        this.urlList = new ArrayList();
        this.FullSCREEN_Y_N = 0;
        this.videoImage = "";
        this.videoPath = "";
        this.postType = 0;
        this.videoId = "";
        this.context = context;
        this.clickNoteListener = clickNoteListener;
        this.question_id = question_id;
        this.module_type = module_type;
        this.title = title;
        this.content = ProjectApp.noteContent;
        this.b_img = ProjectApp.noteBigImage;
        this.s_img = ProjectApp.noteSmellImage;
    }

    public DialogNoteInput(Context context, String module_type, String question_id, QuestionNoteBean questionNoteBean, onDialogNoteListener clickNoteListener) {
        super(context, R.style.MyDialog);
        this.content = "";
        this.title = "";
        this.b_img = "";
        this.s_img = "";
        this.urlList = new ArrayList();
        this.FullSCREEN_Y_N = 0;
        this.videoImage = "";
        this.videoPath = "";
        this.postType = 0;
        this.videoId = "";
        this.context = context;
        this.clickNoteListener = clickNoteListener;
        this.question_id = question_id;
        this.questionNoteBean = questionNoteBean;
        this.module_type = module_type;
    }
}
