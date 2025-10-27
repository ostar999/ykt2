package com.psychiatrygarden.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.hjq.permissions.Permission;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.PopupInfo;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.util.XPopupUtils;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.bean.ImageItem;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.adapter.UploadShowPicAdapter;
import com.psychiatrygarden.bean.ImagesBean;
import com.psychiatrygarden.bean.PushCommentNoteUploadPicItem;
import com.psychiatrygarden.bean.QuestionNoteBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.UpdateVideoCommentNote;
import com.psychiatrygarden.http.CourseDataRequest;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.QuestionDataCallBack;
import com.psychiatrygarden.interfaceclass.onDialogNoteListener;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.ImageFactory;
import com.psychiatrygarden.utils.ImageLoaderUtilsCustom;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.yikaobang.yixue.R;
import com.yikaobang.yixue.R2;
import de.greenrobot.event.EventBus;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class DialogVideoNoteInput extends AlertDialog implements View.OnClickListener, QuestionDataCallBack<String>, DialogInterface.OnDismissListener {
    public int FullSCREEN_Y_N;
    private String b_img;
    private onDialogNoteListener clickNoteListener;
    private boolean clickUpImg;
    private String content;
    private Context context;
    private Builder mBuilder;
    private UploadShowPicAdapter mPicAdapter;
    private List<String> mUrlList;
    private EditText noteEditText;
    public int postType;
    private TextView pushBtm;
    private String s_img;
    private String screenShotFileUrl_b;
    private String screenShotFileUrl_s;
    private ImageView screenview;
    private String title;
    private boolean updateSuccess;
    public String videoImage;
    public String videoPath;

    public static class Builder {
        private String bImg;
        private onDialogNoteListener clickNoteListener;
        private String content;
        private String courseId;
        private QuestionNoteBean editNote;
        private Context mContext;
        private DialogInterface.OnDismissListener mDismissListener;
        private String moduleType;
        private String objId;
        private String sImg;
        private String screenShotFilePath;
        private String title;
        private String videoId;

        public Builder(Context context) {
            this.mContext = context;
        }

        public DialogVideoNoteInput build() {
            return new DialogVideoNoteInput(this.mContext, this);
        }

        public onDialogNoteListener getClickNoteListener() {
            return this.clickNoteListener;
        }

        public String getContent() {
            return this.content;
        }

        public String getCourseId() {
            return this.courseId;
        }

        public DialogInterface.OnDismissListener getDismissListener() {
            return this.mDismissListener;
        }

        public QuestionNoteBean getEditNote() {
            return this.editNote;
        }

        public String getModuleType() {
            return this.moduleType;
        }

        public String getObjId() {
            return this.objId;
        }

        public String getScreenShotFilePath() {
            return this.screenShotFilePath;
        }

        public String getTitle() {
            return this.title;
        }

        public String getVideoId() {
            return this.videoId;
        }

        public String getbImg() {
            return this.bImg;
        }

        public String getsImg() {
            return this.sImg;
        }

        public Builder setClickNoteListener(onDialogNoteListener clickNoteListener) {
            this.clickNoteListener = clickNoteListener;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setCourseId(String courseId) {
            this.courseId = courseId;
            return this;
        }

        public Builder setDismissListener(DialogInterface.OnDismissListener dismissListener) {
            this.mDismissListener = dismissListener;
            return this;
        }

        public void setEditNote(QuestionNoteBean editNote) {
            this.editNote = editNote;
        }

        public Builder setModuleType(String moduleType) {
            this.moduleType = moduleType;
            return this;
        }

        public Builder setObjId(String objId) {
            this.objId = objId;
            return this;
        }

        public Builder setScreenShotFilePath(String screenShotFilePath) {
            this.screenShotFilePath = screenShotFilePath;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setVideoId(String videoId) {
            this.videoId = videoId;
            return this;
        }

        public Builder setbImg(String bImg) {
            this.bImg = bImg;
            return this;
        }

        public Builder setsImg(String sImg) {
            this.sImg = sImg;
            return this;
        }
    }

    public DialogVideoNoteInput(Context context, Builder builder) {
        super(context, R.style.MyDialog);
        this.content = "";
        this.title = "";
        this.b_img = "";
        this.s_img = "";
        this.mUrlList = new ArrayList();
        this.FullSCREEN_Y_N = 0;
        this.videoImage = "";
        this.videoPath = "";
        this.postType = 0;
        this.context = context;
        this.mBuilder = builder;
    }

    private void deleteFile() {
        Builder builder = this.mBuilder;
        if (builder == null || TextUtils.isEmpty(builder.screenShotFilePath)) {
            return;
        }
        try {
            File file = new File(this.mBuilder.screenShotFilePath);
            if (file.exists() && file.length() > 0) {
                file.delete();
                if (Build.VERSION.SDK_INT < 29) {
                    Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                    intent.setData(Uri.parse("file://" + this.mBuilder.screenShotFilePath));
                    this.context.sendBroadcast(intent);
                } else {
                    MediaScannerConnection.scanFile(this.context, new String[]{file.getParent()}, null, new MediaScannerConnection.OnScanCompletedListener() { // from class: com.psychiatrygarden.widget.z7
                        @Override // android.media.MediaScannerConnection.OnScanCompletedListener
                        public final void onScanCompleted(String str, Uri uri) {
                            DialogVideoNoteInput.lambda$deleteFile$6(str, uri);
                        }
                    });
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$deleteFile$6(String str, Uri uri) {
        String str2;
        StringBuilder sb = new StringBuilder();
        sb.append("path = ");
        sb.append(str);
        if (uri != null) {
            str2 = " uri = " + uri.getPath();
        } else {
            str2 = "";
        }
        sb.append(str2);
        LogUtils.d("onScanCompleted", sb.toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$4() {
        ActivityCompat.requestPermissions((Activity) this.context, Build.VERSION.SDK_INT >= 33 ? new String[]{Permission.CAMERA, Permission.READ_MEDIA_IMAGES, Permission.READ_EXTERNAL_STORAGE} : new String[]{Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE}, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$5(List list) {
        if (list == null || list.size() <= 0) {
            return;
        }
        String str = ((ImageItem) list.get(0)).path;
        float imageSize = ImageFactory.getImageSize(str);
        LogUtils.d("imgSize", String.valueOf(imageSize));
        if (imageSize > 20.0f) {
            NewToast.showShort(this.context, "请选择小于10M的图片上传", 0).show();
            return;
        }
        this.postType = 1;
        this.mUrlList.clear();
        this.mUrlList.add(((ImageItem) list.get(0)).path);
        if (this.mPicAdapter.getData().isEmpty() || this.mPicAdapter.getData().size() < 2) {
            PushCommentNoteUploadPicItem pushCommentNoteUploadPicItem = new PushCommentNoteUploadPicItem();
            pushCommentNoteUploadPicItem.setType(1);
            pushCommentNoteUploadPicItem.setStatus(1);
            pushCommentNoteUploadPicItem.setPicFilePath(str);
            this.mPicAdapter.addData((UploadShowPicAdapter) pushCommentNoteUploadPicItem);
        } else {
            PushCommentNoteUploadPicItem item = this.mPicAdapter.getItem(1);
            item.setPicFilePath(str);
            item.setStatus(1);
            item.setType(1);
            this.mPicAdapter.notifyItemChanged(1);
        }
        this.clickUpImg = true;
        this.updateSuccess = false;
        getImageData(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        ImageViewerPopupViewCustom xPopupImageLoader = new ImageViewerPopupViewCustom(this.context).setSingleSrcView(null, this.mPicAdapter.getItem(i2).getbImg()).setXPopupImageLoader(new ImageLoaderUtilsCustom());
        xPopupImageLoader.popupInfo = new PopupInfo();
        xPopupImageLoader.show();
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
        EditText editText = this.noteEditText;
        if (editText != null) {
            editText.setFocusable(true);
            this.noteEditText.setFocusableInTouchMode(true);
            this.noteEditText.requestFocus();
            ((InputMethodManager) this.context.getSystemService("input_method")).showSoftInput(this.noteEditText, 1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setScreen$0() {
        if (getWindow() != null) {
            getWindow().setLayout(-1, -1);
        }
    }

    private void pushNote(String toString, String b_img, String s_img) throws JSONException {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("course_id", this.mBuilder.getCourseId());
        ajaxParams.put("video_id", this.mBuilder.getObjId());
        if (TextUtils.isEmpty(this.mBuilder.getObjId())) {
            ajaxParams.put("video_id", this.mBuilder.getVideoId());
        }
        ajaxParams.put("module_type", this.mBuilder.getModuleType());
        if (this.mBuilder.getEditNote() != null && !TextUtils.isEmpty(this.mBuilder.getEditNote().getId())) {
            ajaxParams.put("id", this.mBuilder.getEditNote().getId());
        }
        ajaxParams.put("content", toString);
        try {
            JSONArray jSONArray = new JSONArray();
            for (PushCommentNoteUploadPicItem pushCommentNoteUploadPicItem : this.mPicAdapter.getData()) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("b_img", pushCommentNoteUploadPicItem.getbImg());
                jSONObject.put("s_img", pushCommentNoteUploadPicItem.getsImg());
                jSONArray.put(jSONObject);
            }
            ajaxParams.put("img", jSONArray.toString());
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        CourseDataRequest.getIntance(getContext()).addNote(ajaxParams, this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void uploadError(int position) {
        this.mPicAdapter.getItem(position).setStatus(3);
        this.mPicAdapter.notifyDataSetChanged();
    }

    private void uploadScreenShotFile() {
        AjaxParams ajaxParams = new AjaxParams();
        try {
            ajaxParams.put("file", new File(this.mBuilder.getScreenShotFilePath()));
            YJYHttpUtils.postImage(this.context, NetworkRequestsURL.noteUploads, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.widget.DialogVideoNoteInput.2
                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onFailure(Throwable t2, int errorNo, String strMsg) {
                    super.onFailure(t2, errorNo, strMsg);
                    DialogVideoNoteInput.this.uploadError(0);
                }

                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onSuccess(String s2) {
                    try {
                        JSONObject jSONObject = new JSONObject(s2);
                        if (jSONObject.optString("code").equals("200")) {
                            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
                            if (jSONObjectOptJSONObject == null) {
                                return;
                            }
                            DialogVideoNoteInput.this.screenShotFileUrl_b = jSONObjectOptJSONObject.optString("b_img");
                            DialogVideoNoteInput.this.screenShotFileUrl_s = jSONObjectOptJSONObject.optString("s_img");
                            DialogVideoNoteInput.this.pushBtm.setSelected(true);
                            PushCommentNoteUploadPicItem item = DialogVideoNoteInput.this.mPicAdapter.getItem(0);
                            item.setStatus(2);
                            item.setsImg(DialogVideoNoteInput.this.screenShotFileUrl_s);
                            item.setbImg(DialogVideoNoteInput.this.screenShotFileUrl_b);
                            DialogVideoNoteInput.this.mPicAdapter.notifyItemChanged(0);
                        } else {
                            NewToast.showShort(DialogVideoNoteInput.this.context, new JSONObject(s2).optString("message"), 0).show();
                            DialogVideoNoteInput.this.uploadError(0);
                        }
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                        DialogVideoNoteInput.this.uploadError(0);
                    }
                }
            });
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        }
    }

    public void CommOkData() {
        Window window = getWindow();
        Objects.requireNonNull(window);
        View viewPeekDecorView = window.peekDecorView();
        if (viewPeekDecorView != null) {
            ((InputMethodManager) this.context.getSystemService("input_method")).hideSoftInputFromWindow(viewPeekDecorView.getWindowToken(), 0);
        }
        dismiss();
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        Window window = getWindow();
        Objects.requireNonNull(window);
        View viewPeekDecorView = window.peekDecorView();
        if (viewPeekDecorView != null) {
            ((InputMethodManager) this.context.getSystemService("input_method")).hideSoftInputFromWindow(viewPeekDecorView.getWindowToken(), 0);
        }
        if (this.mBuilder.getDismissListener() != null) {
            this.mBuilder.getDismissListener().onDismiss(this);
        }
        super.dismiss();
    }

    public void getImageData(String path) {
        AjaxParams ajaxParams = new AjaxParams();
        try {
            ajaxParams.put("file", new File(path));
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        }
        YJYHttpUtils.postImage(this.context, NetworkRequestsURL.noteUploads, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.widget.DialogVideoNoteInput.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                NewToast.showShort(DialogVideoNoteInput.this.context, "上传失败！", 0).show();
                DialogVideoNoteInput.this.uploadError(1);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
                        if (jSONObjectOptJSONObject == null) {
                            return;
                        }
                        DialogVideoNoteInput.this.updateSuccess = true;
                        DialogVideoNoteInput.this.clickUpImg = false;
                        PushCommentNoteUploadPicItem item = DialogVideoNoteInput.this.mPicAdapter.getItem(1);
                        item.setbImg(jSONObjectOptJSONObject.optString("s_img"));
                        item.setbImg(jSONObjectOptJSONObject.optString("b_img"));
                        item.setStatus(2);
                        DialogVideoNoteInput.this.mPicAdapter.notifyDataSetChanged();
                    } else {
                        NewToast.showShort(DialogVideoNoteInput.this.context, new JSONObject(s2).optString("message"), 0).show();
                        AndroidImagePicker.getInstance().clearImageSets();
                        DialogVideoNoteInput.this.uploadError(1);
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                    DialogVideoNoteInput.this.uploadError(1);
                }
            }
        });
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) throws JSONException {
        switch (v2.getId()) {
            case R.id.dialog_input_btn_comment_ok /* 2131362912 */:
            case R.id.pushBtm /* 2131366158 */:
                submitComment();
                break;
            case R.id.iv_dialog_cancel /* 2131364057 */:
                findViewById(R.id.screenview).performClick();
                break;
            case R.id.upimg /* 2131368941 */:
                if (!CommonUtil.hasRequiredPermissions(this.context)) {
                    new XPopup.Builder(this.context).asCustom(new RequestMediaPermissionPop(this.context, new OnConfirmListener() { // from class: com.psychiatrygarden.widget.a8
                        @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                        public final void onConfirm() {
                            this.f16310a.lambda$onClick$4();
                        }
                    })).show();
                    break;
                } else {
                    AndroidImagePicker.getInstance().setSelectLimit(1);
                    AndroidImagePicker.getInstance().pickMulti((Activity) this.context, true, new AndroidImagePicker.OnImagePickCompleteListener() { // from class: com.psychiatrygarden.widget.b8
                        @Override // com.pizidea.imagepicker.AndroidImagePicker.OnImagePickCompleteListener
                        public final void onImagePickComplete(List list) {
                            this.f16338a.lambda$onClick$5(list);
                        }
                    });
                    break;
                }
        }
    }

    @Override // android.app.AlertDialog, android.app.Dialog
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_video_note_input);
        UploadShowPicAdapter uploadShowPicAdapter = new UploadShowPicAdapter();
        this.mPicAdapter = uploadShowPicAdapter;
        uploadShowPicAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.widget.c8
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f16364c.lambda$onCreate$1(baseQuickAdapter, view, i2);
            }
        });
        Window window = getWindow();
        Objects.requireNonNull(window);
        window.setWindowAnimations(R.style.popupAnimation);
        this.clickNoteListener = this.mBuilder.getClickNoteListener();
        getWindow().setGravity(80);
        getWindow().clearFlags(131072);
        getWindow().setSoftInputMode(5);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        TextView textView = (TextView) findViewById(R.id.tv_title);
        this.noteEditText = (EditText) findViewById(R.id.dialog_input_et_comment);
        this.pushBtm = (TextView) findViewById(R.id.pushBtm);
        this.noteEditText.addTextChangedListener(new TextWatcher() { // from class: com.psychiatrygarden.widget.DialogVideoNoteInput.1
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s2) {
                String string = s2.toString();
                if (DialogVideoNoteInput.this.mUrlList.size() > 0 || ((string.length() >= 1 && string.length() <= 5000) || !TextUtils.isEmpty(DialogVideoNoteInput.this.screenShotFileUrl_b))) {
                    DialogVideoNoteInput.this.pushBtm.setSelected(true);
                    ((TextView) DialogVideoNoteInput.this.findViewById(R.id.dialog_input_btn_comment_ok)).setTextColor(ContextCompat.getColor(DialogVideoNoteInput.this.context, SkinManager.getCurrentSkinType(DialogVideoNoteInput.this.context) == 0 ? R.color.first_txt_color : R.color.first_txt_color_night));
                } else {
                    DialogVideoNoteInput.this.pushBtm.setSelected(false);
                    ((TextView) DialogVideoNoteInput.this.findViewById(R.id.dialog_input_btn_comment_ok)).setTextColor(ContextCompat.getColor(DialogVideoNoteInput.this.context, SkinManager.getCurrentSkinType(DialogVideoNoteInput.this.context) == 0 ? R.color.forth_txt_color : R.color.forth_txt_color_night));
                }
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
        this.screenview.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.d8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16400c.lambda$onCreate$2(view);
            }
        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvPic);
        Context context = this.context;
        new GridItemDecoration(context, 3, 0, context.getResources().getDimensionPixelSize(R.dimen.dp_1), this.context.getResources().getDimensionPixelSize(R.dimen.dp_10));
        recyclerView.setAdapter(this.mPicAdapter);
        findViewById(R.id.upimg).setOnClickListener(this);
        if (!TextUtils.isEmpty(this.mBuilder.getScreenShotFilePath())) {
            PushCommentNoteUploadPicItem pushCommentNoteUploadPicItem = new PushCommentNoteUploadPicItem();
            pushCommentNoteUploadPicItem.setbImg(this.screenShotFileUrl_b);
            pushCommentNoteUploadPicItem.setsImg(this.screenShotFileUrl_s);
            pushCommentNoteUploadPicItem.setPicFilePath(this.mBuilder.screenShotFilePath);
            pushCommentNoteUploadPicItem.setStatus(1);
            pushCommentNoteUploadPicItem.setType(2);
            this.mPicAdapter.addData((UploadShowPicAdapter) pushCommentNoteUploadPicItem);
            uploadScreenShotFile();
        }
        if (!TextUtils.isEmpty(this.mBuilder.getTitle())) {
            textView.setText(this.mBuilder.getTitle());
        }
        if (!TextUtils.isEmpty(this.mBuilder.getContent())) {
            this.noteEditText.setText(this.mBuilder.getContent());
        }
        this.pushBtm.setOnClickListener(this);
        if (this.mBuilder.getEditNote() != null) {
            this.content = this.mBuilder.getEditNote().getContent();
            List<ImagesBean> img = this.mBuilder.getEditNote().getImg();
            this.mPicAdapter.getData().clear();
            if (img != null && img.size() > 0) {
                int i2 = 0;
                while (i2 < img.size()) {
                    ImagesBean imagesBean = img.get(i2);
                    PushCommentNoteUploadPicItem pushCommentNoteUploadPicItem2 = new PushCommentNoteUploadPicItem();
                    pushCommentNoteUploadPicItem2.setbImg(imagesBean.getB_img());
                    pushCommentNoteUploadPicItem2.setsImg(imagesBean.getS_img());
                    pushCommentNoteUploadPicItem2.setStatus(2);
                    pushCommentNoteUploadPicItem2.setType(i2 != 0 ? 1 : 2);
                    this.mPicAdapter.addData((UploadShowPicAdapter) pushCommentNoteUploadPicItem2);
                    i2++;
                }
            }
        }
        findViewById(R.id.dialog_input_btn_comment_ok).setOnClickListener(this);
        findViewById(R.id.iv_dialog_cancel).setOnClickListener(this);
        setScreen();
        this.noteEditText.post(new Runnable() { // from class: com.psychiatrygarden.widget.e8
            @Override // java.lang.Runnable
            public final void run() {
                this.f16433c.lambda$onCreate$3();
            }
        });
        setOnDismissListener(this);
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialog) {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(String isDismiss) {
        if (isDismiss.equals(EventBusConstant.EVENT_DIALOG_INPUT_DISMISS)) {
            CommOkData();
        }
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onFailure(Throwable t2, int errorNo, String strMsg, String requestUrl) {
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
            findViewById(R.id.dialog_input_btn_comment_ok).setVisibility(8);
            findViewById(R.id.iv_dialog_cancel).setVisibility(8);
            int pxByDp = ScreenUtil.getPxByDp(this.context, R2.attr.actionModeCloseDrawable);
            Window window = getWindow();
            Objects.requireNonNull(window);
            window.setLayout(-1, pxByDp);
            findViewById(R.id.line).setVisibility(0);
            return;
        }
        this.screenview.setVisibility(8);
        findViewById(R.id.dialog_input_btn_comment_ok).setVisibility(0);
        findViewById(R.id.iv_dialog_cancel).setVisibility(0);
        findViewById(R.id.line).setVisibility(8);
        Window window2 = getWindow();
        Objects.requireNonNull(window2);
        window2.setLayout(-1, XPopupUtils.getScreenHeight(this.context));
        new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.widget.f8
            @Override // java.lang.Runnable
            public final void run() {
                this.f16481c.lambda$setScreen$0();
            }
        }, 200L);
    }

    @Override // android.app.Dialog
    public void show() {
        super.show();
        EventBus.getDefault().register(this);
    }

    public void submitComment() throws JSONException {
        if (this.noteEditText.getText().toString().trim().length() > 5000) {
            NewToast.showShort(this.context, "笔记长度不能超过5000字", 0).show();
            return;
        }
        if (this.postType != 1) {
            pushNote(this.noteEditText.getText().toString(), this.b_img, this.s_img);
        } else if (!this.clickUpImg || this.updateSuccess) {
            pushNote(this.noteEditText.getText().toString(), this.b_img, this.s_img);
        } else {
            NewToast.showShort(this.context, "图片未上传成功，请重新选择上传图片！", 0).show();
        }
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onSuccess(String s2, String requstUrl) {
        try {
            ((BaseActivity) this.context).hideProgressDialog();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (requstUrl.equals(NetworkRequestsURL.courseVideoAddNote)) {
            try {
                JSONObject jSONObject = new JSONObject(s2);
                if (!jSONObject.optString("code").equals("200")) {
                    ToastUtil.shortToast(this.context, jSONObject.optString("message"));
                    return;
                }
                if (this.mBuilder.getEditNote() != null) {
                    ArrayList arrayList = new ArrayList();
                    List<PushCommentNoteUploadPicItem> data = this.mPicAdapter.getData();
                    if (data.size() > 0) {
                        for (PushCommentNoteUploadPicItem pushCommentNoteUploadPicItem : data) {
                            ImagesBean imagesBean = new ImagesBean();
                            imagesBean.setS_img(pushCommentNoteUploadPicItem.getsImg());
                            imagesBean.setB_img(pushCommentNoteUploadPicItem.getbImg());
                            arrayList.add(imagesBean);
                        }
                        this.mBuilder.getEditNote().setImg(arrayList);
                    }
                    this.mBuilder.getEditNote().setContent(this.noteEditText.getText().toString());
                    this.mBuilder.getEditNote().setCtime(String.valueOf(System.currentTimeMillis() / 1000));
                    onDialogNoteListener ondialognotelistener = this.clickNoteListener;
                    if (ondialognotelistener != null) {
                        ondialognotelistener.onclickStringBack(this.mBuilder.getEditNote());
                    }
                    NewToast.showShort(this.context, "编辑成功");
                    EventBus.getDefault().post(EventBusConstant.ADD_NOTE);
                } else {
                    this.mBuilder.setEditNote(new QuestionNoteBean());
                    this.mBuilder.getEditNote().setContent(this.noteEditText.getText().toString());
                    this.mBuilder.getEditNote().setCtime((System.currentTimeMillis() / 1000) + "");
                    if (!TextUtils.isEmpty(this.s_img)) {
                        ArrayList arrayList2 = new ArrayList();
                        ImagesBean imagesBean2 = new ImagesBean();
                        imagesBean2.setS_img(this.s_img);
                        imagesBean2.setB_img(this.b_img);
                        arrayList2.add(imagesBean2);
                        this.mBuilder.getEditNote().setImg(arrayList2);
                    }
                    onDialogNoteListener ondialognotelistener2 = this.clickNoteListener;
                    if (ondialognotelistener2 != null) {
                        ondialognotelistener2.onclickStringBack(this.mBuilder.getEditNote());
                    }
                    NewToast.showShort(this.context, "添加成功");
                    int intConfig = SharePreferencesUtils.readIntConfig(CommonParameter.NOTE_COUNT, getContext(), 0);
                    int intConfig2 = SharePreferencesUtils.readIntConfig(CommonParameter.COMMENT_COUNT, getContext(), 0);
                    int i2 = intConfig + 1;
                    SharePreferencesUtils.writeIntConfig(CommonParameter.NOTE_COUNT, i2, getContext());
                    EventBus.getDefault().post(new UpdateVideoCommentNote(i2, intConfig2));
                    String screenShotFilePath = this.mBuilder.getScreenShotFilePath();
                    if (!TextUtils.isEmpty(screenShotFilePath)) {
                        File file = new File(screenShotFilePath);
                        if (file.exists() && file.length() > 0) {
                            file.delete();
                        }
                    }
                }
                this.b_img = "";
                this.s_img = "";
                this.noteEditText.setText("");
                deleteFile();
                dismiss();
            } catch (Exception e3) {
                this.b_img = "";
                this.s_img = "";
                this.noteEditText.setText("");
                dismiss();
                e3.printStackTrace();
            }
        }
    }
}
